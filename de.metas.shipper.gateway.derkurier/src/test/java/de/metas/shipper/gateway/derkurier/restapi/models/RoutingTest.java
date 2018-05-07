package de.metas.shipper.gateway.derkurier.restapi.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/*
 * #%L
 * de.metas.shipper.gateway.derkurier
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

public class RoutingTest
{
	private static final ClassPathResource ROUTING_RESPONSE_JSON = new ClassPathResource("/RoutingResponse.json");

	@Test
	public void test_deserialize() throws JsonParseException, JsonMappingException, IOException
	{

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//		objectMapper.registerModule(new JavaTimeModule());
//		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		final Routing routing = objectMapper.readValue(ROUTING_RESPONSE_JSON.getInputStream(), Routing.class);
		assertThat(routing.getMessage()).isEqualTo("OK");
		assertThat(routing.getSendDate()).isEqualTo("2018-04-29");

		final Participant sender = routing.getSender();
		assertThat(sender).isNotNull();
		assertThat(sender.getStation()).isEqualTo("50");
		assertThat(sender.getEarliestTimeOfDelivery()).isEqualTo("06:30");
	}

}
