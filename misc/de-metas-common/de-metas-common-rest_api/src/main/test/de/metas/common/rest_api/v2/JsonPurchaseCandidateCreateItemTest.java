/*
 * #%L
 * de-metas-common-rest_api
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.common.rest_api.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class JsonPurchaseCandidateCreateItemTest
{
	private final ObjectMapper mapper = new ObjectMapper();

	@Test
	public void serializeDeserialize() throws IOException
	{
		final JsonPurchaseCandidateCreateItem jsonPurchaseCandidateCreateItem = JsonPurchaseCandidateCreateItem.builder()
				.orgCode("orgCode")
				.externalHeaderId("externalHeaderId")
				.externalLineId("externalLineId")
				.poReference("poReference")
				.externalPurchaseOrderUrl("externalPurchaseOrderUrl")
				.isManualPrice(true)
				.productIdentifier("productIdentifier")
				.vendor(JsonVendor.builder()
								.bpartnerIdentifier("bpartnerIdentifier")
								.build())
				.warehouseIdentifier("warehouseIdentifier")
				.qty(JsonQuantity.builder()
							 .qty(BigDecimal.TEN)
							 .uomCode("uomCode")
							 .build())
				.build();

		final String string = mapper.writeValueAsString(jsonPurchaseCandidateCreateItem);

		final JsonPurchaseCandidateCreateItem result = mapper.readValue(string, JsonPurchaseCandidateCreateItem.class);

		assertThat(result).isEqualTo(jsonPurchaseCandidateCreateItem);
	}
}
