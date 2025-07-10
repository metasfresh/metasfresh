-- Run mode: SWING_CLIENT

-- Value: webui.login.oauth2.separatorText
-- 2025-07-10T11:33:12.561Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545568,0,TO_TIMESTAMP('2025-07-10 14:33:12.297','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','ODER','I',TO_TIMESTAMP('2025-07-10 14:33:12.297','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.login.oauth2.separatorText')
;

-- 2025-07-10T11:33:12.572Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545568 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.login.oauth2.separatorText
-- 2025-07-10T11:33:17.976Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-07-10 14:33:17.976','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545568
;

-- Value: webui.login.oauth2.separatorText
-- 2025-07-10T11:33:20.645Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-07-10 14:33:20.644','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545568
;

-- Value: webui.login.oauth2.separatorText
-- 2025-07-10T11:33:24.541Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='OR',Updated=TO_TIMESTAMP('2025-07-10 14:33:24.541','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545568
;

-- Value: webui.login.oauth2.loginWithCaption
-- 2025-07-10T11:35:14.645Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545569,0,TO_TIMESTAMP('2025-07-10 14:35:14.468','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Mit %(name) anmelden','I',TO_TIMESTAMP('2025-07-10 14:35:14.468','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.login.oauth2.loginWithCaption')
;

-- 2025-07-10T11:35:14.647Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545569 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.login.oauth2.loginWithCaption
-- 2025-07-10T11:35:24.587Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-07-10 14:35:24.587','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545569
;

-- Value: webui.login.oauth2.loginWithCaption
-- 2025-07-10T11:35:29.691Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Login with %(name)',Updated=TO_TIMESTAMP('2025-07-10 14:35:29.69','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545569
;

-- Value: webui.login.oauth2.loginWithCaption
-- 2025-07-10T11:35:31.553Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-07-10 14:35:31.553','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545569
;

-- Value: webui.login.oauth2.loginWithCaption
-- 2025-07-10T11:40:25.570Z
UPDATE AD_Message SET MsgText='Mit %(name)s anmelden',Updated=TO_TIMESTAMP('2025-07-10 14:40:25.567','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545569
;

-- 2025-07-10T11:40:25.583Z
UPDATE AD_Message_Trl trl SET MsgText='Mit %(name)s anmelden' WHERE AD_Message_ID=545569 AND AD_Language='de_DE'
;

-- Value: webui.login.oauth2.loginWithCaption
-- 2025-07-10T11:40:33.314Z
UPDATE AD_Message_Trl SET MsgText='Mit %(name)s anmelden',Updated=TO_TIMESTAMP('2025-07-10 14:40:33.314','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545569
;

-- Value: webui.login.oauth2.loginWithCaption
-- 2025-07-10T11:40:36.881Z
UPDATE AD_Message_Trl SET MsgText='Login with %(name)s',Updated=TO_TIMESTAMP('2025-07-10 14:40:36.881','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545569
;

-- Value: webui.login.oauth2.loginWithCaption
-- 2025-07-10T11:40:43.159Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Mit %(name)s anmelden',Updated=TO_TIMESTAMP('2025-07-10 14:40:43.159','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_CH' AND AD_Message_ID=545569
;

-- Value: webui.login.oauth2.loginWithCaption
-- 2025-07-10T11:40:48.765Z
UPDATE AD_Message_Trl SET MsgText='Mit %(name)s anmelden',Updated=TO_TIMESTAMP('2025-07-10 14:40:48.765','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545569
;

