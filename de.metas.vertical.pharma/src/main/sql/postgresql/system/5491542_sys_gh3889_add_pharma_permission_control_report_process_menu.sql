-- 2018-04-20T18:18:34.163
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,JasperReport,JasperReport_Tabular,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540952,'Y','org.compiere.report.ReportStarter','N',TO_TIMESTAMP('2018-04-20 18:18:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','N','N','N','N','Y','N','Y','@PREFIX@de/metas/reports/pharma_permission_authorisation_control/report.jasper','',0,'Pharma Permission/ Authorisation control','N','Y','Java',TO_TIMESTAMP('2018-04-20 18:18:33','YYYY-MM-DD HH24:MI:SS'),100,'Pharma Permission/ Authorisation control (Jasper)')
;

-- 2018-04-20T18:18:34.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540952 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2018-04-20T18:19:13.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,187,0,540952,541290,30,'C_BPartner_ID',TO_TIMESTAMP('2018-04-20 18:19:13','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','de.metas.vertical.pharma',0,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','Geschäftspartner',10,TO_TIMESTAMP('2018-04-20 18:19:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-20T18:19:13.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541290 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-04-20T18:19:18.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsAutocomplete='Y',Updated=TO_TIMESTAMP('2018-04-20 18:19:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541290
;

-- 2018-04-20T18:32:23.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,364,0,540952,541291,17,540528,'IsCustomer',TO_TIMESTAMP('2018-04-20 18:32:23','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an, ob dieser Geschäftspartner ein Kunde ist','de.metas.vertical.pharma',0,'The Customer checkbox indicates if this Business Partner is a customer.  If it is select additional fields will display which further define this customer.','Y','N','Y','N','N','N','Kunde',20,TO_TIMESTAMP('2018-04-20 18:32:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-20T18:32:23.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541291 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-04-20T18:33:28.041
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543966,0,540952,541292,17,540528,'IsPharmaManufacturerPermission',TO_TIMESTAMP('2018-04-20 18:33:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',0,'Y','N','Y','N','N','N','Herstellererlaubnis §13 AMG',30,TO_TIMESTAMP('2018-04-20 18:33:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-20T18:33:28.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541292 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-04-20T18:33:56.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543965,0,540952,541293,17,540528,'IsPharmaWholesalePermission',TO_TIMESTAMP('2018-04-20 18:33:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',0,'Y','N','Y','N','N','N','Großhandelserlaubnis §52a AMG',40,TO_TIMESTAMP('2018-04-20 18:33:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-20T18:33:56.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541293 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-04-20T18:34:21.026
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543967,0,540952,541294,17,540528,'IsPharmaAgentPermission',TO_TIMESTAMP('2018-04-20 18:34:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',0,'Y','N','Y','N','N','N','Arzneivermittler §52c Abs.1-3 AMG',50,TO_TIMESTAMP('2018-04-20 18:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-20T18:34:21.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541294 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-04-20T18:34:33.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543483,0,540952,541295,17,540528,'IsPharmaciePermission',TO_TIMESTAMP('2018-04-20 18:34:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',0,'Y','N','Y','N','N','N','Pharmacie Permission',60,TO_TIMESTAMP('2018-04-20 18:34:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-20T18:34:33.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541295 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-04-20T18:34:57.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543968,0,540952,541296,17,540528,'IsVeterinaryPharmacyPermission',TO_TIMESTAMP('2018-04-20 18:34:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma',0,'Y','N','Y','N','N','N','Tierärtzliche Hausapotheke §67 ApoG',70,TO_TIMESTAMP('2018-04-20 18:34:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-20T18:34:57.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541296 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-04-20T18:36:19.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('R',0,541075,0,540952,TO_TIMESTAMP('2018-04-20 18:36:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Pharma Permission/ Authorisation control (Jasper)','Y','N','N','N','N','Pharma Permission/ Authorisation control',TO_TIMESTAMP('2018-04-20 18:36:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-20T18:36:19.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541075 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-04-20T18:36:19.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541075, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541075)
;

-- 2018-04-20T18:36:19.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000063, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541075 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540614 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.038
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540750 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.038
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540616 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.039
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540681 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.039
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540617 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540742 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540618 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.041
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540619 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540632 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540635 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540698 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540637 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540638 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540648 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.046
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540674 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.046
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540709 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541075 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540708 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540679 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540684 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540686 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540706 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540701 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540712 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540722 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540723 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540725 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540739 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540740 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540741 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540968 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:29.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541064 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540614 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540750 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540616 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540681 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540617 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540742 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540618 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540619 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540632 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540635 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540698 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540637 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540638 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540648 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540674 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540709 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540708 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540679 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540678 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540684 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540686 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540706 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540701 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540712 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540722 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540723 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540725 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540739 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540740 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540741 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540968 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541064 AND AD_Tree_ID=10
;

-- 2018-04-20T18:36:33.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=540613, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541075 AND AD_Tree_ID=10
;

-- 2018-04-20T18:38:21.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (AD_Client_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,541076,0,TO_TIMESTAMP('2018-04-20 18:38:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Berichte_99','Y','N','N','N','Y','Berichte',TO_TIMESTAMP('2018-04-20 18:38:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-20T18:38:21.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541076 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-04-20T18:38:21.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541076, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541076)
;

-- 2018-04-20T18:38:22.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541055 AND AD_Tree_ID=10
;

-- 2018-04-20T18:38:22.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540992 AND AD_Tree_ID=10
;

-- 2018-04-20T18:38:22.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540993 AND AD_Tree_ID=10
;

-- 2018-04-20T18:38:22.256
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540999 AND AD_Tree_ID=10
;

-- 2018-04-20T18:38:22.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541022 AND AD_Tree_ID=10
;

-- 2018-04-20T18:38:22.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541076 AND AD_Tree_ID=10
;

-- 2018-04-20T18:38:31.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET WEBUI_NameBrowse='Berichte',Updated=TO_TIMESTAMP('2018-04-20 18:38:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541076
;

-- 2018-04-20T18:38:46.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 18:38:46','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Reports',WEBUI_NameBrowse='Reports' WHERE AD_Menu_ID=541076 AND AD_Language='en_US'
;

-- 2018-04-20T18:41:54.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Liefer-/Bezugsberechtigung Kontrolle', Value='Liefer-/Bezugsberechtigung Kontrolle (Jasper)',Updated=TO_TIMESTAMP('2018-04-20 18:41:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540952
;

-- 2018-04-20T18:41:54.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Liefer-/Bezugsberechtigung Kontrolle',Updated=TO_TIMESTAMP('2018-04-20 18:41:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541075
;

-- 2018-04-20T18:42:00.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 18:42:00','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Liefer-/Bezugsberechtigung Kontrolle' WHERE AD_Process_ID=540952 AND AD_Language='de_CH'
;

-- 2018-04-20T18:42:04.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 18:42:04','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Process_ID=540952 AND AD_Language='en_US'
;

-- 2018-04-20T18:42:15.839
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 18:42:15','YYYY-MM-DD HH24:MI:SS'),Name='Liefer-/Bezugsberechtigung Kontrolle' WHERE AD_Process_ID=540952 AND AD_Language='nl_NL'
;

-- 2018-04-20T18:42:53.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('R',0,541077,0,540952,TO_TIMESTAMP('2018-04-20 18:42:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Liefer-/Bezugsberechtigung Kontrolle (Jasper)','Y','N','N','N','N','Liefer-/Bezugsberechtigung Kontrolle',TO_TIMESTAMP('2018-04-20 18:42:53','YYYY-MM-DD HH24:MI:SS'),100,'Liefer-/Bezugsberechtigung Kontrolle')
;

-- 2018-04-20T18:42:53.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541077 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-04-20T18:42:53.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541077, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541077)
;

-- 2018-04-20T18:42:53.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000063, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541077 AND AD_Tree_ID=10
;

-- 2018-04-20T18:42:56.712
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541076, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541077 AND AD_Tree_ID=10
;

-- 2018-04-20T18:43:20.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 18:43:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Pharma Permission/ Authorisation control',WEBUI_NameBrowse='Pharma Permission/ Authorisation control' WHERE AD_Menu_ID=541077 AND AD_Language='en_US'
;

-- 2018-04-20T18:43:56.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 18:43:56','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Menu_ID=541075 AND AD_Language='en_US'
;

-- 2018-04-20T18:44:05.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 18:44:05','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Liefer-/Bezugsberechtigung Kontrolle' WHERE AD_Menu_ID=541075 AND AD_Language='de_CH'
;

-- 2018-04-20T18:44:09.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-20 18:44:09','YYYY-MM-DD HH24:MI:SS'),Name='Liefer-/Bezugsberechtigung Kontrolle' WHERE AD_Menu_ID=541075 AND AD_Language='nl_NL'
;

