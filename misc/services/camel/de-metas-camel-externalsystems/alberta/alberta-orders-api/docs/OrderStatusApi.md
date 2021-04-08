# OrderStatusApi

All URIs are relative to *https://virtserver.swaggerhub.com/it-labs.de/AuftragWawi/1.0.1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**updateOrderStatus**](OrderStatusApi.md#updateOrderStatus) | **PATCH** /order | Auftragsstatus (ggf. später auch Rezeptstatus) ändern

<a name="updateOrderStatus"></a>
# **updateOrderStatus**
> updateOrderStatus(body, apiKey, id)

Auftragsstatus (ggf. später auch Rezeptstatus) ändern

Szenario - ein Auftrag wurde im WaWi geändert und diese Änderungen sollen in Alberta übertragen werden ----- Aufruf &#x3D;&gt; orderStatus/[orderId]

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.OrderStatusApi;


OrderStatusApi apiInstance = new OrderStatusApi();
OrderStatus body = new OrderStatus(); // OrderStatus | Der Bestellstatus
String apiKey = "apiKey_example"; // String | 
String id = "id_example"; // String | die Id des zu ändernden Autrags
try {
    apiInstance.updateOrderStatus(body, apiKey, id);
} catch (ApiException e) {
    System.err.println("Exception when calling OrderStatusApi#updateOrderStatus");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**OrderStatus**](OrderStatus.md)| Der Bestellstatus |
 **apiKey** | **String**|  |
 **id** | **String**| die Id des zu ändernden Autrags |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json, application/xml
 - **Accept**: Not defined

