-- 2022-03-06T19:44:00.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET Name='Abrufauftrag',Updated=TO_TIMESTAMP('2022-03-06 21:44:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541028
;

-- 2022-03-06T19:44:00.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl trl SET Description=NULL, DocumentNote=NULL, Name='Abrufauftrag', PrintName='Call-off Order'  WHERE C_DocType_ID=541028 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2022-03-06T19:44:02.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET PrintName='Abrufauftrag',Updated=TO_TIMESTAMP('2022-03-06 21:44:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541028
;

-- 2022-03-06T19:44:02.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl trl SET Description=NULL, DocumentNote=NULL, Name='Abrufauftrag', PrintName='Abrufauftrag'  WHERE C_DocType_ID=541028 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2022-03-06T19:44:23.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET Name='Call-off order',Updated=TO_TIMESTAMP('2022-03-06 21:44:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541028
;

-- 2022-03-06T19:45:34.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET PrintName='Call-off order',Updated=TO_TIMESTAMP('2022-03-06 21:45:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541028
;

