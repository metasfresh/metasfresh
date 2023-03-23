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

package de.metas.common.bpartner.v2.request;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.common.bpartner.v1.request.JsonRequestBPartner;
import de.metas.common.bpartner.v1.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.v1.request.JsonRequestComposite;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SnapshotExtension.class)
public class JsonRequestBPartnerUpsertItemTest
{
	private final ObjectMapper mapper = new ObjectMapper();
	private Expect expect;


	@Test
	public void serializeDeserialize() throws IOException
	{
		final JsonRequestBPartner partner = new JsonRequestBPartner();
		partner.setCode("code");

		final JsonRequestBPartnerUpsertItem item = JsonRequestBPartnerUpsertItem.builder()
				.bpartnerIdentifier("ext-12345")
				.bpartnerComposite(JsonRequestComposite.builder()
						.bpartner(partner)
						.build())
				.build();

		//final JSONObjectMapper<JsonRequestBPartnerUpsertItem> mapper = JSONObjectMapper.forClass(JsonRequestBPartnerUpsertItem.class);

		final String string = mapper.writeValueAsString(item);
		assertThat(string).isNotEmpty();

		final JsonRequestBPartnerUpsertItem result = mapper.readValue(string, JsonRequestBPartnerUpsertItem.class);

		assertThat(result).isEqualTo(item);
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}
}
