-- 2019-06-07T23:11:23.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-06-07 23:11:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573477 AND AD_Language='de_CH'
;

-- 2019-06-07T23:11:24.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573477,'de_CH') 
;

-- 2019-06-07T23:11:31.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Vendor', PrintName='Vendor',Updated=TO_TIMESTAMP('2019-06-07 23:11:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573477 AND AD_Language='en_US'
;

-- 2019-06-07T23:11:31.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573477,'en_US') 
;

-- 2019-06-07T23:11:37.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-06-07 23:11:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573477 AND AD_Language='de_DE'
;

-- 2019-06-07T23:11:37.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573477,'de_DE') 
;

-- 2019-06-07T23:11:37.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(573477,'de_DE') 
;

-- 2019-06-07T23:11:43.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Element_ID=573477, Description=NULL, Help=NULL,Updated=TO_TIMESTAMP('2019-06-07 23:11:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=224
;

-- 2019-06-07T23:11:43.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(573477) 
;

-- 2019-06-07T23:11:43.418
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(224)
;

