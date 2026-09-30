-- Enable IsApiCarrierAdvise='Y' on the canonical seed nShift shipper (M_Shipper_ID=540019).
-- Hardcoding the ID is intentional: 540019 is the seed nShift shipper present on all instances;
-- test-only shippers (e.g. 1000002) must not be touched by this migration.

SELECT backup_table('m_shipper', '_nshift_apicarrieradvise');

UPDATE M_Shipper
SET    IsApiCarrierAdvise = 'Y',
       Updated            = TO_TIMESTAMP('2026-06-10 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy          = 100
WHERE  M_Shipper_ID       = 540019
  AND  IsApiCarrierAdvise <> 'Y'
;
