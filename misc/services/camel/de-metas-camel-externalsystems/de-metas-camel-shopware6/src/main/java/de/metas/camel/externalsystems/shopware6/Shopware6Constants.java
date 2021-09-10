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

import java.math.BigDecimal;

public interface Shopware6Constants
{
	String SHOPWARE6_SYSTEM_NAME = "Shopware6";

	String FIELD_UPDATED_AT = "updatedAt";
	String FIELD_CREATED_AT = "createdAt";

	String PARAMETERS_DATE_GTE = "gte";

	//camel route properties
	String ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT = "GetOrdersRouteContext";

	//nodes
	String JSON_NODE_DATA = "data";
	String JSON_NODE_DELIVERY_ADDRESS = "shippingOrderAddress";
	String JSON_NODE_ORDER_CUSTOMER = "orderCustomer";

	//shopware client
	int CONNECTION_TIMEOUT_SECONDS = 600;
	int READ_TIMEOUT_SECONDS = 600;

	//external identifier
	String EXTERNAL_ID_PREFIX = "ext";
	String VALUE_PREFIX = "val";
	String BILL_TO_SUFFIX = "-billTo";
	String SHIP_TO_SUFFIX = "-shipTo";

	//default values
	String DATA_SOURCE_INT_SHOPWARE = "int-Shopware";
	String DEFAULT_DELIVERY_RULE = "A";
	String DEFAULT_DELIVERY_VIA_RULE = "S";
	BigDecimal DEFAULT_ORDER_LINE_DISCOUNT = BigDecimal.ZERO;
	String MULTIPLE_SHIPPING_ADDRESSES_WARN_MESSAGE = "The order had multiple shipping addresses, the process picked the last one!";
	String FREIGHT_COST_EXTERNAL_LINE_ID_PREFIX = "TaxRate_";
}
