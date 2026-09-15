
-- Value: M_HU_Trace_CreateForAllHUs
-- Classname: de.metas.handlingunits.trace.process.M_HU_Trace_CreateForAllHUs
-- 2023-05-10T21:12:19.720Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,585260,'Y','de.metas.handlingunits.trace.process.M_HU_Trace_CreateForAllHUs','N',TO_TIMESTAMP('2023-05-11 00:12:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Nachverfolgunsdatensätze für HU erstellen','json','N','N','Java',TO_TIMESTAMP('2023-05-11 00:12:19','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_Trace_CreateForAllHUs')
;

-- 2023-05-10T21:12:19.726Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585260 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;


-- Process: M_HU_Trace_CreateForAllHUs(de.metas.handlingunits.trace.process.M_HU_Trace_CreateForAllHUs)
-- Table: M_HU
-- EntityType: de.metas.handlingunits
-- 2023-05-10T21:34:42.015Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585260,540516,541381,TO_TIMESTAMP('2023-05-11 00:34:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y',TO_TIMESTAMP('2023-05-11 00:34:41','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N')
;

-- Value: M_HU_Trace_CreateForAllHUs
-- Classname: de.metas.handlingunits.trace.process.M_HU_Trace_CreateForAllHUs
-- 2023-05-10T21:35:06.458Z
UPDATE AD_Process SET Name='Nachverfolgunsdatensätze für ALLE HU erstellen',Updated=TO_TIMESTAMP('2023-05-11 00:35:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585260
;

-- Process: M_HU_Trace_CreateForAllHUs(de.metas.handlingunits.trace.process.M_HU_Trace_CreateForAllHUs)
-- 2023-05-10T21:35:15.257Z
UPDATE AD_Process_Trl SET Name='Nachverfolgunsdatensätze für ALLE HU erstellen',Updated=TO_TIMESTAMP('2023-05-11 00:35:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585260
;

-- Process: M_HU_Trace_CreateForAllHUs(de.metas.handlingunits.trace.process.M_HU_Trace_CreateForAllHUs)
-- 2023-05-10T21:35:27.314Z
UPDATE AD_Process_Trl SET Name='Nachverfolgunsdatensätze für ALLE HU erstellen',Updated=TO_TIMESTAMP('2023-05-11 00:35:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585260
;

-- Process: M_HU_Trace_CreateForAllHUs(de.metas.handlingunits.trace.process.M_HU_Trace_CreateForAllHUs)
-- 2023-05-10T21:35:42.084Z
UPDATE AD_Process_Trl SET Name='Create trances for all HUs',Updated=TO_TIMESTAMP('2023-05-11 00:35:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585260
;

-- Process: M_HU_Trace_CreateForAllHUs(de.metas.handlingunits.trace.process.M_HU_Trace_CreateForAllHUs)
-- 2023-05-10T21:41:59.711Z
UPDATE AD_Process_Trl SET Name='Create traces for all HUs',Updated=TO_TIMESTAMP('2023-05-11 00:41:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585260
;

