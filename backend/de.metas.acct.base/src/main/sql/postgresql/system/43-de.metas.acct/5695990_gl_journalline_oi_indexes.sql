drop INDEX if exists sap_gljournalline_oi_invoice;
CREATE INDEX sap_gljournalline_oi_invoice ON sap_gljournalline (oi_invoice_id);

drop INDEX if exists sap_gljournalline_oi_payment;
CREATE INDEX sap_gljournalline_oi_payment ON sap_gljournalline (oi_payment_id);

drop INDEX if exists sap_gljournalline_oi_bankstatementline;
CREATE INDEX sap_gljournalline_oi_bankstatementline ON sap_gljournalline (oi_bankstatementline_id, oi_bankstatement_id);

