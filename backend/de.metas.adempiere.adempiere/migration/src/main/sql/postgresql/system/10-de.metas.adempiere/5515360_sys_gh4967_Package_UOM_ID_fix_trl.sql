-- 2019-03-07T14:38:36.327
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Verpackungseinheit', PrintName='Verpackungseinheit',Updated=TO_TIMESTAMP('2019-03-07 14:38:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543504 AND AD_Language='de_CH'
;

-- 2019-03-07T14:38:36.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543504,'de_CH') 
;

-- 2019-03-07T14:38:41.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-03-07 14:38:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543504 AND AD_Language='en_US'
;

-- 2019-03-07T14:38:41.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543504,'en_US') 
;

-- 2019-03-07T14:38:48.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Verpackungseinheit', PrintName='Verpackungseinheit',Updated=TO_TIMESTAMP('2019-03-07 14:38:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543504 AND AD_Language='de_DE'
;

-- 2019-03-07T14:38:48.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543504,'de_DE') 
;

-- 2019-03-07T14:38:48.642
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543504,'de_DE') 
;

-- 2019-03-07T14:38:48.644
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Package_UOM_ID', Name='Verpackungseinheit', Description='', Help='' WHERE AD_Element_ID=543504
;

-- 2019-03-07T14:38:48.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Package_UOM_ID', Name='Verpackungseinheit', Description='', Help='', AD_Element_ID=543504 WHERE UPPER(ColumnName)='PACKAGE_UOM_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-07T14:38:48.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Package_UOM_ID', Name='Verpackungseinheit', Description='', Help='' WHERE AD_Element_ID=543504 AND IsCentrallyMaintained='Y'
;

-- 2019-03-07T14:38:48.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verpackungseinheit', Description='', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543504) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543504)
;

-- 2019-03-07T14:38:48.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Verpackungseinheit', Name='Verpackungseinheit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543504)
;

-- 2019-03-07T14:38:48.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Verpackungseinheit', Description='', Help='', CommitWarning = NULL WHERE AD_Element_ID = 543504
;

-- 2019-03-07T14:38:48.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Verpackungseinheit', Description='', Help='' WHERE AD_Element_ID = 543504
;

-- 2019-03-07T14:38:48.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Verpackungseinheit', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543504
;

