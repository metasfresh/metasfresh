-- 2018-05-10T17:46:35.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544717,0,TO_TIMESTAMP('2018-05-10 17:46:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.base','Y','Der Organisation {0} wurde noch keine default Newsletter-Gruppe zugeordnet"','E',TO_TIMESTAMP('2018-05-10 17:46:35','YYYY-MM-DD HH24:MI:SS'),100,'MKTG_Campaign_NewsletterGroup_Missing_For_Org')
;

-- 2018-05-10T17:46:35.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544717 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-05-10T17:46:39.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Der Organisation {0} wurde noch keine default Newsletter-Gruppe zugeordnet',Updated=TO_TIMESTAMP('2018-05-10 17:46:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544717
;

-- 2018-05-10T17:46:56.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 17:46:56','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='The organization {0} does not have a default newsletter group' WHERE AD_Message_ID=544717 AND AD_Language='en_US'
;

-- 2018-05-10T17:47:03.376
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 17:47:03','YYYY-MM-DD HH24:MI:SS'),MsgText='Der Organisation {0} wurde noch keine default Newsletter-Gruppe zugeordnet' WHERE AD_Message_ID=544717 AND AD_Language='nl_NL'
;

-- 2018-05-10T17:47:06.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-10 17:47:06','YYYY-MM-DD HH24:MI:SS'),MsgText='Der Organisation {0} wurde noch keine default Newsletter-Gruppe zugeordnet' WHERE AD_Message_ID=544717 AND AD_Language='de_CH'
;

