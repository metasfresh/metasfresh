-- 2020-03-31T13:32:54.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET EntityType='D',Updated=TO_TIMESTAMP('2020-03-31 16:32:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584680
;

-- 2020-03-31T13:32:59.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Vergleichsbericht aktuelle vs. vorige Preisliste  ',Updated=TO_TIMESTAMP('2020-03-31 16:32:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584680 AND AD_Language='de_CH'
;

-- 2020-03-31T13:33:07.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Vergleichsbericht aktuelle vs. vorige Preisliste  ',Updated=TO_TIMESTAMP('2020-03-31 16:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584680
;

-- 2020-03-31T13:33:07.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET IsActive='Y', Name='Vergleichsbericht aktuelle vs. vorige Preisliste  ', Description='',Updated=TO_TIMESTAMP('2020-03-31 16:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541452
;

-- 2020-03-31T13:33:28.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Vergleichsbericht aktuelle vs. vorige Preisliste', IsTranslated='Y', Name='Vergleichsbericht aktuelle vs. vorige Preisliste',Updated=TO_TIMESTAMP('2020-03-31 16:33:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577626
;

-- 2020-03-31T13:33:28.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577626,'de_CH') 
;

-- 2020-03-31T13:33:33.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Vergleichsbericht aktuelle vs. vorige Preisliste', IsTranslated='Y', Name='Vergleichsbericht aktuelle vs. vorige Preisliste',Updated=TO_TIMESTAMP('2020-03-31 16:33:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577626
;

-- 2020-03-31T13:33:33.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577626,'de_DE') 
;

-- 2020-03-31T13:33:33.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577626,'de_DE') 
;

-- 2020-03-31T13:33:33.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Vergleichsbericht aktuelle vs. vorige Preisliste', Description=NULL, Help=NULL WHERE AD_Element_ID=577626
;

-- 2020-03-31T13:33:33.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Vergleichsbericht aktuelle vs. vorige Preisliste', Description=NULL, Help=NULL WHERE AD_Element_ID=577626 AND IsCentrallyMaintained='Y'
;

-- 2020-03-31T13:33:33.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vergleichsbericht aktuelle vs. vorige Preisliste', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577626) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577626)
;

-- 2020-03-31T13:33:33.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Vergleichsbericht aktuelle vs. vorige Preisliste', Name='Vergleichsbericht aktuelle vs. vorige Preisliste' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577626)
;

-- 2020-03-31T13:33:33.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vergleichsbericht aktuelle vs. vorige Preisliste', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577626
;

-- 2020-03-31T13:33:33.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vergleichsbericht aktuelle vs. vorige Preisliste', Description=NULL, Help=NULL WHERE AD_Element_ID = 577626
;

-- 2020-03-31T13:33:33.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vergleichsbericht aktuelle vs. vorige Preisliste', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577626
;

