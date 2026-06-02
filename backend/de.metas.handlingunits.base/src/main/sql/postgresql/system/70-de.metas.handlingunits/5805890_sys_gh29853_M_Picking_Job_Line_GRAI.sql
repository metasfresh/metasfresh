-- IDs allocated from idserver.metas.de on 2026-06-03:
--   AD_Element   584935  (Current_PickTo_TU_GRAI — new)
--   AD_Column    592701  (M_Picking_Job_Line.Current_PickTo_TU_GRAI)
--   AD_Field     780663  (Picking Job Line tab, IsDisplayed='N' — internal only)
--   AD_UI_Element 651963 (Picking Job Line tab, IsDisplayed='N' — internal only)
--
-- Referenced existing IDs (verified from prior scripts):
--   AD_Table     541907  (M_Picking_Job_Line — from 5609260_sys_M_Picking_Job.sql)
--   AD_Tab       544862  (Picking Job Line tab in Kommissionieraufgabe window — from 5766820)
--   AD_UI_ElementGroup 552640 ("pick to" group in that tab — from 5766820)
--
-- This column is an internal persistence column (not user-facing).
-- Pattern matches sibling Current_PickTo_TU_QRCode (5766820_M_Picking_Job_Line.Current_PickTo_TU_ID.sql)
-- which is also IsDisplayed='N' in AD_Field and AD_UI_Element.

-- AD_Element: Current_PickTo_TU_GRAI
-- 2026-06-03T10:00:00.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,584935 /*From ID Server*/,0,'Current_PickTo_TU_GRAI',TO_TIMESTAMP('2026-06-03 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits','Y','Current Pick To TU GRAI','Current Pick To TU GRAI',TO_TIMESTAMP('2026-06-03 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-03T10:00:01.000Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584935
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Picking_Job_Line.Current_PickTo_TU_GRAI
-- 2026-06-03T10:01:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592701 /*From ID Server*/,584935,0,36,541907,'XX','Current_PickTo_TU_GRAI',TO_TIMESTAMP('2026-06-03 10:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.handlingunits',0,9999999,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Current Pick To TU GRAI','NP',0,0,TO_TIMESTAMP('2026-06-03 10:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-06-03T10:01:01.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592701
AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-03T10:01:02.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(584935)
;

-- Physical column (new column — use ALTER TABLE ADD COLUMN, not t_alter_column)
-- 2026-06-03T10:02:00.000Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job_Line','ALTER TABLE public.M_Picking_Job_Line ADD COLUMN IF NOT EXISTS Current_PickTo_TU_GRAI VARCHAR(255)')
;

-- Field: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> Current Pick To TU GRAI
-- Column: M_Picking_Job_Line.Current_PickTo_TU_GRAI
-- IsDisplayed='N': internal persistence column, not user-facing
-- 2026-06-03T10:03:00.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592701,780663 /*From ID Server*/,0,544862,TO_TIMESTAMP('2026-06-03 10:03:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,9999999,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Current Pick To TU GRAI',TO_TIMESTAMP('2026-06-03 10:03:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-03T10:03:01.000Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=780663
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-03T10:03:02.000Z
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(584935)
;

-- 2026-06-03T10:03:03.000Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780663
;

-- 2026-06-03T10:03:04.000Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(780663)
;

-- UI Element: Kommissionieraufgabe(541331,de.metas.handlingunits) -> Picking Job Line(544862,de.metas.handlingunits) -> main -> 10 -> pick to.Current Pick To TU GRAI
-- Column: M_Picking_Job_Line.Current_PickTo_TU_GRAI
-- IsDisplayed='N': hidden — internal persistence column
-- 2026-06-03T10:04:00.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,780663,0,544862,552640,651963 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-03 10:04:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','N','N','Current Pick To TU GRAI',0,0,0,TO_TIMESTAMP('2026-06-03 10:04:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
