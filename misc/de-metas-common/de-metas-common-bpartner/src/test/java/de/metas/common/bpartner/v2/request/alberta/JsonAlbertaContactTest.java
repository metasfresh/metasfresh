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

<<<<<<< HEAD
=======
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
<<<<<<< HEAD
import org.junit.BeforeClass;
import org.junit.Test;
=======
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

import java.io.IOException;
import java.time.Instant;

<<<<<<< HEAD
import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.assertj.core.api.Assertions.*;

public class JsonAlbertaContactTest
{
=======
import static shadow.org.assertj.core.api.Assertions.assertThat;

@ExtendWith({SnapshotExtension.class})
public class JsonAlbertaContactTest
{
	private Expect expect;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	private final ObjectMapper mapper = new ObjectMapper()
			.findAndRegisterModules()
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
			.enable(MapperFeature.USE_ANNOTATIONS);

<<<<<<< HEAD
	@BeforeClass
	public static void beforeAll()
	{
		start();
	}

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		expect(result).toMatchSnapshot();
=======
		expect.serializer("orderedJson").toMatchSnapshot(result);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
