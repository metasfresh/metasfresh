-- 2019-09-26T07:11:32.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Auftragsnr.', PrintName='Auftragsnr.',Updated=TO_TIMESTAMP('2019-09-26 10:11:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543353 AND AD_Language='de_CH'
;

-- 2019-09-26T07:11:32.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543353,'de_CH') 
;

-- 2019-09-26T07:11:35.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-26 10:11:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543353 AND AD_Language='en_US'
;

-- 2019-09-26T07:11:35.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543353,'en_US') 
;

-- 2019-09-26T07:11:40.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Auftragsnr.', PrintName='Auftragsnr.',Updated=TO_TIMESTAMP('2019-09-26 10:11:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543353 AND AD_Language='de_DE'
;

-- 2019-09-26T07:11:40.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543353,'de_DE') 
;

-- 2019-09-26T07:11:40.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543353,'de_DE') 
;

-- 2019-09-26T07:11:40.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='OrderDocumentNo', Name='Auftragsnr.', Description='', Help=NULL WHERE AD_Element_ID=543353
;

-- 2019-09-26T07:11:40.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='OrderDocumentNo', Name='Auftragsnr.', Description='', Help=NULL, AD_Element_ID=543353 WHERE UPPER(ColumnName)='ORDERDOCUMENTNO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-26T07:11:40.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='OrderDocumentNo', Name='Auftragsnr.', Description='', Help=NULL WHERE AD_Element_ID=543353 AND IsCentrallyMaintained='Y'
;

-- 2019-09-26T07:11:40.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Auftragsnr.', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543353) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543353)
;

-- 2019-09-26T07:11:40.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Auftragsnr.', Name='Auftragsnr.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543353)
;

-- 2019-09-26T07:11:40.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Auftragsnr.', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543353
;

-- 2019-09-26T07:11:40.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Auftragsnr.', Description='', Help=NULL WHERE AD_Element_ID = 543353
;

-- 2019-09-26T07:11:40.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Auftragsnr.', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543353
;

