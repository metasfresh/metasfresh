-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: IsFEC
-- 2023-01-26T11:42:13.347Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,581947,0,585192,542469,20,'IsFEC',TO_TIMESTAMP('2023-01-26 13:42:12','YYYY-MM-DD HH24:MI:SS'),100,'N','1=2','D',0,'Y','N','Y','N','Y','N','FEC','1=1',30,TO_TIMESTAMP('2023-01-26 13:42:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T11:42:13.353Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542469 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: C_ForeignExchangeContract_ID
-- 2023-01-26T11:42:43.479Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581935,0,585192,542470,30,'C_ForeignExchangeContract_ID',TO_TIMESTAMP('2023-01-26 13:42:43','YYYY-MM-DD HH24:MI:SS'),100,'@IsFEC/N@=Y','U',0,'Y','N','Y','N','N','N','Foreign Exchange Contract',40,TO_TIMESTAMP('2023-01-26 13:42:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T11:42:43.480Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542470 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: C_ForeignExchangeContract_ID
-- 2023-01-26T11:42:48.346Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2023-01-26 13:42:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542470
;

-- 2023-01-26T11:43:36.373Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581963,0,'FEC_CurrencyRate',TO_TIMESTAMP('2023-01-26 13:43:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','FEC Currency Rate','FEC Currency Rate',TO_TIMESTAMP('2023-01-26 13:43:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T11:43:36.375Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581963 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: FEC_CurrencyRate
-- 2023-01-26T11:44:01.445Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581963,0,585192,542471,22,'FEC_CurrencyRate',TO_TIMESTAMP('2023-01-26 13:44:01','YYYY-MM-DD HH24:MI:SS'),100,'@IsFEC/N@=Y','D',0,'Y','N','Y','N','N','N','FEC Currency Rate',50,TO_TIMESTAMP('2023-01-26 13:44:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T11:44:01.447Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542471 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: C_ForeignExchangeContract_ID
-- 2023-01-26T11:47:48.191Z
UPDATE AD_Process_Para SET AD_Reference_ID=18,Updated=TO_TIMESTAMP('2023-01-26 13:47:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542470
;

