-- Create a Table-type AD_Reference for M_ShipperTransportation that zooms to the
-- Lieferanweisungen (DI) window (541657) instead of the default Transport-Auftrag window.
-- Points AD_Column M_Delivery_Planning.M_ShipperTransportation_ID at this reference
-- so the zoom from the Delivery-Planning window opens the DI window.

-- 1. Insert the AD_Reference (Table type, ValidationType='T')
INSERT INTO AD_Reference
    (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, ValidationType, EntityType)
VALUES
    (542129 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-13 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     TO_TIMESTAMP('2026-08-13 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     'M_ShipperTransportation (DI Zoom)',
     'T',
     'D');

-- 2. Seed _Trl rows for all active system languages
INSERT INTO AD_Reference_Trl
    (AD_Reference_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, IsTranslated)
SELECT
    542129 /*From ID Server*/,
    l.AD_Language,
    0, 0, 'Y',
    TO_TIMESTAMP('2026-08-13 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    100,
    TO_TIMESTAMP('2026-08-13 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    100,
    'M_ShipperTransportation (DI Zoom)',
    'N'
FROM AD_Language l
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND NOT EXISTS (
      SELECT 1 FROM AD_Reference_Trl t
      WHERE t.AD_Reference_ID = 542129 AND t.AD_Language = l.AD_Language
  );

-- 3. Override en_US translation
UPDATE AD_Reference_Trl
SET Name        = 'M_ShipperTransportation (DI Zoom)',
    IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-08-13 10:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Reference_ID = 542129 AND AD_Language = 'en_US';

-- Mark de_DE and de_CH as translated
UPDATE AD_Reference_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-08-13 10:00:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Reference_ID = 542129 AND AD_Language IN ('de_DE', 'de_CH');

-- 4. Insert AD_Ref_Table: key=M_ShipperTransportation_ID(540426), display=DocumentNo(540439),
--    zoom window=541657 (Lieferanweisungen)
INSERT INTO AD_Ref_Table
    (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Table_ID, AD_Key, AD_Display, AD_Window_ID, EntityType)
VALUES
    (542129 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-13 10:00:04', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     TO_TIMESTAMP('2026-08-13 10:00:04', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     540030, -- M_ShipperTransportation table
     540426, -- M_ShipperTransportation_ID (key column)
     540439, -- DocumentNo (display/identifier column)
     541657, -- Lieferanweisungen window (DI)
     'D');

-- 5. Point the DP column at the new Table reference
--    AD_Reference_ID=19 (Table), AD_Reference_Value_ID=542129
UPDATE AD_Column
SET AD_Reference_ID       = 19,
    AD_Reference_Value_ID = 542129 /*From ID Server*/,
    Updated               = TO_TIMESTAMP('2026-08-13 10:00:05', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy             = 100
WHERE AD_Column_ID = 585602; -- M_Delivery_Planning.M_ShipperTransportation_ID
