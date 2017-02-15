
-- declare the function to load rows into the massmigrate_records table
DROP FUNCTION IF EXISTS dlm.load_production_table_rows(text, int);
CREATE OR REPLACE FUNCTION dlm.load_production_table_rows(IN p_tableName text DEFAULT NULL, IN p_limit int DEFAULT 1000000)
  RETURNS TABLE (TableName varchar, UpdateCount int, Massmigrate_ID int)
  AS
$BODY$
DECLARE 
	v_insert_sql character varying;
	v_inserted integer;
	v_config_line record;
BEGIN	
	-- 
	SELECT m.massmigrate_id, t.TableName, m.WhereClause INTO v_config_line
	FROM dlm.massmigrate m
		JOIN AD_Table t ON t.AD_Table_ID=m.AD_Table_ID
	WHERE status IN ('pending', 'load_rows')
		AND lower(COALESCE(p_tableName, t.TableName)) = lower(t.TableName)
	ORDER BY m.massmigrate_id -- we order it just to be somewhat predictable
	LIMIT 1;

	IF v_config_line IS NULL
	THEN
		RAISE INFO 'update_production_table: Found no dlm.massmigrate record with status=''update_rows''; p_tableName=%; nothing to do.', p_tableName;	
		RETURN QUERY SELECT NULL AS TableName, 0 AS UpdateCount, NULL AS MassMigrate_ID;
	END IF;
	
	UPDATE dlm.massmigrate m set status='load_rows' where m.massmigrate_id=v_config_line.massmigrate_id;
	
	v_insert_sql :=
	'INSERT INTO dlm.massmigrate_records (massmigrate_id, TableName, Record_ID)
	SELECT '||
		v_config_line.massmigrate_id||','''|| 
		v_config_line.TableName||''','||
		v_config_line.TableName||'_ID
	FROM '||v_config_line.TableName||'
	WHERE '||v_config_line.WhereClause||'
		AND COALESCE('||v_config_line.TableName||'.DLM_Level,0) < 2
		/* don''t insert records that were already inserted */
		AND NOT EXISTS (select 1 from dlm.massmigrate_records r where r.Record_ID='||v_config_line.TableName||'_ID AND r.TableName='''||v_config_line.TableName||''')
	LIMIT '||p_limit||'
	;';
	
	RAISE INFO 'load_production_table_rows - %: Going to load references to table rows into dlm.massmigrate_records using WHERE=% and LIMIT=%', 
		v_config_line.TableName, v_config_line.WhereClause, p_limit;
	--RAISE NOTICE 'Going to execute %', v_insert_sql;
	EXECUTE v_insert_sql;
	GET DIAGNOSTICS v_inserted = ROW_COUNT;
	RAISE INFO 'load_production_table_rows - %: Inserted % references into dlm.massmigrate_records', v_config_line.TableName, v_inserted;
	
	IF v_inserted = 0 THEN
		RAISE INFO 'load_production_table_rows - %: Update status of massmigrate_id=% row to "done", because there were no matching rows to update.', v_config_line.TableName, v_config_line.massmigrate_id;
		UPDATE dlm.massmigrate m set status='done' where m.massmigrate_id=v_config_line.massmigrate_id;
	ELSIF v_inserted >= p_limit THEN
		RAISE INFO 'load_production_table_rows - %: Update status of massmigrate_id=% row to "load_rows", because there are still rows to load left.', v_config_line.TableName, v_config_line.massmigrate_id;
		UPDATE dlm.massmigrate m set status='load_rows' where m.massmigrate_id=v_config_line.massmigrate_id;
	ELSE
		--we now inserted all the rows go to next stage
		RAISE INFO 'load_production_table_rows - %: Update status of massmigrate_id=% row to "update_rows" because all rows were loaded now.', v_config_line.TableName, v_config_line.massmigrate_id;
		UPDATE dlm.massmigrate m set status='update_rows' where m.massmigrate_id=v_config_line.massmigrate_id;
	END IF;
	
	RETURN QUERY SELECT v_config_line.TableName, v_inserted, v_config_line.massmigrate_id;
END
$BODY$
	LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION dlm.load_production_table_rows(text, int) IS
'Function to get rows into the dlm.massmigrate_records table.
This function works in tandem with the update_production_table function.
If a not-null p_tableName is given (case-insensitive), then only dlm.massmigrate records which reference the respective table are considered. 
The optional int param (default=100000) tells the function how many rows to load each time.
Note that it''s allowed to update dlm.massmigrate back to "pending" or "load_rows" and rerun this function, in order to pick up (new) records that were not yet matched by earlier runs.
But also note that as of now, this function does not care for records which were added to massmigrate_records earlier, but do not qualify anymore!
';
