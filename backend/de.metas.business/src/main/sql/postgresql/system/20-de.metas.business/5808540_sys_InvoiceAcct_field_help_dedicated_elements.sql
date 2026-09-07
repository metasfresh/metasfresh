-- F01010.4 Invoice Accounting Overrides — durable per-field help via dedicated AD_Elements
--
-- Supersedes the field-level help approach of 5808490 (STEP 4) + 5808530 (AD_Field_Trl timestamp-freeze).
-- Per the metasfresh AD convention: a per-field Name/Help that differs from the shared element MUST be
-- backed by a DEDICATED AD_Element linked via AD_Field.AD_Name_ID (the same mechanism used for field
-- 710156 / element 585015). Setting Help directly on AD_Field / AD_Field_Trl is unsafe — the
-- update_TRL_Tables_On_AD_Element_TRL_Update sync overwrites it whenever the shared element is next
-- touched. This script gives fields 710152/710153/710154 their own elements so the help propagates
-- durably through the standard mechanism.
--
-- Window 541659 (Invoice Accounting Overrides). Fields:
--   710152 C_Invoice_ID      (shared element 1008) -> dedicated element 585016 "Rechnung"
--   710153 C_InvoiceLine_ID  (shared element 1076) -> dedicated element 585017 "Rechnungsposition"
--   710154 C_AcctSchema_ID   (shared element 181)  -> dedicated element 585018 "Buchführungs-Schema"
-- Names are preserved exactly; only the Help becomes override-context-specific. The base AD_Field.Help
-- set by 5808490 is cleared so the element-propagated help applies cleanly (as 5808500 did for 710156).
-- Field 710155 (AccountName) and 710156 (C_ElementValue_ID) are already correct — not touched.

-- ============================================================
-- Field 710152 (C_Invoice_ID) -> dedicated element 585016
-- ============================================================
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID,
     IsActive, Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, Description, Help,
     EntityType)
VALUES
    (585016 /*From ID Server*/, 0, 0,
     'Y',
     TO_TIMESTAMP('2026-06-18 09:20:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-18 09:20:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'C_Invoice_ID_InvoiceAcct',
     'Rechnung',
     'Rechnung',
     'Die Rechnung, für die dieses Konto überschrieben wird.',
     'Die Rechnung, für die dieses Konto überschrieben wird.',
     'D');

INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, Name, PrintName, Description, Help,
     IsTranslated, AD_Client_ID, AD_Org_ID,
     Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT
    l.AD_Language, 585016 /*From ID Server*/,
    'Rechnung', 'Rechnung',
    'Die Rechnung, für die dieses Konto überschrieben wird.',
    'Die Rechnung, für die dieses Konto überschrieben wird.',
    'N', 0, 0,
    TO_TIMESTAMP('2026-06-18 09:20:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-18 09:20:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y'
FROM AD_Language l
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = 585016);

UPDATE AD_Element_Trl
SET    Name = 'Rechnung', PrintName = 'Rechnung',
       Description = 'Die Rechnung, für die dieses Konto überschrieben wird.',
       Help = 'Die Rechnung, für die dieses Konto überschrieben wird.',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-18 09:20:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 585016 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Element_Trl
SET    Name = 'Invoice', PrintName = 'Invoice',
       Description = 'The invoice for which this account is overridden.',
       Help = 'The invoice for which this account is overridden.',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-18 09:20:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 585016 AND AD_Language = 'en_US';

UPDATE AD_Field
SET    AD_Name_ID = 585016 /*From ID Server*/, Help = NULL,
       Updated = TO_TIMESTAMP('2026-06-18 09:21:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Field_ID = 710152;

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, Name, Description, Help,
     IsTranslated, AD_Client_ID, AD_Org_ID,
     Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 710152, f.Name, f.Description, f.Help, 'N',
       f.AD_Client_ID, f.AD_Org_ID, f.Created, f.CreatedBy, f.Updated, f.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field f
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 710152
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 710152);

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585016 /*From ID Server*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 710152;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(710152);

-- ============================================================
-- Field 710153 (C_InvoiceLine_ID) -> dedicated element 585017
-- ============================================================
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID,
     IsActive, Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, Description, Help,
     EntityType)
VALUES
    (585017 /*From ID Server*/, 0, 0,
     'Y',
     TO_TIMESTAMP('2026-06-18 09:22:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-18 09:22:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'C_InvoiceLine_ID_InvoiceAcct',
     'Rechnungsposition',
     'Rechnungsposition',
     'Die Rechnungsposition, für die dieses Konto überschrieben wird. Leer lassen, um die Überschreibung auf alle Positionen der Rechnung anzuwenden.',
     'Die Rechnungsposition, für die dieses Konto überschrieben wird. Leer lassen, um die Überschreibung auf alle Positionen der Rechnung anzuwenden.',
     'D');

INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, Name, PrintName, Description, Help,
     IsTranslated, AD_Client_ID, AD_Org_ID,
     Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT
    l.AD_Language, 585017 /*From ID Server*/,
    'Rechnungsposition', 'Rechnungsposition',
    'Die Rechnungsposition, für die dieses Konto überschrieben wird. Leer lassen, um die Überschreibung auf alle Positionen der Rechnung anzuwenden.',
    'Die Rechnungsposition, für die dieses Konto überschrieben wird. Leer lassen, um die Überschreibung auf alle Positionen der Rechnung anzuwenden.',
    'N', 0, 0,
    TO_TIMESTAMP('2026-06-18 09:22:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-18 09:22:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y'
FROM AD_Language l
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = 585017);

UPDATE AD_Element_Trl
SET    Name = 'Rechnungsposition', PrintName = 'Rechnungsposition',
       Description = 'Die Rechnungsposition, für die dieses Konto überschrieben wird. Leer lassen, um die Überschreibung auf alle Positionen der Rechnung anzuwenden.',
       Help = 'Die Rechnungsposition, für die dieses Konto überschrieben wird. Leer lassen, um die Überschreibung auf alle Positionen der Rechnung anzuwenden.',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-18 09:22:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 585017 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Element_Trl
SET    Name = 'Invoice Line', PrintName = 'Invoice Line',
       Description = 'The invoice line for which this account is overridden. Leave empty to apply the override to all lines of the invoice.',
       Help = 'The invoice line for which this account is overridden. Leave empty to apply the override to all lines of the invoice.',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-18 09:22:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 585017 AND AD_Language = 'en_US';

UPDATE AD_Field
SET    AD_Name_ID = 585017 /*From ID Server*/, Help = NULL,
       Updated = TO_TIMESTAMP('2026-06-18 09:23:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Field_ID = 710153;

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, Name, Description, Help,
     IsTranslated, AD_Client_ID, AD_Org_ID,
     Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 710153, f.Name, f.Description, f.Help, 'N',
       f.AD_Client_ID, f.AD_Org_ID, f.Created, f.CreatedBy, f.Updated, f.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field f
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 710153
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 710153);

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585017 /*From ID Server*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 710153;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(710153);

-- ============================================================
-- Field 710154 (C_AcctSchema_ID) -> dedicated element 585018
-- ============================================================
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID,
     IsActive, Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, Description, Help,
     EntityType)
VALUES
    (585018 /*From ID Server*/, 0, 0,
     'Y',
     TO_TIMESTAMP('2026-06-18 09:24:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-18 09:24:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'C_AcctSchema_ID_InvoiceAcct',
     'Buchführungs-Schema',
     'Buchführungs-Schema',
     'Das Buchführungsschema, in dessen Kontenrahmen das überschreibende Konto gesucht wird.',
     'Das Buchführungsschema, in dessen Kontenrahmen das überschreibende Konto gesucht wird.',
     'D');

INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, Name, PrintName, Description, Help,
     IsTranslated, AD_Client_ID, AD_Org_ID,
     Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT
    l.AD_Language, 585018 /*From ID Server*/,
    'Buchführungs-Schema', 'Buchführungs-Schema',
    'Das Buchführungsschema, in dessen Kontenrahmen das überschreibende Konto gesucht wird.',
    'Das Buchführungsschema, in dessen Kontenrahmen das überschreibende Konto gesucht wird.',
    'N', 0, 0,
    TO_TIMESTAMP('2026-06-18 09:24:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-06-18 09:24:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y'
FROM AD_Language l
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = 585018);

UPDATE AD_Element_Trl
SET    Name = 'Buchführungs-Schema', PrintName = 'Buchführungs-Schema',
       Description = 'Das Buchführungsschema, in dessen Kontenrahmen das überschreibende Konto gesucht wird.',
       Help = 'Das Buchführungsschema, in dessen Kontenrahmen das überschreibende Konto gesucht wird.',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-18 09:24:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 585018 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Element_Trl
SET    Name = 'Accounting Schema', PrintName = 'Accounting Schema',
       Description = 'The accounting schema in whose chart of accounts the overriding account is looked up.',
       Help = 'The accounting schema in whose chart of accounts the overriding account is looked up.',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-18 09:24:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 585018 AND AD_Language = 'en_US';

UPDATE AD_Field
SET    AD_Name_ID = 585018 /*From ID Server*/, Help = NULL,
       Updated = TO_TIMESTAMP('2026-06-18 09:25:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Field_ID = 710154;

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, Name, Description, Help,
     IsTranslated, AD_Client_ID, AD_Org_ID,
     Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 710154, f.Name, f.Description, f.Help, 'N',
       f.AD_Client_ID, f.AD_Org_ID, f.Created, f.CreatedBy, f.Updated, f.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field f
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 710154
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 710154);

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585018 /*From ID Server*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 710154;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(710154);
