-- gh#28631: Add IsDeliveryStop column to M_ReceiptSchedule
-- Mirrors M_ShipmentSchedule.IsDeliveryStop for the vendor/purchase side.

-- ==========================================================================
-- 1. AD_Column: M_ReceiptSchedule.IsDeliveryStop (reuse AD_Element 543441)
-- ==========================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       ColumnName, Name, Description,
                       FieldLength, IsMandatory, IsUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsSelectionColumn,
                       IsAlwaysUpdateable, PersonalDataCategory)
VALUES (592212, 0, 0, 'Y', TO_TIMESTAMP('2026-03-10 02:30', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-10 02:30', 'YYYY-MM-DD HH24:MI'), 0,
        0, 'de.metas.inoutcandidate', 540524, 543441, 20,
        'IsDeliveryStop', 'Lieferstopp', 'Liefer-/Auftragssperre',
        1, 'Y', 'Y', 'N',
        'N', 'N', 'N', 'N', 'N',
        'N', 'NP');

-- Physical column
ALTER TABLE M_ReceiptSchedule ADD COLUMN IF NOT EXISTS IsDeliveryStop CHAR(1) DEFAULT 'N';
UPDATE M_ReceiptSchedule SET IsDeliveryStop = 'N' WHERE IsDeliveryStop IS NULL;
ALTER TABLE M_ReceiptSchedule ALTER COLUMN IsDeliveryStop SET NOT NULL;
