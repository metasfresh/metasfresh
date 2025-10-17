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

package de.metas.common.delivery.v1.json;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DeliveryMappingConstants
{
	// attributeTypes
	public static final String ATTRIBUTE_TYPE_SENDER_ATTENTION = "SenderAttention";
	public static final String ATTRIBUTE_TYPE_RECEIVER_ATTENTION = "ReceiverAttention";
	public static final String ATTRIBUTE_TYPE_REFERENCE = "Reference";
	public static final String ATTRIBUTE_TYPE_LINE_REFERENCE = "LineReference";
	public static final String ATTRIBUTE_TYPE_DETAIL_GROUP = "DetailGroup";

	// attributeValues
	public static final String ATTRIBUTE_VALUE_PICKUP_DATE_AND_TIME_START = "PickupDateAndTimeStart";
	public static final String ATTRIBUTE_VALUE_PICKUP_DATE_AND_TIME_END = "PickupDateAndTimeEnd";
	public static final String ATTRIBUTE_VALUE_DELIVERY_DATE = "DeliveryDate";
	public static final String ATTRIBUTE_VALUE_CUSTOMER_REFERENCE = "CustomerReference";
	public static final String ATTRIBUTE_VALUE_RECEIVER_COUNTRY_CODE = "ReceiverCountryCode";
	public static final String ATTRIBUTE_VALUE_SHIPPER_PRODUCT_NAME = "ShipperProductName";
	public static final String ATTRIBUTE_VALUE_SENDER_COMPANY_NAME = "SenderCompanyName";
	public static final String ATTRIBUTE_VALUE_SENDER_COMPANY_NAME_2 = "SenderCompanyName2";
	public static final String ATTRIBUTE_VALUE_SENDER_DEPARTMENT = "SenderDepartment";
	public static final String ATTRIBUTE_VALUE_RECEIVER_COMPANY_NAME = "ReceiverCompanyName";
	public static final String ATTRIBUTE_VALUE_RECEIVER_DEPARTMENT = "ReceiverDepartment";
	public static final String ATTRIBUTE_VALUE_RECEIVER_CONTACT_FIRSTNAME_AND_LASTNAME = "ReceiverContactFirstnameAndLastname";

	// mappingRules
	public static final String MAPPING_RULE_RECEIVER_COUNTRY_CODE = "ReceiverCountryCode";
}
