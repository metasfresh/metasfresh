# PayerApi

All URIs are relative to *https://virtserver.swaggerhub.com/it-labs.de/PatientWawi/1.0.1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getPayer**](PayerApi.md#getPayer) | **GET** /payer/{_id} | Daten eines einzelnen Kostenträgers abrufen

<a name="getPayer"></a>
# **getPayer**
> Payer getPayer(albertaApiKey, tenant, _id)

Daten eines einzelnen Kostenträgers abrufen

Szenario - das WaWi fragt bei Alberta nach, wie die Daten des Kostenträgers mit der angegebenen Id sind

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PayerApi;


PayerApi apiInstance = new PayerApi();
String albertaApiKey = "albertaApiKey_example"; // String | 
String tenant = "tenant_example"; // String | 
String _id = "_id_example"; // String | eindeutige id des Kostenträgers - bei Selbstzahlern, die des Patienten
try {
    Payer result = apiInstance.getPayer(albertaApiKey, tenant, _id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PayerApi#getPayer");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **albertaApiKey** | **String**|  |
 **tenant** | **String**|  |
 **_id** | **String**| eindeutige id des Kostenträgers - bei Selbstzahlern, die des Patienten |

### Return type

[**Payer**](Payer.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/xml

