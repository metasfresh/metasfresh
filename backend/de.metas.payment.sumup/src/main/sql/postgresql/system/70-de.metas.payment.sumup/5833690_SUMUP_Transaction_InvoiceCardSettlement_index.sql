-- Lookup index on the settlement reference of SumUp transactions.
CREATE INDEX sumup_transaction_invoicecardsettlement ON SUMUP_Transaction (C_POS_InvoiceCardSettlement_ID)
;
