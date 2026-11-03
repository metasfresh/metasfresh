drop index if exists ad_eventlog_ad_table_id;
CREATE INDEX ad_eventlog_ad_table_id ON ad_eventlog (ad_table_id);

drop index if exists ad_eventlog_record_id_ad_table_id;
CREATE INDEX ad_eventlog_record_id_ad_table_id ON ad_eventlog (record_id, ad_table_id)
;

