-- 2021-05-01T08:04:18.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579086,0,TO_TIMESTAMP('2021-05-01 11:04:18','YYYY-MM-DD HH24:MI:SS'),100,'Name of the external system parameter','de.metas.externalsystem','Y','Name','Name',TO_TIMESTAMP('2021-05-01 11:04:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-01T08:04:18.268Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579086 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-01T08:04:32.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Name des Parameters für das externe System',Updated=TO_TIMESTAMP('2021-05-01 11:04:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579086 AND AD_Language='de_CH'
;

-- 2021-05-01T08:04:32.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579086,'de_CH') 
;

-- 2021-05-01T08:04:38.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Name des Parameters für das externe System',Updated=TO_TIMESTAMP('2021-05-01 11:04:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579086 AND AD_Language='de_DE'
;

-- 2021-05-01T08:04:38.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579086,'de_DE') 
;

-- 2021-05-01T08:04:38.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579086,'de_DE') 
;

-- 2021-05-01T08:04:38.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Name', Description='Name des Parameters für das externe System', Help=NULL WHERE AD_Element_ID=579086
;

-- 2021-05-01T08:04:38.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Name', Description='Name des Parameters für das externe System', Help=NULL WHERE AD_Element_ID=579086 AND IsCentrallyMaintained='Y'
;

-- 2021-05-01T08:04:38.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Name', Description='Name des Parameters für das externe System', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579086) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579086)
;

-- 2021-05-01T08:04:38.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Name', Description='Name des Parameters für das externe System', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579086
;

-- 2021-05-01T08:04:38.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Name', Description='Name des Parameters für das externe System', Help=NULL WHERE AD_Element_ID = 579086
;

-- 2021-05-01T08:04:38.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Name', Description = 'Name des Parameters für das externe System', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579086
;

-- 2021-05-01T08:04:41.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Name des Parameters für das externe System',Updated=TO_TIMESTAMP('2021-05-01 11:04:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579086 AND AD_Language='nl_NL'
;

-- 2021-05-01T08:04:41.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579086,'nl_NL') 
;

-- 2021-05-01T08:05:24.515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579087,0,TO_TIMESTAMP('2021-05-01 11:05:24','YYYY-MM-DD HH24:MI:SS'),100,'Parameter value.','de.metas.externalsystem','Y','Value','Value',TO_TIMESTAMP('2021-05-01 11:05:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-01T08:05:24.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579087 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-01T08:05:37.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Parameter-Wert.', Name='Wert', PrintName='Wert',Updated=TO_TIMESTAMP('2021-05-01 11:05:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579087 AND AD_Language='nl_NL'
;

-- 2021-05-01T08:05:37.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579087,'nl_NL') 
;

-- 2021-05-01T08:05:55.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Parameter-Wert.', Name='Wert', PrintName='Wert',Updated=TO_TIMESTAMP('2021-05-01 11:05:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579087 AND AD_Language='de_DE'
;

-- 2021-05-01T08:05:55.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579087,'de_DE') 
;

-- 2021-05-01T08:05:55.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579087,'de_DE') 
;

-- 2021-05-01T08:05:55.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Wert', Description='Parameter-Wert.', Help=NULL WHERE AD_Element_ID=579087
;

-- 2021-05-01T08:05:55.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Wert', Description='Parameter-Wert.', Help=NULL WHERE AD_Element_ID=579087 AND IsCentrallyMaintained='Y'
;

-- 2021-05-01T08:05:55.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Wert', Description='Parameter-Wert.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579087) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579087)
;

-- 2021-05-01T08:05:55.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Wert', Name='Wert' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579087)
;

-- 2021-05-01T08:05:55.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Wert', Description='Parameter-Wert.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579087
;

-- 2021-05-01T08:05:55.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Wert', Description='Parameter-Wert.', Help=NULL WHERE AD_Element_ID = 579087
;

-- 2021-05-01T08:05:55.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Wert', Description = 'Parameter-Wert.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579087
;

-- 2021-05-01T08:06:10.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Parameter-Wert.', Name='Wert', PrintName='Wert',Updated=TO_TIMESTAMP('2021-05-01 11:06:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579087 AND AD_Language='de_CH'
;

-- 2021-05-01T08:06:10.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579087,'de_CH') 
;

-- 2021-05-01T08:09:44.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=579086, Description='Name des Parameters für das externe System', Help=NULL, Name='Name',Updated=TO_TIMESTAMP('2021-05-01 11:09:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=644797
;

-- 2021-05-01T08:09:44.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579086)
;

-- 2021-05-01T08:09:44.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644797
;

-- 2021-05-01T08:09:44.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(644797)
;

-- 2021-05-01T08:10:18.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=579087, Description='Parameter-Wert.', Help=NULL, Name='Wert',Updated=TO_TIMESTAMP('2021-05-01 11:10:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=644798
;

-- 2021-05-01T08:10:18.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579087)
;

-- 2021-05-01T08:10:18.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644798
;

-- 2021-05-01T08:10:18.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(644798)
;

