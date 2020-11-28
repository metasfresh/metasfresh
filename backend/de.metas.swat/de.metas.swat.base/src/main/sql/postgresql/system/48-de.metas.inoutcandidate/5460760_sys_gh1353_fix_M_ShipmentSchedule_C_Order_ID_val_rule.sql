--
-- https://github.com/metasfresh/metasfresh/issues/1353
-- update AD_Reference_Value_ID for M_ShipmentSchedule.C_Order_ID so that it fitlers by IsSOTrx='Y'.
--

-- 2017-04-25T21:48:48.872
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_Value_ID=540100,Updated=TO_TIMESTAMP('2017-04-25 21:48:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=500224
;

