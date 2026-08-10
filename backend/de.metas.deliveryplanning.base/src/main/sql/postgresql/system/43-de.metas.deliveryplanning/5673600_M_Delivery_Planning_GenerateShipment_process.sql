-- Value: M_Delivery_Planning_GenerateShipment
-- Classname: de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment
-- 2023-01-25T21:10:38.279Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585194,'Y','de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment','N',TO_TIMESTAMP('2023-01-25 23:10:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Generate Goods Issue','json','N','N','xls','Java',TO_TIMESTAMP('2023-01-25 23:10:36','YYYY-MM-DD HH24:MI:SS'),100,'M_Delivery_Planning_GenerateShipment')
;

-- 2023-01-25T21:10:38.282Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585194 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: DeliveryDate
-- 2023-01-25T21:11:51.577Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,541376,0,585194,542467,15,'DeliveryDate',TO_TIMESTAMP('2023-01-25 23:11:51','YYYY-MM-DD HH24:MI:SS'),100,'@#Date@','D',0,'Y','N','Y','N','Y','N','Shipmentdate',10,TO_TIMESTAMP('2023-01-25 23:11:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T21:11:51.578Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542467 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: Qty
-- 2023-01-25T21:12:07.453Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,526,0,585194,542468,29,'Qty',TO_TIMESTAMP('2023-01-25 23:12:07','YYYY-MM-DD HH24:MI:SS'),100,'Quantity','U',0,'The Quantity indicates the number of a specific product or item for this document.','Y','N','Y','N','Y','N','Quantity',20,TO_TIMESTAMP('2023-01-25 23:12:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T21:12:07.455Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542468 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: Qty
-- 2023-01-25T21:12:11.776Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2023-01-25 23:12:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542468
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- Table: M_Delivery_Planning
-- EntityType: D
-- 2023-01-25T21:12:35.654Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585194,542259,541341,TO_TIMESTAMP('2023-01-25 23:12:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-01-25 23:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N')
;

