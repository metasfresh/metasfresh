
-- Column: ExternalSystem_Config.ExternalSystem_ID
-- 2025-09-18T11:38:54.399Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590941,583968,0,30,541988,541576,'XX','ExternalSystem_ID',TO_TIMESTAMP('2025-09-18 11:38:54.102000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externes System',0,0,TO_TIMESTAMP('2025-09-18 11:38:54.102000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-18T11:38:54.412Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590941 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-18T11:38:54.415Z
/* DDL */  select update_Column_Translation_From_AD_Element(583968)
;

-- 2025-09-18T11:58:25.228Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config','ALTER TABLE public.ExternalSystem_Config ADD COLUMN ExternalSystem_ID NUMERIC(10)')
;

-- 2025-09-18T11:58:25.235Z
ALTER TABLE ExternalSystem_Config ADD CONSTRAINT ExternalSystem_ExternalSystemConfig FOREIGN KEY (ExternalSystem_ID) REFERENCES public.ExternalSystem DEFERRABLE INITIALLY DEFERRED
;

-- Field: Externes System Konfiguration(541024,de.metas.externalsystem) -> Externes System Konfiguration(543321,de.metas.externalsystem) -> Externes System
-- Column: ExternalSystem_Config.ExternalSystem_ID
-- 2025-09-18T11:48:41.383Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590941,753784,0,543321,TO_TIMESTAMP('2025-09-18 11:48:41.254000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Externes System',TO_TIMESTAMP('2025-09-18 11:48:41.254000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T11:48:41.384Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753784 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T11:48:41.386Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583968)
;

-- 2025-09-18T11:48:41.390Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753784
;

-- 2025-09-18T11:48:41.391Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753784)
;

-- UI Element: Externes System Konfiguration(541024,de.metas.externalsystem) -> Externes System Konfiguration(543321,de.metas.externalsystem) -> main -> 10 -> default.Externes System
-- Column: ExternalSystem_Config.ExternalSystem_ID
-- 2025-09-18T11:49:39.700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753784,0,543321,544785,637134,'F',TO_TIMESTAMP('2025-09-18 11:49:39.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Externes System',21,0,0,TO_TIMESTAMP('2025-09-18 11:49:39.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externes System Konfiguration(541024,de.metas.externalsystem) -> Externes System Konfiguration(543321,de.metas.externalsystem) -> main -> 10 -> default.Externes System
-- Column: ExternalSystem_Config.ExternalSystem_ID
-- 2025-09-18T11:50:01.627Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-09-18 11:50:01.627000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637134
;

-- UI Element: Externes System Konfiguration(541024,de.metas.externalsystem) -> Externes System Konfiguration(543321,de.metas.externalsystem) -> main -> 10 -> default.Art
-- Column: ExternalSystem_Config.Type
-- 2025-09-18T11:50:01.636Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-09-18 11:50:01.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=576649
;
