-- 2021-11-24T11:21:12.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580291,0,TO_TIMESTAMP('2021-11-24 13:21:12','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Open quantity','Open quantity',TO_TIMESTAMP('2021-11-24 13:21:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-24T11:21:12.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580291 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-11-24T11:21:43.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Offene Menge', PrintName='Offene Menge',Updated=TO_TIMESTAMP('2021-11-24 13:21:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580291 AND AD_Language='de_CH'
;

-- 2021-11-24T11:21:43.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580291,'de_CH') 
;

-- 2021-11-24T11:21:47.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Offene Menge', PrintName='Offene Menge',Updated=TO_TIMESTAMP('2021-11-24 13:21:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580291 AND AD_Language='de_DE'
;

-- 2021-11-24T11:21:47.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580291,'de_DE') 
;

-- 2021-11-24T11:21:47.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580291,'de_DE') 
;

-- 2021-11-24T11:21:47.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Offene Menge', Description=NULL, Help=NULL WHERE AD_Element_ID=580291
;

-- 2021-11-24T11:21:47.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Offene Menge', Description=NULL, Help=NULL WHERE AD_Element_ID=580291 AND IsCentrallyMaintained='Y'
;

-- 2021-11-24T11:21:47.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Offene Menge', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580291) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580291)
;

-- 2021-11-24T11:21:47.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Offene Menge', Name='Offene Menge' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580291)
;

-- 2021-11-24T11:21:47.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Offene Menge', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580291
;

-- 2021-11-24T11:21:47.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Offene Menge', Description=NULL, Help=NULL WHERE AD_Element_ID = 580291
;

-- 2021-11-24T11:21:47.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Offene Menge', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580291
;

-- 2021-11-24T11:21:50.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Offene Menge', PrintName='Offene Menge',Updated=TO_TIMESTAMP('2021-11-24 13:21:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580291 AND AD_Language='nl_NL'
;

-- 2021-11-24T11:21:50.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580291,'nl_NL') 
;

-- 2021-11-24T11:22:20.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=580291, Description=NULL, Help=NULL, Name='Offene Menge',Updated=TO_TIMESTAMP('2021-11-24 13:22:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669112
;

-- 2021-11-24T11:22:20.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580291) 
;

-- 2021-11-24T11:22:20.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=669112
;

-- 2021-11-24T11:22:20.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(669112)
;

-- 2021-11-24T11:22:53.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580292,0,TO_TIMESTAMP('2021-11-24 13:22:52','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Produced quantity','Produced quantity',TO_TIMESTAMP('2021-11-24 13:22:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-24T11:22:53.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580292 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-11-24T11:23:02.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produzierte Menge', PrintName='Produzierte Menge',Updated=TO_TIMESTAMP('2021-11-24 13:23:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580292 AND AD_Language='de_CH'
;

-- 2021-11-24T11:23:02.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580292,'de_CH') 
;

-- 2021-11-24T11:23:05.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produzierte Menge', PrintName='Produzierte Menge',Updated=TO_TIMESTAMP('2021-11-24 13:23:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580292 AND AD_Language='de_DE'
;

-- 2021-11-24T11:23:05.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580292,'de_DE') 
;

-- 2021-11-24T11:23:05.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580292,'de_DE') 
;

-- 2021-11-24T11:23:05.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Produzierte Menge', Description=NULL, Help=NULL WHERE AD_Element_ID=580292
;

-- 2021-11-24T11:23:05.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Produzierte Menge', Description=NULL, Help=NULL WHERE AD_Element_ID=580292 AND IsCentrallyMaintained='Y'
;

-- 2021-11-24T11:23:05.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produzierte Menge', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580292) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580292)
;

-- 2021-11-24T11:23:05.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Produzierte Menge', Name='Produzierte Menge' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580292)
;

-- 2021-11-24T11:23:05.379Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produzierte Menge', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580292
;

-- 2021-11-24T11:23:05.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Produzierte Menge', Description=NULL, Help=NULL WHERE AD_Element_ID = 580292
;

-- 2021-11-24T11:23:05.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Produzierte Menge', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580292
;

-- 2021-11-24T11:23:10.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produzierte Menge', PrintName='Produzierte Menge',Updated=TO_TIMESTAMP('2021-11-24 13:23:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580292 AND AD_Language='nl_NL'
;

-- 2021-11-24T11:23:10.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580292,'nl_NL') 
;

-- 2021-11-24T11:23:43.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=580292, Description=NULL, Help=NULL, Name='Produzierte Menge',Updated=TO_TIMESTAMP('2021-11-24 13:23:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=669111
;

-- 2021-11-24T11:23:43.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580292) 
;

-- 2021-11-24T11:23:43.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=669111
;

-- 2021-11-24T11:23:43.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(669111)
;

-- 2021-11-24T11:25:40.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580293,0,TO_TIMESTAMP('2021-11-24 13:25:40','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Manufacturing candidate','Manufacturing candidate',TO_TIMESTAMP('2021-11-24 13:25:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-24T11:25:40.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580293 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-11-24T11:25:47.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produktionsdisposition', PrintName='Produktionsdisposition',Updated=TO_TIMESTAMP('2021-11-24 13:25:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580293 AND AD_Language='de_CH'
;

-- 2021-11-24T11:25:47.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580293,'de_CH') 
;

-- 2021-11-24T11:25:49.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produktionsdisposition', PrintName='Produktionsdisposition',Updated=TO_TIMESTAMP('2021-11-24 13:25:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580293 AND AD_Language='de_DE'
;

-- 2021-11-24T11:25:49.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580293,'de_DE') 
;

-- 2021-11-24T11:25:49.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580293,'de_DE') 
;

-- 2021-11-24T11:25:49.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Produktionsdisposition', Description=NULL, Help=NULL WHERE AD_Element_ID=580293
;

-- 2021-11-24T11:25:49.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Produktionsdisposition', Description=NULL, Help=NULL WHERE AD_Element_ID=580293 AND IsCentrallyMaintained='Y'
;

-- 2021-11-24T11:25:49.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produktionsdisposition', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580293) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580293)
;

-- 2021-11-24T11:25:49.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Produktionsdisposition', Name='Produktionsdisposition' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580293)
;

-- 2021-11-24T11:25:49.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produktionsdisposition', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580293
;

-- 2021-11-24T11:25:49.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Produktionsdisposition', Description=NULL, Help=NULL WHERE AD_Element_ID = 580293
;

-- 2021-11-24T11:25:49.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Produktionsdisposition', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580293
;

-- 2021-11-24T11:25:53.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produktionsdisposition', PrintName='Produktionsdisposition',Updated=TO_TIMESTAMP('2021-11-24 13:25:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580293 AND AD_Language='nl_NL'
;

-- 2021-11-24T11:25:53.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580293,'nl_NL') 
;

-- 2021-11-24T11:26:11.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET AD_Element_ID=580293, Description=NULL, InternalName='Manufacturing candidate', Name='Produktionsdisposition', WEBUI_NameBrowse=NULL, WEBUI_NameNew=NULL, WEBUI_NameNewBreadcrumb=NULL,Updated=TO_TIMESTAMP('2021-11-24 13:26:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541831
;

-- 2021-11-24T11:26:11.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(580293) 
;

-- 2021-11-24T11:27:50.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580294,0,TO_TIMESTAMP('2021-11-24 13:27:50','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Manufacturing candidate line','Manufacturing candidate line',TO_TIMESTAMP('2021-11-24 13:27:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-24T11:27:50.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580294 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-11-24T11:27:57.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produktionsdispozeile', PrintName='Produktionsdispozeile',Updated=TO_TIMESTAMP('2021-11-24 13:27:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580294 AND AD_Language='de_CH'
;

-- 2021-11-24T11:27:57.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580294,'de_CH') 
;

-- 2021-11-24T11:28:00.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produktionsdispozeile', PrintName='Produktionsdispozeile',Updated=TO_TIMESTAMP('2021-11-24 13:28:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580294 AND AD_Language='de_DE'
;

-- 2021-11-24T11:28:00.515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580294,'de_DE') 
;

-- 2021-11-24T11:28:00.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580294,'de_DE') 
;

-- 2021-11-24T11:28:00.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Produktionsdispozeile', Description=NULL, Help=NULL WHERE AD_Element_ID=580294
;

-- 2021-11-24T11:28:00.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Produktionsdispozeile', Description=NULL, Help=NULL WHERE AD_Element_ID=580294 AND IsCentrallyMaintained='Y'
;

-- 2021-11-24T11:28:00.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produktionsdispozeile', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580294) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580294)
;

-- 2021-11-24T11:28:00.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Produktionsdispozeile', Name='Produktionsdispozeile' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580294)
;

-- 2021-11-24T11:28:00.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produktionsdispozeile', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580294
;

-- 2021-11-24T11:28:00.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Produktionsdispozeile', Description=NULL, Help=NULL WHERE AD_Element_ID = 580294
;

-- 2021-11-24T11:28:00.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Produktionsdispozeile', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580294
;

-- 2021-11-24T11:28:03.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produktionsdispozeile', PrintName='Produktionsdispozeile',Updated=TO_TIMESTAMP('2021-11-24 13:28:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580294 AND AD_Language='nl_NL'
;

-- 2021-11-24T11:28:03.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580294,'nl_NL') 
;

-- 2021-11-24T11:28:34.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Element_ID=580294, CommitWarning=NULL, Description=NULL, Help=NULL, Name='Produktionsdispozeile',Updated=TO_TIMESTAMP('2021-11-24 13:28:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544808
;

-- 2021-11-24T11:28:34.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(580294) 
;

-- 2021-11-24T11:28:34.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(544808)
;

-- 2021-11-24T11:29:55.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Manufacture selection',Updated=TO_TIMESTAMP('2021-11-24 13:29:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584935
;

-- 2021-11-24T11:30:02.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Auswahl produzieren',Updated=TO_TIMESTAMP('2021-11-24 13:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584935
;

-- 2021-11-24T11:30:04.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Auswahl produzieren',Updated=TO_TIMESTAMP('2021-11-24 13:30:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584935
;

-- 2021-11-24T11:30:04.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Auswahl produzieren',Updated=TO_TIMESTAMP('2021-11-24 13:30:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584935
;

-- 2021-11-24T11:30:09.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Manufacture selection',Updated=TO_TIMESTAMP('2021-11-24 13:30:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584935
;

-- 2021-11-24T11:30:13.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Auswahl produzieren',Updated=TO_TIMESTAMP('2021-11-24 13:30:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584935
;

-- 2021-11-24T11:31:29.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Close selection',Updated=TO_TIMESTAMP('2021-11-24 13:31:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584949
;

-- 2021-11-24T11:31:40.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Auswahl schließen',Updated=TO_TIMESTAMP('2021-11-24 13:31:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584949
;

-- 2021-11-24T11:31:40.161Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Auswahl schließen',Updated=TO_TIMESTAMP('2021-11-24 13:31:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584949
;

-- 2021-11-24T11:31:42.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Auswahl schließen',Updated=TO_TIMESTAMP('2021-11-24 13:31:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584949
;

-- 2021-11-24T11:31:48.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Close selection',Updated=TO_TIMESTAMP('2021-11-24 13:31:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584949
;

-- 2021-11-24T11:31:52.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Auswahl schließen',Updated=TO_TIMESTAMP('2021-11-24 13:31:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584949
;

-- 2021-11-24T11:32:09.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Reopen selection',Updated=TO_TIMESTAMP('2021-11-24 13:32:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584948
;

-- 2021-11-24T11:32:28.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Auswahl wieder öffen',Updated=TO_TIMESTAMP('2021-11-24 13:32:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584948
;

-- 2021-11-24T11:32:29.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Auswahl wieder öffen',Updated=TO_TIMESTAMP('2021-11-24 13:32:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584948
;

-- 2021-11-24T11:32:29.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Auswahl wieder öffen',Updated=TO_TIMESTAMP('2021-11-24 13:32:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584948
;

-- 2021-11-24T11:32:34.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Reopen selection',Updated=TO_TIMESTAMP('2021-11-24 13:32:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584948
;

-- 2021-11-24T11:32:38.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Auswahl wieder öffen',Updated=TO_TIMESTAMP('2021-11-24 13:32:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584948
;

-- 2021-11-24T11:33:58.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Creates production orders for the selected and not yet processed candidate lines.',Updated=TO_TIMESTAMP('2021-11-24 13:33:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584935
;

-- 2021-11-24T11:34:12.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Creates production orders for the selected and not yet processed candidate lines.',Updated=TO_TIMESTAMP('2021-11-24 13:34:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584935
;

-- 2021-11-24T11:34:15.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Erstellt Produktionsaufträge zu den ausgewählten und noch nicht verarbeiteten Dispozeilen.', Help=NULL, Name='Auswahl produzieren',Updated=TO_TIMESTAMP('2021-11-24 13:34:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584935
;

-- 2021-11-24T11:34:15.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Erstellt Produktionsaufträge zu den ausgewählten und noch nicht verarbeiteten Dispozeilen.',Updated=TO_TIMESTAMP('2021-11-24 13:34:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584935
;

-- 2021-11-24T11:34:17.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Erstellt Produktionsaufträge zu den ausgewählten und noch nicht verarbeiteten Dispozeilen.',Updated=TO_TIMESTAMP('2021-11-24 13:34:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584935
;

-- 2021-11-24T11:34:19.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Erstellt Produktionsaufträge zu den ausgewählten und noch nicht verarbeiteten Dispozeilen.',Updated=TO_TIMESTAMP('2021-11-24 13:34:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584935
;

-- 2021-11-24T11:35:37.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Opens the selected and closed candidate lines.',Updated=TO_TIMESTAMP('2021-11-24 13:35:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584948
;

-- 2021-11-24T11:35:43.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Opens the selected and closed candidate lines.',Updated=TO_TIMESTAMP('2021-11-24 13:35:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584948
;

-- 2021-11-24T11:35:48.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Öffnet die ausgewählten und geschlossenen Dispozeilen.',Updated=TO_TIMESTAMP('2021-11-24 13:35:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584948
;

-- 2021-11-24T11:35:50.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Öffnet die ausgewählten und geschlossenen Dispozeilen.', Help=NULL, Name='Auswahl wieder öffen',Updated=TO_TIMESTAMP('2021-11-24 13:35:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584948
;

-- 2021-11-24T11:35:50.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Öffnet die ausgewählten und geschlossenen Dispozeilen.',Updated=TO_TIMESTAMP('2021-11-24 13:35:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584948
;

-- 2021-11-24T11:35:52.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Öffnet die ausgewählten und geschlossenen Dispozeilen.',Updated=TO_TIMESTAMP('2021-11-24 13:35:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584948
;

-- 2021-11-24T14:32:50.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=547106, SeqNo=50,Updated=TO_TIMESTAMP('2021-11-24 16:32:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=594542
;

-- 2021-11-24T14:53:10.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Description='Manufacturing candidate', Name='Manufacturing candidate',Updated=TO_TIMESTAMP('2021-11-24 16:53:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541913
;

-- 2021-11-24T14:53:38.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Produktionsdisposition',Updated=TO_TIMESTAMP('2021-11-24 16:53:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=541913
;

-- 2021-11-24T14:53:42.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Produktionsdisposition',Updated=TO_TIMESTAMP('2021-11-24 16:53:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Table_ID=541913
;

-- 2021-11-24T14:53:49.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Manufacturing candidate',Updated=TO_TIMESTAMP('2021-11-24 16:53:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=541913
;

-- 2021-11-24T14:53:53.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET Name='Produktionsdisposition',Updated=TO_TIMESTAMP('2021-11-24 16:53:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=541913
;

-- 2021-11-24T14:58:12.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produktionsdisposition', PrintName='Produktionsdisposition',Updated=TO_TIMESTAMP('2021-11-24 16:58:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580109 AND AD_Language='de_CH'
;

-- 2021-11-24T14:58:12.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580109,'de_CH')
;

-- 2021-11-24T14:58:14.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produktionsdisposition', PrintName='Produktionsdisposition',Updated=TO_TIMESTAMP('2021-11-24 16:58:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580109 AND AD_Language='de_DE'
;

-- 2021-11-24T14:58:14.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580109,'de_DE')
;

-- 2021-11-24T14:58:14.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580109,'de_DE')
;

-- 2021-11-24T14:58:14.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PP_Order_Candidate_ID', Name='Produktionsdisposition', Description=NULL, Help=NULL WHERE AD_Element_ID=580109
;

-- 2021-11-24T14:58:14.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PP_Order_Candidate_ID', Name='Produktionsdisposition', Description=NULL, Help=NULL, AD_Element_ID=580109 WHERE UPPER(ColumnName)='PP_ORDER_CANDIDATE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-11-24T14:58:15Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PP_Order_Candidate_ID', Name='Produktionsdisposition', Description=NULL, Help=NULL WHERE AD_Element_ID=580109 AND IsCentrallyMaintained='Y'
;

-- 2021-11-24T14:58:15.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produktionsdisposition', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580109) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580109)
;

-- 2021-11-24T14:58:15.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Produktionsdisposition', Name='Produktionsdisposition' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580109)
;

-- 2021-11-24T14:58:15.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produktionsdisposition', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580109
;

-- 2021-11-24T14:58:15.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Produktionsdisposition', Description=NULL, Help=NULL WHERE AD_Element_ID = 580109
;

-- 2021-11-24T14:58:15.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Produktionsdisposition', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580109
;

-- 2021-11-24T14:58:20.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Manufacturing candidate', PrintName='Manufacturing candidate',Updated=TO_TIMESTAMP('2021-11-24 16:58:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580109 AND AD_Language='en_US'
;

-- 2021-11-24T14:58:20.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580109,'en_US')
;

-- 2021-11-24T14:58:25.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Produktionsdisposition', PrintName='Produktionsdisposition',Updated=TO_TIMESTAMP('2021-11-24 16:58:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580109 AND AD_Language='nl_NL'
;

-- 2021-11-24T14:58:25.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580109,'nl_NL')
;

-- 2021-11-24T15:08:47.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Closes the selected and not yet processed candidate lines.',Updated=TO_TIMESTAMP('2021-11-24 17:08:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584949
;

-- 2021-11-24T15:09:05.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Schliesst die ausgewählten und noch nicht verarbeiteten Dispozeilen.',Updated=TO_TIMESTAMP('2021-11-24 17:09:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584949
;

-- 2021-11-24T15:09:06.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Schliesst die ausgewählten und noch nicht verarbeiteten Dispozeilen.', Help=NULL, Name='Auswahl schließen',Updated=TO_TIMESTAMP('2021-11-24 17:09:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584949
;

-- 2021-11-24T15:09:06.775Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Schliesst die ausgewählten und noch nicht verarbeiteten Dispozeilen.',Updated=TO_TIMESTAMP('2021-11-24 17:09:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584949
;

-- 2021-11-24T15:09:13.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Closes the selected and not yet processed candidate lines.',Updated=TO_TIMESTAMP('2021-11-24 17:09:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584949
;

-- 2021-11-24T15:09:21.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Schliesst die ausgewählten und noch nicht verarbeiteten Dispozeilen.',Updated=TO_TIMESTAMP('2021-11-24 17:09:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584949
;