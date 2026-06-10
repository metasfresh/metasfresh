-- Enable IsApiCarrierAdvise='Y' on all M_Shipper rows whose carrier gateway is nShift.
-- Predicate is keyed on ShipperGateway='nshift' — portable across all instances,
-- no hardcoded M_Shipper_ID.

SELECT backup_table('m_shipper', '_nshift_apicarrieradvise');

UPDATE M_Shipper
SET    IsApiCarrierAdvise = 'Y',
       Updated            = TO_TIMESTAMP('2026-06-10 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy          = 99
WHERE  IsApiCarrierAdvise <> 'Y'
  AND  ShipperGateway     = 'nshift'
;
