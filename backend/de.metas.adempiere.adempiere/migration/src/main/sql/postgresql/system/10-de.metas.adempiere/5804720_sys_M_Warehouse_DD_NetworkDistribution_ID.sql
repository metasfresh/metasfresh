-- DD_Order picking reconcile — add M_Warehouse.DD_NetworkDistribution_ID (FK to DD_NetworkDistribution)
-- IDs allocated from idserver.metas.de on 2026-05-26:
--   AD_MigrationScript  5804720 (migration script prefix)
--   AD_Column           592624  (M_Warehouse.DD_NetworkDistribution_ID)

-- =============================================================================
-- 1. AD_Column (M_Warehouse, AD_Table_ID=190 — reuse existing AD_Element)
-- =============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       ColumnName, Name, Description, Help,
                       FieldLength, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       DefaultValue, EntityType, IsKey, IsParent, IsSelectionColumn,
                       IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging,
                       PersonalDataCategory)
VALUES (592624 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-05-26 14:10:00','YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-05-26 14:10:00','YYYY-MM-DD HH24:MI:SS'), 100,
        0, 190 /*M_Warehouse*/,
        (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'DD_NetworkDistribution_ID'),
        30 /*Search*/,
        'DD_NetworkDistribution_ID',
        'Verteilungsnetz',
        'Verteilungsnetz, das für den DD_Order-Abgleich auf diesem Kommissionierungslager verwendet wird.',
        'Pflicht, wenn IsPackingWarehouse=Ja. Der Reconcile-Flow nutzt dieses Netz, um pro Produkt das Quelllager aufzulösen.',
        10, 'N', 'Y', 'N',
        NULL, 'D', 'N', 'N', 'N',
        'N', 'N', 'N', 'Y',
        'NP');

-- Skeleton Trl rows
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592624
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- =============================================================================
-- 2. Physical DDL — new nullable FK column
-- =============================================================================
ALTER TABLE M_Warehouse ADD COLUMN IF NOT EXISTS DD_NetworkDistribution_ID NUMERIC(10);
ALTER TABLE M_Warehouse
  ADD CONSTRAINT ddnetworkdistribution_mwarehouse
  FOREIGN KEY (DD_NetworkDistribution_ID) REFERENCES DD_NetworkDistribution(DD_NetworkDistribution_ID);

-- =============================================================================
-- 3. Index for foreign key performance
-- =============================================================================
CREATE INDEX IF NOT EXISTS idx_M_Warehouse_DD_NetworkDistribution_ID
  ON M_Warehouse (DD_NetworkDistribution_ID);

-- =============================================================================
-- 4. Propagate translations from AD_Element to AD_Column_Trl
-- =============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(
  (SELECT AD_Element_ID FROM AD_Element WHERE ColumnName = 'DD_NetworkDistribution_ID')
);
