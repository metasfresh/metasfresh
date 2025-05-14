DROP INDEX IF EXISTS c_invoice_detail_c_invoice_candidate_id
;

CREATE INDEX c_invoice_detail_c_invoice_candidate_id
    ON c_invoice_detail (c_invoice_candidate_id)
;

DROP INDEX IF EXISTS c_invoice_detail_c_invoice_id
;

CREATE INDEX c_invoice_detail_c_invoice_id
    ON c_invoice_detail (c_invoice_id)
;