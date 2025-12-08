-- we fixed the code so that if bill_location_override_value_id is set, it now is uses in the header-aggregation-key.
INSERT INTO c_invoice_candidate_recompute (c_invoice_candidate_id)
SELECT c_invoice_candidate_id
FROM c_invoice_candidate
WHERE bill_location_override_value_id IS NOT NULL
  AND processed = 'N' and isactive='Y'
;