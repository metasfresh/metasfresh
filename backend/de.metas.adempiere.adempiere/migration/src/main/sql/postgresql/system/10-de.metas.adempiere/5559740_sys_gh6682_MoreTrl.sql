-- 2020-05-20T08:50:07.011Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-05-20 11:50:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540911 AND AD_Language='de_DE'
;

-- 2020-05-20T08:50:10.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-05-20 11:50:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540911 AND AD_Language='en_US'
;
-- 2020-05-20T10:40:23.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET PrintName='Verbuchung erzwingen', Name='Verbuchung erzwingen',Updated=TO_TIMESTAMP('2020-05-20 13:40:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543744
;

-- 2020-05-20T10:40:23.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsEnforcePosting', Name='Verbuchung erzwingen', Description=NULL, Help=NULL WHERE AD_Element_ID=543744
;

-- 2020-05-20T10:40:23.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsEnforcePosting', Name='Verbuchung erzwingen', Description=NULL, Help=NULL, AD_Element_ID=543744 WHERE UPPER(ColumnName)='ISENFORCEPOSTING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-05-20T10:40:23.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsEnforcePosting', Name='Verbuchung erzwingen', Description=NULL, Help=NULL WHERE AD_Element_ID=543744 AND IsCentrallyMaintained='Y'
;

-- 2020-05-20T10:40:23.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verbuchung erzwingen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543744) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543744)
;

-- 2020-05-20T10:40:23.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Verbuchung erzwingen', Name='Verbuchung erzwingen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543744)
;

-- 2020-05-20T10:40:23.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Verbuchung erzwingen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543744
;

-- 2020-05-20T10:40:23.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Verbuchung erzwingen', Description=NULL, Help=NULL WHERE AD_Element_ID = 543744
;

-- 2020-05-20T10:40:23.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Verbuchung erzwingen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543744
;

-- 2020-05-20T10:41:01.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Verbuchung erzwingen', IsTranslated='Y', Name='Verbuchung erzwingen',Updated=TO_TIMESTAMP('2020-05-20 13:41:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=543744
;

-- 2020-05-20T10:41:01.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543744,'de_CH') 
;

-- 2020-05-20T10:41:08.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Verbuchung erzwingen', IsTranslated='Y', Name='Verbuchung erzwingen',Updated=TO_TIMESTAMP('2020-05-20 13:41:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=543744
;

-- 2020-05-20T10:41:08.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543744,'de_DE') 
;

-- 2020-05-20T10:41:08.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543744,'de_DE') 
;

-- 2020-05-20T10:41:08.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsEnforcePosting', Name='Verbuchung erzwingen', Description=NULL, Help=NULL WHERE AD_Element_ID=543744
;

-- 2020-05-20T10:41:08.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsEnforcePosting', Name='Verbuchung erzwingen', Description=NULL, Help=NULL, AD_Element_ID=543744 WHERE UPPER(ColumnName)='ISENFORCEPOSTING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-05-20T10:41:08.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsEnforcePosting', Name='Verbuchung erzwingen', Description=NULL, Help=NULL WHERE AD_Element_ID=543744 AND IsCentrallyMaintained='Y'
;

-- 2020-05-20T10:41:08.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verbuchung erzwingen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543744) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543744)
;

-- 2020-05-20T10:41:08.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Verbuchung erzwingen', Name='Verbuchung erzwingen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543744)
;

-- 2020-05-20T10:41:08.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Verbuchung erzwingen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543744
;

-- 2020-05-20T10:41:08.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Verbuchung erzwingen', Description=NULL, Help=NULL WHERE AD_Element_ID = 543744
;

-- 2020-05-20T10:41:08.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Verbuchung erzwingen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543744
;

-- 2020-05-20T10:41:32.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Der Beleg soll erneut verbucht werden, auch wenn er scheinbar gerade im Moment schon von metasfresh verbucht wird',Updated=TO_TIMESTAMP('2020-05-20 13:41:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=543744
;

-- 2020-05-20T10:41:32.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543744,'de_CH') 
;

-- 2020-05-20T10:41:36.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Der Beleg soll erneut verbucht werden, auch wenn er scheinbar gerade im Moment schon von metasfresh verbucht wird',Updated=TO_TIMESTAMP('2020-05-20 13:41:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=543744
;

-- 2020-05-20T10:41:36.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543744,'de_DE') 
;

-- 2020-05-20T10:41:36.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543744,'de_DE') 
;

-- 2020-05-20T10:41:36.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsEnforcePosting', Name='Verbuchung erzwingen', Description='Der Beleg soll erneut verbucht werden, auch wenn er scheinbar gerade im Moment schon von metasfresh verbucht wird', Help=NULL WHERE AD_Element_ID=543744
;

-- 2020-05-20T10:41:36.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsEnforcePosting', Name='Verbuchung erzwingen', Description='Der Beleg soll erneut verbucht werden, auch wenn er scheinbar gerade im Moment schon von metasfresh verbucht wird', Help=NULL, AD_Element_ID=543744 WHERE UPPER(ColumnName)='ISENFORCEPOSTING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-05-20T10:41:36.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsEnforcePosting', Name='Verbuchung erzwingen', Description='Der Beleg soll erneut verbucht werden, auch wenn er scheinbar gerade im Moment schon von metasfresh verbucht wird', Help=NULL WHERE AD_Element_ID=543744 AND IsCentrallyMaintained='Y'
;

-- 2020-05-20T10:41:36.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verbuchung erzwingen', Description='Der Beleg soll erneut verbucht werden, auch wenn er scheinbar gerade im Moment schon von metasfresh verbucht wird', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543744) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543744)
;

-- 2020-05-20T10:41:36.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Verbuchung erzwingen', Description='Der Beleg soll erneut verbucht werden, auch wenn er scheinbar gerade im Moment schon von metasfresh verbucht wird', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543744
;

-- 2020-05-20T10:41:36.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Verbuchung erzwingen', Description='Der Beleg soll erneut verbucht werden, auch wenn er scheinbar gerade im Moment schon von metasfresh verbucht wird', Help=NULL WHERE AD_Element_ID = 543744
;

-- 2020-05-20T10:41:36.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Verbuchung erzwingen', Description = 'Der Beleg soll erneut verbucht werden, auch wenn er scheinbar gerade im Moment schon von metasfresh verbucht wird', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543744
;

-- 2020-05-20T10:42:04.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Force Posting', IsTranslated='Y', Name='Force Posting',Updated=TO_TIMESTAMP('2020-05-20 13:42:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=543744
;

-- 2020-05-20T10:42:04.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543744,'en_US') 
;

-- 2020-05-20T10:42:15.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Post the document even if it is locked',Updated=TO_TIMESTAMP('2020-05-20 13:42:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=543744
;

-- 2020-05-20T10:42:15.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543744,'en_US') 
;

