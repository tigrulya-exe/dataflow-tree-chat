package exe.tigrulya.treechat.network

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel

interface ChannelInterceptor<E> {
    fun handle(event: E): E
}

class InterceptorsChain<E> {
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

class InterceptableChannel<E>(
    private val channel: Channel<E> = Channel()
) : EventChannel<E> {
    private val inputInterceptors = InterceptorsChain<E>()
    private val outputInterceptors = InterceptorsChain<E>()

    fun inputInterceptor(interceptor: ChannelInterceptor<E>): InterceptableChannel<E> {
        inputInterceptors += interceptor
        return this
    }

    fun outputInterceptor(interceptor: ChannelInterceptor<E>): InterceptableChannel<E> {
        outputInterceptors += interceptor
        return this
    }

    override suspend fun send(event: E) {
        channel.send(inputInterceptors.handle(event))
    }

    override suspend fun receive(): E {
        return outputInterceptors.handle(channel.receive())
    }

    override suspend fun consumeEach(action: (E) -> Unit) {
        for (event in channel) {
            action(outputInterceptors.handle(event))
        }
    }
}