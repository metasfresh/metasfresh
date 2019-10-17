-- 2019-03-27T06:49:11.733
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Nutzer, die benachrichtigt werden, wenn der Prozess erfolgreich ausgeführt wurde und SysConfig org.compiere.server.Scheduler.notifyOnOK=Y', Help='', IsTranslated='Y', Name='Benachrichtigungsempfänger', PrintName='Benachrichtigungsempfänger',Updated=TO_TIMESTAMP('2019-03-27 06:49:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573222 AND AD_Language='de_CH'
;

-- 2019-03-27T06:49:11.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573222,'de_CH') 
;

-- 2019-03-27T06:49:31.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Nutzer, die benachrichtigt werden, wenn der Prozess erfolgreich ausgeführt wurde und SysConfig org.compiere.server.Scheduler.notifyOnOK=Y', Help='',Updated=TO_TIMESTAMP('2019-03-27 06:49:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573222 AND AD_Language='nl_NL'
;

-- 2019-03-27T06:49:31.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573222,'nl_NL') 
;

-- 2019-03-27T06:49:39.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Benachrichtigungsempfänger', PrintName='Benachrichtigungsempfänger',Updated=TO_TIMESTAMP('2019-03-27 06:49:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573222 AND AD_Language='nl_NL'
;

-- 2019-03-27T06:49:39.135
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573222,'nl_NL') 
;

-- 2019-03-27T06:49:42.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Benachrichtigungsempfänger', PrintName='Benachrichtigungsempfänger',Updated=TO_TIMESTAMP('2019-03-27 06:49:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573222 AND AD_Language='de_DE'
;

-- 2019-03-27T06:49:42.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573222,'de_DE') 
;

-- 2019-03-27T06:49:42.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(573222,'de_DE') 
;

-- 2019-03-27T06:49:42.504
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Benachrichtigungsempfänger', Description='Recipient of the Scheduler Notification', Help='You can send the notifications to users or roles' WHERE AD_Element_ID=573222
;

-- 2019-03-27T06:49:42.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Benachrichtigungsempfänger', Description='Recipient of the Scheduler Notification', Help='You can send the notifications to users or roles' WHERE AD_Element_ID=573222 AND IsCentrallyMaintained='Y'
;

-- 2019-03-27T06:49:42.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Benachrichtigungsempfänger', Description='Recipient of the Scheduler Notification', Help='You can send the notifications to users or roles' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=573222) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 573222)
;

-- 2019-03-27T06:49:42.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Benachrichtigungsempfänger', Name='Benachrichtigungsempfänger' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=573222)
;

-- 2019-03-27T06:49:42.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Benachrichtigungsempfänger', Description='Recipient of the Scheduler Notification', Help='You can send the notifications to users or roles', CommitWarning = NULL WHERE AD_Element_ID = 573222
;

-- 2019-03-27T06:49:42.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Benachrichtigungsempfänger', Description='Recipient of the Scheduler Notification', Help='You can send the notifications to users or roles' WHERE AD_Element_ID = 573222
;

-- 2019-03-27T06:49:42.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Benachrichtigungsempfänger', Description = 'Recipient of the Scheduler Notification', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 573222
;

-- 2019-03-27T06:49:48.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Nutzer, die benachrichtigt werden, wenn der Prozess erfolgreich ausgeführt wurde und SysConfig org.compiere.server.Scheduler.notifyOnOK=Y', Help='',Updated=TO_TIMESTAMP('2019-03-27 06:49:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573222 AND AD_Language='de_DE'
;

-- 2019-03-27T06:49:48.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573222,'de_DE') 
;

-- 2019-03-27T06:49:48.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(573222,'de_DE') 
;

-- 2019-03-27T06:49:48.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Benachrichtigungsempfänger', Description='Nutzer, die benachrichtigt werden, wenn der Prozess erfolgreich ausgeführt wurde und SysConfig org.compiere.server.Scheduler.notifyOnOK=Y', Help='' WHERE AD_Element_ID=573222
;

-- 2019-03-27T06:49:48.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Benachrichtigungsempfänger', Description='Nutzer, die benachrichtigt werden, wenn der Prozess erfolgreich ausgeführt wurde und SysConfig org.compiere.server.Scheduler.notifyOnOK=Y', Help='' WHERE AD_Element_ID=573222 AND IsCentrallyMaintained='Y'
;

-- 2019-03-27T06:49:48.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Benachrichtigungsempfänger', Description='Nutzer, die benachrichtigt werden, wenn der Prozess erfolgreich ausgeführt wurde und SysConfig org.compiere.server.Scheduler.notifyOnOK=Y', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=573222) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 573222)
;

-- 2019-03-27T06:49:48.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Benachrichtigungsempfänger', Description='Nutzer, die benachrichtigt werden, wenn der Prozess erfolgreich ausgeführt wurde und SysConfig org.compiere.server.Scheduler.notifyOnOK=Y', Help='', CommitWarning = NULL WHERE AD_Element_ID = 573222
;

-- 2019-03-27T06:49:48.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Benachrichtigungsempfänger', Description='Nutzer, die benachrichtigt werden, wenn der Prozess erfolgreich ausgeführt wurde und SysConfig org.compiere.server.Scheduler.notifyOnOK=Y', Help='' WHERE AD_Element_ID = 573222
;

-- 2019-03-27T06:49:48.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Benachrichtigungsempfänger', Description = 'Nutzer, die benachrichtigt werden, wenn der Prozess erfolgreich ausgeführt wurde und SysConfig org.compiere.server.Scheduler.notifyOnOK=Y', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 573222
;

-- 2019-03-27T06:50:01.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Notification Recipient', PrintName='Notification Recipient',Updated=TO_TIMESTAMP('2019-03-27 06:50:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573222 AND AD_Language='en_US'
;

-- 2019-03-27T06:50:01.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573222,'en_US') 
;

-- 2019-03-27T06:50:43.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Users to notify when a process completed successfully and SysConfig org.compiere.server.Scheduler.notifyOnOK=Y', Help='',Updated=TO_TIMESTAMP('2019-03-27 06:50:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573222 AND AD_Language='en_US'
;

-- 2019-03-27T06:50:43.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573222,'en_US') 
;
