-- 2021-07-26T09:36:52.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Dateiname',Updated=TO_TIMESTAMP('2021-07-26 12:36:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2295 AND AD_Language='de_DE'
;

-- 2021-07-26T09:36:52.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2295,'de_DE') 
;

-- 2021-07-26T09:36:52.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(2295,'de_DE') 
;

-- 2021-07-26T09:36:52.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Dateiname', Name='File Name' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2295)
;

-- 2021-07-26T09:36:57.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Dateiname',Updated=TO_TIMESTAMP('2021-07-26 12:36:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2295 AND AD_Language='nl_NL'
;

-- 2021-07-26T09:36:57.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2295,'nl_NL') 
;

-- 2021-07-26T09:37:04.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Dateiname',Updated=TO_TIMESTAMP('2021-07-26 12:37:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2295 AND AD_Language='de_CH'
;

-- 2021-07-26T09:37:04.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2295,'de_CH') 
;

