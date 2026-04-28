

DROP VIEW IF EXISTS dlm.DLM_Partition_Size;
CREATE VIEW dlm.DLM_Partition_Size AS
SELECT 
	p.DLM_Partition_ID, SUM(count) AS Size
FROM DLM_Partition p
	JOIN DLM_Partition_Config c 
			ON p.DLM_Partition_Config_ID=c.DLM_Partition_Config_ID AND c.IsActive='Y'
	JOIN DLM_Partition_Config_Line l 
			ON l.DLM_Partition_Config_ID=c.DLM_Partition_Config_ID  AND l.IsActive='Y'
		JOIN AD_Table t 
			ON t.AD_Table_ID=l.DLM_Referencing_Table_ID
	JOIN LATERAL (SELECT COUNT(1) AS count FROM dlm.get_dlm_records(t.TableName, p.DLM_Partition_ID)) as count 
			ON true
GROUP BY p.DLM_Partition_ID;
COMMENT ON VIEW dlm.DLM_Partition_Size IS
'gh #489: select DLM_Partition_IDs and the number of records assigned to them';



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
			ON p.DLM_Partition_Config_ID=c.DLM_Partition_Config_ID AND c.IsActive='Y'
	JOIN DLM_Partition_Config_Line l 
			ON l.DLM_Partition_Config_ID=c.DLM_Partition_Config_ID AND l.IsActive='Y'
		JOIN AD_Table t 
			ON t.AD_Table_ID=l.DLM_Referencing_Table_ID
	JOIN LATERAL (SELECT COUNT(1) AS count FROM dlm.get_dlm_records(t.TableName, p.DLM_Partition_ID)) as count 
			ON true
GROUP BY p.DLM_Partition_ID, t.AD_Table_ID, t.TableName;
COMMENT ON VIEW DLM_Partition_Size_per_Table_V IS
'gh #489: select DLM_Partition_IDs and the numer of records assigned to them';


CREATE OR REPLACE FUNCTION dlm.recreate_dlm_triggers(p_table_name text)
  RETURNS void AS
$BODY$
BEGIN
	PERFORM dlm.drop_dlm_triggers(p_table_name);
	PERFORM dlm.create_dlm_triggers(p_table_name);
END 
$BODY$
  LANGUAGE plpgsql VOLATILE;
COMMENT ON FUNCTION dlm.recreate_dlm_triggers(text) IS 'gh #235, #489: 
Calls both drop_dlm_triggers and create_dlm_triggers with the given p_table_name. 
Useful to to just update preexisting triggers and triggerfunctions without touching the indices.

Note: in order to recreate the triggers for all DLM tables, it''s better to run

SELECT TableName, dlm.drop_dlm_triggers(TableName) FROM AD_Table WHERE IsDLM=''Y'';
commit;
SELECT TableName, dlm.create_dlm_triggers(TableName) FROM AD_Table WHERE IsDLM=''Y'';

to avoid locking problems.
';
