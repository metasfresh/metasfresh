-- 2019-02-06T18:14:40.117
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544828,0,TO_TIMESTAMP('2019-02-06 18:14:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Preisliste und Basispreisliste müssen dieselbe Währung haben.','E',TO_TIMESTAMP('2019-02-06 18:14:39','YYYY-MM-DD HH24:MI:SS'),100,'PriceListAndBasePriceListCurrencyMismatchError')
;

-- 2019-02-06T18:14:40.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544828 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-02-06T18:14:45.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-06 18:14:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544828 AND AD_Language='de_CH'
;

-- 2019-02-06T18:14:50.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-06 18:14:50','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Price List''s and Base Price List''s  currency shall be the same.' WHERE AD_Message_ID=544828 AND AD_Language='en_US'
;

-- 2019-02-07T09:15:17.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544829,0,TO_TIMESTAMP('2019-02-07 09:15:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Preisliste und Basispreisliste müssen dieselbe Land haben.','E',TO_TIMESTAMP('2019-02-07 09:15:17','YYYY-MM-DD HH24:MI:SS'),100,'PriceListAndBasePriceListCountryMismatchError')
;

-- 2019-02-07T09:15:17.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544829 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-02-07T09:15:32.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-07 09:15:32','YYYY-MM-DD HH24:MI:SS'),MsgText='Price List''s and Base Price List''s currency shall be the same.' WHERE AD_Message_ID=544828 AND AD_Language='en_US'
;

-- 2019-02-07T09:15:44.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-07 09:15:44','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Price List''s and Base Price List''s  currency shall be the same.' WHERE AD_Message_ID=544829 AND AD_Language='en_US'
;

-- 2019-02-07T09:15:49.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-07 09:15:49','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544829 AND AD_Language='de_CH'
;

-- 2019-02-07T09:16:39.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Preisliste und Basispreisliste müssen dasselbe Land haben.',Updated=TO_TIMESTAMP('2019-02-07 09:16:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544829
;

-- 2019-02-07T09:17:04.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-07 09:17:04','YYYY-MM-DD HH24:MI:SS'),MsgText='Preisliste und Basispreisliste müssen dasselbe Land haben.' WHERE AD_Message_ID=544829 AND AD_Language='de_CH'
;

-- 2019-02-07T09:17:20.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-07 09:17:20','YYYY-MM-DD HH24:MI:SS'),MsgText='Price List''s and Base Price List''s country shall be the same.' WHERE AD_Message_ID=544829 AND AD_Language='en_US'
;

