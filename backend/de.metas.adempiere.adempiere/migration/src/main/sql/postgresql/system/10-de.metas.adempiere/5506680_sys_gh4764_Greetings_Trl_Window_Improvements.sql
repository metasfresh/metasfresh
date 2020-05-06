-- 2018-11-19T13:15:12.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-19 13:15:12','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Briefanrede' WHERE AD_Element_ID=542055 AND AD_Language='de_DE'
;

-- 2018-11-19T13:15:12.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542055,'de_DE') 
;

-- 2018-11-19T13:15:12.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542055,'de_DE') 
;

-- 2018-11-19T13:15:12.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Letter_Salutation', Name='Briefanrede', Description=NULL, Help=NULL WHERE AD_Element_ID=542055
;

-- 2018-11-19T13:15:12.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Letter_Salutation', Name='Briefanrede', Description=NULL, Help=NULL, AD_Element_ID=542055 WHERE UPPER(ColumnName)='LETTER_SALUTATION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-11-19T13:15:12.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Letter_Salutation', Name='Briefanrede', Description=NULL, Help=NULL WHERE AD_Element_ID=542055 AND IsCentrallyMaintained='Y'
;

-- 2018-11-19T13:15:12.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Briefanrede', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542055) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542055)
;

-- 2018-11-19T13:15:12.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Briefanrede', Name='Briefanrede' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542055)
;

-- 2018-11-19T13:15:12.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Briefanrede', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542055
;

-- 2018-11-19T13:15:12.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Briefanrede', Description=NULL, Help=NULL WHERE AD_Element_ID = 542055
;

-- 2018-11-19T13:15:12.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Briefanrede', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542055
;

-- 2018-11-19T13:35:41.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-19 13:35:41','YYYY-MM-DD HH24:MI:SS'),Name='Greeting ',PrintName='Greeting ' WHERE AD_Element_ID=1159 AND AD_Language='en_US'
;

-- 2018-11-19T13:35:41.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1159,'en_US') 
;

-- 2018-11-19T13:35:45.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-19 13:35:45','YYYY-MM-DD HH24:MI:SS'),Name='Greeting',PrintName='Greeting' WHERE AD_Element_ID=1159 AND AD_Language='en_US'
;

-- 2018-11-19T13:35:45.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1159,'en_US') 
;

-- 2018-11-19T13:37:22.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-19 13:37:22','YYYY-MM-DD HH24:MI:SS'),Name='Language ',PrintName='Language ' WHERE AD_Element_ID=109 AND AD_Language='en_US'
;

-- 2018-11-19T13:37:22.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(109,'en_US') 
;

-- 2018-11-19T13:37:25.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-19 13:37:25','YYYY-MM-DD HH24:MI:SS'),Name='Language',PrintName='Language' WHERE AD_Element_ID=109 AND AD_Language='en_US'
;

-- 2018-11-19T13:37:25.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(109,'en_US') 
;

-- 2018-11-19T13:42:27.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-19 13:42:27','YYYY-MM-DD HH24:MI:SS') WHERE AD_Element_ID=1159 AND AD_Language='en_US'
;

-- 2018-11-19T13:42:27.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1159,'en_US') 
;

-- 2018-11-19T14:10:07.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-19 14:10:07','YYYY-MM-DD HH24:MI:SS'),Name='Greeting ',PrintName='Greeting ' WHERE AD_Element_ID=1171 AND AD_Language='en_US'
;

-- 2018-11-19T14:10:07.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1171,'en_US') 
;

-- 2018-11-19T14:10:10.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-19 14:10:10','YYYY-MM-DD HH24:MI:SS'),Name='Greeting',PrintName='Greeting' WHERE AD_Element_ID=1171 AND AD_Language='en_US'
;

-- 2018-11-19T14:10:10.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1171,'en_US') 
;

-- 2018-11-19T14:10:56.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-19 14:10:56','YYYY-MM-DD HH24:MI:SS'),Name='Anrede (ID)',PrintName='Anrede (ID)' WHERE AD_Element_ID=1159 AND AD_Language='de_DE'
;

-- 2018-11-19T14:10:56.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1159,'de_DE') 
;

-- 2018-11-19T14:10:56.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1159,'de_DE') 
;

-- 2018-11-19T14:10:56.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Greeting_ID', Name='Anrede (ID)', Description='Anrede zum Druck auf Korrespondenz', Help='Anrede, die beim Druck auf Korrespondenz verwendet werden soll.' WHERE AD_Element_ID=1159
;

-- 2018-11-19T14:10:56.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Greeting_ID', Name='Anrede (ID)', Description='Anrede zum Druck auf Korrespondenz', Help='Anrede, die beim Druck auf Korrespondenz verwendet werden soll.', AD_Element_ID=1159 WHERE UPPER(ColumnName)='C_GREETING_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-11-19T14:10:56.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Greeting_ID', Name='Anrede (ID)', Description='Anrede zum Druck auf Korrespondenz', Help='Anrede, die beim Druck auf Korrespondenz verwendet werden soll.' WHERE AD_Element_ID=1159 AND IsCentrallyMaintained='Y'
;

-- 2018-11-19T14:10:56.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Anrede (ID)', Description='Anrede zum Druck auf Korrespondenz', Help='Anrede, die beim Druck auf Korrespondenz verwendet werden soll.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1159) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1159)
;

-- 2018-11-19T14:10:56.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Anrede (ID)', Name='Anrede (ID)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1159)
;

-- 2018-11-19T14:10:56.860
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Anrede (ID)', Description='Anrede zum Druck auf Korrespondenz', Help='Anrede, die beim Druck auf Korrespondenz verwendet werden soll.', CommitWarning = NULL WHERE AD_Element_ID = 1159
;

-- 2018-11-19T14:10:56.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Anrede (ID)', Description='Anrede zum Druck auf Korrespondenz', Help='Anrede, die beim Druck auf Korrespondenz verwendet werden soll.' WHERE AD_Element_ID = 1159
;

-- 2018-11-19T14:10:56.866
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Anrede (ID)', Description='Anrede zum Druck auf Korrespondenz', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1159
;

-- 2018-11-19T14:11:12.997
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-19 14:11:12','YYYY-MM-DD HH24:MI:SS'),Name='Greeting (ID)',PrintName='Greeting (ID)' WHERE AD_Element_ID=1159 AND AD_Language='en_US'
;

-- 2018-11-19T14:11:13.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1159,'en_US') 
;

-- 2018-11-19T14:12:24.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-19 14:12:24','YYYY-MM-DD HH24:MI:SS'),Name='Active ',PrintName='Active ' WHERE AD_Element_ID=348 AND AD_Language='en_US'
;

-- 2018-11-19T14:12:24.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(348,'en_US') 
;

-- 2018-11-19T14:12:28.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-19 14:12:28','YYYY-MM-DD HH24:MI:SS'),Name='Active',PrintName='Active' WHERE AD_Element_ID=348 AND AD_Language='en_US'
;

-- 2018-11-19T14:12:28.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(348,'en_US') 
;

-- 2018-11-19T14:13:13.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-19 14:13:13','YYYY-MM-DD HH24:MI:SS'),Name='Translated ',PrintName='Translated ' WHERE AD_Element_ID=420 AND AD_Language='en_US'
;

-- 2018-11-19T14:13:13.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(420,'en_US') 
;

-- 2018-11-19T14:13:16.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-19 14:13:16','YYYY-MM-DD HH24:MI:SS'),Name='Translated',PrintName='Translated' WHERE AD_Element_ID=420 AND AD_Language='en_US'
;

-- 2018-11-19T14:13:16.592
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(420,'en_US') 
;

-- 2018-11-19T14:13:55.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-19 14:13:55','YYYY-MM-DD HH24:MI:SS'),Name='Organisation ',PrintName='Organisation ' WHERE AD_Element_ID=113 AND AD_Language='en_US'
;

-- 2018-11-19T14:13:55.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(113,'en_US') 
;

-- 2018-11-19T14:13:58.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-19 14:13:58','YYYY-MM-DD HH24:MI:SS'),Name='Organisation',PrintName='Organisation' WHERE AD_Element_ID=113 AND AD_Language='en_US'
;

-- 2018-11-19T14:13:58.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(113,'en_US') 
;

-- 2018-11-19T14:15:04.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=356, IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2018-11-19 14:15:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=4083
;

-- 2018-11-19T14:16:19.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=178,Updated=TO_TIMESTAMP('2018-11-19 14:16:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=356
;

