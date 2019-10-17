-- 2018-12-12T16:02:56.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET ErrorMsg='Eine Preisliste mit diesem internen Namen existiert bereits!',Updated=TO_TIMESTAMP('2018-12-12 16:02:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540482
;

-- 2018-12-12T16:03:09.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 16:03:09','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',ErrorMsg='Eine Preisliste mit diesem internen Namen existiert bereits!' WHERE AD_Language='de_CH' AND AD_Index_Table_ID=540482
;

-- 2018-12-12T16:03:18.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 16:03:18','YYYY-MM-DD HH24:MI:SS'),ErrorMsg='A pricelist with this internal name already exists!' WHERE AD_Language='nl_NL' AND AD_Index_Table_ID=540482
;

-- 2018-12-12T16:03:22.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-12-12 16:03:22','YYYY-MM-DD HH24:MI:SS'),ErrorMsg='A pricelist with this internal name already exists!' WHERE AD_Language='en_US' AND AD_Index_Table_ID=540482
;

