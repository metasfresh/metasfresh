-- Column: ExternalSystem_Service.ExternalSystem_ID
-- 2025-09-18T19:33:41.143Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590999,583968,0,30,541988,541940,'XX','ExternalSystem_ID',TO_TIMESTAMP('2025-09-18 19:33:41.016000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externes System',0,0,TO_TIMESTAMP('2025-09-18 19:33:41.016000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-18T19:33:41.145Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590999 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-18T19:33:41.147Z
/* DDL */  select update_Column_Translation_From_AD_Element(583968)
;

-- 2025-09-18T19:37:43.756Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Service','ALTER TABLE public.ExternalSystem_Service ADD COLUMN ExternalSystem_ID NUMERIC(10)')
;

-- 2025-09-18T19:37:43.765Z
ALTER TABLE ExternalSystem_Service ADD CONSTRAINT ExternalSystem_ExternalSystemService FOREIGN KEY (ExternalSystem_ID) REFERENCES public.ExternalSystem DEFERRABLE INITIALLY DEFERRED
;

-- Field: Externe System Service(541340,de.metas.externalsystem) -> ExternalSystem_Service(544882,de.metas.externalsystem) -> Externes System
-- Column: ExternalSystem_Service.ExternalSystem_ID
-- 2025-09-18T19:34:06.432Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590999,753868,0,544882,TO_TIMESTAMP('2025-09-18 19:34:06.205000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Externes System',TO_TIMESTAMP('2025-09-18 19:34:06.205000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T19:34:06.434Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753868 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T19:34:06.436Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583968)
;

-- 2025-09-18T19:34:06.439Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753868
;

-- 2025-09-18T19:34:06.440Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753868)
;

-- UI Element: Externe System Service(541340,de.metas.externalsystem) -> ExternalSystem_Service(544882,de.metas.externalsystem) -> main -> 10 -> default.Externes System
-- Column: ExternalSystem_Service.ExternalSystem_ID
-- 2025-09-18T19:35:33.027Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753868,0,544882,547249,637178,'F',TO_TIMESTAMP('2025-09-18 19:35:32.883000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Externes System',11,0,0,TO_TIMESTAMP('2025-09-18 19:35:32.883000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externe System Service(541340,de.metas.externalsystem) -> ExternalSystem_Service(544882,de.metas.externalsystem) -> main -> 10 -> default.Externes System
-- Column: ExternalSystem_Service.ExternalSystem_ID
-- 2025-09-18T19:35:43.870Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-09-18 19:35:43.870000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637178
;

-- UI Element: Externe System Service(541340,de.metas.externalsystem) -> ExternalSystem_Service(544882,de.metas.externalsystem) -> main -> 10 -> default.Art
-- Column: ExternalSystem_Service.Type
-- 2025-09-18T19:35:43.883Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-09-18 19:35:43.883000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=595388
;

-- UI Element: Externe System Service(541340,de.metas.externalsystem) -> ExternalSystem_Service(544882,de.metas.externalsystem) -> main -> 10 -> default.Suchschlüssel
-- Column: ExternalSystem_Service.Value
-- 2025-09-18T19:35:43.891Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-09-18 19:35:43.891000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=595390
;

-- UI Element: Externe System Service(541340,de.metas.externalsystem) -> ExternalSystem_Service(544882,de.metas.externalsystem) -> main -> 10 -> description.Beschreibung
-- Column: ExternalSystem_Service.Description
-- 2025-09-18T19:35:43.897Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-09-18 19:35:43.896000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=595391
;

-- UI Element: Externe System Service(541340,de.metas.externalsystem) -> ExternalSystem_Service(544882,de.metas.externalsystem) -> main -> 20 -> org.Sektion
-- Column: ExternalSystem_Service.AD_Org_ID
-- 2025-09-18T19:35:43.902Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-09-18 19:35:43.902000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=595393
;
