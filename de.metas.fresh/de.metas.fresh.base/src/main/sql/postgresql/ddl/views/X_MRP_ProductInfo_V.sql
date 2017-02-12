--
-- endcustomer projects can contain an overriding version of this view
--
DROP VIEW IF EXISTS X_MRP_ProductInfo_V;
CREATE OR REPLACE VIEW X_MRP_ProductInfo_V AS
SELECT 
	p.ad_client_id, p.ad_org_id, p.m_product_id, p.name AS ProductName, 
	p.value, p.ispurchased, p.issold, p.m_product_category_id, p.isactive,
	v.DateGeneral,
	SUM(v.QtyOnHand) AS QtyOnHand, -- gh #213
	SUM(v.PMM_QtyPromised_OnDate) AS PMM_QtyPromised_OnDate, -- FRESH-86
	SUM(v.qtyreserved_ondate) AS qtyreserved_ondate, 
	SUM(v.qtyordered_ondate) AS qtyordered_ondate, 
	SUM(v.qtymaterialentnahme) AS qtymaterialentnahme, 
	SUM(v.fresh_qtyonhand_ondate) AS fresh_qtyonhand_ondate, 
	SUM(v.fresh_qtypromised) AS fresh_qtypromised, 
	(
		SELECT SUM(Fresh_QtyMRP)
		FROM "de.metas.fresh".X_MRP_ProductInfo_Detail_Poor_Mans_MRP mrp
		WHERE mrp.DateGeneral = v.DateGeneral AND mrp.M_Product_MRP_ID = p.M_Product_ID
		GROUP BY DateGeneral, M_Product_MRP_ID
	) AS Fresh_QtyMRP

FROM
	M_Product p
		JOIN X_MRP_ProductInfo_Detail_MV v ON v.M_Product_ID=p.M_Product_ID
GROUP BY
	p.ad_client_id, p.ad_org_id, p.m_product_id, p.name, 
	p.value, p.ispurchased, p.issold, p.m_product_category_id, p.isactive,
	v.DateGeneral;
COMMENT On VIEW X_MRP_ProductInfo_V IS 
'This is the view the is displayed in the main table of the MRP product info window. It heavily depends on X_MRP_ProductInfo_Detail_MV';