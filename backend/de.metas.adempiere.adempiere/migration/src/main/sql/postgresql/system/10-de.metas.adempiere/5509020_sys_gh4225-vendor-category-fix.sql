-- 2019-01-08T07:08:19.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=86,Updated=TO_TIMESTAMP('2019-01-08 07:08:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552280
;

-- 2019-01-08T07:12:21.353
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-08 07:12:21','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Lieferanten Kategorie',PrintName='Lieferanten Kategorie',Description='Lieferanten Kategorie',Help='' WHERE AD_Element_ID=622 AND AD_Language='fr_CH'
;

-- 2019-01-08T07:12:21.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(622,'fr_CH') 
;

-- 2019-01-08T07:12:33.436
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-08 07:12:33','YYYY-MM-DD HH24:MI:SS'),Name='Lieferanten Kategorie',PrintName='Lieferanten Kategorie',Description='Lieferanten Kategorie',Help='' WHERE AD_Element_ID=622 AND AD_Language='it_CH'
;

-- 2019-01-08T07:12:33.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(622,'it_CH') 
;

-- 2019-01-08T07:12:58.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-08 07:12:58','YYYY-MM-DD HH24:MI:SS'),Name='Vendor Category',PrintName='Vendor Category',Description='Vendor Category',Help='' WHERE AD_Element_ID=622 AND AD_Language='en_GB'
;

-- 2019-01-08T07:12:58.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(622,'en_GB') 
;

-- 2019-01-08T07:13:20.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-08 07:13:20','YYYY-MM-DD HH24:MI:SS'),Name='Lieferanten Kategorie',PrintName='Lieferanten Kategorie',Description='Lieferanten Kategorie',Help='' WHERE AD_Element_ID=622 AND AD_Language='de_CH'
;

-- 2019-01-08T07:13:20.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(622,'de_CH') 
;

-- 2019-01-08T07:13:43.376
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-08 07:13:43','YYYY-MM-DD HH24:MI:SS'),Name='Vendor Category',PrintName='Vendor Category',Description='Vendor Category',Help='' WHERE AD_Element_ID=622 AND AD_Language='en_US'
;

-- 2019-01-08T07:13:43.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(622,'en_US') 
;

-- 2019-01-08T07:14:14.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-08 07:14:14','YYYY-MM-DD HH24:MI:SS'),Name='Lieferanten Kategorie',PrintName='Lieferanten Kategorie',Description='Lieferanten Kategorie',Help='' WHERE AD_Element_ID=622 AND AD_Language='nl_NL'
;

-- 2019-01-08T07:14:14.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(622,'nl_NL') 
;

-- 2019-01-08T07:14:24.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-08 07:14:24','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Lieferanten Kategorie',PrintName='Lieferanten Kategorie',Description='Lieferanten Kategorie',Help='' WHERE AD_Element_ID=622 AND AD_Language='de_DE'
;

-- 2019-01-08T07:14:24.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(622,'de_DE') 
;

-- 2019-01-08T07:14:24.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(622,'de_DE') 
;

-- 2019-01-08T07:14:24.229
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='VendorCategory', Name='Lieferanten Kategorie', Description='Lieferanten Kategorie', Help='' WHERE AD_Element_ID=622
;

-- 2019-01-08T07:14:24.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='VendorCategory', Name='Lieferanten Kategorie', Description='Lieferanten Kategorie', Help='', AD_Element_ID=622 WHERE UPPER(ColumnName)='VENDORCATEGORY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-01-08T07:14:24.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='VendorCategory', Name='Lieferanten Kategorie', Description='Lieferanten Kategorie', Help='' WHERE AD_Element_ID=622 AND IsCentrallyMaintained='Y'
;

-- 2019-01-08T07:14:24.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferanten Kategorie', Description='Lieferanten Kategorie', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=622) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 622)
;

-- 2019-01-08T07:14:24.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferanten Kategorie', Name='Lieferanten Kategorie' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=622)
;

-- 2019-01-08T07:14:24.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lieferanten Kategorie', Description='Lieferanten Kategorie', Help='', CommitWarning = NULL WHERE AD_Element_ID = 622
;

-- 2019-01-08T07:14:24.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Lieferanten Kategorie', Description='Lieferanten Kategorie', Help='' WHERE AD_Element_ID = 622
;

-- 2019-01-08T07:14:24.266
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Lieferanten Kategorie', Description='Lieferanten Kategorie', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 622
;

