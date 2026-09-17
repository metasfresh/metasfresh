-- Performance: add an index on M_ShippingPackage.C_Order_ID.
-- The M_ReceiptSchedule shipping ColumnSQL columns (ContainerNo, VesselName, TrackingID,
-- M_ShipperTransportation_ID, ETD/ETA/ATA/ATD, BLDate, CRD, POL_ID/POD_ID, ...) each join
--   M_ShippingPackage sp ON st.m_shippertransportation_id = sp.m_shippertransportation_id
--   INNER JOIN M_ReceiptSchedule r ON r.c_order_id = sp.c_order_id
-- Without an index on M_ShippingPackage.C_Order_ID, this is a sequential scan over the
-- (large) M_ShippingPackage table for every displayed row, making the Material Receipt
-- Candidates grid extremely slow to render. IF NOT EXISTS keeps it a no-op where present.
CREATE INDEX IF NOT EXISTS m_shippingpackage_c_order_id
    ON m_shippingpackage (c_order_id);
