-- 2018-04-28T11:15:28.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544709,0,TO_TIMESTAMP('2018-04-28 11:15:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Dateien in Verarbeitung','I',TO_TIMESTAMP('2018-04-28 11:15:28','YYYY-MM-DD HH24:MI:SS'),100,'webui.window.attachment.title.pending')
;

-- 2018-04-28T11:15:28.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544709 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-04-28T11:16:20.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544710,0,TO_TIMESTAMP('2018-04-28 11:16:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Dateien hochgeladen','I',TO_TIMESTAMP('2018-04-28 11:16:20','YYYY-MM-DD HH24:MI:SS'),100,'webui.window.attachment.title.done')
;

-- 2018-04-28T11:16:20.847
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544710 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-04-28T11:17:02.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544711,0,TO_TIMESTAMP('2018-04-28 11:17:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Dateien Fehler','I',TO_TIMESTAMP('2018-04-28 11:17:02','YYYY-MM-DD HH24:MI:SS'),100,'webui.window.attachment.title.error')
;

-- 2018-04-28T11:17:02.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544711 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-04-28T11:21:02.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-28 11:21:02','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Files uploaded' WHERE AD_Message_ID=544710 AND AD_Language='en_US'
;

-- 2018-04-28T11:21:11.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-28 11:21:11','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Files Error' WHERE AD_Message_ID=544711 AND AD_Language='en_US'
;

-- 2018-04-28T11:21:31.984
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Files pending',Updated=TO_TIMESTAMP('2018-04-28 11:21:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544709
;

-- 2018-04-28T11:25:15.654
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-28 11:25:15','YYYY-MM-DD HH24:MI:SS'),MsgText='Files error' WHERE AD_Message_ID=544711 AND AD_Language='en_US'
;

-- 2018-04-28T11:27:00.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-28 11:27:00','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Files pending' WHERE AD_Message_ID=544709 AND AD_Language='en_US'
;

-- 2018-04-28T11:27:28.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Dateien in Verarbeitung',Updated=TO_TIMESTAMP('2018-04-28 11:27:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544709
;

