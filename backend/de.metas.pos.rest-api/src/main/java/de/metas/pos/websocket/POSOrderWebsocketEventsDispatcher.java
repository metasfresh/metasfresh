package de.metas.pos.websocket;

import de.metas.Profiles;
import de.metas.currency.CurrencyRepository;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.pos.POSOrder;
import de.metas.pos.POSOrderEventListener;
import de.metas.pos.rest_api.json.JsonPOSOrder;
import de.metas.pos.websocket.json.JsonPOSOrderChangedWebSocketEvent;
import de.metas.websocket.WebsocketTopicName;
import de.metas.websocket.sender.WebsocketSender;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Optional;

@Component
@Profile(Profiles.PROFILE_App)
public class POSOrderWebsocketEventsDispatcher implements POSOrderEventListener
{
	@NonNull private static final Logger logger = LogManager.getLogger(POSOrderWebsocketEventsDispatcher.class);
	@NonNull private final CurrencyRepository currencyRepository;
	@Nullable private final WebsocketSender websocketSender;

	public POSOrderWebsocketEventsDispatcher(
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final Optional<WebsocketSender> websocketSenderOptional)
	{
		this.currencyRepository = currencyRepository;
		this.websocketSender = websocketSenderOptional.orElse(null);
		if (this.websocketSender == null)
		{
			logger.warn("Won't send websocket events because WebsocketSender is not available.");
		}
	}

	@Override
	public void onChange(final POSOrder posOrder)
	{
		send(
				POSWebsocketTopicNames.orders(posOrder.getPosTerminalId()),
				JsonPOSOrderChangedWebSocketEvent.builder()
						.posOrder(toJson(posOrder))
						.build()
		);
	}

	private JsonPOSOrder toJson(final POSOrder posOrder)
	{
		return JsonPOSOrder.from(posOrder, this::getCurrencySymbolById);
	}

	private String getCurrencySymbolById(final CurrencyId currencyId)
	{
		return currencyRepository.getById(currencyId).getSymbol().getDefaultValue();
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
