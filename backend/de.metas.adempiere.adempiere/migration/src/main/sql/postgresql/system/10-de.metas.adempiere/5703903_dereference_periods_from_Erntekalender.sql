CREATE TEMP TABLE tmp_period_map
AS
SELECT toDeletePeriods.c_period_id fromPeriodId,
       oldPeriods.c_period_id      toPeriodId
FROM c_period oldPeriods
         INNER JOIN c_period toDeletePeriods ON oldPeriods.startdate = toDeletePeriods.startdate

WHERE oldPeriods.c_year_id IN (SELECT c_year_id FROM c_year WHERE c_calendar_id = 1000000 /*Default calendar*/)
  AND toDeletePeriods.c_year_id IN (SELECT c_year_id FROM c_year WHERE c_calendar_id = 540014 /*Erntekalender*/)
;

-- remove period-related info from Erntekalender calendar, moving all references to the same periods in the default calendar----
-----no backups, already created before----------------

UPDATE a_asset_disposed
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;

UPDATE a_asset_reval_entry
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;

UPDATE a_asset_split
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;

UPDATE a_asset_transfer
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;

UPDATE a_depreciation_build
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;

UPDATE a_depreciation_entry
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;

UPDATE c_acctschema
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;

UPDATE c_advcommissionpayroll
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;

UPDATE c_advcomrankforecast
SET c_period_since_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_since_id = tpm.fromPeriodId
;

UPDATE c_advcomrankforecast
SET c_period_until_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_until_id = tpm.fromPeriodId
;

UPDATE c_advcomsalesrepfact
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;

UPDATE c_flatrate_dataentry
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;

UPDATE c_periodcontrol
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;

UPDATE datevacctexport
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;

UPDATE gl_journal
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;

UPDATE gl_journalbatch
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;

UPDATE hr_period
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;

UPDATE i_fajournal
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;

UPDATE i_gljournal
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;

UPDATE i_replenish
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;

UPDATE m_demandline
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;

UPDATE m_forecast
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;

UPDATE m_forecastline
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;

UPDATE m_material_balance_detail
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;

UPDATE m_material_tracking_report
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;

UPDATE m_replenish
SET c_period_id = tpm.toPeriodId,
    updated     = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby   = 100
from tmp_period_map tpm
WHERE c_period_id = tpm.fromPeriodId
;
