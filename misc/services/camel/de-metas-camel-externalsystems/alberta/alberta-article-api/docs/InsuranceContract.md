# InsuranceContract

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**insuranceContractName** | **String** | Vertragsnummer der Krankenkasse |  [optional]
**salesContracts** | [**List&lt;InsuranceContractSalesContracts&gt;**](InsuranceContractSalesContracts.md) |  |  [optional]
**name** | **String** | Sprechender Name des Vertrags | 
**description** | **String** | Beschreibung des Vertrags |  [optional]
**therapyId** | [**BigDecimal**](BigDecimal.md) | Therapie, für die der Vertrag gilt (0&#x3D; Unbekannt, 1 &#x3D; Parenterale Ernährung, 2 &#x3D; Enterale Ernährung, 3 &#x3D; Stoma, 4 &#x3D; Tracheostoma, 5 &#x3D; Inkontinenz ableitend, 6 &#x3D; Wundversorgung, 7 &#x3D; IV-Therapien, 8 &#x3D; Beatmung, 9 &#x3D; Sonstiges, 10 &#x3D; OSA, 11 &#x3D; Hustenhilfen, 12 &#x3D; Absaugung, 13 &#x3D; Patientenüberwachung, 14 &#x3D; Sauerstoff, 15 &#x3D; Inhalations- und Atemtherapie, 16 &#x3D; Lagerungshilfsmittel, 17 &#x3D; Immuntherapie, 18 &#x3D; Rehydration) |  [optional]
**therapyTypeIds** | [**List&lt;BigDecimal&gt;**](BigDecimal.md) |  |  [optional]
**validFrom** | [**LocalDate**](LocalDate.md) | Vertrag gültig ab |  [optional]
**validTo** | [**LocalDate**](LocalDate.md) | Vertrag gültig bis |  [optional]
**payerIds** | [**List&lt;UUID&gt;**](UUID.md) | Alberta-Ids aller Krankenkassen, für die der Vertrag gilt |  [optional]
**notPayingPayerIds** | [**List&lt;UUID&gt;**](UUID.md) | Nur bei Selbstzahlerverträgen! Alberta-Ids aller Krankenkassen, für die der Vertrag NICHT gilt, also selbst gezahlt werden muss |  [optional]
**maximumAmountForProductGroups** | [**List&lt;InsuranceContractMaximumAmountForProductGroups&gt;**](InsuranceContractMaximumAmountForProductGroups.md) |  |  [optional]
**requiredTemplates** | [**List&lt;InsuranceContractRequiredTemplates&gt;**](InsuranceContractRequiredTemplates.md) | Auflistung aller Dokumentenvorlagen in Alberta, die lt. Vertrag ausgefüllt werden müssen |  [optional]
**ageLimit** | **String** | Altersbeschränkung für Personen, für die der Vertrag gilt |  [optional]
**periodOnPrescriptionNecessary** | **Boolean** | Zeitraum auf VO erforderlich |  [optional]
**approvalObligation** | **Boolean** | Genehmigungspflicht |  [optional]
**flatChargeTextAllowed** | **Boolean** | Rezeptanforderung Pauschalentext erlaubt |  [optional]
**costEstimateRequired** | **Boolean** | Kostenvoranschlag nötig |  [optional]
**pricingModelType** | [**BigDecimal**](BigDecimal.md) | Art des Preismodells für die Abrechnung - (0 &#x3D; Unbekannt, 1 &#x3D; Pauschale, 2 &#x3D; Aufschlag, 3 &#x3D; Abschlag, 4 &#x3D; Festbetrag) |  [optional]
**pricingBasis** | **String** | Wenn Preismodell Aufschlag oder Abschlag, dann hier der Bezugspreis für Auf-/Abschlag (mögliche Werte \&quot;AEP\&quot; oder \&quot;Festbetrag\&quot;) |  [optional]
**billingType** | [**BigDecimal**](BigDecimal.md) | Abrechungsart nach § - mögliche Werte 300 und 302 |  [optional]
**isSelfPayer** | **Boolean** | Selbstzahlervertrag Ja/Nein |  [optional]
**visitInterval** | [**InsuranceContractVisitInterval**](InsuranceContractVisitInterval.md) |  |  [optional]
**maxPermanentPrescriptionPeriod** | [**InsuranceContractMaxPermanentPrescriptionPeriod**](InsuranceContractMaxPermanentPrescriptionPeriod.md) |  |  [optional]
