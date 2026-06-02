-- Add IsAutoDistributionOrder to M_Warehouse
-- Script 2 of 2: AD_Column + AD_Column_Trl + physical DDL on M_Warehouse

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
VALUES (592623 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-05-26 14:01:00','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-05-26 14:01:00','YYYY-MM-DD HH24:MI:SS'), 100,
        0, 190, 584916, 20,
        'IsAutoDistributionOrder',
        'Auto-Verteilungsauftrag',
        'Wenn Ja, betreibt dieses Lager den eigenständigen DD_Order-Abgleich für die Kommissionierung — anstelle der allgemeinen Materialdisposition. Setzt voraus, dass auch MRP_Exclude=Ja gesetzt ist und ein Verteilungsnetz hinterlegt ist.',
        'Markiert dieses Lager als Auto-Verteilungsauftrag. Für solche Lager wird ein dedizierter, idempotenter Reconcile-Flow aktiviert, der pro M_ShipmentSchedule genau einen DD_Order erzeugt. Damit der Flow korrekt funktioniert, muss am Lager zusätzlich MRP_Exclude=Ja gesetzt sein und ein DD_NetworkDistribution_ID hinterlegt sein.',
        1, 'Y', 'Y', 'N',
        'N', 'D', 'N', 'N', 'N',
        'N', 'N', 'N', 'Y',
        'NP');

-- Skeleton Trl rows for AD_Column
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592623
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- =============================================================================
-- 2. Physical DDL — new column (t_alter_column does not work for brand-new columns)
-- =============================================================================
ALTER TABLE M_Warehouse ADD COLUMN IF NOT EXISTS IsAutoDistributionOrder CHAR(1) DEFAULT 'N';
UPDATE M_Warehouse SET IsAutoDistributionOrder = 'N' WHERE IsAutoDistributionOrder IS NULL;
ALTER TABLE M_Warehouse ALTER COLUMN IsAutoDistributionOrder SET NOT NULL;

-- =============================================================================
-- 3. Propagate translations from AD_Element to AD_Column_Trl
-- =============================================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584916);
