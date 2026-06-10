/*
 * #%L
 * de-metas-common-delivery
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.common.delivery.v1.json.request;

import de.metas.common.delivery.v1.json.DeliveryMappingConstants;
import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class JsonDeliveryAdvisorRequestItemTest
{
	@Test
	void getValue_topLevelType()
	{
		final JsonDeliveryAdvisorRequestItem withType = JsonDeliveryAdvisorRequestItem.builder()
				.numberOfItems(1)
				.grossWeightKg(BigDecimal.ONE)
				.productName("Prod")
				.productValue("P-1")
				.topLevelType("LU")
				.build();

		assertThat(withType.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_TOP_LEVEL_TYPE)).contains("LU");

		final JsonDeliveryAdvisorRequestItem withoutType = JsonDeliveryAdvisorRequestItem.builder()
				.numberOfItems(1)
				.grossWeightKg(BigDecimal.ONE)
				.productName("Prod")
				.productValue("P-1")
				.build();

		assertThat(withoutType.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_TOP_LEVEL_TYPE)).isEmpty();
	}

	@Test
	void getValue_countryOfOrigin()
	{
		final JsonDeliveryAdvisorRequestItem withCountry = JsonDeliveryAdvisorRequestItem.builder()
				.numberOfItems(1)
				.grossWeightKg(BigDecimal.ONE)
				.productName("Prod")
				.productValue("P-1")
				.countryOfOrigin("DE")
				.build();

		assertThat(withCountry.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_COUNTRY_OF_ORIGIN)).contains("DE");

		final JsonDeliveryAdvisorRequestItem withoutCountry = JsonDeliveryAdvisorRequestItem.builder()
				.numberOfItems(1)
				.grossWeightKg(BigDecimal.ONE)
				.productName("Prod")
				.productValue("P-1")
				.build();

		assertThat(withoutCountry.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_COUNTRY_OF_ORIGIN)).isEmpty();
	}

	@Test
	void getValue_packageDimensions_present()
	{
		final JsonDeliveryAdvisorRequestItem item = JsonDeliveryAdvisorRequestItem.builder()
				.numberOfItems(1)
				.grossWeightKg(BigDecimal.ONE)
				.productName("Prod")
				.productValue("P-1")
				.packageDimensions(JsonPackageDimensions.builder()
						.lengthInCM(60)
						.widthInCM(40)
						.heightInCM(30)
						.build())
				.build();

		assertThat(item.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_LENGTH_CM)).contains("60");
		assertThat(item.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_LENGTH_MM)).contains("600");
		assertThat(item.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_WIDTH_CM)).contains("40");
		assertThat(item.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_WIDTH_MM)).contains("400");
		assertThat(item.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_HEIGHT_CM)).contains("30");
		assertThat(item.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_HEIGHT_MM)).contains("300");
	}

	@Test
	void getValue_packageDimensions_null()
	{
		final JsonDeliveryAdvisorRequestItem item = JsonDeliveryAdvisorRequestItem.builder()
				.numberOfItems(1)
				.grossWeightKg(BigDecimal.ONE)
				.productName("Prod")
				.productValue("P-1")
				.build();

		assertThat(item.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_LENGTH_CM)).isEmpty();
		assertThat(item.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_LENGTH_MM)).isEmpty();
		assertThat(item.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_WIDTH_CM)).isEmpty();
		assertThat(item.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_WIDTH_MM)).isEmpty();
		assertThat(item.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_HEIGHT_CM)).isEmpty();
		assertThat(item.getValue(DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_HEIGHT_MM)).isEmpty();
	}
}
