-- 2019-03-21T14:20:15.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,541083,'N','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2019-03-21 14:20:14','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','N','Y','N','Y','N','Y','Y','@PREFIX@de/metas/fonts/report_fonts.jasper',0,'Display Fonts','N','N','JasperReports',TO_TIMESTAMP('2019-03-21 14:20:14','YYYY-MM-DD HH24:MI:SS'),100,'DisplayFonts')
;

-- 2019-03-21T14:20:15.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541083 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-03-21T14:20:22.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET EntityType='D',Updated=TO_TIMESTAMP('2019-03-21 14:20:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541083
;

-- 2019-03-21T14:23:34.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576291,0,TO_TIMESTAMP('2019-03-21 14:23:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Display Fonts','Display Fonts',TO_TIMESTAMP('2019-03-21 14:23:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-21T14:23:34.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576291 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-21T14:24:16.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,576291,541227,0,541083,TO_TIMESTAMP('2019-03-21 14:24:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.datev','DisplayFonts','Y','N','N','N','N','Display Fonts',TO_TIMESTAMP('2019-03-21 14:24:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-21T14:24:16.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541227 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-03-21T14:24:16.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541227, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541227)
;

-- 2019-03-21T14:24:16.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(576291) 
;

-- 2019-03-21T14:24:25.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET EntityType='D',Updated=TO_TIMESTAMP('2019-03-21 14:24:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541227
;

-- 2019-03-21T14:25:49.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000007 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=218 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540478 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=153 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=263 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=166 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=203 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540627 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540281 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53242 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=236 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=183 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.504
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=160 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=278 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=345 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53014 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540016 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540613 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.509
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53083 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.509
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53108 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=518 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=519 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.511
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000004 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.511
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000001 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000000 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000002 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540029 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540028 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540030 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540031 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540034 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540024 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540004 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540015 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540066 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540043 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540048 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540052 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540058 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540077 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540060 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540076 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540075 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540074 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540073 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540062 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540070 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540068 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540065 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540067 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540083 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540136 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540106 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540090 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540109 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540119 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540120 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540103 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540098 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540105 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540092 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540139 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540187 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540184 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540147 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540185 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540167 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540234 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540148 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540228 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540237 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540225 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540244 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541227 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540269 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540246 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540261 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540404 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540270 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540252 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540400 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540253 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540264 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540443 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540265 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.534
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540238 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.534
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540512 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.534
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540544 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540529 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540517 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540518 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540440 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540559 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=94, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540608 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=95, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540587 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=96, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540570 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=97, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540576 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=98, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540656 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=99, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540649 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=100, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=101, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000005 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=102, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540886 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=103, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540997 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=104, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540967 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:49.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=105, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540987 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000007 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=218 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540478 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=153 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=263 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=166 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=203 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540627 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540281 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53242 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=236 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=183 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=160 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=278 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=345 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53014 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540016 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540613 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53083 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.925
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53108 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=518 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=519 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.926
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000004 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000001 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000000 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000002 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540029 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540028 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540030 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540031 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540034 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.930
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540024 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.930
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540004 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.930
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540015 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540066 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540043 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540048 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540052 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540058 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.932
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540077 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540060 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540076 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540075 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540074 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540073 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540062 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540070 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540068 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540065 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540067 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540083 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540136 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540106 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540090 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540109 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540119 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540120 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540103 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540098 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540105 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540092 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540139 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540187 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540184 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540147 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540185 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541227 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540167 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540234 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540148 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540228 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540237 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540225 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540244 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540269 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540246 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540261 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540404 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540270 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540252 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540400 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540253 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540264 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540443 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540265 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540238 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540512 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540544 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540529 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540517 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540518 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540440 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540559 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=94, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540608 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=95, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540587 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=96, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540570 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=97, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540576 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=98, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540656 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=99, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540649 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=100, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=101, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000005 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=102, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540886 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=103, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540997 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=104, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540967 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:51.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=105, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540987 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000007 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=218 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540478 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=153 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=263 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=166 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=203 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540627 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540281 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53242 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=236 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=183 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=160 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=278 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=345 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53014 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540016 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540613 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53083 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53108 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=518 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=519 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.143
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000004 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000001 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000000 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000002 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540029 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540028 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540030 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540031 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540034 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540024 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540004 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540015 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540066 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540043 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540048 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.151
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540052 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540058 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540077 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.153
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540060 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.153
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540076 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540075 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.154
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540074 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540073 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540062 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540070 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540068 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540065 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540067 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540083 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540136 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540106 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540090 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540109 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540119 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540120 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540103 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541227 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540098 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540105 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540092 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540139 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540187 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540184 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540147 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540185 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540167 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.167
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540234 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540148 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540228 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540237 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540225 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540244 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540269 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540246 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540261 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540404 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540270 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540252 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540400 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540253 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540264 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540443 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540265 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540238 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540512 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540544 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540529 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540517 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540518 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540440 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540559 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=94, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540608 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=95, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540587 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=96, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540570 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=97, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540576 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=98, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540656 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=99, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540649 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=100, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=101, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000005 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=102, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540886 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=103, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540997 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=104, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540967 AND AD_Tree_ID=10
;

-- 2019-03-21T14:25:55.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=105, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540987 AND AD_Tree_ID=10
;

-- 2019-03-21T14:26:05.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541227 AND AD_Tree_ID=10
;

-- 2019-03-21T14:26:05.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- 2019-03-21T14:26:05.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- 2019-03-21T14:26:05.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- 2019-03-21T14:26:05.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- 2019-03-21T14:26:05.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- 2019-03-21T14:26:05.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- 2019-03-21T14:26:05.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- 2019-03-21T14:26:05.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- 2019-03-21T14:26:05.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- 2019-03-21T14:26:05.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- 2019-03-21T14:26:05.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- 2019-03-21T14:26:05.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- 2019-03-21T14:26:05.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- 2019-03-21T14:26:05.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- 2019-03-21T14:26:05.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- 2019-03-21T14:26:05.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- 2019-03-21T14:26:05.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- 2019-03-21T14:26:05.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541085 AND AD_Tree_ID=10
;

-- 2019-03-21T14:26:05.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540861 AND AD_Tree_ID=10
;

-- 2019-03-21T14:26:15.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541085, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541227 AND AD_Tree_ID=10
;

-- 2019-03-21T14:26:15.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541085, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541087 AND AD_Tree_ID=10
;

