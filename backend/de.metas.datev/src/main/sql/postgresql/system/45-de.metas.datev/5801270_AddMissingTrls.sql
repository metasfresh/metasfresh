-- Run mode: SWING_CLIENT

-- 2026-05-07T12:08:34.355Z
UPDATE AD_Process SET JasperReport_Tabular='@PREFIX@de/metas/reports/customerdeliverypriceoverview/report_TabularView.jasper',Updated=TO_TIMESTAMP('2026-05-07 12:08:34.352000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585614
;

-- 2026-05-07T12:09:33.075Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,577492,0,585614,543193,17,541097,'ReportFormat',TO_TIMESTAMP('2026-05-07 12:09:32.938000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'@SQL=SELECT getDefaultValue_ProcessPara(p_AD_Process_Para_ID => 541681)','U',0,'Y','N','Y','N','Y','N','Report format',30,'N',TO_TIMESTAMP('2026-05-07 12:09:32.938000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-07T12:09:33.081Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543193 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2026-05-07T12:38:34.324Z
UPDATE AD_Process_Para SET DefaultValue='PDF',Updated=TO_TIMESTAMP('2026-05-07 12:38:34.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543193
;

-- 2026-05-07T12:41:30.713Z
UPDATE AD_Process_Trl SET Description='This report provides a consolidated view of processed shipment schedules for a specific business partner, enriched with the current raw standard sales price for each product.
It specifically retrieves the base list price from the customer''s assigned pricing system. Please note:
* No Discounts: Prices shown are standard list prices; customer-specific discounts or promotional deductions are not applied.
* No ASI/PI Matching: The lookup ignores Attribute Set Instances (ASI) and Handling Unit/Package Item (PI) specific pricing.
* Standard UOM only: It returns the base standard price for the product''s default unit of measure.', IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-07 12:41:30.711000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585614
;

-- 2026-05-07T12:41:30.715Z
UPDATE AD_Process base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-07T12:42:15.259Z
UPDATE AD_Process_Trl SET Description='Dieser Bericht bietet eine konsolidierte Übersicht der verarbeiteten Lieferpläne (Shipment Schedules) für einen bestimmten Geschäftspartner, angereichert mit dem aktuellen Standard-Listenverkaufspreis für jedes Produkt.
Der Bericht ruft gezielt den Basis-Listenpreis aus dem dem Kunden zugewiesenen Preissystem ab. Bitte beachten Sie:
* Keine Rabatte: Die ausgewiesenen Preise sind Standard-Listenpreise; kundenspezifische Rabatte oder Aktionsabzüge werden nicht berücksichtigt.
* Keine ASI/PI-Prüfung: Die Preisermittlung ignoriert spezifische Preise für Attributsatz-Instanzen (ASI) sowie Packmittel-spezifische Preise (Handling Unit/PI).
* Nur Standard-UOM: Es wird der Basis-Standardpreis für die Standard-Maßeinheit (UOM) des Produkts ausgegeben.', IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-07 12:42:15.255000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585614
;

-- 2026-05-07T12:42:15.259Z
UPDATE AD_Process base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;


--------------------------------------------------------
--------------------------------------------------------
--------------------------------------------------------
--------------------------------------------------------
--------------------------------------------------------
--------------------------------------------------------











-- 2026-05-07T14:03:35.209Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-07 14:03:35.207000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543882 AND AD_Language='de_CH'
;

-- 2026-05-07T14:04:03.514Z
UPDATE AD_Element_Trl SET Name='CSV-Codierung', PrintName='CSV-Codierung',Updated=TO_TIMESTAMP('2026-05-07 14:04:03.512000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543882 AND AD_Language in ('de_CH', 'de_DE')
;

-- 2026-05-07T14:04:03.515Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language in ('de_CH', 'de_DE') AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-07T14:06:31.672Z
UPDATE AD_Element_Trl SET Name='CSV-Feldtrenner', PrintName='CSV-Feldtrenner',Updated=TO_TIMESTAMP('2026-05-07 14:06:31.672000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543883 AND AD_Language='de_CH'
;

-- 2026-05-07T14:06:31.672Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-07T14:06:36.612Z
UPDATE AD_Element_Trl SET Name='CSV-Feldtrenner', PrintName='CSV-Feldtrenner',Updated=TO_TIMESTAMP('2026-05-07 14:06:36.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543883 AND AD_Language='de_DE'
;

-- 2026-05-07T14:06:36.613Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-07T14:07:19.300Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Dezimaltrennzeichen', PrintName='Dezimaltrennzeichen',Updated=TO_TIMESTAMP('2026-05-07 14:07:19.297000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543888 AND AD_Language in ('de_CH', 'de_DE')
;

-- 2026-05-07T14:07:19.301Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language in ('de_CH', 'de_DE') AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-07T14:07:22.594Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-07 14:07:22.593000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543888 AND AD_Language='en_US'
;

-- 2026-05-07T14:08:35.155Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Tausendertrennzeichen', PrintName='Tausendertrennzeichen',Updated=TO_TIMESTAMP('2026-05-07 14:08:35.151000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543887 AND AD_Language in ('de_CH', 'de_DE')
;

-- 2026-05-07T14:08:35.155Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language in ('de_CH', 'de_DE') AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-07T14:08:38.861Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-07 14:08:38.859000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543887 AND AD_Language='en_US'
;

