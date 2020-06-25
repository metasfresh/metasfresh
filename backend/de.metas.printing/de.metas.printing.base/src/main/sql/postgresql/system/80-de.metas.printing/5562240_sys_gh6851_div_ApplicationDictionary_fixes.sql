
-- 2020-06-24T05:25:20.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=COALESCE( ( select (max(t.TrayNumber) +10) from AD_PrinterHW_MediaTray t where t.AD_PrinterHW_ID=@AD_PrinterHW_ID/0@ ), 10)',Updated=TO_TIMESTAMP('2020-06-24 07:25:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548092
;

-- 2020-06-24T06:17:14.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Logical Printer', PrintName='Logical Printer',Updated=TO_TIMESTAMP('2020-06-24 08:17:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541318 AND AD_Language='en_GB'
;

-- 2020-06-24T06:17:14.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541318,'en_GB')
;

-- 2020-06-24T06:17:17.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-06-24 08:17:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541318 AND AD_Language='de_CH'
;

-- 2020-06-24T06:17:17.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541318,'de_CH')
;

-- 2020-06-24T06:17:23.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Logical Printer',Updated=TO_TIMESTAMP('2020-06-24 08:17:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541318 AND AD_Language='en_US'
;

-- 2020-06-24T06:17:23.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541318,'en_US')
;

-- 2020-06-24T06:17:27.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-06-24 08:17:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541318 AND AD_Language='de_DE'
;

-- 2020-06-24T06:17:27.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541318,'de_DE')
;

-- 2020-06-24T06:17:27.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(541318,'de_DE')
;

-- 2020-06-24T06:18:00.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540168,Updated=TO_TIMESTAMP('2020-06-24 08:18:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540281
;

-- 2020-06-24T06:18:15.477Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-06-24 08:18:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574159 AND AD_Language='de_CH'
;

-- 2020-06-24T06:18:15.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574159,'de_CH')
;

-- 2020-06-24T06:18:37.517Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET AD_Element_ID=541318, Description=NULL, Help=NULL, Name='Logischer Drucker',Updated=TO_TIMESTAMP('2020-06-24 08:18:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540168
;

-- 2020-06-24T06:18:37.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Logischer Drucker',Updated=TO_TIMESTAMP('2020-06-24 08:18:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541414
;

-- 2020-06-24T06:18:37.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Logischer Drucker',Updated=TO_TIMESTAMP('2020-06-24 08:18:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540430
;

-- 2020-06-24T06:18:37.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(541318)
;

-- 2020-06-24T06:18:37.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540168
;

-- 2020-06-24T06:18:37.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Window(540168)
;

-- 2020-06-24T06:19:20.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET AD_Element_ID=541318, Description=NULL, InternalName='AD_Printer', Name='Logischer Drucker', WEBUI_NameBrowse=NULL, WEBUI_NameNew=NULL, WEBUI_NameNewBreadcrumb=NULL,Updated=TO_TIMESTAMP('2020-06-24 08:19:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540430
;

-- 2020-06-24T06:19:20.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(541318)
;

-- 2020-06-24T06:21:17.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@OutputType/''X''@=''Queue''', ReadOnlyLogic='@OutputType/''X''@!''Queue''',Updated=TO_TIMESTAMP('2020-06-24 08:21:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540465
;

-- 2020-06-24T06:21:36.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@OutputType/''X''@=''Queue''', ReadOnlyLogic='@OutputType/''X''@!''Queue''',Updated=TO_TIMESTAMP('2020-06-24 08:21:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540466
;

-- 2020-06-24T06:38:02.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=select COALESCE( ( select (max(t.TrayNumber) +10) from AD_PrinterHW_MediaTray t where t.AD_PrinterHW_ID=@AD_PrinterHW_ID/0@ ), 10)',Updated=TO_TIMESTAMP('2020-06-24 08:38:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548092
;

