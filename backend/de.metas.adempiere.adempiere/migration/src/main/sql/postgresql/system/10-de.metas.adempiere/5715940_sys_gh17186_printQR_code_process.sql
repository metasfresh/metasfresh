-- Value: AuthTokenPrintQrCode
-- 2024-01-24T08:51:30.622Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585350,'Y','N',TO_TIMESTAMP('2024-01-24 10:51:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Print authorization QR','json','N','N','xls','Java',TO_TIMESTAMP('2024-01-24 10:51:30','YYYY-MM-DD HH24:MI:SS'),100,'AuthTokenPrintQrCode')
;

-- 2024-01-24T08:51:30.629Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585350 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: AuthTokenPrintQrCode
-- Classname: de.metas.security.process.AuthTokenPrintQrCode
-- 2024-01-24T08:51:41.951Z
UPDATE AD_Process SET Classname='de.metas.security.process.AuthTokenPrintQrCode',Updated=TO_TIMESTAMP('2024-01-24 10:51:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585350
;

-- Process: AuthTokenPrintQrCode(de.metas.security.process.AuthTokenPrintQrCode)
-- Table: AD_User_AuthToken
-- EntityType: D
-- 2024-01-24T08:52:31.930Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585350,540952,541458,TO_TIMESTAMP('2024-01-24 10:52:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2024-01-24 10:52:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','N','N')
;

-- Process: AuthTokenPrintQrCode(de.metas.security.process.AuthTokenPrintQrCode)
-- 2024-01-24T10:19:03.308Z
UPDATE AD_Process_Trl SET Name='Druckberechtigung QR',Updated=TO_TIMESTAMP('2024-01-24 12:19:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585350
;

-- Value: AuthTokenPrintQrCode
-- Classname: de.metas.security.process.AuthTokenPrintQrCode
-- 2024-01-24T10:19:08.270Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Druckberechtigung QR',Updated=TO_TIMESTAMP('2024-01-24 12:19:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585350
;

-- Process: AuthTokenPrintQrCode(de.metas.security.process.AuthTokenPrintQrCode)
-- 2024-01-24T10:19:08.263Z
UPDATE AD_Process_Trl SET Name='Druckberechtigung QR',Updated=TO_TIMESTAMP('2024-01-24 12:19:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585350
;

-- Process: AuthTokenPrintQrCode(de.metas.security.process.AuthTokenPrintQrCode)
-- 2024-01-24T10:19:03.308Z
UPDATE AD_Process_Trl SET Name='Druckberechtigung QR',Updated=TO_TIMESTAMP('2024-01-24 12:19:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585350
;

-- Value: AuthTokenPrintQrCode
-- Classname: de.metas.security.process.AuthTokenPrintQrCode
-- 2024-01-24T10:19:08.270Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Druckberechtigung QR',Updated=TO_TIMESTAMP('2024-01-24 12:19:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585350
;

-- Process: AuthTokenPrintQrCode(de.metas.security.process.AuthTokenPrintQrCode)
-- 2024-01-24T10:19:08.263Z
UPDATE AD_Process_Trl SET Name='Druckberechtigung QR',Updated=TO_TIMESTAMP('2024-01-24 12:19:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585350
;

-- Value: AuthTokenPrintQrCode
-- Classname: de.metas.security.process.AuthTokenPrintQrCode
-- 2024-01-24T10:58:04.434Z
UPDATE AD_Process SET Description='Generate QR code for the selected authentication token.', Help='Generate QR code for the selected authentication token.', ShowHelp='Y',Updated=TO_TIMESTAMP('2024-01-24 12:58:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585350
;

-- Process: AuthTokenPrintQrCode(de.metas.security.process.AuthTokenPrintQrCode)
-- 2024-01-24T10:58:29.617Z
UPDATE AD_Process_Trl SET Description='Generieren Sie einen QR-Code für das ausgewählte Authentifizierungstoken.',Updated=TO_TIMESTAMP('2024-01-24 12:58:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585350
;

-- Value: AuthTokenPrintQrCode
-- Classname: de.metas.security.process.AuthTokenPrintQrCode
-- 2024-01-24T10:58:31.922Z
UPDATE AD_Process SET Description='Generieren Sie einen QR-Code für das ausgewählte Authentifizierungstoken.', Help=NULL, Name='Druckberechtigung QR',Updated=TO_TIMESTAMP('2024-01-24 12:58:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585350
;

-- Process: AuthTokenPrintQrCode(de.metas.security.process.AuthTokenPrintQrCode)
-- 2024-01-24T10:58:31.918Z
UPDATE AD_Process_Trl SET Description='Generieren Sie einen QR-Code für das ausgewählte Authentifizierungstoken.',Updated=TO_TIMESTAMP('2024-01-24 12:58:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585350
;

-- Value: AuthTokenPrintQrCode
-- Classname: de.metas.security.process.AuthTokenPrintQrCode
-- 2024-01-24T10:58:43.160Z
UPDATE AD_Process SET Help='Generieren Sie einen QR-Code für das ausgewählte Authentifizierungstoken.',Updated=TO_TIMESTAMP('2024-01-24 12:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585350
;

-- Process: AuthTokenPrintQrCode(de.metas.security.process.AuthTokenPrintQrCode)
-- 2024-01-24T10:58:57.774Z
UPDATE AD_Process_Trl SET Description='Generate a QR code for the selected authentication token.', Help='Generate a QR code for the selected authentication token.',Updated=TO_TIMESTAMP('2024-01-24 12:58:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585350
;

-- Process: AuthTokenPrintQrCode(de.metas.security.process.AuthTokenPrintQrCode)
-- 2024-01-24T10:59:03.020Z
UPDATE AD_Process_Trl SET Help='Generieren Sie einen QR-Code für das ausgewählte Authentifizierungstoken.',Updated=TO_TIMESTAMP('2024-01-24 12:59:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585350
;

-- Process: AuthTokenPrintQrCode(de.metas.security.process.AuthTokenPrintQrCode)
-- 2024-01-24T10:59:07.158Z
UPDATE AD_Process_Trl SET Help='Generieren Sie einen QR-Code für das ausgewählte Authentifizierungstoken.',Updated=TO_TIMESTAMP('2024-01-24 12:59:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585350
;

