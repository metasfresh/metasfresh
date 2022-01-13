
update ad_column set ddl_noforeignkey='Y' where AD_Table_ID=get_table_id('api_request_audit_log');
alter table api_request_audit_log DROP CONSTRAINT adissue_apirequestauditlog;
