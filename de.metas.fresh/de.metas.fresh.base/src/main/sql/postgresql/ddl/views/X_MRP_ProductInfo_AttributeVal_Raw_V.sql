
CREATE OR REPLACE VIEW "de.metas.fresh".X_MRP_ProductInfo_AttributeVal_Raw_V AS
SELECT 
	p.ad_client_id, p.ad_org_id, p.m_product_id, p.name, 
	p.value, p.ispurchased, p.issold, p.m_product_category_id, p.isactive,
	v.DateGeneral,
	dim.GroupName,
	SUM(v.qtyreserved_ondate) AS qtyreserved_ondate, 
	SUM(v.qtyordered_ondate) AS qtyordered_ondate, 
	SUM(v.qtymaterialentnahme) AS qtymaterialentnahme, 
	SUM(v.fresh_qtyonhand_ondate) AS fresh_qtyonhand_ondate, 
	SUM(v."fresh_qtyonhand_ondate_stö2") AS "fresh_qtyonhand_ondate_stö2", 
	SUM(v.fresh_qtyonhand_ondate_ind9) AS fresh_qtyonhand_ondate_ind9, 
	SUM(v.fresh_qtypromised) AS fresh_qtypromised, 
	SUM(v.fresh_qtymrp) AS fresh_qtymrp
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

