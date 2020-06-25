-- 2020-06-22T18:57:54.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='''@OutputType/X@''=''Queue''',Updated=TO_TIMESTAMP('2020-06-22 20:57:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540465
;

-- 2020-06-22T18:58:04.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='''@OutputType/X@''=''Queue''',Updated=TO_TIMESTAMP('2020-06-22 20:58:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540466
;

-- 2020-06-22T18:58:58.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=45,Updated=TO_TIMESTAMP('2020-06-22 20:58:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547829
;

-- 2020-06-22T19:02:29.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=( select (max(t.SeqNo)+10) from AD_PrinterHW_MediaTray t where t.AD_PrinterHW_ID=@AD_PrinterHW_ID/0@ )',Updated=TO_TIMESTAMP('2020-06-22 21:02:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548092
;

-- 2020-06-22T19:10:07.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=( select (max(t.TrayNumber) +10) from AD_PrinterHW_MediaTray t where t.AD_PrinterHW_ID=@AD_PrinterHW_ID/0@ )',Updated=TO_TIMESTAMP('2020-06-22 21:10:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548092
;

-- 2020-06-22T19:10:11.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-06-22 21:10:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548092
;

-- 2020-06-23T05:51:30.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2020-06-23 07:51:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551593
;

-- 2020-06-23T05:51:33.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_printer_config','ConfigHostKey','VARCHAR(60)',null,null)
;

-- 2020-06-23T05:51:33.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_printer_config','ConfigHostKey',null,'NULL',null)
;

-- 2020-06-23T06:00:00.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann', IsTranslated='Y', Name='Client-Zuordnungsschlüssel', PrintName='Client-Zuordnungsschlüssel',Updated=TO_TIMESTAMP('2020-06-23 08:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543929 AND AD_Language='de_CH'
;

-- 2020-06-23T06:00:00.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543929,'de_CH') 
;

-- 2020-06-23T06:00:08.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann',Updated=TO_TIMESTAMP('2020-06-23 08:00:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543929 AND AD_Language='de_DE'
;

-- 2020-06-23T06:00:08.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543929,'de_DE') 
;

-- 2020-06-23T06:00:08.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543929,'de_DE') 
;

-- 2020-06-23T06:00:08.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ConfigHostKey', Name='Host Key', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann', Help=NULL WHERE AD_Element_ID=543929
;

-- 2020-06-23T06:00:08.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ConfigHostKey', Name='Host Key', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann', Help=NULL, AD_Element_ID=543929 WHERE UPPER(ColumnName)='CONFIGHOSTKEY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-06-23T06:00:08.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ConfigHostKey', Name='Host Key', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann', Help=NULL WHERE AD_Element_ID=543929 AND IsCentrallyMaintained='Y'
;

-- 2020-06-23T06:00:08.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Host Key', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543929) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543929)
;

-- 2020-06-23T06:00:08.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Host Key', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543929
;

-- 2020-06-23T06:00:08.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Host Key', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann', Help=NULL WHERE AD_Element_ID = 543929
;

-- 2020-06-23T06:00:08.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Host Key', Description = 'Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543929
;

-- 2020-06-23T06:00:23.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Client-Zuordnungsschlüssel', PrintName='Client-Zuordnungsschlüssel',Updated=TO_TIMESTAMP('2020-06-23 08:00:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543929 AND AD_Language='de_DE'
;

-- 2020-06-23T06:00:23.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543929,'de_DE') 
;

-- 2020-06-23T06:00:23.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543929,'de_DE') 
;

-- 2020-06-23T06:00:23.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ConfigHostKey', Name='Client-Zuordnungsschlüssel', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann', Help=NULL WHERE AD_Element_ID=543929
;

-- 2020-06-23T06:00:23.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ConfigHostKey', Name='Client-Zuordnungsschlüssel', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann', Help=NULL, AD_Element_ID=543929 WHERE UPPER(ColumnName)='CONFIGHOSTKEY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-06-23T06:00:23.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ConfigHostKey', Name='Client-Zuordnungsschlüssel', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann', Help=NULL WHERE AD_Element_ID=543929 AND IsCentrallyMaintained='Y'
;

-- 2020-06-23T06:00:23.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Client-Zuordnungsschlüssel', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543929) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543929)
;

-- 2020-06-23T06:00:23.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Client-Zuordnungsschlüssel', Name='Client-Zuordnungsschlüssel' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543929)
;

-- 2020-06-23T06:00:23.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Client-Zuordnungsschlüssel', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543929
;

-- 2020-06-23T06:00:23.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Client-Zuordnungsschlüssel', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann', Help=NULL WHERE AD_Element_ID = 543929
;

-- 2020-06-23T06:00:23.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Client-Zuordnungsschlüssel', Description = 'Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543929
;

-- 2020-06-23T06:00:33.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-06-23 08:00:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543929 AND AD_Language='en_US'
;

-- 2020-06-23T06:00:33.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543929,'en_US') 
;

-- 2020-06-23T06:01:15.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Key with which a printer configuration can be assigned to a user''s work station',Updated=TO_TIMESTAMP('2020-06-23 08:01:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543929 AND AD_Language='en_US'
;

-- 2020-06-23T06:01:15.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543929,'en_US') 
;

-- 2020-06-23T06:58:29.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='AD_PrinterHW.HostKey=''@ConfigHostKey/''x''@'' OR AD_PrinterHW.OutputType IN (''Attach'', ''Store'')', Description='All "real" hardware printers for context HostKey, plus "Attach" and "Store" printers',Updated=TO_TIMESTAMP('2020-06-23 08:58:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540166
;

-- 2020-06-23T07:01:28.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann. Notwendig bei Druckern mit Ausgabetyp "Queue"',Updated=TO_TIMESTAMP('2020-06-23 09:01:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543929 AND AD_Language='de_CH'
;

-- 2020-06-23T07:01:28.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543929,'de_CH') 
;

-- 2020-06-23T07:01:48.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann. Notwendig bei Druckern mit Ausgabetyp "Queue"',Updated=TO_TIMESTAMP('2020-06-23 09:01:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543929 AND AD_Language='de_DE'
;

-- 2020-06-23T07:01:48.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543929,'de_DE') 
;

-- 2020-06-23T07:01:48.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543929,'de_DE') 
;

-- 2020-06-23T07:01:48.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ConfigHostKey', Name='Client-Zuordnungsschlüssel', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann. Notwendig bei Druckern mit Ausgabetyp "Queue"', Help=NULL WHERE AD_Element_ID=543929
;

-- 2020-06-23T07:01:48.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ConfigHostKey', Name='Client-Zuordnungsschlüssel', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann. Notwendig bei Druckern mit Ausgabetyp "Queue"', Help=NULL, AD_Element_ID=543929 WHERE UPPER(ColumnName)='CONFIGHOSTKEY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-06-23T07:01:48.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ConfigHostKey', Name='Client-Zuordnungsschlüssel', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann. Notwendig bei Druckern mit Ausgabetyp "Queue"', Help=NULL WHERE AD_Element_ID=543929 AND IsCentrallyMaintained='Y'
;

-- 2020-06-23T07:01:48.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Client-Zuordnungsschlüssel', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann. Notwendig bei Druckern mit Ausgabetyp "Queue"', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543929) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543929)
;

-- 2020-06-23T07:01:48.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Client-Zuordnungsschlüssel', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann. Notwendig bei Druckern mit Ausgabetyp "Queue"', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543929
;

-- 2020-06-23T07:01:48.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Client-Zuordnungsschlüssel', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann. Notwendig bei Druckern mit Ausgabetyp "Queue"', Help=NULL WHERE AD_Element_ID = 543929
;

-- 2020-06-23T07:01:48.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Client-Zuordnungsschlüssel', Description = 'Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann. Notwendig bei Druckern mit Ausgabetyp "Queue"', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543929
;

-- 2020-06-23T07:02:06.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Key with which a printer configuration can be assigned to a user''s work station. Required for printers wuth output type "Queue"',Updated=TO_TIMESTAMP('2020-06-23 09:02:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543929 AND AD_Language='en_US'
;

-- 2020-06-23T07:02:06.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543929,'en_US') 
;

-- 2020-06-23T07:02:52.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann. Notwendig bei Druckern mit Ausgabetyp "PDF als Job für Print-Client bereitstellen"',Updated=TO_TIMESTAMP('2020-06-23 09:02:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543929 AND AD_Language='de_CH'
;

-- 2020-06-23T07:02:52.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543929,'de_CH') 
;

-- 2020-06-23T07:02:57.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann. Notwendig bei Druckern mit Ausgabetyp "PDF als Job für Print-Client bereitstellen".',Updated=TO_TIMESTAMP('2020-06-23 09:02:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543929 AND AD_Language='de_DE'
;

-- 2020-06-23T07:02:57.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543929,'de_DE') 
;

-- 2020-06-23T07:02:57.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543929,'de_DE') 
;

-- 2020-06-23T07:02:57.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ConfigHostKey', Name='Client-Zuordnungsschlüssel', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann. Notwendig bei Druckern mit Ausgabetyp "PDF als Job für Print-Client bereitstellen".', Help=NULL WHERE AD_Element_ID=543929
;

-- 2020-06-23T07:02:57.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ConfigHostKey', Name='Client-Zuordnungsschlüssel', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann. Notwendig bei Druckern mit Ausgabetyp "PDF als Job für Print-Client bereitstellen".', Help=NULL, AD_Element_ID=543929 WHERE UPPER(ColumnName)='CONFIGHOSTKEY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-06-23T07:02:57.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ConfigHostKey', Name='Client-Zuordnungsschlüssel', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann. Notwendig bei Druckern mit Ausgabetyp "PDF als Job für Print-Client bereitstellen".', Help=NULL WHERE AD_Element_ID=543929 AND IsCentrallyMaintained='Y'
;

-- 2020-06-23T07:02:57.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Client-Zuordnungsschlüssel', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann. Notwendig bei Druckern mit Ausgabetyp "PDF als Job für Print-Client bereitstellen".', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543929) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543929)
;

-- 2020-06-23T07:02:57.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Client-Zuordnungsschlüssel', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann. Notwendig bei Druckern mit Ausgabetyp "PDF als Job für Print-Client bereitstellen".', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543929
;

-- 2020-06-23T07:02:57.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Client-Zuordnungsschlüssel', Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann. Notwendig bei Druckern mit Ausgabetyp "PDF als Job für Print-Client bereitstellen".', Help=NULL WHERE AD_Element_ID = 543929
;

-- 2020-06-23T07:02:57.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Client-Zuordnungsschlüssel', Description = 'Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann. Notwendig bei Druckern mit Ausgabetyp "PDF als Job für Print-Client bereitstellen".', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543929
;

-- 2020-06-23T07:03:01.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Schlüssel, mit dem eine Drucker-Zuordnung dem Arbeitsplatz eines Benutzers zugeordnet werden kann. Notwendig bei Druckern mit Ausgabetyp "PDF als Job für Print-Client bereitstellen".',Updated=TO_TIMESTAMP('2020-06-23 09:03:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543929 AND AD_Language='de_CH'
;

-- 2020-06-23T07:03:01.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543929,'de_CH') 
;

-- 2020-06-23T07:03:26.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Key with which a printer configuration can be assigned to a user''s work station. Required for printers wuth output type "Create print job for printing-client"',Updated=TO_TIMESTAMP('2020-06-23 09:03:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543929 AND AD_Language='en_US'
;

-- 2020-06-23T07:03:26.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543929,'en_US') 
;

-- 2020-06-23T08:23:28.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='AD_PrinterHW.HostKey=''@ConfigHostKey/x@'' OR AD_PrinterHW.OutputType IN (''Attach'', ''Store'')',Updated=TO_TIMESTAMP('2020-06-23 10:23:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540166
;

-- 2020-06-23T08:34:23.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='AD_PrinterHW',Updated=TO_TIMESTAMP('2020-06-23 10:34:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540167
;

-- 2020-06-23T08:42:48.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@SQL=COALESCE( ( select (max(t.TrayNumber) +10) from AD_PrinterHW_MediaTray t where t.AD_PrinterHW_ID=@AD_PrinterHW_ID/0@ ), 10)',Updated=TO_TIMESTAMP('2020-06-23 10:42:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548092
;

-- 2020-06-23T08:48:14.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsDirectProcessQueueItem',Updated=TO_TIMESTAMP('2020-06-23 10:48:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542655
;

-- 2020-06-23T08:48:14.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsDirectProcessQueueItem', Name='Sofort Druckjob erstellen', Description='Entscheidet, ob beim Erstellen eines Druck-Warteschlange Eintrags auch sofort ein einzelner Druckjob erstellt wird', Help=NULL WHERE AD_Element_ID=542655
;

-- 2020-06-23T08:48:14.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsDirectProcessQueueItem', Name='Sofort Druckjob erstellen', Description='Entscheidet, ob beim Erstellen eines Druck-Warteschlange Eintrags auch sofort ein einzelner Druckjob erstellt wird', Help=NULL, AD_Element_ID=542655 WHERE UPPER(ColumnName)='ISDIRECTPROCESSQUEUEITEM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-06-23T08:48:14.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsDirectProcessQueueItem', Name='Sofort Druckjob erstellen', Description='Entscheidet, ob beim Erstellen eines Druck-Warteschlange Eintrags auch sofort ein einzelner Druckjob erstellt wird', Help=NULL WHERE AD_Element_ID=542655 AND IsCentrallyMaintained='Y'
;

/* DDL */ select db_alter_table('C_Doc_Outbound_Config', 'ALTER TABLE C_Doc_Outbound_Config RENAME COLUMN IsCreatePrintJob TO IsDirectProcessQueueItem');
/* DDL */ select db_alter_table('AD_Archive', 'ALTER TABLE AD_Archive RENAME COLUMN IsCreatePrintJob TO IsDirectProcessQueueItem');

-- 2020-06-23T08:52:25.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Entscheidet, ob je nach Konfiguration sofort ein Druckjob erstellt oder das PDF lokal gespeichert wird.', IsTranslated='Y', Name='Warteschlangen-Eintrag sofort verarbeiten', PrintName='Warteschlangen-Eintrag sofort verarbeiten',Updated=TO_TIMESTAMP('2020-06-23 10:52:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542655 AND AD_Language='de_CH'
;

-- 2020-06-23T08:52:25.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542655,'de_CH') 
;

-- 2020-06-23T08:52:32.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Entscheidet, ob je nach Konfiguration sofort ein Druckjob erstellt oder das PDF lokal gespeichert wird.',Updated=TO_TIMESTAMP('2020-06-23 10:52:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542655 AND AD_Language='de_DE'
;

-- 2020-06-23T08:52:32.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542655,'de_DE') 
;

-- 2020-06-23T08:52:32.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542655,'de_DE') 
;

-- 2020-06-23T08:52:32.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsDirectProcessQueueItem', Name='Sofort Druckjob erstellen', Description='Entscheidet, ob je nach Konfiguration sofort ein Druckjob erstellt oder das PDF lokal gespeichert wird.', Help=NULL WHERE AD_Element_ID=542655
;

-- 2020-06-23T08:52:32.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsDirectProcessQueueItem', Name='Sofort Druckjob erstellen', Description='Entscheidet, ob je nach Konfiguration sofort ein Druckjob erstellt oder das PDF lokal gespeichert wird.', Help=NULL, AD_Element_ID=542655 WHERE UPPER(ColumnName)='ISDIRECTPROCESSQUEUEITEM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-06-23T08:52:32.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsDirectProcessQueueItem', Name='Sofort Druckjob erstellen', Description='Entscheidet, ob je nach Konfiguration sofort ein Druckjob erstellt oder das PDF lokal gespeichert wird.', Help=NULL WHERE AD_Element_ID=542655 AND IsCentrallyMaintained='Y'
;

-- 2020-06-23T08:52:32.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Sofort Druckjob erstellen', Description='Entscheidet, ob je nach Konfiguration sofort ein Druckjob erstellt oder das PDF lokal gespeichert wird.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542655) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542655)
;

-- 2020-06-23T08:52:32.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Sofort Druckjob erstellen', Description='Entscheidet, ob je nach Konfiguration sofort ein Druckjob erstellt oder das PDF lokal gespeichert wird.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542655
;

-- 2020-06-23T08:52:32.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Sofort Druckjob erstellen', Description='Entscheidet, ob je nach Konfiguration sofort ein Druckjob erstellt oder das PDF lokal gespeichert wird.', Help=NULL WHERE AD_Element_ID = 542655
;

-- 2020-06-23T08:52:32.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Sofort Druckjob erstellen', Description = 'Entscheidet, ob je nach Konfiguration sofort ein Druckjob erstellt oder das PDF lokal gespeichert wird.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542655
;

-- 2020-06-23T08:52:48.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Warteschlangen-Eintrag sofort verarbeiten', PrintName='Warteschlangen-Eintrag sofort verarbeiten',Updated=TO_TIMESTAMP('2020-06-23 10:52:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542655 AND AD_Language='de_DE'
;

-- 2020-06-23T08:52:48.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542655,'de_DE') 
;

-- 2020-06-23T08:52:48.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542655,'de_DE') 
;

-- 2020-06-23T08:52:48.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsDirectProcessQueueItem', Name='Warteschlangen-Eintrag sofort verarbeiten', Description='Entscheidet, ob je nach Konfiguration sofort ein Druckjob erstellt oder das PDF lokal gespeichert wird.', Help=NULL WHERE AD_Element_ID=542655
;

-- 2020-06-23T08:52:48.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsDirectProcessQueueItem', Name='Warteschlangen-Eintrag sofort verarbeiten', Description='Entscheidet, ob je nach Konfiguration sofort ein Druckjob erstellt oder das PDF lokal gespeichert wird.', Help=NULL, AD_Element_ID=542655 WHERE UPPER(ColumnName)='ISDIRECTPROCESSQUEUEITEM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-06-23T08:52:48.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsDirectProcessQueueItem', Name='Warteschlangen-Eintrag sofort verarbeiten', Description='Entscheidet, ob je nach Konfiguration sofort ein Druckjob erstellt oder das PDF lokal gespeichert wird.', Help=NULL WHERE AD_Element_ID=542655 AND IsCentrallyMaintained='Y'
;

-- 2020-06-23T08:52:48.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Warteschlangen-Eintrag sofort verarbeiten', Description='Entscheidet, ob je nach Konfiguration sofort ein Druckjob erstellt oder das PDF lokal gespeichert wird.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542655) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542655)
;

-- 2020-06-23T08:52:48.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Warteschlangen-Eintrag sofort verarbeiten', Name='Warteschlangen-Eintrag sofort verarbeiten' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542655)
;

-- 2020-06-23T08:52:48.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Warteschlangen-Eintrag sofort verarbeiten', Description='Entscheidet, ob je nach Konfiguration sofort ein Druckjob erstellt oder das PDF lokal gespeichert wird.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542655
;

-- 2020-06-23T08:52:48.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Warteschlangen-Eintrag sofort verarbeiten', Description='Entscheidet, ob je nach Konfiguration sofort ein Druckjob erstellt oder das PDF lokal gespeichert wird.', Help=NULL WHERE AD_Element_ID = 542655
;

-- 2020-06-23T08:52:48.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Warteschlangen-Eintrag sofort verarbeiten', Description = 'Entscheidet, ob je nach Konfiguration sofort ein Druckjob erstellt oder das PDF lokal gespeichert wird.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542655
;

-- 2020-06-23T08:53:09.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Directly proces queue item', PrintName='Directly proces queue item',Updated=TO_TIMESTAMP('2020-06-23 10:53:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542655 AND AD_Language='en_US'
;

-- 2020-06-23T08:53:09.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542655,'en_US') 
;

-- 2020-06-23T08:53:19.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='',Updated=TO_TIMESTAMP('2020-06-23 10:53:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542655 AND AD_Language='nl_NL'
;

-- 2020-06-23T08:53:19.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542655,'nl_NL') 
;

-- 2020-06-23T08:54:11.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Decides whether according to config a print-job is created or the PDF is stored locally right away.',Updated=TO_TIMESTAMP('2020-06-23 10:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542655 AND AD_Language='en_US'
;

-- 2020-06-23T08:54:11.042Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542655,'en_US') 
;

-- 2020-06-23T16:24:51.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2020-06-23 18:24:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551302
;

-- 2020-06-23T16:25:04.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-23 18:25:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564768
;

-- 2020-06-23T16:25:04.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2020-06-23 18:25:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564769
;

-- 2020-06-23T16:25:04.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2020-06-23 18:25:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564770
;

-- 2020-06-23T16:25:04.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2020-06-23 18:25:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564771
;

-- 2020-06-23T16:26:13.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2020-06-23 18:26:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564758
;

-- 2020-06-23T16:26:14.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2020-06-23 18:26:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564759
;

-- 2020-06-23T16:26:14.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2020-06-23 18:26:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564760
;

-- 2020-06-23T16:26:14.011Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2020-06-23 18:26:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564761
;

-- 2020-06-23T16:26:14.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2020-06-23 18:26:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564762
;

-- 2020-06-23T16:26:14.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2020-06-23 18:26:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564763
;

-- 2020-06-23T16:26:14.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2020-06-23 18:26:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564771
;
