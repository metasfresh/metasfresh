-- 2022-03-08T20:02:30.640Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Maturity tolerance for picking (months before)', PrintName='Maturity tolerance for picking (months before)',Updated=TO_TIMESTAMP('2022-03-08 22:02:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580606 AND AD_Language='en_US'
;

-- 2022-03-08T20:02:30.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580606,'en_US') 
;

-- 2022-03-08T20:02:48.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Reifetoleranz beim Kommissionieren (Monate bis)', PrintName='Reifetoleranz beim Kommissionieren (Monate bis)',Updated=TO_TIMESTAMP('2022-03-08 22:02:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580606 AND AD_Language='de_DE'
;

-- 2022-03-08T20:02:48.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580606,'de_DE') 
;

-- 2022-03-08T20:02:48.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580606,'de_DE') 
;

-- 2022-03-08T20:02:48.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Picking_AgeTolerance_BeforeMonths', Name='Reifetoleranz beim Kommissionieren (Monate bis)', Description=NULL, Help=NULL WHERE AD_Element_ID=580606
;

-- 2022-03-08T20:02:48.190Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Picking_AgeTolerance_BeforeMonths', Name='Reifetoleranz beim Kommissionieren (Monate bis)', Description=NULL, Help=NULL, AD_Element_ID=580606 WHERE UPPER(ColumnName)='PICKING_AGETOLERANCE_BEFOREMONTHS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-08T20:02:48.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Picking_AgeTolerance_BeforeMonths', Name='Reifetoleranz beim Kommissionieren (Monate bis)', Description=NULL, Help=NULL WHERE AD_Element_ID=580606 AND IsCentrallyMaintained='Y'
;

-- 2022-03-08T20:02:48.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Reifetoleranz beim Kommissionieren (Monate bis)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580606) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580606)
;

-- 2022-03-08T20:02:48.205Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Reifetoleranz beim Kommissionieren (Monate bis)', Name='Reifetoleranz beim Kommissionieren (Monate bis)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580606)
;

-- 2022-03-08T20:02:48.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Reifetoleranz beim Kommissionieren (Monate bis)', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580606
;

-- 2022-03-08T20:02:48.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Reifetoleranz beim Kommissionieren (Monate bis)', Description=NULL, Help=NULL WHERE AD_Element_ID = 580606
;

-- 2022-03-08T20:02:48.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Reifetoleranz beim Kommissionieren (Monate bis)', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580606
;

-- 2022-03-08T20:02:58Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Reifetoleranz beim Kommissionieren (Monate bis)', PrintName='Reifetoleranz beim Kommissionieren (Monate bis)',Updated=TO_TIMESTAMP('2022-03-08 22:02:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580606 AND AD_Language='de_CH'
;

-- 2022-03-08T20:02:58.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580606,'de_CH') 
;

-- 2022-03-08T20:03:33.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Maturity tolerance for picking (months after)', PrintName='Maturity tolerance for picking (months after)',Updated=TO_TIMESTAMP('2022-03-08 22:03:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580607 AND AD_Language='en_US'
;

-- 2022-03-08T20:03:33.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580607,'en_US') 
;

-- 2022-03-08T20:03:50.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Reifetoleranz beim Kommissionieren (Monate danach)', PrintName='Reifetoleranz beim Kommissionieren (Monate danach)',Updated=TO_TIMESTAMP('2022-03-08 22:03:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580607 AND AD_Language='de_DE'
;

-- 2022-03-08T20:03:50.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580607,'de_DE') 
;

-- 2022-03-08T20:03:50.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580607,'de_DE') 
;

-- 2022-03-08T20:03:50.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Picking_AgeTolerance_AfterMonths', Name='Reifetoleranz beim Kommissionieren (Monate danach)', Description=NULL, Help=NULL WHERE AD_Element_ID=580607
;

-- 2022-03-08T20:03:50.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Picking_AgeTolerance_AfterMonths', Name='Reifetoleranz beim Kommissionieren (Monate danach)', Description=NULL, Help=NULL, AD_Element_ID=580607 WHERE UPPER(ColumnName)='PICKING_AGETOLERANCE_AFTERMONTHS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-08T20:03:50.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Picking_AgeTolerance_AfterMonths', Name='Reifetoleranz beim Kommissionieren (Monate danach)', Description=NULL, Help=NULL WHERE AD_Element_ID=580607 AND IsCentrallyMaintained='Y'
;

-- 2022-03-08T20:03:50.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Reifetoleranz beim Kommissionieren (Monate danach)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580607) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580607)
;

-- 2022-03-08T20:03:50.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Reifetoleranz beim Kommissionieren (Monate danach)', Name='Reifetoleranz beim Kommissionieren (Monate danach)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580607)
;

-- 2022-03-08T20:03:50.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Reifetoleranz beim Kommissionieren (Monate danach)', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580607
;

-- 2022-03-08T20:03:50.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Reifetoleranz beim Kommissionieren (Monate danach)', Description=NULL, Help=NULL WHERE AD_Element_ID = 580607
;

-- 2022-03-08T20:03:50.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Reifetoleranz beim Kommissionieren (Monate danach)', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580607
;

-- 2022-03-08T20:03:56.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Reifetoleranz beim Kommissionieren (Monate danach)', PrintName='Reifetoleranz beim Kommissionieren (Monate danach)',Updated=TO_TIMESTAMP('2022-03-08 22:03:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580607 AND AD_Language='de_CH'
;

-- 2022-03-08T20:03:56.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580607,'de_CH') 
;

