# CustomerMappingApi

All URIs are relative to *https://virtserver.swaggerhub.com/it-labs.de/PatientWawi/1.0.1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getCustomerMapping**](CustomerMappingApi.md#getCustomerMapping) | **GET** /patient/customerMapping | Zuordnung Kunde (WaWi) zu Patient (Alberta) abrufen

<a name="getCustomerMapping"></a>
# **getCustomerMapping**
> CustomerMapping getCustomerMapping(albertaApiKey, tenant, customerId)

Zuordnung Kunde (WaWi) zu Patient (Alberta) abrufen

Szenario - das WaWi fragt bei Alberta nach, welche Alberta-Id dem jeweiligen Kunden zugeordnet ist

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.CustomerMappingApi;


CustomerMappingApi apiInstance = new CustomerMappingApi();
String albertaApiKey = "albertaApiKey_example"; // String | 
String tenant = "tenant_example"; // String | 
String customerId = "customerId_example"; // String | die Id des Kunden aus dem WaWi
try {
    CustomerMapping result = apiInstance.getCustomerMapping(albertaApiKey, tenant, customerId);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CustomerMappingApi#getCustomerMapping");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **albertaApiKey** | **String**|  |
 **tenant** | **String**|  |
 **customerId** | **String**| die Id des Kunden aus dem WaWi |

### Return type

[**CustomerMapping**](CustomerMapping.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/xml

