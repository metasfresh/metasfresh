-- 2020-04-01T09:32:19.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Effort issue', PrintName='Effort issue',Updated=TO_TIMESTAMP('2020-04-01 12:32:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577625 AND AD_Language='de_CH'
;

-- 2020-04-01T09:32:19.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577625,'de_CH') 
;

-- 2020-04-01T09:32:32.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Effort issue', PrintName='Effort issue',Updated=TO_TIMESTAMP('2020-04-01 12:32:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577625 AND AD_Language='de_DE'
;

-- 2020-04-01T09:32:32.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577625,'de_DE') 
;

-- 2020-04-01T09:32:32.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577625,'de_DE') 
;

-- 2020-04-01T09:32:32.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Effort issue', Description=NULL, Help=NULL WHERE AD_Element_ID=577625
;

-- 2020-04-01T09:32:32.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Effort issue', Description=NULL, Help=NULL WHERE AD_Element_ID=577625 AND IsCentrallyMaintained='Y'
;

-- 2020-04-01T09:32:32.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Effort issue', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577625) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577625)
;

-- 2020-04-01T09:32:32.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Effort issue', Name='Effort issue' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577625)
;

-- 2020-04-01T09:32:32.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Effort issue', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577625
;

-- 2020-04-01T09:32:32.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Effort issue', Description=NULL, Help=NULL WHERE AD_Element_ID = 577625
;

-- 2020-04-01T09:32:32.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Effort issue', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577625
;

-- 2020-04-01T09:32:36.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Effort issue', PrintName='Effort issue',Updated=TO_TIMESTAMP('2020-04-01 12:32:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577625 AND AD_Language='en_US'
;

-- 2020-04-01T09:32:36.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577625,'en_US') 
;

-- 2020-04-01T09:32:41.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Effort issue', PrintName='Effort issue',Updated=TO_TIMESTAMP('2020-04-01 12:32:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577625 AND AD_Language='nl_NL'
;

-- 2020-04-01T09:32:41.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577625,'nl_NL') 
;

-- 2020-03-27T11:40:14.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541313,'S',TO_TIMESTAMP('2020-03-27 13:40:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.issue.tracking.github.accessToken',TO_TIMESTAMP('2020-03-27 13:40:13','YYYY-MM-DD HH24:MI:SS'),100,'private_access_token')
;

