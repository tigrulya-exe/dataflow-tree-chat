package exe.tigrulya.treechat.model.data

import java.net.InetSocketAddress
import java.time.Instant
import java.util.*

sealed class Alternate {
    object Me : Alternate()
    class Other(val address: InetSocketAddress) : Alternate()
}

class TreeNode(
    id: UUID,
    val name: String,
    val address: InetSocketAddress,
    val alternate: Alternate? = null,
    val profilePic: String? = null
) : Identifiable(id) {
    private var _lastHeartbeat: Instant = Instant.now()
    val lastHeartbeat: Instant
        get() = _lastHeartbeat

    fun onKeepAlive() {
        _lastHeartbeat = Instant.now()
    }
}