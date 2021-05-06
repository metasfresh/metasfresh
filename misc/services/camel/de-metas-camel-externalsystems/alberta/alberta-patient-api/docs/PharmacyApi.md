# PharmacyApi

All URIs are relative to *https://virtserver.swaggerhub.com/it-labs.de/PatientWawi/1.0.1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getPharmacy**](PharmacyApi.md#getPharmacy) | **GET** /pharmacy/{_id} | Daten einer einzelnen Apotheke abrufen

<a name="getPharmacy"></a>
# **getPharmacy**
> Pharmacy getPharmacy(albertaApiKey, tenant, _id)

Daten einer einzelnen Apotheke abrufen

Szenario - das WaWi fragt bei Alberta nach, wie die Daten der Apotheke mit der angegebenen Id sind

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PharmacyApi;


PharmacyApi apiInstance = new PharmacyApi();
String albertaApiKey = "albertaApiKey_example"; // String | 
String tenant = "tenant_example"; // String | 
String _id = "_id_example"; // String | eindeutige Id der Apotheke
try {
    Pharmacy result = apiInstance.getPharmacy(albertaApiKey, tenant, _id);
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
 **tenant** | **String**|  |
 **_id** | **String**| eindeutige Id der Apotheke |

### Return type

[**Pharmacy**](Pharmacy.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/xml

