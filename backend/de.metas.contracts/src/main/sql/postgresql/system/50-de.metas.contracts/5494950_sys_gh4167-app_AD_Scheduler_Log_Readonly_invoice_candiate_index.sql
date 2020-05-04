--
-- make the tab of AD_Scheduler_Log readonly
-- 2018-06-01T13:59:35.736
-- URL zum Konzept
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-06-01 13:59:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=591
;

CREATE INDEX IF NOT EXISTS c_invoice_candidate_dateordered
   ON public.c_invoice_candidate (dateordered ASC NULLS LAST);
COMMENT ON INDEX public.c_invoice_candidate_dateordered
  IS 'this index is requiered to speed up de.metas.contracts.impl.FlatrateDAO.updateCandidates()';
