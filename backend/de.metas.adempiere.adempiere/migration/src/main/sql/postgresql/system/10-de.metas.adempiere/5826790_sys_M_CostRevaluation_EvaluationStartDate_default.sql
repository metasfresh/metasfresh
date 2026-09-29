-- Kosten Neubewertung (M_CostRevaluation): default EvaluationStartDate to the posting date (like DateAcct),
-- so a new header is saveable without typing it; the model interceptor still defaults it on non-UI writes.

UPDATE AD_Column
   SET DefaultValue='@#Date@',
       Updated=TO_TIMESTAMP('2026-09-28 12:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy=100
 WHERE AD_Column_ID=583761;  -- M_CostRevaluation.EvaluationStartDate
