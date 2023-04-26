/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.metas.JsonObjectMapperHolder;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_RabbitMQ_Message_Audit;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQAuditService
{
	private static final Logger logger = LogManager.getLogger(RabbitMQAuditService.class);

	public void log(@NonNull final RabbitMQAuditEntry entry)
	{
		try
		{
			final I_RabbitMQ_Message_Audit record = InterfaceWrapperHelper.newInstance(I_RabbitMQ_Message_Audit.class);
			record.setRabbitMQ_QueueName(entry.getQueueName());
			record.setDirection(entry.getDirection().getCode());
			record.setContent(convertContentToString(entry.getContent()));
			record.setEvent_UUID(entry.getEventUUID());
			record.setRelated_Event_UUID(entry.getRelatedEventUUID());
			record.setHost(entry.getHost() != null ? entry.getHost().toString() : null);
			InterfaceWrapperHelper.save(record);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed logging `{}`. Skip logging.", entry, ex);
		}
	}

	private String convertContentToString(final @NonNull Object content)
	{
		try
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(content);
		}
		catch (final JsonProcessingException e)
		{
			logger.warn("Failed converting `{}` to JSON. Returning toString().", content, e);
			return content.toString();
		}
	}
}
