-- 2019-03-27T14:39:36.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsPriceCopied', Name='Price Copied', PrintName='Price Copied',Updated=TO_TIMESTAMP('2019-03-27 14:39:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576084
;

-- 2019-03-27T14:39:36.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsPriceCopied', Name='Price Copied', Description=NULL, Help=NULL WHERE AD_Element_ID=576084
;

-- 2019-03-27T14:39:36.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPriceCopied', Name='Price Copied', Description=NULL, Help=NULL, AD_Element_ID=576084 WHERE UPPER(ColumnName)='ISPRICECOPIED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-27T14:39:36.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPriceCopied', Name='Price Copied', Description=NULL, Help=NULL WHERE AD_Element_ID=576084 AND IsCentrallyMaintained='Y'
;

-- 2019-03-27T14:39:36.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Price Copied', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576084) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576084)
;

-- 2019-03-27T14:39:36.407
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Price Copied', Name='Price Copied' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576084)
;

-- 2019-03-27T14:39:36.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Price Copied', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576084
;

-- 2019-03-27T14:39:36.424
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Price Copied', Description=NULL, Help=NULL WHERE AD_Element_ID = 576084
;

-- 2019-03-27T14:39:36.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Price Copied', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576084
;


ALTER TABLE I_Pharma_Product RENAME COLUMN IsPriceCreated TO IsPriceCopied;

-- 2019-03-27T14:41:57.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_pharma_product','IsPriceCopied','CHAR(1)',null,'N')
;

-- 2019-03-27T14:41:57.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE I_Pharma_Product SET IsPriceCopied='N' WHERE IsPriceCopied IS NULL
;

