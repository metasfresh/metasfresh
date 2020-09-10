package de.metas.camel.test.endpoints;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.junit.jupiter.api.Disabled;

import com.fasterxml.jackson.core.JsonProcessingException;
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

@Disabled
public class SingleResult implements Processor
{
	public static SingleResult ofJson(@NonNull final Object responseBody)
	{
		final String responseBodyAsJsonString;
		try
		{
			final ObjectMapper jsonObjectMapper = ConfiguredJsonMapper.get();
			responseBodyAsJsonString = jsonObjectMapper.writeValueAsString(responseBody);
		}
		catch (final JsonProcessingException ex)
		{
			throw new RuntimeException("Failed converting " + responseBody + " to json", ex);
		}

		return new SingleResult(responseBodyAsJsonString);
	}

	public static SingleResult ofJsonString(final String responseBody)
	{
		return new SingleResult(responseBody);
	}

	private final String responseBody;

	@Getter
	int called = 0;

	private SingleResult(final String jsonString)
	{
		this.responseBody = jsonString;
	}

	@Override
	public void process(final Exchange exchange)
	{
		exchange.getIn().setBody(responseBody);

		called++;
	}
}
