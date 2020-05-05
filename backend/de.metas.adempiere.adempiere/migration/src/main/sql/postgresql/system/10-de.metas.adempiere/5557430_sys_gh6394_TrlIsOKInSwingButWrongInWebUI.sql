-- 2020-04-16T10:11:58.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Vergleichsbericht aktuelle vs. vorige Preisliste1', Name='Vergleichsbericht aktuelle vs. vorige Preisliste1',Updated=TO_TIMESTAMP('2020-04-16 13:11:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577626
;

-- 2020-04-16T10:11:58.424Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577626,'de_CH') 
;

-- 2020-04-16T10:12:00.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Vergleichsbericht aktuelle vs. vorige Preisliste1', Name='Vergleichsbericht aktuelle vs. vorige Preisliste1',Updated=TO_TIMESTAMP('2020-04-16 13:12:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577626
;

-- 2020-04-16T10:12:00.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577626,'de_DE') 
;

-- 2020-04-16T10:12:00.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577626,'de_DE') 
;

-- 2020-04-16T10:12:00.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Vergleichsbericht aktuelle vs. vorige Preisliste1', Description=NULL, Help=NULL WHERE AD_Element_ID=577626
;

-- 2020-04-16T10:12:00.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Vergleichsbericht aktuelle vs. vorige Preisliste1', Description=NULL, Help=NULL WHERE AD_Element_ID=577626 AND IsCentrallyMaintained='Y'
;

-- 2020-04-16T10:12:00.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vergleichsbericht aktuelle vs. vorige Preisliste1', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577626) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577626)
;

-- 2020-04-16T10:12:00.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Vergleichsbericht aktuelle vs. vorige Preisliste1', Name='Vergleichsbericht aktuelle vs. vorige Preisliste1' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577626)
;

-- 2020-04-16T10:12:00.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vergleichsbericht aktuelle vs. vorige Preisliste1', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577626
;

-- 2020-04-16T10:12:00.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vergleichsbericht aktuelle vs. vorige Preisliste1', Description=NULL, Help=NULL WHERE AD_Element_ID = 577626
;

-- 2020-04-16T10:12:00.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vergleichsbericht aktuelle vs. vorige Preisliste1', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577626
;

-- 2020-04-16T10:12:02.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Current vs Previous Pricelist Comparison Report1', Name='Current vs Previous Pricelist Comparison Report1',Updated=TO_TIMESTAMP('2020-04-16 13:12:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577626
;

-- 2020-04-16T10:12:02.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577626,'en_US') 
;

-- 2020-04-16T10:12:07.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Vergleichsbericht aktuelle vs. vorige Preisliste', Name='Vergleichsbericht aktuelle vs. vorige Preisliste',Updated=TO_TIMESTAMP('2020-04-16 13:12:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577626
;

-- 2020-04-16T10:12:07.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577626,'de_CH') 
;

-- 2020-04-16T10:12:10.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Vergleichsbericht aktuelle vs. vorige Preisliste', Name='Vergleichsbericht aktuelle vs. vorige Preisliste',Updated=TO_TIMESTAMP('2020-04-16 13:12:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577626
;

-- 2020-04-16T10:12:10.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577626,'de_DE') 
;

-- 2020-04-16T10:12:10.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577626,'de_DE') 
;

-- 2020-04-16T10:12:10.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Vergleichsbericht aktuelle vs. vorige Preisliste', Description=NULL, Help=NULL WHERE AD_Element_ID=577626
;

-- 2020-04-16T10:12:10.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Vergleichsbericht aktuelle vs. vorige Preisliste', Description=NULL, Help=NULL WHERE AD_Element_ID=577626 AND IsCentrallyMaintained='Y'
;

-- 2020-04-16T10:12:10.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vergleichsbericht aktuelle vs. vorige Preisliste', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577626) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577626)
;

-- 2020-04-16T10:12:10.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Vergleichsbericht aktuelle vs. vorige Preisliste', Name='Vergleichsbericht aktuelle vs. vorige Preisliste' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577626)
;

-- 2020-04-16T10:12:10.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vergleichsbericht aktuelle vs. vorige Preisliste', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577626
;

-- 2020-04-16T10:12:10.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vergleichsbericht aktuelle vs. vorige Preisliste', Description=NULL, Help=NULL WHERE AD_Element_ID = 577626
;

-- 2020-04-16T10:12:10.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vergleichsbericht aktuelle vs. vorige Preisliste', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577626
;

-- 2020-04-16T10:12:13.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Current vs Previous Pricelist Comparison Report', Name='Current vs Previous Pricelist Comparison Report',Updated=TO_TIMESTAMP('2020-04-16 13:12:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577626
;

-- 2020-04-16T10:12:13.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577626,'en_US') 
;

