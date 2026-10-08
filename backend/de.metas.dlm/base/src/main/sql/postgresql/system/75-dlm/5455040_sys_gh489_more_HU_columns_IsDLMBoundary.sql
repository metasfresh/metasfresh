--
-- DML
--
-- 03.01.2017 08:57
-- URL zum Konzept
-- "PP_Order_BOMLine"
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2017-01-03 08:57:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550972
;

-- 03.01.2017 13:12
-- URL zum Konzept
-- M_Package_HU
UPDATE AD_Column SET IsDLMPartitionBoundary='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2017-01-03 13:12:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549973
;



-- 04.01.2017 12:33
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2017-01-04 12:33:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549951
;

-- 04.01.2017 12:34
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2017-01-04 12:34:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549958
;

-- 04.01.2017 12:35
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2017-01-04 12:35:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550672
;

-- 04.01.2017 12:35
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2017-01-04 12:35:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551755
;

-- 04.01.2017 12:36
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2017-01-04 12:36:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552594
;

-- 04.01.2017 12:36
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2017-01-04 12:36:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552808
;

-- 04.01.2017 12:38
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2017-01-04 12:38:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550971
;

-- 04.01.2017 12:46
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2017-01-04 12:46:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551319
;

--
-- DDL
--
COMMIT;
SELECT TableName, dlm.drop_dlm_triggers(TableName) FROM AD_Table WHERE IsDLM='Y';
COMMIT;
SELECT TableName, dlm.create_dlm_triggers(TableName) FROM AD_Table WHERE IsDLM='Y';

