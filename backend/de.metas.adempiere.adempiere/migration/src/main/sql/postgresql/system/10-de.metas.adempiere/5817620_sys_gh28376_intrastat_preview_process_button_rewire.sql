-- Rewire the Intrastat preview window's in-window action:
--   OLD: AD_Table_Process (541656) wires AD_Process 585508 (INTRASTAT RTIC Datei (AT), with
--        parameter dialog + 10-column AT CSV) to AD_Table 542632 (Intrastat_Preview_V).
--   NEW: same AD_Table_Process row now points to AD_Process 585647 (INTRASTAT RTIC Datei (AT)
--        — Auswahl, parameterless, T_Selection-driven, 12-column CSV) — added by migration 5817610.
--
-- AD_Process 585508 stays alive and unchanged; it remains the sole implementation of the
-- fixed AT RTIC 10-column CSV, invoked only from AD_Menu 542261. This migration merely
-- flips the in-window attachment. Supersedes migration 5817020's wiring intent.
--
-- Idempotent: the WHERE guards on the old AD_Process_ID + AD_Table_ID mean a re-run after
-- the update is a no-op.

UPDATE AD_Table_Process
   SET AD_Process_ID = 585647,
       Updated       = TO_TIMESTAMP('2026-08-05 13:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy     = 100
 WHERE AD_Table_Process_ID = 541656
   AND AD_Process_ID       = 585508
   AND AD_Table_ID         = 542632;
