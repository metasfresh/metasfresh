-- Intrastat preview window -- AD_Menu + tree placement
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
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Intrastat Vorschau', 'W', 542179, 'D',
    'N', 'Y', 'N',
    585150, 'Intrastat_Preview');

-- -----------------------------------------------------------------------------
-- 2. en_US translation
-- -----------------------------------------------------------------------------
INSERT INTO AD_Menu_Trl (AD_Menu_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, IsTranslated)
VALUES (542353 /*From ID Server*/, 'en_US', 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    'Intrastat Preview', 'Y');

-- -----------------------------------------------------------------------------
-- 3. Tree placement -- under Finanzen (Parent_ID=1000015), SeqNo=42 (append)
-- -----------------------------------------------------------------------------
-- Idempotent: ON CONFLICT (AD_Tree_ID, Node_ID) DO NOTHING mirrors 5794280 pattern.
INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
SELECT 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-07-30 12:00','YYYY-MM-DD HH24:MI'), 100,
    t.AD_Tree_ID, 542353 /*From ID Server*/, 1000015, 42
FROM AD_Tree t
WHERE t.AD_Client_ID = 0
  AND t.IsActive = 'Y'
  AND t.IsAllNodes = 'Y'
  AND t.AD_Table_ID = 116
ON CONFLICT (AD_Tree_ID, Node_ID) DO NOTHING;
