-- View: "de.metas.materialtracking".c_invoice_candidates_with_wrong_c_tax_id

-- DROP VIEW "de.metas.materialtracking".c_invoice_candidates_with_wrong_c_tax_id;

CREATE OR REPLACE VIEW "de.metas.materialtracking".c_invoice_candidates_with_wrong_c_tax_id AS 
 SELECT ic.c_invoice_candidate_id, ic.m_pricingsystem_id AS m_pricingsystem_old_id, ps_old.name AS pricingsytem_name, ic.c_tax_id AS c_tax_old_id, t.name AS tax_name
   FROM c_invoice_candidate ic
   JOIN c_bpartner bp ON bp.c_bpartner_id = ic.bill_bpartner_id
   JOIN c_bp_group g ON g.c_bp_group_id = bp.c_bp_group_id
   JOIN m_pricingsystem ps_old ON ps_old.m_pricingsystem_id = ic.m_pricingsystem_id
   JOIN c_tax t ON t.c_tax_id = COALESCE(ic.c_tax_override_id, ic.c_tax_id)
  WHERE g.name::text = 'Ur-Produzent ohne MWST'::text AND COALESCE(ic.c_tax_override_id, ic.c_tax_id) <> 1000014::numeric AND ic.ad_table_id = get_table_id('PP_Order'::character varying);
