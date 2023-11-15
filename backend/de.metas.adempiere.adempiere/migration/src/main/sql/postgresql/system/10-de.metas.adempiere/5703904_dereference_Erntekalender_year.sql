/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


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
