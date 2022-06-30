package exe.tigrulya.treechat.network

import exe.tigrulya.treechat.model.data.Event

interface EventChannel {
    suspend fun send(event: Event)

    suspend fun receive(): Event

    suspend fun consumeEach(action: (Event) -> Unit)
}