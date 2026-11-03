CREATE TEMP TABLE deprecated_kg_uoms AS (SELECT c_uom_id
                                         FROM c_uom
                                         WHERE x12de355 = 'KGM'
                                           AND c_uom_id != 540017)
;

UPDATE c_callorderdetail
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_callorderdetail
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_callordersummary
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_campaign_price
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_commission_instance
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_compensationgroup_schema_templateline
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_customs_invoice_line
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_flatrate_closing
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_flatrate_conditions
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_flatrate_dataentry
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_flatrate_dataentry_detail
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_flatrate_term
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_invoice_candidate
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_invoice_candidate
SET price_uom_id = 540017
WHERE price_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_invoice_detail
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_invoice_detail
SET price_uom_id = 540017
WHERE price_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_invoice_line_alloc
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_invoicecandidate_inoutline
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_invoiceline
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_invoiceline
SET price_uom_id = 540017
WHERE price_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_olcand
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_olcand
SET qtyshipped_catchweight_uom_id = 540017
WHERE qtyshipped_catchweight_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_order_cost_detail
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_orderline
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_orderline
SET price_uom_id = 540017
WHERE price_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_orderline_detail
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_pos_orderline
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_pos_orderline
SET catch_uom_id = 540017
WHERE catch_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_project_repair_consumption_summary
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_project_repair_costcollector
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_project_repair_task
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_projectline
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_purchasecandidate
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_purchasecandidate
SET price_uom_id = 540017
WHERE price_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_rfqline
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_rfqlineqty
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_rfqresponseline
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE c_uom_conversion
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE carrier_shipmentorder_item
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE dd_order_candidate
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE dd_order_candidate_ddorder
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE dd_order_moveschedule
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE dd_orderline
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE dd_orderline_alternative
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE dd_orderline_hu_candidate
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE dhl_shipper_config
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE dhl_shipper_config
SET dhl_lenghtuom_id = 540017
WHERE dhl_lenghtuom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE edi_desadvline
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE edi_desadvline_inoutline
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE externalsystem_config_shopware6_uom
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE externalsystem_config_shopware6_uom
SET externalsystem_config_shopware6_uom_id = 540017
WHERE externalsystem_config_shopware6_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE fact_acct
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE fact_acct_activitychangerequest
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE gl_journalline
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE i_campaign_price
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE i_fajournal
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE i_forecast
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE i_gljournal
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE i_order
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE i_pharma_product
SET package_uom_id = 540017
WHERE package_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE i_pricelist
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE i_product
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE i_product
SET dosageuom_id = 540017
WHERE dosageuom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE i_product
SET package_uom_id = 540017
WHERE package_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE i_product
SET qtycu_uom_id = 540017
WHERE qtycu_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE i_product
SET weight_uom_id = 540017
WHERE weight_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE i_product
SET netweight_uom_id = 540017
WHERE netweight_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_attribute
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_cost
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_costdetail
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_costrevaluation_detail
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_costrevaluationline
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_forecastline
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_hu_item_storage
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_hu_item_storage_snapshot
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_hu_lutu_configuration
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_hu_pi_attribute
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_hu_pi_item_product
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_hu_reservation
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_hu_storage
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_hu_storage_snapshot
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_hu_trace
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_hu_trx_line
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_inout_cost
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_inoutline
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_inoutline
SET catch_uom_id = 540017
WHERE catch_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_inoutline_to_c_customs_invoice_line
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_inventory_candidate
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_inventoryline
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_inventoryline_hu
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_matchinv
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_material_balance_detail
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_picking_candidate
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_picking_candidate_issuetoorder
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_picking_job_hualternative
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_picking_job_line
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_picking_job_line
SET catch_uom_id = 540017
WHERE catch_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_picking_job_schedule
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_picking_job_step
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_picking_job_step_hualternative
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_picking_job_step_pickedhu
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_picking_job_step_pickedhu
SET catch_uom_id = 540017
WHERE catch_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_product
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_product
SET dosageuom_id = 540017
WHERE dosageuom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_product
SET grossweight_uom_id = 540017
WHERE grossweight_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_product
SET issuingtolerance_uom_id = 540017
WHERE issuingtolerance_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_product
SET package_uom_id = 540017
WHERE package_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_product
SET salesgroup_uom_id = 540017
WHERE salesgroup_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;



UPDATE m_product_allergen
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_product_allergen_trace
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_product_ingredients
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_product_nutrition
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_product_po
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_productprice
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_productpricevendorbreak
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_qualityinsp_lagerkonf_month_adj
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_qualityinsp_lagerkonf_processingfee
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_receiptschedule
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_receiptschedule
SET catch_uom_id = 540017
WHERE catch_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_receiptschedule_alloc
SET catch_uom_id = 540017
WHERE catch_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_requisitionline
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_shipment_declaration_line
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_shipmentschedule
SET catch_uom_id = 540017
WHERE catch_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE m_shipmentschedule_qtypicked
SET catch_uom_id = 540017
WHERE catch_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE pmm_purchasecandidate
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE pmm_qtyreport_event
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE pp_cost_collector
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE pp_order
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE pp_order_bom
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE pp_order_bomline
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE pp_order_bomline
SET issuingtolerance_uom_id = 540017
WHERE issuingtolerance_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE pp_order_candidate
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE pp_order_cost
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE pp_order_issueschedule
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE pp_order_node
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE pp_order_qty
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE pp_order_report
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE pp_order_weighting_run
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE pp_order_weighting_runcheck
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE pp_ordercandidate_pp_order
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE pp_orderline_candidate
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE pp_product_bom
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE pp_product_bomline
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE pp_product_bomline
SET issuingtolerance_uom_id = 540017
WHERE issuingtolerance_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE pp_product_planning
SET maxmanufacturedqtyperorderdispo_uom_id = 540017
WHERE maxmanufacturedqtyperorderdispo_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE pp_weighting_spec
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE s_expensetype
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE s_issue
SET effort_uom_id = 540017
WHERE effort_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE s_resource
SET capacityperproductioncycle_uom_id = 540017
WHERE capacityperproductioncycle_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE s_resourcetype
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE s_timeexpenseline
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE s_training
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE t_trialbalance
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

UPDATE test
SET c_uom_id = 540017
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;


DO
$$
    BEGIN
        IF EXISTS (SELECT 1
                   FROM information_schema.columns
                   WHERE table_name = 'm_product'
                     AND column_name = 'weight_uom_id') THEN
            UPDATE m_product
            SET weight_uom_id = 540017
            WHERE weight_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms);
        END IF;
    END
$$
;



DELETE
FROM c_uom_trl
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;


DELETE
FROM c_uom
WHERE c_uom_id IN (SELECT c_uom_id FROM deprecated_kg_uoms)
;

