-- 06.08.2015 16:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540596,'de.metas.fresh.invoice.migrateMatchInv.process.M_MatchInv_RecreateForInvoiceLine','N',TO_TIMESTAMP('2015-08-06 16:01:28','YYYY-MM-DD HH24:MI:SS'),100,'Check all invoice lines which are not fully matched and try recreating the M_MatchInv records for them.','de.metas.fresh','Y','N','N','N','N','N',0,'M_MatchInv_RecreateForInvoiceLine','N','Y',0,0,'Java',TO_TIMESTAMP('2015-08-06 16:01:28','YYYY-MM-DD HH24:MI:SS'),100,'M_MatchInv_RecreateForInvoiceLine')
;

-- 06.08.2015 16:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540596 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 06.08.2015 16:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,630,0,540596,540737,36,'WhereClause',TO_TIMESTAMP('2015-08-06 16:01:48','YYYY-MM-DD HH24:MI:SS'),100,'Fully qualified SQL WHERE clause','de.metas.fresh',0,'The Where Clause indicates the SQL WHERE clause to use for record selection. The WHERE clause is added to the query. Fully qualified means "tablename.columnname".','Y','N','Y','N','N','N','Sql WHERE',10,TO_TIMESTAMP('2015-08-06 16:01:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.08.2015 16:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540737 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 06.08.2015 16:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,540639,0,540596,TO_TIMESTAMP('2015-08-06 16:03:57','YYYY-MM-DD HH24:MI:SS'),100,'Check all invoice lines which are not fully matched and try recreating the M_MatchInv records for them.','de.metas.fresh','M_MatchInv_RecreateForInvoiceLine','Y','N','N','N','M_MatchInv_RecreateForInvoiceLine',TO_TIMESTAMP('2015-08-06 16:03:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 06.08.2015 16:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540639 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 06.08.2015 16:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540639, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540639)
;

-- 06.08.2015 16:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=155 AND AD_Tree_ID=10
;

-- 06.08.2015 16:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=156 AND AD_Tree_ID=10
;

-- 06.08.2015 16:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=175 AND AD_Tree_ID=10
;

-- 06.08.2015 16:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=157 AND AD_Tree_ID=10
;

-- 06.08.2015 16:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=552 AND AD_Tree_ID=10
;

-- 06.08.2015 16:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540537 AND AD_Tree_ID=10
;

-- 06.08.2015 16:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540603 AND AD_Tree_ID=10
;

-- 06.08.2015 16:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=218, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540639 AND AD_Tree_ID=10
;

