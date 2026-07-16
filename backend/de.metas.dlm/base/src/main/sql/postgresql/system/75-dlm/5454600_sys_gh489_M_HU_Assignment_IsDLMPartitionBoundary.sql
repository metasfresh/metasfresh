-- 15.12.2016 16:53
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2016-12-15 16:53:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550378
;

-- 15.12.2016 16:53
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2016-12-15 16:53:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551483
;

-- 15.12.2016 16:53
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2016-12-15 16:53:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551484
;

-- 15.12.2016 16:53
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2016-12-15 16:53:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551756
;
COMMIT;


select dlm.recreate_dlm_triggers(tableName) from AD_Table WHERE TableName='m_hu_assignment' AND IsDLM='Y';
