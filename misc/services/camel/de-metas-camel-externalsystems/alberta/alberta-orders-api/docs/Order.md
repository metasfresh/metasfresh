# Order

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**_id** | **String** |  |  [optional]
**salesId** | **String** | Id des Auftrag im WaWi |  [optional]
**patientId** | **String** | Id des Patienten in Alberta | 
**rootId** | **String** | Id der übergeordeneten Bestellung - falls gesetzt Zusatzbestellung |  [optional]
**creationDate** | [**LocalDate**](LocalDate.md) |  |  [optional]
**deliveryDate** | [**LocalDate**](LocalDate.md) |  | 
**startDate** | [**LocalDate**](LocalDate.md) | Versorgungsbeginn |  [optional]
**endDate** | [**LocalDate**](LocalDate.md) | Versorgungsende |  [optional]
**dayOfDelivery** | [**BigDecimal**](BigDecimal.md) | Tag des Monats an dem geliefert wird -&gt; nur bei Serienbestellung! |  [optional]
**nextDelivery** | [**LocalDate**](LocalDate.md) | Einmal abweichender nächster Liefertag bei Serienbestellung |  [optional]
**deliveryAddress** | [**OrderDeliveryAddress**](OrderDeliveryAddress.md) |  | 
**doctorId** | **String** | Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden | 
**pharmacyId** | **String** | Id der Apotheke bei PE/IV-Therapien (Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden) |  [optional]
**therapyId** | [**BigDecimal**](BigDecimal.md) | Therapie, für die der Vertrag gilt (0&#x3D; Unbekannt, 1 &#x3D; Parenterale Ernährung, 2 &#x3D; Enterale Ernährung, 3 &#x3D; Stoma, 4 &#x3D; Tracheostoma, 5 &#x3D; Inkontinenz ableitend, 6 &#x3D; Wundversorgung, 7 &#x3D; IV-Therapien, 8 &#x3D; Beatmung, 9 &#x3D; Sonstiges, 10 &#x3D; OSA, 11 &#x3D; Hustenhilfen, 12 &#x3D; Absaugung, 13 &#x3D; Patientenüberwachung, 14 &#x3D; Sauerstoff, 15 &#x3D; Inhalations- und Atemtherapie, 16 &#x3D; Lagerungshilfsmittel, 17 &#x3D; Immuntherapie, 18 &#x3D; Rehydration) | 
**therapyTypeIds** | [**List&lt;BigDecimal&gt;**](BigDecimal.md) |  |  [optional]
**isInitialCare** | **Boolean** | true &#x3D; Erstversorgung, false &#x3D; Folgeversorgung | 
**orderedArticleLines** | [**List&lt;OrderedArticleLine&gt;**](OrderedArticleLine.md) | Die einzelnen Bestellzeilen mit Artikeln |  [optional]
**createdBy** | **String** | Id des erstellenden Benutzers (Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich der Benutzer in WaWi vorhanden) |  [optional]
**status** | [**BigDecimal**](BigDecimal.md) | Status der Bestellung (-3 &#x3D; Ausstehend, 0 &#x3D; Angelegt, 1 &#x3D; Übermittelt, 2 &#x3D; Übermittlung fehlgeschlagen, 3 &#x3D; Verarbeitet, 4 &#x3D; Versandt, 5 &#x3D; Ausgeliefert, 6 &#x3D; Gelöscht, 7 &#x3D; Storniert, 8 &#x3D; Gestoppte Serienbestellung) |  [optional]
**isSeriesOrder** | **Boolean** | true &#x3D; Serienbestellung (läuft automatisch über WaWi), false &#x3D; normale Einzelbestellung |  [optional]
**annotation** | **String** | Hinweise zur Bestellung |  [optional]
**archived** | **Boolean** | Kennzeichen ob Bestellung archiviert ist oder nicht |  [optional]
**updated** | [**OffsetDateTime**](OffsetDateTime.md) | Der Zeitstempel der letzten Änderung |  [optional]
