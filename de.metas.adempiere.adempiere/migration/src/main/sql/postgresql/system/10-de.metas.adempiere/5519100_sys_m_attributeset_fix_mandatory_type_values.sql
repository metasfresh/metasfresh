-- 2019-04-11T12:23:17.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Not Mandatory',Updated=TO_TIMESTAMP('2019-04-11 12:23:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=681
;

-- 2019-04-11T12:23:26.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List_Trl WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=681
;

-- 2019-04-11T12:23:26.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List_Trl WHERE AD_Language='it_CH' AND AD_Ref_List_ID=681
;

-- 2019-04-11T12:23:26.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_List_Trl WHERE AD_Language='en_GB' AND AD_Ref_List_ID=681
;

-- 2019-04-11T12:23:47.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Optional',Updated=TO_TIMESTAMP('2019-04-11 12:23:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=681
;

-- 2019-04-11T12:23:55.637
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Optional',Updated=TO_TIMESTAMP('2019-04-11 12:23:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=681
;

-- 2019-04-11T12:24:01.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Not Mandatory',Updated=TO_TIMESTAMP('2019-04-11 12:24:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=681
;

-- 2019-04-11T12:24:07.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Optional',Updated=TO_TIMESTAMP('2019-04-11 12:24:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=681
;

