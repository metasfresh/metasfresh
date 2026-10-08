-- Migration: Document MobileUI_UserProfile_Picking.LotNumber field
--
-- Problem:
--   AD_Element 576652 (ColumnName=LotNumber) is shared across 4 columns with inconsistent meanings:
--     - M_HU_Trace.LotNumber:                       actual lot number string
--     - EDI_Desadv_Pack_Item.LotNumber:              actual lot number string
--     - M_Securpharm_Productdata_Result.LotNumber:   actual lot number string
--     - MobileUI_UserProfile_Picking.LotNumber:      YES/NO flag (controls UI behavior)
--
-- Solution:
--   Create a NEW AD_Element (ID=584559) with a descriptive name for the boolean flag.
--   Link it to the field via AD_Field.AD_Name_ID=584559 (AD_Field_ID=756214).
--   The original AD_Element 576652 and all other usages remain unchanged.
--
-- =====================================================================================
-- AFFECTED RECORDS
-- =====================================================================================
--
-- 1) NEW AD_Element (ID=584559)
-- -------------------------------------------------------
--   Lang  | Name (old -> new)                   | Description (old -> new)
--   ------+-------------------------------------+----------------------------------------------------------------------
--   de_DE | (new) Chargennummer erfassen         | (new) Wenn aktiviert, wird beim Kommissionieren im MobileUI-Dialog
--         |                                      |       ein Eingabefeld fuer die Chargennummer (Lot-Nr.) angezeigt.
--   de_CH | (new) Chargennummer erfassen         | (new) Wenn aktiviert, wird beim Kommissionieren im MobileUI-Dialog
--         |                                      |       ein Eingabefeld fuer die Chargennummer (Lot-Nr.) angezeigt.
--   en_US | (new) Capture Lot Number             | (new) If enabled, a Lot Number input field is displayed in the
--         |                                      |       MobileUI picking read-qty dialog.
--
-- 2) AD_Field (ID=756214) - MobileUI_UserProfile_Picking.LotNumber
--    Window: Mobile UI Kommissionierprofil (ID=541743)
--    Tab:    Mobile UI Kommissionierprofil (ID=547258)
--    Column: MobileUI_UserProfile_Picking.LotNumber (AD_Column_ID=591594)
-- -------------------------------------------------------
--   Field              | Old                  | New
--   -------------------+----------------------+----------------------
--   AD_Name_ID         | (null)               | 584559
--
--   After update_TRL_Tables_On_AD_Element_TRL_Update propagation:
--
--   Lang  | Name (old -> new)                             | Description (old -> new)
--   ------+-----------------------------------------------+----------------------------------------------------------------------
--   de_DE | Chargennummer -> Chargennummer erfassen        | (null) -> Wenn aktiviert, wird beim Kommissionieren im MobileUI-Dialog
--         |                                                |          ein Eingabefeld fuer die Chargennummer (Lot-Nr.) angezeigt.
--   de_CH | Chargennummer -> Chargennummer erfassen        | (null) -> Wenn aktiviert, wird beim Kommissionieren im MobileUI-Dialog
--         |                                                |          ein Eingabefeld fuer die Chargennummer (Lot-Nr.) angezeigt.
--   en_US | Lot number -> Capture Lot Number               | (null) -> If enabled, a Lot Number input field is displayed in the
--         |                                                |          MobileUI picking read-qty dialog.
--
-- 3) NOT AFFECTED (remain on AD_Element 576652):
--   - AD_Field 713810: M_HU_Trace.LotNumber (HU Rueckverfolgung)
--   - AD_Field 700820: EDI_Desadv_Pack_Item.LotNumber (EDI Lieferavis Pack)
--   - AD_Field 580022: M_Securpharm_Productdata_Result.LotNumber (SecurPharm)
--   - AD_Process_Para 542614: M_HU_Trace_Report_Excel.LotNumber (Rueckverfolgungsbericht)
--
-- =====================================================================================

-- Step 1: Create the new AD_Element (base language is de_DE, so Name/PrintName/Description are German)
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType,
                        Name, PrintName, Description, Help)
VALUES (584559, 0, 0, 'Y', TO_TIMESTAMP('2026-02-22 16:45:15.634', 'YYYY-MM-DD HH24:MI:SS.MS'), 100, TO_TIMESTAMP('2026-02-22 16:45:15.634', 'YYYY-MM-DD HH24:MI:SS.MS'), 100,
        NULL, 'de.metas.handlingunits',
        'Chargennummer erfassen', 'Chargennummer erfassen',
        'Wenn aktiviert, wird beim Kommissionieren im MobileUI-Dialog ein Eingabefeld für die Chargennummer (Lot-Nr.) angezeigt.', NULL);

-- Step 2: Create AD_Element_Trl records for all system languages (including base)
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID,
                            CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName,
                            WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb,
                            IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID,
       t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName,
       t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 584559
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- Step 3: Update AD_Element_Trl for de_DE (base language)
UPDATE AD_Element_Trl
SET Name         = 'Chargennummer erfassen',
    PrintName    = 'Chargennummer erfassen',
    Description  = 'Wenn aktiviert, wird beim Kommissionieren im MobileUI-Dialog ein Eingabefeld für die Chargennummer (Lot-Nr.) angezeigt.',
    Help         = NULL,
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-02-22 16:45:16.634', 'YYYY-MM-DD HH24:MI:SS.MS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 584559
  AND AD_Language = 'de_DE';

-- Step 4: Update AD_Element_Trl for de_CH
UPDATE AD_Element_Trl
SET Name         = 'Chargennummer erfassen',
    PrintName    = 'Chargennummer erfassen',
    Description  = 'Wenn aktiviert, wird beim Kommissionieren im MobileUI-Dialog ein Eingabefeld für die Chargennummer (Lot-Nr.) angezeigt.',
    Help         = NULL,
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-02-22 16:45:17.634', 'YYYY-MM-DD HH24:MI:SS.MS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 584559
  AND AD_Language = 'de_CH';

-- Step 5: Update AD_Element_Trl for en_US
UPDATE AD_Element_Trl
SET Name         = 'Capture Lot Number',
    PrintName    = 'Capture Lot Number',
    Description  = 'If enabled, a Lot Number input field is displayed in the MobileUI picking read-qty dialog.',
    Help         = NULL,
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-02-22 16:45:18.634', 'YYYY-MM-DD HH24:MI:SS.MS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 584559
  AND AD_Language = 'en_US';

-- Step 6: Link the new element to AD_Field via AD_Name_ID
-- AD_Field_ID=756214 is the field for MobileUI_UserProfile_Picking.LotNumber
UPDATE AD_Field
SET AD_Name_ID = 584559,
    Updated    = TO_TIMESTAMP('2026-02-22 16:45:19.634', 'YYYY-MM-DD HH24:MI:SS.MS'),
    UpdatedBy  = 100
WHERE AD_Field_ID = 756214;

-- Step 7: Propagate terminology from the new element to all dependent columns/fields/translations
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584559);
