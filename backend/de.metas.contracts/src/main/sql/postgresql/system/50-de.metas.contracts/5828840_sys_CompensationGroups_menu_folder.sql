-- Menu: new folder "Kompensationsgruppen" / "Compensation Groups" under "Vertrieb" (AD_Menu_ID=1000010), placed right
-- after the "Auftrag" entry (AD_Menu_ID=1000040). It holds, in this order:
--   0  the new "Auftrag Kompensationsgruppe" / "Order Compensation Group" window (AD_Window_ID=542196, created by 5828830)
--   1  541040 "Kompensationsgruppe Schema"                 (moved here from 1000037)
--   2  541726 "Kompensationsgruppe Schema Kategorie"       (moved here from 1000037)
--   3  542364 "Kompensationsgruppen-Vertragseinstellungen" (moved here from 1000070)
-- Menu tree order is AD_TreeNodeMM.SeqNo within the parent (ascending); the siblings after "Auftrag" are shifted by one
-- to make room for the folder.
--
-- IDs allocated from idserver.metas.de on 2026-10-09:
--   AD_MigrationScript 5828840 (this script)
--   AD_Element 585528 (folder caption)
--   AD_Menu    542368 (folder), 542367 (window entry)

-- ============================================================================
-- 1) AD_Element: folder caption
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName)
VALUES (585528 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-09 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-09 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        NULL, 'D', 'Kompensationsgruppen', 'Kompensationsgruppen')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585528
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Compensation Groups', PrintName = 'Compensation Groups',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-09 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585528 AND AD_Language = 'en_US'
;
-- de_DE / de_CH: the German base text is final (no ß in it)
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-09 12:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585528 AND AD_Language IN ('de_DE', 'de_CH')
;

-- ============================================================================
-- 2) AD_Menu: folder + window entry
-- ============================================================================
INSERT INTO AD_Menu (Action, AD_Client_ID, AD_Element_ID, AD_Menu_ID, AD_Org_ID, AD_Window_ID, Created, CreatedBy, EntityType, InternalName, IsActive, IsCreateNew, IsReadOnly, IsSOTrx, IsSummary, Name, Updated, UpdatedBy)
VALUES (NULL, 0, 585528, 542368 /*From ID Server*/, 0, NULL, TO_TIMESTAMP('2026-10-09 12:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'CompensationGroups', 'Y', 'N', 'N', 'N', 'Y', 'Kompensationsgruppen', TO_TIMESTAMP('2026-10-09 12:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Menu (Action, AD_Client_ID, AD_Element_ID, AD_Menu_ID, AD_Org_ID, AD_Window_ID, Created, CreatedBy, EntityType, InternalName, IsActive, IsCreateNew, IsReadOnly, IsSOTrx, IsSummary, Name, Updated, UpdatedBy)
VALUES ('W', 0, 543469, 542367 /*From ID Server*/, 0, 542196, TO_TIMESTAMP('2026-10-09 12:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'C_Order_CompensationGroup', 'Y', 'N', 'N', 'Y', 'N', 'Auftrag Kompensationsgruppe', TO_TIMESTAMP('2026-10-09 12:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Menu_Trl (AD_Language, AD_Menu_ID, Description, Name, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Menu_ID, t.Description, t.Name, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Menu t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Menu_ID IN (542367, 542368)
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Menu_ID = t.AD_Menu_ID)
;
/* DDL */ select update_menu_translation_from_ad_element(585528)
;
/* DDL */ select update_menu_translation_from_ad_element(543469)
;

-- ============================================================================
-- 3) Menu tree (AD_Tree_ID=10): folder right after "Auftrag" under "Vertrieb"
-- ============================================================================
-- make room: shift the "Vertrieb" children after "Auftrag" by one
UPDATE AD_TreeNodeMM
SET SeqNo = SeqNo + 1, Updated = TO_TIMESTAMP('2026-10-09 12:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tree_ID = 10
  AND Parent_ID = 1000010 /* Vertrieb */
  AND SeqNo > (SELECT a.SeqNo FROM AD_TreeNodeMM a WHERE a.AD_Tree_ID = 10 AND a.Node_ID = 1000040 /* Auftrag */)
;
INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
SELECT 0, 0, 'Y', TO_TIMESTAMP('2026-10-09 12:00:07', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-09 12:00:07', 'YYYY-MM-DD HH24:MI:SS'), 100,
       10, 542368, 1000010 /* Vertrieb */, a.SeqNo + 1
FROM AD_TreeNodeMM a
WHERE a.AD_Tree_ID = 10 AND a.Node_ID = 1000040 /* Auftrag */
  AND NOT EXISTS (SELECT 1 FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID = 10 AND e.Node_ID = 542368)
;

-- ============================================================================
-- 4) Menu tree: folder content
-- ============================================================================
INSERT INTO AD_TreeNodeMM (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
SELECT 0, 0, 'Y', TO_TIMESTAMP('2026-10-09 12:00:08', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-09 12:00:08', 'YYYY-MM-DD HH24:MI:SS'), 100,
       10, 542367, 542368, 0
WHERE NOT EXISTS (SELECT 1 FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID = 10 AND e.Node_ID = 542367)
;
UPDATE AD_TreeNodeMM
SET Parent_ID = 542368, SeqNo = 1, Updated = TO_TIMESTAMP('2026-10-09 12:00:09', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 541040 /* Kompensationsgruppe Schema */
;
UPDATE AD_TreeNodeMM
SET Parent_ID = 542368, SeqNo = 2, Updated = TO_TIMESTAMP('2026-10-09 12:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 541726 /* Kompensationsgruppe Schema Kategorie */
;
UPDATE AD_TreeNodeMM
SET Parent_ID = 542368, SeqNo = 3, Updated = TO_TIMESTAMP('2026-10-09 12:00:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID = 542364 /* Kompensationsgruppen-Vertragseinstellungen */
;
