-- Run mode: SWING_CLIENT

-- Column: C_InvoiceLine.C_ElementValue_Override_ID
-- me03#30443 F01010.4 "Invoice Accounting Overrides" — NT2
-- Adds a GL account override FK field to C_InvoiceLine so a user can set the
-- override directly on a draft purchase invoice line.
--
-- Mirrors NT1 (5808640 — C_Invoice_Candidate) exactly:
--   same column name, same reference (AD_Reference_ID=30 Search, AD_Reference_Value_ID=331),
--   same AD_Element 585015 (C_ElementValue_Override_ID / "Konto (Überschreibung)").
--
-- AD_Table_ID for C_InvoiceLine = 333
-- Reusing AD_Element_ID=585015 (C_ElementValue_Override_ID) — do NOT create a new element.
-- AD_Reference_ID=30 (Search), AD_Reference_Value_ID=331 (Account_ID table ref).
-- PersonalDataCategory='NP' (GL account FK — required).
-- EntityType='D' (C_InvoiceLine is core).
--
-- ReadOnlyLogic: '@Processed@=Y' — field is editable on draft, read-only once completed.
-- (Idiom verified from existing migration 5764080_sys_gh24725_DatePromised_Override_Update.sql
--  and confirmed: C_InvoiceLine has a Processed column (AD_Reference_ID=20).)
--
-- IDs allocated from idserver.metas.de:
--   AD_Column_ID  = 592837  (C_InvoiceLine.C_ElementValue_Override_ID)
--   AD_Field_ID   = 781215  tab 548568 (Rechnungsposition in Eingangsrechnung 541976)
--   AD_UI_Element_ID = 652327  tab 548568, group 553880 (default)
--
-- Placement: SeqNo=147, after field 781211 (Konto-Überschreibung tooltip at 145).
-- Form-view only (IsDisplayedGrid='N', SeqNoGrid=0).
-- UI SeqNo=150 (after max=140 in current UI elements, placed after tooltip group).
--
-- Tables touched: C_InvoiceLine (333) — model regen required after apply.

-- ============================================================
-- AD_Column
-- ============================================================

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,PersonalDataCategory,Version) VALUES (0,592837/*From ID Server*/,585015,0,30,331,333,'XX','C_ElementValue_Override_ID',TO_TIMESTAMP('2026-06-18 13:00:00','YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','N',0,'Konto (Überschreibung)',0,0,TO_TIMESTAMP('2026-06-18 13:00:00','YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC',100,'NP',0)
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
  AND t.AD_Column_ID = 592837
  AND NOT EXISTS
      (SELECT 1 FROM AD_Column_Trl tt
       WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

/* DDL */ SELECT update_Column_Translation_From_AD_Element(585015)
;

/* DDL */ SELECT public.db_alter_table('C_InvoiceLine',
    'ALTER TABLE public.C_InvoiceLine ADD COLUMN IF NOT EXISTS C_ElementValue_Override_ID NUMERIC(10) DEFAULT NULL NULL')
;

-- ============================================================
-- AD_Field: Tab 548568 (Rechnungsposition in Eingangsrechnung 541976)
-- Placed at SeqNo=147, right after InvoiceAcctOverride_Indicator tooltip field (SeqNo=145).
-- Form view only (IsDisplayedGrid='N', SeqNoGrid=0).
-- ReadOnlyLogic='@Processed@=Y': editable on draft, read-only once completed.
-- IsReadOnly='N' + ReadOnlyLogic is the correct pattern for conditional read-only.
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
     ReadOnlyLogic,
     SelectionColumnSeqNo, SeqNo, SeqNoGrid, SortNo,
     SpanX, SpanY,
     Updated, UpdatedBy)
VALUES
    (0, 592837, 781215 /*From ID Server*/, 0, 548568,
     0,
     TO_TIMESTAMP('2026-06-18 13:00:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC', 100,
     0, 'D',
     0, 0,
     'Y', 'Y', 'N',
     'N', 'N', 'N',
     'N', 'N',
     'N', 'N',
     0, 'Konto (Überschreibung)',
     '@Processed@=Y',
     0, 147, 0, 0,
     1, 1,
     TO_TIMESTAMP('2026-06-18 13:00:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC', 100)
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
  AND t.AD_Field_ID = 781215
  AND NOT EXISTS
      (SELECT 1 FROM AD_Field_Trl tt
       WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585015)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781215
;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781215)
;

-- ============================================================
-- AD_UI_Element: pair for AD_Field 781215
-- Group 553880 ("default") on tab 548568, seqno=150 (after existing max 140 for External IDs)
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
     652327 /*From ID Server*/, 548568, 553880,
     781215,
     'F',
     TO_TIMESTAMP('2026-06-18 13:00:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC', 100,
     'Y',
     'N',
     'Y', 'N', 'N',
     'Konto (Überschreibung)',
     150, 0,
     TO_TIMESTAMP('2026-06-18 13:00:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC', 100)
;
