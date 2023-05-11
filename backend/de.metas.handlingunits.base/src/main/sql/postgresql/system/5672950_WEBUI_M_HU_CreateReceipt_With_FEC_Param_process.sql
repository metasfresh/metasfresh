-- Value: WEBUI_M_HU_CreateReceipt_With_FEC_Param
-- Classname: de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_With_FEC_Param
-- 2023-01-23T10:24:52.810Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585188,'N','de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_With_FEC_Param','N',TO_TIMESTAMP('2023-01-23 12:24:52','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Create Material Receipt with FEC','json','N','Y','Java',TO_TIMESTAMP('2023-01-23 12:24:52','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_M_HU_CreateReceipt_With_FEC_Param')
;

-- 2023-01-23T10:24:52.815Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585188 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: WEBUI_M_HU_CreateReceipt_With_FEC_Param
-- Classname: de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_With_FEC_Param
-- 2023-01-23T10:24:58.984Z
UPDATE AD_Process SET EntityType='de.metas.ui.web',Updated=TO_TIMESTAMP('2023-01-23 12:24:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585188
;

-- 2023-01-23T10:26:21.221Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581947,0,'IsFEC',TO_TIMESTAMP('2023-01-23 12:26:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','FEC','FEC',TO_TIMESTAMP('2023-01-23 12:26:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-23T10:26:21.225Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581947 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Process: WEBUI_M_HU_CreateReceipt_With_FEC_Param(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_With_FEC_Param)
-- ParameterName: IsFEC
-- 2023-01-23T10:27:12.745Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,581947,0,585188,542460,20,'IsFEC',TO_TIMESTAMP('2023-01-23 12:27:12','YYYY-MM-DD HH24:MI:SS'),100,'N','1=2','de.metas.ui.web',0,'Y','N','Y','N','Y','N','FEC','1=1',10,TO_TIMESTAMP('2023-01-23 12:27:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-23T10:27:12.748Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542460 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: WEBUI_M_HU_CreateReceipt_With_FEC_Param(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_With_FEC_Param)
-- ParameterName: C_ForeignExchangeContract_ID
-- 2023-01-23T10:28:09.466Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581935,0,585188,542461,30,'C_ForeignExchangeContract_ID',TO_TIMESTAMP('2023-01-23 12:28:09','YYYY-MM-DD HH24:MI:SS'),100,'@IsFEC/N@=Y','de.metas.ui.web',0,'Y','N','Y','N','N','N','Foreign Exchange Contract',20,TO_TIMESTAMP('2023-01-23 12:28:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-23T10:28:09.468Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542461 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: WEBUI_M_HU_CreateReceipt_With_FEC_Param(de.metas.ui.web.handlingunits.process.WEBUI_M_HU_CreateReceipt_With_FEC_Param)
-- Table: M_HU
-- EntityType: de.metas.ui.web
-- 2023-01-23T10:30:17.832Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585188,540516,541333,TO_TIMESTAMP('2023-01-23 12:30:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y',TO_TIMESTAMP('2023-01-23 12:30:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N')
;

