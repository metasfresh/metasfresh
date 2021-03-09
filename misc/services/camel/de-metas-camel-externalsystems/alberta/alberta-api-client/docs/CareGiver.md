# CareGiver

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**_id** | [**UUID**](UUID.md) |  |  [optional]
**customerId** | **String** |  |  [optional]
**type** | [**BigDecimal**](BigDecimal.md) | gibt des Art des Patientenkontaktes an - Unknown &#x3D; 0, Ehemann &#x3D; 1, Ehefrau &#x3D; 2, Lebensgefährte/in &#x3D; 3, Sohn &#x3D; 4, Tochter &#x3D; 5, Enkel &#x3D; 6, Enkelin &#x3D; 7, Urenkel &#x3D; 8, Urenkelin &#x3D; 9, Mutter &#x3D; 10, Vater &#x3D; 11, Onkel &#x3D; 12, Tante &#x3D; 13, Nichte &#x3D; 14, Neffe &#x3D; 15, Großonkel &#x3D; 16, Großtante &#x3D; 17, Großnichte &#x3D; 18, Großneffe &#x3D; 19, Schwester &#x3D; 20, Bruder &#x3D; 21, Cousin &#x3D; 22, Cousine &#x3D; 23, Schwager &#x3D; 24, Schwägerin &#x3D; 25, Schwagersbruder &#x3D; 26, Schwagersschwester &#x3D; 27, Schwiegermutter &#x3D; 28, Schwiegervater &#x3D; 29, Schwiegeronkel &#x3D; 30, Schwiegertante &#x3D; 31, Schwiegerkind &#x3D; 32, Schwiegersohn &#x3D; 33, Schwiegertochter &#x3D; 34, Schwiegerenkel &#x3D; 35, Schwiegerenkelin &#x3D; 36, Stiefmutter &#x3D; 37, Stiefvater &#x3D; 38, Stiefschwester &#x3D; 39, Stiefbruder &#x3D; 40, Stieftochter &#x3D; 41, Stiefsohn &#x3D; 42, Stiefenkel &#x3D; 43, Stiefurenkel &#x3D; 44, Pflegesohn &#x3D; 45, Pflegetochter &#x3D; 46, Pflegemutter &#x3D; 47, Pflegevater &#x3D; 48, Halbschwester &#x3D; 49, Halbbruder &#x3D; 50, Halbonkel &#x3D; 51, Halbtante &#x3D; 52, Halbcousin &#x3D; 53, Halbcousine &#x3D; 54, Nachbarin &#x3D; 55, Nachbar &#x3D; 56, Betreuungsbüro &#x3D; 57, Freund(in) &#x3D; 58 |  [optional]
**isLegalCarer** | **Boolean** | Kennzeichen, ob es sich beim Kontakt um einen gesetzlichen Betreuer handelt |  [optional]
**gender** | **String** | 0 &#x3D; Unbekannt, 1 &#x3D; Weiblich, 2 &#x3D; Männlich, 3 &#x3D; Divers |  [optional]
**title** | [**BigDecimal**](BigDecimal.md) | 0 &#x3D; Unbekannt, 1 &#x3D; Dr., 2 &#x3D; Prof. Dr., 3 &#x3D; Dipl. Ing., 4 &#x3D; Dipl. Med., 5 &#x3D; Dipl. Psych., 6 &#x3D; Dr. Dr., 7 &#x3D; Dr. med., 8 &#x3D; Prof. Dr. Dr., 9 &#x3D; Prof., 10 &#x3D; Prof. Dr. med., 11 &#x3D; Rechtsanwalt, 12 &#x3D; Rechtsanwältin, 13 &#x3D; Schwester (Orden) |  [optional]
**firstName** | **String** |  | 
**lastName** | **String** |  | 
**address** | **String** |  |  [optional]
**postalCode** | **String** |  |  [optional]
**city** | **String** |  |  [optional]
**phone** | **String** | Ohne Ländervorwahl |  [optional]
**mobilePhone** | **String** | Ohne Ländervorwahl |  [optional]
**fax** | **String** | Ohne Ländervorwahl |  [optional]
**email** | **String** |  |  [optional]
**archived** | **Boolean** | Kennzeichen ob Patientenkontakt archiviert ist oder nicht |  [optional]
**timestamp** | [**OffsetDateTime**](OffsetDateTime.md) | Der Zeitstempel der letzten Änderung |  [optional]
