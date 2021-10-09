-- 2020-10-08T19:43:32.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Element_ID=577781, CommitWarning=NULL, Description=NULL, Help=NULL, Name='PostgREST Configs',Updated=TO_TIMESTAMP('2020-10-08 21:43:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=542805
;

-- 2020-10-08T19:43:32.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(577781) 
;

-- 2020-10-08T19:43:32.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(542805)
;
