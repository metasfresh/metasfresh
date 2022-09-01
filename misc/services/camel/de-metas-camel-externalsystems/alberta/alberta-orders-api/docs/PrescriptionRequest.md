# PrescriptionRequest

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**_id** | **String** |  |  [optional]
**patientId** | **String** | Id des Patienten in Alberta | 
**patientBirthday** | [**LocalDate**](LocalDate.md) | Id des Patienten in Alberta |  [optional]
**orderId** | **String** | Id der zugrundliegenden Bestellung in Alberta |  [optional]
**prescriptionType** | [**BigDecimal**](BigDecimal.md) | Rezepttyp (0 &#x3D; Arzneimittel 1 &#x3D; Verbandstoffe 2 &#x3D; BtM-Rezept 3 &#x3D; Pflegehilfsmittel 4 &#x3D; Hilfsmittel zum Verbrauch bestimmt 5 &#x3D; Hilfsmittel zum Gebrauch bestimmt) |  [optional]
**creationDate** | [**OffsetDateTime**](OffsetDateTime.md) |  |  [optional]
**deliveryDate** | [**LocalDate**](LocalDate.md) | Lieferdatum der Bestellung, auf der das Rezept basiert | 
**startDate** | [**LocalDate**](LocalDate.md) | Versorgungsbeginn |  [optional]
**endDate** | [**LocalDate**](LocalDate.md) | Versorgungsende |  [optional]
**accountingMonths** | [**List&lt;BigDecimal&gt;**](BigDecimal.md) | Auflistung der Versorgungs-/Abrechnungsmonate 1,2 &#x3D;&gt; Januar, Februar |  [optional]
**doctorId** | **String** | Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden | 
**pharmacyId** | **String** | Id der Apotheke bei PE/IV-Therapien (Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden) |  [optional]
**therapyId** | [**BigDecimal**](BigDecimal.md) | Therapie, für die der Vertrag gilt (0&#x3D; Unbekannt, 1 &#x3D; Parenterale Ernährung, 2 &#x3D; Enterale Ernährung, 3 &#x3D; Stoma, 4 &#x3D; Tracheostoma, 5 &#x3D; Inkontinenz ableitend, 6 &#x3D; Wundversorgung, 7 &#x3D; IV-Therapien, 8 &#x3D; Beatmung, 9 &#x3D; Sonstiges, 10 &#x3D; OSA, 11 &#x3D; Hustenhilfen, 12 &#x3D; Absaugung, 13 &#x3D; Patientenüberwachung, 14 &#x3D; Sauerstoff, 15 &#x3D; Inhalations- und Atemtherapie, 16 &#x3D; Lagerungshilfsmittel, 17 &#x3D; Immuntherapie, 18 &#x3D; Rehydration) | 
**therapyTypeIds** | [**List&lt;BigDecimal&gt;**](BigDecimal.md) |  |  [optional]
**prescriptedArticleLines** | [**List&lt;PrescriptedArticleLine&gt;**](PrescriptedArticleLine.md) | Die einzelnen Zeilen der Rezeptanforderung mit Artikeln |  [optional]
**createdBy** | **String** | Id des erstellenden Benutzers (Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich der Benutzer in WaWi vorhanden) |  [optional]
**annotation** | **String** | Bemerkung |  [optional]
**archived** | **Boolean** | Kennzeichen ob Rezeptanforderung archiviert ist oder nicht |  [optional]
**timestamp** | [**OffsetDateTime**](OffsetDateTime.md) | Der Zeitstempel der letzten Datenbankänderung |  [optional]
**updated** | [**OffsetDateTime**](OffsetDateTime.md) | Der Zeitstempel der letzten Änderung |  [optional]
