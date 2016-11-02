
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
Finally, note that the FK-constraint from the table "report.accounting_facts_mv" to the table "public.fact_acct" is ignored, just like any other FK-constraint that is not defined in the public schema. the reason is that at least currently only tables from the public schema can be DLM''ed.
';


 
--DROP FUNCTION IF EXISTS dlm.get_dlm_records(text, numeric(10,0)) CASCADE;
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
DECLARE 
	v_columnNames text[];
	v_sql text;
BEGIN
	-- select all columns into an array.
	SELECT array_agg(c.ColumnName) 
	INTO v_columnNames
	FROM AD_Column c where c.AD_Table_ID=get_table_id(p_Table_Name);
--	RAISE NOTICE 'v_columnNames=%', v_columnNames;

	v_sql:='SELECT '
			||p_DLM_Partition_ID||'::numeric(10,0), '
			||p_Table_Name||'_ID::numeric(10,0) AS Record_ID, '
			||'DLM_Level, '
			-- only try to select the colmn value if the column exists. e.g. AD_PInstance_Log is an example that lacks many standard columns
			||CASE WHEN 'AD_Client_ID'=ANY(v_columnNames) THEN 'AD_Client_ID' ELSE 'NULL::numeric(10,0)' END||', '
			||CASE WHEN 'AD_Org_ID'=ANY(v_columnNames) THEN 'AD_Org_ID' ELSE 'NULL::numeric(10,0)' END||', '
			||CASE WHEN 'Created'=ANY(v_columnNames) THEN 'Created' ELSE 'NULL::timestamp with time zone' END||', '
			||CASE WHEN 'CreatedBy'=ANY(v_columnNames) THEN 'CreatedBy' ELSE 'NULL::numeric(10,0)' END||', '
			||CASE WHEN 'Updated'=ANY(v_columnNames) THEN 'Updated' ELSE 'NULL::timestamp with time zone'  END||', '
			||CASE WHEN 'UpdatedBy'=ANY(v_columnNames) THEN 'UpdatedBy' ELSE 'NULL::numeric(10,0)' END||', '
			||CASE WHEN 'IsActive'=ANY(v_columnNames) THEN 'IsActive' ELSE 'NULL::character(1)' END
			
		||' FROM '||p_Table_Name
		||' WHERE DLM_Partition_ID='||p_DLM_Partition_ID||';';
--	RAISE NOTICE 'v_sql=%', v_sql;

	RETURN QUERY EXECUTE v_sql;
		
END;	
$BODY$
	LANGUAGE plpgsql STABLE;
COMMENT ON FUNCTION dlm.get_dlm_records(text, numeric(10,0)) IS 
'Executes an SQL select that returns all records from the given p_Table_Name which have the given p_DLM_Partition_ID.
See gh #489.';
