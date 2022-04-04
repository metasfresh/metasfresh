# OrderApi

All URIs are relative to *https://api.ebay.com/sell/fulfillment/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getOrder**](OrderApi.md#getOrder) | **GET** /order/{orderId} | 
[**getOrders**](OrderApi.md#getOrders) | **GET** /order | 
[**issueRefund**](OrderApi.md#issueRefund) | **POST** /order/{order_id}/issue_refund | Issue Refund


<a name="getOrder"></a>
# **getOrder**
> Order getOrder(orderId, fieldGroups)



Use this call to retrieve the contents of an order based on its unique identifier, orderId. This value was returned in the getOrders call&#39;s orders.orderId field when you searched for orders by creation date, modification date, or fulfillment status. Include the optional fieldGroups query parameter set to TAX_BREAKDOWN to return a breakdown of the taxes and fees. The returned Order object contains information you can use to create and process fulfillments, including: Information about the buyer and seller Information about the order&#39;s line items The plans for packaging, addressing and shipping the order The status of payment, packaging, addressing, and shipping the order A summary of monetary amounts specific to the order such as pricing, payments, and shipping costs A summary of applied taxes and fees, and optionally a breakdown of each

### Example
```java
// Import classes:
import de.metas.camel.externalsystems.ebay.api.invoker.ApiClient;
import de.metas.camel.externalsystems.ebay.api.invoker.ApiException;
import de.metas.camel.externalsystems.ebay.api.invoker.Configuration;
import de.metas.camel.externalsystems.ebay.api.invoker.auth.*;
import de.metas.camel.externalsystems.ebay.api.invoker.models.*;
import de.metas.camel.externalsystems.ebay.api.OrderApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.ebay.com/sell/fulfillment/v1");
    
    // Configure OAuth2 access token for authorization: api_auth
    OAuth api_auth = (OAuth) defaultClient.getAuthentication("api_auth");
    api_auth.setAccessToken("YOUR ACCESS TOKEN");

    OrderApi apiInstance = new OrderApi(defaultClient);
    String orderId = "orderId_example"; // String | The unique identifier of the order. Order ID values are shown in My eBay/Seller Hub, and are also returned by the getOrders method in the orders.orderId field. Note: A new order ID format was introduced to all eBay APIs (legacy and REST) in June 2019. In REST APIs that return Order IDs, including the Fulfillment API, all order IDs are returned in the new format, but the getOrder method will accept both the legacy and new format order ID. The new format is a non-parsable string, globally unique across all eBay marketplaces, and consistent for both single line item and multiple line item orders. These order identifiers will be automatically generated after buyer payment, and unlike in the past, instead of just being known and exposed to the seller, these unique order identifiers will also be known and used/referenced by the buyer and eBay customer support.
    String fieldGroups = "fieldGroups_example"; // String | The response type associated with the order. The only presently supported value is TAX_BREAKDOWN. This type returns a breakdown of tax and fee values associated with the order.
    try {
      Order result = apiInstance.getOrder(orderId, fieldGroups);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OrderApi#getOrder");
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
 **orderId** | **String**| The unique identifier of the order. Order ID values are shown in My eBay/Seller Hub, and are also returned by the getOrders method in the orders.orderId field. Note: A new order ID format was introduced to all eBay APIs (legacy and REST) in June 2019. In REST APIs that return Order IDs, including the Fulfillment API, all order IDs are returned in the new format, but the getOrder method will accept both the legacy and new format order ID. The new format is a non-parsable string, globally unique across all eBay marketplaces, and consistent for both single line item and multiple line item orders. These order identifiers will be automatically generated after buyer payment, and unlike in the past, instead of just being known and exposed to the seller, these unique order identifiers will also be known and used/referenced by the buyer and eBay customer support. |
 **fieldGroups** | **String**| The response type associated with the order. The only presently supported value is TAX_BREAKDOWN. This type returns a breakdown of tax and fee values associated with the order. | [optional]

### Return type

[**Order**](Order.md)

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

<a name="getOrders"></a>
# **getOrders**
> OrderSearchPagedCollection getOrders(fieldGroups, filter, limit, offset, orderIds)



Use this call to search for and retrieve one or more orders based on their creation date, last modification date, or fulfillment status using the filter parameter. You can alternatively specify a list of orders using the orderIds parameter. Include the optional fieldGroups query parameter set to TAX_BREAKDOWN to return a breakdown of the taxes and fees. The returned Order objects contain information you can use to create and process fulfillments, including: Information about the buyer and seller Information about the order&#39;s line items The plans for packaging, addressing and shipping the order The status of payment, packaging, addressing, and shipping the order A summary of monetary amounts specific to the order such as pricing, payments, and shipping costs A summary of applied taxes and fees, and optionally a breakdown of each Important: In this call, the cancelStatus.cancelRequests array is returned but is always empty. Use the getOrder call instead, which returns this array fully populated with information about any cancellation requests.

### Example
```java
// Import classes:
import de.metas.camel.externalsystems.ebay.api.invoker.ApiClient;
import de.metas.camel.externalsystems.ebay.api.invoker.ApiException;
import de.metas.camel.externalsystems.ebay.api.invoker.Configuration;
import de.metas.camel.externalsystems.ebay.api.invoker.auth.*;
import de.metas.camel.externalsystems.ebay.api.invoker.models.*;
import de.metas.camel.externalsystems.ebay.api.OrderApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.ebay.com/sell/fulfillment/v1");
    
    // Configure OAuth2 access token for authorization: api_auth
    OAuth api_auth = (OAuth) defaultClient.getAuthentication("api_auth");
    api_auth.setAccessToken("YOUR ACCESS TOKEN");

    OrderApi apiInstance = new OrderApi(defaultClient);
    String fieldGroups = "fieldGroups_example"; // String | The response type associated with the order. The only presently supported value is TAX_BREAKDOWN. This type returns a breakdown of tax and fee values associated with the order.
    String filter = "filter_example"; // String | One or more comma-separated criteria for narrowing down the collection of orders returned by this call. These criteria correspond to specific fields in the response payload. Multiple filter criteria combine to further restrict the results. Note: Currently, filter returns data from only the last 90 days. If the orderIds parameter is included in the request, the filter parameter will be ignored. The available criteria are as follows: creationdate The time period during which qualifying orders were created (the orders.creationDate field). In the URI, this is expressed as a starting timestamp, with or without an ending timestamp (in brackets). The timestamps are in ISO 8601 format, which uses the 24-hour Universal Coordinated Time (UTC) clock.For example: creationdate:[2016-02-21T08:25:43.511Z..] identifies orders created on or after the given timestamp. creationdate:[2016-02-21T08:25:43.511Z..2016-04-21T08:25:43.511Z] identifies orders created between the given timestamps, inclusive. lastmodifieddate The time period during which qualifying orders were last modified (the orders.modifiedDate field). In the URI, this is expressed as a starting timestamp, with or without an ending timestamp (in brackets). The timestamps are in ISO 8601 format, which uses the 24-hour Universal Coordinated Time (UTC) clock.For example: lastmodifieddate:[2016-05-15T08:25:43.511Z..] identifies orders modified on or after the given timestamp. lastmodifieddate:[2016-05-15T08:25:43.511Z..2016-05-31T08:25:43.511Z] identifies orders modified between the given timestamps, inclusive. Note: If creationdate and lastmodifieddate are both included, only creationdate is used. orderfulfillmentstatus The degree to which qualifying orders have been shipped (the orders.orderFulfillmentStatus field). In the URI, this is expressed as one of the following value combinations: orderfulfillmentstatus:{NOT_STARTED|IN_PROGRESS} specifies orders for which no shipping fulfillments have been started, plus orders for which at least one shipping fulfillment has been started but not completed. orderfulfillmentstatus:{FULFILLED|IN_PROGRESS} specifies orders for which all shipping fulfillments have been completed, plus orders for which at least one shipping fulfillment has been started but not completed. Note: The values NOT_STARTED, IN_PROGRESS, and FULFILLED can be used in various combinations, but only the combinations shown here are currently supported. Here is an example of a getOrders call using all of these filters: GET https://api.ebay.com/sell/v1/order? filter=creationdate:%5B2016-03-21T08:25:43.511Z..2016-04-21T08:25:43.511Z%5D, lastmodifieddate:%5B2016-05-15T08:25:43.511Z..%5D, orderfulfillmentstatus:%7BNOT_STARTED%7CIN_PROGRESS%7D Note: This call requires that certain special characters in the URI query string be percent-encoded: &nbsp;&nbsp;&nbsp;&nbsp;[ = %5B &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;] = %5D &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{ = %7B &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;| = %7C &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;} = %7D This query filter example uses these codes. For implementation help, refer to eBay API documentation at https://developer.ebay.com/api-docs/sell/fulfillment/types/api:FilterField
    String limit = "limit_example"; // String | The number of orders to return per page of the result set. Use this parameter in conjunction with the offset parameter to control the pagination of the output. For example, if offset is set to 10 and limit is set to 10, the call retrieves orders 11 thru 20 from the result set. Note: This feature employs a zero-based list, where the first item in the list has an offset of 0. If the orderIds parameter is included in the request, this parameter will be ignored. Maximum: 1000 Default: 50
    String offset = "offset_example"; // String | Specifies the number of orders to skip in the result set before returning the first order in the paginated response. Combine offset with the limit query parameter to control the items returned in the response. For example, if you supply an offset of 0 and a limit of 10, the first page of the response contains the first 10 items from the complete list of items retrieved by the call. If offset is 10 and limit is 20, the first page of the response contains items 11-30 from the complete result set. Default: 0
    String orderIds = "orderIds_example"; // String | A comma-separated list of the unique identifiers of the orders to retrieve (maximum 50). If one or more order ID values are specified through the orderIds query parameter, all other query parameters will be ignored. Note: A new order ID format was introduced to all eBay APIs (legacy and REST) in June 2019. In REST APIs that return Order IDs, including the Fulfillment API, all order IDs are returned in the new format, but the getOrders method will accept both the legacy and new format order ID. The new format is a non-parsable string, globally unique across all eBay marketplaces, and consistent for both single line item and multiple line item orders. These order identifiers will be automatically generated after buyer payment, and unlike in the past, instead of just being known and exposed to the seller, these unique order identifiers will also be known and used/referenced by the buyer and eBay customer support.
    try {
      OrderSearchPagedCollection result = apiInstance.getOrders(fieldGroups, filter, limit, offset, orderIds);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OrderApi#getOrders");
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
 **fieldGroups** | **String**| The response type associated with the order. The only presently supported value is TAX_BREAKDOWN. This type returns a breakdown of tax and fee values associated with the order. | [optional]
 **filter** | **String**| One or more comma-separated criteria for narrowing down the collection of orders returned by this call. These criteria correspond to specific fields in the response payload. Multiple filter criteria combine to further restrict the results. Note: Currently, filter returns data from only the last 90 days. If the orderIds parameter is included in the request, the filter parameter will be ignored. The available criteria are as follows: creationdate The time period during which qualifying orders were created (the orders.creationDate field). In the URI, this is expressed as a starting timestamp, with or without an ending timestamp (in brackets). The timestamps are in ISO 8601 format, which uses the 24-hour Universal Coordinated Time (UTC) clock.For example: creationdate:[2016-02-21T08:25:43.511Z..] identifies orders created on or after the given timestamp. creationdate:[2016-02-21T08:25:43.511Z..2016-04-21T08:25:43.511Z] identifies orders created between the given timestamps, inclusive. lastmodifieddate The time period during which qualifying orders were last modified (the orders.modifiedDate field). In the URI, this is expressed as a starting timestamp, with or without an ending timestamp (in brackets). The timestamps are in ISO 8601 format, which uses the 24-hour Universal Coordinated Time (UTC) clock.For example: lastmodifieddate:[2016-05-15T08:25:43.511Z..] identifies orders modified on or after the given timestamp. lastmodifieddate:[2016-05-15T08:25:43.511Z..2016-05-31T08:25:43.511Z] identifies orders modified between the given timestamps, inclusive. Note: If creationdate and lastmodifieddate are both included, only creationdate is used. orderfulfillmentstatus The degree to which qualifying orders have been shipped (the orders.orderFulfillmentStatus field). In the URI, this is expressed as one of the following value combinations: orderfulfillmentstatus:{NOT_STARTED|IN_PROGRESS} specifies orders for which no shipping fulfillments have been started, plus orders for which at least one shipping fulfillment has been started but not completed. orderfulfillmentstatus:{FULFILLED|IN_PROGRESS} specifies orders for which all shipping fulfillments have been completed, plus orders for which at least one shipping fulfillment has been started but not completed. Note: The values NOT_STARTED, IN_PROGRESS, and FULFILLED can be used in various combinations, but only the combinations shown here are currently supported. Here is an example of a getOrders call using all of these filters: GET https://api.ebay.com/sell/v1/order? filter&#x3D;creationdate:%5B2016-03-21T08:25:43.511Z..2016-04-21T08:25:43.511Z%5D, lastmodifieddate:%5B2016-05-15T08:25:43.511Z..%5D, orderfulfillmentstatus:%7BNOT_STARTED%7CIN_PROGRESS%7D Note: This call requires that certain special characters in the URI query string be percent-encoded: &amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;[ &#x3D; %5B &amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;] &#x3D; %5D &amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;{ &#x3D; %7B &amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;| &#x3D; %7C &amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;} &#x3D; %7D This query filter example uses these codes. For implementation help, refer to eBay API documentation at https://developer.ebay.com/api-docs/sell/fulfillment/types/api:FilterField | [optional]
 **limit** | **String**| The number of orders to return per page of the result set. Use this parameter in conjunction with the offset parameter to control the pagination of the output. For example, if offset is set to 10 and limit is set to 10, the call retrieves orders 11 thru 20 from the result set. Note: This feature employs a zero-based list, where the first item in the list has an offset of 0. If the orderIds parameter is included in the request, this parameter will be ignored. Maximum: 1000 Default: 50 | [optional]
 **offset** | **String**| Specifies the number of orders to skip in the result set before returning the first order in the paginated response. Combine offset with the limit query parameter to control the items returned in the response. For example, if you supply an offset of 0 and a limit of 10, the first page of the response contains the first 10 items from the complete list of items retrieved by the call. If offset is 10 and limit is 20, the first page of the response contains items 11-30 from the complete result set. Default: 0 | [optional]
 **orderIds** | **String**| A comma-separated list of the unique identifiers of the orders to retrieve (maximum 50). If one or more order ID values are specified through the orderIds query parameter, all other query parameters will be ignored. Note: A new order ID format was introduced to all eBay APIs (legacy and REST) in June 2019. In REST APIs that return Order IDs, including the Fulfillment API, all order IDs are returned in the new format, but the getOrders method will accept both the legacy and new format order ID. The new format is a non-parsable string, globally unique across all eBay marketplaces, and consistent for both single line item and multiple line item orders. These order identifiers will be automatically generated after buyer payment, and unlike in the past, instead of just being known and exposed to the seller, these unique order identifiers will also be known and used/referenced by the buyer and eBay customer support. | [optional]

### Return type

[**OrderSearchPagedCollection**](OrderSearchPagedCollection.md)

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

<a name="issueRefund"></a>
# **issueRefund**
> Refund issueRefund(orderId, issueRefundRequest)

Issue Refund

This method allows a seller (opted in to eBay Managed Payments) to issue a full or partial refund to a buyer for an order. Full or partial refunds can be issued at the order level or line item level. The refunds issued through this method are processed asynchronously, so the refund will not show as &#39;Refunded&#39; right away. A seller will have to make a subsequent getOrder call to check the status of the refund. The status of an order refund can be found in the paymentSummary.refunds.refundStatus field of the getOrder response. Note: eBay Managed Payments is currently only available to a limited number of US sellers, but this program is scheduled to become available to more sellers throughout 2019 and beyond.

### Example
```java
// Import classes:
import de.metas.camel.externalsystems.ebay.api.invoker.ApiClient;
import de.metas.camel.externalsystems.ebay.api.invoker.ApiException;
import de.metas.camel.externalsystems.ebay.api.invoker.Configuration;
import de.metas.camel.externalsystems.ebay.api.invoker.auth.*;
import de.metas.camel.externalsystems.ebay.api.invoker.models.*;
import de.metas.camel.externalsystems.ebay.api.OrderApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.ebay.com/sell/fulfillment/v1");
    
    // Configure OAuth2 access token for authorization: api_auth
    OAuth api_auth = (OAuth) defaultClient.getAuthentication("api_auth");
    api_auth.setAccessToken("YOUR ACCESS TOKEN");

    OrderApi apiInstance = new OrderApi(defaultClient);
    String orderId = "orderId_example"; // String | The unique identifier of the order. Order IDs are returned in the getOrders method (and GetOrders call of Trading API). The issueRefund method supports the legacy API Order IDs and REST API order IDs. Note: In the Trading API (and other legacy APIs), Order IDs are transitioning to a new format. The new format is a non-parsable string, globally unique across all eBay marketplaces, and consistent for both single line item and multiple line item orders. These order identifiers will be automatically generated after buyer payment, and unlike in the past, instead of just being known and exposed to the seller, these unique order identifiers will also be known and used/referenced by the buyer and eBay customer support. For developers and sellers who are already integrated with the Trading API's order management calls, this change shouldn't impact your integration unless you parse the existing order identifiers (e.g., OrderID or OrderLineItemID), or otherwise infer meaning from the format (e.g., differentiating between a single line item order versus a multiple line item order). Because we realize that some integrations may have logic that is dependent upon the identifier format, eBay is rolling out the Trading API change with version control to support a transition period of approximately 9 months before applications must switch to the new format completely. See the OrderID field description in the GetOrders call for more details and requirements on transitioning to the new Order ID format.
    IssueRefundRequest issueRefundRequest = new IssueRefundRequest(); // IssueRefundRequest | 
    try {
      Refund result = apiInstance.issueRefund(orderId, issueRefundRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling OrderApi#issueRefund");
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
 **orderId** | **String**| The unique identifier of the order. Order IDs are returned in the getOrders method (and GetOrders call of Trading API). The issueRefund method supports the legacy API Order IDs and REST API order IDs. Note: In the Trading API (and other legacy APIs), Order IDs are transitioning to a new format. The new format is a non-parsable string, globally unique across all eBay marketplaces, and consistent for both single line item and multiple line item orders. These order identifiers will be automatically generated after buyer payment, and unlike in the past, instead of just being known and exposed to the seller, these unique order identifiers will also be known and used/referenced by the buyer and eBay customer support. For developers and sellers who are already integrated with the Trading API&#39;s order management calls, this change shouldn&#39;t impact your integration unless you parse the existing order identifiers (e.g., OrderID or OrderLineItemID), or otherwise infer meaning from the format (e.g., differentiating between a single line item order versus a multiple line item order). Because we realize that some integrations may have logic that is dependent upon the identifier format, eBay is rolling out the Trading API change with version control to support a transition period of approximately 9 months before applications must switch to the new format completely. See the OrderID field description in the GetOrders call for more details and requirements on transitioning to the new Order ID format. |
 **issueRefundRequest** | [**IssueRefundRequest**](IssueRefundRequest.md)|  | [optional]

### Return type

[**Refund**](Refund.md)

### Authorization

[api_auth](../README.md#api_auth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | OK |  -  |
**400** | Bad Request |  -  |
**403** | Access Forbidden |  -  |
**404** | Resource Not found |  -  |
**409** | Conflict |  -  |
**500** | Internal Server Error |  -  |

