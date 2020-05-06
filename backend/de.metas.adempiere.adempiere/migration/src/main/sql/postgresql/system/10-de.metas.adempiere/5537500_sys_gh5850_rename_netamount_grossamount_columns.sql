-- 2019-12-02T07:47:59.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Nettowert',Updated=TO_TIMESTAMP('2019-12-02 09:47:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564374
;

-- 2019-12-02T07:59:09.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-12-02 09:59:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564374
;

-- 2019-12-02T07:59:09.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-12-02 09:59:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564375
;

-- 2019-12-02T08:25:53.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2019-12-02 10:25:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577396
;

-- 2019-12-02T08:26:30.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Nettowert', PrintName='Nettowert',Updated=TO_TIMESTAMP('2019-12-02 10:26:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577396 AND AD_Language='de_DE'
;

-- 2019-12-02T08:26:31.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577396,'de_DE') 
;

-- 2019-12-02T08:26:31.021Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577396,'de_DE') 
;

-- 2019-12-02T08:26:31.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='netamount', Name='Nettowert', Description=NULL, Help=NULL WHERE AD_Element_ID=577396
;

-- 2019-12-02T08:26:31.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='netamount', Name='Nettowert', Description=NULL, Help=NULL, AD_Element_ID=577396 WHERE UPPER(ColumnName)='NETAMOUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-02T08:26:31.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='netamount', Name='Nettowert', Description=NULL, Help=NULL WHERE AD_Element_ID=577396 AND IsCentrallyMaintained='Y'
;

-- 2019-12-02T08:26:31.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Nettowert', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577396) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577396)
;

-- 2019-12-02T08:26:31.042Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Nettowert', Name='Nettowert' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577396)
;

-- 2019-12-02T08:26:31.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Nettowert', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577396
;

-- 2019-12-02T08:26:31.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Nettowert', Description=NULL, Help=NULL WHERE AD_Element_ID = 577396
;

-- 2019-12-02T08:26:31.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Nettowert', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577396
;

-- 2019-12-02T09:36:50.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Net amount', PrintName='Net amount',Updated=TO_TIMESTAMP('2019-12-02 11:36:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577396 AND AD_Language='en_US'
;

-- 2019-12-02T09:36:50.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577396,'en_US') 
;

-- 2019-12-02T09:38:19.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Nettowert', PrintName='Nettowert',Updated=TO_TIMESTAMP('2019-12-02 11:38:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577396 AND AD_Language='de_CH'
;

-- 2019-12-02T09:38:19.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577396,'de_CH') 
;

-- 2019-12-02T09:38:56.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2019-12-02 11:38:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577397
;

-- 2019-12-02T09:39:29.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Gross amount', PrintName='Gross amount',Updated=TO_TIMESTAMP('2019-12-02 11:39:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577397 AND AD_Language='en_US'
;

-- 2019-12-02T09:39:29.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577397,'en_US') 
;

-- 2019-12-02T09:39:48.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Bruttowert', PrintName='Bruttowert',Updated=TO_TIMESTAMP('2019-12-02 11:39:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577397 AND AD_Language='de_DE'
;

-- 2019-12-02T09:39:48.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577397,'de_DE') 
;

-- 2019-12-02T09:39:48.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577397,'de_DE') 
;

-- 2019-12-02T09:39:48.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='grossamount', Name='Bruttowert', Description=NULL, Help=NULL WHERE AD_Element_ID=577397
;

-- 2019-12-02T09:39:48.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='grossamount', Name='Bruttowert', Description=NULL, Help=NULL, AD_Element_ID=577397 WHERE UPPER(ColumnName)='GROSSAMOUNT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-02T09:39:48.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='grossamount', Name='Bruttowert', Description=NULL, Help=NULL WHERE AD_Element_ID=577397 AND IsCentrallyMaintained='Y'
;

-- 2019-12-02T09:39:48.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Bruttowert', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577397) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577397)
;

-- 2019-12-02T09:39:48.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Bruttowert', Name='Bruttowert' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577397)
;

-- 2019-12-02T09:39:48.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Bruttowert', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577397
;

-- 2019-12-02T09:39:48.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Bruttowert', Description=NULL, Help=NULL WHERE AD_Element_ID = 577397
;

-- 2019-12-02T09:39:48.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Bruttowert', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577397
;

-- 2019-12-02T09:40:02.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Bruttowert', PrintName='Bruttowert',Updated=TO_TIMESTAMP('2019-12-02 11:40:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577397 AND AD_Language='de_CH'
;

-- 2019-12-02T09:40:02.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577397,'de_CH') 
;

