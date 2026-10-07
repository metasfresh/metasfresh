-- Distinct German label (de_DE, de_CH) for AD_Element 1414 PJ_WIP_Acct (project work in progress).
--
-- It shared the German name "Unfertige Leistungen" with AD_Element 53721 P_WIP_Acct (product work in
-- process); both are shown in window "Buchführungs-Schema", tab "Standardwerte" (C_AcctSchema_Default).
-- P_WIP_Acct keeps "Unfertige Leistungen"; PJ_WIP_Acct becomes "Projekt Unfertige Leistungen",
-- following the "Produkt <account>" naming of the product accounts in the same tab
-- (e.g. P_Expense_Acct "Produkt Aufwand", P_Revenue_Acct "Produkt Ertrag").
--
-- Impact: 2 columns (C_AcctSchema_Default, C_Project_Acct), 3 fields (tab "Standardwerte" of
-- "Buchführungs-Schema", tab "Buchführung" of "Projekt - LEGACY" and of "Projekt (Verkauf)").
-- No print format items, no AD_Field.AD_Name_ID, AD_Process_Para, AD_Menu, AD_Tab or AD_Window usages.
--
-- en_US, Description and Help are left unchanged.

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
