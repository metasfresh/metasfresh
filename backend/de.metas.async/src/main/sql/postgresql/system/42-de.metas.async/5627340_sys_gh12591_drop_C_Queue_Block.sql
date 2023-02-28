delete
from dlm_partition_config_reference
where dlm_partition_config_line_id in (select dlm_partition_config_line_id from dlm_partition_config_line where dlm_referencing_table_id = 540422)
;

delete
from dlm_partition_config_line
where dlm_referencing_table_id = 540422
;

-- Table: C_Queue_Block
-- 2022-02-23T10:28:32.539Z
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=540422
;

-- 2022-02-23T10:28:32.542Z
DELETE FROM AD_Table WHERE AD_Table_ID=540422
;

