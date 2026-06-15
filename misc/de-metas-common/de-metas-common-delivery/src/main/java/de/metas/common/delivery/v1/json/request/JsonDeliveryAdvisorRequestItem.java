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

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.common.delivery.v1.json.DeliveryMappingConstants;
import de.metas.common.delivery.v1.json.JsonMoney;
import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import de.metas.common.delivery.v1.json.JsonQuantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class JsonDeliveryAdvisorRequestItem
{
	int numberOfItems;
	@NonNull BigDecimal grossWeightKg;
	@NonNull String productName;
	@NonNull String productValue;
	@Nullable JsonPackageDimensions packageDimensions;
	@Nullable String topLevelType;
	@Nullable String countryOfOrigin;
	// Commercial content — keep in sync with JsonDeliveryOrderLineContents (delivery-order path)
	@Nullable JsonMoney unitPrice;
	@Nullable JsonMoney totalValue;
	@Nullable JsonQuantity shippedQuantity;
	@Nullable String customsTariff;
	@Nullable BigDecimal totalWeightInKg;

	@JsonIgnore
	public Optional<String> getValue(@NonNull final String attributeValue)
	{
		switch (attributeValue)
		{
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PRODUCT_VALUE:
				return Optional.of(productValue);
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PRODUCT_NAME:
				return Optional.of(productName);
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_GROSS_WEIGHT_KG:
				return Optional.of(grossWeightKg.toPlainString());
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_TOP_LEVEL_TYPE:
				return Optional.ofNullable(topLevelType);
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_COUNTRY_OF_ORIGIN:
				return Optional.ofNullable(countryOfOrigin);
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_UNIT_PRICE:
				return unitPrice != null ? Optional.of(unitPrice.getAmount().toPlainString()) : Optional.empty();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_TOTAL_VALUE:
				return totalValue != null ? Optional.of(totalValue.getAmount().toPlainString()) : Optional.empty();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_CURRENCY_CODE:
				return totalValue != null ? Optional.of(totalValue.getCurrencyCode()) : Optional.empty();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_SHIPPED_QUANTITY:
				return shippedQuantity != null ? Optional.of(shippedQuantity.getValue().toPlainString()) : Optional.empty();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_UOM_CODE:
				return shippedQuantity != null ? Optional.of(shippedQuantity.getUomCode()) : Optional.empty();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_CUSTOMS_TARIFF:
				return Optional.ofNullable(customsTariff);
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_UNIT_WEIGHT_KG:
				return totalWeightInKg != null && shippedQuantity != null && shippedQuantity.getValue().signum() != 0
						? Optional.of(totalWeightInKg.divide(shippedQuantity.getValue(), 6, java.math.RoundingMode.HALF_UP).stripTrailingZeros().toPlainString())
						: Optional.empty();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_UNIT_WEIGHT_G:
				return totalWeightInKg != null && shippedQuantity != null && shippedQuantity.getValue().signum() != 0
						? Optional.of(totalWeightInKg.divide(shippedQuantity.getValue(), 6, java.math.RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(1000)).stripTrailingZeros().toPlainString())
						: Optional.empty();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_TOTAL_WEIGHT_KG:
				return Optional.ofNullable(totalWeightInKg).map(BigDecimal::toPlainString);
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_TOTAL_WEIGHT_G:
				return Optional.ofNullable(totalWeightInKg).map(w -> w.multiply(BigDecimal.valueOf(1000)).stripTrailingZeros().toPlainString());
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_LENGTH_CM:
				return packageDimensions != null ? Optional.of(Integer.toString(packageDimensions.getLengthInCM())) : Optional.empty();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_LENGTH_MM:
				return packageDimensions != null ? Optional.of(Integer.toString(packageDimensions.getLengthInCM() * 10)) : Optional.empty();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_WIDTH_CM:
				return packageDimensions != null ? Optional.of(Integer.toString(packageDimensions.getWidthInCM())) : Optional.empty();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_WIDTH_MM:
				return packageDimensions != null ? Optional.of(Integer.toString(packageDimensions.getWidthInCM() * 10)) : Optional.empty();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_HEIGHT_CM:
				return packageDimensions != null ? Optional.of(Integer.toString(packageDimensions.getHeightInCM())) : Optional.empty();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_HEIGHT_MM:
				return packageDimensions != null ? Optional.of(Integer.toString(packageDimensions.getHeightInCM() * 10)) : Optional.empty();
			default:
				return Optional.empty();
		}
	}
}
