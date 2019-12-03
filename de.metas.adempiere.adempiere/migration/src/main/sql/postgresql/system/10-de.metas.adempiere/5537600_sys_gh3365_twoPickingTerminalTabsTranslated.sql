-- 2019-12-02T13:29:16.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Auftrag / Geschäftspartneradresse', PrintName='Auftrag / Geschäftspartneradresse',Updated=TO_TIMESTAMP('2019-12-02 15:29:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543502 AND AD_Language='de_CH'
;

-- 2019-12-02T13:29:16.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543502,'de_CH') 
;

-- 2019-12-02T13:29:25.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Auftrag / Geschäftspartneradresse', PrintName='Auftrag / Geschäftspartneradresse',Updated=TO_TIMESTAMP('2019-12-02 15:29:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543502 AND AD_Language='de_DE'
;

-- 2019-12-02T13:29:25.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543502,'de_DE') 
;

-- 2019-12-02T13:29:25.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543502,'de_DE') 
;

-- 2019-12-02T13:29:25.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='OrderOrBPLocation', Name='Auftrag / Geschäftspartneradresse', Description=NULL, Help=NULL WHERE AD_Element_ID=543502
;

-- 2019-12-02T13:29:25.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='OrderOrBPLocation', Name='Auftrag / Geschäftspartneradresse', Description=NULL, Help=NULL, AD_Element_ID=543502 WHERE UPPER(ColumnName)='ORDERORBPLOCATION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-02T13:29:25.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='OrderOrBPLocation', Name='Auftrag / Geschäftspartneradresse', Description=NULL, Help=NULL WHERE AD_Element_ID=543502 AND IsCentrallyMaintained='Y'
;

-- 2019-12-02T13:29:25.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Auftrag / Geschäftspartneradresse', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543502) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543502)
;

-- 2019-12-02T13:29:25.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Auftrag / Geschäftspartneradresse', Name='Auftrag / Geschäftspartneradresse' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543502)
;

-- 2019-12-02T13:29:25.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Auftrag / Geschäftspartneradresse', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543502
;

-- 2019-12-02T13:29:25.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Auftrag / Geschäftspartneradresse', Description=NULL, Help=NULL WHERE AD_Element_ID = 543502
;

-- 2019-12-02T13:29:25.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Auftrag / Geschäftspartneradresse', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543502
;

-- 2019-12-02T13:31:59.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produkt / Geschäftspartner', PrintName='Produkt / Geschäftspartner',Updated=TO_TIMESTAMP('2019-12-02 15:31:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543501 AND AD_Language='de_CH'
;

-- 2019-12-02T13:31:59.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543501,'de_CH') 
;

-- 2019-12-02T13:32:06.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produkt / Geschäftspartner', PrintName='Produkt / Geschäftspartner',Updated=TO_TIMESTAMP('2019-12-02 15:32:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543501 AND AD_Language='de_DE'
;

-- 2019-12-02T13:32:06.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543501,'de_DE') 
;

-- 2019-12-02T13:32:06.347Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543501,'de_DE') 
;

-- 2019-12-02T13:32:06.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ProductOrBPartner', Name='Produkt / Geschäftspartner', Description=NULL, Help=NULL WHERE AD_Element_ID=543501
;

-- 2019-12-02T13:32:06.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProductOrBPartner', Name='Produkt / Geschäftspartner', Description=NULL, Help=NULL, AD_Element_ID=543501 WHERE UPPER(ColumnName)='PRODUCTORBPARTNER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-02T13:32:06.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ProductOrBPartner', Name='Produkt / Geschäftspartner', Description=NULL, Help=NULL WHERE AD_Element_ID=543501 AND IsCentrallyMaintained='Y'
;

-- 2019-12-02T13:32:06.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produkt / Geschäftspartner', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543501) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543501)
;

-- 2019-12-02T13:32:06.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Produkt / Geschäftspartner', Name='Produkt / Geschäftspartner' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543501)
;

-- 2019-12-02T13:32:06.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produkt / Geschäftspartner', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543501
;

-- 2019-12-02T13:32:06.366Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Produkt / Geschäftspartner', Description=NULL, Help=NULL WHERE AD_Element_ID = 543501
;

-- 2019-12-02T13:32:06.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Produkt / Geschäftspartner', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543501
;

