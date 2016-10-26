-- 26.10.2016 11:53
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,540733,'Y','de.metas.dlm.partitioner.process.DLM_Partition_Create_Async','N',TO_TIMESTAMP('2016-10-26 11:53:42','YYYY-MM-DD HH24:MI:SS'),100,'Erzeugt ein Arbeitspaket zur asynchronen Verarbeitung. Wenn das Arbeitspaket verarbeitet ist, wird es abhängi von den Prozessparametern ein weiteres Arbeitspaket erstellen usw.','de.metas.dlm','Y','N','N','N','N','N','N','Y',0,'Erstelle Partionen asynchron','N','Y',0,0,'Java',TO_TIMESTAMP('2016-10-26 11:53:42','YYYY-MM-DD HH24:MI:SS'),100,'DLM_Partition_Create_Async')
;

-- 26.10.2016 11:53
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540733 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 26.10.2016 11:54
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543191,0,540733,541045,19,'DLM_Partition_Config_ID',TO_TIMESTAMP('2016-10-26 11:54:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm',0,'Y','N','Y','N','Y','N','DLM Partitionierungskonfiguration',10,TO_TIMESTAMP('2016-10-26 11:54:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 11:54
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541045 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 26.10.2016 11:56
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540733,541046,11,'Count',TO_TIMESTAMP('2016-10-26 11:56:03','YYYY-MM-DD HH24:MI:SS'),100,'Maximale Anzahl der Arbeitspakete, die das System anlegt.','de.metas.dlm',0,'Y','N','Y','N','N','N','Anzahl',20,TO_TIMESTAMP('2016-10-26 11:56:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 11:56
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541046 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 26.10.2016 11:56
-- URL zum Konzept
UPDATE AD_Process_Para SET FieldLength=1,Updated=TO_TIMESTAMP('2016-10-26 11:56:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541044
;

-- 26.10.2016 11:57
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543210,0,'DLMOldestFirst',TO_TIMESTAMP('2016-10-26 11:57:45','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob mit der kleinsten ID zuerst oder Datensätze mit der größten ID verarbeitet werden','de.metas.dlm','Wenn der DB-Paramter "metasfresh.DLM_Coalesce_Level" auf 2 eingestellt ist, machte es in der Regel Sinn, die neuesten Daten zuerst zu Partitionieren.','Y','Alte Datensätze zuerst','Alte Datensätze zuerst',TO_TIMESTAMP('2016-10-26 11:57:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 11:57
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543210 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 26.10.2016 11:57
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543210,0,540733,541047,20,'DLMOldestFirst',TO_TIMESTAMP('2016-10-26 11:57:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','Legt fest, ob mit der kleinsten ID zuerst oder Datensätze mit der größten ID verarbeitet werden','de.metas.dlm',1,'Wenn der DB-Paramter "metasfresh.DLM_Coalesce_Level" auf 2 eingestellt ist, machte es in der Regel Sinn, die neuesten Daten zuerst zu Partitionieren.','Y','N','Y','N','Y','N','Alte Datensätze zuerst',30,TO_TIMESTAMP('2016-10-26 11:57:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 11:57
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541047 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 26.10.2016 11:58
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Element_ID=543210, ColumnName='DLMOldestFirst',Updated=TO_TIMESTAMP('2016-10-26 11:58:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541044
;

-- 26.10.2016 11:58
-- URL zum Konzept
UPDATE AD_Process_Para SET SeqNo=15,Updated=TO_TIMESTAMP('2016-10-26 11:58:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541044
;

-- 26.10.2016 11:58
-- URL zum Konzept
UPDATE AD_Process_Para SET SeqNo=15,Updated=TO_TIMESTAMP('2016-10-26 11:58:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541047
;

-- 26.10.2016 12:00
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540733,541048,16,'DontReEnqueueAfter',TO_TIMESTAMP('2016-10-26 12:00:47','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, dass das System noch dem Verarbeiten eine Arbeitspakets kein neues Arbeitspaket mehr erzeugt, wenn die angegebene Uhrzeit schon vergangen ist.','de.metas.dlm',0,'Y','N','Y','N','N','N','Keine Weitere Pakete nach',30,TO_TIMESTAMP('2016-10-26 12:00:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 12:00
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541048 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 26.10.2016 12:00
-- URL zum Konzept
UPDATE AD_Process_Para SET Name='Keine weiteren Pakete nach',Updated=TO_TIMESTAMP('2016-10-26 12:00:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541048
;

-- 26.10.2016 12:00
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=541048
;


-- 26.10.2016 12:16
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540696,TO_TIMESTAMP('2016-10-26 12:16:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y','N','OnNotDLMTable',TO_TIMESTAMP('2016-10-26 12:16:19','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 26.10.2016 12:16
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540696 AND NOT EXISTS (SELECT * FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 26.10.2016 12:17
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540696,541222,TO_TIMESTAMP('2016-10-26 12:17:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y','Zu DLM Hinzufügen',TO_TIMESTAMP('2016-10-26 12:17:20','YYYY-MM-DD HH24:MI:SS'),100,'ADD_TO_DLM','ADD_TO_DLM')
;

-- 26.10.2016 12:17
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541222 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 26.10.2016 12:17
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540696,541223,TO_TIMESTAMP('2016-10-26 12:17:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dlm','Y','Abbrechen',TO_TIMESTAMP('2016-10-26 12:17:52','YYYY-MM-DD HH24:MI:SS'),100,'FAIL','FAIL')
;

-- 26.10.2016 12:17
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541223 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 26.10.2016 12:20
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540728,541049,17,540696,'OnNotDLMTable',TO_TIMESTAMP('2016-10-26 12:20:40','YYYY-MM-DD HH24:MI:SS'),100,'FAIL','Wenn die Tabelle eine zu Partitionierenden Datensatzes nocht nicht für DLM vorbereitet ist, kann der Prozess dies Nachholen. Zugriffe auf die jeweilige Tabelle werden währenddessen blockiert!','de.metas.dlm',20,'','Y','N','Y','N','Y','N','Bei Nicht-DLM-Tabellen',30,TO_TIMESTAMP('2016-10-26 12:20:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 12:20
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541049 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;


-- 26.10.2016 12:23
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,540733,0,540733,TO_TIMESTAMP('2016-10-26 12:23:54','YYYY-MM-DD HH24:MI:SS'),100,'Erzeugt ein Arbeitspaket zur asynchronen Verarbeitung. Wenn das Arbeitspaket verarbeitet ist, wird es abhängi von den Prozessparametern ein weiteres Arbeitspaket erstellen usw.','de.metas.dlm','DLM_Partition_Create_Async','Y','N','N','N','N','Erstelle Partionen asynchron',TO_TIMESTAMP('2016-10-26 12:23:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 12:23
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540733 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 26.10.2016 12:23
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540733, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540733)
;


-- 26.10.2016 12:24
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540729, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540730 AND AD_Tree_ID=10
;

-- 26.10.2016 12:24
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540729, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540731 AND AD_Tree_ID=10
;

-- 26.10.2016 12:24
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540729, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540733 AND AD_Tree_ID=10
;

-- 26.10.2016 12:24
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=540729, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540732 AND AD_Tree_ID=10
;

-- 26.10.2016 12:24
-- URL zum Konzept
UPDATE AD_Process SET Description='Erzeugt ein Arbeitspaket zur asynchronen Verarbeitung. Wenn das Arbeitspaket verarbeitet ist, wird abhängig von den Prozessparametern ein weiteres Arbeitspaket erstellt usw.',Updated=TO_TIMESTAMP('2016-10-26 12:24:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540733
;

-- 26.10.2016 12:24
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540733
;

-- 26.10.2016 12:24
-- URL zum Konzept
UPDATE AD_Menu SET Description='Erzeugt ein Arbeitspaket zur asynchronen Verarbeitung. Wenn das Arbeitspaket verarbeitet ist, wird abhängig von den Prozessparametern ein weiteres Arbeitspaket erstellt usw.', IsActive='Y', Name='Erstelle Partionen asynchron',Updated=TO_TIMESTAMP('2016-10-26 12:24:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540733
;

-- 26.10.2016 12:24
-- URL zum Konzept
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=540733
;

