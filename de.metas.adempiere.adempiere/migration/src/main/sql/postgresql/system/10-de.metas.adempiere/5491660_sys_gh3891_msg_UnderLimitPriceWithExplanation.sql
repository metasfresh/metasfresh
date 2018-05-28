-- 2018-04-23T17:44:30.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,AD_Client_ID,IsActive,CreatedBy,Value,EntityType,AD_Message_ID,MsgText,AD_Org_ID,UpdatedBy,Created,Updated) VALUES ('E',0,'Y',100,'UnderLimitPriceWithExplanation','D',544706,'Price {0} is under limit price {1} which was calculated as {2}.',0,100,TO_TIMESTAMP('2018-04-23 17:44:30','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-04-23 17:44:30','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-04-23T17:44:30.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544706 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-04-23T17:59:13.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Der Preis {0} liegt unter dem Limit Preis {1}, der wie folgt berechnet wurde: {2}.',Updated=TO_TIMESTAMP('2018-04-23 17:59:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544706
;

-- 2018-04-23T17:59:20.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-23 17:59:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Der Preis {0} liegt unter dem Limit Preis {1}, der wie folgt berechnet wurde: {2}.' WHERE AD_Message_ID=544706 AND AD_Language='de_CH'
;

-- 2018-04-23T17:59:27.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-23 17:59:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544706 AND AD_Language='en_US'
;

-- 2018-04-23T18:28:17.109
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-23 18:28:17','YYYY-MM-DD HH24:MI:SS'),MsgText='Price {1} is under limit price {2} which was calculated as {3}.' WHERE AD_Message_ID=544706 AND AD_Language='en_US'
;

-- 2018-04-23T18:28:30.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-23 18:28:30','YYYY-MM-DD HH24:MI:SS'),MsgText='Price {1} is under limit price {2} which was calculated as {3}.' WHERE AD_Message_ID=544706 AND AD_Language='nl_NL'
;

-- 2018-04-23T18:28:44.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-23 18:28:44','YYYY-MM-DD HH24:MI:SS'),MsgText='Der Preis {1} liegt unter dem Limit Preis {2}, der wie folgt berechnet wurde: {3}.' WHERE AD_Message_ID=544706 AND AD_Language='de_CH'
;

-- 2018-04-23T18:28:49.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Der Preis {1} liegt unter dem Limit Preis {2}, der wie folgt berechnet wurde: {3}.',Updated=TO_TIMESTAMP('2018-04-23 18:28:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544706
;

-- 2018-04-23T18:29:04.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-23 18:29:04','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544706 AND AD_Language='nl_NL'
;

-- 2018-04-23T18:29:19.474
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-23 18:29:19','YYYY-MM-DD HH24:MI:SS'),IsTranslated='N' WHERE AD_Message_ID=544706 AND AD_Language='nl_NL'
;

-- 2018-04-23T18:31:36.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-23 18:31:36','YYYY-MM-DD HH24:MI:SS'),MsgText='Price {0} is under limit price {1} which was calculated as {2}.' WHERE AD_Message_ID=544706 AND AD_Language='nl_NL'
;

-- 2018-04-23T18:31:40.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-23 18:31:40','YYYY-MM-DD HH24:MI:SS'),MsgText='Price {0} is under limit price {1} which was calculated as {2}.' WHERE AD_Message_ID=544706 AND AD_Language='en_US'
;

-- 2018-04-23T18:31:50.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-23 18:31:50','YYYY-MM-DD HH24:MI:SS'),MsgText='Der Preis {0} liegt unter dem Limit Preis {1}, der wie folgt berechnet wurde: {2}.' WHERE AD_Message_ID=544706 AND AD_Language='de_CH'
;

-- 2018-04-23T18:31:54.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Der Preis {0} liegt unter dem Limit Preis {1}, der wie folgt berechnet wurde: {2}.',Updated=TO_TIMESTAMP('2018-04-23 18:31:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544706
;

