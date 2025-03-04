-- Value: PP_Order_AddSourceHU
-- Classname: de.metas.handlingunits.pporder.process.PP_Order_AddSourceHU
-- 2022-11-17T14:16:44.416Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585146,'Y','de.metas.handlingunits.pporder.process.PP_Order_AddSourceHU','N',TO_TIMESTAMP('2022-11-17 16:16:44','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','N','N','Y','N','N','N','Y','Y',0,'Add Source HU','json','N','N','xls','Java',TO_TIMESTAMP('2022-11-17 16:16:44','YYYY-MM-DD HH24:MI:SS'),100,'PP_Order_AddSourceHU')
;

-- 2022-11-17T14:16:44.419Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585146 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: PP_Order_AddSourceHU(de.metas.handlingunits.pporder.process.PP_Order_AddSourceHU)
-- ParameterName: M_HU_ID
-- 2022-11-17T14:17:20.827Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,542140,0,585146,542362,30,540363,'M_HU_ID',TO_TIMESTAMP('2022-11-17 16:17:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',0,'Y','N','Y','N','Y','N','Handling Unit',10,TO_TIMESTAMP('2022-11-17 16:17:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T14:17:20.829Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542362 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: PP_Order_AddSourceHU(de.metas.handlingunits.pporder.process.PP_Order_AddSourceHU)
-- Table: PP_Order
-- EntityType: EE01
-- 2022-11-17T14:17:55.128Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585146,53027,541308,TO_TIMESTAMP('2022-11-17 16:17:54','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y',TO_TIMESTAMP('2022-11-17 16:17:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;

-- Value: PP_Order_AddSourceHU
-- Classname: de.metas.handlingunits.pporder.process.PP_Order_AddSourceHU
-- 2022-11-17T15:12:57.949Z
UPDATE AD_Process SET Name='Quell-HU hinzufügen',Updated=TO_TIMESTAMP('2022-11-17 17:12:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585146
;

-- Process: PP_Order_AddSourceHU(de.metas.handlingunits.pporder.process.PP_Order_AddSourceHU)
-- 2022-11-17T15:13:02.230Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Quell-HU hinzufügen',Updated=TO_TIMESTAMP('2022-11-17 17:13:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585146
;

-- Process: PP_Order_AddSourceHU(de.metas.handlingunits.pporder.process.PP_Order_AddSourceHU)
-- 2022-11-17T15:13:04.463Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Quell-HU hinzufügen',Updated=TO_TIMESTAMP('2022-11-17 17:13:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585146
;

-- Process: PP_Order_AddSourceHU(de.metas.handlingunits.pporder.process.PP_Order_AddSourceHU)
-- 2022-11-17T15:13:06.250Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-17 17:13:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585146
;

-- Value: PP_Order_AddSourceHU
-- Classname: de.metas.handlingunits.pporder.source_hu.process.PP_Order_AddSourceHU
-- 2022-11-18T12:55:34.953Z
UPDATE AD_Process SET Classname='de.metas.handlingunits.pporder.source_hu.process.PP_Order_AddSourceHU',Updated=TO_TIMESTAMP('2022-11-18 14:55:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585146
;

