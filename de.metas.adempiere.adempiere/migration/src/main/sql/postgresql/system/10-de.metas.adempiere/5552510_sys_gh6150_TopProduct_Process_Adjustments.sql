-- 2020-02-18T12:29:55.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='SalesPercentOfTotal',Updated=TO_TIMESTAMP('2020-02-18 14:29:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577529
;

-- 2020-02-18T12:29:56.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SalesPercentOfTotal', Name='Anteil von Verkauf (MW)', Description=NULL, Help=NULL WHERE AD_Element_ID=577529
;

-- 2020-02-18T12:29:56.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SalesPercentOfTotal', Name='Anteil von Verkauf (MW)', Description=NULL, Help=NULL, AD_Element_ID=577529 WHERE UPPER(ColumnName)='SALESPERCENTOFTOTAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-18T12:29:56.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SalesPercentOfTotal', Name='Anteil von Verkauf (MW)', Description=NULL, Help=NULL WHERE AD_Element_ID=577529 AND IsCentrallyMaintained='Y'
;

-- 2020-02-18T12:30:12.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Sales Percent of Total', PrintName='Sales Percent of Total',Updated=TO_TIMESTAMP('2020-02-18 14:30:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577529 AND AD_Language='en_US'
;

-- 2020-02-18T12:30:12.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577529,'en_US') 
;

-- 2020-02-18T12:30:22.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Sales Percent of Total', PrintName='Sales Percent of Total',Updated=TO_TIMESTAMP('2020-02-18 14:30:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577529 AND AD_Language='nl_NL'
;

-- 2020-02-18T12:30:22.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577529,'nl_NL') 
;

-- 2020-02-18T12:32:08.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Anteil von Verkauf', PrintName='Anteil von Verkauf',Updated=TO_TIMESTAMP('2020-02-18 14:32:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577529 AND AD_Language='de_DE'
;

-- 2020-02-18T12:32:08.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577529,'de_DE') 
;

-- 2020-02-18T12:32:08.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577529,'de_DE') 
;

-- 2020-02-18T12:32:08.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SalesPercentOfTotal', Name='Anteil von Verkauf', Description=NULL, Help=NULL WHERE AD_Element_ID=577529
;

-- 2020-02-18T12:32:08.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SalesPercentOfTotal', Name='Anteil von Verkauf', Description=NULL, Help=NULL, AD_Element_ID=577529 WHERE UPPER(ColumnName)='SALESPERCENTOFTOTAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-18T12:32:08.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SalesPercentOfTotal', Name='Anteil von Verkauf', Description=NULL, Help=NULL WHERE AD_Element_ID=577529 AND IsCentrallyMaintained='Y'
;

-- 2020-02-18T12:32:08.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anteil von Verkauf', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577529) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577529)
;

-- 2020-02-18T12:32:09.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anteil von Verkauf', Name='Anteil von Verkauf' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577529)
;

-- 2020-02-18T12:32:09.021Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Anteil von Verkauf', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577529
;

-- 2020-02-18T12:32:09.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Anteil von Verkauf', Description=NULL, Help=NULL WHERE AD_Element_ID = 577529
;

-- 2020-02-18T12:32:09.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Anteil von Verkauf', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577529
;

-- 2020-02-18T12:32:15.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Anteil von Verkauf', PrintName='Anteil von Verkauf',Updated=TO_TIMESTAMP('2020-02-18 14:32:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577529 AND AD_Language='de_CH'
;

-- 2020-02-18T12:32:15.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577529,'de_CH') 
;

-- 2020-02-18T12:44:14.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N',Updated=TO_TIMESTAMP('2020-02-18 14:44:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577529 AND AD_Language='de_DE'
;

-- 2020-02-18T12:44:14.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577529,'de_DE') 
;

-- 2020-02-18T12:44:14.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577529,'de_DE') 
;

-- 2020-02-18T12:44:15.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-02-18 14:44:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577529 AND AD_Language='de_DE'
;

-- 2020-02-18T12:44:15.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577529,'de_DE') 
;

-- 2020-02-18T12:44:15.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577529,'de_DE') 
;

