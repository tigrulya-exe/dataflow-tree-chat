package exe.tigrulya.treechat.model.data

import java.util.*

abstract class VisibleEvent(
    id: UUID = UUID.randomUUID(),
    val nodeId: UUID
): Identifiable(id)

class HelloEvent(
    id: UUID = UUID.randomUUID(),
    nodeId: UUID
) : VisibleEvent(id, nodeId)

sealed class MessageEvent(
    id: UUID = UUID.randomUUID(),
    nodeId: UUID
): VisibleEvent(id, nodeId) {
    class Message(
        id: UUID = UUID.randomUUID(),
        nodeId: UUID,
        val body: String,
    ): MessageEvent(id, nodeId)

    data class Reply(
        val to: List<Message>,
        val content: Message
    ): MessageEvent(content.id, content.nodeId)
}