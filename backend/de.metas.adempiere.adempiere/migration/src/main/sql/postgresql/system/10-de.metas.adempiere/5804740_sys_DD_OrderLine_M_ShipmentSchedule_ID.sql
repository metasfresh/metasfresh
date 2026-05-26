-- Add M_ShipmentSchedule_ID to DD_OrderLine (FK + partial index)
-- Used by DD_Order picking reconcile flow (me03 #29966) — line links back to schedule for the audit trail.
--
-- IDs allocated from idserver.metas.de on 2026-05-26:
--   AD_MigrationScript  5804740 (this script)
--   AD_Column           592626  (DD_OrderLine.M_ShipmentSchedule_ID)

-- =============================================================================
-- 1. AD_Column (DD_OrderLine — lookup AD_Table_ID by name)
-- =============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       ColumnName, Name, Description, Help,
                       FieldLength, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       DefaultValue, EntityType, IsKey, IsParent, IsSelectionColumn,
                       IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging,
                       PersonalDataCategory)
VALUES (592626 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-05-26 14:25:00','YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-05-26 14:25:00','YYYY-MM-DD HH24:MI:SS'), 100,
        0,
        (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'DD_OrderLine'),
        (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'M_ShipmentSchedule_ID'),
        30 /*Search*/,
        'M_ShipmentSchedule_ID',
        'Lieferdisposition',
        'Lieferdispositions-Zeile, für die diese DD_OrderLine erzeugt wurde (Picking-Reconcile-Flow).',
        'Verknüpft die DD_OrderLine mit der M_ShipmentSchedule-Zeile, die sie ausgelöst hat. Stets gleicher Wert wie auf dem DD_Order-Header.',
        10, 'N', 'Y', 'N',
        NULL, 'D', 'N', 'N', 'N',
        'N', 'N', 'N', 'Y',
        'NP');

-- Skeleton Trl rows
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592626
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- =============================================================================
-- 2. Physical DDL — new nullable FK column
-- =============================================================================
ALTER TABLE DD_OrderLine ADD COLUMN IF NOT EXISTS M_ShipmentSchedule_ID NUMERIC(10);
ALTER TABLE DD_OrderLine
  ADD CONSTRAINT mshipmentschedule_ddorderline
  FOREIGN KEY (M_ShipmentSchedule_ID) REFERENCES M_ShipmentSchedule(M_ShipmentSchedule_ID);

-- Partial index (most DD_OrderLine rows will NOT have a M_ShipmentSchedule_ID; only picking-warehouse ones do)
CREATE INDEX IF NOT EXISTS dd_orderline_m_shipmentschedule_idx
  ON DD_OrderLine (M_ShipmentSchedule_ID)
  WHERE M_ShipmentSchedule_ID IS NOT NULL;

-- =============================================================================
-- 3. Propagate translations from AD_Element to AD_Column_Trl
-- =============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(
  (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'M_ShipmentSchedule_ID')
);
