-- F01010.4 — show parent invoice posting state in the
-- "Invoice Accounting Overrides" window so the user can see that a re-post
-- is needed after editing an override on an already-posted invoice.
--
-- What this script does:
--   1. AD_Element 585019 (InvoicePostingStatus) — de_DE name + Help, en_US override
--   2. AD_Column 592833 (virtual ColumnSQL on C_Invoice_Acct, table 542278)
--      ColumnSQL reads C_Invoice.Posted + DocStatus for the parent invoice row
--   3. AD_SQLColumn_SourceTableColumn 540207 — cache-invalidation link to C_Invoice
--   4. AD_Field 781210 — IsReadOnly='Y', IsDisplayed='Y', on tab 546735
--   5. AD_UI_Element 652322 — paired AD_UI_ElementType='F' in element-group 550214
--      ("invoice&matching criteria"), SeqNo=50 (after AccountName=40), not in grid
--   6. AD_Field_Trl skeleton + update_FieldTranslation_From_AD_Name_Element(585019)
--   7. AD_Element_Link rebuild for field 781210
--
-- IDs (pre-allocated from idserver.metas.de):
--   AD_Element_ID                    = 585019
--   AD_Column_ID                     = 592833
--   AD_SQLColumn_SourceTableColumn_ID= 540207
--   AD_Field_ID                      = 781210
--   AD_UI_Element_ID                 = 652322
--
-- Tables touched: C_Invoice_Acct (542278) — model regen required after apply.

-- ============================================================================
-- 1) AD_Element
-- ============================================================================
INSERT INTO AD_Element (
  AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
  Created, CreatedBy, Updated, UpdatedBy,
  ColumnName, EntityType, Name, PrintName, Description, Help)
VALUES (
  585019 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-18 10:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-18 10:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
  'InvoicePostingStatus', 'D',
  'Buchungsstatus Rechnung', 'Buchungsstatus Rechnung',
  'Buchungsstatus der verknüpften Rechnung (gebucht/nicht gebucht + Belegstatus)',
  'Zeigt an, ob die übergeordnete Rechnung gebucht ist und welchen Belegstatus sie hat. Wird eine Konto-Überschreibung nach dem Buchen geändert, ist ein erneutes Buchen der Rechnung erforderlich, damit die Änderung wirksam wird.')
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
  AND t.AD_Element_ID = 585019
  AND NOT EXISTS (
    SELECT 1 FROM AD_Element_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- de_DE — mark as translated (base text is already the German value)
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-18 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Element_ID=585019;

-- de_CH — same text as de_DE (no ß in this text), mark translated
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-18 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Element_ID=585019;

-- en_US override
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Name='Invoice Posting Status', PrintName='Invoice Posting Status',
    Description='Posting status of the linked invoice (posted/not-posted + document status)',
    Help='Shows whether the parent invoice is posted and its current document status. If an accounting override is changed after the invoice has been posted, the invoice must be re-posted for the change to take effect.',
    Updated=TO_TIMESTAMP('2026-06-18 10:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Element_ID=585019;

-- Propagate element translations to all linked records (none yet — column/field come below)
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585019);

-- ============================================================================
-- 2) AD_Column — virtual String column on C_Invoice_Acct (AD_Table_ID=542278)
--    IsSyncDatabase='N' — no physical column; ColumnSQL does the work.
--    AD_Reference_ID=10 (String/Text) — human-readable display.
--    IsUpdateable='N' — read-only virtual.
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
  592833 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-18 10:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-18 10:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
  585019 /* AD_Element InvoicePostingStatus */,
  542278 /* C_Invoice_Acct */,
  'InvoicePostingStatus', 'Buchungsstatus Rechnung', 0,
  10 /* String */, NULL, NULL,
  'D', 'N', 'N', 'N', 'N' /* read-only virtual */,
  'N', 'N', 'N', 'N',
  0, 'Y',
  'N', 'N', 'NP', 255,
  'N', 'N', 'N',
  'N', 'N',
  '(select case when i.posted = ''Y'' then ''Gebucht / Posted'' else ''Nicht gebucht / Not Posted'' end || '' | '' || i.docstatus from c_invoice i where i.c_invoice_id = C_Invoice_Acct.C_Invoice_ID)',
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
  AND t.AD_Column_ID = 592833
  AND NOT EXISTS (
    SELECT 1 FROM AD_Column_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- Propagate element translations → column (fills Name/Description/Help in _Trl)
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585019);

-- ============================================================================
-- 3) AD_SQLColumn_SourceTableColumn — cache-invalidation link
--    When C_Invoice.Posted or DocStatus changes, invalidate C_Invoice_Acct rows
--    for that invoice so the virtual column refreshes in the WebUI.
--    FetchTargetRecordsMethod='S': SQL returns all C_Invoice_Acct_IDs for invoice.
-- ============================================================================
INSERT INTO AD_SQLColumn_SourceTableColumn (
  AD_SQLColumn_SourceTableColumn_ID, AD_Client_ID, AD_Org_ID, IsActive,
  Created, CreatedBy, Updated, UpdatedBy,
  AD_Column_ID, AD_Table_ID, Source_Table_ID,
  FetchTargetRecordsMethod,
  SQL_GetTargetRecordIdBySourceRecordId)
VALUES (
  540207 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-18 10:02:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-18 10:02:00','YYYY-MM-DD HH24:MI:SS'), 100,
  592833 /* AD_Column InvoicePostingStatus on C_Invoice_Acct */,
  542278 /* C_Invoice_Acct — host table */,
  318    /* C_Invoice — source table (has Posted + DocStatus) */,
  'S',
  'select c_invoice_acct_id from c_invoice_acct where c_invoice_id = @Record_ID@')
ON CONFLICT (AD_SQLColumn_SourceTableColumn_ID) DO NOTHING;

-- ============================================================================
-- 4) AD_Field — read-only field on tab 546735 (window 541659)
--    IsReadOnly='Y': the field must not be editable.
--    IsDisplayed='Y': must be shown.
--    AD_Column_ID=592833 links to the virtual column.
--    No AD_Name_ID override — the column's element (585019) is the source.
-- ============================================================================
INSERT INTO AD_Field (
  AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive,
  Created, CreatedBy, Updated, UpdatedBy,
  Name, AD_Tab_ID, AD_Column_ID,
  IsDisplayed, IsReadOnly, IsMandatory, IsDisplayedGrid,
  SeqNo, SeqNoGrid,
  EntityType, IsEncrypted, IsFieldOnly, IsHeading, IsSameLine)
VALUES (
  781210 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-18 10:03:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-18 10:03:00','YYYY-MM-DD HH24:MI:SS'), 100,
  'Buchungsstatus Rechnung',
  546735 /* AD_Tab — Invoice Accounting Overrides */,
  592833 /* InvoicePostingStatus virtual column */,
  'Y', 'Y' /* IsReadOnly=Y */, 'N', 'N' /* not in grid */,
  50, 0,
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
  AND t.AD_Field_ID = 781210
  AND NOT EXISTS (
    SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- Propagate element translations → field (pass column's element ID for standard case)
SELECT update_FieldTranslation_From_AD_Name_Element(585019);

-- Rebuild AD_Element_Link for this field
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781210;
SELECT AD_Element_Link_Create_Missing_Field(781210);

-- ============================================================================
-- 5) AD_UI_Element — pairs the field with the WebUI layout
--    AD_UI_ElementType='F' (field), placed in element-group 550214
--    ("invoice&matching criteria") at SeqNo=50, not in grid (SeqNoGrid=0).
--    This places it after AccountName (SeqNo=40) within the matching-criteria group.
-- ============================================================================
INSERT INTO AD_UI_Element (
  AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
  Created, CreatedBy, Updated, UpdatedBy,
  Name, AD_Tab_ID, AD_Field_ID, AD_UI_ElementGroup_ID,
  AD_UI_ElementType, SeqNo, SeqNoGrid,
  IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
  IsAdvancedField)
VALUES (
  652322 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-18 10:04:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-18 10:04:00','YYYY-MM-DD HH24:MI:SS'), 100,
  'Buchungsstatus Rechnung',
  546735 /* AD_Tab */,
  781210 /* AD_Field InvoicePostingStatus */,
  550214 /* AD_UI_ElementGroup "invoice&matching criteria" */,
  'F', 50, 0,
  'Y', 'N', 'N',
  'N')
ON CONFLICT (AD_UI_Element_ID) DO NOTHING;
