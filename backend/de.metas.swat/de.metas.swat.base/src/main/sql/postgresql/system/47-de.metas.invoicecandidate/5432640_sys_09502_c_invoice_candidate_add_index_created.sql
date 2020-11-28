
CREATE INDEX c_invoice_candidate_created
  ON c_invoice_candidate
  USING btree
  (created );
