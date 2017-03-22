--
-- make DLM_Partition.DLM_ParttionerConfig_ID updatable
--
-- 07.11.2016 12:43
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2016-11-07 12:43:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555128
;

--
-- columnn name fix: DLM_Partition_Records_ID => DLM_Partition_Record_ID
--
-- 07.11.2016 12:44
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='DLM_Partition_Record_ID', Name='DLM_Partition_Record', PrintName='DLM_Partition_Record',Updated=TO_TIMESTAMP('2016-11-07 12:44:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543202
;

-- 07.11.2016 12:44
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=543202
;

-- 07.11.2016 12:44
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='DLM_Partition_Record_ID', Name='DLM_Partition_Record', Description=NULL, Help=NULL WHERE AD_Element_ID=543202
;

-- 07.11.2016 12:44
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DLM_Partition_Record_ID', Name='DLM_Partition_Record', Description=NULL, Help=NULL, AD_Element_ID=543202 WHERE UPPER(ColumnName)='DLM_PARTITION_RECORD_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 07.11.2016 12:44
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DLM_Partition_Record_ID', Name='DLM_Partition_Record', Description=NULL, Help=NULL WHERE AD_Element_ID=543202 AND IsCentrallyMaintained='Y'
;

-- 07.11.2016 12:44
-- URL zum Konzept
UPDATE AD_Field SET Name='DLM_Partition_Record', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543202) AND IsCentrallyMaintained='Y'
;

-- 07.11.2016 12:44
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='DLM_Partition_Record', Name='DLM_Partition_Record' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543202)
;

