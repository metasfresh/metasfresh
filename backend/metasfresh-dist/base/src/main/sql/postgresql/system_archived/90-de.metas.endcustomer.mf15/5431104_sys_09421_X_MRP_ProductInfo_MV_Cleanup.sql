
--Info-Window and view

-- 26.10.2015 10:42
-- URL zum Konzept
DELETE FROM  AD_InfoColumn_Trl WHERE AD_InfoColumn_ID=540132
;

-- 26.10.2015 10:42
-- URL zum Konzept
DELETE FROM AD_InfoColumn WHERE AD_InfoColumn_ID=540132
;

-- 26.10.2015 10:42
-- URL zum Konzept
DELETE FROM  AD_InfoColumn_Trl WHERE AD_InfoColumn_ID=540133
;

-- 26.10.2015 10:42
-- URL zum Konzept
DELETE FROM AD_InfoColumn WHERE AD_InfoColumn_ID=540133
;

-- 26.10.2015 10:43
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=551682
;

-- 26.10.2015 10:43
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=551682
;

-- 26.10.2015 10:43
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=551681
;

-- 26.10.2015 10:43
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=551681
;




COMMIT;

ALTER TABLE x_mrp_productinfo_detail_mv DROP COLUMN IF EXISTS "fresh_qtyonhand_ondate_stö2" CASCADE;
ALTER TABLE x_mrp_productinfo_detail_mv DROP COLUMN IF EXISTS fresh_qtyonhand_ondate_ind9 CASCADE;

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



DROP VIEW IF EXISTS "de.metas.fresh".X_MRP_ProductInfo_AttributeVal_Raw_V;

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


--
-- endcustomer projects can contain an overriding version of this view
--
DROP FUNCTION X_MRP_ProductInfo_AttributeVal_V(IN DateAt date) ;
CREATE OR REPLACE FUNCTION X_MRP_ProductInfo_AttributeVal_V(IN DateAt date) 
RETURNS TABLE(
	ad_client_id numeric, ad_org_id numeric, m_product_id numeric, name text, 
	value text, ispurchased character(1), issold character(1), m_product_category_id numeric, isactive character(1),
	DateGeneral date,
	GroupName text,
	qtyreserved_ondate numeric, 
	qtyordered_ondate numeric, 
	qtymaterialentnahme numeric, 
	fresh_qtyonhand_ondate numeric, 
	fresh_qtypromised numeric, 
	fresh_qtymrp numeric
) AS
$BODY$
SELECT
	ad_client_id, ad_org_id, m_product_id, name, 
	value, ispurchased, issold, m_product_category_id, isactive,
	DateGeneral,
	GroupName,
	SUM(qtyreserved_ondate) AS qtyreserved_ondate, 
	SUM(qtyordered_ondate) AS qtyordered_ondate, 
	SUM(qtymaterialentnahme) AS qtymaterialentnahme, 
	SUM(fresh_qtyonhand_ondate) AS fresh_qtyonhand_ondate, 
	SUM(fresh_qtypromised) AS fresh_qtypromised, 
	SUM(fresh_qtymrp) AS fresh_qtymrp
FROM (
	SELECT *
	FROM "de.metas.fresh".X_MRP_ProductInfo_AttributeVal_Raw_V
	WHERE DateGeneral=$1
	UNION ALL
	SELECT DISTINCT
		p.ad_client_id, p.ad_org_id, p.m_product_id, p.name, 
		p.value, p.ispurchased, p.issold, p.m_product_category_id, p.isactive,
		$1::date,
		dim.GroupName,
		0::numeric,
		0::numeric,
		0::numeric,
		0::numeric,
		0::numeric,
		0::numeric		
	FROM "de.metas.dimension".DIM_Dimension_Spec_Attribute_AllValues dim
		JOIN M_Product p ON true
	WHERE true
		AND dim.InternalName='MRP_Product_Info_ASI_Values'
		/*
		AND NOT EXISTS (
			SELECT 1 
			FROM X_MRP_ProductInfo_Detail_MV mv
			WHERE true
				AND mv.M_Product_ID=p.M_Product_ID
				AND mv.DateGeneral=$1
				AND (
						-- match on ASIKey containing ValueName as substring
						(mv.ASIKey ILIKE '%'||dim.ValueName||'%')
						-- or match on 'DIM_EMPTY' and the absence of any other match 
						OR (dim.ValueName='DIM_EMPTY' AND NOT EXISTS (select 1 from "de.metas.dimension".DIM_Dimension_Spec_Attribute_AllValues dim2 where dim2.InternalName=dim.InternalName and mv.ASIKey ILIKE '%'||dim2.ValueName||'%'))
					)
		)
		*/
) data
GROUP BY
	ad_client_id, ad_org_id, m_product_id, name, 
	value, ispurchased, issold, m_product_category_id, isactive,
	DateGeneral,
	GroupName
;
$BODY$
LANGUAGE sql STABLE;
COMMENT ON FUNCTION X_MRP_ProductInfo_AttributeVal_V(date) IS 'This function is a union of X_MRP_ProductInfo_AttributeVal_V_Raw and the dimension spec''s attribute values that do *not* have matching X_MRP_ProductInfo_Detail_MV records for the given date';
