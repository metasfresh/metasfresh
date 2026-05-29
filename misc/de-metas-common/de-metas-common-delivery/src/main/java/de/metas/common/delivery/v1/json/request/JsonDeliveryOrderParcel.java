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
import com.google.common.collect.ImmutableSet;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class JsonDeliveryOrderParcel
{
	@NonNull String id;
	@Nullable String content;
	@NonNull BigDecimal grossWeightKg;
	@NonNull JsonPackageDimensions packageDimensions;
	@NonNull String packageId;
	@Nullable String awb;
	@Nullable String trackingUrl;
	@Nullable byte[] labelPdfBase64;
	@NonNull List<JsonDeliveryOrderLineContents> contents;

	@JsonIgnore
	public Optional<String> getValue(@NonNull final String attributeValue)
	{
		switch (attributeValue)
		{
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PARCEL_ID:
				return Optional.of(getId());
			//aggregated JsonDeliveryOrderLineContents
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PRODUCT_NAME:
				return Optional.of(contents.stream().map(JsonDeliveryOrderLineContents::getProductName).collect(Collectors.joining(",")));
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PRODUCT_VALUE:
				return Optional.of(contents.stream().map(JsonDeliveryOrderLineContents::getProductValue).collect(Collectors.joining(",")));
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_CUSTOMS_TARIFF:
				//Empty Strings should be filtered out by gateway implementation
				return Optional.of(contents.stream().map(JsonDeliveryOrderLineContents::getCustomsTariff).filter(Objects::nonNull).collect(Collectors.joining(",")));
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_TOTAL_VALUE:
				return Optional.of(contents.stream()
						.map(JsonDeliveryOrderLineContents::getTotalValue)
				        .map(JsonMoney::getAmount)
						.reduce(BigDecimal.ZERO, BigDecimal::add)
						.toPlainString());
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_CURRENCY_CODE:
				final ImmutableSet<String> currencies = contents.stream()
						.map(JsonDeliveryOrderLineContents::getTotalValue)
						.map(JsonMoney::getCurrencyCode)
						.collect(ImmutableSet.toImmutableSet());
				return currencies.size() == 1 ? Optional.of(currencies.iterator().next()) : Optional.empty();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_UOM_CODE:
				final ImmutableSet<String> uomCodes = contents.stream()
						.map(JsonDeliveryOrderLineContents::getShippedQuantity)
						.map(JsonQuantity::getUomCode)
						.collect(ImmutableSet.toImmutableSet());
				return uomCodes.size() == 1 ? Optional.of(uomCodes.iterator().next()) : Optional.empty();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_UNIT_WEIGHT_KG:
				final ImmutableSet<BigDecimal> unitWeightsKg = contents.stream()
						.map(JsonDeliveryOrderLineContents::getUnitWeightKg)
						.collect(ImmutableSet.toImmutableSet());
				return unitWeightsKg.size() == 1 ? Optional.of(unitWeightsKg.iterator().next().toPlainString()) : Optional.empty();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_UNIT_WEIGHT_G:
				final ImmutableSet<BigDecimal> unitWeightsG = contents.stream()
						.map(JsonDeliveryOrderLineContents::getUnitWeightKg)
						.map(JsonDeliveryOrderParcel::kgToG)
						.collect(ImmutableSet.toImmutableSet());
				return unitWeightsG.size() == 1 ? Optional.of(unitWeightsG.iterator().next().toPlainString()) : Optional.empty();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_GROSS_WEIGHT_KG:
				return Optional.of(getGrossWeightKg().toPlainString());
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_GROSS_WEIGHT_G:
				return Optional.of(kgToG(getGrossWeightKg()).toPlainString());
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_LENGTH_CM:
				return Optional.of(Integer.toString(getPackageDimensions().getLengthInCM()));
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_LENGTH_MM:
				return Optional.of(Integer.toString(getPackageDimensions().getLengthInCM() * 10));
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_WIDTH_CM:
				return Optional.of(Integer.toString(getPackageDimensions().getWidthInCM()));
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_WIDTH_MM:
				return Optional.of(Integer.toString(getPackageDimensions().getWidthInCM() * 10));
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_HEIGHT_CM:
				return Optional.of(Integer.toString(getPackageDimensions().getHeightInCM()));
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_HEIGHT_MM:
				return Optional.of(Integer.toString(getPackageDimensions().getHeightInCM() * 10));
			default:
				return Optional.empty();
		}
	}

	@NonNull
	private static BigDecimal kgToG(@NonNull final BigDecimal kg)
	{
		return kg.multiply(BigDecimal.valueOf(1000)).stripTrailingZeros();
	}
}
