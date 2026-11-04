-- Introduce a dedicated C_BPartner.Matchcode column (VARCHAR 60).
-- Background: AD_Element 'Matchcode' (584301) was previously used to RELABEL the Name3 field
-- (migration 5779010). That is replaced here: Matchcode becomes its own column, and the Name3
-- field is reverted to "Name3" so Name3 can hold a real third name again.
-- Reuses existing AD_Element 584301 (Matchcode). C_BPartner = AD_Table 291, BPartner window tab = 220.

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
  TO_TIMESTAMP('2026-11-04 14:30','YYYY-MM-DD HH24:MI'),100,'N',NULL,'D',0,60,'Y','N',
  'Y','N','N','N','N','N',
  'N','N','N','N','N',
  'N','N','N','N','N','N','N',
  'N','Y','N','N','N',
  'Y','N','Y','N',0,'Matchcode',0,0,
  'P',TO_TIMESTAMP('2026-11-04 14:30','YYYY-MM-DD HH24:MI'),100,0
)
;

/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN Matchcode VARCHAR(60)')
;

-- 2) Field for Matchcode in the Business Partner window (AD_Tab 220), same UI group as Name3 (1000010)
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
  'N','N',0,'Matchcode',30,0,0,TO_TIMESTAMP('2026-11-04 14:30','YYYY-MM-DD HH24:MI'),100
)
;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(788125)
;

-- 3) Revert the Name3 field (560559) from the "Matchcode" relabel (migration 5779010) back to "Name3"
UPDATE AD_Field SET AD_Name_ID=543480, Name='Name3',
  Updated=TO_TIMESTAMP('2026-11-04 14:30','YYYY-MM-DD HH24:MI'),UpdatedBy=100
 WHERE AD_Field_ID=560559
;

UPDATE AD_Field_Trl SET Name='Name3', IsTranslated='Y'
 WHERE AD_Field_ID=560559 AND AD_Language='de_DE'
;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(543480)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=560559
;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(560559)
;
