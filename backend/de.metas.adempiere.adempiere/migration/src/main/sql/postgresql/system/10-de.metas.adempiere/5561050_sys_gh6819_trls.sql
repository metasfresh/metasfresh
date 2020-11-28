-- 2020-06-11T09:07:24.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Verbuchungsprobleme',Updated=TO_TIMESTAMP('2020-06-11 12:07:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542152
;

-- 2020-06-11T09:07:29.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Verbuchungsprobleme',Updated=TO_TIMESTAMP('2020-06-11 12:07:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542152
;

-- 2020-06-11T09:07:33.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-06-11 12:07:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542152
;

-- 2020-06-11T09:07:47.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Andere Probleme',Updated=TO_TIMESTAMP('2020-06-11 12:07:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542153
;

-- 2020-06-11T09:07:50.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Andere Probleme',Updated=TO_TIMESTAMP('2020-06-11 12:07:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542153
;

-- 2020-06-11T09:07:53.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-06-11 12:07:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542153
;

-- 2020-06-11T09:08:15.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Problemkategorie', PrintName='Problemkategorie',Updated=TO_TIMESTAMP('2020-06-11 12:08:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577756 AND AD_Language='de_CH'
;

-- 2020-06-11T09:08:15.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577756,'de_CH') 
;

-- 2020-06-11T09:08:17.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Problemkategorie',Updated=TO_TIMESTAMP('2020-06-11 12:08:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577756 AND AD_Language='de_DE'
;

-- 2020-06-11T09:08:17.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577756,'de_DE') 
;

-- 2020-06-11T09:08:17.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577756,'de_DE') 
;

-- 2020-06-11T09:08:17.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IssueCategory', Name='Problemkategorie', Description=NULL, Help=NULL WHERE AD_Element_ID=577756
;

-- 2020-06-11T09:08:17.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IssueCategory', Name='Problemkategorie', Description=NULL, Help=NULL, AD_Element_ID=577756 WHERE UPPER(ColumnName)='ISSUECATEGORY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-06-11T09:08:17.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IssueCategory', Name='Problemkategorie', Description=NULL, Help=NULL WHERE AD_Element_ID=577756 AND IsCentrallyMaintained='Y'
;

-- 2020-06-11T09:08:17.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Problemkategorie', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577756) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577756)
;

-- 2020-06-11T09:08:17.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Category', Name='Problemkategorie' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577756)
;

-- 2020-06-11T09:08:17.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Problemkategorie', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577756
;

-- 2020-06-11T09:08:17.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Problemkategorie', Description=NULL, Help=NULL WHERE AD_Element_ID = 577756
;

-- 2020-06-11T09:08:17.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Problemkategorie', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577756
;

-- 2020-06-11T09:08:20.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Problemkategorie',Updated=TO_TIMESTAMP('2020-06-11 12:08:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577756 AND AD_Language='de_DE'
;

-- 2020-06-11T09:08:20.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577756,'de_DE') 
;

-- 2020-06-11T09:08:20.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577756,'de_DE') 
;

-- 2020-06-11T09:08:20.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Problemkategorie', Name='Problemkategorie' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577756)
;

-- 2020-06-11T09:08:22.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-06-11 12:08:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577756 AND AD_Language='en_US'
;

-- 2020-06-11T09:08:22.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577756,'en_US') 
;

-- 2020-06-11T09:08:30.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Issue Category', PrintName='Issue Category',Updated=TO_TIMESTAMP('2020-06-11 12:08:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577756 AND AD_Language='en_US'
;

-- 2020-06-11T09:08:30.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577756,'en_US') 
;

-- 2020-06-11T09:10:59.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Probleme', PrintName='Probleme',Updated=TO_TIMESTAMP('2020-06-11 12:10:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2887 AND AD_Language='de_CH'
;

-- 2020-06-11T09:10:59.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2887,'de_CH') 
;

-- 2020-06-11T09:11:23.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='', Name='Issue', PrintName='Issue',Updated=TO_TIMESTAMP('2020-06-11 12:11:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2887 AND AD_Language='en_US'
;

-- 2020-06-11T09:11:23.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2887,'en_US') 
;

-- 2020-06-11T09:11:25.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='',Updated=TO_TIMESTAMP('2020-06-11 12:11:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2887 AND AD_Language='de_CH'
;

-- 2020-06-11T09:11:25.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2887,'de_CH') 
;

-- 2020-06-11T09:11:54.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='', IsTranslated='Y', Name='Probleme', PrintName='Probleme',Updated=TO_TIMESTAMP('2020-06-11 12:11:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2887 AND AD_Language='de_DE'
;

-- 2020-06-11T09:11:54.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2887,'de_DE') 
;

-- 2020-06-11T09:11:54.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(2887,'de_DE') 
;

-- 2020-06-11T09:11:54.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_Issue_ID', Name='Probleme', Description='', Help='' WHERE AD_Element_ID=2887
;

-- 2020-06-11T09:11:54.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Issue_ID', Name='Probleme', Description='', Help='', AD_Element_ID=2887 WHERE UPPER(ColumnName)='AD_ISSUE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-06-11T09:11:54.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Issue_ID', Name='Probleme', Description='', Help='' WHERE AD_Element_ID=2887 AND IsCentrallyMaintained='Y'
;

-- 2020-06-11T09:11:54.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Probleme', Description='', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2887) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 2887)
;

-- 2020-06-11T09:11:54.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Probleme', Name='Probleme' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2887)
;

-- 2020-06-11T09:11:54.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Probleme', Description='', Help='', CommitWarning = NULL WHERE AD_Element_ID = 2887
;

-- 2020-06-11T09:11:54.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Probleme', Description='', Help='' WHERE AD_Element_ID = 2887
;

-- 2020-06-11T09:11:54.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Probleme', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 2887
;

-- 2020-06-11T09:13:16.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='', IsTranslated='Y', Name='Probleme', PrintName='Probleme',Updated=TO_TIMESTAMP('2020-06-11 12:13:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574361 AND AD_Language='de_CH'
;

-- 2020-06-11T09:13:16.681Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574361,'de_CH') 
;

-- 2020-06-11T09:13:27.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='', Name='Issues', PrintName='Issues',Updated=TO_TIMESTAMP('2020-06-11 12:13:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574361 AND AD_Language='en_US'
;

-- 2020-06-11T09:13:27.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574361,'en_US') 
;

-- 2020-06-11T09:13:35.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Help='', IsTranslated='Y', Name='Probleme', PrintName='Probleme',Updated=TO_TIMESTAMP('2020-06-11 12:13:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574361 AND AD_Language='de_DE'
;

-- 2020-06-11T09:13:35.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574361,'de_DE') 
;

-- 2020-06-11T09:13:35.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(574361,'de_DE') 
;

-- 2020-06-11T09:13:35.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Probleme', Description='', Help='' WHERE AD_Element_ID=574361
;

-- 2020-06-11T09:13:35.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Probleme', Description='', Help='' WHERE AD_Element_ID=574361 AND IsCentrallyMaintained='Y'
;

-- 2020-06-11T09:13:35.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Probleme', Description='', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=574361) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 574361)
;

-- 2020-06-11T09:13:35.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Probleme', Name='Probleme' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=574361)
;

-- 2020-06-11T09:13:35.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Probleme', Description='', Help='', CommitWarning = NULL WHERE AD_Element_ID = 574361
;

-- 2020-06-11T09:13:35.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Probleme', Description='', Help='' WHERE AD_Element_ID = 574361
;

-- 2020-06-11T09:13:35.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Probleme', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 574361
;

-- 2020-06-11T09:18:07.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET AD_Element_ID=2887, Description='', Name='Probleme', WEBUI_NameBrowse=NULL, WEBUI_NameNew=NULL, WEBUI_NameNewBreadcrumb=NULL,Updated=TO_TIMESTAMP('2020-06-11 12:18:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=552
;

-- 2020-06-11T09:18:07.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(2887) 
;

-- 2020-06-11T09:18:30.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Issues', PrintName='Issues',Updated=TO_TIMESTAMP('2020-06-11 12:18:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2887 AND AD_Language='en_US'
;

-- 2020-06-11T09:18:30.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2887,'en_US') 
;

-- 2020-06-11T09:18:37.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET InternalName='AD_Issue_ID',Updated=TO_TIMESTAMP('2020-06-11 12:18:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=552
;

-- 2020-06-11T09:19:11.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET AD_Element_ID=2887, Description='', Help='', InternalName='AD_Issue_ID', Name='Probleme',Updated=TO_TIMESTAMP('2020-06-11 12:19:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=363
;

-- 2020-06-11T09:19:11.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(2887) 
;

-- 2020-06-11T09:19:11.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=363
;

-- 2020-06-11T09:19:12.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Window(363)
;

-- 2020-06-11T09:19:41.730Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET CommitWarning=NULL, Help='', Name='Probleme', Description='', AD_Element_ID=2887,Updated=TO_TIMESTAMP('2020-06-11 12:19:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=777
;

-- 2020-06-11T09:19:41.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(2887) 
;

-- 2020-06-11T09:19:41.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(777)
;

-- 2020-06-11T09:20:16.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=575177
;

-- 2020-06-11T09:20:16.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=575177
;

-- 2020-06-11T09:20:31.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=574361
;

-- 2020-06-11T09:20:31.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=574361
;

-- 2020-06-11T09:23:52.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET CommitWarning=NULL, Help=NULL, Name='System-Problem', Description='System creating the issue', AD_Element_ID=2957,Updated=TO_TIMESTAMP('2020-06-11 12:23:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=794
;

-- 2020-06-11T09:23:52.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(2957) 
;

-- 2020-06-11T09:23:52.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(794)
;

-- 2020-06-11T09:24:11.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET AD_Element_ID=2957, Description='System creating the issue', Help=NULL, Name='System-Problem',Updated=TO_TIMESTAMP('2020-06-11 12:24:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=372
;

-- 2020-06-11T09:24:11.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='System creating the issue', IsActive='Y', Name='System-Problem',Updated=TO_TIMESTAMP('2020-06-11 12:24:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=564
;

-- 2020-06-11T09:24:11.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(2957) 
;

-- 2020-06-11T09:24:11.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=372
;

-- 2020-06-11T09:24:11.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Window(372)
;

-- 2020-06-11T09:25:05.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=573348
;

-- 2020-06-11T09:25:05.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=573348
;

-- 2020-06-11T09:25:07.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=573988
;

-- 2020-06-11T09:25:07.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=573988
;

-- 2020-06-11T09:26:00.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET AD_Element_ID=2957, Description='System creating the issue', Name='System-Problem', WEBUI_NameBrowse=NULL, WEBUI_NameNew=NULL, WEBUI_NameNewBreadcrumb=NULL,Updated=TO_TIMESTAMP('2020-06-11 12:26:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=564
;

-- 2020-06-11T09:26:00.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(2957) 
;

-- 2020-06-11T09:26:07.616Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=575166
;

-- 2020-06-11T09:26:07.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=575166
;

-- 2020-06-11T09:26:37.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=573556
;

-- 2020-06-11T09:26:37.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=573556
;

