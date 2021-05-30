-- 2021-05-19T21:18:22.376Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Patient', PrintName='Patient',Updated=TO_TIMESTAMP('2021-05-20 00:18:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579164 AND AD_Language='de_CH'
;

-- 2021-05-19T21:18:22.376Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579164,'de_CH') 
;

-- 2021-05-19T21:18:25.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Patient', PrintName='Patient',Updated=TO_TIMESTAMP('2021-05-20 00:18:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579164 AND AD_Language='de_DE'
;

-- 2021-05-19T21:18:25.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579164,'de_DE') 
;

-- 2021-05-19T21:18:25.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579164,'de_DE') 
;

-- 2021-05-19T21:18:25.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_BPartner_AlbertaPatient_ID', Name='Patient', Description=NULL, Help=NULL WHERE AD_Element_ID=579164
;

-- 2021-05-19T21:18:25.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_AlbertaPatient_ID', Name='Patient', Description=NULL, Help=NULL, AD_Element_ID=579164 WHERE UPPER(ColumnName)='C_BPARTNER_ALBERTAPATIENT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-19T21:18:25.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_AlbertaPatient_ID', Name='Patient', Description=NULL, Help=NULL WHERE AD_Element_ID=579164 AND IsCentrallyMaintained='Y'
;

-- 2021-05-19T21:18:25.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Patient', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579164) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579164)
;

-- 2021-05-19T21:18:25.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Patient', Name='Patient' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579164)
;

-- 2021-05-19T21:18:25.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Patient', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579164
;

-- 2021-05-19T21:18:25.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Patient', Description=NULL, Help=NULL WHERE AD_Element_ID = 579164
;

-- 2021-05-19T21:18:25.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Patient', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579164
;

-- 2021-05-19T21:18:28.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Patient', PrintName='Patient',Updated=TO_TIMESTAMP('2021-05-20 00:18:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579164 AND AD_Language='en_US'
;

-- 2021-05-19T21:18:28.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579164,'en_US') 
;

-- 2021-05-19T21:18:31.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Patient', PrintName='Patient',Updated=TO_TIMESTAMP('2021-05-20 00:18:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579164 AND AD_Language='nl_NL'
;

-- 2021-05-19T21:18:31.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579164,'nl_NL') 
;

-- 2021-05-19T21:19:21.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Caregiver', PrintName='Caregiver',Updated=TO_TIMESTAMP('2021-05-20 00:19:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579175 AND AD_Language='de_CH'
;

-- 2021-05-19T21:19:21.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579175,'de_CH') 
;

-- 2021-05-19T21:19:23.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Caregiver', PrintName='Caregiver',Updated=TO_TIMESTAMP('2021-05-20 00:19:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579175 AND AD_Language='de_DE'
;

-- 2021-05-19T21:19:23.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579175,'de_DE') 
;

-- 2021-05-19T21:19:23.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579175,'de_DE') 
;

-- 2021-05-19T21:19:23.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_BPartner_AlbertaCareGiver_ID', Name='Caregiver', Description=NULL, Help=NULL WHERE AD_Element_ID=579175
;

-- 2021-05-19T21:19:24Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_AlbertaCareGiver_ID', Name='Caregiver', Description=NULL, Help=NULL, AD_Element_ID=579175 WHERE UPPER(ColumnName)='C_BPARTNER_ALBERTACAREGIVER_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-19T21:19:24Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BPartner_AlbertaCareGiver_ID', Name='Caregiver', Description=NULL, Help=NULL WHERE AD_Element_ID=579175 AND IsCentrallyMaintained='Y'
;

-- 2021-05-19T21:19:24.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Caregiver', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579175) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579175)
;

-- 2021-05-19T21:19:24.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Caregiver', Name='Caregiver' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579175)
;

-- 2021-05-19T21:19:24.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Caregiver', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579175
;

-- 2021-05-19T21:19:24.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Caregiver', Description=NULL, Help=NULL WHERE AD_Element_ID = 579175
;

-- 2021-05-19T21:19:24.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Caregiver', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579175
;

-- 2021-05-19T21:19:26.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Caregiver', PrintName='Caregiver',Updated=TO_TIMESTAMP('2021-05-20 00:19:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579175 AND AD_Language='en_US'
;

-- 2021-05-19T21:19:26.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579175,'en_US') 
;

-- 2021-05-19T21:19:29.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Caregiver', PrintName='Caregiver',Updated=TO_TIMESTAMP('2021-05-20 00:19:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579175 AND AD_Language='nl_NL'
;

-- 2021-05-19T21:19:29.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579175,'nl_NL') 
;

