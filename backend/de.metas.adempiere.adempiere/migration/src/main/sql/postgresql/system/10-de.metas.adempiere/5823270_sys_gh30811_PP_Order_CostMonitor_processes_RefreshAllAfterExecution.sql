-- Both cost-monitor actions change the view they run from. RefreshAllAfterExecution is the declarative
-- way to say so (ProcessInfo -> execution result -> ADProcessPostProcessService), hence SQL, not Java.
-- It re-reads the EXISTING selection, it does NOT rebuild it: a closed order keeps its row in a
-- DocStatus='CO'-scoped view until the selection itself is recreated.

UPDATE AD_Process SET RefreshAllAfterExecution='Y',
       Updated=TO_TIMESTAMP('2026-09-09 09:10:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Process_ID IN (585649 /*PP_Order_PostCalculation*/, 585671 /*PP_Order_CloseSelection*/);
