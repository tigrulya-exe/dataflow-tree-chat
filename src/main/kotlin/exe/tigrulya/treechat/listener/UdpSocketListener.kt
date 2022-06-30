package exe.tigrulya.treechat.listener

import exe.tigrulya.treechat.network.EventChannel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.net.DatagramPacket
import java.net.DatagramSocket

class UdpSocketListener(
    private val channel: EventChannel,
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
            channel.send()
        }
    }
}