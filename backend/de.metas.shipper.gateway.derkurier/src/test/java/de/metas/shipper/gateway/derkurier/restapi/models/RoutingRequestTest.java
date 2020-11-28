package de.metas.shipper.gateway.derkurier.restapi.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.JsonObjectMapperHolder;
import de.metas.shipper.gateway.derkurier.DerKurierTestTools;

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

public class RoutingRequestTest
{
	@Test
	public void serialize_and_deserialize() throws IOException
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		final RoutingRequest routingRequest = DerKurierTestTools.createRoutingRequest_times_without_seconds();

		final String serializedRoutingRequest = objectMapper.writeValueAsString(routingRequest);

		final RoutingRequest deserializedRoutingRequest = objectMapper.readValue(serializedRoutingRequest, RoutingRequest.class);
		assertThat(deserializedRoutingRequest).isEqualTo(routingRequest);
	}

}
