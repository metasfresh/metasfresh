
-- 2020-05-31T11:52:06.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Lager ab', PrintName='Lager ab',Updated=TO_TIMESTAMP('2020-05-31 14:52:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577736 AND AD_Language='nl_NL'
;

-- 2020-05-31T11:52:06.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577736,'nl_NL') 
;

-- 2020-05-31T12:08:52.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Lager ab', PrintName='Lager ab',Updated=TO_TIMESTAMP('2020-05-31 15:08:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577736 AND AD_Language='de_DE'
;

-- 2020-05-31T12:08:52.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577736,'de_DE') 
;

-- 2020-05-31T12:08:52.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577736,'de_DE') 
;

-- 2020-05-31T12:08:52.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_Warehouse_From_ID', Name='Lager ab', Description=NULL, Help=NULL WHERE AD_Element_ID=577736
;

-- 2020-05-31T12:08:52.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Warehouse_From_ID', Name='Lager ab', Description=NULL, Help=NULL, AD_Element_ID=577736 WHERE UPPER(ColumnName)='M_WAREHOUSE_FROM_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-05-31T12:08:52.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Warehouse_From_ID', Name='Lager ab', Description=NULL, Help=NULL WHERE AD_Element_ID=577736 AND IsCentrallyMaintained='Y'
;

-- 2020-05-31T12:08:52.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lager ab', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577736) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577736)
;

-- 2020-05-31T12:08:52.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lager ab', Name='Lager ab' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577736)
;

-- 2020-05-31T12:08:52.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lager ab', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577736
;

-- 2020-05-31T12:08:52.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Lager ab', Description=NULL, Help=NULL WHERE AD_Element_ID = 577736
;

-- 2020-05-31T12:08:52.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Lager ab', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577736
;

-- 2020-05-31T12:08:57.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Lager ab', PrintName='Lager ab',Updated=TO_TIMESTAMP('2020-05-31 15:08:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577736 AND AD_Language='de_CH'
;

-- 2020-05-31T12:08:57.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577736,'de_CH') 
;

-- 2020-05-31T12:10:59.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Lager an', PrintName='Lager an',Updated=TO_TIMESTAMP('2020-05-31 15:10:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577737 AND AD_Language='de_CH'
;

-- 2020-05-31T12:10:59.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577737,'de_CH') 
;

-- 2020-05-31T12:11:06.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Lager an', PrintName='Lager an',Updated=TO_TIMESTAMP('2020-05-31 15:11:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577737 AND AD_Language='de_DE'
;

-- 2020-05-31T12:11:06.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577737,'de_DE') 
;

-- 2020-05-31T12:11:06.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577737,'de_DE') 
;

-- 2020-05-31T12:11:06.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_Warehouse_To_ID', Name='Lager an', Description=NULL, Help=NULL WHERE AD_Element_ID=577737
;

-- 2020-05-31T12:11:06.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Warehouse_To_ID', Name='Lager an', Description=NULL, Help=NULL, AD_Element_ID=577737 WHERE UPPER(ColumnName)='M_WAREHOUSE_TO_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-05-31T12:11:06.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Warehouse_To_ID', Name='Lager an', Description=NULL, Help=NULL WHERE AD_Element_ID=577737 AND IsCentrallyMaintained='Y'
;

-- 2020-05-31T12:11:06.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lager an', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577737) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577737)
;

-- 2020-05-31T12:11:06.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lager an', Name='Lager an' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577737)
;

-- 2020-05-31T12:11:06.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lager an', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577737
;

-- 2020-05-31T12:11:06.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Lager an', Description=NULL, Help=NULL WHERE AD_Element_ID = 577737
;

-- 2020-05-31T12:11:06.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Lager an', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577737
;

-- 2020-05-31T12:11:13.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Lager an', PrintName='Lager an',Updated=TO_TIMESTAMP('2020-05-31 15:11:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577737 AND AD_Language='nl_NL'
;

-- 2020-05-31T12:11:13.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577737,'nl_NL') 
;

