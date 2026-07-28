-- Performance: index C_Invoice_Candidate.C_OrderSO_ID (the sales-order link).
-- Used for per-row joins/lookups that otherwise sequentially scan the (large)
-- C_Invoice_Candidate table. IF NOT EXISTS keeps it a no-op where already present.
CREATE INDEX IF NOT EXISTS c_invoice_candidate_c_orderso_id
    ON c_invoice_candidate (c_orderso_id);
