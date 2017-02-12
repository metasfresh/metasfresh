
DROP FUNCTION IF EXISTS X_MRP_ProductInfo_Detail_Fallback_V(date);
CREATE OR REPLACE FUNCTION X_MRP_ProductInfo_Detail_Fallback_V(IN DateFrom date) RETURNS 
TABLE (
  M_Product_ID numeric(10,0),
  DateGeneral date,
  ASIKey character varying(2000),
  M_AttributesetInstance_ID numeric(10,0),
  qtyreserved_ondate numeric,
  qtyordered_ondate numeric,
  qtyordered_sale_ondate numeric,
  qtymaterialentnahme numeric,
  fresh_qtyonhand_ondate numeric,
  fresh_qtypromised numeric,
  QtyOnHand numeric, -- gh #213
  IsFallback character(1),
  GroupNames character varying[],
  PMM_QtyPromised_OnDate numeric,
  AD_Client_ID numeric(10,0),
  AD_Org_ID numeric(10,0),
  Created timestamp with time zone,
  CreatedBy numeric(10,0),
  IsActive character(1),
  Updated timestamp with time zone,
  UpdatedBy numeric(10,0)
) AS
$BODY$
SELECT data.* 
FROM
	(
	SELECT
		p_fallback.M_Product_ID
		,$1 AS DateGeneral
		,'' AS ASIKey
		,0::numeric(10,0) AS M_AttributesetInstance_ID
		,0::numeric AS QtyReserved_OnDate
		,0::numeric AS QtyOrdered_OnDate
		,0::numeric AS QtyOrdered_Sale_OnDate
		,0::numeric AS QtyMaterialentnahme 
		,0::numeric AS fresh_qtyonhand_ondate
		,0::numeric AS fresh_qtypromised
		,0::numeric AS QtyOnHand -- gh #213
		,'Y'::character(1) as IsFallback
		,"de.metas.dimension".DIM_Get_GroupName('MRP_Product_Info_ASI_Values', null) AS GroupNames
		,0::numeric AS PMM_QtyPromised_OnDate -- FRESH-86: Qty from PMM_PurchaseCandidate
		,p_fallback.AD_Client_ID
		,p_fallback.AD_Org_ID
		,now() AS Created
		,99::numeric(10,0) AS CreatedBy
		,p_fallback.IsActive
		,now() AS Updated
		,99::numeric(10,0) AS UpdatedBy
	FROM
		M_Product p_fallback
	UNION
	-- Insert empty records for those raw materials (AND their ASI) that are not yet included, but that have an end-product which is already included.
	-- This will allow us to update them later on
	SELECT 
		v.bl_M_Product_ID as M_Product_ID
		,mv.DateGeneral 
		,GenerateHUStorageASIKey(v.bl_M_AttributeSetInstance_ID, '') as ASIKey
		,v.bl_M_AttributeSetInstance_ID
		,0::numeric
		,0::numeric
		,0::numeric
		,0::numeric
		,0::numeric
		,0::numeric
		,0::numeric AS QtyOnHand -- gh #213
		,'Y'::character(1) as IsFallback
		,"de.metas.dimension".DIM_Get_GroupName('MRP_Product_Info_ASI_Values', GenerateHUStorageASIKey(v.bl_M_AttributeSetInstance_ID)) AS GroupNames
		,0::numeric AS PMM_QtyPromised_OnDate
		,p_fallback.AD_Client_ID
		,p_fallback.AD_Org_ID
		,now() AS Created
		,99::numeric(10,0) AS CreatedBy
		,p_fallback.IsActive
		,now() AS Updated
		,99::numeric(10,0) AS UpdatedBy
	FROM X_mrp_productinfo_detail_mv mv
		-- join the existing end-products from X_mrp_productinfo_detail_mv to their raw materials and add records for those raw materials
		JOIN "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V v 
				ON mv.M_Product_ID=v.b_m_Product_ID	AND mv.ASIKey=GenerateHUStorageASIKey(v.b_M_AttributeSetInstance_ID,'')
			JOIN M_Product p_fallback ON p_fallback.M_Product_ID=v.bl_M_Product_ID
				
	WHERE true
		AND mv.IsFallback='N' -- we don't need to raw-materials for end-products of fallback records, because they don't have a reserved qty, so they don't contribute to any raw material's Poor_Mans_MRP_Purchase_Qty
		AND v.IsPurchased='Y' -- only include raw-materials that are purchased, other wise there is no use showing it (because stuff is for the purchase department)
	) data
	LEFT JOIN X_MRP_ProductInfo_Detail_MV mv_ext -- anti-join existing mv records; better performance than with "where not exists"
		ON mv_ext.M_Product_ID=data.M_Product_ID 
				AND mv_ext.DateGeneral=data.DateGeneral
				AND mv_ext.ASIKey=data.ASIKey
WHERE true
		AND mv_ext.M_Product_ID IS NULL;
$BODY$
LANGUAGE sql STABLE;
COMMENT ON FUNCTION X_MRP_ProductInfo_Detail_Fallback_V(date) IS 'Supplemental function to X_MRP_ProductInfo_Detail_V that returns one "empty" row for 
*the date that is given as function parameter
*each product.
*each raw-material row that has an end-product row (with the ASIKey according to the BOMLine), using the view MRP_ProductInfo_Poor_Mans_MRP_V

"empty row" means: 
*numeric values being 0
*IsFallback=''Y''

Note that we want an empty row (with ASIKey=null) even if there is also a "non-empty" row (with ASIKey!=null, created by X_MRP_ProductInfo_Detail_V) for the given date and product.
Also note that we do not return X_MRP_ProductInfo_Detail_MV_ID. It will be set via default (from a sequence).
';
