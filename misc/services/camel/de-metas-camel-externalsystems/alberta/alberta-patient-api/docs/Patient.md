# Patient

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**_id** | [**UUID**](UUID.md) |  |  [optional]
**customerId** | **String** |  |  [optional]
**customerContactId** | **String** |  |  [optional]
**gender** | [**BigDecimal**](BigDecimal.md) | 0 &#x3D; Unbekannt, 1 &#x3D; Weiblich, 2 &#x3D; Männlich, 3 &#x3D; Divers |  [optional]
**title** | [**BigDecimal**](BigDecimal.md) | 0 &#x3D; Unbekannt, 1 &#x3D; Dr., 2 &#x3D; Prof. Dr., 3 &#x3D; Dipl. Ing., 4 &#x3D; Dipl. Med., 5 &#x3D; Dipl. Psych., 6 &#x3D; Dr. Dr., 7 &#x3D; Dr. med., 8 &#x3D; Prof. Dr. Dr., 9 &#x3D; Prof., 10 &#x3D; Prof. Dr. med., 11 &#x3D; Rechtsanwalt, 12 &#x3D; Rechtsanwältin, 13 &#x3D; Schwester (Orden) |  [optional]
**firstName** | **String** |  | 
**lastName** | **String** |  | 
**birthday** | [**LocalDate**](LocalDate.md) |  | 
**address** | **String** |  |  [optional]
**additionalAddress** | **String** |  |  [optional]
**additionalAddress2** | **String** |  |  [optional]
**postalCode** | **String** |  |  [optional]
**city** | **String** |  |  [optional]
**phone** | **String** | Ohne Ländervorwahl |  [optional]
**fax** | **String** | Ohne Ländervorwahl |  [optional]
**mobilePhone** | **String** | Ohne Ländervorwahl |  [optional]
**email** | **String** |  |  [optional]
**primaryDoctorId** | **String** | Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden |  [optional]
**nursingHomeId** | **String** | Id des Pflegeheimes (Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden) |  [optional]
**nursingServiceId** | **String** | Id des Pflegedienstes (Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden) |  [optional]
**hospital** | [**PatientHospital**](PatientHospital.md) |  |  [optional]
**payer** | [**PatientPayer**](PatientPayer.md) |  |  [optional]
**changeInSupplier** | **Boolean** | Kennzeichen ob Umversorgungspatient oder nicht |  [optional]
**ivTherapy** | **Boolean** | Kennzeichen ob IV-Therapie oder nicht |  [optional]
**comment** | **String** | Bemerkung zum Patienten |  [optional]
**billingAddress** | [**PatientBillingAddress**](PatientBillingAddress.md) |  |  [optional]
**deliveryAddress** | [**PatientDeliveryAddress**](PatientDeliveryAddress.md) |  |  [optional]
**regionId** | **String** | Id der Region, der der Patient zugeordnet ist (Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden) | 
**fieldNurseId** | **String** | Id des zuständingen Außendienstmitarbeiters (Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden) |  [optional]
**deactivationReason** | [**BigDecimal**](BigDecimal.md) | 0 &#x3D; Unbekannt 1 &#x3D; Patient verstorben, 2 &#x3D; Therapieende (alle Therapien), 3 &#x3D; Leistungserbringerwechsel, 4 &#x3D; Sonstiges |  [optional]
**deactivationDate** | [**LocalDate**](LocalDate.md) | Datum der Deaktivierung (ggf. Sterbedatum) |  [optional]
**deactivationComment** | **String** |  |  [optional]
**careGivers** | [**List&lt;CareGiver&gt;**](CareGiver.md) |  |  [optional]
**archived** | **Boolean** | Kennzeichen ob Patient archiviert ist oder nicht |  [optional]
**createdAt** | [**OffsetDateTime**](OffsetDateTime.md) | Der Zeitstempel der Anlage |  [optional]
**createdBy** | **String** | Id des anlegenden Benutzers |  [optional]
**updatedAt** | [**OffsetDateTime**](OffsetDateTime.md) | Der Zeitstempel der letzten Änderung |  [optional]
**updatedBy** | **String** | Id des ändernden Benutzers |  [optional]
**timestamp** | [**OffsetDateTime**](OffsetDateTime.md) | Der letzte Zeitstempel in Alberta |  [optional]
