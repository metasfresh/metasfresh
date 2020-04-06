-- 2017-04-21T10:04:29.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544307,0,TO_TIMESTAMP('2017-04-21 10:04:29','YYYY-MM-DD HH24:MI:SS'),0,'U','Y','PPOrderIncludedHUEditorActions.issueSelectedHUs','I',TO_TIMESTAMP('2017-04-21 10:04:29','YYYY-MM-DD HH24:MI:SS'),0,'PPOrderIncludedHUEditorActions.issueSelectedHUs')
;

-- 2017-04-21T10:04:29.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544307 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-04-21T12:37:33.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=540779
;

-- 2017-04-21T12:37:33.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=540779
;

-- 2017-04-21T12:40:03.764
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET EntityType='de.metas.ui.web', MsgText='Issue selected HUs',Updated=TO_TIMESTAMP('2017-04-21 12:40:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544307
;

-- 2017-04-21T12:40:03.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=544307
;

-- 2017-04-21T12:40:12.678
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-04-21 12:40:12','YYYY-MM-DD HH24:MI:SS'),MsgText='Issue selected HUs' WHERE AD_Message_ID=544307 AND AD_Language='de_CH'
;

-- 2017-04-21T12:40:17.056
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-04-21 12:40:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Issue selected HUs' WHERE AD_Message_ID=544307 AND AD_Language='en_US'
;

