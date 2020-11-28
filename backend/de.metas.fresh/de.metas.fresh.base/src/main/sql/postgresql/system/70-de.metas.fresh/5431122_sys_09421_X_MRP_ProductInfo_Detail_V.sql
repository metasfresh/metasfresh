
DROP FUNCTION IF EXISTS X_MRP_ProductInfo_Detail_Fallback_V(date, date);
CREATE OR REPLACE FUNCTION X_MRP_ProductInfo_Detail_Fallback_V(
		IN DateFrom date, 
		IN DateTo date) 
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
		,currentDate::date AS DateGeneral
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
		generate_series($1, $2, '1 day') AS currentDate
		LEFT JOIN M_Product p_fallback ON true
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
		JOIN "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V v ON mv.M_Product_ID=v.b_m_Product_ID
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
COMMENT ON FUNCTION X_MRP_ProductInfo_Detail_Fallback_V(date, date) 
IS 'Supplemental function to X_MRP_ProductInfo_Detail_V that returns one "empty" row for 
*each product and each date of a give range (with empty ASIKey).
*each raw-material row that has an end-product row (with the ASIKey according to the BOMLine), using the view MRP_ProductInfo_Poor_Mans_MRP_V

"empty row" means: 
*numeric values being 0
*IsFallback=''Y''

Note that we want an empty row (with ASIKey=null) even if there is also a "non-empty" row (with ASIKey!=null, created by X_MRP_ProductInfo_Detail_V) for the given date and product.
Also note that in certain endcustomer projects, we have a other versions of this, which do have additional specific columns. Such a more specific version will override this one.
';

DROP FUNCTION IF EXISTS X_MRP_ProductInfo_Detail_V(date, date);
CREATE OR REPLACE FUNCTION X_MRP_ProductInfo_Detail_V(IN DateFrom date, IN DateTo date) 
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
	p.M_Product_ID
	,currentDate::date AS DateGeneral
	,GenerateHUStorageASIKey(p.M_AttributeSetInstance_ID,'') as ASIKey
	
	,CEIL(SUM(COALESCE(ol_d.QtyReserved_Sale, 0))) AS QtyReserved_OnDate
	,CEIL(SUM(COALESCE(ol_d.QtyReserved_Purchase, 0))) AS QtyOrdered_OnDate
	,CEIL(SUM(COALESCE(ol_d.QtyOrdered_Sale, 0))) AS QtyOrdered_Sale_OnDate
	,CEIL(SUM(COALESCE(hu_me_d.qtymaterialentnahme, 0))) AS QtyMaterialentnahme  --T1-OK
	,CEIL(SUM(COALESCE(qoh_d.qty, 0)) + sum(COALESCE(qohl.QtyCountSum, 0)) - SUM(COALESCE(hu_me_d.qtymaterialentnahme, 0))) AS Fresh_qtyonhand_ondate
	,CEIL(SUM(COALESCE(qoh_d.qty, 0)) + SUM(COALESCE(qohl.QtyCountSum, 0)) - SUM(COALESCE(hu_me_d.qtymaterialentnahme, 0)) + SUM(COALESCE(ol_d.qtypromised, 0))) AS Fresh_qtypromised
	,'N'::character(1) as IsFallback
	,"de.metas.dimension".DIM_Get_GroupName('MRP_Product_Info_ASI_Values', GenerateHUStorageASIKey(p.M_AttributeSetInstance_ID)) AS GroupNames
FROM 
	generate_series($1, $2, '1 day') AS currentDate
--	generate_series((now() - interval '1 day')::date, (now() + interval '4 days')::date, '1 day') AS currentDate
	JOIN "de.metas.fresh".M_Product_ID_M_AttributeSetInstance_ID_V p ON p.DateGeneral::Date=currentDate::Date
	
	LEFT JOIN "de.metas.fresh".RV_C_OrderLine_QtyOrderedReservedPromised_OnDate_V ol_d ON ol_d.m_product_id = p.M_Product_ID AND ol_d.DateGeneral::date=currentDate::date 
		AND COALESCE(ol_d.M_AttributeSetInstance_ID,-1)=COALESCE(p.M_AttributeSetInstance_ID,-1)

	LEFT JOIN "de.metas.fresh".RV_HU_QtyMaterialentnahme_OnDate hu_me_d ON hu_me_d.m_product_id = p.M_Product_ID AND hu_me_d.updated_date::date = currentDate::date
		AND COALESCE(hu_me_d.M_AttributesetInstance_ID,-1)=COALESCE(p.M_AttributesetInstance_ID,-1)
		
	LEFT JOIN (
		-- task 09473: we might have mutiple records with the same date, product and ASI, so we need to aggregate.
		select movementdate::date as movementdate, m_product_id, M_AttributesetInstance_ID, sum(qty) AS qty 
		from "de.metas.fresh".X_Fresh_QtyOnHand_OnDate 
		group by movementdate::date, m_product_id, M_AttributesetInstance_ID
	) qoh_d ON qoh_d.movementdate = currentDate::date AND qoh_d.m_product_id = p.M_Product_ID
		AND COALESCE(qoh_d.M_AttributesetInstance_ID,-1)=COALESCE(p.M_AttributesetInstance_ID,-1)

	LEFT JOIN (	
		SELECT SUM(qohl.QtyCount) AS QtyCountSum, qoh.datedoc::date, qohl.M_Product_ID, qohl.M_AttributesetInstance_ID
		FROM Fresh_qtyonhand qoh
			LEFT JOIN Fresh_qtyonhand_line qohl ON qoh.Fresh_qtyonhand_id = qohl.Fresh_qtyonhand_id
		WHERE true
			AND qoh.Processed='Y'
			GROUP BY qoh.datedoc::date, qohl.M_Product_ID, qohl.M_AttributesetInstance_ID 
	) qohl ON qohl.datedoc = currentDate::date AND qohl.m_product_id = p.M_Product_ID AND COALESCE(qohl.M_AttributesetInstance_ID,-1)=COALESCE(p.M_AttributesetInstance_ID,-1)

	/*
	LEFT JOIN pp_mrp mrp ON mrp.isactive = 'Y'::bpchar 
   		AND mrp.typemrp = 'D'::bpchar 
   		AND mrp.ordertype::text = 'MOP'::text 
   		AND mrp.docstatus::text = 'CO'::text 
   		AND mrp.m_product_id = p.M_Product_ID
   		AND mrp.DateGeneral::Date = currentDate::date
		AND COALESCE(mrp.M_AttributesetInstance_ID,-1)=COALESCE(p.M_AttributesetInstance_ID,-1)
	*/
WHERE true
--	AND p.M_Product_ID=2000039 
--	AND dates.CurrentDate::Date='2015-03-24'::Date
  GROUP BY 
	p.M_Product_ID, ASIKey,	CurrentDate::date, 
	GroupNames -- groupnames is just required by postgres, but it doesn't make a difference: same ASIKey allways means same GroupNames
;
$BODY$
LANGUAGE sql STABLE;
COMMENT ON FUNCTION X_MRP_ProductInfo_Detail_V(date, date) IS 'creates tha basic info for the MRP info window, for a given date range.
The returned rows allways have IsFallback=''N'' to indicate that these rows are the "real" rows, with actual data.
';



--
-- there can be an overriding function in an endcustomer project
--
CREATE OR REPLACE FUNCTION X_MRP_ProductInfo_Detail_V(IN DateFrom date, IN DateTo date) 
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
	p.M_Product_ID
	,currentDate::date AS DateGeneral
	,GenerateHUStorageASIKey(p.M_AttributeSetInstance_ID,'') as ASIKey
	
	,CEIL(SUM(COALESCE(ol_d.QtyReserved_Sale, 0))) AS QtyReserved_OnDate
	,CEIL(SUM(COALESCE(ol_d.QtyReserved_Purchase, 0))) AS QtyOrdered_OnDate
	,CEIL(SUM(COALESCE(ol_d.QtyOrdered_Sale, 0))) AS QtyOrdered_Sale_OnDate
	,CEIL(SUM(COALESCE(hu_me_d.qtymaterialentnahme, 0))) AS QtyMaterialentnahme  --T1-OK
	,CEIL(SUM(COALESCE(qoh_d.qty, 0)) + sum(COALESCE(qohl.QtyCountSum, 0)) - SUM(COALESCE(hu_me_d.qtymaterialentnahme, 0))) AS Fresh_qtyonhand_ondate
	,CEIL(SUM(COALESCE(qoh_d.qty, 0)) + SUM(COALESCE(qohl.QtyCountSum, 0)) - SUM(COALESCE(hu_me_d.qtymaterialentnahme, 0)) + SUM(COALESCE(ol_d.qtypromised, 0))) AS Fresh_qtypromised
	,'N'::character(1) as IsFallback
	,"de.metas.dimension".DIM_Get_GroupName('MRP_Product_Info_ASI_Values', GenerateHUStorageASIKey(p.M_AttributeSetInstance_ID)) AS GroupNames
FROM 
	generate_series($1, $2, '1 day') AS currentDate
--	generate_series((now() - interval '1 day')::date, (now() + interval '4 days')::date, '1 day') AS currentDate
	JOIN "de.metas.fresh".M_Product_ID_M_AttributeSetInstance_ID_V p ON p.DateGeneral::Date=currentDate::Date
	
	LEFT JOIN "de.metas.fresh".RV_C_OrderLine_QtyOrderedReservedPromised_OnDate_V ol_d ON ol_d.m_product_id = p.M_Product_ID AND ol_d.DateGeneral::date=currentDate::date 
		AND COALESCE(ol_d.M_AttributeSetInstance_ID,-1)=COALESCE(p.M_AttributeSetInstance_ID,-1)

	LEFT JOIN "de.metas.fresh".RV_HU_QtyMaterialentnahme_OnDate hu_me_d ON hu_me_d.m_product_id = p.M_Product_ID AND hu_me_d.updated_date::date = currentDate::date
		AND COALESCE(hu_me_d.M_AttributesetInstance_ID,-1)=COALESCE(p.M_AttributesetInstance_ID,-1)
		
	LEFT JOIN (
		-- task 09473: we might have mutiple records with the same date, product and ASI, so we need to aggregate.
		select movementdate::date as movementdate, m_product_id, M_AttributesetInstance_ID, sum(qty) AS qty 
		from "de.metas.fresh".X_Fresh_QtyOnHand_OnDate 
		group by movementdate::date, m_product_id, M_AttributesetInstance_ID
	) qoh_d ON qoh_d.movementdate = currentDate::date AND qoh_d.m_product_id = p.M_Product_ID
		AND COALESCE(qoh_d.M_AttributesetInstance_ID,-1)=COALESCE(p.M_AttributesetInstance_ID,-1)

	LEFT JOIN (	
		SELECT SUM(qohl.QtyCount) AS QtyCountSum, qoh.datedoc::date, qohl.M_Product_ID, qohl.M_AttributesetInstance_ID
		FROM Fresh_qtyonhand qoh
			LEFT JOIN Fresh_qtyonhand_line qohl ON qoh.Fresh_qtyonhand_id = qohl.Fresh_qtyonhand_id
		WHERE true
			AND qoh.Processed='Y'
			GROUP BY qoh.datedoc::date, qohl.M_Product_ID, qohl.M_AttributesetInstance_ID 
	) qohl ON qohl.datedoc = currentDate::date AND qohl.m_product_id = p.M_Product_ID AND COALESCE(qohl.M_AttributesetInstance_ID,-1)=COALESCE(p.M_AttributesetInstance_ID,-1)

	/*
	LEFT JOIN pp_mrp mrp ON mrp.isactive = 'Y'::bpchar 
   		AND mrp.typemrp = 'D'::bpchar 
   		AND mrp.ordertype::text = 'MOP'::text 
   		AND mrp.docstatus::text = 'CO'::text 
   		AND mrp.m_product_id = p.M_Product_ID
   		AND mrp.DateGeneral::Date = currentDate::date
		AND COALESCE(mrp.M_AttributesetInstance_ID,-1)=COALESCE(p.M_AttributesetInstance_ID,-1)
	*/
WHERE true
--	AND p.M_Product_ID=2000039 
--	AND dates.CurrentDate::Date='2015-03-24'::Date
  GROUP BY 
	p.M_Product_ID, ASIKey,	CurrentDate::date, 
	GroupNames -- groupnames is just required by postgres, but it doesn't make a difference: same ASIKey allways means same GroupNames
;
$BODY$
LANGUAGE sql STABLE;
COMMENT ON FUNCTION X_MRP_ProductInfo_Detail_V(date, date) IS 'creates tha basic info for the MRP info window, for a given date range.
The returned rows allways have IsFallback=''N'' to indicate that these rows are the "real" rows, with actual data.
';
