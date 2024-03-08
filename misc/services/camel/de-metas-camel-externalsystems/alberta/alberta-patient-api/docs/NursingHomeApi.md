# NursingHomeApi

All URIs are relative to *https://virtserver.swaggerhub.com/it-labs.de/PatientWawi/1.0.1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**geNursingHome**](NursingHomeApi.md#geNursingHome) | **GET** /nursingHome/{_id} | Daten eines einzelnen Pflegeheimes abrufen
[**getNewAndUpdatedNursingHomes**](NursingHomeApi.md#getNewAndUpdatedNursingHomes) | **GET** /nursingHome | Daten der neuen und geänderten Pflegeheime abrufen

<a name="geNursingHome"></a>
# **geNursingHome**
> NursingHome geNursingHome(albertaApiKey, _id)

Daten eines einzelnen Pflegeheimes abrufen

Szenario - das WaWi fragt bei Alberta nach, wie die Daten des Pflegeheimes mit der angegebenen Id sind

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.NursingHomeApi;


NursingHomeApi apiInstance = new NursingHomeApi();
String albertaApiKey = "albertaApiKey_example"; // String | 
String _id = "_id_example"; // String | eindeutige id des Pflegeheimes
try {
    NursingHome result = apiInstance.geNursingHome(albertaApiKey, _id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NursingHomeApi#geNursingHome");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **albertaApiKey** | **String**|  |
 **_id** | **String**| eindeutige id des Pflegeheimes |

### Return type

[**NursingHome**](NursingHome.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/xml

<a name="getNewAndUpdatedNursingHomes"></a>
# **getNewAndUpdatedNursingHomes**
> List&lt;NursingHome&gt; getNewAndUpdatedNursingHomes(albertaApiKey, updatedAfter)

Daten der neuen und geänderten Pflegeheime abrufen

Szenario - das WaWi fragt bei Alberta nach, wie ob es neue oder geänderte Pflegeheime gibt

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.NursingHomeApi;


NursingHomeApi apiInstance = new NursingHomeApi();
String albertaApiKey = "albertaApiKey_example"; // String | 
String updatedAfter = "updatedAfter_example"; // String | 2021-02-21T09:30:00.000Z (im UTC-Format)
try {
    List<NursingHome> result = apiInstance.getNewAndUpdatedNursingHomes(albertaApiKey, updatedAfter);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling NursingHomeApi#getNewAndUpdatedNursingHomes");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **albertaApiKey** | **String**|  |
 **updatedAfter** | **String**| 2021-02-21T09:30:00.000Z (im UTC-Format) |

### Return type

[**List&lt;NursingHome&gt;**](NursingHome.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/xml

