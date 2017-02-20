
-- 06.01.2017 09:17
-- URL zum Konzept
UPDATE AD_Element SET Description='Falls ja, dann gehören Datensätze, die über die jeweilige Referenz verknüpft sind nicht zur selben Partition. Nach eine Änderung an eine für DLM eingerichteten Tabelle bitte "select dlm.recreate_dlm_triggers(''tablename'')" ausführen.',Updated=TO_TIMESTAMP('2017-01-06 09:17:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543253
;

-- 06.01.2017 09:17
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=543253
;

-- 06.01.2017 09:17
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsDLMPartitionBoundary', Name='Partitionsgrenze', Description='Falls ja, dann gehören Datensatze, die über die jeweilige Referenz verknüpft sind nicht zur selben Partition. Nach eine Änderung an eine für DLM eingerichteten Tabelle bitte "select dlm.recreate_dlm_triggers(''tablename'')" ausführen.', Help='If you change this value for a DLM''ed table then please run  "select dlm.recreate_dlm_triggers(''tablename'')" to update the DLM triggers' WHERE AD_Element_ID=543253
;

-- 06.01.2017 09:17
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsDLMPartitionBoundary', Name='Partitionsgrenze', Description='Falls ja, dann gehören Datensatze, die über die jeweilige Referenz verknüpft sind nicht zur selben Partition. Nach eine Änderung an eine für DLM eingerichteten Tabelle bitte "select dlm.recreate_dlm_triggers(''tablename'')" ausführen.', Help='If you change this value for a DLM''ed table then please run  "select dlm.recreate_dlm_triggers(''tablename'')" to update the DLM triggers', AD_Element_ID=543253 WHERE UPPER(ColumnName)='ISDLMPARTITIONBOUNDARY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 06.01.2017 09:17
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsDLMPartitionBoundary', Name='Partitionsgrenze', Description='Falls ja, dann gehören Datensatze, die über die jeweilige Referenz verknüpft sind nicht zur selben Partition. Nach eine Änderung an eine für DLM eingerichteten Tabelle bitte "select dlm.recreate_dlm_triggers(''tablename'')" ausführen.', Help='If you change this value for a DLM''ed table then please run  "select dlm.recreate_dlm_triggers(''tablename'')" to update the DLM triggers' WHERE AD_Element_ID=543253 AND IsCentrallyMaintained='Y'
;

-- 06.01.2017 09:17
-- URL zum Konzept
UPDATE AD_Field SET Name='Partitionsgrenze', Description='Falls ja, dann gehören Datensätze, die über die jeweilige Referenz verknüpft sind nicht zur selben Partition. Nach einer Änderung einer Referenz auf eine bereits für DLM eingerichteten Tabelle bitte "select dlm.recreate_dlm_triggers(''Ziel-TableName'')" ausführen.', Help='If you change this value for a DLM''ed table then please run  "select dlm.recreate_dlm_triggers(''tablename'')" to update the DLM triggers' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543253) AND IsCentrallyMaintained='Y'
;
