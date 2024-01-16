# PharmacyApi

All URIs are relative to *https://virtserver.swaggerhub.com/it-labs.de/PatientWawi/1.0.1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getNewAndUpdatedPharmacies**](PharmacyApi.md#getNewAndUpdatedPharmacies) | **GET** /pharmacy | Daten der neuen und geänderten Apotheken abrufen
[**getPharmacy**](PharmacyApi.md#getPharmacy) | **GET** /pharmacy/{_id} | Daten einer einzelnen Apotheke abrufen

<a name="getNewAndUpdatedPharmacies"></a>
# **getNewAndUpdatedPharmacies**
> List&lt;Pharmacy&gt; getNewAndUpdatedPharmacies(albertaApiKey, updatedAfter)

Daten der neuen und geänderten Apotheken abrufen

Szenario - das WaWi fragt bei Alberta nach, wie ob es neue oder geänderte Apotheken gibt

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PharmacyApi;


PharmacyApi apiInstance = new PharmacyApi();
String albertaApiKey = "albertaApiKey_example"; // String | 
String updatedAfter = "updatedAfter_example"; // String | 2021-02-21T09:30:00.000Z (im UTC-Format)
try {
    List<Pharmacy> result = apiInstance.getNewAndUpdatedPharmacies(albertaApiKey, updatedAfter);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PharmacyApi#getNewAndUpdatedPharmacies");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **albertaApiKey** | **String**|  |
 **updatedAfter** | **String**| 2021-02-21T09:30:00.000Z (im UTC-Format) |

### Return type

[**List&lt;Pharmacy&gt;**](Pharmacy.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/xml

<a name="getPharmacy"></a>
# **getPharmacy**
> Pharmacy getPharmacy(albertaApiKey, _id)

Daten einer einzelnen Apotheke abrufen

Szenario - das WaWi fragt bei Alberta nach, wie die Daten der Apotheke mit der angegebenen Id sind

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PharmacyApi;


PharmacyApi apiInstance = new PharmacyApi();
String albertaApiKey = "albertaApiKey_example"; // String | 
String _id = "_id_example"; // String | eindeutige Id der Apotheke
try {
    Pharmacy result = apiInstance.getPharmacy(albertaApiKey, _id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PharmacyApi#getPharmacy");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **albertaApiKey** | **String**|  |
 **_id** | **String**| eindeutige Id der Apotheke |

### Return type

[**Pharmacy**](Pharmacy.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/xml

