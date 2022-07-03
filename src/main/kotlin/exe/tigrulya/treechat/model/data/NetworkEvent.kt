package exe.tigrulya.treechat.model.data

import java.net.InetSocketAddress
import java.util.*

open class NetworkData(
    val address: InetSocketAddress,
    val data: ByteArray
)

class InputNetworkData(
    address: InetSocketAddress,
    data: ByteArray,
    val type: EventType,
) : NetworkData(address, data)

abstract class NetworkEvent(
    id: UUID,
    val nodeId: UUID,
    val address: InetSocketAddress,
): Identifiable(id)

//todo nodeId+address -> separate class
class AckNetworkEvent(
    id: UUID,
    nodeId: UUID,
    address: InetSocketAddress,
) : NetworkEvent(id, nodeId, address) {

    constructor(event: NetworkEvent) : this(event.id, event.nodeId, event.address)
}

class KeepAliveNetworkEvent(
    id: UUID,
    nodeId: UUID,
    address: InetSocketAddress
) : NetworkEvent(id, nodeId, address)

class HelloNetworkEvent(
    id: UUID,
    nodeId: UUID,
    address: InetSocketAddress,
    val userName: String,
    val alternate: InetSocketAddress,
    val profilePic: String?
) : NetworkEvent(id, nodeId, address)

class MessageNetworkEvent(
    id: UUID,
    nodeId: UUID,
    address: InetSocketAddress,
    val messageEvent: MessageEvent
) : NetworkEvent(id, nodeId, address)
