package exe.tigrulya.treechat.model.data

import java.util.*

abstract class Event : Identifiable()

data class NodeInfo(
    val id: UUID,
    val userName: String,
    //todo
    val profilePic: String?
)

data class AckEvent(
    val ackId: UUID,
    val nodeId: UUID
) : Event()

class KeepAliveEvent(
    val nodeId: UUID
) : Event()

// Visible events

abstract class VisibleEvent(
    val nodeInfo: NodeInfo
)

class HelloEvent(
    nodeInfo: NodeInfo
) : VisibleEvent(nodeInfo)

sealed class MessageEvent(
    nodeInfo: NodeInfo
): VisibleEvent(nodeInfo) {
    class Message(
        nodeInfo: NodeInfo,
        val body: String,
    ): MessageEvent(nodeInfo)

    data class Reply(
        val to: List<Message>,
        val content: Message
    ): MessageEvent(content.nodeInfo)
}