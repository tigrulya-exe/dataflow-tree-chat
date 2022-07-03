package exe.tigrulya.treechat.network

import exe.tigrulya.treechat.model.data.NetworkData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

abstract class EventSender(
    private val channel: EventChannel<NetworkData>
) {
    fun start() = runBlocking(Dispatchers.IO) {
        channel.consumeEach {
            launch {
                sendEvent(it)
            }
        }
    }

    abstract fun sendEvent(event: NetworkData)
}