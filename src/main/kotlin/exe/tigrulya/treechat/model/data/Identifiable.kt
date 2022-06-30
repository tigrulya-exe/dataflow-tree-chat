package exe.tigrulya.treechat.model.data

import java.util.UUID

open class Identifiable(
    val id: UUID = UUID.randomUUID()
)