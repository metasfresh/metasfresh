# PayerApi

All URIs are relative to *https://virtserver.swaggerhub.com/it-labs.de/PatientWawi/1.0.1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getNewAndUpdatedPayers**](PayerApi.md#getNewAndUpdatedPayers) | **GET** /payer | Daten der neuen und geänderten Kostenträger abrufen
[**getPayer**](PayerApi.md#getPayer) | **GET** /payer/{_id} | Daten eines einzelnen Kostenträgers abrufen

<a name="getNewAndUpdatedPayers"></a>
# **getNewAndUpdatedPayers**
> List&lt;Payer&gt; getNewAndUpdatedPayers(albertaApiKey, updatedAfter)

Daten der neuen und geänderten Kostenträger abrufen

Szenario - das WaWi fragt bei Alberta nach, wie ob es neue oder geänderte Kostenträger gibt

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PayerApi;


PayerApi apiInstance = new PayerApi();
String albertaApiKey = "albertaApiKey_example"; // String | 
String updatedAfter = "updatedAfter_example"; // String | 2021-02-21T09:30:00.000Z (im UTC-Format)
try {
    List<Payer> result = apiInstance.getNewAndUpdatedPayers(albertaApiKey, updatedAfter);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PayerApi#getNewAndUpdatedPayers");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **albertaApiKey** | **String**|  |
 **updatedAfter** | **String**| 2021-02-21T09:30:00.000Z (im UTC-Format) |

### Return type

[**List&lt;Payer&gt;**](Payer.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/xml

<a name="getPayer"></a>
# **getPayer**
> Payer getPayer(albertaApiKey, _id)

Daten eines einzelnen Kostenträgers abrufen

Szenario - das WaWi fragt bei Alberta nach, wie die Daten des Kostenträgers mit der angegebenen Id sind

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PayerApi;


PayerApi apiInstance = new PayerApi();
String albertaApiKey = "albertaApiKey_example"; // String | 
String _id = "_id_example"; // String | eindeutige id des Kostenträgers - bei Selbstzahlern, die des Patienten
try {
    Payer result = apiInstance.getPayer(albertaApiKey, _id);
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
 **_id** | **String**| eindeutige id des Kostenträgers - bei Selbstzahlern, die des Patienten |

### Return type

[**Payer**](Payer.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/xml

