
CREATE VIEW DLM_Partition_Records AS
SELECT 
	c.DLM_Partition_Config_ID, p.DLM_Partition_ID, t.TableName, t.IsDLM, t.AD_Table_ID, r.Record_ID
FROM DLM_Partition p
	JOIN DLM_Partition_Config c 
			ON p.DLM_Partition_Config_ID=c.DLM_Partition_Config_ID
	JOIN DLM_Partition_Config_Line l 
			ON l.DLM_Partition_Config_ID=c.DLM_Partition_Config_ID
		JOIN AD_Table t 
				ON t.AD_Table_ID=l.DLM_Referencing_Table_ID

	JOIN LATERAL (SELECT dlm.get_dlm_records(t.TableName, p.DLM_Partition_ID) ) records ON true
;
