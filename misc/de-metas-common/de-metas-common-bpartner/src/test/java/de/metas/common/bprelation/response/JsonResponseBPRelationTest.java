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

package de.metas.common.bprelation.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.common.rest_api.JsonExternalId;
import de.metas.common.bprelation.JsonBPRelationRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.assertj.core.api.Assertions.assertThat;

class JsonResponseBPRelationTest
{
	private final ObjectMapper mapper = new ObjectMapper();

	@BeforeAll
	public static void beforeAll()
	{
		start();
	}

	@Test
	public void serializeDeserializeTest() throws IOException
	{
		final JsonResponseBPRelationComposite item = JsonResponseBPRelationComposite.builder()
				.responseItem(JsonResponseBPRelationItem.builder()
						.bpartnerId(123456)
						.locationId(234567)
						.targetBPartnerId(345678)
						.targetBPLocationId(456789)
						.name("relName")
						.description("relDesc")
						.externalId(JsonExternalId.of("ext123"))
						.role(JsonBPRelationRole.MainProducer)
						.shipTo(true)
						.payFrom(true)
						.billTo(true)
						.handoverLocation(true)
						.fetchedFrom(true)
						.remitTo(true)
						.active(true)
						.build())
				.build();

		final String str = mapper.writeValueAsString(item);

		final JsonResponseBPRelationComposite result = mapper.readValue(str, JsonResponseBPRelationComposite.class);
		assertThat(result).isEqualTo(item);

		expect(result).toMatchSnapshot();
	}

}
