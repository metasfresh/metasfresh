-- 05.01.2016 15:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540634,'org.adempiere.acct.process.Fact_Acct_Log_Process','N',TO_TIMESTAMP('2016-01-05 15:07:38','YYYY-MM-DD HH24:MI:SS'),100,'Process Fact Acct logs and updates Fact Acct Summary table','D','Y','N','N','N','N','N',0,'Process Fact Acct logs','N','Y',0,0,'Java',TO_TIMESTAMP('2016-01-05 15:07:38','YYYY-MM-DD HH24:MI:SS'),100,'Fact_Acct_Log_Process')
;

-- 05.01.2016 15:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540634 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 05.01.2016 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,540671,0,540634,TO_TIMESTAMP('2016-01-05 15:13:07','YYYY-MM-DD HH24:MI:SS'),100,'Process Fact Acct logs and updates Fact Acct Summary table','D','Fact_Acct_Log_Process','Y','N','N','N','Process Fact Acct logs',TO_TIMESTAMP('2016-01-05 15:13:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 05.01.2016 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540671 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 05.01.2016 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540671, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540671)
;

-- 05.01.2016 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=164 AND AD_Tree_ID=10
;

-- 05.01.2016 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=280 AND AD_Tree_ID=10
;

-- 05.01.2016 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=158 AND AD_Tree_ID=10
;

-- 05.01.2016 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=522 AND AD_Tree_ID=10
;

-- 05.01.2016 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=118 AND AD_Tree_ID=10
;

-- 05.01.2016 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=169 AND AD_Tree_ID=10
;

-- 05.01.2016 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=433 AND AD_Tree_ID=10
;

-- 05.01.2016 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=352 AND AD_Tree_ID=10
;

-- 05.01.2016 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=434 AND AD_Tree_ID=10
;

-- 05.01.2016 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=435 AND AD_Tree_ID=10
;

-- 05.01.2016 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540671 AND AD_Tree_ID=10
;

update AD_Process set Classname='de.metas.acct.process.Fact_Acct_Log_Process' where Classname like '%.Fact_Acct_Log_Process';


















-- 06.01.2016 18:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,542936,0,'IsAsync',TO_TIMESTAMP('2016-01-06 18:03:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Async','Async',TO_TIMESTAMP('2016-01-06 18:03:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.01.2016 18:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=542936 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 06.01.2016 18:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542936,0,540634,540835,20,'IsAsync',TO_TIMESTAMP('2016-01-06 18:04:02','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.acct',0,'Y','N','Y','N','Y','N','Async',10,TO_TIMESTAMP('2016-01-06 18:04:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.01.2016 18:04
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540835 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

