-- 2017-09-18T18:57:44.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544501,0,TO_TIMESTAMP('2017-09-18 18:57:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Download selected','M',TO_TIMESTAMP('2017-09-18 18:57:44','YYYY-MM-DD HH24:MI:SS'),100,'window.downloadSelected.caption')
;

-- 2017-09-18T18:57:44.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544501 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-09-18T18:58:27.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544502,0,TO_TIMESTAMP('2017-09-18 18:58:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Download selected (nothing selected)','M',TO_TIMESTAMP('2017-09-18 18:58:26','YYYY-MM-DD HH24:MI:SS'),100,'window.downloadSelected.nothingSelected')
;

-- 2017-09-18T18:58:27.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544502 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-09-18T19:00:10.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='webui.window.downloadSelected.nothingSelected',Updated=TO_TIMESTAMP('2017-09-18 19:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544502
;

-- 2017-09-18T19:00:19.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='webui.window.downloadSelected.caption',Updated=TO_TIMESTAMP('2017-09-18 19:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544501
;

-- 2017-09-18T19:01:27.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='nothing selected',Updated=TO_TIMESTAMP('2017-09-18 19:01:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544502
;

-- 2017-09-18T19:02:09.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-09-18 19:02:09','YYYY-MM-DD HH24:MI:SS'),MsgText='nothing selected' WHERE AD_Message_ID=544502 AND AD_Language='de_CH'
;

-- 2017-09-18T19:02:13.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-09-18 19:02:13','YYYY-MM-DD HH24:MI:SS'),MsgText='nothing selected' WHERE AD_Message_ID=544502 AND AD_Language='nl_NL'
;

-- 2017-09-18T19:02:20.573
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-09-18 19:02:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='nothing selected' WHERE AD_Message_ID=544502 AND AD_Language='en_US'
;

