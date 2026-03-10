-- Run mode: SWING_CLIENT

-- Value: C_Order_OpenModularOrder
-- Classname: de.metas.contracts.modular.process.C_Order_OpenModularOrder
-- 2025-06-17T07:55:29.109Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,585475,'Y','de.metas.contracts.modular.process.C_Order_OpenModularOrder','N',TO_TIMESTAMP('2025-06-17 09:55:28.872','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Öffnen','json','N','N','xls','Java',TO_TIMESTAMP('2025-06-17 09:55:28.872','YYYY-MM-DD HH24:MI:SS.US'),100,'C_Order_OpenModularOrder')
;

-- 2025-06-17T07:55:29.122Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585475 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: C_Order_OpenModularOrder(de.metas.contracts.modular.process.C_Order_OpenModularOrder)
-- 2025-06-17T07:55:59.186Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Open',Updated=TO_TIMESTAMP('2025-06-17 09:55:59.186','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585475
;

-- Process: C_Order_OpenModularOrder(de.metas.contracts.modular.process.C_Order_OpenModularOrder)
-- 2025-06-17T07:56:00.270Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-17 09:56:00.27','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585475
;

-- Process: C_Order_OpenModularOrder(de.metas.contracts.modular.process.C_Order_OpenModularOrder)
-- 2025-06-17T07:56:03.693Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-17 09:56:03.692','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585475
;

-- Process: C_Order_OpenModularOrder(de.metas.contracts.modular.process.C_Order_OpenModularOrder)
-- Table: C_Order
-- EntityType: de.metas.contracts
-- 2025-06-17T08:08:48.036Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585475,259,541559,TO_TIMESTAMP('2025-06-17 10:08:47.911','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2025-06-17 10:08:47.911','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

-- Value: C_Order_OpenModularOrder_LinkedModularPurchaseOrderNotOpen
-- 2025-06-17T08:34:28.066Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545563,0,TO_TIMESTAMP('2025-06-17 10:34:27.936','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Verlinkte modulare Bestellung nicht offen','I',TO_TIMESTAMP('2025-06-17 10:34:27.936','YYYY-MM-DD HH24:MI:SS.US'),100,'C_Order_OpenModularOrder_LinkedModularPurchaseOrderNotOpen')
;

-- 2025-06-17T08:34:28.069Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545563 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: C_Order_OpenModularOrder_LinkedModularPurchaseOrderNotOpen
-- 2025-06-17T08:34:54.371Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Linked modular purchase order not open',Updated=TO_TIMESTAMP('2025-06-17 10:34:54.371','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545563
;

-- Value: C_Order_OpenModularOrder_LinkedModularPurchaseOrderNotOpen
-- 2025-06-17T08:34:55.293Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-17 10:34:55.293','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545563
;

-- Value: C_Order_OpenModularOrder_LinkedModularPurchaseOrderNotOpen
-- 2025-06-17T08:34:56.243Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-17 10:34:56.243','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545563
;

