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

import java.math.BigDecimal;
import java.util.Optional;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class JsonDeliveryOrderLineContents
{
	@NonNull String shipmentOrderItemId;
	@NonNull JsonMoney unitPrice;
	@NonNull JsonMoney totalValue;
	@NonNull String productName;
	@NonNull String productValue;
	@NonNull BigDecimal totalWeightInKg;
	@NonNull JsonQuantity shippedQuantity;

	@JsonIgnore
	public Optional<String> getValue(@NonNull final String attributeValue)
	{
		switch (attributeValue)
		{
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_SHIPPED_QUANTITY:
				return Optional.of(getShippedQuantity().getValue().toPlainString());
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_UOM_CODE:
				return Optional.of(getShippedQuantity().getUomCode());
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PRODUCT_NAME:
				return Optional.of(getProductName());
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_SHIPMENT_ORDER_ITEM_ID:
				return Optional.of(getShipmentOrderItemId());
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_UNIT_PRICE:
				return Optional.of(getUnitPrice().getAmount().toPlainString());
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_TOTAL_VALUE:
				return Optional.of(getTotalValue().getAmount().toPlainString());
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_CURRENCY_CODE:
				return Optional.of(getTotalValue().getCurrencyCode());
			default:
				return Optional.empty();
		}
	}
}
