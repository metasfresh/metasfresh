package de.metas.camel.test.endpoints;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.common.shipping.ConfiguredJsonMapper;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de-metas-camel-shipping
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

public class CaptureLastMessage implements Processor
{
	private Processor nextProcessor;

	@Getter
	private int called = 0;
	@Getter
	private String lastMessageBody;

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		lastMessageBody = exchange.getIn().getBody(String.class);

		called++;

		if (nextProcessor != null)
		{
			nextProcessor.process(exchange);
		}
	}

	public <T> T getLastMessageBody(@NonNull final Class<T> type)
	{
		final String jsonString = getLastMessageBody();

		try
		{
			final ObjectMapper jsonObjectMapper = ConfiguredJsonMapper.get();
			return jsonObjectMapper.readValue(jsonString, type);
		}
		catch (final Exception ex)
		{
			throw new RuntimeException("Failed converting `" + jsonString + "` to " + type, ex);
		}
	}

	public CaptureLastMessage andThen(final Processor nextProcessor)
	{
		this.nextProcessor = nextProcessor;
		return this;
	}
}
