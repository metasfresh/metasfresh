# DefaultApi

All URIs are relative to *https://virtserver.swaggerhub.com/it-labs.de/Artikel/1.0.2*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addArticle**](DefaultApi.md#addArticle) | **POST** /article | neuen Artikel in Alberta anlegen
[**addInsuranceContract**](DefaultApi.md#addInsuranceContract) | **POST** /insuranceContract | neuen Krankenkassenvertrag in Alberta anlegen
[**updateArticle**](DefaultApi.md#updateArticle) | **PUT** /article/{customerNumber} | Artikel in Alberta ändern
[**updateInsuranceContract**](DefaultApi.md#updateInsuranceContract) | **PUT** /insuranceContract/{id} | Krankenkassenvertrag in Alberta ändern

<a name="addArticle"></a>
# **addArticle**
> ArticleMapping addArticle(albertaApiKey, body)

neuen Artikel in Alberta anlegen

Legt einen neuen Artikel in Alberta an

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DefaultApi;


DefaultApi apiInstance = new DefaultApi();
String albertaApiKey = "albertaApiKey_example"; // String | 
Article body = new Article(); // Article | article to add
try {
    ArticleMapping result = apiInstance.addArticle(albertaApiKey, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DefaultApi#addArticle");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **albertaApiKey** | **String**|  |
 **body** | [**Article**](Article.md)| article to add | [optional]

### Return type

[**ArticleMapping**](ArticleMapping.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="addInsuranceContract"></a>
# **addInsuranceContract**
> ArticleMapping addInsuranceContract(albertaApiKey, body)

neuen Krankenkassenvertrag in Alberta anlegen

Legt einen neuen Krankenkassenvertrag in Alberta an

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DefaultApi;


DefaultApi apiInstance = new DefaultApi();
String albertaApiKey = "albertaApiKey_example"; // String | 
Article body = new Article(); // Article | insuranceContract to add
try {
    ArticleMapping result = apiInstance.addInsuranceContract(albertaApiKey, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DefaultApi#addInsuranceContract");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **albertaApiKey** | **String**|  |
 **body** | [**Article**](Article.md)| insuranceContract to add | [optional]

### Return type

[**ArticleMapping**](ArticleMapping.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateArticle"></a>
# **updateArticle**
> ArticleMapping updateArticle(albertaApiKey, customerNumber, body)

Artikel in Alberta ändern

ändert einen Artikel in Alberta

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DefaultApi;


DefaultApi apiInstance = new DefaultApi();
String albertaApiKey = "albertaApiKey_example"; // String | 
String customerNumber = "customerNumber_example"; // String | 
Article body = new Article(); // Article | article to update
try {
    ArticleMapping result = apiInstance.updateArticle(albertaApiKey, customerNumber, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DefaultApi#updateArticle");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **albertaApiKey** | **String**|  |
 **customerNumber** | **String**|  |
 **body** | [**Article**](Article.md)| article to update | [optional]

### Return type

[**ArticleMapping**](ArticleMapping.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="updateInsuranceContract"></a>
# **updateInsuranceContract**
> ArticleMapping updateInsuranceContract(albertaApiKey, tenant, id, body)

Krankenkassenvertrag in Alberta ändern

ändert einen Krankenkassenvertrag in Alberta

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DefaultApi;


DefaultApi apiInstance = new DefaultApi();
String albertaApiKey = "albertaApiKey_example"; // String | 
String tenant = "tenant_example"; // String | 
String id = "id_example"; // String | 
Article body = new Article(); // Article | article to update
try {
    ArticleMapping result = apiInstance.updateInsuranceContract(albertaApiKey, tenant, id, body);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DefaultApi#updateInsuranceContract");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **albertaApiKey** | **String**|  |
 **tenant** | **String**|  |
 **id** | **String**|  |
 **body** | [**Article**](Article.md)| article to update | [optional]

### Return type

[**ArticleMapping**](ArticleMapping.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

