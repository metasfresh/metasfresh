
-----1000000 - Default calendar
-----540014 - Calendar to be removed

SELECT backup_table('c_flatrate_transition')
;

UPDATE c_flatrate_transition
SET c_calendar_contract_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_calendar_contract_id = 540014
;

SELECT backup_table('ad_clientinfo')
;

UPDATE ad_clientinfo
SET c_calendar_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_calendar_id = 540014
;

SELECT backup_table('ad_orginfo')
;

UPDATE ad_orginfo
SET c_calendar_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_calendar_id = 540014
;

SELECT backup_table('c_advcommissioncondition')
;

UPDATE c_advcommissioncondition
SET c_calendar_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_calendar_id = 540014
;

SELECT backup_table('c_bp_purchaseschedule')
;

UPDATE c_bp_purchaseschedule
SET c_calendar_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_calendar_id = 540014
;

SELECT backup_table('c_doctype_taxreporting')
;

UPDATE c_doctype_taxreporting
SET c_calendar_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_calendar_id = 540014
;

SELECT backup_table('c_nonbusinessday')
;

UPDATE c_nonbusinessday
SET c_calendar_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_calendar_id = 540014
;

UPDATE i_replenish
SET c_calendar_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_calendar_id = 540014
;

UPDATE m_demand
SET c_calendar_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_calendar_id = 540014
;

UPDATE m_forecast
SET c_calendar_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_calendar_id = 540014
;

SELECT backup_table('m_material_balance_config')
;

UPDATE m_material_balance_config
SET c_calendar_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_calendar_id = 540014
;

UPDATE m_replenish
SET c_calendar_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_calendar_id = 540014
;

UPDATE modcntr_settings
SET c_calendar_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_calendar_id = 540014
;

SELECT backup_table('pa_report')
;

UPDATE pa_report
SET c_calendar_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_calendar_id = 540014
;

SELECT backup_table('pa_reportcube')
;

UPDATE pa_reportcube
SET c_calendar_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_calendar_id = 540014
;

UPDATE c_acctschema_element
SET c_harvesting_calendar_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_harvesting_calendar_id = 540014
;

UPDATE c_bpartner_interimcontract
SET c_harvesting_calendar_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_harvesting_calendar_id = 540014
;

UPDATE c_invoice
SET c_harvesting_calendar_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_harvesting_calendar_id = 540014
;

UPDATE c_invoice_candidate
SET c_harvesting_calendar_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_harvesting_calendar_id = 540014
;

UPDATE c_invoiceline
SET c_harvesting_calendar_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_harvesting_calendar_id = 540014
;

UPDATE c_order
SET c_harvesting_calendar_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_harvesting_calendar_id = 540014
;

UPDATE c_validcombination
SET c_harvesting_calendar_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_harvesting_calendar_id = 540014
;

UPDATE m_inoutline
SET c_harvesting_calendar_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_harvesting_calendar_id = 540014
;

UPDATE m_shipping_notification
SET c_harvesting_calendar_id = 1000000,
    updated       = TO_TIMESTAMP('2023-09-22 22:22:22.222', 'YYYY-MM-DD HH24:MI:SS.US'),
    updatedby     = 100
WHERE c_harvesting_calendar_id = 540014
;
