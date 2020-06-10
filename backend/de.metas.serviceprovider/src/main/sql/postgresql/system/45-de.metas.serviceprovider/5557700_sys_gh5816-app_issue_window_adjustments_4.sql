-- 2020-04-22T09:20:43.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External issue no', PrintName='External issue no',Updated=TO_TIMESTAMP('2020-04-22 12:20:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577606 AND AD_Language='de_CH'
;

-- 2020-04-22T09:20:43.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577606,'de_CH') 
;

-- 2020-04-22T09:20:49.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External issue no', PrintName='External issue no',Updated=TO_TIMESTAMP('2020-04-22 12:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577606 AND AD_Language='de_DE'
;

-- 2020-04-22T09:20:49.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577606,'de_DE') 
;

-- 2020-04-22T09:20:49.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577606,'de_DE') 
;

-- 2020-04-22T09:20:49.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExternalIssueNo', Name='External issue no', Description='External issue number ( e.g. github issue number )', Help=NULL WHERE AD_Element_ID=577606
;

-- 2020-04-22T09:20:49.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalIssueNo', Name='External issue no', Description='External issue number ( e.g. github issue number )', Help=NULL, AD_Element_ID=577606 WHERE UPPER(ColumnName)='EXTERNALISSUENO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-04-22T09:20:49.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalIssueNo', Name='External issue no', Description='External issue number ( e.g. github issue number )', Help=NULL WHERE AD_Element_ID=577606 AND IsCentrallyMaintained='Y'
;

-- 2020-04-22T09:20:49.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='External issue no', Description='External issue number ( e.g. github issue number )', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577606) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577606)
;

-- 2020-04-22T09:20:49.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='External issue no', Name='External issue no' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577606)
;

-- 2020-04-22T09:20:49.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='External issue no', Description='External issue number ( e.g. github issue number )', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577606
;

-- 2020-04-22T09:20:49.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='External issue no', Description='External issue number ( e.g. github issue number )', Help=NULL WHERE AD_Element_ID = 577606
;

-- 2020-04-22T09:20:49.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'External issue no', Description = 'External issue number ( e.g. github issue number )', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577606
;

-- 2020-04-22T09:20:53.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External issue no', PrintName='External issue no',Updated=TO_TIMESTAMP('2020-04-22 12:20:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577606 AND AD_Language='en_US'
;

-- 2020-04-22T09:20:53.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577606,'en_US') 
;

-- 2020-04-22T09:20:58.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='External issue no', PrintName='External issue no',Updated=TO_TIMESTAMP('2020-04-22 12:20:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577606 AND AD_Language='nl_NL'
;

-- 2020-04-22T09:20:58.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577606,'nl_NL') 
;

-- 2020-04-22T09:21:56.681Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Issue effort (H:mm)', PrintName='Issue effort (H:mm)',Updated=TO_TIMESTAMP('2020-04-22 12:21:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577676 AND AD_Language='de_CH'
;

-- 2020-04-22T09:21:56.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577676,'de_CH') 
;

-- 2020-04-22T09:22:03.057Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Issue effort (H:mm)', PrintName='Issue effort (H:mm)',Updated=TO_TIMESTAMP('2020-04-22 12:22:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577676 AND AD_Language='de_DE'
;

-- 2020-04-22T09:22:03.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577676,'de_DE') 
;

-- 2020-04-22T09:22:03.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577676,'de_DE') 
;

-- 2020-04-22T09:22:03.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IssueEffort', Name='Issue effort (H:mm)', Description='Time spent directly on this task in H:mm format.', Help=NULL WHERE AD_Element_ID=577676
;

-- 2020-04-22T09:22:03.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IssueEffort', Name='Issue effort (H:mm)', Description='Time spent directly on this task in H:mm format.', Help=NULL, AD_Element_ID=577676 WHERE UPPER(ColumnName)='ISSUEEFFORT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-04-22T09:22:03.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IssueEffort', Name='Issue effort (H:mm)', Description='Time spent directly on this task in H:mm format.', Help=NULL WHERE AD_Element_ID=577676 AND IsCentrallyMaintained='Y'
;

-- 2020-04-22T09:22:03.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Issue effort (H:mm)', Description='Time spent directly on this task in H:mm format.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577676) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577676)
;

-- 2020-04-22T09:22:03.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Issue effort (H:mm)', Name='Issue effort (H:mm)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577676)
;

-- 2020-04-22T09:22:03.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Issue effort (H:mm)', Description='Time spent directly on this task in H:mm format.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577676
;

-- 2020-04-22T09:22:03.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Issue effort (H:mm)', Description='Time spent directly on this task in H:mm format.', Help=NULL WHERE AD_Element_ID = 577676
;

-- 2020-04-22T09:22:03.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Issue effort (H:mm)', Description = 'Time spent directly on this task in H:mm format.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577676
;

-- 2020-04-22T09:22:07.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Issue effort (H:mm)', PrintName='Issue effort (H:mm)',Updated=TO_TIMESTAMP('2020-04-22 12:22:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577676 AND AD_Language='en_US'
;

-- 2020-04-22T09:22:07.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577676,'en_US') 
;

-- 2020-04-22T09:22:13.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Issue effort (H:mm)', PrintName='Issue effort (H:mm)',Updated=TO_TIMESTAMP('2020-04-22 12:22:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577676 AND AD_Language='nl_NL'
;

-- 2020-04-22T09:22:13.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577676,'nl_NL') 
;

-- 2020-04-22T09:22:24.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsRangeFilter='Y',Updated=TO_TIMESTAMP('2020-04-22 12:22:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570626
;

