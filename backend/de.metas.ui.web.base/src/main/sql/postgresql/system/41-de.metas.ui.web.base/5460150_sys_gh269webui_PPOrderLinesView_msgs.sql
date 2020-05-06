-- 2017-04-13T13:56:08.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544300,0,TO_TIMESTAMP('2017-04-13 13:56:08','YYYY-MM-DD HH24:MI:SS'),0,'U','Y','PPOrderLinesView.openViewsToIssue','I',TO_TIMESTAMP('2017-04-13 13:56:08','YYYY-MM-DD HH24:MI:SS'),0,'PPOrderLinesView.openViewsToIssue')
;

-- 2017-04-13T13:56:08.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544300 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-04-13T15:26:37.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544301,0,TO_TIMESTAMP('2017-04-13 15:26:37','YYYY-MM-DD HH24:MI:SS'),0,'U','Y','PPOrderLinesView.processPlan','I',TO_TIMESTAMP('2017-04-13 15:26:37','YYYY-MM-DD HH24:MI:SS'),0,'PPOrderLinesView.processPlan')
;

-- 2017-04-13T15:26:37.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544301 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-04-13T16:02:26.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET EntityType='de.metas.ui.web', MsgText='Process plan',Updated=TO_TIMESTAMP('2017-04-13 16:02:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544301
;

-- 2017-04-13T16:02:26.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=544301
;

-- 2017-04-13T16:02:50.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET EntityType='de.metas.ui.web', MsgText='Issue HUs',Updated=TO_TIMESTAMP('2017-04-13 16:02:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544300
;

-- 2017-04-13T16:02:50.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=544300
;

-- 2017-04-13T16:12:34.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-04-13 16:12:34','YYYY-MM-DD HH24:MI:SS'),MsgText='Issue HUs' WHERE AD_Message_ID=544300 AND AD_Language='de_CH'
;

-- 2017-04-13T16:12:39.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-04-13 16:12:39','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Issue HUs' WHERE AD_Message_ID=544300 AND AD_Language='en_US'
;

-- 2017-04-13T16:12:52.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-04-13 16:12:52','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Process plan' WHERE AD_Message_ID=544301 AND AD_Language='en_US'
;

-- 2017-04-13T16:12:56.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-04-13 16:12:56','YYYY-MM-DD HH24:MI:SS'),MsgText='Process plan' WHERE AD_Message_ID=544301 AND AD_Language='de_CH'
;

