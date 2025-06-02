


-- 2024-05-13T12:54:14.813Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('1',0,0,585389,'Y','de.metas.ui.web.contract.flatrate.process.WEBUI_C_Flatrate_DataEntry_Detail_Launcher','N',TO_TIMESTAMP('2024-05-13 14:54:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Datenerfassung','json','N','N','Java',TO_TIMESTAMP('2024-05-13 14:54:12','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_C_Flatrate_DataEntry_Detail_Launcher')
;

-- 2024-05-13T12:54:15.282Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585389 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

