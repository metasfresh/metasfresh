/*
 * #%L
 * de-metas-camel-shopware6
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.shopware6;

public interface Shopware6Constants
{
	String SHOPWARE6_SYSTEM_NAME = "Shopware6";

	String FIELD_UPDATED_AT = "updatedAt";
	String FIELD_CREATED_AT = "createdAt";

	String PARAMETERS_DATE_GTE = "gte";

	//camel route properties
	String ROUTE_PROPERTY_SHOPWARE_CLIENT = "shopwareClient";
	String ROUTE_PROPERTY_ORG_CODE = "orgCode";
	String ROUTE_PROPERTY_PATH_CONSTANT_BPARTNER_ID = "JSONPathConstantBPartnerID";
	String ROUTE_PROPERTY_PATH_CONSTANT_BPARTNER_LOCATION_ID = "JSONPathConstantBPartnerLocationID";

	//nodes
	String JSON_NODE_DATA = "data";
	String JSON_NODE_DELIVERY_ADDRESS = "shippingOrderAddress";
	String JSON_NODE_ORDER_CUSTOMER = "orderCustomer";

	//shopware client
	int CONNECTION_TIMEOUT_SECONDS = 600;
	int READ_TIMEOUT_SECONDS = 600;

	//external identifier
	String EXTERNAL_ID_PREFIX = "ext-" + SHOPWARE6_SYSTEM_NAME + "-";
	String BILL_TO_SUFFIX = "-billTo";
	String SHIP_TO_SUFFIX = "-shipTo";

}
