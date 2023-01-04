/*
 * #%L
 * de-metas-camel-rabbitmq
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

package de.metas.camel.externalsystems.rabbitmq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

public class TestPOJOs
{
	private final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	@Test
	public void givenJsonRabbitMQHttpMessage_whenSerializeDeserialize_thenSuccess() throws IOException
	{
		testSerializeDeserializeObject(getMockJsonRabbitMQHttpMessage());
	}

	@Test
	public void givenJsonRabbitMQHttpResponse_whenSerializeDeserialize_thenSuccess() throws IOException
	{
		testSerializeDeserializeObject(getMockJsonRabbitMQHttpResponse());
	}

	private void testSerializeDeserializeObject(final Object value) throws IOException
	{
		final Class<?> valueClass = value.getClass();
		final String json = objectMapper.writeValueAsString(value);
		final Object value2 = objectMapper.readValue(json, valueClass);
		assertThat(value2).isEqualTo(value);
	}

	private JsonRabbitMQHttpMessage getMockJsonRabbitMQHttpMessage()
	{
		return JsonRabbitMQHttpMessage.builder()
				.jsonRabbitMQProperties(JsonRabbitMQProperties.builder()
												.delivery_mode(2)
												.build())
				.routingKey("key")
				.payload("test")
				.payloadEncoding("string")
				.build();
	}

	private JsonRabbitMQHttpResponse getMockJsonRabbitMQHttpResponse()
	{
		return JsonRabbitMQHttpResponse.builder()
				.routed(true)
				.build();
	}
}
