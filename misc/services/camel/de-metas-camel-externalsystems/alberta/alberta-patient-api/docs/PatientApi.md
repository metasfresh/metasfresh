# PatientApi

All URIs are relative to *https://virtserver.swaggerhub.com/it-labs.de/PatientWawi/1.0.1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addPatient**](PatientApi.md#addPatient) | **POST** /patient | Patient hinzufügen
[**getCreatedPatients**](PatientApi.md#getCreatedPatients) | **GET** /patient | Patienten je nach Status abrufen
[**updatePatient**](PatientApi.md#updatePatient) | **PUT** /patient | Patient ändern

<a name="addPatient"></a>
# **addPatient**
> ArrayOfMappings addPatient(body, albertaApiKey, tenant)

Patient hinzufügen

Szenario - ein Patient wurde im WaWi angelegt und soll in Alberta übertragen werden

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PatientApi;


PatientApi apiInstance = new PatientApi();
Patient body = new Patient(); // Patient | Der Patient
String albertaApiKey = "albertaApiKey_example"; // String | 
String tenant = "tenant_example"; // String | 
try {
    ArrayOfMappings result = apiInstance.addPatient(body, albertaApiKey, tenant);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PatientApi#addPatient");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Patient**](Patient.md)| Der Patient |
 **albertaApiKey** | **String**|  |
 **tenant** | **String**|  |

### Return type

[**ArrayOfMappings**](ArrayOfMappings.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json, application/xml
 - **Accept**: application/json, application/xml

<a name="getCreatedPatients"></a>
# **getCreatedPatients**
> ArrayOfPatients getCreatedPatients(albertaApiKey, tenant, status, updatedAfter)

Patienten je nach Status abrufen

Szenario - das WaWi fragt in einem bestimmten Intervall bei Alberta nach, ob es neu angelegte, geänderte oder archivierte Patienten gibt ----- Aufruf &#x3D;&gt; patient/?status&#x3D;[status]&amp;updatedAfter&#x3D;[updatedAfter]

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PatientApi;


PatientApi apiInstance = new PatientApi();
String albertaApiKey = "albertaApiKey_example"; // String | 
String tenant = "tenant_example"; // String | 
String status = "status_example"; // String | created, updated oder archived -
String updatedAfter = "updatedAfter_example"; // String | 2018-02-21T09:30:00.000Z (im UTC-Format)
try {
    ArrayOfPatients result = apiInstance.getCreatedPatients(albertaApiKey, tenant, status, updatedAfter);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PatientApi#getCreatedPatients");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **albertaApiKey** | **String**|  |
 **tenant** | **String**|  |
 **status** | **String**| created, updated oder archived - |
 **updatedAfter** | **String**| 2018-02-21T09:30:00.000Z (im UTC-Format) |

### Return type

[**ArrayOfPatients**](ArrayOfPatients.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json, application/xml

<a name="updatePatient"></a>
# **updatePatient**
> CustomerMapping updatePatient(body, albertaApiKey, tenant, id)

Patient ändern

Szenario - ein Patient wurde im WaWi geändert und diese Änderungen sollen in Alberta übertragen werden ----- Aufruf &#x3D;&gt; patient/[patientenId]

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PatientApi;


PatientApi apiInstance = new PatientApi();
Patient body = new Patient(); // Patient | Der Patient
String albertaApiKey = "albertaApiKey_example"; // String | 
String tenant = "tenant_example"; // String | 
String id = "id_example"; // String | die Id des zu ändernden Patienten
try {
    CustomerMapping result = apiInstance.updatePatient(body, albertaApiKey, tenant, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PatientApi#updatePatient");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Patient**](Patient.md)| Der Patient |
 **albertaApiKey** | **String**|  |
 **tenant** | **String**|  |
 **id** | **String**| die Id des zu ändernden Patienten |

### Return type

[**CustomerMapping**](CustomerMapping.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json, application/xml
 - **Accept**: application/json, application/xml

