-- 2017-08-19T10:48:11.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Verpackungsmaterial', PrintName='Verpackungsmaterial',Updated=TO_TIMESTAMP('2017-08-19 10:48:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542246
;

-- 2017-08-19T10:48:11.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsPackagingMaterial', Name='Verpackungsmaterial', Description=NULL, Help=NULL WHERE AD_Element_ID=542246
;

-- 2017-08-19T10:48:11.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPackagingMaterial', Name='Verpackungsmaterial', Description=NULL, Help=NULL, AD_Element_ID=542246 WHERE UPPER(ColumnName)='ISPACKAGINGMATERIAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-19T10:48:11.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPackagingMaterial', Name='Verpackungsmaterial', Description=NULL, Help=NULL WHERE AD_Element_ID=542246 AND IsCentrallyMaintained='Y'
;

-- 2017-08-19T10:48:11.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verpackungsmaterial', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542246) AND IsCentrallyMaintained='Y'
;

-- 2017-08-19T10:48:11.930
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Verpackungsmaterial', Name='Verpackungsmaterial' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542246)
;

-- 2017-08-19T10:50:28.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-08-19 10:50:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540604
;

