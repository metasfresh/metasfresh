-- View: "de.metas.materialtracking".c_invoice_candidates_surplus_for_regular_orders

-- DROP VIEW "de.metas.materialtracking".c_invoice_candidates_surplus_for_regular_orders;

CREATE OR REPLACE VIEW "de.metas.materialtracking".c_invoice_candidates_surplus_for_regular_orders AS 
 SELECT lk.name AS lk_name, lkv.validto, lkm.m_qualityinsp_lagerkonf_month_adj_id, po.description, ic.c_invoice_candidate_id, ic.qtytoinvoice, ic.priceactual, ic.processed, ic_p.value AS ic_p_value, ic_p.name AS ic_p_name
   FROM m_material_tracking mt
   JOIN m_qualityinsp_lagerkonf_version lkv ON lkv.m_qualityinsp_lagerkonf_version_id = mt.m_qualityinsp_lagerkonf_version_id
   LEFT JOIN m_qualityinsp_lagerkonf_month_adj lkm ON lkm.m_qualityinsp_lagerkonf_version_id = lkv.m_qualityinsp_lagerkonf_version_id
   JOIN m_qualityinsp_lagerkonf lk ON lk.m_qualityinsp_lagerkonf_id = lkv.m_qualityinsp_lagerkonf_id
   JOIN m_material_tracking_ref mtr ON mtr.m_material_tracking_id = mt.m_material_tracking_id AND mtr.isqualityinspectiondoc = 'N'::bpchar AND mtr.ad_table_id = get_table_id('PP_Order'::character varying)
   JOIN pp_order po ON po.pp_order_id = mtr.record_id
   JOIN c_invoice_candidate ic ON ic.m_material_tracking_id = mt.m_material_tracking_id
   JOIN m_product ic_p ON ic_p.m_product_id = ic.m_product_id AND ic_p.m_product_id = lkv.m_product_regularpporder_id
  WHERE true AND lkm.m_qualityinsp_lagerkonf_month_adj_id IS NULL;
