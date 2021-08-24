# DeviceToCreate

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**patientId** | **String** | Eindeutige Id des Patienten aus Alberta | 
**articleId** | **String** | Eindeutige Id des Artikel aus Alberta |  [optional]
**articleNumber** | **String** | Eindeutige Nummer des Artikel aus dem WaWi |  [optional]
**serialNumber** | **String** | Seriennummer des Geräts | 
**registerNumber** | **String** | Registernummer des Geräts |  [optional]
**description** | **String** | Beschreibung des Geräts | 
**additionalDescription** | **String** | Zusatzbeschreibung des Geräts |  [optional]
**locked** | **Boolean** | Gesperrt |  [optional]
**commissioningDate** | **String** | Inbetriebnahmedatum |  [optional]
**repairEstimateRequired** | **Boolean** | Rep. KV nötig |  [optional]
**repairEstimateLimit** | **String** | Rep. KV Wertgrenze |  [optional]
**lastBookingCode** | **String** | Letzter Buchungscode |  [optional]
**lastLocationCode** | **String** | Letzter LagerOrtCode |  [optional]
**ownerName** | **String** | Eigentümername |  [optional]
**deviceNumber** | **String** | Gerätenummer |  [optional]
**maintenances** | [**List&lt;DeviceToCreateMaintenances&gt;**](DeviceToCreateMaintenances.md) |  |  [optional]
**deviceInfomationLines** | [**List&lt;DeviceInformationLine&gt;**](DeviceInformationLine.md) | Die einzelnen Wartungen zum Gerät |  [optional]
**archived** | **Boolean** | Kennzeichen ob Gerät komplett archiviert wurde |  [optional]
