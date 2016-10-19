
DROP FUNCTION IF EXISTS dlm.get_dlm_records(text, numeric(10,0));
CREATE OR REPLACE FUNCTION dlm.get_dlm_records(p_Table_Name text, p_DLM_Partition_ID numeric(10,0))
	RETURNS TABLE(TableName text, DLM_Partition_ID numeric(10,0), Record_ID numeric(10,0)) AS
$BODY$
BEGIN
	RETURN QUERY EXECUTE 'SELECT '||p_Table_Name||', '||p_DLM_Partition_ID||', '||p_Table_Name||'_ID AS Record_ID FROM '||p_Table_Name||' WHERE DLM_Partition_ID='||p_DLM_Partition_ID||';';
END;	
$BODY$
	LANGUAGE plpgsql STABLE;
