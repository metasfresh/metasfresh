/*
 * #%L
 * de-metas-common-bpartner
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.common.bpartner.v2.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class JsonResponseBPartnerUpsertItemTest
{
	final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void serializeWithDebtorId_creditorIdAbsent() throws IOException
	{
		final JsonResponseBPartnerUpsertItem item = JsonResponseBPartnerUpsertItem.bpartnerUpsertItemBuilder()
				.identifier("val-12345")
				.metasfreshId(JsonMetasfreshId.of(23))
				.syncOutcome(JsonResponseUpsertItem.SyncOutcome.CREATED)
				.debtorId(10000)
				// creditorId intentionally left null
				.build();

		final String json = objectMapper.writeValueAsString(item);

		// round-trip: deserialize back and assert fields — this also validates the @JsonCreator binding
		final JsonResponseBPartnerUpsertItem roundTripped = objectMapper.readValue(json, JsonResponseBPartnerUpsertItem.class);
		assertThat(roundTripped.getDebtorId()).isEqualTo(10000);
		assertThat(roundTripped.getCreditorId()).isNull();
		assertThat(roundTripped.getMetasfreshId()).isEqualTo(JsonMetasfreshId.of(23));
	}
}
