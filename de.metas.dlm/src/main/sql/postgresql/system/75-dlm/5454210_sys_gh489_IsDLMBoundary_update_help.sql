

-- 09.12.2016 14:48
-- URL zum Konzept
UPDATE AD_Element SET Help='If you change this value for a DLM''ed table then please run  "select dlm.recreate_dlm_triggers(''<tablename>'')" to update the DLM triggers',Updated=TO_TIMESTAMP('2016-12-09 14:48:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543253
;

-- 09.12.2016 14:48
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=543253
;

-- 09.12.2016 14:48
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsDLMPartitionBoundary', Name='Partitionsgrenze', Description='Falls ja, dann gehören Datensatze, die über die jeweilige Referenz verknüpft sind nicht zur selben Partition.', Help='If you change this value for a DLM''ed table then please run  "select dlm.recreate_dlm_triggers(''<tablename>'')" to update the DLM triggers' WHERE AD_Element_ID=543253
;

-- 09.12.2016 14:48
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsDLMPartitionBoundary', Name='Partitionsgrenze', Description='Falls ja, dann gehören Datensatze, die über die jeweilige Referenz verknüpft sind nicht zur selben Partition.', Help='If you change this value for a DLM''ed table then please run  "select dlm.recreate_dlm_triggers(''<tablename>'')" to update the DLM triggers', AD_Element_ID=543253 WHERE UPPER(ColumnName)='ISDLMPARTITIONBOUNDARY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 09.12.2016 14:48
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsDLMPartitionBoundary', Name='Partitionsgrenze', Description='Falls ja, dann gehören Datensatze, die über die jeweilige Referenz verknüpft sind nicht zur selben Partition.', Help='If you change this value for a DLM''ed table then please run  "select dlm.recreate_dlm_triggers(''<tablename>'')" to update the DLM triggers' WHERE AD_Element_ID=543253 AND IsCentrallyMaintained='Y'
;

-- 09.12.2016 14:48
-- URL zum Konzept
UPDATE AD_Field SET Name='Partitionsgrenze', Description='Falls ja, dann gehören Datensatze, die über die jeweilige Referenz verknüpft sind nicht zur selben Partition.', Help='If you change this value for a DLM''ed table then please run  "select dlm.recreate_dlm_triggers(''<tablename>'')" to update the DLM triggers' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543253) AND IsCentrallyMaintained='Y'
;

