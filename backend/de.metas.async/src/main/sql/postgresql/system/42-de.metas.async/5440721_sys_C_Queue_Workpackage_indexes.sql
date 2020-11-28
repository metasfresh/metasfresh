-- NOTE: it takes ~12sec to create it, so it's acceptable to deleted it first
drop index if exists C_Queue_Workpackage_Log_Parent;
create index C_Queue_Workpackage_Log_Parent on C_Queue_Workpackage_Log(C_Queue_Workpackage_ID);

-- NOTE: it takes ~27sec to create it, so it's kind of acceptable to deleted it first
drop index if exists C_Queue_Workpackage_Param_Parent;
create index C_Queue_Workpackage_Param_Parent on C_Queue_Workpackage_Param(C_Queue_Workpackage_ID);


-- Dropping a duplicate index. We already have c_queue_workpackage_c_queue_block_id.
drop index if exists c_queue_workpackage_queueblock_;

--
-- C_Queue_Element: drop the C_Queue_Element.C_Queue_Block_ID FK because it's a big overhead, mainly when we try to delete queue blocks
ALTER TABLE c_queue_element DROP CONSTRAINT if exists cqueueblock_cqueueelement;
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2016-03-31 15:00:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547867;
