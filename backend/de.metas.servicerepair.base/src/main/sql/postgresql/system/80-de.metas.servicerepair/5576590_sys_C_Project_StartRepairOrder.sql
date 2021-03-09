-- 2021-01-15T18:22:28.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584786,'Y','de.metas.servicerepair.project.process.C_Project_StartRepairOrder','N',TO_TIMESTAMP('2021-01-15 20:22:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.servicerepair','Y','N','N','N','N','N','N','Y','Y',0,'Create Repair Order(s)','json','N','N','Java',TO_TIMESTAMP('2021-01-15 20:22:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.servicerepair.project.process.C_Project_StartRepairOrder')
;

-- 2021-01-15T18:22:28.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584786 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-01-15T18:23:51.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584786,203,540886,TO_TIMESTAMP('2021-01-15 20:23:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.servicerepair','Y',TO_TIMESTAMP('2021-01-15 20:23:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;

