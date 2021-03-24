-- 2020-12-15T11:54:44.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Service/Reparatur',Updated=TO_TIMESTAMP('2020-12-15 13:54:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542228
;

-- 2020-12-15T11:54:53.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Service/Reparatur',Updated=TO_TIMESTAMP('2020-12-15 13:54:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542228
;

-- 2020-12-15T11:54:58.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-12-15 13:54:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542228
;

-- 2020-12-15T11:55:03.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Service/Reparatur',Updated=TO_TIMESTAMP('2020-12-15 13:55:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542228
;

-- 2020-12-15T11:56:24.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Service/Reparatur Projekt', PrintName='Service/Reparatur Projekt',Updated=TO_TIMESTAMP('2020-12-15 13:56:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578610 AND AD_Language='de_CH'
;

-- 2020-12-15T11:56:24.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578610,'de_CH') 
;

-- 2020-12-15T11:56:32.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Service/Reparatur Projekt', PrintName='Service/Reparatur Projekt',Updated=TO_TIMESTAMP('2020-12-15 13:56:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578610 AND AD_Language='de_DE'
;

-- 2020-12-15T11:56:32.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578610,'de_DE') 
;

-- 2020-12-15T11:56:32.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578610,'de_DE') 
;

-- 2020-12-15T11:56:32.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Service/Reparatur Projekt', Description=NULL, Help=NULL WHERE AD_Element_ID=578610
;

-- 2020-12-15T11:56:32.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Service/Reparatur Projekt', Description=NULL, Help=NULL WHERE AD_Element_ID=578610 AND IsCentrallyMaintained='Y'
;

-- 2020-12-15T11:56:32.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Service/Reparatur Projekt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578610) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578610)
;

-- 2020-12-15T11:56:32.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Service/Reparatur Projekt', Name='Service/Reparatur Projekt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578610)
;

-- 2020-12-15T11:56:32.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Service/Reparatur Projekt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578610
;

-- 2020-12-15T11:56:32.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Service/Reparatur Projekt', Description=NULL, Help=NULL WHERE AD_Element_ID = 578610
;

-- 2020-12-15T11:56:32.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Service/Reparatur Projekt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578610
;

-- 2020-12-15T11:56:35.616Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-12-15 13:56:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578610 AND AD_Language='en_US'
;

-- 2020-12-15T11:56:35.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578610,'en_US') 
;

