-- 2017-08-10T15:48:27.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544473,0,TO_TIMESTAMP('2017-08-10 15:48:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Dateien','I',TO_TIMESTAMP('2017-08-10 15:48:27','YYYY-MM-DD HH24:MI:SS'),100,'webui.window.attachment.title')
;

-- 2017-08-10T15:48:27.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544473 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-08-10T15:48:49.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-10 15:48:49','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Files' WHERE AD_Message_ID=544473 AND AD_Language='en_US'
;

-- 2017-08-10T15:49:34.810
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544474,0,TO_TIMESTAMP('2017-08-10 15:49:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Datei hochladen','I',TO_TIMESTAMP('2017-08-10 15:49:34','YYYY-MM-DD HH24:MI:SS'),100,'webui.window.attachment.uploading')
;

-- 2017-08-10T15:49:34.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544474 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-08-10T15:49:45.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-10 15:49:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Uploading file' WHERE AD_Message_ID=544474 AND AD_Language='en_US'
;

-- 2017-08-10T15:50:18.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544475,0,TO_TIMESTAMP('2017-08-10 15:50:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Dateiupload war erfolgreich','I',TO_TIMESTAMP('2017-08-10 15:50:18','YYYY-MM-DD HH24:MI:SS'),100,'webui.window.attachment.upload.success')
;

-- 2017-08-10T15:50:18.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544475 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-08-10T15:50:45.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-10 15:50:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='File upload succeeded' WHERE AD_Message_ID=544475 AND AD_Language='en_US'
;

-- 2017-08-10T15:51:15.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544476,0,TO_TIMESTAMP('2017-08-10 15:51:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Fehler beim Dateiupload','I',TO_TIMESTAMP('2017-08-10 15:51:15','YYYY-MM-DD HH24:MI:SS'),100,'webui.window.attachment.upload.error')
;

-- 2017-08-10T15:51:15.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544476 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-08-10T15:51:38.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-10 15:51:38','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='File upload error' WHERE AD_Message_ID=544476 AND AD_Language='en_US'
;

