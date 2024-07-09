-- Value: WEBUI_PP_Order_Clearance
-- Classname: de.metas.ui.web.pporder.process.WEBUI_PP_Order_Clearance
-- 2024-05-14T13:33:29.807Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,Help,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585390,'Y','de.metas.ui.web.pporder.process.WEBUI_PP_Order_Clearance','N',TO_TIMESTAMP('2024-05-14 16:33:29','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.handlingunits','','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Freigabe','json','N','Y','xls','Java',TO_TIMESTAMP('2024-05-14 16:33:29','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_PP_Order_Clearance')
;

-- 2024-05-14T13:33:29.813Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585390 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: WEBUI_PP_Order_Clearance(de.metas.ui.web.pporder.process.WEBUI_PP_Order_Clearance)
-- ParameterName: ClearanceStatus
-- 2024-05-14T13:46:43.733Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,580555,0,585390,542823,17,541540,'ClearanceStatus',TO_TIMESTAMP('2024-05-14 16:46:43','YYYY-MM-DD HH24:MI:SS'),100,'C','de.metas.handlingunits',0,'Y','N','Y','N','Y','N','Freigabe',10,TO_TIMESTAMP('2024-05-14 16:46:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-05-14T13:46:43.736Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542823 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: WEBUI_PP_Order_Clearance(de.metas.ui.web.pporder.process.WEBUI_PP_Order_Clearance)
-- ParameterName: ClearanceNote
-- 2024-05-14T13:47:04.788Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,580556,0,585390,542824,10,'ClearanceNote',TO_TIMESTAMP('2024-05-14 16:47:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',0,'Y','N','Y','N','N','N','Freigabe-Notiz',20,TO_TIMESTAMP('2024-05-14 16:47:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-05-14T13:47:04.789Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542824 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: WEBUI_PP_Order_Clearance(de.metas.ui.web.pporder.process.WEBUI_PP_Order_Clearance)
-- ParameterName: ClearanceDate
-- 2024-05-14T13:47:29.001Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581602,0,585390,542825,15,'ClearanceDate',TO_TIMESTAMP('2024-05-14 16:47:28','YYYY-MM-DD HH24:MI:SS'),100,'@#Date@','de.metas.handlingunits',0,'Y','N','Y','N','N','N','Freigabedatum',30,TO_TIMESTAMP('2024-05-14 16:47:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-05-14T13:47:29.003Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542825 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: WEBUI_PP_Order_Clearance(de.metas.ui.web.pporder.process.WEBUI_PP_Order_Clearance)
-- Table: PP_Order
-- EntityType: de.metas.handlingunits
-- 2024-05-14T13:48:11.345Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585390,53027,541487,TO_TIMESTAMP('2024-05-14 16:48:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',TO_TIMESTAMP('2024-05-14 16:48:11','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N')
;

-- Process: WEBUI_PP_Order_Clearance(de.metas.ui.web.pporder.process.WEBUI_PP_Order_Clearance)
-- ParameterName: ClearanceNote
-- 2024-05-14T13:48:56.745Z
UPDATE AD_Process_Para SET DefaultValue='@#AD_User_Name@',Updated=TO_TIMESTAMP('2024-05-14 16:48:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542824
;

-- Process: WEBUI_PP_Order_Clearance(de.metas.ui.web.pporder.process.WEBUI_PP_Order_Clearance)
-- Table: PP_Order
-- EntityType: de.metas.handlingunits
-- 2024-05-14T14:42:57.907Z
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction='Y',Updated=TO_TIMESTAMP('2024-05-14 17:42:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541487
;

-- Process: WEBUI_PP_Order_Clearance(de.metas.ui.web.pporder.process.WEBUI_PP_Order_Clearance)
-- 2024-05-14T16:13:20.712Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-14 19:13:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585390
;

-- Process: WEBUI_PP_Order_Clearance(de.metas.ui.web.pporder.process.WEBUI_PP_Order_Clearance)
-- 2024-05-14T16:13:22.398Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-14 19:13:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585390
;

-- Process: WEBUI_PP_Order_Clearance(de.metas.ui.web.pporder.process.WEBUI_PP_Order_Clearance)
-- 2024-05-14T16:13:25.233Z
UPDATE AD_Process_Trl SET Name='Clearance',Updated=TO_TIMESTAMP('2024-05-14 19:13:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585390
;

-- Process: WEBUI_PP_Order_Clearance(de.metas.ui.web.pporder.process.WEBUI_PP_Order_Clearance)
-- 2024-05-14T16:13:27.978Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-14 19:13:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585390
;

