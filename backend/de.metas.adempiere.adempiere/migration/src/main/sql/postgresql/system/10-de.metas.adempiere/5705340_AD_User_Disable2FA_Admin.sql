-- Value: AD_User_Disable2FA_Admin
-- Classname: de.metas.security.user_2fa.process.AD_User_Disable2FA_Admin
-- 2023-10-05T11:01:56.809184500Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585324,'Y','de.metas.security.user_2fa.process.AD_User_Disable2FA_Admin','N',TO_TIMESTAMP('2023-10-05 14:01:56.547','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Zwei-Faktor-Authentisierung deaktivieren','json','N','N','xls','Java',TO_TIMESTAMP('2023-10-05 14:01:56.547','YYYY-MM-DD HH24:MI:SS.US'),100,'AD_User_Disable2FA_Admin')
;

-- 2023-10-05T11:01:56.823393200Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585324 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: AD_User_Disable2FA_Admin(de.metas.security.user_2fa.process.AD_User_Disable2FA_Admin)
-- 2023-10-05T11:02:18.793777200Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Disable Two-factor authentication',Updated=TO_TIMESTAMP('2023-10-05 14:02:18.793','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585324
;

-- Process: AD_User_Disable2FA_Admin(de.metas.security.user_2fa.process.AD_User_Disable2FA_Admin)
-- 2023-10-05T11:02:20.423253900Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-05 14:02:20.423','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585324
;

-- Process: AD_User_Disable2FA_Admin(de.metas.security.user_2fa.process.AD_User_Disable2FA_Admin)
-- 2023-10-05T11:02:22.063436400Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-05 14:02:22.063','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585324
;

-- Process: AD_User_Disable2FA_Admin(de.metas.security.user_2fa.process.AD_User_Disable2FA_Admin)
-- Table: AD_User
-- EntityType: D
-- 2023-10-05T11:10:29.999776200Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585324,114,541432,TO_TIMESTAMP('2023-10-05 14:10:29.75','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y',TO_TIMESTAMP('2023-10-05 14:10:29.75','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

-- Process: AD_User_Enable2FA(de.metas.security.user_2fa.process.AD_User_Enable2FA)
-- Table: AD_User
-- EntityType: D
-- 2023-10-05T15:10:36.074889200Z
UPDATE AD_Table_Process SET WEBUI_ViewAction='Y',Updated=TO_TIMESTAMP('2023-10-05 18:10:36.074','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=541423
;

-- Process: AD_User_Disable2FA(de.metas.security.user_2fa.process.AD_User_Disable2FA)
-- Table: AD_User
-- EntityType: D
-- 2023-10-05T15:10:49.637376700Z
UPDATE AD_Table_Process SET WEBUI_ViewAction='Y',Updated=TO_TIMESTAMP('2023-10-05 18:10:49.636','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=541424
;

-- Process: AD_User_Regenerate2FA(de.metas.security.user_2fa.process.AD_User_Regenerate2FA)
-- Table: AD_User
-- EntityType: D
-- 2023-10-05T15:11:01.511354500Z
UPDATE AD_Table_Process SET WEBUI_ViewAction='Y',Updated=TO_TIMESTAMP('2023-10-05 18:11:01.511','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=541425
;

