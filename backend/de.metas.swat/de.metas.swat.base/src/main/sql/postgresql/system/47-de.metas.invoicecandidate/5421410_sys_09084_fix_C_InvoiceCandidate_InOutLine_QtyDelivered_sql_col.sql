
--C_InvoiceCandidate_InOutLine.QtyDelivered
-- 09.07.2015 09:32
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(SELECT iol.MovementQty FROM M_InOutLine iol WHERE iol.M_InOutLine_ID = C_InvoiceCandidate_InOutLine.M_InOutLine_ID)',Updated=TO_TIMESTAMP('2015-07-09 09:32:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551808
;

