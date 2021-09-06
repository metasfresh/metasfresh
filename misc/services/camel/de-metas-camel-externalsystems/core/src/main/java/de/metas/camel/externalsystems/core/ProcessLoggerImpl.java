/*
 * #%L
 * de-metas-camel-externalsystems-core
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.core;

import de.metas.camel.externalsystems.common.LogMessageRequest;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.NonNull;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_LOG_MESSAGE_ROUTE_ID;

@Component
public class ProcessLoggerImpl implements ProcessLogger
{
	private final ProducerTemplate producerTemplate;

	public ProcessLoggerImpl(final ProducerTemplate producerTemplate)
	{
		this.producerTemplate = producerTemplate;
	}

	public void logMessage(
			@NonNull final String message,
			@Nullable final Integer adPInstanceId)
	{
		if (adPInstanceId == null)
		{
			return; //nothing to do
		}

		final LogMessageRequest logMessageRequest = LogMessageRequest.builder()
				.logMessage(message)
				.pInstanceId(JsonMetasfreshId.of(adPInstanceId))
				.build();

		producerTemplate
				.sendBody("direct:" + MF_LOG_MESSAGE_ROUTE_ID, logMessageRequest);
	}
}
