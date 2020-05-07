-- 2019-09-11T13:30:55.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Ignore Price', PrintName='Ignore Price',Updated=TO_TIMESTAMP('2019-09-11 16:30:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577056 AND AD_Language='en_US'
;

-- 2019-09-11T13:30:55.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577056,'en_US') 
;

-- 2019-09-11T13:31:06.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Preis ignorieren', PrintName='Preis ignorieren',Updated=TO_TIMESTAMP('2019-09-11 16:31:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577056 AND AD_Language='de_DE'
;

-- 2019-09-11T13:31:06.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577056,'de_DE') 
;

-- 2019-09-11T13:31:06.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577056,'de_DE') 
;

-- 2019-09-11T13:31:06.268Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsInvalidPrice', Name='Preis ignorieren', Description=NULL, Help=NULL WHERE AD_Element_ID=577056
;

-- 2019-09-11T13:31:06.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInvalidPrice', Name='Preis ignorieren', Description=NULL, Help=NULL, AD_Element_ID=577056 WHERE UPPER(ColumnName)='ISINVALIDPRICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-11T13:31:06.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInvalidPrice', Name='Preis ignorieren', Description=NULL, Help=NULL WHERE AD_Element_ID=577056 AND IsCentrallyMaintained='Y'
;

-- 2019-09-11T13:31:06.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Preis ignorieren', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577056) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577056)
;

-- 2019-09-11T13:31:06.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Preis ignorieren', Name='Preis ignorieren' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577056)
;

-- 2019-09-11T13:31:06.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Preis ignorieren', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577056
;

-- 2019-09-11T13:31:06.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Preis ignorieren', Description=NULL, Help=NULL WHERE AD_Element_ID = 577056
;

-- 2019-09-11T13:31:06.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Preis ignorieren', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577056
;

-- 2019-09-11T13:31:22.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Ist das Feld "Preis ignorieren" aktiviert, kann der Produktpreis zwar kopiert werden, allerdings wird er danach nicht für Preiskalkulationen verwendet.',Updated=TO_TIMESTAMP('2019-09-11 16:31:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577056 AND AD_Language='de_DE'
;

-- 2019-09-11T13:31:22.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577056,'de_DE') 
;

-- 2019-09-11T13:31:22.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577056,'de_DE') 
;

-- 2019-09-11T13:31:22.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsInvalidPrice', Name='Preis ignorieren', Description='Ist das Feld "Preis ignorieren" aktiviert, kann der Produktpreis zwar kopiert werden, allerdings wird er danach nicht für Preiskalkulationen verwendet.', Help=NULL WHERE AD_Element_ID=577056
;

-- 2019-09-11T13:31:22.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInvalidPrice', Name='Preis ignorieren', Description='Ist das Feld "Preis ignorieren" aktiviert, kann der Produktpreis zwar kopiert werden, allerdings wird er danach nicht für Preiskalkulationen verwendet.', Help=NULL, AD_Element_ID=577056 WHERE UPPER(ColumnName)='ISINVALIDPRICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-11T13:31:22.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInvalidPrice', Name='Preis ignorieren', Description='Ist das Feld "Preis ignorieren" aktiviert, kann der Produktpreis zwar kopiert werden, allerdings wird er danach nicht für Preiskalkulationen verwendet.', Help=NULL WHERE AD_Element_ID=577056 AND IsCentrallyMaintained='Y'
;

-- 2019-09-11T13:31:22.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Preis ignorieren', Description='Ist das Feld "Preis ignorieren" aktiviert, kann der Produktpreis zwar kopiert werden, allerdings wird er danach nicht für Preiskalkulationen verwendet.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577056) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577056)
;

-- 2019-09-11T13:31:22.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Preis ignorieren', Description='Ist das Feld "Preis ignorieren" aktiviert, kann der Produktpreis zwar kopiert werden, allerdings wird er danach nicht für Preiskalkulationen verwendet.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577056
;

-- 2019-09-11T13:31:22.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Preis ignorieren', Description='Ist das Feld "Preis ignorieren" aktiviert, kann der Produktpreis zwar kopiert werden, allerdings wird er danach nicht für Preiskalkulationen verwendet.', Help=NULL WHERE AD_Element_ID = 577056
;

-- 2019-09-11T13:31:22.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Preis ignorieren', Description = 'Ist das Feld "Preis ignorieren" aktiviert, kann der Produktpreis zwar kopiert werden, allerdings wird er danach nicht für Preiskalkulationen verwendet.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577056
;

-- 2019-09-11T13:31:37.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Ist das Feld "Preis ignorieren" aktiviert, kann der Produktpreis zwar kopiert werden, allerdings wird er danach nicht für Preiskalkulationen verwendet.', Name='Preis ignorieren', PrintName='Preis ignorieren',Updated=TO_TIMESTAMP('2019-09-11 16:31:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577056 AND AD_Language='de_CH'
;

-- 2019-09-11T13:31:37.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577056,'de_CH') 
;

-- 2019-09-11T13:31:53.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If "Ignore Price" = Y then the Product Price can be copied but it will not be applied by the pricing engine when it computes a price.',Updated=TO_TIMESTAMP('2019-09-11 16:31:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577056 AND AD_Language='en_US'
;

-- 2019-09-11T13:31:53.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577056,'en_US') 
;

