-- E-Invoicing: add C_Tax.EN16931VATCategory column (UNTDID 5305 subset, EN16931 VAT category code).
-- Mapper tasks B4/B5 read this column to produce the BT-151 VAT category code on eInvoices.
-- Accountants set/correct the value per tax rate; the backfill below provides a best-effort start.
--
-- IDs allocated from idserver.metas.de on 2026-06-19:
--   AD_MigrationScript  5808910  (prefix = 5808910)
--   AD_Reference        542109   (EN16931VATCategory ref-list)
--   AD_Ref_List         544274   (S)
--   AD_Ref_List         544275   (Z)
--   AD_Ref_List         544276   (E)
--   AD_Ref_List         544277   (AE)
--   AD_Ref_List         544278   (K)
--   AD_Ref_List         544279   (G)
--   AD_Ref_List         544280   (O)
--   AD_Element          585031   (EN16931VATCategory)
--   AD_Column           592857   (C_Tax.EN16931VATCategory)
--   AD_Field            781226   (field on tab 174)
--   AD_UI_Element       652337   (ui element in group 545777 / property)

-- =============================================================================
-- STEP 1: AD_Reference (ValidationType='L', German name in base column)
-- =============================================================================

INSERT INTO AD_Reference (AD_Client_ID, IsActive, Created, CreatedBy, IsOrderByValue,
  Updated, UpdatedBy, AD_Reference_ID, ValidationType, Name, AD_Org_ID, EntityType)
VALUES (0, 'Y',
  TO_TIMESTAMP('2026-06-19 10:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
  'N',
  TO_TIMESTAMP('2026-06-19 10:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
  542109 /*From ID Server*/, 'L', 'EN16931VATCategory', 0, 'D')
;

-- =============================================================================
-- STEP 2: AD_Reference_Trl (skeleton rows for all system languages)
-- =============================================================================

INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Help, Name, Description,
  IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Reference_ID, t.Help, t.Name, t.Description,
  'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Reference t
WHERE l.IsActive='Y'
  AND l.IsSystemLanguage='Y'
  AND t.AD_Reference_ID=542109 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Update en_US translation for the reference itself
UPDATE AD_Reference_Trl
SET IsTranslated='Y', Name='EN16931 VAT Category',
  Updated=TO_TIMESTAMP('2026-06-19 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Reference_ID=542109 /*From ID Server*/
;

-- =============================================================================
-- STEP 3: AD_Ref_List entries (German names in base column per convention)
-- ValueName = Value (used as Java constant identifier in generated X_* class)
-- =============================================================================

-- S = Normaler Steuersatz
INSERT INTO AD_Ref_List (AD_Reference_ID, AD_Client_ID, IsActive, Created, CreatedBy,
  Name, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, AD_Org_ID, Description, EntityType)
VALUES (542109 /*From ID Server*/, 0, 'Y',
  TO_TIMESTAMP('2026-06-19 10:00:02','YYYY-MM-DD HH24:MI:SS'), 100,
  'Normaler Steuersatz',
  TO_TIMESTAMP('2026-06-19 10:00:02','YYYY-MM-DD HH24:MI:SS'), 100,
  544274 /*From ID Server*/, 'S', 'S', 0, NULL, 'D')
;

-- Z = Nullsatz
INSERT INTO AD_Ref_List (AD_Reference_ID, AD_Client_ID, IsActive, Created, CreatedBy,
  Name, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, AD_Org_ID, Description, EntityType)
VALUES (542109 /*From ID Server*/, 0, 'Y',
  TO_TIMESTAMP('2026-06-19 10:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
  'Nullsatz',
  TO_TIMESTAMP('2026-06-19 10:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
  544275 /*From ID Server*/, 'Z', 'Z', 0, NULL, 'D')
;

-- E = Steuerbefreit
INSERT INTO AD_Ref_List (AD_Reference_ID, AD_Client_ID, IsActive, Created, CreatedBy,
  Name, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, AD_Org_ID, Description, EntityType)
VALUES (542109 /*From ID Server*/, 0, 'Y',
  TO_TIMESTAMP('2026-06-19 10:00:04','YYYY-MM-DD HH24:MI:SS'), 100,
  'Steuerbefreit',
  TO_TIMESTAMP('2026-06-19 10:00:04','YYYY-MM-DD HH24:MI:SS'), 100,
  544276 /*From ID Server*/, 'E', 'E', 0, NULL, 'D')
;

-- AE = Reverse Charge (Umkehrung der Steuerschuld)
INSERT INTO AD_Ref_List (AD_Reference_ID, AD_Client_ID, IsActive, Created, CreatedBy,
  Name, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, AD_Org_ID, Description, EntityType)
VALUES (542109 /*From ID Server*/, 0, 'Y',
  TO_TIMESTAMP('2026-06-19 10:00:05','YYYY-MM-DD HH24:MI:SS'), 100,
  'Reverse Charge',
  TO_TIMESTAMP('2026-06-19 10:00:05','YYYY-MM-DD HH24:MI:SS'), 100,
  544277 /*From ID Server*/, 'AE', 'AE', 0, NULL, 'D')
;

-- K = Steuerbefreit (innergemeinschaftlich, EWR)
INSERT INTO AD_Ref_List (AD_Reference_ID, AD_Client_ID, IsActive, Created, CreatedBy,
  Name, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, AD_Org_ID, Description, EntityType)
VALUES (542109 /*From ID Server*/, 0, 'Y',
  TO_TIMESTAMP('2026-06-19 10:00:06','YYYY-MM-DD HH24:MI:SS'), 100,
  'Steuerbefreit (innergemeinschaftlich)',
  TO_TIMESTAMP('2026-06-19 10:00:06','YYYY-MM-DD HH24:MI:SS'), 100,
  544278 /*From ID Server*/, 'K', 'K', 0, NULL, 'D')
;

-- G = Exportlieferung (steuerfrei)
INSERT INTO AD_Ref_List (AD_Reference_ID, AD_Client_ID, IsActive, Created, CreatedBy,
  Name, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, AD_Org_ID, Description, EntityType)
VALUES (542109 /*From ID Server*/, 0, 'Y',
  TO_TIMESTAMP('2026-06-19 10:00:07','YYYY-MM-DD HH24:MI:SS'), 100,
  'Exportlieferung (steuerfrei)',
  TO_TIMESTAMP('2026-06-19 10:00:07','YYYY-MM-DD HH24:MI:SS'), 100,
  544279 /*From ID Server*/, 'G', 'G', 0, NULL, 'D')
;

-- O = Nicht im Steuerumfang
INSERT INTO AD_Ref_List (AD_Reference_ID, AD_Client_ID, IsActive, Created, CreatedBy,
  Name, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, AD_Org_ID, Description, EntityType)
VALUES (542109 /*From ID Server*/, 0, 'Y',
  TO_TIMESTAMP('2026-06-19 10:00:08','YYYY-MM-DD HH24:MI:SS'), 100,
  'Nicht im Steuerumfang',
  TO_TIMESTAMP('2026-06-19 10:00:08','YYYY-MM-DD HH24:MI:SS'), 100,
  544280 /*From ID Server*/, 'O', 'O', 0, NULL, 'D')
;

-- =============================================================================
-- STEP 4: AD_Ref_List_Trl (skeleton rows) + English translation UPDATEs
-- =============================================================================

-- S
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, Description,
  IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name, t.Description,
  'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND t.AD_Ref_List_ID=544274 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Standard rate',
  Updated=TO_TIMESTAMP('2026-06-19 10:00:09','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544274 /*From ID Server*/
;

-- Z
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, Description,
  IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name, t.Description,
  'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND t.AD_Ref_List_ID=544275 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Zero rated goods',
  Updated=TO_TIMESTAMP('2026-06-19 10:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544275 /*From ID Server*/
;

-- E
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, Description,
  IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name, t.Description,
  'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND t.AD_Ref_List_ID=544276 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Exempt from tax',
  Updated=TO_TIMESTAMP('2026-06-19 10:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544276 /*From ID Server*/
;

-- AE
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, Description,
  IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name, t.Description,
  'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND t.AD_Ref_List_ID=544277 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='VAT Reverse Charge',
  Updated=TO_TIMESTAMP('2026-06-19 10:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544277 /*From ID Server*/
;

-- K
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, Description,
  IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name, t.Description,
  'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND t.AD_Ref_List_ID=544278 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='VAT exempt — intra-community supply (EEA)',
  Updated=TO_TIMESTAMP('2026-06-19 10:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544278 /*From ID Server*/
;

-- G
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, Description,
  IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name, t.Description,
  'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND t.AD_Ref_List_ID=544279 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Free export item, tax not charged',
  Updated=TO_TIMESTAMP('2026-06-19 10:00:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544279 /*From ID Server*/
;

-- O
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, Description,
  IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name, t.Description,
  'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND t.AD_Ref_List_ID=544280 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Services outside scope of tax',
  Updated=TO_TIMESTAMP('2026-06-19 10:00:15','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544280 /*From ID Server*/
;

-- =============================================================================
-- STEP 5: AD_Element (German name in base column, ColumnName = EN16931VATCategory)
-- =============================================================================

INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
  Created, CreatedBy, Updated, UpdatedBy,
  ColumnName, EntityType,
  Name, PrintName, Description, Help)
VALUES (585031 /*From ID Server*/, 0, 0, 'Y',
  TO_TIMESTAMP('2026-06-19 10:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
  TO_TIMESTAMP('2026-06-19 10:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
  'EN16931VATCategory', 'D',
  'EN16931 MwSt-Kategorie', 'EN16931 MwSt-Kategorie',
  'MwSt-Kategoriecode nach UNTDID 5305 (EN16931) für die eRechnung.', NULL)
;

-- =============================================================================
-- STEP 6: AD_Element_Trl skeleton rows for all system languages
-- =============================================================================

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID,
  CommitWarning, Description, Help, Name, PO_Description, PO_Help,
  PO_Name, PO_PrintName, PrintName,
  WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb,
  IsTranslated, AD_Client_ID, AD_Org_ID,
  Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID,
  t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help,
  t.PO_Name, t.PO_PrintName, t.PrintName,
  t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb,
  'N', t.AD_Client_ID, t.AD_Org_ID,
  t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Element_ID=585031 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Update de_DE translation (same text as base column which is already German)
UPDATE AD_Element_Trl
SET Name='EN16931 MwSt-Kategorie', PrintName='EN16931 MwSt-Kategorie',
    Description='MwSt-Kategoriecode nach UNTDID 5305 (EN16931) für die eRechnung.',
    Help=NULL,
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-19 10:01:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585031 /*From ID Server*/ AND AD_Language='de_DE'
;

-- Update de_CH translation (same text, ß→ss not applicable here)
UPDATE AD_Element_Trl
SET Name='EN16931 MwSt-Kategorie', PrintName='EN16931 MwSt-Kategorie',
    Description='MwSt-Kategoriecode nach UNTDID 5305 (EN16931) für die eRechnung.',
    Help=NULL,
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-19 10:01:18','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585031 /*From ID Server*/ AND AD_Language='de_CH'
;

-- Update en_US translation (English)
UPDATE AD_Element_Trl
SET Name='EN16931 VAT Category', PrintName='EN16931 VAT Category',
    Description='VAT category code per UNTDID 5305 (EN16931) for e-invoicing.',
    Help=NULL,
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-19 10:01:24','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585031 /*From ID Server*/ AND AD_Language='en_US'
;

-- =============================================================================
-- STEP 7: AD_Column (C_Tax.EN16931VATCategory)
-- AD_Table_ID=261 (C_Tax), AD_Reference_ID=17 (List), AD_Reference_Value_ID=542109
-- NOT mandatory (accountants fill/correct gradually), FieldLength=2 (longest value = 'AE')
-- PersonalDataCategory='NP' (tax rate code, not personal data)
-- =============================================================================

INSERT INTO AD_Column (
  AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID,
  AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID,
  CloningStrategy, ColumnName,
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
VALUES (
  0, 592857 /*From ID Server*/, 585031 /*From ID Server*/, 0,
  17, 542109 /*From ID Server*/, 261,
  'XX', 'EN16931VATCategory',
  TO_TIMESTAMP('2026-06-19 10:02:00','YYYY-MM-DD HH24:MI:SS'), 100,
  'N', 'D',
  0, 2,
  'Y', 'N', 'Y', 'N',
  'N', 'N', 'N',
  'N', 'N', 'N',
  'Y', 'N', 'N',
  'N', 'N',
  'N', 'N', 'N',
  'N', 'N', 'N',
  'N', 'N', 'N',
  'N', 'N', 'N', 'Y', 'N',
  0, 'EN16931 MwSt-Kategorie',
  'NP',
  0, 0,
  TO_TIMESTAMP('2026-06-19 10:02:00','YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

-- =============================================================================
-- STEP 8: AD_Column_Trl skeleton rows
-- =============================================================================

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
  AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
  t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Column_ID=592857 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- =============================================================================
-- STEP 9: Propagate translations from AD_Element to AD_Column
-- =============================================================================

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585031 /*From ID Server*/);
/* DDL */ SELECT update_Column_Translation_From_AD_Element(585031 /*From ID Server*/);

-- =============================================================================
-- STEP 10: Physical column (new column — use ALTER TABLE ADD COLUMN, not t_alter_column)
-- Nullable: NOT mandatory in AD, accountants fill gradually
-- =============================================================================

ALTER TABLE C_Tax ADD COLUMN IF NOT EXISTS EN16931VATCategory VARCHAR(2);

-- Check constraint to enforce valid UNTDID 5305 values
-- DROP-IF-EXISTS + ADD = idempotent on re-run (raw ADD or db_alter_table would error "already exists")
ALTER TABLE C_Tax DROP CONSTRAINT IF EXISTS EN16931VATCategory_Check;
ALTER TABLE C_Tax ADD CONSTRAINT EN16931VATCategory_Check
  CHECK (EN16931VATCategory IN ('S','Z','E','AE','K','G','O'));

-- =============================================================================
-- STEP 11: Backfill best-effort initial values
-- Priority order matches the task brief. C_Tax_ID=100 (Tax_Not_Found) stays NULL.
-- Accountants are expected to correct edge cases — this is a starting point only.
-- =============================================================================

-- Rule 1: IsReverseCharge='Y' → 'AE' (takes precedence)
UPDATE C_Tax SET EN16931VATCategory='AE'
WHERE IsReverseCharge='Y' AND C_Tax_ID != 100
;

-- Rule 2: IsTaxExempt='Y' AND IsToEULocation='Y' → 'K'
UPDATE C_Tax SET EN16931VATCategory='K'
WHERE IsTaxExempt='Y' AND IsToEULocation='Y' AND IsReverseCharge='N' AND C_Tax_ID != 100
;

-- Rule 3: IsTaxExempt='Y' AND IsToEULocation='N' → 'E'
-- (third-country export may really be 'G'; accountant corrects)
UPDATE C_Tax SET EN16931VATCategory='E'
WHERE IsTaxExempt='Y' AND (IsToEULocation IS NULL OR IsToEULocation='N')
  AND IsReverseCharge='N' AND C_Tax_ID != 100
;

-- Rule 4: Rate=0, IsToEULocation='Y', not exempt/RC → 'K'
UPDATE C_Tax SET EN16931VATCategory='K'
WHERE Rate=0 AND IsToEULocation='Y'
  AND IsTaxExempt='N' AND IsReverseCharge='N'
  AND EN16931VATCategory IS NULL AND C_Tax_ID != 100
;

-- Rule 5: Rate=0, IsToEULocation='N', not exempt/RC → 'Z'
UPDATE C_Tax SET EN16931VATCategory='Z'
WHERE Rate=0 AND (IsToEULocation IS NULL OR IsToEULocation='N')
  AND IsTaxExempt='N' AND IsReverseCharge='N'
  AND EN16931VATCategory IS NULL AND C_Tax_ID != 100
;

-- Rule 6: Rate>0, not exempt/RC → 'S'
UPDATE C_Tax SET EN16931VATCategory='S'
WHERE Rate>0
  AND IsTaxExempt='N' AND IsReverseCharge='N'
  AND EN16931VATCategory IS NULL AND C_Tax_ID != 100
;

-- =============================================================================
-- STEP 12: AD_Field on tab 174 (window 137 "Steuersatz", tab "Steuer")
-- Placed in the 'property' element group (545777) near IsReverseCharge / Rate.
-- SeqNo placement: after 715578 (IsReverseCharge, currently no SeqNo set → treating as
-- informal; we place at SeqNo=320, after ValidTo at 310), SeqNoGrid=235 (after 230)
-- =============================================================================

INSERT INTO AD_Field (
  AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
  Created, CreatedBy, Description, DisplayLength,
  EntityType, Help, IsActive,
  IsDisplayed, IsDisplayedGrid, IsEncrypted,
  IsMandatory, IsReadOnly, IsSameLine,
  Name, SeqNo, SeqNoGrid,
  Updated, UpdatedBy)
VALUES (
  0, 592857 /*From ID Server*/, 781226 /*From ID Server*/, 0, 174,
  TO_TIMESTAMP('2026-06-19 10:03:00','YYYY-MM-DD HH24:MI:SS'), 100,
  'MwSt-Kategoriecode nach UNTDID 5305 (EN16931) für die eRechnung.', 2,
  'D', NULL, 'Y',
  'Y', 'Y', 'N',
  'N', 'N', 'N',
  'EN16931 MwSt-Kategorie', 320, 235,
  TO_TIMESTAMP('2026-06-19 10:03:00','YYYY-MM-DD HH24:MI:SS'), 100)
;

-- =============================================================================
-- STEP 13: AD_Field_Trl skeleton rows
-- =============================================================================

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Name, IsTranslated,
  AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, 'N',
  t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Field_ID=781226 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Propagate element translations → field (uses element ID 585031, not field ID)
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585031 /*From ID Server*/);

-- Rebuild element links for the field
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781226 /*From ID Server*/;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781226 /*From ID Server*/);

-- =============================================================================
-- STEP 14: AD_UI_Element in group 545777 ('property') on tab 174
-- SeqNo=5  (before Reverse Charge at SeqNo=15 → EN16931VATCategory logically comes first
--           as it classifies the tax's legal nature; RC is one of its values)
-- SeqNoGrid=135 (after IsReverseCharge at 130, before SeqNo at 50 — grid order by relevance)
-- IsDisplayed='Y', IsDisplayedGrid='Y'
-- =============================================================================

INSERT INTO AD_UI_Element (
  AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID,
  AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType,
  Created, CreatedBy, IsActive, IsAdvancedField,
  IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList,
  Name, SeqNo, SeqNoGrid, SeqNo_SideList,
  Updated, UpdatedBy)
VALUES (
  0, 781226 /*From ID Server*/, 0, 174,
  545777, 652337 /*From ID Server*/, 'F',
  TO_TIMESTAMP('2026-06-19 10:04:00','YYYY-MM-DD HH24:MI:SS'), 100,
  'Y', 'N',
  'Y', 'Y', 'N',
  'EN16931 MwSt-Kategorie', 5, 135, 0,
  TO_TIMESTAMP('2026-06-19 10:04:00','YYYY-MM-DD HH24:MI:SS'), 100)
;

-- =============================================================================
-- STEP 15: Re-propagate element translations now that the AD_Field exists
-- (the first call in step 9 ran before the field existed, so field_trl rows
--  weren't updated yet; this call fills them in)
-- =============================================================================

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585031 /*From ID Server*/);
