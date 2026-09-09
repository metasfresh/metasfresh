-- Both cost-monitor actions end with their order closed, so both change the view they were run from,
-- yet neither told the WebUI so: after execution the view was left as it was.
--
-- RefreshAllAfterExecution is the declarative form of that request - ProcessInfo copies the column into
-- the execution result and ADProcessPostProcessService invalidates the view (or, when the action ran
-- from a single document rather than a view, that document). No process code is involved, which is why
-- the flag is set here rather than in Java.
--
-- It re-reads the rows of the view's EXISTING selection; it does not re-create that selection, so a
-- closed order keeps its row in a DocStatus='CO'-scoped view until the view itself is rebuilt.

UPDATE AD_Process SET RefreshAllAfterExecution='Y',
       Updated=TO_TIMESTAMP('2026-09-09 09:10:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Process_ID IN (585649 /*PP_Order_PostCalculation*/, 585671 /*PP_Order_CloseSelection*/);
