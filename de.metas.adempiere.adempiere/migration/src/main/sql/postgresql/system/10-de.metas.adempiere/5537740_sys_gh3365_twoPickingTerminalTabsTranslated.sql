-- 2019-12-03T13:05:40.972Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produkt / ', PrintName='Produkt / Geschäftspartner',Updated=TO_TIMESTAMP('2019-12-03 15:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543501 AND AD_Language='de_CH'
;

-- 2019-12-03T13:05:41.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543501,'de_CH') 
;

-- 2019-12-03T13:05:50.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produkt / Geschäftspartner', PrintName='Produkt / Geschäftspartner',Updated=TO_TIMESTAMP('2019-12-03 15:05:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543501 AND AD_Language='de_DE'
;

-- 2019-12-03T13:05:50.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543501,'de_DE') 
;

-- 2019-12-03T13:05:50.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543501,'de_DE') 
;

-- 2019-12-03T13:05:50.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ProductOrBPartner', Name='Produkt / Geschäftspartner', Description=NULL, Help=NULL WHERE AD_Element_ID=543501
;

-- 2019-12-03T13:05:50.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProductOrBPartner', Name='Produkt / Geschäftspartner', Description=NULL, Help=NULL, AD_Element_ID=543501 WHERE UPPER(ColumnName)='PRODUCTORBPARTNER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-03T13:05:50.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProductOrBPartner', Name='Produkt / Geschäftspartner', Description=NULL, Help=NULL WHERE AD_Element_ID=543501 AND IsCentrallyMaintained='Y'
;

-- 2019-12-03T13:05:50.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produkt / Geschäftspartner', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543501) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543501)
;

-- 2019-12-03T13:05:50.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Produkt / Geschäftspartner', Name='Produkt / Geschäftspartner' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543501)
;

-- 2019-12-03T13:05:50.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produkt / Geschäftspartner', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543501
;

-- 2019-12-03T13:05:50.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Produkt / Geschäftspartner', Description=NULL, Help=NULL WHERE AD_Element_ID = 543501
;

-- 2019-12-03T13:05:50.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Produkt / Geschäftspartner', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543501
;

-- 2019-12-03T13:10:46.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Auftrag / Geschäftspartneradresse', PrintName='Auftrag / Geschäftspartneradresse',Updated=TO_TIMESTAMP('2019-12-03 15:10:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543502 AND AD_Language='de_CH'
;

-- 2019-12-03T13:10:46.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543502,'de_CH') 
;

-- 2019-12-03T13:10:54.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Auftrag / Geschäftspartneradresse', PrintName='Auftrag / Geschäftspartneradresse',Updated=TO_TIMESTAMP('2019-12-03 15:10:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543502 AND AD_Language='de_DE'
;

-- 2019-12-03T13:10:54.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543502,'de_DE') 
;

-- 2019-12-03T13:10:54.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543502,'de_DE') 
;

-- 2019-12-03T13:10:54.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='OrderOrBPLocation', Name='Auftrag / Geschäftspartneradresse', Description=NULL, Help=NULL WHERE AD_Element_ID=543502
;

-- 2019-12-03T13:10:54.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='OrderOrBPLocation', Name='Auftrag / Geschäftspartneradresse', Description=NULL, Help=NULL, AD_Element_ID=543502 WHERE UPPER(ColumnName)='ORDERORBPLOCATION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-03T13:10:54.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='OrderOrBPLocation', Name='Auftrag / Geschäftspartneradresse', Description=NULL, Help=NULL WHERE AD_Element_ID=543502 AND IsCentrallyMaintained='Y'
;

-- 2019-12-03T13:10:54.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Auftrag / Geschäftspartneradresse', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543502) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543502)
;

-- 2019-12-03T13:10:54.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Auftrag / Geschäftspartneradresse', Name='Auftrag / Geschäftspartneradresse' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543502)
;

-- 2019-12-03T13:10:54.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Auftrag / Geschäftspartneradresse', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543502
;

-- 2019-12-03T13:10:54.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Auftrag / Geschäftspartneradresse', Description=NULL, Help=NULL WHERE AD_Element_ID = 543502
;

-- 2019-12-03T13:10:54.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Auftrag / Geschäftspartneradresse', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543502
;

