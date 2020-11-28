-- View: "de.metas.materialtracking".c_invoice_candidates_to_update

-- DROP VIEW "de.metas.materialtracking".c_invoice_candidates_to_update;

CREATE OR REPLACE VIEW "de.metas.materialtracking".c_invoice_candidates_to_update AS 
 SELECT mt.lot, ppo_dt.name AS ppo_dt_name, ppo.documentno, ppo.pp_order_id, ppo_r.updated, ppo_r.qtyprojected, ic.m_material_tracking_id, ic.c_invoice_candidate_id, ic_p.value, ic.qtytoinvoice, ic.qtyinvoiced, ic.created
   FROM pp_order ppo
   JOIN c_doctype ppo_dt ON ppo_dt.c_doctype_id = ppo.c_doctype_id
   JOIN pp_order_report ppo_r ON ppo_r.pp_order_id = ppo.pp_order_id AND ppo_r.name::text = 'Ausbeute (Marktfähige Ware)'::text
   JOIN c_invoice_candidate ic ON ic.record_id = ppo.pp_order_id AND ic.ad_table_id = (( SELECT ad_table.ad_table_id
   FROM ad_table
  WHERE ad_table.tablename::text = 'PP_Order'::text))
   JOIN m_product ic_p ON ic_p.m_product_id = ic.m_product_id
   LEFT JOIN m_material_tracking mt ON mt.m_material_tracking_id = ic.m_material_tracking_id
  WHERE true AND ic_p.value::text ~~* 'MT_Fee%%'::text AND (ic.qtytoinvoice + ic.qtyinvoiced) <> ppo_r.qtyprojected
  ORDER BY mt.lot, ppo.pp_order_id;
