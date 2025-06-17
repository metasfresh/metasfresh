
CREATE INDEX IF NOT EXISTS modcntr_log_c_invoice_candidate_id ON modcntr_log(c_invoice_candidate_id)
;

CREATE INDEX IF NOT EXISTS modcntr_log_c_flatrate_term_id_modcntr_module_id ON modcntr_log(c_flatrate_term_id, modcntr_module_id)
;

