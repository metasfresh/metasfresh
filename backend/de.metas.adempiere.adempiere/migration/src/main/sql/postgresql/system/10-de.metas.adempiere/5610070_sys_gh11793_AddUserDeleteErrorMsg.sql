-- 2021-10-19T11:26:36.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545067,0,TO_TIMESTAMP('2021-10-19 14:26:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Logged in user can not be deleted','E',TO_TIMESTAMP('2021-10-19 14:26:36','YYYY-MM-DD HH24:MI:SS'),100,'LoggedInUserCannotBeDeleted')
;

-- 2021-10-19T11:26:36.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545067 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-10-19T11:26:42.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Logged in user can not be deleted!!!',Updated=TO_TIMESTAMP('2021-10-19 14:26:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545067
;

-- 2021-10-19T13:06:51.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='The currently logged-in user cannot be deleted.',Updated=TO_TIMESTAMP('2021-10-19 16:06:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545067
;

-- 2021-10-19T13:07:01.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Der aktuell angemeldete Benutzer kann nicht gelöscht werden.',Updated=TO_TIMESTAMP('2021-10-19 16:07:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545067
;

-- 2021-10-19T13:07:06.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Der aktuell angemeldete Benutzer kann nicht gelöscht werden.',Updated=TO_TIMESTAMP('2021-10-19 16:07:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545067
;

-- 2021-10-19T13:07:18.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='The currently logged-in user cannot be deleted.',Updated=TO_TIMESTAMP('2021-10-19 16:07:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545067
;

-- 2021-10-19T13:07:32.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Der aktuell angemeldete Benutzer kann nicht gelöscht werden.',Updated=TO_TIMESTAMP('2021-10-19 16:07:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545067
;
