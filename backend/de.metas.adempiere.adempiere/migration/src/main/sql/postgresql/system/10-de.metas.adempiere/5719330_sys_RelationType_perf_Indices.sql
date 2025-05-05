
CREATE INDEX if not exists API_Request_Audit_Log_AD_Table_ID ON api_request_audit_log (ad_table_ID);
COMMENT ON INDEX API_Request_Audit_Log_AD_Table_ID IS 'Created in order to support the DB-function table_record_reference_retrieve_distinct_ids(table, column) which in turn is used to find external references
when called via SpecificRelationTypeRelatedDocumentsProvider for AD_RelationTypes with IsTableRecordidTarget=Y';

CREATE INDEX if not exists ad_user_record_access_AD_Table_ID ON ad_user_record_access (ad_table_ID);
COMMENT ON INDEX ad_user_record_access_AD_Table_ID IS 'Created in order to support the DB-function table_record_reference_retrieve_distinct_ids(table, column) which in turn is used to find external references
when called via SpecificRelationTypeRelatedDocumentsProvider for AD_RelationTypes with IsTableRecordidTarget=Y';

CREATE INDEX if not exists c_doc_outbound_log_AD_Table_ID ON c_doc_outbound_log (ad_table_ID);
COMMENT ON INDEX c_doc_outbound_log_AD_Table_ID IS 'Created in order to support the DB-function table_record_reference_retrieve_distinct_ids(table, column) which in turn is used to find external references
when called via SpecificRelationTypeRelatedDocumentsProvider for AD_RelationTypes with IsTableRecordidTarget=Y';

CREATE INDEX if not exists c_doc_responsible_AD_Table_ID ON c_doc_responsible (ad_table_ID);
COMMENT ON INDEX c_doc_responsible_AD_Table_ID IS 'Created in order to support the DB-function table_record_reference_retrieve_distinct_ids(table, column) which in turn is used to find external references
when called via SpecificRelationTypeRelatedDocumentsProvider for AD_RelationTypes with IsTableRecordidTarget=Y';

CREATE INDEX if not exists data_export_audit_AD_Table_ID ON data_export_audit (ad_table_ID);
COMMENT ON INDEX data_export_audit_AD_Table_ID IS 'Created in order to support the DB-function table_record_reference_retrieve_distinct_ids(table, column) which in turn is used to find external references
when called via SpecificRelationTypeRelatedDocumentsProvider for AD_RelationTypes with IsTableRecordidTarget=Y';

CREATE INDEX if not exists modcntr_log_AD_Table_ID ON modcntr_log (ad_table_ID);
COMMENT ON INDEX modcntr_log_AD_Table_ID IS 'Created in order to support the DB-function table_record_reference_retrieve_distinct_ids(table, column) which in turn is used to find external references
when called via SpecificRelationTypeRelatedDocumentsProvider for AD_RelationTypes with IsTableRecordidTarget=Y';

CREATE INDEX if not exists ad_user_record_access_AD_Table_ID ON api_request_audit_log (ad_table_ID);
COMMENT ON INDEX ad_user_record_access_AD_Table_ID IS 'Created in order to support the DB-function table_record_reference_retrieve_distinct_ids(table, column) which in turn is used to find external references
when called via SpecificRelationTypeRelatedDocumentsProvider for AD_RelationTypes with IsTableRecordidTarget=Y';
