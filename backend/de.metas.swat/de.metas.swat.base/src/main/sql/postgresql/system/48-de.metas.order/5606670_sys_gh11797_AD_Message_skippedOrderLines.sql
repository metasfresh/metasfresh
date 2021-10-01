-- 2021-09-27T12:56:42.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545060,0,TO_TIMESTAMP('2021-09-27 15:56:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Skipped order line ids: {0}','I',TO_TIMESTAMP('2021-09-27 15:56:41','YYYY-MM-DD HH24:MI:SS'),100,'SkippedOrderLines')
;

-- 2021-09-27T12:56:42.376Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545060 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-09-27T12:56:56.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Übersprungene Auftragszeilen-IDs: {0}',Updated=TO_TIMESTAMP('2021-09-27 15:56:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545060
;

-- 2021-09-27T12:57:02.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Übersprungene Auftragszeilen-IDs: {0}',Updated=TO_TIMESTAMP('2021-09-27 15:57:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545060
;

-- 2021-09-27T12:57:17.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Übersprungene Auftragszeilen-IDs: {0}',Updated=TO_TIMESTAMP('2021-09-27 15:57:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545060
;

-- 2021-09-30T12:54:52.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Skipped order lines: {0}',Updated=TO_TIMESTAMP('2021-09-30 15:54:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545060
;

-- 2021-09-30T12:55:04.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Übersprungene Auftragszeilen: {0}',Updated=TO_TIMESTAMP('2021-09-30 15:55:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545060
;

-- 2021-09-30T12:55:06.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Übersprungene Auftragszeilen: {0}',Updated=TO_TIMESTAMP('2021-09-30 15:55:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545060
;

-- 2021-09-30T12:55:32.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Skipped sales order lines: {0}',Updated=TO_TIMESTAMP('2021-09-30 15:55:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545060
;

-- 2021-09-30T12:55:36.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Übersprungene Auftragszeilen: {0}',Updated=TO_TIMESTAMP('2021-09-30 15:55:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545060
;

-- 2021-09-30T12:55:53.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Skipped sales order lines {0}',Updated=TO_TIMESTAMP('2021-09-30 15:55:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545060
;

-- 2021-09-30T12:55:59.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Skipped sales order lines {0}',Updated=TO_TIMESTAMP('2021-09-30 15:55:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545060
;

-- 2021-09-30T12:56:31.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Skipped sales order lines: {0}',Updated=TO_TIMESTAMP('2021-09-30 15:56:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545060
;

-- 2021-09-30T12:56:38.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Skipped sales order lines: {0}',Updated=TO_TIMESTAMP('2021-09-30 15:56:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545060
;