-- Value: M_Delivery_Planning_ReGenerateDeliveryInstruction
-- Classname: de.metas.deliveryplanning.process.M_Delivery_Planning_ReGenerateDeliveryInstruction
-- 2023-01-25T13:20:50.916Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585191,'Y','de.metas.deliveryplanning.process.M_Delivery_Planning_ReGenerateDeliveryInstruction','N',TO_TIMESTAMP('2023-01-25 15:20:50','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'ReGenerate Delivery Instruction','json','Y','N','xls','Java',TO_TIMESTAMP('2023-01-25 15:20:50','YYYY-MM-DD HH24:MI:SS'),100,'M_Delivery_Planning_ReGenerateDeliveryInstruction')
;

-- 2023-01-25T13:20:50.917Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585191 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: M_Delivery_Planning_ReGenerateDeliveryInstruction
-- Classname: de.metas.deliveryplanning.process.M_Delivery_Planning_ReGenerateDeliveryInstruction
-- 2023-01-25T13:21:10.274Z
UPDATE AD_Process SET EntityType='D',Updated=TO_TIMESTAMP('2023-01-25 15:21:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585191
;

-- Process: M_Delivery_Planning_ReGenerateDeliveryInstruction(de.metas.deliveryplanning.process.M_Delivery_Planning_ReGenerateDeliveryInstruction)
-- Table: M_Delivery_Planning
-- EntityType: D
-- 2023-01-25T13:24:46.507Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585191,542259,541339,TO_TIMESTAMP('2023-01-25 15:24:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-01-25 15:24:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- Process: M_Delivery_Planning_ReGenerateDeliveryInstruction(de.metas.deliveryplanning.process.M_Delivery_Planning_ReGenerateDeliveryInstruction)
-- Table: M_Delivery_Planning
-- Window: Lieferplanung(541632,D)
-- EntityType: D
-- 2023-01-25T13:25:55.558Z
UPDATE AD_Table_Process SET AD_Window_ID=541632,Updated=TO_TIMESTAMP('2023-01-25 15:25:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541339
;


-- Value: M_Delivery_Planning_ReGenerateDeliveryInstruction
-- Classname: de.metas.deliveryplanning.process.M_Delivery_Planning_ReGenerateDeliveryInstruction
-- 2023-01-26T13:12:40.643Z
UPDATE AD_Process SET Name='Delivery Instruction Adjustment',Updated=TO_TIMESTAMP('2023-01-26 15:12:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585191
;

-- 2023-01-26T13:12:40.644Z
UPDATE AD_Process_Trl trl SET Name='Delivery Instruction Adjustment' WHERE AD_Process_ID=585191 AND AD_Language='de_DE'
;

