-- 2019-12-18T11:33:18.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Buchungsstatus', Help='Zeigt den Verbuchungsstatus der Hauptbuchpositionen an.', Name='Buchungsstatus', PrintName='Buchungsstatus',Updated=TO_TIMESTAMP('2019-12-18 13:33:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1308 AND AD_Language='de_DE'
;

-- 2019-12-18T11:33:18.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1308,'de_DE') 
;

-- 2019-12-18T11:33:18.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1308,'de_DE') 
;

-- 2019-12-18T11:33:18.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Posted', Name='Buchungsstatus', Description='Buchungsstatus', Help='Zeigt den Verbuchungsstatus der Hauptbuchpositionen an.' WHERE AD_Element_ID=1308
;

-- 2019-12-18T11:33:18.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Posted', Name='Buchungsstatus', Description='Buchungsstatus', Help='Zeigt den Verbuchungsstatus der Hauptbuchpositionen an.', AD_Element_ID=1308 WHERE UPPER(ColumnName)='POSTED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-18T11:33:18.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Posted', Name='Buchungsstatus', Description='Buchungsstatus', Help='Zeigt den Verbuchungsstatus der Hauptbuchpositionen an.' WHERE AD_Element_ID=1308 AND IsCentrallyMaintained='Y'
;

-- 2019-12-18T11:33:18.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Buchungsstatus', Description='Buchungsstatus', Help='Zeigt den Verbuchungsstatus der Hauptbuchpositionen an.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1308) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1308)
;

-- 2019-12-18T11:33:19.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Buchungsstatus', Name='Buchungsstatus' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1308)
;

-- 2019-12-18T11:33:19.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Buchungsstatus', Description='Buchungsstatus', Help='Zeigt den Verbuchungsstatus der Hauptbuchpositionen an.', CommitWarning = NULL WHERE AD_Element_ID = 1308
;

-- 2019-12-18T11:33:19.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Buchungsstatus', Description='Buchungsstatus', Help='Zeigt den Verbuchungsstatus der Hauptbuchpositionen an.' WHERE AD_Element_ID = 1308
;

-- 2019-12-18T11:33:19.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Buchungsstatus', Description = 'Buchungsstatus', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1308
;

-- 2019-12-18T11:33:48.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Buchungsstatus', Help='Zeigt den Verbuchungsstatus der Hauptbuchpositionen an.', IsTranslated='Y', Name='Buchungsstatus', PrintName='Buchungsstatus',Updated=TO_TIMESTAMP('2019-12-18 13:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1308 AND AD_Language='de_CH'
;

-- 2019-12-18T11:33:48.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1308,'de_CH') 
;

-- 2019-12-18T11:36:58.195Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='The Posted field indicates the status of the Generation of General Ledger Accounting Lines.',Updated=TO_TIMESTAMP('2019-12-18 13:36:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1308 AND AD_Language='fr_CH'
;

-- 2019-12-18T11:36:58.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1308,'fr_CH') 
;

-- 2019-12-18T11:37:23.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='The ''Posting Status'' field indicates the status of the General Ledger accounting lines.', Name='Posting status', PrintName='Posting status',Updated=TO_TIMESTAMP('2019-12-18 13:37:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1308 AND AD_Language='fr_CH'
;

-- 2019-12-18T11:37:23.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1308,'fr_CH') 
;

-- 2019-12-18T11:37:29.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='The ''Posting Status'' field indicates the status of the General Ledger accounting lines.',Updated=TO_TIMESTAMP('2019-12-18 13:37:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1308 AND AD_Language='it_CH'
;

-- 2019-12-18T11:37:29.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1308,'it_CH') 
;

-- 2019-12-18T11:37:33.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='The ''Posting Status'' field indicates the status of the General Ledger accounting lines.',Updated=TO_TIMESTAMP('2019-12-18 13:37:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1308 AND AD_Language='en_GB'
;

-- 2019-12-18T11:37:33.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1308,'en_GB') 
;

-- 2019-12-18T11:37:39.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='The ''Posting Status'' field indicates the status of the General Ledger accounting lines.',Updated=TO_TIMESTAMP('2019-12-18 13:37:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1308 AND AD_Language='en_US'
;

-- 2019-12-18T11:37:39.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1308,'en_US') 
;

-- 2019-12-18T11:37:43.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='The ''Posting Status'' field indicates the status of the General Ledger accounting lines.',Updated=TO_TIMESTAMP('2019-12-18 13:37:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1308 AND AD_Language='nl_NL'
;

-- 2019-12-18T11:37:43.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1308,'nl_NL') 
;

-- 2019-12-18T11:37:49.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Posting status', PrintName='Posting status',Updated=TO_TIMESTAMP('2019-12-18 13:37:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1308 AND AD_Language='nl_NL'
;

-- 2019-12-18T11:37:49.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1308,'nl_NL') 
;

-- 2019-12-18T11:37:54.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Posting status', PrintName='Posting status',Updated=TO_TIMESTAMP('2019-12-18 13:37:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1308 AND AD_Language='en_US'
;

-- 2019-12-18T11:37:54.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1308,'en_US') 
;

-- 2019-12-18T11:38:00.649Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Posting status', PrintName='Posting status',Updated=TO_TIMESTAMP('2019-12-18 13:38:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1308 AND AD_Language='en_GB'
;

-- 2019-12-18T11:38:00.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1308,'en_GB') 
;

-- 2019-12-18T11:38:04.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Posting status', PrintName='Posting status',Updated=TO_TIMESTAMP('2019-12-18 13:38:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1308 AND AD_Language='it_CH'
;

-- 2019-12-18T11:38:04.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1308,'it_CH') 
;

-- 2019-12-18T11:38:24.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-18 13:38:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1308 AND AD_Language='de_DE'
;

-- 2019-12-18T11:38:24.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1308,'de_DE') 
;

-- 2019-12-18T11:38:24.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1308,'de_DE') 
;

