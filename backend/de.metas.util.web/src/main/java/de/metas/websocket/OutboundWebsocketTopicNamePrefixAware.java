package de.metas.websocket;

import java.util.Set;

@FunctionalInterface
public interface OutboundWebsocketTopicNamePrefixAware
{
	Set<String> getOutboundTopicNamePrefixes();
}
