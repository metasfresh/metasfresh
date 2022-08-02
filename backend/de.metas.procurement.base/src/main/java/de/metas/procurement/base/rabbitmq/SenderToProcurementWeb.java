/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.procurement.base.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.metas.common.procurement.sync.Constants;
import de.metas.common.procurement.sync.protocol.RequestToProcurementWeb;
import de.metas.rabbitmq.RabbitMQAuditEntry;
import de.metas.rabbitmq.RabbitMQAuditService;
import de.metas.rabbitmq.RabbitMQDirection;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.net.NetUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class SenderToProcurementWeb
{
	private final RabbitTemplate rabbitTemplate;
	private final RabbitMQAuditService rabbitLogger;
	private final Queue queue;

	public SenderToProcurementWeb(
			@NonNull final RabbitTemplate rabbitTemplate,
			@NonNull final RabbitMQAuditService rabbitLogger,
			@NonNull @Qualifier(Constants.QUEUE_NAME_MF_TO_PW) final Queue queue)
	{
		this.rabbitTemplate = rabbitTemplate;
		this.rabbitLogger = rabbitLogger;
		this.queue = queue;
	}

	public void send(@NonNull final RequestToProcurementWeb requestToProcurementWeb)
	{
		final byte[] messageAsBytes;
		try
		{
			messageAsBytes = Constants.PROCUREMENT_WEBUI_OBJECT_MAPPER.writeValueAsBytes(requestToProcurementWeb);
		}
		catch (final JsonProcessingException e)
		{
			throw new AdempiereException("Exception serializing requestToProcurementWeb", e).appendParametersToMessage()
					.setParameter("requestToProcurementWeb", requestToProcurementWeb);
		}

		final MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentEncoding(StandardCharsets.UTF_8.displayName());
		messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);

		rabbitTemplate.convertAndSend(queue.getName(), new Message(messageAsBytes, messageProperties));

		rabbitLogger.log(RabbitMQAuditEntry.builder()
								 .queueName(queue.getName())
								 .direction(RabbitMQDirection.OUT)
								 .content(requestToProcurementWeb)
								 .eventUUID(requestToProcurementWeb.getEventId())
								 .relatedEventUUID(requestToProcurementWeb.getRelatedEventId())
								 .host(NetUtils.getLocalHost())
								 .build());
	}
}
