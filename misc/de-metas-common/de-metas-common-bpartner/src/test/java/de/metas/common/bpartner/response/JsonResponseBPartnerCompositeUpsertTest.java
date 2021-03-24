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

package de.metas.common.bpartner.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.common.bpartner.response.JsonResponseUpsertItem.SyncOutcome;
import de.metas.common.rest_api.JsonMetasfreshId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.assertj.core.api.Assertions.assertThat;

class JsonResponseBPartnerCompositeUpsertTest
{
	ObjectMapper objectMapper = new ObjectMapper();
	
	@BeforeAll
	public static void beforeAll()
	{
		start();
	}

	@Test
	public void serializeDeserializeTest() throws IOException
	{
		final JsonResponseBPartnerCompositeUpsert item = JsonResponseBPartnerCompositeUpsert.builder()
				.responseItem(JsonResponseBPartnerCompositeUpsertItem.builder()
						.responseBPartnerItem(JsonResponseUpsertItem.builder()
								.identifier("val-12345")
								.metasfreshId(JsonMetasfreshId.of(23))
								.syncOutcome(SyncOutcome.CREATED)
								.build())
						.responseContactItem(JsonResponseUpsertItem.builder()
								.identifier("ext-12345")
								.metasfreshId(JsonMetasfreshId.of(24))
								.syncOutcome(SyncOutcome.UPDATED)
								.build())
						.responseLocationItem(JsonResponseUpsertItem.builder()
								.identifier("ext-12345")
								.metasfreshId(JsonMetasfreshId.of(24))
								.syncOutcome(SyncOutcome.UPDATED)
								.build())
						.responseBankAccountItem(JsonResponseUpsertItem.builder()
								.identifier("ext-12345")
								.metasfreshId(JsonMetasfreshId.of(24))
								.syncOutcome(SyncOutcome.NOTHING_DONE)
								.build())
						.build())
				.build();
		
		final String str = objectMapper.writeValueAsString(item);

		final JsonResponseBPartnerCompositeUpsert result = objectMapper.readValue(str,JsonResponseBPartnerCompositeUpsert.class);
		assertThat(result).isEqualTo(item);

		expect(result).toMatchSnapshot();
	}

}
