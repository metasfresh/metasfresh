DROP VIEW IF EXISTS "de_metas_endcustomer_fresh_reports".PickingList;
CREATE OR REPLACE VIEW "de_metas_endcustomer_fresh_reports".PickingList AS 
SELECT
	v.bpartnervalue,
	v.bpartnername,
	v.bpartneraddress_override,
	v.orderdocumentno,
	v.warehousename,
	v.preparationdate,
	p.value as productvalue,
	v.productname,
	v.qtyordered,
	v.c_uom_id,
	v.ad_org_id,
	v.c_orderso_id,
	u.uomsymbol,
	l.value as locator
FROM m_packageable_v v
	JOIN m_product p on p.m_product_id = v.m_product_id
	JOIN m_picking_candidate pc on pc.m_shipmentschedule_id = v.m_shipmentschedule_id
	JOIN C_Uom u on u.C_Uom_id = v.C_Uom_id
	LEFT JOIN M_HU hu on hu.M_hu_id = pc.pickfrom_hu_id
	LEFT JOIN m_locator l on l.m_locator_id = hu.m_locator_id
ORDER BY l.value, l.x, l.y, l.z, l.x1;
