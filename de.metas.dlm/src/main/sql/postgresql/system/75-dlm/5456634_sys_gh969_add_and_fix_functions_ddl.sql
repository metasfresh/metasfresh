CREATE OR REPLACE FUNCTION dlm.enable_dlm_triggers(p_table_name text, p_silent boolean DEFAULT false)
  RETURNS void AS
$BODY$
DECLARE
	v_trigger_select_sql text;
	v_trigger_view_row dlm.triggers;
BEGIN
	v_trigger_select_sql = '
SELECT v.*
FROM dlm.triggers v 
	JOIN public.AD_Table t ON lower(t.TableName)=lower(v.table_name) 
			AND t.IsDLM=''Y'' /* ignore not-DLM tables because they might now even have e.g. DLMLevel columns */
		JOIN AD_Column c ON c.AD_Table_ID=t.AD_Table_ID AND lower(c.ColumnName)=lower(v.column_name)
WHERE c.IsDLMPartitionBoundary=''N''
	AND  lower(v.foreign_table_name) = lower('''|| p_table_name ||''')';
	
	/* Iterate the dlm.triggers view and enable the triggers for each FK constraint.
	 */
	FOR v_trigger_view_row IN 
		EXECUTE v_trigger_select_sql
	LOOP
		BEGIN
			EXECUTE v_trigger_view_row.enable_dlm_trigger_ddl;

			IF p_silent = FALSE THEN
				RAISE NOTICE 'enable_dlm_triggers - %: Enabled dlm trigger analog to FK constraint %', p_table_name, v_trigger_view_row.constraint_name;
			END IF;
		EXCEPTION
			WHEN undefined_object /*SQLState 42704*/ THEN RAISE WARNING 'enable_dlm_triggers - %: No such trigger, SQL statement % failed. Nothing do to.', p_table_name, v_trigger_view_row.enable_dlm_trigger_ddl;
		END;
	END LOOP;
END 
$BODY$
	LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION dlm.enable_dlm_triggers(text, boolean) IS 
'Uses the view dlm.triggers to enablle tiggers for the given p_table_name which were created using the dlm.triggers view.
If called with p_silent=false, is invokes "RAISE NOTICE" on each triggers that it enabled.
If if disabling an individual trigger fails, it logs a warning and goes on.
See gh #489.'
;

CREATE OR REPLACE FUNCTION dlm.disable_dlm_triggers(p_table_name text, p_silent boolean DEFAULT false)
  RETURNS void AS
$BODY$
DECLARE
	v_trigger_select_sql text;
	v_trigger_view_row dlm.triggers;
BEGIN
	v_trigger_select_sql = '
SELECT v.*
FROM dlm.triggers v 
	JOIN public.AD_Table t ON lower(t.TableName)=lower(v.table_name) 
			AND t.IsDLM=''Y'' /* ignore not-DLM tables because they might now even have e.g. DLMLevel columns */
		JOIN AD_Column c ON c.AD_Table_ID=t.AD_Table_ID AND lower(c.ColumnName)=lower(v.column_name)
WHERE c.IsDLMPartitionBoundary=''N''
	AND  lower(v.foreign_table_name) = lower('''|| p_table_name ||''')';

	/* Iterate the dlm.triggers view and disable the triggers for each FK constraint.
	 */
	FOR v_trigger_view_row IN 
		EXECUTE v_trigger_select_sql
	LOOP
		BEGIN
			EXECUTE v_trigger_view_row.disable_dlm_trigger_ddl;

			IF p_silent = FALSE THEN
				RAISE NOTICE 'disable_dlm_triggers - %: Disabled dlm trigger analog to FK constraint %', p_table_name, v_trigger_view_row.constraint_name;
			END IF;
		EXCEPTION
			WHEN undefined_object /*SQLState 42704*/ THEN RAISE WARNING 'No such trigger, SQL statement % failed. Nothing do to.', v_trigger_view_row.disable_dlm_trigger_ddl;
		END;
	END LOOP;
END 
$BODY$
	LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION dlm.disable_dlm_triggers(text) IS 
'Uses the view dlm.triggers to disable tiggers for the given p_table_name which were created using the dlm.triggers view.
If called with p_silent=false, is invokes "RAISE NOTICE" on each triggers that it enabled.
If if disabling an individual trigger fails, it logs a warning and goes on.
See gh #489.'
;


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
		RAISE INFO 'load_production_table_rows: Found no dlm.massmigrate record with status=''update_rows''; p_tableName=%; nothing to do.', p_tableName;	
		RETURN; -- see RETURN and RETURN QUERY in https://www.postgresql.org/docs/9.5/static/plpgsql-control-structures.html
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
	
	RETURN QUERY SELECT v_config_line.TableName::varchar, v_inserted, v_config_line.massmigrate_id;
	RETURN;
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

