-- Name: Einstellungen für Vorfinanzierungen
-- Action Type: W
-- Window: Einstellungen für Vorfinanzierungen(541562,D)
-- 2023-09-18T18:24:46.362Z
DELETE
FROM AD_Menu_Trl
WHERE AD_Menu_ID = 541974
;

-- 2023-09-18T18:24:46.365Z
DELETE
FROM AD_Menu
WHERE AD_Menu_ID = 541974
;

-- 2023-09-18T18:24:46.369Z
DELETE
FROM AD_TreeNodeMM n
WHERE Node_ID = 541974
  AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID = n.AD_Tree_ID AND t.AD_Table_ID = 116)
;

-- UI Element: Vertragsbedingungen(540113,de.metas.contracts) -> Bedingungen(540331,de.metas.contracts) -> main -> 10 -> default.Einstellungen für Vorfinanzierungen
-- Column: C_Flatrate_Conditions.C_Interim_Invoice_Settings_ID
-- 2023-09-18T18:29:54.926Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 610553
;

-- 2023-09-18T18:32:31.652Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702171
;

-- Field: Vertragsbedingungen(540113,de.metas.contracts) -> Bedingungen(540331,de.metas.contracts) -> Einstellungen für Vorfinanzierungen
-- Column: C_Flatrate_Conditions.C_Interim_Invoice_Settings_ID
-- 2023-09-18T18:32:31.655Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702171
;

-- 2023-09-18T18:32:31.659Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702171
;

-- Column: C_Flatrate_Conditions.C_Interim_Invoice_Settings_ID
-- 2023-09-18T18:33:41.755Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583805
;

-- 2023-09-18T18:33:41.758Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583805
;

ALTER TABLE C_Flatrate_Conditions
    DROP COLUMN IF EXISTS C_Interim_Invoice_Settings_ID
;

-- Tab: Einstellungen für Vorfinanzierungen(541562,D) -> Einstellungen für Vorfinanzierungen(546462,D)
-- UI Section: main
-- UI Section: Einstellungen für Vorfinanzierungen(541562,D) -> Einstellungen für Vorfinanzierungen(546462,D) -> main
-- UI Column: 20
-- UI Column: Einstellungen für Vorfinanzierungen(541562,D) -> Einstellungen für Vorfinanzierungen(546462,D) -> main -> 20
-- UI Element Group: org
-- UI Element: Einstellungen für Vorfinanzierungen(541562,D) -> Einstellungen für Vorfinanzierungen(546462,D) -> main -> 20 -> org.Mandant
-- Column: C_Interim_Invoice_Settings.AD_Client_ID
-- 2023-09-18T18:36:27.250Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 610517
;

-- UI Element: Einstellungen für Vorfinanzierungen(541562,D) -> Einstellungen für Vorfinanzierungen(546462,D) -> main -> 20 -> org.Sektion
-- Column: C_Interim_Invoice_Settings.AD_Org_ID
-- 2023-09-18T18:36:27.258Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 610518
;

-- 2023-09-18T18:36:27.261Z
DELETE
FROM AD_UI_ElementGroup
WHERE AD_UI_ElementGroup_ID = 549555
;

-- UI Column: Einstellungen für Vorfinanzierungen(541562,D) -> Einstellungen für Vorfinanzierungen(546462,D) -> main -> 20
-- UI Element Group: flags
-- UI Element: Einstellungen für Vorfinanzierungen(541562,D) -> Einstellungen für Vorfinanzierungen(546462,D) -> main -> 20 -> flags.Aktiv
-- Column: C_Interim_Invoice_Settings.IsActive
-- 2023-09-18T18:36:27.270Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 610519
;

-- 2023-09-18T18:36:27.273Z
DELETE
FROM AD_UI_ElementGroup
WHERE AD_UI_ElementGroup_ID = 549554
;

-- 2023-09-18T18:36:27.277Z
DELETE
FROM AD_UI_Column
WHERE AD_UI_Column_ID = 546188
;

-- UI Section: Einstellungen für Vorfinanzierungen(541562,D) -> Einstellungen für Vorfinanzierungen(546462,D) -> main
-- UI Column: 10
-- UI Column: Einstellungen für Vorfinanzierungen(541562,D) -> Einstellungen für Vorfinanzierungen(546462,D) -> main -> 10
-- UI Element Group: default
-- UI Element: Einstellungen für Vorfinanzierungen(541562,D) -> Einstellungen für Vorfinanzierungen(546462,D) -> main -> 10 -> default.Produkt für Einbehalt
-- Column: C_Interim_Invoice_Settings.M_Product_Witholding_ID
-- 2023-09-18T18:36:27.289Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 610521
;

-- UI Element: Einstellungen für Vorfinanzierungen(541562,D) -> Einstellungen für Vorfinanzierungen(546462,D) -> main -> 10 -> default.Harvesting Calendar
-- Column: C_Interim_Invoice_Settings.C_Harvesting_Calendar_ID
-- 2023-09-18T18:36:27.296Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 610520
;

-- 2023-09-18T18:36:27.299Z
DELETE
FROM AD_UI_ElementGroup
WHERE AD_UI_ElementGroup_ID = 549553
;

-- 2023-09-18T18:36:27.302Z
DELETE
FROM AD_UI_Column
WHERE AD_UI_Column_ID = 546187
;

-- 2023-09-18T18:36:27.303Z
DELETE
FROM AD_UI_Section_Trl
WHERE AD_UI_Section_ID = 545101
;

-- 2023-09-18T18:36:27.306Z
DELETE
FROM AD_UI_Section
WHERE AD_UI_Section_ID = 545101
;

-- Tab: Einstellungen für Vorfinanzierungen(541562,D) -> Einstellungen für Vorfinanzierungen
-- Table: C_Interim_Invoice_Settings
-- 2023-09-18T18:36:36.157Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702126
;

-- Field: Einstellungen für Vorfinanzierungen(541562,D) -> Einstellungen für Vorfinanzierungen(546462,D) -> Mandant
-- Column: C_Interim_Invoice_Settings.AD_Client_ID
-- 2023-09-18T18:36:36.161Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702126
;

-- 2023-09-18T18:36:36.167Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702126
;

-- 2023-09-18T18:36:36.173Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702127
;

-- Field: Einstellungen für Vorfinanzierungen(541562,D) -> Einstellungen für Vorfinanzierungen(546462,D) -> Organisation
-- Column: C_Interim_Invoice_Settings.AD_Org_ID
-- 2023-09-18T18:36:36.175Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702127
;

-- 2023-09-18T18:36:36.178Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702127
;

-- 2023-09-18T18:36:36.183Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702128
;

-- Field: Einstellungen für Vorfinanzierungen(541562,D) -> Einstellungen für Vorfinanzierungen(546462,D) -> Aktiv
-- Column: C_Interim_Invoice_Settings.IsActive
-- 2023-09-18T18:36:36.184Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702128
;

-- 2023-09-18T18:36:36.187Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702128
;

-- 2023-09-18T18:36:36.193Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702130
;

-- Field: Einstellungen für Vorfinanzierungen(541562,D) -> Einstellungen für Vorfinanzierungen(546462,D) -> Erntekalender
-- Column: C_Interim_Invoice_Settings.C_Harvesting_Calendar_ID
-- 2023-09-18T18:36:36.194Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702130
;

-- 2023-09-18T18:36:36.197Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702130
;

-- 2023-09-18T18:36:36.202Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702131
;

-- Field: Einstellungen für Vorfinanzierungen(541562,D) -> Einstellungen für Vorfinanzierungen(546462,D) -> Produkt für Einbehalt
-- Column: C_Interim_Invoice_Settings.M_Product_Witholding_ID
-- 2023-09-18T18:36:36.204Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702131
;

-- 2023-09-18T18:36:36.208Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702131
;

-- 2023-09-18T18:36:36.211Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702129
;

-- Field: Einstellungen für Vorfinanzierungen(541562,D) -> Einstellungen für Vorfinanzierungen(546462,D) -> Einstellungen für Vorfinanzierungen
-- Column: C_Interim_Invoice_Settings.C_Interim_Invoice_Settings_ID
-- 2023-09-18T18:36:36.213Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702129
;

-- 2023-09-18T18:36:36.216Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702129
;

-- 2023-09-18T18:36:36.218Z
DELETE
FROM AD_Tab_Trl
WHERE AD_Tab_ID = 546462
;

-- 2023-09-18T18:36:36.221Z
DELETE
FROM AD_Tab
WHERE AD_Tab_ID = 546462
;

-- Window: Einstellungen für Vorfinanzierungen, InternalName=C_Interim_Invoice_Settings
-- 2023-09-18T18:37:12.595Z
DELETE
FROM AD_Element_Link
WHERE AD_Window_ID = 541562
;

-- 2023-09-18T18:37:12.596Z
DELETE
FROM AD_Window_Trl
WHERE AD_Window_ID = 541562
;

-- 2023-09-18T18:37:12.600Z
DELETE
FROM AD_Window
WHERE AD_Window_ID = 541562
;

-- Column: C_Interim_Invoice_Settings.AD_Client_ID
-- 2023-09-18T18:37:56.458Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583712
;

-- 2023-09-18T18:37:56.461Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583712
;

-- Column: C_Interim_Invoice_Settings.AD_Org_ID
-- 2023-09-18T18:37:57.036Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583713
;

-- 2023-09-18T18:37:57.039Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583713
;

-- Column: C_Interim_Invoice_Settings.C_Harvesting_Calendar_ID
-- 2023-09-18T18:37:57.676Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583722
;

-- 2023-09-18T18:37:57.678Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583722
;

-- Column: C_Interim_Invoice_Settings.C_Interim_Invoice_Settings_ID
-- 2023-09-18T18:37:58.310Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583719
;

-- 2023-09-18T18:37:58.312Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583719
;

-- Column: C_Interim_Invoice_Settings.Created
-- 2023-09-18T18:37:58.916Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583714
;

-- 2023-09-18T18:37:58.918Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583714
;

-- Column: C_Interim_Invoice_Settings.CreatedBy
-- 2023-09-18T18:37:59.479Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583715
;

-- 2023-09-18T18:37:59.482Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583715
;

-- Column: C_Interim_Invoice_Settings.IsActive
-- 2023-09-18T18:38:00.099Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583716
;

-- 2023-09-18T18:38:00.102Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583716
;

-- Column: C_Interim_Invoice_Settings.M_Product_Witholding_ID
-- 2023-09-18T18:38:00.684Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583723
;

-- 2023-09-18T18:38:00.687Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583723
;

-- Column: C_Interim_Invoice_Settings.Updated
-- 2023-09-18T18:38:01.306Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583717
;

-- 2023-09-18T18:38:01.309Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583717
;

-- Column: C_Interim_Invoice_Settings.UpdatedBy
-- 2023-09-18T18:38:01.895Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583718
;

-- 2023-09-18T18:38:01.897Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583718
;

-- Table: C_Interim_Invoice_Settings
-- 2023-09-18T18:38:09.762Z
DELETE
FROM AD_Table_Trl
WHERE AD_Table_ID = 542188
;

-- 2023-09-18T18:38:09.765Z
DELETE
FROM AD_Table
WHERE AD_Table_ID = 542188
;

DROP TABLE IF EXISTS C_Interim_Invoice_Settings
;

