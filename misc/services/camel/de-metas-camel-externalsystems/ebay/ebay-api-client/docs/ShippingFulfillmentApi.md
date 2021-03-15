# ShippingFulfillmentApi

All URIs are relative to *https://api.ebay.com/sell/fulfillment/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createShippingFulfillment**](ShippingFulfillmentApi.md#createShippingFulfillment) | **POST** /order/{orderId}/shipping_fulfillment | 
[**getShippingFulfillment**](ShippingFulfillmentApi.md#getShippingFulfillment) | **GET** /order/{orderId}/shipping_fulfillment/{fulfillmentId} | 
[**getShippingFulfillments**](ShippingFulfillmentApi.md#getShippingFulfillments) | **GET** /order/{orderId}/shipping_fulfillment | 


<a name="createShippingFulfillment"></a>
# **createShippingFulfillment**
> Object createShippingFulfillment(orderId, shippingFulfillmentDetails)



When you group an order&#39;s line items into one or more packages, each package requires a corresponding plan for handling, addressing, and shipping; this is a shipping fulfillment. For each package, execute this call once to generate a shipping fulfillment associated with that package. Note: A single line item in an order can consist of multiple units of a purchased item, and one unit can consist of multiple parts or components. Although these components might be provided by the manufacturer in separate packaging, the seller must include all components of a given line item in the same package. Before using this call for a given package, you must determine which line items are in the package. If the package has been shipped, you should provide the date of shipment in the request. If not provided, it will default to the current date and time.

### Example
```java
// Import classes:
import de.metas.camel.externalsystems.ebay.api.invoker.ApiClient;
import de.metas.camel.externalsystems.ebay.api.invoker.ApiException;
import de.metas.camel.externalsystems.ebay.api.invoker.Configuration;
import de.metas.camel.externalsystems.ebay.api.invoker.auth.*;
import de.metas.camel.externalsystems.ebay.api.invoker.models.*;
import de.metas.camel.externalsystems.ebay.api.ShippingFulfillmentApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.ebay.com/sell/fulfillment/v1");
    
    // Configure OAuth2 access token for authorization: api_auth
    OAuth api_auth = (OAuth) defaultClient.getAuthentication("api_auth");
    api_auth.setAccessToken("YOUR ACCESS TOKEN");

    ShippingFulfillmentApi apiInstance = new ShippingFulfillmentApi(defaultClient);
    String orderId = "orderId_example"; // String | The unique identifier of the order. Order ID values are shown in My eBay/Seller Hub, and are also returned by the getOrders method in the orders.orderId field. Note: A new order ID format was introduced to all eBay APIs (legacy and REST) in June 2019. In REST APIs that return Order IDs, including the Fulfillment API, all order IDs are returned in the new format, but the createShippingFulfillment method will accept both the legacy and new format order ID. The new format is a non-parsable string, globally unique across all eBay marketplaces, and consistent for both single line item and multiple line item orders. These order identifiers will be automatically generated after buyer payment, and unlike in the past, instead of just being known and exposed to the seller, these unique order identifiers will also be known and used/referenced by the buyer and eBay customer support.
    ShippingFulfillmentDetails shippingFulfillmentDetails = new ShippingFulfillmentDetails(); // ShippingFulfillmentDetails | fulfillment payload
    try {
      Object result = apiInstance.createShippingFulfillment(orderId, shippingFulfillmentDetails);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ShippingFulfillmentApi#createShippingFulfillment");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **orderId** | **String**| The unique identifier of the order. Order ID values are shown in My eBay/Seller Hub, and are also returned by the getOrders method in the orders.orderId field. Note: A new order ID format was introduced to all eBay APIs (legacy and REST) in June 2019. In REST APIs that return Order IDs, including the Fulfillment API, all order IDs are returned in the new format, but the createShippingFulfillment method will accept both the legacy and new format order ID. The new format is a non-parsable string, globally unique across all eBay marketplaces, and consistent for both single line item and multiple line item orders. These order identifiers will be automatically generated after buyer payment, and unlike in the past, instead of just being known and exposed to the seller, these unique order identifiers will also be known and used/referenced by the buyer and eBay customer support. |
 **shippingFulfillmentDetails** | [**ShippingFulfillmentDetails**](ShippingFulfillmentDetails.md)| fulfillment payload |

### Return type

**Object**

### Authorization

[api_auth](../README.md#api_auth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | Created. The call also returns the following location code: &lt;br /&gt;&lt;br /&gt;&lt;code&gt;{ENV}/sell/fulfillment/v1/order/{ORDERID}/shipping_fulfillment/{FULFILLMENTID}&lt;/code&gt; &lt;br /&gt;&lt;br /&gt;The &lt;code&gt;ENV&lt;/code&gt; string is the HTTPS path to the same eBay supported environment in which this call was issued. The &lt;code&gt;ORDERID&lt;/code&gt; parameter is the unique identifier of the order addressed by this call; for example, &lt;code&gt;01-03955-36441&lt;/code&gt;. The &lt;code&gt;FULFILLMENTID&lt;/code&gt; parameter identifies the newly created fulfillment; for example, &lt;code&gt;9405509699937003457459&lt;/code&gt;. Use this Get Fulfillment URI to retrieve the contents of the new fulfillment. |  * Location -  <br>  |
**400** | Bad Request |  -  |
**500** | Internal Server Error |  -  |

<a name="getShippingFulfillment"></a>
# **getShippingFulfillment**
> ShippingFulfillment getShippingFulfillment(fulfillmentId, orderId)



Use this call to retrieve the contents of a fulfillment based on its unique identifier, fulfillmentId (combined with the associated order&#39;s orderId). The fulfillmentId value was originally generated by the createShippingFulfillment call, and is returned by the getShippingFulfillments call in the members.fulfillmentId field.

### Example
```java
// Import classes:
import de.metas.camel.externalsystems.ebay.api.invoker.ApiClient;
import de.metas.camel.externalsystems.ebay.api.invoker.ApiException;
import de.metas.camel.externalsystems.ebay.api.invoker.Configuration;
import de.metas.camel.externalsystems.ebay.api.invoker.auth.*;
import de.metas.camel.externalsystems.ebay.api.invoker.models.*;
import de.metas.camel.externalsystems.ebay.api.ShippingFulfillmentApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.ebay.com/sell/fulfillment/v1");
    
    // Configure OAuth2 access token for authorization: api_auth
    OAuth api_auth = (OAuth) defaultClient.getAuthentication("api_auth");
    api_auth.setAccessToken("YOUR ACCESS TOKEN");

    ShippingFulfillmentApi apiInstance = new ShippingFulfillmentApi(defaultClient);
    String fulfillmentId = "fulfillmentId_example"; // String | The unique identifier of the fulfillment. This eBay-generated value was created by the Create Shipping Fulfillment call, and returned by the getShippingFulfillments call in the fulfillments.fulfillmentId field; for example, 9405509699937003457459.
    String orderId = "orderId_example"; // String | The unique identifier of the order. Order ID values are shown in My eBay/Seller Hub, and are also returned by the getOrders method in the orders.orderId field. Note: A new order ID format was introduced to all eBay APIs (legacy and REST) in June 2019. In REST APIs that return Order IDs, including the Fulfillment API, all order IDs are returned in the new format, but the getShippingFulfillment method will accept both the legacy and new format order ID. The new format is a non-parsable string, globally unique across all eBay marketplaces, and consistent for both single line item and multiple line item orders. These order identifiers will be automatically generated after buyer payment, and unlike in the past, instead of just being known and exposed to the seller, these unique order identifiers will also be known and used/referenced by the buyer and eBay customer support.
    try {
      ShippingFulfillment result = apiInstance.getShippingFulfillment(fulfillmentId, orderId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ShippingFulfillmentApi#getShippingFulfillment");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **fulfillmentId** | **String**| The unique identifier of the fulfillment. This eBay-generated value was created by the Create Shipping Fulfillment call, and returned by the getShippingFulfillments call in the fulfillments.fulfillmentId field; for example, 9405509699937003457459. |
 **orderId** | **String**| The unique identifier of the order. Order ID values are shown in My eBay/Seller Hub, and are also returned by the getOrders method in the orders.orderId field. Note: A new order ID format was introduced to all eBay APIs (legacy and REST) in June 2019. In REST APIs that return Order IDs, including the Fulfillment API, all order IDs are returned in the new format, but the getShippingFulfillment method will accept both the legacy and new format order ID. The new format is a non-parsable string, globally unique across all eBay marketplaces, and consistent for both single line item and multiple line item orders. These order identifiers will be automatically generated after buyer payment, and unlike in the past, instead of just being known and exposed to the seller, these unique order identifiers will also be known and used/referenced by the buyer and eBay customer support. |

### Return type

[**ShippingFulfillment**](ShippingFulfillment.md)

### Authorization

[api_auth](../README.md#api_auth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Success |  -  |
**400** | Bad Request |  -  |
**404** | Not Found |  -  |
**500** | Internal Server Error |  -  |

<a name="getShippingFulfillments"></a>
# **getShippingFulfillments**
> ShippingFulfillmentPagedCollection getShippingFulfillments(orderId)



Use this call to retrieve the contents of all fulfillments currently defined for a specified order based on the order&#39;s unique identifier, orderId. This value is returned in the getOrders call&#39;s members.orderId field when you search for orders by creation date or shipment status.

### Example
```java
// Import classes:
import de.metas.camel.externalsystems.ebay.api.invoker.ApiClient;
import de.metas.camel.externalsystems.ebay.api.invoker.ApiException;
import de.metas.camel.externalsystems.ebay.api.invoker.Configuration;
import de.metas.camel.externalsystems.ebay.api.invoker.auth.*;
import de.metas.camel.externalsystems.ebay.api.invoker.models.*;
import de.metas.camel.externalsystems.ebay.api.ShippingFulfillmentApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.ebay.com/sell/fulfillment/v1");
    
    // Configure OAuth2 access token for authorization: api_auth
    OAuth api_auth = (OAuth) defaultClient.getAuthentication("api_auth");
    api_auth.setAccessToken("YOUR ACCESS TOKEN");

    ShippingFulfillmentApi apiInstance = new ShippingFulfillmentApi(defaultClient);
    String orderId = "orderId_example"; // String | The unique identifier of the order. Order ID values are shown in My eBay/Seller Hub, and are also returned by the getOrders method in the orders.orderId field. Note: A new order ID format was introduced to all eBay APIs (legacy and REST) in June 2019. In REST APIs that return Order IDs, including the Fulfillment API, all order IDs are returned in the new format, but the getShippingFulfillments method will accept both the legacy and new format order ID. The new format is a non-parsable string, globally unique across all eBay marketplaces, and consistent for both single line item and multiple line item orders. These order identifiers will be automatically generated after buyer payment, and unlike in the past, instead of just being known and exposed to the seller, these unique order identifiers will also be known and used/referenced by the buyer and eBay customer support.
    try {
      ShippingFulfillmentPagedCollection result = apiInstance.getShippingFulfillments(orderId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ShippingFulfillmentApi#getShippingFulfillments");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **orderId** | **String**| The unique identifier of the order. Order ID values are shown in My eBay/Seller Hub, and are also returned by the getOrders method in the orders.orderId field. Note: A new order ID format was introduced to all eBay APIs (legacy and REST) in June 2019. In REST APIs that return Order IDs, including the Fulfillment API, all order IDs are returned in the new format, but the getShippingFulfillments method will accept both the legacy and new format order ID. The new format is a non-parsable string, globally unique across all eBay marketplaces, and consistent for both single line item and multiple line item orders. These order identifiers will be automatically generated after buyer payment, and unlike in the past, instead of just being known and exposed to the seller, these unique order identifiers will also be known and used/referenced by the buyer and eBay customer support. |

### Return type

[**ShippingFulfillmentPagedCollection**](ShippingFulfillmentPagedCollection.md)

### Authorization

[api_auth](../README.md#api_auth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Success |  -  |
**400** | Bad Request |  -  |
**500** | Internal Server Error |  -  |

