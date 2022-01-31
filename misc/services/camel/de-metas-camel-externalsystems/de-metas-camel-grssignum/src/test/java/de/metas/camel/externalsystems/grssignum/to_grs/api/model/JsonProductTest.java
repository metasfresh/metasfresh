/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.to_grs.api.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class JsonProductTest
{

	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Test
	public void testDeserializeWithUnknownField() throws Exception
	{
		//given
		final String candidate = "{\n"
				+ "    \"FLAG\": 200,\n"
				+ "    \"ARTNR\": \"productValue\",\n"
				+ "    \"ARTNRID\": \"productId\",\n"
				+ "    \"TEXT\": \"Name1\",\n"
				+ "    \"TEXT2\": \"Name2\",\n"
				+ "    \"INAKTIV\": 0,\n"
				+ "    \"KRED\": [\n"
				+ "        {\n"
				+ "            \"MKREDID\": \"vendorId\",\n"
				+ "            \"STDKRED\": 1,\n"
				+ "            \"LIEFERANTENFREIGABE\": 0,\n"
				+ "            \"INAKTIV\": 0,\n"
				+ "            \"METASFRESHID\":\"12345\"\n"
				+ "        }\n"
				+ "    ]\n"
				+ "}";

		//when
		final JsonProduct product = objectMapper.readValue(candidate, JsonProduct.class);

		//then
		final JsonBPartnerProduct expectedBPartnerProduct = JsonBPartnerProduct.builder()
				.bpartnerId("vendorId")
				.currentVendor(1)
				.approvedForPurchase(0)
				.inactive(0)
				.bPartnerMetasfreshId("12345")
				.build();

		final JsonProduct expectedProduct = JsonProduct.builder()
				.flag(200)
				.productValue("productValue")
				.productId("productId")
				.name1("Name1")
				.name2("Name2")
				.inactive(0)
				.bPartnerProducts(ImmutableList.of(expectedBPartnerProduct))
				.build();

		assertThat(product).isEqualTo(expectedProduct);
	}
}
