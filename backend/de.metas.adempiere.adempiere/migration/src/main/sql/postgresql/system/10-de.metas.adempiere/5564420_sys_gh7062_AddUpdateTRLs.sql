-- 2020-07-29T09:16:15.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Trading Unit',Updated=TO_TIMESTAMP('2020-07-29 12:16:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542975 AND AD_Language='en_US'
;

-- 2020-07-29T09:16:15.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542975,'en_US') 
;

-- 2020-07-29T09:16:19.033Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-29 12:16:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542975 AND AD_Language='de_CH'
;

-- 2020-07-29T09:16:19.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542975,'de_CH') 
;

-- 2020-07-29T09:16:22.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Trading Unit', PrintName='Trading Unit',Updated=TO_TIMESTAMP('2020-07-29 12:16:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542975 AND AD_Language='en_GB'
;

-- 2020-07-29T09:16:22.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542975,'en_GB') 
;

-- 2020-07-29T09:16:28.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-29 12:16:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542975 AND AD_Language='de_DE'
;

-- 2020-07-29T09:16:28.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542975,'de_DE') 
;

-- 2020-07-29T09:16:28.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542975,'de_DE') 
;

-- 2020-07-29T09:48:53.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Approved for Invoicing',Updated=TO_TIMESTAMP('2020-07-29 12:48:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542572 AND AD_Language='en_US'
;

-- 2020-07-29T09:48:53.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542572,'en_US') 
;

-- 2020-07-29T09:49:02.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Approved for Invoicing', PrintName='Approved for Invoicing',Updated=TO_TIMESTAMP('2020-07-29 12:49:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542572 AND AD_Language='en_GB'
;

-- 2020-07-29T09:49:02.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542572,'en_GB') 
;

-- 2020-07-29T09:50:29.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578018,0,'IsNotApprovedForInvoicing',TO_TIMESTAMP('2020-07-29 12:50:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','IsNotApprovedForInvoicing','IsNotApprovedForInvoicing',TO_TIMESTAMP('2020-07-29 12:50:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-29T09:50:29.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578018 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-07-29T09:50:39.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578019,0,'IsApprovedForInvoicing',TO_TIMESTAMP('2020-07-29 12:50:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','IsApprovedForInvoicing','IsApprovedForInvoicing',TO_TIMESTAMP('2020-07-29 12:50:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-29T09:50:39.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578019 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-07-29T09:51:50.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Is Approved For Invoicing ', PrintName='Is Approved For Invoicing ',Updated=TO_TIMESTAMP('2020-07-29 12:51:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578019 AND AD_Language='en_US'
;

-- 2020-07-29T09:51:50.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578019,'en_US') 
;

-- 2020-07-29T09:52:02.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Is Not Approved For Invoicing ', PrintName='Is Not Approved For Invoicing ',Updated=TO_TIMESTAMP('2020-07-29 12:52:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578018 AND AD_Language='en_US'
;

-- 2020-07-29T09:52:02.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578018,'en_US') 
;

-- 2020-07-29T09:52:44.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='Net',Updated=TO_TIMESTAMP('2020-07-29 12:52:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1003124
;

-- 2020-07-29T09:52:44.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Net', Name='Netto', Description='Effektiver Preis', Help='Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.' WHERE AD_Element_ID=1003124
;

-- 2020-07-29T09:52:44.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Net', Name='Netto', Description='Effektiver Preis', Help='Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.', AD_Element_ID=1003124 WHERE UPPER(ColumnName)='NET' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-07-29T09:52:44.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Net', Name='Netto', Description='Effektiver Preis', Help='Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.' WHERE AD_Element_ID=1003124 AND IsCentrallyMaintained='Y'
;

-- 2020-07-29T09:52:56.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-29 12:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1003124 AND AD_Language='de_DE'
;

-- 2020-07-29T09:52:56.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1003124,'de_DE') 
;

-- 2020-07-29T09:52:56.928Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1003124,'de_DE') 
;

-- 2020-07-29T09:52:59.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-29 12:52:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1003124 AND AD_Language='de_CH'
;

-- 2020-07-29T09:52:59.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1003124,'de_CH') 
;

-- 2020-07-29T09:57:55.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='NetIsApprovedForInvoicing', Name='NetIsApprovedForInvoicing', PrintName='NetIsApprovedForInvoicing',Updated=TO_TIMESTAMP('2020-07-29 12:57:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578019
;

-- 2020-07-29T09:57:55.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='NetIsApprovedForInvoicing', Name='NetIsApprovedForInvoicing', Description=NULL, Help=NULL WHERE AD_Element_ID=578019
;

-- 2020-07-29T09:57:55.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NetIsApprovedForInvoicing', Name='NetIsApprovedForInvoicing', Description=NULL, Help=NULL, AD_Element_ID=578019 WHERE UPPER(ColumnName)='NETISAPPROVEDFORINVOICING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-07-29T09:57:55.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NetIsApprovedForInvoicing', Name='NetIsApprovedForInvoicing', Description=NULL, Help=NULL WHERE AD_Element_ID=578019 AND IsCentrallyMaintained='Y'
;

-- 2020-07-29T09:57:55.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='NetIsApprovedForInvoicing', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578019) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578019)
;

-- 2020-07-29T09:57:55.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='NetIsApprovedForInvoicing', Name='NetIsApprovedForInvoicing' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578019)
;

-- 2020-07-29T09:57:55.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='NetIsApprovedForInvoicing', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578019
;

-- 2020-07-29T09:57:55.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='NetIsApprovedForInvoicing', Description=NULL, Help=NULL WHERE AD_Element_ID = 578019
;

-- 2020-07-29T09:57:55.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'NetIsApprovedForInvoicing', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578019
;

-- 2020-07-29T09:58:00.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='NetIsNotApprovedForInvoicing', Name='NetIsNotApprovedForInvoicing', PrintName='NetIsNotApprovedForInvoicing',Updated=TO_TIMESTAMP('2020-07-29 12:58:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578018
;

-- 2020-07-29T09:58:00.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='NetIsNotApprovedForInvoicing', Name='NetIsNotApprovedForInvoicing', Description=NULL, Help=NULL WHERE AD_Element_ID=578018
;

-- 2020-07-29T09:58:00.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NetIsNotApprovedForInvoicing', Name='NetIsNotApprovedForInvoicing', Description=NULL, Help=NULL, AD_Element_ID=578018 WHERE UPPER(ColumnName)='NETISNOTAPPROVEDFORINVOICING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-07-29T09:58:00.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NetIsNotApprovedForInvoicing', Name='NetIsNotApprovedForInvoicing', Description=NULL, Help=NULL WHERE AD_Element_ID=578018 AND IsCentrallyMaintained='Y'
;

-- 2020-07-29T09:58:00.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='NetIsNotApprovedForInvoicing', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578018) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578018)
;

-- 2020-07-29T09:58:00.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='NetIsNotApprovedForInvoicing', Name='NetIsNotApprovedForInvoicing' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578018)
;

-- 2020-07-29T09:58:00.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='NetIsNotApprovedForInvoicing', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578018
;

-- 2020-07-29T09:58:00.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='NetIsNotApprovedForInvoicing', Description=NULL, Help=NULL WHERE AD_Element_ID = 578018
;

-- 2020-07-29T09:58:00.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'NetIsNotApprovedForInvoicing', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578018
;

-- 2020-07-29T09:58:36.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Net: Approved For Invoicing ', PrintName='Net: Approved For Invoicing ',Updated=TO_TIMESTAMP('2020-07-29 12:58:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578019 AND AD_Language='en_US'
;

-- 2020-07-29T09:58:36.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578019,'en_US') 
;

-- 2020-07-29T09:58:44.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Net: Not Approved For Invoicing ', PrintName='Net: Not Approved For Invoicing ',Updated=TO_TIMESTAMP('2020-07-29 12:58:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578018 AND AD_Language='en_US'
;

-- 2020-07-29T09:58:44.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578018,'en_US') 
;

-- 2020-07-29T10:00:39.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Net (Approved For Invoicing)', PrintName='Net (Approved For Invoicing)',Updated=TO_TIMESTAMP('2020-07-29 13:00:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578019 AND AD_Language='en_US'
;

-- 2020-07-29T10:00:39.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578019,'en_US') 
;

-- 2020-07-29T10:00:49.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Net (Not Approved For Invoicing)', PrintName='Net (Not Approved For Invoicing)',Updated=TO_TIMESTAMP('2020-07-29 13:00:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578018 AND AD_Language='en_US'
;

-- 2020-07-29T10:00:49.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578018,'en_US') 
;

-- 2020-07-29T10:06:07.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='To be Updated',Updated=TO_TIMESTAMP('2020-07-29 13:06:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541237 AND AD_Language='en_US'
;

-- 2020-07-29T10:06:07.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541237,'en_US') 
;

-- 2020-07-29T10:08:24.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Recompute', PrintName='Recompute',Updated=TO_TIMESTAMP('2020-07-29 13:08:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541237 AND AD_Language='en_US'
;

-- 2020-07-29T10:08:24.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541237,'en_US') 
;

-- 2020-07-29T10:57:22.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Netto (Mit Freigabe)', PrintName='Netto (Mit Freigabe)',Updated=TO_TIMESTAMP('2020-07-29 13:57:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578019 AND AD_Language='de_CH'
;

-- 2020-07-29T10:57:22.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578019,'de_CH') 
;

-- 2020-07-29T10:57:26.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Netto (Mit Freigabe)', PrintName='Netto (Mit Freigabe)',Updated=TO_TIMESTAMP('2020-07-29 13:57:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578019 AND AD_Language='de_DE'
;

-- 2020-07-29T10:57:26.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578019,'de_DE') 
;

-- 2020-07-29T10:57:26.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578019,'de_DE') 
;

-- 2020-07-29T10:57:26.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='NetIsApprovedForInvoicing', Name='Netto (Mit Freigabe)', Description=NULL, Help=NULL WHERE AD_Element_ID=578019
;

-- 2020-07-29T10:57:26.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NetIsApprovedForInvoicing', Name='Netto (Mit Freigabe)', Description=NULL, Help=NULL, AD_Element_ID=578019 WHERE UPPER(ColumnName)='NETISAPPROVEDFORINVOICING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-07-29T10:57:26.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NetIsApprovedForInvoicing', Name='Netto (Mit Freigabe)', Description=NULL, Help=NULL WHERE AD_Element_ID=578019 AND IsCentrallyMaintained='Y'
;

-- 2020-07-29T10:57:26.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Netto (Mit Freigabe)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578019) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578019)
;

-- 2020-07-29T10:57:26.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Netto (Mit Freigabe)', Name='Netto (Mit Freigabe)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578019)
;

-- 2020-07-29T10:57:26.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Netto (Mit Freigabe)', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578019
;

-- 2020-07-29T10:57:26.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Netto (Mit Freigabe)', Description=NULL, Help=NULL WHERE AD_Element_ID = 578019
;

-- 2020-07-29T10:57:26.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Netto (Mit Freigabe)', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578019
;

-- 2020-07-29T10:57:48.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Netto (Ohne Freigabe)', PrintName='Netto (Ohne Freigabe)',Updated=TO_TIMESTAMP('2020-07-29 13:57:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578018 AND AD_Language='de_CH'
;

-- 2020-07-29T10:57:48.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578018,'de_CH') 
;

-- 2020-07-29T10:57:51.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Netto (Ohne Freigabe)', PrintName='Netto (Ohne Freigabe)',Updated=TO_TIMESTAMP('2020-07-29 13:57:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578018 AND AD_Language='de_DE'
;

-- 2020-07-29T10:57:51.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578018,'de_DE') 
;

-- 2020-07-29T10:57:51.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578018,'de_DE') 
;

-- 2020-07-29T10:57:51.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='NetIsNotApprovedForInvoicing', Name='Netto (Ohne Freigabe)', Description=NULL, Help=NULL WHERE AD_Element_ID=578018
;

-- 2020-07-29T10:57:51.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NetIsNotApprovedForInvoicing', Name='Netto (Ohne Freigabe)', Description=NULL, Help=NULL, AD_Element_ID=578018 WHERE UPPER(ColumnName)='NETISNOTAPPROVEDFORINVOICING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-07-29T10:57:51.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NetIsNotApprovedForInvoicing', Name='Netto (Ohne Freigabe)', Description=NULL, Help=NULL WHERE AD_Element_ID=578018 AND IsCentrallyMaintained='Y'
;

-- 2020-07-29T10:57:51.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Netto (Ohne Freigabe)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578018) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578018)
;

-- 2020-07-29T10:57:51.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Netto (Ohne Freigabe)', Name='Netto (Ohne Freigabe)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578018)
;

-- 2020-07-29T10:57:51.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Netto (Ohne Freigabe)', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578018
;

-- 2020-07-29T10:57:51.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Netto (Ohne Freigabe)', Description=NULL, Help=NULL WHERE AD_Element_ID = 578018
;

-- 2020-07-29T10:57:51.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Netto (Ohne Freigabe)', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578018
;

-- 2020-07-29T11:30:47.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578020,0,'IsGoods',TO_TIMESTAMP('2020-07-29 14:30:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Goods','Goods',TO_TIMESTAMP('2020-07-29 14:30:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-29T11:30:47.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578020 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-07-29T11:30:52.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ware', PrintName='Ware',Updated=TO_TIMESTAMP('2020-07-29 14:30:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578020 AND AD_Language='de_CH'
;

-- 2020-07-29T11:30:52.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578020,'de_CH') 
;

-- 2020-07-29T11:30:58.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ware', PrintName='Ware',Updated=TO_TIMESTAMP('2020-07-29 14:30:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578020 AND AD_Language='de_DE'
;

-- 2020-07-29T11:30:58.190Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578020,'de_DE') 
;

-- 2020-07-29T11:30:58.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578020,'de_DE') 
;

-- 2020-07-29T11:30:58.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsGoods', Name='Ware', Description=NULL, Help=NULL WHERE AD_Element_ID=578020
;

-- 2020-07-29T11:30:58.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsGoods', Name='Ware', Description=NULL, Help=NULL, AD_Element_ID=578020 WHERE UPPER(ColumnName)='ISGOODS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-07-29T11:30:58.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsGoods', Name='Ware', Description=NULL, Help=NULL WHERE AD_Element_ID=578020 AND IsCentrallyMaintained='Y'
;

-- 2020-07-29T11:30:58.205Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ware', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578020) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578020)
;

-- 2020-07-29T11:30:58.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ware', Name='Ware' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578020)
;

-- 2020-07-29T11:30:58.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ware', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578020
;

-- 2020-07-29T11:30:58.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ware', Description=NULL, Help=NULL WHERE AD_Element_ID = 578020
;

-- 2020-07-29T11:30:58.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ware', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578020
;

-- 2020-07-29T11:30:59.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-29 14:30:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578020 AND AD_Language='en_US'
;

-- 2020-07-29T11:30:59.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578020,'en_US') 
;

-- 2020-07-29T11:40:36.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Help='"IsGoods" is the opposite of "IsTradingUnit". If a ProductCategory is "Goods" ("Ware"), then it is not a Trading Unit ("Gebinde").',Updated=TO_TIMESTAMP('2020-07-29 14:40:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578020
;

-- 2020-07-29T11:40:36.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsGoods', Name='Ware', Description=NULL, Help='"IsGoods" is the opposite of "IsTradingUnit". If a ProductCategory is "Goods" ("Ware"), then it is not a Trading Unit ("Gebinde").' WHERE AD_Element_ID=578020
;

-- 2020-07-29T11:40:36.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsGoods', Name='Ware', Description=NULL, Help='"IsGoods" is the opposite of "IsTradingUnit". If a ProductCategory is "Goods" ("Ware"), then it is not a Trading Unit ("Gebinde").', AD_Element_ID=578020 WHERE UPPER(ColumnName)='ISGOODS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-07-29T11:40:36.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsGoods', Name='Ware', Description=NULL, Help='"IsGoods" is the opposite of "IsTradingUnit". If a ProductCategory is "Goods" ("Ware"), then it is not a Trading Unit ("Gebinde").' WHERE AD_Element_ID=578020 AND IsCentrallyMaintained='Y'
;

-- 2020-07-29T11:40:36.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ware', Description=NULL, Help='"IsGoods" is the opposite of "IsTradingUnit". If a ProductCategory is "Goods" ("Ware"), then it is not a Trading Unit ("Gebinde").' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578020) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578020)
;

-- 2020-07-29T11:40:36.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ware', Description=NULL, Help='"IsGoods" is the opposite of "IsTradingUnit". If a ProductCategory is "Goods" ("Ware"), then it is not a Trading Unit ("Gebinde").', CommitWarning = NULL WHERE AD_Element_ID = 578020
;

-- 2020-07-29T11:40:36.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ware', Description=NULL, Help='"IsGoods" is the opposite of "IsTradingUnit". If a ProductCategory is "Goods" ("Ware"), then it is not a Trading Unit ("Gebinde").' WHERE AD_Element_ID = 578020
;

-- 2020-07-29T11:40:36.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ware', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578020
;

