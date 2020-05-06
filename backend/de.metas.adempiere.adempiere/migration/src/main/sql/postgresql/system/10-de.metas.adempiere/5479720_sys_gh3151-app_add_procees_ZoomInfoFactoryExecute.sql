-- 2017-12-06T18:03:04.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,540893,'Y','de.metas.zoom.process.ZoomInfoFactoryExecute','N',TO_TIMESTAMP('2017-12-06 18:03:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y',0,'ZoomInfoFactoryExecute','N','Y','Java',TO_TIMESTAMP('2017-12-06 18:03:04','YYYY-MM-DD HH24:MI:SS'),100,'Diagnose ZoomInfoFactory')
;

-- 2017-12-06T18:03:04.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540893 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-12-06T18:04:53.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,143,0,540893,541241,30,'AD_Window_ID',TO_TIMESTAMP('2017-12-06 18:04:53','YYYY-MM-DD HH24:MI:SS'),100,'Eingabe- oder Anzeige-Fenster','U',0,'Das Feld "Fenster" identifiziert ein bestimmtes Fenster im system.','Y','N','Y','N','N','N','Fenster',10,TO_TIMESTAMP('2017-12-06 18:04:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-06T18:04:53.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541241 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2017-12-06T18:05:00.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2017-12-06 18:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541241
;

-- 2017-12-06T18:05:16.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,126,0,540893,541242,30,'AD_Table_ID',TO_TIMESTAMP('2017-12-06 18:05:16','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information','U',0,'The Database Table provides the information of the table definition','Y','N','Y','N','Y','N','DB-Tabelle',20,TO_TIMESTAMP('2017-12-06 18:05:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-06T18:05:16.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541242 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2017-12-06T18:05:21.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2017-12-06 18:05:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541242
;

-- 2017-12-06T18:05:36.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,538,0,540893,541243,11,'Record_ID',TO_TIMESTAMP('2017-12-06 18:05:36','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID','D',0,'The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','Y','N','Y','N','Datensatz-ID',30,TO_TIMESTAMP('2017-12-06 18:05:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-06T18:05:36.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541243 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2017-12-06T18:08:10.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2017-12-06 18:08:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541241
;

-- 2017-12-06T18:09:29.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Diagnose ZoomInfoFactory', Value='ZoomInfoFactoryExecute',Updated=TO_TIMESTAMP('2017-12-06 18:09:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540893
;

-- 2017-12-06T18:09:51.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,540994,0,540893,TO_TIMESTAMP('2017-12-06 18:09:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','ZoomInfoFactoryExecute','Y','N','N','N','N','Diagnose ZoomInfoFactory',TO_TIMESTAMP('2017-12-06 18:09:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-12-06T18:09:51.084
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540994 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-12-06T18:09:51.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540994, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540994)
;

-- 2017-12-06T18:09:51.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53203 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=586 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53251 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=138 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=139 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=249 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=141 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53455 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=216 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.665
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=589 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=140 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=300 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=142 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=295 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53012 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=143 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.670
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=201 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=176 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53086 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=239 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=517 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=499 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53221 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53222 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53089 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53090 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53267 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53568 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540037 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540492 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53266 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540735 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540575 AND AD_Tree_ID=10
;

-- 2017-12-06T18:09:51.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540994 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53203 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=586 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53251 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540994 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.855
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=138 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=139 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.857
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=249 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=141 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.859
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53455 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=216 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=589 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=140 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=300 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.868
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=142 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=295 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53012 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=143 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=201 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=176 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53086 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=239 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=517 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=499 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53221 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53222 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53089 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53090 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53267 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53568 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540037 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540492 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53266 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540735 AND AD_Tree_ID=10
;

-- 2017-12-06T18:10:01.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=153, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540575 AND AD_Tree_ID=10
;

