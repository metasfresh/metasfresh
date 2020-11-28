-- 2019-04-09T11:13:26.377
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Shows if the partner is active',Updated=TO_TIMESTAMP('2019-04-09 11:13:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576562 AND AD_Language='en_US'
;

-- 2019-04-09T11:13:26.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576562,'en_US') 
;

-- 2019-04-09T11:13:42.804
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Shows if the partner is active',Updated=TO_TIMESTAMP('2019-04-09 11:13:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576562 AND AD_Language='en_US'
;

-- 2019-04-09T11:13:42.804
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576562,'en_US') 
;

-- 2019-04-09T11:14:02.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='',Updated=TO_TIMESTAMP('2019-04-09 11:14:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576562 AND AD_Language='en_US'
;

-- 2019-04-09T11:14:02.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576562,'en_US') 
;

-- 2019-04-09T11:24:05.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Zeigt an, ob der Geschäftspartner-Datensatz in metasfresh aktiviert ist',Updated=TO_TIMESTAMP('2019-04-09 11:24:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576562
;

-- 2019-04-09T11:24:05.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsActivePartner', Name='Aktiv', Description='Zeigt an, ob der Geschäftspartner-Datensatz in metasfresh aktiviert ist', Help=NULL WHERE AD_Element_ID=576562
;

-- 2019-04-09T11:24:05.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsActivePartner', Name='Aktiv', Description='Zeigt an, ob der Geschäftspartner-Datensatz in metasfresh aktiviert ist', Help=NULL, AD_Element_ID=576562 WHERE UPPER(ColumnName)='ISACTIVEPARTNER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-09T11:24:05.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsActivePartner', Name='Aktiv', Description='Zeigt an, ob der Geschäftspartner-Datensatz in metasfresh aktiviert ist', Help=NULL WHERE AD_Element_ID=576562 AND IsCentrallyMaintained='Y'
;

-- 2019-04-09T11:24:05.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Aktiv', Description='Zeigt an, ob der Geschäftspartner-Datensatz in metasfresh aktiviert ist', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576562) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576562)
;

-- 2019-04-09T11:24:05.990
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Aktiv', Description='Zeigt an, ob der Geschäftspartner-Datensatz in metasfresh aktiviert ist', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576562
;

-- 2019-04-09T11:24:05.990
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Aktiv', Description='Zeigt an, ob der Geschäftspartner-Datensatz in metasfresh aktiviert ist', Help=NULL WHERE AD_Element_ID = 576562
;

-- 2019-04-09T11:24:05.990
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Aktiv', Description = 'Zeigt an, ob der Geschäftspartner-Datensatz in metasfresh aktiviert ist', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576562
;

