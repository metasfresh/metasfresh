-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544927,0,TO_TIMESTAMP('2019-06-07 12:18:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Are you sure you want to delete the attachment?','I',TO_TIMESTAMP('2019-06-07 12:18:55','YYYY-MM-DD HH24:MI:SS'),100,'webui.window.attachment.deleteQuestion')
;

-- 2019-06-07T12:18:56.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544927 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-06-07T12:19:23.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Sind Sie sicher, dass Sie den Datenanhang löschen möchten?',Updated=TO_TIMESTAMP('2019-06-07 12:19:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544927
;

-- 2019-06-07T12:22:13.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Sind Sie sicher, dass Sie den Datenanhang löschen möchten?',Updated=TO_TIMESTAMP('2019-06-07 12:22:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544927
;