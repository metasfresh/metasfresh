
CREATE INDEX IF NOT EXISTS c_invoice_candidate_POReference
  ON public.c_invoice_candidate
  (POReference);
COMMENT ON INDEX public.c_invoice_candidate_POReference
  IS 'this index is required to speed up user filtering';
