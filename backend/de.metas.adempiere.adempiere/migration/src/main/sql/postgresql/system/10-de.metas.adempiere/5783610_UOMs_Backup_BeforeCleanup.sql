/*

SELECT c.table_schema,
       c.table_name,
       c.column_name
FROM information_schema.columns c
         INNER JOIN information_schema.tables t
                    ON c.table_name = t.table_name
                        AND c.table_schema = t.table_schema
WHERE c.column_name ILIKE '%_UOM_ID'
  AND t.table_type = 'BASE TABLE'
  AND t.table_name NOT ILIKE '%_mv'
  AND t.table_name NOT ILIKE '%bkp%'
  AND t.table_name NOT ILIKE 'c_invoice_candidate_failed_to_update'
  AND t.table_name NOT ILIKE 'c_uom'
  AND t.table_name NOT ILIKE 'c_uom_trl'
  AND c.table_schema NOT IN ('backup', 'migration_data')
ORDER BY c.table_name
;
*/




select backup_table( 'c_uom'); 



SELECT backup_table('c_callorderdetail');
SELECT backup_table('c_callordersummary');
SELECT backup_table('c_campaign_price');
SELECT backup_table('c_commission_instance');
SELECT backup_table('c_compensationgroup_schema_templateline');
SELECT backup_table('c_customs_invoice_line');
SELECT backup_table('c_flatrate_closing');
SELECT backup_table('c_flatrate_conditions');
SELECT backup_table('c_flatrate_dataentry');
SELECT backup_table('c_flatrate_dataentry_detail');
SELECT backup_table('c_flatrate_term');
SELECT backup_table('c_invoice_candidate');
SELECT backup_table('c_invoice_detail');
SELECT backup_table('c_invoice_line_alloc');
SELECT backup_table('c_invoicecandidate_inoutline');
SELECT backup_table('c_invoiceline');
SELECT backup_table('c_olcand');
SELECT backup_table('c_order_cost_detail');
SELECT backup_table('c_orderline');
SELECT backup_table('c_orderline_detail');
SELECT backup_table('c_pos_orderline');
SELECT backup_table('c_project_repair_consumption_summary');
SELECT backup_table('c_project_repair_costcollector');
SELECT backup_table('c_project_repair_task');
SELECT backup_table('c_projectline');
SELECT backup_table('c_purchasecandidate');
SELECT backup_table('c_rfqline');
SELECT backup_table('c_rfqlineqty');
SELECT backup_table('c_rfqresponseline');
SELECT backup_table('c_uom_conversion');
SELECT backup_table('carrier_shipmentorder_item');
SELECT backup_table('dd_order_candidate');
SELECT backup_table('dd_order_candidate_ddorder');
SELECT backup_table('dd_order_moveschedule');
SELECT backup_table('dd_orderline');
SELECT backup_table('dd_orderline_alternative');
SELECT backup_table('dd_orderline_hu_candidate');
SELECT backup_table('dhl_shipper_config');
SELECT backup_table('edi_desadvline');
SELECT backup_table('edi_desadvline_inoutline');
SELECT backup_table('externalsystem_config_shopware6_uom');
SELECT backup_table('fact_acct');
SELECT backup_table('fact_acct_activitychangerequest');
SELECT backup_table('gl_journalline');
SELECT backup_table('i_campaign_price');
SELECT backup_table('i_fajournal');
SELECT backup_table('i_forecast');
SELECT backup_table('i_gljournal');
SELECT backup_table('i_order');
SELECT backup_table('i_pharma_product');
SELECT backup_table('i_pricelist');
SELECT backup_table('i_product');
SELECT backup_table('m_attribute');
SELECT backup_table('m_cost');
SELECT backup_table('m_costdetail');
SELECT backup_table('m_costrevaluation_detail');
SELECT backup_table('m_costrevaluationline');
SELECT backup_table('m_forecastline');
SELECT backup_table('m_hu_item_storage');
SELECT backup_table('m_hu_item_storage_snapshot');
SELECT backup_table('m_hu_lutu_configuration');
SELECT backup_table('m_hu_pi_attribute');
SELECT backup_table('m_hu_pi_item_product');
SELECT backup_table('m_hu_reservation');
SELECT backup_table('m_hu_storage');
SELECT backup_table('m_hu_storage_snapshot');
SELECT backup_table('m_hu_trace');
SELECT backup_table('m_hu_trx_line');
SELECT backup_table('m_inout_cost');
SELECT backup_table('m_inoutline');
SELECT backup_table('m_inoutline_to_c_customs_invoice_line');
SELECT backup_table('m_inventory_candidate');
SELECT backup_table('m_inventoryline');
SELECT backup_table('m_inventoryline_hu');
SELECT backup_table('m_matchinv');
SELECT backup_table('m_material_balance_detail');
SELECT backup_table('m_picking_candidate');
SELECT backup_table('m_picking_candidate_issuetoorder');
SELECT backup_table('m_picking_job_hualternative');
SELECT backup_table('m_picking_job_line');
SELECT backup_table('m_picking_job_schedule');
SELECT backup_table('m_picking_job_step');
SELECT backup_table('m_picking_job_step_hualternative');
SELECT backup_table('m_picking_job_step_pickedhu');
SELECT backup_table('m_product');
SELECT backup_table('m_product_allergen');
SELECT backup_table('m_product_allergen_trace');
SELECT backup_table('m_product_ingredients');
SELECT backup_table('m_product_nutrition');
SELECT backup_table('m_product_po');
SELECT backup_table('m_productprice');
SELECT backup_table('m_productpricevendorbreak');
SELECT backup_table('m_qualityinsp_lagerkonf_month_adj');
SELECT backup_table('m_qualityinsp_lagerkonf_processingfee');
SELECT backup_table('m_receiptschedule');
SELECT backup_table('m_receiptschedule_alloc');
SELECT backup_table('m_requisitionline');
SELECT backup_table('m_shipment_declaration_line');
SELECT backup_table('m_shipmentschedule');
SELECT backup_table('m_shipmentschedule_qtypicked');
SELECT backup_table('pmm_purchasecandidate');
SELECT backup_table('pmm_qtyreport_event');
SELECT backup_table('pp_cost_collector');
SELECT backup_table('pp_order');
SELECT backup_table('pp_order_bom');
SELECT backup_table('pp_order_bomline');
SELECT backup_table('pp_order_candidate');
SELECT backup_table('pp_order_cost');
SELECT backup_table('pp_order_issueschedule');
SELECT backup_table('pp_order_node');
SELECT backup_table('pp_order_qty');
SELECT backup_table('pp_order_report');
SELECT backup_table('pp_order_weighting_run');
SELECT backup_table('pp_order_weighting_runcheck');
SELECT backup_table('pp_ordercandidate_pp_order');
SELECT backup_table('pp_orderline_candidate');
SELECT backup_table('pp_product_bom');
SELECT backup_table('pp_product_bomline');
SELECT backup_table('pp_product_planning');
SELECT backup_table('pp_weighting_spec');
SELECT backup_table('s_expensetype');
SELECT backup_table('s_issue');
SELECT backup_table('s_resource');
SELECT backup_table('s_resourcetype');
SELECT backup_table('s_timeexpenseline');
SELECT backup_table('s_training');
SELECT backup_table('t_trialbalance');
SELECT backup_table('test');




