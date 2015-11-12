-- View: pp_order_workflow_header_vt
--DROP VIEW pp_order_workflow_header_vt;
CREATE OR REPLACE VIEW pp_order_workflow_header_vt AS 
 SELECT o.ad_client_id, o.ad_org_id, o.isactive, o.created, o.createdby, o.updated, o.updatedby, owt.ad_language, o.pp_order_id, o.docstatus, 
 o.c_doctype_id, obpl.c_location_id AS org_location_id, oi.taxid, o.m_warehouse_id, wh.c_location_id AS warehouse_location_id, dt.printname AS documenttype, 
 dt.documentnote AS documenttypenote, o.planner_id, u.name AS salesrep_name, o.datestart, o.datestartschedule, o.floatafter, o.floatbefored, o.line, o.lot, 
 o.serno, o.c_uom_id, o.pp_product_bom_id, o.assay, o.c_orderline_id, o.priorityrule, o.qtybatchsize, o.qtybatchs, o.qtydelivered, o.qtyentered, o.qtyordered,
 o.dateconfirm, o.datedelivered, o.datefinish, o.datefinishschedule, o.dateordered, o.datepromised, o.qtyreject, o.qtyreserved, o.qtyscrap, o.s_resource_id, 
 o.c_campaign_id, o.c_project_id, o.c_activity_id, owt.name, owt.description, owt.help, ow.author, ow.cost, ow.documentno, ow.duration, ow.durationunit, 
 ow.version, ow.validfrom, ow.validto, ow.movingtime, ow.overlapunits, ow.ad_workflow_id, ow.publishstatus, ow.queuingtime, ow.setuptime, ow.unitscycles, 
 ow.waitingtime, ow.workflowtype, ow.workingtime, ow.yield, COALESCE(oi.logo_id, ci.logo_id) AS logo_id
   FROM pp_order o
   JOIN pp_order_workflow ow ON ow.pp_order_id = o.pp_order_id
   JOIN pp_order_workflow_trl owt ON owt.pp_order_workflow_id = ow.pp_order_workflow_id
   JOIN c_doctype dt ON o.c_doctype_id = dt.c_doctype_id
   JOIN m_warehouse wh ON o.m_warehouse_id = wh.m_warehouse_id
   JOIN ad_orginfo oi ON o.ad_org_id = oi.ad_org_id
   JOIN c_bpartner obp ON o.ad_org_id = obp.ad_orgbp_id
   LEFT OUTER JOIN C_Bpartner_Location obpl ON obp.C_BPartner_ID = obpl.C_Bpartner_ID AND obpl.IsDefaultLocation = 'Y' -- location from bpartner location;  LEFT JOIN c_bpartner_location obpl ON obp.c_bpartner_id = obpl.c_bpartner_id
   JOIN ad_clientinfo ci ON o.ad_client_id = ci.ad_client_id
   LEFT JOIN ad_user u ON o.planner_id = u.ad_user_id;


GRANT ALL ON TABLE pp_order_workflow_header_vt TO adempiere;