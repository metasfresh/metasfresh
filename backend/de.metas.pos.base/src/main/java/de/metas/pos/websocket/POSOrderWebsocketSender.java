package de.metas.pos.websocket;

import de.metas.pos.POSOrder;
import de.metas.pos.POSOrderChangedWebSocketEvent;
import de.metas.websocket.sender.WebsocketSender;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class POSOrderWebsocketSender
{
	@NonNull private final WebsocketSender websocketSender;

	public void notifyFrontendThatOrderChanged(final POSOrder posOrder)
	{
		websocketSender.convertAndSend(
				POSWebsocketTopicNames.orders(posOrder.getPosTerminalId()),
				POSOrderChangedWebSocketEvent.builder()
						.posOrderId(posOrder.getExternalId())
						.build()
		);
	}
}
