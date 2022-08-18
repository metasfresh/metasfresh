UPDATE ad_scheduler
SET isactive='N', updatedby=99, updated=NOW()
WHERE ad_scheduler.ad_scheduler_id = 550076
;
