package exe.tigrulya.treechat.model

import exe.tigrulya.treechat.model.data.*
import java.net.InetSocketAddress
import java.util.*
import javax.print.attribute.standard.Destination

class ApplicationModel(
    myAddress: InetSocketAddress,
    alternate: Alternate? = null,
) {
    val data = DataModel()
    val network = NetworkModel(myAddress, alternate)
}

data class DataModel(
    val nodes: MutableMap<UUID, TreeNode> = mutableMapOf(),
    val events: MutableList<VisibleEvent> = mutableListOf(),
)

data class NetworkModel(
    val myAddress: InetSocketAddress,
    var alternate: Alternate? = null,
    val nodes: MutableMap<UUID, TreeNode> = mutableMapOf(),
    val unconfirmedMessages: MutableMap<UUID, UnconfirmedEvent> = mutableMapOf()
)

