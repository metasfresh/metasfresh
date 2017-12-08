-- 2017-12-06T14:33:48.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544597,0,TO_TIMESTAMP('2017-12-06 14:33:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment','Y','Fehlende Bankverbindung in Zeile(n) Nr','E',TO_TIMESTAMP('2017-12-06 14:33:47','YYYY-MM-DD HH24:MI:SS'),100,'C_PaySelection_PaySelectionLines_No_BankAccount')
;

-- 2017-12-06T14:33:48.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544597 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;



-- 2017-12-06T15:39:27.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Fehlende Bankverbindung in Zeile(n) Nr {}',Updated=TO_TIMESTAMP('2017-12-06 15:39:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544597
;

-- 2017-12-06T15:57:48.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Fehlende Bankverbindung in Zeile(n) Nr  {}',Updated=TO_TIMESTAMP('2017-12-06 15:57:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544597
;

-- 2017-12-06T15:58:32.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Fehlende Bankverbindung in Zeile(n) Nr: {0}',Updated=TO_TIMESTAMP('2017-12-06 15:58:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544597
;


-- 2017-12-06T16:17:22.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET EntityType='de.metas.banking',Updated=TO_TIMESTAMP('2017-12-06 16:17:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544597
;

-- 2017-12-06T16:27:09.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Fehlende Bankverbindung in Zeile(n) Nr : {0}',Updated=TO_TIMESTAMP('2017-12-06 16:27:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544597
;

-- 2017-12-06T16:27:16.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-12-06 16:27:16','YYYY-MM-DD HH24:MI:SS'),MsgText='Fehlende Bankverbindung in Zeile(n) Nr : {0}' WHERE AD_Message_ID=544597 AND AD_Language='en_US'
;

-- 2017-12-06T16:27:20.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-12-06 16:27:20','YYYY-MM-DD HH24:MI:SS'),MsgText='Fehlende Bankverbindung in Zeile(n) Nr : {0}' WHERE AD_Message_ID=544597 AND AD_Language='nl_NL'
;

-- 2017-12-06T16:27:23.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-12-06 16:27:23','YYYY-MM-DD HH24:MI:SS'),MsgText='Fehlende Bankverbindung in Zeile(n) Nr : {0}' WHERE AD_Message_ID=544597 AND AD_Language='de_CH'
;

