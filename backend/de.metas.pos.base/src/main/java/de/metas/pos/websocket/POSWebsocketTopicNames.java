package de.metas.pos.websocket;

import com.google.common.collect.ImmutableSet;
import de.metas.pos.POSTerminalId;
import de.metas.websocket.OutboundWebsocketTopicNamePrefixAware;
import de.metas.websocket.WebsocketTopicName;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class POSWebsocketTopicNames implements OutboundWebsocketTopicNamePrefixAware
{
	public static final String TOPIC_PREFIX_ORDERS = "/pos/orders";

	@Override
	public Set<String> getOutboundTopicNamePrefixes()
	{
		return ImmutableSet.of(TOPIC_PREFIX_ORDERS);
	}

	public static WebsocketTopicName orders(@NonNull POSTerminalId posTerminalId)
	{
		return WebsocketTopicName.ofString(POSWebsocketTopicNames.TOPIC_PREFIX_ORDERS + "/" + posTerminalId.getRepoId());
	}
}
