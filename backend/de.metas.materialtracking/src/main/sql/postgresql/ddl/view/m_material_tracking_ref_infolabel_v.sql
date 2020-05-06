-- View: "de.metas.materialtracking".m_material_tracking_ref_infolabel_v

-- DROP VIEW "de.metas.materialtracking".m_material_tracking_ref_infolabel_v;

CREATE OR REPLACE VIEW "de.metas.materialtracking".m_material_tracking_ref_infolabel_v AS 
 SELECT t.tablename, mt.lot, COALESCE((((((o.documentno::text || ' - '::text) || ol.line) || '  - '::text) || ol_p.value::text) || '_'::text) || ol_p.name::text, (((((((io_dt.name::text || ' '::text) || io.documentno::text) || ' - '::text) || iol.line) || ' - '::text) || iol_p.value::text) || '_'::text) || iol_p.name::text, (po_dt.name::text || ' '::text) || po.documentno::text, (((ic.c_invoice_candidate_id::text || ' - '::text) || ic_p.value::text) || '_'::text) || ic_p.name::text, i.documentno::text) AS label, po.pp_order_id, io.m_inout_id, o.c_order_id, (i_dt.name::text || ' '::text) || i.c_invoice_id, mtr.ad_client_id, mtr.ad_org_id, mtr.ad_table_id, mtr.created, mtr.createdby, mtr.isactive, mtr.isqualityinspectiondoc, mtr.m_material_tracking_id, mtr.m_material_tracking_ref_id, mtr.record_id, mtr.updated, mtr.updatedby
   FROM m_material_tracking_ref mtr
   JOIN m_material_tracking mt ON mt.m_material_tracking_id = mtr.m_material_tracking_id
   JOIN ad_table t ON t.ad_table_id = mtr.ad_table_id
   LEFT JOIN c_orderline ol ON t.tablename::text = 'C_OrderLine'::text AND ol.c_orderline_id = mtr.record_id
   LEFT JOIN c_order o ON o.c_order_id = ol.c_order_id
   LEFT JOIN m_product ol_p ON ol_p.m_product_id = ol.m_product_id
   LEFT JOIN m_inoutline iol ON t.tablename::text = 'M_InOutLine'::text AND iol.m_inoutline_id = mtr.record_id
   LEFT JOIN m_inout io ON io.m_inout_id = iol.m_inout_id
   LEFT JOIN c_doctype io_dt ON io_dt.c_doctype_id = io.c_doctype_id
   LEFT JOIN m_product iol_p ON iol_p.m_product_id = iol.m_product_id
   LEFT JOIN pp_order po ON t.tablename::text = 'PP_Order'::text AND po.pp_order_id = mtr.record_id
   LEFT JOIN c_doctype po_dt ON po_dt.c_doctype_id = po.c_doctype_id
   LEFT JOIN c_invoice_candidate ic ON t.tablename::text = 'C_Invoice_Candidate'::text AND ic.c_invoice_candidate_id = mtr.record_id
   LEFT JOIN m_product ic_p ON ic_p.m_product_id = ic.m_product_id
   LEFT JOIN c_invoice i ON t.tablename::text = 'C_Invoice'::text AND i.c_invoice_id = mtr.record_id
   LEFT JOIN c_doctype i_dt ON i_dt.c_doctype_id = i.c_doctype_id
  WHERE true
  ORDER BY mtr.m_material_tracking_ref_id;
