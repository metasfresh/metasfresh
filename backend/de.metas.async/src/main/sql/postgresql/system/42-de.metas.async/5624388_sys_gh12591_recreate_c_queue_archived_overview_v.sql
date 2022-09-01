DROP VIEW dlm.c_queue_archived_overview_v;

CREATE OR REPLACE VIEW dlm.c_queue_archived_overview_v AS
SELECT
    qwp.c_queue_workpackage_id,
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
    qwp.ad_pinstance_id as ad_pinstance_creator_id,
    qe.c_queue_element_id,
    t.tablename AS qe_table_name,
    qe.record_id AS qe_record_id
FROM dlm.c_queue_workpackage_archived qwp
         JOIN c_queue_packageprocessor qpp ON qpp.c_queue_packageprocessor_id = qwp.c_queue_packageprocessor_id
         LEFT JOIN t_lock l ON l.ad_table_id = 540425::numeric AND l.record_id = qwp.c_queue_workpackage_id
         LEFT JOIN dlm.c_queue_element_archived qe ON qe.c_queue_workpackage_id = qwp.c_queue_workpackage_id
         LEFT JOIN ad_table t ON t.ad_table_id = qe.ad_table_id
         LEFT JOIN ad_issue i ON i.ad_issue_id = qwp.ad_issue_id
ORDER BY qwp.c_queue_workpackage_id DESC, qe.c_queue_element_id;
