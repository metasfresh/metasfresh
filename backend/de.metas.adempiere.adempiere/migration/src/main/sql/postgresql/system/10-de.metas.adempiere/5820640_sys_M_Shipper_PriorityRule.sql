-- Run mode: SWING_CLIENT

-- Add M_Shipper.PriorityRule (char(1), nullable) + AD metadata; reuses existing AD_Element 522
--
-- IDs allocated from idserver.metas.de on 2026-08-27:
--   AD_Column     593411 (M_Shipper.PriorityRule, nullable)
--   AD_Field      783022 (Lieferweg window 142 / tab 185)
--   AD_UI_Element 653671 (main group 541019, SeqNo 50)

-- Column: M_Shipper.PriorityRule
-- AD_Table_ID=253 (M_Shipper), AD_Reference_ID=17 (List), AD_Reference_Value_ID=154 (_PriorityRule)
-- 2026-08-27T00:00:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,PersonalDataCategory,Version)
VALUES (0,593411 /*From ID Server*/,522,0,17,154,253,'XX','PriorityRule',TO_TIMESTAMP('2026-08-27 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Priorität',0,0,TO_TIMESTAMP('2026-08-27 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'NP',0)
;

-- 2026-08-27T00:00:00.100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593411
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-08-27T00:00:00.200Z
/* DDL */  select update_Column_Translation_From_AD_Element(522)
;

-- Physical column: M_Shipper.PriorityRule CHAR(1)
-- 2026-08-27T00:00:01.000Z
/* DDL */ SELECT public.db_alter_table('M_Shipper','ALTER TABLE public.M_Shipper ADD COLUMN PriorityRule CHAR(1)')
;

-- Field: Lieferweg(142,D) -> Lieferweg(185,D) -> Priorität
-- Column: M_Shipper.PriorityRule
-- 2026-08-27T00:00:02.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593411,783022 /*From ID Server*/,0,185,TO_TIMESTAMP('2026-08-27 00:00:02','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Priorität',TO_TIMESTAMP('2026-08-27 00:00:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-08-27T00:00:02.100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783022
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-08-27T00:00:02.200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(522)
;

-- 2026-08-27T00:00:02.300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=783022
;

-- 2026-08-27T00:00:02.400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(783022)
;

-- UI Element: Lieferweg(142,D) -> Lieferweg(185,D) -> main -> 50 -> Priorität
-- Column: M_Shipper.PriorityRule
-- Placed in main group (541019, col 10, sec 10), SeqNo 50
-- 2026-08-27T00:00:03.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,783022,0,185,541019,653671 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-27 00:00:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Priorität',50,0,0,TO_TIMESTAMP('2026-08-27 00:00:03','YYYY-MM-DD HH24:MI:SS'),100)
;
