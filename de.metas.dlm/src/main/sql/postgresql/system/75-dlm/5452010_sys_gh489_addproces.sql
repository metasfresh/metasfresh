-- 19.10.2016 13:12
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,540730,'Y','de.metas.dlm.partitioner.process.Remove_Table_from_DLM','N',TO_TIMESTAMP('2016-10-19 13:12:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y','N','N','N','N','N','N','Y',0,'Tabelle aus DLM entfernen','N','Y',0,0,'Java',TO_TIMESTAMP('2016-10-19 13:12:00','YYYY-MM-DD HH24:MI:SS'),100,'Remove_Table_From_DLM')
;

-- 19.10.2016 13:12
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540730 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 19.10.2016 13:12
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,126,0,540730,541039,30,'AD_Table_ID',TO_TIMESTAMP('2016-10-19 13:12:46','YYYY-MM-DD HH24:MI:SS'),100,'Tabelle, die nicht mehr Teil des DLM sein soll','de.metas.dlm',0,'','Y','N','Y','N','Y','N','DB-Tabelle',10,TO_TIMESTAMP('2016-10-19 13:12:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.10.2016 13:12
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541039 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 19.10.2016 13:13
-- URL zum Konzept
UPDATE AD_Process SET Description='Ggf. vorhandene DLM_Level und DLM_Partition_ID splaten werden in den Datenbank belassen',Updated=TO_TIMESTAMP('2016-10-19 13:13:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540730
;

-- 19.10.2016 13:13
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540730
;

-- 19.10.2016 13:14
-- URL zum Konzept
UPDATE AD_Process SET Description='Ggf. vorhandene DLM_Level und DLM_Partition_ID splaten werden in den Datenbank belassen.',Updated=TO_TIMESTAMP('2016-10-19 13:14:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540730
;

-- 19.10.2016 13:14
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540730
;

-- 19.10.2016 13:21
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540730,540790,TO_TIMESTAMP('2016-10-19 13:21:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y',TO_TIMESTAMP('2016-10-19 13:21:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.10.2016 13:22
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540730,100,TO_TIMESTAMP('2016-10-19 13:22:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y',TO_TIMESTAMP('2016-10-19 13:22:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.10.2016 13:23
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555159,543199,0,20,540790,'N','IsDLM','(select t.IsDLM from AD_Table t where t.AD_Table_ID=)',TO_TIMESTAMP('2016-10-19 13:23:26','YYYY-MM-DD HH24:MI:SS'),100,'N','Die Datensätze einer Tabelle mit aktiviertem DLM können vom System unterschiedlichen DLM-Levels zugeordnet werden','de.metas.dlm',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','DLM aktiviert',0,TO_TIMESTAMP('2016-10-19 13:23:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 19.10.2016 13:23
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555159 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 19.10.2016 13:24
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(select t.IsDLM from AD_Table t where t.AD_Table_ID=DLM_Partition_Config_Line.DLM_Referencing_Table_ID)',Updated=TO_TIMESTAMP('2016-10-19 13:24:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555159
;

-- 19.10.2016 13:25
-- URL zum Konzept
INSERT INTO DLM_Partition_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,DLM_Partition_Config_ID,IsActive,Name,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2016-10-19 13:25:03','YYYY-MM-DD HH24:MI:SS'),100,540000,'Y','ts',TO_TIMESTAMP('2016-10-19 13:25:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.10.2016 13:25
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555159,557338,0,540763,0,TO_TIMESTAMP('2016-10-19 13:25:59','YYYY-MM-DD HH24:MI:SS'),100,'Die Datensätze einer Tabelle mit aktiviertem DLM können vom System unterschiedlichen DLM-Levels zugeordnet werden',0,'de.metas.dlm',0,'Y','Y','Y','Y','N','N','N','N','Y','DLM aktiviert',45,45,0,1,1,TO_TIMESTAMP('2016-10-19 13:25:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.10.2016 13:25
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=557338 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;



-- 19.10.2016 14:35
-- URL zum Konzept
UPDATE AD_Column SET AD_Element_ID=543190, AD_Reference_ID=19, ColumnName='DLM_Partition_ID', Description=NULL, EntityType='de.metas.dlm', FieldLength=10, Help=NULL, Name='Partition',Updated=TO_TIMESTAMP('2016-10-19 14:35:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555155
;

