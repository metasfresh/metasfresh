-- AD_Process 585647: relocate Classname to the WebUI module.
--
-- The Java class was moved from de.metas.impexp.spreadsheet.process.intrastat (base module)
-- to de.metas.ui.web.impexp.intrastat.process (WebUI module) so it can extend
-- ViewBasedProcessTemplate — the base template that exposes the WebUI view + row-selection
-- context to the process. The old location was a plain JavaProcess subclass, so T_Selection
-- was never populated on invocation from the window → the process saw an empty selection
-- and produced an empty CSV.
--
-- Idempotent — the WHERE guard on the old Classname makes a re-run a no-op.

UPDATE AD_Process
   SET Classname = 'de.metas.ui.web.impexp.intrastat.process.Intrastat_ExportFromWindow',
       Updated   = TO_TIMESTAMP('2026-08-07 15:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_Process_ID = 585647
   AND Classname     = 'de.metas.impexp.spreadsheet.process.intrastat.Intrastat_ExportFromWindow';
