-- Business partner window (123):
-- 1) Remove the old, empty tab 548980 "EDI-Konfiguration" including its layout. Its fields were
--    deleted when the EDI columns moved from C_BPartner into C_BPartner_EDI_Setting; the tab is
--    replaced by tab 549287 "EDI-Einstellungen". Child rows are deleted before the parent; all
--    deletes are WHERE-based, so the script can be re-run.
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID IN (SELECT AD_UI_Section_ID FROM AD_UI_Section WHERE AD_Tab_ID = 548980);
DELETE FROM AD_UI_Element WHERE AD_UI_ElementGroup_ID IN (
    SELECT g.AD_UI_ElementGroup_ID FROM AD_UI_ElementGroup g
    JOIN AD_UI_Column c ON c.AD_UI_Column_ID = g.AD_UI_Column_ID
    JOIN AD_UI_Section s ON s.AD_UI_Section_ID = c.AD_UI_Section_ID
    WHERE s.AD_Tab_ID = 548980);
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_Column_ID IN (
    SELECT c.AD_UI_Column_ID FROM AD_UI_Column c
    JOIN AD_UI_Section s ON s.AD_UI_Section_ID = c.AD_UI_Section_ID
    WHERE s.AD_Tab_ID = 548980);
DELETE FROM AD_UI_Column WHERE AD_UI_Section_ID IN (SELECT AD_UI_Section_ID FROM AD_UI_Section WHERE AD_Tab_ID = 548980);
DELETE FROM AD_UI_Section WHERE AD_Tab_ID = 548980;
DELETE FROM AD_Element_Link WHERE AD_Tab_ID = 548980;
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 548980;
DELETE FROM AD_Tab WHERE AD_Tab_ID = 548980;

-- 2) Tab 549287 "EDI-Einstellungen" was linked to its parent through column 563682, which is
--    C_Customer_Retention.C_BPartner_ID. Link it through its own C_BPartner_EDI_Setting.C_BPartner_ID
--    (592678) and the parent tab's C_BPartner.C_BPartner_ID (2893), as tab 540877 "Partner Merkmale" does.
UPDATE AD_Tab
SET AD_Column_ID = 592678, Parent_Column_ID = 2893,
    Updated = TO_TIMESTAMP('2026-10-09 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tab_ID = 549287;

-- 3) Same wrong Parent_Column_ID 563682 on tab 547544 "Abteilung" (C_BPartner_Department.C_BPartner_ID
--    588240 is already its AD_Column_ID).
UPDATE AD_Tab
SET AD_Column_ID = 588240, Parent_Column_ID = 2893,
    Updated = TO_TIMESTAMP('2026-10-09 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tab_ID = 547544;
