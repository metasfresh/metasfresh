-- Migration: Document MobileUI_UserProfile_Picking.BestBeforeDate field
--
-- Problem:
--   AD_Element 577375 (ColumnName=BestBeforeDate) is shared across 2 columns and 1 process parameter
--   with inconsistent meanings:
--     - EDI_Desadv_Pack_Item.BestBeforeDate:              actual best-before date value
--     - M_ShipmentSchedule_Set_BestBeforeDate (process):  actual date input parameter
--     - MobileUI_UserProfile_Picking.BestBeforeDate:      YES/NO flag (controls UI behavior)
--
-- Solution:
--   Create a NEW AD_Element (ID=584560) with a descriptive name for the boolean flag.
--   Link it to the field via AD_Field.AD_Name_ID=584560 (AD_Field_ID=756213).
--   The original AD_Element 577375 and all other usages remain unchanged.
--
-- =====================================================================================
-- AFFECTED RECORDS
-- =====================================================================================
--
-- 1) NEW AD_Element (ID=584560)
-- -------------------------------------------------------
--   Lang  | Name (old -> new)                   | Description (old -> new)
--   ------+-------------------------------------+----------------------------------------------------------------------
--   de_DE | (new) MHD erfassen                   | (new) Wenn aktiviert, wird beim Kommissionieren im MobileUI-Dialog
--         |                                      |       ein Eingabefeld fuer das Mindesthaltbarkeitsdatum (MHD) angezeigt.
--   de_CH | (new) MHD erfassen                   | (new) Wenn aktiviert, wird beim Kommissionieren im MobileUI-Dialog
--         |                                      |       ein Eingabefeld fuer das Mindesthaltbarkeitsdatum (MHD) angezeigt.
--   en_US | (new) Capture Best Before Date       | (new) If enabled, a Best Before Date input field is displayed in the
--         |                                      |       MobileUI picking read-qty dialog.
--
-- 2) AD_Field (ID=756213) - MobileUI_UserProfile_Picking.BestBeforeDate
--    Window: Mobile UI Kommissionierprofil (ID=541743)
--    Tab:    Mobile UI Kommissionierprofil (ID=547258)
--    Column: MobileUI_UserProfile_Picking.BestBeforeDate (AD_Column_ID=591593)
-- -------------------------------------------------------
--   Field              | Old                  | New
--   -------------------+----------------------+----------------------
--   AD_Name_ID         | (null)               | 584560
--
--   After update_TRL_Tables_On_AD_Element_TRL_Update propagation:
--
--   Lang  | Name (old -> new)                                       | Description (old -> new)
--   ------+---------------------------------------------------------+----------------------------------------------------------------------
--   de_DE | Mindesthaltbarkeitsdatum -> MHD erfassen                 | (null) -> Wenn aktiviert, wird beim Kommissionieren im MobileUI-Dialog
--         |                                                          |          ein Eingabefeld fuer das Mindesthaltbarkeitsdatum (MHD) angezeigt.
--   de_CH | Mindesthaltbarkeitsdatum -> MHD erfassen                 | (null) -> Wenn aktiviert, wird beim Kommissionieren im MobileUI-Dialog
--         |                                                          |          ein Eingabefeld fuer das Mindesthaltbarkeitsdatum (MHD) angezeigt.
--   en_US | Best before date -> Capture Best Before Date             | (null) -> If enabled, a Best Before Date input field is displayed in the
--         |                                                          |          MobileUI picking read-qty dialog.
--
-- 3) NOT AFFECTED (remain on AD_Element 577375):
--   - AD_Field 700815: EDI_Desadv_Pack_Item.BestBeforeDate (EDI Lieferavis Pack)
--   - AD_Process_Para 541637: M_ShipmentSchedule_Set_BestBeforeDate.BestBeforeDate (Mindesthaltbarkeitsdatum aendern)
--
-- =====================================================================================

-- Step 1: Create the new AD_Element (base language is de_DE, so Name/PrintName/Description are German)
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType,
                        Name, PrintName, Description, Help)
VALUES (584560, 0, 0, 'Y', TO_TIMESTAMP('2026-02-22 16:50:33.512', 'YYYY-MM-DD HH24:MI:SS.MS'), 100, TO_TIMESTAMP('2026-02-22 16:50:33.512', 'YYYY-MM-DD HH24:MI:SS.MS'), 100,
        NULL, 'de.metas.handlingunits',
        'MHD erfassen', 'MHD erfassen',
        'Wenn aktiviert, wird beim Kommissionieren im MobileUI-Dialog ein Eingabefeld für das Mindesthaltbarkeitsdatum (MHD) angezeigt.', NULL);

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
  AND t.AD_Element_ID = 584560
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- Step 3: Update AD_Element_Trl for de_DE (base language)
UPDATE AD_Element_Trl
SET Name         = 'MHD erfassen',
    PrintName    = 'MHD erfassen',
    Description  = 'Wenn aktiviert, wird beim Kommissionieren im MobileUI-Dialog ein Eingabefeld für das Mindesthaltbarkeitsdatum (MHD) angezeigt.',
    Help         = NULL,
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-02-22 16:50:34.512', 'YYYY-MM-DD HH24:MI:SS.MS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 584560
  AND AD_Language = 'de_DE';

-- Step 4: Update AD_Element_Trl for de_CH
UPDATE AD_Element_Trl
SET Name         = 'MHD erfassen',
    PrintName    = 'MHD erfassen',
    Description  = 'Wenn aktiviert, wird beim Kommissionieren im MobileUI-Dialog ein Eingabefeld für das Mindesthaltbarkeitsdatum (MHD) angezeigt.',
    Help         = NULL,
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-02-22 16:50:35.512', 'YYYY-MM-DD HH24:MI:SS.MS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 584560
  AND AD_Language = 'de_CH';

-- Step 5: Update AD_Element_Trl for en_US
UPDATE AD_Element_Trl
SET Name         = 'Capture Best Before Date',
    PrintName    = 'Capture Best Before Date',
    Description  = 'If enabled, a Best Before Date input field is displayed in the MobileUI picking read-qty dialog.',
    Help         = NULL,
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-02-22 16:50:36.512', 'YYYY-MM-DD HH24:MI:SS.MS'),
    UpdatedBy    = 100
WHERE AD_Element_ID = 584560
  AND AD_Language = 'en_US';

-- Step 6: Link the new element to AD_Field via AD_Name_ID
-- AD_Field_ID=756213 is the field for MobileUI_UserProfile_Picking.BestBeforeDate
UPDATE AD_Field
SET AD_Name_ID = 584560,
    Updated    = TO_TIMESTAMP('2026-02-22 16:50:37.512', 'YYYY-MM-DD HH24:MI:SS.MS'),
    UpdatedBy  = 100
WHERE AD_Field_ID = 756213;

-- Step 7: Propagate terminology from the new element to all dependent columns/fields/translations
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584560);
