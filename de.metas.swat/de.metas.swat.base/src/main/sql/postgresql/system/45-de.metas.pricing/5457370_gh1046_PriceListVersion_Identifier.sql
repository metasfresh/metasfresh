-- 01.03.2017 14:31
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543288,0,'testRC1Mar',TO_TIMESTAMP('2017-03-01 14:31:48','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','testRC1Mar','testRC1Mar',TO_TIMESTAMP('2017-03-01 14:31:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 01.03.2017 14:31
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543288 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 01.03.2017 14:32
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,ReplicationType,TableName,Updated,UpdatedBy) VALUES ('3',0,0,0,540803,'N',TO_TIMESTAMP('2017-03-01 14:32:36','YYYY-MM-DD HH24:MI:SS'),100,'U','N','Y','N','N','Y','N','N','N','N',0,'testRC1Mar','L','testRC1Mar',TO_TIMESTAMP('2017-03-01 14:32:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 01.03.2017 14:32
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Table_ID=540803 AND NOT EXISTS (SELECT * FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 01.03.2017 14:32
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,554383,TO_TIMESTAMP('2017-03-01 14:32:36','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table testRC1Mar',1,'Y','N','Y','Y','testRC1Mar','N',1000000,TO_TIMESTAMP('2017-03-01 14:32:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 01.03.2017 14:39
-- URL zum Konzept
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-03-01 14:39:54','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='testRC1MarTrans' WHERE AD_Element_ID=543288 AND AD_Language='it_CH'
;

-- 01.03.2017 14:40
-- URL zum Konzept
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-03-01 14:40:30','YYYY-MM-DD HH24:MI:SS'),Name='testRC1MarTransl' WHERE AD_Element_ID=543288 AND AD_Language='it_CH'
;

-- 01.03.2017 14:45
-- URL zum Konzept
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-03-01 14:45:03','YYYY-MM-DD HH24:MI:SS'),Name='testRC1MarTrans' WHERE AD_Element_ID=543288 AND AD_Language='it_CH'
;

-- 01.03.2017 14:46
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='testRC1Mar1',Updated=TO_TIMESTAMP('2017-03-01 14:46:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543288
;

-- 01.03.2017 14:46
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='testRC1Mar1', Name='testRC1Mar', Description=NULL, Help=NULL WHERE AD_Element_ID=543288
;

-- 01.03.2017 14:46
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='testRC1Mar1', Name='testRC1Mar', Description=NULL, Help=NULL, AD_Element_ID=543288 WHERE UPPER(ColumnName)='TESTRC1MAR1' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 01.03.2017 14:46
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='testRC1Mar1', Name='testRC1Mar', Description=NULL, Help=NULL WHERE AD_Element_ID=543288 AND IsCentrallyMaintained='Y'
;

-- 01.03.2017 14:46
-- URL zum Konzept
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-03-01 14:46:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=543288 AND AD_Language='fr_CH'
;

-- 01.03.2017 14:48
-- URL zum Konzept
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-03-01 14:48:50','YYYY-MM-DD HH24:MI:SS'),Name='testRC1Martra' WHERE AD_Element_ID=543288 AND AD_Language='fr_CH'
;

-- 01.03.2017 14:51
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='testRC1Mar',Updated=TO_TIMESTAMP('2017-03-01 14:51:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543288
;

-- 01.03.2017 14:51
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='testRC1Mar', Name='testRC1Mar', Description=NULL, Help=NULL WHERE AD_Element_ID=543288
;

-- 01.03.2017 14:51
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='testRC1Mar', Name='testRC1Mar', Description=NULL, Help=NULL, AD_Element_ID=543288 WHERE UPPER(ColumnName)='TESTRC1MAR' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 01.03.2017 14:51
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='testRC1Mar', Name='testRC1Mar', Description=NULL, Help=NULL WHERE AD_Element_ID=543288 AND IsCentrallyMaintained='Y'
;

-- 01.03.2017 14:52
-- URL zum Konzept
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-03-01 14:52:07','YYYY-MM-DD HH24:MI:SS'),Name='testRC1Martran' WHERE AD_Element_ID=543288 AND AD_Language='fr_CH'
;

-- 01.03.2017 14:52
-- URL zum Konzept
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-03-01 14:52:55','YYYY-MM-DD HH24:MI:SS'),Name='testRC1Martrans' WHERE AD_Element_ID=543288 AND AD_Language='fr_CH'
;

-- 01.03.2017 16:32
-- URL zum Konzept
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=3,Updated=TO_TIMESTAMP('2017-03-01 16:32:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2110
;

-- 01.03.2017 16:32
-- URL zum Konzept
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=2,Updated=TO_TIMESTAMP('2017-03-01 16:32:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2998
;

-- 01.03.2017 16:33
-- URL zum Konzept
UPDATE AD_Column SET IsIdentifier='N',Updated=TO_TIMESTAMP('2017-03-01 16:33:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2995
;

-- 01.03.2017 16:56
-- URL zum Konzept
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=1,Updated=TO_TIMESTAMP('2017-03-01 16:56:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2997
;

