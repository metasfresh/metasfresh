-- Value: auth.Invalid2FACode
-- 2023-09-29T13:51:13.793918600Z
UPDATE AD_Message_Trl SET MsgText='Ungültiger Authentisierungscode',Updated=TO_TIMESTAMP('2023-09-29 16:51:13.793','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545343
;

-- Value: auth.Invalid2FACode
-- 2023-09-29T13:51:18.782254100Z
UPDATE AD_Message_Trl SET MsgText='Ungültiger Authentisierungscode',Updated=TO_TIMESTAMP('2023-09-29 16:51:18.782','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545343
;

-- 2023-09-29T13:51:18.783832700Z
UPDATE AD_Message SET MsgText='Ungültiger Authentisierungscode', Updated=TO_TIMESTAMP('2023-09-29 16:51:18.783','YYYY-MM-DD HH24:MI:SS.US') WHERE AD_Message_ID=545343
;

-- Value: webui.login.2FA.caption
-- 2023-09-29T13:51:37.567753700Z
UPDATE AD_Message_Trl SET MsgText='Authentisierungscode eingeben',Updated=TO_TIMESTAMP('2023-09-29 16:51:37.567','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545342
;

-- Value: webui.login.2FA.caption
-- 2023-09-29T13:51:42.736851200Z
UPDATE AD_Message_Trl SET MsgText='Authentisierungscode eingeben',Updated=TO_TIMESTAMP('2023-09-29 16:51:42.736','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545342
;

-- 2023-09-29T13:51:42.737889700Z
UPDATE AD_Message SET MsgText='Authentisierungscode eingeben', Updated=TO_TIMESTAMP('2023-09-29 16:51:42.737','YYYY-MM-DD HH24:MI:SS.US') WHERE AD_Message_ID=545342
;

-- Process: AD_User_Regenerate2FA(de.metas.security.user_2fa.process.AD_User_Regenerate2FA)
-- 2023-09-29T16:44:50.857207100Z
UPDATE AD_Process_Trl SET Name='Authentisierungscode neu generieren',Updated=TO_TIMESTAMP('2023-09-29 19:44:50.855','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585320
;

-- Process: AD_User_Regenerate2FA(de.metas.security.user_2fa.process.AD_User_Regenerate2FA)
-- 2023-09-29T16:44:57.435816500Z
UPDATE AD_Process_Trl SET Name='Authentisierungscode neu generieren',Updated=TO_TIMESTAMP('2023-09-29 19:44:57.435','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585320
;

-- 2023-09-29T16:44:57.437373200Z
UPDATE AD_Process SET Name='Authentisierungscode neu generieren', Updated=TO_TIMESTAMP('2023-09-29 19:44:57.437','YYYY-MM-DD HH24:MI:SS.US') WHERE AD_Process_ID=585320
;

-- Process: AD_User_Regenerate2FA(de.metas.security.user_2fa.process.AD_User_Regenerate2FA)
-- 2023-09-29T16:46:19.559682500Z
UPDATE AD_Process_Trl SET Name='Zwei-Faktor-Authentisierung neu generieren',Updated=TO_TIMESTAMP('2023-09-29 19:46:19.559','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585320
;

-- Process: AD_User_Regenerate2FA(de.metas.security.user_2fa.process.AD_User_Regenerate2FA)
-- 2023-09-29T16:46:21.457712800Z
UPDATE AD_Process_Trl SET Name='Zwei-Faktor-Authentisierung neu generieren',Updated=TO_TIMESTAMP('2023-09-29 19:46:21.457','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585320
;

-- 2023-09-29T16:46:21.458747300Z
UPDATE AD_Process SET Name='Zwei-Faktor-Authentisierung neu generieren', Updated=TO_TIMESTAMP('2023-09-29 19:46:21.458','YYYY-MM-DD HH24:MI:SS.US') WHERE AD_Process_ID=585320
;

