--
-- remove the AD_Process record for EDI_Desadv_Create_From_C_Order which still exists on some systems and causes error messages in the log.
--
-- 2017-06-19T06:51:13.198
-- URL zum Konzept
DELETE FROM AD_Table_Process WHERE AD_Process_ID=540721 AND AD_Table_ID=259
;

-- 2017-06-19T06:51:20.261
-- URL zum Konzept
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=540721
;

-- 2017-06-19T06:51:20.266
-- URL zum Konzept
DELETE FROM AD_Process WHERE AD_Process_ID=540721
;

