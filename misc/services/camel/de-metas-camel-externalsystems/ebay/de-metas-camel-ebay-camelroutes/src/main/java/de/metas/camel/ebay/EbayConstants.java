package de.metas.camel.ebay;

import java.math.BigDecimal;

public interface EbayConstants {
	
	//camel route properties
	String ROUTE_PROPERTY_EBAY_CLIENT = "ebayClient";
	String ROUTE_PROPERTY_EBAY_AUTH_CLIENT = "ebayAuthClient";
	String ROUTE_PROPERTY_CURRENT_ORDER = "currentOrder";
	String ROUTE_PROPERTY_ORDER_DELIVERIES = "orderDeliveries";
	String ROUTE_PROPERTY_ORG_CODE = "orgCode";
	
	
	//camel object with some properties to process order.
	String ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT = "ebay_order_context";
	
	//Additional Params
	String PARAM_API_AUTH_CODE = "authCode";
	String PARAM_API_MODE = "apiMode";
	
	
	//external identifier
	String EXTERNAL_ID_PREFIX = "ext-ebay-";
	
	
	
	
	
	
	//default values
	String DATA_SOURCE_INT_EBAY = "int-Ebay";
	String DEFAULT_DELIVERY_RULE = "A"; //FIXME: magic name
	String DEFAULT_DELIVERY_VIA_RULE = "D"; //FIXME: magic name
	BigDecimal DEFAULT_ORDER_LINE_DISCOUNT = BigDecimal.ZERO;

}
