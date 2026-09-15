-- gh#28126: Add QtyDelivered column to M_QtyReservation
-- Tracks how much of each reservation has been fulfilled via deliveries.
-- Effective reservation = Qty - QtyDelivered.

-- ==========================================================================
-- 1. AD_Column for QtyDelivered (reuse existing AD_Element_ID=528)
-- ==========================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name,
                       IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo,
                       FieldLength, DefaultValue, EntityType, Version, PersonalDataCategory)
VALUES (592130, 0, 0, 'Y', TO_TIMESTAMP('2026-03-03 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-03 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        542583, 528, 29, 'QtyDelivered', 'Gelieferte Menge',
        'N', 'Y', 'Y', 'N', 0,
        14, '0', 'D', 0, 'NP');

-- ==========================================================================
-- 2. Physical column DDL
-- ==========================================================================
ALTER TABLE M_QtyReservation ADD COLUMN IF NOT EXISTS QtyDelivered NUMERIC DEFAULT 0 NOT NULL;
