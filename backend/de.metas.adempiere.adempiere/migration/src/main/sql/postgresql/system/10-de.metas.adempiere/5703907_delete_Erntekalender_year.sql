CREATE TEMP TABLE tmp_period_map
AS
SELECT toDeletePeriods.c_period_id fromPeriodId,
       oldPeriods.c_period_id      toPeriodId
FROM c_period oldPeriods
         INNER JOIN c_period toDeletePeriods ON oldPeriods.startdate = toDeletePeriods.startdate

WHERE oldPeriods.c_year_id IN (SELECT c_year_id FROM c_year WHERE c_calendar_id = 1000000 /*Default calendar*/)
  AND toDeletePeriods.c_year_id IN (SELECT c_year_id FROM c_year WHERE c_calendar_id = 540014 /*Erntekalender*/)
;

DELETE
FROM C_PeriodControl
WHERE c_period_id IN (SELECT fromPeriodId FROM tmp_period_map)
;

DELETE
FROM c_period_trl
WHERE c_period_id IN (SELECT fromPeriodId FROM tmp_period_map)
;

DELETE
FROM c_period
WHERE c_period_id IN (SELECT fromPeriodId FROM tmp_period_map)
;

DELETE
FROM c_year
WHERE c_calendar_id = 540014
;

DELETE
FROM c_calendar
WHERE c_calendar_id = 540014
;

