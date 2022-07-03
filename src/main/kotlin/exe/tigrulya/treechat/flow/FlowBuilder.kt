package exe.tigrulya.treechat.flow

import exe.tigrulya.treechat.model.data.MessageEvent
import exe.tigrulya.treechat.model.data.NetworkData
import exe.tigrulya.treechat.socket.UdpSocketListener
import ru.nsu.convoyeur.core.declaration.graph.SourceNode
import ru.nsu.convoyeur.core.declaration.graph.TransformNode

class FlowBuilder {
    fun test() {
        val sourceNode = SourceNode<NetworkData>("EventListener") {
            UdpSocketListener(
                outputChannels().values.first(),
                10
            )
        }

        val ser = GsonSerializer()
        val mapperNode = TransformNode<NetworkData, MessageEvent>(
            "NetworkEventToMessageEventMapper"
        ) {_, networkEvent ->
            ser.toObject(networkEvent.data)
        }



        sourceNode.to(mapperNode)
    }
}