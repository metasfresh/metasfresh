-- View: "de.metas.materialtracking".pp_order_materialtracking_id

 DROP VIEW  IF EXISTS "de.metas.materialtracking".pp_order_materialtracking_id;

CREATE OR REPLACE VIEW "de.metas.materialtracking".pp_order_materialtracking_id AS 
 SELECT 
	mtr.m_material_tracking_id,
	mtr.isqualityinspectiondoc,
	ppo.documentno,
	ppo.s_resource_id,
	ppo.m_product_id,
	ppo.m_warehouse_id,
	ppo.assay,
	ppo.c_activity_id,
	ppo.c_campaign_id,
	ppo.c_doctypetarget_id,
	ppo.c_doctype_id,
	ppo.c_orderline_id,
	COALESCE(ol.C_Project_ID, o.C_Project_ID) as c_project_id,
	ppo.c_uom_id,
	ppo.copyfrom,
	ppo.created,
	ppo.createdby,
	ppo.dateconfirm,
	ppo.datedelivered,
	ppo.datefinish,
	ppo.datefinishschedule,
	ppo.dateordered,
	ppo.datepromised,
	ppo.datestart,
	ppo.datestartschedule,
	ppo.description,
	ppo.docaction,
	ppo.docstatus,
	ppo.floatafter,
	ppo.floatbefored,
	ppo.isactive,
	ppo.isapproved,
	ppo.isprinted,
	ppo.isqtypercentage,
	ppo.issotrx,
	ppo.isselected,
	ppo.line,
	ppo.lot,
	ppo.m_attributesetinstance_id,
	ppo.ordertype,
	ppo.pp_order_id,
	ppo.pp_product_bom_id,
	ppo.planner_id,
	ppo.posted,
	ppo.priorityrule,
	ppo.processed,
	ppo.processing,
	ppo.qtybatchsize,
	ppo.qtybatchs,
	ppo.qtydelivered,
	ppo.qtyentered,
	ppo.qtyordered,
	ppo.qtyreject,
	ppo.qtyreserved,
	ppo.qtyscrap,
	ppo.scheduletype,
	ppo.serno,
	ppo.updated,
	ppo.updatedby,
	ppo.user1_id,
	ppo.user2_id,
	ppo.ad_client_id,
	ppo.yield,
	ppo.ad_orgtrx_id,
	ppo.ad_org_id,
	ppo.ad_workflow_id,
	ppo.preparationdate,
	ppo.c_bpartner_id,
	ppo.m_locator_id,
	ppo.m_hu_lutu_configuration_id,
	ppo.c_orderline_mto_id,
	ppo.qm_qtydeliveredpercofraw,
	ppo.qm_qtydeliveredavg,
	ppo.mrp_generated,
	ppo.mrp_allowcleanup,
	ppo.mrp_todelete,
	ppo.qtybeforeclose
   FROM m_material_tracking_ref mtr
   JOIN pp_order ppo ON ppo.pp_order_id = mtr.record_id
   LEFT JOIN C_OrderLine ol on ppo.C_OrderLine_ID = ol.C_OrderLine_ID
   LEFT JOIN C_Order o on ol.C_Order_Id = o.C_Order_ID
  WHERE mtr.ad_table_id = get_table_id('PP_Order'::character varying);
  
  
  
  
  
  
  
  
  

DELETE FROM AD_MENU 
where AD_MENU_ID IN ( select m.AD_MENU_ID from AD_MENU m 
JOIN ad_process p on m.AD_Process_ID = p.AD_Process_ID
JOIN ad_printformat pf on p.ad_printformat_ID = pf.ad_printformat_ID
JOIN AD_ReportView rv on pf.AD_ReportView_ID = rv.AD_ReportView_ID
WHERE rv.AD_ReportView_ID = 53001);





DELETE FROM ad_process 
where ad_process_ID IN ( select p.AD_Process_ID from ad_process p 
JOIN ad_printformat pf on p.ad_printformat_ID = pf.ad_printformat_ID
JOIN AD_ReportView rv on pf.AD_ReportView_ID = rv.AD_ReportView_ID
WHERE rv.AD_ReportView_ID = 53001);







DELETE FROM ad_printformat 
where ad_printformat_ID IN ( select pf.ad_printformat_ID
from  ad_printformat pf 
JOIN AD_ReportView rv on pf.AD_ReportView_ID = rv.AD_ReportView_ID
WHERE rv.AD_ReportView_ID = 53001);









DELETE FROM AD_ReportView WHERE AD_ReportView_ID = 53001;









  
DROP VIEW IF EXISTS rv_pp_order;
  
  
  
  
  
  
  
  
  
  