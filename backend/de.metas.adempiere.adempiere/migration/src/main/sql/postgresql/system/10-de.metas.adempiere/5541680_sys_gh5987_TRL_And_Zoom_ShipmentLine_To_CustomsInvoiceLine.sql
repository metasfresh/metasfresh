-- 2020-01-16T09:05:53.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zollrechnungsposition', PrintName='Zollrechnungsposition',Updated=TO_TIMESTAMP('2020-01-16 11:05:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576742 AND AD_Language='de_CH'
;

-- 2020-01-16T09:05:53.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576742,'de_CH') 
;

-- 2020-01-16T09:05:55.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-16 11:05:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576742 AND AD_Language='de_CH'
;

-- 2020-01-16T09:05:55.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576742,'de_CH') 
;

-- 2020-01-16T09:06:03.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zollrechnungsposition', PrintName='Zollrechnungsposition',Updated=TO_TIMESTAMP('2020-01-16 11:06:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576742 AND AD_Language='de_DE'
;

-- 2020-01-16T09:06:03.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576742,'de_DE') 
;

-- 2020-01-16T09:06:03.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576742,'de_DE') 
;

-- 2020-01-16T09:06:03.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Customs_Invoice_Line_ID', Name='Zollrechnungsposition', Description=NULL, Help=NULL WHERE AD_Element_ID=576742
;

-- 2020-01-16T09:06:03.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Customs_Invoice_Line_ID', Name='Zollrechnungsposition', Description=NULL, Help=NULL, AD_Element_ID=576742 WHERE UPPER(ColumnName)='C_CUSTOMS_INVOICE_LINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-01-16T09:06:03.745Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Customs_Invoice_Line_ID', Name='Zollrechnungsposition', Description=NULL, Help=NULL WHERE AD_Element_ID=576742 AND IsCentrallyMaintained='Y'
;

-- 2020-01-16T09:06:03.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zollrechnungsposition', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576742) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576742)
;

-- 2020-01-16T09:06:03.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zollrechnungsposition', Name='Zollrechnungsposition' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576742)
;

-- 2020-01-16T09:06:03.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zollrechnungsposition', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576742
;

-- 2020-01-16T09:06:03.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zollrechnungsposition', Description=NULL, Help=NULL WHERE AD_Element_ID = 576742
;

-- 2020-01-16T09:06:03.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zollrechnungsposition', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576742
;

-- 2020-01-16T09:06:17.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Customs Invoice Line', PrintName='Customs Invoice Line',Updated=TO_TIMESTAMP('2020-01-16 11:06:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576742 AND AD_Language='en_US'
;

-- 2020-01-16T09:06:17.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576742,'en_US') 
;

-- 2020-01-16T09:07:34.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540643,Updated=TO_TIMESTAMP('2020-01-16 11:07:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541361
;

