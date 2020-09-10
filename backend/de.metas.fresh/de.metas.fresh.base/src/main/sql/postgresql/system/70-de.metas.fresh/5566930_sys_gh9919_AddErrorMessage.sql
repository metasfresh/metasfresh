-- 2020-09-10T09:04:31.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545002,0,TO_TIMESTAMP('2020-09-10 12:04:26','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','There is not default locator for the transit warehouse {}','E',TO_TIMESTAMP('2020-09-10 12:04:26','YYYY-MM-DD HH24:MI:SS'),100,'org.eevolution.api.impl.DD_Order_MovementBuilder.DD_Order_DD_Order_NoTransitLocator')
;

-- 2020-09-10T09:04:31.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=545002 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-09-10T09:04:36.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Beim Transitlager ist kein Standard-Lagerort hinterlegt',Updated=TO_TIMESTAMP('2020-09-10 12:04:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545002
;

-- 2020-09-10T09:04:44.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Beim Transitlager ist kein Standard-Lagerort hinterlegt',Updated=TO_TIMESTAMP('2020-09-10 12:04:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545002
;

-- 2020-09-10T09:04:48.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-09-10 12:04:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545002
;

-- 2020-09-10T09:11:38.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The transit warehouse has no default locator',Updated=TO_TIMESTAMP('2020-09-10 12:11:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545002
;

-- 2020-09-10T09:11:44.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The transit warehouse has no default locator',Updated=TO_TIMESTAMP('2020-09-10 12:11:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545002
;

