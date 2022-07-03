package exe.tigrulya.treechat.model.data

import java.util.*

data class UnconfirmedEvent(
    val event: NetworkEvent,
    val destinations: MutableSet<UUID>
)

fun MessageEvent.asUnconfirmed(sender: TreeNode): UnconfirmedEvent {
    return UnconfirmedEvent(
        MessageNetworkEvent(
            id = id,
            nodeId = sender.id,
            address = sender.address,
            messageEvent = this
        ),
        when (this) {
            is MessageEvent.Message -> mutableSetOf(nodeId)
            is MessageEvent.Reply -> to.map { it.nodeId }.toMutableSet()
        }
    )
}