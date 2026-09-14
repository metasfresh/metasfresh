-- Value: M_DeliveryPlanning_CreateAdditionalLines
-- Classname: de.metas.deliveryplanning.process.M_DeliveryPlanning_CreateAdditionalLines
-- 2022-11-23T16:02:53.554Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585153,'Y','de.metas.deliveryplanning.process.M_DeliveryPlanning_CreateAdditionalLines','N',TO_TIMESTAMP('2022-11-23 18:02:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Create Additional Lines','json','N','N','xls','Java',TO_TIMESTAMP('2022-11-23 18:02:53','YYYY-MM-DD HH24:MI:SS'),100,'M_DeliveryPlanning_CreateAdditionalLines')
;

-- 2022-11-23T16:02:53.583Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585153 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2022-11-23T16:23:27.111Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581727,0,'AdditionalLines',TO_TIMESTAMP('2022-11-23 18:23:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Additional Lines','Additional Lines',TO_TIMESTAMP('2022-11-23 18:23:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T16:23:27.142Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581727 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Process: M_DeliveryPlanning_CreateAdditionalLines(de.metas.deliveryplanning.process.M_DeliveryPlanning_CreateAdditionalLines)
-- ParameterName: AdditionalLines
-- 2022-11-23T16:24:51.878Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy,ValueMin) VALUES (0,581727,0,585153,542376,11,'AdditionalLines',TO_TIMESTAMP('2022-11-23 18:24:51','YYYY-MM-DD HH24:MI:SS'),100,'1','D',0,'Y','N','Y','N','Y','N','Additional Lines',10,TO_TIMESTAMP('2022-11-23 18:24:51','YYYY-MM-DD HH24:MI:SS'),100,'1')
;

-- 2022-11-23T16:24:51.910Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542376 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_DeliveryPlanning_CreateAdditionalLines(de.metas.deliveryplanning.process.M_DeliveryPlanning_CreateAdditionalLines)
-- Table: M_Delivery_Planning
-- EntityType: D
-- 2022-11-23T16:26:50.548Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585153,542259,541313,TO_TIMESTAMP('2022-11-23 18:26:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2022-11-23 18:26:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N')
;

-- Value: M_DeliveryPlanning_CreateAdditionalLines
-- Classname: de.metas.deliveryplanning.process.M_DeliveryPlanning_CreateAdditionalLines
-- 2022-11-24T10:16:18.388Z
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2022-11-24 12:16:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585153
;
