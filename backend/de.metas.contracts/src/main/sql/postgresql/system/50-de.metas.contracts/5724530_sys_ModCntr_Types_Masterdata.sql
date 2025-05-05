


SELECT backup_table('ModCntr_Type')
;



SELECT backup_table('ModCntr_Module')
;


INSERT INTO modcntr_type (ad_client_id, ad_org_id, created, createdby, description, isactive, modcntr_type_id, name, updated, updatedby, value, modularcontracthandlertype)
VALUES (1000000, 1000000, '2024-05-27 10:37:53.824000 +01:00', 100, 'Erstellt Wareneingangslogs, die für Verkaufsmenge und Verkaufspreis in der Schlusszahlung genutzt werden.', 'Y', 540011, 'Verkauf Rohprodukt', '2024-05-27 19:18:59.305000 +02:00', 100, 'Raw Products Sales', 'SalesOnRawProduct')
;

INSERT INTO modcntr_type (ad_client_id, ad_org_id, created, createdby, description, isactive, modcntr_type_id, name, updated, updatedby, value, modularcontracthandlertype)
VALUES (1000000, 1000000, '2024-05-27 10:37:53.824000 +01:00', 100, 'Erstellt Logs für erhaltenes verarbeitetes Erzeugnis in der Produktion, , die für Verkaufsmenge und Verkaufspreis in der Schlusszahlung genutzt werden.', 'Y', 540012, 'Verkauf verarbeitetes Erzeugnis', '2024-05-27 19:21:47.104000 +02:00', 100, 'Processed Product Sales', 'SalesOnProcessedProduct')
;

INSERT INTO modcntr_type (ad_client_id, ad_org_id, created, createdby, description, isactive, modcntr_type_id, name, updated, updatedby, value, modularcontracthandlertype)
VALUES (1000000, 1000000, '2024-05-27 10:37:53.824000 +01:00', 100, 'Erstellt Wareneingangslogs für das Erntegewicht in der Schlusszahlung', 'Y', 540013, 'Wareneingang', '2024-05-27 18:44:43.627000 +02:00', 100, 'Receipts', 'Receipt')
;

INSERT INTO modcntr_type (ad_client_id, ad_org_id, created, createdby, description, isactive, modcntr_type_id, name, updated, updatedby, value, modularcontracthandlertype)
VALUES (1000000, 1000000, '2024-05-27 10:37:53.824000 +01:00', 100, 'Erstellt Logs für erhaltenes verarbeitetes Erzeugnis in der Produktion für hinzugefügte Werte in der Schlusszahlung', 'Y', 540014, 'Wert zu verarbeiteten Erzeugnis hinzufügen', '2024-05-27 19:16:19.839000 +02:00', 100, 'Add Value On Processed Product', 'AddValueOnProcessedProduct')
;

INSERT INTO modcntr_type (ad_client_id, ad_org_id, created, createdby, description, isactive, modcntr_type_id, name, updated, updatedby, value, modularcontracthandlertype)
VALUES (1000000, 1000000, '2024-05-27 10:37:53.824000 +01:00', 100, 'Erstellt die Wareneingangslogs, die in Akontozahlungen genutzt werden können und Akontozahlungslogs für die Schlusszahlung', 'Y', 540015, 'Vorfinanzierung', '2024-05-27 18:47:55.986000 +02:00', 100, 'Interim Contract', 'Interim')
;

INSERT INTO modcntr_type (ad_client_id, ad_org_id, created, createdby, description, isactive, modcntr_type_id, name, updated, updatedby, value, modularcontracthandlertype)
VALUES (1000000, 1000000, '2024-05-27 10:37:53.824000 +01:00', 100, 'Erstellt Logs für Akontozahlungen und Lieferavis, die für Zinsgutschriften in der Schlusszahlung genutzt werden', 'Y', 540016, 'Zinsgutschrift', '2024-05-27 19:29:17.456000 +02:00', 100, 'Subtracted Value on interim', 'SubtractValueOnInterim')
;

INSERT INTO modcntr_type (ad_client_id, ad_org_id, created, createdby, description, isactive, modcntr_type_id, name, updated, updatedby, value, modularcontracthandlertype)
VALUES (1000000, 1000000, '2024-05-27 10:37:53.824000 +01:00', 100, 'Erstellt Logs für genutztes Rohprodukt und erhaltenes verarbeitetes Erzeugnis in Produktion für "Abgang, Kalibrierung" in der Schlusszahlung', 'Y', 540017, 'Abgang, Kalibrierung', '2024-05-27 19:14:43.986000 +02:00', 100, 'Reduction, Calibration', 'ReductionCalibration')
;

INSERT INTO modcntr_type (ad_client_id, ad_org_id, created, createdby, description, isactive, modcntr_type_id, name, updated, updatedby, value, modularcontracthandlertype)
VALUES (1000000, 1000000, '2024-05-27 10:37:53.824000 +01:00', 100, 'Erstellt Wareneingangslogs für hinzugefügte Werte in der Schlusszahlung', 'Y', 540018, 'Wert zu Rohprodukt hinzufügen', '2024-05-27 19:19:56.743000 +02:00', 100, 'Added Value on Raw', 'AddValueOnRawProduct')
;

INSERT INTO modcntr_type (ad_client_id, ad_org_id, created, createdby, description, isactive, modcntr_type_id, name, updated, updatedby, value, modularcontracthandlertype)
VALUES (1000000, 1000000, '2024-05-27 10:37:53.824000 +01:00', 100, 'Erstellt Logs für Akontozahlungen und Lieferavis, die für "Zins Vorfinanzierung" in der Schlusszahlung genutzt werden', 'Y', 540019, 'Zins Vorfinanzierung', '2024-05-27 19:28:33.993000 +02:00', 100, 'Add Value on Interim', 'AddValueOnInterim')
;

INSERT INTO modcntr_type (ad_client_id, ad_org_id, created, createdby, description, isactive, modcntr_type_id, name, updated, updatedby, value, modularcontracthandlertype)
VALUES (1000000, 1000000, '2024-05-27 10:37:53.824000 +01:00', 100, 'Erstellt die Lieferlogs für die Lagerkosten in der Schlusszahlung', 'Y', 540020, 'Lagerkosten', '2024-05-27 19:03:52.128000 +02:00', 100, 'Storage Cost', 'StorageCost')
;

INSERT INTO modcntr_type (ad_client_id, ad_org_id, created, createdby, description, isactive, modcntr_type_id, name, updated, updatedby, value, modularcontracthandlertype)
VALUES (1000000, 1000000, '2024-05-27 10:37:53.824000 +01:00', 100, 'Erstellt Logs für erhaltenes Kuppelprodukt in der Produktion für die Menge des erhaltenem Kuppelprodukts in der Schlusszahlung', 'Y', 540021, 'Kuppelprodukt', '2024-05-27 10:37:53.824000 +01:00', 100, 'Co-Product', 'CoProduct')
;

INSERT INTO modcntr_type (ad_client_id, ad_org_id, created, createdby, description, isactive, modcntr_type_id, name, updated, updatedby, value, modularcontracthandlertype)
VALUES (1000000, 1000000, '2024-05-27 10:37:53.824000 +01:00', 100, 'Erstellt Wareneingangslogs für abgezogene Werte in der Schlusszahlung', 'Y', 540022, 'Wert von Rohprodukt abziehen', '2024-05-27 10:37:53.824000 +01:00', 100, 'Subtracted value on raw', 'SubtractValueOnRawProduct')
;


-- drop table newModCntrTypes
-- drop table oldModCntrTypes

CREATE TEMP TABLE newModCntrTypes AS (Select * from modcntr_type where modcntr_type_id <=  540022 and modcntr_type_id >= 540011);
CREATE TEMP TABLE oldModCntrTypes AS ( SELECT * FROM modcntr_type o WHERE o.modularcontracthandlertype in (select modularcontracthandlertype from newModCntrTypes n) AND not exists (select 1 from newModCntrTypes n where o.modcntr_type_id = n.modcntr_type_id));
CREATE TEMP TABLE modCntrTypeToChange AS (SELECT n.modcntr_type_ID as newTypeId, o. modcntr_type_ID as oldTypeId
                                          FROM newModCntrTypes n JOIN oldModCntrTypes o on n.modularcontracthandlertype = o.modularcontracthandlertype);

-- select * from newModCntrTypes;
-- select * from oldModCntrTypes;
-- select * from modCntrTypeToChange;

UPDATE ModCntr_Module m
SET modcntr_type_id = n.newTypeId
FROM modCntrTypeToChange n WHERE m.modcntr_type_id = n.oldTypeId;

UPDATE modcntr_type set isActive = 'N' WHERE modcntr_type_id in (select modCntrTypeToChange.oldTypeId from modCntrTypeToChange);




UPDATE modcntr_type set isActive = 'N' WHERE modcntr_type_id >=1000000;