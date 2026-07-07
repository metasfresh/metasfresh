-- Run mode: SWING_CLIENT

-- Virtual ColumnSQL field on C_Invoice_Candidate that resolves the GL account override FK
-- (C_ElementValue_Override_ID, added in 5808640) to "<Value> - <Name>" for display.
-- F01010.4 "Invoice Accounting Overrides"
--
-- AD_Table_ID for C_Invoice_Candidate = 540270
-- AD_Table_ID for C_ElementValue       = 188
-- AD_Element_ID  = 585025  (new: "Überschreibungskonto (aufgelöst)" / "Override account (resolved)")
-- AD_Column_ID   = 592842  (virtual ColumnSQL column)
-- AD_Field_ID    = 781219  (tab 543052, SeqNo=348 — between override FK 345 and effective tax 350)
-- AD_UI_Element_ID = 652331 (group 544364, SeqNo=70)

-- ============================================================
-- 1. AD_Element (new — label + help for the resolved-display virtual column)
-- ============================================================

INSERT INTO AD_Element
    (AD_Client_ID, AD_Element_ID, AD_Org_ID,
     ColumnName,
     Created, CreatedBy,
     Description,
     EntityType,
     Help,
     IsActive,
     Name,
     PrintName,
     Updated, UpdatedBy)
VALUES
    (0, 585025 /*From ID Server*/, 0,
     'C_Invoice_Acct_OverrideAcct_Display',
     TO_TIMESTAMP('2026-06-18 12:01:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC', 100,
     'Aufgelöstes Überschreibungskonto (Wert – Name)',
     'de.metas.invoicecandidate',
     'Zeigt das unter "Konto (Überschreibung)" gewählte Sachkonto als "Wert – Name" an.',
     'Y',
     'Überschreibungskonto (aufgelöst)',
     'Überschreibungskonto (aufgelöst)',
     TO_TIMESTAMP('2026-06-18 12:01:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC', 100)
;

-- AD_Element_Trl — seed all active system languages NOT EXISTS
INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID,
     Description, Help, IsTranslated,
     Name, PO_Description, PO_Help, PO_Name, PO_PrintName,
     PrintName,
     AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT
    l.AD_Language, e.AD_Element_ID,
    e.Description, e.Help, 'N',
    e.Name, NULL, NULL, NULL, NULL,
    e.PrintName,
    e.AD_Client_ID, e.AD_Org_ID, e.Created, e.CreatedBy, e.Updated, e.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element e
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND e.AD_Element_ID = 585025
  AND NOT EXISTS
      (SELECT 1 FROM AD_Element_Trl tt
       WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = e.AD_Element_ID)
;

-- de_DE override (base language — IsTranslated='Y' for explicit languages)
UPDATE AD_Element_Trl
SET Name        = 'Überschreibungskonto (aufgelöst)',
    PrintName   = 'Überschreibungskonto (aufgelöst)',
    Description = 'Aufgelöstes Überschreibungskonto (Wert – Name)',
    Help        = 'Zeigt das unter "Konto (Überschreibung)" gewählte Sachkonto als "Wert – Name" an.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-18 12:01:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC',
    UpdatedBy    = 100
WHERE AD_Element_ID = 585025
  AND AD_Language = 'de_DE'
;

-- de_CH override (ß → ss per Swiss convention; otherwise identical to de_DE — none here)
UPDATE AD_Element_Trl
SET Name        = 'Überschreibungskonto (aufgelöst)',
    PrintName   = 'Überschreibungskonto (aufgelöst)',
    Description = 'Aufgelöstes Überschreibungskonto (Wert – Name)',
    Help        = 'Zeigt das unter "Konto (Überschreibung)" gewählte Sachkonto als "Wert – Name" an.',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-18 12:01:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC',
    UpdatedBy    = 100
WHERE AD_Element_ID = 585025
  AND AD_Language = 'de_CH'
;

-- en_US override
UPDATE AD_Element_Trl
SET Name        = 'Override account (resolved)',
    PrintName   = 'Override account (resolved)',
    Description = 'Resolved override GL account (Value – Name)',
    Help        = 'Shows the GL account selected in "Override account" as "Value – Name".',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-18 12:01:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC',
    UpdatedBy    = 100
WHERE AD_Element_ID = 585025
  AND AD_Language = 'en_US'
;

-- ============================================================
-- 2. AD_Column (virtual, IsSyncDatabase='N', IsReadOnly implied by ColumnSQL)
-- ============================================================

INSERT INTO AD_Column
    (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID,
     AD_Reference_ID,
     AD_Table_ID,
     CloningStrategy, ColumnName,
     ColumnSQL,
     Created, CreatedBy,
     DDL_NoForeignKey, EntityType,
     FacetFilterSeqNo, FieldLength,
     IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable,
     IsAutoApplyValidationRule, IsAutocomplete, IsCalculated,
     IsDimension, IsDLMPartitionBoundary, IsEncrypted,
     IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel,
     IsGenericZoomKeyColumn, IsGenericZoomOrigin,
     IsIdentifier, IsKey, IsLazyLoading,
     IsMandatory, IsParent, IsRestAPICustomColumn,
     IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline,
     IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence,
     MaxFacetsToFetch, Name,
     PersonalDataCategory,
     SelectionColumnSeqNo, SeqNo,
     Updated, UpdatedBy, Version)
VALUES
    (0, 592842 /*From ID Server*/, 585025, 0,
     10,  -- String
     540270,
     'XX', 'C_Invoice_Acct_OverrideAcct_Display',
     '(SELECT ev.Value || '' - '' || ev.Name FROM C_ElementValue ev WHERE ev.C_ElementValue_ID = C_Invoice_Candidate.C_ElementValue_Override_ID)',
     TO_TIMESTAMP('2026-06-18 12:01:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC', 100,
     'N', 'de.metas.invoicecandidate',
     0, 60,
     'Y', 'N', 'Y', 'N',
     'N', 'N', 'N',
     'N', 'N', 'N',
     'Y', 'N', 'N',
     'N', 'N',
     'N', 'N', 'N',
     'N', 'N', 'N',
     'N', 'N', 'N',
     'N', 'N', 'N', 'N', 'N',
     0, 'Überschreibungskonto (aufgelöst)',
     'NP',
     0, 0,
     TO_TIMESTAMP('2026-06-18 12:01:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC', 100, 0)
;

-- AD_Column_Trl — seed all active system languages NOT EXISTS
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
  AND t.AD_Column_ID = 592842
  AND NOT EXISTS
      (SELECT 1 FROM AD_Column_Trl tt
       WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

/* DDL */ SELECT update_Column_Translation_From_AD_Element(585025)
;

-- NOTE: No db_alter_table DDL — this is a virtual ColumnSQL column (IsSyncDatabase='N').

-- ============================================================
-- 3. AD_SQLColumn_SourceTableColumn — cache invalidation
--    Source: C_ElementValue (AD_Table_ID=188) linked via C_ElementValue_Override_ID (AD_Column_ID=592836)
--    IDs: use next available from the sequence (no pre-allocated IDs for source rows)
-- ============================================================

INSERT INTO AD_SQLColumn_SourceTableColumn
    (AD_Client_ID, AD_Org_ID,
     AD_SQLColumn_SourceTableColumn_ID,
     AD_Column_ID,
     AD_Table_ID,
     source_table_id,
     source_column_id,
     link_column_id,
     FetchTargetRecordsMethod,
     IsActive,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (0, 0,
     540220 /*From ID Server*/,
     592842,     -- virtual column
     540270,     -- C_Invoice_Candidate (own table)
     188,        -- C_ElementValue (source table)
     1125,       -- C_ElementValue.C_ElementValue_ID (source column — the PK being joined)
     592836,     -- C_Invoice_Candidate.C_ElementValue_Override_ID (link column — the FK on own table)
     'L',
     'Y',
     TO_TIMESTAMP('2026-06-18 12:01:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC', 100,
     TO_TIMESTAMP('2026-06-18 12:01:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC', 100)
;

-- ============================================================
-- 4. AD_Field (read-only, form-view only, SeqNo=348 — between override FK 345 and effective tax 350)
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
    (0, 592842, 781219 /*From ID Server*/, 0, 543052,
     0,
     TO_TIMESTAMP('2026-06-18 12:01:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC', 100,
     0, 'de.metas.invoicecandidate',
     0, 0,
     'Y', 'Y', 'N',
     'N', 'N', 'N',
     'N', 'N',
     'Y', 'N',
     0, 'Überschreibungskonto (aufgelöst)',
     0, 348, 0, 0,
     1, 1,
     TO_TIMESTAMP('2026-06-18 12:01:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC', 100)
;

-- AD_Field_Trl — seed all active system languages NOT EXISTS
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
  AND t.AD_Field_ID = 781219
  AND NOT EXISTS
      (SELECT 1 FROM AD_Field_Trl tt
       WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585025)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781219
;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781219)
;

-- ============================================================
-- 5. AD_UI_Element (paired with AD_Field 781219, group 544364, SeqNo=70)
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
     652331 /*From ID Server*/, 543052, 544364,
     781219,
     'F',
     TO_TIMESTAMP('2026-06-18 12:01:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC', 100,
     'Y',
     'N',
     'Y', 'N', 'N',
     'Überschreibungskonto (aufgelöst)',
     70, 0,
     TO_TIMESTAMP('2026-06-18 12:01:00', 'YYYY-MM-DD HH24:MI:SS') AT TIME ZONE 'UTC', 100)
;
