--
-- endcustomer projects can contain an overriding version of this view
--
DROP VIEW IF EXISTS X_MRP_ProductInfo_V;
CREATE OR REPLACE VIEW X_MRP_ProductInfo_V AS
SELECT 
	p.ad_client_id, p.ad_org_id, p.m_product_id, p.name AS ProductName, 
	p.value, p.ispurchased, p.issold, p.m_product_category_id, p.isactive,
	v.DateGeneral,
	SUM(v.PMM_QtyPromised_OnDate) AS PMM_QtyPromised_OnDate, -- FRESH-86
	SUM(v.qtyreserved_ondate) AS qtyreserved_ondate, 
	SUM(v.qtyordered_ondate) AS qtyordered_ondate, 
	SUM(v.qtymaterialentnahme) AS qtymaterialentnahme, 
	SUM(v.fresh_qtyonhand_ondate) AS fresh_qtyonhand_ondate, 
	SUM(v.fresh_qtypromised) AS fresh_qtypromised, 
	COALESCE(( 
		SELECT sum(mrp.fresh_qtymrp) AS sum
		FROM "de.metas.fresh".x_mrp_productinfo_detail_poor_mans_mrp mrp
		WHERE mrp.dategeneral = v.dategeneral AND mrp.m_product_mrp_id = p.m_product_id
		GROUP BY mrp.dategeneral, mrp.m_product_mrp_id), 0::numeric) AS fresh_qtymrp
FROM
	M_Product p
		JOIN X_MRP_ProductInfo_Detail_MV v ON v.M_Product_ID=p.M_Product_ID
GROUP BY
	p.ad_client_id, p.ad_org_id, p.m_product_id, p.name, 
--	p.qtyreserved, p.qtyordered, p.qtyonhand, p.qtyavailable, 
	p.value, p.ispurchased, p.issold, p.m_product_category_id, p.isactive,
	v.DateGeneral
;
