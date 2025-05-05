CREATE TABLE backup.MaterialEventWorkpackageProcessor_bkp_20231204
AS select w.*
   from "de.metas.async".c_queue_overview_v v
            inner join c_queue_workpackage w ON w.c_queue_workpackage_id=v.c_queue_workpackage_id
   where v.classname='de.metas.material.event.MaterialEventWorkpackageProcessor'
;

DELETE 
FROM c_queue_workpackage w
WHERE w.c_queue_workpackage_id IN (SELECT c_queue_workpackage_id FROM backup.MaterialEventWorkpackageProcessor_bkp_20231204);
