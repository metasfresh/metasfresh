-- 2020-09-11T11:36:57.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Lager', PrintName='Lager',Updated=TO_TIMESTAMP('2020-09-11 14:36:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578071 AND AD_Language='de_CH'
;

-- 2020-09-11T11:36:57.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578071,'de_CH') 
;

-- 2020-09-11T11:37:11.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Lager', PrintName='Lager',Updated=TO_TIMESTAMP('2020-09-11 14:37:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578071 AND AD_Language='de_DE'
;

-- 2020-09-11T11:37:11.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578071,'de_DE') 
;

-- 2020-09-11T11:37:11.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578071,'de_DE') 
;

-- 2020-09-11T11:37:11.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MoveToWarehouseId', Name='Lager', Description=NULL, Help=NULL WHERE AD_Element_ID=578071
;

-- 2020-09-11T11:37:11.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MoveToWarehouseId', Name='Lager', Description=NULL, Help=NULL, AD_Element_ID=578071 WHERE UPPER(ColumnName)='MOVETOWAREHOUSEID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-09-11T11:37:11.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MoveToWarehouseId', Name='Lager', Description=NULL, Help=NULL WHERE AD_Element_ID=578071 AND IsCentrallyMaintained='Y'
;

-- 2020-09-11T11:37:11.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lager', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578071) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578071)
;

-- 2020-09-11T11:37:11.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lager', Name='Lager' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578071)
;

-- 2020-09-11T11:37:11.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lager', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578071
;

-- 2020-09-11T11:37:11.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Lager', Description=NULL, Help=NULL WHERE AD_Element_ID = 578071
;

-- 2020-09-11T11:37:11.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Lager', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578071
;

-- 2020-09-11T11:37:17.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lager', PrintName='Lager',Updated=TO_TIMESTAMP('2020-09-11 14:37:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578071 AND AD_Language='nl_NL'
;

-- 2020-09-11T11:37:17.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578071,'nl_NL') 
;

-- 2020-09-11T11:42:42.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='Specifies if the `de.metas.ui.web.handlingunits.process.WEBUI_M_HU_Transform` shall have the option of moving the resulted HUs to a different warehouse.
The default is "false".', Name='.WEBUI_M_HU_Transform.enableMovement',Updated=TO_TIMESTAMP('2020-09-11 14:42:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541339
;

-- 2020-09-11T11:43:00.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Name='WEBUI_M_HU_Transform.enableMovement',Updated=TO_TIMESTAMP('2020-09-11 14:43:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541339
;

