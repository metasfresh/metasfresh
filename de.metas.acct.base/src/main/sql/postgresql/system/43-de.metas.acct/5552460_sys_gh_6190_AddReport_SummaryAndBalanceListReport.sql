-- 2020-02-18T07:06:31.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,584655,'Y','N',TO_TIMESTAMP('2020-02-18 09:06:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','N','Y','Y',0,'SummaryAndBalanceListReport','N','N','Java',TO_TIMESTAMP('2020-02-18 09:06:30','YYYY-MM-DD HH24:MI:SS'),100,'SummaryAndBalanceListReport')
;

-- 2020-02-18T07:06:31.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=584655 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2020-02-18T07:10:53.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.impexp.excel.process.ExportToExcelProcess', SQLStatement='select * from SummaryAndBalanceListReport(dateFrom, dateTo, c_acctschema_id, ad_org_id)
', Type='Excel',Updated=TO_TIMESTAMP('2020-02-18 09:10:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584655
;

-- 2020-02-18T07:11:19.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1581,0,584655,541725,15,'DateFrom',TO_TIMESTAMP('2020-02-18 09:11:18','YYYY-MM-DD HH24:MI:SS'),100,'Startdatum eines Abschnittes','D',0,'Datum von bezeichnet das Startdatum eines Abschnittes','Y','N','Y','N','N','N','Datum von',10,TO_TIMESTAMP('2020-02-18 09:11:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-18T07:11:19.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541725 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-02-18T07:11:31.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1582,0,584655,541726,15,'DateTo',TO_TIMESTAMP('2020-02-18 09:11:31','YYYY-MM-DD HH24:MI:SS'),100,'Enddatum eines Abschnittes','D',0,'Datum bis bezeichnet das Enddatum eines Abschnittes (inklusiv)','Y','N','Y','N','N','N','Datum bis',20,TO_TIMESTAMP('2020-02-18 09:11:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-18T07:11:31.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541726 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-02-18T07:12:02.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,181,0,584655,541727,18,'C_AcctSchema_ID',TO_TIMESTAMP('2020-02-18 09:12:02','YYYY-MM-DD HH24:MI:SS'),100,'Stammdaten für Buchhaltung','D',0,'Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','N','Y','N','N','N','Buchführungs-Schema',30,TO_TIMESTAMP('2020-02-18 09:12:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-18T07:12:02.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541727 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-02-18T07:12:20.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,113,0,584655,541728,18,'AD_Org_ID',TO_TIMESTAMP('2020-02-18 09:12:20','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','D',0,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','N','Sektion',40,TO_TIMESTAMP('2020-02-18 09:12:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-18T07:12:20.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541728 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-02-18T07:13:53.866Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577545,0,'SummaryAndBalanceListReport',TO_TIMESTAMP('2020-02-18 09:13:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Summary and Balance Report','Summary and Balance Report',TO_TIMESTAMP('2020-02-18 09:13:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-18T07:13:53.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577545 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-02-18T07:14:09.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Summen- und Saldenliste', PrintName='Summen- und Saldenliste',Updated=TO_TIMESTAMP('2020-02-18 09:14:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577545 AND AD_Language='de_CH'
;

-- 2020-02-18T07:14:09.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577545,'de_CH')
;

-- 2020-02-18T07:14:14.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-18 09:14:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577545 AND AD_Language='en_US'
;

-- 2020-02-18T07:14:14.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577545,'en_US')
;

-- 2020-02-18T07:14:18.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Summen- und Saldenliste', PrintName='Summen- und Saldenliste',Updated=TO_TIMESTAMP('2020-02-18 09:14:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577545 AND AD_Language='de_DE'
;

-- 2020-02-18T07:14:18.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577545,'de_DE')
;

-- 2020-02-18T07:14:18.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577545,'de_DE')
;

-- 2020-02-18T07:14:18.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SummaryAndBalanceListReport', Name='Summen- und Saldenliste', Description=NULL, Help=NULL WHERE AD_Element_ID=577545
;

-- 2020-02-18T07:14:18.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SummaryAndBalanceListReport', Name='Summen- und Saldenliste', Description=NULL, Help=NULL, AD_Element_ID=577545 WHERE UPPER(ColumnName)='SUMMARYANDBALANCELISTREPORT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-18T07:14:18.826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SummaryAndBalanceListReport', Name='Summen- und Saldenliste', Description=NULL, Help=NULL WHERE AD_Element_ID=577545 AND IsCentrallyMaintained='Y'
;

-- 2020-02-18T07:14:18.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Summen- und Saldenliste', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577545) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577545)
;

-- 2020-02-18T07:14:18.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Summen- und Saldenliste', Name='Summen- und Saldenliste' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577545)
;

-- 2020-02-18T07:14:18.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Summen- und Saldenliste', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577545
;

-- 2020-02-18T07:14:18.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Summen- und Saldenliste', Description=NULL, Help=NULL WHERE AD_Element_ID = 577545
;

-- 2020-02-18T07:14:18.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Summen- und Saldenliste', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577545
;

-- 2020-02-18T07:17:01.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Summary and Balance List Report', PrintName='Summary and Balance List Report',Updated=TO_TIMESTAMP('2020-02-18 09:17:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577545 AND AD_Language='en_US'
;

-- 2020-02-18T07:17:01.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577545,'en_US')
;

-- 2020-02-18T07:17:18.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,577545,541437,0,584655,TO_TIMESTAMP('2020-02-18 09:17:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','SummaryAndBalanceListReport','Y','N','N','N','N','Summen- und Saldenliste',TO_TIMESTAMP('2020-02-18 09:17:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-18T07:17:18.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541437 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2020-02-18T07:17:18.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541437, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541437)
;

-- 2020-02-18T07:17:18.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(577545)
;

-- 2020-02-18T07:17:18.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000087 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:18.725Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540970 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:18.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540728 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:18.727Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540783 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:18.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541251 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:18.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541250 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:18.730Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540841 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:18.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540868 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:18.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541138 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:18.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540971 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:18.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540949 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:18.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540950 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:18.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540976 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:18.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541395 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:18.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000060 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:18.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541406 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:18.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000068 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:18.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000079 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:18.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541437 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541433 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541434 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541437 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- 2020-02-18T07:17:30.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- 2020-02-18T07:18:14.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET EntityType='de.metas.printing', Name='SummaryAndBalanceListReport',Updated=TO_TIMESTAMP('2020-02-18 09:18:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541437
;

-- 2020-02-18T07:22:13.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='select * from SummaryAndBalanceListReport(@dateFrom@, @dateTo@, @c_acctschema_id@, @ad_org_id@, @C_account_id/null@)',Updated=TO_TIMESTAMP('2020-02-18 09:22:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584655
;

-- 2020-02-18T07:22:39.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,148,0,584655,541729,30,'Account_ID',TO_TIMESTAMP('2020-02-18 09:22:38','YYYY-MM-DD HH24:MI:SS'),100,'Verwendetes Konto','D',0,'Das verwendete (Standard-) Konto','Y','N','Y','N','N','N','Konto',50,TO_TIMESTAMP('2020-02-18 09:22:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-18T07:22:39.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541729 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-02-18T07:22:43.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='select * from SummaryAndBalanceListReport(@dateFrom@, @dateTo@, @c_acctschema_id@, @ad_org_id@, @account_id/null@)',Updated=TO_TIMESTAMP('2020-02-18 09:22:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584655
;

-- 2020-02-18T07:23:03.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='select * from SummaryAndBalanceListReport(@DateFrom@, @dateTo@, @c_acctschema_id@, @ad_org_id@, @account_id/null@)',Updated=TO_TIMESTAMP('2020-02-18 09:23:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584655
;

-- 2020-02-18T07:23:13.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='select * from SummaryAndBalanceListReport(@DateFrom@, @DateTo@, @c_acctschema_id@, @ad_org_id@, @account_id/null@)',Updated=TO_TIMESTAMP('2020-02-18 09:23:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584655
;

-- 2020-02-18T07:23:49.826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='select * from SummaryAndBalanceListReport(@DateFrom@, @DateTo@, @C_AcctSchema_ID@, @AD_Org_ID@, @Account_ID/null@)',Updated=TO_TIMESTAMP('2020-02-18 09:23:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584655
;

-- 2020-02-18T07:24:13.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='select * from SummaryAndBalanceListReport(''@DateFrom@''::date, ''@DateTo@''::date, @C_AcctSchema_ID@, @AD_Org_ID@, @Account_ID/null@)',Updated=TO_TIMESTAMP('2020-02-18 09:24:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584655
;

-- 2020-02-18T07:24:28.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='select * from SummaryAndBalanceListReport(''@DateFrom@''::date, ''@DateTo@''::date, @C_AcctSchema_ID@, @AD_Org_ID@, @Account_ID/null@::NUMERIC)',Updated=TO_TIMESTAMP('2020-02-18 09:24:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584655
;

-- 2020-02-18T07:24:52.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='select * from SummaryAndBalanceListReport(''@DateFrom@''::date, ''@DateTo@''::date, @C_AcctSchema_ID@::NUMERIC, @AD_Org_ID@::NUMERIC, @Account_ID/null@::NUMERIC)',Updated=TO_TIMESTAMP('2020-02-18 09:24:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584655
;

-- 2020-02-18T07:29:44.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577546,0,'Credit',TO_TIMESTAMP('2020-02-18 09:29:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Credit','Credit',TO_TIMESTAMP('2020-02-18 09:29:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-18T07:29:44.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577546 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-02-18T07:29:52.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Haben', PrintName='Haben',Updated=TO_TIMESTAMP('2020-02-18 09:29:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577546 AND AD_Language='de_CH'
;

-- 2020-02-18T07:29:52.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577546,'de_CH')
;

-- 2020-02-18T07:29:55.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Haben', PrintName='Haben',Updated=TO_TIMESTAMP('2020-02-18 09:29:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577546 AND AD_Language='de_DE'
;

-- 2020-02-18T07:29:55.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577546,'de_DE')
;

-- 2020-02-18T07:29:55.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577546,'de_DE')
;

-- 2020-02-18T07:29:55.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Credit', Name='Haben', Description=NULL, Help=NULL WHERE AD_Element_ID=577546
;

-- 2020-02-18T07:29:55.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Credit', Name='Haben', Description=NULL, Help=NULL, AD_Element_ID=577546 WHERE UPPER(ColumnName)='CREDIT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-18T07:29:55.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Credit', Name='Haben', Description=NULL, Help=NULL WHERE AD_Element_ID=577546 AND IsCentrallyMaintained='Y'
;

-- 2020-02-18T07:29:55.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Haben', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577546) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577546)
;

-- 2020-02-18T07:29:55.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Haben', Name='Haben' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577546)
;

-- 2020-02-18T07:29:55.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Haben', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577546
;

-- 2020-02-18T07:29:55.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Haben', Description=NULL, Help=NULL WHERE AD_Element_ID = 577546
;

-- 2020-02-18T07:29:55.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Haben', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577546
;

-- 2020-02-18T07:29:57.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-18 09:29:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577546 AND AD_Language='en_US'
;

-- 2020-02-18T07:29:57.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577546,'en_US')
;

-- 2020-02-18T07:30:18.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577547,0,'Debit',TO_TIMESTAMP('2020-02-18 09:30:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Debit','Debit',TO_TIMESTAMP('2020-02-18 09:30:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-18T07:30:18.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577547 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-02-18T07:30:32.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Soll', PrintName='Soll',Updated=TO_TIMESTAMP('2020-02-18 09:30:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577547 AND AD_Language='de_CH'
;

-- 2020-02-18T07:30:32.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577547,'de_CH')
;

-- 2020-02-18T07:30:35.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Soll', PrintName='Soll',Updated=TO_TIMESTAMP('2020-02-18 09:30:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577547 AND AD_Language='de_DE'
;

-- 2020-02-18T07:30:35.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577547,'de_DE')
;

-- 2020-02-18T07:30:35.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577547,'de_DE')
;

-- 2020-02-18T07:30:35.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Debit', Name='Soll', Description=NULL, Help=NULL WHERE AD_Element_ID=577547
;

-- 2020-02-18T07:30:35.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Debit', Name='Soll', Description=NULL, Help=NULL, AD_Element_ID=577547 WHERE UPPER(ColumnName)='DEBIT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-18T07:30:35.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Debit', Name='Soll', Description=NULL, Help=NULL WHERE AD_Element_ID=577547 AND IsCentrallyMaintained='Y'
;

-- 2020-02-18T07:30:35.980Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Soll', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577547) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577547)
;

-- 2020-02-18T07:30:35.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Soll', Name='Soll' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577547)
;

-- 2020-02-18T07:30:35.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Soll', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577547
;

-- 2020-02-18T07:30:35.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Soll', Description=NULL, Help=NULL WHERE AD_Element_ID = 577547
;

-- 2020-02-18T07:30:35.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Soll', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577547
;

-- 2020-02-18T07:30:37.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-18 09:30:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577547 AND AD_Language='en_US'
;

-- 2020-02-18T07:30:37.681Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577547,'en_US')
;

-- 2020-02-18T07:30:55.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='select * from SummaryAndBalanceListReport(''@DateFrom@''::date, ''@DateTo@''::date, @C_AcctSchema_ID@, @AD_Org_ID@, @Account_ID/null@)',Updated=TO_TIMESTAMP('2020-02-18 09:30:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584655
;





DROP FUNCTION IF EXISTS de_metas_acct.acctBalanceToDate(p_Account_ID numeric, p_C_AcctSchema_ID numeric, p_DateAcct date, p_IncludePostingTypeStatistical char);

DROP FUNCTION IF EXISTS de_metas_acct.acctBalanceToDate(p_Account_ID numeric, p_C_AcctSchema_ID numeric, p_DateAcct date, ad_org_id numeric, p_IncludePostingTypeStatistical char(1));
DROP FUNCTION IF EXISTS de_metas_acct.acctBalanceToDate(p_Account_ID numeric, p_C_AcctSchema_ID numeric, p_DateAcct date, ad_org_id numeric, p_IncludePostingTypeStatistical char(1), p_ExcludePostingTypeYearEnd char(1));

/*

-- This type shall be already in the database. Do not create it again

CREATE TYPE de_metas_acct.BalanceAmt AS
(
	Balance numeric
    , Debit numeric
    , Credit numeric
);

 */
CREATE OR REPLACE FUNCTION de_metas_acct.acctBalanceToDate(p_Account_ID numeric,
                                                           p_C_AcctSchema_ID numeric,
                                                           p_DateAcct date,
                                                           p_AD_Org_ID numeric(10, 0),
                                                           p_IncludePostingTypeStatistical char(1) = 'N',
                                                           p_ExcludePostingTypeYearEnd char(1) = 'N')
    RETURNS de_metas_acct.BalanceAmt
AS
$BODY$
WITH filteredAndOrdered AS (
    SELECT --
           fas.PostingType,
           ev.AccountType,
           fas.AmtAcctCr,
           fas.AmtAcctCr_YTD,
           fas.AmtAcctDr,
           fas.AmtAcctDr_YTD,
           fas.DateAcct
    FROM Fact_Acct_Summary fas
             INNER JOIN C_ElementValue ev ON (ev.C_ElementValue_ID = fas.Account_ID) AND ev.isActive = 'Y'
    WHERE TRUE
      AND fas.Account_ID = $1      -- p_Account_ID
      AND fas.C_AcctSchema_ID = $2 -- p_C_AcctSchema_ID
      AND fas.PA_ReportCube_ID IS NULL
      AND fas.DateAcct <= $3       -- p_DateAcct
      AND fas.ad_org_id = $4       -- p_AD_Org_ID
      AND fas.isActive = 'Y'
    ORDER BY fas.DateAcct DESC
)
-- NOTE: we use COALESCE(SUM(..)) just to make sure we are not returning null
SELECT ROW (SUM((Balance).Balance), SUM((Balance).Debit), SUM((Balance).Credit))::de_metas_acct.BalanceAmt
FROM (
         -- Include posting type Actual
         (
             SELECT (CASE
                 -- When the account is Expense/Revenue => we shall consider only the Year to Date amount
                         WHEN fo.AccountType IN ('E', 'R') AND fo.DateAcct >= date_trunc('year', $3) THEN ROW (fo.AmtAcctDr_YTD - fo.AmtAcctCr_YTD, fo.AmtAcctDr_YTD, fo.AmtAcctCr_YTD)::de_metas_acct.BalanceAmt
                         WHEN fo.AccountType IN ('E', 'R') THEN ROW (0, 0, 0)::de_metas_acct.BalanceAmt
                 -- For any other account => we consider from the beginning to Date amount
                         ELSE ROW (fo.AmtAcctDr - fo.AmtAcctCr, fo.AmtAcctDr, fo.AmtAcctCr)::de_metas_acct.BalanceAmt
                 END) AS Balance
             FROM filteredAndOrdered fo
             WHERE TRUE
               AND fo.PostingType = 'A'
             ORDER BY fo.DateAcct DESC
             LIMIT 1
         )
         -- Include posting type Year End
         UNION ALL
         (
             SELECT (CASE
                 -- When the account is Expense/Revenue => we shall consider only the Year to Date amount
                         WHEN fo.AccountType IN ('E', 'R') AND fo.DateAcct >= date_trunc('year', $3) THEN ROW (fo.AmtAcctDr_YTD - fo.AmtAcctCr_YTD, fo.AmtAcctDr_YTD, fo.AmtAcctCr_YTD)::de_metas_acct.BalanceAmt
                         WHEN fo.AccountType IN ('E', 'R') THEN ROW (0, 0, 0)::de_metas_acct.BalanceAmt
                 -- For any other account => we consider from the beginning to Date amount
                         ELSE ROW (fo.AmtAcctDr - fo.AmtAcctCr, fo.AmtAcctDr, fo.AmtAcctCr)::de_metas_acct.BalanceAmt
                 END) AS Balance
             FROM filteredAndOrdered fo
             WHERE TRUE
               AND $6 = 'N' -- p_ExcludePostingTypeYearEnd
               AND fo.PostingType = 'Y'
             ORDER BY fo.DateAcct DESC
             LIMIT 1
         )
         -- Include posting type Statistical
         UNION ALL
         (
             SELECT (CASE
                 -- When the account is Expense/Revenue => we shall consider only the Year to Date amount
                         WHEN fo.AccountType IN ('E', 'R') AND fo.DateAcct >= date_trunc('year', $3) THEN ROW (fo.AmtAcctDr_YTD - fo.AmtAcctCr_YTD, fo.AmtAcctDr_YTD, fo.AmtAcctCr_YTD)::de_metas_acct.BalanceAmt
                         WHEN fo.AccountType IN ('E', 'R') THEN ROW (0, 0, 0)::de_metas_acct.BalanceAmt
                 -- For any other account => we consider from the beginning to Date amount
                         ELSE ROW (fo.AmtAcctDr - fo.AmtAcctCr, fo.AmtAcctDr, fo.AmtAcctCr)::de_metas_acct.BalanceAmt
                 END) AS Balance
             FROM filteredAndOrdered fo
             WHERE TRUE
               AND $5 = 'Y' -- p_IncludePostingTypeStatistical
               AND fo.PostingType = 'S'
             ORDER BY fo.DateAcct DESC
             LIMIT 1
         )
         -- Default value:
         UNION ALL
         (
             SELECT ROW (0, 0, 0)::de_metas_acct.BalanceAmt
         )
     ) t
$BODY$
    LANGUAGE sql STABLE;









DROP FUNCTION IF EXISTS SummaryAndBalanceListReport(p_dateFrom date, p_dateTo date, p_c_acctschema_id NUMERIC, p_ad_org_id numeric, p_account_id NUMERIC, p_c_activity_id NUMERIC);
DROP FUNCTION IF EXISTS SummaryAndBalanceListReport(p_dateFrom date, p_dateTo date, p_c_acctschema_id NUMERIC, p_ad_org_id numeric, p_account_id NUMERIC);



CREATE OR REPLACE FUNCTION SummaryAndBalanceListReport(p_dateFrom date,
                                                       p_dateTo date,
                                                       p_c_acctschema_id NUMERIC,
                                                       p_ad_org_id numeric,
                                                       p_account_id NUMERIC=NULL)
    RETURNS table
            (
                AccountValue     text,
                AccountName      text,
                beginningBalance numeric,
                debit            numeric,
                credit           numeric,
                endingBalance    numeric
            )
AS
$$
WITH filteredElementValues AS
         (
             SELECT ev.c_elementvalue_id,
                    ev.name  AccountName,
                    ev.value AccountValue
             FROM c_elementvalue ev
             WHERE TRUE
               AND (p_account_id IS NULL OR ev.c_elementvalue_id = p_account_id)
             ORDER BY ev.c_elementvalue_id
         ),
     balances AS
         (
             SELECT --
                    (de_metas_acct.acctBalanceToDate(ev.c_elementvalue_id, p_c_acctschema_id, (p_dateFrom - INTERVAL '1 day')::date, p_ad_org_id)::de_metas_acct.BalanceAmt) begining,
                    (de_metas_acct.acctBalanceToDate(ev.c_elementvalue_id, p_c_acctschema_id, (p_dateTo)::date, p_ad_org_id)::de_metas_acct.BalanceAmt)                      ending,
                    ev.c_elementvalue_id,
                    ev.AccountName,
                    ev.AccountValue
             FROM filteredElementValues ev
         ),
     data AS
         (
             SELECT --
                    AccountValue,
                    AccountName,
                    (begining).balance                  beginningBalance,
                    (ending).debit - (begining).debit   debit,
                    (ending).credit - (begining).credit credit,
                    (ending).balance                    endingBalance
             FROM balances
         )
SELECT *
FROM data
$$
    LANGUAGE sql STABLE;
