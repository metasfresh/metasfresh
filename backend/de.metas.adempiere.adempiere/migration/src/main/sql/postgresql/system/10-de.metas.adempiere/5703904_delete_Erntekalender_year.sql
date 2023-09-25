
CREATE TEMP TABLE tmp_year_map
AS
SELECT toDeleteYear.c_year_Id fromYearId,
       existingYear.c_year_Id toYearId
FROM c_year existingYear
         INNER JOIN c_year toDeleteYear ON existingYear.fiscalyear = toDeleteYear.fiscalyear

WHERE existingYear.c_year_id IN (SELECT c_year_id FROM c_year WHERE c_calendar_id = 1000000 /*Default calendar*/)
  AND toDeleteYear.c_year_id IN (SELECT c_year_id FROM c_year WHERE c_calendar_id = 540014 /*Erntekalender*/)
;

UPDATE c_acctschema_element
SET harvesting_year_id = yearMap.toYearId,
    updated            = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby          = 100
FROM tmp_year_map yearMap
WHERE harvesting_year_id = yearMap.fromYearId
;

UPDATE c_auction
SET harvesting_year_id = yearMap.toYearId,
    updated            = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby          = 100
FROM tmp_year_map yearMap
WHERE harvesting_year_id = yearMap.fromYearId
;

UPDATE c_bpartner_interimcontract
SET harvesting_year_id = yearMap.toYearId,
    updated            = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby          = 100
FROM tmp_year_map yearMap
WHERE harvesting_year_id = yearMap.fromYearId
;

UPDATE c_invoice
SET harvesting_year_id = yearMap.toYearId,
    updated            = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby          = 100
FROM tmp_year_map yearMap
WHERE harvesting_year_id = yearMap.fromYearId
;

UPDATE c_invoice_candidate
SET harvesting_year_id = yearMap.toYearId,
    updated            = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby          = 100
FROM tmp_year_map yearMap
WHERE harvesting_year_id = yearMap.fromYearId
;

UPDATE c_invoiceline
SET harvesting_year_id = yearMap.toYearId,
    updated            = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby          = 100
FROM tmp_year_map yearMap
WHERE harvesting_year_id = yearMap.fromYearId
;

UPDATE c_membershipmonth
SET c_year_id = yearMap.toYearId,
    updated   = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby = 100
FROM tmp_year_map yearMap
WHERE c_year_id = yearMap.fromYearId
;

UPDATE c_order
SET harvesting_year_id = yearMap.toYearId,
    updated            = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby          = 100
FROM tmp_year_map yearMap
WHERE harvesting_year_id = yearMap.fromYearId
;

UPDATE c_validcombination
SET harvesting_year_id = yearMap.toYearId,
    updated            = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby          = 100
FROM tmp_year_map yearMap
WHERE harvesting_year_id = yearMap.fromYearId
;

UPDATE fact_acct
SET harvesting_year_id = yearMap.toYearId,
    updated            = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby          = 100
FROM tmp_year_map yearMap
WHERE harvesting_year_id = yearMap.fromYearId
;

UPDATE fact_acct_summary
SET c_year_id = yearMap.toYearId,
    updated   = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby = 100
FROM tmp_year_map yearMap
WHERE c_year_id = yearMap.fromYearId
;

UPDATE hr_period
SET c_year_id = yearMap.toYearId,
    updated   = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby = 100
FROM tmp_year_map yearMap
WHERE c_year_id = yearMap.fromYearId
;

UPDATE hr_year
SET c_year_id = yearMap.toYearId,
    updated   = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby = 100
FROM tmp_year_map yearMap
WHERE c_year_id = yearMap.fromYearId
;

UPDATE m_demand
SET c_year_id = yearMap.toYearId,
    updated   = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby = 100
FROM tmp_year_map yearMap
WHERE c_year_id = yearMap.fromYearId
;

UPDATE m_forecast
SET c_year_id = yearMap.toYearId,
    updated   = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby = 100
FROM tmp_year_map yearMap
WHERE c_year_id = yearMap.fromYearId
;

UPDATE m_inoutline
SET harvesting_year_id = yearMap.toYearId,
    updated            = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby          = 100
FROM tmp_year_map yearMap
WHERE harvesting_year_id = yearMap.fromYearId
;

UPDATE m_material_tracking_report
SET c_year_id = yearMap.toYearId,
    updated   = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby = 100
FROM tmp_year_map yearMap
WHERE c_year_id = yearMap.fromYearId
;

UPDATE m_shipping_notification
SET harvesting_year_id = yearMap.toYearId,
    updated            = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby          = 100
FROM tmp_year_map yearMap
WHERE harvesting_year_id = yearMap.fromYearId
;

UPDATE modcntr_log
SET harvesting_year_id = yearMap.toYearId,
    updated            = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby          = 100
FROM tmp_year_map yearMap
WHERE harvesting_year_id = yearMap.fromYearId
;

UPDATE modcntr_settings
SET c_year_id = yearMap.toYearId,
    updated   = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby = 100
FROM tmp_year_map yearMap
WHERE c_year_id = yearMap.fromYearId
;

DELETE
FROM c_year
WHERE c_calendar_id = 540014
;

DELETE
FROM c_calendar
WHERE c_calendar_id = 540014
;

