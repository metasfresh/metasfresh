update c_queue_workpackage
set c_queue_packageprocessor_id = block.c_queue_packageprocessor_id, updatedBy=99, updated=TO_TIMESTAMP('2022-02-04 12:16:30', 'YYYY-MM-DD HH24:MI:SS')
from c_queue_block block
where block.c_queue_block_id = c_queue_workpackage.c_queue_block_id
;

alter table dlm.c_queue_workpackage_archived ADD c_queue_packageprocessor_id NUMERIC(10) DEFAULT NULL
;

update dlm.c_queue_workpackage_archived
set c_queue_packageprocessor_id = block.c_queue_packageprocessor_id, updatedBy=99, updated=TO_TIMESTAMP('2022-02-04 12:16:30', 'YYYY-MM-DD HH24:MI:SS')
from c_queue_block block
where block.c_queue_block_id = dlm.c_queue_workpackage_archived.c_queue_block_id
;

alter table dlm.c_queue_workpackage_archived alter column c_queue_packageprocessor_id set not null
;