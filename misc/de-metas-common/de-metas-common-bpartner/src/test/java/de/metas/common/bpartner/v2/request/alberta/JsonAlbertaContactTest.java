/*
 * #%L
 * de-metas-common-bpartner
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

package de.metas.common.bpartner.v2.request.alberta;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.time.Instant;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.assertj.core.api.Assertions.*;

public class JsonAlbertaContactTest
{
	private final ObjectMapper mapper = new ObjectMapper()
			.findAndRegisterModules()
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
			.enable(MapperFeature.USE_ANNOTATIONS);

	@BeforeClass
	public static void beforeAll()
	{
		start();
	}

	@Test
	public void serializeDeserialize() throws IOException
	{
		final JsonAlbertaContact contact = new JsonAlbertaContact();
		
		contact.setTitle("title");
		contact.setGender("gender");
		contact.setTimestamp(Instant.parse("2019-11-22T00:00:00Z"));

		final String string = mapper.writeValueAsString(contact);
		assertThat(string).isNotEmpty();

		final JsonAlbertaContact result = mapper.readValue(string, JsonAlbertaContact.class);

		assertThat(result).isEqualTo(contact);
		expect(result).toMatchSnapshot();
	}
}
