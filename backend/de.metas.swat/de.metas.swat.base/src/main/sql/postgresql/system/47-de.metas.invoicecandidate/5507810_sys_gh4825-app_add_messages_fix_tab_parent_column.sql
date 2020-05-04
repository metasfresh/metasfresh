-- 2018-12-10T14:03:32.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544814,0,TO_TIMESTAMP('2018-12-10 14:03:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','Y','Überspringe Rechnungskandiat {0}, da es als zu Verrrechnen (IsToClear) marktiert ist.','I',TO_TIMESTAMP('2018-12-10 14:03:31','YYYY-MM-DD HH24:MI:SS'),100,'InvoiceCandBL_Invoicing_Skipped_IsToClear')
;

-- 2018-12-10T14:03:32.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544814 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-12-10T14:03:51.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Überspringe Rechnungskandiat {0}, da als zu Verrrechnen (IsToClear) marktiert.',Updated=TO_TIMESTAMP('2018-12-10 14:03:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544814
;

-- 2018-12-10T14:04:01.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-10 14:04:01','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Überspringe Rechnungskandiat {0}, da als zu Verrrechnen (IsToClear) marktiert.' WHERE AD_Message_ID=544814 AND AD_Language='de_CH'
;

-- 2018-12-10T14:04:31.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-10 14:04:31','YYYY-MM-DD HH24:MI:SS'),MsgText='Skipping invoice candidate {0} because is is flagged as "is to clear".' WHERE AD_Message_ID=544814 AND AD_Language='en_US'
;

-- 2018-12-10T14:05:35.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544815,0,TO_TIMESTAMP('2018-12-10 14:05:35','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Überspringe Rechnungskandiat {0}, da als "im Disput" markiert.','I',TO_TIMESTAMP('2018-12-10 14:05:35','YYYY-MM-DD HH24:MI:SS'),100,'InvoiceCandBL_Invoicing_Skipped_IsInDispute')
;

-- 2018-12-10T14:05:35.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544815 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-12-10T14:05:41.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Überspringe Rechnungskandiat {0}, da als "zu Verrechnen" (IsToClear) marktiert.',Updated=TO_TIMESTAMP('2018-12-10 14:05:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544814
;

-- 2018-12-10T14:05:48.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-10 14:05:48','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544815 AND AD_Language='de_CH'
;

-- 2018-12-10T14:06:13.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-10 14:06:13','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Skipping invoice candidate {0} because it is flagged as in dispute.' WHERE AD_Message_ID=544815 AND AD_Language='en_US'
;

--
-- fix the previously wrong parent column
--
-- 2018-12-10T14:20:05.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=548862,Updated=TO_TIMESTAMP('2018-12-10 14:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540455
;

