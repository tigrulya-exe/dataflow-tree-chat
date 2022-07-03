package exe.tigrulya.treechat.socket

import exe.tigrulya.treechat.model.data.NetworkData
import exe.tigrulya.treechat.network.EventChannel
import exe.tigrulya.treechat.network.EventSender
import java.net.DatagramPacket
import java.net.DatagramSocket

class UdpSocketSender(
    channel: EventChannel<NetworkData>,
    private val socket: DatagramSocket = DatagramSocket()
) : EventSender(channel) {

    override fun sendEvent(event: NetworkData) {
        val packet =  DatagramPacket(
            event.data,
            event.data.size,
            event.address
        )
        socket.send(packet)
    }
}