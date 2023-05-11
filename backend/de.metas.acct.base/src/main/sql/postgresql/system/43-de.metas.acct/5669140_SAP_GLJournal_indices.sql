DROP INDEX IF EXISTS sap_gljournalline_parent
;

CREATE INDEX sap_gljournalline_parent ON sap_gljournalline (sap_gljournal_id)
;

DROP INDEX IF EXISTS sap_gljournal_for_RV_UnPosted
;

CREATE INDEX sap_gljournal_for_RV_UnPosted ON sap_gljournal (posted, docstatus)
;
