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

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import de.metas.JsonObjectMapperHolder;
import de.metas.event.Event;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class RabbitMQConsumer extends DefaultConsumer
{
	private final static Logger logger = LogManager.getLogger(RabbitMQConsumer.class);

	private final ReentrantLock mainLock = new ReentrantLock();
	private final List<Event> messageCollector = new ArrayList<>();

	private CountDownLatch countDownLatch;

	/**
	 * Constructs a new instance and records its association to the passed-in channel.
	 *
	 * @param channel the channel to which this consumer is attached
	 */
	public RabbitMQConsumer(@NonNull final Channel channel)
	{
		super(channel);
	}

	@Override
	public void handleDelivery(final String consumerTag, final Envelope envelope, final AMQP.BasicProperties properties, final byte[] body) throws IOException
	{
		mainLock.lock();
		try
		{
			messageCollector.add(JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(body, Event.class));

			if (countDownLatch != null)
			{
				countDownLatch.countDown();
			}
		}
		finally
		{
			mainLock.unlock();
		}
	}

	@NonNull
	public List<Event> collectMessages(final int numberOfMessages) throws InterruptedException
	{
		try
		{
			setCountDownLatch(numberOfMessages);

			final boolean messageReceivedWithinTimeout = countDownLatch.await(60, TimeUnit.SECONDS);

			if (!messageReceivedWithinTimeout)
			{
				logCurrentMessageCollector(numberOfMessages);
				throw new RuntimeException("Messages not found!");
			}

			return new ArrayList<>(messageCollector);
		}
		finally
		{
			messageCollector.clear();
			setCountDownLatch(null);
		}
	}

	@Nullable
	public String waitForMessage(final int nrOfSeconds) throws InterruptedException
	{
		try
		{
			setCountDownLatch(1);

			final boolean messageReceivedWithinTimeout = countDownLatch.await(nrOfSeconds, TimeUnit.SECONDS);

			if (!messageReceivedWithinTimeout)
			{
				return null;
			}

			return messageCollector.get(0).getBody();
		}
		finally
		{
			messageCollector.clear();
			setCountDownLatch(null);
		}
	}

	private void setCountDownLatch(@Nullable final Integer numberOfMessages)
	{
		mainLock.lock();
		try
		{
			if (numberOfMessages == null)
			{
				this.countDownLatch = null;
				return;
			}

			final int numberOfMissingMessages = numberOfMessages - messageCollector.size();

			if (numberOfMissingMessages < 0)
			{
				throw new RuntimeException("Multiple messages received! see list=" + messageCollector);
			}

			this.countDownLatch = new CountDownLatch(numberOfMissingMessages);
		}
		finally
		{
			mainLock.unlock();
		}
	}

	private void logCurrentMessageCollector(final int numberOfMessages)
	{
		final StringBuilder message = new StringBuilder();

		message.append("Looking for ").append(numberOfMessages).append(" messages,").append("\n")
				.append("but so far only those were collected:").append("\n");

		messageCollector
				.forEach(queueMessage -> message
						.append(queueMessage).append(" ; ")
						.append("\n"));

		logger.error("*** Error:\n" + message);
	}
}
