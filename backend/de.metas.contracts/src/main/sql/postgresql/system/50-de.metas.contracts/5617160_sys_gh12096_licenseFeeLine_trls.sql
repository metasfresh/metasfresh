-- 2021-12-03T17:33:07.866Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2021-12-03 19:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578578
;

-- 2021-12-03T17:33:12.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_licensefeesettingsline','C_LicenseFeeSettings_ID','NUMERIC(10)',null,null)
;

-- 2021-12-03T17:33:22.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET SeqNo=20,Updated=TO_TIMESTAMP('2021-12-03 19:33:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578579
;

-- 2021-12-03T17:33:24.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_licensefeesettingsline','SeqNo','NUMERIC(10)',null,null)
;

-- 2021-12-03T17:33:35.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=30,Updated=TO_TIMESTAMP('2021-12-03 19:33:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578620
;

-- 2021-12-03T17:33:38.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_licensefeesettingsline','PercentOfBasePoints','NUMERIC',null,'0')
;

-- 2021-12-03T17:33:38.347Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_LicenseFeeSettingsLine SET PercentOfBasePoints=0 WHERE PercentOfBasePoints IS NULL
;

-- 2021-12-03T17:35:16.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Lizenzgebührdetail', PrintName='Lizenzgebührdetail',Updated=TO_TIMESTAMP('2021-12-03 19:35:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580268 AND AD_Language='de_DE'
;

-- 2021-12-03T17:35:16.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580268,'de_DE') 
;

-- 2021-12-03T17:35:16.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580268,'de_DE') 
;

-- 2021-12-03T17:35:16.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_LicenseFeeSettingsLine_ID', Name='Lizenzgebührdetail', Description=NULL, Help=NULL WHERE AD_Element_ID=580268
;

-- 2021-12-03T17:35:16.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_LicenseFeeSettingsLine_ID', Name='Lizenzgebührdetail', Description=NULL, Help=NULL, AD_Element_ID=580268 WHERE UPPER(ColumnName)='C_LICENSEFEESETTINGSLINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-03T17:35:16.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_LicenseFeeSettingsLine_ID', Name='Lizenzgebührdetail', Description=NULL, Help=NULL WHERE AD_Element_ID=580268 AND IsCentrallyMaintained='Y'
;

-- 2021-12-03T17:35:16.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lizenzgebührdetail', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580268) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580268)
;

-- 2021-12-03T17:35:17.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lizenzgebührdetail', Name='Lizenzgebührdetail' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580268)
;

-- 2021-12-03T17:35:17.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lizenzgebührdetail', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580268
;

-- 2021-12-03T17:35:17.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Lizenzgebührdetail', Description=NULL, Help=NULL WHERE AD_Element_ID = 580268
;

-- 2021-12-03T17:35:17.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Lizenzgebührdetail', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580268
;

-- 2021-12-03T17:35:24.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Lizenzgebührdetail', PrintName='Lizenzgebührdetail',Updated=TO_TIMESTAMP('2021-12-03 19:35:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580268 AND AD_Language='de_CH'
;

-- 2021-12-03T17:35:24.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580268,'de_CH') 
;

-- 2021-12-03T17:35:36.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='License fee detail', PrintName='License fee detail',Updated=TO_TIMESTAMP('2021-12-03 19:35:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580268 AND AD_Language='en_US'
;

-- 2021-12-03T17:35:36.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580268,'en_US') 
;

-- 2021-12-03T17:35:47.235Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Lizenzgebührdetail', PrintName='Lizenzgebührdetail',Updated=TO_TIMESTAMP('2021-12-03 19:35:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580268 AND AD_Language='nl_NL'
;

-- 2021-12-03T17:35:47.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580268,'nl_NL') 
;

-- 2021-12-03T17:36:44.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vertreibspartnergruppe', PrintName='Vertreibspartnergruppe',Updated=TO_TIMESTAMP('2021-12-03 19:36:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580269 AND AD_Language='de_CH'
;

-- 2021-12-03T17:36:44.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580269,'de_CH') 
;

-- 2021-12-03T17:36:47.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vertreibspartnergruppe', PrintName='Vertreibspartnergruppe',Updated=TO_TIMESTAMP('2021-12-03 19:36:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580269 AND AD_Language='de_DE'
;

-- 2021-12-03T17:36:47.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580269,'de_DE') 
;

-- 2021-12-03T17:36:47.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580269,'de_DE') 
;

-- 2021-12-03T17:36:47.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_BP_Group_Match_ID', Name='Vertreibspartnergruppe', Description=NULL, Help=NULL WHERE AD_Element_ID=580269
;

-- 2021-12-03T17:36:47.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BP_Group_Match_ID', Name='Vertreibspartnergruppe', Description=NULL, Help=NULL, AD_Element_ID=580269 WHERE UPPER(ColumnName)='C_BP_GROUP_MATCH_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-12-03T17:36:47.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BP_Group_Match_ID', Name='Vertreibspartnergruppe', Description=NULL, Help=NULL WHERE AD_Element_ID=580269 AND IsCentrallyMaintained='Y'
;

-- 2021-12-03T17:36:47.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vertreibspartnergruppe', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580269) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580269)
;

-- 2021-12-03T17:36:47.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Vertreibspartnergruppe', Name='Vertreibspartnergruppe' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580269)
;

-- 2021-12-03T17:36:47.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vertreibspartnergruppe', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580269
;

-- 2021-12-03T17:36:47.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vertreibspartnergruppe', Description=NULL, Help=NULL WHERE AD_Element_ID = 580269
;

-- 2021-12-03T17:36:47.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vertreibspartnergruppe', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580269
;

-- 2021-12-03T17:36:52.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Vertreibspartnergruppe', PrintName='Vertreibspartnergruppe',Updated=TO_TIMESTAMP('2021-12-03 19:36:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580269 AND AD_Language='nl_NL'
;

-- 2021-12-03T17:36:52.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580269,'nl_NL') 
;

-- 2021-12-03T17:36:59.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Sales rep group', PrintName='Sales rep group',Updated=TO_TIMESTAMP('2021-12-03 19:36:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580269 AND AD_Language='en_US'
;


-- 2021-12-03T17:36:59.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580269,'en_US') 
;

