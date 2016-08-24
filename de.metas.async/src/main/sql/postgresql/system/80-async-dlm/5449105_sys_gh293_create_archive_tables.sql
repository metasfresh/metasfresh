-- this is crude but i spend already the whole day with this task and need to move on.
-- we only want to create the tables if they don't exists yet
-- CREATE TABLE IF NOT EXISTS ... AS SELECT ... doesn'T work, at least not with pg-9.1

CREATE OR REPLACE FUNCTION create_tables_if_not_exist ()
  RETURNS void AS
$func$
BEGIN
   IF EXISTS (SELECT 1 FROM pg_catalog.pg_tables 
              WHERE  schemaname = 'dlm' AND tablename  = 'c_queue_workpackage_archived') THEN
      RAISE NOTICE 'Table dlm.c_queue_workpackage_archived already exists. Assuming that all the tables exist';
   ELSE

CREATE TABLE dlm.c_queue_workpackage_archived AS SELECT * FROM c_queue_workpackage LIMIT 0;
COMMENT ON TABLE dlm.c_queue_workpackage_archived IS 'Contains old records from the c_queue_workpackage table; see https://github.com/metasfresh/metasfresh/issues/293'; 

CREATE TABLE dlm.c_queue_workpackage_log_archived AS SELECT * FROM c_queue_workpackage_log LIMIT 0;
COMMENT ON TABLE dlm.c_queue_workpackage_log_archived IS 'Contains old records from the c_queue_workpackage_log table; see https://github.com/metasfresh/metasfresh/issues/293'; 

CREATE TABLE dlm.c_queue_workpackage_param_archived AS SELECT * FROM c_queue_workpackage_param LIMIT 0;
COMMENT ON TABLE dlm.c_queue_workpackage_param_archived IS 'Contains old records from the c_queue_workpackage_param table; see https://github.com/metasfresh/metasfresh/issues/293'; 

CREATE TABLE dlm.c_queue_element_archived AS SELECT * FROM c_queue_element LIMIT 0;
COMMENT ON TABLE dlm.c_queue_element_archived IS 'Contains old records from the c_queue_element table; see https://github.com/metasfresh/metasfresh/issues/293'; 

CREATE TABLE dlm.c_queue_block_archived AS SELECT * FROM c_queue_block LIMIT 0;
COMMENT ON TABLE dlm.c_queue_block_archived IS 'Contains old records from the C_Queue_Block table; see https://github.com/metasfresh/metasfresh/issues/293'; 


CREATE TABLE dlm.C_Queue_Workpackage_ToArchive_All AS SELECT C_Queue_Workpackage_ID, C_Queue_Block_ID FROM C_Queue_Workpackage LIMIT 0;
COMMENT ON TABLE dlm.C_Queue_Workpackage_ToArchive_All IS 'Contains C_queue_Workpackage records that will be moved for archive tables by future invocation(s) of the dlm.Archive_C_Queue_Data() function; see https://github.com/metasfresh/metasfresh/issues/293';

DROP INDEX IF EXISTS dlm.c_queue_workpackage_toarchive_all_c_queue_block_id_idx;
CREATE INDEX c_queue_workpackage_toarchive_all_c_queue_block_id_idx
  ON dlm.c_queue_workpackage_toarchive_all
  USING btree
  (c_queue_block_id);
DROP INDEX IF EXISTS dlm.c_queue_workpackage_toarchive_all_c_queue_workpackage_id_idx;
CREATE INDEX c_queue_workpackage_toarchive_all_c_queue_workpackage_id_idx
  ON dlm.c_queue_workpackage_toarchive_all
  USING btree
  (c_queue_workpackage_id);

     END IF;
END
$func$ LANGUAGE plpgsql;


select create_tables_if_not_exist();
drop function create_tables_if_not_exist();