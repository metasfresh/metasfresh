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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableSet;
import de.metas.common.delivery.v1.json.DeliveryMappingConstants;
import de.metas.common.delivery.v1.json.JsonAddress;
import de.metas.common.delivery.v1.json.JsonContact;
import de.metas.common.delivery.v1.json.JsonMoney;
import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import de.metas.common.delivery.v1.json.JsonQuantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Carrier advise request. Mirrors the two-level structure of the ship path's {@link JsonDeliveryOrderParcel}:
 * the request itself carries the PARCEL-level fields ({@code grossWeightKg}, {@code packageDimensions},
 * {@code topLevelType}) and a LIST of per-product {@link JsonDeliveryAdvisorRequestItem}s.
 * <p>
 * {@link #getValue(String)} resolves the request/parcel-level attribute values; per-product values are
 * resolved by each {@link JsonDeliveryAdvisorRequestItem#getValue(String)}. The parcel-level resolution of the
 * aggregated content attributes (product name/value, customs tariff, country of origin, total value, …) mirrors
 * {@link JsonDeliveryOrderParcel#getValue(String)}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Value
@Builder(toBuilder = true)
@Jacksonized
public class JsonDeliveryAdvisorRequest
{
	@Builder.Default
	@NonNull String id = UUID.randomUUID().toString();
	@NonNull JsonAddress pickupAddress;
	@Nullable JsonContact pickupContact;
	@NonNull String pickupDate;
	@NonNull String pickupTimeFrom;
	@Nullable String pickupTimeTo;
	@Nullable String pickupNote;
	@NonNull JsonAddress deliveryAddress;
	@Nullable JsonContact deliveryContact;
	@Nullable String deliveryDate;
	@Nullable String deliveryNote;
	@Nullable String customerReference;
	@Nullable String incotermsValue;
	@Nullable String externalSystemValue;
	@Nullable String preAdviceRequired;
	// PARCEL-level fields (mirror JsonDeliveryOrderParcel) — describe the physical HU / parcel being advised
	@NonNull BigDecimal grossWeightKg;
	@Nullable JsonPackageDimensions packageDimensions;
	@Nullable String topLevelType;
	@NonNull List<JsonDeliveryAdvisorRequestItem> items;
	@NonNull JsonShipperConfig shipperConfig;
	@NonNull @Builder.Default JsonMappingConfigList mappingConfigs = JsonMappingConfigList.EMPTY;

	@JsonIgnore
	@Nullable
	public String getValue(@NonNull final String attributeValue)
	{
		switch (attributeValue)
		{
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PICKUP_DATE_AND_TIME_START:
				return pickupDate + "T" + pickupTimeFrom;
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PICKUP_DATE_AND_TIME_END:
				return pickupTimeTo != null ? pickupDate + "T" + pickupTimeTo : null;
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_DELIVERY_DATE:
				return deliveryDate;
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_CUSTOMER_REFERENCE:
				return customerReference;
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_RECEIVER_COUNTRY_CODE:
				return deliveryAddress.getCountry();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_RECEIVER_CONTACT_LASTNAME_AND_FIRSTNAME:
				return deliveryContact != null ? deliveryContact.getName() : null;
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_RECEIVER_DEPARTMENT:
				return deliveryAddress.getCompanyDepartment();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_RECEIVER_COMPANY_NAME:
				return deliveryAddress.getCompanyName1();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_SENDER_COMPANY_NAME:
				return pickupAddress.getCompanyName1();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_SENDER_COMPANY_NAME_2:
				return pickupAddress.getCompanyName2();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_SENDER_DEPARTMENT:
				return pickupAddress.getCompanyDepartment();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_SENDER_COUNTRY_CODE:
				return pickupAddress.getCountry();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_RECEIVER_BPARTNER_ATTENTION:
				return deliveryAddress.getAttention();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_SENDER_BPARTNER_ATTENTION:
				return pickupAddress.getAttention();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_IS_PRE_ADVICE_REQUIRED:
				return preAdviceRequired;
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_INCOTERMS_VALUE:
				return incotermsValue;
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_EXTERNAL_SYSTEM_VALUE:
				return externalSystemValue;
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_SHIPPER_PRODUCT_EXTERNAL_ID:
				// Must return "" not null: JsonMappingConfig.isConfigForShipperProduct() takes @NonNull.
				// "" → general configs apply, product-scoped configs are skipped (no product selected at advise time).
				return "";
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_SHIPPER_EORI:
				return null;
			// PARCEL-level resolution (mirror JsonDeliveryOrderParcel.getValue)
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_TOP_LEVEL_TYPE:
				return topLevelType;
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_GROSS_WEIGHT_KG:
				return grossWeightKg.toPlainString();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_GROSS_WEIGHT_G:
				return kgToG(grossWeightKg).toPlainString();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_LENGTH_CM:
				return packageDimensions != null ? Integer.toString(packageDimensions.getLengthInCM()) : null;
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_LENGTH_MM:
				return packageDimensions != null ? Integer.toString(packageDimensions.getLengthInCM() * 10) : null;
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_WIDTH_CM:
				return packageDimensions != null ? Integer.toString(packageDimensions.getWidthInCM()) : null;
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_WIDTH_MM:
				return packageDimensions != null ? Integer.toString(packageDimensions.getWidthInCM() * 10) : null;
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_HEIGHT_CM:
				return packageDimensions != null ? Integer.toString(packageDimensions.getHeightInCM()) : null;
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PACKAGE_HEIGHT_MM:
				return packageDimensions != null ? Integer.toString(packageDimensions.getHeightInCM() * 10) : null;
			// Aggregated per-product content (mirror JsonDeliveryOrderParcel.getValue)
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PRODUCT_NAME:
				return items.stream().map(JsonDeliveryAdvisorRequestItem::getProductName).collect(Collectors.joining(","));
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PRODUCT_VALUE:
				return items.stream().map(JsonDeliveryAdvisorRequestItem::getProductValue).collect(Collectors.joining(","));
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_CUSTOMS_TARIFF:
				// Empty Strings should be filtered out by gateway implementation
				return items.stream().map(JsonDeliveryAdvisorRequestItem::getCustomsTariff).filter(Objects::nonNull).collect(Collectors.joining(","));
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_COUNTRY_OF_ORIGIN:
				// null if the items disagree (undefined for a mixed-origin parcel) — mirror JsonDeliveryOrderParcel
				final ImmutableSet<String> countriesOfOrigin = items.stream()
						.map(JsonDeliveryAdvisorRequestItem::getCountryOfOrigin)
						.filter(Objects::nonNull)
						.collect(ImmutableSet.toImmutableSet());
				return countriesOfOrigin.size() == 1 ? countriesOfOrigin.iterator().next() : null;
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_TOTAL_VALUE:
				// Sum every item's total value — must NOT deduplicate (two items with the same amount sum to 2x),
				// mirroring JsonDeliveryOrderParcel.getValue. null when no item carries a total value.
				final List<BigDecimal> totalValueAmounts = items.stream()
						.map(JsonDeliveryAdvisorRequestItem::getTotalValue)
						.filter(Objects::nonNull)
						.map(JsonMoney::getAmount)
						.collect(Collectors.toList());
				if (totalValueAmounts.isEmpty())
				{
					return null;
				}
				return totalValueAmounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add).toPlainString();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_CURRENCY_CODE:
				final ImmutableSet<String> currencies = items.stream()
						.map(JsonDeliveryAdvisorRequestItem::getTotalValue)
						.filter(Objects::nonNull)
						.map(JsonMoney::getCurrencyCode)
						.collect(ImmutableSet.toImmutableSet());
				return currencies.size() == 1 ? currencies.iterator().next() : null;
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_UOM_CODE:
				final ImmutableSet<String> uomCodes = items.stream()
						.map(JsonDeliveryAdvisorRequestItem::getShippedQuantity)
						.filter(Objects::nonNull)
						.map(JsonQuantity::getUomCode)
						.collect(ImmutableSet.toImmutableSet());
				return uomCodes.size() == 1 ? uomCodes.iterator().next() : null;
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_CUSTOM_VALUE_STRING_1:
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_CUSTOM_VALUE_STRING_2:
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_CUSTOM_VALUE_STRING_3:
				// the attribute value name IS the Carrier_Config column / shipper-config property key
				return shipperConfig.getAdditionalProperty(attributeValue);
			default:
				return null; // attribute not available at advise time — filtered out by caller
		}
	}

	@NonNull
	private static BigDecimal kgToG(@NonNull final BigDecimal kg)
	{
		return kg.multiply(BigDecimal.valueOf(1000)).stripTrailingZeros();
	}
}
