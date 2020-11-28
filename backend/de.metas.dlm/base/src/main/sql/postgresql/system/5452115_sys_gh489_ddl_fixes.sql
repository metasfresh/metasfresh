
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
		SELECT r.' || fk_info.column_name || ', COALESCE(r.DLM_Level, current_setting(''metasfresh.DLM_Coalesce_Level'')::smallint)
		INTO v_referencing_id, v_referencing_level
		FROM '|| fk_info.table_name ||' r
		WHERE 
			r.'|| fk_info.column_name || '= NEW.'|| fk_info.foreign_column_name ||' /* A '|| fk_info.column_name ||'-record that references the record whose DLM_Level we want to update */
			AND COALESCE(r.DLM_Level, current_setting(''metasfresh.DLM_Coalesce_Level'')::smallint) != NEW.DLM_Level; /* DLM_Level of that record is, so the record would effectively be "left behind" with a dangling reference */

		IF v_referencing_id IS NOT NULL
		THEN
			RAISE EXCEPTION 
			''ERROR: Migrating the '|| fk_info.foreign_table_name ||' record with '|| fk_info.foreign_column_name ||'=% to DLM_Level=% violates the constraint trigger dlm_' || fk_info.constraint_name || '_tg'', 
				NEW.'|| fk_info.foreign_column_name ||', NEW.DLM_Level
			USING ERRCODE = ''235D3'', /* ''23503'' is defined as foreign_key_violation.. we use 235D3, with "D" for DLM */
				HINT=''The '|| fk_info.table_name ||' record with '|| fk_info.foreign_column_name ||'=''|| v_referencing_id ||'' and DLM_Level=''|| v_referencing_level ||'' still references that record via its '||fk_info.column_name||' column'',
				DETAIL=''DLM_Referenced_Table_Name='|| fk_info.foreign_table_name ||'; DLM_Referencing_Table_Name='|| fk_info.table_name ||'; DLM_Referencig_Column_Name='||fk_info.column_name||';'' /* shall be parsable by metasfresh*/
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
				DETAIL=''DLM_Referenced_Table_Name='|| fk_info.foreign_table_name ||';DLM_Referencing_Table_Name='|| fk_info.table_name ||'; DLM_Referencig_Column_Name='||fk_info.column_name||';'' /* shall be parsable by metasfresh*/
			;
		END IF;
	END IF;
RETURN NULL;
END; $BODY$
  LANGUAGE plpgsql VOLATILE;
COMMENT ON function  dlm.' || fk_info.constraint_name || '_tgfn() IS ''Trigger function for trigger dlm_' || fk_info.constraint_name || '_tg (on table '||fk_info.foreign_table_name||'). For more details, see view dlm.triggers and the comments within this function.'';' AS create_dlm_triggerfunction_ddl
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

Note that 23503 is an official error code, defined as "foreign_key_violation".
Also, please note that the "DETAIL" part of the exception that the trigger functions raise are parsed by metasfresh, so please be carefull when changing them and update the DLMExceptionWrapperTests unit tests accordingly';

DROP FUNCTION IF EXISTS dlm.get_dlm_records(text, numeric(10,0)) CASCADE;
CREATE OR REPLACE FUNCTION dlm.get_dlm_records(p_Table_Name text, p_DLM_Partition_ID numeric(10,0))
	RETURNS TABLE(DLM_Partition_ID numeric(10,0), 
		Record_ID numeric(10,0),
		DLM_Level smallint,
		AD_Client_ID numeric(10,0),
		AD_Org_ID numeric(10,0),
		Created timestamp with time zone,
		CreatedBy numeric(10,0),
		Updated timestamp with time zone,
		UpdatedBy numeric(10,0),
		IsActive character(1)
		) AS
$BODY$
BEGIN
	RETURN QUERY EXECUTE 
		'SELECT '||p_DLM_Partition_ID||'::numeric(10,0), '||p_Table_Name||'_ID::numeric(10,0) AS Record_ID, '||
		   'DLM_Level, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive '||
		'FROM '||p_Table_Name||' WHERE DLM_Partition_ID='||p_DLM_Partition_ID||';';
END;	
$BODY$
	LANGUAGE plpgsql STABLE;

CREATE VIEW DLM_Partition_Records AS
SELECT 
	c.DLM_Partition_Config_ID, p.DLM_Partition_ID, t.TableName, t.IsDLM, t.AD_Table_ID, 
	records.Record_ID AS DLM_Partition_Records_ID,
	records.AD_Client_ID,
	records.DLM_Level,
	records.AD_Org_ID,
	records.Record_ID, 
	records.Created,
	records.CreatedBy,
	records.Updated,
	records.UpdatedBy,
	records.IsActive
FROM DLM_Partition p
	JOIN DLM_Partition_Config c 
			ON p.DLM_Partition_Config_ID=c.DLM_Partition_Config_ID
	JOIN DLM_Partition_Config_Line l 
			ON l.DLM_Partition_Config_ID=c.DLM_Partition_Config_ID
		JOIN AD_Table t 
				ON t.AD_Table_ID=l.DLM_Referencing_Table_ID

	JOIN LATERAL (SELECT * FROM dlm.get_dlm_records(t.TableName, p.DLM_Partition_ID) ) records ON t.IsDLM='Y'
;

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
		EXECUTE 'ALTER TABLE ' || p_table_name || '_tbl ADD COLUMN DLM_Partition_ID numeric(10,0);';
		RAISE NOTICE 'Added column DLM_Partition_ID to table %', p_table_name||'_tbl';
	EXCEPTION
		WHEN duplicate_column THEN RAISE NOTICE 'Column DLM_Partition_ID already exists in %. Nothing do to', p_table_name||'_tbl';
    END;

	/* Generally, Note that we create indices concurrently because this DB-function might be executed on large tables and during production. 
	   See https://www.postgresql.org/docs/9.5/static/sql-createindex.html#SQL-CREATEINDEX-CONCURRENTLY
	 */
	/* Non-partial indices; they large and grow with the table, but so does everything else, and at most times the DBMS will only have to keep those blocks in memory that have DLM_Level=0.
	   And this way we have the flexibility to go with current_setting('metasfresh.DLM_Level').
	*/
	EXECUTE 'CREATE INDEX CONCURRENTLY IF NOT EXISTS ' || p_table_name || '_DLM_Level ON ' || p_table_name || '_tbl (DLM_Level)';   
	EXECUTE 'CREATE INDEX CONCURRENTLY IF NOT EXISTS ' || p_table_name || '_DLM_Partition_ID ON ' || p_table_name || '_tbl (DLM_Partition_ID)';   
	RAISE NOTICE 'Created indices %_DLM_Level and %_DLM_Partition_ID', p_table_name, p_table_name;
	
	EXECUTE 'CREATE VIEW dlm.' || p_table_name || ' AS SELECT * FROM ' || p_table_name || '_tbl WHERE COALESCE(DLM_Level, current_setting(''metasfresh.DLM_Coalesce_Level'')::smallint) <= current_setting(''metasfresh.DLM_Level'')::smallint;';
	EXECUTE 'COMMENT ON VIEW dlm.' || p_table_name || ' IS ''This view selects records according to the metasfresh.DLM_Coalesce_Level and metasfresh.DLM_Level DBMS parameters. See task gh #489'';';
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
* Adds a DLM_Level and DLM_Partition_ID column to the table.
* Renames the table to "<tablename>_tbl" and creates a view named <tablename> that selects * from the table, but has a where-clause to make it select only records with DLM_Level <= current_setting(''metasfresh.DLM_Level'')
* Creates an index for the new DLM_Level column
* iterates "incoming" FK constraints for the table and creates a trigger&triggerfunction to avoid "dangling" references in case a record''s DLM_Level is increased. See the view dlm.triggers for more details.
* Does an analyze on the table
';

