/*
 * #%L
 * de.metas.shipper.gateway.commons
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

package de.metas.shipper.gateway.commons.mapping;

import de.metas.common.delivery.v1.json.DeliveryMappingConstants;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AttributeValue implements ReferenceListAwareEnum
{
	//TODO replace with constants from model
	PICKUP_DATE_AND_TIME_START(DeliveryMappingConstants.ATTRIBUTE_VALUE_PICKUP_DATE_AND_TIME_START),
	PICKUP_DATE_AND_TIME_END(DeliveryMappingConstants.ATTRIBUTE_VALUE_PICKUP_DATE_AND_TIME_END),
	DELIVERY_DATE(DeliveryMappingConstants.ATTRIBUTE_VALUE_DELIVERY_DATE),
	CUSTOMER_REFERENCE(DeliveryMappingConstants.ATTRIBUTE_VALUE_CUSTOMER_REFERENCE),
	RECEIVER_COUNTRY_CODE(DeliveryMappingConstants.ATTRIBUTE_VALUE_RECEIVER_COUNTRY_CODE),
	SHIPPER_PRODUCT_NAME(DeliveryMappingConstants.ATTRIBUTE_VALUE_SHIPPER_PRODUCT_NAME),
	SENDER_COMPANY_NAME(DeliveryMappingConstants.ATTRIBUTE_VALUE_SENDER_COMPANY_NAME),
	SENDER_COMPANY_NAME_2(DeliveryMappingConstants.ATTRIBUTE_VALUE_SENDER_COMPANY_NAME_2),
	SENDER_DEPARTMENT(DeliveryMappingConstants.ATTRIBUTE_VALUE_SENDER_DEPARTMENT),
	SENDER_COUNTRY_CODE(DeliveryMappingConstants.ATTRIBUTE_VALUE_SENDER_COUNTRY_CODE),
	RECEIVER_COMPANY_NAME(DeliveryMappingConstants.ATTRIBUTE_VALUE_RECEIVER_COMPANY_NAME),
	RECEIVER_DEPARTMENT(DeliveryMappingConstants.ATTRIBUTE_VALUE_RECEIVER_DEPARTMENT),
	RECEIVER_CONTACT_FIRSTNAME_AND_LASTNAME(DeliveryMappingConstants.ATTRIBUTE_VALUE_RECEIVER_CONTACT_FIRSTNAME_AND_LASTNAME),
	SHIPPER_EORI(DeliveryMappingConstants.ATTRIBUTE_VALUE_SHIPPER_EORI),

	// From parcel
	PARCEL_ID(DeliveryMappingConstants.ATTRIBUTE_VALUE_PARCEL_ID),

	// From content
	SHIPPED_QUANTITY(DeliveryMappingConstants.ATTRIBUTE_VALUE_SHIPPED_QUANTITY),
	UOM_CODE(DeliveryMappingConstants.ATTRIBUTE_VALUE_UOM_CODE),
	PRODUCT_NAME(DeliveryMappingConstants.ATTRIBUTE_VALUE_PRODUCT_NAME),
	SHIPMENT_ORDER_ITEM_ID(DeliveryMappingConstants.ATTRIBUTE_VALUE_SHIPMENT_ORDER_ITEM_ID),
	UNIT_PRICE(DeliveryMappingConstants.ATTRIBUTE_VALUE_UNIT_PRICE),
	TOTAL_VALUE(DeliveryMappingConstants.ATTRIBUTE_VALUE_TOTAL_VALUE),
	CURRENCY_CODE(DeliveryMappingConstants.ATTRIBUTE_VALUE_CURRENCY_CODE)
	;

	private static final ReferenceListAwareEnums.ValuesIndex<AttributeValue> index = ReferenceListAwareEnums.index(values());

	@NonNull @Getter private final String code;

	@NonNull
	public static AttributeValue ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}
}
