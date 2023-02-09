-- 2022-07-25T08:55:48.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Einstellungen für Vorfinanzierungen', PrintName='Einstellungen für Vorfinanzierungen',Updated=TO_TIMESTAMP('2022-07-25 11:55:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581151 AND AD_Language='de_DE'
;

-- 2022-07-25T08:55:48.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581151,'de_DE') 
;

-- 2022-07-25T08:55:48.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(581151,'de_DE') 
;

-- 2022-07-25T08:55:48.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_Prefinancing_Settings_ID', Name='Einstellungen für Vorfinanzierungen', Description=NULL, Help=NULL WHERE AD_Element_ID=581151
;

-- 2022-07-25T08:55:48.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Prefinancing_Settings_ID', Name='Einstellungen für Vorfinanzierungen', Description=NULL, Help=NULL, AD_Element_ID=581151 WHERE UPPER(ColumnName)='C_PREFINANCING_SETTINGS_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-25T08:55:48.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_Prefinancing_Settings_ID', Name='Einstellungen für Vorfinanzierungen', Description=NULL, Help=NULL WHERE AD_Element_ID=581151 AND IsCentrallyMaintained='Y'
;

-- 2022-07-25T08:55:48.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Einstellungen für Vorfinanzierungen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581151) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581151)
;

-- 2022-07-25T08:55:48.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Einstellungen für Vorfinanzierungen', Name='Einstellungen für Vorfinanzierungen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581151)
;

-- 2022-07-25T08:55:48.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Einstellungen für Vorfinanzierungen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581151
;

-- 2022-07-25T08:55:48.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Einstellungen für Vorfinanzierungen', Description=NULL, Help=NULL WHERE AD_Element_ID = 581151
;

-- 2022-07-25T08:55:48.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Einstellungen für Vorfinanzierungen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581151
;

-- 2022-07-25T08:56:26.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-07-25 11:56:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610517
;

-- 2022-07-25T08:56:26.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-07-25 11:56:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610520
;

-- 2022-07-25T08:56:26.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-07-25 11:56:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610521
;

-- 2022-07-25T08:56:26.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-07-25 11:56:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610518
;

-- 2022-07-25T08:58:06.928Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-07-25 11:58:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583722
;

-- 2022-07-25T08:58:07.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_prefinancing_settings','C_Harvesting_Calendar_ID','NUMERIC(10)',null,null)
;

-- 2022-07-25T08:58:07.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_prefinancing_settings','C_Harvesting_Calendar_ID',null,'NOT NULL',null)
;

-- 2022-07-25T08:58:16.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-07-25 11:58:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583723
;

-- 2022-07-25T08:58:17.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_prefinancing_settings','M_Product_Witholding_ID','NUMERIC(10)',null,null)
;

-- 2022-07-25T08:58:17.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_prefinancing_settings','M_Product_Witholding_ID',null,'NOT NULL',null)
;

