-- 2020-01-08T08:43:13.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-08 10:43:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574479 AND AD_Language='de_CH'
;

-- 2020-01-08T08:43:13.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574479,'de_CH') 
;

-- 2020-01-08T08:43:24.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Bank Account', PrintName='Bank Account',Updated=TO_TIMESTAMP('2020-01-08 10:43:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574479 AND AD_Language='en_US'
;

-- 2020-01-08T08:43:24.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574479,'en_US') 
;

-- 2020-01-08T08:43:29.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-08 10:43:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574479 AND AD_Language='de_DE'
;

-- 2020-01-08T08:43:29.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574479,'de_DE') 
;

-- 2020-01-08T08:43:29.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(574479,'de_DE') 
;

-- 2020-01-08T08:58:54.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-08 10:58:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575616 AND AD_Language='de_CH'
;

-- 2020-01-08T08:58:54.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575616,'de_CH') 
;

-- 2020-01-08T08:59:35.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Bankkontoo',Updated=TO_TIMESTAMP('2020-01-08 10:59:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574479 AND AD_Language='de_DE'
;

-- 2020-01-08T08:59:35.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574479,'de_DE') 
;

-- 2020-01-08T08:59:35.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(574479,'de_DE') 
;

-- 2020-01-08T08:59:35.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Bankkontoo', Description='Bankkonto verwalten', Help='Das Fenster ermöglicht Kontodaten für eine Organisation oder einen Geschäftspartner zu verwalten' WHERE AD_Element_ID=574479
;

-- 2020-01-08T08:59:35.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Bankkontoo', Description='Bankkonto verwalten', Help='Das Fenster ermöglicht Kontodaten für eine Organisation oder einen Geschäftspartner zu verwalten' WHERE AD_Element_ID=574479 AND IsCentrallyMaintained='Y'
;

-- 2020-01-08T08:59:35.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Bankkontoo', Description='Bankkonto verwalten', Help='Das Fenster ermöglicht Kontodaten für eine Organisation oder einen Geschäftspartner zu verwalten' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=574479) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 574479)
;

-- 2020-01-08T08:59:35.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Bankkonto', Name='Bankkontoo' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=574479)
;

-- 2020-01-08T08:59:35.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Bankkontoo', Description='Bankkonto verwalten', Help='Das Fenster ermöglicht Kontodaten für eine Organisation oder einen Geschäftspartner zu verwalten', CommitWarning = NULL WHERE AD_Element_ID = 574479
;

-- 2020-01-08T08:59:35.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Bankkontoo', Description='Bankkonto verwalten', Help='Das Fenster ermöglicht Kontodaten für eine Organisation oder einen Geschäftspartner zu verwalten' WHERE AD_Element_ID = 574479
;

-- 2020-01-08T08:59:35.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Bankkontoo', Description = 'Bankkonto verwalten', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 574479
;

-- 2020-01-08T08:59:40.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Bankkontoo',Updated=TO_TIMESTAMP('2020-01-08 10:59:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574479 AND AD_Language='de_DE'
;

-- 2020-01-08T08:59:40.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574479,'de_DE') 
;

-- 2020-01-08T08:59:40.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(574479,'de_DE') 
;

-- 2020-01-08T08:59:40.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Bankkontoo', Name='Bankkontoo' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=574479)
;

-- 2020-01-08T09:00:33.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Bankkonto', PrintName='Bankkonto',Updated=TO_TIMESTAMP('2020-01-08 11:00:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574479 AND AD_Language='de_DE'
;

-- 2020-01-08T09:00:33.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574479,'de_DE') 
;

-- 2020-01-08T09:00:33.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(574479,'de_DE') 
;

-- 2020-01-08T09:00:33.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Bankkonto', Description='Bankkonto verwalten', Help='Das Fenster ermöglicht Kontodaten für eine Organisation oder einen Geschäftspartner zu verwalten' WHERE AD_Element_ID=574479
;

-- 2020-01-08T09:00:33.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Bankkonto', Description='Bankkonto verwalten', Help='Das Fenster ermöglicht Kontodaten für eine Organisation oder einen Geschäftspartner zu verwalten' WHERE AD_Element_ID=574479 AND IsCentrallyMaintained='Y'
;

-- 2020-01-08T09:00:33.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Bankkonto', Description='Bankkonto verwalten', Help='Das Fenster ermöglicht Kontodaten für eine Organisation oder einen Geschäftspartner zu verwalten' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=574479) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 574479)
;

-- 2020-01-08T09:00:33.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Bankkonto', Name='Bankkonto' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=574479)
;

-- 2020-01-08T09:00:33.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Bankkonto', Description='Bankkonto verwalten', Help='Das Fenster ermöglicht Kontodaten für eine Organisation oder einen Geschäftspartner zu verwalten', CommitWarning = NULL WHERE AD_Element_ID = 574479
;

-- 2020-01-08T09:00:33.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Bankkonto', Description='Bankkonto verwalten', Help='Das Fenster ermöglicht Kontodaten für eine Organisation oder einen Geschäftspartner zu verwalten' WHERE AD_Element_ID = 574479
;

-- 2020-01-08T09:00:33.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Bankkonto', Description = 'Bankkonto verwalten', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 574479
;

-- 2020-01-08T09:00:41.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Bank Accounttt', PrintName='Bank Accounttt',Updated=TO_TIMESTAMP('2020-01-08 11:00:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574479 AND AD_Language='en_US'
;

-- 2020-01-08T09:00:41.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574479,'en_US') 
;

-- 2020-01-08T09:01:15.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Bank Account', PrintName='Bank Account',Updated=TO_TIMESTAMP('2020-01-08 11:01:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574479 AND AD_Language='en_US'
;

-- 2020-01-08T09:01:15.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574479,'en_US') 
;

-- 2020-01-08T09:01:19.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET AD_Element_ID=574479, Description='Bankkonto verwalten', EntityType='D', Name='Bankkonto', WEBUI_NameBrowse=NULL, WEBUI_NameNew=NULL, WEBUI_NameNewBreadcrumb=NULL,Updated=TO_TIMESTAMP('2020-01-08 11:01:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540814
;

-- 2020-01-08T09:01:19.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(574479) 
;

