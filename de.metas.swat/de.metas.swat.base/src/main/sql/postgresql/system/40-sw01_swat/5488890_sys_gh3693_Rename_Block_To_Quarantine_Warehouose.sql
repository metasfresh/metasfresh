-- 2018-03-20T14:28:54.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsQuarantineWarehouse', Name='IsQuarantineWarehouse', PrintName='IsQuarantineWarehouse',Updated=TO_TIMESTAMP('2018-03-20 14:28:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543932
;

-- 2018-03-20T14:28:54.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsQuarantineWarehouse', Name='IsQuarantineWarehouse', Description=NULL, Help=NULL WHERE AD_Element_ID=543932
;

-- 2018-03-20T14:28:54.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsQuarantineWarehouse', Name='IsQuarantineWarehouse', Description=NULL, Help=NULL, AD_Element_ID=543932 WHERE UPPER(ColumnName)='ISQUARANTINEWAREHOUSE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-03-20T14:28:54.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsQuarantineWarehouse', Name='IsQuarantineWarehouse', Description=NULL, Help=NULL WHERE AD_Element_ID=543932 AND IsCentrallyMaintained='Y'
;

-- 2018-03-20T14:28:54.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='IsQuarantineWarehouse', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543932) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543932)
;

-- 2018-03-20T14:28:54.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='IsQuarantineWarehouse', Name='IsQuarantineWarehouse' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543932)
;

-- 2018-03-20T14:28:59.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-03-20 14:28:59','YYYY-MM-DD HH24:MI:SS'),Name='IsQuarantineWarehouse',PrintName='IsQuarantineWarehouse' WHERE AD_Element_ID=543932 AND AD_Language='de_CH'
;

-- 2018-03-20T14:28:59.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'de_CH') 
;

-- 2018-03-20T14:29:32.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-03-20 14:29:32','YYYY-MM-DD HH24:MI:SS'),Name='IsQuarantineWarehouse',PrintName='IsQuarantineWarehouse' WHERE AD_Element_ID=543932 AND AD_Language='nl_NL'
;

-- 2018-03-20T14:29:32.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'nl_NL') 
;

-- 2018-03-20T14:29:36.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-03-20 14:29:36','YYYY-MM-DD HH24:MI:SS'),Name='IsQuarantineWarehouse',PrintName='IsQuarantineWarehouse' WHERE AD_Element_ID=543932 AND AD_Language='en_US'
;

-- 2018-03-20T14:29:36.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'en_US') 
;

-- 2018-03-20T14:30:22.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Sperrlager', PrintName='Sperrlager',Updated=TO_TIMESTAMP('2018-03-20 14:30:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543932
;

-- 2018-03-20T14:30:22.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsQuarantineWarehouse', Name='Sperrlager', Description=NULL, Help=NULL WHERE AD_Element_ID=543932
;

-- 2018-03-20T14:30:22.870
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsQuarantineWarehouse', Name='Sperrlager', Description=NULL, Help=NULL, AD_Element_ID=543932 WHERE UPPER(ColumnName)='ISQUARANTINEWAREHOUSE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-03-20T14:30:22.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsQuarantineWarehouse', Name='Sperrlager', Description=NULL, Help=NULL WHERE AD_Element_ID=543932 AND IsCentrallyMaintained='Y'
;

-- 2018-03-20T14:30:22.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Sperrlager', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543932) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543932)
;

-- 2018-03-20T14:30:22.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Sperrlager', Name='Sperrlager' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543932)
;

-- 2018-03-20T14:30:34.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-03-20 14:30:34','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=543932 AND AD_Language='en_US'
;

-- 2018-03-20T14:30:34.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'en_US') 
;

