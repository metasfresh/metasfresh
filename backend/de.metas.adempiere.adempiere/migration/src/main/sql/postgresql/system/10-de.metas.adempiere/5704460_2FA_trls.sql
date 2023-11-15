-- Value: auth.Invalid2FACode
-- 2023-09-29T05:14:31.286158900Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Ungültiger Authentifizierungscode',Updated=TO_TIMESTAMP('2023-09-29 08:14:31.285','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545343
;

-- Value: auth.Invalid2FACode
-- 2023-09-29T05:14:35.318997900Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-09-29 08:14:35.318','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545343
;

-- Value: auth.Invalid2FACode
-- 2023-09-29T05:14:39.835347200Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Ungültiger Authentifizierungscode',Updated=TO_TIMESTAMP('2023-09-29 08:14:39.835','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545343
;

-- 2023-09-29T05:14:39.839875800Z
UPDATE AD_Message SET MsgText='Ungültiger Authentifizierungscode', Updated=TO_TIMESTAMP('2023-09-29 08:14:39.838','YYYY-MM-DD HH24:MI:SS.US') WHERE AD_Message_ID=545343
;

-- Value: webui.login.2FA.caption
-- 2023-09-29T05:15:01.592098800Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Geben Sie den Authentifizierungscode ein',Updated=TO_TIMESTAMP('2023-09-29 08:15:01.59','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545342
;

-- 2023-09-29T05:15:01.594292800Z
UPDATE AD_Message SET MsgText='Geben Sie den Authentifizierungscode ein', Updated=TO_TIMESTAMP('2023-09-29 08:15:01.593','YYYY-MM-DD HH24:MI:SS.US') WHERE AD_Message_ID=545342
;

-- Value: webui.login.2FA.caption
-- 2023-09-29T05:15:06.282766Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-09-29 08:15:06.282','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545342
;

-- Value: webui.login.2FA.caption
-- 2023-09-29T05:15:10.726541100Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Geben Sie den Authentifizierungscode ein',Updated=TO_TIMESTAMP('2023-09-29 08:15:10.726','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545342
;

-- Process: AD_User_Enable2FA(de.metas.security.user_2fa.process.AD_User_Enable2FA)
-- 2023-09-29T05:15:47.356924800Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Zwei-Faktor-Authentisierung aktivieren',Updated=TO_TIMESTAMP('2023-09-29 08:15:47.355','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585318
;

-- Process: AD_User_Enable2FA(de.metas.security.user_2fa.process.AD_User_Enable2FA)
-- 2023-09-29T05:15:53.241711700Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Zwei-Faktor-Authentisierung aktivieren',Updated=TO_TIMESTAMP('2023-09-29 08:15:53.241','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585318
;

-- 2023-09-29T05:15:53.244453300Z
UPDATE AD_Process SET Name='Zwei-Faktor-Authentisierung aktivieren', Updated=TO_TIMESTAMP('2023-09-29 08:15:53.243','YYYY-MM-DD HH24:MI:SS.US') WHERE AD_Process_ID=585318
;

-- Process: AD_User_Enable2FA(de.metas.security.user_2fa.process.AD_User_Enable2FA)
-- 2023-09-29T05:15:55.476346400Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-09-29 08:15:55.475','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585318
;

-- Process: AD_User_Disable2FA(de.metas.security.user_2fa.process.AD_User_Disable2FA)
-- 2023-09-29T05:16:17.142706700Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Zwei-Faktor-Authentisierung deaktivieren',Updated=TO_TIMESTAMP('2023-09-29 08:16:17.141','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585319
;

-- Process: AD_User_Disable2FA(de.metas.security.user_2fa.process.AD_User_Disable2FA)
-- 2023-09-29T05:16:24.186267100Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Zwei-Faktor-Authentisierung deaktivieren',Updated=TO_TIMESTAMP('2023-09-29 08:16:24.185','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585319
;

-- 2023-09-29T05:16:24.189024Z
UPDATE AD_Process SET Name='Zwei-Faktor-Authentisierung deaktivieren', Updated=TO_TIMESTAMP('2023-09-29 08:16:24.188','YYYY-MM-DD HH24:MI:SS.US') WHERE AD_Process_ID=585319
;

-- Process: AD_User_Disable2FA(de.metas.security.user_2fa.process.AD_User_Disable2FA)
-- 2023-09-29T05:16:26.409112Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-09-29 08:16:26.408','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585319
;

-- Process: AD_User_Regenerate2FA(de.metas.security.user_2fa.process.AD_User_Regenerate2FA)
-- 2023-09-29T05:17:07.229239800Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Zwei-Faktor-Authentisierung regenerieren',Updated=TO_TIMESTAMP('2023-09-29 08:17:07.228','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585320
;

-- Process: AD_User_Regenerate2FA(de.metas.security.user_2fa.process.AD_User_Regenerate2FA)
-- 2023-09-29T05:17:11.852860100Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Zwei-Faktor-Authentisierung regenerieren',Updated=TO_TIMESTAMP('2023-09-29 08:17:11.851','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585320
;

-- 2023-09-29T05:17:11.855560200Z
UPDATE AD_Process SET Name='Zwei-Faktor-Authentisierung regenerieren', Updated=TO_TIMESTAMP('2023-09-29 08:17:11.855','YYYY-MM-DD HH24:MI:SS.US') WHERE AD_Process_ID=585320
;

-- Process: AD_User_Regenerate2FA(de.metas.security.user_2fa.process.AD_User_Regenerate2FA)
-- 2023-09-29T05:17:13.858265500Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-09-29 08:17:13.857','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585320
;

