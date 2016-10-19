--
-- DDL
--
drop table IF EXISTS DLM_Partion_Config;
drop table IF EXISTS DLM_Partion_Config_Line;
drop table IF EXISTS DLM_Partion_Config_Reference;


-- 19.10.2016 10:27
-- URL zum Konzept
ALTER SEQUENCE DLM_Partion_Config_SEQ RENAME TO DLM_Partition_Config_SEQ
;


-- 19.10.2016 10:28
-- URL zum Konzept
CREATE TABLE DLM_Partition_Config (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description VARCHAR(2000) DEFAULT NULL , DLM_Partition_Config_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Name VARCHAR(255) DEFAULT NULL , Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT DLM_Partition_Config_Key PRIMARY KEY (DLM_Partition_Config_ID))
;

-- 19.10.2016 10:30
-- URL zum Konzept
ALTER SEQUENCE DLM_Partion_Config_Line_SEQ RENAME TO DLM_Partition_Config_Line_SEQ
;

-- 19.10.2016 10:30
-- URL zum Konzept
CREATE TABLE DLM_Partition_Config_Line (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description VARCHAR(2000) DEFAULT NULL , DLM_Partition_Config_ID NUMERIC(10) NOT NULL, DLM_Partition_Config_Line_ID NUMERIC(10) NOT NULL, DLM_Referencing_Table_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT DLM_Partition_Config_Line_Key PRIMARY KEY (DLM_Partition_Config_Line_ID))
;

-- 19.10.2016 10:31
-- URL zum Konzept
ALTER SEQUENCE DLM_Partion_Config_Reference_SEQ RENAME TO DLM_Partition_Config_Reference_SEQ
;

-- 19.10.2016 10:32
-- URL zum Konzept
CREATE TABLE DLM_Partition_Config_Reference (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DLM_Partition_Config_Line_ID NUMERIC(10) NOT NULL, DLM_Partition_Config_Reference_ID NUMERIC(10) NOT NULL, DLM_Referenced_Table_ID NUMERIC(10) NOT NULL, DLM_Referenced_Table_Partion_Config_Line_ID NUMERIC(10) DEFAULT NULL , DLM_Referencing_Column_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT DLM_Partition_Config_Reference_Key PRIMARY KEY (DLM_Partition_Config_Reference_ID))
;

-- 19.10.2016 10:35
-- URL zum Konzept
CREATE UNIQUE INDEX DLM_Partition_Config_UC_Name ON DLM_Partition_Config (AD_Client_ID,AD_Org_ID,Name) WHERE Isactive='Y'
;

-- 19.10.2016 10:36
-- URL zum Konzept
CREATE UNIQUE INDEX DLM_Partition_Config_Line_UC_table ON DLM_Partition_Config_Line (DLM_Partition_Config_ID,DLM_Referencing_Table_ID) WHERE IsActive='Y'
;

-- 19.10.2016 10:39
-- URL zum Konzept
CREATE UNIQUE INDEX DLM_Partition_Config_Reference_UC_reference ON DLM_Partition_Config_Reference (DLM_Partition_Config_Line_ID,DLM_Referenced_Table_ID,DLM_Referencing_Column_ID) WHERE IsActive='Y'
;


-- 19.10.2016 11:05
-- URL zum Konzept
ALTER TABLE DLM_Partition_Config_Reference ADD DLM_Referenced_Table_Partition_Config_Line_ID NUMERIC(10) DEFAULT NULL 
;

ALTER TABLE dlm_partition ADD COLUMN dlm_partition_config_id numeric(10,0);
ALTER TABLE dlm_partition ALTER COLUMN dlm_partition_config_id SET NOT NULL;


--
-- DML
--

-- 19.10.2016 10:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2016-10-19 10:10:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557311
;

-- 19.10.2016 10:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2016-10-19 10:10:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557312
;

-- 19.10.2016 10:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2016-10-19 10:10:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557313
;

-- 19.10.2016 10:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2016-10-19 10:10:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557315
;

-- 19.10.2016 10:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2016-10-19 10:10:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557311
;

-- 19.10.2016 10:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2016-10-19 10:10:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557312
;

-- 19.10.2016 10:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2016-10-19 10:10:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557313
;

-- 19.10.2016 10:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2016-10-19 10:10:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557315
;

-- 19.10.2016 10:10
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-10-19 10:10:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557312
;

-- 19.10.2016 10:10
-- URL zum Konzept
UPDATE AD_Field SET SpanX=2,Updated=TO_TIMESTAMP('2016-10-19 10:10:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557315
;

-- 19.10.2016 10:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2016-10-19 10:11:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557320
;

-- 19.10.2016 10:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2016-10-19 10:11:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557316
;

-- 19.10.2016 10:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2016-10-19 10:11:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557317
;

-- 19.10.2016 10:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2016-10-19 10:11:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557318
;

-- 19.10.2016 10:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2016-10-19 10:11:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557322
;

-- 19.10.2016 10:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2016-10-19 10:11:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557321
;

-- 19.10.2016 10:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-10-19 10:11:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557320
;

-- 19.10.2016 10:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2016-10-19 10:11:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557316
;

-- 19.10.2016 10:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2016-10-19 10:11:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557317
;

-- 19.10.2016 10:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2016-10-19 10:11:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557318
;

-- 19.10.2016 10:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2016-10-19 10:11:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557322
;

-- 19.10.2016 10:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2016-10-19 10:11:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557321
;

-- 19.10.2016 10:12
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-10-19 10:12:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557317
;

-- 19.10.2016 10:12
-- URL zum Konzept
UPDATE AD_Field SET SpanX=2,Updated=TO_TIMESTAMP('2016-10-19 10:12:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557321
;

-- 19.10.2016 10:13
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2016-10-19 10:13:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557323
;

-- 19.10.2016 10:13
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2016-10-19 10:13:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557324
;

-- 19.10.2016 10:13
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2016-10-19 10:13:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557327
;

-- 19.10.2016 10:13
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2016-10-19 10:13:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557325
;

-- 19.10.2016 10:13
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2016-10-19 10:13:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557329
;

-- 19.10.2016 10:13
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2016-10-19 10:13:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557328
;

-- 19.10.2016 10:13
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2016-10-19 10:13:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557330
;

-- 19.10.2016 10:13
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-10-19 10:13:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557323
;

-- 19.10.2016 10:13
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2016-10-19 10:13:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557324
;

-- 19.10.2016 10:13
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2016-10-19 10:13:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557327
;

-- 19.10.2016 10:13
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2016-10-19 10:13:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557325
;

-- 19.10.2016 10:13
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2016-10-19 10:13:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557328
;

-- 19.10.2016 10:13
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2016-10-19 10:13:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557329
;

-- 19.10.2016 10:13
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2016-10-19 10:13:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557330
;

-- 19.10.2016 10:14
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-10-19 10:14:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557325
;

-- 19.10.2016 10:14
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-10-19 10:14:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557328
;

-- 19.10.2016 10:14
-- URL zum Konzept
UPDATE AD_Table SET AccessLevel='7',Updated=TO_TIMESTAMP('2016-10-19 10:14:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540792
;

-- 19.10.2016 10:15
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2016-10-19 10:15:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555149
;

-- 19.10.2016 10:16
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18,Updated=TO_TIMESTAMP('2016-10-19 10:16:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555149
;

-- 19.10.2016 10:16
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=540314,Updated=TO_TIMESTAMP('2016-10-19 10:16:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540792
;

-- 19.10.2016 10:17
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=540314,Updated=TO_TIMESTAMP('2016-10-19 10:17:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540790
;

-- 19.10.2016 10:17
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=540314,Updated=TO_TIMESTAMP('2016-10-19 10:17:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540789
;

-- 19.10.2016 10:17
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=540315,Updated=TO_TIMESTAMP('2016-10-19 10:17:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540788
;

-- 19.10.2016 10:17
-- URL zum Konzept
UPDATE AD_Table SET AccessLevel='7',Updated=TO_TIMESTAMP('2016-10-19 10:17:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540790
;

-- 19.10.2016 10:17
-- URL zum Konzept
UPDATE AD_Table SET AccessLevel='7',Updated=TO_TIMESTAMP('2016-10-19 10:17:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540789
;

-- 19.10.2016 10:17
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2016-10-19 10:17:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557316
;

-- 19.10.2016 10:17
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2016-10-19 10:17:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557317
;

-- 19.10.2016 10:19
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=18,Updated=TO_TIMESTAMP('2016-10-19 10:19:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555151
;

-- 19.10.2016 10:20
-- URL zum Konzept
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2016-10-19 10:20:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540792
;

-- 19.10.2016 10:20
-- URL zum Konzept
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2016-10-19 10:20:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540790
;

-- 19.10.2016 10:20
-- URL zum Konzept
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2016-10-19 10:20:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540789
;

-- 19.10.2016 10:23
-- URL zum Konzept
UPDATE AD_Column SET IsAutocomplete='Y',Updated=TO_TIMESTAMP('2016-10-19 10:23:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555150
;


-- 19.10.2016 10:25
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555152,469,0,10,540789,'N','Name',TO_TIMESTAMP('2016-10-19 10:25:42','YYYY-MM-DD HH24:MI:SS'),100,'N','','Alphanumeric identifier of the entity','de.metas.dlm',255,'The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','N','Y','N','Name',1,TO_TIMESTAMP('2016-10-19 10:25:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 19.10.2016 10:25
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555152 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 19.10.2016 10:27
-- URL zum Konzept
UPDATE AD_Table SET Name='DLM_Partition_Config', TableName='DLM_Partition_Config',Updated=TO_TIMESTAMP('2016-10-19 10:27:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540789
;

-- 19.10.2016 10:27
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540789
;

-- 19.10.2016 10:27
-- URL zum Konzept
UPDATE AD_Sequence SET Name='DLM_Partition_Config',Updated=TO_TIMESTAMP('2016-10-19 10:27:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=554372
;


-- 19.10.2016 10:28
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='DLM_Partition_Config_ID', Name='DLM Partitionierungskonfiguration', PrintName='DLM Partitionierungskonfiguration',Updated=TO_TIMESTAMP('2016-10-19 10:28:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543191
;

-- 19.10.2016 10:28
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=543191
;

-- 19.10.2016 10:28
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='DLM_Partition_Config_ID', Name='DLM Partitionierungskonfiguration', Description=NULL, Help=NULL WHERE AD_Element_ID=543191
;

-- 19.10.2016 10:28
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DLM_Partition_Config_ID', Name='DLM Partitionierungskonfiguration', Description=NULL, Help=NULL, AD_Element_ID=543191 WHERE UPPER(ColumnName)='DLM_PARTITION_CONFIG_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 19.10.2016 10:28
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DLM_Partition_Config_ID', Name='DLM Partitionierungskonfiguration', Description=NULL, Help=NULL WHERE AD_Element_ID=543191 AND IsCentrallyMaintained='Y'
;

-- 19.10.2016 10:28
-- URL zum Konzept
UPDATE AD_Field SET Name='DLM Partitionierungskonfiguration', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543191) AND IsCentrallyMaintained='Y'
;

-- 19.10.2016 10:28
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='DLM Partitionierungskonfiguration', Name='DLM Partitionierungskonfiguration' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543191)
;

-- 19.10.2016 10:29
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2016-10-19 10:29:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555152
;

-- 19.10.2016 10:29
-- URL zum Konzept
INSERT INTO t_alter_column values('dlm_partition_config','Name','VARCHAR(255)',null,null)
;

-- 19.10.2016 10:29
-- URL zum Konzept
INSERT INTO t_alter_column values('dlm_partition_config','Name',null,'NOT NULL',null)
;

-- 19.10.2016 10:30
-- URL zum Konzept
UPDATE AD_Table SET Name='DLM_Partition_Config_Line', TableName='DLM_Partition_Config_Line',Updated=TO_TIMESTAMP('2016-10-19 10:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540790
;

-- 19.10.2016 10:30
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540790
;

-- 19.10.2016 10:30
-- URL zum Konzept
UPDATE AD_Sequence SET Name='DLM_Partition_Config_Line',Updated=TO_TIMESTAMP('2016-10-19 10:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=554373
;


-- 19.10.2016 10:30
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='DLM_Partition_Config_Line_ID', Name='DLM Partitionierungskonfigzeile', PrintName='DLM Partitionierungskonfigzeile',Updated=TO_TIMESTAMP('2016-10-19 10:30:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543192
;

-- 19.10.2016 10:30
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=543192
;

-- 19.10.2016 10:30
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='DLM_Partition_Config_Line_ID', Name='DLM Partitionierungskonfigzeile', Description=NULL, Help=NULL WHERE AD_Element_ID=543192
;

-- 19.10.2016 10:30
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DLM_Partition_Config_Line_ID', Name='DLM Partitionierungskonfigzeile', Description=NULL, Help=NULL, AD_Element_ID=543192 WHERE UPPER(ColumnName)='DLM_PARTITION_CONFIG_LINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 19.10.2016 10:30
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DLM_Partition_Config_Line_ID', Name='DLM Partitionierungskonfigzeile', Description=NULL, Help=NULL WHERE AD_Element_ID=543192 AND IsCentrallyMaintained='Y'
;

-- 19.10.2016 10:30
-- URL zum Konzept
UPDATE AD_Field SET Name='DLM Partitionierungskonfigzeile', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543192) AND IsCentrallyMaintained='Y'
;

-- 19.10.2016 10:30
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='DLM Partitionierungskonfigzeile', Name='DLM Partitionierungskonfigzeile' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543192)
;

-- 19.10.2016 10:31
-- URL zum Konzept
UPDATE AD_Table SET Name='DLM_Partition_Config_Reference', TableName='DLM_Partition_Config_Reference',Updated=TO_TIMESTAMP('2016-10-19 10:31:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540792
;

-- 19.10.2016 10:31
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540792
;

-- 19.10.2016 10:31
-- URL zum Konzept
UPDATE AD_Sequence SET Name='DLM_Partition_Config_Reference',Updated=TO_TIMESTAMP('2016-10-19 10:31:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=554374
;


-- 19.10.2016 10:32
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='DLM_Partition_Config_Reference_ID', Name='DLM Partitionierungkonfigurationsreferenz', PrintName='DLM Partitionierungkonfigurationsreferenz',Updated=TO_TIMESTAMP('2016-10-19 10:32:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543193
;

-- 19.10.2016 10:32
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=543193
;

-- 19.10.2016 10:32
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='DLM_Partition_Config_Reference_ID', Name='DLM Partitionierungkonfigurationsreferenz', Description=NULL, Help=NULL WHERE AD_Element_ID=543193
;

-- 19.10.2016 10:32
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DLM_Partition_Config_Reference_ID', Name='DLM Partitionierungkonfigurationsreferenz', Description=NULL, Help=NULL, AD_Element_ID=543193 WHERE UPPER(ColumnName)='DLM_PARTITION_CONFIG_REFERENCE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 19.10.2016 10:32
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DLM_Partition_Config_Reference_ID', Name='DLM Partitionierungkonfigurationsreferenz', Description=NULL, Help=NULL WHERE AD_Element_ID=543193 AND IsCentrallyMaintained='Y'
;

-- 19.10.2016 10:32
-- URL zum Konzept
UPDATE AD_Field SET Name='DLM Partitionierungkonfigurationsreferenz', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543193) AND IsCentrallyMaintained='Y'
;

-- 19.10.2016 10:32
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='DLM Partitionierungkonfigurationsreferenz', Name='DLM Partitionierungkonfigurationsreferenz' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543193)
;


-- 19.10.2016 10:34
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540387,0,540789,TO_TIMESTAMP('2016-10-19 10:34:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y','Y','DLM_Partition_Config_UC_Name','N',TO_TIMESTAMP('2016-10-19 10:34:16','YYYY-MM-DD HH24:MI:SS'),100,'Isactive=''Y''')
;

-- 19.10.2016 10:34
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540387 AND NOT EXISTS (SELECT * FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 19.10.2016 10:34
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,555119,540772,540387,0,TO_TIMESTAMP('2016-10-19 10:34:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y',10,TO_TIMESTAMP('2016-10-19 10:34:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.10.2016 10:34
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,555120,540773,540387,0,TO_TIMESTAMP('2016-10-19 10:34:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y',20,TO_TIMESTAMP('2016-10-19 10:34:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.10.2016 10:35
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,555152,540774,540387,0,TO_TIMESTAMP('2016-10-19 10:35:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y',30,TO_TIMESTAMP('2016-10-19 10:35:13','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 19.10.2016 10:35
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540388,0,540790,TO_TIMESTAMP('2016-10-19 10:35:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y','Y','DLM_Partition_Config_Line_UC_table','N',TO_TIMESTAMP('2016-10-19 10:35:54','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 19.10.2016 10:35
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540388 AND NOT EXISTS (SELECT * FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 19.10.2016 10:36
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,555137,540775,540388,0,TO_TIMESTAMP('2016-10-19 10:36:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y',10,TO_TIMESTAMP('2016-10-19 10:36:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.10.2016 10:36
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,555139,540776,540388,0,TO_TIMESTAMP('2016-10-19 10:36:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y',20,TO_TIMESTAMP('2016-10-19 10:36:28','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 19.10.2016 10:37
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540389,0,540792,TO_TIMESTAMP('2016-10-19 10:37:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y','Y','DLM_Partition_Config_Reference_UC_reference','N',TO_TIMESTAMP('2016-10-19 10:37:09','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 19.10.2016 10:37
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540389 AND NOT EXISTS (SELECT * FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 19.10.2016 10:37
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,555148,540777,540389,0,TO_TIMESTAMP('2016-10-19 10:37:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y',10,TO_TIMESTAMP('2016-10-19 10:37:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.10.2016 10:38
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,555150,540778,540389,0,TO_TIMESTAMP('2016-10-19 10:38:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y',20,TO_TIMESTAMP('2016-10-19 10:38:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.10.2016 10:38
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,555149,540779,540389,0,TO_TIMESTAMP('2016-10-19 10:38:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y',30,TO_TIMESTAMP('2016-10-19 10:38:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.10.2016 10:49
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555152,557336,0,540762,0,TO_TIMESTAMP('2016-10-19 10:49:33','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity',0,'U','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.',0,'Y','Y','Y','Y','N','N','N','N','N','Name',25,25,0,1,1,TO_TIMESTAMP('2016-10-19 10:49:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.10.2016 10:49
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557336 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 19.10.2016 10:49
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2016-10-19 10:49:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557313
;

-- 19.10.2016 10:53
-- URL zum Konzept
UPDATE AD_Val_Rule SET Code='DLM_Partition_Config_Line.DLM_Referencing_Table_ID=@DLM_Referenced_Table_ID/-1@', Name='DLM_Partition_Config_Line for DLM_Referenced_Table_ID',Updated=TO_TIMESTAMP('2016-10-19 10:53:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540350
;

-- 19.10.2016 10:53
-- URL zum Konzept
UPDATE AD_Reference SET Name='DLM_Partition_Config_Line',Updated=TO_TIMESTAMP('2016-10-19 10:53:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540695
;

-- 19.10.2016 10:53
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='N' WHERE AD_Reference_ID=540695
;

-- 19.10.2016 10:54
-- URL zum Konzept
UPDATE AD_Val_Rule SET EntityType='de.metas.dlm',Updated=TO_TIMESTAMP('2016-10-19 10:54:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540350
;

-- 19.10.2016 11:04
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='DLM_Referenced_Table_Partition_Config_Line_ID',Updated=TO_TIMESTAMP('2016-10-19 11:04:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543197
;

-- 19.10.2016 11:04
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='DLM_Referenced_Table_Partition_Config_Line_ID', Name='Konfig-Zeile zur referenzierten Tabelle', Description=NULL, Help=NULL WHERE AD_Element_ID=543197
;

-- 19.10.2016 11:04
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DLM_Referenced_Table_Partition_Config_Line_ID', Name='Konfig-Zeile zur referenzierten Tabelle', Description=NULL, Help=NULL, AD_Element_ID=543197 WHERE UPPER(ColumnName)='DLM_REFERENCED_TABLE_PARTITION_CONFIG_LINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 19.10.2016 11:04
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DLM_Referenced_Table_Partition_Config_Line_ID', Name='Konfig-Zeile zur referenzierten Tabelle', Description=NULL, Help=NULL WHERE AD_Element_ID=543197 AND IsCentrallyMaintained='Y'
;
