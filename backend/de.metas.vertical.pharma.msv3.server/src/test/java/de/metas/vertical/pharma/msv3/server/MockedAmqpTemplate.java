package de.metas.vertical.pharma.msv3.server;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.ReceiveAndReplyCallback;
import org.springframework.amqp.core.ReplyToAddressCallback;
import org.springframework.core.ParameterizedTypeReference;

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

	@java.lang.Override
	public void send(final Message message) throws AmqpException
	{
		
	}

	@java.lang.Override
	public void send(final java.lang.String routingKey, final Message message) throws AmqpException
	{

	}

	@java.lang.Override
	public void send(final java.lang.String exchange, final java.lang.String routingKey, final Message message) throws AmqpException
	{

	}

	@java.lang.Override
	public void convertAndSend(final java.lang.Object message) throws AmqpException
	{

	}

	@java.lang.Override
	public void convertAndSend(final java.lang.String routingKey, final java.lang.Object message) throws AmqpException
	{

	}

	@java.lang.Override
	public void convertAndSend(final java.lang.String exchange, final java.lang.String routingKey, final java.lang.Object message) throws AmqpException
	{

	}

	@java.lang.Override
	public void convertAndSend(final java.lang.Object message, final MessagePostProcessor messagePostProcessor) throws AmqpException
	{

	}

	@java.lang.Override
	public void convertAndSend(final java.lang.String routingKey, final java.lang.Object message, final MessagePostProcessor messagePostProcessor) throws AmqpException
	{

	}

	@java.lang.Override
	public void convertAndSend(final java.lang.String exchange, final java.lang.String routingKey, final java.lang.Object message, final MessagePostProcessor messagePostProcessor) throws AmqpException
	{

	}

	@java.lang.Override
	public Message receive() throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public Message receive(final java.lang.String queueName) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public Message receive(final long timeoutMillis) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public Message receive(final java.lang.String queueName, final long timeoutMillis) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public java.lang.Object receiveAndConvert() throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public java.lang.Object receiveAndConvert(final java.lang.String queueName) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public java.lang.Object receiveAndConvert(final long timeoutMillis) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public java.lang.Object receiveAndConvert(final java.lang.String queueName, final long timeoutMillis) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public <T> T receiveAndConvert(final ParameterizedTypeReference<T> type) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public <T> T receiveAndConvert(final java.lang.String queueName, final ParameterizedTypeReference<T> type) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public <T> T receiveAndConvert(final long timeoutMillis, final ParameterizedTypeReference<T> type) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public <T> T receiveAndConvert(final java.lang.String queueName, final long timeoutMillis, final ParameterizedTypeReference<T> type) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public <R, S> boolean receiveAndReply(final ReceiveAndReplyCallback<R, S> callback) throws AmqpException
	{
		return false;
	}

	@java.lang.Override
	public <R, S> boolean receiveAndReply(final java.lang.String queueName, final ReceiveAndReplyCallback<R, S> callback) throws AmqpException
	{
		return false;
	}

	@java.lang.Override
	public <R, S> boolean receiveAndReply(final ReceiveAndReplyCallback<R, S> callback, final java.lang.String replyExchange, final java.lang.String replyRoutingKey) throws AmqpException
	{
		return false;
	}

	@java.lang.Override
	public <R, S> boolean receiveAndReply(final java.lang.String queueName, final ReceiveAndReplyCallback<R, S> callback, final java.lang.String replyExchange, final java.lang.String replyRoutingKey) throws AmqpException
	{
		return false;
	}

	@java.lang.Override
	public <R, S> boolean receiveAndReply(final ReceiveAndReplyCallback<R, S> callback, final ReplyToAddressCallback<S> replyToAddressCallback) throws AmqpException
	{
		return false;
	}

	@java.lang.Override
	public <R, S> boolean receiveAndReply(final java.lang.String queueName, final ReceiveAndReplyCallback<R, S> callback, final ReplyToAddressCallback<S> replyToAddressCallback) throws AmqpException
	{
		return false;
	}

	@java.lang.Override
	public Message sendAndReceive(final Message message) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public Message sendAndReceive(final java.lang.String routingKey, final Message message) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public Message sendAndReceive(final java.lang.String exchange, final java.lang.String routingKey, final Message message) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public java.lang.Object convertSendAndReceive(final java.lang.Object message) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public java.lang.Object convertSendAndReceive(final java.lang.String routingKey, final java.lang.Object message) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public java.lang.Object convertSendAndReceive(final java.lang.String exchange, final java.lang.String routingKey, final java.lang.Object message) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public java.lang.Object convertSendAndReceive(final java.lang.Object message, final MessagePostProcessor messagePostProcessor) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public java.lang.Object convertSendAndReceive(final java.lang.String routingKey, final java.lang.Object message, final MessagePostProcessor messagePostProcessor) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public java.lang.Object convertSendAndReceive(final java.lang.String exchange, final java.lang.String routingKey, final java.lang.Object message, final MessagePostProcessor messagePostProcessor) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public <T> T convertSendAndReceiveAsType(final java.lang.Object message, final ParameterizedTypeReference<T> responseType) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public <T> T convertSendAndReceiveAsType(final java.lang.String routingKey, final java.lang.Object message, final ParameterizedTypeReference<T> responseType) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public <T> T convertSendAndReceiveAsType(final java.lang.String exchange, final java.lang.String routingKey, final java.lang.Object message, final ParameterizedTypeReference<T> responseType) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public <T> T convertSendAndReceiveAsType(final java.lang.Object message, final MessagePostProcessor messagePostProcessor, final ParameterizedTypeReference<T> responseType) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public <T> T convertSendAndReceiveAsType(final java.lang.String routingKey, final java.lang.Object message, final MessagePostProcessor messagePostProcessor, final ParameterizedTypeReference<T> responseType) throws AmqpException
	{
		return null;
	}

	@java.lang.Override
	public <T> T convertSendAndReceiveAsType(final java.lang.String exchange, final java.lang.String routingKey, final java.lang.Object message, final MessagePostProcessor messagePostProcessor, final ParameterizedTypeReference<T> responseType) throws AmqpException
	{
		return null;
	}
}
