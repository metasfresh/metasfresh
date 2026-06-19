-- Run mode: SWING_CLIENT

-- Column: M_Picking_Job.M_TU_HU_ID
-- 2025-09-04T08:39:12.196Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590807,542462,0,30,540499,541906,'XX','M_TU_HU_ID',TO_TIMESTAMP('2025-09-04 08:39:11.569000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Trading Unit','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'TU',0,0,TO_TIMESTAMP('2025-09-04 08:39:11.569000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-04T08:39:12.199Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590807 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-04T08:39:12.371Z
/* DDL */  select update_Column_Translation_From_AD_Element(542462)
;

-- 2025-09-04T08:39:13.643Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job','ALTER TABLE public.M_Picking_Job ADD COLUMN M_TU_HU_ID NUMERIC(10)')
;

-- 2025-09-04T08:39:13.666Z
ALTER TABLE M_Picking_Job ADD CONSTRAINT MTUHU_MPickingJob FOREIGN KEY (M_TU_HU_ID) REFERENCES public.M_HU DEFERRABLE INITIALLY DEFERRED
;

-- Field: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> TU
-- Column: M_Picking_Job.M_TU_HU_ID
-- 2025-09-04T08:39:43.730Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590807,753474,0,544861,TO_TIMESTAMP('2025-09-04 08:39:43.466000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Trading Unit',10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','TU',TO_TIMESTAMP('2025-09-04 08:39:43.466000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-04T08:39:43.734Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753474 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-04T08:39:43.739Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542462)
;

-- 2025-09-04T08:39:43.764Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753474
;

-- 2025-09-04T08:39:43.770Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753474)
;

-- UI Column: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> main -> 10
-- UI Element Group: Picking to LU/TU
-- 2025-09-04T08:44:51.566Z
UPDATE AD_UI_ElementGroup SET Name='Picking to LU/TU',Updated=TO_TIMESTAMP('2025-09-04 08:44:51.566000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551853
;

-- UI Element: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> main -> 10 -> Picking to LU/TU.TU
-- Column: M_Picking_Job.M_TU_HU_ID
-- 2025-09-04T08:45:11.117Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753474,0,544861,551853,636922,'F',TO_TIMESTAMP('2025-09-04 08:45:10.892000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Trading Unit','Y','N','Y','N','N','TU',30,0,0,TO_TIMESTAMP('2025-09-04 08:45:10.892000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> main -> 10 -> Picking to LU/TU.Packvorschrift (TU)
-- Column: M_Picking_Job.M_TU_HU_PI_ID
-- 2025-09-04T08:45:20.784Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,740352,0,544861,551853,636923,'F',TO_TIMESTAMP('2025-09-04 08:45:20.601000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Packvorschrift (TU)',40,0,0,TO_TIMESTAMP('2025-09-04 08:45:20.601000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> main -> 10 -> Picking to LU/TU.TU
-- Column: M_Picking_Job.M_TU_HU_ID
-- 2025-09-04T08:45:40.487Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2025-09-04 08:45:40.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636922
;

-- UI Element: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> main -> 10 -> Picking to LU/TU.Packvorschrift (TU)
-- Column: M_Picking_Job.M_TU_HU_PI_ID
-- 2025-09-04T08:45:45.796Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2025-09-04 08:45:45.795000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636923
;

-- Field: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> TU
-- Column: M_Picking_Job.M_TU_HU_ID
-- 2025-09-04T08:46:16.759Z
UPDATE AD_Field SET DisplayLogic='@M_TU_HU_ID/0@>0',Updated=TO_TIMESTAMP('2025-09-04 08:46:16.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=753474
;

-- Field: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> Packvorschrift (TU)
-- Column: M_Picking_Job.M_TU_HU_PI_ID
-- 2025-09-04T08:46:52.745Z
UPDATE AD_Field SET DisplayLogic='@M_TU_HU_PI_ID/0@>0',Updated=TO_TIMESTAMP('2025-09-04 08:46:52.745000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=740352
;

-- Field: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> Packvorschrift (TU)
-- Column: M_Picking_Job.M_TU_HU_PI_ID
-- 2025-09-04T08:47:23.325Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-09-04 08:47:23.324000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=740352
;

-- Field: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> TU
-- Column: M_Picking_Job.M_TU_HU_ID
-- 2025-09-04T08:47:38.513Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-09-04 08:47:38.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=753474
;

