/*
 * #%L
 * de.metas.business.rest-api
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

package de.metas.common.bprelation.request;

<<<<<<< HEAD
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.common.bprelation.JsonBPRelationRole;
import org.junit.BeforeClass;
import org.junit.Test;
=======
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.common.bprelation.JsonBPRelationRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

import java.io.IOException;
import java.util.Collections;

<<<<<<< HEAD
import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.assertj.core.api.Assertions.assertThat;

public class JsonRequestBPRelationUpsertTest
{
	private final ObjectMapper mapper = new ObjectMapper();

	@BeforeClass
	public static void beforeAll()
	{
		start();
	}
=======
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SnapshotExtension.class)
public class JsonRequestBPRelationUpsertTest
{
	private final ObjectMapper mapper = new ObjectMapper();
	private Expect expect;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	@Test
	public void serializeDeserialize() throws IOException
	{
		final JsonRequestBPRelationsUpsert item = JsonRequestBPRelationsUpsert.builder()
				.orgCode("org123")
				.locationIdentifier("loc123")
				.relatesTo(Collections.singletonList(JsonRequestBPRelationTarget.builder()
						.targetBpartnerIdentifier("targetBP123")
						.targetLocationIdentifier("targetLoc123")
						.name("relName")
						.description("relDesc")
						.externalId("ext123")
						.billTo(true)
						.shipTo(true)
						.fetchedFrom(true)
						.handOverLocation(true)
						.role(JsonBPRelationRole.Caregiver)
						.payFrom(true)
						.remitTo(true)
						.active(true)
						.build()))
				.build();

		final String string = mapper.writeValueAsString(item);
		assertThat(string).isNotEmpty();

		final JsonRequestBPRelationsUpsert result = mapper.readValue(string, JsonRequestBPRelationsUpsert.class);

		assertThat(result).isEqualTo(item);
<<<<<<< HEAD
		expect(result).toMatchSnapshot();
=======
		expect.serializer("orderedJson").toMatchSnapshot(result);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
