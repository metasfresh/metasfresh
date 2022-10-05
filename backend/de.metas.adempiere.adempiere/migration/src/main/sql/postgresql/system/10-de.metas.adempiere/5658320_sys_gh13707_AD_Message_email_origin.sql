
-- 2022-10-03T07
-- Value: EMAIL_INFO
-- 2022-10-03T14:38:35.126Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545161,0,TO_TIMESTAMP('2022-10-03 17:38:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','This mail was sent from {0}.','I',TO_TIMESTAMP('2022-10-03 17:38:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.notification.email.origin')
;

-- 2022-10-03T14:38:35.148Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545161 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: EMAIL_INFO
-- 2022-10-03T14:40:12.506Z
UPDATE AD_Message_Trl SET MsgText='Diese E-Mail wurde von {0} gesendet.',Updated=TO_TIMESTAMP('2022-10-03 17:40:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545161
;

-- Value: EMAIL_INFO
-- 2022-10-03T14:40:15.513Z
UPDATE AD_Message_Trl SET MsgText='Diese E-Mail wurde von {0} gesendet.',Updated=TO_TIMESTAMP('2022-10-03 17:40:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545161
;

-- Value: EMAIL_INFO
-- 2022-10-03T14:40:20.172Z
UPDATE AD_Message_Trl SET MsgText='Diese E-Mail wurde von {0} gesendet.',Updated=TO_TIMESTAMP('2022-10-03 17:40:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545161
;

