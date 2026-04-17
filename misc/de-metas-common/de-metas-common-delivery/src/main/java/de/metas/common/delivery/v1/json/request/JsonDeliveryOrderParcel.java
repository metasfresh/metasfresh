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
			default:
				return Optional.empty();
		}
	}
}
