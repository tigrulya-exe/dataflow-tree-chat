package exe.tigrulya.treechat.model.data

import java.net.InetSocketAddress

sealed class Alternate {
    object Me : Alternate()
    class Other(val address: InetSocketAddress) : Alternate()
}

data class TreeNode(
    val address: InetSocketAddress,
    val alternate: Alternate?
): Identifiable() {
    var isAlive = true
}