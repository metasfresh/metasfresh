-- Value: M_HU_MultipleSelection_Report_Print_Label
-- Classname: de.metas.handlingunits.process.M_HU_MultipleSelection_Report_Print_Label
-- 2024-02-12T20:45:39.965Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585354,'Y','de.metas.handlingunits.process.M_HU_MultipleSelection_Report_Print_Label','N',TO_TIMESTAMP('2024-02-12 22:45:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Etiketten','json','N','N','xls','Java',TO_TIMESTAMP('2024-02-12 22:45:38','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_MultipleSelection_Report_Print_Label')
;

-- 2024-02-12T20:45:39.972Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585354 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_HU_MultipleSelection_Report_Print_Label(de.metas.handlingunits.process.M_HU_MultipleSelection_Report_Print_Label)
-- ParameterName: AD_Process_ID
-- 2024-02-12T20:47:02.835Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,117,0,585354,542779,30,540706,540604,'AD_Process_ID',TO_TIMESTAMP('2024-02-12 22:47:02','YYYY-MM-DD HH24:MI:SS'),100,'Prozess oder Bericht','de.metas.handlingunits',255,'das Feld "Prozess" bezeichnet einen einzelnen Prozess oder Bericht im System.','Y','N','Y','N','Y','N','Prozess',10,TO_TIMESTAMP('2024-02-12 22:47:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-12T20:47:02.840Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542779 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-02-12T20:49:38.551Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582963,0,'PrintCopies',TO_TIMESTAMP('2024-02-12 22:49:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y','Copies','Copies',TO_TIMESTAMP('2024-02-12 22:49:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-12T20:49:38.562Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582963 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PrintCopies
-- 2024-02-12T20:49:53.297Z
UPDATE AD_Element_Trl SET Name='Exemplare', PrintName='Exemplare',Updated=TO_TIMESTAMP('2024-02-12 22:49:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582963 AND AD_Language='de_CH'
;

-- 2024-02-12T20:49:53.331Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582963,'de_CH') 
;

-- Element: PrintCopies
-- 2024-02-12T20:49:55.658Z
UPDATE AD_Element_Trl SET Name='Exemplare', PrintName='Exemplare',Updated=TO_TIMESTAMP('2024-02-12 22:49:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582963 AND AD_Language='de_DE'
;

-- 2024-02-12T20:49:55.661Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582963,'de_DE') 
;

-- 2024-02-12T20:49:55.671Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582963,'de_DE') 
;

-- Element: PrintCopies
-- 2024-02-12T20:49:59.581Z
UPDATE AD_Element_Trl SET Name='Exemplare', PrintName='Exemplare',Updated=TO_TIMESTAMP('2024-02-12 22:49:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582963 AND AD_Language='fr_CH'
;

-- 2024-02-12T20:49:59.584Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582963,'fr_CH') 
;

-- Element: PrintCopies
-- 2024-02-12T20:50:15.269Z
UPDATE AD_Element_Trl SET PrintName='Exemplare',Updated=TO_TIMESTAMP('2024-02-12 22:50:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582963 AND AD_Language='nl_NL'
;

-- 2024-02-12T20:50:15.271Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582963,'nl_NL') 
;

-- Process: M_HU_MultipleSelection_Report_Print_Label(de.metas.handlingunits.process.M_HU_MultipleSelection_Report_Print_Label)
-- ParameterName: PrintCopies
-- 2024-02-12T20:50:48.571Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582963,0,585354,542780,11,'PrintCopies',TO_TIMESTAMP('2024-02-12 22:50:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing',255,'Y','N','Y','N','Y','N','Exemplare',20,TO_TIMESTAMP('2024-02-12 22:50:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-12T20:50:48.578Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542780 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_HU_MultipleSelection_Report_Print_Label(de.metas.handlingunits.process.M_HU_MultipleSelection_Report_Print_Label)
-- Table: M_HU
-- EntityType: de.metas.handlingunits
-- 2024-02-12T20:51:18.777Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585354,540516,541459,TO_TIMESTAMP('2024-02-12 22:51:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',TO_TIMESTAMP('2024-02-12 22:51:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N')
;

-- Process: M_HU_MultipleSelection_Report_Print_Label(de.metas.handlingunits.process.M_HU_MultipleSelection_Report_Print_Label)
-- 2024-02-12T20:53:55.339Z
UPDATE AD_Process_Trl SET Name='HU Labels',Updated=TO_TIMESTAMP('2024-02-12 22:53:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585354
;

-- Process: M_HU_MultipleSelection_Report_Print_Label(de.metas.handlingunits.process.M_HU_MultipleSelection_Report_Print_Label)
-- ParameterName: AD_Process_ID
-- 2024-02-12T21:08:58.034Z
UPDATE AD_Process_Para SET SeqNo=15,Updated=TO_TIMESTAMP('2024-02-12 23:08:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542779
;

-- Process: M_HU_MultipleSelection_Report_Print_Label(de.metas.handlingunits.process.M_HU_MultipleSelection_Report_Print_Label)
-- ParameterName: PrintCopies
-- 2024-02-12T21:09:06.430Z
UPDATE AD_Process_Para SET SeqNo=10,Updated=TO_TIMESTAMP('2024-02-12 23:09:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542780
;

-- Process: M_HU_MultipleSelection_Report_Print_Label(de.metas.handlingunits.process.M_HU_MultipleSelection_Report_Print_Label)
-- ParameterName: AD_Process_ID
-- 2024-02-12T21:09:12.500Z
UPDATE AD_Process_Para SET SeqNo=20,Updated=TO_TIMESTAMP('2024-02-12 23:09:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542779
;

-- Process: M_HU_MultipleSelection_Report_Print_Label(de.metas.handlingunits.process.M_HU_MultipleSelection_Report_Print_Label)
-- ParameterName: PrintCopies
-- 2024-02-12T21:09:23.051Z
UPDATE AD_Process_Para SET DefaultValue='1',Updated=TO_TIMESTAMP('2024-02-12 23:09:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542780
;

-- Value: M_HU_MultipleSelection_Report_Print_Label
-- Classname: de.metas.handlingunits.process.M_HU_MultipleSelection_Report_Print_Label
-- 2024-02-12T21:19:07.400Z
UPDATE AD_Process SET IsActive='N',Updated=TO_TIMESTAMP('2024-02-12 23:19:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585354
;

