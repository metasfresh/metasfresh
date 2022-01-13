-- 2021-05-24T08:32:56.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545032,0,TO_TIMESTAMP('2021-05-24 11:32:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','There are more results. Please refine your search.','I',TO_TIMESTAMP('2021-05-24 11:32:56','YYYY-MM-DD HH24:MI:SS'),100,'webui.widget.lookup.hasMoreResults')
;

-- 2021-05-24T08:32:56.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545032 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-05-24T09:10:09.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Es gibt weitere Ergebnisse. Bitte die Suche weiter einschränken.',Updated=TO_TIMESTAMP('2021-05-24 12:10:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545032
;

-- 2021-05-24T09:10:13.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Es gibt weitere Ergebnisse. Bitte die Suche weiter einschränken.',Updated=TO_TIMESTAMP('2021-05-24 12:10:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545032
;

-- 2021-05-24T09:10:16.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-24 12:10:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545032
;

-- 2021-05-24T09:10:20.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Es gibt weitere Ergebnisse. Bitte die Suche weiter einschränken.',Updated=TO_TIMESTAMP('2021-05-24 12:10:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545032
;

