-- 2019-10-23T11:47:33.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission''',Updated=TO_TIMESTAMP('2019-10-23 13:47:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551009
;

-- 2019-10-23T11:51:01.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540359,Updated=TO_TIMESTAMP('2019-10-23 13:51:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540320
;

-- 2019-10-23T11:51:45.040Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions@!''FlatFee'' & @Type_Conditions@!''HoldingFee'' & @Type_Conditions@!''Procuremnt'' & @Type_Conditions@!''Refund'' & @Type_Conditions/''Commission''@!''Commission''',Updated=TO_TIMESTAMP('2019-10-23 13:51:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559764
;

-- 2019-10-23T11:52:40.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''FlatFee''@!''FlatFee'' & @Type_Conditions/''HoldingFee''@!''HoldingFee'' & @Type_Conditions/''Procuremnt''@!''Procuremnt'' & 
@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission''',Updated=TO_TIMESTAMP('2019-10-23 13:52:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559764
;

-- 2019-10-23T11:53:52.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''Refundable''@!''Refundable'' & @Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission''',Updated=TO_TIMESTAMP('2019-10-23 13:53:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559780
;

-- 2019-10-23T12:22:59.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Fakturierbare Punktzahl', PrintName='Fakturierbare Punktzahl',Updated=TO_TIMESTAMP('2019-10-23 14:22:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577082 AND AD_Language='de_CH'
;

-- 2019-10-23T12:22:59.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577082,'de_CH') 
;

-- 2019-10-23T12:23:06.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Fakturierbare Punktzahl', PrintName='Fakturierbare Punktzahl',Updated=TO_TIMESTAMP('2019-10-23 14:23:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577082 AND AD_Language='de_DE'
;

-- 2019-10-23T12:23:06.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577082,'de_DE') 
;

-- 2019-10-23T12:23:06.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577082,'de_DE') 
;

-- 2019-10-23T12:23:06.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PointsSum_Invoiceable', Name='Fakturierbare Punktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID=577082
;

-- 2019-10-23T12:23:07.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PointsSum_Invoiceable', Name='Fakturierbare Punktzahl', Description=NULL, Help=NULL, AD_Element_ID=577082 WHERE UPPER(ColumnName)='POINTSSUM_INVOICEABLE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-23T12:23:07.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PointsSum_Invoiceable', Name='Fakturierbare Punktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID=577082 AND IsCentrallyMaintained='Y'
;

-- 2019-10-23T12:23:07.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Fakturierbare Punktzahl', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577082) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577082)
;

-- 2019-10-23T12:23:07.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Fakturierbare Punktzahl', Name='Fakturierbare Punktzahl' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577082)
;

-- 2019-10-23T12:23:07.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Fakturierbare Punktzahl', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577082
;

-- 2019-10-23T12:23:07.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Fakturierbare Punktzahl', Description=NULL, Help=NULL WHERE AD_Element_ID = 577082
;

-- 2019-10-23T12:23:07.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Fakturierbare Punktzahl', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577082
;

-- 2019-10-23T12:23:16.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Invoiceable points', PrintName='Invoiceable points',Updated=TO_TIMESTAMP('2019-10-23 14:23:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577082 AND AD_Language='en_US'
;

-- 2019-10-23T12:23:16.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577082,'en_US') 
;

