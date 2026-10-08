-- 2019-01-24T06:17:59.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='MSV3_Server_Product_Category',Updated=TO_TIMESTAMP('2019-01-24 06:17:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540425
;

-- 2019-01-24T06:18:36.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-24 06:18:36','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='MSV3 Server Produkte',PrintName='MSV3 Server Produkte' WHERE AD_Element_ID=574495 AND AD_Language='de_CH'
;

-- 2019-01-24T06:18:36.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574495,'de_CH') 
;

-- 2019-01-24T06:19:50.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-24 06:19:50','YYYY-MM-DD HH24:MI:SS'),Description='Legt die Produktkategorien fest, deren Produkte auf dem MSV3 Server verfügbar sind' WHERE AD_Element_ID=574495 AND AD_Language='de_CH'
;

-- 2019-01-24T06:19:50.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574495,'de_CH') 
;

-- 2019-01-24T06:20:21.850
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-24 06:20:21','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Description='Specifies the product categories whose products are available on the MSV3 server' WHERE AD_Element_ID=574495 AND AD_Language='en_US'
;

-- 2019-01-24T06:20:21.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574495,'en_US') 
;

-- 2019-01-24T06:20:44.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-24 06:20:44','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='MSV3 Server Produkte',PrintName='MSV3 Server Produkte',Description='Legt die Produktkategorien fest, deren Produkte auf dem MSV3 Server verfügbar sind' WHERE AD_Element_ID=574495 AND AD_Language='de_DE'
;

-- 2019-01-24T06:20:44.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574495,'de_DE') 
;

-- 2019-01-24T06:20:44.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(574495,'de_DE') 
;

-- 2019-01-24T06:20:44.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='MSV3 Server Produkte', Description='Legt die Produktkategorien fest, deren Produkte auf dem MSV3 Server verfügbar sind', Help=NULL WHERE AD_Element_ID=574495
;

-- 2019-01-24T06:20:44.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='MSV3 Server Produkte', Description='Legt die Produktkategorien fest, deren Produkte auf dem MSV3 Server verfügbar sind', Help=NULL WHERE AD_Element_ID=574495 AND IsCentrallyMaintained='Y'
;

-- 2019-01-24T06:20:44.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='MSV3 Server Produkte', Description='Legt die Produktkategorien fest, deren Produkte auf dem MSV3 Server verfügbar sind', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=574495) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 574495)
;

-- 2019-01-24T06:20:44.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='MSV3 Server Produkte', Name='MSV3 Server Produkte' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=574495)
;

-- 2019-01-24T06:20:44.605
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='MSV3 Server Produkte', Description='Legt die Produktkategorien fest, deren Produkte auf dem MSV3 Server verfügbar sind', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 574495
;

-- 2019-01-24T06:20:44.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='MSV3 Server Produkte', Description='Legt die Produktkategorien fest, deren Produkte auf dem MSV3 Server verfügbar sind', Help=NULL WHERE AD_Element_ID = 574495
;

-- 2019-01-24T06:20:44.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='MSV3 Server Produkte', Description='Legt die Produktkategorien fest, deren Produkte auf dem MSV3 Server verfügbar sind', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 574495
;

