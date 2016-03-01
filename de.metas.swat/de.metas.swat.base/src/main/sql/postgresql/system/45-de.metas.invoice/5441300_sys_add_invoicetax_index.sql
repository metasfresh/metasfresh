
--
-- solving a performance problem
--
DROP INDEX IF EXISTS c_invoicetax_invoice;
CREATE INDEX c_invoicetax_invoice
  ON c_invoicetax
  USING btree
  (c_invoice_id );
