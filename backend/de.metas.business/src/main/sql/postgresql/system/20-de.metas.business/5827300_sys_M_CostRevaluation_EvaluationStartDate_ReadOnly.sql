-- Kosten Neubewertung: the evaluation start date is read-only, because it always equals the accounting date.
-- Only a revaluation that copies the cost from another cost element keeps it editable, since there it is the cut-off date.
-- Field 702155 (tab 546464) keeps IsReadOnly='N', so this column logic applies.

UPDATE AD_Column SET ReadOnlyLogic='@RevaluationSource@!''CopyFromCostElement''', Updated=TO_TIMESTAMP('2026-10-01 12:20:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=583761 -- M_CostRevaluation.EvaluationStartDate
;
