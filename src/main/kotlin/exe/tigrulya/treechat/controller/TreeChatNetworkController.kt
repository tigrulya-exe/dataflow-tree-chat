package exe.tigrulya.treechat.controller

import exe.tigrulya.treechat.model.NetworkModel
import exe.tigrulya.treechat.model.data.*
import exe.tigrulya.treechat.network.EventChannel

typealias EventHandler = suspend (NetworkEvent) -> Unit

class TreeChatNetworkController(
    val model: NetworkModel,
    val eventChannel: EventChannel<NetworkEvent>,
    val senderChannel: EventChannel<NetworkEvent>
) {
    val eventHandlers: MutableMap<EventType, EventHandler> = mutableMapOf(
        DefaultEventTypes.KEEP_ALIVE to this::handleKeepAlive,
        DefaultEventTypes.MESSAGE to this::handleMessage,
        DefaultEventTypes.HELLO to this::handleHello,
        DefaultEventTypes.ACK to this::handleAck
    )

    suspend fun handle(event: NetworkEvent, eventType: EventType) {
        eventHandlers[eventType]?.invoke(event)
    }

    suspend fun onNewMessage(event: MessageEvent) {
        val node = model.nodes[event.nodeId]
            ?: throw IllegalArgumentException("Wrong nodeId")

        val unconfirmedEvent = event.asUnconfirmed(node)
        senderChannel.send(unconfirmedEvent.event)
        model.unconfirmedMessages[event.id] = unconfirmedEvent
    }

    private suspend fun handleKeepAlive(event: NetworkEvent) {
        model.nodes[event.id]?.onKeepAlive()
    }

    private suspend fun handleMessage(event: NetworkEvent) {
        ack(event)
        forwardEvent(event)
    }

    private suspend fun handleHello(event: NetworkEvent) {
        ack(event)
        event as HelloNetworkEvent
        model.apply {
            nodes[event.nodeId] = TreeNode(
                id = event.nodeId,
                name = event.userName,
                address = event.address,
                alternate = if (myAddress == event.alternate)
                    Alternate.Me
                else Alternate.Other(event.alternate)
            )


            if (alternate == null) {
                alternate = Alternate.Other(event.address)
            }
        }
        forwardEvent(event)
    }

    private suspend fun handleAck(event: NetworkEvent) {
        val unconfirmedEvent = model.unconfirmedMessages[event.id]
        unconfirmedEvent?.let {
            unconfirmedEvent.destinations.remove(event.nodeId)
            if (unconfirmedEvent.destinations.isEmpty()) {
                model.unconfirmedMessages.remove(event.id)
            }
        }
    }

    private suspend fun forwardEvent(event: NetworkEvent) = eventChannel.send(event)

    private suspend fun ack(event: NetworkEvent) = senderChannel.send(AckNetworkEvent(event))
}