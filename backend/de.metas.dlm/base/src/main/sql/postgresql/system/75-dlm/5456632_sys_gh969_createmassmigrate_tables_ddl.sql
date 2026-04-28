
-- create the tables
DROP TABLE IF EXISTS dlm.massmigrate;
CREATE TABLE dlm.massmigrate
(
  massmigrate_id SERIAL PRIMARY KEY,
  dlm_partition_config_id numeric(10,0),
  dlm_partition_config_line_id numeric(10,0),
  ad_table_id numeric(10,0),
  dlm_partition_id integer,
  whereclause character varying,
  status character varying
);
CREATE UNIQUE INDEX massmigrate_uc
  ON dlm.massmigrate
  USING btree
  (dlm_partition_config_line_id, dlm_partition_id);

DROP TABLE IF EXISTS dlm.massmigrate_records;
CREATE TABLE dlm.massmigrate_records
(
	massmigrate_records_id SERIAL PRIMARY KEY,
	massmigrate_id integer,
	TableName character varying,
	Record_ID numeric(10,0),
	IsDone character(1) NOT NULL DEFAULT 'N'
);
CREATE INDEX massmigrate_records_perf
  ON dlm.massmigrate_records
  USING btree
  (Record_ID, TableName);
CREATE INDEX massmigrate_records_perf2
  ON dlm.massmigrate_records
  USING btree
  (IsDone, Record_ID, TableName);
