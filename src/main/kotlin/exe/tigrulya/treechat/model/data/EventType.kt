package exe.tigrulya.treechat.model.data

interface EventType {
    val type: String
}

enum class DefaultEventTypes: EventType {
    MESSAGE,
    MESSAGE_REPLY,
    ACK,
    KEEP_ALIVE,
    HELLO;

    override val type: String
        get() = name
}