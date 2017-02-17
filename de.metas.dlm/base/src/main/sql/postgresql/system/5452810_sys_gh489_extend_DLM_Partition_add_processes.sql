--
-- DDL to add the columns Target_DLM_Level, Current_DLM_LEvel and DateNextInspection to the table DLM_Partition
--
-- 31.10.2016 07:21
-- URL zum Konzept
ALTER TABLE DLM_Partition ADD Current_DLM_Level NUMERIC(10) DEFAULT NULL 
;
-- 31.10.2016 07:22
-- URL zum Konzept
ALTER TABLE DLM_Partition ADD Target_DLM_Level NUMERIC(10) DEFAULT NULL 
;
-- 31.10.2016 08:14
-- URL zum Konzept
ALTER TABLE DLM_Partition ADD DateNextInspection TIMESTAMP WITH TIME ZONE DEFAULT NULL 
;

COMMIT;

--
-- DML to add the columns Target_DLM_Level, Current_DLM_LEvel and DateNextInspection to the table DLM_Partition
--
-- 31.10.2016 07:02
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543220,0,'Target_DLM_Level',TO_TIMESTAMP('2016-10-31 07:02:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y','Ziel-DLM-Level','Ziel-DLM-Level',TO_TIMESTAMP('2016-10-31 07:02:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 31.10.2016 07:02
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543220 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 31.10.2016 07:03
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543221,0,'Current_DLM_Level',TO_TIMESTAMP('2016-10-31 07:03:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y','aktuelles DLM-Level','aktuelles DLM-Level',TO_TIMESTAMP('2016-10-31 07:03:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 31.10.2016 07:03
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543221 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 31.10.2016 07:03
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555366,543221,0,11,540788,'N','Current_DLM_Level',TO_TIMESTAMP('2016-10-31 07:03:48','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.dlm',14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','aktuelles DLM-Level',0,TO_TIMESTAMP('2016-10-31 07:03:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 31.10.2016 07:03
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555366 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 31.10.2016 07:21
-- URL zum Konzept
UPDATE AD_Element SET Name='aktueller DLM-Level', PrintName='aktueller DLM-Level',Updated=TO_TIMESTAMP('2016-10-31 07:21:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543221
;

-- 31.10.2016 07:21
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=543221
;

-- 31.10.2016 07:21
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Current_DLM_Level', Name='aktueller DLM-Level', Description=NULL, Help=NULL WHERE AD_Element_ID=543221
;

-- 31.10.2016 07:21
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Current_DLM_Level', Name='aktueller DLM-Level', Description=NULL, Help=NULL, AD_Element_ID=543221 WHERE UPPER(ColumnName)='CURRENT_DLM_LEVEL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 31.10.2016 07:21
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Current_DLM_Level', Name='aktueller DLM-Level', Description=NULL, Help=NULL WHERE AD_Element_ID=543221 AND IsCentrallyMaintained='Y'
;

-- 31.10.2016 07:21
-- URL zum Konzept
UPDATE AD_Field SET Name='aktueller DLM-Level', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543221) AND IsCentrallyMaintained='Y'
;

-- 31.10.2016 07:21
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='aktueller DLM-Level', Name='aktueller DLM-Level' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543221)
;


-- 31.10.2016 07:22
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555367,543220,0,11,540788,'N','Target_DLM_Level',TO_TIMESTAMP('2016-10-31 07:22:01','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.dlm',14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Ziel-DLM-Level',0,TO_TIMESTAMP('2016-10-31 07:22:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 31.10.2016 07:22
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555367 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;



-- 31.10.2016 07:22
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555366,557386,0,540765,0,TO_TIMESTAMP('2016-10-31 07:22:38','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.dlm',0,'Y','Y','Y','Y','N','N','N','N','N','aktueller DLM-Level',60,60,0,1,1,TO_TIMESTAMP('2016-10-31 07:22:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 31.10.2016 07:22
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557386 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 31.10.2016 07:22
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555367,557387,0,540765,0,TO_TIMESTAMP('2016-10-31 07:22:57','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.dlm',0,'Y','Y','Y','Y','N','N','N','Y','Y','Ziel-DLM-Level',70,70,0,1,1,TO_TIMESTAMP('2016-10-31 07:22:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 31.10.2016 07:22
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557387 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 31.10.2016 07:23
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2016-10-31 07:23:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557386
;

-- 31.10.2016 08:14
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543222,0,'DateNextInspection',TO_TIMESTAMP('2016-10-31 08:14:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y','Nächte Prüfung der DLM-Levels','Nächte Prüfung der DLM-Levels',TO_TIMESTAMP('2016-10-31 08:14:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 31.10.2016 08:14
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543222 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 31.10.2016 08:14
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555368,543222,0,16,540788,'N','DateNextInspection',TO_TIMESTAMP('2016-10-31 08:14:41','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.dlm',7,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Nächte Prüfung der DLM-Levels',0,TO_TIMESTAMP('2016-10-31 08:14:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 31.10.2016 08:14
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555368 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


-- 31.10.2016 08:15
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y', SeqNo=90, SeqNoGrid=90,Updated=TO_TIMESTAMP('2016-10-31 08:15:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557335
;

-- 31.10.2016 08:16
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555368,557388,0,540765,0,TO_TIMESTAMP('2016-10-31 08:16:13','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.dlm',0,'Y','Y','Y','Y','N','N','N','Y','N','Nächte Prüfung der DLM-Levels',80,80,0,1,1,TO_TIMESTAMP('2016-10-31 08:16:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 31.10.2016 08:16
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557388 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 31.10.2016 08:16
-- URL zum Konzept
UPDATE AD_Element SET Name='Nächte Prüfung des DLM-Levels', PrintName='Nächte Prüfung des DLM-Levels',Updated=TO_TIMESTAMP('2016-10-31 08:16:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543222
;

-- 31.10.2016 08:16
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=543222
;

-- 31.10.2016 08:16
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='DateNextInspection', Name='Nächte Prüfung des DLM-Levels', Description=NULL, Help=NULL WHERE AD_Element_ID=543222
;

-- 31.10.2016 08:16
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DateNextInspection', Name='Nächte Prüfung des DLM-Levels', Description=NULL, Help=NULL, AD_Element_ID=543222 WHERE UPPER(ColumnName)='DATENEXTINSPECTION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 31.10.2016 08:16
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DateNextInspection', Name='Nächte Prüfung des DLM-Levels', Description=NULL, Help=NULL WHERE AD_Element_ID=543222 AND IsCentrallyMaintained='Y'
;

-- 31.10.2016 08:16
-- URL zum Konzept
UPDATE AD_Field SET Name='Nächte Prüfung des DLM-Levels', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543222) AND IsCentrallyMaintained='Y'
;

-- 31.10.2016 08:16
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Nächte Prüfung des DLM-Levels', Name='Nächte Prüfung des DLM-Levels' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543222)
;

--
-- Add the processes to update the new columns
--


-- 31.10.2016 13:51
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp
	-- ,Statistic_Count,Statistic_Seconds
	,Type,Updated,UpdatedBy,Value)
VALUES ('7',0,0,540734,'Y','de.metas.dlm.coordinator.process.DLM_Partition_Inspect','N',TO_TIMESTAMP('2016-10-31 13:51:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y','N','N','N','N','N','N','Y',0,'Ziel-DLM-Level der Partition überprüfen','N','Y'
	-- ,0,0
	,'Java',TO_TIMESTAMP('2016-10-31 13:51:46','YYYY-MM-DD HH24:MI:SS'),100,'DLM_Partition_Inspect')
;

-- 31.10.2016 13:51
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540734 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 31.10.2016 13:53
-- URL zum Konzept
UPDATE AD_Process SET Description='Überprüpft die aktuelle Auswahl (falls aus dem Fenster heraus gestartet) oder alle Partitionen und legt ihre jeweiligen SOLL DLM-Level fest',Updated=TO_TIMESTAMP('2016-10-31 13:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540734
;

-- 31.10.2016 13:53
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540734
;

-- 31.10.2016 13:53
-- URL zum Konzept
UPDATE AD_Process SET Description='Überprüpft die aktuelle Auswahl (falls aus dem Fenster heraus gestartet) oder alle Partitionen und legt ihre jeweiligen SOLL DLM-Level sowie ggf. den Zeitpunkt der der nächsten Überprüfung fest',Updated=TO_TIMESTAMP('2016-10-31 13:53:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540734
;

-- 31.10.2016 13:53
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540734
;

-- 31.10.2016 13:54
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540734,540788,TO_TIMESTAMP('2016-10-31 13:54:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y',TO_TIMESTAMP('2016-10-31 13:54:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 31.10.2016 13:54
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp
	-- ,Statistic_Count,Statistic_Seconds
	,Type,Updated,UpdatedBy,Value)
VALUES ('7',0,0,540735,'Y','de.metas.dlm.migrator.process.DLM_Partition_Migrate','N',TO_TIMESTAMP('2016-10-31 13:54:55','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.dlm','Y','N','N','N','N','N','N','Y',0,'Partition migrieren','N','Y'
	-- ,0,0
	,'Java',TO_TIMESTAMP('2016-10-31 13:54:55','YYYY-MM-DD HH24:MI:SS'),100,'DLM_Partition_Migrate')
;

-- 31.10.2016 13:54
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540735 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 31.10.2016 13:56
-- URL zum Konzept
UPDATE AD_Process SET Description='Migriert die aktuelle Auswahl (falls aus dem Fenster heraus gestartet) oder alle Partitionen und aktuelisert die jeweiligen IST DLM-LEvel entsprechend der Vorgaben aus den SOLL DLM-Leveln',Updated=TO_TIMESTAMP('2016-10-31 13:56:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540735
;

-- 31.10.2016 13:56
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540735
;

-- 31.10.2016 13:56
-- URL zum Konzept
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2016-10-31 13:56:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540735
;

-- 31.10.2016 13:56
-- URL zum Konzept
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2016-10-31 13:56:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540734
;

-- 31.10.2016 13:56
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540735,540788,TO_TIMESTAMP('2016-10-31 13:56:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y',TO_TIMESTAMP('2016-10-31 13:56:39','YYYY-MM-DD HH24:MI:SS'),100)
;
