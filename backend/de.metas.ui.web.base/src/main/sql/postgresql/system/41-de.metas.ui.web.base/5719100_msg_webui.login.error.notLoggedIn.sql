-- Value: webui.login.error.notLoggedIn
-- 2024-03-13T09:31:31.098Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545377,0,TO_TIMESTAMP('2024-03-13 11:31:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Benutzer ist nicht angemeldet','E',TO_TIMESTAMP('2024-03-13 11:31:30','YYYY-MM-DD HH24:MI:SS'),100,'webui.login.error.notLoggedIn')
;

-- 2024-03-13T09:31:31.102Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545377 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.login.error.notLoggedIn
-- 2024-03-13T09:31:33.818Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-13 11:31:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545377
;

-- Value: webui.login.error.notLoggedIn
-- 2024-03-13T09:31:42.855Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='User is not logged in',Updated=TO_TIMESTAMP('2024-03-13 11:31:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545377
;

-- Value: webui.login.error.notLoggedIn
-- 2024-03-13T09:31:45.609Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-13 11:31:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545377
;

