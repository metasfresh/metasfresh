/*
 * #%L
 * procurement-webui-backend
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.procurement.webui.service;

import com.google.common.base.Ascii;
import de.metas.common.procurement.sync.Constants;
import de.metas.common.procurement.sync.protocol.RequestToMetasfresh;
import de.metas.common.procurement.sync.protocol.RequestToProcurementWeb;
import de.metas.procurement.webui.model.RabbitMQAuditEntry;
import de.metas.procurement.webui.repository.RabbitMQAuditEntryRepository;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQAuditService
{
	private static final Logger logger = LoggerFactory.getLogger(RabbitMQAuditService.class);
	private final RabbitMQAuditEntryRepository repository;

	public RabbitMQAuditService(final RabbitMQAuditEntryRepository repository)
	{
		this.repository = repository;
	}

	public void logSendToMetasfresh(final String queueName, final RequestToMetasfresh message)
	{
		log(queueName,
			"OUT",
			message,
			message.getEventId(),
			null);
	}

	public void logReceivedFromMetasfresh(final String queueName, final RequestToProcurementWeb message)
	{
		log(queueName,
			"IN",
			message,
			message.getEventId(),
			message.getRelatedEventId());
	}

	private void log(
			final String queueName,
			final String direction,
			final Object message,
			final String eventId,
			final String relatedEventId)
	{
		try
		{
			final RabbitMQAuditEntry entry = new RabbitMQAuditEntry();
			entry.setQueue(queueName);
			entry.setDirection(direction);
			entry.setContent(Ascii.truncate(convertToString(message), RabbitMQAuditEntry.CONTENT_LENGTH, "..."));
			entry.setEventId(eventId);
			entry.setRelatedEventId(relatedEventId);

			repository.save(entry);
		}
		catch (Exception ex)
		{
			logger.warn("Failed saving audit entry for queueName={}, direction={}, message=`{}`.", queueName, direction, message, ex);
		}

	}

	private String convertToString(@NonNull final Object message)
	{
		try
		{
			return Constants.PROCUREMENT_WEBUI_OBJECT_MAPPER.writeValueAsString(message);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed converting message to JSON: {}. Returning toString().", message, ex);
			return message.toString();
		}
	}
}
