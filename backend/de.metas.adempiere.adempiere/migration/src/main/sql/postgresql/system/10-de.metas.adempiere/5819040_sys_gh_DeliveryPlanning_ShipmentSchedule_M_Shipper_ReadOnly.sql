-- C5: AC-13c — Make Shipper read-only on Shipment Schedule window 500221
-- AD_Field 591224 (M_ShipmentSchedule.M_Shipper_ID, Tab 500221 "Auslieferplan", Window 500221)
-- Mechanism: IsReadOnly='Y' on the field (system-derived value; AD_Column.IsUpdateable left as-is).

UPDATE AD_Field
SET    IsReadOnly  = 'Y',
       Updated     = TO_TIMESTAMP('2026-08-13', 'YYYY-MM-DD'),
       UpdatedBy   = 99
WHERE  AD_Field_ID = 591224 -- existing row, no ID-server call needed
;
