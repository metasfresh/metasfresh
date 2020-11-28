-- 2017-11-14T19:49:24.937
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsShipToday', Name='IsShipToday', PrintName='IsShipToday',Updated=TO_TIMESTAMP('2017-11-14 19:49:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543470
;

-- 2017-11-14T19:49:24.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsShipToday', Name='IsShipToday', Description=NULL, Help=NULL WHERE AD_Element_ID=543470
;

-- 2017-11-14T19:49:24.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsShipToday', Name='IsShipToday', Description=NULL, Help=NULL, AD_Element_ID=543470 WHERE UPPER(ColumnName)='ISSHIPTODAY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-11-14T19:49:24.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsShipToday', Name='IsShipToday', Description=NULL, Help=NULL WHERE AD_Element_ID=543470 AND IsCentrallyMaintained='Y'
;

-- 2017-11-14T19:49:24.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='IsShipToday', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543470) AND IsCentrallyMaintained='Y'
;

-- 2017-11-14T19:49:24.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='IsShipToday', Name='IsShipToday' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543470)
;

