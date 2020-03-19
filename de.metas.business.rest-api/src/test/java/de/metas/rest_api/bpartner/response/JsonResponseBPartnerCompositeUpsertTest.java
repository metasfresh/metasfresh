package de.metas.rest_api.bpartner.response;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.assertj.core.api.Assertions.assertThat;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.metas.rest_api.bpartner.response.JsonResponseUpsertItem.SyncOutcome;
import de.metas.rest_api.common.MetasfreshId;
import de.metas.util.JSONObjectMapper;

/*
 * #%L
 * de.metas.business.rest-api
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

class JsonResponseBPartnerCompositeUpsertTest
{
	@BeforeAll
	public static void beforeAll()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@Test
	public void serializeDeserializeTest()
	{
		final JsonResponseBPartnerCompositeUpsert item = JsonResponseBPartnerCompositeUpsert.builder()
				.responseItem(JsonResponseBPartnerCompositeUpsertItem.builder()
						.jsonResponseBPartnerUpsertItem(JsonResponseUpsertItem.builder()
								.identifier("val-12345")
								.metasfreshId(MetasfreshId.of(23))
								.syncOutcome(SyncOutcome.CREATED)
								.build())
						.jsonResponseContactUpsertItem(JsonResponseUpsertItem.builder()
								.identifier("ext-12345")
								.metasfreshId(MetasfreshId.of(24))
								.syncOutcome(SyncOutcome.UPDATED)
								.build())
						.jsonResponseLocationUpsertItem(JsonResponseUpsertItem.builder()
								.identifier("ext-12345")
								.metasfreshId(MetasfreshId.of(24))
								.syncOutcome(SyncOutcome.UPDATED)
								.build())
						.jsonResponseBankAccountUpsertItem(JsonResponseUpsertItem.builder()
								.identifier("ext-12345")
								.metasfreshId(MetasfreshId.of(24))
								.syncOutcome(SyncOutcome.NOTHING_DONE)
								.build())
						.build())
				.build();
		final JSONObjectMapper<JsonResponseBPartnerCompositeUpsert> m = JSONObjectMapper.forClass(JsonResponseBPartnerCompositeUpsert.class);

		final String str = m.writeValueAsString(item);

		final JsonResponseBPartnerCompositeUpsert result = m.readValue(str);
		assertThat(result).isEqualTo(item);

		expect(result).toMatchSnapshot();
	}

}
