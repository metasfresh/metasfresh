-- 2020-05-06T06:18:34.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,MsgText,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Message_ID,Value,AD_Org_ID,EntityType) VALUES ('I','Please select a contact with valid email address.',0,'Y',TO_TIMESTAMP('2020-05-06 09:18:33','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2020-05-06 09:18:33','YYYY-MM-DD HH24:MI:SS'),100,544982,'de.metas.order.model.interceptor.C_Order.PleaseSelectAContactWithValidEmailAddress ',0,'D')
;

-- 2020-05-06T06:18:34.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544982 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-05-06T06:18:45.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Bitte einen Kontakt mit gültiger E-Mail-Adresse auswählen.', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-05-06 09:18:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544982
;

-- 2020-05-06T06:18:47.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-05-06 09:18:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544982
;

-- 2020-05-06T06:18:54.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Bitte einen Kontakt mit gültiger E-Mail-Adresse auswählen.',Updated=TO_TIMESTAMP('2020-05-06 09:18:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544982
;

-- 2020-05-06T06:24:52.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='de.metas.order.model.interceptor.C_Order.PleaseSelectAContactWithValidEmailAddress',Updated=TO_TIMESTAMP('2020-05-06 09:24:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544982
;

