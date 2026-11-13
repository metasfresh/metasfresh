-- Run mode: SWING_CLIENT

-- Add nullable M_Shipper.DocumentCopies (Integer) + field on the Lieferweg window (142) / tab 185.
-- NULL = not set (fall back), 0 = print nothing, N = N copies. No DB default, not mandatory.
-- Reuses the existing AD_Element 866 (DocumentCopies), so no element or element translation is created here.
--
-- IDs allocated from idserver.metas.de on 2026-10-19:
--   AD_Column     593749 (M_Shipper.DocumentCopies)
--   AD_Field      785688 (Lieferweg window 142 / tab 185)
--   AD_UI_Element 655046 (tracking group 541021, SeqNo 40)

-- Column: M_Shipper.DocumentCopies
-- AD_Table_ID=253 (M_Shipper), AD_Reference_ID=11 (Integer), AD_Element_ID=866 (DocumentCopies)
-- 2026-10-19T10:00:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,PersonalDataCategory,Version,ValueMin)
VALUES (0,593749 /*From ID Server*/,866,0,11,253,'XX','DocumentCopies',TO_TIMESTAMP('2026-10-19 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kopien',0,0,TO_TIMESTAMP('2026-10-19 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'NP',0,'0')
;

-- 2026-10-19T10:00:00.100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593749
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-10-19T10:00:00.200Z
/* DDL */  select update_Column_Translation_From_AD_Element(866)
;

-- Physical column: M_Shipper.DocumentCopies NUMERIC(10), nullable, no default
-- 2026-10-19T10:00:01.000Z
/* DDL */ SELECT public.db_alter_table('M_Shipper','ALTER TABLE public.M_Shipper ADD COLUMN DocumentCopies NUMERIC(10)')
;

-- Field: Lieferweg(142,D) -> Lieferweg(185,D) -> Kopien
-- Column: M_Shipper.DocumentCopies
-- 2026-10-19T10:01:00.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593749,785688 /*From ID Server*/,0,185,TO_TIMESTAMP('2026-10-19 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','N','Kopien',TO_TIMESTAMP('2026-10-19 10:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-10-19T10:01:00.100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=785688
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-10-19T10:01:00.200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(866)
;

-- 2026-10-19T10:01:00.300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=785688
;

-- 2026-10-19T10:01:00.400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(785688)
;

-- UI Element: Lieferweg(142,D) -> Lieferweg(185,D) -> main -> 10 -> tracking.Kopien
-- Column: M_Shipper.DocumentCopies
-- Placed in tracking group (541021, left column), SeqNo 40 (after Shipper Gateway at 30); not a checkbox so not in the flag group; form view only
-- 2026-10-19T10:02:00.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,785688,0,185,541021,655046 /*From ID Server*/,'F',TO_TIMESTAMP('2026-10-19 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kopien',40,0,0,TO_TIMESTAMP('2026-10-19 10:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;
