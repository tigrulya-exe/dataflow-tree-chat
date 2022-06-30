package exe.tigrulya.treechat.model

import exe.tigrulya.treechat.model.data.Alternate
import exe.tigrulya.treechat.model.data.TreeNode
import exe.tigrulya.treechat.model.data.VisibleEvent
import java.util.*

class ApplicationModel(
    var alternate: Alternate?,
    val neighbours: MutableMap<UUID, TreeNode> = mutableMapOf(),
    //todo
    val events: List<VisibleEvent>
)