package de.metas.server.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.JsonObjectMapperHolder;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.websocket.producers.WebSocketProducerFactory;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer
{
	private static final Logger logger = LogManager.getLogger(WebsocketConfig.class);

	private final ImmutableSet<String> topicNamePrefixes;

	public WebsocketConfig(@NonNull final Optional<List<WebSocketProducerFactory>> producerFactories)
	{
		topicNamePrefixes = extractTopicPrefixes(producerFactories);
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
		registry.addEndpoint("/stomp")
				.setAllowedOriginPatterns("http://*", "https://*")
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
		final MappingJackson2MessageConverter jsonConverter = new MappingJackson2MessageConverter();
		jsonConverter.setObjectMapper(JsonObjectMapperHolder.sharedJsonObjectMapper());
		messageConverters.add(jsonConverter);
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
}
