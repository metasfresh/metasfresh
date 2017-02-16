

-- I_Asset=>M_AttributeSetInstance
-- 06.01.2017 09:20
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2017-01-06 09:20:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=56043
;

-- M_Storage=>M_AttributeSetInstance
-- 06.01.2017 09:15
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2017-01-06 09:15:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8762
;

select dlm.recreate_dlm_triggers('M_AttributeSetInstance');
