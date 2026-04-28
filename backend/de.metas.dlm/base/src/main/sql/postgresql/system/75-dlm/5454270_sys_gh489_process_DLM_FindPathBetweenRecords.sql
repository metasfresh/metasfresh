-- 12.12.2016 07:08
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,540746,'Y','de.metas.dlm.partitioner.process.DLM_FindPathBetweenRecords','N',TO_TIMESTAMP('2016-12-12 07:08:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y','N','N','N','N','N','N','Y',0,'Pfadsuche zwischen zwei Datensätzen','N','Y','Java',TO_TIMESTAMP('2016-12-12 07:08:35','YYYY-MM-DD HH24:MI:SS'),100,'DLM_FindPathBetweenRecords')
;

-- 12.12.2016 07:08
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540746 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 12.12.2016 07:09
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543191,0,540746,541124,19,'DLM_Partition_Config_ID',TO_TIMESTAMP('2016-12-12 07:09:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm',0,'Y','N','Y','N','Y','N','DLM Partitionierungskonfiguration',10,TO_TIMESTAMP('2016-12-12 07:09:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:09
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541124 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 12.12.2016 07:09
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540746,541125,30,'AD_Table_Start_ID',TO_TIMESTAMP('2016-12-12 07:09:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm',0,'Y','N','Y','N','Y','N','Tabelle Startdatensatz',20,TO_TIMESTAMP('2016-12-12 07:09:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:09
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541125 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 12.12.2016 07:10
-- URL zum Konzept
UPDATE AD_Process_Para SET Name='DB-Tabelle des Startdatensatz',Updated=TO_TIMESTAMP('2016-12-12 07:10:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541125
;

-- 12.12.2016 07:10
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=541125
;

-- 12.12.2016 07:11
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540746,541126,11,'Record_Start_ID',TO_TIMESTAMP('2016-12-12 07:11:17','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.dlm',0,'','Y','N','N','N','Y','N','Datensatz-ID Startdatensatz',30,TO_TIMESTAMP('2016-12-12 07:11:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:11
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541126 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 12.12.2016 07:11
-- URL zum Konzept
UPDATE AD_Process_Para SET Name='DB-Tabelle Startdatensatz',Updated=TO_TIMESTAMP('2016-12-12 07:11:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541125
;

-- 12.12.2016 07:11
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=541125
;

-- 12.12.2016 07:11
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540746,541128,30,'AD_Table_Start_ID',TO_TIMESTAMP('2016-12-12 07:11:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm',0,'Y','N','N','N','Y','N','DB-Tabelle Zieldatensatz',40,TO_TIMESTAMP('2016-12-12 07:11:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:11
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541128 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 12.12.2016 07:12
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540746,541129,11,'Record_Start_ID',TO_TIMESTAMP('2016-12-12 07:12:57','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.dlm',0,'','Y','N','N','N','Y','N','Datensatz-ID Zieldatensatz',50,TO_TIMESTAMP('2016-12-12 07:12:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:12
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541129 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 12.12.2016 07:13
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,540747,0,540746,TO_TIMESTAMP('2016-12-12 07:13:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','DLM_FindPathBetweenRecords','Y','N','N','N','N','Pfadsuche zwischen zwei Datensätzen',TO_TIMESTAMP('2016-12-12 07:13:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.12.2016 07:13
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540747 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 12.12.2016 07:13
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540747, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540747)
;

-- 12.12.2016 07:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540729, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540730 AND AD_Tree_ID=10
;

-- 12.12.2016 07:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540729, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540731 AND AD_Tree_ID=10
;

-- 12.12.2016 07:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540729, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540747 AND AD_Tree_ID=10
;

-- 12.12.2016 07:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540729, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540733 AND AD_Tree_ID=10
;

-- 12.12.2016 07:14
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540729, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540732 AND AD_Tree_ID=10
;



-- 12.12.2016 07:19
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_Value_ID=53290, IsAutocomplete='Y',Updated=TO_TIMESTAMP('2016-12-12 07:19:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541125
;

-- 12.12.2016 07:19
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_Value_ID=53290, IsAutocomplete='Y',Updated=TO_TIMESTAMP('2016-12-12 07:19:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541128
;

-- 12.12.2016 07:21
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_Table_Goal_ID',Updated=TO_TIMESTAMP('2016-12-12 07:21:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541128
;

-- 12.12.2016 07:21
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Record_Goal_ID',Updated=TO_TIMESTAMP('2016-12-12 07:21:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541129
;

-- 12.12.2016 07:22
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_ID=18,Updated=TO_TIMESTAMP('2016-12-12 07:22:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541128
;

-- 12.12.2016 07:22
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_ID=18,Updated=TO_TIMESTAMP('2016-12-12 07:22:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541125
;

