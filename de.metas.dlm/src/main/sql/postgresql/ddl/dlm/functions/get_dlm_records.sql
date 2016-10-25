
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
