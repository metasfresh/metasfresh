-- 2020-03-26T14:38:37.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Überfällige Auftragpositionen exportieren', PrintName='Überfällige Auftragpositionen exportieren',Updated=TO_TIMESTAMP('2020-03-26 15:38:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577462 AND AD_Language='de_CH'
;

-- 2020-03-26T14:38:37.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577462,'de_CH') 
;

-- 2020-03-26T14:38:44.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Überfällige Auftragpositionen exportieren', PrintName='Überfällige Auftragpositionen exportieren',Updated=TO_TIMESTAMP('2020-03-26 15:38:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577462 AND AD_Language='de_DE'
;

-- 2020-03-26T14:38:44.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577462,'de_DE') 
;

-- 2020-03-26T14:38:44.038Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577462,'de_DE') 
;

-- 2020-03-26T14:38:44.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Überfällige Auftragpositionen exportieren', Description=NULL, Help=NULL WHERE AD_Element_ID=577462
;

-- 2020-03-26T14:38:44.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Überfällige Auftragpositionen exportieren', Description=NULL, Help=NULL WHERE AD_Element_ID=577462 AND IsCentrallyMaintained='Y'
;

-- 2020-03-26T14:38:44.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Überfällige Auftragpositionen exportieren', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577462) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577462)
;

-- 2020-03-26T14:38:44.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Überfällige Auftragpositionen exportieren', Name='Überfällige Auftragpositionen exportieren' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577462)
;

-- 2020-03-26T14:38:44.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Überfällige Auftragpositionen exportieren', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577462
;

-- 2020-03-26T14:38:44.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Überfällige Auftragpositionen exportieren', Description=NULL, Help=NULL WHERE AD_Element_ID = 577462
;

-- 2020-03-26T14:38:44.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Überfällige Auftragpositionen exportieren', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577462
;

-- 2020-03-26T14:38:46.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-03-26 15:38:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577462 AND AD_Language='de_DE'
;

-- 2020-03-26T14:38:46.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577462,'de_DE') 
;

-- 2020-03-26T14:38:46.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577462,'de_DE') 
;

-- 2020-03-26T14:39:30.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Value='M_ShipmentSchedule_ExportOverdueOrders',Updated=TO_TIMESTAMP('2020-03-26 15:39:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541238
;

-- 2020-03-26T14:39:35.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET InternalName='M_ShipmentSchedule_ExportOverdueOrders',Updated=TO_TIMESTAMP('2020-03-26 15:39:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541420
;

