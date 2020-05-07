-- View: m_inout_header_vt
--DROP VIEW m_inout_header_vt;
CREATE OR REPLACE VIEW m_inout_header_vt AS 
 SELECT io.ad_client_id, io.ad_org_id, io.isactive, io.created, io.createdby, io.updated, io.updatedby, dt.ad_language, io.m_inout_id, 
 io.issotrx, io.documentno, io.docstatus, io.c_doctype_id, io.c_bpartner_id, bp.value AS bpvalue, bp.taxid AS bptaxid, bp.naics, bp.duns, 
 obpl.c_location_id AS org_location_id, oi.taxid, io.m_warehouse_id, wh.c_location_id AS warehouse_location_id, dt.printname AS documenttype, 
 dt.documentnote AS documenttypenote, io.c_order_id, bpc.phone, io.movementdate, io.movementtype, bpg.greeting AS bpgreeting, bp.name, bp.name2, 
 bpcg.greeting AS bpcontactgreeting, bpc.title, NULLIF(bpc.name::text, bp.name::text) AS contactname, bpl.c_location_id, 
 l.postal::text || l.postal_add::text AS postal, bp.referenceno, io.description, io.poreference, io.dateordered, io.volume, io.weight, io.m_shipper_id, 
 io.deliveryrule, io.deliveryviarule, io.priorityrule, COALESCE(oi.logo_id, ci.logo_id) AS logo_id
   FROM m_inout io
   JOIN c_doctype_trl dt ON io.c_doctype_id = dt.c_doctype_id
   JOIN c_bpartner bp ON io.c_bpartner_id = bp.c_bpartner_id
   LEFT JOIN c_greeting_trl bpg ON bp.c_greeting_id = bpg.c_greeting_id AND dt.ad_language::text = bpg.ad_language::text
   JOIN c_bpartner_location bpl ON io.c_bpartner_location_id = bpl.c_bpartner_location_id
   JOIN c_location l ON bpl.c_location_id = l.c_location_id
   LEFT JOIN ad_user bpc ON io.ad_user_id = bpc.ad_user_id
   LEFT JOIN c_greeting_trl bpcg ON bpc.c_greeting_id = bpcg.c_greeting_id AND dt.ad_language::text = bpcg.ad_language::text
   JOIN ad_orginfo oi ON io.ad_org_id = oi.ad_org_id
   JOIN c_bpartner obp ON io.ad_org_id = obp.ad_orgbp_id
   LEFT OUTER JOIN C_Bpartner_Location obpl ON obp.C_BPartner_ID = obpl.C_Bpartner_ID AND obpl.IsDefaultLocation = 'Y' -- location from bpartner location;  LEFT JOIN c_bpartner_location obpl ON obp.c_bpartner_id = obpl.c_bpartner_id
   JOIN ad_clientinfo ci ON io.ad_client_id = ci.ad_client_id
   JOIN m_warehouse wh ON io.m_warehouse_id = wh.m_warehouse_id;


GRANT ALL ON TABLE m_inout_header_vt TO adempiere;
