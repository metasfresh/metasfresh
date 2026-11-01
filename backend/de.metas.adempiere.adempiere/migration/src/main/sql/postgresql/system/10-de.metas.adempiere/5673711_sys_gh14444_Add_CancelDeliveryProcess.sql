-- Value: de.metas.deliveryplanning.DeliveryPlanningService.WhithOutReleaseNo
-- 2023-01-26T11:36:13.427Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545231,0,TO_TIMESTAMP('2023-01-26 13:36:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Records do not have a ReleaseNo.','I',TO_TIMESTAMP('2023-01-26 13:36:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.deliveryplanning.DeliveryPlanningService.WhithOutReleaseNo')
;

-- 2023-01-26T11:36:13.429Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545231 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.deliveryplanning.DeliveryPlanningService.WhithOutReleaseNo
-- 2023-01-26T11:36:21.352Z
UPDATE AD_Message SET MsgText='The records do not have a ReleaseNo.',Updated=TO_TIMESTAMP('2023-01-26 13:36:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545231
;

-- 2023-01-26T11:36:21.353Z
UPDATE AD_Message_Trl trl SET MsgText='The records do not have a ReleaseNo.' WHERE AD_Message_ID=545231 AND AD_Language='de_DE'
;

-- Value: M_Delivery_Planning_CancelDeliveryInstruction
-- Classname: de.metas.deliveryplanning.process.M_Delivery_Planning_CancelDeliveryInstruction
-- 2023-01-26T12:54:35.519Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585195,'Y','de.metas.deliveryplanning.process.M_Delivery_Planning_CancelDeliveryInstruction','N',TO_TIMESTAMP('2023-01-26 14:54:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Delivery cancelled','json','Y','N','xls','Java',TO_TIMESTAMP('2023-01-26 14:54:35','YYYY-MM-DD HH24:MI:SS'),100,'M_Delivery_Planning_CancelDeliveryInstruction')
;

-- 2023-01-26T12:54:35.521Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585195 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_Delivery_Planning_CancelDeliveryInstruction(de.metas.deliveryplanning.process.M_Delivery_Planning_CancelDeliveryInstruction)
-- Table: M_Delivery_Planning
-- EntityType: D
-- 2023-01-26T12:55:05.556Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585195,542259,541342,TO_TIMESTAMP('2023-01-26 14:55:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-01-26 14:55:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Process: M_Delivery_Planning_CancelDeliveryInstruction(de.metas.deliveryplanning.process.M_Delivery_Planning_CancelDeliveryInstruction)
-- Table: M_Delivery_Planning
-- Window: Lieferplanung(541632,D)
-- EntityType: D
-- 2023-01-26T12:57:31.429Z
UPDATE AD_Table_Process SET AD_Window_ID=541632,Updated=TO_TIMESTAMP('2023-01-26 14:57:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541342
;

