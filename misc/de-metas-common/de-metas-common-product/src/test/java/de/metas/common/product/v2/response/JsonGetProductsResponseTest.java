/*
 * #%L
 * de-metas-common-product
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

package de.metas.common.product.v2.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.common.product.v2.response.alberta.JsonAlbertaPackagingUnit;
import de.metas.common.product.v2.response.alberta.JsonAlbertaProductInfo;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

public class JsonGetProductsResponseTest
{
	final ObjectMapper mapper = new ObjectMapper();

	@Test
	void serializeDeserialize() throws IOException
	{
		final JsonGetProductsResponse response = JsonGetProductsResponse.builder()
				.product(JsonProduct.builder()
								 .id(JsonMetasfreshId.of(2))
								 .productCategoryId(JsonMetasfreshId.of(3))
								 .productNo("productNo")
								 .description("description")
								 .manufacturerId(JsonMetasfreshId.of(1))
								 .manufacturerNumber("manufacturerNumber")
								 .ean("ean")
								 .name("name")
								 .uom("uom")
								 .albertaProductInfo(JsonAlbertaProductInfo.builder()
															 .albertaProductId("albertaProductId")
															 .additionalDescription("additionalDescription")
															 .assortmentType("assortmentType")
															 .fixedPrice(BigDecimal.ONE)
															 .pharmacyPrice(BigDecimal.TEN)
															 .inventoryType("inventoryType")
															 .medicalAidPositionNumber("medicalAidPositionNumber")
															 .size("size")
															 .stars(BigDecimal.ZERO)
															 .status("status")
															 .purchaseRating("purchaseRating")
															 .billableTherapies(ImmutableList.of("billableTherapy"))
															 .therapyIds(ImmutableList.of("Therapy"))
															 .productGroupId("productGroupId")
															 .packagingUnits(ImmutableList.of(JsonAlbertaPackagingUnit.builder()
																									  .quantity(BigDecimal.ONE)
																									  .unit("unit")
																									  .build())
															 )
															 .build()
								 )
								 .build()
				)
				.build();

		final String valueAsString = mapper.writeValueAsString(response);

		final JsonGetProductsResponse readValue = mapper.readValue(valueAsString, JsonGetProductsResponse.class);

		assertThat(readValue).isEqualTo(response);
	}
}
