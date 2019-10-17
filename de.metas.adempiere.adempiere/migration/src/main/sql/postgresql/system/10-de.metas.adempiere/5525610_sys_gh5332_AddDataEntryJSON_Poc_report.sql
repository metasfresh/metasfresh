-- 2019-06-20T15:35:45.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,541154,'Y','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2019-06-20 15:35:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','Y','N','Y','Y',0,'Products Data Entry (Jasper)','N','N','JasperReportsSQL',TO_TIMESTAMP('2019-06-20 15:35:45','YYYY-MM-DD HH24:MI:SS'),100,'ProductsDataEntry(Jasper)')
;

-- 2019-06-20T15:35:45.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541154 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-06-20T15:36:07.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Type='JasperReportsJSON',Updated=TO_TIMESTAMP('2019-06-20 15:36:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541154
;


-- 2019-06-20T15:44:25.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Type='JasperReportsSQL',Updated=TO_TIMESTAMP('2019-06-20 15:44:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541154
;

-- 2019-06-20T15:44:37.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Type='JasperReportsJSON',Updated=TO_TIMESTAMP('2019-06-20 15:44:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541154
;



-- 2019-06-20T15:49:55.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.report.jasper.client.process.JasperReportStarter', Type='JasperReports with SQL',Updated=TO_TIMESTAMP('2019-06-20 15:49:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541154
;



-- 2019-06-20T15:56:19.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.report.jasper.client.process.JasperReportStarter', Type='JasperReportsSQL',Updated=TO_TIMESTAMP('2019-06-20 15:56:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541154
;




-- 2019-06-20T16:06:19.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.report.jasper.client.process.JasperReportStarter', JSONPath='/dataentry/byId/540573/@M_Product_ID/-1@', Type='JasperReportsJSON',Updated=TO_TIMESTAMP('2019-06-20 16:06:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541154
;

-- 2019-06-20T16:29:09.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET JasperReport='@PREFIX@de/metas/docs/label/dataentry/report_cu.jasper',Updated=TO_TIMESTAMP('2019-06-20 16:29:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541154
;





----- ADD IT IN THE MENU -------------



-- 2019-06-20T18:28:04.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576865,0,TO_TIMESTAMP('2019-06-20 18:28:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','ProductsDataEntry(Jasper)','Products Data Entry (Jasper)',TO_TIMESTAMP('2019-06-20 18:28:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-06-20T18:28:04.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576865 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-06-20T18:28:15.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('R',0,576865,541293,0,541154,TO_TIMESTAMP('2019-06-20 18:28:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.processing','ProductsDataEntry(Jasper)','Y','N','N','N','N','ProductsDataEntry(Jasper)',TO_TIMESTAMP('2019-06-20 18:28:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-06-20T18:28:15.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541293 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-06-20T18:28:15.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541293, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541293)
;

-- 2019-06-20T18:28:16.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(576865) 
;

-- 2019-06-20T18:28:23.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET WEBUI_NameBrowse='ProductsDataEntry(Jasper)',Updated=TO_TIMESTAMP('2019-06-20 18:28:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541293
;



-----------------------



-- 2019-06-21T17:28:44.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET JasperReport='@PREFIX@de/metas/docs/label/dataentry/MAIN_JSON_POC.jasper',Updated=TO_TIMESTAMP('2019-06-21 17:28:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541154
;




-----------------------


-- 2019-06-20T19:45:32.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541293 AND AD_Tree_ID=10
;

-- 2019-06-20T19:45:32.712
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- 2019-06-20T19:45:32.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- 2019-06-20T19:45:32.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- 2019-06-20T19:45:32.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- 2019-06-20T19:45:32.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- 2019-06-20T19:45:32.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- 2019-06-20T19:45:32.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- 2019-06-20T19:45:32.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- 2019-06-20T19:45:32.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- 2019-06-20T19:45:32.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- 2019-06-20T19:45:32.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- 2019-06-20T19:45:32.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- 2019-06-20T19:45:32.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- 2019-06-20T19:45:32.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- 2019-06-20T19:45:32.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- 2019-06-20T19:45:32.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- 2019-06-20T19:45:32.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- 2019-06-20T19:45:32.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- 2019-06-20T19:45:32.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541085 AND AD_Tree_ID=10
;

-- 2019-06-20T19:45:32.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540861 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:46.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000034 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:46.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541293 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:46.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541235 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:46.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000089 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:46.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541151 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:46.630
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540840 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:46.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540990 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:46.633
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000092 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:46.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541175 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:46.635
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000093 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:46.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541230 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:46.637
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541062 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:46.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541115 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:46.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541116 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:46.641
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541126 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:46.642
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541127 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:46.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541194 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:46.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000044 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:46.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000035 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:46.656
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000037 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:48.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541293 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:48.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000034 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:48.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541235 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:48.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000089 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:48.321
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541151 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:48.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540840 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:48.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540990 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:48.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000092 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:48.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541175 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:48.327
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000093 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:48.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541230 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:48.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541062 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:48.330
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541115 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:48.331
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541116 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:48.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541126 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:48.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541127 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:48.335
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541194 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:48.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000044 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:48.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000035 AND AD_Tree_ID=10
;

-- 2019-06-20T19:47:48.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000037 AND AD_Tree_ID=10
;

-- 2019-06-21T17:24:33.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET JSONPath='/dataentry/byId/140/@M_Product_ID@',Updated=TO_TIMESTAMP('2019-06-21 17:24:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541154
;


-- 2019-06-24T16:23:59.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET JasperReport='@PREFIX@de/metas/docs/label/dataentry/JSON_POC_Report.jasper',Updated=TO_TIMESTAMP('2019-06-24 16:23:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541154
;


-- 2019-06-24T16:33:09.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement=null,Updated=TO_TIMESTAMP('2019-06-24 16:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541154
;


-- Add it to M_product

-- 2019-06-24T17:08:17.656
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,541154,208,540725,TO_TIMESTAMP('2019-06-24 17:08:17','YYYY-MM-DD HH24:MI:SS'),100,'U','Y',TO_TIMESTAMP('2019-06-24 17:08:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;
