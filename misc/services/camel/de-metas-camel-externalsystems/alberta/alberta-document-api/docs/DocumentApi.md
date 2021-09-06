# DocumentApi

All URIs are relative to *https://virtserver.swaggerhub.com/it-labs.de/Dokument/1.0.0*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllDocuments**](DocumentApi.md#getAllDocuments) | **GET** /document | Abgeschlossene Dokumente abrufen
[**getSingleDocument**](DocumentApi.md#getSingleDocument) | **GET** /document/{id} | Einzelnes Dokument abrufen

<a name="getAllDocuments"></a>
# **getAllDocuments**
> ArrayOfDocuments getAllDocuments(apiKey, createdAfter)

Abgeschlossene Dokumente abrufen

Szenario - das WaWi fragt in einem bestimmten Intervall bei Alberta nach, ob es neue abgeschlossene Dokumente gibt

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DocumentApi;


DocumentApi apiInstance = new DocumentApi();
String apiKey = "apiKey_example"; // String | 
String createdAfter = "createdAfter_example"; // String | letzter timestamp z.B. 2020-11-10T08:55:00.000Z
try {
    ArrayOfDocuments result = apiInstance.getAllDocuments(apiKey, createdAfter);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DocumentApi#getAllDocuments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **apiKey** | **String**|  |
 **createdAfter** | **String**| letzter timestamp z.B. 2020-11-10T08:55:00.000Z |

### Return type

[**ArrayOfDocuments**](ArrayOfDocuments.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSingleDocument"></a>
# **getSingleDocument**
> File getSingleDocument(apiKey, id)

Einzelnes Dokument abrufen

Szenario - das WaWi fragt ein bestimmtes Dokument im PDF-Format an

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DocumentApi;


DocumentApi apiInstance = new DocumentApi();
String apiKey = "apiKey_example"; // String | 
String id = "id_example"; // String | 
try {
    File result = apiInstance.getSingleDocument(apiKey, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DocumentApi#getSingleDocument");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **apiKey** | **String**|  |
 **id** | **String**|  |

### Return type

[**File**](File.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/pdf

