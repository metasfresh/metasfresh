-- Backfill: an earlier script in this folder created C_BP_PrintFormat.IsDropShip / .IsAutoPrint and
-- was amended in place to set PersonalDataCategory on creation, but a DB that already applied the
-- pre-amend version needs this follow-up to reach the same state (harmless no-op on a fresh apply).
-- Column: C_BP_PrintFormat.IsDropShip
-- Column: C_BP_PrintFormat.IsAutoPrint
-- 2026-09-01T12:30:00.000Z
UPDATE AD_Column SET PersonalDataCategory='NP', Updated=TO_TIMESTAMP('2026-09-01 12:30:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID IN (593458,593459) AND PersonalDataCategory IS NULL
;
