
CREATE OR REPLACE FUNCTION dlm.update_partition_size(p_DLM_Partition_ID numeric(10,0)) 
RETURNS VOID AS
$BODY$
BEGIN
	UPDATE DLM_Partition p 
	SET PartitionSize=v.Size
	FROM dlm.DLM_Partition_Size v
	WHERE p.DLM_Partition_ID=p_DLM_Partition_ID AND v.DLM_Partition_ID=p.DLM_Partition_ID;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION dlm.update_partition_size(numeric(10,0)) IS
'gh #489: Updates the PartitionSize column of the DLM_Partition record with the given p_DLM_Partition_ID. If there is no DLM_Partition with the given Id, then the function does nothing';
