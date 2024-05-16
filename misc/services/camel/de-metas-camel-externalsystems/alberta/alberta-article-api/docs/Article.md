# Article

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**customerNumber** | **String** | Eindeutige Nummer aus der Warenwirtschaft | 
**name** | **String** | Name des Artikels | 
**description** | **String** | Beschreibung des Artikels |  [optional]
**additionalDescription** | **String** | Zusatzbeschreibung des Artikels |  [optional]
**manufacturer** | **String** | Name des Herstellers |  [optional]
**manufacturerNumber** | **String** | Herstellernummer des Artikels |  [optional]
**therapyIds** | [**List&lt;BigDecimal&gt;**](BigDecimal.md) | Auflistung der Therapien, in denen der Artikel verwendet wird (0 &#x3D; Unbekannt, 1 &#x3D; Parenterale Ernährung, 2 &#x3D; Enterale Ernährung, 3 &#x3D; Stoma, 4 &#x3D; Tracheostoma, 5 &#x3D; Inkontinenz ableitend, 6 &#x3D; Wundversorgung, 7 &#x3D; IV-Therapien, 8 &#x3D; Beatmung, 9 &#x3D; Sonstiges, 10 &#x3D; OSA, 11 &#x3D; Hustenhilfen, 12 &#x3D; Absaugung, 13 &#x3D; Patientenüberwachung, 14 &#x3D; Sauerstoff, 15 &#x3D; Inhalations- und Atemtherapie, 16 &#x3D; Lagerungshilfsmittel, 17 &#x3D; Schmerztherapie, 18 &#x3D; Immuntherapie, 19 &#x3D; Rehydration, Befeuchtung &#x3D; 20, High-Flow &#x3D; 21, Atemtherapie &#x3D; 22, Monitoring &#x3D; 23, Diagnostik &#x3D; 24, Zahnschiene &#x3D; 25, Sitzschalenbau &#x3D; 26, Orthopaedietechnik &#x3D; 27, Reha Hilfsmittel &#x3D; 28, Elektrostimulation &#x3D; 29, Diabetes &#x3D; 30, Applikationshilfen IV PE EE &#x3D; 31, Messgeraete fuer Koerperszustaende/-funktionen &#x3D; 32, PCA Schmerztherapie &#x3D; 33, Arzneimittelgabe &#x3D; 34) |  [optional]
**billableTherapies** | [**List&lt;BigDecimal&gt;**](BigDecimal.md) | Auflistung der abrechenbaren Therapien für den Artikel (siehe therapies) |  [optional]
**productGroupId** | **String** | Id der Warengruppe (Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich der Warengruppen in WaWi vorhanden) |  [optional]
**size** | **String** | Die Beschreibung der Größe |  [optional]
**inventoryType** | [**BigDecimal**](BigDecimal.md) | Art der Bevorratung 1&#x3D;Lagerartikel / 2&#x3D;Kommisionnierartikel |  [optional]
**prescriptionType** | [**BigDecimal**](BigDecimal.md) | Rezepttyp 0&#x3D;Arzneimittel, 1&#x3D;Verbandstoffe, 2&#x3D;BtM-Rezept, 3&#x3D;Pflegehilfsmittel, 4&#x3D;Hilfsmittel zum Verbrauch bestimmt, 5&#x3D;Hilfsmittel zum Gebrauch bestimmt |  [optional]
**status** | [**BigDecimal**](BigDecimal.md) | Status des Artikels (0&#x3D; Unbekannt, 1&#x3D;Verfügbar, 2&#x3D;Gesperrt, 3&#x3D;Temporär nicht verfügbar, 4&#x3D;Endgültig nicht verfügbar\&quot;) |  [optional]
**statusAnnotation** | **String** | Hinweis zum Status |  [optional]
**medicalAidPositionNumber** | **String** | Hilfsmittelpositionsnummer |  [optional]
**unavailableFrom** | [**LocalDate**](LocalDate.md) | gesperrt/nicht mehr verfügbar ab Datum |  [optional]
**unavailableTo** | [**LocalDate**](LocalDate.md) | temporär nicht verfügbar bis Datum - nur bei Status 3 |  [optional]
**stars** | [**BigDecimal**](BigDecimal.md) | Sterneranking des einzelnen Artikels im Warenkorb, falls keine Wirtschaftlichkeitsberechnung gewünscht |  [optional]
**assortmentType** | [**BigDecimal**](BigDecimal.md) | Sortimentsart (0&#x3D;Unbekannt / 1&#x3D;Fokusartikel / 2&#x3D;Standardartikel / 3&#x3D;Randartikel) - beeinflusst die Darstellung im Warenkorb |  [optional]
**pharmacyPrice** | **String** | Der Apothekeneinkaufspreis eines Artikels (AEP) -&gt; pro Stück |  [optional]
**fixedPrice** | **String** | Der Festbetrag eines Artikels -&gt; pro Stück |  [optional]
**purchaseRating** | [**BigDecimal**](BigDecimal.md) | Rating (virtueller Rabatt) für die Wirtschaftlichkeitsberechung - Werte von 0 bis 6  (0 &#x3D; sehr wirtschaftlich 6 &#x3D; nicht wirtschaftlich) |  [optional]
**packagingUnits** | [**List&lt;PackagingUnit&gt;**](PackagingUnit.md) |  | 
**pharmacyOnly** | **Boolean** | apothekenpflichtige Artikel |  [optional]
**archived** | **Boolean** | Kennzeichen ob Artikel archiviert ist |  [optional]
