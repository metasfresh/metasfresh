-- 2021-12-17T08:36:46.680Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545087,0,TO_TIMESTAMP('2021-12-17 10:36:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Zum angegebenen Preissystem "{0}" konnte keine Verkaufspreisliste gefunden werden für die folgenden Länder: {1}.','E',TO_TIMESTAMP('2021-12-17 10:36:46','YYYY-MM-DD HH24:MI:SS'),100,'NoSOPriceListForCountries')
;

-- 2021-12-17T08:36:46.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545087 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-12-17T08:37:20.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The pricing system "{0}" doesn''t have any sales price list for the following countries: {1}.',Updated=TO_TIMESTAMP('2021-12-17 10:37:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545087
;

-- 2021-12-17T08:37:59.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545088,0,TO_TIMESTAMP('2021-12-17 10:37:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Zum angegebenen Preissystem "{0}" konnte keine Einkaufspreisliste gefunden werden für die folgenden Länder: {1}.
','E',TO_TIMESTAMP('2021-12-17 10:37:59','YYYY-MM-DD HH24:MI:SS'),100,'NoPOPriceListForCountries')
;

-- 2021-12-17T08:37:59.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545088 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-12-17T08:38:20.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='The pricing system "{0}" doesn''t have any purchase price list for the following countries: {1}.',Updated=TO_TIMESTAMP('2021-12-17 10:38:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545088
;

