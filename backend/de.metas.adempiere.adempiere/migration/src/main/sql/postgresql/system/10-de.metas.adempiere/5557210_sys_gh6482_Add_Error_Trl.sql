-- 2020-04-14T11:54:39.366Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,MsgText,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Message_ID,Value,AD_Org_ID,EntityType) VALUES ('I','Cloning not allowed for current window.',0,'Y',TO_TIMESTAMP('2020-04-14 14:54:39','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2020-04-14 14:54:39','YYYY-MM-DD HH24:MI:SS'),100,544972,'de.metas.ui.web.window.model.DocumentCollection.CloningNotAllowedForCurrentWindow',0,'D')
;

-- 2020-04-14T11:54:39.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544972 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-04-14T11:54:44.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Dieses Fenster kann nicht geclont werden.', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-04-14 14:54:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544972
;

-- 2020-04-14T11:54:48.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-04-14 14:54:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544972
;

-- 2020-04-14T12:09:23.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Dieses Fenster kann nicht geclont werden.',Updated=TO_TIMESTAMP('2020-04-14 15:09:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544972
;

