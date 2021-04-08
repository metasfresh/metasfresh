# PrescriptionRequestApi

All URIs are relative to *https://virtserver.swaggerhub.com/it-labs.de/AuftragWawi/1.0.1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getCreatedPrescriptionRequests**](PrescriptionRequestApi.md#getCreatedPrescriptionRequests) | **GET** /prescriptionRequest | Rezeptanforderungen je nach Status abrufen

<a name="getCreatedPrescriptionRequests"></a>
# **getCreatedPrescriptionRequests**
> ArrayOfPrescriptionRequests getCreatedPrescriptionRequests(albertaApiKey, status, updatedAfter)

Rezeptanforderungen je nach Status abrufen

Szenario - das WaWi fragt in einem bestimmten Intervall bei Alberta nach, ob es neu angelegte Rezeptanforderungen gibt ----- Aufruf &#x3D;&gt; order/?status&#x3D;[status]&amp;updatedAfter&#x3D;[updatedAfter]

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PrescriptionRequestApi;


PrescriptionRequestApi apiInstance = new PrescriptionRequestApi();
String albertaApiKey = "albertaApiKey_example"; // String | 
String status = "status_example"; // String | created (später ggf. archived) -
String updatedAfter = "updatedAfter_example"; // String | 2018-02-21T09:30:00.000Z (im UTC-Format)
try {
    ArrayOfPrescriptionRequests result = apiInstance.getCreatedPrescriptionRequests(albertaApiKey, status, updatedAfter);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PrescriptionRequestApi#getCreatedPrescriptionRequests");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **albertaApiKey** | **String**|  |
 **status** | **String**| created (später ggf. archived) - |
 **updatedAfter** | **String**| 2018-02-21T09:30:00.000Z (im UTC-Format) |

### Return type

[**ArrayOfPrescriptionRequests**](ArrayOfPrescriptionRequests.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/xml

