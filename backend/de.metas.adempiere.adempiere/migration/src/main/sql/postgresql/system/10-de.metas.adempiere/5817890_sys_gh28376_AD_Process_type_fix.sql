-- AD_Process 585647: revert Type + SpreadsheetFormat to the framework-recognised values.
--
-- The prior state (Type='CSV', SpreadsheetFormat='csv') broke framework dispatch at process
-- launch — ProcessType is a ReferenceListAwareEnum whose valid codes are:
--   Java, SQL, POSTGREST, JasperReportsJSON, JasperReportsSQL, Excel, RelationTypeInOverlay.
-- There is no 'CSV' value; ProcessType.ofCode('CSV') threw
--   "No ProcessType found for code: CSV"
-- at ProcessInfo build time.
--
-- The actual output format (CSV, no header, tab-delimited) is controlled entirely by the Java
-- class Intrastat_ExportFromWindow via JdbcCSVExporter — same pattern as AD_Process 585508
-- (INTRASTAT RTIC Datei (AT)), which is also Type='Excel'/'xls' but writes tab-delimited CSV.

UPDATE AD_Process
   SET Type              = 'Excel',
       SpreadsheetFormat = 'xls',
       Updated           = TO_TIMESTAMP('2026-08-07 09:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy         = 100
 WHERE AD_Process_ID = 585647
   AND (Type = 'CSV' OR SpreadsheetFormat = 'csv');
