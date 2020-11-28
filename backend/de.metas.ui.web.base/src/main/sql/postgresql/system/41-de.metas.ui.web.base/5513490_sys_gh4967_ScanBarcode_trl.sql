-- 2019-02-21T14:11:19.586
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Scan Barcode',Updated=TO_TIMESTAMP('2019-02-21 14:11:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541011
;

-- 2019-02-21T14:11:19.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Scan Barcode',Updated=TO_TIMESTAMP('2019-02-21 14:11:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541142
;

-- 2019-02-21T14:11:26.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-21 14:11:26','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Scan Barcode' WHERE AD_Process_ID=541011 AND AD_Language='de_CH'
;

-- 2019-02-21T14:11:31.860
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-21 14:11:31','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Scan Barcode' WHERE AD_Process_ID=541011 AND AD_Language='de_DE'
;

-- 2019-02-21T14:11:36.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-21 14:11:36','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Scan Barcode' WHERE AD_Process_ID=541011 AND AD_Language='en_US'
;

-- 2019-02-21T14:11:41.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-21 14:11:41','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Scan Barcode' WHERE AD_Process_ID=541011 AND AD_Language='nl_NL'
;

-- 2019-02-21T14:17:11.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-21 14:17:11','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Scan Barcode',PrintName='Scan Barcode' WHERE AD_Element_ID=575799 AND AD_Language='de_CH'
;

-- 2019-02-21T14:17:11.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575799,'de_CH') 
;

-- 2019-02-21T14:17:17.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-21 14:17:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Scan Barcode',PrintName='Scan Barcode' WHERE AD_Element_ID=575799 AND AD_Language='en_US'
;

-- 2019-02-21T14:17:17.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575799,'en_US') 
;

-- 2019-02-21T14:17:23.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-21 14:17:23','YYYY-MM-DD HH24:MI:SS'),Name='Scan Barcode',PrintName='Scan Barcode' WHERE AD_Element_ID=575799 AND AD_Language='nl_NL'
;

-- 2019-02-21T14:17:23.159
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575799,'nl_NL') 
;

-- 2019-02-21T14:17:31.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-02-21 14:17:31','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Scan Barcode',PrintName='Scan Barcode' WHERE AD_Element_ID=575799 AND AD_Language='de_DE'
;

-- 2019-02-21T14:17:31.585
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575799,'de_DE') 
;

-- 2019-02-21T14:17:31.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575799,'de_DE') 
;

-- 2019-02-21T14:17:31.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Scan Barcode', Description=NULL, Help=NULL WHERE AD_Element_ID=575799
;

-- 2019-02-21T14:17:31.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Scan Barcode', Description=NULL, Help=NULL WHERE AD_Element_ID=575799 AND IsCentrallyMaintained='Y'
;

-- 2019-02-21T14:17:31.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Scan Barcode', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575799) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575799)
;

-- 2019-02-21T14:17:31.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Scan Barcode', Name='Scan Barcode' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575799)
;

-- 2019-02-21T14:17:31.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Scan Barcode', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575799
;

-- 2019-02-21T14:17:31.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Scan Barcode', Description=NULL, Help=NULL WHERE AD_Element_ID = 575799
;

-- 2019-02-21T14:17:31.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Scan Barcode', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575799
;

