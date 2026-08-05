-- Intrastat preview window — new parameterless AD_Process for the in-window export.
-- Reads the user's grid selection via T_Selection and writes them to CSV with the extended
-- column set (10 AT RTIC columns + UOM + Currency). No AD_Process_Para rows: parameterless.
--
-- The existing AD_Process 585508 (INTRASTAT RTIC Datei (AT)) is unchanged — it remains wired
-- to AD_Menu 542261 with its parameter dialog + fixed 10-column AT RTIC CSV.
--
-- Rewiring of AD_Table_Process (removing 585508 from Intrastat_Preview_V, attaching this new
-- process instead) is the next migration (Task 19).
--
-- Java class: de.metas.impexp.spreadsheet.process.intrastat.Intrastat_ExportFromWindow
-- (added by Task 18 in the same branch).

-- =====================================================================
-- 1. AD_Element for the process caption
-- =====================================================================
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName,
    Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 585154 /*From ID Server*/, 0, 'Intrastat_Export_FromWindow',
    TO_TIMESTAMP('2026-08-05 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'D', 'Y', 'INTRASTAT RTIC Datei (AT) — Auswahl', 'INTRASTAT RTIC Datei (AT) — Auswahl',
    TO_TIMESTAMP('2026-08-05 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100);

-- Seed _Trl skeleton for every system language
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID,
    CommitWarning, Description, Help, Name,
    PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName,
    WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb,
    IsTranslated, AD_Client_ID, AD_Org_ID,
    Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID,
    t.CommitWarning, t.Description, t.Help, t.Name,
    t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName,
    t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb,
    'N', t.AD_Client_ID, t.AD_Org_ID,
    t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585154
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- en_US translation
UPDATE AD_Element_Trl
   SET IsTranslated = 'Y',
       Name         = 'INTRASTAT RTIC File (AT) — Selection',
       PrintName    = 'INTRASTAT RTIC File (AT) — Selection',
       Updated      = TO_TIMESTAMP('2026-08-05 12:00:01', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
 WHERE AD_Element_ID = 585154 AND AD_Language = 'en_US';

-- de_CH mirrors de_DE for this label (no ß in Name/PrintName); mark translated
UPDATE AD_Element_Trl
   SET IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-08-05 12:00:01', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
 WHERE AD_Element_ID = 585154 AND AD_Language = 'de_CH';

-- =====================================================================
-- 2. AD_Process — parameterless, Java-driven, CSV export
-- =====================================================================
INSERT INTO AD_Process (AccessLevel, AD_Client_ID, AD_Element_ID, AD_Org_ID, AD_Process_ID,
    AllowProcessReRun, Classname, CopyFromProcess,
    Created, CreatedBy, Description, EntityType,
    IsActive, IsApplySecuritySettings, IsBetaFunctionality, IsDirectPrint,
    IsFormatExcelFile, IsLogWarning, IsNotifyUserAfterExecution, IsOneInstanceOnly,
    IsReport, IsTranslateExcelHeaders, IsUpdateExportDate, IsUseBPartnerLanguage,
    LockWaitTimeout, Name, PostgrestResponseFormat,
    RefreshAllAfterExecution, ShowHelp, SpreadsheetFormat,
    CSVFieldDelimiter, Type, Updated, UpdatedBy, Value)
VALUES ('3', 0, 585154, 0, 585647 /*From ID Server*/,
    'Y', 'de.metas.impexp.spreadsheet.process.intrastat.Intrastat_ExportFromWindow', 'N',
    TO_TIMESTAMP('2026-08-05 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Exportiert die im Fenster ausgewählten Zeilen (bzw. bei fehlender Auswahl den gefilterten Satz) als CSV im INTRASTAT-RTIC-Format, erweitert um die Spalten Maßeinheit und Währung.',
    'D',
    'Y', 'N', 'N', 'N',
    'Y', 'N', 'N', 'N',
    'Y', 'N', 'Y', 'N',
    0, 'INTRASTAT RTIC Datei (AT) — Auswahl', 'json',
    'N', 'N', 'xls',
    '	', 'Excel', TO_TIMESTAMP('2026-08-05 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Intrastat_Export_FromWindow');

-- Seed _Trl skeleton
INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID,
    Description, Help, Name, IsTranslated,
    AD_Client_ID, AD_Org_ID,
    Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_ID,
    t.Description, t.Help, t.Name, 'N',
    t.AD_Client_ID, t.AD_Org_ID,
    t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_ID = 585647
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID);

-- en_US translation
UPDATE AD_Process_Trl
   SET IsTranslated = 'Y',
       Name         = 'INTRASTAT RTIC File (AT) — Selection',
       Description  = 'Exports the rows selected in the window (or the filtered set when no row is checked) as CSV in the INTRASTAT RTIC format, extended with the UOM and Currency columns.',
       Updated      = TO_TIMESTAMP('2026-08-05 12:00:03', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
 WHERE AD_Language = 'en_US' AND AD_Process_ID = 585647;

-- de_CH: Swiss convention Maßeinheit → Masseinheit (ß → ss)
UPDATE AD_Process_Trl
   SET IsTranslated = 'Y',
       Description  = 'Exportiert die im Fenster ausgewählten Zeilen (bzw. bei fehlender Auswahl den gefilterten Satz) als CSV im INTRASTAT-RTIC-Format, erweitert um die Spalten Masseinheit und Währung.',
       Updated      = TO_TIMESTAMP('2026-08-05 12:00:03', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
 WHERE AD_Language = 'de_CH' AND AD_Process_ID = 585647;
