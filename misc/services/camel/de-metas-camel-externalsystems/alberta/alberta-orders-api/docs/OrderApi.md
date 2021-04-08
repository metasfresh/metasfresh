# OrderApi

All URIs are relative to *https://virtserver.swaggerhub.com/it-labs.de/AuftragWawi/1.0.1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addOrder**](OrderApi.md#addOrder) | **POST** /order | Auftrag hinzufügen
[**getCreatedOrders**](OrderApi.md#getCreatedOrders) | **GET** /order | Bestellungen je nach Status abrufen

<a name="addOrder"></a>
# **addOrder**
> OrderMapping addOrder(body, apiKey)

Auftrag hinzufügen

Szenario - ein Auftrag wurde im WaWi angelegt und soll in Alberta übertragen werden

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.OrderApi;


OrderApi apiInstance = new OrderApi();
Order body = new Order(); // Order | Die Bestellung
String apiKey = "apiKey_example"; // String | 
try {
    OrderMapping result = apiInstance.addOrder(body, apiKey);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OrderApi#addOrder");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Order**](Order.md)| Die Bestellung |
 **apiKey** | **String**|  |

### Return type

[**OrderMapping**](OrderMapping.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json, application/xml
 - **Accept**: application/json, application/xml

<a name="getCreatedOrders"></a>
# **getCreatedOrders**
> ArrayOfOrders getCreatedOrders(apiKey, status, updatedAfter)

Bestellungen je nach Status abrufen

Szenario - das WaWi fragt in einem bestimmten Intervall bei Alberta nach, ob es neu angelegte Bestellungen gibt ----- Aufruf &#x3D;&gt; order/?status&#x3D;[status]&amp;updatedAfter&#x3D;[updatedAfter]

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.OrderApi;


OrderApi apiInstance = new OrderApi();
String apiKey = "apiKey_example"; // String | 
String status = "status_example"; // String | created (später ggf. archived) -
String updatedAfter = "updatedAfter_example"; // String | 2018-02-21T09:30:00.000Z (im UTC-Format)
try {
    ArrayOfOrders result = apiInstance.getCreatedOrders(apiKey, status, updatedAfter);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling OrderApi#getCreatedOrders");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **apiKey** | **String**|  |
 **status** | **String**| created (später ggf. archived) - |
 **updatedAfter** | **String**| 2018-02-21T09:30:00.000Z (im UTC-Format) |

### Return type

[**ArrayOfOrders**](ArrayOfOrders.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/xml

