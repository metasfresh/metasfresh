-- 2019-02-18T17:01:09.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544833,0,TO_TIMESTAMP('2019-02-18 17:01:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Code scannnen','I',TO_TIMESTAMP('2019-02-18 17:01:08','YYYY-MM-DD HH24:MI:SS'),100,'widget.scanFromCamera.caption')
;

-- 2019-02-18T17:01:09.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544833 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-02-18T17:01:16.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-18 17:01:16','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544833 AND AD_Language='de_CH'
;

-- 2019-02-18T17:01:24.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-18 17:01:24','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Scan code' WHERE AD_Message_ID=544833 AND AD_Language='en_US'
;

-- 2019-02-18T17:03:08.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='webui.widget.scanFromCamera.caption',Updated=TO_TIMESTAMP('2019-02-18 17:03:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544833
;

