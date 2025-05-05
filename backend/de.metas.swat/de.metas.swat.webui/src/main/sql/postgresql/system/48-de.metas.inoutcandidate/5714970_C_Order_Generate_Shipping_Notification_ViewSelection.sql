-- Run mode: SWING_CLIENT

-- Value: C_Order_Generate_Shipping_Notification_ViewSelection
-- Classname: de.metas.inoutcandidate.process.C_Order_Generate_Shipping_Notification_ViewSelection
-- 2024-01-10T08:38:20.049Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585344,'Y','de.metas.inoutcandidate.process.C_Order_Generate_Shipping_Notification_ViewSelection','N',TO_TIMESTAMP('2024-01-10 10:38:19.744','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Lieferavis generieren','json','N','N','xls','Java',TO_TIMESTAMP('2024-01-10 10:38:19.744','YYYY-MM-DD HH24:MI:SS.US'),100,'C_Order_Generate_Shipping_Notification_ViewSelection')
;

-- 2024-01-10T08:38:20.056Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585344 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Order_Generate_Shipping_Notification_ViewSelection(de.metas.inoutcandidate.process.C_Order_Generate_Shipping_Notification_ViewSelection)
-- ParameterName: PhysicalClearanceDate
-- 2024-01-10T08:38:47.807Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582696,0,585344,542766,15,'PhysicalClearanceDate',TO_TIMESTAMP('2024-01-10 10:38:47.653','YYYY-MM-DD HH24:MI:SS.US'),100,'@#Date@','de.metas.shippingnotification',0,'Y','N','Y','N','Y','N','Datum der physischen Freigabe',10,TO_TIMESTAMP('2024-01-10 10:38:47.653','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-10T08:38:47.810Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542766 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-01-10T08:38:47.834Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(582696)
;

-- Process: C_Order_Generate_Shipping_Notification(de.metas.inoutcandidate.process.C_Order_Generate_Shipping_Notification)
-- Table: C_Order
-- EntityType: de.metas.shippingnotification
-- 2024-01-10T08:39:15.385Z
UPDATE AD_Table_Process SET WEBUI_ViewAction='N',Updated=TO_TIMESTAMP('2024-01-10 10:39:15.385','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=541422
;

-- Process: C_Order_Generate_Shipping_Notification_ViewSelection(de.metas.inoutcandidate.process.C_Order_Generate_Shipping_Notification_ViewSelection)
-- Table: C_Order
-- EntityType: de.metas.shippingnotification
-- 2024-01-10T08:39:36.191Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585344,259,541452,TO_TIMESTAMP('2024-01-10 10:39:35.02','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','Y',TO_TIMESTAMP('2024-01-10 10:39:35.02','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','Y','N','N')
;

-- Process: C_Order_Generate_Shipping_Notification_ViewSelection(de.metas.inoutcandidate.process.C_Order_Generate_Shipping_Notification_ViewSelection)
-- 2024-01-10T08:54:45.853Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Generate Shipping Notification',Updated=TO_TIMESTAMP('2024-01-10 10:54:45.853','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585344
;

-- Process: C_Order_Generate_Shipping_Notification_ViewSelection(de.metas.inoutcandidate.process.C_Order_Generate_Shipping_Notification_ViewSelection)
-- 2024-01-10T08:54:47.517Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-01-10 10:54:47.517','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585344
;

-- Process: C_Order_Generate_Shipping_Notification_ViewSelection(de.metas.inoutcandidate.process.C_Order_Generate_Shipping_Notification_ViewSelection)
-- 2024-01-10T08:54:51.103Z
UPDATE AD_Process_Trl SET IsTranslated='N',Updated=TO_TIMESTAMP('2024-01-10 10:54:51.103','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585344
;

-- Process: C_Order_Generate_Shipping_Notification_ViewSelection(de.metas.inoutcandidate.process.C_Order_Generate_Shipping_Notification_ViewSelection)
-- 2024-01-10T08:54:56.003Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-01-10 10:54:56.003','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585344
;

-- Process: C_Order_Generate_Shipping_Notification_ViewSelection(de.metas.inoutcandidate.process.C_Order_Generate_Shipping_Notification_ViewSelection)
-- 2024-01-10T08:54:57.505Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-01-10 10:54:57.505','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585344
;

