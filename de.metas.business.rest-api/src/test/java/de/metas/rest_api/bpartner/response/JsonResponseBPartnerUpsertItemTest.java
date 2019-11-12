package de.metas.rest_api.bpartner.response;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.BeforeClass;
import org.junit.Test;

import de.metas.rest_api.common.MetasfreshId;
import de.metas.util.JSONObjectMapper;

/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class JsonResponseBPartnerUpsertItemTest
{
	@BeforeClass
	public static void beforeAll()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	/** no need to deserialize this again */
	@Test
	public void serializeTest()
	{
		final JsonResponseUpsertItem item = JsonResponseUpsertItem.builder()
				.identifier("val-12345")
				.metasfreshId(MetasfreshId.of(23))
				.build();
		final JSONObjectMapper<JsonResponseUpsertItem> m = JSONObjectMapper.forClass(JsonResponseUpsertItem.class);

		final String str = m.writeValueAsString(item);

		final JsonResponseUpsertItem result = m.readValue(str);
		assertThat(result).isEqualTo(item);

		expect(result).toMatchSnapshot();
	}

}
