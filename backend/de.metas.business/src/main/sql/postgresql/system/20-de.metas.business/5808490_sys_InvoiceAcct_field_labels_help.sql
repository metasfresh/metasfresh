-- F01010.4 Invoice Accounting Overrides — AC2: correct field labels and German help
--
-- Impact summary:
--   AD_Element 577539 (AccountName): used ONLY by C_Invoice_Acct.AccountName → safe to rename
--     Old name: "Kontenbezeichnung"  → New name: "Kontenart"
--   AD_Element 198 (C_ElementValue_ID): SHARED across 10+ columns/tabs — NOT mutated here.
--     Window 541659 field 710156 (C_ElementValue_ID) gets AD_Name_ID override pointing at new element 585015.
--   New AD_Element 585015 /*From ID Server*/: "Konto (Überschreibung)" — dedicated to window 541659's
--     C_ElementValue_ID field only, via AD_Field.AD_Name_ID.
--   DE help added for all 5 user-facing fields via AD_Field overrides (Help column on AD_Field).
--
-- Fields addressed (window 541659):
--   710152  C_Invoice_ID         — add DE help
--   710153  C_InvoiceLine_ID     — add DE help
--   710154  C_AcctSchema_ID      — add DE help
--   710155  AccountName (577539) — rename element to "Kontenart", add DE help with empty=all note
--   710156  C_ElementValue_ID    — new element 585015 "Konto (Überschreibung)" via AD_Name_ID

-- ============================================================
-- STEP 1: Rename AD_Element 577539 (AccountName) → "Kontenart"
-- ============================================================
-- Base column: German (base language is de_DE)
UPDATE AD_Element
SET    Name        = 'Kontenart',
       PrintName   = 'Kontenart',
       Description = 'Buchhalterisches Konzept, das durch diesen Eintrag überschrieben wird.',
       Help        = 'Buchhalterisches Konto-Konzept (z. B. Erlöskonto, Aufwandskonto), das durch diesen Überschreibungseintrag ersetzt wird. Leer lassen bedeutet: Die Überschreibung gilt für alle Kontenarten der Rechnung bzw. Rechnungsposition.',
       Updated     = TO_TIMESTAMP('2026-06-18 09:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy   = 100
WHERE  AD_Element_ID = 577539;

-- de_DE translation
UPDATE AD_Element_Trl
SET    Name        = 'Kontenart',
       PrintName   = 'Kontenart',
       Description = 'Buchhalterisches Konzept, das durch diesen Eintrag überschrieben wird.',
       Help        = 'Buchhalterisches Konto-Konzept (z. B. Erlöskonto, Aufwandskonto), das durch diesen Überschreibungseintrag ersetzt wird. Leer lassen bedeutet: Die Überschreibung gilt für alle Kontenarten der Rechnung bzw. Rechnungsposition.',
       IsTranslated = 'Y',
       Updated     = TO_TIMESTAMP('2026-06-18 09:00:12', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy   = 100
WHERE  AD_Element_ID = 577539 AND AD_Language = 'de_DE';

-- de_CH translation (identical to de_DE)
UPDATE AD_Element_Trl
SET    Name        = 'Kontenart',
       PrintName   = 'Kontenart',
       Description = 'Buchhalterisches Konzept, das durch diesen Eintrag überschrieben wird.',
       Help        = 'Buchhalterisches Konto-Konzept (z. B. Erlöskonto, Aufwandskonto), das durch diesen Überschreibungseintrag ersetzt wird. Leer lassen bedeutet: Die Überschreibung gilt für alle Kontenarten der Rechnung bzw. Rechnungsposition.',
       IsTranslated = 'Y',
       Updated     = TO_TIMESTAMP('2026-06-18 09:00:13', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy   = 100
WHERE  AD_Element_ID = 577539 AND AD_Language = 'de_CH';

-- en_US translation
UPDATE AD_Element_Trl
SET    Name        = 'Account Concept',
       PrintName   = 'Account Concept',
       Description = 'The accounting concept (account type) that this override entry replaces.',
       Help        = 'The accounting concept (e.g. Revenue, Expense) that this override replaces. Leave empty to apply the override to all account concepts of the invoice or invoice line.',
       IsTranslated = 'Y',
       Updated     = TO_TIMESTAMP('2026-06-18 09:00:14', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy   = 100
WHERE  AD_Element_ID = 577539 AND AD_Language = 'en_US';

-- Propagate element 577539 changes to all linked AD_Column_Trl / AD_Field_Trl rows
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(577539);

-- ============================================================
-- STEP 2: New AD_Element 585015 for "Konto (Überschreibung)"
--         (dedicated override label for window 541659 C_ElementValue_ID field)
-- ============================================================
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID,
     IsActive, Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, Description, Help,
     EntityType)
VALUES
    (585015 /*From ID Server*/, 0, 0,
     'Y',
     TO_TIMESTAMP('2026-06-18 09:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-18 09:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'C_ElementValue_Override_ID',
     'Konto (Überschreibung)',
     'Konto (Überschreibung)',
     'Das GL-Konto, auf das für diese Rechnung bzw. Rechnungsposition gebucht wird.',
     'Das Sachkonto, das anstelle des aus den Produktstammdaten abgeleiteten Kontos für die Buchung verwendet wird.',
     'D');

-- Seed _Trl rows for all active system languages
INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, Name, PrintName, Description, Help,
     IsTranslated, AD_Client_ID, AD_Org_ID,
     Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT
    l.AD_Language,
    585015 /*From ID Server*/,
    'Konto (Überschreibung)',
    'Konto (Überschreibung)',
    'Das GL-Konto, auf das für diese Rechnung bzw. Rechnungsposition gebucht wird.',
    'Das Sachkonto, das anstelle des aus den Produktstammdaten abgeleiteten Kontos für die Buchung verwendet wird.',
    'N',
    0, 0,
    TO_TIMESTAMP('2026-06-18 09:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-18 09:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Y'
FROM AD_Language l
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND NOT EXISTS (
      SELECT 1 FROM AD_Element_Trl tt
      WHERE tt.AD_Language = l.AD_Language
        AND tt.AD_Element_ID = 585015
  );

-- Override de_DE
UPDATE AD_Element_Trl
SET    Name        = 'Konto (Überschreibung)',
       PrintName   = 'Konto (Überschreibung)',
       Description = 'Das GL-Konto, auf das für diese Rechnung bzw. Rechnungsposition gebucht wird.',
       Help        = 'Das Sachkonto, das anstelle des aus den Produktstammdaten abgeleiteten Kontos für die Buchung verwendet wird.',
       IsTranslated = 'Y',
       Updated     = TO_TIMESTAMP('2026-06-18 09:01:12', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy   = 100
WHERE  AD_Element_ID = 585015 AND AD_Language = 'de_DE';

-- Override de_CH (identical)
UPDATE AD_Element_Trl
SET    Name        = 'Konto (Überschreibung)',
       PrintName   = 'Konto (Überschreibung)',
       Description = 'Das GL-Konto, auf das für diese Rechnung bzw. Rechnungsposition gebucht wird.',
       Help        = 'Das Sachkonto, das anstelle des aus den Produktstammdaten abgeleiteten Kontos für die Buchung verwendet wird.',
       IsTranslated = 'Y',
       Updated     = TO_TIMESTAMP('2026-06-18 09:01:13', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy   = 100
WHERE  AD_Element_ID = 585015 AND AD_Language = 'de_CH';

-- Override en_US
UPDATE AD_Element_Trl
SET    Name        = 'Account (Override)',
       PrintName   = 'Account (Override)',
       Description = 'The GL account to which this invoice or invoice line is posted.',
       Help        = 'The account that is used instead of the account derived from the product accounting setup.',
       IsTranslated = 'Y',
       Updated     = TO_TIMESTAMP('2026-06-18 09:01:14', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy   = 100
WHERE  AD_Element_ID = 585015 AND AD_Language = 'en_US';

-- ============================================================
-- STEP 3: Set AD_Field.AD_Name_ID on field 710156 (C_ElementValue_ID in window 541659)
--         to the new dedicated element 585015 → "Konto (Überschreibung)"
-- ============================================================
UPDATE AD_Field
SET    AD_Name_ID  = 585015 /*From ID Server*/,
       Updated     = TO_TIMESTAMP('2026-06-18 09:02:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy   = 100
WHERE  AD_Field_ID = 710156;

-- Seed AD_Field_Trl skeleton rows for field 710156 if missing
INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, Name, Description, Help,
     IsTranslated, AD_Client_ID, AD_Org_ID,
     Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT
    l.AD_Language,
    710156,
    f.Name, f.Description, f.Help,
    'N',
    f.AD_Client_ID, f.AD_Org_ID,
    f.Created, f.CreatedBy,
    f.Updated, f.UpdatedBy,
    'Y'
FROM AD_Language l, AD_Field f
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND f.AD_Field_ID = 710156
  AND NOT EXISTS (
      SELECT 1 FROM AD_Field_Trl tt
      WHERE tt.AD_Language = l.AD_Language
        AND tt.AD_Field_ID = 710156
  );

-- Propagate new element 585015 translations to field 710156 via AD_Name_ID path
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585015 /*From ID Server*/);

-- Rebuild element links for field 710156
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 710156;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(710156);

-- ============================================================
-- STEP 4: Add German Help text to the remaining four fields
--         via AD_Field.Help overrides (field-level, not element-level,
--         because these elements are shared and their help is acceptable
--         elsewhere — only the help in this window needs enrichment)
-- ============================================================

-- C_Invoice_ID (field 710152)
UPDATE AD_Field
SET    Help      = 'Die Rechnung, für die dieses Konto überschrieben wird.',
       Updated   = TO_TIMESTAMP('2026-06-18 09:03:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Field_ID = 710152;

-- C_InvoiceLine_ID (field 710153) — optional; empty = applies to whole invoice
UPDATE AD_Field
SET    Help      = 'Die Rechnungsposition, für die dieses Konto überschrieben wird. Leer lassen, um die Überschreibung auf alle Positionen der Rechnung anzuwenden.',
       Updated   = TO_TIMESTAMP('2026-06-18 09:03:01', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Field_ID = 710153;

-- C_AcctSchema_ID (field 710154)
UPDATE AD_Field
SET    Help      = 'Das Buchführungsschema, in dessen Kontenrahmen das überschreibende Konto gesucht wird.',
       Updated   = TO_TIMESTAMP('2026-06-18 09:03:02', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Field_ID = 710154;

-- AccountName (field 710155) — element help already set via element 577539 propagation above;
-- set field-level Help as well so it is immediately visible without the element propagation delay
UPDATE AD_Field
SET    Help      = 'Buchhalterisches Konto-Konzept (z. B. Erlöskonto, Aufwandskonto), das durch diesen Überschreibungseintrag ersetzt wird. Leer lassen bedeutet: Die Überschreibung gilt für alle Kontenarten der Rechnung bzw. Rechnungsposition.',
       Updated   = TO_TIMESTAMP('2026-06-18 09:03:03', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Field_ID = 710155;

-- Seed / refresh AD_Field_Trl for the four remaining fields (710152, 710153, 710154, 710155)
INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, Name, Description, Help,
     IsTranslated, AD_Client_ID, AD_Org_ID,
     Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT
    l.AD_Language,
    f.AD_Field_ID,
    f.Name, f.Description, f.Help,
    'N',
    f.AD_Client_ID, f.AD_Org_ID,
    f.Created, f.CreatedBy,
    f.Updated, f.UpdatedBy,
    'Y'
FROM AD_Language l
CROSS JOIN (SELECT * FROM AD_Field WHERE AD_Field_ID IN (710152, 710153, 710154, 710155)) f
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND NOT EXISTS (
      SELECT 1 FROM AD_Field_Trl tt
      WHERE tt.AD_Language = l.AD_Language
        AND tt.AD_Field_ID = f.AD_Field_ID
  );

-- Final: propagate element 577539 again (now that AD_Field_Trl rows exist for 710155)
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(577539);
-- Propagate element 585015 again (now that AD_Field_Trl row exists for 710156)
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585015 /*From ID Server*/);
