--
-- DDL
--
-- 09.11.2016 16:29:09
-- URL zum Konzept
ALTER TABLE DLM_Partition_Config ADD IsDefault CHAR(1) DEFAULT 'N' CHECK (IsDefault IN ('Y','N')) NOT NULL
;


-- 09.11.2016 16:33:38
-- URL zum Konzept
CREATE UNIQUE INDEX DLM_Partition_Config_UC_Default ON DLM_Partition_Config (IsDefault) WHERE IsActive='Y' AND IsDefault='Y'
;

-- 09.11.2016 16:33:38
-- URL zum Konzept
CREATE OR REPLACE FUNCTION DLM_Partition_Config_UC_Default_tgfn()
 RETURNS TRIGGER AS $DLM_Partition_Config_UC_Default_tg$
 BEGIN
 IF TG_OP='INSERT' THEN
UPDATE DLM_Partition_Config SET IsDefault='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND IsDefault=NEW.IsDefault AND DLM_Partition_Config_ID<>NEW.DLM_Partition_Config_ID AND IsActive='Y' AND IsDefault='Y';
 ELSE
IF OLD.IsDefault<>NEW.IsDefault THEN
UPDATE DLM_Partition_Config SET IsDefault='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND IsDefault=NEW.IsDefault AND DLM_Partition_Config_ID<>NEW.DLM_Partition_Config_ID AND IsActive='Y' AND IsDefault='Y';
 END IF;
 END IF;
 RETURN NEW;
 END;
 $DLM_Partition_Config_UC_Default_tg$ LANGUAGE plpgsql;
;

-- 09.11.2016 16:33:38
-- URL zum Konzept
DROP TRIGGER IF EXISTS DLM_Partition_Config_UC_Default_tg ON DLM_Partition_Config
;

-- 09.11.2016 16:33:38
-- URL zum Konzept
CREATE TRIGGER DLM_Partition_Config_UC_Default_tg BEFORE INSERT OR UPDATE  ON DLM_Partition_Config FOR EACH ROW EXECUTE PROCEDURE DLM_Partition_Config_UC_Default_tgfn()
;


-- 09.11.2016 16:39:32
-- URL zum Konzept
ALTER TABLE DLM_Partition ADD PartitionSize NUMERIC(10) DEFAULT NULL 
;


-- 09.11.2016 16:42:46
-- URL zum Konzept
CREATE TABLE DLM_Partition_Workqueue (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_Table_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DLM_Partition_ID NUMERIC(10) DEFAULT NULL , DLM_Partition_Workqueue_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Record_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ADTable_DLMPartitionWorkqueue FOREIGN KEY (AD_Table_ID) REFERENCES AD_Table DEFERRABLE INITIALLY DEFERRED
, CONSTRAINT DLMPartition_DLMPartitionWorkq FOREIGN KEY (DLM_Partition_ID) REFERENCES DLM_Partition DEFERRABLE INITIALLY DEFERRED
, CONSTRAINT DLM_Partition_Workqueue_Key PRIMARY KEY (DLM_Partition_Workqueue_ID))
;



COMMIT;

-- 09.11.2016 16:29:02
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555452,1103,0,20,540789,'N','IsDefault',TO_TIMESTAMP('2016-11-09 16:29:02','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Default value','de.metas.dlm',1,'The Default Checkbox indicates if this record will be used as a default value.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Standard',0,TO_TIMESTAMP('2016-11-09 16:29:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 09.11.2016 16:29:02
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555452 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 09.11.2016 16:29:53
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540393,0,540789,TO_TIMESTAMP('2016-11-09 16:29:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y','Y','DLM_Partition_Config_UC_Default','N',TO_TIMESTAMP('2016-11-09 16:29:53','YYYY-MM-DD HH24:MI:SS'),100,'ISActive=''Y''')
;

-- 09.11.2016 16:29:53
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540393 AND NOT EXISTS (SELECT * FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 09.11.2016 16:31:16
-- URL zum Konzept
UPDATE AD_Index_Table SET BeforeChangeCode='IsDefault=''N''', BeforeChangeCodeType='SQLS', BeforeChangeWarning='Es darf nur eine Standard-Konfiguration geben.',Updated=TO_TIMESTAMP('2016-11-09 16:31:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540393
;

-- 09.11.2016 16:31:52
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,555452,540786,540393,0,TO_TIMESTAMP('2016-11-09 16:31:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y',10,TO_TIMESTAMP('2016-11-09 16:31:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.11.2016 16:32:00
-- URL zum Konzept
UPDATE AD_Index_Table SET WhereClause='IsActive=''Y''',Updated=TO_TIMESTAMP('2016-11-09 16:32:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540393
;

-- 09.11.2016 16:32:06
-- URL zum Konzept
UPDATE AD_Index_Table SET BeforeChangeWarning='Es darf nur eine (aktive) Standard-Konfiguration geben.',Updated=TO_TIMESTAMP('2016-11-09 16:32:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540393
;

-- 09.11.2016 16:32:58
-- URL zum Konzept
UPDATE AD_Index_Table SET BeforeChangeWarning='', ErrorMsg='Es darf nur eine (aktive) Standard-Konfiguration geben.',Updated=TO_TIMESTAMP('2016-11-09 16:32:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540393
;

-- 09.11.2016 16:32:58
-- URL zum Konzept
UPDATE AD_Index_Table_Trl SET IsTranslated='N' WHERE AD_Index_Table_ID=540393
;

-- 09.11.2016 16:33:35
-- URL zum Konzept
UPDATE AD_Index_Table SET WhereClause='IsActive=''Y'' AND IsDefault=''Y''',Updated=TO_TIMESTAMP('2016-11-09 16:33:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540393
;

-- 09.11.2016 16:34:39
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555452,557413,0,540762,0,TO_TIMESTAMP('2016-11-09 16:34:39','YYYY-MM-DD HH24:MI:SS'),100,'Default value',0,'de.metas.dlm','The Default Checkbox indicates if this record will be used as a default value.',0,'Y','Y','Y','Y','N','N','N','N','N','Standard',35,35,0,1,1,TO_TIMESTAMP('2016-11-09 16:34:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.11.2016 16:34:39
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557413 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 09.11.2016 16:36:43
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543240,0,'IsCompletePartition',TO_TIMESTAMP('2016-11-09 16:36:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y','Partition is vollständig','Partition is vollständig',TO_TIMESTAMP('2016-11-09 16:36:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.11.2016 16:36:43
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543240 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 09.11.2016 16:36:50
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='IsPartitionComplete',Updated=TO_TIMESTAMP('2016-11-09 16:36:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543240
;

-- 09.11.2016 16:36:50
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsPartitionComplete', Name='Partition is vollständig', Description=NULL, Help=NULL WHERE AD_Element_ID=543240
;

-- 09.11.2016 16:36:51
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsPartitionComplete', Name='Partition is vollständig', Description=NULL, Help=NULL, AD_Element_ID=543240 WHERE UPPER(ColumnName)='ISPARTITIONCOMPLETE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 09.11.2016 16:36:51
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsPartitionComplete', Name='Partition is vollständig', Description=NULL, Help=NULL WHERE AD_Element_ID=543240 AND IsCentrallyMaintained='Y'
;

-- 09.11.2016 16:37:50
-- URL zum Konzept
UPDATE AD_Element SET Description='Sagt aus, ob das System dieser Partition noch weitere Datensätze hinzufügen muss bevor sie als Ganzes verschoben werden kann.',Updated=TO_TIMESTAMP('2016-11-09 16:37:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543240
;

-- 09.11.2016 16:37:50
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=543240
;

-- 09.11.2016 16:37:50
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsPartitionComplete', Name='Partition is vollständig', Description='Sagt aus, ob das System dieser Partition noch weitere Datensätze hinzufügen muss bevor sie als Ganzes verschoben werden kann.', Help=NULL WHERE AD_Element_ID=543240
;

-- 09.11.2016 16:37:50
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsPartitionComplete', Name='Partition is vollständig', Description='Sagt aus, ob das System dieser Partition noch weitere Datensätze hinzufügen muss bevor sie als Ganzes verschoben werden kann.', Help=NULL, AD_Element_ID=543240 WHERE UPPER(ColumnName)='ISPARTITIONCOMPLETE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 09.11.2016 16:37:50
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsPartitionComplete', Name='Partition is vollständig', Description='Sagt aus, ob das System dieser Partition noch weitere Datensätze hinzufügen muss bevor sie als Ganzes verschoben werden kann.', Help=NULL WHERE AD_Element_ID=543240 AND IsCentrallyMaintained='Y'
;

-- 09.11.2016 16:37:50
-- URL zum Konzept
UPDATE AD_Field SET Name='Partition is vollständig', Description='Sagt aus, ob das System dieser Partition noch weitere Datensätze hinzufügen muss bevor sie als Ganzes verschoben werden kann.', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543240) AND IsCentrallyMaintained='Y'
;

-- 09.11.2016 16:38:07
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555453,543240,0,20,540788,'N','IsPartitionComplete',TO_TIMESTAMP('2016-11-09 16:38:07','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Sagt aus, ob das System dieser Partition noch weitere Datensätze hinzufügen muss bevor sie als Ganzes verschoben werden kann.','de.metas.dlm',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Partition is vollständig',0,TO_TIMESTAMP('2016-11-09 16:38:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 09.11.2016 16:38:07
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555453 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 09.11.2016 16:38:11
-- URL zum Konzept
ALTER TABLE DLM_Partition ADD IsPartitionComplete CHAR(1) DEFAULT 'N' CHECK (IsPartitionComplete IN ('Y','N')) NOT NULL
;

-- 09.11.2016 16:39:11
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543241,0,'PartitionSize',TO_TIMESTAMP('2016-11-09 16:39:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y','Anz. zugeordneter Datensätze','Anz. zugeordneter Datensätze',TO_TIMESTAMP('2016-11-09 16:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.11.2016 16:39:11
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543241 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 09.11.2016 16:39:27
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555454,543241,0,11,540788,'N','PartitionSize',TO_TIMESTAMP('2016-11-09 16:39:27','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.dlm',14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Anz. zugeordneter Datensätze',0,TO_TIMESTAMP('2016-11-09 16:39:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 09.11.2016 16:39:27
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555454 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 09.11.2016 16:41:10
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,ReplicationType,TableName,Updated,UpdatedBy) VALUES ('7',0,0,0,540798,'N',TO_TIMESTAMP('2016-11-09 16:41:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','N','Y','N','N','Y','N','Y','N','N',0,'Partitionierungswarteschlange','L','DLM_Partition_Workqueue',TO_TIMESTAMP('2016-11-09 16:41:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.11.2016 16:41:10
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Table_ID=540798 AND NOT EXISTS (SELECT * FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 09.11.2016 16:41:10
-- URL zum Konzept
CREATE SEQUENCE DLM_PARTITION_WORKQUEUE_SEQ INCREMENT 1 MINVALUE 0 MAXVALUE 2147483647 START 1000000
;

-- 09.11.2016 16:41:10
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,554379,TO_TIMESTAMP('2016-11-09 16:41:10','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table DLM_Partition_Workqueue',1,'Y','N','Y','Y','DLM_Partition_Workqueue','N',1000000,TO_TIMESTAMP('2016-11-09 16:41:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.11.2016 16:41:21
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=540315,Updated=TO_TIMESTAMP('2016-11-09 16:41:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540798
;

-- 09.11.2016 16:41:30
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555455,102,0,19,540798,'AD_Client_ID',TO_TIMESTAMP('2016-11-09 16:41:29','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','de.metas.dlm',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2016-11-09 16:41:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 09.11.2016 16:41:30
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555455 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 09.11.2016 16:41:30
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555456,113,0,19,540798,'AD_Org_ID',TO_TIMESTAMP('2016-11-09 16:41:30','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','de.metas.dlm',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','Y','N','N','Y','N','N','Sektion',0,TO_TIMESTAMP('2016-11-09 16:41:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 09.11.2016 16:41:30
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555456 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 09.11.2016 16:41:30
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555457,245,0,16,540798,'Created',TO_TIMESTAMP('2016-11-09 16:41:30','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','de.metas.dlm',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2016-11-09 16:41:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 09.11.2016 16:41:30
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555457 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 09.11.2016 16:41:30
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555458,246,0,18,110,540798,'CreatedBy',TO_TIMESTAMP('2016-11-09 16:41:30','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','de.metas.dlm',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2016-11-09 16:41:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 09.11.2016 16:41:30
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555458 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 09.11.2016 16:41:30
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555459,348,0,20,540798,'IsActive',TO_TIMESTAMP('2016-11-09 16:41:30','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','de.metas.dlm',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2016-11-09 16:41:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 09.11.2016 16:41:30
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555459 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 09.11.2016 16:41:30
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555460,607,0,16,540798,'Updated',TO_TIMESTAMP('2016-11-09 16:41:30','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.dlm',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2016-11-09 16:41:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 09.11.2016 16:41:30
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555460 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 09.11.2016 16:41:30
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555461,608,0,18,110,540798,'UpdatedBy',TO_TIMESTAMP('2016-11-09 16:41:30','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','de.metas.dlm',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2016-11-09 16:41:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 09.11.2016 16:41:30
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555461 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 09.11.2016 16:41:30
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543242,0,'DLM_Partition_Workqueue_ID',TO_TIMESTAMP('2016-11-09 16:41:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y','Partitionierungswarteschlange','Partitionierungswarteschlange',TO_TIMESTAMP('2016-11-09 16:41:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.11.2016 16:41:30
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543242 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 09.11.2016 16:41:30
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555462,543242,0,13,540798,'DLM_Partition_Workqueue_ID',TO_TIMESTAMP('2016-11-09 16:41:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm',10,'Y','N','N','N','Y','Y','N','N','Y','N','N','Partitionierungswarteschlange',0,TO_TIMESTAMP('2016-11-09 16:41:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 09.11.2016 16:41:30
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555462 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 09.11.2016 16:41:52
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555463,543190,0,19,540798,'N','DLM_Partition_ID',TO_TIMESTAMP('2016-11-09 16:41:52','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.dlm',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Partition',0,TO_TIMESTAMP('2016-11-09 16:41:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 09.11.2016 16:41:52
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555463 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 09.11.2016 16:41:57
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2016-11-09 16:41:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555463
;

-- 09.11.2016 16:42:00
-- URL zum Konzept
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2016-11-09 16:42:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555463
;

-- 09.11.2016 16:42:18
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555464,126,0,19,540798,'N','AD_Table_ID',TO_TIMESTAMP('2016-11-09 16:42:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Database Table information','de.metas.dlm',10,'The Database Table provides the information of the table definition','Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','DB-Tabelle',0,TO_TIMESTAMP('2016-11-09 16:42:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 09.11.2016 16:42:18
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555464 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 09.11.2016 16:42:33
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555465,538,0,28,540798,'N','Record_ID',TO_TIMESTAMP('2016-11-09 16:42:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Direct internal record ID','de.metas.dlm',10,'The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Datensatz-ID',0,TO_TIMESTAMP('2016-11-09 16:42:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 09.11.2016 16:42:33
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555465 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
-- 09.11.2016 16:44:17
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555453,557414,0,540765,0,TO_TIMESTAMP('2016-11-09 16:44:17','YYYY-MM-DD HH24:MI:SS'),100,'Sagt aus, ob das System dieser Partition noch weitere Datensätze hinzufügen muss bevor sie als Ganzes verschoben werden kann.',0,'de.metas.dlm',0,'Y','Y','Y','Y','N','N','N','Y','N','Partition is vollständig',50,50,0,1,1,TO_TIMESTAMP('2016-11-09 16:44:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.11.2016 16:44:17
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557414 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 09.11.2016 16:44:34
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555454,557415,0,540765,0,TO_TIMESTAMP('2016-11-09 16:44:34','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.dlm',0,'Y','Y','Y','Y','N','N','N','Y','Y','Anz. zugeordneter Datensätze',55,55,0,1,1,TO_TIMESTAMP('2016-11-09 16:44:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.11.2016 16:44:34
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557415 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 09.11.2016 16:45:41
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,555463,0,540770,540798,540315,TO_TIMESTAMP('2016-11-09 16:45:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','N','N','Y','N','Y','N','N','N','N','Y','Y','N','Y','Y','N','N','N',0,'Warteschlange','N',30,0,TO_TIMESTAMP('2016-11-09 16:45:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.11.2016 16:45:41
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Tab_ID=540770 AND NOT EXISTS (SELECT * FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 09.11.2016 16:45:46
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555455,557416,0,540770,TO_TIMESTAMP('2016-11-09 16:45:46','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.dlm','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','Y','N','N','N','N','N','Mandant',TO_TIMESTAMP('2016-11-09 16:45:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.11.2016 16:45:46
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557416 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 09.11.2016 16:45:46
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555456,557417,0,540770,TO_TIMESTAMP('2016-11-09 16:45:46','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.dlm','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','Y','N','N','N','N','N','Sektion',TO_TIMESTAMP('2016-11-09 16:45:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.11.2016 16:45:46
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557417 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 09.11.2016 16:45:46
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555459,557418,0,540770,TO_TIMESTAMP('2016-11-09 16:45:46','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.dlm','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2016-11-09 16:45:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.11.2016 16:45:46
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557418 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 09.11.2016 16:45:46
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555462,557419,0,540770,TO_TIMESTAMP('2016-11-09 16:45:46','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.dlm','Y','Y','N','N','N','N','N','N','N','Partitionierungswarteschlange',TO_TIMESTAMP('2016-11-09 16:45:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.11.2016 16:45:46
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557419 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 09.11.2016 16:45:46
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555463,557420,0,540770,TO_TIMESTAMP('2016-11-09 16:45:46','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.dlm','Y','Y','Y','N','N','N','N','N','Partition',TO_TIMESTAMP('2016-11-09 16:45:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.11.2016 16:45:46
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557420 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 09.11.2016 16:45:46
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555464,557421,0,540770,TO_TIMESTAMP('2016-11-09 16:45:46','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information',10,'de.metas.dlm','The Database Table provides the information of the table definition','Y','Y','Y','N','N','N','N','N','DB-Tabelle',TO_TIMESTAMP('2016-11-09 16:45:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.11.2016 16:45:46
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557421 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 09.11.2016 16:45:47
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,555465,557422,0,540770,TO_TIMESTAMP('2016-11-09 16:45:46','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID',10,'de.metas.dlm','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','Y','Y','N','N','N','N','N','Datensatz-ID',TO_TIMESTAMP('2016-11-09 16:45:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.11.2016 16:45:47
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557422 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 09.11.2016 16:46:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2016-11-09 16:46:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557418
;

-- 09.11.2016 16:46:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2016-11-09 16:46:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557416
;

-- 09.11.2016 16:46:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2016-11-09 16:46:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557417
;

-- 09.11.2016 16:46:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2016-11-09 16:46:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557420
;

-- 09.11.2016 16:46:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2016-11-09 16:46:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557421
;

-- 09.11.2016 16:46:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2016-11-09 16:46:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557422
;

-- 09.11.2016 16:46:45
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2016-11-09 16:46:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557417
;

-- 09.11.2016 16:46:48
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=10,Updated=TO_TIMESTAMP('2016-11-09 16:46:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557416
;

-- 09.11.2016 16:46:53
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=30,Updated=TO_TIMESTAMP('2016-11-09 16:46:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557420
;

-- 09.11.2016 16:47:06
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2016-11-09 16:47:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557422
;

-- 09.11.2016 16:47:11
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2016-11-09 16:47:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557421
;

-- 09.11.2016 16:48:35
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnClass,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555466,587,0,10,540798,'N','(select t.Tablename from AD_Table t where t.AD_Table_ID=DLM_Partition_Workqueue.AD_Table_ID)','TableName',TO_TIMESTAMP('2016-11-09 16:48:34','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.dlm',50,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','Y','N','Name der DB-Tabelle',0,TO_TIMESTAMP('2016-11-09 16:48:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 09.11.2016 16:48:35
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555466 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 09.11.2016 16:49:16
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='N', SeqNo=60, SeqNoGrid=60,Updated=TO_TIMESTAMP('2016-11-09 16:49:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557422
;

-- 09.11.2016 16:49:39
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555466,557423,0,540770,0,TO_TIMESTAMP('2016-11-09 16:49:39','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.dlm',0,'Y','Y','Y','Y','N','N','N','N','Y','Name der DB-Tabelle',50,50,0,1,1,TO_TIMESTAMP('2016-11-09 16:49:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 09.11.2016 16:49:39
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557423 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 09.11.2016 18:57:40
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2016-11-09 18:57:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557415
;


-- 10.11.2016 12:16
-- URL zum Konzept
UPDATE AD_Process SET Name='Erstelle order erweitere Partionen',Updated=TO_TIMESTAMP('2016-11-10 12:16:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540728
;

-- 10.11.2016 12:16
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540728
;

-- 10.11.2016 12:16
-- URL zum Konzept
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Erstelle order erweitere Partionen',Updated=TO_TIMESTAMP('2016-11-10 12:16:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540732
;

-- 10.11.2016 12:16
-- URL zum Konzept
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=540732
;

-- 10.11.2016 12:16
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540728,540788,TO_TIMESTAMP('2016-11-10 12:16:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y',TO_TIMESTAMP('2016-11-10 12:16:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 10.11.2016 12:17
-- URL zum Konzept
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2016-11-10 12:17:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540770
;

-- 10.11.2016 12:17
-- URL zum Konzept
UPDATE AD_Column SET ColumnClass='(select t.TableName from AD_Table t where t.AD_Table_ID=DLM_Partition_Workqueue.AD_Table_ID)',Updated=TO_TIMESTAMP('2016-11-10 12:17:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555466
;

-- 10.11.2016 12:25
-- URL zum Konzept
UPDATE AD_Process_Para SET Description='Falls der Prozess zunächst selbst unpartitionierte Datensätze ermittelt, legt dieser Parameter fest, ob pro Tabelle mit der jeweils kleinsten oder größten ID begonnen werden soll. Der Parameter wird bei Aufruf aus dem Partitions-Fenster ignoriert.', Help='Wenn der DB-Paramter "metasfresh.DLM_Coalesce_Level" auf 2 eingestellt ist, macht es in der Regel Sinn, die neuesten Daten zuerst zu Partitionieren.',Updated=TO_TIMESTAMP('2016-11-10 12:25:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541044
;

-- 10.11.2016 12:25
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=541044
;

-- 10.11.2016 12:29
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp
	-- ,Statistic_Count,Statistic_Seconds
	,Type,Updated,UpdatedBy,Value)
VALUES ('7',0,0,540737,'Y','de.metas.dlm.partitioner.process.DLM_Partition_Destroy','N',TO_TIMESTAMP('2016-11-10 12:29:51','YYYY-MM-DD HH24:MI:SS'),100,'Entfernt alle dieser Partition zugeordneten Datensätze.','de.metas.dlm','Y','N','N','N','N','N','N','Y',0,'Partitions-Zuordnung aufheben','N','Y'
	-- ,0,0
	,'Java',TO_TIMESTAMP('2016-11-10 12:29:51','YYYY-MM-DD HH24:MI:SS'),100,'DLM_Partition_Destroy')
;

-- 10.11.2016 12:29
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540737 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 10.11.2016 12:30
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540737,540788,TO_TIMESTAMP('2016-11-10 12:30:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y',TO_TIMESTAMP('2016-11-10 12:30:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 10.11.2016 12:30
-- URL zum Konzept
UPDATE AD_Process SET Name='Auswahl: Ziel-DLM-Level der Partitionen überprüfen',Updated=TO_TIMESTAMP('2016-11-10 12:30:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540734
;

-- 10.11.2016 12:30
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540734
;

-- 10.11.2016 12:31
-- URL zum Konzept
UPDATE AD_Process SET Description='Migriert die aktuelle Auswahl (falls aus dem Fenster heraus gestartet) oder alle Partitionen (falls aus dem Menübaum oder Ablaufsteuerung gestartet) und aktuelisiert die jeweiligen IST DLM-LEvel entsprechend der Vorgaben aus den SOLL DLM-Leveln', Name='Auswahl: Partitionen migrieren',Updated=TO_TIMESTAMP('2016-11-10 12:31:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540735
;

-- 10.11.2016 12:31
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540735
;

-- 10.11.2016 13:39
-- URL zum Konzept
UPDATE AD_Column SET ColumnClass='', ColumnSQL='(select t.TableName from AD_Table t where t.AD_Table_ID=DLM_Partition_Workqueue.AD_Table_ID)', IsLazyLoading='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2016-11-10 13:39:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555466
;

-- 10.11.2016 14:13
-- URL zum Konzept
UPDATE AD_Process SET Name='Erstelle oder erweitere Partionen',Updated=TO_TIMESTAMP('2016-11-10 14:13:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540728
;

-- 10.11.2016 14:13
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540728
;

-- 10.11.2016 14:13
-- URL zum Konzept
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Erstelle oder erweitere Partionen',Updated=TO_TIMESTAMP('2016-11-10 14:13:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540732
;

-- 10.11.2016 14:13
-- URL zum Konzept
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=540732
;

-- 10.11.2016 14:13
-- URL zum Konzept
UPDATE AD_Process SET Name='Erstelle oder erweitere Partionen - asynchron',Updated=TO_TIMESTAMP('2016-11-10 14:13:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540733
;

-- 10.11.2016 14:13
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540733
;

-- 10.11.2016 14:13
-- URL zum Konzept
UPDATE AD_Menu SET Description='Erzeugt ein Arbeitspaket zur asynchronen Verarbeitung. Wenn das Arbeitspaket verarbeitet ist, wird abhängig von den Prozessparametern ein weiteres Arbeitspaket erstellt usw.', IsActive='Y', Name='Erstelle oder erweitere Partionen - asynchron',Updated=TO_TIMESTAMP('2016-11-10 14:13:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540733
;

-- 10.11.2016 14:13
-- URL zum Konzept
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=540733
;

