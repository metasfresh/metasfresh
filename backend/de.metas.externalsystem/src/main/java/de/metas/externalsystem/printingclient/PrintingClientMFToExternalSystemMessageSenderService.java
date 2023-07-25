/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.printingclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.externalsystem.printingclient.JsonExternalSystemPrintingClientRequest;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

import static de.metas.common.externalsystem.ExternalSystemConstants.QUEUE_NAME_MF_TO_ES_PRINTING_CLIENT;

@Service
public class PrintingClientMFToExternalSystemMessageSenderService
{
	private final RabbitTemplate rabbitTemplate;
	private final Queue queue;

	public PrintingClientMFToExternalSystemMessageSenderService(
			@NonNull final RabbitTemplate rabbitTemplate,
			@NonNull @Qualifier(QUEUE_NAME_MF_TO_ES_PRINTING_CLIENT) final Queue queue)
	{
		this.rabbitTemplate = rabbitTemplate;
		this.queue = queue;
	}

	public void send(@NonNull final JsonExternalSystemPrintingClientRequest externalSystemRequest)
	{
		final byte[] messageAsBytes;
		try
		{
			messageAsBytes = JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsBytes(externalSystemRequest);
		}
		catch (final JsonProcessingException e)
		{
			throw new AdempiereException("Exception serializing externalSystemRequest", e)
					.appendParametersToMessage()
					.setParameter("externalSystemRequest", externalSystemRequest);
		}

		final MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentEncoding(StandardCharsets.UTF_8.displayName());
		messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);

		rabbitTemplate.convertAndSend(queue.getName(), new Message(messageAsBytes, messageProperties));
	}
}
