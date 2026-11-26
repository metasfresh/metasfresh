-- German labels (de_DE, de_CH) for five AD_Elements that still carried English text in their German
-- translation rows. Name and PrintName are set to the same German text, like the sibling P_*_Acct elements:
--    50070 M_Product_Category_Parent_ID  "Parent Product Category" -> "Übergeordnete Produktkategorie"
--    53729 P_Burden_Acct                 "Burden"                  -> "Materialgemeinkosten"
--          (material overhead, cost element type B "Burden (M.Overhead)"; distinct from
--           P_Overhead_Acct, which is "Gemeinkosten" in the same accounting tabs)
--    53728 P_Labor_Acct                  "Labor"                   -> "Arbeitskosten"
--      524 Processing                    "Process Now"             -> "Jetzt verarbeiten"
--   582074 P_CostClearing_Acct           "Cost Clearing Account"   -> "Kostenverrechnung"
--          (same pattern as the clearing account P_InventoryClearing_Acct "Bestandsverrechnung")
--
-- Impact (all usages share the same meaning, so the shared elements are updated in place):
--    50070: 1 column (M_Product_Category), 1 field (window "Produkt Kategorie")
--    53729 / 53728: 3 columns each (C_AcctSchema_Default, M_Product_Acct, M_Product_Category_Acct), 4 fields each
--      524: the generic "Processing" button column, 153 columns / 235 fields / 18 print format items
--   582074: 2 columns (C_AcctSchema_Default, M_CostElement_Acct), 2 fields
--   No AD_Field.AD_Name_ID, AD_Process_Para, AD_Menu, AD_Tab, AD_Window or AD_UI_Element.AD_Name_ID usages.
--
-- en_US is left unchanged. Description/Help are left unchanged (53729 and 53728 still have English
-- Description/Help; the other three elements have none).
-- update_TRL_Tables_On_AD_Element_TRL_Update copies the German text to the base AD_Element row and to the
-- dependent AD_Column / AD_Field / AD_PrintFormatItem translations.

-- 50070 M_Product_Category_Parent_ID
UPDATE AD_Element_Trl
SET Name         = 'Übergeordnete Produktkategorie',
    PrintName    = 'Übergeordnete Produktkategorie',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-07 13:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 50070
  AND AD_Language IN ('de_DE', 'de_CH')
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50070, NULL)
;

-- 53729 P_Burden_Acct
UPDATE AD_Element_Trl
SET Name         = 'Materialgemeinkosten',
    PrintName    = 'Materialgemeinkosten',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-07 13:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 53729
  AND AD_Language IN ('de_DE', 'de_CH')
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(53729, NULL)
;

-- 53728 P_Labor_Acct
UPDATE AD_Element_Trl
SET Name         = 'Arbeitskosten',
    PrintName    = 'Arbeitskosten',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-07 13:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 53728
  AND AD_Language IN ('de_DE', 'de_CH')
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(53728, NULL)
;

-- 524 Processing
UPDATE AD_Element_Trl
SET Name         = 'Jetzt verarbeiten',
    PrintName    = 'Jetzt verarbeiten',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-07 13:00:04', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 524
  AND AD_Language IN ('de_DE', 'de_CH')
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(524, NULL)
;

-- 582074 P_CostClearing_Acct
UPDATE AD_Element_Trl
SET Name         = 'Kostenverrechnung',
    PrintName    = 'Kostenverrechnung',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-07 13:00:05', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 582074
  AND AD_Language IN ('de_DE', 'de_CH')
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582074, NULL)
;
