/*
 * #%L
 * de-metas-camel-ebay-camelroutes
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

package de.metas.camel.ebay.processor;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_LOG_MESSAGE_ROUTE_ID;

import javax.annotation.Nullable;

import org.apache.camel.Exchange;

import de.metas.camel.externalsystems.common.LogMessageRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.NonNull;

public class ProcessorHelper
{
	public static <T> T getPropertyOrThrowError(@NonNull final Exchange exchange, @NonNull final String propertyName, @NonNull final Class<T> propertyClass)
	{
		final T property = exchange.getProperty(propertyName, propertyClass);
		if (property == null)
		{
			throw new RuntimeException("Missing route property: " + propertyName + " !");
		}

		return property;
	}

	public static void logProcessMessage(
			@NonNull final Exchange exchange,
			@NonNull final String message,
			@Nullable final Integer adPInstanceId)
	{
		if (adPInstanceId == null)
		{
			return; // nothing to do
		}

		final LogMessageRequest logMessageRequest = LogMessageRequest.builder()
				.logMessage(message)
				.pInstanceId(JsonMetasfreshId.of(adPInstanceId))
				.build();

		exchange.getContext().createProducerTemplate()
				.sendBody("direct:" + MF_LOG_MESSAGE_ROUTE_ID, logMessageRequest);
	}

}
