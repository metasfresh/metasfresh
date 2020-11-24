-- 2020-06-22T12:44:05.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=2047, ColumnName='IsComplete', Description='It is complete', Help='Indication that this is complete', Name='Fertigstellen',Updated=TO_TIMESTAMP('2020-06-22 15:44:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541835
;

-- 2020-06-22T12:44:08.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=577771
;

-- 2020-06-22T12:44:08.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=577771
;

-- 2020-06-22T12:44:31.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-06-22 15:44:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2047 AND AD_Language='de_DE'
;

-- 2020-06-22T12:44:31.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2047,'de_DE') 
;

-- 2020-06-22T12:44:31.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(2047,'de_DE') 
;

-- 2020-06-22T12:44:48.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-06-22 15:44:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2047 AND AD_Language='de_CH'
;

-- 2020-06-22T12:44:48.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2047,'de_CH') 
;

-- 2020-06-22T12:44:52.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Complete', PrintName='Complete',Updated=TO_TIMESTAMP('2020-06-22 15:44:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2047 AND AD_Language='en_GB'
;

-- 2020-06-22T12:44:52.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2047,'en_GB') 
;

