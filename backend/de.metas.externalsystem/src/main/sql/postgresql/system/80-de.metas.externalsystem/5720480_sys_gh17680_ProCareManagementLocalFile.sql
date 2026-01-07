-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ProcessedDirectory
-- 2024-03-29T14:51:17.769Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588083,581583,0,10,542401,'ProcessedDirectory',TO_TIMESTAMP('2024-03-29 16:51:17','YYYY-MM-DD HH24:MI:SS'),100,'N','Legt fest, wohin Dateien nach erfolgreicher Verarbeitung verschoben werden sollen.','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Bearbeitetes Verzeichnis',0,0,TO_TIMESTAMP('2024-03-29 16:51:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-29T14:51:17.774Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588083 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-29T14:51:17.829Z
/* DDL */  select update_Column_Translation_From_AD_Element(581583) 
;

-- 2024-03-29T14:52:21.712Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ProCareManagement_LocalFile','ALTER TABLE public.ExternalSystem_Config_ProCareManagement_LocalFile ADD COLUMN ProcessedDirectory VARCHAR(255) NOT NULL')
;

-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ErroredDirectory
-- 2024-03-29T14:53:00.823Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588084,581584,0,10,542401,'ErroredDirectory',TO_TIMESTAMP('2024-03-29 16:53:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Gibt an, wohin die Dateien nach einem fehlerhaften Verarbeitungsversuch verschoben werden sollen.','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Fehlerhaftes Verzeichnis',0,0,TO_TIMESTAMP('2024-03-29 16:53:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-29T14:53:00.826Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588084 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-29T14:53:00.848Z
/* DDL */  select update_Column_Translation_From_AD_Element(581584) 
;

-- 2024-03-29T14:53:02.731Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ProCareManagement_LocalFile','ALTER TABLE public.ExternalSystem_Config_ProCareManagement_LocalFile ADD COLUMN ErroredDirectory VARCHAR(255) NOT NULL')
;

-- Field: Externe System Konfiguration PCM -> Lokale Datei -> Bearbeitetes Verzeichnis
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ProcessedDirectory
-- 2024-03-29T14:53:24.781Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588083,727291,0,547488,TO_TIMESTAMP('2024-03-29 16:53:24','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wohin Dateien nach erfolgreicher Verarbeitung verschoben werden sollen.',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Bearbeitetes Verzeichnis',TO_TIMESTAMP('2024-03-29 16:53:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-29T14:53:24.807Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727291 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-29T14:53:24.879Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581583) 
;

-- 2024-03-29T14:53:24.961Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727291
;

-- 2024-03-29T14:53:24.970Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727291)
;

-- Field: Externe System Konfiguration PCM -> Lokale Datei -> Fehlerhaftes Verzeichnis
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ErroredDirectory
-- 2024-03-29T14:53:38.252Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588084,727292,0,547488,TO_TIMESTAMP('2024-03-29 16:53:38','YYYY-MM-DD HH24:MI:SS'),100,'Gibt an, wohin die Dateien nach einem fehlerhaften Verarbeitungsversuch verschoben werden sollen.',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Fehlerhaftes Verzeichnis',TO_TIMESTAMP('2024-03-29 16:53:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-29T14:53:38.255Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=727292 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-29T14:53:38.265Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581584) 
;

-- 2024-03-29T14:53:38.279Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727292
;

-- 2024-03-29T14:53:38.289Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727292)
;

-- UI Element: Externe System Konfiguration PCM -> Lokale Datei.Bearbeitetes Verzeichnis
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ProcessedDirectory
-- 2024-03-29T14:54:12.645Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,727291,0,547488,624010,551705,'F',TO_TIMESTAMP('2024-03-29 16:54:12','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wohin Dateien nach erfolgreicher Verarbeitung verschoben werden sollen.','Y','N','N','Y','N','N','N',0,'Bearbeitetes Verzeichnis',10,0,0,TO_TIMESTAMP('2024-03-29 16:54:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externe System Konfiguration PCM -> Lokale Datei.Fehlerhaftes Verzeichnis
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ErroredDirectory
-- 2024-03-29T14:54:24.406Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,727292,0,547488,624011,551705,'F',TO_TIMESTAMP('2024-03-29 16:54:24','YYYY-MM-DD HH24:MI:SS'),100,'Gibt an, wohin die Dateien nach einem fehlerhaften Verarbeitungsversuch verschoben werden sollen.','Y','N','N','Y','N','N','N',0,'Fehlerhaftes Verzeichnis',20,0,0,TO_TIMESTAMP('2024-03-29 16:54:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externe System Konfiguration PCM -> Lokale Datei.Bearbeitetes Verzeichnis
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ProcessedDirectory
-- 2024-03-29T14:54:49.137Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-03-29 16:54:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624010
;

-- UI Element: Externe System Konfiguration PCM -> Lokale Datei.Fehlerhaftes Verzeichnis
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ErroredDirectory
-- 2024-03-29T14:54:49.142Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-03-29 16:54:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=624011
;

INSERT INTO ExternalSystem_Config_ProCareManagement_LocalFile (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ErroredDirectory,ExternalSystem_Config_ProCareManagement_ID,ExternalSystem_Config_ProCareManagement_LocalFile_ID,Frequency,IsActive,LocalRootLocation,ProcessedDirectory,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2024-03-21 17:35:36','YYYY-MM-DD HH24:MI:SS'),100,'error',540000,540000,1000,'Y','Lokales-Stammverzeichnis','move',TO_TIMESTAMP('2024-03-21 17:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;
