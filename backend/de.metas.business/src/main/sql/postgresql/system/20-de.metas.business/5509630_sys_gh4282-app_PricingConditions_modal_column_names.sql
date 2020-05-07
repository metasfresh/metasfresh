-- 2019-01-15T11:25:20.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=1708 AND AD_Language='fr_CH'
;

-- 2019-01-15T11:25:21.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=1708 AND AD_Language='it_CH'
;

-- 2019-01-15T11:25:21.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=1708 AND AD_Language='en_GB'
;

-- 2019-01-15T11:26:55.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 11:26:55','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Mengenstufe',PrintName='Mengenstufe',Description='Mindestmenge ab der die Kondition gilt',Help='' WHERE AD_Element_ID=1708 AND AD_Language='de_CH'
;

-- 2019-01-15T11:26:55.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1708,'de_CH') 
;

-- 2019-01-15T11:27:25.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 11:27:25','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Mengenstufe',PrintName='Mengenstufe',Description='Mindestmenge ab der die Kondition gilt',Help='' WHERE AD_Element_ID=1708 AND AD_Language='de_DE'
;

-- 2019-01-15T11:27:25.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1708,'de_DE') 
;

-- 2019-01-15T11:27:25.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1708,'de_DE') 
;

-- 2019-01-15T11:27:25.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='BreakValue', Name='Mengenstufe', Description='Mindestmenge ab der die Kondition gilt', Help='' WHERE AD_Element_ID=1708
;

-- 2019-01-15T11:27:25.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BreakValue', Name='Mengenstufe', Description='Mindestmenge ab der die Kondition gilt', Help='', AD_Element_ID=1708 WHERE UPPER(ColumnName)='BREAKVALUE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-01-15T11:27:25.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BreakValue', Name='Mengenstufe', Description='Mindestmenge ab der die Kondition gilt', Help='' WHERE AD_Element_ID=1708 AND IsCentrallyMaintained='Y'
;

-- 2019-01-15T11:27:25.573
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Mengenstufe', Description='Mindestmenge ab der die Kondition gilt', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1708) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1708)
;

-- 2019-01-15T11:27:25.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Mengenstufe', Name='Mengenstufe' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1708)
;

-- 2019-01-15T11:27:25.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Mengenstufe', Description='Mindestmenge ab der die Kondition gilt', Help='', CommitWarning = NULL WHERE AD_Element_ID = 1708
;

-- 2019-01-15T11:27:25.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Mengenstufe', Description='Mindestmenge ab der die Kondition gilt', Help='' WHERE AD_Element_ID = 1708
;

-- 2019-01-15T11:27:25.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Mengenstufe', Description='Mindestmenge ab der die Kondition gilt', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1708
;

-- 2019-01-15T11:27:36.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 11:27:36','YYYY-MM-DD HH24:MI:SS'),PrintName='Mengenstufe' WHERE AD_Element_ID=1708 AND AD_Language='nl_NL'
;

-- 2019-01-15T11:27:36.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1708,'nl_NL') 
;

-- 2019-01-15T11:27:46.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 11:27:46','YYYY-MM-DD HH24:MI:SS'),Description='Mindestmenge ab der die Kondition gilt',Help='' WHERE AD_Element_ID=1708 AND AD_Language='nl_NL'
;

-- 2019-01-15T11:27:46.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1708,'nl_NL') 
;

-- 2019-01-15T11:32:26.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 11:32:26','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Preissystem',PrintName='Preissystem' WHERE AD_Element_ID=543951 AND AD_Language='de_CH'
;

-- 2019-01-15T11:32:26.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543951,'de_CH') 
;

-- 2019-01-15T11:32:45.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 11:32:45','YYYY-MM-DD HH24:MI:SS'),Name='Pricing system',PrintName='Pricing system' WHERE AD_Element_ID=543951 AND AD_Language='en_US'
;

-- 2019-01-15T11:32:45.608
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543951,'en_US') 
;

-- 2019-01-15T11:32:59.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 11:32:59','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Preissystem',PrintName='Preissystem' WHERE AD_Element_ID=543951 AND AD_Language='de_DE'
;

-- 2019-01-15T11:32:59.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543951,'de_DE') 
;

-- 2019-01-15T11:32:59.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543951,'de_DE') 
;

-- 2019-01-15T11:32:59.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Base_PricingSystem_ID', Name='Preissystem', Description=NULL, Help=NULL WHERE AD_Element_ID=543951
;

-- 2019-01-15T11:32:59.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Base_PricingSystem_ID', Name='Preissystem', Description=NULL, Help=NULL, AD_Element_ID=543951 WHERE UPPER(ColumnName)='BASE_PRICINGSYSTEM_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-01-15T11:32:59.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Base_PricingSystem_ID', Name='Preissystem', Description=NULL, Help=NULL WHERE AD_Element_ID=543951 AND IsCentrallyMaintained='Y'
;

-- 2019-01-15T11:32:59.330
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Preissystem', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543951) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543951)
;

-- 2019-01-15T11:32:59.351
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Preissystem', Name='Preissystem' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543951)
;

-- 2019-01-15T11:32:59.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Preissystem', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543951
;

-- 2019-01-15T11:32:59.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Preissystem', Description=NULL, Help=NULL WHERE AD_Element_ID = 543951
;

-- 2019-01-15T11:32:59.375
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Preissystem', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543951
;

-- 2019-01-15T11:45:10.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575949,0,'LastInOutDate',TO_TIMESTAMP('2019-01-15 11:45:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Letzter Warenein- oder ausgang','Letzter Warenein- oder Ausgang',TO_TIMESTAMP('2019-01-15 11:45:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-01-15T11:45:10.997
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=575949 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-01-15T11:46:04.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 11:46:04','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Letzte(r) Wareneingang / Lieferung',PrintName='Letzte(r) Wareneingang / Lieferung' WHERE AD_Element_ID=575949 AND AD_Language='de_CH'
;

-- 2019-01-15T11:46:04.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575949,'de_CH') 
;

-- 2019-01-15T11:46:12.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 11:46:12','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Letzte(r) Wareneingang / Lieferung',PrintName='Letzte(r) Wareneingang / Lieferung' WHERE AD_Element_ID=575949 AND AD_Language='de_DE'
;

-- 2019-01-15T11:46:12.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575949,'de_DE') 
;

-- 2019-01-15T11:46:12.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(575949,'de_DE') 
;

-- 2019-01-15T11:46:12.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='LastInOutDate', Name='Letzte(r) Wareneingang / Lieferung', Description=NULL, Help=NULL WHERE AD_Element_ID=575949
;

-- 2019-01-15T11:46:12.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastInOutDate', Name='Letzte(r) Wareneingang / Lieferung', Description=NULL, Help=NULL, AD_Element_ID=575949 WHERE UPPER(ColumnName)='LASTINOUTDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-01-15T11:46:12.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='LastInOutDate', Name='Letzte(r) Wareneingang / Lieferung', Description=NULL, Help=NULL WHERE AD_Element_ID=575949 AND IsCentrallyMaintained='Y'
;

-- 2019-01-15T11:46:12.261
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Letzte(r) Wareneingang / Lieferung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575949) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575949)
;

-- 2019-01-15T11:46:12.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Letzte(r) Wareneingang / Lieferung', Name='Letzte(r) Wareneingang / Lieferung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575949)
;

-- 2019-01-15T11:46:12.282
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Letzte(r) Wareneingang / Lieferung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 575949
;

-- 2019-01-15T11:46:12.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Letzte(r) Wareneingang / Lieferung', Description=NULL, Help=NULL WHERE AD_Element_ID = 575949
;

-- 2019-01-15T11:46:12.296
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Name='Letzte(r) Wareneingang / Lieferung', Description=NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 575949
;

-- 2019-01-15T11:46:30.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-15 11:46:30','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Last receipt or shipment',PrintName='Last receipt or shipment' WHERE AD_Element_ID=575949 AND AD_Language='en_US'
;

-- 2019-01-15T11:46:30.286
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575949,'en_US') 
;

