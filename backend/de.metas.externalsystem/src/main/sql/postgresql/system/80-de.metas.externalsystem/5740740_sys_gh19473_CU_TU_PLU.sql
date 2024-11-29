-- 2024-11-29T09:54:42.196Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583381,0,'CU_TU_PLU',TO_TIMESTAMP('2024-11-29 11:54:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','CU/TU PLU','CU/TU PLU',TO_TIMESTAMP('2024-11-29 11:54:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-11-29T09:54:42.206Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583381 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: CU_TU_PLU
-- 2024-11-29T09:56:16.148Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541906,TO_TIMESTAMP('2024-11-29 11:56:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','CU_TU_PLU',TO_TIMESTAMP('2024-11-29 11:56:15','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2024-11-29T09:56:16.152Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541906 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: CU_TU_PLU
-- Value: CU
-- ValueName: CU
-- 2024-11-29T09:56:54.729Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543775,541906,TO_TIMESTAMP('2024-11-29 11:56:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','CU',TO_TIMESTAMP('2024-11-29 11:56:54','YYYY-MM-DD HH24:MI:SS'),100,'CU','CU')
;

-- 2024-11-29T09:56:54.732Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543775 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: CU_TU_PLU
-- Value: TU
-- ValueName: TU
-- 2024-11-29T09:57:11.012Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543776,541906,TO_TIMESTAMP('2024-11-29 11:57:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','TU',TO_TIMESTAMP('2024-11-29 11:57:10','YYYY-MM-DD HH24:MI:SS'),100,'TU','TU')
;

-- 2024-11-29T09:57:11.015Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543776 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.CU_TU_PLU
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.CU_TU_PLU
-- 2024-11-29T09:58:41.769Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589467,583381,0,17,541906,542172,'XX','CU_TU_PLU',TO_TIMESTAMP('2024-11-29 11:58:41','YYYY-MM-DD HH24:MI:SS'),100,'N','CU','de.metas.externalsystem',0,2,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'CU/TU PLU',0,0,TO_TIMESTAMP('2024-11-29 11:58:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-29T09:58:41.771Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589467 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-29T09:58:41.807Z
/* DDL */  select update_Column_Translation_From_AD_Element(583381) 
;

-- 2024-11-29T09:58:43.445Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_LeichMehl_ProductMapping','ALTER TABLE public.ExternalSystem_Config_LeichMehl_ProductMapping ADD COLUMN CU_TU_PLU VARCHAR(2) DEFAULT ''CU'' NOT NULL')
;

-- Column: ExternalSystem_Config_LeichMehl.CU_TU_PLU
-- Column: ExternalSystem_Config_LeichMehl.CU_TU_PLU
-- 2024-11-29T10:04:00.302Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589468,583381,0,17,541906,542129,'XX','CU_TU_PLU',TO_TIMESTAMP('2024-11-29 12:04:00','YYYY-MM-DD HH24:MI:SS'),100,'N','CU','de.metas.externalsystem',0,2,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'CU/TU PLU',0,0,TO_TIMESTAMP('2024-11-29 12:04:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-29T10:04:00.310Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589468 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-29T10:04:00.314Z
/* DDL */  select update_Column_Translation_From_AD_Element(583381) 
;

-- 2024-11-29T10:04:01.885Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_LeichMehl','ALTER TABLE public.ExternalSystem_Config_LeichMehl ADD COLUMN CU_TU_PLU VARCHAR(2) DEFAULT ''CU'' NOT NULL')
;

-- Field: Externe System Konfiguration Leich + Mehl -> Leich + Mehl -> CU/TU PLU
-- Column: ExternalSystem_Config_LeichMehl.CU_TU_PLU
-- Field: Externe System Konfiguration Leich + Mehl(541540,de.metas.externalsystem) -> Leich + Mehl(546388,de.metas.externalsystem) -> CU/TU PLU
-- Column: ExternalSystem_Config_LeichMehl.CU_TU_PLU
-- 2024-11-29T10:04:16.142Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589468,734054,0,546388,TO_TIMESTAMP('2024-11-29 12:04:15','YYYY-MM-DD HH24:MI:SS'),100,2,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','CU/TU PLU',TO_TIMESTAMP('2024-11-29 12:04:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-11-29T10:04:16.147Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=734054 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-29T10:04:16.151Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583381) 
;

-- 2024-11-29T10:04:16.178Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734054
;

-- 2024-11-29T10:04:16.185Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734054)
;

-- UI Element: Externe System Konfiguration Leich + Mehl -> Leich + Mehl.CU/TU PLU
-- Column: ExternalSystem_Config_LeichMehl.CU_TU_PLU
-- UI Element: Externe System Konfiguration Leich + Mehl(541540,de.metas.externalsystem) -> Leich + Mehl(546388,de.metas.externalsystem) -> main -> 10 -> directory.CU/TU PLU
-- Column: ExternalSystem_Config_LeichMehl.CU_TU_PLU
-- 2024-11-29T10:04:57.596Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,734054,0,546388,627372,549387,'F',TO_TIMESTAMP('2024-11-29 12:04:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'CU/TU PLU',20,0,0,TO_TIMESTAMP('2024-11-29 12:04:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Externes System -> Leich + Mehl -> CU/TU PLU
-- Column: ExternalSystem_Config_LeichMehl.CU_TU_PLU
-- Field: Externes System(541024,de.metas.externalsystem) -> Leich + Mehl(546100,de.metas.externalsystem) -> CU/TU PLU
-- Column: ExternalSystem_Config_LeichMehl.CU_TU_PLU
-- 2024-11-29T10:07:31.267Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589468,734055,0,546100,TO_TIMESTAMP('2024-11-29 12:07:31','YYYY-MM-DD HH24:MI:SS'),100,2,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','CU/TU PLU',TO_TIMESTAMP('2024-11-29 12:07:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-11-29T10:07:31.269Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=734055 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-29T10:07:31.271Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583381) 
;

-- 2024-11-29T10:07:31.278Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734055
;

-- 2024-11-29T10:07:31.279Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734055)
;

-- UI Element: Externes System -> Leich + Mehl.CU/TU PLU
-- Column: ExternalSystem_Config_LeichMehl.CU_TU_PLU
-- UI Element: Externes System(541024,de.metas.externalsystem) -> Leich + Mehl(546100,de.metas.externalsystem) -> main -> 10 -> directory.CU/TU PLU
-- Column: ExternalSystem_Config_LeichMehl.CU_TU_PLU
-- 2024-11-29T10:07:44.025Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,734055,0,546100,627373,549385,'F',TO_TIMESTAMP('2024-11-29 12:07:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'CU/TU PLU',20,0,0,TO_TIMESTAMP('2024-11-29 12:07:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System -> Leich + Mehl.CU/TU PLU
-- Column: ExternalSystem_Config_LeichMehl.CU_TU_PLU
-- UI Element: Externes System(541024,de.metas.externalsystem) -> Leich + Mehl(546100,de.metas.externalsystem) -> main -> 10 -> directory.CU/TU PLU
-- Column: ExternalSystem_Config_LeichMehl.CU_TU_PLU
-- 2024-11-29T10:08:20.929Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-11-29 12:08:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=627373
;

-- UI Element: Externes System -> Leich + Mehl.PLU-Datei Exportprüfung aktivieren
-- Column: ExternalSystem_Config_LeichMehl.IsPluFileExportAuditEnabled
-- UI Element: Externes System(541024,de.metas.externalsystem) -> Leich + Mehl(546100,de.metas.externalsystem) -> main -> 20 -> file export audit.PLU-Datei Exportprüfung aktivieren
-- Column: ExternalSystem_Config_LeichMehl.IsPluFileExportAuditEnabled
-- 2024-11-29T10:08:20.935Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-11-29 12:08:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610514
;

-- Field: Produkt -> PLU Konfiguration -> CU/TU PLU
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.CU_TU_PLU
-- Field: Produkt(140,D) -> PLU Konfiguration(547280,de.metas.externalsystem) -> CU/TU PLU
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.CU_TU_PLU
-- 2024-11-29T10:10:17.645Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589467,734056,0,547280,TO_TIMESTAMP('2024-11-29 12:10:17','YYYY-MM-DD HH24:MI:SS'),100,2,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','CU/TU PLU',TO_TIMESTAMP('2024-11-29 12:10:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-11-29T10:10:17.648Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=734056 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-29T10:10:17.651Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583381) 
;

-- 2024-11-29T10:10:17.663Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734056
;

-- 2024-11-29T10:10:17.671Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734056)
;

-- UI Element: Produkt -> PLU Konfiguration.CU/TU PLU
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.CU_TU_PLU
-- UI Element: Produkt(140,D) -> PLU Konfiguration(547280,de.metas.externalsystem) -> main -> 10 -> default.CU/TU PLU
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.CU_TU_PLU
-- 2024-11-29T10:10:40.141Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,734056,0,547280,627374,551301,'F',TO_TIMESTAMP('2024-11-29 12:10:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'CU/TU PLU',35,0,0,TO_TIMESTAMP('2024-11-29 12:10:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produkt -> PLU Konfiguration.CU/TU PLU
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.CU_TU_PLU
-- UI Element: Produkt(140,D) -> PLU Konfiguration(547280,de.metas.externalsystem) -> main -> 10 -> default.CU/TU PLU
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.CU_TU_PLU
-- 2024-11-29T10:10:47.380Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-11-29 12:10:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=627374
;

-- UI Element: Produkt -> PLU Konfiguration.Aktiv
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.IsActive
-- UI Element: Produkt(140,D) -> PLU Konfiguration(547280,de.metas.externalsystem) -> main -> 10 -> default.Aktiv
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.IsActive
-- 2024-11-29T10:10:47.390Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-11-29 12:10:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621268
;
