-- 2019-06-05T23:44:37.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544925,0,TO_TIMESTAMP('2019-06-05 23:44:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Limited to {limit} rows of {total}','I',TO_TIMESTAMP('2019-06-05 23:44:36','YYYY-MM-DD HH24:MI:SS'),100,'webui.view.limitTo')
;

-- 2019-06-05T23:44:37.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544925 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-06-05T23:44:49.534
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Begrenzt auf {limit} von {total} Zeilen',Updated=TO_TIMESTAMP('2019-06-05 23:44:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544925
;

-- 2019-06-05T23:44:59.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Begrenzt auf {limit} von {total} Zeilen',Updated=TO_TIMESTAMP('2019-06-05 23:44:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544925
;

-- 2019-06-05T23:45:09.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Begrenzt auf {limit} von {total} Zeilen',Updated=TO_TIMESTAMP('2019-06-05 23:45:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544925
;

-- 2019-06-05T23:57:06.763
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Limited to {limit} rows of {total}',Updated=TO_TIMESTAMP('2019-06-05 23:57:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544925
;

