-- 2021-06-11T03:44:27.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value) VALUES (0,545035,0,TO_TIMESTAMP('2021-06-11 06:44:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Prozess-Not-Available','No alberta config','E',TO_TIMESTAMP('2021-06-11 06:44:27','YYYY-MM-DD HH24:MI:SS'),100,'ALBERTA_NO_CONFIG_AVAILABLE')
;

-- 2021-06-11T03:44:27.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545035 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-06-11T03:44:40.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgTip='Keine Alberta-Konfig',Updated=TO_TIMESTAMP('2021-06-11 06:44:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545035
;

-- 2021-06-11T03:44:43.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgTip='Keine Alberta-Konfig',Updated=TO_TIMESTAMP('2021-06-11 06:44:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545035
;

-- 2021-06-11T03:44:47.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgTip='Keine Alberta-Konfig',Updated=TO_TIMESTAMP('2021-06-11 06:44:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545035
;

-- 2021-06-11T03:48:17.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn gesetzt, werden unabhängig von der Auswahl alle Geschäftspartner synchrionisiert. Es wird ggf. trotzdem nach Organisation gefiltert', Name='Auswahl ignorieren', PrintName='Auswahl ignorieren',Updated=TO_TIMESTAMP('2021-06-11 06:48:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579313 AND AD_Language='de_CH'
;

-- 2021-06-11T03:48:17.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579313,'de_CH') 
;

-- 2021-06-11T03:48:28.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn gesetzt, werden unabhängig von der Auswahl alle Geschäftspartner synchrionisiert. Es wird ggf. trotzdem nach Organisation gefiltert', Name='Auswahl ignorieren', PrintName='Auswahl ignorieren',Updated=TO_TIMESTAMP('2021-06-11 06:48:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579313 AND AD_Language='de_DE'
;

-- 2021-06-11T03:48:28.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579313,'de_DE') 
;

-- 2021-06-11T03:48:28.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579313,'de_DE') 
;

-- 2021-06-11T03:48:28.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsIgnoreSelectionSyncAll', Name='Auswahl ignorieren', Description='Wenn gesetzt, werden unabhängig von der Auswahl alle Geschäftspartner synchrionisiert. Es wird ggf. trotzdem nach Organisation gefiltert', Help=NULL WHERE AD_Element_ID=579313
;

-- 2021-06-11T03:48:28.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsIgnoreSelectionSyncAll', Name='Auswahl ignorieren', Description='Wenn gesetzt, werden unabhängig von der Auswahl alle Geschäftspartner synchrionisiert. Es wird ggf. trotzdem nach Organisation gefiltert', Help=NULL, AD_Element_ID=579313 WHERE UPPER(ColumnName)='ISIGNORESELECTIONSYNCALL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-11T03:48:28.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsIgnoreSelectionSyncAll', Name='Auswahl ignorieren', Description='Wenn gesetzt, werden unabhängig von der Auswahl alle Geschäftspartner synchrionisiert. Es wird ggf. trotzdem nach Organisation gefiltert', Help=NULL WHERE AD_Element_ID=579313 AND IsCentrallyMaintained='Y'
;

-- 2021-06-11T03:48:28.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Auswahl ignorieren', Description='Wenn gesetzt, werden unabhängig von der Auswahl alle Geschäftspartner synchrionisiert. Es wird ggf. trotzdem nach Organisation gefiltert', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579313) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579313)
;

-- 2021-06-11T03:48:29.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Auswahl ignorieren', Name='Auswahl ignorieren' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579313)
;

-- 2021-06-11T03:48:29.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Auswahl ignorieren', Description='Wenn gesetzt, werden unabhängig von der Auswahl alle Geschäftspartner synchrionisiert. Es wird ggf. trotzdem nach Organisation gefiltert', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579313
;

-- 2021-06-11T03:48:29.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Auswahl ignorieren', Description='Wenn gesetzt, werden unabhängig von der Auswahl alle Geschäftspartner synchrionisiert. Es wird ggf. trotzdem nach Organisation gefiltert', Help=NULL WHERE AD_Element_ID = 579313
;

-- 2021-06-11T03:48:29.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Auswahl ignorieren', Description = 'Wenn gesetzt, werden unabhängig von der Auswahl alle Geschäftspartner synchrionisiert. Es wird ggf. trotzdem nach Organisation gefiltert', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579313
;

-- 2021-06-11T03:48:45.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn gesetzt, werden unabhängig von der Auswahl alle Geschäftspartner synchrionisiert. Es wird ggf. trotzdem nach Organisation gefiltert', Name='Auswahl ignorieren', PrintName='Auswahl ignorieren',Updated=TO_TIMESTAMP('2021-06-11 06:48:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579313 AND AD_Language='nl_NL'
;

-- 2021-06-11T03:48:45.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579313,'nl_NL') 
;

-- 2021-06-11T03:49:59.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=10,Updated=TO_TIMESTAMP('2021-06-11 06:49:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542023
;

-- 2021-06-11T03:49:59.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsActive='Y', SeqNo=20,Updated=TO_TIMESTAMP('2021-06-11 06:49:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542022
;

-- 2021-06-11T04:02:56.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=NULL, Description='If set, then only partners from the given organisation are synched', Help='', Name='Organisation',Updated=TO_TIMESTAMP('2021-06-11 07:02:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542024
;

-- 2021-06-11T04:04:30.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Description='Wenn gesetzt werden generell nur Geschäftspartner der jeweiligen Organisation synchronisiert', Help='', Name='Organisation',Updated=TO_TIMESTAMP('2021-06-11 07:04:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_Para_ID=542024
;

-- 2021-06-11T04:04:45.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Description='Wenn gesetzt werden generell nur Geschäftspartner der jeweiligen Organisation synchronisiert', Help='', Name='Organisation',Updated=TO_TIMESTAMP('2021-06-11 07:04:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_Para_ID=542024
;

-- 2021-06-11T04:04:58.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Description='If set, then only partners from the given organisation are synched', Help='', Name='Organisation',Updated=TO_TIMESTAMP('2021-06-11 07:04:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=542024
;

-- 2021-06-11T04:05:14.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para_Trl SET Description='Wenn gesetzt werden generell nur Geschäftspartner der jeweiligen Organisation synchronisiert', Help='', Name='Organisation',Updated=TO_TIMESTAMP('2021-06-11 07:05:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_Para_ID=542024
;
