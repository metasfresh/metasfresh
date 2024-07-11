-- 2022-03-06T19:21:39.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Abrufauftrag',Updated=TO_TIMESTAMP('2022-03-06 21:21:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543134
;

-- 2022-03-06T19:21:44.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Abrufauftrag',Updated=TO_TIMESTAMP('2022-03-06 21:21:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543134
;

-- 2022-03-06T19:21:47.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Abrufauftrag',Updated=TO_TIMESTAMP('2022-03-06 21:21:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543134
;

-- 2022-03-06T19:21:50.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Abrufauftrag',Updated=TO_TIMESTAMP('2022-03-06 21:21:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543134
;

-- 2022-03-06T19:22:01.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Call-off order',Updated=TO_TIMESTAMP('2022-03-06 21:22:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543134
;

-- 2022-03-06T19:23:59.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Abrufauftrag Übersicht', PrintName='Abrufauftrag Übersicht',Updated=TO_TIMESTAMP('2022-03-06 21:23:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580602 AND AD_Language='de_CH'
;

-- 2022-03-06T19:23:59.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580602,'de_CH')
;

-- 2022-03-06T19:24:05.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Abrufauftrag Übersicht', PrintName='Abrufauftrag Übersicht',Updated=TO_TIMESTAMP('2022-03-06 21:24:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580602 AND AD_Language='de_DE'
;

-- 2022-03-06T19:24:05.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580602,'de_DE')
;

-- 2022-03-06T19:24:05.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580602,'de_DE')
;

-- 2022-03-06T19:24:05.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_CallOrderSummary_ID', Name='Abrufauftrag Übersicht', Description=NULL, Help=NULL WHERE AD_Element_ID=580602
;

-- 2022-03-06T19:24:05.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_CallOrderSummary_ID', Name='Abrufauftrag Übersicht', Description=NULL, Help=NULL, AD_Element_ID=580602 WHERE UPPER(ColumnName)='C_CALLORDERSUMMARY_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-06T19:24:05.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_CallOrderSummary_ID', Name='Abrufauftrag Übersicht', Description=NULL, Help=NULL WHERE AD_Element_ID=580602 AND IsCentrallyMaintained='Y'
;

-- 2022-03-06T19:24:05.711Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abrufauftrag Übersicht', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580602) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580602)
;

-- 2022-03-06T19:24:05.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Abrufauftrag Übersicht', Name='Abrufauftrag Übersicht' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580602)
;

-- 2022-03-06T19:24:05.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Abrufauftrag Übersicht', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580602
;

-- 2022-03-06T19:24:05.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Abrufauftrag Übersicht', Description=NULL, Help=NULL WHERE AD_Element_ID = 580602
;

-- 2022-03-06T19:24:05.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Abrufauftrag Übersicht', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580602
;

-- 2022-03-06T19:24:11.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Abrufauftrag Übersicht', PrintName='Abrufauftrag Übersicht',Updated=TO_TIMESTAMP('2022-03-06 21:24:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580602 AND AD_Language='nl_NL'
;

-- 2022-03-06T19:24:11.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580602,'nl_NL')
;

-- 2022-03-06T19:24:26.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Call-off order overview', PrintName='Call-off order overview',Updated=TO_TIMESTAMP('2022-03-06 21:24:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580602 AND AD_Language='en_US'
;

-- 2022-03-06T19:24:26.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580602,'en_US')
;

-- 2022-03-06T19:30:32.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Angaben', PrintName='Angaben',Updated=TO_TIMESTAMP('2022-03-06 21:30:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580603 AND AD_Language='de_CH'
;

-- 2022-03-06T19:30:32.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580603,'de_CH')
;

-- 2022-03-06T19:30:36.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Angaben', PrintName='Angaben',Updated=TO_TIMESTAMP('2022-03-06 21:30:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580603 AND AD_Language='de_DE'
;

-- 2022-03-06T19:30:36.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580603,'de_DE')
;

-- 2022-03-06T19:30:36.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580603,'de_DE')
;

-- 2022-03-06T19:30:36.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_CallOrderDetail_ID', Name='Angaben', Description=NULL, Help=NULL WHERE AD_Element_ID=580603
;

-- 2022-03-06T19:30:36.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_CallOrderDetail_ID', Name='Angaben', Description=NULL, Help=NULL, AD_Element_ID=580603 WHERE UPPER(ColumnName)='C_CALLORDERDETAIL_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-06T19:30:36.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_CallOrderDetail_ID', Name='Angaben', Description=NULL, Help=NULL WHERE AD_Element_ID=580603 AND IsCentrallyMaintained='Y'
;

-- 2022-03-06T19:30:36.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Angaben', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580603) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580603)
;

-- 2022-03-06T19:30:36.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Angaben', Name='Angaben' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580603)
;

-- 2022-03-06T19:30:36.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Angaben', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580603
;

-- 2022-03-06T19:30:36.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Angaben', Description=NULL, Help=NULL WHERE AD_Element_ID = 580603
;

-- 2022-03-06T19:30:36.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Angaben', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580603
;

-- 2022-03-06T19:30:40.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Angaben', PrintName='Angaben',Updated=TO_TIMESTAMP('2022-03-06 21:30:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580603 AND AD_Language='nl_NL'
;

-- 2022-03-06T19:30:40.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580603,'nl_NL')
;

-- 2022-03-06T19:30:48.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Detail', PrintName='Detail',Updated=TO_TIMESTAMP('2022-03-06 21:30:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580603 AND AD_Language='en_US'
;

-- 2022-03-06T19:30:48.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580603,'en_US')
;

-- 2022-03-06T19:33:46.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Description='Bei einem Abrufauftrag wird vereinbart, innerhalb eines bestimmten Zeitraums eine bestimmte Warenmenge zu bestimmten Konditionen abzunehmen.',Updated=TO_TIMESTAMP('2022-03-06 21:33:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543134
;

-- 2022-03-06T19:33:51.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Bei einem Abrufauftrag wird vereinbart, innerhalb eines bestimmten Zeitraums eine bestimmte Warenmenge zu bestimmten Konditionen abzunehmen.',Updated=TO_TIMESTAMP('2022-03-06 21:33:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543134
;

-- 2022-03-06T19:33:53.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Bei einem Abrufauftrag wird vereinbart, innerhalb eines bestimmten Zeitraums eine bestimmte Warenmenge zu bestimmten Konditionen abzunehmen.',Updated=TO_TIMESTAMP('2022-03-06 21:33:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543134
;

-- 2022-03-06T19:33:57.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='Bei einem Abrufauftrag wird vereinbart, innerhalb eines bestimmten Zeitraums eine bestimmte Warenmenge zu bestimmten Konditionen abzunehmen.',Updated=TO_TIMESTAMP('2022-03-06 21:33:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543134
;

-- 2022-03-06T19:34:06.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Description='In the case of a call-off order, it is agreed to take delivery of a certain quantity of goods at certain conditions within a certain period of time.',Updated=TO_TIMESTAMP('2022-03-06 21:34:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543134
;
