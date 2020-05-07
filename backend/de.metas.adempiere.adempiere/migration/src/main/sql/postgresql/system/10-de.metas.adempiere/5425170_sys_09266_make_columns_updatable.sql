

-- C_InvoiceLine.M_InOutLine_ID: we need to unlink the iol on certain reversal cases; note that the fileds are read-only
-- 31.08.2015 15:27
-- URL zum Konzept
UPDATE AD_Column SET IsAlwaysUpdateable='Y', IsUpdateable='Y',Updated=TO_TIMESTAMP('2015-08-31 15:27:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=4251
;

---- MatchPO.C_InvoiceLine_ID: we need to unlink the il when a receipt is reactivated
-- 31.08.2015 15:41
-- URL zum Konzept
UPDATE AD_Column SET IsAlwaysUpdateable='Y', IsUpdateable='Y',Updated=TO_TIMESTAMP('2015-08-31 15:41:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6770
;

---- MatchPO.M_InOutLine: we need to unlink the iol on certain invoice reversal cases
-- 31.08.2015 18:16
-- URL zum Konzept
UPDATE AD_Column SET IsAlwaysUpdateable='Y', IsUpdateable='Y',Updated=TO_TIMESTAMP('2015-08-31 18:16:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6521
;

