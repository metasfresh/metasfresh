-- 2019-12-16T21:32:57.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--UPDATE AD_Process_Para SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2019-12-16 22:32:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540523
--;

-- 2019-12-17T07:54:04.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544950,0,TO_TIMESTAMP('2019-12-17 08:54:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Fehlende Umrechnungsregel für das Product {0} von {1} nach {2}','E',TO_TIMESTAMP('2019-12-17 08:54:04','YYYY-MM-DD HH24:MI:SS'),100,'NoUOMConversion_Params')
;

-- 2019-12-17T07:54:04.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544950 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-12-17T08:08:31.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Fehlende Umrechnungsregel für das Product {0} von Maßeinheit {1} nach {2}',Updated=TO_TIMESTAMP('2019-12-17 09:08:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544950
;

-- 2019-12-17T08:08:49.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Fehlende Umrechnungsregel für das Product {0} von Masseinheit {1} nach {2}',Updated=TO_TIMESTAMP('2019-12-17 09:08:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544950
;

-- 2019-12-17T08:09:20.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Missing UOM conversion rule for product {0} from {1} to {2}.',Updated=TO_TIMESTAMP('2019-12-17 09:09:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544950
;

-- 2019-12-17T08:09:26.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Fehlende Umrechnungsregel für das Produkt {0} von {1} nach {2}',Updated=TO_TIMESTAMP('2019-12-17 09:09:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=544950
;

-- 2019-12-17T08:09:41.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Fehlende Umrechnungsregel für das Produkt {0} von Masseinheit {1} nach {2}',Updated=TO_TIMESTAMP('2019-12-17 09:09:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544950
;

-- 2019-12-17T08:09:58.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Fehlende Umrechnungsregel für das Product {0} von Maßeinheit {1} nach {2}.',Updated=TO_TIMESTAMP('2019-12-17 09:09:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544950
;

-- 2019-12-17T08:10:23.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Fehlende Umrechnungsregel für das Produkt {0} von Masseinheit {1} nach {2}.',Updated=TO_TIMESTAMP('2019-12-17 09:10:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544950
;

