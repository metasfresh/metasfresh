/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.calendar.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

class JsonDateTimeTest
{
	@Test
	void serializeDeserialize() throws JsonProcessingException
	{
		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

		final JsonDateTime obj = JsonDateTime.ofZonedDateTime(ZonedDateTime.parse("2020-01-01T00:00:00+01:00"), ZoneId.of("Europe/Berlin"));
		System.out.println("obj=" + obj);
		final String jsonString = objectMapper.writeValueAsString(obj);
		System.out.println("jsonString=" + jsonString);

		final JsonDateTime objDeserialized = objectMapper.readValue(jsonString, JsonDateTime.class);
		System.out.println("objDeserialized=" + objDeserialized);

		Assertions.assertThat(objDeserialized).isEqualTo(obj);
	}

	@Test
	void ofInstant_toInstant()
	{
		final Instant instant = LocalDate.parse("2022-02-03").atTime(13, 14).atZone(ZoneId.of("Europe/Berlin")).toInstant();
		final JsonDateTime json = JsonDateTime.ofInstant(instant, ZoneId.of("Europe/Berlin"));
		Assertions.assertThat(json.toInstant()).isEqualTo(instant);
	}
}