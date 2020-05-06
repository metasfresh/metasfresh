-- 2019-11-25T12:27:55.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=90,Updated=TO_TIMESTAMP('2019-11-25 13:27:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563706
;

-- 2019-11-25T12:27:55.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=100,Updated=TO_TIMESTAMP('2019-11-25 13:27:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559589
;

-- 2019-11-25T12:27:55.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=110,Updated=TO_TIMESTAMP('2019-11-25 13:27:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544273
;

-- 2019-11-25T13:38:40.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Rüstliste erfassen',Updated=TO_TIMESTAMP('2019-11-25 14:38:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=541179
;

-- 2019-11-25T13:39:26.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Quick-edit',Updated=TO_TIMESTAMP('2019-11-25 14:39:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=541179
;

-- 2019-11-25T13:39:52.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Rüstliste erfassen',Updated=TO_TIMESTAMP('2019-11-25 14:39:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541179
;

-- 2019-11-25T13:44:31.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-25 14:44:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543895 AND AD_Language='de_CH'
;

-- 2019-11-25T13:44:31.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543895,'de_CH') 
;

-- 2019-11-25T13:45:42.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Mindesthaltbarkeit', PrintName='Mindesthaltbarkeit',Updated=TO_TIMESTAMP('2019-11-25 14:45:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543895 AND AD_Language='de_CH'
;

-- 2019-11-25T13:45:42.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543895,'de_CH') 
;

-- 2019-11-25T13:45:54.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Best before', PrintName='Best before',Updated=TO_TIMESTAMP('2019-11-25 14:45:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543895 AND AD_Language='en_US'
;

-- 2019-11-25T13:45:54.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543895,'en_US') 
;

-- 2019-11-25T13:45:59.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Best before',Updated=TO_TIMESTAMP('2019-11-25 14:45:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543895 AND AD_Language='de_DE'
;

-- 2019-11-25T13:45:59.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543895,'de_DE') 
;

-- 2019-11-25T13:45:59.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543895,'de_DE') 
;

-- 2019-11-25T13:45:59.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='HU_BestBeforeDate', Name='Best before', Description=NULL, Help=NULL WHERE AD_Element_ID=543895
;

-- 2019-11-25T13:45:59.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HU_BestBeforeDate', Name='Best before', Description=NULL, Help=NULL, AD_Element_ID=543895 WHERE UPPER(ColumnName)='HU_BESTBEFOREDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-25T13:45:59.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HU_BestBeforeDate', Name='Best before', Description=NULL, Help=NULL WHERE AD_Element_ID=543895 AND IsCentrallyMaintained='Y'
;

-- 2019-11-25T13:45:59.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Best before', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543895) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543895)
;

-- 2019-11-25T13:45:59.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Best Before Date', Name='Best before' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543895)
;

-- 2019-11-25T13:45:59.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Best before', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543895
;

-- 2019-11-25T13:45:59.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Best before', Description=NULL, Help=NULL WHERE AD_Element_ID = 543895
;

-- 2019-11-25T13:45:59.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Best before', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543895
;

-- 2019-11-25T13:46:06.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Mindesthaltbarkeit', PrintName='Mindesthaltbarkeit',Updated=TO_TIMESTAMP('2019-11-25 14:46:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543895 AND AD_Language='de_DE'
;

-- 2019-11-25T13:46:06.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543895,'de_DE') 
;

-- 2019-11-25T13:46:06.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543895,'de_DE') 
;

-- 2019-11-25T13:46:06.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='HU_BestBeforeDate', Name='Mindesthaltbarkeit', Description=NULL, Help=NULL WHERE AD_Element_ID=543895
;

-- 2019-11-25T13:46:06.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HU_BestBeforeDate', Name='Mindesthaltbarkeit', Description=NULL, Help=NULL, AD_Element_ID=543895 WHERE UPPER(ColumnName)='HU_BESTBEFOREDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-25T13:46:06.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HU_BestBeforeDate', Name='Mindesthaltbarkeit', Description=NULL, Help=NULL WHERE AD_Element_ID=543895 AND IsCentrallyMaintained='Y'
;

-- 2019-11-25T13:46:06.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mindesthaltbarkeit', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543895) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543895)
;

-- 2019-11-25T13:46:06.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Mindesthaltbarkeit', Name='Mindesthaltbarkeit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543895)
;

-- 2019-11-25T13:46:06.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Mindesthaltbarkeit', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543895
;

-- 2019-11-25T13:46:06.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Mindesthaltbarkeit', Description=NULL, Help=NULL WHERE AD_Element_ID = 543895
;

-- 2019-11-25T13:46:06.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Mindesthaltbarkeit', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543895
;

