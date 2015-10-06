

CREATE OR REPLACE FUNCTION X_MRP_ProductInfo_Detail_V(IN DateFrom date, IN DateTo date) RETURNS SETOF X_mrp_productinfo_detail_mv AS
$BODY$
SELECT
	p.M_Product_ID
	,currentDate::date AS DateGeneral
	,GenerateHUStorageASIKey(p.M_AttributeSetInstance_ID,'') as ASIKey
	
	,CEIL(SUM(COALESCE(ol_d.QtyReserved_Sale, 0))) AS QtyReserved_OnDate
	,CEIL(SUM(COALESCE(ol_d.QtyReserved_Purchase, 0))) AS QtyOrdered_OnDate
	,CEIL(SUM(COALESCE(ol_d.QtyOrdered_Sale, 0))) AS QtyOrdered_Sale_OnDate
	,CEIL(SUM(COALESCE(hu_me_d.qtymaterialentnahme, 0))) AS QtyMaterialentnahme  --T1-OK
	,CEIL(SUM(COALESCE(qoh_d.qty, 0)) + sum(COALESCE(qohl.QtyCountSum, 0)) - SUM(COALESCE(hu_me_d.qtymaterialentnahme, 0))) AS fresh_qtyonhand_ondate
	,CEIL(SUM(COALESCE(qohl_s.QtyCountSum, 0))) AS "fresh_qtyonhand_ondate_stö2" --T1-OK
	,CEIL(SUM(COALESCE(qohl_i.QtyCountSum, 0))) AS fresh_qtyonhand_ondate_ind9   --T1-OK
	,CEIL(SUM(COALESCE(qoh_d.qty, 0)) + SUM(COALESCE(qohl.QtyCountSum, 0)) - SUM(COALESCE(hu_me_d.qtymaterialentnahme, 0)) + SUM(COALESCE(ol_d.qtypromised, 0))) AS fresh_qtypromised
	,0::numeric AS fresh_qtymrp --fresh_qtymrp will be updated with the help of "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V mrp viewe in the next step
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
		
	LEFT JOIN "de.metas.fresh".X_fresh_QtyOnHand_OnDate qoh_d ON qoh_d.movementdate::date = currentDate::date AND qoh_d.m_product_id = p.M_Product_ID
		AND COALESCE(qoh_d.M_AttributesetInstance_ID,-1)=COALESCE(p.M_AttributesetInstance_ID,-1)

--	IMPORTANT: I thought that using these (stable) functions was equivalent to running the left-joined subselects, but that was *not* the case. The perf of the function calls is much worse
--	LEFT JOIN "de.metas.fresh".fresh_QtyOnHand_Summary_V(null) qohl ON qohl.dateDoc=currentDate::date AND qohl.m_product_id = p.M_Product_ID AND COALESCE(qohl.M_AttributesetInstance_ID,-1)=COALESCE(p.M_AttributesetInstance_ID,-1)
--	LEFT JOIN "de.metas.fresh".fresh_QtyOnHand_Summary_V(1000001) qohl_s ON qohl.dateDoc=currentDate::date AND qohl.m_product_id = p.M_Product_ID AND COALESCE(qohl.M_AttributesetInstance_ID,-1)=COALESCE(p.M_AttributesetInstance_ID,-1)
--	LEFT JOIN "de.metas.fresh".fresh_QtyOnHand_Summary_V(1000002) qohl_i ON qohl.dateDoc=currentDate::date AND qohl.m_product_id = p.M_Product_ID AND COALESCE(qohl.M_AttributesetInstance_ID,-1)=COALESCE(p.M_AttributesetInstance_ID,-1)
	LEFT JOIN (	
		SELECT SUM(qohl.QtyCount) AS QtyCountSum, qoh.datedoc::date, qohl.M_Product_ID, qohl.M_AttributesetInstance_ID
		FROM fresh_qtyonhand qoh
			LEFT JOIN fresh_qtyonhand_line qohl ON qoh.fresh_qtyonhand_id = qohl.fresh_qtyonhand_id
		WHERE true
			AND qoh.Processed='Y'
			GROUP BY qoh.datedoc::date, qohl.M_Product_ID, qohl.M_AttributesetInstance_ID 
	) qohl ON qohl.datedoc = currentDate::date AND qohl.m_product_id = p.M_Product_ID AND COALESCE(qohl.M_AttributesetInstance_ID,-1)=COALESCE(p.M_AttributesetInstance_ID,-1)

	LEFT JOIN (	
		SELECT SUM(qohl.QtyCount) AS QtyCountSum, qoh.datedoc::date, qohl.M_Product_ID, qohl.M_AttributesetInstance_ID
		FROM fresh_qtyonhand qoh
			LEFT JOIN fresh_qtyonhand_line qohl ON qoh.fresh_qtyonhand_id = qohl.fresh_qtyonhand_id
		WHERE true
			AND qoh.Processed='Y'
			AND qohl.PP_Plant_ID=1000001
		GROUP BY qoh.datedoc::date, qohl.M_Product_ID, qohl.M_AttributesetInstance_ID 
	) qohl_s ON qohl_s.datedoc = currentDate::date AND qohl_s.m_product_id = p.M_Product_ID AND COALESCE(qohl_s.M_AttributesetInstance_ID,-1)=COALESCE(p.M_AttributesetInstance_ID,-1)

	LEFT JOIN (	
		SELECT SUM(qohl.QtyCount) AS QtyCountSum, qoh.datedoc::date, qohl.M_Product_ID, qohl.M_AttributesetInstance_ID
		FROM fresh_qtyonhand qoh
			LEFT JOIN fresh_qtyonhand_line qohl ON qoh.fresh_qtyonhand_id = qohl.fresh_qtyonhand_id
		WHERE true
			AND qoh.Processed='Y'
			AND qohl.PP_Plant_ID=1000002
		GROUP BY qoh.datedoc::date, qohl.M_Product_ID, qohl.M_AttributesetInstance_ID 
	) qohl_i ON qohl_i.datedoc = currentDate::date AND qohl_i.m_product_id = p.M_Product_ID AND COALESCE(qohl_i.M_AttributesetInstance_ID,-1)=COALESCE(p.M_AttributesetInstance_ID,-1)
		
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
