
DROP VIEW IF EXISTS dlm.indices;
CREATE OR REPLACE VIEW dlm.indices AS 
SELECT 
	c_t.relname table_name,
	c_i.relname index_name,
	pg_relation_size(c_i.relname::regclass) AS current_index_size,
	pg_size_pretty(pg_relation_size(c_i.relname::regclass)) AS current_index_size_pretty,
	pg_get_indexdef(indexrelid) || ';' AS current_index_create_ddl,
	'DROP INDEX IF EXISTS ' || c_i.relname || ';' AS index_drop_ddl,
	pg_get_indexdef(indexrelid) ||
	CASE
		/* prepend the "where" and the condition */
		WHEN NOT pg_get_indexdef(indexrelid) ILIKE '% WHERE %' THEN ' WHERE COALESCE(dlm_level, 0::smallint) = 0::smallint'
		
		/* just prepend the condition, there is already a "where"  */
		WHEN pg_get_indexdef(indexrelid) ILIKE '% WHERE %' AND NOT pg_get_indexdef(indexrelid) ILIKE ' WHERE%COALESCE(dlm_level, 0::smallint) = 0::smallint%' THEN ' AND COALESCE(dlm_level, 0::smallint) = 0::smallint' 
		
		/* do nothing */
		WHEN pg_get_indexdef(indexrelid) ILIKE ' WHERE %COALESCE(dlm_level, 0::smallint) = 0::smallint%' THEN ''
	END || ';' AS new_index_create_ddl
FROM pg_index i
JOIN pg_class c_t ON c_t.oid = i.indrelid
JOIN pg_class c_i ON c_i.oid = i.indexrelid
WHERE true 
	AND c_t.relname LIKE '%_tbl'
	AND NOT i.IndIsPrimary
	AND NOT i.indIsUnique
	AND NOT c_i.relname ILIKE '%_dlm_level' /* dont't fiddle with "our" DLM indexes, that's already done elsewhere */
ORDER BY index_name;
COMMENT ON VIEW dlm.indices IS 'This view *was* used by the functions that DLM and un-DLM tables. Currently it seems as if we won''t need it in future';


DROP VIEW IF EXISTS dlm.triggers;
CREATE OR REPLACE VIEW dlm.triggers AS
SELECT 
	fk_info.*,
------------------------- DDL to drop the trigger ------------------------- 
	'DROP TRIGGER IF EXISTS dlm_' || fk_info.constraint_name || '_tg ON '|| fk_info.foreign_table_name ||';' AS drop_dlm_trigger_ddl,
	
------------------------- DDL to create the trigger ------------------------- 
	'DROP TRIGGER IF EXISTS dlm_' || fk_info.constraint_name || '_tg ON '|| fk_info.foreign_table_name ||';
CREATE CONSTRAINT TRIGGER dlm_' || fk_info.constraint_name || '_tg
AFTER UPDATE OF DLM_Level ON '|| fk_info.foreign_table_name ||'
DEFERRABLE INITIALLY DEFERRED
FOR EACH ROW
WHEN (NEW.DLM_Level > 0) /* only fire when we migrate out of production */
EXECUTE PROCEDURE dlm.' || fk_info.constraint_name || '_tgfn();' AS create_dlm_trigger_ddl,

------------------------- DDL to create the trgger function ------------------------- 
	'DROP FUNCTION IF EXISTS dlm.' || fk_info.constraint_name || '_tgfn();
CREATE OR REPLACE FUNCTION dlm.' || fk_info.constraint_name || '_tgfn()
  RETURNS trigger AS
$BODY$
DECLARE
	v_referencing_id numeric(10,0);
	v_referencing_level smallint;
BEGIN
	/* check if the referencing table has a DLM_Level column after all. In both cases, we will raise an exception, but with a different error code, because it needs to be handled differently */
	/* TODO: consider never selecting the DLM_Level of the referencing table, because we might not need the information and that way we would only need to have different error code and message, but could use most of the code for both cases */
	IF EXISTS (SELECT column_name FROM information_schema.columns WHERE table_name='''|| fk_info.table_name ||''' and column_name=''dlm_level'')
	THEN
		SELECT r.' || fk_info.column_name || ', r.DLM_Level
		INTO v_referencing_id, v_referencing_level
		FROM '|| fk_info.table_name ||' r
		WHERE 
			r.'|| fk_info.column_name || '= NEW.'|| fk_info.foreign_column_name ||'
			AND COLAESCE(r.DLM_Level,0)=0;

		IF v_referencing_id IS NOT NULL
		THEN
			RAISE EXCEPTION 
			''ERROR: Migrating the '|| fk_info.foreign_table_name ||' record with '|| fk_info.foreign_column_name ||'=% to DLM_Level=% violates the constraint trigger dlm_' || fk_info.constraint_name || '_tg'', 
				NEW.'|| fk_info.foreign_column_name ||', NEW.DLM_Level
			USING ERRCODE = ''235D3'', /* ''23503'' is defined as foreign_key_violation.. we use 235D3, with "D" for DLM */
				HINT=''The '|| fk_info.table_name ||' record with '|| fk_info.foreign_column_name ||'=''|| v_referencing_id ||'' and DLM_Level=''|| v_referencing_level ||'' still references that record via its '||fk_info.column_name||' column'',
				DETAIL=''DLM_Referencing_Table_Name='|| fk_info.table_name ||'; DLM_Referencig_Column_Name='||fk_info.column_name||';'' /* shall be parsable by metasfresh*/
			;
		END IF;
	ELSE
		SELECT r.' || fk_info.column_name || '
		INTO v_referencing_id
		FROM '|| fk_info.table_name ||' r
		WHERE 
			r.'|| fk_info.column_name || '= NEW.'|| fk_info.foreign_column_name ||'	;

		IF v_referencing_id IS NOT NULL
		THEN
			RAISE EXCEPTION 
			''ERROR: Migrating the '|| fk_info.foreign_table_name ||' record with '|| fk_info.foreign_column_name ||'=% to DLM_Level=% violates the constraint trigger dlm_' || fk_info.constraint_name || '_tg'', 
				NEW.'|| fk_info.foreign_column_name ||', NEW.DLM_Level
			USING ERRCODE = ''235D3'', /* ''23503'' is defined as foreign_key_violation.. we use 235D3, with "D" for DLM */
				HINT=''The '|| fk_info.table_name ||' record with '|| fk_info.foreign_column_name ||'=''|| v_referencing_id ||'' and *no* DLM_Level column still references that record via its '||fk_info.column_name||' column'',
				DETAIL=''DLM_Referencing_Table_Name='|| fk_info.table_name ||'; DLM_Referencig_Column_Name='||fk_info.column_name||';'' /* shall be parsable by metasfresh*/
			;
		END IF;
	END IF;
RETURN NULL;
END; $BODY$
  LANGUAGE plpgsql VOLATILE;
COMMENT ON function  dlm.' || fk_info.constraint_name || '_tgfn() IS ''See view dlm.triggers for details.'';' AS create_dlm_triggerfunction_ddl
-------------------------
FROM ( 
	-- thanks to http://stackoverflow.com/questions/1152260/postgres-sql-to-list-table-foreign-keys
	SELECT
	    tc.constraint_name, 
	    tc.table_name, 
	    kcu.column_name, 
	    ccu.table_name AS foreign_table_name,
	    ccu.column_name AS foreign_column_name 
	FROM 
	    information_schema.table_constraints AS tc 
	    JOIN information_schema.key_column_usage AS kcu
	      ON tc.constraint_name = kcu.constraint_name
	    JOIN information_schema.constraint_column_usage AS ccu
	      ON ccu.constraint_name = tc.constraint_name
	WHERE tc.constraint_type = 'FOREIGN KEY'
	) fk_info
;
COMMENT ON VIEW dlm.triggers IS
'gh #235, #489: selects foreign key constraints and creates the DDL for a trigger and trigger-function that is analog to the respective FK constraint. 
The trigger checks if the referencing records are within the same DLM_Level (or are DLM''ed at all).
If this is not the case, then the trigger-function raises an exception with error-code: 
* 235D3 if the referencing table has a DLM_Level column, but with a highter value.
* 235N3 if the referncing table does not have a DLM_Level column.

In both cases, the change of the DLM_Level column of the referenced table is not allowed to happen, because it would mean that for metasfresh as seen from the referencing record,
the referened record would vanish.

Note that 23503 is defined as "foreign_key_violation"';



CREATE OR REPLACE FUNCTION dlm.add_table_to_dlm(p_table_name text)
  RETURNS void AS
$BODY$
DECLARE
    -- v_index_view_row dlm.indices;
	v_trigger_view_row dlm.triggers;
BEGIN
	EXECUTE 'ALTER TABLE ' || p_table_name || ' RENAME TO ' || p_table_name || '_tbl;';
	RAISE NOTICE 'Renamed table % to %', p_table_name, p_table_name||'_tbl';
	
	BEGIN
		EXECUTE 'ALTER TABLE ' || p_table_name || '_tbl ADD COLUMN DLM_Level smallint;'; /* using smallint, see https://www.postgresql.org/docs/9.1/static/datatype-numeric.html */
		RAISE NOTICE 'Added column DLM_Level to table %', p_table_name||'_tbl';
	EXCEPTION
		WHEN duplicate_column THEN RAISE NOTICE 'Column DLM_Level already exists in %. Nothing do to', p_table_name||'_tbl';
    END;
		BEGIN
		EXECUTE 'ALTER TABLE ' || p_table_name || '_tbl ADD COLUMN DLM_Partion_ID numeric(10,0);';
		RAISE NOTICE 'Added column DLM_Partion_ID to table %', p_table_name||'_tbl';
	EXCEPTION
		WHEN duplicate_column THEN RAISE NOTICE 'Column DLM_Partion_ID already exists in %. Nothing do to', p_table_name||'_tbl';
    END;
	
	/* 
	   partial index; we *wanted* postgresql to actually pick this one, because it's small and doesn't grow with the table (as long as we manage to limit the number of "production" records).
	   But we can't have current_setting('metasfresh.DLM_Level') in the index predicate (a.k.a. where-clause) because current_setting() is not an immutable function.
	   Also see http://stackoverflow.com/a/26031289/1012103
	EXECUTE 'CREATE INDEX ' || p_table_name || '_DLM_Level ON ' || p_table_name || '_tbl (COALESCE(DLM_Level,0::smallint)) WHERE COALESCE(DLM_Level,0::smallint) = 0::smallint;'; 
	RAISE NOTICE 'Created index %_DLM_Level', p_table_name;
	*/
	
	/* 
	   non-partial index; it's large and grows with the table, but so does everything else, and the DBMS will only have to keep those blocks in memory that have DLM_Level=0.
	   And this way we have the flexibility to go with current_setting('metasfresh.DLM_Level').
	*/
	EXECUTE 'CREATE INDEX ' || p_table_name || '_DLM_Level ON ' || p_table_name || '_tbl (COALESCE(DLM_Level,0::smallint))';   
	
	EXECUTE 'CREATE VIEW dlm.' || p_table_name || ' AS SELECT * FROM ' || p_table_name || '_tbl WHERE COALESCE(DLM_Level,0::smallint) <= current_setting(''metasfresh.DLM_Level'')::smallint;';
	RAISE NOTICE 'Created view dlm.%', p_table_name;

	FOR v_trigger_view_row IN 
		EXECUTE 'SELECT * FROM dlm.triggers v WHERE lower(v.foreign_table_name) = lower('''|| p_table_name ||'_tbl'')'
	LOOP
		EXECUTE v_trigger_view_row.create_dlm_triggerfunction_ddl;
		EXECUTE v_trigger_view_row.create_dlm_trigger_ddl;
	
		RAISE NOTICE 'Created dlm trigger and trigger-function analog to FK constraint %', v_trigger_view_row.constraint_name;
	END LOOP;
	
	/* make sure that the DB actually takes note of what we just did */
	EXECUTE 'ANALYZE ' || p_table_name || '_tbl;'; 
	RAISE NOTICE 'Called ANALYZE %', p_table_name || '_tbl';
END;
$BODY$
  LANGUAGE plpgsql VOLATILE; 
COMMENT ON FUNCTION dlm.add_table_to_dlm(text) IS 'gh #235, #489: DLMs the given table:
* Adds a DLM_Level and DLM_Partion_ID column to the table.
* Renames the table to "<tablename>_tbl" and creates a view named <tablename> that selects * from the table, but has a where-clause to make it select only records with DLM_Level <= current_setting(''metasfresh.DLM_Level'')
* Creates an index for the new DLM_Level column
* iterates "incoming" FK constraints for the table and creates a trigger&triggerfunction to avoid "dangling" references in case a record''s DLM_Level is increased. See the view dlm.triggers for more details.
* Does an analyze on the table
';


CREATE OR REPLACE FUNCTION dlm.remove_table_from_dlm(p_table_name text, p_retain_dlm_column boolean DEFAULT true)
  RETURNS void AS
$BODY$
DECLARE 
	v_trigger_view_row dlm.triggers;
BEGIN
	EXECUTE 'DROP VIEW IF EXISTS dlm.' || p_table_name;
	EXECUTE 'DROP INDEX IF EXISTS ' || p_table_name || '_DLM_Level;';
	
	IF p_retain_dlm_column = false
	THEN
		EXECUTE 'ALTER TABLE ' || p_table_name || '_Tbl DROP COLUMN IF EXISTS DLM_Level;';
		EXECUTE 'ALTER TABLE ' || p_table_name || '_Tbl DROP COLUMN IF EXISTS DLM_Partion_ID;';
		RAISE NOTICE 'Dropped columns DLM_Level and DLM_Partion_ID from table % (if they existed)', p_table_name;
		
	ELSE
		RAISE NOTICE 'Retained columns DLM_Level and DLM_Partion_ID of table %', p_table_name;
	END IF;

	FOR v_trigger_view_row IN 
		EXECUTE 'SELECT * FROM dlm.triggers v WHERE lower(v.foreign_table_name) = lower('''|| p_table_name ||'_tbl'')'
	LOOP
		EXECUTE v_trigger_view_row.drop_dlm_trigger_ddl;
	
		RAISE NOTICE 'Dropped dlm trigger analog to FK constraint %', v_trigger_view_row.constraint_name;
	END LOOP;
	
	EXECUTE 'ALTER TABLE ' || p_table_name || '_Tbl RENAME TO ' || p_table_name || ';';
	RAISE NOTICE 'Renamed table % back to % ', p_table_name||'_Tbl', p_table_name;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION dlm.remove_table_from_dlm(text, boolean) IS 'gh #235, #489: Un-DLMs the given table:
* drops the view and removes the "_tbl" suffix from the table name
* drops partial indices
* drops the tiggers and triggerfunctions that were created using the dlm.triggers view.
* optionally drops the DLM_Level and DLM_Partion_ID column, if told so explicitly with the p_retain_dlm_column parameter set to false.'
;
