-- 2017-10-28T20:18:08.617
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,AD_Client_ID,IsActive,Created,CreatedBy,AD_Message_ID,MsgText,AD_Org_ID,Value,EntityType,Updated,UpdatedBy) VALUES ('E',0,'Y',TO_TIMESTAMP('2017-10-28 20:18:08','YYYY-MM-DD HH24:MI:SS'),100,544557,'Cannot invoice incomplete compensation groups. Check orders: {1}',0,'InvoiceCandEnqueuer_IncompleteGroupsFound','de.metas.invoicecandidate',TO_TIMESTAMP('2017-10-28 20:18:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-28T20:18:08.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544557 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-10-28T20:23:39.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Cannot invoice incomplete compensation groups. Check orders: {0}',Updated=TO_TIMESTAMP('2017-10-28 20:23:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544557
;

-- 2017-10-28T20:23:44.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-28 20:23:44','YYYY-MM-DD HH24:MI:SS'),MsgText='Cannot invoice incomplete compensation groups. Check orders: {0}' WHERE AD_Message_ID=544557 AND AD_Language='de_CH'
;

-- 2017-10-28T20:23:48.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-28 20:23:48','YYYY-MM-DD HH24:MI:SS'),MsgText='Cannot invoice incomplete compensation groups. Check orders: {0}' WHERE AD_Message_ID=544557 AND AD_Language='nl_NL'
;

-- 2017-10-28T20:23:51.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-10-28 20:23:51','YYYY-MM-DD HH24:MI:SS'),MsgText='Cannot invoice incomplete compensation groups. Check orders: {0}' WHERE AD_Message_ID=544557 AND AD_Language='en_US'
;

