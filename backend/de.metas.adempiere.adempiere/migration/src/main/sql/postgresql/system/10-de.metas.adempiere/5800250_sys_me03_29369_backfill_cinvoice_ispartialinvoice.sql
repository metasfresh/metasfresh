-- 2026-04-28 https://github.com/metasfresh/me03/issues/29369
-- Iter 3 backfill: C_Invoice.IsPartialInvoice from doctype default for pre-iter-3 invoices.
-- Informational only — per R13 (revised 2026-04-28), iter-3 only acts on orders that reach the Delivery
-- step after iter-3 deploys; existing invoices' flag value is never read by iter-3 logic.

SELECT backup_table('c_invoice', '_iter3_ispartialinvoice_bkp');

UPDATE C_Invoice i
SET IsPartialInvoice = COALESCE(dt.IsPartialInvoice, 'Y')
FROM C_DocType dt
WHERE dt.C_DocType_ID = i.C_DocType_ID;
