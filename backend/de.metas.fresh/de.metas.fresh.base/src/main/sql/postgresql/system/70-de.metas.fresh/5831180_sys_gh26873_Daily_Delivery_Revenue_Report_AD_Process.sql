-- Daily Delivery Revenue report: AD_Process + parameters + translations + menu + table-process.
-- Function DDL registered by 5831170_sys_gh26873_Daily_Delivery_Revenue_Report_Process.sql.
-- Cloned-and-adapted from AD_Process 585601 (Daily Revenue, registered by
-- 5795490_sys_gh27544_C_Order_Daily_Revenue_Report_Process.sql) -- that process/script is untouched.

-- AD_Process: base row (German in base columns, English via AD_Process_Trl below)
INSERT INTO AD_Process (
    AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID, AllowProcessReRun, Classname, CopyFromProcess,
    Created, CreatedBy, Description, EntityType, IsActive, IsApplySecuritySettings, IsBetaFunctionality,
    IsDirectPrint, IsFormatExcelFile, IsLogWarning, IsNotifyUserAfterExecution, IsOneInstanceOnly, IsReport,
    IsTranslateExcelHeaders, IsUpdateExportDate, IsUseBPartnerLanguage, JasperReport, LockWaitTimeout, Name,
    PostgrestResponseFormat, RefreshAllAfterExecution, ShowHelp, SpreadsheetFormat, Type, Updated, UpdatedBy, Value
) VALUES (
    '3', 0, 0, 585689 /*From ID Server*/, 'Y', 'de.metas.report.jasper.client.process.JasperReportStarter', 'N',
    TO_TIMESTAMP('2026-10-22 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Tagesübersicht des Netto-Lieferumsatzes: Wert der an diesem Tag ausgelieferten Ware, bereits fakturiert oder noch als Rechnungskandidat offen.',
    'D', 'Y', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'Y', 'Y', 'N', 'Y',
    '@PREFIX@de/metas/docs/sales/daily_delivery_revenue/report.jasper', 0, 'Tagesumsatz (auf Basis Lieferung)',
    'json', 'N', 'N', 'xls', 'JasperReportsSQL',
    TO_TIMESTAMP('2026-10-22 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Docs_Daily_Delivery_Revenue_Report'
)
;

-- AD_Process_Trl: skeleton rows for all active + base languages
INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_ID = 585689
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID)
;

-- AD_Process_Trl: en_US override (Name / Description / Help)
UPDATE AD_Process_Trl SET
    Name = 'Daily Revenue (based on Delivery)',
    Description = 'Daily overview of net delivery revenue: the value of goods shipped out that day, whether already invoiced or still an open invoice candidate.',
    Help = 'Shows one row per day, sorted newest first. For every completed sales delivery (document status Completed/Closed) dated that day, the value of each delivery line is: the net amount of the invoice already created for it, plus -- for the quantity not yet invoiced -- the net amount of its open invoice candidate. This way a day''s total includes goods that have shipped but have not yet been billed. Customer returns reduce the return day''s total; they never change the original delivery''s value. Amounts are shown in the accounting currency. Optional filters: date range, business partner, and business partner group -- leave empty to include everything.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-10-22 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Process_ID = 585689
;

-- AD_Process_Trl: de_DE / de_CH override (same text as base -- marks them actively translated)
UPDATE AD_Process_Trl SET
    Name = 'Tagesumsatz (auf Basis Lieferung)',
    Description = 'Tagesübersicht des Netto-Lieferumsatzes: Wert der an diesem Tag ausgelieferten Ware, bereits fakturiert oder noch als Rechnungskandidat offen.',
    Help = 'Zeigt eine Zeile pro Tag, neueste zuerst. Für jede abgeschlossene Verkaufslieferung (Belegstatus Vollständig/Geschlossen) dieses Tages ergibt sich der Wert jeder Lieferzeile aus: dem Nettobetrag der dafür bereits erstellten Rechnung, zuzüglich -- für die noch nicht fakturierte Menge -- dem Nettobetrag des offenen Rechnungskandidaten. So enthält die Tagessumme auch bereits ausgelieferte, aber noch nicht fakturierte Ware. Kundenretouren mindern die Summe des Retouren-Tages, ohne den Wert der ursprünglichen Lieferung zu verändern. Die Beträge werden in der Hauswährung angezeigt. Optionale Filter: Zeitraum, Geschäftspartner und Geschäftspartnergruppe -- leer lassen, um alles einzuschließen.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-10-22 10:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Process_ID = 585689
;

-- AD_Process is self-owned (no AD_Element_ID) -- sync the base row from the base-language (de_DE) _Trl row
UPDATE AD_Process base SET
    Name = trl.Name, Description = trl.Description, Help = trl.Help, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Process_Trl trl
WHERE trl.AD_Process_ID = base.AD_Process_ID AND trl.AD_Language = 'de_DE' AND trl.AD_Language = getBaseLanguage()
  AND base.AD_Process_ID = 585689
;

-- AD_Process_Para: DateFrom (reuses AD_Element 1581)
INSERT INTO AD_Process_Para (
    AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID, ColumnName,
    Created, CreatedBy, Description, EntityType, FieldLength, Help, IsActive, IsAutocomplete, IsCentrallyMaintained,
    IsEncrypted, IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy
) VALUES (
    0, 1581, 0, 585689, 543356 /*From ID Server*/, 15, 'DateFrom',
    TO_TIMESTAMP('2026-10-22 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Startdatum eines Abschnittes', 'D', 0, 'Datum von bezeichnet das Startdatum eines Abschnittes',
    'Y', 'N', 'Y', 'N', 'N', 'N', 'Datum von', 10,
    TO_TIMESTAMP('2026-10-22 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100
)
;

INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_Para_ID = 543356
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

-- AD_Process_Para: DateTo (reuses AD_Element 1582)
INSERT INTO AD_Process_Para (
    AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID, ColumnName,
    Created, CreatedBy, Description, EntityType, FieldLength, Help, IsActive, IsAutocomplete, IsCentrallyMaintained,
    IsEncrypted, IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy
) VALUES (
    0, 1582, 0, 585689, 543357 /*From ID Server*/, 15, 'DateTo',
    TO_TIMESTAMP('2026-10-22 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Enddatum eines Abschnittes', 'D', 0, 'Datum bis bezeichnet das Enddatum eines Abschnittes (inklusiv)',
    'Y', 'N', 'Y', 'N', 'N', 'N', 'Datum bis', 20,
    TO_TIMESTAMP('2026-10-22 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100
)
;

INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_Para_ID = 543357
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

-- AD_Process_Para: C_BPartner_ID (reuses AD_Element 187; AD_Reference_Value_ID=138 = C_BPartner search, per clone's final shape)
INSERT INTO AD_Process_Para (
    AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID, AD_Reference_Value_ID, ColumnName,
    Created, CreatedBy, Description, EntityType, FieldLength, Help, IsActive, IsAutocomplete, IsCentrallyMaintained,
    IsEncrypted, IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy
) VALUES (
    0, 187, 0, 585689, 543354 /*From ID Server*/, 30, 138, 'C_BPartner_ID',
    TO_TIMESTAMP('2026-10-22 10:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Bezeichnet einen Geschäftspartner', 'D', 0,
    'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',
    'Y', 'N', 'Y', 'N', 'N', 'N', 'Geschäftspartner', 30,
    TO_TIMESTAMP('2026-10-22 10:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100
)
;

INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_Para_ID = 543354
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

-- AD_Process_Para: C_BP_Group_ID (reuses AD_Element 1383; plain FK lookup, no AD_Reference_Value_ID, per clone's final shape)
INSERT INTO AD_Process_Para (
    AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID, AD_Process_Para_ID, AD_Reference_ID, ColumnName,
    Created, CreatedBy, Description, EntityType, FieldLength, Help, IsActive, IsAutocomplete, IsCentrallyMaintained,
    IsEncrypted, IsMandatory, IsRange, Name, SeqNo, Updated, UpdatedBy
) VALUES (
    0, 1383, 0, 585689, 543355 /*From ID Server*/, 30, 'C_BP_Group_ID',
    TO_TIMESTAMP('2026-10-22 10:04:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Geschäftspartnergruppe', 'D', 0,
    'Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.',
    'Y', 'N', 'Y', 'N', 'N', 'N', 'Geschäftspartnergruppe', 40,
    TO_TIMESTAMP('2026-10-22 10:04:00', 'YYYY-MM-DD HH24:MI:SS'), 100
)
;

INSERT INTO AD_Process_Para_Trl (AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_Para_ID = 543355
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID)
;

-- AD_Table_Process: attach the report process to M_InOut (AD_Table_ID=319), per plan AC1/AC6 menu+process wiring
-- (the clone-base process 585601 attaches to C_Order via AD_Table_Process 541635 -- this report's source data
-- is the delivery document, so it is attached to M_InOut instead).
INSERT INTO AD_Table_Process (
    AD_Client_ID, AD_Org_ID, AD_Process_ID, AD_Table_ID, AD_Table_Process_ID, Created, CreatedBy, EntityType, IsActive,
    Updated, UpdatedBy, WEBUI_DocumentAction, WEBUI_IncludedTabTopAction, WEBUI_ViewAction, WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default
) VALUES (
    0, 0, 585689, 319, 541701 /*From ID Server*/,
    TO_TIMESTAMP('2026-10-22 10:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y',
    TO_TIMESTAMP('2026-10-22 10:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'Y', 'N', 'N'
)
;

-- AD_Element: dedicated menu-caption element (NOT reusing a sibling's "Umsatzreport" element --
-- that text names a different report; sharing it would mean our caption gets silently overwritten
-- whenever that element is next propagated).
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 585551 /*From ID Server*/, 0, 'Docs_Daily_Delivery_Revenue_Report', TO_TIMESTAMP('2026-10-22 10:05:30', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.fresh', 'Y', 'Tagesumsatz (auf Basis Lieferung)', 'Tagesumsatz (auf Basis Lieferung)', TO_TIMESTAMP('2026-10-22 10:05:30', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Description, Help, Name, PrintName, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Description, t.Help, t.Name, t.PrintName, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585551
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET
    Name = 'Daily Revenue (based on Delivery)', PrintName = 'Daily Revenue (based on Delivery)', IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-10-22 10:05:40', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585551 AND AD_Language = 'en_US'
;

UPDATE AD_Element_Trl SET
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-22 10:05:50', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585551 AND AD_Language IN ('de_DE', 'de_CH')
;

-- AD_Menu: placed under the same "Berichte" (Reports) folder as the sibling revenue reports (Node_ID 1000048)
INSERT INTO AD_Menu (
    AD_Client_ID, AD_Org_ID, AD_Menu_ID, AD_Process_ID, AD_Element_ID, Action, Created, CreatedBy, Description,
    EntityType, InternalName, IsActive, IsReadOnly, IsSOTrx, IsSummary, Name, Updated, UpdatedBy
) VALUES (
    0, 0, 542370 /*From ID Server*/, 585689, 585551, 'R',
    TO_TIMESTAMP('2026-10-22 10:06:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Tagesübersicht des Netto-Lieferumsatzes: Wert der an diesem Tag ausgelieferten Ware, bereits fakturiert oder noch als Rechnungskandidat offen.',
    'de.metas.fresh', 'Docs_Daily_Delivery_Revenue_Report', 'Y', 'N', 'N', 'N', 'Tagesumsatz (auf Basis Lieferung)',
    TO_TIMESTAMP('2026-10-22 10:06:00', 'YYYY-MM-DD HH24:MI:SS'), 100
)
;

-- Element-driven sync so the menu row's Name/Description are fed from the element's own translations
-- (the propagation function also mirrors the base-language text onto AD_Menu directly).
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585551, 'en_US');
;

INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, AD_Tree_ID, Created, CreatedBy, IsActive, Node_ID, Parent_ID, SeqNo, Updated, UpdatedBy)
SELECT 0, 0, t.AD_Tree_ID, TO_TIMESTAMP('2026-10-22 10:07:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 542370, 1000048, 19,
       TO_TIMESTAMP('2026-10-22 10:07:00', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Tree t
WHERE t.AD_Client_ID = 0 AND t.IsActive = 'Y' AND t.IsAllNodes = 'Y' AND t.AD_Table_ID = 116
;
