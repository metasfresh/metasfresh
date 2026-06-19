-- DD_Order picking replenishment — add DD_Order.IsPickingDisconnected flag.
-- Technical, non-UI boolean: marks a replenishment DD_Order as detached from its
-- M_Picking_Job_Schedule assignment when the shipment was closed-out independently
-- (in-progress move). Set programmatically by the close-out reconcile; the guard /
-- reconcile lookup filter on it. No AD_Field / AD_UI_Element — never surfaced on a window.
--
-- IDs allocated from idserver.metas.de on 2026-06-12:
--   AD_MigrationScript  5807600 (this script)
--   AD_Element          584990  (IsPickingDisconnected)
--   AD_Column           592806  (DD_Order.IsPickingDisconnected)

-- =============================================================================
-- 1. AD_Element (new) — German base text; en_US via Trl
-- =============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName)
VALUES (584990 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-06-12 12:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-06-12 12:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
        'IsPickingDisconnected', 'D', 'Kommissionierung entkoppelt', 'Kommissionierung entkoppelt');

-- Skeleton Trl rows (system languages; copy base, IsTranslated='N')
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Element_ID = 584990
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- English translation
UPDATE AD_Element_Trl
   SET Name = 'Is Picking Disconnected', PrintName = 'Is Picking Disconnected', IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-12 12:00:01','YYYY-MM-DD HH24:MI:SS')
 WHERE AD_Element_ID = 584990 AND AD_Language = 'en_US';

-- =============================================================================
-- 2. AD_Column (DD_Order — lookup AD_Table_ID / AD_Element_ID by name)
-- =============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       ColumnName, Name,
                       FieldLength, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       DefaultValue, EntityType, IsKey, IsParent, IsSelectionColumn,
                       IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging,
                       IsExcludeFromZoomTargets, CloningStrategy,
                       PersonalDataCategory)
VALUES (592806 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-06-12 12:00:02','YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-06-12 12:00:02','YYYY-MM-DD HH24:MI:SS'), 100,
        0,
        (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'DD_Order'),
        (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'IsPickingDisconnected'),
        20 /*Yes-No*/,
        'IsPickingDisconnected', 'Kommissionierung entkoppelt',
        1, 'Y', 'Y', 'N',
        'N', 'D', 'N', 'N', 'N',
        'N', 'N', 'N', 'Y',
        'Y', 'XX',
        'NP');

-- Skeleton Trl rows
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592806
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- =============================================================================
-- 3. Physical DDL — mandatory boolean, backfilled to 'N'
-- =============================================================================
SELECT public.db_alter_table('DD_Order','ALTER TABLE public.DD_Order ADD COLUMN IsPickingDisconnected CHAR(1) DEFAULT ''N'' CHECK (IsPickingDisconnected IN (''Y'',''N'')) NOT NULL');

-- =============================================================================
-- 4. Propagate translations from AD_Element to AD_Column_Trl
-- =============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(
  (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'IsPickingDisconnected')
);
