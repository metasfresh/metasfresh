
DROP FUNCTION IF EXISTS dlm.update_production_table(text, int );
CREATE OR REPLACE FUNCTION dlm.update_production_table(IN p_tableName text DEFAULT NULL, IN p_limit int DEFAULT 1000000)
  RETURNS TABLE (TableName varchar, UpdateCount int, Massmigrate_ID int)
  AS
$BODY$
DECLARE 
	v_update_production_table_sql character varying;
	v_to_update integer;
	v_config_line record;
BEGIN
	SELECT m.massmigrate_id, m.DLM_Partition_ID, t.TableName INTO v_config_line
	FROM dlm.massmigrate m
		JOIN AD_Table t ON t.AD_Table_ID=m.AD_Table_ID
	WHERE status IN ('update_rows')
		AND lower(COALESCE(p_tableName, t.TableName)) = lower(t.TableName)
	ORDER BY m.massmigrate_id -- we order it just to be somewhat predictable
	LIMIT 1  /* we only want one table at a time to make disabling triggers and everything easier */
	;
	
	IF v_config_line IS NULL
	THEN
		RAISE INFO 'update_production_table: Found no dlm.massmigrate record with status=''update_rows''; p_tableName=%; nothing to do.', p_tableName;	
		RETURN; -- see RETURN and RETURN QUERY in https://www.postgresql.org/docs/9.5/static/plpgsql-control-structures.html
	END IF;
	
	RAISE INFO 'update_production_table - %: Going to load max % references from dlm.massmigrate_records into a temporary table', v_config_line.TableName, p_limit;

	-- create a temporary table and load 100000 records into it.
	-- those 100000 records reference the production-table records which we are going to update in this run of the function
	CREATE TEMPORARY TABLE massmigrate_records_temp AS
	SELECT r.massmigrate_records_ID, r.Record_ID, r.TableName
	FROM dlm.massmigrate_records r
	WHERE r.IsDone='N' AND r.massmigrate_id=v_config_line.massmigrate_id
	ORDER BY r.massmigrate_records_id
	LIMIT p_limit;

	GET DIAGNOSTICS v_to_update = ROW_COUNT;
	RAISE INFO 'update_production_table - %: Loaded % rows from dlm.massmigrate_records into a temporary table', v_config_line.TableName, v_to_update;	

	IF v_to_update > 0
	THEN
		-- there are records to update, so update the production table and also set the respective massmigrate_records rows to IsDone='Y'
		RAISE INFO 'update_production_table - %: Going to update % rows to DLM_Level=2', v_config_line.TableName, v_to_update;	

		-- disable DLM-triggers for table v_config_line.TableName
		PERFORM dlm.disable_dlm_triggers(v_config_line.TableName, true); /*p_silent=true*/
		
		v_update_production_table_sql :=	'
			UPDATE '||v_config_line.TableName||' 
			SET DLM_Level=2, DLM_Partition_ID='||v_config_line.DLM_Partition_ID||'
			FROM massmigrate_records_temp t 
			WHERE '||v_config_line.TableName||'_ID=t.Record_ID;';
		
		EXECUTE v_update_production_table_sql;
		
		-- re-enable DLM-triggers for table v_config_line.TableName
		PERFORM dlm.disable_dlm_triggers(v_config_line.TableName, true); /*p_silent=true*/
		
		UPDATE dlm.massmigrate_records r SET IsDone='Y'
		FROM massmigrate_records_temp t
		WHERE t.massmigrate_records_ID=r.massmigrate_records_ID;
	END IF;
	
	DROP TABLE massmigrate_records_temp;
	
	IF v_to_update < p_limit
	THEN
		-- no more rows to update for the current massmigrate; go to 'done'
		RAISE INFO 'update_production_table - %: Update status of massmigrate_id=% row to "done"', v_config_line.TableName, v_config_line.massmigrate_id;
		UPDATE dlm.massmigrate m set status='done' where m.massmigrate_id=v_config_line.massmigrate_id;
	END IF;

	RETURN QUERY SELECT v_config_line.TableName, v_to_update, v_config_line.massmigrate_id;
	RETURN;
END
$BODY$
	LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION dlm.update_production_table(text, int) IS 
'Function to update production rows which have a referencing row in dlm.massmigrate_records.
This function works in tandem with the load_production_table_rows function.
If a not-null p_tableName is given (case-insensitive), then only dlm.massmigrate records which reference the respective table are considered.
The optional int param (default=100000) tells the function how many rows to load each time.
';