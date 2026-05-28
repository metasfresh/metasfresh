-- DD_Order picking reconcile — add DD_Order.M_ShipmentSchedule_ID FK + partial index.
-- Used by the picking reconcile flow: 1 DD_Order per shipment-schedule line.
--
-- IDs allocated from idserver.metas.de on 2026-05-26:
--   AD_MigrationScript  5804730 (this script)
--   AD_Column           592625  (DD_Order.M_ShipmentSchedule_ID)

-- =============================================================================
-- 1. AD_Column (DD_Order — lookup AD_Table_ID by name)
-- =============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       ColumnName, Name, Description, Help,
                       FieldLength, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       DefaultValue, EntityType, IsKey, IsParent, IsSelectionColumn,
                       IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging,
                       PersonalDataCategory)
VALUES (592625 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-05-26 14:20:00','YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-05-26 14:20:00','YYYY-MM-DD HH24:MI:SS'), 100,
        0,
        (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'DD_Order'),
        (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'M_ShipmentSchedule_ID'),
        30 /*Search*/,
        'M_ShipmentSchedule_ID',
        'Lieferdisposition',
        'Lieferdispositions-Zeile, für die dieser DD_Order erzeugt wurde (Picking-Reconcile-Flow).',
        'Verknüpft den DD_Order mit der M_ShipmentSchedule-Zeile, die ihn ausgelöst hat. Der Reconcile-Flow stellt sicher, dass es pro Schedule-Zeile genau einen aktiven DD_Order gibt.',
        10, 'N', 'Y', 'N',
        NULL, 'D', 'N', 'N', 'N',
        'N', 'N', 'N', 'Y',
        'NP');

-- Skeleton Trl rows
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592625
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- =============================================================================
-- 2. Physical DDL — new nullable FK column
-- =============================================================================
ALTER TABLE DD_Order ADD COLUMN IF NOT EXISTS M_ShipmentSchedule_ID NUMERIC(10);
ALTER TABLE DD_Order
  ADD CONSTRAINT mshipmentschedule_ddorder
  FOREIGN KEY (M_ShipmentSchedule_ID) REFERENCES M_ShipmentSchedule(M_ShipmentSchedule_ID);

-- Partial index (most DD_Order rows will NOT have a M_ShipmentSchedule_ID; only picking-warehouse ones do)
CREATE INDEX IF NOT EXISTS dd_order_m_shipmentschedule_idx
  ON DD_Order (M_ShipmentSchedule_ID)
  WHERE M_ShipmentSchedule_ID IS NOT NULL;

-- =============================================================================
-- 3. Propagate translations from AD_Element to AD_Column_Trl
-- =============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(
  (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'M_ShipmentSchedule_ID')
);
