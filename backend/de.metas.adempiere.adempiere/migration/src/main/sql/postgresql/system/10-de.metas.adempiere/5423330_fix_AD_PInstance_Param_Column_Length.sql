--
-- avoids truncation of e.g. whereclause process parameters
--
-- 05.08.2015 09:19
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_ID=36, FieldLength=999999,Updated=TO_TIMESTAMP('2015-08-05 09:19:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540459
;

-- 05.08.2015 09:19
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_ID=36, FieldLength=999999,Updated=TO_TIMESTAMP('2015-08-05 09:19:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540475
;

-- 05.08.2015 11:00
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=999999,Updated=TO_TIMESTAMP('2015-08-05 11:00:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2791
;

-- 05.08.2015 11:00
-- URL zum Konzept
INSERT INTO t_alter_column values('ad_pinstance_para','P_String','VARCHAR(999999)',null,'NULL')
;

-- 05.08.2015 11:00
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=999999,Updated=TO_TIMESTAMP('2015-08-05 11:00:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2792
;

-- 05.08.2015 11:01
-- URL zum Konzept
INSERT INTO t_alter_column values('ad_pinstance_para','P_String_To','VARCHAR(999999)',null,'NULL')
;

