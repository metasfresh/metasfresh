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
import de.metas.common.delivery.v1.json.DeliveryMappingConstants;
import de.metas.common.delivery.v1.json.JsonAddress;
import de.metas.common.delivery.v1.json.JsonContact;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

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
	@NonNull JsonDeliveryAdvisorRequestItem item;
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
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_SHIPPER_PRODUCT_EXTERNAL_ID:
				// Must return "" not null: JsonMappingConfig.isConfigForShipperProduct() takes @NonNull.
				// "" → general configs apply, product-scoped configs are skipped (no product selected at advise time).
				return "";
			case DeliveryMappingConstants.ATTRIBUTE_VALUE_SHIPPER_EORI:
				return null;
			default:
				return null; // attribute not available at advise time — filtered out by caller
		}
	}
}
