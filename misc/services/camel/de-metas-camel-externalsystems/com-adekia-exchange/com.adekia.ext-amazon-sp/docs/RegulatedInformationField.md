# RegulatedInformationField

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**fieldId** | **String** | The unique identifier for the field. | 
**fieldLabel** | **String** | The human-readable name for the field. | 
**fieldType** | [**FieldTypeEnum**](#FieldTypeEnum) | The type of field the field. | 
**fieldValue** | **String** | The content of the field as collected in regulatory form. Note that FileAttachment type fields will contain an URL to download the attachment here. | 

<a name="FieldTypeEnum"></a>
## Enum: FieldTypeEnum
Name | Value
---- | -----
TEXT | &quot;Text&quot;
FILEATTACHMENT | &quot;FileAttachment&quot;
