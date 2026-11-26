-- Manual Cost Adjustment: lines of the M_CostRevaluationLine tab are added by the quick-input only.
-- The generic "Add new" record form would ask for the Product, which is read-only on the line, so it could
-- never be saved. 'Q' = Quick Input Only: the WebUI opens the batch entry by itself and shows no "Add new" button.
UPDATE AD_Tab SET IncludedTabNewRecordInputMode='Q', Updated=TO_TIMESTAMP('2026-09-29 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Tab_ID=546465;
