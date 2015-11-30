-- 23.11.2015 17:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540627,'N',TO_TIMESTAMP('2015-11-23 17:17:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','N','N','N','Y','N',0,'Revisionsstelle Geschäftspartner','N','Y',0,0,'Java',TO_TIMESTAMP('2015-11-23 17:17:12','YYYY-MM-DD HH24:MI:SS'),100,'C_BPartner_Changes')
;

-- 23.11.2015 17:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540627 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 23.11.2015 17:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET JasperReport='@PREFIX@de/metas/reports/bpartner_changes/report.jrxml',Updated=TO_TIMESTAMP('2015-11-23 17:18:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540627
;

-- 23.11.2015 17:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Storage SET QtyOnHand=1,Updated=TO_TIMESTAMP('2015-11-23 17:21:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_AttributeSetInstance_ID=3798425 AND M_Locator_ID=1000045 AND M_Product_ID=2000616
;

-- 23.11.2015 18:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('R',0,540665,0,540627,TO_TIMESTAMP('2015-11-23 18:02:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','C_BPartner_Changes','Y','N','N','N','Revisionsstelle Geschäftspartner',TO_TIMESTAMP('2015-11-23 18:02:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 23.11.2015 18:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540665 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 23.11.2015 18:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540665, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540665)
;

-- 23.11.2015 18:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=165 AND AD_Tree_ID=10
;

-- 23.11.2015 18:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=372 AND AD_Tree_ID=10
;

-- 23.11.2015 18:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=271 AND AD_Tree_ID=10
;

-- 23.11.2015 18:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=528 AND AD_Tree_ID=10
;

-- 23.11.2015 18:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=237 AND AD_Tree_ID=10
;

-- 23.11.2015 18:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=414 AND AD_Tree_ID=10
;

-- 23.11.2015 18:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=238 AND AD_Tree_ID=10
;

-- 23.11.2015 18:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=396 AND AD_Tree_ID=10
;

-- 23.11.2015 18:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540594 AND AD_Tree_ID=10
;

-- 23.11.2015 18:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=263, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540665 AND AD_Tree_ID=10
;

-- 24.11.2015 09:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1581,0,540627,540824,15,'DateFrom',TO_TIMESTAMP('2015-11-24 09:50:38','YYYY-MM-DD HH24:MI:SS'),100,'Startdatum eines Abschnittes','de.metas.fresh',0,'Datum von bezeichnet das Startdatum eines Abschnittes','Y','N','Y','N','Y','N','Datum von',10,TO_TIMESTAMP('2015-11-24 09:50:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 24.11.2015 09:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540824 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 24.11.2015 09:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1582,0,540627,540827,15,'DateTo',TO_TIMESTAMP('2015-11-24 09:51:22','YYYY-MM-DD HH24:MI:SS'),100,'Enddatum eines Abschnittes','de.metas.fresh',0,'Datum bis bezeichnet das Enddatum eines Abschnittes (inklusiv)','Y','N','Y','N','Y','N','Datum bis',20,TO_TIMESTAMP('2015-11-24 09:51:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 24.11.2015 09:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540827 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 24.11.2015 10:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET JasperReport='@PREFIX@de/metas/reports/bpartner_changes/report.jasper',Updated=TO_TIMESTAMP('2015-11-24 10:15:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540627
;

