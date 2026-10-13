-- Translate the AD_Ref_List entries of AD_Reference 542025
-- (MobileUI_UserProfile_DD_CaptionItem_Field — backs MobileUI_UserProfile_DD_CaptionItem.FieldName).
-- These caption-item options render as the labels of the mobile distribution job-detail header.
-- They were created with English base names and never translated (IsTranslated='N'), so the
-- German WebUI showed English. Set the German base Name + de_DE/de_CH/fr_CH + en_US translations.
-- German terms are harmonized with the mobile-webui frontend vocabulary
-- (misc/services/mobile-webui/mobile-webui-frontend/src/utils/translations_de.js):
--   Locator='Lagerplatz', source locator='Quell-Lagerplatz', DropToLocator='Ziellagerplatz',
--   ProductValue='Artikelnummer' — NOT the backend AD_Element terms (Lagerort/Suchschlüssel).
-- PickingInstruction (544097) was already translated and is intentionally left unchanged.
-- See https://github.com/metasfresh/me03/issues/30474

-- 1. German base Name (base language is de_DE)
UPDATE AD_Ref_List rl
SET Name = m.de_name,
    Updated = TO_TIMESTAMP('2026-06-18 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
FROM (VALUES
    ('SourceDoc',           'Quelldokument'),
    ('WarehouseFrom',       'Quell-Lager'),
    ('WarehouseTo',         'Ziellager'),
    ('PickDate',            'Kommissionierdatum'),
    ('LocatorFrom',         'Quell-Lagerplatz'),
    ('LocatorTo',           'Ziellagerplatz'),
    ('ProductGTIN',         'Produkt-GTIN'),
    ('ProductValueAndName', 'Artikelnummer und Name'),
    ('Plant',               'Produktionsstätte'),
    ('Qty',                 'Menge'),
    ('Priority',            'Priorität')
) AS m(val, de_name)
WHERE rl.AD_Reference_ID = 542025 AND rl.Value = m.val
;

-- 2. German translation rows (de_DE, de_CH)
UPDATE AD_Ref_List_Trl t
SET Name = m.de_name,
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-06-18 12:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
FROM AD_Ref_List rl, (VALUES
    ('SourceDoc',           'Quelldokument'),
    ('WarehouseFrom',       'Quell-Lager'),
    ('WarehouseTo',         'Ziellager'),
    ('PickDate',            'Kommissionierdatum'),
    ('LocatorFrom',         'Quell-Lagerplatz'),
    ('LocatorTo',           'Ziellagerplatz'),
    ('ProductGTIN',         'Produkt-GTIN'),
    ('ProductValueAndName', 'Artikelnummer und Name'),
    ('Plant',               'Produktionsstätte'),
    ('Qty',                 'Menge'),
    ('Priority',            'Priorität')
) AS m(val, de_name)
WHERE rl.AD_Reference_ID = 542025 AND rl.Value = m.val
  AND t.AD_Ref_List_ID = rl.AD_Ref_List_ID
  AND t.AD_Language IN ('de_DE', 'de_CH')
;

-- 3. English translation rows (en_US) — must be set explicitly, else en_US falls back to the German base
UPDATE AD_Ref_List_Trl t
SET Name = m.en_name,
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-06-18 12:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
FROM AD_Ref_List rl, (VALUES
    ('SourceDoc',           'Source Document'),
    ('WarehouseFrom',       'Source Warehouse'),
    ('WarehouseTo',         'Target Warehouse'),
    ('PickDate',            'Pick Date'),
    ('LocatorFrom',         'From Locator'),
    ('LocatorTo',           'Drop to locator'),
    ('ProductGTIN',         'Product GTIN'),
    ('ProductValueAndName', 'Product Value and Name'),
    ('Plant',               'Plant'),
    ('Qty',                 'Quantity'),
    ('Priority',            'Priority')
) AS m(val, en_name)
WHERE rl.AD_Reference_ID = 542025 AND rl.Value = m.val
  AND t.AD_Ref_List_ID = rl.AD_Ref_List_ID
  AND t.AD_Language = 'en_US'
;

-- 4. fr_CH falls back to the German text (metasfresh convention; matches the PickingInstruction entry)
UPDATE AD_Ref_List_Trl t
SET Name = m.de_name,
    Updated = TO_TIMESTAMP('2026-06-18 12:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
FROM AD_Ref_List rl, (VALUES
    ('SourceDoc',           'Quelldokument'),
    ('WarehouseFrom',       'Quell-Lager'),
    ('WarehouseTo',         'Ziellager'),
    ('PickDate',            'Kommissionierdatum'),
    ('LocatorFrom',         'Quell-Lagerplatz'),
    ('LocatorTo',           'Ziellagerplatz'),
    ('ProductGTIN',         'Produkt-GTIN'),
    ('ProductValueAndName', 'Artikelnummer und Name'),
    ('Plant',               'Produktionsstätte'),
    ('Qty',                 'Menge'),
    ('Priority',            'Priorität')
) AS m(val, de_name)
WHERE rl.AD_Reference_ID = 542025 AND rl.Value = m.val
  AND t.AD_Ref_List_ID = rl.AD_Ref_List_ID
  AND t.AD_Language = 'fr_CH'
;
