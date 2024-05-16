
--
-- Adapt the "archived" tables so that dlm.archive_c_queue_data can continue to work
--

ALTER TABLE dlm.C_Queue_Element_Archived
    DROP COLUMN IF EXISTS c_queue_block_id
;
ALTER TABLE dlm.C_Queue_Workpackage_Log_Archived
    DROP COLUMN IF EXISTS c_queue_block_id
;
ALTER TABLE dlm.C_Queue_Workpackage_Archived
    DROP COLUMN IF EXISTS c_queue_block_id
;

ALTER TABLE dlm.c_queue_workpackage_archived
    ALTER COLUMN skipped_last_reason TYPE text USING skipped_last_reason::text
;
