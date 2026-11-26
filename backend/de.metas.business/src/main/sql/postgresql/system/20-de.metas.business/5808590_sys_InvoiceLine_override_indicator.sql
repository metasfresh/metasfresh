-- F01010.4 — Flag overridden GL account on invoice line
--
-- An invoice line is "overridden" when a C_Invoice_Acct row matches it:
--   - line-level:    c_invoiceline_id = this line's ID, or
--   - invoice-level: c_invoice_id = this line's invoice AND c_invoiceline_id IS NULL
-- The virtual column returns the overridden account name(s) (comma-separated via
-- STRING_AGG from C_ElementValue) or NULL when no override applies.
-- NULL → no marker; a non-NULL value → tooltip icon appears on the product field.
--
-- The tooltip is surfaced via the standard AD_UI_ElementField type='tooltip' pattern
-- (same as C_BPartner_Memo, M_Product_DocumentNote — reused ~19x in the codebase).
-- It attaches to the M_Product_ID AD_UI_Element (542663) on CORE tab 291
-- ("Rechnungsposition", standard window 183 "Eingangsrechnung", C_Invoice.po_window_id=183).
-- dt204's override window (541976/tab 548568) gets the same via a separate customer-repo script.
--
-- This script does NOT touch C_InvoiceLine.Account_ID (dropped in Task 8).
-- It does NOT change posting logic (read-time derived only).
--
-- What this script does:
--   1. AD_Element 585020 (InvoiceAcctOverride_Indicator) — de/en name + help
--   2. AD_Column 592834 — virtual ColumnSQL on C_InvoiceLine (table 333)
--      Returns STRING_AGG(ev.name) of matched C_ElementValue rows, or NULL.
--   3. AD_SQLColumn_SourceTableColumn 540208 — cache link to C_Invoice_Acct
--   4. AD_SQLColumn_SourceTableColumn 540209 — cache link to C_ElementValue
--   5. AD_Field 781211 — IsReadOnly='Y', on CORE tab 291
--   6. AD_Field_Trl skeleton + update_FieldTranslation_From_AD_Name_Element(585020)
--   7. AD_Element_Link rebuild for field 781211
--   8. AD_UI_ElementField 542540 — type='tooltip', tooltipiconname='text',
--      on AD_UI_Element 542663 (M_Product_ID, core tab 291), pointing at AD_Field 781211
--
-- IDs (pre-allocated from idserver.metas.de):
--   AD_Element_ID                     = 585020
--   AD_Column_ID                      = 592834
--   AD_SQLColumn_SourceTableColumn_ID = 540208 (C_Invoice_Acct source)
--   AD_SQLColumn_SourceTableColumn_ID = 540209 (C_ElementValue source)
--   AD_Field_ID                       = 781211
--   AD_UI_Element_ID                  = 652323 (spare — NOT used; tooltip rides on 542663)
--   AD_UI_ElementField_ID             = 542540
--
-- Tables touched: C_InvoiceLine (333) — model regen required after apply.

-- ============================================================================
-- 1) AD_Element
-- ============================================================================
INSERT INTO AD_Element (
  AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
  Created, CreatedBy, Updated, UpdatedBy,
  ColumnName, EntityType, Name, PrintName, Description, Help)
VALUES (
  585020 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-18 11:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-18 11:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
  'InvoiceAcctOverride_Indicator', 'D',
  'Konto-Überschreibung', 'Konto-Überschreibung',
  'Überschriebenes Buchungskonto dieser Rechnungsposition',
  'Zeigt das überschriebene Buchungskonto für diese Rechnungsposition an. Eine Überschreibung liegt vor, wenn in den Rechnung-Konten-Überschreibungen ein passender Eintrag für diese Position oder für die gesamte Rechnung vorhanden ist.')
ON CONFLICT (AD_Element_ID) DO NOTHING;

-- Seed _Trl rows for all active system languages (copies base DE text)
INSERT INTO AD_Element_Trl (
  AD_Language, AD_Element_ID, Name, PrintName, Description, Help,
  IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT
  l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help,
  'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Element_ID = 585020
  AND NOT EXISTS (
    SELECT 1 FROM AD_Element_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- de_DE — mark as translated (base text is already the German value)
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-18 11:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Element_ID=585020;

-- de_CH — same text as de_DE (no ß in this text), mark translated
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-18 11:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Element_ID=585020;

-- en_US override
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Name='Account Override', PrintName='Account Override',
    Description='Overridden GL account for this invoice line',
    Help='Shows the overridden posting account for this invoice line. An override is present when a matching entry exists in Invoice Accounting Overrides for this line or for the entire invoice.',
    Updated=TO_TIMESTAMP('2026-06-18 11:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Element_ID=585020;

-- Propagate element translations to all linked records (column/field come below)
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585020);

-- ============================================================================
-- 2) AD_Column — virtual String column on C_InvoiceLine (AD_Table_ID=333)
--    IsSyncDatabase='N' — no physical column; ColumnSQL does the work.
--    AD_Reference_ID=10 (String/Text) — human-readable display.
--    IsUpdateable='N' — read-only virtual.
--
--    ColumnSQL logic:
--      Match C_Invoice_Acct rows for this line:
--        - line-level:    c_invoiceline_id = @...@C_InvoiceLine_ID
--        - invoice-level: c_invoice_id = @...@C_Invoice_ID
--                         AND c_invoiceline_id IS NULL
--      Aggregate C_ElementValue.Name for all matches.
--      Returns NULL if no override applies (→ no tooltip icon shown).
-- ============================================================================
INSERT INTO AD_Column (
  AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
  Created, CreatedBy, Updated, UpdatedBy,
  AD_Element_ID, AD_Table_ID, ColumnName, Name, Version,
  AD_Reference_ID, AD_Reference_Value_ID, AD_Val_Rule_ID,
  EntityType, IsKey, IsParent, IsMandatory, IsUpdateable,
  IsIdentifier, IsTranslated, IsEncrypted, IsSelectionColumn,
  SeqNo, IsAllowLogging, IsAutocomplete, IsCalculated,
  PersonalDataCategory, FieldLength,
  IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsForceIncludeInGeneratedModel,
  IsLazyLoading, IsUseDocSequence,
  ColumnSql, IsStaleable, IsSyncDatabase)
VALUES (
  592834 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-18 11:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-18 11:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
  585020 /* AD_Element InvoiceAcctOverride_Indicator */,
  333 /* C_InvoiceLine */,
  'InvoiceAcctOverride_Indicator', 'Konto-Überschreibung', 0,
  10 /* String */, NULL, NULL,
  'D', 'N', 'N', 'N', 'N' /* read-only virtual */,
  'N', 'N', 'N', 'N',
  0, 'Y',
  'N', 'N', 'NP', 255,
  'N', 'N', 'N',
  'N', 'N',
  '(select string_agg(ev.name, '', '' order by ev.name)
    from c_invoice_acct ia
    join c_elementvalue ev on ev.c_elementvalue_id = ia.c_elementvalue_id
    where ia.isactive = ''Y''
      and (
            (ia.c_invoiceline_id = C_InvoiceLine.C_InvoiceLine_ID)
            or (    ia.c_invoice_id     = C_InvoiceLine.C_Invoice_ID
                and ia.c_invoiceline_id is null)
          ))',
  'N', 'N' /* IsSyncDatabase=N → virtual, no physical column */)
ON CONFLICT (AD_Column_ID) DO NOTHING;

-- Seed AD_Column_Trl rows
INSERT INTO AD_Column_Trl (
  AD_Language, AD_Column_ID, Name, Description, IsTranslated,
  AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT
  l.AD_Language, t.AD_Column_ID, t.Name, NULL, 'N',
  t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND t.AD_Column_ID = 592834
  AND NOT EXISTS (
    SELECT 1 FROM AD_Column_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- Propagate element translations → column
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585020);

-- ============================================================================
-- 3a) AD_SQLColumn_SourceTableColumn — cache-invalidation: C_Invoice_Acct
--     When a C_Invoice_Acct row changes, find affected C_InvoiceLine rows.
--     We need both line-level and invoice-level matches, so use SQL method.
--     The SQL returns all C_InvoiceLine_IDs covered by the changed override row.
-- ============================================================================
INSERT INTO AD_SQLColumn_SourceTableColumn (
  AD_SQLColumn_SourceTableColumn_ID, AD_Client_ID, AD_Org_ID, IsActive,
  Created, CreatedBy, Updated, UpdatedBy,
  AD_Column_ID, AD_Table_ID, Source_Table_ID,
  FetchTargetRecordsMethod,
  SQL_GetTargetRecordIdBySourceRecordId)
VALUES (
  540208 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-18 11:02:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-18 11:02:00','YYYY-MM-DD HH24:MI:SS'), 100,
  592834 /* AD_Column InvoiceAcctOverride_Indicator on C_InvoiceLine */,
  333    /* C_InvoiceLine — host table */,
  542278 /* C_Invoice_Acct — source table */,
  'S',
  'select il.c_invoiceline_id
   from c_invoice_acct ia
   join c_invoiceline il on il.c_invoice_id = ia.c_invoice_id
   where ia.c_invoice_acct_id = @Record_ID@
     and (ia.c_invoiceline_id is null or ia.c_invoiceline_id = il.c_invoiceline_id)')
ON CONFLICT (AD_SQLColumn_SourceTableColumn_ID) DO NOTHING;

-- ============================================================================
-- 3b) AD_SQLColumn_SourceTableColumn — cache-invalidation: C_ElementValue
--     When the name of a GL account changes, refresh lines that reference it.
-- ============================================================================
INSERT INTO AD_SQLColumn_SourceTableColumn (
  AD_SQLColumn_SourceTableColumn_ID, AD_Client_ID, AD_Org_ID, IsActive,
  Created, CreatedBy, Updated, UpdatedBy,
  AD_Column_ID, AD_Table_ID, Source_Table_ID,
  FetchTargetRecordsMethod,
  SQL_GetTargetRecordIdBySourceRecordId)
VALUES (
  540209 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-18 11:02:01','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-18 11:02:01','YYYY-MM-DD HH24:MI:SS'), 100,
  592834 /* AD_Column InvoiceAcctOverride_Indicator on C_InvoiceLine */,
  333    /* C_InvoiceLine — host table */,
  188    /* C_ElementValue — source table */,
  'S',
  'select il.c_invoiceline_id
   from c_invoice_acct ia
   join c_invoiceline il on il.c_invoice_id = ia.c_invoice_id
   where ia.c_elementvalue_id = @Record_ID@
     and ia.isactive = ''Y''
     and (ia.c_invoiceline_id is null or ia.c_invoiceline_id = il.c_invoiceline_id)')
ON CONFLICT (AD_SQLColumn_SourceTableColumn_ID) DO NOTHING;

-- ============================================================================
-- 4) AD_Field — read-only tooltip field on CORE tab 291
--    IsReadOnly='Y': the tooltip value must not be editable.
--    IsDisplayed='Y', IsDisplayedGrid='N': shown in form (tooltip only); not a grid col.
--    SeqNo=270 (core tab 291, after existing max 260), SeqNoGrid=0.
--    AD_Column_ID=592834 links to the virtual column.
--    No AD_Name_ID override — the column's element (585020) is the source.
-- ============================================================================
INSERT INTO AD_Field (
  AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
  Created, CreatedBy, Updated, UpdatedBy,
  Name, AD_Tab_ID, AD_Column_ID,
  IsDisplayed, IsReadOnly, IsMandatory, IsDisplayedGrid,
  SeqNo, SeqNoGrid,
  EntityType, IsEncrypted, IsFieldOnly, IsHeading, IsSameLine)
VALUES (
  781211 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-18 11:03:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-18 11:03:00','YYYY-MM-DD HH24:MI:SS'), 100,
  'Konto-Überschreibung',
  291 /* AD_Tab — Rechnungsposition, CORE window 183 "Eingangsrechnung" */,
  592834 /* InvoiceAcctOverride_Indicator virtual column */,
  'Y', 'Y' /* IsReadOnly=Y */, 'N', 'N' /* not in grid */,
  270, 0,
  'D', 'N', 'N', 'N', 'N')
ON CONFLICT (AD_Field_ID) DO NOTHING;

-- Seed AD_Field_Trl skeleton rows for all active system languages
INSERT INTO AD_Field_Trl (
  AD_Language, AD_Field_ID, Name, Description, Help,
  IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT
  l.AD_Language, t.AD_Field_ID, t.Name, NULL, NULL,
  'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND t.AD_Field_ID = 781211
  AND NOT EXISTS (
    SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- Propagate element translations → field (pass column's element ID for standard case)
SELECT update_FieldTranslation_From_AD_Name_Element(585020);

-- Durable en_US/de propagation (guard-bypass): copy translated element texts into the
-- field _Trl directly (the function above no-ops when the _Trl timestamps coincide).
UPDATE AD_Field_Trl ft
SET    Name=et.Name, Description=et.Description, Help=et.Help, IsTranslated=et.IsTranslated,
       Updated=TO_TIMESTAMP('2026-06-19 10:07:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
FROM   AD_Element_Trl et
WHERE  et.AD_Element_ID=585020 /*From ID Server*/
  AND  ft.AD_Field_ID=781211 /*From ID Server*/
  AND  ft.AD_Language=et.AD_Language
  AND  et.IsTranslated='Y';

-- Rebuild AD_Element_Link for this field
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781211;
SELECT AD_Element_Link_Create_Missing_Field(781211);

-- ============================================================================
-- 5) AD_UI_ElementField — tooltip attachment
--    The tooltip rides on the existing M_Product_ID AD_UI_Element (542663)
--    on CORE tab 291. We add a type='tooltip' elementfield pointing at field 781211.
--    TooltipIconName='text' matches all other tooltip examples in the codebase.
--    SeqNo=20 (after the existing widget elementfield at seqno=10 for M_HU_PI_Item_Product_ID).
--
--    No separate AD_UI_Element is created — per the C_BPartner_Memo precedent,
--    a tooltip attaches directly to an existing field's AD_UI_Element.
-- ============================================================================
INSERT INTO AD_UI_ElementField (
  AD_UI_ElementField_ID, AD_Client_ID, AD_Org_ID, IsActive,
  Created, CreatedBy, Updated, UpdatedBy,
  AD_UI_Element_ID, AD_Field_ID,
  Type, SeqNo, TooltipIconName)
VALUES (
  542540 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-18 11:04:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-18 11:04:00','YYYY-MM-DD HH24:MI:SS'), 100,
  542663 /* AD_UI_Element: M_Product_ID on CORE tab 291 */,
  781211 /* AD_Field: InvoiceAcctOverride_Indicator */,
  'tooltip', 20, 'text')
ON CONFLICT (AD_UI_ElementField_ID) DO NOTHING;
