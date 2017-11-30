-- 2017-11-23T18:37:15.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,AD_Client_ID,IsActive,Created,CreatedBy,Value,AD_Message_ID,MsgText,AD_Org_ID,EntityType,Updated,UpdatedBy) VALUES ('I',0,'Y',TO_TIMESTAMP('2017-11-23 18:37:14','YYYY-MM-DD HH24:MI:SS'),100,'Event_PurchaseOrderCreated',544581,'Bestellung {0} für Partner {1} {2} wurde erstellt.',0,'de.metas.order',TO_TIMESTAMP('2017-11-23 18:37:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-23T18:37:15.014
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544581 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-11-23T18:37:45.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,AD_Client_ID,IsActive,Created,CreatedBy,Value,AD_Message_ID,MsgText,AD_Org_ID,EntityType,Updated,UpdatedBy) VALUES ('I',0,'Y',TO_TIMESTAMP('2017-11-23 18:37:45','YYYY-MM-DD HH24:MI:SS'),100,'Event_SalesOrderCreated',544582,'Auftrag {0} für Partner {1} {2} wurde erstellt.',0,'de.metas.order',TO_TIMESTAMP('2017-11-23 18:37:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-23T18:37:45.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544582 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-11-23T18:40:39.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-23 18:40:39','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Purchase order {0} for vendor {1} {2} has been created.' WHERE AD_Message_ID=544581 AND AD_Language='en_US'
;

-- 2017-11-23T18:41:05.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-23 18:41:05','YYYY-MM-DD HH24:MI:SS'),MsgText='Sales order {0} for customer {1} {2} has been created.' WHERE AD_Message_ID=544582 AND AD_Language='en_US'
;

-- 2017-11-23T18:41:14.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-11-23 18:41:14','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544582 AND AD_Language='en_US'
;

