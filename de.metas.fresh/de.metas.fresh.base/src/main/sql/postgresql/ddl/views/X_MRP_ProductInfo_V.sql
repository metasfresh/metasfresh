--
-- endcustomer projects can contain an overriding version of this view
--
DROP VIEW IF EXISTS X_MRP_ProductInfo_V;
CREATE OR REPLACE VIEW X_MRP_ProductInfo_V AS
SELECT 
	p.ad_client_id, p.ad_org_id, p.m_product_id, p.name AS ProductName, 
	p.value, p.ispurchased, p.issold, p.m_product_category_id, p.isactive,
	v.DateGeneral,
	SUM(v.qtyreserved_ondate) AS qtyreserved_ondate, 
	SUM(v.qtyordered_ondate) AS qtyordered_ondate, 
	SUM(v.qtymaterialentnahme) AS qtymaterialentnahme, 
	SUM(v.fresh_qtyonhand_ondate) AS fresh_qtyonhand_ondate, 
	SUM(v.fresh_qtypromised) AS fresh_qtypromised, 
	SUM(v.fresh_qtymrp) AS fresh_qtymrp
FROM
	M_Product p
		JOIN X_MRP_ProductInfo_Detail_MV v ON v.M_Product_ID=p.M_Product_ID
GROUP BY
	p.ad_client_id, p.ad_org_id, p.m_product_id, p.name, 
--	p.qtyreserved, p.qtyordered, p.qtyonhand, p.qtyavailable, 
	p.value, p.ispurchased, p.issold, p.m_product_category_id, p.isactive,
	v.DateGeneral
;
