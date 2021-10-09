-- 2021-01-22T08:58:38.980Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,IsDirectPrint,Name,AccessLevel,ShowHelp,IsBetaFunctionality,CopyFromProcess,UpdatedBy,AD_Process_ID,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,Type,IsTranslateExcelHeaders,IsNotifyUserAfterExecution,PostgrestResponseFormat,AD_Org_ID,Value,Classname,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2021-01-22 10:58:38','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2021-01-22 10:58:38','YYYY-MM-DD HH24:MI:SS'),'N','N','Open closed Project','3','N','N','N',100,584791,'Y','Y','N','N','N',0,'Java','Y','N','json',0,'C_Project_UnClose','de.metas.project.process.C_Project_UnClose','D')
;

-- 2021-01-22T08:58:39.033Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Name,Help,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Name,t.Help,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584791 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-01-22T08:58:53.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584791,203,540889,TO_TIMESTAMP('2021-01-22 10:58:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2021-01-22 10:58:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;

