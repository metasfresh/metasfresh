package de.metas.websocket.producers;

import com.google.common.collect.ImmutableMap;
import de.metas.logging.LogManager;
import de.metas.websocket.WebsocketHeaders;
import de.metas.websocket.WebsocketSessionId;
import de.metas.websocket.WebsocketSubscriptionId;
import de.metas.websocket.WebsocketTopicName;
import de.metas.websocket.sender.WebsocketSender;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Configuration
@ConditionalOnBean(value = { WebSocketProducerFactory.class, WebsocketSender.class })
public class WebSocketProducerConfiguration
{
	private static final Logger logger = LogManager.getLogger(WebSocketProducerConfiguration.class);

	@NonNull private final WebsocketActiveSubscriptionsIndex activeSubscriptionsIndex;
	private final WebSocketProducersRegistry websocketProducersRegistry;

	public WebSocketProducerConfiguration(
			final @NonNull WebsocketSender websocketSender,
			final @NonNull List<WebSocketProducerFactory> factories)
	{
		this.activeSubscriptionsIndex = new WebsocketActiveSubscriptionsIndex();
		this.websocketProducersRegistry = new WebSocketProducersRegistry(websocketSender, factories);
	}

	@Bean
	public WebsocketActiveSubscriptionsIndex websocketActiveSubscriptionsIndex() {return activeSubscriptionsIndex;}

	@Bean
	public WebSocketProducersRegistry websocketProducersRegistry() {return websocketProducersRegistry;}

	@Bean
	public ApplicationListener<SessionSubscribeEvent> sessionSubscribeListener() {return this::onSessionSubscribeEvent;}

	@Bean
	public ApplicationListener<SessionUnsubscribeEvent> sessionUnsubscribeListener() {return this::onSessionUnsubribeEvent;}

	@Bean
	public ApplicationListener<SessionDisconnectEvent> sessionDisconnectListener() {return this::onSessionDisconnectEvent;}

	private void onSessionSubscribeEvent(final SessionSubscribeEvent event)
	{
		final WebsocketSubscriptionId subscriptionId = extractUniqueSubscriptionId(event);
		final WebsocketTopicName topicName = extractTopicName(event);
		final WebsocketHeaders headers = WebsocketHeaders.of(extractNativeHeaders(event));

		activeSubscriptionsIndex.addSubscription(subscriptionId, topicName);
		websocketProducersRegistry.onTopicSubscribed(subscriptionId, topicName, headers);

		logger.debug("Subscribed to topicName={} [ subscriptionId={} ]", topicName, subscriptionId);
	}

	private void onSessionUnsubribeEvent(final SessionUnsubscribeEvent event)
	{
		final WebsocketSubscriptionId subscriptionId = extractUniqueSubscriptionId(event);

		final WebsocketTopicName topicName = activeSubscriptionsIndex.removeSubscriptionAndGetTopicName(subscriptionId);
		websocketProducersRegistry.onTopicUnsubscribed(subscriptionId, topicName);

		logger.debug("Unsubscribed from topicName={} [ subscriptionId={} ]", topicName, subscriptionId);
	}

	private void onSessionDisconnectEvent(final SessionDisconnectEvent event)
	{
		final WebsocketSessionId sessionId = WebsocketSessionId.ofString(event.getSessionId());

		final Set<WebsocketTopicName> topicNames = activeSubscriptionsIndex.removeSessionAndGetTopicNames(sessionId);
		websocketProducersRegistry.onSessionDisconnect(sessionId, topicNames);

		logger.debug("Disconnected from topicName={} [ sessionId={} ]", topicNames, sessionId);
	}

	private static WebsocketTopicName extractTopicName(final AbstractSubProtocolEvent event)
	{
		return WebsocketTopicName.ofString(SimpMessageHeaderAccessor.getDestination(event.getMessage().getHeaders()));
	}

	private static WebsocketSubscriptionId extractUniqueSubscriptionId(final AbstractSubProtocolEvent event)
	{
		final MessageHeaders headers = event.getMessage().getHeaders();
		final WebsocketSessionId sessionId = WebsocketSessionId.ofString(SimpMessageHeaderAccessor.getSessionId(headers));
		final String simpSubscriptionId = SimpMessageHeaderAccessor.getSubscriptionId(headers);
		return WebsocketSubscriptionId.of(sessionId, Objects.requireNonNull(simpSubscriptionId, "simpSubscriptionId"));
	}

	@NonNull
	private static Map<String, List<String>> extractNativeHeaders(@NonNull final AbstractSubProtocolEvent event)
	{
		final Object nativeHeaders = event.getMessage().getHeaders().get("nativeHeaders");
		
		return Optional.ofNullable(nativeHeaders)
				.filter(headers -> headers instanceof Map)
				.filter(headers -> !((Map<?, ?>)headers).isEmpty())
				.map(headers -> (Map<String, List<String>>)headers)
				.map(ImmutableMap::copyOf)
				.orElseGet(ImmutableMap::of);
	}
}
