package exe.tigrulya.treechat.network

interface EventChannel<E> {
    suspend fun send(event: E)

    suspend fun receive(): E

    suspend fun consumeEach(action: (E) -> Unit)
}