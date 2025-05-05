-- 2022-08-11T10:35:27.183Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585089,'Y','de.metas.serviceprovider.external.process.RegisterIssuesWebhookProcess','N',TO_TIMESTAMP('2022-08-11 13:35:26','YYYY-MM-DD HH24:MI:SS'),100,'Creates a webhook in Github such that metasfresh gets notified when an issue is created.','D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Register issues webhook','json','N','N','xls','Java',TO_TIMESTAMP('2022-08-11 13:35:26','YYYY-MM-DD HH24:MI:SS'),100,'RegisterIssuesWebhookProcess')
;

-- 2022-08-11T10:35:27.192Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585089 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2022-08-11T10:35:44.081Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585089,541489,541138,TO_TIMESTAMP('2022-08-11 13:35:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2022-08-11 13:35:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2022-08-11T10:36:03.388Z
UPDATE AD_Process_Trl SET Description='Erstellt einen Webhook in Github, damit metasfresh benachrichtigt wird, wenn eine Issue erstellt wird.', Name='Issue Webhook registrieren',Updated=TO_TIMESTAMP('2022-08-11 13:36:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585089
;

-- 2022-08-11T10:36:12.461Z
UPDATE AD_Process_Trl SET Description='Erstellt einen Webhook in Github, damit metasfresh benachrichtigt wird, wenn eine Issue erstellt wird.', Name='Issue Webhook registrieren',Updated=TO_TIMESTAMP('2022-08-11 13:36:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585089
;

-- 2022-08-11T10:36:21.895Z
UPDATE AD_Process_Trl SET Description='Erstellt einen Webhook in Github, damit metasfresh benachrichtigt wird, wenn eine Issue erstellt wird.', Name='Issue Webhook registrieren',Updated=TO_TIMESTAMP('2022-08-11 13:36:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585089
;

-- 2022-08-11T10:36:30.054Z
UPDATE AD_Process SET Description='Erstellt einen Webhook in Github, damit metasfresh benachrichtigt wird, wenn eine Issue erstellt wird.', Help=NULL, Name='Issue Webhook registrieren',Updated=TO_TIMESTAMP('2022-08-11 13:36:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585089
;

-- 2022-08-11T10:36:30.043Z
UPDATE AD_Process_Trl SET Description='Erstellt einen Webhook in Github, damit metasfresh benachrichtigt wird, wenn eine Issue erstellt wird.', Name='Issue Webhook registrieren',Updated=TO_TIMESTAMP('2022-08-11 13:36:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585089
;

update ad_process
set showhelp='Y',
    updatedby=99
where ad_process_id = 585089;


-- 2022-08-11T10:38:15.219Z
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=541138
;

-- 2022-08-11T10:38:26.224Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585089,541466,541139,TO_TIMESTAMP('2022-08-11 13:38:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2022-08-11 13:38:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;
