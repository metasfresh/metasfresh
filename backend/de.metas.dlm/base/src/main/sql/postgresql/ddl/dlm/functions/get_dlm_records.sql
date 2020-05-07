 
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
