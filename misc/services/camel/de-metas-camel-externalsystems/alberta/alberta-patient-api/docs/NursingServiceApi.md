# NursingServiceApi

All URIs are relative to *https://virtserver.swaggerhub.com/it-labs.de/PatientWawi/1.0.1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getNursingService**](NursingServiceApi.md#getNursingService) | **GET** /nursingService/{_id} | Daten eines einzelnen Pflegedienstes abrufen

<a name="getNursingService"></a>
# **getNursingService**
> NursingService getNursingService(albertaApiKey, tenant, _id)

Daten eines einzelnen Pflegedienstes abrufen

Szenario - das WaWi fragt bei Alberta nach, wie die Daten des Pflegedienstes mit der angegebenen Id sind

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.NursingServiceApi;


NursingServiceApi apiInstance = new NursingServiceApi();
String albertaApiKey = "albertaApiKey_example"; // String | 
String tenant = "tenant_example"; // String | 
String _id = "_id_example"; // String | eindeutige id des Pflegedienstes
try {
    NursingService result = apiInstance.getNursingService(albertaApiKey, tenant, _id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NursingServiceApi#getNursingService");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **albertaApiKey** | **String**|  |
 **tenant** | **String**|  |
 **_id** | **String**| eindeutige id des Pflegedienstes |

### Return type

[**NursingService**](NursingService.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/xml

