# RegionApi

All URIs are relative to *https://virtserver.swaggerhub.com/it-labs.de/PatientWawi/1.0.1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getRegion**](RegionApi.md#getRegion) | **GET** /region/{_id} | Daten einer einzelnen Region abrufen

<a name="getRegion"></a>
# **getRegion**
> Region getRegion(albertaApiKey, tenant, _id)

Daten einer einzelnen Region abrufen

Szenario - das WaWi fragt bei Alberta nach, wie die Daten der Region mit der angegebenen Id sind

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.RegionApi;


RegionApi apiInstance = new RegionApi();
String albertaApiKey = "albertaApiKey_example"; // String | 
String tenant = "tenant_example"; // String | 
String _id = "_id_example"; // String | eindeutige id der Region
try {
    Region result = apiInstance.getRegion(albertaApiKey, tenant, _id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling RegionApi#getRegion");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **albertaApiKey** | **String**|  |
 **tenant** | **String**|  |
 **_id** | **String**| eindeutige id der Region |

### Return type

[**Region**](Region.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/xml

