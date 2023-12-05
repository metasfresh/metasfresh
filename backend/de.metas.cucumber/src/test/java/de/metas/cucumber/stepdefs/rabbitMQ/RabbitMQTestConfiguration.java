/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.rabbitMQ;

import de.metas.event.Topic;
import de.metas.event.remote.IEventBusQueueConfiguration;
import lombok.NonNull;
import lombok.Value;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Base64UrlNamingStrategy;
import org.springframework.amqp.core.NamingStrategy;

import java.util.Optional;

@Value
public class RabbitMQTestConfiguration implements IEventBusQueueConfiguration
{
	@NonNull
	Topic topic;
	@NonNull
	AnonymousQueue queue;
	@NonNull
	String exchangeName;

	public RabbitMQTestConfiguration(
			@NonNull final Topic topic,
			@NonNull final String exchangeName)
	{
		this.topic = topic;
		this.exchangeName = exchangeName;

		final NamingStrategy eventQueueNamingStrategy = new Base64UrlNamingStrategy(topic.getName() + "." + "serverBoot" + "-");
		this.queue = new AnonymousQueue(eventQueueNamingStrategy);
	}

	@Override
	public String getQueueName()
	{
		return queue.getName();
	}

	@Override
	public Optional<String> getTopicName()
	{
		return Optional.of(topic.getName());
	}

	@Override
	public String getFanoutExchangeName()
	{
		return exchangeName;
	}
}
