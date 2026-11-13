-- Introduce a dedicated C_BPartner.Matchcode column (VARCHAR 255).
-- Background: AD_Element 'Matchcode' (584301) was previously used to RELABEL the Name3 field
-- (migration 5779010). That is replaced here: Matchcode becomes its own column, and the Name3
-- field is reverted to "Name3" so Name3 can hold a real third name again.
-- Reuses existing AD_Element 584301 (Matchcode). C_BPartner = AD_Table 291, BPartner window tab = 220.
--
-- Field captions are derived from the element: new fields get skeleton AD_Field_Trl rows and are then
-- filled by update_FieldTranslation_From_AD_Name_Element; AD_Field_Trl is never written directly.
-- The Name3 revert sets AD_Field.AD_Name_ID back to NULL (the standard field takes its caption from the
-- column's own element 543480, which is how Name3 looked before the 5779010 relabel).

-- 1) New column C_BPartner.Matchcode (AD_Column + physical DDL)
INSERT INTO AD_Column (
  AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,
  Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,
  IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,
  IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,
  IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,
  IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,
  IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,
  PersonalDataCategory,Updated,UpdatedBy,Version
) VALUES (
  0,593769,584301,0,10,291,'XX','Matchcode',
  TO_TIMESTAMP('2026-11-04 14:30','YYYY-MM-DD HH24:MI'),100,'N',NULL,'D',0,255,'Y','N',
  'Y','N','N','N','N','N',
  'N','N','N','N','N',
  'N','N','N','N','N','N','N',
  'N','Y','N','N','N',
  'Y','N','Y','N',0,'Matchcode',0,0,
  'P',TO_TIMESTAMP('2026-11-04 14:30','YYYY-MM-DD HH24:MI'),100,0
)
;

/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN Matchcode VARCHAR(255)')
;

-- 2) Field for Matchcode in the Business Partner window (AD_Tab 220), same UI group as Name3 (1000010).
--    AD_Name_ID left NULL: the caption is taken from the column's element (584301 Matchcode).
INSERT INTO AD_Field (
  AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,
  EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy
) VALUES (
  0,593769,788125,0,220,TO_TIMESTAMP('2026-11-04 14:30','YYYY-MM-DD HH24:MI'),100,NULL,0,
  'D','Y','Y','N','N','N','N','N','Matchcode',TO_TIMESTAMP('2026-11-04 14:30','YYYY-MM-DD HH24:MI'),100
)
;

INSERT INTO AD_UI_Element (
  AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,
  Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,
  IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy
) VALUES (
  0,788125,0,220,1000010,656441,'F',
  TO_TIMESTAMP('2026-11-04 14:30','YYYY-MM-DD HH24:MI'),100,NULL,'Y','N','N','Y','N',
  'N','N',0,'Matchcode',26,0,0,TO_TIMESTAMP('2026-11-04 14:30','YYYY-MM-DD HH24:MI'),100
)
;

-- 2a) Skeleton AD_Field_Trl rows for the new field (one per active system/base language), left untranslated.
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Field_ID=788125
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(788125)
;

-- 3) Revert the Name3 field (560559) from the "Matchcode" relabel (migration 5779010) back to "Name3".
--    AD_Name_ID -> NULL so the caption comes from the Name3 column's element (543480).
UPDATE AD_Field SET AD_Name_ID=NULL, Name='Name3',
  Updated=TO_TIMESTAMP('2026-11-04 14:30','YYYY-MM-DD HH24:MI'),UpdatedBy=100
 WHERE AD_Field_ID=560559
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=560559
;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(560559)
;

-- 4) Propagate element captions to the field translations (and base AD_Field rows):
--    584301 fills the new Matchcode field; 543480 restores "Name3" on the reverted field.
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584301)
;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(543480)
;
