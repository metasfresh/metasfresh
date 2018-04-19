-- 2018-04-17T16:00:26.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsExcludedFromSale', Name='Exclusion from sales documents', PrintName='Exclusion from  sales documents',Updated=TO_TIMESTAMP('2018-04-17 16:00:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543955
;

-- 2018-04-17T16:00:26.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsExcludedFromSale', Name='Exclusion from sales documents', Description=NULL, Help=NULL WHERE AD_Element_ID=543955
;

-- 2018-04-17T16:00:26.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExcludedFromSale', Name='Exclusion from sales documents', Description=NULL, Help=NULL, AD_Element_ID=543955 WHERE UPPER(ColumnName)='ISEXCLUDEDFROMSALE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-04-17T16:00:26.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExcludedFromSale', Name='Exclusion from sales documents', Description=NULL, Help=NULL WHERE AD_Element_ID=543955 AND IsCentrallyMaintained='Y'
;

-- 2018-04-17T16:00:26.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Exclusion from sales documents', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543955) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543955)
;

-- 2018-04-17T16:00:26.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Exclusion from  sales documents', Name='Exclusion from sales documents' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543955)
;

-- 2018-04-17T16:00:48.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-17 16:00:48','YYYY-MM-DD HH24:MI:SS'),Name='Exclusion from sales documents',PrintName='Exclusion from sales documents' WHERE AD_Element_ID=543955 AND AD_Language='de_CH'
;

-- 2018-04-17T16:00:48.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543955,'de_CH') 
;

-- 2018-04-17T16:00:52.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-17 16:00:52','YYYY-MM-DD HH24:MI:SS'),Name='Exclusion from sales documents',PrintName='Exclusion from sales documents' WHERE AD_Element_ID=543955 AND AD_Language='nl_NL'
;

-- 2018-04-17T16:00:52.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543955,'nl_NL') 
;

-- 2018-04-17T16:00:57.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-17 16:00:57','YYYY-MM-DD HH24:MI:SS'),Name='Exclusion from sales documents',PrintName='Exclusion from sales documents' WHERE AD_Element_ID=543955 AND AD_Language='en_US'
;

-- 2018-04-17T16:00:57.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543955,'en_US') 
;

