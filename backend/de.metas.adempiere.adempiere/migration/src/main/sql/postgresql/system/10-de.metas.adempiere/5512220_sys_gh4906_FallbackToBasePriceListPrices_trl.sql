-- 2019-02-07T17:59:11.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-07 17:59:11','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Rückgriff auf Preise der Basispreisliste',PrintName='Rückgriff auf Preise der Basispreisliste' WHERE AD_Element_ID=576070 AND AD_Language='de_CH'
;

-- 2019-02-07T17:59:11.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576070,'de_CH') 
;

-- 2019-02-07T17:59:18.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-07 17:59:18','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Rückgriff auf Preise der Basispreisliste',PrintName='Rückgriff auf Preise der Basispreisliste' WHERE AD_Element_ID=576070 AND AD_Language='de_DE'
;

-- 2019-02-07T17:59:18.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576070,'de_DE') 
;

-- 2019-02-07T17:59:18.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576070,'de_DE') 
;

-- 2019-02-07T17:59:18.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FallbackToBasePriceListPrices', Name='Rückgriff auf Preise der Basispreisliste', Description=NULL, Help=NULL WHERE AD_Element_ID=576070
;

-- 2019-02-07T17:59:18.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FallbackToBasePriceListPrices', Name='Rückgriff auf Preise der Basispreisliste', Description=NULL, Help=NULL, AD_Element_ID=576070 WHERE UPPER(ColumnName)='FALLBACKTOBASEPRICELISTPRICES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-02-07T17:59:18.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FallbackToBasePriceListPrices', Name='Rückgriff auf Preise der Basispreisliste', Description=NULL, Help=NULL WHERE AD_Element_ID=576070 AND IsCentrallyMaintained='Y'
;

-- 2019-02-07T17:59:18.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rückgriff auf Preise der Basispreisliste', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576070) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576070)
;

-- 2019-02-07T17:59:18.974
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rückgriff auf Preise der Basispreisliste', Name='Rückgriff auf Preise der Basispreisliste' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576070)
;

-- 2019-02-07T17:59:18.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rückgriff auf Preise der Basispreisliste', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576070
;

-- 2019-02-07T17:59:18.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rückgriff auf Preise der Basispreisliste', Description=NULL, Help=NULL WHERE AD_Element_ID = 576070
;

-- 2019-02-07T17:59:18.997
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Rückgriff auf Preise der Basispreisliste', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576070
;

-- 2019-02-07T17:59:22.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-07 17:59:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=576070 AND AD_Language='en_US'
;

-- 2019-02-07T17:59:22.843
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576070,'en_US') 
;

