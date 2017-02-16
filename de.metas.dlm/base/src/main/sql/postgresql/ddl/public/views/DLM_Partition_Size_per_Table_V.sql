

DROP VIEW IF EXISTS DLM_Partition_Size_per_Table_V;
CREATE VIEW DLM_Partition_Size_per_Table_V AS
SELECT 
	p.AD_Client_ID,
	p.AD_Org_ID,
	p.IsActive,
	p.Created,
	p.Createdby,
	p.Updated,
	p.UpdatedBy,
	(p.DLM_Partition_ID + t.AD_Table_ID) AS DLM_Partition_Size_Per_Table_V_ID, -- we don't need a real PK, this view is for display in a subtab only, so this is OK
	p.DLM_Partition_ID, 
	t.AD_Table_ID, 
	t.TableName, 
	SUM(count) AS PartitionSize
FROM DLM_Partition p
	JOIN DLM_Partition_Config c 
			ON p.DLM_Partition_Config_ID=c.DLM_Partition_Config_ID
	JOIN DLM_Partition_Config_Line l 
			ON l.DLM_Partition_Config_ID=c.DLM_Partition_Config_ID
		JOIN AD_Table t 
			ON t.AD_Table_ID=l.DLM_Referencing_Table_ID
	JOIN LATERAL (SELECT COUNT(1) AS count FROM dlm.get_dlm_records(t.TableName, p.DLM_Partition_ID)) as count 
			ON true
GROUP BY p.DLM_Partition_ID, t.AD_Table_ID, t.TableName;
COMMENT ON VIEW DLM_Partition_Size_per_Table_V IS
'gh #489: select DLM_Partition_IDs and the numer of records assigned to them';


