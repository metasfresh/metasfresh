-- 22.09.2016 15:07
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,555102,2019,0,35,540787,'N','M_AttributeSetInstance_ID',TO_TIMESTAMP('2016-09-22 15:07:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Instanz des Merkmals-Satzes zum Produkt','de.metas.fresh',10,'The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Ausprägung Merkmals-Satz',0,TO_TIMESTAMP('2016-09-22 15:07:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 22.09.2016 15:07
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555102 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 22.09.2016 15:08
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,540724,'Y','de.metas.fresh.mrp_productinfo.process.X_MRP_ProductInfo_Detail_MV_Update_Records','N',TO_TIMESTAMP('2016-09-22 15:08:42','YYYY-MM-DD HH24:MI:SS'),100,'Aktualisert alle X_MRP_ProductInfo_Detail_MV Datensätze für das angegebene Datum','de.metas.fresh','Y','N','N','N','N','N','N','Y',0,'X_MRP_ProductInfo_Detail_MV Datensätze aktualisieren','N','Y',0,0,'Java',TO_TIMESTAMP('2016-09-22 15:08:42','YYYY-MM-DD HH24:MI:SS'),100,'X_MRP_ProductInfo_Detail_MV_Update_Records')
;

-- 22.09.2016 15:08
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540724 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 22.09.2016 15:08
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542844,0,540724,541023,15,'DateGeneral',TO_TIMESTAMP('2016-09-22 15:08:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh',0,'Y','N','Y','N','Y','N','Datum',10,TO_TIMESTAMP('2016-09-22 15:08:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 22.09.2016 15:08
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541023 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 22.09.2016 15:09
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,540726,0,540724,TO_TIMESTAMP('2016-09-22 15:09:59','YYYY-MM-DD HH24:MI:SS'),100,'Aktualisert alle X_MRP_ProductInfo_Detail_MV Datensätze für das angegebene Datum','de.metas.fresh','X_MRP_ProductInfo_Detail_MV_Update_Records','Y','N','N','N','N','X_MRP_ProductInfo_Detail_MV Datensätze aktualisieren',TO_TIMESTAMP('2016-09-22 15:09:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 22.09.2016 15:09
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540726 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 22.09.2016 15:09
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540726, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540726)
;


-- 22.09.2016 15:10
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540590, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540726 AND AD_Tree_ID=10
;

-- 22.09.2016 15:19
-- URL zum Konzept
UPDATE AD_Process_Para SET Description='Datum der Datensätze die zu aktualisieren sind. Falls leer wird das aktuelle Datum angenommen.', IsMandatory='N',Updated=TO_TIMESTAMP('2016-09-22 15:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541023
;

-- 22.09.2016 15:19
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=541023
;

-- 22.09.2016 15:20
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540724,541024,11,'DaysAhead',TO_TIMESTAMP('2016-09-22 15:20:55','YYYY-MM-DD HH24:MI:SS'),100,'0','Das Stichdatum (lt. Parameter oder "heute") wird um die angegeberne Anzahl von Tagen erhöht.','de.metas.fresh',0,'Y','N','Y','N','N','N','DaysAhead',20,TO_TIMESTAMP('2016-09-22 15:20:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 22.09.2016 15:20
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541024 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 22.09.2016 15:25
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540724,541025,20,'FirstCreateFallBack',TO_TIMESTAMP('2016-09-22 15:25:26','YYYY-MM-DD HH24:MI:SS'),100,'N','Falls angehakt wird zunächst die DB-Funktion X_MRP_ProductInfo_Detail_Insert_Fallback mit dem aus DateGeneral/"heute" und DaysAhead ermittelten Stichdatum aufgerufen','de.metas.fresh',0,'Y','N','Y','N','Y','N','FirstCreateFallBack',30,TO_TIMESTAMP('2016-09-22 15:25:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 22.09.2016 15:25
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541025 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 22.09.2016 15:27
-- URL zum Konzept
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,Description,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (0,0,540724,550040,TO_TIMESTAMP('2016-09-22 15:27:05','YYYY-MM-DD HH24:MI:SS'),100,'30 00 * * *','Runs at 00:30 every night, to insert and update new records into X_MRP_ProductInfo_Detail_MV (not for the coming day, but for n days in future, so it''s not required to run early each day); task 09421','de.metas.fresh',10,'M','Y','N',7,'N','X_MRP_ProductInfo_Detail_MV_Update_Records','N','P','C','NEW',0,TO_TIMESTAMP('2016-09-22 15:27:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 22.09.2016 15:27
-- URL zum Konzept
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,Created,CreatedBy,IsActive,ParameterDefault,Updated,UpdatedBy) VALUES (0,0,541024,550040,TO_TIMESTAMP('2016-09-22 15:27:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','10',TO_TIMESTAMP('2016-09-22 15:27:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 22.09.2016 15:27
-- URL zum Konzept
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,Created,CreatedBy,IsActive,ParameterDefault,Updated,UpdatedBy) VALUES (0,0,541025,550040,TO_TIMESTAMP('2016-09-22 15:27:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y',TO_TIMESTAMP('2016-09-22 15:27:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 22.09.2016 15:28
-- URL zum Konzept
UPDATE AD_Scheduler SET IsActive='N',Updated=TO_TIMESTAMP('2016-09-22 15:28:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550027
;

-- 22.09.2016 15:28
-- URL zum Konzept
UPDATE AD_Scheduler SET IsActive='Y',Updated=TO_TIMESTAMP('2016-09-22 15:28:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550027
;

-- 22.09.2016 15:28
-- URL zum Konzept
UPDATE AD_Scheduler SET Description='!! INACTIVE, the job is now done by X_MRP_ProductInfo_Detail_MV_Update_Records !! Runs at 00:30 every night, to insert new empty/fallback records into X_MRP_ProductInfo_Detail_MV (not for the coming day, but for n days in future, so it''S not required to run early each day); task 09421',Updated=TO_TIMESTAMP('2016-09-22 15:28:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550027
;

-- 22.09.2016 15:28
-- URL zum Konzept
UPDATE AD_Scheduler SET IsActive='N',Updated=TO_TIMESTAMP('2016-09-22 15:28:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Scheduler_ID=550027
;


-- 22.09.2016 15:31
-- URL zum Konzept
UPDATE AD_Process SET AllowProcessReRun='N', IsUseBPartnerLanguage='N',Updated=TO_TIMESTAMP('2016-09-22 15:31:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540724
;

--
-- DDL
--
COMMIT;

-- 22.09.2016 15:07
-- URL zum Konzept
ALTER TABLE X_MRP_ProductInfo_Detail_MV ADD M_AttributeSetInstance_ID NUMERIC(10) DEFAULT NULL 
;


