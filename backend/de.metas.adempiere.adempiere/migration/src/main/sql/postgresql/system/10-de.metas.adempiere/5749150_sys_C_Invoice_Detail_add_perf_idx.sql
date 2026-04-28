
-- useful in jasper when we have to display details that are not assigned to a certain invoice-line
CREATE INDEX IF NOT EXISTS c_invoice_detail_c_invoice_id ON c_invoice_detail (c_invoice_id)
;

