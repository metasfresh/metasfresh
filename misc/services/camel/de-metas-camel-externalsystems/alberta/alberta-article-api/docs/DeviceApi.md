# DeviceApi

All URIs are relative to *https://virtserver.swaggerhub.com/it-labs.de/Artikel/1.0.2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**postDevice**](DeviceApi.md#postDevice) | **POST** /device | Gerätedaten nach Alberta übertragen
[**putDevice**](DeviceApi.md#putDevice) | **PUT** /device/{_id} | Gerätedaten in Alberta ändern

<a name="postDevice"></a>
# **postDevice**
> DeviceMapping postDevice(albertaApiKey, body)

Gerätedaten nach Alberta übertragen

legt Geräte in Alberta an

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DeviceApi;


DeviceApi apiInstance = new DeviceApi();
String albertaApiKey = "albertaApiKey_example"; // String | 
DeviceToCreate body = new DeviceToCreate(); // DeviceToCreate | device to create
try {
    DeviceMapping result = apiInstance.postDevice(albertaApiKey, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DeviceApi#postDevice");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **albertaApiKey** | **String**|  |
 **body** | [**DeviceToCreate**](DeviceToCreate.md)| device to create | [optional]

### Return type

[**DeviceMapping**](DeviceMapping.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="putDevice"></a>
# **putDevice**
> DeviceMapping putDevice(albertaApiKey, _id, body)

Gerätedaten in Alberta ändern

ändert Geräte in Alberta

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DeviceApi;


DeviceApi apiInstance = new DeviceApi();
String albertaApiKey = "albertaApiKey_example"; // String | 
String _id = "_id_example"; // String | 
DeviceToCreate body = new DeviceToCreate(); // DeviceToCreate | device to create
try {
    DeviceMapping result = apiInstance.putDevice(albertaApiKey, _id, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DeviceApi#putDevice");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **albertaApiKey** | **String**|  |
 **_id** | **String**|  |
 **body** | [**DeviceToCreate**](DeviceToCreate.md)| device to create | [optional]

### Return type

[**DeviceMapping**](DeviceMapping.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

