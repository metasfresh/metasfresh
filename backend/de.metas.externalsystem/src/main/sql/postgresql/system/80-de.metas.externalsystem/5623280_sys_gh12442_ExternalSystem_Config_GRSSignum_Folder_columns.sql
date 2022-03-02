-- 2022-01-27T12:23:10.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580524,0,'BasePathForExportDirectories',TO_TIMESTAMP('2022-01-27 14:23:10','YYYY-MM-DD HH24:MI:SS'),100,'Specifies the base path where all business partner folders are created.','de.metas.externalsystem','Y','Base path','Base path',TO_TIMESTAMP('2022-01-27 14:23:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-27T12:23:10.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580524 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-01-27T12:23:34.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Gibt den Verzeichnispfad an, in dem alle Geschäftspartner-Ordner erstellt werden.', Name='Basis-Pfad', PrintName='Basis-Pfad',Updated=TO_TIMESTAMP('2022-01-27 14:23:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580524 AND AD_Language='de_CH'
;

-- 2022-01-27T12:23:34.235Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580524,'de_CH') 
;

-- 2022-01-27T12:23:49.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Gibt den Verzeichnispfad an, in dem alle Geschäftspartner-Ordner erstellt werden.', Name='Basis-Pfad', PrintName='Basis-Pfad',Updated=TO_TIMESTAMP('2022-01-27 14:23:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580524 AND AD_Language='de_DE'
;

-- 2022-01-27T12:23:49.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580524,'de_DE') 
;

-- 2022-01-27T12:23:49.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580524,'de_DE') 
;

-- 2022-01-27T12:23:49.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='BasePathForExportDirectories', Name='Basis-Pfad', Description='Gibt den Verzeichnispfad an, in dem alle Geschäftspartner-Ordner erstellt werden.', Help=NULL WHERE AD_Element_ID=580524
;

-- 2022-01-27T12:23:49.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BasePathForExportDirectories', Name='Basis-Pfad', Description='Gibt den Verzeichnispfad an, in dem alle Geschäftspartner-Ordner erstellt werden.', Help=NULL, AD_Element_ID=580524 WHERE UPPER(ColumnName)='BASEPATHFOREXPORTDIRECTORIES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-01-27T12:23:49.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BasePathForExportDirectories', Name='Basis-Pfad', Description='Gibt den Verzeichnispfad an, in dem alle Geschäftspartner-Ordner erstellt werden.', Help=NULL WHERE AD_Element_ID=580524 AND IsCentrallyMaintained='Y'
;

-- 2022-01-27T12:23:49.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Basis-Pfad', Description='Gibt den Verzeichnispfad an, in dem alle Geschäftspartner-Ordner erstellt werden.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580524) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580524)
;

-- 2022-01-27T12:23:49.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Basis-Pfad', Name='Basis-Pfad' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580524)
;

-- 2022-01-27T12:23:49.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Basis-Pfad', Description='Gibt den Verzeichnispfad an, in dem alle Geschäftspartner-Ordner erstellt werden.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580524
;

-- 2022-01-27T12:23:49.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Basis-Pfad', Description='Gibt den Verzeichnispfad an, in dem alle Geschäftspartner-Ordner erstellt werden.', Help=NULL WHERE AD_Element_ID = 580524
;

-- 2022-01-27T12:23:49.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Basis-Pfad', Description = 'Gibt den Verzeichnispfad an, in dem alle Geschäftspartner-Ordner erstellt werden.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580524
;

-- 2022-01-27T12:24:06.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Gibt den Verzeichnispfad an, in dem alle Geschäftspartner-Ordner erstellt werden.', Name='Basis-Pfad', PrintName='Basis-Pfad',Updated=TO_TIMESTAMP('2022-01-27 14:24:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580524 AND AD_Language='nl_NL'
;

-- 2022-01-27T12:24:06.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580524,'nl_NL') 
;

-- 2022-01-27T12:24:23.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579145,580524,0,10,541882,'BasePathForExportDirectories',TO_TIMESTAMP('2022-01-27 14:24:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Gibt den Verzeichnispfad an, in dem alle Geschäftspartner-Ordner erstellt werden.','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Basis-Pfad',0,0,TO_TIMESTAMP('2022-01-27 14:24:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-01-27T12:24:23.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579145 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-01-27T12:24:23.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580524) 
;

-- 2022-01-27T12:24:24.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_GRSSignum','ALTER TABLE public.ExternalSystem_Config_GRSSignum ADD COLUMN BasePathForExportDirectories VARCHAR(255)')
;

-- 2022-01-27T12:25:56.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580525,0,'BPartnerExportDirectories',TO_TIMESTAMP('2022-01-27 14:25:56','YYYY-MM-DD HH24:MI:SS'),100,'Comma-separated folder structures to be created within the business partner folder. Example: "Zertifikate, Korrespondenz/Allgemein, Korrespondenz/Entwicklungen".','de.metas.externalsystem','Y','Business partner subfolder','Business partner subfolder',TO_TIMESTAMP('2022-01-27 14:25:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-27T12:25:56.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580525 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-01-27T12:26:17.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Komma-getrennte Ordner-Strukturen, die innerhalb des Geschäftspartner-Ordners angelegt werden sollen. Beispiel: "Zertifikate, Korrespondenz/Allgemein, Korrespondenz/Entwicklungen".', Name='Geschäftspartner-Unterordner', PrintName='Geschäftspartner-Unterordner',Updated=TO_TIMESTAMP('2022-01-27 14:26:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580525 AND AD_Language='de_CH'
;

-- 2022-01-27T12:26:17.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580525,'de_CH') 
;

-- 2022-01-27T12:26:28.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Komma-getrennte Ordner-Strukturen, die innerhalb des Geschäftspartner-Ordners angelegt werden sollen. Beispiel: "Zertifikate, Korrespondenz/Allgemein, Korrespondenz/Entwicklungen".', Name='Geschäftspartner-Unterordner', PrintName='Geschäftspartner-Unterordner',Updated=TO_TIMESTAMP('2022-01-27 14:26:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580525 AND AD_Language='de_DE'
;

-- 2022-01-27T12:26:28.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580525,'de_DE') 
;

-- 2022-01-27T12:26:28.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580525,'de_DE') 
;

-- 2022-01-27T12:26:28.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='BPartnerExportDirectories', Name='Geschäftspartner-Unterordner', Description='Komma-getrennte Ordner-Strukturen, die innerhalb des Geschäftspartner-Ordners angelegt werden sollen. Beispiel: "Zertifikate, Korrespondenz/Allgemein, Korrespondenz/Entwicklungen".', Help=NULL WHERE AD_Element_ID=580525
;

-- 2022-01-27T12:26:28.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BPartnerExportDirectories', Name='Geschäftspartner-Unterordner', Description='Komma-getrennte Ordner-Strukturen, die innerhalb des Geschäftspartner-Ordners angelegt werden sollen. Beispiel: "Zertifikate, Korrespondenz/Allgemein, Korrespondenz/Entwicklungen".', Help=NULL, AD_Element_ID=580525 WHERE UPPER(ColumnName)='BPARTNEREXPORTDIRECTORIES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-01-27T12:26:28.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='BPartnerExportDirectories', Name='Geschäftspartner-Unterordner', Description='Komma-getrennte Ordner-Strukturen, die innerhalb des Geschäftspartner-Ordners angelegt werden sollen. Beispiel: "Zertifikate, Korrespondenz/Allgemein, Korrespondenz/Entwicklungen".', Help=NULL WHERE AD_Element_ID=580525 AND IsCentrallyMaintained='Y'
;

-- 2022-01-27T12:26:28.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Geschäftspartner-Unterordner', Description='Komma-getrennte Ordner-Strukturen, die innerhalb des Geschäftspartner-Ordners angelegt werden sollen. Beispiel: "Zertifikate, Korrespondenz/Allgemein, Korrespondenz/Entwicklungen".', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580525) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580525)
;

-- 2022-01-27T12:26:28.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Geschäftspartner-Unterordner', Name='Geschäftspartner-Unterordner' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580525)
;

-- 2022-01-27T12:26:28.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Geschäftspartner-Unterordner', Description='Komma-getrennte Ordner-Strukturen, die innerhalb des Geschäftspartner-Ordners angelegt werden sollen. Beispiel: "Zertifikate, Korrespondenz/Allgemein, Korrespondenz/Entwicklungen".', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580525
;

-- 2022-01-27T12:26:28.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Geschäftspartner-Unterordner', Description='Komma-getrennte Ordner-Strukturen, die innerhalb des Geschäftspartner-Ordners angelegt werden sollen. Beispiel: "Zertifikate, Korrespondenz/Allgemein, Korrespondenz/Entwicklungen".', Help=NULL WHERE AD_Element_ID = 580525
;

-- 2022-01-27T12:26:28.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Geschäftspartner-Unterordner', Description = 'Komma-getrennte Ordner-Strukturen, die innerhalb des Geschäftspartner-Ordners angelegt werden sollen. Beispiel: "Zertifikate, Korrespondenz/Allgemein, Korrespondenz/Entwicklungen".', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580525
;

-- 2022-01-27T12:26:43.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Komma-getrennte Ordner-Strukturen, die innerhalb des Geschäftspartner-Ordners angelegt werden sollen. Beispiel: "Zertifikate, Korrespondenz/Allgemein, Korrespondenz/Entwicklungen".', Name='Geschäftspartner-Unterordner', PrintName='Geschäftspartner-Unterordner',Updated=TO_TIMESTAMP('2022-01-27 14:26:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580525 AND AD_Language='nl_NL'
;

-- 2022-01-27T12:26:43.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580525,'nl_NL') 
;

-- 2022-01-27T12:27:22.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579146,580525,0,10,541882,'BPartnerExportDirectories',TO_TIMESTAMP('2022-01-27 14:27:22','YYYY-MM-DD HH24:MI:SS'),100,'N','Komma-getrennte Ordner-Strukturen, die innerhalb des Geschäftspartner-Ordners angelegt werden sollen. Beispiel: "Zertifikate, Korrespondenz/Allgemein, Korrespondenz/Entwicklungen".','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Geschäftspartner-Unterordner',0,0,TO_TIMESTAMP('2022-01-27 14:27:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-01-27T12:27:22.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579146 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-01-27T12:27:22.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580525) 
;

-- 2022-01-27T12:27:23.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_GRSSignum','ALTER TABLE public.ExternalSystem_Config_GRSSignum ADD COLUMN BPartnerExportDirectories VARCHAR(255)')
;

-- 2022-01-27T12:29:25.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@BPartnerExportDirectories@!null',Updated=TO_TIMESTAMP('2022-01-27 14:29:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579145
;

-- 2022-01-27T12:30:21.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580526,0,'IsCreateBPartnerFolders',TO_TIMESTAMP('2022-01-27 14:30:21','YYYY-MM-DD HH24:MI:SS'),100,'If ticked, then when exporting a business partner, a folder with subfolders for file attachments for this business partner is created in the file system.','de.metas.externalsystem','Y','Create partner folders','Create partner folders',TO_TIMESTAMP('2022-01-27 14:30:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-27T12:30:21.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580526 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-01-27T12:30:41.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann wird beim Export eines Geschäftspartners im Dateisystem ein Ordner mit Unterordnern für Dateianlagen zu diesem Geschäftspartner angelegt.', Name='Partner-Ordner erst', PrintName='Partner-Ordner erst',Updated=TO_TIMESTAMP('2022-01-27 14:30:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580526 AND AD_Language='nl_NL'
;

-- 2022-01-27T12:30:41.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580526,'nl_NL') 
;

-- 2022-01-27T12:30:54.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann wird beim Export eines Geschäftspartners im Dateisystem ein Ordner mit Unterordnern für Dateianlagen zu diesem Geschäftspartner angelegt.', Name='Partner-Ordner erst', PrintName='Partner-Ordner erst',Updated=TO_TIMESTAMP('2022-01-27 14:30:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580526 AND AD_Language='de_CH'
;

-- 2022-01-27T12:30:54.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580526,'de_CH') 
;

-- 2022-01-27T12:31:06.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann wird beim Export eines Geschäftspartners im Dateisystem ein Ordner mit Unterordnern für Dateianlagen zu diesem Geschäftspartner angelegt.', Name='Partner-Ordner erst', PrintName='Partner-Ordner erst',Updated=TO_TIMESTAMP('2022-01-27 14:31:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580526 AND AD_Language='de_DE'
;

-- 2022-01-27T12:31:06.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580526,'de_DE') 
;

-- 2022-01-27T12:31:06.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580526,'de_DE') 
;

-- 2022-01-27T12:31:06.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsCreateBPartnerFolders', Name='Partner-Ordner erst', Description='Wenn angehakt, dann wird beim Export eines Geschäftspartners im Dateisystem ein Ordner mit Unterordnern für Dateianlagen zu diesem Geschäftspartner angelegt.', Help=NULL WHERE AD_Element_ID=580526
;

-- 2022-01-27T12:31:06.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsCreateBPartnerFolders', Name='Partner-Ordner erst', Description='Wenn angehakt, dann wird beim Export eines Geschäftspartners im Dateisystem ein Ordner mit Unterordnern für Dateianlagen zu diesem Geschäftspartner angelegt.', Help=NULL, AD_Element_ID=580526 WHERE UPPER(ColumnName)='ISCREATEBPARTNERFOLDERS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-01-27T12:31:06.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsCreateBPartnerFolders', Name='Partner-Ordner erst', Description='Wenn angehakt, dann wird beim Export eines Geschäftspartners im Dateisystem ein Ordner mit Unterordnern für Dateianlagen zu diesem Geschäftspartner angelegt.', Help=NULL WHERE AD_Element_ID=580526 AND IsCentrallyMaintained='Y'
;

-- 2022-01-27T12:31:06.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Partner-Ordner erst', Description='Wenn angehakt, dann wird beim Export eines Geschäftspartners im Dateisystem ein Ordner mit Unterordnern für Dateianlagen zu diesem Geschäftspartner angelegt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580526) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580526)
;

-- 2022-01-27T12:31:06.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Partner-Ordner erst', Name='Partner-Ordner erst' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580526)
;

-- 2022-01-27T12:31:06.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Partner-Ordner erst', Description='Wenn angehakt, dann wird beim Export eines Geschäftspartners im Dateisystem ein Ordner mit Unterordnern für Dateianlagen zu diesem Geschäftspartner angelegt.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580526
;

-- 2022-01-27T12:31:06.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Partner-Ordner erst', Description='Wenn angehakt, dann wird beim Export eines Geschäftspartners im Dateisystem ein Ordner mit Unterordnern für Dateianlagen zu diesem Geschäftspartner angelegt.', Help=NULL WHERE AD_Element_ID = 580526
;

-- 2022-01-27T12:31:06.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Partner-Ordner erst', Description = 'Wenn angehakt, dann wird beim Export eines Geschäftspartners im Dateisystem ein Ordner mit Unterordnern für Dateianlagen zu diesem Geschäftspartner angelegt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580526
;

-- 2022-01-27T12:31:57.561Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579148,580526,0,20,541882,'IsCreateBPartnerFolders',TO_TIMESTAMP('2022-01-27 14:31:57','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Wenn angehakt, dann wird beim Export eines Geschäftspartners im Dateisystem ein Ordner mit Unterordnern für Dateianlagen zu diesem Geschäftspartner angelegt.','de.metas.externalsystem',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Partner-Ordner erst',0,0,TO_TIMESTAMP('2022-01-27 14:31:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-01-27T12:31:57.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579148 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-01-27T12:31:57.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580526) 
;

-- 2022-01-27T12:31:58.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_GRSSignum','ALTER TABLE public.ExternalSystem_Config_GRSSignum ADD COLUMN IsCreateBPartnerFolders CHAR(1) DEFAULT ''N'' CHECK (IsCreateBPartnerFolders IN (''Y'',''N''))')
;

