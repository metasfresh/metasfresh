# HospitalApi

All URIs are relative to *https://virtserver.swaggerhub.com/it-labs.de/PatientWawi/1.0.1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getHospital**](HospitalApi.md#getHospital) | **GET** /hospital/{_id} | Daten einer einzelnen Klinik abrufen

<a name="getHospital"></a>
# **getHospital**
> Hospital getHospital(albertaApiKey, tenant, _id)

Daten einer einzelnen Klinik abrufen

Szenario - das WaWi fragt bei Alberta nach, wie die Daten der Klinik mit der angegebenen Id sind

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.HospitalApi;


HospitalApi apiInstance = new HospitalApi();
String albertaApiKey = "albertaApiKey_example"; // String | 
String tenant = "tenant_example"; // String | 
String _id = "_id_example"; // String | eindeutige Id der Klinik
try {
    Hospital result = apiInstance.getHospital(albertaApiKey, tenant, _id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling HospitalApi#getHospital");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **albertaApiKey** | **String**|  |
 **tenant** | **String**|  |
 **_id** | **String**| eindeutige Id der Klinik |

### Return type

[**Hospital**](Hospital.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/xml

