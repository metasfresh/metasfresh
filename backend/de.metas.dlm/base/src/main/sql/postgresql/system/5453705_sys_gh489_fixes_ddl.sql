
DROP VIEW IF EXISTS dlm.triggers;
CREATE OR REPLACE VIEW dlm.triggers AS
SELECT 
	fk_info.*,
------------------------- DDL to drop the trigger ------------------------- 
	'DROP TRIGGER IF EXISTS dlm_' || fk_info.constraint_name || '_tg ON '|| fk_info.foreign_table_name ||';' AS drop_dlm_trigger_ddl,
	
------------------------- DDL to create the trigger ------------------------- 
	'DROP TRIGGER IF EXISTS dlm_' || fk_info.constraint_name || '_tg ON public.'|| fk_info.foreign_table_name ||';
CREATE CONSTRAINT TRIGGER dlm_' || fk_info.constraint_name || '_tg
AFTER UPDATE OF DLM_Level ON public.'|| fk_info.foreign_table_name ||'
DEFERRABLE INITIALLY DEFERRED
FOR EACH ROW
WHEN (NEW.DLM_Level > OLD.DLM_Level) /* only fire when we migrate a record out of other records'' visiblility */
EXECUTE PROCEDURE dlm.' || fk_info.constraint_name || '_tgfn();
COMMENT ON TRIGGER dlm_' || fk_info.constraint_name || '_tg ON public.'|| fk_info.foreign_table_name ||'
  IS ''Trigger to fire when we might migrate a '||fk_info.foreign_table_name||' record out of the visiblility of a '||fk_info.table_name||' record'';'
AS create_dlm_trigger_ddl,

------------------------- DDL to create the trgger function ------------------------- 
	'DROP FUNCTION IF EXISTS dlm.' || fk_info.constraint_name || '_tgfn() CASCADE;
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
		FROM public.'|| fk_info.table_name ||' r
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
				DETAIL=''DLM_Referenced_Table_Name='|| fk_info.foreign_table_name ||'; DLM_Referenced_Record_ID=''|| v_referencing_id ||''; DLM_Referencing_Table_Name='|| fk_info.table_name ||'; DLM_Referencig_Column_Name='||fk_info.column_name||';'' /* shall be parsable by metasfresh*/
			;
		END IF;
	ELSE
		SELECT r.' || fk_info.column_name || '
		INTO v_referencing_id
		FROM public.'|| fk_info.table_name ||' r
		WHERE 
			r.'|| fk_info.column_name || '= NEW.'|| fk_info.foreign_column_name ||'	;

		IF v_referencing_id IS NOT NULL
		THEN
			RAISE EXCEPTION 
			''ERROR: Migrating the '|| fk_info.foreign_table_name ||' record with '|| fk_info.foreign_column_name ||'=% to DLM_Level=% violates the constraint trigger dlm_' || fk_info.constraint_name || '_tg'', 
				NEW.'|| fk_info.foreign_column_name ||', NEW.DLM_Level
			USING ERRCODE = ''235D3'', /* ''23503'' is defined as foreign_key_violation.. we use 235D3, with "D" for DLM */
				HINT=''The '|| fk_info.table_name ||' record with '|| fk_info.foreign_column_name ||'=''|| v_referencing_id ||'' and *no* DLM_Level column still references that record via its '||fk_info.column_name||' column'',
				DETAIL=''DLM_Referenced_Table_Name='|| fk_info.foreign_table_name ||'; DLM_Referenced_Record_ID=''|| v_referencing_id ||''; DLM_Referencing_Table_Name='|| fk_info.table_name ||'; DLM_Referencig_Column_Name='||fk_info.column_name||';'' /* shall be parsable by metasfresh*/
			;
		END IF;
	END IF;
RETURN NULL;
END; $BODY$
  LANGUAGE plpgsql STABLE;
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
		AND tc.constraint_schema = 'public'
	) fk_info
;
COMMENT ON VIEW dlm.triggers IS
'gh #235, #489: selects foreign key constraints from the "public" schema and creates the DDL for a trigger and trigger-function that is analog to the respective FK constraint. 
The trigger checks if the referencing records are within the same DLM_Level (or are DLM''ed at all).
If this is not the case, then the trigger-function raises an exception with error-code: 
* 235D3 if the referencing table has a DLM_Level column, but with a highter value.
* 235N3 if the referncing table does not have a DLM_Level column.

In both cases, the change of the DLM_Level column of the referenced table is not allowed to happen, because it would mean that for metasfresh as seen from the referencing record,
the referened record would vanish.

Note that 23503 is an official error code, defined as "foreign_key_violation".
Also, please note that the "DETAIL" part of the exception that the trigger functions raise are parsed by metasfresh, so please be carefull when changing them and update the DLMExceptionWrapperTests unit tests accordingly
Finally, note that the FK-constraint from the table "report.accounting_facts_mv" to the table "public.fact_acct" is ignored, just like any other FK-constraint that is not defined in the public schema. 
The reason is that at least currently only tables from the public schema can be DLM''ed.

Final hint: if you change this view, you can then run the following to recreate existing triggers and trigger functions: 
 select tablename, dlm.recreate_dlm_triggers(tablename) from ad_table where isdlm=''Y'' order by tablename; 
';

CREATE OR REPLACE FUNCTION dlm.drop_dlm_triggers(p_table_name text)
  RETURNS void AS
$BODY$
DECLARE
	v_trigger_view_row dlm.triggers;
BEGIN
	/* Iterate the dlm.triggers view and drop the triggers for each FK constraint.
	 */
	FOR v_trigger_view_row IN 
		EXECUTE 'SELECT * FROM dlm.triggers v WHERE lower(v.foreign_table_name) = lower('''|| p_table_name ||''')'
	LOOP
		EXECUTE v_trigger_view_row.drop_dlm_trigger_ddl;
	
		RAISE NOTICE 'drop_dlm_triggers - %: Dropped dlm trigger analog to FK constraint %', p_table_name, v_trigger_view_row.constraint_name;
	END LOOP;
END 
$BODY$
	LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION dlm.create_dlm_triggers(text) IS 
'Uses the view dlm.triggers to drop the tiggers and triggerfunctions that were created using the dlm.triggers view.
See gh #489.'
;

CREATE OR REPLACE FUNCTION dlm.create_dlm_triggers(p_table_name text)
  RETURNS void AS
$BODY$
DECLARE
	v_trigger_view_row dlm.triggers;
BEGIN
	/* Iterate the dlm.triggers view and create triggers for each FK constraint.
	 */
	FOR v_trigger_view_row IN 
		EXECUTE 'SELECT * FROM dlm.triggers v WHERE lower(v.foreign_table_name) = lower('''|| p_table_name ||''')'
	LOOP
		EXECUTE v_trigger_view_row.create_dlm_triggerfunction_ddl;
		EXECUTE v_trigger_view_row.create_dlm_trigger_ddl;
	
		RAISE NOTICE 'create_dlm_triggers - %: Created dlm trigger and trigger-function analog to FK constraint %', p_table_name, v_trigger_view_row.constraint_name;
	END LOOP;
END 
$BODY$
	LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION dlm.create_dlm_triggers(text) IS 
'Uses the view dlm.triggers to iterate "incoming" FK constraints for the table and creates a trigger&triggerfunction to avoid "dangling" references in case a record''s DLM_Level is increased. See the view dlm.triggers for more details.
See gh #489.';

CREATE OR REPLACE FUNCTION dlm.remove_table_from_dlm(p_table_name text, p_retain_dlm_column boolean DEFAULT true)
  RETURNS void AS
$BODY$
DECLARE 
	v_trigger_view_row dlm.triggers;
BEGIN
	EXECUTE 'DROP VIEW IF EXISTS dlm.' || p_table_name;
	EXECUTE 'DROP INDEX IF EXISTS ' || p_table_name || '_DLM_Level;';

	/* drop the triggers first, because they depends on the DLM_Level column (important in case of p_retain_dlm_column=false). */
 	PERFORM dlm.drop_dlm_triggers(p_table_name);
	
	IF p_retain_dlm_column = false
	THEN
		EXECUTE 'ALTER TABLE ' || p_table_name || ' DROP COLUMN IF EXISTS DLM_Level;';
		EXECUTE 'ALTER TABLE ' || p_table_name || ' DROP COLUMN IF EXISTS DLM_Partition_ID;';
		RAISE NOTICE 'remove_table_from_dlm - %: Dropped columns DLM_Level and DLM_Partition_ID from table % (if they existed)', p_table_name, p_table_name;
		
	ELSE
		RAISE NOTICE 'remove_table_from_dlm - %: Retained columns DLM_Level and DLM_Partition_ID of table %', p_table_name, p_table_name;
	END IF;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION dlm.remove_table_from_dlm(text, boolean) IS 'gh #235, #489: Un-DLMs the given table:
* drops the view for the given table name
* drops partial indices
* invokes dlm.drop_dlm_triggers with the given table name.
* optionally drops the DLM_Level and DLM_Partition_ID column, if told so explicitly with the p_retain_dlm_column parameter set to false.';


CREATE OR REPLACE FUNCTION dlm.add_table_to_dlm(p_table_name text)
  RETURNS void AS
$BODY$
DECLARE
    -- v_index_view_row dlm.indices;
	v_trigger_view_row dlm.triggers;
BEGIN
	BEGIN
		/* using smallint, see https://www.postgresql.org/docs/9.5/static/datatype-numeric.html */
		/* add without default because we can't touch every preexisting record */
		EXECUTE 'ALTER TABLE ' || p_table_name || ' ADD COLUMN DLM_Level smallint;'; 
		/* *now* set default 0, so that new records are automatically inserted as "operational" */
		EXECUTE 'ALTER TABLE ' || p_table_name || ' ALTER COLUMN DLM_Level SET DEFAULT 0;';
		RAISE NOTICE 'add_table_to_dlm - %: Added column DLM_Level to table %', p_table_name, p_table_name;
	EXCEPTION
		WHEN duplicate_column THEN RAISE NOTICE 'Column DLM_Level already exists in %. Nothing do to', p_table_name;
    END;
	BEGIN
		EXECUTE 'ALTER TABLE ' || p_table_name || ' ADD COLUMN DLM_Partition_ID numeric(10,0);';
		RAISE NOTICE 'add_table_to_dlm - %: Added column DLM_Partition_ID to table %', p_table_name, p_table_name;
	EXCEPTION
		WHEN duplicate_column THEN RAISE NOTICE 'Column DLM_Partition_ID already exists in %. Nothing do to', p_table_name;
    END;

	/* Generally, we would like to create indices concurrently because this DB-function might be executed on large tables and during production. 
	   See https://www.postgresql.org/docs/9.5/static/sql-createindex.html#SQL-CREATEINDEX-CONCURRENTLY.
	   However, this doesn't work: "ERROR: CREATE INDEX CONCURRENTLY cannot be executed from a function or multi-command string"
	 */
	/* Non-partial indices; they large and grow with the table, but so does everything else, and at most times the DBMS will only have to keep those blocks in memory that have DLM_Level=0.
	   And this way we have the flexibility to go with current_setting('metasfresh.DLM_Level').
	 */
	EXECUTE 'CREATE INDEX IF NOT EXISTS ' || p_table_name || '_DLM_Level ON ' || p_table_name || ' (DLM_Level)';   
	EXECUTE 'CREATE INDEX IF NOT EXISTS ' || p_table_name || '_DLM_Partition_ID ON ' || p_table_name || ' (DLM_Partition_ID)';   
	RAISE NOTICE 'add_table_to_dlm - %: Created indices %_DLM_Level and %_DLM_Partition_ID', p_table_name, p_table_name, p_table_name;
	
	EXECUTE 'CREATE VIEW dlm.' || p_table_name || ' AS SELECT * FROM ' || p_table_name || ' WHERE COALESCE(DLM_Level, current_setting(''metasfresh.DLM_Coalesce_Level'')::smallint) <= current_setting(''metasfresh.DLM_Level'')::smallint;';
	EXECUTE 'COMMENT ON VIEW dlm.' || p_table_name || ' IS ''This view selects records according to the metasfresh.DLM_Coalesce_Level and metasfresh.DLM_Level DBMS parameters. See task gh #489'';';
	RAISE NOTICE 'add_table_to_dlm - %: Created view dlm.%', p_table_name, p_table_name;

	/* Create triggers and trigger functions for each FK constraint.
	 */
	PERFORM dlm.create_dlm_triggers(p_table_name);
	
	/* make sure that the DB actually takes note of what we just did */
	EXECUTE 'ANALYZE ' || p_table_name || ';'; 
	RAISE NOTICE 'Called ANALYZE %', p_table_name;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE; 
COMMENT ON FUNCTION dlm.add_table_to_dlm(text) IS 'gh #235, #489: DLMs the given table:
* Adds a DLM_Level and DLM_Partition_ID column to the table.
* Creates a view named <tablename> in the dlm schema that selects * from the table, but has a where-clause to make it select only records with DLM_Level <= current_setting(''metasfresh.DLM_Level'')
* Creates an index for the new DLM_Level column
* invokes dlm.create_dlm_triggers for the given p_tablename
* Does an analyze on the table
';
