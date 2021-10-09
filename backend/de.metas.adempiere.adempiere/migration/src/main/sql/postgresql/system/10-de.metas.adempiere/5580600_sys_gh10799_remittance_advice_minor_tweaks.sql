-- 2021-03-11T18:54:47.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=541023,Updated=TO_TIMESTAMP('2021-03-11 19:54:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541573
;

-- 2021-03-11T18:56:15.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2021-03-11 19:56:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576419
;

-- 2021-03-11T18:56:31.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2021-03-11 19:56:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576425
;
-- 2021-03-11T20:29:43.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zahlungsavis (REMADV)', PrintName='Zahlungsavis (REMADV)',Updated=TO_TIMESTAMP('2021-03-11 21:29:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578673 AND AD_Language='de_CH'
;

-- 2021-03-11T20:29:43.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578673,'de_CH') 
;

-- 2021-03-11T20:29:48.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zahlungsavis (REMADV)', PrintName='Zahlungsavis (REMADV)',Updated=TO_TIMESTAMP('2021-03-11 21:29:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578673 AND AD_Language='de_DE'
;

-- 2021-03-11T20:29:48.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578673,'de_DE') 
;

-- 2021-03-11T20:29:48.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578673,'de_DE') 
;

-- 2021-03-11T20:29:48.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_RemittanceAdvice_ID', Name='Zahlungsavis (REMADV)', Description=NULL, Help=NULL WHERE AD_Element_ID=578673
;

-- 2021-03-11T20:29:48.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_RemittanceAdvice_ID', Name='Zahlungsavis (REMADV)', Description=NULL, Help=NULL, AD_Element_ID=578673 WHERE UPPER(ColumnName)='C_REMITTANCEADVICE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-03-11T20:29:48.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_RemittanceAdvice_ID', Name='Zahlungsavis (REMADV)', Description=NULL, Help=NULL WHERE AD_Element_ID=578673 AND IsCentrallyMaintained='Y'
;

-- 2021-03-11T20:29:48.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zahlungsavis (REMADV)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578673) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578673)
;

-- 2021-03-11T20:29:48.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zahlungsavis (REMADV)', Name='Zahlungsavis (REMADV)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578673)
;

-- 2021-03-11T20:29:48.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zahlungsavis (REMADV)', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578673
;

-- 2021-03-11T20:29:48.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zahlungsavis (REMADV)', Description=NULL, Help=NULL WHERE AD_Element_ID = 578673
;

-- 2021-03-11T20:29:48.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zahlungsavis (REMADV)', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578673
;

-- 2021-03-11T20:29:53.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Remittance Advice (REMADV)', PrintName='Remittance Advice (REMADV)',Updated=TO_TIMESTAMP('2021-03-11 21:29:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578673 AND AD_Language='en_US'
;

-- 2021-03-11T20:29:53.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578673,'en_US') 
;

