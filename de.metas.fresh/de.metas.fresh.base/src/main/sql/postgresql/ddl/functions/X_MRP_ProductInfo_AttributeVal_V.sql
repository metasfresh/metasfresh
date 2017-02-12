
CREATE OR REPLACE FUNCTION x_mrp_productinfo_attributeval_v(IN dateat date, IN M_Product_ID numeric)
	RETURNS TABLE(
	  	ad_client_id numeric,
	  	ad_org_id numeric,
	  	m_product_id numeric,
	  	name text,
	  	value text,
	  	ispurchased character,
	  	issold character,
	  	m_product_category_id numeric,
	  	isactive character,
	  	DateGeneral date,
	  	GroupName text,
		QtyOnHand numeric,
		PMM_QtyPromised_OnDate numeric,
	  	qtyreserved_ondate numeric,
	  	qtyordered_ondate numeric,
	  	qtymaterialentnahme numeric,
	  	fresh_qtyonhand_ondate numeric,
	  	"sp80_qtyonhand_ondate_stö2" numeric,
	  	sp80_qtyonhand_ondate_ind9 numeric,
	  	fresh_qtypromised numeric,
	  	fresh_qtymrp numeric
  	) AS
$BODY$
SELECT
	ad_client_id, ad_org_id, m_product_id, name, 
	value, ispurchased, issold, m_product_category_id, isactive,
	DateGeneral,
	GroupName,
	SUM(QtyOnHand) AS QtyOnHand, -- gh #213
	SUM(PMM_QtyPromised_OnDate) AS PMM_QtyPromised_OnDate, -- FRESH-86
	SUM(qtyreserved_ondate) AS qtyreserved_ondate, 
	SUM(qtyordered_ondate) AS qtyordered_ondate, 
	SUM(qtymaterialentnahme) AS qtymaterialentnahme, 
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
		$1::date AS DateGeneral,
		dim.GroupName,
		0::numeric AS QtyOnHand, -- QtyOnHand, gh #213
		0::numeric AS PMM_QtyPromised_OnDate, -- FRESH-86
		0::numeric AS qtyreserved_ondate,
		0::numeric AS qtyordered_ondate,
		0::numeric AS qtymaterialentnahme,
		0::numeric AS fresh_qtyonhand_ondate,
		0::numeric AS fresh_qtypromised,
		0::numeric AS fresh_qtymrp
	FROM "de.metas.dimension".DIM_Dimension_Spec_Attribute_AllValues dim
		JOIN M_Product p ON true
	WHERE true
		AND dim.InternalName='MRP_Product_Info_ASI_Values'
		AND p.M_Product_ID=$2
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
ALTER FUNCTION x_mrp_productinfo_attributeval_v(date, numeric)
  OWNER TO adempiere;
COMMENT ON FUNCTION x_mrp_productinfo_attributeval_v(date, numeric) IS 'This function is a union of X_MRP_ProductInfo_AttributeVal_V_Raw and the dimension spec''s attribute values that do *not* have matching X_MRP_ProductInfo_Detail_MV records for the given date';
