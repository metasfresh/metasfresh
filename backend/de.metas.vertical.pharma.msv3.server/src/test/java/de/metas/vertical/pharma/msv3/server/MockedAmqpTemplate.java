package de.metas.vertical.pharma.msv3.server;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.ReceiveAndReplyCallback;
import org.springframework.amqp.core.ReplyToAddressCallback;

/*
 * #%L
 * metasfresh-pharma.msv3.server
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class MockedAmqpTemplate implements AmqpTemplate
{

	@Override
	public void send(final Message message) throws AmqpException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void send(final String routingKey, final Message message) throws AmqpException
	{
		throw new UnsupportedOperationException();

	}

	@Override
	public void send(final String exchange, final String routingKey, final Message message) throws AmqpException
	{
		throw new UnsupportedOperationException();

	}

	@Override
	public void convertAndSend(final Object message) throws AmqpException
	{
		System.out.println("convertAndSend: message=" + message);
	}

	@Override
	public void convertAndSend(final String routingKey, final Object message) throws AmqpException
	{
		throw new UnsupportedOperationException();

	}

	@Override
	public void convertAndSend(final String exchange, final String routingKey, final Object message) throws AmqpException
	{
		System.out.println("convertAndSend: exchange=" + exchange + ", routingKey=" + routingKey + ", message=" + message);
	}

	@Override
	public void convertAndSend(final Object message, final MessagePostProcessor messagePostProcessor) throws AmqpException
	{
		System.out.println("convertAndSend: message=" + message);
	}

	@Override
	public void convertAndSend(final String routingKey, final Object message, final MessagePostProcessor messagePostProcessor) throws AmqpException
	{
		System.out.println("convertAndSend: routingKey=" + routingKey + ", message=" + message);
	}

	@Override
	public void convertAndSend(final String exchange, final String routingKey, final Object message, final MessagePostProcessor messagePostProcessor) throws AmqpException
	{
		System.out.println("convertAndSend: exchange=" + exchange + ", routingKey=" + routingKey + ", message=" + message);
	}

	@Override
	public Message receive() throws AmqpException
	{
		throw new UnsupportedOperationException();

	}

	@Override
	public Message receive(final String queueName) throws AmqpException
	{
		throw new UnsupportedOperationException();

	}

	@Override
	public Message receive(final long timeoutMillis) throws AmqpException
	{
		throw new UnsupportedOperationException();

	}

	@Override
	public Message receive(final String queueName, final long timeoutMillis) throws AmqpException
	{
		throw new UnsupportedOperationException();

	}

	@Override
	public Object receiveAndConvert() throws AmqpException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Object receiveAndConvert(final String queueName) throws AmqpException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Object receiveAndConvert(final long timeoutMillis) throws AmqpException
	{
		throw new UnsupportedOperationException();

	}

	@Override
	public Object receiveAndConvert(final String queueName, final long timeoutMillis) throws AmqpException
	{
		throw new UnsupportedOperationException();

	}

	@Override
	public <R, S> boolean receiveAndReply(final ReceiveAndReplyCallback<R, S> callback) throws AmqpException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public <R, S> boolean receiveAndReply(final String queueName, final ReceiveAndReplyCallback<R, S> callback) throws AmqpException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public <R, S> boolean receiveAndReply(final ReceiveAndReplyCallback<R, S> callback, final String replyExchange, final String replyRoutingKey) throws AmqpException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public <R, S> boolean receiveAndReply(final String queueName, final ReceiveAndReplyCallback<R, S> callback, final String replyExchange, final String replyRoutingKey) throws AmqpException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public <R, S> boolean receiveAndReply(final ReceiveAndReplyCallback<R, S> callback, final ReplyToAddressCallback<S> replyToAddressCallback) throws AmqpException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public <R, S> boolean receiveAndReply(final String queueName, final ReceiveAndReplyCallback<R, S> callback, final ReplyToAddressCallback<S> replyToAddressCallback) throws AmqpException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Message sendAndReceive(final Message message) throws AmqpException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Message sendAndReceive(final String routingKey, final Message message) throws AmqpException
	{
		throw new UnsupportedOperationException();

	}

	@Override
	public Message sendAndReceive(final String exchange, final String routingKey, final Message message) throws AmqpException
	{
		throw new UnsupportedOperationException();

	}

	@Override
	public Object convertSendAndReceive(final Object message) throws AmqpException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Object convertSendAndReceive(final String routingKey, final Object message) throws AmqpException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Object convertSendAndReceive(final String exchange, final String routingKey, final Object message) throws AmqpException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Object convertSendAndReceive(final Object message, final MessagePostProcessor messagePostProcessor) throws AmqpException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Object convertSendAndReceive(final String routingKey, final Object message, final MessagePostProcessor messagePostProcessor) throws AmqpException
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Object convertSendAndReceive(final String exchange, final String routingKey, final Object message, final MessagePostProcessor messagePostProcessor) throws AmqpException
	{
		throw new UnsupportedOperationException();
	}

}
