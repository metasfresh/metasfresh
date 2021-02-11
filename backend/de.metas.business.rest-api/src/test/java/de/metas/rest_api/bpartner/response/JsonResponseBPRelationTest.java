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

package de.metas.rest_api.bpartner.response;

import de.metas.rest_api.common.JsonBPRelationRole;
import de.metas.rest_api.common.JsonExternalId;
import de.metas.util.JSONObjectMapper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.assertj.core.api.Assertions.assertThat;

class JsonResponseBPRelationTest
{
	@BeforeAll
	public static void beforeAll()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@Test
	public void serializeDeserializeTest()
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
		final JSONObjectMapper<JsonResponseBPRelationComposite> m = JSONObjectMapper.forClass(JsonResponseBPRelationComposite.class);

		final String str = m.writeValueAsString(item);

		final JsonResponseBPRelationComposite result = m.readValue(str);
		assertThat(result).isEqualTo(item);

		expect(result).toMatchSnapshot();
	}

}
