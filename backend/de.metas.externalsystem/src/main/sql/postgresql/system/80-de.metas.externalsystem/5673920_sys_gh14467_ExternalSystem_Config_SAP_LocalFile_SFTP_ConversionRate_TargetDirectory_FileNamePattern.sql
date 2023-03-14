-- 2023-01-27T12:04:23.807Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581969,0,'LocalFile_ConversionRate_TargetDirectory',TO_TIMESTAMP('2023-01-27 14:04:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Conversion Rate Target Directory','Conversion Rate Target Directory',TO_TIMESTAMP('2023-01-27 14:04:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T12:04:23.814Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581969 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: LocalFile_ConversionRate_TargetDirectory
-- 2023-01-27T12:05:33.272Z
UPDATE AD_Element_Trl SET Name='Konversionsrate-Zielverzeichnis', PrintName='Konversionsrate-Zielverzeichnis',Updated=TO_TIMESTAMP('2023-01-27 14:05:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581969 AND AD_Language='de_CH'
;

-- 2023-01-27T12:05:33.299Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581969,'de_CH') 
;

-- Element: LocalFile_ConversionRate_TargetDirectory
-- 2023-01-27T12:05:35.710Z
UPDATE AD_Element_Trl SET Name='Konversionsrate-Zielverzeichnis',Updated=TO_TIMESTAMP('2023-01-27 14:05:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581969 AND AD_Language='de_DE'
;

-- 2023-01-27T12:05:35.712Z
UPDATE AD_Element SET Name='Konversionsrate-Zielverzeichnis' WHERE AD_Element_ID=581969
;

-- 2023-01-27T12:05:36.009Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581969,'de_DE') 
;

-- 2023-01-27T12:05:36.013Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581969,'de_DE') 
;

-- Element: LocalFile_ConversionRate_TargetDirectory
-- 2023-01-27T12:06:27.806Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Konvertierungsraten vom lokalen Rechner verwendet wird. (Wenn hier kein Wert angegeben wird, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis abgerufen).', PrintName='Konversionsrate-Zielverzeichnis',Updated=TO_TIMESTAMP('2023-01-27 14:06:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581969 AND AD_Language='de_DE'
;

-- 2023-01-27T12:06:27.807Z
UPDATE AD_Element SET Description='Verzeichnis, das zum Abrufen von Konvertierungsraten vom lokalen Rechner verwendet wird. (Wenn hier kein Wert angegeben wird, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis abgerufen).', PrintName='Konversionsrate-Zielverzeichnis' WHERE AD_Element_ID=581969
;

-- 2023-01-27T12:06:28.093Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581969,'de_DE') 
;

-- 2023-01-27T12:06:28.094Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581969,'de_DE') 
;

-- Element: LocalFile_ConversionRate_TargetDirectory
-- 2023-01-27T12:06:30.486Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Konvertierungsraten vom lokalen Rechner verwendet wird. (Wenn hier kein Wert angegeben wird, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis abgerufen).',Updated=TO_TIMESTAMP('2023-01-27 14:06:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581969 AND AD_Language='de_CH'
;

-- 2023-01-27T12:06:30.489Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581969,'de_CH') 
;

-- Element: LocalFile_ConversionRate_TargetDirectory
-- 2023-01-27T12:07:05.761Z
UPDATE AD_Element_Trl SET Description='Directory used to pull conversion rates from the local machine. (If no value is specified here, the files are pulled from the configured local root location)',Updated=TO_TIMESTAMP('2023-01-27 14:07:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581969 AND AD_Language='en_US'
;

-- 2023-01-27T12:07:05.762Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581969,'en_US') 
;

-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_ConversionRate_TargetDirectory
-- 2023-01-27T12:07:52.583Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585658,581969,0,10,542258,'LocalFile_ConversionRate_TargetDirectory',TO_TIMESTAMP('2023-01-27 14:07:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Verzeichnis, das zum Abrufen von Konvertierungsraten vom lokalen Rechner verwendet wird. (Wenn hier kein Wert angegeben wird, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis abgerufen).','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Konversionsrate-Zielverzeichnis',0,0,TO_TIMESTAMP('2023-01-27 14:07:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-27T12:07:52.591Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585658 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-27T12:07:52.598Z
/* DDL */  select update_Column_Translation_From_AD_Element(581969) 
;

-- 2023-01-27T12:07:53.620Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE public.ExternalSystem_Config_SAP_LocalFile ADD COLUMN LocalFile_ConversionRate_TargetDirectory VARCHAR(255)')
;

-- 2023-01-27T12:09:24.668Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581970,0,'LocalFile_ConversionRate_FileName_Pattern',TO_TIMESTAMP('2023-01-27 14:09:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Conversion Rate File Name Pattern','Conversion Rate File Name Pattern',TO_TIMESTAMP('2023-01-27 14:09:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T12:09:24.672Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581970 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: LocalFile_ConversionRate_FileName_Pattern
-- 2023-01-27T12:09:33.060Z
UPDATE AD_Element_Trl SET Name='Konversionsrate Dateinamen-Muster', PrintName='Konversionsrate Dateinamen-Muster',Updated=TO_TIMESTAMP('2023-01-27 14:09:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581970 AND AD_Language='de_CH'
;

-- 2023-01-27T12:09:33.062Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581970,'de_CH') 
;

-- Element: LocalFile_ConversionRate_FileName_Pattern
-- 2023-01-27T12:09:34.344Z
UPDATE AD_Element_Trl SET Name='Konversionsrate Dateinamen-Muster',Updated=TO_TIMESTAMP('2023-01-27 14:09:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581970 AND AD_Language='de_DE'
;

-- 2023-01-27T12:09:34.345Z
UPDATE AD_Element SET Name='Konversionsrate Dateinamen-Muster' WHERE AD_Element_ID=581970
;

-- 2023-01-27T12:09:34.885Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581970,'de_DE') 
;

-- 2023-01-27T12:09:34.886Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581970,'de_DE') 
;

-- Element: LocalFile_ConversionRate_FileName_Pattern
-- 2023-01-27T12:10:03.032Z
UPDATE AD_Element_Trl SET PrintName='Konversionsrate Dateinamen-Muster',Updated=TO_TIMESTAMP('2023-01-27 14:10:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581970 AND AD_Language='de_DE'
;

-- 2023-01-27T12:10:03.033Z
UPDATE AD_Element SET PrintName='Konversionsrate Dateinamen-Muster' WHERE AD_Element_ID=581970
;

-- 2023-01-27T12:10:03.468Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581970,'de_DE') 
;

-- 2023-01-27T12:10:03.469Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581970,'de_DE') 
;

-- Element: LocalFile_ConversionRate_FileName_Pattern
-- 2023-01-27T12:10:04.523Z
UPDATE AD_Element_Trl SET Description='Muster zur Identifizierung von Dateien mit Konvertierungsraten auf dem lokalen Rechner. (Wenn nicht angegeben, werden alle Dateien berücksichtigt)',Updated=TO_TIMESTAMP('2023-01-27 14:10:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581970 AND AD_Language='de_CH'
;

-- 2023-01-27T12:10:04.525Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581970,'de_CH') 
;

-- Element: LocalFile_ConversionRate_FileName_Pattern
-- 2023-01-27T12:10:17.616Z
UPDATE AD_Element_Trl SET Description='Muster zur Identifizierung von Dateien mit Konvertierungsraten auf dem lokalen Rechner. (Wenn nicht angegeben, werden alle Dateien berücksichtigt).',Updated=TO_TIMESTAMP('2023-01-27 14:10:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581970 AND AD_Language='de_DE'
;

-- 2023-01-27T12:10:17.616Z
UPDATE AD_Element SET Description='Muster zur Identifizierung von Dateien mit Konvertierungsraten auf dem lokalen Rechner. (Wenn nicht angegeben, werden alle Dateien berücksichtigt).' WHERE AD_Element_ID=581970
;

-- 2023-01-27T12:10:18.056Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581970,'de_DE') 
;

-- 2023-01-27T12:10:18.057Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581970,'de_DE') 
;

-- Element: LocalFile_ConversionRate_FileName_Pattern
-- 2023-01-27T12:10:37.644Z
UPDATE AD_Element_Trl SET Description='Ant-style pattern used to identify conversion rate files on the local machine. (If not set, all files are considered)',Updated=TO_TIMESTAMP('2023-01-27 14:10:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581970 AND AD_Language='en_US'
;

-- 2023-01-27T12:10:37.646Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581970,'en_US') 
;

-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_ConversionRate_FileName_Pattern
-- 2023-01-27T12:11:02.279Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585659,581970,0,10,542258,'LocalFile_ConversionRate_FileName_Pattern',TO_TIMESTAMP('2023-01-27 14:11:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Muster zur Identifizierung von Dateien mit Konvertierungsraten auf dem lokalen Rechner. (Wenn nicht angegeben, werden alle Dateien berücksichtigt).','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Konversionsrate Dateinamen-Muster',0,0,TO_TIMESTAMP('2023-01-27 14:11:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-27T12:11:02.281Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585659 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-27T12:11:02.285Z
/* DDL */  select update_Column_Translation_From_AD_Element(581970) 
;

-- 2023-01-27T12:11:03.091Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE public.ExternalSystem_Config_SAP_LocalFile ADD COLUMN LocalFile_ConversionRate_FileName_Pattern VARCHAR(255)')
;

-- 2023-01-27T12:13:42.367Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581971,0,'SFTP_ConversionRate_TargetDirectory',TO_TIMESTAMP('2023-01-27 14:13:42','YYYY-MM-DD HH24:MI:SS'),100,'The directory used to retrieve conversion rates from the sftp server. (If no value is specified here, the files are pulled from the root directory of the sftp server)','D','Y','Conversion Rate Target Directory','Conversion Rate Target Directory',TO_TIMESTAMP('2023-01-27 14:13:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T12:13:42.370Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581971 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: SFTP_ConversionRate_TargetDirectory
-- 2023-01-27T12:14:40.362Z
UPDATE AD_Element_Trl SET Name='Konversionsrate-Zielverzeichnis', PrintName='Konversionsrate-Zielverzeichnis',Updated=TO_TIMESTAMP('2023-01-27 14:14:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581971 AND AD_Language='de_CH'
;

-- 2023-01-27T12:14:40.363Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581971,'de_CH') 
;

-- Element: SFTP_ConversionRate_TargetDirectory
-- 2023-01-27T12:15:11.283Z
UPDATE AD_Element_Trl SET Name='Konversionsrate-Zielverzeichnis', PrintName='Konversionsrate-Zielverzeichnis',Updated=TO_TIMESTAMP('2023-01-27 14:15:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581971 AND AD_Language='de_DE'
;

-- 2023-01-27T12:15:11.284Z
UPDATE AD_Element SET Name='Konversionsrate-Zielverzeichnis', PrintName='Konversionsrate-Zielverzeichnis' WHERE AD_Element_ID=581971
;

-- 2023-01-27T12:15:11.796Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581971,'de_DE') 
;

-- 2023-01-27T12:15:11.797Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581971,'de_DE') 
;

-- Element: SFTP_ConversionRate_TargetDirectory
-- 2023-01-27T12:15:13.384Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen der Konvertierungsraten vom sftp-Server verwendet wird. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers abgerufen).',Updated=TO_TIMESTAMP('2023-01-27 14:15:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581971 AND AD_Language='de_CH'
;

-- 2023-01-27T12:15:13.385Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581971,'de_CH') 
;

-- Element: SFTP_ConversionRate_TargetDirectory
-- 2023-01-27T12:37:17.106Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen der Konvertierungsraten vom sftp-Server verwendet wird. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers abgerufen).',Updated=TO_TIMESTAMP('2023-01-27 14:37:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581971 AND AD_Language='de_DE'
;

-- 2023-01-27T12:37:17.107Z
UPDATE AD_Element SET Description='Verzeichnis, das zum Abrufen der Konvertierungsraten vom sftp-Server verwendet wird. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers abgerufen).' WHERE AD_Element_ID=581971
;

-- 2023-01-27T12:37:17.570Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581971,'de_DE') 
;

-- 2023-01-27T12:37:17.574Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581971,'de_DE') 
;

-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_ConversionRate_TargetDirectory
-- 2023-01-27T12:37:44.751Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585660,581971,0,10,542257,'SFTP_ConversionRate_TargetDirectory',TO_TIMESTAMP('2023-01-27 14:37:44','YYYY-MM-DD HH24:MI:SS'),100,'N','Verzeichnis, das zum Abrufen der Konvertierungsraten vom sftp-Server verwendet wird. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers abgerufen).','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Konversionsrate-Zielverzeichnis',0,0,TO_TIMESTAMP('2023-01-27 14:37:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-27T12:37:44.755Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585660 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-27T12:37:44.771Z
/* DDL */  select update_Column_Translation_From_AD_Element(581971) 
;

-- 2023-01-27T12:37:45.808Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN SFTP_ConversionRate_TargetDirectory VARCHAR(255)')
;

-- 2023-01-27T12:39:10.715Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581972,0,'SFTP_ConversionRate_FileName_Pattern',TO_TIMESTAMP('2023-01-27 14:39:10','YYYY-MM-DD HH24:MI:SS'),100,'Muster zur Identifizierung von Dateien mit Konvertierungsraten auf dem SFTP-Server verwendet wird. (Wenn nicht angegeben, werden alle Dateien berücksichtigt).','D','Y','Konversionsrate Dateinamen-Muster','Konversionsrate Dateinamen-Muster',TO_TIMESTAMP('2023-01-27 14:39:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T12:39:10.716Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581972 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: SFTP_ConversionRate_FileName_Pattern
-- 2023-01-27T12:39:47.365Z
UPDATE AD_Element_Trl SET Description='Ant-style pattern used to identify conversion rate files on the SFTP-Server. (If not set, all files are considered)', Name='Conversion Rate File Name Pattern', PrintName='Conversion Rate File Name Pattern',Updated=TO_TIMESTAMP('2023-01-27 14:39:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581972 AND AD_Language='en_US'
;

-- 2023-01-27T12:39:47.367Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581972,'en_US') 
;

-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_ConversionRate_FileName_Pattern
-- 2023-01-27T12:40:01.127Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585661,581972,0,10,542257,'SFTP_ConversionRate_FileName_Pattern',TO_TIMESTAMP('2023-01-27 14:40:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Muster zur Identifizierung von Dateien mit Konvertierungsraten auf dem SFTP-Server verwendet wird. (Wenn nicht angegeben, werden alle Dateien berücksichtigt).','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Konversionsrate Dateinamen-Muster',0,0,TO_TIMESTAMP('2023-01-27 14:40:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-27T12:40:01.129Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585661 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-27T12:40:01.133Z
/* DDL */  select update_Column_Translation_From_AD_Element(581972) 
;

-- 2023-01-27T12:40:01.884Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN SFTP_ConversionRate_FileName_Pattern VARCHAR(255)')
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> Konversionsrate Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_ConversionRate_FileName_Pattern
-- 2023-01-27T12:41:41.133Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585661,710686,0,546672,0,TO_TIMESTAMP('2023-01-27 14:41:40','YYYY-MM-DD HH24:MI:SS'),100,'Muster zur Identifizierung von Dateien mit Konvertierungsraten auf dem SFTP-Server verwendet wird. (Wenn nicht angegeben, werden alle Dateien berücksichtigt).',0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','Konversionsrate Dateinamen-Muster',0,190,0,1,1,TO_TIMESTAMP('2023-01-27 14:41:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T12:41:41.140Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710686 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-27T12:41:41.143Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581972) 
;

-- 2023-01-27T12:41:41.155Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710686
;

-- 2023-01-27T12:41:41.159Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710686)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> Konversionsrate-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_ConversionRate_TargetDirectory
-- 2023-01-27T12:41:51.652Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585660,710687,0,546672,0,TO_TIMESTAMP('2023-01-27 14:41:51','YYYY-MM-DD HH24:MI:SS'),100,'Verzeichnis, das zum Abrufen der Konvertierungsraten vom sftp-Server verwendet wird. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers abgerufen).',0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','Konversionsrate-Zielverzeichnis',0,200,0,1,1,TO_TIMESTAMP('2023-01-27 14:41:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T12:41:51.657Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710687 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-27T12:41:51.659Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581971) 
;

-- 2023-01-27T12:41:51.662Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710687
;

-- 2023-01-27T12:41:51.665Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710687)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> Konversionsrate Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_ConversionRate_FileName_Pattern
-- 2023-01-27T12:42:17.259Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585659,710688,0,546673,0,TO_TIMESTAMP('2023-01-27 14:42:17','YYYY-MM-DD HH24:MI:SS'),100,'Muster zur Identifizierung von Dateien mit Konvertierungsraten auf dem lokalen Rechner. (Wenn nicht angegeben, werden alle Dateien berücksichtigt).',0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','Konversionsrate Dateinamen-Muster',0,160,0,1,1,TO_TIMESTAMP('2023-01-27 14:42:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T12:42:17.260Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710688 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-27T12:42:17.261Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581970) 
;

-- 2023-01-27T12:42:17.263Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710688
;

-- 2023-01-27T12:42:17.263Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710688)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> Konversionsrate-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_ConversionRate_TargetDirectory
-- 2023-01-27T12:42:42.924Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585658,710689,0,546673,0,TO_TIMESTAMP('2023-01-27 14:42:42','YYYY-MM-DD HH24:MI:SS'),100,'Verzeichnis, das zum Abrufen von Konvertierungsraten vom lokalen Rechner verwendet wird. (Wenn hier kein Wert angegeben wird, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis abgerufen).',0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','Konversionsrate-Zielverzeichnis',0,170,0,1,1,TO_TIMESTAMP('2023-01-27 14:42:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-27T12:42:42.926Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710689 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-27T12:42:42.928Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581969) 
;

-- 2023-01-27T12:42:42.932Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710689
;

-- 2023-01-27T12:42:42.933Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710689)
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10
-- UI Element Group: ConversionRate
-- 2023-01-27T12:43:05.343Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546450,550258,TO_TIMESTAMP('2023-01-27 14:43:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','ConversionRate',50,TO_TIMESTAMP('2023-01-27 14:43:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> ConversionRate.Konversionsrate Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_ConversionRate_FileName_Pattern
-- 2023-01-27T12:43:43.104Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,710688,0,546673,614885,550258,'F',TO_TIMESTAMP('2023-01-27 14:43:42','YYYY-MM-DD HH24:MI:SS'),100,'Muster zur Identifizierung von Dateien mit Konvertierungsraten auf dem lokalen Rechner. (Wenn nicht angegeben, werden alle Dateien berücksichtigt).','Y','N','N','Y','N','N','N',0,'Konversionsrate Dateinamen-Muster',10,0,0,TO_TIMESTAMP('2023-01-27 14:43:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> ConversionRate.Konversionsrate-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_ConversionRate_TargetDirectory
-- 2023-01-27T12:43:56.665Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,710689,0,546673,614886,550258,'F',TO_TIMESTAMP('2023-01-27 14:43:56','YYYY-MM-DD HH24:MI:SS'),100,'Verzeichnis, das zum Abrufen von Konvertierungsraten vom lokalen Rechner verwendet wird. (Wenn hier kein Wert angegeben wird, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis abgerufen).','Y','N','N','Y','N','N','N',0,'Konversionsrate-Zielverzeichnis',20,0,0,TO_TIMESTAMP('2023-01-27 14:43:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10
-- UI Element Group: ConversionRate
-- 2023-01-27T12:44:17.631Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546448,550259,TO_TIMESTAMP('2023-01-27 14:44:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','ConversionRate',50,TO_TIMESTAMP('2023-01-27 14:44:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10 -> ConversionRate.Konversionsrate Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_ConversionRate_FileName_Pattern
-- 2023-01-27T12:44:39.103Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,710686,0,546672,614887,550259,'F',TO_TIMESTAMP('2023-01-27 14:44:38','YYYY-MM-DD HH24:MI:SS'),100,'Muster zur Identifizierung von Dateien mit Konvertierungsraten auf dem SFTP-Server verwendet wird. (Wenn nicht angegeben, werden alle Dateien berücksichtigt).','Y','N','N','Y','N','N','N',0,'Konversionsrate Dateinamen-Muster',10,0,0,TO_TIMESTAMP('2023-01-27 14:44:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10 -> ConversionRate.Konversionsrate-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_ConversionRate_TargetDirectory
-- 2023-01-27T12:44:50.388Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,710687,0,546672,614888,550259,'F',TO_TIMESTAMP('2023-01-27 14:44:50','YYYY-MM-DD HH24:MI:SS'),100,'Verzeichnis, das zum Abrufen der Konvertierungsraten vom sftp-Server verwendet wird. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers abgerufen).','Y','N','N','Y','N','N','N',0,'Konversionsrate-Zielverzeichnis',20,0,0,TO_TIMESTAMP('2023-01-27 14:44:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: SAP accepted operations
-- Value: stopConversionRateSyncLocalFile
-- ValueName: import
-- 2023-01-27T12:50:21.839Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543395,541661,TO_TIMESTAMP('2023-01-27 14:50:21','YYYY-MM-DD HH24:MI:SS'),100,'Stoppt die Synchronisation des Konversionsrates mit dem externen SAP-System','de.metas.externalsystem','Y','Lokale Datei - Konversionsrate-Import - Stop',TO_TIMESTAMP('2023-01-27 14:50:21','YYYY-MM-DD HH24:MI:SS'),100,'stopConversionRateSyncLocalFile','import')
;

-- 2023-01-27T12:50:21.843Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543395 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: SAP accepted operations -> stopConversionRateSyncLocalFile_import
-- 2023-01-27T12:51:09.733Z
UPDATE AD_Ref_List_Trl SET Description='Stops the product synchronization with SAP external system from the local server''s file system.', Name='Local File - Conversion Rate Import - Stop',Updated=TO_TIMESTAMP('2023-01-27 14:51:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543395
;

-- Reference Item: SAP accepted operations -> stopConversionRateSyncLocalFile_import
-- 2023-01-27T12:51:11.483Z
UPDATE AD_Ref_List_Trl SET Description='Stoppt die Synchronisation des Konversionsrates mit dem externen SAP-System',Updated=TO_TIMESTAMP('2023-01-27 14:51:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543395
;

-- Reference Item: SAP accepted operations -> stopConversionRateSyncLocalFile_import
-- 2023-01-27T12:51:34.612Z
UPDATE AD_Ref_List_Trl SET Description='Stops the conversion rate synchronization with SAP external system from the local server''s file system.',Updated=TO_TIMESTAMP('2023-01-27 14:51:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543395
;

-- Reference: SAP accepted operations
-- Value: stopConversionRateSyncLocalFile
-- ValueName: import
-- 2023-01-27T12:51:52.319Z
UPDATE AD_Ref_List SET Value='stopConversionRateSyncLocalFile',Updated=TO_TIMESTAMP('2023-01-27 14:51:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543395
;

-- Reference: SAP accepted operations
-- Value: startConversionRateSyncLocalFile
-- ValueName: import
-- 2023-01-27T12:53:11.626Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543396,541661,TO_TIMESTAMP('2023-01-27 14:53:11','YYYY-MM-DD HH24:MI:SS'),100,'Startet die Synchronisation der Konversionsrates mit dem externen SAP-System. Die Konvertierungskursdateien werden vom lokalen Server-Dateisystem abgerufen.','de.metas.externalsystem','Y','Lokale Datei - Konversionsrate-Import - Start',TO_TIMESTAMP('2023-01-27 14:53:11','YYYY-MM-DD HH24:MI:SS'),100,'startConversionRateSyncLocalFile','import')
;

-- 2023-01-27T12:53:11.630Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543396 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: SAP accepted operations -> startConversionRateSyncLocalFile_import
-- 2023-01-27T12:53:45.397Z
UPDATE AD_Ref_List_Trl SET Description='Starts the conversion rate sychronization with SAP external system. The product files are fetched from the local server''s file system.', Name='Local File - Conversion Rate Import - Start',Updated=TO_TIMESTAMP('2023-01-27 14:53:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543396
;

-- Reference: SAP accepted operations
-- Value: startConversionRateSyncSFTP
-- ValueName: import
-- 2023-01-27T12:57:25.167Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543397,541661,TO_TIMESTAMP('2023-01-27 14:57:25','YYYY-MM-DD HH24:MI:SS'),100,'Startet die Synchronisation der Konversionsrates mit dem externen SAP-System. Die Konvertierungskursdateien werden von dem konfigurierten SFTP-Server geholt.','de.metas.externalsystem','Y','SFTP - Konversionsrate-Import - Start',TO_TIMESTAMP('2023-01-27 14:57:25','YYYY-MM-DD HH24:MI:SS'),100,'startConversionRateSyncSFTP','import')
;

-- 2023-01-27T12:57:25.169Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543397 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: SAP accepted operations -> startConversionRateSyncSFTP_import
-- 2023-01-27T12:59:24.766Z
UPDATE AD_Ref_List_Trl SET Description='Starts the conversion rates sychronization with SAP external system. The conversion rate files are fetched from the configured sftp server.', Name='SFTP - Conversion Rates-Import - Start',Updated=TO_TIMESTAMP('2023-01-27 14:59:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543397
;

-- Reference: SAP accepted operations
-- Value: stopConversionRateSyncSFTP
-- ValueName: import
-- 2023-01-27T13:03:33.671Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543398,541661,TO_TIMESTAMP('2023-01-27 15:03:33','YYYY-MM-DD HH24:MI:SS'),100,'Stoppt die Synchronisation des Konversionsrates mit dem externen SAP-System vom konfigurierten SFTP-Server.','de.metas.externalsystem','Y','SFTP - Konversionsrate-Import - Stop',TO_TIMESTAMP('2023-01-27 15:03:33','YYYY-MM-DD HH24:MI:SS'),100,'stopConversionRateSyncSFTP','import')
;

-- 2023-01-27T13:03:33.673Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543398 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: SAP accepted operations -> stopConversionRateSyncSFTP_import
-- 2023-01-27T13:04:04.122Z
UPDATE AD_Ref_List_Trl SET Description='Stops the conversion rate synchronization with SAP external system from the configured SFTP server.', Name='SFTP - Conversion Rates-Import - Stop',Updated=TO_TIMESTAMP('2023-01-27 15:04:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543398
;

-- Reference Item: SAP accepted operations -> startConversionRateSyncSFTP_import
-- 2023-01-27T13:14:14.008Z
UPDATE AD_Ref_List_Trl SET Name='SFTP - Conversion Rate Import - Start',Updated=TO_TIMESTAMP('2023-01-27 15:14:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543397
;

-- Reference Item: SAP accepted operations -> stopConversionRateSyncSFTP_import
-- 2023-01-27T13:14:20.627Z
UPDATE AD_Ref_List_Trl SET Name='SFTP - Conversion Rate Import - Stop',Updated=TO_TIMESTAMP('2023-01-27 15:14:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543398
;

-- 2023-01-30T10:45:09.687966200Z
INSERT INTO ExternalSystem_Service (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_Service_ID,IsActive,Name,Type,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2023-01-30 08:01:09','YYYY-MM-DD HH24:MI:SS'),100,540008,'Y','SFTP Sync-ConversionRate','SAP',TO_TIMESTAMP('2023-01-30 08:01:09','YYYY-MM-DD HH24:MI:SS'),100,'SFTPSyncConversionRate')
;

-- 2023-01-30T10:45:12.897805900Z
UPDATE ExternalSystem_Service SET Description='SFTP Sync-ConversionRate',Updated=TO_TIMESTAMP('2023-01-30 08:01:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540008
;

-- 2023-01-30T10:46:07.923288900Z
UPDATE ExternalSystem_Service SET EnableCommand='startConversionRateSyncSFTP',Updated=TO_TIMESTAMP('2023-01-30 08:02:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540008
;

-- 2023-01-30T10:46:17.838934100Z
UPDATE ExternalSystem_Service SET DisableCommand='stopConversionRateSyncSFTP',Updated=TO_TIMESTAMP('2023-01-30 08:02:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540008
;

-- 2023-01-30T10:45:09.687966200Z
INSERT INTO ExternalSystem_Service (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_Service_ID,IsActive,Name,Type,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2023-01-30 08:10:09','YYYY-MM-DD HH24:MI:SS'),100,540009,'Y','Local-File Sync-ConversionRate','SAP',TO_TIMESTAMP('2023-01-30 08:10:09','YYYY-MM-DD HH24:MI:SS'),100,'LocalFileSyncConversionRate')
;

-- 2023-01-30T10:45:12.897805900Z
UPDATE ExternalSystem_Service SET Description='Local-File Sync-ConversionRate',Updated=TO_TIMESTAMP('2023-01-30 08:10:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540009
;

-- 2023-01-30T10:46:07.923288900Z
UPDATE ExternalSystem_Service SET EnableCommand='startConversionRateSyncLocalFile',Updated=TO_TIMESTAMP('2023-01-30 08:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540009
;

/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- 2023-01-30T10:46:17.838934100Z
UPDATE ExternalSystem_Service SET DisableCommand='stopConversionRateSyncLocalFile',Updated=TO_TIMESTAMP('2023-01-30 08:10:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Service_ID=540009
;


