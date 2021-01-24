-- 2021-01-21T15:46:30.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584790,'Y','de.metas.servicerepair.project.process.C_Project_RecalculateSummary','N',TO_TIMESTAMP('2021-01-21 17:46:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.servicerepair','Y','N','N','N','N','N','N','Y','Y',0,'Recalculate Summary','json','N','N','Java',TO_TIMESTAMP('2021-01-21 17:46:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.servicerepair.project.process.C_Project_RecalculateSummary')
;

-- 2021-01-21T15:46:30.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584790 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-01-21T15:47:58.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584790,203,540888,TO_TIMESTAMP('2021-01-21 17:47:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.servicerepair','Y',TO_TIMESTAMP('2021-01-21 17:47:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;

