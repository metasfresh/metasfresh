package de.metas.rest_api.bpartner.request;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.BeforeClass;
import org.junit.Test;

import de.metas.rest_api.bpartner.request.JsonRequestBPartner;
import de.metas.rest_api.bpartner.request.JsonRequestComposite;
import de.metas.rest_api.bpartner.request.JsonRequestBPartnerUpsertItem;
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

public class JsonRequestBPartnerUpsertItemTest
{

	@BeforeClass
	public static void beforeAll()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@Test
	public void serializeDeserialize()
	{
		final JsonRequestBPartnerUpsertItem item = JsonRequestBPartnerUpsertItem.builder()
				.bpartnerIdentifier("ext-12345")
				.bpartnerComposite(JsonRequestComposite.builder()
						.bpartner(JsonRequestBPartner.builder()
								.code("code")
								.build())
						.build())
				.build();

		final JSONObjectMapper<JsonRequestBPartnerUpsertItem> mapper = JSONObjectMapper.forClass(JsonRequestBPartnerUpsertItem.class);

		final String string = mapper.writeValueAsString(item);
		assertThat(string).isNotEmpty();

		final JsonRequestBPartnerUpsertItem result = mapper.readValue(string);

		assertThat(result).isEqualTo(item);
		expect(result).toMatchSnapshot();
	}
}
