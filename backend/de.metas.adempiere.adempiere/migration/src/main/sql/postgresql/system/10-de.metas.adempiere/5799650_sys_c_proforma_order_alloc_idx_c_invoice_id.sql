-- Add a non-partial INDEX on C_Proforma_Order_Alloc(C_Invoice_ID).
--
-- The existing partial composite index `c_proforma_order_alloc_c_invoice_id_c_order_id_uq`
-- on `(C_Invoice_ID, C_Order_ID) WHERE IsActive = 'Y'` covers queries that filter
-- `IsActive = 'Y'`. Queries that don't filter IsActive (e.g., audit / history scans by
-- invoice) cannot use the partial index. This non-partial single-column index closes
-- that gap, mirroring the existing non-partial `c_proforma_order_alloc_c_order_id`
-- index on the C_Order_ID side.

CREATE INDEX IF NOT EXISTS c_proforma_order_alloc_c_invoice_id
    ON C_Proforma_Order_Alloc (C_Invoice_ID)
;
