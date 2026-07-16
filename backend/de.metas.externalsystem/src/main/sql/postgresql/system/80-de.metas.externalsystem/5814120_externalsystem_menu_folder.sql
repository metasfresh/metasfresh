-- #############################################################################
-- Migration: group the three external-system windows under a dedicated "External
-- System" menu folder instead of leaving them loose directly under the top-level
-- "System" folder (AD_Menu 1000098). Reparents the menu nodes of:
--   541024 "Externes System Konfiguration"      (menu node 541585)
--   541962 "Skriptbasierte Importkonvertierung" (menu node 542263)
--   541967 "ExternalSystem Endpoint"            (menu node 542268)
-- so a user configuring a scripted import can find the config windows and create
-- an endpoint from one place.
--
-- IDs from idserver.metas.de:
--   AD_Element 585108  (label for the new menu folder)
--   AD_Menu    542347  (the "External System" summary folder)
-- #############################################################################

-- ============================================================================
-- 1. Dedicated AD_Element for the menu-folder label. Menu elements carry no
--    ColumnName (mirrors the existing external-system menu elements).
-- ============================================================================

INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	ColumnName, Name, PrintName, Description, EntityType)
VALUES (585108 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-07-16 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
	TO_TIMESTAMP('2026-07-16 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
	NULL, 'Externes System', 'Externes System', NULL, 'de.metas.externalsystem');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated,
	AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N',
	t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585108
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name = 'Externes System', PrintName = 'Externes System', IsTranslated = 'Y',
	Updated = TO_TIMESTAMP('2026-07-16 09:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585108 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Element_Trl SET Name = 'External System', PrintName = 'External System', IsTranslated = 'Y',
	Updated = TO_TIMESTAMP('2026-07-16 09:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585108 AND AD_Language = 'en_US';

-- ============================================================================
-- 2. The "External System" summary menu folder (no action; booleans mirror the
--    existing de.metas summary folders).
-- ============================================================================

INSERT INTO AD_Menu (AD_Menu_ID, AD_Client_ID, AD_Org_ID, IsActive,
	Created, CreatedBy, Updated, UpdatedBy,
	Name, IsSummary, IsSOTrx, IsReadOnly, IsCreateNew, EntityType, Action, InternalName, AD_Element_ID)
VALUES (542347 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-07-16 09:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
	TO_TIMESTAMP('2026-07-16 09:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
	'Externes System', 'Y', 'N', 'N', 'N', 'de.metas.externalsystem', NULL, 'External_System', 585108);

INSERT INTO AD_Menu_Trl (AD_Menu_ID, AD_Language, Name, IsTranslated,
	AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT 542347, l.AD_Language, 'Externes System', 'N', 0, 0, 'Y',
	TO_TIMESTAMP('2026-07-16 09:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
	TO_TIMESTAMP('2026-07-16 09:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Menu_ID = 542347);

UPDATE AD_Menu_Trl SET Name = 'Externes System', IsTranslated = 'Y',
	Updated = TO_TIMESTAMP('2026-07-16 09:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Menu_ID = 542347 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Menu_Trl SET Name = 'External System', IsTranslated = 'Y',
	Updated = TO_TIMESTAMP('2026-07-16 09:00:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Menu_ID = 542347 AND AD_Language = 'en_US';

-- ============================================================================
-- 3. Place the new folder under the top-level "System" tree node (1000098),
--    then reparent the three external-system window nodes under the folder.
-- ============================================================================

INSERT INTO AD_TreeNodeMM (AD_Tree_ID, Node_ID, Parent_ID, SeqNo, AD_Client_ID, AD_Org_ID,
	IsActive, Created, CreatedBy, Updated, UpdatedBy)
VALUES (10, 542347, 1000098, 7, 0, 0, 'Y',
	TO_TIMESTAMP('2026-07-16 09:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
	TO_TIMESTAMP('2026-07-16 09:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100);

UPDATE AD_TreeNodeMM
SET Parent_ID = 542347,
	Updated = TO_TIMESTAMP('2026-07-16 09:00:25', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tree_ID = 10 AND Node_ID IN (541585, 542263, 542268);
