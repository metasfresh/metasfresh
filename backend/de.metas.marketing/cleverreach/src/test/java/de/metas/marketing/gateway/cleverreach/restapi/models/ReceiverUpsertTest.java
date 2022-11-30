/*
 * #%L
 * marketing-cleverreach
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

package de.metas.marketing.gateway.cleverreach.restapi.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ReceiverUpsertTest
{
	@Test
	void serialize_deserialize() throws JsonProcessingException
	{
		final ReceiverUpsert data = ReceiverUpsert.builder()
				.email("email")
				.id(30)
				.build();
		final ObjectMapper objectMapper = new ObjectMapper();
		final String jsonString = objectMapper.writeValueAsString(data);

		final ReceiverUpsert dataFromJson = objectMapper.readValue(jsonString, ReceiverUpsert.class);
		assertThat(dataFromJson).isEqualTo(data);
	}
}