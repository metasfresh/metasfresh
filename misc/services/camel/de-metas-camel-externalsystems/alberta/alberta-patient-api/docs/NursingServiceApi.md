# NursingServiceApi

All URIs are relative to *https://virtserver.swaggerhub.com/it-labs.de/PatientWawi/1.0.1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getNewAndUpdatedNursingServices**](NursingServiceApi.md#getNewAndUpdatedNursingServices) | **GET** /nursingService | Daten der neuen und geänderten Pflegedienste abrufen
[**getNursingService**](NursingServiceApi.md#getNursingService) | **GET** /nursingService/{_id} | Daten eines einzelnen Pflegedienstes abrufen

<a name="getNewAndUpdatedNursingServices"></a>
# **getNewAndUpdatedNursingServices**
> List&lt;NursingService&gt; getNewAndUpdatedNursingServices(albertaApiKey, updatedAfter)

Daten der neuen und geänderten Pflegedienste abrufen

Szenario - das WaWi fragt bei Alberta nach, wie ob es neue oder geänderte Pflegedienste gibt

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.NursingServiceApi;


NursingServiceApi apiInstance = new NursingServiceApi();
String albertaApiKey = "albertaApiKey_example"; // String | 
String updatedAfter = "updatedAfter_example"; // String | 2021-02-21T09:30:00.000Z (im UTC-Format)
try {
    List<NursingService> result = apiInstance.getNewAndUpdatedNursingServices(albertaApiKey, updatedAfter);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NursingServiceApi#getNewAndUpdatedNursingServices");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **albertaApiKey** | **String**|  |
 **updatedAfter** | **String**| 2021-02-21T09:30:00.000Z (im UTC-Format) |

### Return type

[**List&lt;NursingService&gt;**](NursingService.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/xml

<a name="getNursingService"></a>
# **getNursingService**
> NursingService getNursingService(albertaApiKey, _id)

Daten eines einzelnen Pflegedienstes abrufen

Szenario - das WaWi fragt bei Alberta nach, wie die Daten des Pflegedienstes mit der angegebenen Id sind

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.NursingServiceApi;


NursingServiceApi apiInstance = new NursingServiceApi();
String albertaApiKey = "albertaApiKey_example"; // String | 
String _id = "_id_example"; // String | eindeutige id des Pflegedienstes
try {
    NursingService result = apiInstance.getNursingService(albertaApiKey, _id);
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
 **_id** | **String**| eindeutige id des Pflegedienstes |

### Return type

[**NursingService**](NursingService.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/xml

