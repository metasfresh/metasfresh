-- 2019-05-21T19:39:13.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_NotificationGroup (AD_Client_ID,AD_NotificationGroup_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,Name,Updated,UpdatedBy) VALUES (0,540016,0,TO_TIMESTAMP('2019-05-21 19:39:13','YYYY-MM-DD HH24:MI:SS'),100,'D','de.metas.customs.UserNotifications','Y','Customs Invoice',TO_TIMESTAMP('2019-05-21 19:39:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-05-21T19:03:45.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544921,0,TO_TIMESTAMP('2019-05-21 19:03:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Zollrechnung {0} wurde erstellt.','I',TO_TIMESTAMP('2019-05-21 19:03:45','YYYY-MM-DD HH24:MI:SS'),100,'Event_CustomsInvoiceGenerated')
;

-- 2019-05-21T19:03:45.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544921 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
