-- Run mode: SWING_CLIENT

UPDATE AD_Process SET VALUE = 'Sales_Invoice_Line_Details (Excel)',Updated=TO_TIMESTAMP('2026-02-01 11:51:10.541000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585439;


-- Process: Sales Invoice Report (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2026-02-01T11:51:10.542Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Sales Invoice Lines (Excel)',Updated=TO_TIMESTAMP('2026-02-01 11:51:10.541000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585439
;

-- 2026-02-01T11:51:10.545Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: Sales Invoice Report (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2026-02-01T11:51:39.343Z
UPDATE AD_Process_Trl SET Description='Exports sales invoice line details including product, customer, sales rep, delivery/invoice dates, quantities, and amounts. 
Filterable by invoice date and delivery date ranges.',Updated=TO_TIMESTAMP('2026-02-01 11:51:39.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585439
;

-- 2026-02-01T11:51:39.346Z
UPDATE AD_Process base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: Sales Invoice Report (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2026-02-01T11:51:52.694Z
UPDATE AD_Process_Trl SET Description='Exports sales invoice line details including product, customer, sales rep, delivery/invoice dates, quantities, and amounts. 
Filterable by invoice date and shipment date ranges.',Updated=TO_TIMESTAMP('2026-02-01 11:51:52.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585439
;

-- 2026-02-01T11:51:52.696Z
UPDATE AD_Process base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: Sales Invoice Report (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2026-02-01T11:52:00.728Z
UPDATE AD_Process_Trl SET Description='Exports sales invoice line details including product, customer, sales rep, delivery/invoice dates, quantities, and amounts. 
Filterable by invoice date and delivery date ranges.',Updated=TO_TIMESTAMP('2026-02-01 11:52:00.728000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585439
;

-- 2026-02-01T11:52:00.729Z
UPDATE AD_Process base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: Sales Invoice Report (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2026-02-01T11:52:32.024Z
DELETE FROM AD_Process_Trl WHERE AD_Language='fr_CH' AND AD_Process_ID=585439
;


-- Process: Sales Invoice Report (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2026-02-01T12:00:53.380Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Verkaufsrechnungs-Positionen (Excel)',Updated=TO_TIMESTAMP('2026-02-01 12:00:53.380000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585439
;

-- 2026-02-01T12:00:53.385Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Process: Sales Invoice Report (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2026-02-01T12:01:07.223Z
UPDATE AD_Process_Trl SET Description='Exportiert Verkaufsrechnungs-Positionsdetails inkl. Produkt, Kunde, Vertriebsmitarbeiter, Liefer-/Rechnungsdatum, Mengen und Beträge.
Filterbar nach Rechnungs- und Lieferdatum.',Updated=TO_TIMESTAMP('2026-02-01 12:01:07.223000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585439
;

-- 2026-02-01T12:01:07.224Z
UPDATE AD_Process base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-----------

-- Process: Sales Invoice Report (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2026-02-01T12:00:53.380Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Verkaufsrechnungs-Positionen (Excel)',Updated=TO_TIMESTAMP('2026-02-01 12:00:53.380000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585439
;

-- 2026-02-01T12:00:53.385Z
UPDATE AD_Process base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Process: Sales Invoice Report (Excel)(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2026-02-01T12:01:07.223Z
UPDATE AD_Process_Trl SET Description='Exportiert Verkaufsrechnungs-Positionsdetails inkl. Produkt, Kunde, Vertriebsmitarbeiter, Liefer-/Rechnungsdatum, Mengen und Beträge.
Filterbar nach Rechnungs- und Lieferdatum.',Updated=TO_TIMESTAMP('2026-02-01 12:01:07.223000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585439
;

-- 2026-02-01T12:01:07.224Z
UPDATE AD_Process base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Trl trl  WHERE trl.AD_Process_ID=base.AD_Process_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

