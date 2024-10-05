package de.metas.pos.websocket;

import de.metas.logging.LogManager;
import de.metas.pos.POSOrder;
import de.metas.pos.POSOrderChangedWebSocketEvent;
import de.metas.websocket.WebsocketTopicName;
import de.metas.websocket.sender.WebsocketSender;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;

@Service
public class POSOrderWebsocketSender
{
	@NonNull private static final Logger logger = LogManager.getLogger(POSOrderWebsocketSender.class);
	@Nullable private final WebsocketSender websocketSender;

	public POSOrderWebsocketSender(@NonNull final Optional<WebsocketSender> websocketSenderOptional)
	{
		this.websocketSender = websocketSenderOptional.orElse(null);
		if (this.websocketSender == null)
		{
			logger.warn("Won't send websocket events because WebsocketSender is not available.");
		}
	}

	public void notifyFrontendThatOrderChanged(final POSOrder posOrder)
	{
		send(
				POSWebsocketTopicNames.orders(posOrder.getPosTerminalId()),
				POSOrderChangedWebSocketEvent.builder()
						.posOrderId(posOrder.getExternalId())
						.build()
		);
	}

	private void send(final WebsocketTopicName destination, final Object event)
	{
		if (websocketSender == null)
		{
			// shall not happen because we shall not change data that trigger WS events in instances where WS is not available (like Swing).
			logger.info("Skip sending event to WS `{}` because websocket infrastructure is not available: {}", destination, event);
			return;
		}

		websocketSender.convertAndSend(destination, event);
	}
}
