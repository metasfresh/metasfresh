# OrderedArticleLine

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**_id** | **String** | Id der bestellten Artikelzeile |  [optional]
**salesLineId** | **String** | Id der einzelnen Verkaufszeile im WaWi |  [optional]
**articleId** | **String** | Id des Artikels in Alberta |  [optional]
**articleCustomerNumber** | **String** | Eindeutige Nummer des Artikels im WaWi des Kunden |  [optional]
**quantity** | [**BigDecimal**](BigDecimal.md) | Anzahl |  [optional]
**unit** | **String** | STK &#x3D; Stück, KTN &#x3D; Karton/Verpackung |  [optional]
**duration** | [**OrderedArticleLineDuration**](OrderedArticleLineDuration.md) |  |  [optional]
**isRentalEquipment** | **Boolean** | Mietgerät/Fremdgerät |  [optional]
**isPrivateSale** | **Boolean** | Privatkauf (nicht über Kostenträger abgerechnet) |  [optional]
**updated** | [**OffsetDateTime**](OffsetDateTime.md) | Der Zeitstempel der letzten Änderung |  [optional]
