
-- 2021-03-17T13:55:06.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Apotheken-Preisliste', PrintName='Apotheken-Preisliste',Updated=TO_TIMESTAMP('2021-03-17 14:55:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578840 AND AD_Language='de_CH'
;

-- 2021-03-17T13:55:06.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578840,'de_CH') 
;

-- 2021-03-17T13:55:11.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Apotheken-Preisliste', PrintName='Apotheken-Preisliste',Updated=TO_TIMESTAMP('2021-03-17 14:55:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578840 AND AD_Language='de_DE'
;

-- 2021-03-17T13:55:11.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578840,'de_DE') 
;

-- 2021-03-17T13:55:11.195Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578840,'de_DE') 
;

-- 2021-03-17T13:55:11.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Pharmacy_PriceList_ID', Name='Apotheken-Preisliste', Description=NULL, Help=NULL WHERE AD_Element_ID=578840
;

-- 2021-03-17T13:55:11.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Pharmacy_PriceList_ID', Name='Apotheken-Preisliste', Description=NULL, Help=NULL, AD_Element_ID=578840 WHERE UPPER(ColumnName)='PHARMACY_PRICELIST_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-03-17T13:55:11.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Pharmacy_PriceList_ID', Name='Apotheken-Preisliste', Description=NULL, Help=NULL WHERE AD_Element_ID=578840 AND IsCentrallyMaintained='Y'
;

-- 2021-03-17T13:55:11.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Apotheken-Preisliste', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578840) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578840)
;

-- 2021-03-17T13:55:11.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Apotheken-Preisliste', Name='Apotheken-Preisliste' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578840)
;

-- 2021-03-17T13:55:11.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Apotheken-Preisliste', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578840
;

-- 2021-03-17T13:55:11.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Apotheken-Preisliste', Description=NULL, Help=NULL WHERE AD_Element_ID = 578840
;

-- 2021-03-17T13:55:11.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Apotheken-Preisliste', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578840
;

-- 2021-03-17T13:55:14.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-17 14:55:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578840 AND AD_Language='en_US'
;

-- 2021-03-17T13:55:14.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578840,'en_US') 
;

