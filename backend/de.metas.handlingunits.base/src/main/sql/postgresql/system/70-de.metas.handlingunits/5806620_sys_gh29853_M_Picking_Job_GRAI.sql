-- IDs allocated from idserver.metas.de on 2026-06-06:
--   AD_Column    592730  (M_Picking_Job.Current_PickTo_TU_GRAI)
--   AD_Field     780724  (Picking Job header tab, IsDisplayed='N' — internal only)
--   AD_UI_Element 652019 (Picking Job header tab, IsDisplayed='N' — internal only)
--
-- Referenced existing IDs (verified from prior scripts):
--   AD_Element   584935  (Current_PickTo_TU_GRAI — REUSED from 5805890_sys_gh29853_M_Picking_Job_Line_GRAI.sql)
--   AD_Table     541906  (M_Picking_Job — from 5766040_M_Picking_Job.M_TU_HU_ID.sql)
--   AD_Tab       544861  (Kommissionieraufgabe header tab — from 5766040)
--   AD_UI_ElementGroup 551853 ("Picking to LU/TU" group in that tab — from 5766040)
--
-- This column is an internal persistence column (not user-facing). It mirrors the line-level
-- M_Picking_Job_Line.Current_PickTo_TU_GRAI (5805890) on the job header, so the scanned GRAI
-- of the TU pick-target survives the save/reload roundtrip for order-based (job-level) aggregation.
--   AD_Field.IsDisplayed='N' / AD_UI_Element.IsDisplayed='N': pure backend persistence column,
--   never shown to users.

-- Column: M_Picking_Job.Current_PickTo_TU_GRAI
-- 2026-06-06T10:01:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592730 /*From ID Server*/,584935,0,36,541906,'XX','Current_PickTo_TU_GRAI',TO_TIMESTAMP('2026-06-06 10:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.handlingunits',0,9999999,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Current Pick To TU GRAI','NP',0,0,TO_TIMESTAMP('2026-06-06 10:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-06-06T10:01:01.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592730
AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-06T10:01:02.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(584935)
;

-- Physical column (new column — use ALTER TABLE ADD COLUMN, not t_alter_column)
-- 2026-06-06T10:02:00.000Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job','ALTER TABLE public.M_Picking_Job ADD COLUMN IF NOT EXISTS Current_PickTo_TU_GRAI TEXT')
;

-- Field: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> Current Pick To TU GRAI
-- Column: M_Picking_Job.Current_PickTo_TU_GRAI
-- IsDisplayed='N': internal persistence column, not user-facing
-- 2026-06-06T10:03:00.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592730,780724 /*From ID Server*/,0,544861,TO_TIMESTAMP('2026-06-06 10:03:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,9999999,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Current Pick To TU GRAI',TO_TIMESTAMP('2026-06-06 10:03:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-06T10:03:01.000Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=780724
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-06T10:03:02.000Z
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(584935)
;

-- 2026-06-06T10:03:03.000Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780724
;

-- 2026-06-06T10:03:04.000Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(780724)
;

-- UI Element: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Kommissionieraufgabe(544861,de.metas.handlingunits) -> main -> 10 -> Picking to LU/TU.Current Pick To TU GRAI
-- Column: M_Picking_Job.Current_PickTo_TU_GRAI
-- IsDisplayed='N': hidden — internal persistence column
-- 2026-06-06T10:04:00.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,780724,0,544861,551853,652019 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-06 10:04:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','N','N','Aktuelles Pickziel-TU GRAI',90,0,0,TO_TIMESTAMP('2026-06-06 10:04:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
