-- Run mode: SWING_CLIENT

-- Column: M_Picking_Job.PickFrom_HU_ID
-- 2025-02-27T19:51:17.102Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589747,544444,0,30,540499,541906,'XX','PickFrom_HU_ID',TO_TIMESTAMP('2025-02-27 19:51:16.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Pick From HU',0,0,TO_TIMESTAMP('2025-02-27 19:51:16.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-27T19:51:17.109Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589747 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-27T19:51:17.143Z
/* DDL */  select update_Column_Translation_From_AD_Element(544444)
;

-- 2025-02-27T19:51:18.154Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job','ALTER TABLE public.M_Picking_Job ADD COLUMN PickFrom_HU_ID NUMERIC(10)')
;

-- 2025-02-27T19:51:18.171Z
ALTER TABLE M_Picking_Job ADD CONSTRAINT PickFromHU_MPickingJob FOREIGN KEY (PickFrom_HU_ID) REFERENCES public.M_HU DEFERRABLE INITIALLY DEFERRED
;

-- Field: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> Pick From HU
-- Column: M_Picking_Job.PickFrom_HU_ID
-- 2025-02-27T19:51:46.954Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589747,740354,0,544861,TO_TIMESTAMP('2025-02-27 19:51:46.763000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Pick From HU',TO_TIMESTAMP('2025-02-27 19:51:46.763000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-27T19:51:46.958Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=740354 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-27T19:51:46.963Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544444)
;

-- 2025-02-27T19:51:46.986Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740354
;

-- 2025-02-27T19:51:46.989Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(740354)
;

-- UI Column: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> main -> 10
-- UI Element Group: Picking to LU
-- 2025-02-27T19:52:30.573Z
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2025-02-27 19:52:30.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551853
;

-- UI Column: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> main -> 10
-- UI Element Group: Pick from
-- 2025-02-27T19:52:39.967Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547151,552639,TO_TIMESTAMP('2025-02-27 19:52:39.800000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Pick from',30,TO_TIMESTAMP('2025-02-27 19:52:39.800000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> main -> 10 -> Pick from.Pick From HU
-- Column: M_Picking_Job.PickFrom_HU_ID
-- 2025-02-27T19:52:51.440Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,740354,0,544861,552639,630660,'F',TO_TIMESTAMP('2025-02-27 19:52:51.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Pick From HU',10,0,0,TO_TIMESTAMP('2025-02-27 19:52:51.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> Pick From HU
-- Column: M_Picking_Job.PickFrom_HU_ID
-- 2025-02-27T19:53:10.618Z
UPDATE AD_Field SET DisplayLogic='@PickFrom_HU_ID/0@>0',Updated=TO_TIMESTAMP('2025-02-27 19:53:10.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=740354
;

