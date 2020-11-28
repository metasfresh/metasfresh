-- 2017-05-11T12:09:39.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544319,0,TO_TIMESTAMP('2017-05-11 12:09:39','YYYY-MM-DD HH24:MI:SS'),0,'U','Y','PPOrderIncludedHUEditorActions.issueSelectedTUs','I',TO_TIMESTAMP('2017-05-11 12:09:39','YYYY-MM-DD HH24:MI:SS'),0,'PPOrderIncludedHUEditorActions.issueSelectedTUs')
;

-- 2017-05-11T12:09:39.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544319 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-05-11T20:08:15.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET EntityType='de.metas.ui.web', MsgText='Issue TUs (partial)',Updated=TO_TIMESTAMP('2017-05-11 20:08:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544319
;

-- 2017-05-11T20:08:15.231
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=544319
;

-- 2017-05-11T20:08:35.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-05-11 20:08:35','YYYY-MM-DD HH24:MI:SS'),MsgText='Issue TUs (partial)' WHERE AD_Message_ID=544319 AND AD_Language='de_CH'
;

-- 2017-05-11T20:08:40.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-05-11 20:08:40','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Issue TUs (partial)' WHERE AD_Message_ID=544319 AND AD_Language='en_US'
;

