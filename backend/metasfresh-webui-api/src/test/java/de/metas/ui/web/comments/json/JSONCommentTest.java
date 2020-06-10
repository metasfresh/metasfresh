/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.comments.json;

import de.metas.ui.web.window.datatypes.json.DateTimeConverters;
import de.metas.util.JSONObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class JSONCommentTest
{
	@Test
	void testSerialisationDeserialisation()
	{
		// expected
		final ZonedDateTime zonedDateTime = ZonedDateTime.of(2020, Month.APRIL.getValue(), 22, 11, 11, 11, 0, ZoneId.of("UTC+8"));
		final String zonedDateTimeString = DateTimeConverters.toJson(zonedDateTime, ZoneId.of("UTC+8"));

		final JSONComment expected = JSONComment.builder()
				.createdBy("who created this?")
				.created(zonedDateTimeString)
				.text("This is a test Comment.\nTra la la.")
				.build();

		// actual
		final JSONObjectMapper<JSONComment> jsonObjectMapper = JSONObjectMapper.forClass(JSONComment.class);
		final String json = jsonObjectMapper.writeValueAsString(expected);
		final JSONComment deserialisedRequest = jsonObjectMapper.readValue(json);

		assertThat(deserialisedRequest).isEqualToIgnoringGivenFields(expected);
	}
}
