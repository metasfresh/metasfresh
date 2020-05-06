
--
--
---- 30.10.2015 09:18
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(select M_InOut_ID from M_InOutLine iol where iol.M_InOutLine_ID = M_ReceiptSchedule_Alloc.M_InOutLine_ID)',Updated=TO_TIMESTAMP('2015-10-30 09:18:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549609
;

-- 30.10.2015 09:19
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(select QualityDiscountPercent from M_InOutLine iol where iol.M_InOutLine_ID = M_ReceiptSchedule_Alloc.M_InOutLine_ID)',Updated=TO_TIMESTAMP('2015-10-30 09:19:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550581
;

-- 30.10.2015 09:19
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(select QualityNote from M_InOutLine iol where iol.M_InOutLine_ID = M_ReceiptSchedule_Alloc.M_InOutLine_ID)',Updated=TO_TIMESTAMP('2015-10-30 09:19:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550582
;
