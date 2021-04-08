package de.metas.ui.web.websocket;

import de.metas.logging.LogManager;
import de.metas.ui.web.WebuiURLs;
import de.metas.ui.web.session.UserSession;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * #%L
 * metasfresh-webui-vaadin
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer
{
	private static final Logger logger = LogManager.getLogger(WebSocketConfig.class);

	private static final String ENDPOINT = "/stomp";

	@Override
	public void registerStompEndpoints(@NonNull final StompEndpointRegistry registry)
	{
		final StompWebSocketEndpointRegistration endpoint = registry.addEndpoint(ENDPOINT);

		final WebuiURLs webuiURLs = WebuiURLs.newInstance();
		if (webuiURLs.isCrossSiteUsageAllowed())
		{
			// we can't allow '*' anymore, see https://github.com/spring-projects/spring-framework/issues/26111	
			endpoint.setAllowedOriginPatterns("http://*", "https://*");
		}
		else
		{
			endpoint.setAllowedOrigins(webuiURLs.getFrontendURL()); // the endpoint for websocket connections	
		}
		endpoint
				.addInterceptors(new WebsocketHandshakeInterceptor())
				.withSockJS();
	}

	@Override
	public void configureMessageBroker(@NonNull final MessageBrokerRegistry config)
	{
		// use the /topic prefix for outgoing Websocket communication
		config.enableSimpleBroker(
				WebsocketTopicNames.TOPIC_UserSession,
				WebsocketTopicNames.TOPIC_Notifications,
				WebsocketTopicNames.TOPIC_View,
				WebsocketTopicNames.TOPIC_Document,
				WebsocketTopicNames.TOPIC_Board,
				WebsocketTopicNames.TOPIC_Dashboard,
				WebsocketTopicNames.TOPIC_Devices);

		// use the /app prefix for others
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void configureClientOutboundChannel(@NonNull final ChannelRegistration registration)
	{
		//
		// IMPORTANT: make sure we are using only one thread for sending outbound messages.
		// If not, it might be that the messages will not be sent in the right order,
		// and that's important for things like WS notifications API.
		// ( thanks to http://stackoverflow.com/questions/29689838/sockjs-receive-stomp-messages-from-spring-websocket-out-of-order )
		registration.taskExecutor()
				.corePoolSize(1)
				.maxPoolSize(1);
	}

	@Override
	public void configureClientInboundChannel(final ChannelRegistration registration)
	{
		registration.interceptors(new WebsocketChannelInterceptor());

		// NOTE: atm we don't care if the inbound messages arrived in the right order
		// When and If we would care we would restrict the taskExecutor()'s corePoolSize to ONE.
		// see: configureClientOutboundChannel().
	}

	@Override
	public boolean configureMessageConverters(final List<MessageConverter> messageConverters)
	{
		messageConverters.add(new MappingJackson2MessageConverter());
		return true;
	}

	private static class WebsocketChannelInterceptor implements ChannelInterceptor
	{
		private static final Logger logger = LogManager.getLogger(WebsocketChannelInterceptor.class);

		@Override
		public void afterSendCompletion(final @NonNull Message<?> message, final @NonNull MessageChannel channel, final boolean sent, final Exception ex)
		{
			if (!sent)
			{
				logger.warn("Failed sending: message={}, channel={}, sent={}", message, channel, sent, ex);
			}
		}

		@Override
		public void afterReceiveCompletion(final Message<?> message, final @NonNull MessageChannel channel, final Exception ex)
		{
			if (ex != null)
			{
				logger.warn("Failed receiving: message={}, channel={}", message, channel, ex);
			}
		}

	}

	private static class WebsocketHandshakeInterceptor implements HandshakeInterceptor
	{
		private static final Logger logger = LogManager.getLogger(WebsocketHandshakeInterceptor.class);

		@Override
		public boolean beforeHandshake(@NonNull final ServerHttpRequest request, final @NonNull ServerHttpResponse response, final @NonNull WebSocketHandler wsHandler, final @NonNull Map<String, Object> attributes)
		{
			final UserSession userSession = UserSession.getCurrentOrNull();
			if (userSession == null)
			{
				logger.warn("Websocket connection not allowed (missing userSession)");
				response.setStatusCode(HttpStatus.UNAUTHORIZED);
				return false;
			}

			if (!userSession.isLoggedIn())
			{
				logger.warn("Websocket connection not allowed (not logged in) - userSession={}", userSession);
				response.setStatusCode(HttpStatus.UNAUTHORIZED);
				return false;
			}

			return true;
		}

		@Override
		public void afterHandshake(final @NonNull ServerHttpRequest request, final @NonNull ServerHttpResponse response, final @NonNull WebSocketHandler wsHandler, final Exception exception)
		{
			// nothing
		}
	}

	//
	//
	// Event handlers
	//
	//

	private static WebsocketTopicName extractTopicName(final AbstractSubProtocolEvent event)
	{
		return WebsocketTopicName.ofString(SimpMessageHeaderAccessor.getDestination(event.getMessage().getHeaders()));
	}

	private static WebsocketSubscriptionId extractUniqueSubscriptionId(final AbstractSubProtocolEvent event)
	{
		final MessageHeaders headers = event.getMessage().getHeaders();
		final WebsocketSessionId sessionId = WebsocketSessionId.ofString(SimpMessageHeaderAccessor.getSessionId(headers));
		final String simpSubscriptionId = SimpMessageHeaderAccessor.getSubscriptionId(headers);
		return WebsocketSubscriptionId.of(sessionId, simpSubscriptionId);
	}

	@Component
	public static class WebsocketSubscribeEventListener implements ApplicationListener<SessionSubscribeEvent>
	{
		private final WebsocketActiveSubscriptionsIndex activeSubscriptionsIndex;
		private final WebSocketProducersRegistry websocketProducersRegistry;

		public WebsocketSubscribeEventListener(
				@NonNull final WebsocketActiveSubscriptionsIndex activeSubscriptionsIndex,
				@NonNull final WebSocketProducersRegistry websocketProducersRegistry)
		{
			this.activeSubscriptionsIndex = activeSubscriptionsIndex;
			this.websocketProducersRegistry = websocketProducersRegistry;
		}

		@Override
		public void onApplicationEvent(final @NonNull SessionSubscribeEvent event)
		{
			final WebsocketSubscriptionId subscriptionId = extractUniqueSubscriptionId(event);
			final WebsocketTopicName topicName = extractTopicName(event);

			activeSubscriptionsIndex.addSubscription(subscriptionId, topicName);
			websocketProducersRegistry.onTopicSubscribed(subscriptionId, topicName);

			logger.debug("Subscribed to topicName={} [ subscriptionId={} ]", topicName, subscriptionId);
		}
	}

	@Component
	public static class WebsocketUnsubscribeEventListener implements ApplicationListener<SessionUnsubscribeEvent>
	{
		private final WebsocketActiveSubscriptionsIndex activeSubscriptionsIndex;
		private final WebSocketProducersRegistry websocketProducersRegistry;

		public WebsocketUnsubscribeEventListener(
				@NonNull final WebsocketActiveSubscriptionsIndex activeSubscriptionsIndex,
				@NonNull final WebSocketProducersRegistry websocketProducersRegistry)
		{
			this.activeSubscriptionsIndex = activeSubscriptionsIndex;
			this.websocketProducersRegistry = websocketProducersRegistry;
		}

		@Override
		public void onApplicationEvent(final @NonNull SessionUnsubscribeEvent event)
		{
			final WebsocketSubscriptionId subscriptionId = extractUniqueSubscriptionId(event);

			final WebsocketTopicName topicName = activeSubscriptionsIndex.removeSubscriptionAndGetTopicName(subscriptionId);
			websocketProducersRegistry.onTopicUnsubscribed(subscriptionId, topicName);

			logger.debug("Unsubscribed from topicName={} [ subscriptionId={} ]", topicName, subscriptionId);
		}
	}

	@Component
	public static class WebsocketDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent>
	{
		private final WebsocketActiveSubscriptionsIndex activeSubscriptionsIndex;
		private final WebSocketProducersRegistry websocketProducersRegistry;

		public WebsocketDisconnectEventListener(
				@NonNull final WebsocketActiveSubscriptionsIndex activeSubscriptionsIndex,
				@NonNull final WebSocketProducersRegistry websocketProducersRegistry)
		{
			this.activeSubscriptionsIndex = activeSubscriptionsIndex;
			this.websocketProducersRegistry = websocketProducersRegistry;
		}

		@Override
		public void onApplicationEvent(final SessionDisconnectEvent event)
		{
			final WebsocketSessionId sessionId = WebsocketSessionId.ofString(event.getSessionId());
			final Set<WebsocketTopicName> topicNames = activeSubscriptionsIndex.removeSessionAndGetTopicNames(sessionId);

			websocketProducersRegistry.onSessionDisconnect(sessionId, topicNames);

			logger.debug("Disconnected from topicName={} [ sessionId={} ]", topicNames, sessionId);
		}
	}
}
