-- Element: IsReplicationTrxError
-- 2023-01-30T10:48:59.620Z
UPDATE AD_Element_Trl SET Name='EDI Import Error', PrintName='EDI Import Error',Updated=TO_TIMESTAMP('2023-01-30 12:48:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581956 AND AD_Language='en_US'
;

-- 2023-01-30T10:48:59.670Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581956,'en_US') 
;

-- Element: IsReplicationTrxError
-- 2023-01-30T10:49:03.184Z
UPDATE AD_Element_Trl SET Name='EDI-Importfehler', PrintName='EDI-Importfehler',Updated=TO_TIMESTAMP('2023-01-30 12:49:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581956 AND AD_Language='de_DE'
;

-- 2023-01-30T10:49:03.186Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581956,'de_DE') 
;

-- 2023-01-30T10:49:03.195Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581956,'de_DE') 
;

-- 2023-01-30T10:49:03.203Z
UPDATE AD_Column SET ColumnName='IsReplicationTrxError', Name='EDI-Importfehler', Description=NULL, Help=NULL WHERE AD_Element_ID=581956
;

-- 2023-01-30T10:49:03.204Z
UPDATE AD_Process_Para SET ColumnName='IsReplicationTrxError', Name='EDI-Importfehler', Description=NULL, Help=NULL, AD_Element_ID=581956 WHERE UPPER(ColumnName)='ISREPLICATIONTRXERROR' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-01-30T10:49:03.206Z
UPDATE AD_Process_Para SET ColumnName='IsReplicationTrxError', Name='EDI-Importfehler', Description=NULL, Help=NULL WHERE AD_Element_ID=581956 AND IsCentrallyMaintained='Y'
;

-- 2023-01-30T10:49:03.206Z
UPDATE AD_Field SET Name='EDI-Importfehler', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581956) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581956)
;

-- 2023-01-30T10:49:03.232Z
UPDATE AD_PrintFormatItem pi SET PrintName='EDI-Importfehler', Name='EDI-Importfehler' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581956)
;

-- 2023-01-30T10:49:03.233Z
UPDATE AD_Tab SET Name='EDI-Importfehler', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581956
;

-- 2023-01-30T10:49:03.235Z
UPDATE AD_WINDOW SET Name='EDI-Importfehler', Description=NULL, Help=NULL WHERE AD_Element_ID = 581956
;

-- 2023-01-30T10:49:03.236Z
UPDATE AD_Menu SET   Name = 'EDI-Importfehler', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581956
;

-- Element: IsReplicationTrxError
-- 2023-01-30T10:49:05.665Z
UPDATE AD_Element_Trl SET Name='EDI-Importfehler', PrintName='EDI-Importfehler',Updated=TO_TIMESTAMP('2023-01-30 12:49:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581956 AND AD_Language='de_CH'
;

-- 2023-01-30T10:49:05.668Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581956,'de_CH') 
;

-- Element: IsReplicationTrxError
-- 2023-01-30T10:49:16.359Z
UPDATE AD_Element_Trl SET Name='EDI-Importfehler', PrintName='EDI-Importfehler',Updated=TO_TIMESTAMP('2023-01-30 12:49:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581956 AND AD_Language='nl_NL'
;

-- 2023-01-30T10:49:16.360Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581956,'nl_NL') 
;

-- Element: IsReplicationTrxFinished
-- 2023-01-30T10:49:44.283Z
UPDATE AD_Element_Trl SET Name='EDI-Import abgeschlossen', PrintName='EDI-Import abgeschlossen',Updated=TO_TIMESTAMP('2023-01-30 12:49:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581955 AND AD_Language='de_CH'
;

-- 2023-01-30T10:49:44.285Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581955,'de_CH') 
;

-- Element: IsReplicationTrxFinished
-- 2023-01-30T10:49:47.231Z
UPDATE AD_Element_Trl SET Name='EDI-Import abgeschlossen', PrintName='EDI-Import abgeschlossen',Updated=TO_TIMESTAMP('2023-01-30 12:49:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581955 AND AD_Language='de_DE'
;

-- 2023-01-30T10:49:47.232Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581955,'de_DE') 
;

-- 2023-01-30T10:49:47.243Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581955,'de_DE') 
;

-- 2023-01-30T10:49:47.245Z
UPDATE AD_Column SET ColumnName='IsReplicationTrxFinished', Name='EDI-Import abgeschlossen', Description=NULL, Help=NULL WHERE AD_Element_ID=581955
;

-- 2023-01-30T10:49:47.246Z
UPDATE AD_Process_Para SET ColumnName='IsReplicationTrxFinished', Name='EDI-Import abgeschlossen', Description=NULL, Help=NULL, AD_Element_ID=581955 WHERE UPPER(ColumnName)='ISREPLICATIONTRXFINISHED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-01-30T10:49:47.247Z
UPDATE AD_Process_Para SET ColumnName='IsReplicationTrxFinished', Name='EDI-Import abgeschlossen', Description=NULL, Help=NULL WHERE AD_Element_ID=581955 AND IsCentrallyMaintained='Y'
;

-- 2023-01-30T10:49:47.248Z
UPDATE AD_Field SET Name='EDI-Import abgeschlossen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581955) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581955)
;

-- 2023-01-30T10:49:47.270Z
UPDATE AD_PrintFormatItem pi SET PrintName='EDI-Import abgeschlossen', Name='EDI-Import abgeschlossen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581955)
;

-- 2023-01-30T10:49:47.271Z
UPDATE AD_Tab SET Name='EDI-Import abgeschlossen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581955
;

-- 2023-01-30T10:49:47.273Z
UPDATE AD_WINDOW SET Name='EDI-Import abgeschlossen', Description=NULL, Help=NULL WHERE AD_Element_ID = 581955
;

-- 2023-01-30T10:49:47.274Z
UPDATE AD_Menu SET   Name = 'EDI-Import abgeschlossen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581955
;

-- Element: IsReplicationTrxFinished
-- 2023-01-30T10:49:50.446Z
UPDATE AD_Element_Trl SET Name='EDI-Import abgeschlossen', PrintName='EDI-Import abgeschlossen',Updated=TO_TIMESTAMP('2023-01-30 12:49:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581955 AND AD_Language='nl_NL'
;

-- 2023-01-30T10:49:50.448Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581955,'nl_NL') 
;

-- Element: IsReplicationTrxFinished
-- 2023-01-30T10:49:55.041Z
UPDATE AD_Element_Trl SET Name='EDI Import Finished', PrintName='EDI Import Finished',Updated=TO_TIMESTAMP('2023-01-30 12:49:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581955 AND AD_Language='en_US'
;

-- 2023-01-30T10:49:55.043Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581955,'en_US') 
;

-- Element: ReplicationTrxErrorMsg
-- 2023-01-30T10:50:28.535Z
UPDATE AD_Element_Trl SET Name='EDI-Import-Fehlermeldung', PrintName='EDI-Import-Fehlermeldung',Updated=TO_TIMESTAMP('2023-01-30 12:50:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581957 AND AD_Language='de_CH'
;

-- 2023-01-30T10:50:28.537Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581957,'de_CH') 
;

-- Element: ReplicationTrxErrorMsg
-- 2023-01-30T10:50:32.062Z
UPDATE AD_Element_Trl SET Name='EDI-Import-Fehlermeldung', PrintName='EDI-Import-Fehlermeldung',Updated=TO_TIMESTAMP('2023-01-30 12:50:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581957 AND AD_Language='de_DE'
;

-- 2023-01-30T10:50:32.063Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581957,'de_DE') 
;

-- 2023-01-30T10:50:32.074Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581957,'de_DE') 
;

-- 2023-01-30T10:50:32.075Z
UPDATE AD_Column SET ColumnName='ReplicationTrxErrorMsg', Name='EDI-Import-Fehlermeldung', Description=NULL, Help=NULL WHERE AD_Element_ID=581957
;

-- 2023-01-30T10:50:32.076Z
UPDATE AD_Process_Para SET ColumnName='ReplicationTrxErrorMsg', Name='EDI-Import-Fehlermeldung', Description=NULL, Help=NULL, AD_Element_ID=581957 WHERE UPPER(ColumnName)='REPLICATIONTRXERRORMSG' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-01-30T10:50:32.077Z
UPDATE AD_Process_Para SET ColumnName='ReplicationTrxErrorMsg', Name='EDI-Import-Fehlermeldung', Description=NULL, Help=NULL WHERE AD_Element_ID=581957 AND IsCentrallyMaintained='Y'
;

-- 2023-01-30T10:50:32.078Z
UPDATE AD_Field SET Name='EDI-Import-Fehlermeldung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581957) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581957)
;

-- 2023-01-30T10:50:32.096Z
UPDATE AD_PrintFormatItem pi SET PrintName='EDI-Import-Fehlermeldung', Name='EDI-Import-Fehlermeldung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581957)
;

-- 2023-01-30T10:50:32.097Z
UPDATE AD_Tab SET Name='EDI-Import-Fehlermeldung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581957
;

-- 2023-01-30T10:50:32.099Z
UPDATE AD_WINDOW SET Name='EDI-Import-Fehlermeldung', Description=NULL, Help=NULL WHERE AD_Element_ID = 581957
;

-- 2023-01-30T10:50:32.100Z
UPDATE AD_Menu SET   Name = 'EDI-Import-Fehlermeldung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581957
;

-- Element: ReplicationTrxErrorMsg
-- 2023-01-30T10:50:37.075Z
UPDATE AD_Element_Trl SET Name='EDI-Import-Fehlermeldung', PrintName='EDI-Import-Fehlermeldung',Updated=TO_TIMESTAMP('2023-01-30 12:50:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581957 AND AD_Language='nl_NL'
;

-- 2023-01-30T10:50:37.076Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581957,'nl_NL') 
;

-- Element: ReplicationTrxErrorMsg
-- 2023-01-30T10:50:41.669Z
UPDATE AD_Element_Trl SET Name='EDI Import Error Message', PrintName='EDI Import Error Message',Updated=TO_TIMESTAMP('2023-01-30 12:50:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581957 AND AD_Language='en_US'
;

-- 2023-01-30T10:50:41.671Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581957,'en_US') 
;

