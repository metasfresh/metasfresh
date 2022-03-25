update c_queue_workpackage
set c_queue_packageprocessor_id = block.c_queue_packageprocessor_id, updatedBy=99, updated=TO_TIMESTAMP('2022-02-04 12:16:30', 'YYYY-MM-DD HH24:MI:SS')
from c_queue_block block
where block.c_queue_block_id = c_queue_workpackage.c_queue_block_id
;

update dlm.c_queue_workpackage_archived
set c_queue_packageprocessor_id = block.c_queue_packageprocessor_id, updatedBy=99, updated=TO_TIMESTAMP('2022-02-04 12:16:30', 'YYYY-MM-DD HH24:MI:SS')
from c_queue_block block
where block.c_queue_block_id = dlm.c_queue_workpackage_archived.c_queue_block_id
;