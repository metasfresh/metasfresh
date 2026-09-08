-- Give PP_Order.HasCostDifference (AD_Column 593510) a chosen rather than incidental position in the
-- default filter box, which is sorted by AD_Column.SelectionColumnSeqNo. 10 puts it ahead of every
-- field of the tab that carries an explicit SeqNo.
--
-- SelectionColumnSeqNo is an AD_Column-level (shared) attribute, safe here only because tab 549352
-- holds the only AD_Field for column 593510 - keep it that way.

-- 2026-09-08T00:00:00.000Z
UPDATE AD_Column SET SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2026-09-08 00:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=593510
;
