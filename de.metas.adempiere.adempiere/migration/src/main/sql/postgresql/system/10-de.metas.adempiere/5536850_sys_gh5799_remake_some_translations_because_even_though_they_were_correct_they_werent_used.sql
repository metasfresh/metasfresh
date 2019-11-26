-- 2019-11-26T12:09:51.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Price List Schema Line1', Name='Price List Schema Line1',Updated=TO_TIMESTAMP('2019-11-26 14:09:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577361
;

-- 2019-11-26T12:09:51.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577361,'en_US') 
;

-- 2019-11-26T12:09:59.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='1Preislisten-Schema Position', Name='1Preislisten-Schema Position',Updated=TO_TIMESTAMP('2019-11-26 14:09:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577361
;

-- 2019-11-26T12:09:59.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577361,'de_DE') 
;

-- 2019-11-26T12:09:59.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577361,'de_DE') 
;

-- 2019-11-26T12:09:59.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='1Preislisten-Schema Position', Description=NULL, Help=NULL WHERE AD_Element_ID=577361
;

-- 2019-11-26T12:09:59.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='1Preislisten-Schema Position', Description=NULL, Help=NULL WHERE AD_Element_ID=577361 AND IsCentrallyMaintained='Y'
;

-- 2019-11-26T12:09:59.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='1Preislisten-Schema Position', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577361) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577361)
;

-- 2019-11-26T12:09:59.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='1Preislisten-Schema Position', Name='1Preislisten-Schema Position' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577361)
;

-- 2019-11-26T12:09:59.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='1Preislisten-Schema Position', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577361
;

-- 2019-11-26T12:09:59.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='1Preislisten-Schema Position', Description=NULL, Help=NULL WHERE AD_Element_ID = 577361
;

-- 2019-11-26T12:09:59.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = '1Preislisten-Schema Position', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577361
;

-- 2019-11-26T12:10:05.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='1Preislisten-Schema Position', Name='1Preislisten-Schema Position',Updated=TO_TIMESTAMP('2019-11-26 14:10:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577361
;

-- 2019-11-26T12:10:05.253Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577361,'de_CH') 
;

-- 2019-11-26T12:10:24.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Preislisten-Schema Position', Name='Preislisten-Schema Position',Updated=TO_TIMESTAMP('2019-11-26 14:10:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577361
;

-- 2019-11-26T12:10:24.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577361,'de_CH') 
;

-- 2019-11-26T12:10:28.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Preislisten-Schema Position', Name='Preislisten-Schema Position',Updated=TO_TIMESTAMP('2019-11-26 14:10:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577361
;

-- 2019-11-26T12:10:28.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577361,'de_DE') 
;

-- 2019-11-26T12:10:28.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577361,'de_DE') 
;

-- 2019-11-26T12:10:28.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Preislisten-Schema Position', Description=NULL, Help=NULL WHERE AD_Element_ID=577361
;

-- 2019-11-26T12:10:28.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Preislisten-Schema Position', Description=NULL, Help=NULL WHERE AD_Element_ID=577361 AND IsCentrallyMaintained='Y'
;

-- 2019-11-26T12:10:28.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Preislisten-Schema Position', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577361) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577361)
;

-- 2019-11-26T12:10:28.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Preislisten-Schema Position', Name='Preislisten-Schema Position' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577361)
;

-- 2019-11-26T12:10:28.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Preislisten-Schema Position', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577361
;

-- 2019-11-26T12:10:28.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Preislisten-Schema Position', Description=NULL, Help=NULL WHERE AD_Element_ID = 577361
;

-- 2019-11-26T12:10:28.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Preislisten-Schema Position', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577361
;

-- 2019-11-26T12:10:34.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Price List Schema Line', Name='Price List Schema Line',Updated=TO_TIMESTAMP('2019-11-26 14:10:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577361
;

-- 2019-11-26T12:10:34.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577361,'en_US') 
;

-- 2019-11-26T12:11:33.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Preislisten-Schema Positionen exportieren',Updated=TO_TIMESTAMP('2019-11-26 14:11:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541210 AND AD_Language='de_CH'
;

-- 2019-11-26T12:11:36.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Preislisten-Schema Positionen exportieren',Updated=TO_TIMESTAMP('2019-11-26 14:11:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541210 AND AD_Language='de_DE'
;

-- 2019-11-26T12:11:56.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Preislisten-Schema Positionen importieren',Updated=TO_TIMESTAMP('2019-11-26 14:11:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541211 AND AD_Language='de_CH'
;

-- 2019-11-26T12:11:59.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Preislisten-Schema Positionen importieren',Updated=TO_TIMESTAMP('2019-11-26 14:11:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541211 AND AD_Language='de_DE'
;

