-- Lookup indexes on the settlement reference columns of the POS tables.
CREATE INDEX c_pos_journalline_invoicecardsettlement ON C_POS_JournalLine (C_POS_InvoiceCardSettlement_ID)
;

CREATE INDEX c_pos_invoicecardsettlement_sumup_transaction ON C_POS_InvoiceCardSettlement (SUMUP_Transaction_ID)
;
