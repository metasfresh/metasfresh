-- #30984 Manual Cost Adjustment: make the M_CostRevaluationLine tab insertable so the
-- WebUI quick-input surfaces on it. AllowQuickInput was already 'Y'; only IsInsertRecord
-- needed to flip.
UPDATE AD_Tab SET IsInsertRecord='Y', Updated=TO_TIMESTAMP('2026-09-28 09:58:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Tab_ID=546465;
