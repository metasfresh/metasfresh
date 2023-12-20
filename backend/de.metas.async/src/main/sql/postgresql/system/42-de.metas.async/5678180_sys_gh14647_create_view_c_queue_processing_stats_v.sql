CREATE OR REPLACE VIEW "de.metas.async".c_queue_processing_stats_v AS
SELECT EXTRACT('year' FROM qwp_all.updated)   AS year,
       EXTRACT('month' FROM qwp_all.updated)  AS month,
       EXTRACT('day' FROM qwp_all.updated)    AS day,
       EXTRACT('hour' FROM qwp_all.updated)   AS hour,
       p.classname,
       COUNT(qwp_all.c_queue_workpackage_id)  AS "all",
       COUNT(qwp_enq.c_queue_workpackage_id)  AS enqueued,
       COUNT(qwp_proc.c_queue_workpackage_id) AS processed
FROM c_queue_packageprocessor p
         JOIN c_queue_workpackage qwp_all
              ON qwp_all.c_queue_packageprocessor_id = p.c_queue_packageprocessor_id
         LEFT JOIN c_queue_workpackage qwp_enq
                   ON qwp_enq.c_queue_packageprocessor_id = p.c_queue_packageprocessor_id
                       AND qwp_enq.isreadyforprocessing = 'Y'
                       AND qwp_enq.isactive = 'Y'
                       AND qwp_enq.c_queue_workpackage_id = qwp_all.c_queue_workpackage_id
         LEFT JOIN c_queue_workpackage qwp_proc
                   ON qwp_proc.c_queue_packageprocessor_id = p.c_queue_packageprocessor_id
                       AND qwp_proc.processed = 'Y'
                       AND qwp_proc.isactive = 'Y'
                       AND qwp_proc.c_queue_workpackage_id = qwp_all.c_queue_workpackage_id
         LEFT JOIN c_queue_workpackage qwp_err
                   ON qwp_err.c_queue_packageprocessor_id = p.c_queue_packageprocessor_id
                       AND qwp_err.iserror = 'Y'
                       AND qwp_err.c_queue_workpackage_id = qwp_all.c_queue_workpackage_id
WHERE TRUE
GROUP BY EXTRACT('year' FROM qwp_all.updated),
         EXTRACT('month' FROM qwp_all.updated),
         EXTRACT('day' FROM qwp_all.updated),
         EXTRACT('hour' FROM qwp_all.updated),
         p.classname
ORDER BY EXTRACT('year' FROM qwp_all.updated),
         EXTRACT('month' FROM qwp_all.updated),
         EXTRACT('day' FROM qwp_all.updated),
         EXTRACT('hour' FROM qwp_all.updated),
         p.classname
;

COMMENT ON VIEW "de.metas.async".c_queue_processing_stats_v IS 'Selects the number of enqueued, processed and error''ed workpackages per hour'
    'Usage examples:

     select
        year, month, day, hour,
        SUM(all)       AS all,
        SUM(enqueued)  AS enqueued,
        SUM(processed) AS processed
    from "de.metas.async".c_queue_processing_stats_v
    where true
        AND year=EXTRACT(''year'' FROM now())
        AND month=EXTRACT(''month'' FROM now())
        AND day=EXTRACT(''day'' FROM now())
    group by year, month, day, hour;

    select *
    from "de.metas.async".c_queue_processing_stats_v
    where true
        AND classname in (
            ''de.metas.invoicecandidate.async.spi.impl.InvoiceCandWorkpackageProcessor'',
            ''de.metas.invoicecandidate.async.spi.impl.CreateMissingInvoiceCandidatesWorkpackageProcessor''
            ''de.metas.invoicecandidate.async.spi.impl.UpdateInvalidInvoiceCandidatesWorkpackageProcessor''
        )
        AND year=EXTRACT(''year'' FROM now())
        AND month=EXTRACT(''month'' FROM now())
        AND day=EXTRACT(''day'' FROM now());
'
;
