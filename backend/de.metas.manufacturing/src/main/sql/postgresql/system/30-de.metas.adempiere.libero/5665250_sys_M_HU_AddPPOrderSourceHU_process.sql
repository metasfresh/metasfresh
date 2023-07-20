-- Value: M_HU_AddPPOrderSourceHU
-- Classname: de.metas.handlingunits.pporder.source_hu.process.M_HU_AddPPOrderSourceHU
-- 2022-11-18T12:58:03.062Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585148,'Y','de.metas.handlingunits.pporder.source_hu.process.M_HU_AddPPOrderSourceHU','N',TO_TIMESTAMP('2022-11-18 14:58:02','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','N','N','Y','N','N','N','Y','Y',0,'Add Source HU to Manufacturing Order','json','N','N','xls','Java',TO_TIMESTAMP('2022-11-18 14:58:02','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_AddPPOrderSourceHU')
;

-- 2022-11-18T12:58:03.068Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585148 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_HU_AddPPOrderSourceHU(de.metas.handlingunits.pporder.source_hu.process.M_HU_AddPPOrderSourceHU)
-- Table: M_HU
-- EntityType: EE01
-- 2022-11-18T12:58:56.759Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585148,540516,541310,TO_TIMESTAMP('2022-11-18 14:58:56','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y',TO_TIMESTAMP('2022-11-18 14:58:56','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N')
;

-- Process: M_HU_AddPPOrderSourceHU(de.metas.handlingunits.pporder.source_hu.process.M_HU_AddPPOrderSourceHU)
-- 2022-11-18T13:03:51.178Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Quell-HU zu Produktionsauftrag hinzufügen',Updated=TO_TIMESTAMP('2022-11-18 15:03:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585148
;

-- Value: M_HU_AddPPOrderSourceHU
-- Classname: de.metas.handlingunits.pporder.source_hu.process.M_HU_AddPPOrderSourceHU
-- 2022-11-18T13:03:53.597Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Quell-HU zu Produktionsauftrag hinzufügen',Updated=TO_TIMESTAMP('2022-11-18 15:03:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585148
;

-- Process: M_HU_AddPPOrderSourceHU(de.metas.handlingunits.pporder.source_hu.process.M_HU_AddPPOrderSourceHU)
-- 2022-11-18T13:03:53.584Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Quell-HU zu Produktionsauftrag hinzufügen',Updated=TO_TIMESTAMP('2022-11-18 15:03:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585148
;

-- Process: M_HU_AddPPOrderSourceHU(de.metas.handlingunits.pporder.source_hu.process.M_HU_AddPPOrderSourceHU)
-- 2022-11-18T13:03:55.394Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-18 15:03:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585148
;

-- Process: M_HU_AddPPOrderSourceHU(de.metas.handlingunits.pporder.source_hu.process.M_HU_AddPPOrderSourceHU)
-- ParameterName: PP_Order_ID
-- 2022-11-18T14:47:23.981Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,53276,0,585148,542363,30,52038,'PP_Order_ID',TO_TIMESTAMP('2022-11-18 16:47:23','YYYY-MM-DD HH24:MI:SS'),100,'EE01',10,'Y','N','Y','N','Y','N','Produktionsauftrag',10,TO_TIMESTAMP('2022-11-18 16:47:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-18T14:47:23.986Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542363 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_HU_AddPPOrderSourceHU(de.metas.handlingunits.pporder.source_hu.process.M_HU_AddPPOrderSourceHU)
-- Table: M_HU
-- EntityType: EE01
-- 2022-11-18T14:58:01.030Z
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction='Y',Updated=TO_TIMESTAMP('2022-11-18 16:58:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=541310
;

