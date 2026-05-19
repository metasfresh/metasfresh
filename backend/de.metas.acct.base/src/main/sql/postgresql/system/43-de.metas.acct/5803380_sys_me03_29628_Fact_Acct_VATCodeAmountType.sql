-- Tax Declaration Iter4: add Fact_Acct.VATCodeAmountType column.
--
-- Until now, the AmountType (Net/Tax) discriminator lived only on C_VAT_Code. The
-- per-leg matcher knows the AmountType at posting time (filtered C_VAT_Code lookup
-- uses it), but then discards it after extracting only the VATCode string. This
-- breaks under "Strategy B" coding (same VATCode for Net+Tax legs of one UStVA
-- Kennziffer): the snapshot can't tell which leg a Fact_Acct row is.
--
-- Fix: persist VATCodeAmountType on Fact_Acct alongside VATCode. The matcher
-- already has the value (VATCodeAmountType arg in FactLine.setTaxIdAndUpdateVatCode).
--
-- Uses AD_Reference 542087 ("C_VAT_Code AmountType", N=Netto, T=Steuer) — same list
-- as C_VAT_Code.AmountType for consistency.

-- 1. AD_Element
INSERT INTO AD_Element (
    AD_Client_ID, AD_Org_ID, AD_Element_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    ColumnName, EntityType, Name, PrintName
)
VALUES (
    0, 0, 584891 /*From ID Server*/, 'Y',
    TIMESTAMP '2026-05-19 00:00:00', 100, TIMESTAMP '2026-05-19 00:00:00', 100,
    'VATCodeAmountType', 'de.metas.acct', 'VAT Code Amount Type', 'VAT Code Amount Type'
);
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help,
    IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, e.AD_Element_ID, e.Name, e.PrintName, e.Description, e.Help,
       'N', e.AD_Client_ID, e.AD_Org_ID, e.Created, e.CreatedBy, e.Updated, e.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element e
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND e.AD_Element_ID = 584891
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=e.AD_Element_ID);

-- 2. AD_Column on Fact_Acct (table 270)
INSERT INTO AD_Column (
    AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    AD_Table_ID, AD_Element_ID, ColumnName, FieldLength, IsKey, IsParent, IsMandatory,
    IsTranslated, IsIdentifier, SeqNo, IsEncrypted, IsUpdateable, IsSelectionColumn,
    AD_Reference_ID, AD_Reference_Value_ID, IsAlwaysUpdateable, IsAutocomplete, IsAllowLogging, EntityType, Version,
    PersonalDataCategory
)
VALUES (
    592565 /*From ID Server*/, 0, 0, 'Y', TIMESTAMP '2026-05-19 00:00:00', 100, TIMESTAMP '2026-05-19 00:00:00', 100,
    (SELECT AD_Table_ID FROM AD_Table WHERE TableName='Fact_Acct'),
    584891, 'VATCodeAmountType', 1, 'N', 'N', 'N',
    'N', 'N', 0, 'N', 'Y', 'N',
    17, 542087, 'N', 'N', 'Y', 'de.metas.acct', 0,
    'NP'
);
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, c.AD_Column_ID, COALESCE(etrl.Name, e.Name), 'N', c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy, 'Y'
FROM AD_Language l
CROSS JOIN AD_Column c
JOIN AD_Element e ON e.AD_Element_ID = c.AD_Element_ID
LEFT JOIN AD_Element_Trl etrl ON etrl.AD_Element_ID = e.AD_Element_ID AND etrl.AD_Language = l.AD_Language
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND c.AD_Column_ID = 592565
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID);

-- 3. Physical DDL
ALTER TABLE Fact_Acct ADD COLUMN IF NOT EXISTS VATCodeAmountType CHAR(1);
