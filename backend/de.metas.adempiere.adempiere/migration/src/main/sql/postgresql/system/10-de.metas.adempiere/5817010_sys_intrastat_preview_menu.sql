-- Intrastat preview window: AD_Menu + tree placement.
--
-- Adds the top-level menu entry that opens the new Intrastat preview window
-- (AD_Window_ID = 542179), and places it in the main menu tree next to the
-- existing "INTRASTAT RTIC Datei (AT)" entry under the Finanzen folder.
--
-- Allocated from central ID server:
--   AD_Menu_ID = 542353

-- -----------------------------------------------------------------------------
-- 1. Menu entry
-- -----------------------------------------------------------------------------
INSERT INTO AD_Menu (AD_Menu_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, Action, AD_Window_ID, EntityType,
    IsSummary, IsSOTrx, IsReadOnly,
    AD_Element_ID, InternalName)
VALUES (542353 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:00','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
    'Intrastat Vorschau', 'W', 542179, 'D',
    'N', 'Y', 'N',
    585150, 'Intrastat_Preview');

-- -----------------------------------------------------------------------------
-- 2. Seed AD_Menu_Trl rows for every active system language
--    (canonical Swing-export pattern; matches 5794770 §Inventory Position).
--    IsTranslated='N' -- rows are placeholders until the propagation function
--    fills them from AD_Element_Trl in step 3.
-- -----------------------------------------------------------------------------
INSERT INTO AD_Menu_Trl (AD_Language, AD_Menu_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, Description, IsTranslated,
    WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb)
SELECT l.AD_Language, t.AD_Menu_ID, t.AD_Client_ID, t.AD_Org_ID, 'Y',
    t.Created, t.CreatedBy, t.Updated, t.UpdatedBy,
    t.Name, t.Description, 'N',
    t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb
FROM AD_Language l, AD_Menu t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Menu_ID = 542353
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt
                   WHERE tt.AD_Language = l.AD_Language AND tt.AD_Menu_ID = t.AD_Menu_ID);

-- -----------------------------------------------------------------------------
-- 3. Propagate translations from AD_Element 585150 into AD_Menu_Trl.
--    Fills Name / Description / IsTranslated for every language present in
--    AD_Element_Trl (typically en_US, plus any customer-added languages).
--    Base language (de_DE) is served by AD_Menu.Name directly.
-- -----------------------------------------------------------------------------
SELECT update_menu_translation_from_ad_element(585150);

-- -----------------------------------------------------------------------------
-- 4. Tree placement: under Finanzen (Parent_ID=1000015), SeqNo=42 (append).
--    ON CONFLICT (AD_Tree_ID, Node_ID) DO NOTHING mirrors 5794280 pattern.
-- -----------------------------------------------------------------------------
INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
SELECT 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 13:00:01','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-30 13:00:01','YYYY-MM-DD HH24:MI:SS'), 100,
    t.AD_Tree_ID, 542353 /*From ID Server*/, 1000015, 42
FROM AD_Tree t
WHERE t.AD_Client_ID = 0
  AND t.IsActive = 'Y'
  AND t.IsAllNodes = 'Y'
  AND t.AD_Table_ID = 116
ON CONFLICT (AD_Tree_ID, Node_ID) DO NOTHING;
