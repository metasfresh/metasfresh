-- 2017-08-17T17:06:59.844
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='', Help='', Name='Dokument Basis Typ', PrintName='Dokument Basis Typ',Updated=TO_TIMESTAMP('2017-08-17 17:06:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=865
;

-- 2017-08-17T17:06:59.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DocBaseType', Name='Dokument Basis Typ', Description='', Help='' WHERE AD_Element_ID=865
;

-- 2017-08-17T17:06:59.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DocBaseType', Name='Dokument Basis Typ', Description='', Help='', AD_Element_ID=865 WHERE UPPER(ColumnName)='DOCBASETYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-17T17:06:59.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DocBaseType', Name='Dokument Basis Typ', Description='', Help='' WHERE AD_Element_ID=865 AND IsCentrallyMaintained='Y'
;

-- 2017-08-17T17:06:59.922
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Dokument Basis Typ', Description='', Help='' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=865) AND IsCentrallyMaintained='Y'
;

-- 2017-08-17T17:06:59.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Dokument Basis Typ', Name='Dokument Basis Typ' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=865)
;

-- 2017-08-17T17:07:13.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-17 17:07:13','YYYY-MM-DD HH24:MI:SS'),Name='Document Base Type',PrintName='Doc Base Type' WHERE AD_Element_ID=865 AND AD_Language='en_US'
;

-- 2017-08-17T17:07:13.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(865,'en_US') 
;

-- 2017-08-17T17:07:45.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='CC Pfad', PrintName='CC Pfad',Updated=TO_TIMESTAMP('2017-08-17 17:07:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542348
;

-- 2017-08-17T17:07:45.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='CCPath', Name='CC Pfad', Description=NULL, Help=NULL WHERE AD_Element_ID=542348
;

-- 2017-08-17T17:07:45.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CCPath', Name='CC Pfad', Description=NULL, Help=NULL, AD_Element_ID=542348 WHERE UPPER(ColumnName)='CCPATH' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-17T17:07:45.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='CCPath', Name='CC Pfad', Description=NULL, Help=NULL WHERE AD_Element_ID=542348 AND IsCentrallyMaintained='Y'
;

-- 2017-08-17T17:07:45.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='CC Pfad', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542348) AND IsCentrallyMaintained='Y'
;

-- 2017-08-17T17:07:45.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='CC Pfad', Name='CC Pfad' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542348)
;

