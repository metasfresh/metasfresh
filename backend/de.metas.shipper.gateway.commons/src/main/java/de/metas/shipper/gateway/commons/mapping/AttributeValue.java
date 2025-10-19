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

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_M_Shipper_Mapping_Config;

@RequiredArgsConstructor
public enum AttributeValue implements ReferenceListAwareEnum
{
	// Keep in sync with de.metas.common.delivery.v1.json.DeliveryMappingConstants
	PICKUP_DATE_AND_TIME_START(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTEVALUE_PickupDateAndTimeStart),
	PICKUP_DATE_AND_TIME_END(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTEVALUE_PickupDateAndTimeEnd),
	DELIVERY_DATE(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTEVALUE_DeliveryDate),
	CUSTOMER_REFERENCE(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTEVALUE_CustomerReference),
	RECEIVER_COUNTRY_CODE(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTEVALUE_ReceiverCountryCode),
	SHIPPER_PRODUCT_EXTERNAL_ID(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTEVALUE_ShipperProductExternalId),
	SENDER_COMPANY_NAME(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTEVALUE_SenderCompanyName),
	SENDER_COMPANY_NAME_2(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTEVALUE_SenderCompanyName2),
	SENDER_DEPARTMENT(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTEVALUE_SenderDepartment),
	SENDER_COUNTRY_CODE(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTEVALUE_SenderCountryCode),
	RECEIVER_COMPANY_NAME(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTEVALUE_ReceiverCompanyName),
	RECEIVER_DEPARTMENT(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTEVALUE_ReceiverDepartment),
	RECEIVER_CONTACT_FIRSTNAME_AND_LASTNAME(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTEVALUE_ReceiverContactLastnameAndFirstname),
	SHIPPER_EORI(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTEVALUE_ShipperEORI),

	// From parcel
	PARCEL_ID(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTEVALUE_ParcelId),

	// From content
	SHIPPED_QUANTITY(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTEVALUE_ShippedQuantity),
	UOM_CODE(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTEVALUE_UomCode),
	PRODUCT_NAME(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTEVALUE_ProductName),
	SHIPMENT_ORDER_ITEM_ID(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTEVALUE_ShipmentOrderItemId),
	UNIT_PRICE(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTEVALUE_UnitPrice),
	TOTAL_VALUE(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTEVALUE_TotalValue),
	CURRENCY_CODE(X_M_Shipper_Mapping_Config.MAPPINGATTRIBUTEVALUE_CurrencyCode)
	;

	private static final ReferenceListAwareEnums.ValuesIndex<AttributeValue> index = ReferenceListAwareEnums.index(values());

	@NonNull @Getter private final String code;

	@NonNull
	public static AttributeValue ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}
}
