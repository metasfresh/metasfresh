-- 2019-03-19T16:22:31.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Unterregister', PrintName='Unterregister',Updated=TO_TIMESTAMP('2019-03-19 16:22:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576037 AND AD_Language='de_CH'
;

-- 2019-03-19T16:22:31.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576037,'de_CH') 
;

-- 2019-03-19T16:22:35.474
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Unterregister', PrintName='Unterregister',Updated=TO_TIMESTAMP('2019-03-19 16:22:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576037 AND AD_Language='de_DE'
;

-- 2019-03-19T16:22:35.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576037,'de_DE') 
;

-- 2019-03-19T16:22:35.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576037,'de_DE') 
;

-- 2019-03-19T16:22:35.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='DataEntry_SubGroup_ID', Name='Unterregister', Description=NULL, Help=NULL WHERE AD_Element_ID=576037
;

-- 2019-03-19T16:22:35.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DataEntry_SubGroup_ID', Name='Unterregister', Description=NULL, Help=NULL, AD_Element_ID=576037 WHERE UPPER(ColumnName)='DATAENTRY_SUBGROUP_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-03-19T16:22:35.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='DataEntry_SubGroup_ID', Name='Unterregister', Description=NULL, Help=NULL WHERE AD_Element_ID=576037 AND IsCentrallyMaintained='Y'
;

-- 2019-03-19T16:22:35.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Unterregister', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576037) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576037)
;

-- 2019-03-19T16:22:35.511
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Unterregister', Name='Unterregister' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576037)
;

-- 2019-03-19T16:22:35.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Unterregister', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576037
;

-- 2019-03-19T16:22:35.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Unterregister', Description=NULL, Help=NULL WHERE AD_Element_ID = 576037
;

-- 2019-03-19T16:22:35.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Unterregister', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576037
;

-- 2019-03-19T16:22:42.015
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Subtab', PrintName='Subtab',Updated=TO_TIMESTAMP('2019-03-19 16:22:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576037 AND AD_Language='en_US'
;

-- 2019-03-19T16:22:42.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576037,'en_US') 
;

-- 2019-03-19T16:22:51.875
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Subtab', PrintName='Subtab',Updated=TO_TIMESTAMP('2019-03-19 16:22:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576037 AND AD_Language='nl_NL'
;

-- 2019-03-19T16:22:51.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576037,'nl_NL') 
;

-- 2019-03-19T16:23:09.419
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Register', PrintName='Register',Updated=TO_TIMESTAMP('2019-03-19 16:23:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576047 AND AD_Language='de_CH'
;

-- 2019-03-19T16:23:09.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576047,'de_CH') 
;

-- 2019-03-19T16:23:13.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Register', PrintName='Register',Updated=TO_TIMESTAMP('2019-03-19 16:23:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576047 AND AD_Language='de_DE'
;

-- 2019-03-19T16:23:13.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576047,'de_DE') 
;

-- 2019-03-19T16:23:13.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576047,'de_DE') 
;

-- 2019-03-19T16:23:13.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Register', Description=NULL, Help=NULL WHERE AD_Element_ID=576047
;

-- 2019-03-19T16:23:13.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Register', Description=NULL, Help=NULL WHERE AD_Element_ID=576047 AND IsCentrallyMaintained='Y'
;

-- 2019-03-19T16:23:13.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Register', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576047) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576047)
;

-- 2019-03-19T16:23:13.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Register', Name='Register' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576047)
;

-- 2019-03-19T16:23:13.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Register', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576047
;

-- 2019-03-19T16:23:13.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Register', Description=NULL, Help=NULL WHERE AD_Element_ID = 576047
;

-- 2019-03-19T16:23:13.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Register', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576047
;

-- 2019-03-19T16:23:21.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Tab', PrintName='Tab',Updated=TO_TIMESTAMP('2019-03-19 16:23:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576047 AND AD_Language='en_US'
;

-- 2019-03-19T16:23:21.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576047,'en_US') 
;

-- 2019-03-19T16:23:27.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Tab', PrintName='Tab',Updated=TO_TIMESTAMP('2019-03-19 16:23:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576047 AND AD_Language='nl_NL'
;

-- 2019-03-19T16:23:27.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576047,'nl_NL') 
;

-- 2019-03-19T16:24:28.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Feldgruppe', PrintName='Feldgruppe',Updated=TO_TIMESTAMP('2019-03-19 16:24:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576136 AND AD_Language='de_CH'
;

-- 2019-03-19T16:24:28.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576136,'de_CH') 
;

-- 2019-03-19T16:24:42.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Gruppierung/Sektion von Feldern innerhalb des selben Unterregisters', Name='Feldgruppe', PrintName='Feldgruppe',Updated=TO_TIMESTAMP('2019-03-19 16:24:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576136 AND AD_Language='de_DE'
;

-- 2019-03-19T16:24:42.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576136,'de_DE') 
;

-- 2019-03-19T16:24:42.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576136,'de_DE') 
;

-- 2019-03-19T16:24:42.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Feldgruppe', Description='Gruppierung/Sektion von Feldern innerhalb des selben Unterregisters', Help=NULL WHERE AD_Element_ID=576136
;

-- 2019-03-19T16:24:42.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Feldgruppe', Description='Gruppierung/Sektion von Feldern innerhalb des selben Unterregisters', Help=NULL WHERE AD_Element_ID=576136 AND IsCentrallyMaintained='Y'
;

-- 2019-03-19T16:24:42.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Feldgruppe', Description='Gruppierung/Sektion von Feldern innerhalb des selben Unterregisters', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576136) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576136)
;

-- 2019-03-19T16:24:42.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Feldgruppe', Name='Feldgruppe' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576136)
;

-- 2019-03-19T16:24:42.286
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Feldgruppe', Description='Gruppierung/Sektion von Feldern innerhalb des selben Unterregisters', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576136
;

-- 2019-03-19T16:24:42.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Feldgruppe', Description='Gruppierung/Sektion von Feldern innerhalb des selben Unterregisters', Help=NULL WHERE AD_Element_ID = 576136
;

-- 2019-03-19T16:24:42.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Feldgruppe', Description = 'Gruppierung/Sektion von Feldern innerhalb des selben Unterregisters', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576136
;

-- 2019-03-19T16:24:44.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Gruppierung/Sektion von Feldern innerhalb des selben Unterregisters',Updated=TO_TIMESTAMP('2019-03-19 16:24:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576136 AND AD_Language='de_CH'
;

-- 2019-03-19T16:24:44.858
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576136,'de_CH') 
;

-- 2019-03-19T16:25:10.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Grouping/section of fields within the same subtab', Name='Field group', PrintName='Field group',Updated=TO_TIMESTAMP('2019-03-19 16:25:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576136 AND AD_Language='en_US'
;

-- 2019-03-19T16:25:10.222
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576136,'en_US') 
;

----

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

