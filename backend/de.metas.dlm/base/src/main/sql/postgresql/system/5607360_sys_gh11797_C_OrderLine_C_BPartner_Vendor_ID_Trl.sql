-- 2021-10-01T07:09:31.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Lieferant', PrintName='Lieferant',Updated=TO_TIMESTAMP('2021-10-01 10:09:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579924 AND AD_Language='de_CH'
;

-- 2021-10-01T07:09:31.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579924,'de_CH') 
;

-- 2021-10-01T07:09:43.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Lieferant', PrintName='Lieferant',Updated=TO_TIMESTAMP('2021-10-01 10:09:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579924 AND AD_Language='de_DE'
;

-- 2021-10-01T07:09:43.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579924,'de_DE') 
;

-- 2021-10-01T07:09:43.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579924,'de_DE') 
;

-- 2021-10-01T07:09:43.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='VendId', Name='Lieferant', Description=NULL, Help=NULL WHERE AD_Element_ID=579924
;

-- 2021-10-01T07:09:43.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='VendId', Name='Lieferant', Description=NULL, Help=NULL, AD_Element_ID=579924 WHERE UPPER(ColumnName)='VENDID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-10-01T07:09:43.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='VendId', Name='Lieferant', Description=NULL, Help=NULL WHERE AD_Element_ID=579924 AND IsCentrallyMaintained='Y'
;

-- 2021-10-01T07:09:43.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferant', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579924) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579924)
;

-- 2021-10-01T07:09:43.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferant', Name='Lieferant' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579924)
;

-- 2021-10-01T07:09:43.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lieferant', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579924
;

-- 2021-10-01T07:09:43.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Lieferant', Description=NULL, Help=NULL WHERE AD_Element_ID = 579924
;

-- 2021-10-01T07:09:43.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Lieferant', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579924
;

-- 2021-10-01T07:09:51.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vendor', PrintName='Vendor',Updated=TO_TIMESTAMP('2021-10-01 10:09:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579924 AND AD_Language='en_US'
;

-- 2021-10-01T07:09:51.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579924,'en_US') 
;

-- 2021-10-01T07:10:12.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Lieferant', PrintName='Lieferant',Updated=TO_TIMESTAMP('2021-10-01 10:10:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579924 AND AD_Language='nl_NL'
;

-- 2021-10-01T07:10:12.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579924,'nl_NL') 
;

