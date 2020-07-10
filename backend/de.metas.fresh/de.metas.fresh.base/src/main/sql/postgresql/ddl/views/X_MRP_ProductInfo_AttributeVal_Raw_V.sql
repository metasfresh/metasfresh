--
-- might be overridden in an endcustomer project
--
DROP VIEW IF EXISTS "de.metas.fresh".X_MRP_ProductInfo_AttributeVal_Raw_V;

CREATE OR REPLACE VIEW "de.metas.fresh".X_MRP_ProductInfo_AttributeVal_Raw_V AS
SELECT 
	p.ad_client_id, 
	p.ad_org_id, 
	p.m_product_id, 
	p.name, 
	p.value, 
	p.ispurchased, 
	p.issold, 
	p.m_product_category_id, 
	p.isactive,
	v.DateGeneral,
	dim.GroupName,
	SUM(COALESCE(v.QtyOnHand,0)) AS QtyOnHand, -- gh #213 note that not-fallback records might have qtyOnHand IS NULL
	SUM(v.PMM_QtyPromised_OnDate) AS PMM_QtyPromised_OnDate, -- FRESH-86
	SUM(v.qtyreserved_ondate) AS qtyreserved_ondate, 
	SUM(v.qtyordered_ondate) AS qtyordered_ondate, 
	SUM(v.qtymaterialentnahme) AS qtymaterialentnahme, 
	SUM(v.fresh_qtyonhand_ondate) AS fresh_qtyonhand_ondate, 
	SUM(v.fresh_qtypromised) AS fresh_qtypromised, 
	(
		SELECT SUM(Fresh_QtyMRP)
		FROM "de.metas.fresh".X_MRP_ProductInfo_Detail_Poor_Mans_MRP mrp
		WHERE mrp.DateGeneral = v.DateGeneral 
			AND mrp.M_Product_MRP_ID = p.M_Product_ID 
			AND dim.GroupName = ANY("de.metas.dimension".DIM_Get_GroupName('MRP_Product_Info_ASI_Values', mrp.ASIKey_MRP))
		GROUP BY DateGeneral, M_Product_MRP_ID
	) AS Fresh_QtyMRP
FROM
	(select distinct GroupName from "de.metas.dimension".DIM_Dimension_Spec_Attribute_AllValues where InternalName='MRP_Product_Info_ASI_Values') as dim
	JOIN M_Product p ON true 
		JOIN X_MRP_ProductInfo_Detail_MV v 
			ON v.M_Product_ID=p.M_Product_ID
				AND dim.GroupName = ANY(v.GroupNames)
WHERE true
GROUP BY
	p.ad_client_id, p.ad_org_id, p.m_product_id, p.name, 
	p.value, p.ispurchased, p.issold, p.m_product_category_id, p.isactive,
	v.DateGeneral,
	dim.GroupName
;
COMMENT ON VIEW "de.metas.fresh".X_MRP_ProductInfo_AttributeVal_Raw_V IS 'Selects those attribute values that have a matching X_MRP_ProductInfo_Detail_MV record.';

