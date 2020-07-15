
DROP INDEX public.ad_schedulerlog_ad_scheduler_id;

CREATE INDEX ad_schedulerlog_ad_scheduler_id_created
    ON public.ad_schedulerlog
    (ad_scheduler_id, created);
COMMENT ON INDEX ad_schedulerlog_ad_scheduler_id_created is 
'This index supports deleting old AD_SchedulerLogs records';
