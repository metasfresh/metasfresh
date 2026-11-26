-- Normalize existing C_BP_PrintFormat.DocumentCopies_Override: an override of 0 and NULL both mean
-- "no override -> use the doc-type default", so collapse the ambiguous 0 to NULL. This aligns
-- existing rows with the now-nullable column (its '0' DB default was dropped) and the model
-- interceptor that keeps new/changed rows at NULL instead of 0. Behaviour-neutral: copy resolution
-- treats 0 and NULL identically. Positive overrides (>0) are left untouched.
-- 2026-09-02T09:00:00.000Z
SELECT backup_table('c_bp_printformat', '_31721_copies_0_to_null');

UPDATE C_BP_PrintFormat SET DocumentCopies_Override=NULL, Updated=TO_TIMESTAMP('2026-09-02 09:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99 WHERE DocumentCopies_Override=0
;
