package de.metas.camel.ebay;

public interface EbayConstants {
	
	//camel route properties
	String ROUTE_PROPERTY_EBAY_CLIENT = "ebayClient";
	String ROUTE_PROPERTY_CURRENT_ORDER = "currentOrder";
	String ROUTE_PROPERTY_ORDER_DELIVERIES = "orderDeliveries";
	String ROUTE_PROPERTY_ORG_CODE = "orgCode";
	
	
	//Additional Params
	String PARAM_API_AUTH_CODE = "authCode";
	String PARAM_API_MODE = "apiMode";
	
	
	String EXTERNAL_ID_PREFIX = "ext-ebay-";

}
