-- Rename shared VAT Code AD_Elements to DATEV/ERP-style German base names
-- The German base Name column was holding English. metasfresh convention: DE base, EN via _Trl.
-- 3 elements affected (used across 50+ windows/columns):
--   542958 C_VAT_Code_ID      -> USt.-Code
--   542959 VATCode            -> USt.-Code
--   584891 VATCodeAmountType  -> USt.-Code Betragsart
-- Cascade via update_TRL_Tables_On_AD_Element_TRL_Update(<id>, NULL) propagates to
-- AD_Column, AD_Field, AD_Process_Para, AD_PrintFormatItem, AD_Tab, AD_Window, AD_Menu.

-- AD_Element 542958 — C_VAT_Code_ID
UPDATE AD_Element
SET Name = 'USt.-Code',
    PrintName = 'USt.-Code',
    Updated = TIMESTAMP '2026-05-22 13:00:01',
    UpdatedBy = 100
WHERE AD_Element_ID = 542958;

UPDATE AD_Element_Trl
SET Name = 'VAT Code',
    PrintName = 'VAT Code',
    IsTranslated = 'Y',
    Updated = TIMESTAMP '2026-05-22 13:00:02',
    UpdatedBy = 100
WHERE AD_Element_ID = 542958 AND AD_Language = 'en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542958, NULL);

-- AD_Element 542959 — VATCode
UPDATE AD_Element
SET Name = 'USt.-Code',
    PrintName = 'USt.-Code',
    Updated = TIMESTAMP '2026-05-22 13:00:03',
    UpdatedBy = 100
WHERE AD_Element_ID = 542959;

UPDATE AD_Element_Trl
SET Name = 'VAT Code',
    PrintName = 'VAT Code',
    IsTranslated = 'Y',
    Updated = TIMESTAMP '2026-05-22 13:00:04',
    UpdatedBy = 100
WHERE AD_Element_ID = 542959 AND AD_Language = 'en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542959, NULL);

-- AD_Element 584891 — VATCodeAmountType
UPDATE AD_Element
SET Name = 'USt.-Code Betragsart',
    PrintName = 'USt.-Code Betragsart',
    Updated = TIMESTAMP '2026-05-22 13:00:05',
    UpdatedBy = 100
WHERE AD_Element_ID = 584891;

UPDATE AD_Element_Trl
SET Name = 'VAT Code Amount Type',
    PrintName = 'VAT Code Amount Type',
    IsTranslated = 'Y',
    Updated = TIMESTAMP '2026-05-22 13:00:06',
    UpdatedBy = 100
WHERE AD_Element_ID = 584891 AND AD_Language = 'en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584891, NULL);
