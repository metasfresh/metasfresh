-- 2022-05-25T13:16:10.785Z
UPDATE AD_Process_Trl SET Name='Vergleichsbericht aktuelle mit nächster Preisliste',Updated=TO_TIMESTAMP('2022-05-25 16:16:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584762
;

-- 2022-05-25T13:16:12.731Z
UPDATE AD_Process SET Description='', Help=NULL, Name='Vergleichsbericht aktuelle mit nächster Preisliste',Updated=TO_TIMESTAMP('2022-05-25 16:16:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584762
;

-- 2022-05-25T13:16:12.688Z
UPDATE AD_Process_Trl SET Name='Vergleichsbericht aktuelle mit nächster Preisliste',Updated=TO_TIMESTAMP('2022-05-25 16:16:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584762
;

-- 2022-05-25T13:16:27.411Z
UPDATE AD_Process_Trl SET Name='Vergleichsbericht aktuelle mit nächster Preisliste',Updated=TO_TIMESTAMP('2022-05-25 16:16:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584762
;

-- 2022-05-25T13:18:04.555Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,JasperReport,JasperReport_Tabular,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585061,'N','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2022-05-25 16:18:04','YYYY-MM-DD HH24:MI:SS'),100,'','U','Y','N','N','Y','Y','N','N','Y','N','N','@PREFIX@de/metas/reports/pricelist/report_NextVsAfterNextPricelistComparison.jasper','@PREFIX@de/metas/reports/pricelist/report_NextVsAfterNextPricelistComparison.jasper',0,'Vergleichsbericht nächste mit übernächster Preisliste','json','N','Y','','JasperReportsSQL',TO_TIMESTAMP('2022-05-25 16:18:04','YYYY-MM-DD HH24:MI:SS'),100,'NextVsAfterNextPricelistComparison')
;

-- 2022-05-25T13:18:04.557Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585061 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2022-05-25T13:18:48.871Z
UPDATE AD_Process_Trl SET Name='Next vs AfterNext Pricelist Comparison',Updated=TO_TIMESTAMP('2022-05-25 16:18:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585061
;

-- 2022-05-25T13:19:30.695Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,187,0,585061,542263,30,'C_BPartner_ID',TO_TIMESTAMP('2022-05-25 16:19:30','YYYY-MM-DD HH24:MI:SS'),100,'@C_BPartner_ID@','Bezeichnet einen Geschäftspartner','U',0,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','Geschäftspartner',10,TO_TIMESTAMP('2022-05-25 16:19:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-25T13:19:30.698Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542263 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-05-25T13:20:08.977Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1383,0,585061,542264,30,540439,'C_BP_Group_ID',TO_TIMESTAMP('2022-05-25 16:20:08','YYYY-MM-DD HH24:MI:SS'),100,'@C_BP_Group_ID@','Geschäftspartnergruppe','U',0,'Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.','Y','N','Y','N','N','N','Geschäftspartnergruppe',20,TO_TIMESTAMP('2022-05-25 16:20:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-25T13:20:08.978Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542264 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-05-25T13:20:30.665Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1106,0,585061,542265,20,'IsSOTrx',TO_TIMESTAMP('2022-05-25 16:20:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','Dies ist eine Verkaufstransaktion','U',0,'The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','Y','N','N','N','Verkaufstransaktion',30,TO_TIMESTAMP('2022-05-25 16:20:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-25T13:20:30.666Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542265 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-05-25T13:20:52.605Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577666,0,585061,542266,20,'p_show_product_price_pi_flag',TO_TIMESTAMP('2022-05-25 16:20:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','de.metas.fresh',0,'Y','N','Y','N','N','N','Preisliste Packvorschriften',40,TO_TIMESTAMP('2022-05-25 16:20:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-25T13:20:52.606Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542266 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-05-25T13:23:57.391Z
UPDATE AD_Element_Trl SET PrintName='Vergleichsbericht aktuelle mit nächster Preisliste',Updated=TO_TIMESTAMP('2022-05-25 16:23:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577626 AND AD_Language='de_CH'
;

-- 2022-05-25T13:23:57.425Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577626,'de_CH') 
;

-- 2022-05-25T13:23:58.783Z
UPDATE AD_Element_Trl SET PrintName='Vergleichsbericht aktuelle mit nächster Preisliste',Updated=TO_TIMESTAMP('2022-05-25 16:23:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577626 AND AD_Language='de_DE'
;

-- 2022-05-25T13:23:58.785Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577626,'de_DE') 
;

-- 2022-05-25T13:23:58.794Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(577626,'de_DE') 
;

-- 2022-05-25T13:23:58.797Z
UPDATE AD_PrintFormatItem pi SET PrintName='Vergleichsbericht aktuelle mit nächster Preisliste', Name='Vergleichsbericht aktuelle vs. vorige Preisliste' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577626)
;

-- 2022-05-25T13:24:07.160Z
UPDATE AD_Element_Trl SET PrintName='Vergleichsbericht aktuelle mit nächster Preisliste',Updated=TO_TIMESTAMP('2022-05-25 16:24:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577626 AND AD_Language='nl_NL'
;

-- 2022-05-25T13:24:07.162Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577626,'nl_NL') 
;

-- 2022-05-25T13:24:29.354Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580942,0,TO_TIMESTAMP('2022-05-25 16:24:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Vergleichsbericht nächste mit übernächster Preisliste','Vergleichsbericht nächste mit übernächster Preisliste',TO_TIMESTAMP('2022-05-25 16:24:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-25T13:24:29.355Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580942 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-05-25T13:25:12.761Z
UPDATE AD_Element_Trl SET PrintName='Next vs AfterNext Pricelist Comparison',Updated=TO_TIMESTAMP('2022-05-25 16:25:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580942 AND AD_Language='en_US'
;

-- 2022-05-25T13:25:12.762Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580942,'en_US') 
;

-- 2022-05-25T13:25:22.984Z
UPDATE AD_Element_Trl SET Name='Next vs AfterNext Pricelist Comparison',Updated=TO_TIMESTAMP('2022-05-25 16:25:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580942 AND AD_Language='en_US'
;

-- 2022-05-25T13:25:22.986Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580942,'en_US') 
;

-- 2022-05-25T13:25:56.597Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('R',0,580942,541956,0,585061,TO_TIMESTAMP('2022-05-25 16:25:56','YYYY-MM-DD HH24:MI:SS'),100,'D','NextVsAfterNextPricelistComparison','Y','N','N','N','N','Vergleichsbericht nächste mit übernächster Preisliste',TO_TIMESTAMP('2022-05-25 16:25:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-25T13:25:56.598Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541956 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-05-25T13:25:56.600Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541956, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541956)
;

-- 2022-05-25T13:25:56.611Z
/* DDL */  select update_menu_translation_from_ad_element(580942) 
;

-- Reordering children of `Berichte`
-- Node name: `Vergleichsbericht nächste mit übernächster Preisliste`
-- 2022-05-25T13:25:57.186Z
UPDATE AD_TreeNodeMM SET Parent_ID=540863, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541956 AND AD_Tree_ID=10
;

-- Reordering children of `Reports`
-- Node name: `Vergleichsbericht nächste mit übernächster Preisliste`
-- 2022-05-25T13:26:05.241Z
UPDATE AD_TreeNodeMM SET Parent_ID=540754, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541956 AND AD_Tree_ID=10
;

-- Node name: `Current vs Previous Pricelist Comparison Report (@PREFIX@de/metas/reports/pricelist/report_CurrentVsLastPricelistComparison.jasper)`
-- 2022-05-25T13:26:05.242Z
UPDATE AD_TreeNodeMM SET Parent_ID=540754, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541452 AND AD_Tree_ID=10
;

-- 2022-05-25T13:26:57.256Z
UPDATE AD_Process SET EntityType='D',Updated=TO_TIMESTAMP('2022-05-25 16:26:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585061
;

