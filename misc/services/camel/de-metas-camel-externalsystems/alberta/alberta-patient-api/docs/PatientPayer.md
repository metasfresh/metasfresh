# PatientPayer

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**payerId** | **String** | Id des Kostenträgers (Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden) |  [optional]
**payerType** | **String** | Typ des Kostenträgers (0 &#x3D; Unbekannt, 1 &#x3D; gesetzlich, 2 &#x3D; privat, 3 &#x3D; Berufsgenossenschaft, 4 &#x3D; Selbstzahler, 5 &#x3D; Andere) |  [optional]
**numberOfInsured** | **String** | Versichertennummer des Patienten (Krankenkasse) |  [optional]
**copaymentFromDate** | [**LocalDate**](LocalDate.md) | Zuzahlungsbefreit ab Datum - Feld bleibt leer, wenn keine Zuzahlungsbefreiung |  [optional]
**copaymentToDate** | [**LocalDate**](LocalDate.md) | Zuzahlungsbefreit bis Datum - Feld bleibt leer, wenn keine Zuzahlungsbefreiung |  [optional]
