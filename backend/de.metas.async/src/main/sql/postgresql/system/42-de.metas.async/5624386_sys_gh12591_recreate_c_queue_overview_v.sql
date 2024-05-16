drop view "de.metas.monitoring".async_unprocessed_workpackage_sum_v
;

drop view "de.metas.async".c_queue_unprocessed_workpackage_counts_v
;

DROP VIEW "de.metas.async".c_queue_overview_v;

CREATE OR REPLACE VIEW "de.metas.async".c_queue_overview_v AS
SELECT qwp.c_queue_workpackage_id,
       qwp.created AS qwp_created,
       qwp.updated AS qwp_updated,
       qwp.processed,
       qwp.iserror,
       CASE
           WHEN l.record_id IS NULL THEN 'N'::text
                                    ELSE 'Y'::text
       END AS islocked,
       i.ad_issue_id,
       i.issuesummary,
       qpp.classname,
       qwp.AD_PInstance_ID as AD_PInstance_Creator_ID,
       qe.c_queue_element_id,
       t.tablename AS qe_table_name,
       qe.Record_ID AS qe_Record_ID

FROM c_queue_workpackage qwp
         JOIN c_queue_packageprocessor qpp ON qpp.c_queue_packageprocessor_id = qwp.c_queue_packageprocessor_id
         LEFT JOIN t_lock l ON l.ad_table_id = 540425::numeric AND l.record_id = qwp.c_queue_workpackage_id
         LEFT JOIN c_queue_element qe ON qe.c_queue_workpackage_id = qwp.c_queue_workpackage_id
         LEFT JOIN ad_table t ON t.ad_table_id = qe.ad_table_id
         LEFT JOIN ad_issue i ON i.ad_issue_id = qwp.ad_issue_id
ORDER BY qwp.c_queue_workpackage_id DESC, qe.c_queue_element_id;
COMMENT ON VIEW "de.metas.async".c_queue_overview_v
    IS 'View to more easily select infos about workpackages.
Note that in the "dlm" schema there is a pendant for those workpackages that were already archived.

Usage example(s):
Select all invoices that were enqueue for EDI-Sending, with their documentNo and current EDI-Status:

select v.*, i.DocumentNo, i.EDI_EXportStatus
from "de.metas.async".c_queue_overview_v v
	join C_Invoice i ON i.C_Invoice_ID=v.qe_record_ID
where true
	AND v.classname ilike ''%EDIWorkpackageProcessor%''
	AND v.qe_table_name=''C_Invoice''
	AND v.qwp_Created>=''2016-08-25 18:00''
	AND v.qwp_Created<=''2016-08-26''
order by qwp_created;
';


-- recreate "de.metas.async".C_Queue_Unprocessed_WorkPackage_Counts_v
CREATE OR REPLACE VIEW "de.metas.async".C_Queue_Unprocessed_WorkPackage_Counts_v AS
SELECT COUNT(*) as count, classname
FROM "de.metas.async".c_queue_overview_v v
         JOIN c_queue_workpackage qwp ON qwp.c_queue_workpackage_id=v.c_queue_workpackage_id
    AND qwp.IsActive='Y'
    AND qwp.IsReadyForProcessing='Y'
    AND qwp.processed='N'
    AND qwp.iserror='N'
GROUP BY classname;
COMMENT ON VIEW "de.metas.async".C_Queue_Unprocessed_WorkPackage_Counts_v IS
    'Lists the number of unprocessed packages per processor className.
    Note that I join c_queue_workpackage just to have additional conditions (like IsReadyForProcessing) that are not in the view';


-- recreate "de.metas.monitoring".async_unprocessed_workpackage_sum_v;
DROP VIEW IF EXISTS "de.metas.monitoring".async_unprocessed_workpackage_sum_v;
CREATE OR REPLACE VIEW "de.metas.monitoring".async_unprocessed_workpackage_sum_v AS
SELECT COALESCE(sum(c_queue_unprocessed_workpackage_counts_v.count), 0::numeric) AS "coalesce"
FROM "de.metas.async".c_queue_unprocessed_workpackage_counts_v
WHERE c_queue_unprocessed_workpackage_counts_v.classname::text <> 'de.metas.document.archive.async.spi.impl.ProcessPrintingQueueWorkpackageProcessor'::text;


