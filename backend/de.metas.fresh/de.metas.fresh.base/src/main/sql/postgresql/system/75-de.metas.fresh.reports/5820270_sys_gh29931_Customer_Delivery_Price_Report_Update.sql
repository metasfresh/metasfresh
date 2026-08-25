-- Run mode: SWING_CLIENT

-- 2026-08-25T15:15:21.854Z
UPDATE AD_Process_Para SET AD_Element_ID=1581, ColumnName='DateFrom', Description='Startdatum eines Abschnittes', Help='Datum von bezeichnet das Startdatum eines Abschnittes', Name='Datum von',Updated=TO_TIMESTAMP('2026-08-25 15:15:21.673000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543191
;

-- 2026-08-25T15:15:21.930Z
UPDATE AD_Process_Para_Trl trl SET Description='Startdatum eines Abschnittes',Help='Datum von bezeichnet das Startdatum eines Abschnittes',Name='Datum von' WHERE AD_Process_Para_ID=543191 AND AD_Language='de_DE'
;

-- 2026-08-25T15:16:26.499Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1582,0,585614,543275,15,'DateTo',TO_TIMESTAMP('2026-08-25 15:16:26.118000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Enddatum eines Abschnittes','D',0,'Datum bis bezeichnet das Enddatum eines Abschnittes (inklusiv)','Y','N','Y','N','N','N','Datum bis',15,TO_TIMESTAMP('2026-08-25 15:16:26.118000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-25T15:16:26.569Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543275 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

UPDATE AD_Process
SET Description = E'This report provides a consolidated overview of the processed shipment schedules for a given business partner (or all) within a selectable delivery period (Date from - Date to), enriched with the current sales prices for each product.\nFor each product two prices are shown: the standard list price from the price system assigned to the customer and - where one exists - the customer-specific special price. Please note:\n* Period: if no "Date to" is given, only the "Date from" day is considered.\n* No discounts: the prices shown are standard list prices; customer-specific discounts or promotional deductions are not applied.\n* No ASI/PI matching: price resolution ignores attribute-set-instance (ASI) specific prices and packaging (handling unit/PI) specific prices.\n* Standard UOM only: the standard price for the standard unit of measure (UOM) of the product is output.',
    Updated = now(), UpdatedBy = 100
WHERE AD_Process_ID = 585614
;

UPDATE AD_Process_Trl
SET Description = E'Dieser Bericht bietet eine konsolidierte Übersicht der verarbeiteten Lieferpläne (Shipment Schedules) für einen bestimmten Geschäftspartner (oder alle) innerhalb eines wählbaren Lieferzeitraums (Datum von - Datum bis), angereichert mit den aktuellen Verkaufspreisen je Produkt.\nFür jedes Produkt werden zwei Preise ausgewiesen: der Standard-Listenpreis aus dem dem Kunden zugewiesenen Preissystem sowie - sofern vorhanden - der kundenspezifische Sonderpreis. Bitte beachten Sie:\n* Zeitraum: Ohne Angabe von "Datum bis" wird nur der Tag "Datum von" berücksichtigt.\n* Keine Rabatte: Die ausgewiesenen Preise sind Standard-Listenpreise; kundenspezifische Rabatte oder Aktionsabzüge werden nicht berücksichtigt.\n* Keine ASI/PI-Prüfung: Die Preisermittlung ignoriert spezifische Preise für Attributsatz-Instanzen (ASI) sowie Packmittel-spezifische Preise (Handling Unit/PI).\n* Nur Standard-UOM: Es wird der Standardpreis für die Standard-Maßeinheit (UOM) des Produkts ausgegeben.',
    IsTranslated = 'Y', Updated=TO_TIMESTAMP('2026-08-25 15:15:21.673000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
WHERE AD_Process_ID = 585614
  AND AD_Language LIKE 'de\_%'
;

UPDATE AD_Process_Trl
SET Description = E'This report provides a consolidated overview of the processed shipment schedules for a given business partner (or all) within a selectable delivery period (Date from - Date to), enriched with the current sales prices for each product.\nFor each product two prices are shown: the standard list price from the price system assigned to the customer and - where one exists - the customer-specific special price. Please note:\n* Period: if no "Date to" is given, only the "Date from" day is considered.\n* No discounts: the prices shown are standard list prices; customer-specific discounts or promotional deductions are not applied.\n* No ASI/PI matching: price resolution ignores attribute-set-instance (ASI) specific prices and packaging (handling unit/PI) specific prices.\n* Standard UOM only: the standard price for the standard unit of measure (UOM) of the product is output.',
    IsTranslated = 'Y', Updated=TO_TIMESTAMP('2026-08-25 15:15:21.673000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
WHERE AD_Process_ID = 585614
  AND AD_Language LIKE 'en\_%'
;
