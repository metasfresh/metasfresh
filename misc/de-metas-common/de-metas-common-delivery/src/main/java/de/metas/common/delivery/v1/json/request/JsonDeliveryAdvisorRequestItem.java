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
import de.metas.common.delivery.v1.json.JsonQuantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * Per-product line item within a {@link JsonDeliveryAdvisorRequest}. Mirrors the ship path's
 * {@link JsonDeliveryOrderLineContents}: carries only the per-product (commercial / content) fields.
 * The PARCEL-level fields (gross weight, package dimensions, top-level type) live on the
 * {@link JsonDeliveryAdvisorRequest} itself.
 */
@Value
@Builder(toBuilder = true)
@Jacksonized
public class JsonDeliveryAdvisorRequestItem
{
	int numberOfItems;
	@NonNull String productName;
	@NonNull String productValue;
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
						? Optional.of(totalWeightInKg.divide(shippedQuantity.getValue(), 6, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString())
						: Optional.empty();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_UNIT_WEIGHT_G:
				return totalWeightInKg != null && shippedQuantity != null && shippedQuantity.getValue().signum() != 0
						? Optional.of(totalWeightInKg.divide(shippedQuantity.getValue(), 6, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(1000)).stripTrailingZeros().toPlainString())
						: Optional.empty();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_TOTAL_WEIGHT_KG:
				return Optional.ofNullable(totalWeightInKg).map(BigDecimal::toPlainString);
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_TOTAL_WEIGHT_G:
				return Optional.ofNullable(totalWeightInKg).map(w -> w.multiply(BigDecimal.valueOf(1000)).stripTrailingZeros().toPlainString());
			default:
				return Optional.empty();
		}
	}
}
