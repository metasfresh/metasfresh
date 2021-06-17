-- 2021-06-17T09:15:00.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='Individual business partner''s name format',Updated=TO_TIMESTAMP('2021-06-17 12:15:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541338
;

-- 2021-06-17T09:15:15.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='Y', Name='Namensformat der Geschäftspartnerperson',Updated=TO_TIMESTAMP('2021-06-17 12:15:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541338
;

-- 2021-06-17T09:15:20.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference_Trl SET IsTranslated='Y', Name='Namensformat der Geschäftspartnerperson',Updated=TO_TIMESTAMP('2021-06-17 12:15:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541338
;

-- 2021-06-17T09:16:49.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Describes how the new business partner''s name is initiated, based on the added contacts'' names and forms of address.', IsTranslated='Y', Name='Individual business partner''s name format', PrintName='Individual business partner''s name format',Updated=TO_TIMESTAMP('2021-06-17 12:16:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579365 AND AD_Language='en_US'
;

-- 2021-06-17T09:16:49.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579365,'en_US') 
;

-- 2021-06-17T09:17:19.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Namensformat der Geschäftspartnerperson', PrintName='Namensformat der Geschäftspartnerperson',Updated=TO_TIMESTAMP('2021-06-17 12:17:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579365 AND AD_Language='de_DE'
;

-- 2021-06-17T09:17:19.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579365,'de_DE') 
;

-- 2021-06-17T09:17:19.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579365,'de_DE') 
;

-- 2021-06-17T09:17:19.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='BPNameAndGreetingStrategy', Name='Namensformat der Geschäftspartnerperson', Description='Describes how the name of the new business partner is initialized, based on the names and greetings of the added contacts.', Help=NULL WHERE AD_Element_ID=579365
;

-- 2021-06-17T09:17:19.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BPNameAndGreetingStrategy', Name='Namensformat der Geschäftspartnerperson', Description='Describes how the name of the new business partner is initialized, based on the names and greetings of the added contacts.', Help=NULL, AD_Element_ID=579365 WHERE UPPER(ColumnName)='BPNAMEANDGREETINGSTRATEGY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-17T09:17:19.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BPNameAndGreetingStrategy', Name='Namensformat der Geschäftspartnerperson', Description='Describes how the name of the new business partner is initialized, based on the names and greetings of the added contacts.', Help=NULL WHERE AD_Element_ID=579365 AND IsCentrallyMaintained='Y'
;

-- 2021-06-17T09:17:19.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Namensformat der Geschäftspartnerperson', Description='Describes how the name of the new business partner is initialized, based on the names and greetings of the added contacts.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579365) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579365)
;

-- 2021-06-17T09:17:19.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Namensformat der Geschäftspartnerperson', Name='Namensformat der Geschäftspartnerperson' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579365)
;

-- 2021-06-17T09:17:19.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Namensformat der Geschäftspartnerperson', Description='Describes how the name of the new business partner is initialized, based on the names and greetings of the added contacts.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579365
;

-- 2021-06-17T09:17:19.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Namensformat der Geschäftspartnerperson', Description='Describes how the name of the new business partner is initialized, based on the names and greetings of the added contacts.', Help=NULL WHERE AD_Element_ID = 579365
;

-- 2021-06-17T09:17:19.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Namensformat der Geschäftspartnerperson', Description = 'Describes how the name of the new business partner is initialized, based on the names and greetings of the added contacts.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579365
;

-- 2021-06-17T09:17:26.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Namensformat der Geschäftspartnerperson', PrintName='Namensformat der Geschäftspartnerperson',Updated=TO_TIMESTAMP('2021-06-17 12:17:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579365 AND AD_Language='de_CH'
;

-- 2021-06-17T09:17:26.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579365,'de_CH') 
;

-- 2021-06-17T09:17:40.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Beschreibt, wie der Name des neuen Geschäftspartners eingeleitet wird, basierend auf den Namen und der Anrede der hinzugefügten Kontakte.',Updated=TO_TIMESTAMP('2021-06-17 12:17:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579365 AND AD_Language='de_CH'
;

-- 2021-06-17T09:17:40.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579365,'de_CH') 
;

-- 2021-06-17T09:17:42.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Beschreibt, wie der Name des neuen Geschäftspartners eingeleitet wird, basierend auf den Namen und der Anrede der hinzugefügten Kontakte.',Updated=TO_TIMESTAMP('2021-06-17 12:17:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579365 AND AD_Language='de_DE'
;

-- 2021-06-17T09:17:42.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579365,'de_DE') 
;

-- 2021-06-17T09:17:42.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579365,'de_DE') 
;

-- 2021-06-17T09:17:42.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='BPNameAndGreetingStrategy', Name='Namensformat der Geschäftspartnerperson', Description='Beschreibt, wie der Name des neuen Geschäftspartners eingeleitet wird, basierend auf den Namen und der Anrede der hinzugefügten Kontakte.', Help=NULL WHERE AD_Element_ID=579365
;

-- 2021-06-17T09:17:42.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BPNameAndGreetingStrategy', Name='Namensformat der Geschäftspartnerperson', Description='Beschreibt, wie der Name des neuen Geschäftspartners eingeleitet wird, basierend auf den Namen und der Anrede der hinzugefügten Kontakte.', Help=NULL, AD_Element_ID=579365 WHERE UPPER(ColumnName)='BPNAMEANDGREETINGSTRATEGY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-17T09:17:42.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BPNameAndGreetingStrategy', Name='Namensformat der Geschäftspartnerperson', Description='Beschreibt, wie der Name des neuen Geschäftspartners eingeleitet wird, basierend auf den Namen und der Anrede der hinzugefügten Kontakte.', Help=NULL WHERE AD_Element_ID=579365 AND IsCentrallyMaintained='Y'
;

-- 2021-06-17T09:17:42.826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Namensformat der Geschäftspartnerperson', Description='Beschreibt, wie der Name des neuen Geschäftspartners eingeleitet wird, basierend auf den Namen und der Anrede der hinzugefügten Kontakte.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579365) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579365)
;

-- 2021-06-17T09:17:42.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Namensformat der Geschäftspartnerperson', Description='Beschreibt, wie der Name des neuen Geschäftspartners eingeleitet wird, basierend auf den Namen und der Anrede der hinzugefügten Kontakte.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579365
;

-- 2021-06-17T09:17:42.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Namensformat der Geschäftspartnerperson', Description='Beschreibt, wie der Name des neuen Geschäftspartners eingeleitet wird, basierend auf den Namen und der Anrede der hinzugefügten Kontakte.', Help=NULL WHERE AD_Element_ID = 579365
;

-- 2021-06-17T09:17:42.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Namensformat der Geschäftspartnerperson', Description = 'Beschreibt, wie der Name des neuen Geschäftspartners eingeleitet wird, basierend auf den Namen und der Anrede der hinzugefügten Kontakte.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579365
;

-- 2021-06-17T09:18:10.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Erstkontakt',Updated=TO_TIMESTAMP('2021-06-17 12:18:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542673
;

-- 2021-06-17T09:18:15.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Erstkontakt',Updated=TO_TIMESTAMP('2021-06-17 12:18:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542673
;

-- 2021-06-17T09:18:29.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Mitgliederkontakt',Updated=TO_TIMESTAMP('2021-06-17 12:18:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542674
;

-- 2021-06-17T09:18:33.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Mitgliederkontakt',Updated=TO_TIMESTAMP('2021-06-17 12:18:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542674
;

