-- 2019-09-05T11:43:07.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544935,0,TO_TIMESTAMP('2019-09-05 13:43:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Gewählte Zeilen haben versch. Produkte','I',TO_TIMESTAMP('2019-09-05 13:43:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web.pricing.process.M_DiscountSchemaBreak_CopyToOtherSchema_Product')
;

-- 2019-09-05T11:43:07.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544935 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-09-05T11:43:11.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-05 13:43:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544935
;

-- 2019-09-05T11:44:25.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Selection contains multiple propducts or none',Updated=TO_TIMESTAMP('2019-09-05 13:44:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544935
;

-- 2019-09-05T11:44:36.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Gewählte Zeilen haben versch. oder kein Produkt',Updated=TO_TIMESTAMP('2019-09-05 13:44:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544935
;

-- 2019-09-05T11:44:42.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Gewählte Zeilen haben versch. oder kein Produkt',Updated=TO_TIMESTAMP('2019-09-05 13:44:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=544935
;

-- 2019-09-05T11:44:44.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Gewählte Zeilen haben versch. oder kein Produkt',Updated=TO_TIMESTAMP('2019-09-05 13:44:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544935
;

-- 2019-09-05T11:46:14.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='de.metas.ui.web.pricing.process.M_DiscountSchemaBreak_CopyToOtherSchema_Product.NoSingleProduct',Updated=TO_TIMESTAMP('2019-09-05 13:46:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544935
;

