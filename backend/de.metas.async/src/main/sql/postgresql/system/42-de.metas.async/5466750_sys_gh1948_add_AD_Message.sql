-- 2017-07-04T15:37:09.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544429,0,TO_TIMESTAMP('2017-07-04 15:37:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.async','Y','Es wurden {} asynchrone Arbeitspakete erstellt','I',TO_TIMESTAMP('2017-07-04 15:37:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.async.C_QueueC_WorkPackages_Created_1P')
;

-- 2017-07-04T15:37:09.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544429 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-07-04T15:37:47.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-04 15:37:47','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='{} asynchronous work packages created' WHERE AD_Message_ID=544429 AND AD_Language='en_US'
;


-- 2017-07-04T15:42:42.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Es wurden {0} asynchrone Arbeitspakete erstellt',Updated=TO_TIMESTAMP('2017-07-04 15:42:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544429
;

-- 2017-07-04T15:42:47.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-07-04 15:42:47','YYYY-MM-DD HH24:MI:SS'),MsgText='{0} asynchronous work packages created' WHERE AD_Message_ID=544429 AND AD_Language='en_US'
;

