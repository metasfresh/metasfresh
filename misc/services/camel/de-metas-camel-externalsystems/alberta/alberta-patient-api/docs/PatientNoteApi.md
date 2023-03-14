# PatientNoteApi

All URIs are relative to *https://virtserver.swaggerhub.com/it-labs.de/PatientWawi/1.0.1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**patientNote**](PatientNoteApi.md#patientNote) | **POST** /patientNote | PatientenNotiz erstellen
[**patientNotePatch**](PatientNoteApi.md#patientNotePatch) | **PATCH** /patientNote/{id} | PatientenNotiz ändern

<a name="patientNote"></a>
# **patientNote**
> PatientNoteMapping patientNote(apiKey, body)

PatientenNotiz erstellen

Szenario - das WaWi erstellt eine PatientenNotiz in Alberta

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PatientNoteApi;


PatientNoteApi apiInstance = new PatientNoteApi();
String apiKey = "apiKey_example"; // String | 
PatientNote body = new PatientNote(); // PatientNote | patientNote to add
try {
    PatientNoteMapping result = apiInstance.patientNote(apiKey, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PatientNoteApi#patientNote");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **apiKey** | **String**|  |
 **body** | [**PatientNote**](PatientNote.md)| patientNote to add | [optional]

### Return type

[**PatientNoteMapping**](PatientNoteMapping.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="patientNotePatch"></a>
# **patientNotePatch**
> PatientNoteMapping patientNotePatch(apiKey, id, body)

PatientenNotiz ändern

Szenario - das WaWi ändert eine PatientenNotiz in Alberta

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.PatientNoteApi;


PatientNoteApi apiInstance = new PatientNoteApi();
String apiKey = "apiKey_example"; // String | 
String id = "id_example"; // String | Id des Patienten in Alberta
PatientNote body = new PatientNote(); // PatientNote | patientNote to update
try {
    PatientNoteMapping result = apiInstance.patientNotePatch(apiKey, id, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling PatientNoteApi#patientNotePatch");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **apiKey** | **String**|  |
 **id** | **String**| Id des Patienten in Alberta |
 **body** | [**PatientNote**](PatientNote.md)| patientNote to update | [optional]

### Return type

[**PatientNoteMapping**](PatientNoteMapping.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

