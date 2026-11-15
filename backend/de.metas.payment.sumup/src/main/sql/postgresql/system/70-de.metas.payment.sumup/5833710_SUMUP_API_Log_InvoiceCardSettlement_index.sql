-- Lookup index on the settlement reference of SumUp API log rows.
CREATE INDEX sumup_api_log_invoicecardsettlement ON SUMUP_API_Log (C_POS_InvoiceCardSettlement_ID)
;
