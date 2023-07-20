-- 2021-11-12T09:57:28.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Create Membership Contracts',Updated=TO_TIMESTAMP('2021-11-12 11:57:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584929
;

-- 2021-11-12T09:57:28.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Create Membership Contracts',Updated=TO_TIMESTAMP('2021-11-12 11:57:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541832
;

-- 2021-11-12T09:57:38.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Mitgliedsverträge erstellen',Updated=TO_TIMESTAMP('2021-11-12 11:57:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584929
;

-- 2021-11-12T09:57:38.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Mitgliedsverträge erstellen',Updated=TO_TIMESTAMP('2021-11-12 11:57:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541832
;

-- 2021-11-12T09:57:46.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Mitgliedsverträge erstellen',Updated=TO_TIMESTAMP('2021-11-12 11:57:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584929
;

-- 2021-11-12T09:57:54.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Mitgliedsverträge erstellen',Updated=TO_TIMESTAMP('2021-11-12 11:57:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584929
;

-- 2021-11-12T09:57:59.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Create Membership Contracts',Updated=TO_TIMESTAMP('2021-11-12 11:57:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584929
;

-- 2021-11-12T09:58:04.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Create Membership Contracts',Updated=TO_TIMESTAMP('2021-11-12 11:58:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584929
;




-- 2021-11-12T11:39:28.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Mitgliedsverträge erstellen', PrintName='Mitgliedsverträge erstellen',Updated=TO_TIMESTAMP('2021-11-12 13:39:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580154 AND AD_Language='de_CH'
;

-- 2021-11-12T11:39:28.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580154,'de_CH') 
;

-- 2021-11-12T11:39:33.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Mitgliedsverträge erstellen', PrintName='Mitgliedsverträge erstellen',Updated=TO_TIMESTAMP('2021-11-12 13:39:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580154 AND AD_Language='de_DE'
;

-- 2021-11-12T11:39:33.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580154,'de_DE') 
;

-- 2021-11-12T11:39:33.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580154,'de_DE') 
;

-- 2021-11-12T11:39:33.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Mitgliedsverträge erstellen', Description=NULL, Help=NULL WHERE AD_Element_ID=580154
;

-- 2021-11-12T11:39:33.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Mitgliedsverträge erstellen', Description=NULL, Help=NULL WHERE AD_Element_ID=580154 AND IsCentrallyMaintained='Y'
;

-- 2021-11-12T11:39:33.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mitgliedsverträge erstellen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580154) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580154)
;

-- 2021-11-12T11:39:33.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Mitgliedsverträge erstellen', Name='Mitgliedsverträge erstellen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580154)
;

-- 2021-11-12T11:39:33.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Mitgliedsverträge erstellen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580154
;

-- 2021-11-12T11:39:33.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Mitgliedsverträge erstellen', Description=NULL, Help=NULL WHERE AD_Element_ID = 580154
;

-- 2021-11-12T11:39:33.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Mitgliedsverträge erstellen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580154
;

-- 2021-11-12T11:39:41.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Create Membership Contracts', PrintName='Create Membership Contracts',Updated=TO_TIMESTAMP('2021-11-12 13:39:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580154 AND AD_Language='en_US'
;

-- 2021-11-12T11:39:41.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580154,'en_US') 
;

-- 2021-11-12T11:39:48.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Create Membership Contracts', PrintName='Create Membership Contracts',Updated=TO_TIMESTAMP('2021-11-12 13:39:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580154 AND AD_Language='nl_NL'
;

-- 2021-11-12T11:39:48.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580154,'nl_NL') 
;

