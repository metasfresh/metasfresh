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

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class JsonBOMTest
{
	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Test
	public void testDeserializeWithUnknownField() throws Exception
	{
		//given
		final String candidate = "{\n"
				+ "    	\"FLAG\": 300,\n"
				+ "    	\"ARTNR\": \"productValue\",\n"
				+ "    	\"ARTNRID\": \"productId\",\n"
				+ "    	\"TEXT\": \"Name1\",\n"
				+ "    	\"TEXT2\": \"Name2\",\n"
				+ "    	\"INAKTIV\": 0,\n"
				+ " 	\"VERLUST\":15,\n"
				+ "		\"GTIN\":\"gtin\",\n"
				+ "     \"METASFRESHID\":\"12345\",\n"
				+ "		\"DETAIL\": [\n "
				+ "        {\n"
				+ "				\"ARTNR\": \"productValue1\",\n"
				+ "				\"ARTNRID\": \"productId1\",\n"
				+ "				\"HERKUNFTSLAND\": \"countryCode\",\n"
				+ "				\"POS\": 1,\n"
				+ "				\"ANTEIL\": 20,\n"
				+ "				\"UOM\": \"KGM\" \n"
				+ "        }\n"
				+ "    ]\n"
				+ "}";

		//when
		final JsonBOM bom = objectMapper.readValue(candidate, JsonBOM.class);

		//then
		final JsonBOMLine expectedBOMLine = JsonBOMLine.builder()
				.line(1)
				.productId("productId1")
				.productValue("productValue1")
				.countryCode("countryCode")
				.uom("KGM")
				.qtyBOM(BigDecimal.valueOf(20))
				.build();

		final JsonBOM expectedBOM = JsonBOM.builder()
				.flag(300)
				.productValue("productValue")
				.productId("productId")
				.name1("Name1")
				.name2("Name2")
				.inactive(0)
				.scrap(BigDecimal.valueOf(15))
				.gtin("gtin")
				.bPartnerMetasfreshId("12345")
				.bomLines(ImmutableList.of(expectedBOMLine))
				.build();

		assertThat(bom).isEqualTo(expectedBOM);
	}
}
