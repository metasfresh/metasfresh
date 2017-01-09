
DROP VIEW IF EXISTS dlm.DLM_Partition_Size;
CREATE VIEW dlm.DLM_Partition_Size AS
SELECT 
	p.DLM_Partition_ID, SUM(count) AS Size
FROM DLM_Partition p
	JOIN DLM_Partition_Config c 
			ON p.DLM_Partition_Config_ID=c.DLM_Partition_Config_ID
	JOIN DLM_Partition_Config_Line l 
			ON l.DLM_Partition_Config_ID=c.DLM_Partition_Config_ID
		JOIN AD_Table t 
			ON t.AD_Table_ID=l.DLM_Referencing_Table_ID
	JOIN LATERAL (SELECT COUNT(1) AS count FROM dlm.get_dlm_records(t.TableName, p.DLM_Partition_ID)) as count 
			ON true
GROUP BY p.DLM_Partition_ID;
COMMENT ON VIEW dlm.DLM_Partition_Size IS
'gh #489: select DLM_Partition_IDs and the number of records assigned to them';
