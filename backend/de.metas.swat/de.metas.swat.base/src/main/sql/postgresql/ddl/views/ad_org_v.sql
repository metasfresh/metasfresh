-- View: ad_org_v

-- DROP VIEW ad_org_v;

CREATE OR REPLACE VIEW ad_org_v AS 
 SELECT o.ad_client_id, o.ad_org_id, o.isactive, o.created, o.createdby, o.updated, o.updatedby, o.value, 
 o.name, o.description, o.issummary, bpl.c_location_id, i.duns, i.taxid, i.supervisor_id, i.parent_org_id, 
 i.ad_orgtype_id, i.m_warehouse_id, bp.c_bpartner_id
   FROM ad_org o
   JOIN ad_orginfo i ON o.ad_org_id = i.ad_org_id
   LEFT JOIN c_bpartner bp ON o.ad_org_id = bp.ad_orgbp_id
   LEFT OUTER JOIN C_Bpartner_Location bpl ON bp.C_BPartner_ID = bpl.C_Bpartner_ID AND bpl.IsDefaultLocation = 'Y'; -- location from bpartner location


GRANT ALL ON TABLE ad_org_v TO adempiere;


