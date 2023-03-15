
<<<<<<< HEAD
/*

--
-- prepare delete
CREATE TABLE backup.c_queue_workpackage_delete_20230221 AS
SELECT qwp.*
FROM c_queue_workpackage qwp
         JOIN c_queue_block block
              ON block.c_queue_block_id = qwp.c_queue_block_id
         LEFT JOIN c_queue_packageprocessor p
                   ON block.c_queue_packageprocessor_id = p.c_queue_packageprocessor_id
WHERE p.c_queue_packageprocessor_id IS NULL
;
-- [2023-02-21 08:21:52] 4,896 rows affected in 125 ms

CREATE TABLE backup.c_queue_element_delete_20230221 AS
SELECT *
FROM c_queue_element e
WHERE e.c_queue_workpackage_id IN (SELECT c_queue_workpackage_id FROM backup.c_queue_workpackage_delete_20230221)
;
-- [2023-02-21 08:21:57] 10,853 rows affected in 107 ms

CREATE TABLE backup.c_queue_workpackage_log_delete_20230221 AS
SELECT *
FROM c_queue_workpackage_log
WHERE c_queue_workpackage_id IN (SELECT c_queue_workpackage_id FROM backup.c_queue_workpackage_delete_20230221)
;
-- [2023-02-21 08:22:15] 4,832 rows affected in 108 ms

--
-- inspect the records you are about to delete
-- they will very probably be obsolete
select * from backup.c_queue_workpackage_delete_20230221;

--
-- perform delete
DELETE
FROM c_queue_element
WHERE c_queue_element_id IN (SELECT c_queue_element_id FROM backup.c_queue_element_delete_20230221)
;
-- [2023-02-21 08:22:47] 10,853 rows affected in 87 ms

DELETE
FROM c_queue_workpackage_log
WHERE c_queue_workpackage_log_id IN (SELECT c_queue_workpackage_log_id FROM backup.c_queue_workpackage_log_delete_20230221)
;
-- [2023-02-21 08:22:56] 4,832 rows affected in 81 ms

DELETE
FROM c_queue_workpackage
WHERE c_queue_workpackage_id IN (SELECT c_queue_workpackage_id FROM backup.c_queue_workpackage_delete_20230221)
;
-- [2023-02-21 08:23:08] 4,896 rows affected in 231 ms

 */


=======

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

>>>>>>> 8375cb87f66 (Script correction after merge from release (#14875))
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