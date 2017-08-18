-- 2017-08-18T12:46:23.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-08-18 12:46:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540204
;

-- 2017-08-18T12:47:03.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Read Only',Updated=TO_TIMESTAMP('2017-08-18 12:47:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542522
;

-- 2017-08-18T12:47:16.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Transferieren wenn null',Updated=TO_TIMESTAMP('2017-08-18 12:47:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542523
;

-- 2017-08-18T12:47:36.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Transferieren wenn null',Updated=TO_TIMESTAMP('2017-08-18 12:47:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557503
;

-- 2017-08-18T12:47:44.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Read Only',Updated=TO_TIMESTAMP('2017-08-18 12:47:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557412
;

-- 2017-08-18T12:48:06.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Read Only', PrintName='Read Only',Updated=TO_TIMESTAMP('2017-08-18 12:48:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543239
;

-- 2017-08-18T12:48:06.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsReadOnlyValues', Name='Read Only', Description=NULL, Help=NULL WHERE AD_Element_ID=543239
;

-- 2017-08-18T12:48:06.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsReadOnlyValues', Name='Read Only', Description=NULL, Help=NULL, AD_Element_ID=543239 WHERE UPPER(ColumnName)='ISREADONLYVALUES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-18T12:48:06.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsReadOnlyValues', Name='Read Only', Description=NULL, Help=NULL WHERE AD_Element_ID=543239 AND IsCentrallyMaintained='Y'
;

-- 2017-08-18T12:48:06.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Read Only', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543239) AND IsCentrallyMaintained='Y'
;

-- 2017-08-18T12:48:06.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Read Only', Name='Read Only' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543239)
;

-- 2017-08-18T12:48:17.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-08-18 12:48:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Read Only',PrintName='Read Only' WHERE AD_Element_ID=543239 AND AD_Language='en_US'
;

-- 2017-08-18T12:48:17.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543239,'en_US') 
;

-- 2017-08-18T12:48:50.390
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Help='', Name='Transferieren wenn null', PrintName='Transferieren wenn null',Updated=TO_TIMESTAMP('2017-08-18 12:48:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543272
;

-- 2017-08-18T12:48:50.391
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsTransferWhenNull', Name='Transferieren wenn null', Description=NULL, Help='' WHERE AD_Element_ID=543272
;

-- 2017-08-18T12:48:50.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsTransferWhenNull', Name='Transferieren wenn null', Description=NULL, Help='', AD_Element_ID=543272 WHERE UPPER(ColumnName)='ISTRANSFERWHENNULL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-08-18T12:48:50.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsTransferWhenNull', Name='Transferieren wenn null', Description=NULL, Help='' WHERE AD_Element_ID=543272 AND IsCentrallyMaintained='Y'
;

-- 2017-08-18T12:48:50.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Transferieren wenn null', Description=NULL, Help='' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543272) AND IsCentrallyMaintained='Y'
;

-- 2017-08-18T12:48:50.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Transferieren wenn null', Name='Transferieren wenn null' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543272)
;

