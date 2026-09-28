-- Kosten Neubewertung (M_CostRevaluation): default the mandatory EvaluationStartDate to the posting
-- date on a new header. Without a default the field renders empty in the WebUI and its mandatory-field
-- check blocks saving the header (the line tab reports allowCreateNew=false) until the user types the
-- date by hand. Mirrors how DateAcct itself defaults (@#Date@), so on a new record both arrive as
-- today's posting date and the header is saveable with no manual entry.
--
-- Forward-only semantics stay owned by the M_CostRevaluation model interceptor (beforeNew), which sets
-- EvaluationStartDate = DateAcct whenever it arrives null on a non-UI write path (REST/import, where
-- the @#Date@ context is absent). This default only pre-fills the interactive UI; the interceptor
-- remains the authority, and a user may still pick an earlier date for a retrospective revaluation.
--
-- @#Date@ is an application-context default (not a physical DB default), so this is an AD_Column-only
-- change and no I_*/X_* model regeneration is needed (DefaultValue is not part of the generated model).

UPDATE AD_Column
   SET DefaultValue='@#Date@',
       Updated=TO_TIMESTAMP('2026-09-28 12:00:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy=100
 WHERE AD_Column_ID=583761;  -- M_CostRevaluation.EvaluationStartDate
