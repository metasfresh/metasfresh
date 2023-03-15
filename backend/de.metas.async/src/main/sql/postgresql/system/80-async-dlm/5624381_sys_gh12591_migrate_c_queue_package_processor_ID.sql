

-- BACKUP

CREATE TABLE backup.BKP_c_queue_block_15023023 AS
SELECT *
FROM c_queue_block
;

CREATE TABLE backup.BKP_c_queue_workpackage_15032023 AS
SELECT *
FROM c_queue_workpackage
;

CREATE TABLE backup.BKP_dlm_c_queue_workpackage_archived_15032023 AS
SELECT *
FROM dlm.c_queue_workpackage_archived
;

CREATE TABLE backup.BKP_c_queue_element_15032023 AS
SELECT *
FROM c_queue_element
;


-- DELETE invalid data

CREATE TABLE backup.workPackageIDsToDelete_15032023 AS
SELECT c_queue_workpackage_ID
FROM c_queue_workpackage wp
WHERE EXISTS(SELECT 1
             FROM c_queue_block b
             WHERE b.c_queue_block_id = wp.c_queue_block_id
               AND NOT EXISTS(SELECT 1 FROM c_queue_packageprocessor proc WHERE proc.c_queue_packageprocessor_id = b.c_queue_packageprocessor_id))
;


DELETE
FROM c_queue_element el
WHERE EXISTS(SELECT 1 FROM backup.workPackageIDsToDelete_15032023 wpd WHERE el.c_queue_workpackage_id = wpd.c_queue_workpackage_ID)
;

DELETE
FROM c_queue_workpackage wp
WHERE EXISTS(SELECT 1
             FROM backup.workPackageIDsToDelete_15032023 wpd WHERE wp.c_queue_workpackage_id = wpd.c_queue_workpackage_ID)
;

DELETE
FROM c_queue_block b
WHERE NOT EXISTS(SELECT 1 FROM c_queue_packageprocessor WHERE c_queue_packageprocessor_id = b.c_queue_packageprocessor_id)
;


-- Actual migration

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


drop index if exists dlm.c_queue_block_archived_c_queue_id;
create index c_queue_block_archived_c_queue_id on dlm.c_queue_block_archived (c_queue_block_id);

UPDATE dlm.c_queue_workpackage_archived wp
SET c_queue_packageprocessor_id=(SELECT b.c_queue_packageprocessor_id FROM dlm.c_queue_block_archived b WHERE b.c_queue_block_id = wp.c_queue_block_id)
WHERE c_queue_packageprocessor_id IS NULL
;