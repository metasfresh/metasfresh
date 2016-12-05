package de.metas.ui.web.config;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import de.metas.logging.LogManager;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer
{
	private static final String ENDPOINT = "/stomp";
	private static final String TOPIC_Notifications = "/notifications";

	public static final String buildNotificationsTopicName(final int adUserId)
	{
		return TOPIC_Notifications + "/" + adUserId;
	}

	@Override
	public void registerStompEndpoints(final StompEndpointRegistry registry)
	{
		// the endpoint for websocket connections
		registry.addEndpoint(ENDPOINT)
				.setAllowedOrigins("*") // FIXME: for now we allow any origin
				.addInterceptors(new WebSocketHandshakeInterceptor())
				.withSockJS()
				//
				;
	}

	@Override
	public void configureMessageBroker(final MessageBrokerRegistry config)
	{
		// use the /topic prefix for outgoing WebSocket communication
		config.enableSimpleBroker(TOPIC_Notifications);

		// use the /app prefix for others
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void configureClientOutboundChannel(final ChannelRegistration registration)
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
		registration.setInterceptors(new WebSocketChannelInterceptor());

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

	private static class WebSocketChannelInterceptor extends ChannelInterceptorAdapter
	{
		private static final Logger logger = LogManager.getLogger(WebSocketConfig.WebSocketChannelInterceptor.class);

		@Override
		public void afterSendCompletion(final Message<?> message, final MessageChannel channel, final boolean sent, final Exception ex)
		{
			if (!sent)
			{
				logger.warn("Failed sending: message={}, channel={}, sent={}", message, channel, sent, ex);
			}
		}

		@Override
		public void afterReceiveCompletion(final Message<?> message, final MessageChannel channel, final Exception ex)
		{
			if (ex != null)
			{
				logger.warn("Failed receiving: message={}, channel={}", message, channel, ex);
			}
		}

	}

	private static class WebSocketHandshakeInterceptor implements HandshakeInterceptor
	{
		private static final Logger logger = LogManager.getLogger(WebSocketConfig.WebSocketHandshakeInterceptor.class);

		@Override
		public boolean beforeHandshake(final ServerHttpRequest request, final ServerHttpResponse response, final WebSocketHandler wsHandler, final Map<String, Object> attributes) throws Exception
		{
			logger.trace("before handshake: {}", wsHandler);
			return true;
		}

		@Override
		public void afterHandshake(final ServerHttpRequest request, final ServerHttpResponse response, final WebSocketHandler wsHandler, final Exception exception)
		{
			logger.trace("after handshake: {}", wsHandler);
		}
	}
}
