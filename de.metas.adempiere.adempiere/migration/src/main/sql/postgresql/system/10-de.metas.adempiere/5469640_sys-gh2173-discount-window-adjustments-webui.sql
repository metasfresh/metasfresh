-- 2017-08-19T14:17:17.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Description='', Name='Rabatt Konditionen',Updated=TO_TIMESTAMP('2017-08-19 14:17:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=406
;

-- 2017-08-19T15:24:04.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Anwenden auf',Updated=TO_TIMESTAMP('2017-08-19 15:24:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545458
;

-- 2017-08-19T15:24:27.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anwenden auf',Updated=TO_TIMESTAMP('2017-08-19 15:24:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=5241
;

-- 2017-08-19T15:25:05.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Anwenden auf', PrintName='Anwenden auf',Updated=TO_TIMESTAMP('2017-08-19 15:25:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1710
;

-- 2017-08-19T15:25:05.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='CumulativeLevel', Name='Anwenden auf', Description='Level for accumulative calculations', Help=NULL WHERE AD_Element_ID=1710
;

-- 2017-08-19T15:25:05.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CumulativeLevel', Name='Anwenden auf', Description='Level for accumulative calculations', Help=NULL, AD_Element_ID=1710 WHERE UPPER(ColumnName)='CUMULATIVELEVEL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-19T15:25:05.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CumulativeLevel', Name='Anwenden auf', Description='Level for accumulative calculations', Help=NULL WHERE AD_Element_ID=1710 AND IsCentrallyMaintained='Y'
;

-- 2017-08-19T15:25:05.429
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anwenden auf', Description='Level for accumulative calculations', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1710) AND IsCentrallyMaintained='Y'
;

-- 2017-08-19T15:25:05.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anwenden auf', Name='Anwenden auf' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1710)
;

