-- Intrastat window — new parameterless AD_Process for the in-window selection-driven CSV export.
-- Reads the user's grid selection via T_Selection and writes them to CSV (no header row, matching
-- the AT RTIC-file convention) with the 10 AT RTIC columns. Column list + number formats +
-- direction-conditional logic match report.Intrastat_Export exactly; only the row source
-- differs (T_Selection vs. year/period parameters). No AD_Process_Para rows: parameterless.
--
-- Attached to AD_Table 542587 (Intrastat_Report_Detail_V) via AD_Table_Process — see the
-- companion migration in this batch.
--
-- The existing AD_Process 585508 (INTRASTAT RTIC Datei (AT)) is unchanged — it remains wired
-- to AD_Menu 542261 with its parameter dialog + fixed 10-column AT RTIC CSV.
--
-- Java class: de.metas.ui.web.impexp.intrastat.process.Intrastat_ExportFromWindow

-- =====================================================================
-- AD_Process — parameterless, Java-driven, CSV export (no header row)
-- =====================================================================
INSERT INTO AD_Process (AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID,
    AllowProcessReRun, Classname, CopyFromProcess,
    Created, CreatedBy, Description, EntityType,
    IsActive, IsApplySecuritySettings, IsBetaFunctionality, IsDirectPrint,
    IsFormatExcelFile, IsIncludeCSVHeaderRow, IsLogWarning, IsNotifyUserAfterExecution, IsOneInstanceOnly,
    IsReport, IsTranslateExcelHeaders, IsUpdateExportDate, IsUseBPartnerLanguage,
    LockWaitTimeout, Name, PostgrestResponseFormat,
    RefreshAllAfterExecution, ShowHelp, SpreadsheetFormat,
    CSVFieldDelimiter, Type, Updated, UpdatedBy, Value)
VALUES ('3', 0, 0, 585647 /*From ID Server*/,
    'Y', 'de.metas.ui.web.impexp.intrastat.process.Intrastat_ExportFromWindow', 'N',
    TO_TIMESTAMP('2026-08-06 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Exportiert die im Fenster ausgewählten Zeilen (bzw. bei fehlender Auswahl den gefilterten Satz) als CSV-Datei ohne Kopfzeile im INTRASTAT-RTIC-Format (identisch zum Report Intrastat_Export).',
    'D',
    'Y', 'N', 'N', 'N',
    'N', 'N', 'N', 'N', 'N',
    'Y', 'N', 'Y', 'N',
    0, 'INTRASTAT RTIC Datei (AT) — Auswahl', 'json',
    'N', 'N', 'xls',
    E'\t', 'Java', TO_TIMESTAMP('2026-08-06 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Intrastat_Export_FromWindow');
-- Note: Type='Java' — this is a custom JavaProcess subclass. Type='Excel' would cause the AD_Process
-- interceptor (setClassnameForProcessType) to overwrite Classname with ExportToSpreadsheetProcess
-- whenever the record is saved through the WebUI/PO path, wiping our custom Classname. The output
-- file format (CSV, no header, delimiter) is controlled by the Java class via JdbcCSVExporter.

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

-- en_US
UPDATE AD_Process_Trl
   SET IsTranslated = 'Y',
       Name         = 'INTRASTAT RTIC File (AT) — Selection',
       Description  = 'Exports the rows selected in the window (or the filtered set when no row is checked) as a CSV file without header row, in the INTRASTAT RTIC format (identical to the Intrastat_Export report).',
       Updated      = TO_TIMESTAMP('2026-08-06 09:00:01', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
 WHERE AD_Language = 'en_US' AND AD_Process_ID = 585647;

-- de_CH: Swiss convention Maßeinheit → Masseinheit (ß → ss)
UPDATE AD_Process_Trl
   SET IsTranslated = 'Y',
       Description  = 'Exportiert die im Fenster ausgewählten Zeilen (bzw. bei fehlender Auswahl den gefilterten Satz) als CSV-Datei ohne Kopfzeile im INTRASTAT-RTIC-Format (identisch zum Report Intrastat_Export).',
       Updated      = TO_TIMESTAMP('2026-08-06 09:00:01', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
 WHERE AD_Language = 'de_CH' AND AD_Process_ID = 585647;

-- de_DE: base-language row — mark as translated for data consistency
UPDATE AD_Process_Trl
   SET IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-08-06 09:00:01', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
 WHERE AD_Language = 'de_DE' AND AD_Process_ID = 585647;
