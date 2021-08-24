# AttachmentApi

All URIs are relative to *https://virtserver.swaggerhub.com/it-labs.de/Dokument/1.0.0*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllAttachments**](AttachmentApi.md#getAllAttachments) | **GET** /attachment | Anlagen abrufen
[**getSingleAttachment**](AttachmentApi.md#getSingleAttachment) | **GET** /attachment/{id} | Einzelne Anlage abrufen

<a name="getAllAttachments"></a>
# **getAllAttachments**
> ArrayOfAttachments getAllAttachments(apiKey, createdAfter, type)

Anlagen abrufen

Szenario - das WaWi fragt in einem bestimmten Intervall bei Alberta nach, ob es neue hochgeladene Anlagen gibt

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AttachmentApi;


AttachmentApi apiInstance = new AttachmentApi();
String apiKey = "apiKey_example"; // String | 
String createdAfter = "createdAfter_example"; // String | letzter timestamp z.B. 2020-11-10T08:55:00.000Z
BigDecimal type = new BigDecimal(); // BigDecimal | Art der Anlage (0 = Krankenkassekarte, 1 = Rezeptkopie, 2 = Entlassbrief, 3 = Patientenwahlrecht, 4 = Abliefernachweis, 5 = Sonstiges, 6 = Zuzahlungsbefreiungsausweis, 7 = Versorgungsvorschlag, 8 = Ärztliche Delegation)
try {
    ArrayOfAttachments result = apiInstance.getAllAttachments(apiKey, createdAfter, type);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AttachmentApi#getAllAttachments");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **apiKey** | **String**|  |
 **createdAfter** | **String**| letzter timestamp z.B. 2020-11-10T08:55:00.000Z |
 **type** | **BigDecimal**| Art der Anlage (0 &#x3D; Krankenkassekarte, 1 &#x3D; Rezeptkopie, 2 &#x3D; Entlassbrief, 3 &#x3D; Patientenwahlrecht, 4 &#x3D; Abliefernachweis, 5 &#x3D; Sonstiges, 6 &#x3D; Zuzahlungsbefreiungsausweis, 7 &#x3D; Versorgungsvorschlag, 8 &#x3D; Ärztliche Delegation) | [optional]

### Return type

[**ArrayOfAttachments**](ArrayOfAttachments.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

<a name="getSingleAttachment"></a>
# **getSingleAttachment**
> File getSingleAttachment(apiKey, id)

Einzelne Anlage abrufen

Szenario - das WaWi ruft ein bestimmte Anlage als PDF ab

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AttachmentApi;


AttachmentApi apiInstance = new AttachmentApi();
String apiKey = "apiKey_example"; // String | 
String id = "id_example"; // String | 
try {
    File result = apiInstance.getSingleAttachment(apiKey, id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AttachmentApi#getSingleAttachment");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **apiKey** | **String**|  |
 **id** | **String**|  |

### Return type

[**File**](File.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/pdf

