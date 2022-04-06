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




-- 2022-03-10T17:24:59.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Represents the acceptable threshold (in months) that can exceed the required maturity (Age value) from the shipment schedule.', Help='Represents the acceptable threshold (in months) that can exceed the required maturity (Age value) from the shipment schedule.',Updated=TO_TIMESTAMP('2022-03-10 19:24:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580607 AND AD_Language='en_US'
;

-- 2022-03-10T17:24:59.235Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580607,'en_US') 
;

-- 2022-03-10T17:27:53.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Represents the acceptable threshold (in months) that can be subtracted from the required maturity (Age value) from the shipment schedule.',Updated=TO_TIMESTAMP('2022-03-10 19:27:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580606 AND AD_Language='en_US'
;

-- 2022-03-10T17:27:53.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580606,'en_US') 
;

-- 2022-03-10T17:28:10.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Represents the acceptable threshold (in months) that can be added to the required maturity (Age value) from the shipment schedule.', Help='Represents the acceptable threshold (in months) that can be added to the required maturity (Age value) from the shipment schedule.',Updated=TO_TIMESTAMP('2022-03-10 19:28:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580607 AND AD_Language='en_US'
;

-- 2022-03-10T17:28:10.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580607,'en_US') 
;

-- 2022-03-10T17:28:22.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Represents the acceptable threshold (in months) that can be subtracted from the required maturity (Age value) from the shipment schedule.',Updated=TO_TIMESTAMP('2022-03-10 19:28:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580606 AND AD_Language='en_US'
;

-- 2022-03-10T17:28:22.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580606,'en_US') 
;




-- 2022-03-11T09:08:53.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Number of months that a pickable handling unit may be younger than specified in the shipment disposition.', Help='Number of months that a pickable handling unit may be younger than specified in the shipment disposition.',Updated=TO_TIMESTAMP('2022-03-11 11:08:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580606 AND AD_Language='en_US'
;

-- 2022-03-11T09:08:53.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580606,'en_US') 
;

-- 2022-03-11T09:09:29.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Anzahl der Monate, die eine kommissionierbare Handling Unit jünger sein darf, als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Handling Unit jünger sein darf, als im Lieferplan angegeben.',Updated=TO_TIMESTAMP('2022-03-11 11:09:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580606 AND AD_Language='de_CH'
;

-- 2022-03-11T09:09:29.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580606,'de_CH') 
;

-- 2022-03-11T09:09:32.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Anzahl der Monate, die eine kommissionierbare Handling Unit jünger sein darf, als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Handling Unit jünger sein darf, als im Lieferplan angegeben.',Updated=TO_TIMESTAMP('2022-03-11 11:09:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580606 AND AD_Language='de_DE'
;

-- 2022-03-11T09:09:32.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580606,'de_DE') 
;

-- 2022-03-11T09:09:32.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580606,'de_DE') 
;

-- 2022-03-11T09:09:32.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Picking_AgeTolerance_BeforeMonths', Name='Reifetoleranz beim Kommissionieren (Monate bis)', Description='Anzahl der Monate, die eine kommissionierbare Handling Unit jünger sein darf, als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Handling Unit jünger sein darf, als im Lieferplan angegeben.' WHERE AD_Element_ID=580606
;

-- 2022-03-11T09:09:32.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Picking_AgeTolerance_BeforeMonths', Name='Reifetoleranz beim Kommissionieren (Monate bis)', Description='Anzahl der Monate, die eine kommissionierbare Handling Unit jünger sein darf, als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Handling Unit jünger sein darf, als im Lieferplan angegeben.', AD_Element_ID=580606 WHERE UPPER(ColumnName)='PICKING_AGETOLERANCE_BEFOREMONTHS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-11T09:09:32.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Picking_AgeTolerance_BeforeMonths', Name='Reifetoleranz beim Kommissionieren (Monate bis)', Description='Anzahl der Monate, die eine kommissionierbare Handling Unit jünger sein darf, als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Handling Unit jünger sein darf, als im Lieferplan angegeben.' WHERE AD_Element_ID=580606 AND IsCentrallyMaintained='Y'
;

-- 2022-03-11T09:09:32.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Reifetoleranz beim Kommissionieren (Monate bis)', Description='Anzahl der Monate, die eine kommissionierbare Handling Unit jünger sein darf, als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Handling Unit jünger sein darf, als im Lieferplan angegeben.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580606) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580606)
;

-- 2022-03-11T09:09:32.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Reifetoleranz beim Kommissionieren (Monate bis)', Description='Anzahl der Monate, die eine kommissionierbare Handling Unit jünger sein darf, als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Handling Unit jünger sein darf, als im Lieferplan angegeben.', CommitWarning = NULL WHERE AD_Element_ID = 580606
;

-- 2022-03-11T09:09:32.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Reifetoleranz beim Kommissionieren (Monate bis)', Description='Anzahl der Monate, die eine kommissionierbare Handling Unit jünger sein darf, als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Handling Unit jünger sein darf, als im Lieferplan angegeben.' WHERE AD_Element_ID = 580606
;

-- 2022-03-11T09:09:32.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Reifetoleranz beim Kommissionieren (Monate bis)', Description = 'Anzahl der Monate, die eine kommissionierbare Handling Unit jünger sein darf, als im Lieferplan angegeben.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580606
;

-- 2022-03-11T09:12:51.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Anzahl der Monate, die eine kommissionierbare Ladeeinheit älter sein darf als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Ladeeinheit älter sein darf als im Lieferplan angegeben.',Updated=TO_TIMESTAMP('2022-03-11 11:12:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580607 AND AD_Language='de_DE'
;

-- 2022-03-11T09:12:51.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580607,'de_DE') 
;

-- 2022-03-11T09:12:51.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580607,'de_DE') 
;

-- 2022-03-11T09:12:51.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Picking_AgeTolerance_AfterMonths', Name='Reifetoleranz beim Kommissionieren (Monate danach)', Description='Anzahl der Monate, die eine kommissionierbare Ladeeinheit älter sein darf als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Ladeeinheit älter sein darf als im Lieferplan angegeben.' WHERE AD_Element_ID=580607
;

-- 2022-03-11T09:12:51.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Picking_AgeTolerance_AfterMonths', Name='Reifetoleranz beim Kommissionieren (Monate danach)', Description='Anzahl der Monate, die eine kommissionierbare Ladeeinheit älter sein darf als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Ladeeinheit älter sein darf als im Lieferplan angegeben.', AD_Element_ID=580607 WHERE UPPER(ColumnName)='PICKING_AGETOLERANCE_AFTERMONTHS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-11T09:12:51.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Picking_AgeTolerance_AfterMonths', Name='Reifetoleranz beim Kommissionieren (Monate danach)', Description='Anzahl der Monate, die eine kommissionierbare Ladeeinheit älter sein darf als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Ladeeinheit älter sein darf als im Lieferplan angegeben.' WHERE AD_Element_ID=580607 AND IsCentrallyMaintained='Y'
;

-- 2022-03-11T09:12:51.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Reifetoleranz beim Kommissionieren (Monate danach)', Description='Anzahl der Monate, die eine kommissionierbare Ladeeinheit älter sein darf als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Ladeeinheit älter sein darf als im Lieferplan angegeben.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580607) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580607)
;

-- 2022-03-11T09:12:51.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Reifetoleranz beim Kommissionieren (Monate danach)', Description='Anzahl der Monate, die eine kommissionierbare Ladeeinheit älter sein darf als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Ladeeinheit älter sein darf als im Lieferplan angegeben.', CommitWarning = NULL WHERE AD_Element_ID = 580607
;

-- 2022-03-11T09:12:51.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Reifetoleranz beim Kommissionieren (Monate danach)', Description='Anzahl der Monate, die eine kommissionierbare Ladeeinheit älter sein darf als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Ladeeinheit älter sein darf als im Lieferplan angegeben.' WHERE AD_Element_ID = 580607
;

-- 2022-03-11T09:12:51.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Reifetoleranz beim Kommissionieren (Monate danach)', Description = 'Anzahl der Monate, die eine kommissionierbare Ladeeinheit älter sein darf als im Lieferplan angegeben.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580607
;

-- 2022-03-11T09:12:56.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Anzahl der Monate, die eine kommissionierbare Ladeeinheit älter sein darf als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Ladeeinheit älter sein darf als im Lieferplan angegeben.',Updated=TO_TIMESTAMP('2022-03-11 11:12:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580607 AND AD_Language='de_CH'
;

-- 2022-03-11T09:12:56.010Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580607,'de_CH') 
;

-- 2022-03-11T09:13:31.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Number of months that a pickable handling unit may be older  than specified in the shipment disposition.', Help='Number of months that a pickable handling unit may be older  than specified in the shipment disposition.',Updated=TO_TIMESTAMP('2022-03-11 11:13:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580607 AND AD_Language='en_US'
;

-- 2022-03-11T09:13:31.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580607,'en_US') 
;

-- 2022-03-11T09:18:42.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Anzahl der Monate, die eine kommissionierbare Handling Unit älter sein darf als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Handling Unit älter sein darf als im Lieferplan angegeben.',Updated=TO_TIMESTAMP('2022-03-11 11:18:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580607 AND AD_Language='de_DE'
;

-- 2022-03-11T09:18:42.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580607,'de_DE') 
;

-- 2022-03-11T09:18:42.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580607,'de_DE') 
;

-- 2022-03-11T09:18:42.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Picking_AgeTolerance_AfterMonths', Name='Reifetoleranz beim Kommissionieren (Monate danach)', Description='Anzahl der Monate, die eine kommissionierbare Handling Unit älter sein darf als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Handling Unit älter sein darf als im Lieferplan angegeben.' WHERE AD_Element_ID=580607
;

-- 2022-03-11T09:18:42.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Picking_AgeTolerance_AfterMonths', Name='Reifetoleranz beim Kommissionieren (Monate danach)', Description='Anzahl der Monate, die eine kommissionierbare Handling Unit älter sein darf als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Handling Unit älter sein darf als im Lieferplan angegeben.', AD_Element_ID=580607 WHERE UPPER(ColumnName)='PICKING_AGETOLERANCE_AFTERMONTHS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-11T09:18:42.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Picking_AgeTolerance_AfterMonths', Name='Reifetoleranz beim Kommissionieren (Monate danach)', Description='Anzahl der Monate, die eine kommissionierbare Handling Unit älter sein darf als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Handling Unit älter sein darf als im Lieferplan angegeben.' WHERE AD_Element_ID=580607 AND IsCentrallyMaintained='Y'
;

-- 2022-03-11T09:18:42.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Reifetoleranz beim Kommissionieren (Monate danach)', Description='Anzahl der Monate, die eine kommissionierbare Handling Unit älter sein darf als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Handling Unit älter sein darf als im Lieferplan angegeben.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580607) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580607)
;

-- 2022-03-11T09:18:42.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Reifetoleranz beim Kommissionieren (Monate danach)', Description='Anzahl der Monate, die eine kommissionierbare Handling Unit älter sein darf als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Handling Unit älter sein darf als im Lieferplan angegeben.', CommitWarning = NULL WHERE AD_Element_ID = 580607
;

-- 2022-03-11T09:18:42.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Reifetoleranz beim Kommissionieren (Monate danach)', Description='Anzahl der Monate, die eine kommissionierbare Handling Unit älter sein darf als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Handling Unit älter sein darf als im Lieferplan angegeben.' WHERE AD_Element_ID = 580607
;

-- 2022-03-11T09:18:42.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Reifetoleranz beim Kommissionieren (Monate danach)', Description = 'Anzahl der Monate, die eine kommissionierbare Handling Unit älter sein darf als im Lieferplan angegeben.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580607
;

-- 2022-03-11T09:18:50.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Anzahl der Monate, die eine kommissionierbare Handling Unit älter sein darf als im Lieferplan angegeben.', Help='Anzahl der Monate, die eine kommissionierbare Handling Unit älter sein darf als im Lieferplan angegeben.',Updated=TO_TIMESTAMP('2022-03-11 11:18:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580607 AND AD_Language='de_CH'
;

-- 2022-03-11T09:18:50.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580607,'de_CH') 
;

