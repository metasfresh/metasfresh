-- 2019-03-20T12:16:57.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Erweiterte Dateneingabe - Register', PrintName='Erweiterte Dateneingabe - Register',Updated=TO_TIMESTAMP('2019-03-20 12:16:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576046 AND AD_Language='de_CH'
;

-- 2019-03-20T12:16:57.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576046,'de_CH') 
;

-- 2019-03-20T12:17:02.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Erweiterte Dateneingabe - Register', PrintName='Erweiterte Dateneingabe - Register',Updated=TO_TIMESTAMP('2019-03-20 12:17:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576046 AND AD_Language='de_DE'
;

-- 2019-03-20T12:17:02.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576046,'de_DE') 
;

-- 2019-03-20T12:17:02.701
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576046,'de_DE') 
;

-- 2019-03-20T12:17:02.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Erweiterte Dateneingabe - Register', Description=NULL, Help=NULL WHERE AD_Element_ID=576046
;

-- 2019-03-20T12:17:02.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Erweiterte Dateneingabe - Register', Description=NULL, Help=NULL WHERE AD_Element_ID=576046 AND IsCentrallyMaintained='Y'
;

-- 2019-03-20T12:17:02.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Erweiterte Dateneingabe - Register', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576046) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576046)
;

-- 2019-03-20T12:17:02.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Erweiterte Dateneingabe - Register', Name='Erweiterte Dateneingabe - Register' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576046)
;

-- 2019-03-20T12:17:02.721
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Erweiterte Dateneingabe - Register', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576046
;

-- 2019-03-20T12:17:02.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Erweiterte Dateneingabe - Register', Description=NULL, Help=NULL WHERE AD_Element_ID = 576046
;

-- 2019-03-20T12:17:02.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Erweiterte Dateneingabe - Register', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576046
;

-- 2019-03-20T12:17:10.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Custom data entry - tab', PrintName='Custom data entry - tab',Updated=TO_TIMESTAMP('2019-03-20 12:17:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576046 AND AD_Language='en_US'
;

-- 2019-03-20T12:17:10.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576046,'en_US') 
;

-- 2019-03-20T12:34:14.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Descriptionn',Updated=TO_TIMESTAMP('2019-03-20 12:34:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=275 AND AD_Language='en_US'
;

-- 2019-03-20T12:34:14.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(275,'en_US') 
;

-- 2019-03-20T12:34:28.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Description',Updated=TO_TIMESTAMP('2019-03-20 12:34:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=275 AND AD_Language='en_US'
;

-- 2019-03-20T12:34:28.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(275,'en_US') 
;

-- 2019-03-20T12:39:22.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Erweiterte Dateneingabe - Feldgruppe', PrintName='Erweiterte Dateneingabe - Feldgruppe',Updated=TO_TIMESTAMP('2019-03-20 12:39:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576162 AND AD_Language='de_CH'
;

-- 2019-03-20T12:39:22.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576162,'de_CH') 
;

-- 2019-03-20T12:39:33.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Erweiterte Dateneingabe - Feldgruppe', PrintName='Erweiterte Dateneingabe - Feldgruppe',Updated=TO_TIMESTAMP('2019-03-20 12:39:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576162 AND AD_Language='de_DE'
;

-- 2019-03-20T12:39:33.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576162,'de_DE') 
;

-- 2019-03-20T12:39:33.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576162,'de_DE') 
;

-- 2019-03-20T12:39:33.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Erweiterte Dateneingabe - Feldgruppe', Description=NULL, Help=NULL WHERE AD_Element_ID=576162
;

-- 2019-03-20T12:39:33.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Erweiterte Dateneingabe - Feldgruppe', Description=NULL, Help=NULL WHERE AD_Element_ID=576162 AND IsCentrallyMaintained='Y'
;

-- 2019-03-20T12:39:33.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Erweiterte Dateneingabe - Feldgruppe', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576162) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576162)
;

-- 2019-03-20T12:39:33.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Erweiterte Dateneingabe - Feldgruppe', Name='Erweiterte Dateneingabe - Feldgruppe' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576162)
;

-- 2019-03-20T12:39:33.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Erweiterte Dateneingabe - Feldgruppe', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576162
;

-- 2019-03-20T12:39:33.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Erweiterte Dateneingabe - Feldgruppe', Description=NULL, Help=NULL WHERE AD_Element_ID = 576162
;

-- 2019-03-20T12:39:33.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Erweiterte Dateneingabe - Feldgruppe', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576162
;

-- 2019-03-20T12:39:47.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Custom data entry - field group', PrintName='Custom data entry - field group',Updated=TO_TIMESTAMP('2019-03-20 12:39:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576162 AND AD_Language='en_US'
;

-- 2019-03-20T12:39:47.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576162,'en_US') 
;

-- 2019-03-20T12:40:47.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Gruppenname', PrintName='Gruppenname',Updated=TO_TIMESTAMP('2019-03-20 12:40:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576134 AND AD_Language='de_CH'
;

-- 2019-03-20T12:40:47.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576134,'de_CH') 
;

-- 2019-03-20T12:40:51.432
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Gruppenname', PrintName='Gruppenname',Updated=TO_TIMESTAMP('2019-03-20 12:40:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576134 AND AD_Language='de_DE'
;

-- 2019-03-20T12:40:51.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576134,'de_DE') 
;

-- 2019-03-20T12:40:51.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576134,'de_DE') 
;

-- 2019-03-20T12:40:51.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SectionName', Name='Gruppenname', Description='', Help=NULL WHERE AD_Element_ID=576134
;

-- 2019-03-20T12:40:51.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SectionName', Name='Gruppenname', Description='', Help=NULL, AD_Element_ID=576134 WHERE UPPER(ColumnName)='SECTIONNAME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-20T12:40:51.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SectionName', Name='Gruppenname', Description='', Help=NULL WHERE AD_Element_ID=576134 AND IsCentrallyMaintained='Y'
;

-- 2019-03-20T12:40:51.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Gruppenname', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576134) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576134)
;

-- 2019-03-20T12:40:51.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Gruppenname', Name='Gruppenname' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576134)
;

-- 2019-03-20T12:40:51.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Gruppenname', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576134
;

-- 2019-03-20T12:40:51.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Gruppenname', Description='', Help=NULL WHERE AD_Element_ID = 576134
;

-- 2019-03-20T12:40:51.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Gruppenname', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576134
;

-- 2019-03-20T12:41:05.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Field group name', PrintName='Field group name',Updated=TO_TIMESTAMP('2019-03-20 12:41:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576134 AND AD_Language='en_US'
;

-- 2019-03-20T12:41:05.391
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576134,'en_US') 
;

-- 2019-03-20T12:41:14.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Gruppenname', PrintName='Gruppenname',Updated=TO_TIMESTAMP('2019-03-20 12:41:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576134 AND AD_Language='nl_NL'
;

-- 2019-03-20T12:41:14.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576134,'nl_NL') 
;

