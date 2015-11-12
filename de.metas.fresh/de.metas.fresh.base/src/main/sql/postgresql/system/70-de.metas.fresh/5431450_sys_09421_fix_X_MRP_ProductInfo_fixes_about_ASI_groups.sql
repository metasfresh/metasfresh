

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

DROP FUNCTION IF EXISTS X_MRP_ProductInfo_Detail_V(date, numeric, numeric);
DROP FUNCTION IF EXISTS X_MRP_ProductInfo_Detail_V(date, numeric);
CREATE OR REPLACE FUNCTION X_MRP_ProductInfo_Detail_V(
		IN DateFrom date, 
		IN M_Product_ID numeric) 
	RETURNS TABLE (
	  m_product_id numeric(10,0),
	  dategeneral date,
	  asikey text,
	  qtyreserved_ondate numeric,
	  qtyordered_ondate numeric,
	  qtyordered_sale_ondate numeric,
	  qtymaterialentnahme numeric,
	  fresh_qtyonhand_ondate numeric,
	  fresh_qtypromised numeric,
	  isfallback character(1),
	  groupnames character varying[]
	) AS
$BODY$
SELECT
	p.M_Product_ID as M_Product_ID
	,p.DateGeneral::date AS DateGeneral
	,GenerateHUStorageASIKey(p.M_AttributeSetInstance_ID,'') as ASIKey
	
	,CEIL(SUM(COALESCE(ol_d.QtyReserved_Sale, 0))) AS QtyReserved_OnDate
	,CEIL(SUM(COALESCE(ol_d.QtyReserved_Purchase, 0))) AS QtyOrdered_OnDate
	,CEIL(SUM(COALESCE(ol_d.QtyOrdered_Sale, 0))) AS QtyOrdered_Sale_OnDate
	,CEIL(SUM(COALESCE(hu_me_d.qtymaterialentnahme, 0))) AS QtyMaterialentnahme  --T1-OK
	,CEIL(SUM(COALESCE(qoh_d.qty, 0)) + sum(COALESCE(qohl.QtyCountSum, 0)) - SUM(COALESCE(hu_me_d.qtymaterialentnahme, 0))) AS fresh_qtyonhand_ondate
	,CEIL(SUM(COALESCE(qoh_d.qty, 0)) + SUM(COALESCE(qohl.QtyCountSum, 0)) - SUM(COALESCE(hu_me_d.qtymaterialentnahme, 0)) + SUM(COALESCE(ol_d.qtypromised, 0))) AS fresh_qtypromised
	,'N'::character(1) as IsFallback
	,"de.metas.dimension".DIM_Get_GroupName('MRP_Product_Info_ASI_Values', GenerateHUStorageASIKey(p.M_AttributeSetInstance_ID)) AS GroupNames
FROM 
	-- join all specific products and ASI-IDs that we have at 'DateGeneral'
	-- it's not enough to just join records with our current ASIKey, because there might be other records with differnt, but "overlapping" ASIs
    -- note: '&&' is the array "overlap" operator
	"de.metas.fresh".M_Product_ID_M_AttributeSetInstance_ID_V p

	LEFT JOIN "de.metas.fresh".RV_C_OrderLine_QtyOrderedReservedPromised_OnDate_V ol_d ON ol_d.m_product_id = p.M_Product_ID AND ol_d.DateGeneral::date=p.DateGeneral::date 
		AND COALESCE(ol_d.M_AttributeSetInstance_ID,-1)=COALESCE(p.M_AttributeSetInstance_ID,-1)

	LEFT JOIN "de.metas.fresh".RV_HU_QtyMaterialentnahme_OnDate hu_me_d ON hu_me_d.m_product_id = p.M_Product_ID AND hu_me_d.updated_date::date = p.DateGeneral::date
		AND COALESCE(hu_me_d.M_AttributesetInstance_ID,-1)=COALESCE(p.M_AttributesetInstance_ID,-1)

	LEFT JOIN (
		-- task 09473: we might have mutiple records with the same date, product and ASI, so we need to aggregate.
		select movementdate::date as movementdate, m_product_id, M_AttributesetInstance_ID, sum(qty) AS qty 
		from "de.metas.fresh".X_Fresh_QtyOnHand_OnDate 
		group by movementdate::date, m_product_id, M_AttributesetInstance_ID
	) qoh_d ON qoh_d.movementdate = p.DateGeneral::date AND qoh_d.m_product_id = p.M_Product_ID
		AND COALESCE(qoh_d.M_AttributesetInstance_ID,-1)=COALESCE(p.M_AttributesetInstance_ID,-1)

	LEFT JOIN (	
		SELECT SUM(qohl.QtyCount) AS QtyCountSum, qoh.datedoc::date, qohl.M_Product_ID, qohl.M_AttributesetInstance_ID
		FROM Fresh_qtyonhand qoh
			LEFT JOIN Fresh_qtyonhand_line qohl ON qoh.Fresh_qtyonhand_id = qohl.Fresh_qtyonhand_id
		WHERE true
			AND qoh.Processed='Y'
			GROUP BY qoh.datedoc::date, qohl.M_Product_ID, qohl.M_AttributesetInstance_ID 
	) qohl ON qohl.datedoc = p.DateGeneral::date AND qohl.m_product_id = p.M_Product_ID 
		AND COALESCE(qohl.M_AttributesetInstance_ID,-1)=COALESCE(p.M_AttributesetInstance_ID,-1)

	LEFT JOIN (	
		SELECT SUM(qohl.QtyCount) AS QtyCountSum, qoh.datedoc::date, qohl.M_Product_ID, qohl.M_AttributesetInstance_ID
		FROM Fresh_qtyonhand qoh
			LEFT JOIN Fresh_qtyonhand_line qohl ON qoh.Fresh_qtyonhand_id = qohl.Fresh_qtyonhand_id
		WHERE true
			AND qoh.Processed='Y'
			AND qohl.PP_Plant_ID=1000001
		GROUP BY qoh.datedoc::date, qohl.M_Product_ID, qohl.M_AttributesetInstance_ID 
	) qohl_s ON qohl_s.datedoc = p.DateGeneral::date AND qohl_s.m_product_id = p.M_Product_ID 
		AND COALESCE(qohl_s.M_AttributesetInstance_ID,-1)=COALESCE(p.M_AttributesetInstance_ID,-1)

	LEFT JOIN (	
		SELECT SUM(qohl.QtyCount) AS QtyCountSum, qoh.datedoc::date, qohl.M_Product_ID, qohl.M_AttributesetInstance_ID
		FROM Fresh_qtyonhand qoh
			LEFT JOIN Fresh_qtyonhand_line qohl ON qoh.Fresh_qtyonhand_id = qohl.Fresh_qtyonhand_id
		WHERE true
			AND qoh.Processed='Y'
			AND qohl.PP_Plant_ID=1000002
		GROUP BY qoh.datedoc::date, qohl.M_Product_ID, qohl.M_AttributesetInstance_ID 
	) qohl_i ON qohl_i.datedoc = p.DateGeneral::date AND qohl_i.m_product_id = p.M_Product_ID 
		AND COALESCE(qohl_i.M_AttributesetInstance_ID,-1)=COALESCE(p.M_AttributesetInstance_ID,-1)

WHERE true
	AND p.DateGeneral::Date=$1::Date 
	AND p.M_Product_ID=$2
--	AND p.M_Product_ID=2000039 
--	AND p.DateGeneral::Date='2015-03-24'::Date
  GROUP BY 
	p.M_Product_ID, ASIKey,	p.DateGeneral::date, 
	GroupNames -- groupnames is just required by postgres, but it doesn't make a difference: same ASIKey allways means same GroupNames
;
$BODY$
LANGUAGE sql STABLE;
COMMENT ON FUNCTION X_MRP_ProductInfo_Detail_V(date, numeric) IS 'Creates tha basic info for the MRP info window, for a given date and M_Product_ID.
The returned rows always have IsFallback=''N'' to indicate that these rows are the "real" rows, with actual data.
';


CREATE OR REPLACE FUNCTION X_MRP_ProductInfo_Detail_MV_Refresh(IN DateFrom date, 
	IN M_Product_ID numeric, 
	IN M_AttributesetInstance_ID numeric)
  RETURNS void AS
$BODY$
	
    -- Update "de.metas.fresh".x_mrp_productinfo_detail_poor_mans_mrp. 
    -- It will be joined to X_MRP_ProductInfo_Detail_MV
    SELECT "de.metas.fresh".X_MRP_ProductInfo_Detail_Update_Poor_Mans_MRP($1, $2, $3);

    -- Delete existing records from X_MRP_ProductInfo_Detail_MV. 
    -- They will be recreated if there is anything to be recreated
    -- we don't do any ASI related filtering, because there might be an inout line with an ASI that is totally different from the order line,
    -- but still we need to change both the the X_MRP_ProductInfo_Detail_MV records for the inout and the order line
    DELETE FROM X_MRP_ProductInfo_Detail_MV 
    WHERE DateGeneral::Date = $1 AND M_Product_ID = $2 
    ;

	-- Insert new rows.
    INSERT INTO X_MRP_ProductInfo_Detail_MV (
    	m_product_id,
		dategeneral,
		asikey,
		qtyreserved_ondate,
		qtyordered_ondate,
		qtyordered_sale_ondate,
		qtymaterialentnahme,
		fresh_qtyonhand_ondate,
		fresh_qtypromised,
		isfallback,
		groupnames)
    SELECT 
    	m_product_id,
		dategeneral,
		asikey,
		qtyreserved_ondate,
		qtyordered_ondate,
		qtyordered_sale_ondate,
		qtymaterialentnahme,
		fresh_qtyonhand_ondate,
		fresh_qtypromised,
		isfallback,
		groupnames
    FROM X_MRP_ProductInfo_Detail_V($1, $2);

$BODY$
  LANGUAGE sql VOLATILE
  COST 100;
COMMENT ON FUNCTION X_MRP_ProductInfo_Detail_MV_Refresh(date, numeric, numeric) IS 'tasks 08681 and 08682: refreshes the table X_MRP_ProductInfo_Detail_MV.
1. deleting existing records
2. calling X_MRP_ProductInfo_Detail_MV so add new records
3. updating fresh_qtymrp using the view "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V
';


----------------------


DROP FUNCTION IF EXISTS X_MRP_ProductInfo_Detail_Fallback_V(date, date);
DROP FUNCTION IF EXISTS X_MRP_ProductInfo_Detail_Fallback_V(date);
CREATE OR REPLACE FUNCTION X_MRP_ProductInfo_Detail_Fallback_V(IN DateFrom date) 
	RETURNS TABLE (
	  m_product_id numeric(10,0),
	  dategeneral date,
	  asikey text,
	  qtyreserved_ondate numeric,
	  qtyordered_ondate numeric,
	  qtyordered_sale_ondate numeric,
	  qtymaterialentnahme numeric,
	  fresh_qtyonhand_ondate numeric,
	  fresh_qtypromised numeric,
	  isfallback character(1),
	  groupnames character varying[]
	) AS
$BODY$
SELECT data.* 
FROM
	(
	SELECT
		p_fallback.M_Product_ID
		,$1 AS DateGeneral
		,'' AS ASIKey
		,0::numeric AS QtyReserved_OnDate
		,0::numeric AS QtyOrdered_OnDate
		,0::numeric AS QtyOrdered_Sale_OnDate
		,0::numeric AS QtyMaterialentnahme 
		,0::numeric AS fresh_qtyonhand_ondate
		,0::numeric AS fresh_qtypromised
		,'Y'::character(1) as IsFallback
		,"de.metas.dimension".DIM_Get_GroupName('MRP_Product_Info_ASI_Values', null) AS GroupNames
	FROM
		M_Product p_fallback
	UNION
	-- Insert empty records for those raw materials (AND their ASI) that are not yet included, but that have an end-product which is already included.
	-- This will allow us to update them later on
	SELECT 
		v.bl_M_Product_ID as M_Product_ID
		,mv.DateGeneral 
		,GenerateHUStorageASIKey(v.bl_M_AttributeSetInstance_ID, '') as ASIKey
		,0::numeric
		,0::numeric
		,0::numeric
		,0::numeric
		,0::numeric
		,0::numeric
		,'Y'::character(1) as IsFallback
		,"de.metas.dimension".DIM_Get_GroupName('MRP_Product_Info_ASI_Values', GenerateHUStorageASIKey(v.bl_M_AttributeSetInstance_ID)) AS GroupNames
	FROM X_mrp_productinfo_detail_mv mv
		-- join the existing end-products from X_mrp_productinfo_detail_mv to their raw materials and add records for those raw materials
		JOIN "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V v 
			ON mv.M_Product_ID=v.b_m_Product_ID
				AND mv.ASIKey= GenerateHUStorageASIKey(v.b_M_AttributeSetInstance_ID,'')
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
*each product.
*each raw-material row that has an end-product row (with the ASIKey according to the BOMLine), using the view MRP_ProductInfo_Poor_Mans_MRP_V

"empty row" means: 
*numeric values being 0
*IsFallback=''Y''

Note that we want an empty row (with ASIKey=null) even if there is also a "non-empty" row (with ASIKey!=null, created by X_MRP_ProductInfo_Detail_V) for the given date and product.
';

DROP FUNCTION IF EXISTS X_MRP_ProductInfo_Detail_Insert_Fallback(date);
CREATE OR REPLACE FUNCTION X_MRP_ProductInfo_Detail_Insert_Fallback(IN DateOn date) 
	RETURNS VOID 
  AS
$BODY$

INSERT INTO X_MRP_ProductInfo_Detail_MV (
	M_Product_ID
	,DateGeneral
	,ASIKey
	,QtyReserved_OnDate
	,QtyOrdered_OnDate
	,QtyOrdered_Sale_OnDate
	,QtyMaterialentnahme 
	,fresh_qtyonhand_ondate
	,fresh_qtypromised
	,IsFallback
	,GroupNames
)
SELECT 
	M_Product_ID
	,DateGeneral
	,ASIKey
	,QtyReserved_OnDate
	,QtyOrdered_OnDate
	,QtyOrdered_Sale_OnDate
	,QtyMaterialentnahme 
	,fresh_qtyonhand_ondate
	,fresh_qtypromised
	,IsFallback
	,GroupNames
FROM X_MRP_ProductInfo_Detail_Fallback_V($1);

$BODY$
LANGUAGE sql VOLATILE;
COMMENT ON FUNCTION X_MRP_ProductInfo_Detail_Fallback_V(date) 
IS 'Calls X_MRP_ProductInfo_Detail_V(date) and directly inserts the result into X_MRP_ProductInfo_Detail_MV for a given day.';
