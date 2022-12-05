/*
 * #%L
 * de-metas-camel-ebay-camelroutes
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

package de.metas.camel.externalsystems.ebay;

import de.metas.common.pricing.v2.productprice.TaxCategory;

import java.math.BigDecimal;

public interface EbayConstants
{

	// camel route properties
	String ROUTE_PROPERTY_EBAY_CLIENT = "ebayClient";
	String ROUTE_PROPERTY_EBAY_AUTH_CLIENT = "ebayAuthClient";
	String ROUTE_PROPERTY_CURRENT_ORDER = "currentOrder";
	String ROUTE_PROPERTY_ORDER_DELIVERIES = "orderDeliveries";
	String ROUTE_PROPERTY_ORG_CODE = "orgCode";

	// camel object with some properties to process order.
	String ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT = "ebay_order_context";

	// Additional Params
	String PARAM_API_AUTH_CODE = "authCode";
	String PARAM_API_MODE = "apiMode";

	// external identifier
	String EXTERNAL_ID_PREFIX = "ext-Ebay-";

	// default values
	String DATA_SOURCE_INT_EBAY = "int-Ebay";
	String DEFAULT_DELIVERY_RULE = "A"; // FIXME: magic name
	String DEFAULT_DELIVERY_VIA_RULE = "S"; // FIXME: magic name
	BigDecimal DEFAULT_ORDER_LINE_DISCOUNT = BigDecimal.ZERO;
	String DEFAULT_PRODUCT_UOM = "PCE";
	
	// For price upserts
	TaxCategory DEFAULT_TAX_CATEGORY = TaxCategory.NORMAL;
	String DEFAULT_PRICELIST_ID = "2008396"; //TBD
	

	public enum OrderFulfillmentStatus
	{
		NOT_STARTED, IN_PROGRESS, FULFILLED;
	}

	public enum OrderPaymentStatus
	{
		FAILED, FULLY_REFUNDED, PAID, PARTIALLY_REFUNDED, PENDING;
	}

	public enum CancelState
	{
		CANCELED, IN_PROGRESS, NONE_REQUESTED;
	}

}
