# DoctorApi

All URIs are relative to *https://virtserver.swaggerhub.com/it-labs.de/PatientWawi/1.0.1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getDoctor**](DoctorApi.md#getDoctor) | **GET** /doctor/{_id} | Daten eines einzelnen Arztes abrufen

<a name="getDoctor"></a>
# **getDoctor**
> Doctor getDoctor(albertaApiKey, tenant, _id)

Daten eines einzelnen Arztes abrufen

Szenario - das WaWi fragt bei Alberta nach, wie die Daten des Arztes mit der angegebenen Id sind

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DoctorApi;


DoctorApi apiInstance = new DoctorApi();
String albertaApiKey = "albertaApiKey_example"; // String | 
String tenant = "tenant_example"; // String | 
String _id = "_id_example"; // String | eindeutige id des Arztes (Object-Id)
try {
    Doctor result = apiInstance.getDoctor(albertaApiKey, tenant, _id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DoctorApi#getDoctor");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **albertaApiKey** | **String**|  |
 **tenant** | **String**|  |
 **_id** | **String**| eindeutige id des Arztes (Object-Id) |

### Return type

[**Doctor**](Doctor.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/xml

