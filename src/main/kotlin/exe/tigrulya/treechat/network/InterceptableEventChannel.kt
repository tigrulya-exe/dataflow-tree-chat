package exe.tigrulya.treechat.network

import exe.tigrulya.treechat.model.data.Event
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel

interface ChannelInterceptor<E : Event> {
    fun handle(event: E): E
}

class InterceptorsChain<E : Event> {
    private val interceptors: MutableList<ChannelInterceptor<E>> = mutableListOf()

    fun handle(event: E): E {
        return interceptors.fold(event) { handledEvent, interceptor ->
            interceptor.handle(handledEvent)
        }
    }

    operator fun plusAssign(interceptor: ChannelInterceptor<E>) {
        interceptors += interceptor
    }
}

class InterceptableEventChannel(
    capacity: Int = Channel.RENDEZVOUS,
    onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND,
): EventChannel {
    private val inputInterceptors = InterceptorsChain<Event>()
    private val outputInterceptors = InterceptorsChain<Event>()

    private val channel: Channel<Event> = Channel(
        capacity,
        onBufferOverflow
    )

    fun inputInterceptor(interceptor: ChannelInterceptor<Event>): InterceptableEventChannel {
        inputInterceptors += interceptor
        return this
    }

    fun outputInterceptor(interceptor: ChannelInterceptor<Event>): InterceptableEventChannel {
        outputInterceptors += interceptor
        return this
    }

    override suspend fun send(event: Event) {
        channel.send(inputInterceptors.handle(event))
    }

    override suspend fun receive(): Event {
        return outputInterceptors.handle(channel.receive())
    }

    override suspend fun consumeEach(action: (Event) -> Unit) {
        for (event in channel) {
            action(outputInterceptors.handle(event))
        }
    }
}