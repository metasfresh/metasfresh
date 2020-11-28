-- 2019-06-10T14:42:03.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Besuchsadresse', PrintName='Besuchsadresse',Updated=TO_TIMESTAMP('2019-06-10 14:42:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576808 AND AD_Language='de_CH'
;

-- 2019-06-10T14:42:03.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576808,'de_CH') 
;

-- 2019-06-10T14:42:07.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Besuchsadresse', PrintName='Besuchsadresse',Updated=TO_TIMESTAMP('2019-06-10 14:42:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576808 AND AD_Language='de_DE'
;

-- 2019-06-10T14:42:07.836
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576808,'de_DE') 
;

-- 2019-06-10T14:42:07.844
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576808,'de_DE') 
;

-- 2019-06-10T14:42:07.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='VisitorsAddress', Name='Besuchsadresse', Description=NULL, Help=NULL WHERE AD_Element_ID=576808
;

-- 2019-06-10T14:42:07.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='VisitorsAddress', Name='Besuchsadresse', Description=NULL, Help=NULL, AD_Element_ID=576808 WHERE UPPER(ColumnName)='VISITORSADDRESS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-06-10T14:42:07.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='VisitorsAddress', Name='Besuchsadresse', Description=NULL, Help=NULL WHERE AD_Element_ID=576808 AND IsCentrallyMaintained='Y'
;

-- 2019-06-10T14:42:07.860
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Besuchsadresse', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576808) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576808)
;

-- 2019-06-10T14:42:07.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Besuchsadresse', Name='Besuchsadresse' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576808)
;

-- 2019-06-10T14:42:07.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Besuchsadresse', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576808
;

-- 2019-06-10T14:42:07.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Besuchsadresse', Description=NULL, Help=NULL WHERE AD_Element_ID = 576808
;

-- 2019-06-10T14:42:07.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Besuchsadresse', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576808
;

-- 2019-06-10T14:42:10.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-06-10 14:42:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576808 AND AD_Language='en_US'
;

-- 2019-06-10T14:42:10.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576808,'en_US') 
;

