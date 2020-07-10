-- 2020-06-11T06:06:05.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Verbuchungsfehler', PrintName='Verbuchungsfehler',Updated=TO_TIMESTAMP('2020-06-11 09:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577755 AND AD_Language='de_CH'
;

-- 2020-06-11T06:06:06.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577755,'de_CH') 
;

-- 2020-06-11T06:06:09.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Verbuchungsfehler', PrintName='Verbuchungsfehler',Updated=TO_TIMESTAMP('2020-06-11 09:06:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577755 AND AD_Language='de_DE'
;

-- 2020-06-11T06:06:09.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577755,'de_DE') 
;

-- 2020-06-11T06:06:09.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577755,'de_DE') 
;

-- 2020-06-11T06:06:09.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PostingError_Issue_ID', Name='Verbuchungsfehler', Description=NULL, Help=NULL WHERE AD_Element_ID=577755
;

-- 2020-06-11T06:06:09.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PostingError_Issue_ID', Name='Verbuchungsfehler', Description=NULL, Help=NULL, AD_Element_ID=577755 WHERE UPPER(ColumnName)='POSTINGERROR_ISSUE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-06-11T06:06:09.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PostingError_Issue_ID', Name='Verbuchungsfehler', Description=NULL, Help=NULL WHERE AD_Element_ID=577755 AND IsCentrallyMaintained='Y'
;

-- 2020-06-11T06:06:09.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verbuchungsfehler', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577755) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577755)
;

-- 2020-06-11T06:06:09.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Verbuchungsfehler', Name='Verbuchungsfehler' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577755)
;

-- 2020-06-11T06:06:09.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Verbuchungsfehler', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577755
;

-- 2020-06-11T06:06:09.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Verbuchungsfehler', Description=NULL, Help=NULL WHERE AD_Element_ID = 577755
;

-- 2020-06-11T06:06:09.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Verbuchungsfehler', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577755
;

-- 2020-06-11T06:06:11.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-06-11 09:06:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577755 AND AD_Language='en_US'
;

-- 2020-06-11T06:06:11.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577755,'en_US') 
;

