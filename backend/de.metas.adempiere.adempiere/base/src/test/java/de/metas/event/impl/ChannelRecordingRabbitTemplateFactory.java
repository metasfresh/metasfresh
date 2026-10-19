/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.event.impl;

import com.google.common.collect.ImmutableList;
import com.rabbitmq.client.AlreadyClosedException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ShutdownSignalException;
import lombok.NonNull;
import lombok.Value;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * Creates a real {@link RabbitTemplate} on top of a fake broker connection that opens a <b>new channel for every channel request</b>.
 * <p>
 * That is what Spring's {@code CachingConnectionFactory} does when its cached channel is busy (checked out by another thread):
 * its channel cache size is not a limit. RabbitMQ only keeps the order of messages within one channel,
 * so two sends that end up on different channels can reach the consumer swapped.
 * <p>
 * Every {@code basicPublish} is recorded with the number of the channel it went out on.
 */
public class ChannelRecordingRabbitTemplateFactory
{
	@Value
	public static class Publish
	{
		int channelNumber;
		@NonNull String exchange;
		@NonNull String routingKey;
		@NonNull String body;
	}

	private final List<Publish> publishes = new ArrayList<>();
	private final Set<String> bodiesToFail = new HashSet<>();
	private int nextChannelNumber = 1;

	/**
	 * @param bodyExtractor renders the sent object as message body; that body is what {@link Publish#getBody()} returns.
	 */
	public RabbitTemplate newRabbitTemplate(@NonNull final Function<Object, String> bodyExtractor)
	{
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(newProxy(ConnectionFactory.class, this::invokeOnConnectionFactory));
		rabbitTemplate.setMessageConverter(new MessageConverter()
		{
			@Override
			public Message toMessage(final Object object, final MessageProperties messageProperties)
			{
				return new Message(bodyExtractor.apply(object).getBytes(StandardCharsets.UTF_8), messageProperties);
			}

			@Override
			public Object fromMessage(final Message message)
			{
				throw new UnsupportedOperationException();
			}
		});
		return rabbitTemplate;
	}

	/**
	 * The publish of a message with the given body fails, and the broker closes that channel, like it does e.g. for a missing exchange.
	 */
	public void failPublishOfBody(@NonNull final String body)
	{
		bodiesToFail.add(body);
	}

	public ImmutableList<Publish> getPublishes()
	{
		return ImmutableList.copyOf(publishes);
	}

	private Object invokeOnConnectionFactory(final Method method, final Object[] args)
	{
		if ("createConnection".equals(method.getName()))
		{
			return newProxy(Connection.class, this::invokeOnConnection);
		}
		return defaultValue(method);
	}

	private Object invokeOnConnection(final Method method, final Object[] args)
	{
		switch (method.getName())
		{
			case "createChannel":
				return newChannel(nextChannelNumber++);
			case "isOpen":
				return true;
			default:
				return defaultValue(method);
		}
	}

	private Channel newChannel(final int channelNumber)
	{
		final boolean[] open = { true };
		return newProxy(Channel.class, (method, args) -> {
			switch (method.getName())
			{
				case "getChannelNumber":
					return channelNumber;
				case "isOpen":
					return open[0];
				case "basicPublish":
				{
					if (!open[0])
					{
						throw new AlreadyClosedException(new ShutdownSignalException(false, false, null, "channel " + channelNumber));
					}
					final String exchange = (String)args[0];
					final String routingKey = (String)args[1];
					final byte[] body = (byte[])args[args.length - 1];
					final String bodyStr = new String(body, StandardCharsets.UTF_8);
					if (bodiesToFail.contains(bodyStr))
					{
						open[0] = false;
						throw new IOException("Simulated publish failure for body=" + bodyStr);
					}
					publishes.add(new Publish(channelNumber, exchange, routingKey, bodyStr));
					return null;
				}
				default:
					return defaultValue(method);
			}
		});
	}

	private interface Handler
	{
		Object invoke(Method method, Object[] args) throws Throwable;
	}

	@SuppressWarnings("unchecked")
	private static <T> T newProxy(final Class<T> type, final Handler handler)
	{
		final InvocationHandler invocationHandler = (proxy, method, args) -> {
			if (method.getDeclaringClass() == Object.class)
			{
				switch (method.getName())
				{
					case "equals":
						return proxy == args[0];
					case "hashCode":
						return System.identityHashCode(proxy);
					default:
						return type.getSimpleName() + "@" + System.identityHashCode(proxy);
				}
			}
			return handler.invoke(method, args != null ? args : new Object[] {});
		};
		return (T)Proxy.newProxyInstance(ChannelRecordingRabbitTemplateFactory.class.getClassLoader(), new Class<?>[] { type }, invocationHandler);
	}

	private static Object defaultValue(final Method method)
	{
		final Class<?> returnType = method.getReturnType();
		if (returnType == boolean.class)
		{
			return false;
		}
		else if (returnType == int.class)
		{
			return 0;
		}
		else if (returnType == long.class)
		{
			return 0L;
		}
		else if (returnType.isPrimitive() && returnType != void.class)
		{
			throw new UnsupportedOperationException("No default for " + method);
		}
		return null;
	}
}
