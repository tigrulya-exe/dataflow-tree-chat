package exe.tigrulya.treechat.socket

import exe.tigrulya.treechat.model.data.NetworkData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.runBlocking
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetSocketAddress

class UdpSocketListener(
    private val channel: SendChannel<NetworkData>,
    bufferSize: Int
) {
    private val receiveBuffer: ByteArray = ByteArray(bufferSize)

    fun listen(port: Int) = runBlocking(Dispatchers.IO) {
        val socket = DatagramSocket(port)
        val datagramPacket = DatagramPacket(
            receiveBuffer,
            receiveBuffer.size
        )

        //todo add flag + async net io
        while (true) {
            socket.receive(datagramPacket)

            channel.send(
                NetworkData(
                    address = datagramPacket.socketAddress as InetSocketAddress,
                    data = datagramPacket.data
                )
            )
        }
    }
}