
-- Value: Recompute effort control
-- Classname: de.metas.serviceprovider.effortcontrol.process.RecomputeEffortControlProcess
-- 2022-09-15T06:18:57.844Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585109,'Y','de.metas.serviceprovider.effortcontrol.process.RecomputeEffortControlProcess','N',TO_TIMESTAMP('2022-09-15 09:18:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Recompute effort control','json','N','N','xls','Java',TO_TIMESTAMP('2022-09-15 09:18:57','YYYY-MM-DD HH24:MI:SS'),100,'Recompute effort control')
;

-- 2022-09-15T06:18:57.860Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585109 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: Recompute effort control(de.metas.serviceprovider.effortcontrol.process.RecomputeEffortControlProcess)
-- Table: S_EffortControl
-- Window: S_EffortControl(541615,de.metas.serviceprovider)
-- EntityType: de.metas.serviceprovider
-- 2022-09-15T06:19:24.873Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585109,542215,541281,541615,TO_TIMESTAMP('2022-09-15 09:19:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y',TO_TIMESTAMP('2022-09-15 09:19:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

