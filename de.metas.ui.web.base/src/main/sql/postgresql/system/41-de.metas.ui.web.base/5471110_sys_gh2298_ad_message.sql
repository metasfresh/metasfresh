-- 2017-09-07T11:06:37.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544489,0,TO_TIMESTAMP('2017-09-07 11:06:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Nur wenn es passende Quell-HUs gibt','I',TO_TIMESTAMP('2017-09-07 11:06:37','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Picking_MissingSourceHU')
;

-- 2017-09-07T11:06:37.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544489 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-09-07T11:06:55.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-09-07 11:06:55','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Only if there are matching source HUs' WHERE AD_Message_ID=544489 AND AD_Language='en_US'
;

