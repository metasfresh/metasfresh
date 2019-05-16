-- 2019-05-09T07:24:26.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Specifies whether this field is available to external applications via metasfresh API.', Name='Available in API', PrintName='Available in API',Updated=TO_TIMESTAMP('2019-05-09 07:24:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576703 AND AD_Language='en_US'
;

-- 2019-05-09T07:24:26.738
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576703,'en_US') 
;

-- 2019-05-09T07:24:48.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest, ob dieses Feld via metasfresh API für externe Anwendungen verfügbar ist.', IsTranslated='Y', Name='In API verfügbar', PrintName='In API verfügbar',Updated=TO_TIMESTAMP('2019-05-09 07:24:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576703 AND AD_Language='de_CH'
;

-- 2019-05-09T07:24:48.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576703,'de_CH') 
;

-- 2019-05-09T07:25:01.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt fest, ob dieses Feld via metasfresh API für externe Anwendungen verfügbar ist.', Name='In API verfügbar', PrintName='In API verfügbar',Updated=TO_TIMESTAMP('2019-05-09 07:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576703 AND AD_Language='de_DE'
;

-- 2019-05-09T07:25:01.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576703,'de_DE') 
;

-- 2019-05-09T07:25:01.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576703,'de_DE') 
;

-- 2019-05-09T07:25:01.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IncludedInAPICall', Name='In API verfügbar', Description='Legt fest, ob dieses Feld via metasfresh API für externe Anwendungen verfügbar ist.', Help=NULL WHERE AD_Element_ID=576703
;

-- 2019-05-09T07:25:01.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IncludedInAPICall', Name='In API verfügbar', Description='Legt fest, ob dieses Feld via metasfresh API für externe Anwendungen verfügbar ist.', Help=NULL, AD_Element_ID=576703 WHERE UPPER(ColumnName)='INCLUDEDINAPICALL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-05-09T07:25:01.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IncludedInAPICall', Name='In API verfügbar', Description='Legt fest, ob dieses Feld via metasfresh API für externe Anwendungen verfügbar ist.', Help=NULL WHERE AD_Element_ID=576703 AND IsCentrallyMaintained='Y'
;

-- 2019-05-09T07:25:01.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='In API verfügbar', Description='Legt fest, ob dieses Feld via metasfresh API für externe Anwendungen verfügbar ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576703) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576703)
;

-- 2019-05-09T07:25:01.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='In API verfügbar', Name='In API verfügbar' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576703)
;

-- 2019-05-09T07:25:01.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='In API verfügbar', Description='Legt fest, ob dieses Feld via metasfresh API für externe Anwendungen verfügbar ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576703
;

-- 2019-05-09T07:25:01.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='In API verfügbar', Description='Legt fest, ob dieses Feld via metasfresh API für externe Anwendungen verfügbar ist.', Help=NULL WHERE AD_Element_ID = 576703
;

-- 2019-05-09T07:25:01.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'In API verfügbar', Description = 'Legt fest, ob dieses Feld via metasfresh API für externe Anwendungen verfügbar ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576703
;

-- 2019-05-09T07:25:04.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-05-09 07:25:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576703 AND AD_Language='de_DE'
;

-- 2019-05-09T07:25:04.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576703,'de_DE') 
;

-- 2019-05-09T07:25:04.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576703,'de_DE') 
;

-- 2019-05-09T07:25:28.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-05-09 07:25:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576703 AND AD_Language='en_US'
;

-- 2019-05-09T07:25:28.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576703,'en_US') 
;

