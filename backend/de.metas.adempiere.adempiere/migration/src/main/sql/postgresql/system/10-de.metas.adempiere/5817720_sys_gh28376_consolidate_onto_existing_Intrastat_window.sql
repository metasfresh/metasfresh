-- Consolidation: drop the new "Intrastat Vorschau" window and attach the selection-driven
-- Excel export (AD_Process 585647) to the EXISTING Intrastat window (AD_Window 542107,
-- AD_Table 542587 / Intrastat_Report_Detail_V) instead.
--
-- Rationale: the existing debug window already exposes per-line Intrastat data with
-- M_Product_ID / partner / invoice zoom-into. Adding a second window with a coarser
-- (per-product) aggregation duplicated most of that infrastructure without adding
-- meaningful capability. This migration:
--   1. Attaches AD_Process 585647 to AD_Table 542587 (Intrastat_Report_Detail_V)
--   2. Deactivates the new AD_Window 542179 (Intrastat Vorschau) + its AD_Menu 542353
--      + AD_Tab 549359 + all AD_Field / AD_UI_Element rows
--   3. Removes the AD_Table_Process wiring 541656 (585647→542632 no longer needed)
--   4. Deactivates AD_Table 542632 (Intrastat_Preview_V) and all its AD_Columns
--   5. Restores AD_Menu 542307 to its original "Intrastat" caption (no disambiguation
--      needed since the new window is gone)
-- The physical view Intrastat_Preview_V is dropped by the companion migration in
-- 70-de.metas.fresh (same numeric prefix).

-- =====================================================================
-- 1. Attach AD_Process 585647 to AD_Table 542587 (Intrastat_Report_Detail_V)
-- =====================================================================
INSERT INTO AD_Table_Process (AD_Table_Process_ID, AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    AD_Process_ID, AD_Table_ID, EntityType,
    WEBUI_DocumentAction, WEBUI_ViewAction, WEBUI_IncludedTabTopAction,
    WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default)
SELECT 541659 /*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-08-06 09:00:00','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-06 09:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
    585647, 542587, 'D',
    'Y', 'Y', 'N',
    'N', 'N'
WHERE NOT EXISTS (
    SELECT 1 FROM AD_Table_Process
    WHERE AD_Process_ID = 585647 AND AD_Table_ID = 542587);

-- =====================================================================
-- 2. Deactivate the old AD_Table_Process wiring (585647 → 542632); no longer needed.
-- =====================================================================
UPDATE AD_Table_Process
   SET IsActive  = 'N',
       Updated   = TO_TIMESTAMP('2026-08-06 09:00:01','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_Table_Process_ID = 541656;

-- =====================================================================
-- 3. Deactivate the new AD_Menu (542353 "Intrastat Vorschau")
-- =====================================================================
UPDATE AD_Menu
   SET IsActive  = 'N',
       Updated   = TO_TIMESTAMP('2026-08-06 09:00:02','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_Menu_ID = 542353;

UPDATE AD_TreeNodeMM
   SET IsActive  = 'N',
       Updated   = TO_TIMESTAMP('2026-08-06 09:00:03','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE Node_ID = 542353;

-- =====================================================================
-- 4. Deactivate the new tab / fields / UI elements (AD_Tab 549359 under AD_Window 542179).
-- =====================================================================
UPDATE AD_UI_Element
   SET IsActive  = 'N',
       Updated   = TO_TIMESTAMP('2026-08-06 09:00:04','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_Tab_ID = 549359;

UPDATE AD_UI_ElementGroup
   SET IsActive  = 'N',
       Updated   = TO_TIMESTAMP('2026-08-06 09:00:05','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_UI_Column_ID IN (SELECT AD_UI_Column_ID FROM AD_UI_Column
                            WHERE AD_UI_Section_ID IN (SELECT AD_UI_Section_ID FROM AD_UI_Section WHERE AD_Tab_ID = 549359));

UPDATE AD_UI_Column
   SET IsActive  = 'N',
       Updated   = TO_TIMESTAMP('2026-08-06 09:00:06','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_UI_Section_ID IN (SELECT AD_UI_Section_ID FROM AD_UI_Section WHERE AD_Tab_ID = 549359);

UPDATE AD_UI_Section
   SET IsActive  = 'N',
       Updated   = TO_TIMESTAMP('2026-08-06 09:00:07','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_Tab_ID = 549359;

UPDATE AD_Field
   SET IsActive  = 'N',
       Updated   = TO_TIMESTAMP('2026-08-06 09:00:08','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_Tab_ID = 549359;

UPDATE AD_Tab
   SET IsActive  = 'N',
       Updated   = TO_TIMESTAMP('2026-08-06 09:00:09','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_Tab_ID = 549359;

UPDATE AD_Window
   SET IsActive  = 'N',
       Updated   = TO_TIMESTAMP('2026-08-06 09:00:10','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_Window_ID = 542179;

-- =====================================================================
-- 5. Deactivate AD_Table 542632 (Intrastat_Preview_V) + all its AD_Columns.
-- =====================================================================
UPDATE AD_Column
   SET IsActive  = 'N',
       Updated   = TO_TIMESTAMP('2026-08-06 09:00:11','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_Table_ID = 542632;

UPDATE AD_Table
   SET IsActive  = 'N',
       Updated   = TO_TIMESTAMP('2026-08-06 09:00:12','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_Table_ID = 542632;

-- =====================================================================
-- 6. Restore AD_Menu 542307 to its original "Intrastat" name.
-- =====================================================================
UPDATE AD_Menu
   SET AD_Element_ID = 584668,
       Name          = 'Intrastat',
       Updated       = TO_TIMESTAMP('2026-08-06 09:00:13','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy     = 100
 WHERE AD_Menu_ID = 542307;

UPDATE AD_Menu_Trl
   SET Name         = 'Intrastat',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-08-06 09:00:14','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
 WHERE AD_Menu_ID = 542307 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Menu_Trl
   SET Name         = 'Intrastat',
       IsTranslated = 'Y',
       Updated      = TO_TIMESTAMP('2026-08-06 09:00:15','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
 WHERE AD_Menu_ID = 542307 AND AD_Language = 'en_US';
