package de.metas.ui.web.config;

import java.util.List;
import java.util.Map;

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
	public static final String ENDPOINT = "/stomp";
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
	public void configureClientOutboundChannel(ChannelRegistration registration)
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
		@Override
		public Message<?> preSend(final Message<?> message, final MessageChannel channel)
		{
			System.out.println("websocket - preSend: " + message + ", channel=" + channel);
			return message;
		}

		@Override
		public void postSend(final Message<?> message, final MessageChannel channel, final boolean sent)
		{
			System.out.println("websocket - postSend: " + message + ", channel=" + channel + ", sent=" + sent);
		}

		@Override
		public void afterSendCompletion(final Message<?> message, final MessageChannel channel, final boolean sent, final Exception ex)
		{
			System.out.println("websocket - afterSendCompletion: " + message + ", channel=" + channel + ", sent=" + sent + ", ex=" + ex);
			if (ex != null)
			{
				ex.printStackTrace();
			}
		}

		@Override
		public boolean preReceive(final MessageChannel channel)
		{
			System.out.println("websocket - postReceive: channel=" + channel);
			return true;
		}

		@Override
		public Message<?> postReceive(final Message<?> message, final MessageChannel channel)
		{
			System.out.println("websocket - postReceive: " + message + ", channel=" + channel);
			return message;
		}

		@Override
		public void afterReceiveCompletion(final Message<?> message, final MessageChannel channel, final Exception ex)
		{
			System.out.println("websocket - afterReceiveCompletion: " + message + ", channel=" + channel + ", ex=" + ex);
			if (ex != null)
			{
				ex.printStackTrace();
			}
		}

	}

	private static class WebSocketHandshakeInterceptor implements HandshakeInterceptor
	{

		@Override
		public boolean beforeHandshake(final ServerHttpRequest request, final ServerHttpResponse response, final WebSocketHandler wsHandler, final Map<String, Object> attributes) throws Exception
		{
			System.out.println("websocket server - before handshake: " + wsHandler);
			return true;
		}

		@Override
		public void afterHandshake(final ServerHttpRequest request, final ServerHttpResponse response, final WebSocketHandler wsHandler, final Exception exception)
		{
			System.out.println("websocket server - after handshake: " + wsHandler);
		}

	}
}
