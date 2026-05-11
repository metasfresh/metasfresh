-- me03#29584: Add IsDropShipWarehouse to M_Warehouse
-- Script 2 of 3: AD_Column + AD_Column_Trl + physical DDL on M_Warehouse

-- =============================================================================
-- 1. AD_Column (M_Warehouse, AD_Table_ID=190)
-- =============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       ColumnName, Name, Description, Help,
                       FieldLength, IsMandatory, IsUpdateable, IsAlwaysUpdateable,
                       DefaultValue, EntityType, IsKey, IsParent, IsSelectionColumn,
                       IsTranslated, IsIdentifier, IsEncrypted, IsAllowLogging,
                       PersonalDataCategory)
VALUES (592508 /*From ID Server*/, 0, 0, 'Y', '2026-05-11 09:00', 100, '2026-05-11 09:00', 100,
        0, 190, 584854, 20,
        'IsDropShipWarehouse',
        'Streckengeschäft-Lager',
        'Wenn Ja, werden Verkaufsaufträge auf diesem Lager als Streckengeschäft (Dropship) abgewickelt. Bei Auftragsabschluss wird automatisch eine Bestellung an den Lieferanten erzeugt.',
        'Markiert dieses Lager als Streckengeschäft-Lager (Dropship). Auf solchen Lagern wird bei Auftragsabschluss automatisch genau eine Bestellung pro Verkaufsauftrag an den Lieferanten erzeugt — ohne Umweg über die normale Materialdisposition / Bestellkandidaten. Die Lieferant-Auswahl pro Position wird bei dieser Lager-Art strenger geprüft.',
        1, 'Y', 'Y', 'N',
        'N', 'D', 'N', 'N', 'N',
        'N', 'N', 'N', 'Y',
        'NP');

-- Skeleton Trl rows for AD_Column
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592508
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- =============================================================================
-- 2. Physical DDL — new column (t_alter_column does not work for brand-new columns)
-- =============================================================================
ALTER TABLE M_Warehouse ADD COLUMN IF NOT EXISTS IsDropShipWarehouse CHAR(1) DEFAULT 'N';
UPDATE M_Warehouse SET IsDropShipWarehouse = 'N' WHERE IsDropShipWarehouse IS NULL;
ALTER TABLE M_Warehouse ALTER COLUMN IsDropShipWarehouse SET NOT NULL;

-- =============================================================================
-- 3. Propagate translations from AD_Element to AD_Column_Trl
-- =============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584854);
