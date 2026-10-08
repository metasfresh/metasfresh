-- Run mode: SWING_CLIENT

-- Column: M_Picking_Job_Line.M_PickingSlot_ID
-- 2025-02-28T08:45:41.762Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589749,542276,0,30,541907,'XX','M_PickingSlot_ID',TO_TIMESTAMP('2025-02-28 08:45:41.507000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Picking Slot',0,0,TO_TIMESTAMP('2025-02-28 08:45:41.507000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-28T08:45:41.768Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589749 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-28T08:45:41.775Z
/* DDL */  select update_Column_Translation_From_AD_Element(542276)
;

-- 2025-02-28T08:45:42.673Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Line','ALTER TABLE public.M_Picking_Job_Line ADD COLUMN M_PickingSlot_ID NUMERIC(10)')
;

-- 2025-02-28T08:45:42.694Z
ALTER TABLE M_Picking_Job_Line ADD CONSTRAINT MPickingSlot_MPickingJobLine FOREIGN KEY (M_PickingSlot_ID) REFERENCES public.M_PickingSlot DEFERRABLE INITIALLY DEFERRED
;

-- Field: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Picking Slot
-- Column: M_Picking_Job_Line.M_PickingSlot_ID
-- 2025-02-28T08:46:00.014Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589749,740356,0,544862,TO_TIMESTAMP('2025-02-28 08:45:59.843000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Picking Slot',TO_TIMESTAMP('2025-02-28 08:45:59.843000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-28T08:46:00.019Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=740356 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-28T08:46:00.023Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542276)
;

-- 2025-02-28T08:46:00.041Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740356
;

-- 2025-02-28T08:46:00.046Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(740356)
;

-- UI Column: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 10
-- UI Element Group: pick to
-- 2025-02-28T08:46:47.856Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547149,552640,TO_TIMESTAMP('2025-02-28 08:46:47.691000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','pick to',20,TO_TIMESTAMP('2025-02-28 08:46:47.691000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 10 -> pick to.Picking Slot
-- Column: M_Picking_Job_Line.M_PickingSlot_ID
-- 2025-02-28T08:47:07.896Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,740356,0,544862,552640,630662,'F',TO_TIMESTAMP('2025-02-28 08:47:07.737000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Picking Slot',10,0,0,TO_TIMESTAMP('2025-02-28 08:47:07.737000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

