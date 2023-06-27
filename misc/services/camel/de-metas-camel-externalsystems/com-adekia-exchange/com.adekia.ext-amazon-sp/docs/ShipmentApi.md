# ShipmentApi

All URIs are relative to *https://sellingpartnerapi-na.amazon.com/*

Method | HTTP request | Description
------------- | ------------- | -------------
[**updateShipmentStatus**](ShipmentApi.md#updateShipmentStatus) | **POST** /orders/v0/orders/{orderId}/shipment | 

<a name="updateShipmentStatus"></a>
# **updateShipmentStatus**
> updateShipmentStatus(body, orderId)



Update the shipment status.

### Example
```java
// Import classes:
//import com.adekia.exchange.amazonsp.client.ApiException;
//import com.adekia.exchange.amazonsp.client.api.ShipmentApi;


ShipmentApi apiInstance = new ShipmentApi();
UpdateShipmentStatusRequest body = new UpdateShipmentStatusRequest(); // UpdateShipmentStatusRequest | Request to update the shipment status.
String orderId = "orderId_example"; // String | An Amazon-defined order identifier, in 3-7-7 format.
try {
    apiInstance.updateShipmentStatus(body, orderId);
} catch (ApiException e) {
    System.err.println("Exception when calling ShipmentApi#updateShipmentStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**UpdateShipmentStatusRequest**](UpdateShipmentStatusRequest.md)| Request to update the shipment status. |
 **orderId** | **String**| An Amazon-defined order identifier, in 3-7-7 format. |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

