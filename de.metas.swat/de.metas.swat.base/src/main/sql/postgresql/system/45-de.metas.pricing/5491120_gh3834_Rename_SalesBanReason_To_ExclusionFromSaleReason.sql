-- 2018-04-17T16:09:19.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='ExclusionFromSalesReason',Updated=TO_TIMESTAMP('2018-04-17 16:09:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543956
;

-- 2018-04-17T16:09:19.140
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExclusionFromSalesReason', Name='Sales Ban Reason', Description=NULL, Help=NULL WHERE AD_Element_ID=543956
;

-- 2018-04-17T16:09:19.144
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExclusionFromSalesReason', Name='Sales Ban Reason', Description=NULL, Help=NULL, AD_Element_ID=543956 WHERE UPPER(ColumnName)='EXCLUSIONFROMSALESREASON' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-04-17T16:09:19.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExclusionFromSalesReason', Name='Sales Ban Reason', Description=NULL, Help=NULL WHERE AD_Element_ID=543956 AND IsCentrallyMaintained='Y'
;

-- 2018-04-17T16:09:49.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Exclusion From Sales Reason', PrintName='Exclusion From Sales Reason',Updated=TO_TIMESTAMP('2018-04-17 16:09:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543956
;

-- 2018-04-17T16:09:49.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExclusionFromSalesReason', Name='Exclusion From Sales Reason', Description=NULL, Help=NULL WHERE AD_Element_ID=543956
;

-- 2018-04-17T16:09:49.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExclusionFromSalesReason', Name='Exclusion From Sales Reason', Description=NULL, Help=NULL, AD_Element_ID=543956 WHERE UPPER(ColumnName)='EXCLUSIONFROMSALESREASON' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-04-17T16:09:49.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExclusionFromSalesReason', Name='Exclusion From Sales Reason', Description=NULL, Help=NULL WHERE AD_Element_ID=543956 AND IsCentrallyMaintained='Y'
;

-- 2018-04-17T16:09:49.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Exclusion From Sales Reason', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543956) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543956)
;

-- 2018-04-17T16:09:49.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Exclusion From Sales Reason', Name='Exclusion From Sales Reason' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543956)
;

-- 2018-04-17T16:09:59.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-17 16:09:59','YYYY-MM-DD HH24:MI:SS'),Name='Exclusion From Sales Reason' WHERE AD_Element_ID=543956 AND AD_Language='de_CH'
;

-- 2018-04-17T16:09:59.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543956,'de_CH') 
;

-- 2018-04-17T16:10:03.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-17 16:10:03','YYYY-MM-DD HH24:MI:SS'),PrintName='Exclusion From Sales Reason' WHERE AD_Element_ID=543956 AND AD_Language='de_CH'
;

-- 2018-04-17T16:10:03.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543956,'de_CH') 
;

-- 2018-04-17T16:10:11.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-17 16:10:11','YYYY-MM-DD HH24:MI:SS'),Name='Exclusion From Sales Reason',PrintName='Exclusion From Sales Reason' WHERE AD_Element_ID=543956 AND AD_Language='nl_NL'
;

-- 2018-04-17T16:10:11.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543956,'nl_NL') 
;

-- 2018-04-17T16:10:15.003
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-17 16:10:15','YYYY-MM-DD HH24:MI:SS'),Name='Exclusion From Sales Reason',PrintName='Exclusion From Sales Reason' WHERE AD_Element_ID=543956 AND AD_Language='en_US'
;

-- 2018-04-17T16:10:15.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543956,'en_US') 
;

-- 2018-04-17T16:20:10.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='ExclusionFromSaleReason', Name='Exclusion From Sale Reason', PrintName='Exclusion From Sale Reason',Updated=TO_TIMESTAMP('2018-04-17 16:20:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543956
;

-- 2018-04-17T16:20:10.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExclusionFromSaleReason', Name='Exclusion From Sale Reason', Description=NULL, Help=NULL WHERE AD_Element_ID=543956
;

-- 2018-04-17T16:20:10.670
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExclusionFromSaleReason', Name='Exclusion From Sale Reason', Description=NULL, Help=NULL, AD_Element_ID=543956 WHERE UPPER(ColumnName)='EXCLUSIONFROMSALEREASON' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-04-17T16:20:10.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExclusionFromSaleReason', Name='Exclusion From Sale Reason', Description=NULL, Help=NULL WHERE AD_Element_ID=543956 AND IsCentrallyMaintained='Y'
;

-- 2018-04-17T16:20:10.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Exclusion From Sale Reason', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543956) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543956)
;

-- 2018-04-17T16:20:10.683
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Exclusion From Sale Reason', Name='Exclusion From Sale Reason' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543956)
;

-- 2018-04-17T16:20:15.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-17 16:20:15','YYYY-MM-DD HH24:MI:SS'),Name='Exclusion From Sale Reason',PrintName='Exclusion From Sale Reason' WHERE AD_Element_ID=543956 AND AD_Language='de_CH'
;

-- 2018-04-17T16:20:15.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543956,'de_CH') 
;

-- 2018-04-17T16:20:20.301
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-17 16:20:20','YYYY-MM-DD HH24:MI:SS'),Name='Exclusion From Sale Reason',PrintName='Exclusion From Sale Reason' WHERE AD_Element_ID=543956 AND AD_Language='nl_NL'
;

-- 2018-04-17T16:20:20.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543956,'nl_NL') 
;

-- 2018-04-17T16:20:25.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-17 16:20:25','YYYY-MM-DD HH24:MI:SS'),Name='Exclusion From Sale Reason',PrintName='Exclusion From Sale Reason' WHERE AD_Element_ID=543956 AND AD_Language='en_US'
;

-- 2018-04-17T16:20:25.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543956,'en_US') 
;

