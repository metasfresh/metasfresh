--
-- might be overridden in an endcustomer project
--
CREATE OR REPLACE FUNCTION x_mrp_productinfo_attributeval_v(IN dateat date, IN M_Product_ID numeric)
	RETURNS TABLE(
	  	ad_client_id numeric,
	  	ad_org_id numeric,
	  	m_product_id numeric,
	  	name text,
	  	value text, -- 5
	  	ispurchased character,
	  	issold character,
	  	m_product_category_id numeric,
	  	isactive character,
	  	dategeneral date, -- 10
	  	groupname text,
	  	PMM_QtyPromised_OnDate numeric,
	  	qtyreserved_ondate numeric,
	  	qtyordered_ondate numeric,
	  	qtymaterialentnahme numeric, -- 15
	  	fresh_qtyonhand_ondate numeric, 
	  	fresh_qtypromised numeric,
	  	fresh_qtymrp numeric
  	) AS
$BODY$
SELECT
	ad_client_id, 
	ad_org_id, 
	m_product_id, 
	name, 
	value, -- 5
	ispurchased, 
	issold, 
	m_product_category_id, 
	isactive,
	DateGeneral, -- 10
	GroupName,
	SUM(PMM_QtyPromised_OnDate) AS PMM_QtyPromised_OnDate, -- FRESH-86
	SUM(qtyreserved_ondate) AS qtyreserved_ondate, 
	SUM(qtyordered_ondate) AS qtyordered_ondate, 
	SUM(qtymaterialentnahme) AS qtymaterialentnahme,  -- 15
	SUM(fresh_qtyonhand_ondate) AS fresh_qtyonhand_ondate, 
	SUM(fresh_qtypromised) AS fresh_qtypromised, 
	SUM(fresh_qtymrp) AS fresh_qtymrp
FROM (
	SELECT *
	FROM "de.metas.fresh".X_MRP_ProductInfo_AttributeVal_Raw_V
	WHERE DateGeneral=$1 AND M_Product_ID=$2
	UNION ALL
	SELECT DISTINCT
		p.ad_client_id, p.ad_org_id, p.m_product_id, p.name, 
		p.value, p.ispurchased, p.issold, p.m_product_category_id, p.isactive,
		$1::date,
		dim.GroupName,
		0::numeric, -- FRESH-86
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
		AND p.M_Product_ID=$2
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
  LANGUAGE sql STABLE
  COST 100
  ROWS 1000;
COMMENT ON FUNCTION x_mrp_productinfo_attributeval_v(date, numeric) IS 'This function is a union of X_MRP_ProductInfo_AttributeVal_V_Raw and the dimension spec''s attribute values that do *not* have matching X_MRP_ProductInfo_Detail_MV records for the given date';
