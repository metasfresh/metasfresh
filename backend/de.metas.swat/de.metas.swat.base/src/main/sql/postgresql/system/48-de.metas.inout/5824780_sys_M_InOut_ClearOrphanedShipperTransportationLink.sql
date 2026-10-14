SELECT backup_table('M_InOut', '_orphaned_shippertransp_5824780');

-- Repair shipments orphaned by a regression and by a pre-existing gap where
-- M_InOut.M_ShipperTransportation_ID was never cleared on M_ShippingPackage
-- delete or void operations. A shipment with the M_ShipperTransportation_ID FK
-- set but no active M_ShippingPackage row for that (M_InOut, M_ShipperTransportation)
-- pair is permanently stuck in the system: the Transport Order candidate query that
-- identifies shipments available to add excludes any M_InOut whose FK is already set,
-- and the no-HU code path also skips such shipments. New orphans are prevented at the
-- model layer; this script is the one-time repair of orphans that pre-date that fix.
-- Restricted to completed shipments: only a completed shipment can ever have had this
-- FK set by the add-shipment-to-Transport-Order flow, so a voided or reverse-corrected
-- shipment reaching this same state is a separate, legitimate case that must be left alone.
UPDATE M_InOut mi
SET M_ShipperTransportation_ID = NULL,
    UpdatedBy = 99
WHERE mi.M_ShipperTransportation_ID IS NOT NULL
  AND mi.DocStatus = 'CO'
  AND NOT EXISTS (
    SELECT 1
    FROM M_ShippingPackage sp
    WHERE sp.M_InOut_ID = mi.M_InOut_ID
      AND sp.M_ShipperTransportation_ID = mi.M_ShipperTransportation_ID
      AND sp.IsActive = 'Y'
  );
