-- 2019-03-26T07:39:47.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('1',0,0,541084,'Y','de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.imp.process.C_Invoice_ImportInvoiceResponse','N',TO_TIMESTAMP('2019-03-26 07:39:47','YYYY-MM-DD HH24:MI:SS'),100,'Importiert forum-datenaustausch.ch XML-Rechnungsantworten und speichert sie als Anhang zu den jeweiligen Rechnungen.
Falls die jeweilige Rechnungsantwort eine Rückweisung ist, wird das "Im Disput"-Flag der jeweiligen Rechnung markiert.','de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','N','N','N','N','N','N','Y','Y',0,'KK Rückweisungen importieren','N','Y','Java',TO_TIMESTAMP('2019-03-26 07:39:47','YYYY-MM-DD HH24:MI:SS'),100,'C_Invoice_ImportInvoiceResponse')
;

-- 2019-03-26T07:39:47.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541084 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-03-26T07:41:10.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541084,541383,38,'InputDirectory',TO_TIMESTAMP('2019-03-26 07:41:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.forum_datenaustausch_ch',0,'Y','N','Y','N','Y','N','Input-Verzeichnis',10,TO_TIMESTAMP('2019-03-26 07:41:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-26T07:41:10.129
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541383 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-03-26T07:42:10.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541084,541384,10,'ImportFileWildCard',TO_TIMESTAMP('2019-03-26 07:42:10','YYYY-MM-DD HH24:MI:SS'),100,'*.xml','de.metas.vertical.healthcare.forum_datenaustausch_ch',200,'Y','N','Y','N','Y','N','Input-wildcard',20,TO_TIMESTAMP('2019-03-26 07:42:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-26T07:42:10.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541384 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-03-26T08:43:55.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET Name='Input-Wildcard',Updated=TO_TIMESTAMP('2019-03-26 08:43:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541384
;

-- 2019-03-26T08:44:44.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541084,541385,38,'OutputDirectory',TO_TIMESTAMP('2019-03-26 08:44:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.forum_datenaustausch_ch',0,'Y','N','Y','N','Y','N','Output-Verzeichnis',30,TO_TIMESTAMP('2019-03-26 08:44:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-26T08:44:44.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541385 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-03-26T08:45:04.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET AllowProcessReRun='N',Updated=TO_TIMESTAMP('2019-03-26 08:45:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541084
;

-- 2019-03-26T08:47:16.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576298,0,'C_Invoice_ImportInvoiceResponse',TO_TIMESTAMP('2019-03-26 08:47:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.forum_datenaustausch_ch','Y','KK-Rückweisungen importieren','KK-Rückweisungen importieren',TO_TIMESTAMP('2019-03-26 08:47:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-26T08:47:16.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576298 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-26T08:47:23.391
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-03-26 08:47:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576298 AND AD_Language='de_CH'
;

-- 2019-03-26T08:47:23.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576298,'de_CH') 
;

-- 2019-03-26T08:47:25.703
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-03-26 08:47:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576298 AND AD_Language='de_DE'
;

-- 2019-03-26T08:47:25.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576298,'de_DE') 
;

-- 2019-03-26T08:47:25.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576298,'de_DE') 
;

-- 2019-03-26T08:48:06.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Import ',Updated=TO_TIMESTAMP('2019-03-26 08:48:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576298 AND AD_Language='en_US'
;

-- 2019-03-26T08:48:06.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576298,'en_US') 
;

-- 2019-03-26T09:08:05.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Import forum-datenaustausch.ch ', Name='Import health insurance invoice cancelation', PrintName='Import health insurance invoice cancelation',Updated=TO_TIMESTAMP('2019-03-26 09:08:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576298 AND AD_Language='en_US'
;

-- 2019-03-26T09:08:05.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576298,'en_US') 
;

-- 2019-03-26T09:09:07.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,576298,541228,0,541084,TO_TIMESTAMP('2019-03-26 09:09:06','YYYY-MM-DD HH24:MI:SS'),100,'Importiert forum-datenaustausch.ch XML-Rechnungsantworten und speichert sie als Anhang zu den jeweiligen Rechnungen.
Falls die jeweilige Rechnungsantwort eine Rückweisung ist, wird das "Im Disput"-Flag der jeweiligen Rechnung markiert.','de.metas.vertical.healthcare.forum_datenaustausch_ch','C_Invoice_ImportInvoiceResponse','Y','N','N','N','N','KK Rückweisungen importieren',TO_TIMESTAMP('2019-03-26 09:09:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-26T09:09:07.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541228 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-03-26T09:09:07.038
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541228, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541228)
;

-- 2019-03-26T09:09:07.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(576298) 
;

-- 2019-03-26T09:09:07.665
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53324 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=241 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.670
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=288 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=432 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=243 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=413 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=538 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=462 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=505 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=235 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=511 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=245 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=251 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.687
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=246 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.688
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=509 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.690
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=510 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=496 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=304 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=255 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=286 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=287 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=438 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=234 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=244 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.702
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53190 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.703
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53187 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540642 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540651 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540666 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000103 AND AD_Tree_ID=10
;

-- 2019-03-26T09:09:07.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541228 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:25.838
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,573958,541229,0,TO_TIMESTAMP('2019-03-26 09:16:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.forum_datenaustausch_ch','forum_datenaustausch_ch','Y','N','N','N','Y','Forum Datenaustausch',TO_TIMESTAMP('2019-03-26 09:16:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-26T09:16:25.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541229 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-03-26T09:16:25.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541229, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541229)
;

-- 2019-03-26T09:16:25.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(573958) 
;

-- 2019-03-26T09:16:26.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53324 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.046
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=241 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=288 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=432 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=243 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=413 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=538 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=462 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=505 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=235 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=511 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=245 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=251 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=246 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.067
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=509 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=510 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=496 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=304 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=255 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=286 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=287 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=438 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=234 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=244 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53190 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53187 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540642 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540651 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540666 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000103 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541228 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:26.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53324 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=241 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=288 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.301
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=432 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=243 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=413 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.305
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=538 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=462 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=505 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=235 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=511 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=245 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=251 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=246 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=509 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=510 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.321
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=496 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=304 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=255 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=286 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.327
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=287 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=438 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.330
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=234 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=244 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53190 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53187 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540642 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540651 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540666 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000103 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541228 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:32.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=236, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:34.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541229, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541228 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:49.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:49.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:49.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:49.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:49.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:49.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:49.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:49.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:49.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:49.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:49.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:49.965
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:49.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:49.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:49.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:49.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:49.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:49.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:49.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541085 AND AD_Tree_ID=10
;

-- 2019-03-26T09:16:49.980
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540861 AND AD_Tree_ID=10
;

-- 2019-03-26T09:17:37.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Forum Datenaustausch Konfig', PrintName='Forum Datenaustausch Konfig',Updated=TO_TIMESTAMP('2019-03-26 09:17:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575800 AND AD_Language='de_CH'
;

-- 2019-03-26T09:17:37.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575800,'de_CH') 
;

-- 2019-03-26T09:17:47.844
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Forum Datenaustausch Config', PrintName='Forum Datenaustausch Config',Updated=TO_TIMESTAMP('2019-03-26 09:17:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575800 AND AD_Language='en_US'
;

-- 2019-03-26T09:17:47.846
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575800,'en_US') 
;

-- 2019-03-26T09:17:57.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Forum Datenaustausch Konfig', PrintName='Forum Datenaustausch Konfig',Updated=TO_TIMESTAMP('2019-03-26 09:17:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575800 AND AD_Language='nl_NL'
;

-- 2019-03-26T09:17:57.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575800,'nl_NL') 
;

-- 2019-03-26T09:18:01.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Forum Datenaustausch Konfig', PrintName='Forum Datenaustausch Konfig',Updated=TO_TIMESTAMP('2019-03-26 09:18:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575800 AND AD_Language='de_DE'
;

-- 2019-03-26T09:18:01.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575800,'de_DE') 
;

-- 2019-03-26T09:18:01.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575800,'de_DE') 
;

-- 2019-03-26T09:18:01.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Forum Datenaustausch Konfig', Description=NULL, Help=NULL WHERE AD_Element_ID=575800
;

-- 2019-03-26T09:18:01.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Forum Datenaustausch Konfig', Description=NULL, Help=NULL WHERE AD_Element_ID=575800 AND IsCentrallyMaintained='Y'
;

-- 2019-03-26T09:18:01.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Forum Datenaustausch Konfig', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575800) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575800)
;

-- 2019-03-26T09:18:01.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Forum Datenaustausch Konfig', Name='Forum Datenaustausch Konfig' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575800)
;

-- 2019-03-26T09:18:01.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Forum Datenaustausch Konfig', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575800
;

-- 2019-03-26T09:18:01.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Forum Datenaustausch Konfig', Description=NULL, Help=NULL WHERE AD_Element_ID = 575800
;

-- 2019-03-26T09:18:01.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Forum Datenaustausch Konfig', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575800
;

-- 2019-03-26T09:18:45.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541229, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541145 AND AD_Tree_ID=10
;

-- 2019-03-26T09:18:45.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541229, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541228 AND AD_Tree_ID=10
;

-- 2019-03-26T09:19:22.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='http://www.forum-datenaustausch.ch',Updated=TO_TIMESTAMP('2019-03-26 09:19:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575800 AND AD_Language='de_CH'
;

-- 2019-03-26T09:19:22.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575800,'de_CH') 
;

-- 2019-03-26T09:19:25.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='http://www.forum-datenaustausch.ch',Updated=TO_TIMESTAMP('2019-03-26 09:19:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575800 AND AD_Language='en_US'
;

-- 2019-03-26T09:19:25.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575800,'en_US') 
;

-- 2019-03-26T09:19:27.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='http://www.forum-datenaustausch.ch',Updated=TO_TIMESTAMP('2019-03-26 09:19:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575800 AND AD_Language='nl_NL'
;

-- 2019-03-26T09:19:27.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575800,'nl_NL') 
;

-- 2019-03-26T09:19:29.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='http://www.forum-datenaustausch.ch',Updated=TO_TIMESTAMP('2019-03-26 09:19:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575800 AND AD_Language='de_DE'
;

-- 2019-03-26T09:19:29.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575800,'de_DE') 
;

-- 2019-03-26T09:19:29.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575800,'de_DE') 
;

-- 2019-03-26T09:19:29.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Forum Datenaustausch Konfig', Description=NULL, Help='http://www.forum-datenaustausch.ch' WHERE AD_Element_ID=575800
;

-- 2019-03-26T09:19:29.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Forum Datenaustausch Konfig', Description=NULL, Help='http://www.forum-datenaustausch.ch' WHERE AD_Element_ID=575800 AND IsCentrallyMaintained='Y'
;

-- 2019-03-26T09:19:29.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Forum Datenaustausch Konfig', Description=NULL, Help='http://www.forum-datenaustausch.ch' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575800) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575800)
;

-- 2019-03-26T09:19:29.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Forum Datenaustausch Konfig', Description=NULL, Help='http://www.forum-datenaustausch.ch', CommitWarning = NULL WHERE AD_Element_ID = 575800
;

-- 2019-03-26T09:19:29.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Forum Datenaustausch Konfig', Description=NULL, Help='http://www.forum-datenaustausch.ch' WHERE AD_Element_ID = 575800
;

-- 2019-03-26T09:19:29.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Forum Datenaustausch Konfig', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575800
;

-- 2019-03-26T09:19:40.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='http://www.forum-datenaustausch.ch',Updated=TO_TIMESTAMP('2019-03-26 09:19:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576298 AND AD_Language='de_CH'
;

-- 2019-03-26T09:19:40.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576298,'de_CH') 
;

-- 2019-03-26T09:19:42.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='http://www.forum-datenaustausch.ch',Updated=TO_TIMESTAMP('2019-03-26 09:19:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576298 AND AD_Language='de_DE'
;

-- 2019-03-26T09:19:42.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576298,'de_DE') 
;

-- 2019-03-26T09:19:42.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576298,'de_DE') 
;

-- 2019-03-26T09:19:42.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Invoice_ImportInvoiceResponse', Name='KK-Rückweisungen importieren', Description=NULL, Help='http://www.forum-datenaustausch.ch' WHERE AD_Element_ID=576298
;

-- 2019-03-26T09:19:42.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_ImportInvoiceResponse', Name='KK-Rückweisungen importieren', Description=NULL, Help='http://www.forum-datenaustausch.ch', AD_Element_ID=576298 WHERE UPPER(ColumnName)='C_INVOICE_IMPORTINVOICERESPONSE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-26T09:19:42.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Invoice_ImportInvoiceResponse', Name='KK-Rückweisungen importieren', Description=NULL, Help='http://www.forum-datenaustausch.ch' WHERE AD_Element_ID=576298 AND IsCentrallyMaintained='Y'
;

-- 2019-03-26T09:19:42.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='KK-Rückweisungen importieren', Description=NULL, Help='http://www.forum-datenaustausch.ch' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576298) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576298)
;

-- 2019-03-26T09:19:42.127
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='KK-Rückweisungen importieren', Description=NULL, Help='http://www.forum-datenaustausch.ch', CommitWarning = NULL WHERE AD_Element_ID = 576298
;

-- 2019-03-26T09:19:42.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='KK-Rückweisungen importieren', Description=NULL, Help='http://www.forum-datenaustausch.ch' WHERE AD_Element_ID = 576298
;

-- 2019-03-26T09:19:42.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'KK-Rückweisungen importieren', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576298
;

-- 2019-03-26T09:19:50.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='http://www.forum-datenaustausch.ch',Updated=TO_TIMESTAMP('2019-03-26 09:19:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576298 AND AD_Language='en_US'
;

-- 2019-03-26T09:19:50.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576298,'en_US') 
;

-- 2019-03-26T09:19:53.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='http://www.forum-datenaustausch.ch',Updated=TO_TIMESTAMP('2019-03-26 09:19:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576298 AND AD_Language='nl_NL'
;

-- 2019-03-26T09:19:53.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576298,'nl_NL') 
;

-- 2019-03-26T09:20:02.394
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='',Updated=TO_TIMESTAMP('2019-03-26 09:20:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576298 AND AD_Language='en_US'
;

-- 2019-03-26T09:20:02.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576298,'en_US') 
;

-- 2019-03-26T09:20:17.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Import health insurance invoice cancelations', PrintName='Import health insurance invoice cancelations',Updated=TO_TIMESTAMP('2019-03-26 09:20:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576298 AND AD_Language='en_US'
;

-- 2019-03-26T09:20:17.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576298,'en_US') 
;

