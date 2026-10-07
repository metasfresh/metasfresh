-- Value: M_Delivery_Planning_GenerateReceipt
-- Classname: de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt
-- 2023-01-25T15:00:34.564Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585192,'Y','de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt','N',TO_TIMESTAMP('2023-01-25 17:00:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Generate Goods Receipt','json','N','N','xls','Java',TO_TIMESTAMP('2023-01-25 17:00:34','YYYY-MM-DD HH24:MI:SS'),100,'M_Delivery_Planning_GenerateReceipt')
;

-- 2023-01-25T15:00:34.566Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585192 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: MovementDate
-- 2023-01-25T15:01:13.661Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1037,0,585192,542465,15,'MovementDate',TO_TIMESTAMP('2023-01-25 17:01:13','YYYY-MM-DD HH24:MI:SS'),100,'@#Date@','Date a product was moved in or out of inventory','D',0,'The Movement Date indicates the date that a product moved in or out of inventory.  This is the result of a shipment, receipt or inventory movement.','Y','N','Y','N','Y','N','Date',10,TO_TIMESTAMP('2023-01-25 17:01:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T15:01:13.664Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542465 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: Qty
-- 2023-01-25T15:02:24.018Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,526,0,585192,542466,29,'Qty',TO_TIMESTAMP('2023-01-25 17:02:23','YYYY-MM-DD HH24:MI:SS'),100,'Quantity','D',0,'The Quantity indicates the number of a specific product or item for this document.','Y','N','Y','N','Y','N','Quantity',20,TO_TIMESTAMP('2023-01-25 17:02:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T15:02:24.020Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542466 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- Table: M_Delivery_Planning
-- EntityType: D
-- 2023-01-25T15:02:53.862Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585192,542259,541340,TO_TIMESTAMP('2023-01-25 17:02:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-01-25 17:02:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N')
;

-- 2023-01-25T21:14:21.016Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581961,0,'ReceiptDate',TO_TIMESTAMP('2023-01-25 23:14:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Receipt Date','Receipt Date',TO_TIMESTAMP('2023-01-25 23:14:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T21:14:21.018Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581961 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Process: M_Delivery_Planning_GenerateReceipt(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateReceipt)
-- ParameterName: ReceiptDate
-- 2023-01-25T21:14:32.215Z
UPDATE AD_Process_Para SET AD_Element_ID=581961, ColumnName='ReceiptDate', Description=NULL, Help=NULL, Name='Receipt Date',Updated=TO_TIMESTAMP('2023-01-25 23:14:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542465
;

-- 2023-01-25T21:14:32.216Z
UPDATE AD_Process_Para_Trl trl SET Description=NULL,Help=NULL,Name='Receipt Date' WHERE AD_Process_Para_ID=542465 AND AD_Language='en_US'
;

