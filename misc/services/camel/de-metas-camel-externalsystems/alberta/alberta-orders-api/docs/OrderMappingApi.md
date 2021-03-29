# OrderMappingApi

All URIs are relative to *https://virtserver.swaggerhub.com/it-labs.de/AuftragWawi/1.0.1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getOrderMapping**](OrderMappingApi.md#getOrderMapping) | **GET** /order/orderMapping | Zuordnung Auftrag (WaWi) zu Bestellung (Alberta) abrufen

<a name="getOrderMapping"></a>
# **getOrderMapping**
> OrderMapping getOrderMapping(apiKey, salesId)

Zuordnung Auftrag (WaWi) zu Bestellung (Alberta) abrufen

Szenario - das WaWi fragt bei Alberta nach, welche Alberta-Id dem jeweiligen Auftrag zugeordnet ist

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.OrderMappingApi;


OrderMappingApi apiInstance = new OrderMappingApi();
String apiKey = "apiKey_example"; // String | 
String salesId = "salesId_example"; // String | Die Id des Auftrags aus dem WaWi
try {
    OrderMapping result = apiInstance.getOrderMapping(apiKey, salesId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OrderMappingApi#getOrderMapping");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **apiKey** | **String**|  |
 **salesId** | **String**| Die Id des Auftrags aus dem WaWi |

### Return type

[**OrderMapping**](OrderMapping.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/xml

