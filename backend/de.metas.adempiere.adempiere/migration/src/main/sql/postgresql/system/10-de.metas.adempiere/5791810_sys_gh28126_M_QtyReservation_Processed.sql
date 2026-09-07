-- gh#28126: Add Processed flag to M_QtyReservation
-- Set to 'Y' when effective qty (Qty - QtyDelivered) reaches zero.
-- Enables efficient DB-level filtering of fully-fulfilled reservations.

-- ==========================================================================
-- 1. AD_Column for Processed (reuse standard AD_Element_ID=1047)
-- ==========================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, AD_Reference_ID, ColumnName, Name,
                       IsKey, IsMandatory, IsUpdateable, IsIdentifier, SeqNo,
                       FieldLength, DefaultValue, EntityType, Version, PersonalDataCategory)
VALUES (579379/*OLD=592131*/, 0, 0, 'Y', TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-04 10:00', 'YYYY-MM-DD HH24:MI'), 0,
        542583, 1047, 20, 'Processed', 'Verarbeitet',
        'N', 'Y', 'Y', 'N', 0,
        1, 'N', 'D', 0, 'NP');

-- ==========================================================================
-- 2. Physical column DDL
-- ==========================================================================
ALTER TABLE M_QtyReservation ADD COLUMN IF NOT EXISTS Processed CHAR(1) DEFAULT 'N' NOT NULL;

-- ==========================================================================
-- 3. Data migration: mark already fully-delivered records as processed
-- ==========================================================================
UPDATE M_QtyReservation SET Processed = 'Y' WHERE Qty - QtyDelivered <= 0;

-- ==========================================================================
-- 4. Index for fast filtering of active (unprocessed) reservations
-- ==========================================================================
CREATE INDEX IF NOT EXISTS M_QtyReservation_Processed ON M_QtyReservation (Processed) WHERE Processed = 'N';
