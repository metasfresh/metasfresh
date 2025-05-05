-- Column: LeichMehl_PluFile_Config.Description
-- Column: LeichMehl_PluFile_Config.Description
-- 2024-01-29T09:18:29.752Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587854,275,0,10,542182,'XX','Description',TO_TIMESTAMP('2024-01-29 11:18:29','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2024-01-29 11:18:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-29T09:18:29.754Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587854 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-29T09:18:29.786Z
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- 2024-01-29T09:18:31.154Z
/* DDL */ SELECT public.db_alter_table('LeichMehl_PluFile_Config','ALTER TABLE public.LeichMehl_PluFile_Config ADD COLUMN Description VARCHAR(255)')
;

-- Column: LeichMehl_PluFile_ConfigGroup.Description
-- Column: LeichMehl_PluFile_ConfigGroup.Description
-- 2024-01-29T09:18:55.844Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587855,275,0,10,542378,'XX','Description',TO_TIMESTAMP('2024-01-29 11:18:55','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2024-01-29 11:18:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-29T09:18:55.846Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587855 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-29T09:18:55.849Z
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- 2024-01-29T09:18:56.608Z
/* DDL */ SELECT public.db_alter_table('LeichMehl_PluFile_ConfigGroup','ALTER TABLE public.LeichMehl_PluFile_ConfigGroup ADD COLUMN Description VARCHAR(255)')
;

-- Table: LeichMehl_PluFile_ConfigGroup
-- Table: LeichMehl_PluFile_ConfigGroup
-- 2024-01-29T09:19:11.535Z
UPDATE AD_Table SET AD_Window_ID=541751,Updated=TO_TIMESTAMP('2024-01-29 11:19:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542378
;

-- Table: LeichMehl_PluFile_Config
-- Table: LeichMehl_PluFile_Config
-- 2024-01-29T09:19:27.224Z
UPDATE AD_Table SET AD_Window_ID=541751,Updated=TO_TIMESTAMP('2024-01-29 11:19:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542182
;

-- Field: PLU-Datei Konfiguration -> PLU-Datei Konfiguration -> Beschreibung
-- Column: LeichMehl_PluFile_ConfigGroup.Description
-- Field: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfiguration(547278,de.metas.externalsystem) -> Beschreibung
-- Column: LeichMehl_PluFile_ConfigGroup.Description
-- 2024-01-29T09:55:54.215Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587855,723848,0,547278,TO_TIMESTAMP('2024-01-29 11:55:54','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2024-01-29 11:55:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T09:55:54.218Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723848 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-29T09:55:54.221Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2024-01-29T09:55:54.434Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723848
;

-- 2024-01-29T09:55:54.436Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723848)
;

-- Field: PLU-Datei Konfiguration -> PLU-Datei Konfig -> Beschreibung
-- Column: LeichMehl_PluFile_Config.Description
-- Field: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> Beschreibung
-- Column: LeichMehl_PluFile_Config.Description
-- 2024-01-29T09:56:01.994Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587854,723849,0,547279,TO_TIMESTAMP('2024-01-29 11:56:01','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2024-01-29 11:56:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-29T09:56:02.011Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723849 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-29T09:56:02.024Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2024-01-29T09:56:02.193Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723849
;

-- 2024-01-29T09:56:02.199Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723849)
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfig.Beschreibung
-- Column: LeichMehl_PluFile_Config.Description
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> main -> 10 -> default.Beschreibung
-- Column: LeichMehl_PluFile_Config.Description
-- 2024-01-29T09:59:03.932Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723849,0,547279,551298,622141,'F',TO_TIMESTAMP('2024-01-29 11:59:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Beschreibung',80,0,0,TO_TIMESTAMP('2024-01-29 11:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfiguration.Beschreibung
-- Column: LeichMehl_PluFile_ConfigGroup.Description
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfiguration(547278,de.metas.externalsystem) -> main -> 10 -> default.Beschreibung
-- Column: LeichMehl_PluFile_ConfigGroup.Description
-- 2024-01-29T10:00:09.978Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723848,0,547278,551297,622142,'F',TO_TIMESTAMP('2024-01-29 12:00:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Beschreibung',20,0,0,TO_TIMESTAMP('2024-01-29 12:00:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfiguration.Beschreibung
-- Column: LeichMehl_PluFile_ConfigGroup.Description
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfiguration(547278,de.metas.externalsystem) -> main -> 10 -> default.Beschreibung
-- Column: LeichMehl_PluFile_ConfigGroup.Description
-- 2024-01-29T10:00:41.383Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-01-29 12:00:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=622142
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfiguration.Sektion
-- Column: LeichMehl_PluFile_ConfigGroup.AD_Org_ID
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfiguration(547278,de.metas.externalsystem) -> main -> 20 -> orgClient.Sektion
-- Column: LeichMehl_PluFile_ConfigGroup.AD_Org_ID
-- 2024-01-29T10:00:41.394Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-01-29 12:00:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621256
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfig.Beschreibung
-- Column: LeichMehl_PluFile_Config.Description
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfig(547279,de.metas.externalsystem) -> main -> 10 -> default.Beschreibung
-- Column: LeichMehl_PluFile_Config.Description
-- 2024-01-29T10:01:07.583Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-01-29 12:01:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=622141
;



-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfiguration.Aktiv
-- Column: LeichMehl_PluFile_ConfigGroup.IsActive
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfiguration(547278,de.metas.externalsystem) -> main -> 20 -> default.Aktiv
-- Column: LeichMehl_PluFile_ConfigGroup.IsActive
-- 2024-01-29T10:12:57.198Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2024-01-29 12:12:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621255
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfiguration.Sektion
-- Column: LeichMehl_PluFile_ConfigGroup.AD_Org_ID
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfiguration(547278,de.metas.externalsystem) -> main -> 20 -> orgClient.Sektion
-- Column: LeichMehl_PluFile_ConfigGroup.AD_Org_ID
-- 2024-01-29T10:16:12.853Z
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2024-01-29 12:16:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621256
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfiguration.Name
-- Column: LeichMehl_PluFile_ConfigGroup.Name
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfiguration(547278,de.metas.externalsystem) -> main -> 10 -> default.Name
-- Column: LeichMehl_PluFile_ConfigGroup.Name
-- 2024-01-29T10:17:00.914Z
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2024-01-29 12:17:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621254
;

-- UI Element: PLU-Datei Konfiguration -> PLU-Datei Konfiguration.Beschreibung
-- Column: LeichMehl_PluFile_ConfigGroup.Description
-- UI Element: PLU-Datei Konfiguration(541751,de.metas.externalsystem) -> PLU-Datei Konfiguration(547278,de.metas.externalsystem) -> main -> 10 -> default.Beschreibung
-- Column: LeichMehl_PluFile_ConfigGroup.Description
-- 2024-01-29T10:17:05.578Z
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2024-01-29 12:17:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=622142
;




-- Column: LeichMehl_PluFile_ConfigGroup.Name
-- Column: LeichMehl_PluFile_ConfigGroup.Name
-- 2024-01-29T15:24:15.127Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2024-01-29 17:24:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587641
;

-- 2024-01-29T15:24:15.834Z
INSERT INTO t_alter_column values('leichmehl_plufile_configgroup','Name','VARCHAR(255)',null,null)
;



