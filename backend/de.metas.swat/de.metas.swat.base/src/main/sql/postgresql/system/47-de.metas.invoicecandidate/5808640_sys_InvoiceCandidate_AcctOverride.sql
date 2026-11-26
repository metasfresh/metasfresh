-- Run mode: SWING_CLIENT

-- Column: C_Invoice_Candidate.C_ElementValue_Override_ID
-- F01010.4 "Invoice Accounting Overrides"
-- Adds a GL account override field to C_Invoice_Candidate so that the invoicing engine
-- can post to a specific account instead of the product-derived one.
--
-- AD_Table_ID for C_Invoice_Candidate = 540270
-- Reusing AD_Element_ID=585015 (C_ElementValue_Override_ID / Konto (Überschreibung))
-- AD_Reference_ID=30 (Search), AD_Reference_Value_ID=331 (Account_ID — active AC accounts for client)
-- Pattern mirrors C_Tax_Override_ID (Search/30 + table-ref) — column name does not match
-- <TableName>_ID so AD_Reference_Value_ID is mandatory (else GenerateModel fails).
--
-- IDs allocated from idserver.metas.de:
--   AD_Column  592836  (C_Invoice_Candidate.C_ElementValue_Override_ID)
--   AD_Field   781214  tab 543052 (Rechnungsdisposition Einkauf / Rechnungskandidaten)
--   AD_UI_Element 652326  tab 543052, group 544364 (override)

-- ============================================================
-- AD_Column
-- ============================================================

-- AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,
-- CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,
-- IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,
-- IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,
-- IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,
-- IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,
-- IsMandatory,IsParent,IsRestAPICustomColumn,
-- IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,
-- IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,
-- MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,PersonalDataCategory,Version
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,PersonalDataCategory,Version) VALUES (0,592836/*From ID Server*/,585015,0,30,331,540270,'XX','C_ElementValue_Override_ID',TO_TIMESTAMP('2026-06-18 12:00:00','YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC',100,'N','de.metas.invoicecandidate',0,10,'Y','N','Y','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Konto (Überschreibung)',0,0,TO_TIMESTAMP('2026-06-18 12:00:00','YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC',100,'NP',0)
;

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID,
     Name, IsTranslated,
     AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT
    l.AD_Language, t.AD_Column_ID,
    t.Name, 'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592836
  AND NOT EXISTS
      (SELECT 1 FROM AD_Column_Trl tt
       WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

/* DDL */ SELECT update_Column_Translation_From_AD_Element(585015)
;

/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate',
    'ALTER TABLE public.C_Invoice_Candidate ADD COLUMN IF NOT EXISTS C_ElementValue_Override_ID NUMERIC(10) DEFAULT NULL NULL')
;

-- ============================================================
-- AD_Field: Tab 543052 (Rechnungskandidaten / Rechnungsdisposition Einkauf)
-- Placed immediately after C_Tax_Override_ID (form SeqNo 340, gridSeqNo 390).
-- New field gets SeqNo=345. Grid display disabled (IsDisplayedGrid='N', SeqNoGrid=0) — form view only.
-- ============================================================

INSERT INTO AD_Field
    (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
     ColumnDisplayLength,
     Created, CreatedBy,
     DisplayLength, EntityType,
     FacetFilterSeqNo, IncludedTabHeight,
     IsActive, IsDisplayed, IsDisplayedGrid,
     IsEncrypted, IsFieldOnly, IsHeading,
     IsHideGridColumnIfEmpty, IsOverrideFilterDefaultValue,
     IsReadOnly, IsSameLine,
     MaxFacetsToFetch, Name,
     SelectionColumnSeqNo, SeqNo, SeqNoGrid, SortNo,
     SpanX, SpanY,
     Updated, UpdatedBy)
VALUES
    (0, 592836, 781214 /*From ID Server*/, 0, 543052,
     0,
     TO_TIMESTAMP('2026-06-18 12:00:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC', 100,
     0, 'de.metas.invoicecandidate',
     0, 0,
     'Y', 'Y', 'N',
     'N', 'N', 'N',
     'N', 'N',
     'N', 'N',
     0, 'Konto (Überschreibung)',
     0, 345, 0, 0,
     1, 1,
     TO_TIMESTAMP('2026-06-18 12:00:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC', 100)
;

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID,
     Description, Help, Name,
     IsTranslated,
     AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT
    l.AD_Language, t.AD_Field_ID,
    t.Description, t.Help, t.Name,
    'N',
    t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 781214
  AND NOT EXISTS
      (SELECT 1 FROM AD_Field_Trl tt
       WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585015)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781214
;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781214)
;

-- ============================================================
-- AD_UI_Element: pair for AD_Field 781214
-- Group 544364 ("override") on tab 543052, seqno=60 (after existing max 50 for C_Tax_Effective_ID)
-- Form-view only (SeqNoGrid=0, IsDisplayedGrid='N')
-- ============================================================

INSERT INTO AD_UI_Element
    (AD_Client_ID, AD_Org_ID,
     AD_UI_Element_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
     AD_Field_ID,
     AD_UI_ElementType,
     Created, CreatedBy,
     IsActive,
     IsAdvancedField,
     IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
     Name,
     SeqNo, SeqNoGrid,
     Updated, UpdatedBy)
VALUES
    (0, 0,
     652326 /*From ID Server*/, 543052, 544364,
     781214,
     'F',
     TO_TIMESTAMP('2026-06-18 12:00:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC', 100,
     'Y',
     'N',
     'Y', 'N', 'N',
     'Konto (Überschreibung)',
     60, 0,
     TO_TIMESTAMP('2026-06-18 12:00:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC', 100)
;
