-- 2020-05-20T14:47:31.866Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544983,0,TO_TIMESTAMP('2020-05-20 17:47:31','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Could not clone Sales Order because Adress is not active.','I',TO_TIMESTAMP('2020-05-20 17:47:31','YYYY-MM-DD HH24:MI:SS'),100,'webui.salesorder.clone.inactivelocation')
;

-- 2020-05-20T14:47:31.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544983 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-05-20T14:48:02.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Kundenauftrag konnte nicht geklont werden, da Adresse nicht aktiv ist',Updated=TO_TIMESTAMP('2020-05-20 17:48:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544983
;

-- 2020-05-20T14:48:08.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-05-20 17:48:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544983
;

-- 2020-05-20T14:48:15.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Kundenauftrag konnte nicht geklont werden, da Adresse nicht aktiv ist',Updated=TO_TIMESTAMP('2020-05-20 17:48:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544983
;

-- 2020-05-20T14:53:13.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Der Auftrag mit inaktiver Adresse kann nicht geclont werden.',Updated=TO_TIMESTAMP('2020-05-20 17:53:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544983
;

-- 2020-05-20T14:53:24.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgType='E',Updated=TO_TIMESTAMP('2020-05-20 17:53:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544983
;

-- 2020-05-20T14:53:43.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Der Auftrag mit inaktiver Adresse kann nicht geclont werden.',Updated=TO_TIMESTAMP('2020-05-20 17:53:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544983
;

