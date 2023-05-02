-- 2022-04-05T15:31:42.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Einmalanschriften sind "flüchtige" Geschäftspartneradressen, die über die REST-API erstellt werden. Wenn eine Adresse als Einmalanschrift markiert ist, wird sie beim Import von Dokumenten in das metafresh-System verwendet. Sie steht aber nicht beim Erstellen neuer Dokumente in der metasfresh-Benutzeroberfläche zur Auswahl.',Updated=TO_TIMESTAMP('2022-04-05 18:31:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580745 AND AD_Language='de_CH'
;

-- 2022-04-05T15:31:42.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580745,'de_CH') 
;

-- 2022-04-05T15:50:21.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Einmalanschriften sind "flüchtige" Geschäftspartneradressen, die über die REST-API erstellt werden. Wenn eine Adresse als Einmalanschrift markiert ist, wird sie beim Import von Dokumenten in das metafresh-System verwendet. Sie steht aber nicht beim Erstellen neuer Dokumente in der metasfresh-Benutzeroberfläche zur Auswahl.',Updated=TO_TIMESTAMP('2022-04-05 18:50:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580745 AND AD_Language='de_DE'
;

-- 2022-04-05T15:50:21.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580745,'de_DE') 
;

-- 2022-04-05T15:50:21.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580745,'de_DE') 
;

-- 2022-04-05T15:50:21.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsEphemeral', Name='Einmalanschrift', Description='Einmalanschriften sind "flüchtige" Geschäftspartneradressen, die über die REST-API erstellt werden. Wenn eine Adresse als Einmalanschrift markiert ist, wird sie beim Import von Dokumenten in das metafresh-System verwendet. Sie steht aber nicht beim Erstellen neuer Dokumente in der metasfresh-Benutzeroberfläche zur Auswahl.', Help=NULL WHERE AD_Element_ID=580745
;

-- 2022-04-05T15:50:21.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsEphemeral', Name='Einmalanschrift', Description='Einmalanschriften sind "flüchtige" Geschäftspartneradressen, die über die REST-API erstellt werden. Wenn eine Adresse als Einmalanschrift markiert ist, wird sie beim Import von Dokumenten in das metafresh-System verwendet. Sie steht aber nicht beim Erstellen neuer Dokumente in der metasfresh-Benutzeroberfläche zur Auswahl.', Help=NULL, AD_Element_ID=580745 WHERE UPPER(ColumnName)='ISEPHEMERAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-04-05T15:50:21.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsEphemeral', Name='Einmalanschrift', Description='Einmalanschriften sind "flüchtige" Geschäftspartneradressen, die über die REST-API erstellt werden. Wenn eine Adresse als Einmalanschrift markiert ist, wird sie beim Import von Dokumenten in das metafresh-System verwendet. Sie steht aber nicht beim Erstellen neuer Dokumente in der metasfresh-Benutzeroberfläche zur Auswahl.', Help=NULL WHERE AD_Element_ID=580745 AND IsCentrallyMaintained='Y'
;

-- 2022-04-05T15:50:21.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Einmalanschrift', Description='Einmalanschriften sind "flüchtige" Geschäftspartneradressen, die über die REST-API erstellt werden. Wenn eine Adresse als Einmalanschrift markiert ist, wird sie beim Import von Dokumenten in das metafresh-System verwendet. Sie steht aber nicht beim Erstellen neuer Dokumente in der metasfresh-Benutzeroberfläche zur Auswahl.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580745) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580745)
;

-- 2022-04-05T15:50:21.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Einmalanschrift', Description='Einmalanschriften sind "flüchtige" Geschäftspartneradressen, die über die REST-API erstellt werden. Wenn eine Adresse als Einmalanschrift markiert ist, wird sie beim Import von Dokumenten in das metafresh-System verwendet. Sie steht aber nicht beim Erstellen neuer Dokumente in der metasfresh-Benutzeroberfläche zur Auswahl.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580745
;

-- 2022-04-05T15:50:21.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Einmalanschrift', Description='Einmalanschriften sind "flüchtige" Geschäftspartneradressen, die über die REST-API erstellt werden. Wenn eine Adresse als Einmalanschrift markiert ist, wird sie beim Import von Dokumenten in das metafresh-System verwendet. Sie steht aber nicht beim Erstellen neuer Dokumente in der metasfresh-Benutzeroberfläche zur Auswahl.', Help=NULL WHERE AD_Element_ID = 580745
;

-- 2022-04-05T15:50:21.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Einmalanschrift', Description = 'Einmalanschriften sind "flüchtige" Geschäftspartneradressen, die über die REST-API erstellt werden. Wenn eine Adresse als Einmalanschrift markiert ist, wird sie beim Import von Dokumenten in das metafresh-System verwendet. Sie steht aber nicht beim Erstellen neuer Dokumente in der metasfresh-Benutzeroberfläche zur Auswahl.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580745
;

-- 2022-04-05T15:50:46.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Einmalanschriften sind "flüchtige" Geschäftspartneradressen, die über die REST-API erstellt werden. Wenn eine Adresse als Einmalanschrift markiert ist, wird sie beim Import von Dokumenten in das metafresh-System verwendet. Sie steht aber nicht beim Erstellen neuer Dokumente in der metasfresh-Benutzeroberfläche zur Auswahl.',Updated=TO_TIMESTAMP('2022-04-05 18:50:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580745 AND AD_Language='nl_NL'
;

-- 2022-04-05T15:50:46.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580745,'nl_NL') 
;

-- 2022-04-05T15:51:05.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='One-time addresses are ephemeral business partner addresses created via the REST API. If an address is marked as a one-time address, it will be used when importing documents into the metafresh system. However, it is not available for selection when creating new documents in the metasfresh user interface.',Updated=TO_TIMESTAMP('2022-04-05 18:51:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580745 AND AD_Language='en_US'
;

-- 2022-04-05T15:51:05.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580745,'en_US') 
;

