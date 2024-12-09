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

package de.metas.common.bpartner.v1.request;

<<<<<<< HEAD
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.assertj.core.api.Assertions.assertThat;

public class JsonRequestBPartnerUpsertItemTest
{
	private final ObjectMapper mapper = new ObjectMapper();

	@BeforeClass	
	public static void beforeAll()
	{
		start();
	}
=======
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SnapshotExtension.class)
public class JsonRequestBPartnerUpsertItemTest
{
	private final ObjectMapper mapper = new ObjectMapper();
	private Expect expect;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
		expect(result).toMatchSnapshot();
=======
		expect.serializer("orderedJson").toMatchSnapshot(result);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
