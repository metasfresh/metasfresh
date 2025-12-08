-- 2022-05-01T12:16:12.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='FTP-Passwort', PrintName='FTP-Passwort',Updated=TO_TIMESTAMP('2022-05-01 15:16:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580814 AND AD_Language='de_CH'
;

-- 2022-05-01T12:16:12.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580814,'de_CH') 
;

-- 2022-05-01T12:16:16.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='FTP-Passwort', PrintName='FTP-Passwort',Updated=TO_TIMESTAMP('2022-05-01 15:16:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580814 AND AD_Language='de_DE'
;

-- 2022-05-01T12:16:16.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580814,'de_DE') 
;

-- 2022-05-01T12:16:16.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580814,'de_DE') 
;

-- 2022-05-01T12:16:16.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FTP_Password', Name='FTP-Passwort', Description=NULL, Help=NULL WHERE AD_Element_ID=580814
;

-- 2022-05-01T12:16:16.561Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FTP_Password', Name='FTP-Passwort', Description=NULL, Help=NULL, AD_Element_ID=580814 WHERE UPPER(ColumnName)='FTP_PASSWORD' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-05-01T12:16:16.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FTP_Password', Name='FTP-Passwort', Description=NULL, Help=NULL WHERE AD_Element_ID=580814 AND IsCentrallyMaintained='Y'
;

-- 2022-05-01T12:16:16.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='FTP-Passwort', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580814) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580814)
;

-- 2022-05-01T12:16:16.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='FTP-Passwort', Name='FTP-Passwort' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580814)
;

-- 2022-05-01T12:16:16.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='FTP-Passwort', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580814
;

-- 2022-05-01T12:16:16.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='FTP-Passwort', Description=NULL, Help=NULL WHERE AD_Element_ID = 580814
;

-- 2022-05-01T12:16:16.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'FTP-Passwort', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580814
;

-- 2022-05-01T12:16:21.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='FTP-Passwort', PrintName='FTP-Passwort',Updated=TO_TIMESTAMP('2022-05-01 15:16:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580814 AND AD_Language='nl_NL'
;

-- 2022-05-01T12:16:21.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580814,'nl_NL') 
;

-- 2022-05-01T12:16:36.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='FTP-Port', PrintName='FTP-Port',Updated=TO_TIMESTAMP('2022-05-01 15:16:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580812 AND AD_Language='de_CH'
;

-- 2022-05-01T12:16:36.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580812,'de_CH') 
;

-- 2022-05-01T12:16:39.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='FTP-Port', PrintName='FTP-Port',Updated=TO_TIMESTAMP('2022-05-01 15:16:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580812 AND AD_Language='de_DE'
;

-- 2022-05-01T12:16:39.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580812,'de_DE') 
;

-- 2022-05-01T12:16:39.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580812,'de_DE') 
;

-- 2022-05-01T12:16:39.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FTP_Port', Name='FTP-Port', Description=NULL, Help=NULL WHERE AD_Element_ID=580812
;

-- 2022-05-01T12:16:39.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FTP_Port', Name='FTP-Port', Description=NULL, Help=NULL, AD_Element_ID=580812 WHERE UPPER(ColumnName)='FTP_PORT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-05-01T12:16:39.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FTP_Port', Name='FTP-Port', Description=NULL, Help=NULL WHERE AD_Element_ID=580812 AND IsCentrallyMaintained='Y'
;

-- 2022-05-01T12:16:39.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='FTP-Port', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580812) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580812)
;

-- 2022-05-01T12:16:39.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='FTP-Port', Name='FTP-Port' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580812)
;

-- 2022-05-01T12:16:39.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='FTP-Port', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580812
;

-- 2022-05-01T12:16:39.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='FTP-Port', Description=NULL, Help=NULL WHERE AD_Element_ID = 580812
;

-- 2022-05-01T12:16:39.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'FTP-Port', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580812
;

-- 2022-05-01T12:16:44.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='FTP-Port', PrintName='FTP-Port',Updated=TO_TIMESTAMP('2022-05-01 15:16:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580812 AND AD_Language='nl_NL'
;

-- 2022-05-01T12:16:44.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580812,'nl_NL') 
;

-- 2022-05-01T12:16:59.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='FTP-Benutzername',Updated=TO_TIMESTAMP('2022-05-01 15:16:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580813 AND AD_Language='de_CH'
;

-- 2022-05-01T12:16:59.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580813,'de_CH') 
;

-- 2022-05-01T12:17:05.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='FTP-Benutzername',Updated=TO_TIMESTAMP('2022-05-01 15:17:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580813 AND AD_Language='de_CH'
;

-- 2022-05-01T12:17:05.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580813,'de_CH') 
;

-- 2022-05-01T12:17:09.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='FTP-Benutzername', PrintName='FTP-Benutzername',Updated=TO_TIMESTAMP('2022-05-01 15:17:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580813 AND AD_Language='de_DE'
;

-- 2022-05-01T12:17:09.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580813,'de_DE') 
;

-- 2022-05-01T12:17:09.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580813,'de_DE') 
;

-- 2022-05-01T12:17:09.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FTP_Username', Name='FTP-Benutzername', Description=NULL, Help=NULL WHERE AD_Element_ID=580813
;

-- 2022-05-01T12:17:09.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FTP_Username', Name='FTP-Benutzername', Description=NULL, Help=NULL, AD_Element_ID=580813 WHERE UPPER(ColumnName)='FTP_USERNAME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-05-01T12:17:09.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FTP_Username', Name='FTP-Benutzername', Description=NULL, Help=NULL WHERE AD_Element_ID=580813 AND IsCentrallyMaintained='Y'
;

-- 2022-05-01T12:17:09.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='FTP-Benutzername', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580813) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580813)
;

-- 2022-05-01T12:17:09.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='FTP-Benutzername', Name='FTP-Benutzername' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580813)
;

-- 2022-05-01T12:17:09.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='FTP-Benutzername', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580813
;

-- 2022-05-01T12:17:09.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='FTP-Benutzername', Description=NULL, Help=NULL WHERE AD_Element_ID = 580813
;

-- 2022-05-01T12:17:09.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'FTP-Benutzername', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580813
;

-- 2022-05-01T12:17:12.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='FTP-Benutzername', PrintName='FTP-Benutzername',Updated=TO_TIMESTAMP('2022-05-01 15:17:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580813 AND AD_Language='nl_NL'
;

-- 2022-05-01T12:17:12.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580813,'nl_NL') 
;

-- 2022-05-02T14:05:27.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='An Leich + Mehl PAW senden',Updated=TO_TIMESTAMP('2022-05-02 17:05:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585045
;

-- 2022-05-02T14:05:33.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='An Leich + Mehl PAW senden',Updated=TO_TIMESTAMP('2022-05-02 17:05:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585045
;

-- 2022-05-02T14:05:40.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='An Leich + Mehl PAW senden',Updated=TO_TIMESTAMP('2022-05-02 17:05:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585045
;

-- 2022-05-02T14:05:47.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='An Leich + Mehl PAW senden',Updated=TO_TIMESTAMP('2022-05-02 17:05:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585045
;

-- 2022-05-02T14:05:57.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Send to Leich + Mehl Scale',Updated=TO_TIMESTAMP('2022-05-02 17:05:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585045
;

-- 2022-05-02T14:20:36.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='LeichUndMehl',Updated=TO_TIMESTAMP('2022-05-02 17:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543167
;

-- 2022-05-02T14:20:55.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Leich + Mehl',Updated=TO_TIMESTAMP('2022-05-02 17:20:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543167
;

-- 2022-05-02T14:21:11.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Leich + Mehl',Updated=TO_TIMESTAMP('2022-05-02 17:21:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543167
;

-- 2022-05-02T14:21:13.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Leich + Mehl',Updated=TO_TIMESTAMP('2022-05-02 17:21:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543167
;

-- 2022-05-02T14:21:17.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Leich + Mehl',Updated=TO_TIMESTAMP('2022-05-02 17:21:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543167
;

-- 2022-05-05T09:20:45.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='FTP Host', PrintName='FTP Host',Updated=TO_TIMESTAMP('2022-05-05 12:20:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580819 AND AD_Language='en_US'
;

-- 2022-05-05T09:20:45.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580819,'en_US')
;

-- 2022-05-05T09:20:51.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='FTP_Host',Updated=TO_TIMESTAMP('2022-05-05 12:20:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580819
;

-- 2022-05-05T09:20:51.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FTP_Host', Name='FTP-Hostname', Description=NULL, Help=NULL WHERE AD_Element_ID=580819
;

-- 2022-05-05T09:20:51.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FTP_Host', Name='FTP-Hostname', Description=NULL, Help=NULL, AD_Element_ID=580819 WHERE UPPER(ColumnName)='FTP_HOST' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-05-05T09:20:51.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FTP_Host', Name='FTP-Hostname', Description=NULL, Help=NULL WHERE AD_Element_ID=580819 AND IsCentrallyMaintained='Y'
;

-- 2022-05-05T09:23:48.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='FTP_Hostname',Updated=TO_TIMESTAMP('2022-05-05 12:23:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580819
;

-- 2022-05-05T09:23:48.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='FTP_Hostname', Name='FTP-Hostname', Description=NULL, Help=NULL WHERE AD_Element_ID=580819
;

-- 2022-05-05T09:23:48.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FTP_Hostname', Name='FTP-Hostname', Description=NULL, Help=NULL, AD_Element_ID=580819 WHERE UPPER(ColumnName)='FTP_HOSTNAME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-05-05T09:23:48.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='FTP_Hostname', Name='FTP-Hostname', Description=NULL, Help=NULL WHERE AD_Element_ID=580819 AND IsCentrallyMaintained='Y'
;

-- 2022-05-05T09:24:49.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='FTP Password',Updated=TO_TIMESTAMP('2022-05-05 12:24:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580814 AND AD_Language='en_US'
;

-- 2022-05-05T09:24:49.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580814,'en_US')
;

-- 2022-05-05T09:25:12.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='FTP Port',Updated=TO_TIMESTAMP('2022-05-05 12:25:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580812 AND AD_Language='en_US'
;

-- 2022-05-05T09:25:12.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580812,'en_US')
;

-- 2022-05-05T09:25:26.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='FTP Username',Updated=TO_TIMESTAMP('2022-05-05 12:25:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580813 AND AD_Language='en_US'
;

-- 2022-05-05T09:25:26.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580813,'en_US')
;

-- 2022-05-05T09:25:48.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='FTP Directory', PrintName='FTP Directory',Updated=TO_TIMESTAMP('2022-05-05 12:25:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580820 AND AD_Language='en_US'
;

-- 2022-05-05T09:25:48.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580820,'en_US')
;

-- 2022-05-05T09:26:08.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='FTP Username',Updated=TO_TIMESTAMP('2022-05-05 12:26:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580813 AND AD_Language='en_US'
;

-- 2022-05-05T09:26:08.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580813,'en_US')
;

-- 2022-05-05T09:26:25.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='FTP Password',Updated=TO_TIMESTAMP('2022-05-05 12:26:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580814 AND AD_Language='en_US'
;

-- 2022-05-05T09:26:25.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580814,'en_US')
;

-- 2022-05-05T09:26:46.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='FTP Port',Updated=TO_TIMESTAMP('2022-05-05 12:26:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580812 AND AD_Language='en_US'
;

-- 2022-05-05T09:26:46.873Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580812,'en_US')
;

-- 2022-05-10T09:20:24.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543178,541117,TO_TIMESTAMP('2022-05-10 12:20:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','LeichUndMehl',TO_TIMESTAMP('2022-05-10 12:20:24','YYYY-MM-DD HH24:MI:SS'),100,'LeichUndMehl','LeichUndMehl')
;

-- 2022-05-10T09:20:24.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543178 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-05-10T09:25:46.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='LeichUndMehl', PrintName='LeichUndMehl',Updated=TO_TIMESTAMP('2022-05-10 12:25:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580811 AND AD_Language='de_CH'
;

-- 2022-05-10T09:25:46.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580811,'de_CH')
;

-- 2022-05-10T09:26:07.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='LeichUndMehl', PrintName='LeichUndMehl',Updated=TO_TIMESTAMP('2022-05-10 12:26:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580811 AND AD_Language='de_DE'
;

-- 2022-05-10T09:26:07.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580811,'de_DE')
;

-- 2022-05-10T09:26:07.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580811,'de_DE')
;

-- 2022-05-10T09:26:08.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExternalSystem_Config_LeichMehl_ID', Name='LeichUndMehl', Description=NULL, Help=NULL WHERE AD_Element_ID=580811
;

-- 2022-05-10T09:26:08.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Config_LeichMehl_ID', Name='LeichUndMehl', Description=NULL, Help=NULL, AD_Element_ID=580811 WHERE UPPER(ColumnName)='EXTERNALSYSTEM_CONFIG_LEICHMEHL_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-05-10T09:26:08.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Config_LeichMehl_ID', Name='LeichUndMehl', Description=NULL, Help=NULL WHERE AD_Element_ID=580811 AND IsCentrallyMaintained='Y'
;

-- 2022-05-10T09:26:08.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='LeichUndMehl', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580811) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580811)
;

-- 2022-05-10T09:26:08.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='LeichUndMehl', Name='LeichUndMehl' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580811)
;

-- 2022-05-10T09:26:08.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='LeichUndMehl', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580811
;

-- 2022-05-10T09:26:08.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='LeichUndMehl', Description=NULL, Help=NULL WHERE AD_Element_ID = 580811
;

-- 2022-05-10T09:26:08.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'LeichUndMehl', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580811
;

-- 2022-05-10T09:26:09.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='LeichUndMehl', PrintName='LeichUndMehl',Updated=TO_TIMESTAMP('2022-05-10 12:26:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580811 AND AD_Language='en_US'
;

-- 2022-05-10T09:26:09.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580811,'en_US')
;

-- 2022-05-10T09:26:11.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='LeichUndMehl', PrintName='LeichUndMehl',Updated=TO_TIMESTAMP('2022-05-10 12:26:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580811 AND AD_Language='nl_NL'
;

-- 2022-05-10T09:26:11.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580811,'nl_NL')
;

