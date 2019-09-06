
-- 2019-08-02T11:27:27.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM_Trl SET Description='Please don''t use this UOM (anymore). Instead, please use the "KGM" UOM with ID=540017', Name='Kilogramm (mandant metasfresh)', UOMSymbol='kg', IsTranslated='Y' WHERE C_UOM_ID=1000000
;

-- 2019-08-02T11:27:30.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM_Trl SET Description='Please don''t use this UOM (anymore). Instead, please use the "KGM" UOM with ID=540017', Name='Kilogramm (mandant metasfresh)', UOMSymbol='kg', IsTranslated='Y' WHERE C_UOM_ID=1000000
;

-- conversion to convert exiting records from the legacy KGM to the "new" one
-- 2019-08-02T11:27:43.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_UOM_Conversion (AD_Client_ID,AD_Org_ID,Created,CreatedBy,C_UOM_Conversion_ID,C_UOM_ID,C_UOM_To_ID,DivideRate,IsActive,IsCatchUOMForProduct,MultiplyRate,Updated,UpdatedBy) VALUES (1000000,0,TO_TIMESTAMP('2019-08-02 13:27:43','YYYY-MM-DD HH24:MI:SS'),100,540011,1000000,540017,1.000000000000,'Y','N',1.000000000000,TO_TIMESTAMP('2019-08-02 13:27:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T11:27:48.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM SET IsActive='N',Updated=TO_TIMESTAMP('2019-08-02 13:27:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=1000000
;


update C_UOM_Conversion SET C_UOM_ID=540017/*system level KGM*/ WHERE C_UOM_ID=1000000/*deprecated client-level KGM*/ AND COALESCE(M_Product_ID,0)>0;
update C_UOM_Conversion SET C_UOM_TO_ID=540017/*system level KGM*/ WHERE C_UOM_TO_ID=1000000/*deprecated client-level KGM*/;






/*
The following updated are created with this SQL:
select --*, 
'UPDATE '||"FK_Table"||' SET '||"FK_Column"||'=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE '||"FK_Column"||'=1000000/*deprecated client-level KGM*/;'
from
(
SELECT conrelid::regclass AS "FK_Table"
      ,CASE WHEN pg_get_constraintdef(c.oid) LIKE 'FOREIGN KEY %' THEN substring(pg_get_constraintdef(c.oid), 14, position(')' in pg_get_constraintdef(c.oid))-14) END AS "FK_Column"
      ,CASE WHEN pg_get_constraintdef(c.oid) LIKE 'FOREIGN KEY %' THEN substring(pg_get_constraintdef(c.oid), position(' REFERENCES ' in pg_get_constraintdef(c.oid))+12, position('(' in substring(pg_get_constraintdef(c.oid), 14))-position(' REFERENCES ' in pg_get_constraintdef(c.oid))+1) END AS "PK_Table"
      ,CASE WHEN pg_get_constraintdef(c.oid) LIKE 'FOREIGN KEY %' THEN substring(pg_get_constraintdef(c.oid), position('(' in substring(pg_get_constraintdef(c.oid), 14))+14, position(')' in substring(pg_get_constraintdef(c.oid), position('(' in substring(pg_get_constraintdef(c.oid), 14))+14))-1) END AS "PK_Column"
FROM pg_constraint c
JOIN pg_namespace n ON n.oid = c.connamespace
WHERE contype IN ('f', 'p ')
	AND pg_get_constraintdef(c.oid) LIKE 'FOREIGN KEY %'
ORDER  BY pg_get_constraintdef(c.oid), conrelid::regclass::text, contype DESC
) foo
where "PK_Table"='c_uom' AND "PK_Column"='c_uom_id' AND "FK_Table" NOT IN ('c_uom','c_uom_conversion','c_uom_trl')
;
-- Many thanks to https://dba.stackexchange.com/a/156684/127433 and others
*/

UPDATE m_inoutline SET catch_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE catch_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_shipmentschedule SET catch_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE catch_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_shipmentschedule_qtypicked SET catch_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE catch_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_hu_packingmaterial SET c_uom_dimension_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_dimension_id=1000000/*deprecated client-level KGM*/;
UPDATE dd_orderline SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE i_fajournal SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE i_gljournal SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE i_pricelist SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_requisitionline SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE pp_cost_collector SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE pp_order SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE pp_order_bom SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE pp_order_bomline SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE pp_product_bom SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE pp_product_bomline SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE c_customs_invoice_line SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE c_flatrate_conditions SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE c_flatrate_dataentry SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE c_flatrate_term SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE c_invoice_candidate SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE c_invoice_detail SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE c_invoiceline SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE c_invoice_line_alloc SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE c_olcand SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE c_orderline SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE c_purchasecandidate SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE c_rfqline SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE c_rfqlineqty SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE c_rfqresponseline SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE dd_orderline_alternative SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE edi_desadvline SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE fact_acct SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE fact_acct_activitychangerequest SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE gl_journalline SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_attribute SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_cost SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_costdetail SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_forecastline SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_hu_item_storage SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_hu_item_storage_snapshot SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_hu_lutu_configuration SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_hu_pi_attribute SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_hu_pi_item_product SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_hu_reservation SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_hu_storage SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_hu_storage_snapshot SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_hu_trx_line SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_inoutline SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_inventoryline SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_inventoryline_hu SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_matchinv SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_material_balance_detail SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_picking_candidate SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_product SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_product_po SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_productprice SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_productpricevendorbreak SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_qualityinsp_lagerkonf_month_adj SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_qualityinsp_lagerkonf_processingfee SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_receiptschedule SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_shipment_declaration_line SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE pmm_purchasecandidate SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE pp_order_node SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE pp_order_qty SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE pp_order_report SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE s_expensetype SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE s_resourcetype SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE s_timeexpenseline SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE s_training SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE i_order SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE i_product SET c_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE c_olcand SET c_uom_internal_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_internal_id=1000000/*deprecated client-level KGM*/;
UPDATE ad_clientinfo SET c_uom_length_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_length_id=1000000/*deprecated client-level KGM*/;
UPDATE m_packagingcontainer SET c_uom_length_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_length_id=1000000/*deprecated client-level KGM*/;
UPDATE m_qualityinsp_lagerkonf_version SET c_uom_scrap_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_scrap_id=1000000/*deprecated client-level KGM*/;
UPDATE ad_clientinfo SET c_uom_time_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_time_id=1000000/*deprecated client-level KGM*/;
UPDATE ad_clientinfo SET c_uom_volume_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_volume_id=1000000/*deprecated client-level KGM*/;
UPDATE ad_clientinfo SET c_uom_weight_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_weight_id=1000000/*deprecated client-level KGM*/;
UPDATE m_hu_packingmaterial SET c_uom_weight_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_weight_id=1000000/*deprecated client-level KGM*/;
UPDATE m_packagingcontainer SET c_uom_weight_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE c_uom_weight_id=1000000/*deprecated client-level KGM*/;
UPDATE i_pharma_product SET package_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE package_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE i_product SET package_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE package_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE m_product SET package_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE package_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE c_invoice_candidate SET price_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE price_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE c_invoice_detail SET price_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE price_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE c_invoiceline SET price_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE price_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE c_orderline SET price_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE price_uom_id=1000000/*deprecated client-level KGM*/;
UPDATE c_olcand SET price_uom_internal_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE price_uom_internal_id=1000000/*deprecated client-level KGM*/;
UPDATE m_product SET salesgroup_uom_id=540017/*system level KGM*/, Updated=now(), UpdatedBy=99 WHERE salesgroup_uom_id=1000000/*deprecated client-level KGM*/;
