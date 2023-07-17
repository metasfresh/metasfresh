UPDATE sap_gljournalline l
SET processed=(SELECT j.processed FROM sap_gljournal j WHERE j.sap_gljournal_id = l.sap_gljournal_id)
;
