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
import com.google.common.collect.ImmutableList;
import de.metas.common.delivery.v1.json.DeliveryMappingConstants;
import de.metas.common.delivery.v1.json.JsonAddress;
import de.metas.common.delivery.v1.json.JsonContact;
import de.metas.common.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Value
@Builder(toBuilder = true)
@Jacksonized
public class JsonDeliveryRequest
{
	@Builder.Default
	@NonNull String id = UUID.randomUUID().toString();
	int deliveryOrderId;
	@NonNull JsonAddress pickupAddress;
	@NonNull String pickupDate;
	@Nullable String pickupTimeStart;
	@Nullable String pickupTimeEnd;
	@Nullable String pickupNote;
	@NonNull JsonAddress deliveryAddress;
	@Nullable JsonContact deliveryContact;
	@Nullable String deliveryDate;
	@Nullable String deliveryNote;
	@Nullable String customerReference;
	@NonNull @Singular ImmutableList<JsonDeliveryOrderParcel> deliveryOrderParcels;
	@Nullable String shipperProduct;
	@NonNull @Singular Set<String> shipperProductServices;
	@Nullable String shipperEORI;
	@Nullable String receiverEORI;
	@NonNull JsonShipperConfig shipperConfig;
	@NonNull @Builder.Default JsonMappingConfigList mappingConfigs = JsonMappingConfigList.EMPTY;
	@NonNull @Singular Map<String, String> shipAdvises;

	@JsonIgnore
	@NonNull
	public String getShipAdviceNotNull(@NonNull final String key)
	{
		return Check.assumeNotNull( getShipAdvice(key), "No ShipAdvice found for key '%s'. Available keys: %s", key, shipAdvises.keySet());
	}

	@JsonIgnore
	@Nullable
	public String getShipAdvice(@NonNull final String key)
	{
		return shipAdvises.get(key);
	}

	@JsonIgnore
	@Nullable
	public String getPickupDateAndTimeStart()
	{
		if (pickupTimeStart == null){return null;}
		return pickupDate + "T" + pickupTimeStart;
	}

	@JsonIgnore
	@Nullable
	public String getPickupDateAndTimeEnd()
	{
		if (pickupTimeEnd == null){return null;}
		return pickupDate + "T" + pickupTimeEnd;
	}

	@JsonIgnore
	@Nullable
	public String getValue(@NonNull final String attributeValue)
	{
		switch (attributeValue)
		{
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PICKUP_DATE_AND_TIME_START:
				return getPickupDateAndTimeStart();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_PICKUP_DATE_AND_TIME_END:
				return getPickupDateAndTimeEnd();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_DELIVERY_DATE:
				return getDeliveryDate();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_CUSTOMER_REFERENCE:
				return getCustomerReference();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_RECEIVER_COUNTRY_CODE:
				return getDeliveryAddress().getCountry();
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_RECEIVER_CONTACT_FIRSTNAME_AND_LASTNAME:
				return getDeliveryContact() != null ? getDeliveryContact().getName() : null;
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_SHIPPER_PRODUCT_NAME:
				return getShipperProduct();
			default:
				throw new IllegalArgumentException("Unknown attributeValue: " + attributeValue);
		}
	}
}
