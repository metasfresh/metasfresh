-- View: c_order_header_v

-- DROP VIEW c_order_header_v;
CREATE OR REPLACE VIEW c_order_header_v AS 
 SELECT o.ad_client_id, o.ad_org_id, o.isactive, o.created, o.createdby, o.updated, o.updatedby, 'en_US'::character varying AS ad_language, 
 o.c_order_id, o.issotrx, o.documentno, o.docstatus, o.c_doctype_id, o.c_bpartner_id, bp.value AS bpvalue, bp.taxid AS bptaxid, bp.naics, bp.duns,
 obpl.c_location_id AS org_location_id, oi.taxid, o.m_warehouse_id, wh.c_location_id AS warehouse_location_id, dt.printname AS documenttype, 
 dt.documentnote AS documenttypenote, o.salesrep_id, COALESCE(ubp.name, u.name) AS salesrep_name, o.dateordered, o.datepromised, bpg.greeting AS bpgreeting, 
 bp.name, bp.name2, bpcg.greeting AS bpcontactgreeting, bpc.title, bpc.phone, NULLIF(bpc.name::text, bp.name::text) AS contactname, bpl.c_location_id, 
 l.postal::text || l.postal_add::text AS postal, bp.referenceno, o.bill_bpartner_id, o.bill_location_id, o.bill_user_id, bbp.value AS bill_bpvalue, 
 bbp.taxid AS bill_bptaxid, bbp.name AS bill_name, bbp.name2 AS bill_name2, bbpc.title AS bill_title, bbpc.phone AS bill_phone, 
 NULLIF(bbpc.name::text, bbp.name::text) AS bill_contactname, bbpl.c_location_id AS bill_c_location_id, o.description, o.poreference, o.c_currency_id, 
 pt.name AS paymentterm, pt.documentnote AS paymenttermnote, o.c_charge_id, o.chargeamt, o.totallines, o.grandtotal, o.grandtotal AS amtinwords, 
 o.m_pricelist_id, o.istaxincluded, o.volume, o.weight, o.c_campaign_id, o.c_project_id, o.c_activity_id, o.m_shipper_id, o.deliveryrule, 
 o.deliveryviarule, o.priorityrule, o.invoicerule, COALESCE(oi.logo_id, ci.logo_id) AS logo_id
   FROM c_order o
   JOIN c_doctype dt ON o.c_doctype_id = dt.c_doctype_id
   JOIN m_warehouse wh ON o.m_warehouse_id = wh.m_warehouse_id
   JOIN c_paymentterm pt ON o.c_paymentterm_id = pt.c_paymentterm_id
   JOIN c_bpartner bp ON o.c_bpartner_id = bp.c_bpartner_id
   LEFT JOIN c_greeting bpg ON bp.c_greeting_id = bpg.c_greeting_id
   JOIN c_bpartner_location bpl ON o.c_bpartner_location_id = bpl.c_bpartner_location_id
   JOIN c_location l ON bpl.c_location_id = l.c_location_id
   LEFT JOIN ad_user bpc ON o.ad_user_id = bpc.ad_user_id
   LEFT JOIN c_greeting bpcg ON bpc.c_greeting_id = bpcg.c_greeting_id
   JOIN ad_orginfo oi ON o.ad_org_id = oi.ad_org_id
   JOIN c_bpartner obp ON o.ad_org_id = obp.ad_orgbp_id
   LEFT OUTER JOIN C_Bpartner_Location obpl ON obp.C_BPartner_ID = obpl.C_Bpartner_ID AND obpl.IsDefaultLocation = 'Y' -- location from bpartner location
   JOIN ad_clientinfo ci ON o.ad_client_id = ci.ad_client_id
   LEFT JOIN ad_user u ON o.salesrep_id = u.ad_user_id
   LEFT JOIN c_bpartner ubp ON u.c_bpartner_id = ubp.c_bpartner_id
   JOIN c_bpartner bbp ON o.bill_bpartner_id = bbp.c_bpartner_id
   JOIN c_bpartner_location bbpl ON o.bill_location_id = bbpl.c_bpartner_location_id
   LEFT JOIN ad_user bbpc ON o.bill_user_id = bbpc.ad_user_id;


GRANT ALL ON TABLE c_order_header_v TO adempiere;