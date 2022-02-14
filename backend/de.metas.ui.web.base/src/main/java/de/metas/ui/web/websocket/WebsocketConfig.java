package de.metas.ui.web.websocket;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import de.metas.ui.web.WebuiURLs;
import de.metas.ui.web.session.UserSession;
import de.metas.util.Check;
import de.metas.websocket.producers.WebSocketProducerFactory;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer
{
	private static final Logger logger = LogManager.getLogger(WebsocketConfig.class);

	private final ImmutableSet<String> topicNamePrefixes;

	public WebsocketConfig(@NonNull final Optional<List<WebSocketProducerFactory>> producerFactories)
	{
		topicNamePrefixes = ImmutableSet.<String>builder()
				.add(WebsocketTopicNames.TOPIC_UserSession)
				.add(WebsocketTopicNames.TOPIC_Notifications)
				.add(WebsocketTopicNames.TOPIC_View)
				.add(WebsocketTopicNames.TOPIC_Document)
				.add(WebsocketTopicNames.TOPIC_Board)
				.add(WebsocketTopicNames.TOPIC_Dashboard)
				.addAll(extractTopicPrefixes(producerFactories))
				.build();
	}

	private static ImmutableSet<String> extractTopicPrefixes(@NonNull final Optional<List<WebSocketProducerFactory>> producerFactories)
	{
		return producerFactories.orElse(ImmutableList.of())
				.stream()
				.map(WebSocketProducerFactory::getTopicNamePrefix)
				.filter(Objects::nonNull)
				.filter(Check::isNotBlank)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public void registerStompEndpoints(@NonNull final StompEndpointRegistry registry)
	{
		final StompWebSocketEndpointRegistration endpoint = registry.addEndpoint("/stomp");

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
				.addInterceptors(new AuthorizationHandshakeInterceptor())
				.withSockJS();
	}

	@Override
	public void configureMessageBroker(@NonNull final MessageBrokerRegistry config)
	{
		// use the /topic prefix for outgoing Websocket communication
		config.enableSimpleBroker(topicNamePrefixes.toArray(new String[0]));
		logger.info("configureMessageBroker: topicNamePrefixes={}", topicNamePrefixes);

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
		registration.interceptors(new LoggingChannelInterceptor());

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

	private static class LoggingChannelInterceptor implements ChannelInterceptor
	{
		private static final Logger logger = LogManager.getLogger(LoggingChannelInterceptor.class);

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

	private static class AuthorizationHandshakeInterceptor implements HandshakeInterceptor
	{
		private static final Logger logger = LogManager.getLogger(AuthorizationHandshakeInterceptor.class);

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
}
