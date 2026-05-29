-- View: support.shipment_schedule_wp_timeline_v
-- Purpose: Shows the complete async workpackage processing timeline for shipment generation.
--          Helps identify when multiple WP batches processed the same shipment schedules,
--          revealing the race condition that causes duplicate shipment lines.
--
-- Usage:
--   -- All shipment generation WPs for a specific date
--   SELECT * FROM support.shipment_schedule_wp_timeline_v WHERE wp_created::date = '2026-02-14';
--
--   -- WPs that processed a specific shipment schedule
--   SELECT * FROM support.shipment_schedule_wp_timeline_v
--   WHERE wp_id IN (
--     SELECT c_queue_workpackage_id FROM c_queue_workpackage_log
--     WHERE msgtext LIKE '%M_ShipmentSchedule_ID=1020989%'
--   );

CREATE OR REPLACE VIEW support.shipment_schedule_wp_timeline_v AS
SELECT
    wp.c_queue_workpackage_id                      AS wp_id,
    wp.created                                     AS wp_created,
    wp.updated                                     AS wp_processed_at,
    wp.updated - wp.created                        AS processing_duration,
    wp.processed                                   AS is_processed,
    wp.iserror                                     AS is_error,
    wp.errormsg,
    wp.skipped_count,
    wp.ad_pinstance_id,
    pp.classname                                   AS processor_classname,
    -- Count elements (shipment schedules) in this WP
    (SELECT COUNT(*) FROM c_queue_element qe
     WHERE qe.c_queue_workpackage_id = wp.c_queue_workpackage_id) AS element_count,
    -- Check if there are other WPs in the same AD_PInstance batch
    (SELECT COUNT(*) FROM c_queue_workpackage wp2
     WHERE wp2.ad_pinstance_id = wp.ad_pinstance_id
       AND wp2.c_queue_workpackage_id != wp.c_queue_workpackage_id) AS sibling_wp_count
FROM c_queue_workpackage wp
         JOIN c_queue_packageprocessor pp ON pp.c_queue_packageprocessor_id = wp.c_queue_packageprocessor_id
WHERE pp.classname LIKE '%GenerateInOutFromShipmentSchedules%'
ORDER BY wp.created DESC;

COMMENT ON VIEW support.shipment_schedule_wp_timeline_v IS
    'Timeline of GenerateInOutFromShipmentSchedules workpackage processing. '
        'Use to identify multiple WP batches processing the same shipment schedules. '
        'Helps diagnose duplicate shipment line race conditions.';
