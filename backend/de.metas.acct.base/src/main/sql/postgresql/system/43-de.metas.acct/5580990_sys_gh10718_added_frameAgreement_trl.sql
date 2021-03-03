-- 2021-03-03T13:15:51.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Rahmenvertrag',Updated=TO_TIMESTAMP('2021-03-03 15:15:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578783 AND AD_Language='de_CH'
;

-- 2021-03-03T13:15:51.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578783,'de_CH') 
;

-- 2021-03-03T13:15:53.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Rahmenvertrag',Updated=TO_TIMESTAMP('2021-03-03 15:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578783 AND AD_Language='de_DE'
;

-- 2021-03-03T13:15:53.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578783,'de_DE') 
;

-- 2021-03-03T13:15:53.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578783,'de_DE') 
;

-- 2021-03-03T13:15:53.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_FrameAgreement_Order_ID', Name='Rahmenvertrag', Description='Rahmenvertrag Referenz', Help='Rahmenvertrag Referenz' WHERE AD_Element_ID=578783
;

-- 2021-03-03T13:15:53.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_FrameAgreement_Order_ID', Name='Rahmenvertrag', Description='Rahmenvertrag Referenz', Help='Rahmenvertrag Referenz', AD_Element_ID=578783 WHERE UPPER(ColumnName)='C_FRAMEAGREEMENT_ORDER_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-03-03T13:15:53.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_FrameAgreement_Order_ID', Name='Rahmenvertrag', Description='Rahmenvertrag Referenz', Help='Rahmenvertrag Referenz' WHERE AD_Element_ID=578783 AND IsCentrallyMaintained='Y'
;

-- 2021-03-03T13:15:53.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rahmenvertrag', Description='Rahmenvertrag Referenz', Help='Rahmenvertrag Referenz' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578783) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578783)
;

-- 2021-03-03T13:15:53.343Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rahmenvertrag', Name='Rahmenvertrag' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578783)
;

-- 2021-03-03T13:15:53.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rahmenvertrag', Description='Rahmenvertrag Referenz', Help='Rahmenvertrag Referenz', CommitWarning = NULL WHERE AD_Element_ID = 578783
;

-- 2021-03-03T13:15:53.347Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rahmenvertrag', Description='Rahmenvertrag Referenz', Help='Rahmenvertrag Referenz' WHERE AD_Element_ID = 578783
;

-- 2021-03-03T13:15:53.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Rahmenvertrag', Description = 'Rahmenvertrag Referenz', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578783
;

-- 2021-03-03T13:15:56.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Rahmenvertrag',Updated=TO_TIMESTAMP('2021-03-03 15:15:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578783 AND AD_Language='nl_NL'
;

-- 2021-03-03T13:15:56.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578783,'nl_NL') 
;

