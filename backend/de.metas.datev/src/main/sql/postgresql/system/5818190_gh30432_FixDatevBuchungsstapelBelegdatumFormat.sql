-- DATEV Buchungsstapel (EXTF) export: column J "Belegdatum" must be the shortened DATEV date
-- ddMM, not the full date dd.MM.yyyy. Script 5800892 seeded this column
-- (DATEV_ExportFormatColumn_ID=540059) as ddMM, then overwrote it to dd.MM.yyyy in the same
-- script. Reset the shipped system default back to ddMM.
-- Guarded so an already hand-corrected / deliberately customised value is not clobbered.

SELECT backup_table('datev_exportformatcolumn', '_gh30432_belegdatum_ddmm');

UPDATE DATEV_ExportFormatColumn
   SET FormatPattern = 'ddMM',
       Updated       = TO_TIMESTAMP('2026-08-11 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy     = 100
 WHERE DATEV_ExportFormatColumn_ID = 540059
   AND FormatPattern = 'dd.MM.yyyy';
