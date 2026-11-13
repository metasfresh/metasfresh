-- Lookup indexes on further settlement columns (full, non-unique; the partial unique index covers only blocking rows).
CREATE INDEX c_pos_invoicecardsettlement_c_pos ON C_POS_InvoiceCardSettlement (C_POS_ID)
;

CREATE INDEX c_pos_invoicecardsettlement_c_payment ON C_POS_InvoiceCardSettlement (C_Payment_ID)
;

CREATE INDEX c_pos_invoicecardsettlement_c_invoice ON C_POS_InvoiceCardSettlement (C_Invoice_ID)
;
