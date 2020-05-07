-- 2018-02-10T08:28:51.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544640,0,TO_TIMESTAMP('2018-02-10 08:28:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web','Y','Alle Zeiträume anzeigen','I',TO_TIMESTAMP('2018-02-10 08:28:51','YYYY-MM-DD HH24:MI:SS'),100,'webui.window.daterange.filter.hint')
;

-- 2018-02-10T08:28:51.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544640 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-02-10T08:29:09.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-02-10 08:29:09','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Show all Dates' WHERE AD_Message_ID=544640 AND AD_Language='en_US'
;

