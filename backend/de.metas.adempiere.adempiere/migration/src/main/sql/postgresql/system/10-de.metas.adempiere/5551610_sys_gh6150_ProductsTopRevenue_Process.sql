-- 2020-02-10T13:14:12.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584651,'Y','de.metas.impexp.excel.process.ExportToExcelProcess','N',TO_TIMESTAMP('2020-02-10 15:14:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Y',0,'ProductTopSales','N','N','Excel',TO_TIMESTAMP('2020-02-10 15:14:12','YYYY-MM-DD HH24:MI:SS'),100,'ProductTopSales')
;

-- 2020-02-10T13:14:12.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=584651 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-02-10T13:14:40.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1581,0,584651,541705,15,'DateFrom',TO_TIMESTAMP('2020-02-10 15:14:40','YYYY-MM-DD HH24:MI:SS'),100,'Startdatum eines Abschnittes','D',0,'Datum von bezeichnet das Startdatum eines Abschnittes','Y','N','Y','N','N','N','Datum von',10,TO_TIMESTAMP('2020-02-10 15:14:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-10T13:14:40.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541705 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-02-10T13:15:09.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1582,0,584651,541706,15,'DateTo',TO_TIMESTAMP('2020-02-10 15:15:08','YYYY-MM-DD HH24:MI:SS'),100,'Enddatum eines Abschnittes','D',0,'Datum bis bezeichnet das Enddatum eines Abschnittes (inklusiv)','Y','N','Y','N','N','N','Datum bis',20,TO_TIMESTAMP('2020-02-10 15:15:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-10T13:15:09.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541706 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-02-10T13:16:09.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543188,0,584651,541707,11,'Limit',TO_TIMESTAMP('2020-02-10 15:16:09','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','N','N','Limit',30,TO_TIMESTAMP('2020-02-10 15:16:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-10T13:16:09.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541707 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-02-10T13:18:11.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='select * from ProductTopSales(''@DateFrom/1900-01-01@''::date , ''@DateTo/9999-12-31@''::date , @Limit/-1@)',Updated=TO_TIMESTAMP('2020-02-10 15:18:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584651
;

-- 2020-02-10T13:29:15.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577526,0,TO_TIMESTAMP('2020-02-10 15:29:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','ProductTopSales','ProductTopSales',TO_TIMESTAMP('2020-02-10 15:29:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-10T13:29:15.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577526 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-02-10T13:29:42.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,577526,541432,0,584651,TO_TIMESTAMP('2020-02-10 15:29:41','YYYY-MM-DD HH24:MI:SS'),100,'D','ProductTopSales','Y','N','N','N','N','ProductTopSales',TO_TIMESTAMP('2020-02-10 15:29:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-10T13:29:42.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541432 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2020-02-10T13:29:42.040Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541432, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541432)
;

-- 2020-02-10T13:29:42.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(577526) 
;

-- 2020-02-10T13:29:42.681Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541000 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:42.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541001 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:42.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541008 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:42.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541002 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:42.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541003 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:42.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540956 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:42.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540881 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:42.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540882 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:42.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540842 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:42.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541098 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:42.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540843 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:42.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541097 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:42.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540810 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:42.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540812 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:42.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541107 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:42.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540813 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:42.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540780 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:42.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541103 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:42.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000072, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541432 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:58.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:58.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:58.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:58.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:58.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541432 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:58.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:58.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:58.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:58.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:58.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:58.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:58.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:58.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:58.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:58.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:58.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:58.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:58.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:58.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:58.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:58.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541085 AND AD_Tree_ID=10
;

-- 2020-02-10T13:29:58.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540861 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:00.928Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541221 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:00.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000040 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:00.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000097 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:00.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540855 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:00.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540867 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:00.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541038 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:00.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000046 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:00.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000048 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:00.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000050 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:00.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541226 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:00.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541369 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:00.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541432 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:00.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541427 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:03.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541221 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:03.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000040 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:03.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000097 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:03.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540855 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:03.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540867 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:03.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541038 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:03.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000046 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:03.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000048 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:03.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000050 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:03.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541226 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:03.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541369 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:03.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541427 AND AD_Tree_ID=10
;

-- 2020-02-10T13:30:03.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541432 AND AD_Tree_ID=10
;






-- 2020-02-11T10:38:35.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,102,0,584651,541708,19,'AD_Client_ID',TO_TIMESTAMP('2020-02-11 12:38:35','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','D',0,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','N','Mandant',40,TO_TIMESTAMP('2020-02-11 12:38:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-11T10:38:35.499Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541708 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-02-11T10:38:47.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='#AD_Client_ID',Updated=TO_TIMESTAMP('2020-02-11 12:38:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541708
;

-- 2020-02-11T10:39:10.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,113,0,584651,541709,19,'AD_Org_ID',TO_TIMESTAMP('2020-02-11 12:39:10','YYYY-MM-DD HH24:MI:SS'),100,'#AD_Org_ID','Organisatorische Einheit des Mandanten','D',0,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','N','Sektion',50,TO_TIMESTAMP('2020-02-11 12:39:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-11T10:39:10.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541709 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-02-11T10:39:12.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-02-11 12:39:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541709
;

-- 2020-02-11T10:39:15.010Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-02-11 12:39:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541708
;

-- 2020-02-11T10:39:25.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=10,Updated=TO_TIMESTAMP('2020-02-11 12:39:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541708
;

-- 2020-02-11T10:39:25.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=20,Updated=TO_TIMESTAMP('2020-02-11 12:39:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541709
;

-- 2020-02-11T10:39:25.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=30,Updated=TO_TIMESTAMP('2020-02-11 12:39:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541705
;

-- 2020-02-11T10:39:25.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=40,Updated=TO_TIMESTAMP('2020-02-11 12:39:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541706
;

-- 2020-02-11T10:39:25.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=50,Updated=TO_TIMESTAMP('2020-02-11 12:39:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541707
;

-- 2020-02-11T10:39:57.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='select * from ProductTopSales(@AD_Client_ID@, @AD_Org_ID@, ''@DateFrom/1900-01-01@''::date , ''@DateTo/9999-12-31@''::date , @Limit/-1@)',Updated=TO_TIMESTAMP('2020-02-11 12:39:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584651
;

-- 2020-02-11T10:41:20.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='@#AD_Client_ID@',Updated=TO_TIMESTAMP('2020-02-11 12:41:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541708
;

-- 2020-02-11T10:41:26.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET DefaultValue='@#AD_Org_ID@',Updated=TO_TIMESTAMP('2020-02-11 12:41:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541709
;









-- 2020-02-11T11:03:42.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='select * from ProductTopRevenue(@AD_Client_ID@, @AD_Org_ID@, ''@DateFrom/1900-01-01@''::date , ''@DateTo/9999-12-31@''::date , @Limit/-1@)',Updated=TO_TIMESTAMP('2020-02-11 13:03:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584651
;

-- 2020-02-11T11:05:13.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='select * from ProductTopRevenue(@AD_Client_ID@::numeric, @AD_Org_ID@::numeric, ''@DateFrom/1900-01-01@''::date , ''@DateTo/9999-12-31@''::date , @Limit/-1@::numeric)',Updated=TO_TIMESTAMP('2020-02-11 13:05:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584651
;

-- 2020-02-11T11:05:38.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='select * from ProductsTopRevenue(@AD_Client_ID@::numeric, @AD_Org_ID@::numeric, ''@DateFrom/1900-01-01@''::date , ''@DateTo/9999-12-31@''::date , @Limit/-1@::numeric)',Updated=TO_TIMESTAMP('2020-02-11 13:05:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584651
;

-- 2020-02-11T11:06:09.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='ProductTopRevenue', Value='ProductTopRevenue',Updated=TO_TIMESTAMP('2020-02-11 13:06:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584651
;

-- 2020-02-11T11:06:09.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='ProductTopRevenue',Updated=TO_TIMESTAMP('2020-02-11 13:06:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541432
;

-- 2020-02-11T11:06:54.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='select * from ProductsTopRevenue(@AD_Client_ID@, @AD_Org_ID@, ''@DateFrom/1900-01-01@''::date , ''@DateTo/9999-12-31@''::date , @Limit/-1@)',Updated=TO_TIMESTAMP('2020-02-11 13:06:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584651
;























-- 2020-02-11T11:49:23.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Umsatzstärkste Produkte anzeigen', PrintName='Umsatzstärkste Produkte anzeigen',Updated=TO_TIMESTAMP('2020-02-11 13:49:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577526 AND AD_Language='de_CH'
;

-- 2020-02-11T11:49:23.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577526,'de_CH') 
;

-- 2020-02-11T11:49:28.011Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Umsatzstärkste Produkte anzeigen', PrintName='Umsatzstärkste Produkte anzeigen',Updated=TO_TIMESTAMP('2020-02-11 13:49:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577526 AND AD_Language='de_DE'
;

-- 2020-02-11T11:49:28.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577526,'de_DE') 
;

-- 2020-02-11T11:49:28.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577526,'de_DE') 
;

-- 2020-02-11T11:49:28.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Umsatzstärkste Produkte anzeigen', Description=NULL, Help=NULL WHERE AD_Element_ID=577526
;

-- 2020-02-11T11:49:28.033Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Umsatzstärkste Produkte anzeigen', Description=NULL, Help=NULL WHERE AD_Element_ID=577526 AND IsCentrallyMaintained='Y'
;

-- 2020-02-11T11:49:28.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Umsatzstärkste Produkte anzeigen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577526) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577526)
;

-- 2020-02-11T11:49:28.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Umsatzstärkste Produkte anzeigen', Name='Umsatzstärkste Produkte anzeigen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577526)
;

-- 2020-02-11T11:49:28.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Umsatzstärkste Produkte anzeigen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577526
;

-- 2020-02-11T11:49:28.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Umsatzstärkste Produkte anzeigen', Description=NULL, Help=NULL WHERE AD_Element_ID = 577526
;

-- 2020-02-11T11:49:28.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Umsatzstärkste Produkte anzeigen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577526
;

-- 2020-02-11T11:49:43.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Show top-revenue products', PrintName='Show top-revenue products',Updated=TO_TIMESTAMP('2020-02-11 13:49:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577526 AND AD_Language='en_US'
;

-- 2020-02-11T11:49:43.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577526,'en_US') 
;

-- 2020-02-11T11:54:43.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577527,0,'Rang',TO_TIMESTAMP('2020-02-11 13:54:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Rang','Rang',TO_TIMESTAMP('2020-02-11 13:54:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-11T11:54:43.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577527 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-02-11T11:56:37.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577528,0,'ProductRevenue',TO_TIMESTAMP('2020-02-11 13:56:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','ProductRevenue','ProductRevenue',TO_TIMESTAMP('2020-02-11 13:56:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-11T11:56:37.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577528 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-02-11T11:56:44.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Product Revenue', PrintName='Product Revenue',Updated=TO_TIMESTAMP('2020-02-11 13:56:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577528 AND AD_Language='en_US'
;

-- 2020-02-11T11:56:44.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577528,'en_US') 
;

-- 2020-02-11T11:57:05.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Verkauf (MW)', PrintName='Verkauf (MW)',Updated=TO_TIMESTAMP('2020-02-11 13:57:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577528 AND AD_Language='de_DE'
;

-- 2020-02-11T11:57:05.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577528,'de_DE') 
;

-- 2020-02-11T11:57:05.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577528,'de_DE') 
;

-- 2020-02-11T11:57:05.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ProductRevenue', Name='Verkauf (MW)', Description=NULL, Help=NULL WHERE AD_Element_ID=577528
;

-- 2020-02-11T11:57:05.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProductRevenue', Name='Verkauf (MW)', Description=NULL, Help=NULL, AD_Element_ID=577528 WHERE UPPER(ColumnName)='PRODUCTREVENUE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-11T11:57:05.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProductRevenue', Name='Verkauf (MW)', Description=NULL, Help=NULL WHERE AD_Element_ID=577528 AND IsCentrallyMaintained='Y'
;

-- 2020-02-11T11:57:05.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verkauf (MW)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577528) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577528)
;

-- 2020-02-11T11:57:05.361Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Verkauf (MW)', Name='Verkauf (MW)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577528)
;

-- 2020-02-11T11:57:05.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Verkauf (MW)', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577528
;

-- 2020-02-11T11:57:05.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Verkauf (MW)', Description=NULL, Help=NULL WHERE AD_Element_ID = 577528
;

-- 2020-02-11T11:57:05.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Verkauf (MW)', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577528
;

-- 2020-02-11T11:57:10.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Verkauf (MW)', PrintName='Verkauf (MW)',Updated=TO_TIMESTAMP('2020-02-11 13:57:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577528 AND AD_Language='de_CH'
;

-- 2020-02-11T11:57:10.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577528,'de_CH') 
;

-- 2020-02-11T11:58:57.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577529,0,'SalesPercentProduct',TO_TIMESTAMP('2020-02-11 13:58:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Sales Percent Product','Sales Percent Product',TO_TIMESTAMP('2020-02-11 13:58:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-11T11:58:57.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577529 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-02-11T11:59:27.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Anteil von Verkauf (MW)', PrintName='Anteil von Verkauf (MW)',Updated=TO_TIMESTAMP('2020-02-11 13:59:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577529 AND AD_Language='de_CH'
;

-- 2020-02-11T11:59:27.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577529,'de_CH') 
;

-- 2020-02-11T11:59:32.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Anteil von Verkauf (MW)', PrintName='Anteil von Verkauf (MW)',Updated=TO_TIMESTAMP('2020-02-11 13:59:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577529 AND AD_Language='de_DE'
;

-- 2020-02-11T11:59:32.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577529,'de_DE') 
;

-- 2020-02-11T11:59:32.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577529,'de_DE') 
;

-- 2020-02-11T11:59:32.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SalesPercentProduct', Name='Anteil von Verkauf (MW)', Description=NULL, Help=NULL WHERE AD_Element_ID=577529
;

-- 2020-02-11T11:59:32.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SalesPercentProduct', Name='Anteil von Verkauf (MW)', Description=NULL, Help=NULL, AD_Element_ID=577529 WHERE UPPER(ColumnName)='SALESPERCENTPRODUCT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-11T11:59:32.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SalesPercentProduct', Name='Anteil von Verkauf (MW)', Description=NULL, Help=NULL WHERE AD_Element_ID=577529 AND IsCentrallyMaintained='Y'
;

-- 2020-02-11T11:59:32.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anteil von Verkauf (MW)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577529) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577529)
;

-- 2020-02-11T11:59:32.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anteil von Verkauf (MW)', Name='Anteil von Verkauf (MW)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577529)
;

-- 2020-02-11T11:59:32.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Anteil von Verkauf (MW)', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577529
;

-- 2020-02-11T11:59:32.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Anteil von Verkauf (MW)', Description=NULL, Help=NULL WHERE AD_Element_ID = 577529
;

-- 2020-02-11T11:59:32.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Anteil von Verkauf (MW)', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577529
;

-- 2020-02-11T12:04:32.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Qty On Hand Stock', IsTranslated='Y', Name='Qty On Hand Stock', PrintName='Qty On Hand Stock',Updated=TO_TIMESTAMP('2020-02-11 14:04:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543686 AND AD_Language='en_US'
;

-- 2020-02-11T12:04:32.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543686,'en_US') 
;


