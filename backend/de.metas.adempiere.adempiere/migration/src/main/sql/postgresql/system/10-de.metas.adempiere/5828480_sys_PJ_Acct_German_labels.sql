-- German labels (de_DE, de_CH) for the two project accounts, Name = PrintName:
--    1414 PJ_WIP_Acct    "Unfertige Leistungen" -> "Projekt Unfertige Leistungen"
--         (it shared "Unfertige Leistungen" with AD_Element 53721 P_WIP_Acct, product work in process,
--          which keeps that name; both are shown in tab "Standardwerte" of window "Buchführungs-Schema")
--    1046 PJ_Asset_Acct  "Project Asset"        -> "Projekt Anlagevermögen"
--         (was still English; capital projects post to fixed assets)
-- The "Projekt <account>" naming follows the "Produkt <account>" naming of the product accounts in the
-- same tab (e.g. P_Expense_Acct "Produkt Aufwand", P_Revenue_Acct "Produkt Ertrag").
--
-- Impact (each element): 2 columns (C_AcctSchema_Default, C_Project_Acct), 3 fields (tab "Standardwerte"
-- of "Buchführungs-Schema", tab "Buchführung" of "Projekt - LEGACY" and of "Projekt (Verkauf)").
-- No print format items, no AD_Field.AD_Name_ID, AD_Process_Para, AD_Menu, AD_Tab, AD_Window or
-- AD_UI_Element.AD_Name_ID usages.
--
-- en_US, Description and Help are left unchanged.

-- 1414 PJ_WIP_Acct
UPDATE AD_Element_Trl
SET Name         = 'Projekt Unfertige Leistungen',
    PrintName    = 'Projekt Unfertige Leistungen',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-07 14:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 1414
  AND AD_Language IN ('de_DE', 'de_CH')
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(1414, NULL)
;

-- 1046 PJ_Asset_Acct
UPDATE AD_Element_Trl
SET Name         = 'Projekt Anlagevermögen',
    PrintName    = 'Projekt Anlagevermögen',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-10-07 14:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 1046
  AND AD_Language IN ('de_DE', 'de_CH')
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(1046, NULL)
;
