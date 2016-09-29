
--
-- there can be an overriding function in an endcustomer project
--
DROP FUNCTION IF EXISTS X_MRP_ProductInfo_Detail_V(date, numeric);
CREATE OR REPLACE FUNCTION X_MRP_ProductInfo_Detail_V(
		IN DateFrom date, 
		IN M_Product_ID numeric) 
	RETURNS TABLE (
	  AD_Client_ID numeric(10,0),
	  AD_Org_ID numeric(10,0),
	  m_product_id numeric(10,0),
	  dategeneral date,
	  asikey text,
	  M_AttributesetInstance_ID numeric(10,0),
	  PMM_QtyPromised_OnDate numeric, -- FRESH-86
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
	prod.AD_Client_ID
	,prod.AD_Org_ID
	,p.M_Product_ID as M_Product_ID
	,p.DateGeneral::date AS DateGeneral
	,GenerateHUStorageASIKey(p.M_AttributeSetInstance_ID,'') as ASIKey
	,COALESCE(MAX(p.M_AttributesetInstance_ID),-1) AS M_AttributesetInstance_ID -- gh #213: we need one prototypical M_AttributesetInstance_ID so that late on we can constructs a storage query when computing QtyOnHand in the java code
	,CEIL(SUM(COALESCE(qp.PMM_QtyPromised_OnDate, 0))) AS PMM_QtyPromised_OnDate -- FRESH-86
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
		LEFT JOIN M_Product prod ON prod.M_Product_ID=p.M_Product_ID

	LEFT JOIN "de.metas.fresh".RV_C_OrderLine_QtyOrderedReservedPromised_OnDate_V ol_d ON ol_d.m_product_id = p.M_Product_ID AND ol_d.DateGeneral::date=p.DateGeneral::date 
		AND COALESCE(ol_d.M_AttributeSetInstance_ID,-1)=COALESCE(p.M_AttributeSetInstance_ID,-1)

	LEFT JOIN "de.metas.fresh".RV_HU_QtyMaterialentnahme_OnDate hu_me_d ON hu_me_d.m_product_id = p.M_Product_ID AND hu_me_d.updated_date::date = p.DateGeneral::date
		AND COALESCE(hu_me_d.M_AttributesetInstance_ID,-1)=COALESCE(p.M_AttributesetInstance_ID,-1)

	LEFT JOIN (
		-- task 09473: we might have mutiple records with the same date, product and ASI, so we need to aggregate.
		select movementdate::date as movementdate, m_product_id, M_AttributesetInstance_ID, sum(qty) AS qty 
		from "de.metas.fresh".X_Fresh_QtyOnHand_OnDate -- note that this is a table, not a view. it's maintained by the trigger-function "de.metas.fresh".M_Transaction_update_X_Fresh_QtyOnHand_OnDate()
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

	-- FRESH-86
	-- qp = "quantity promised"
	LEFT JOIN (
		SELECT SUM(pc.QtyPromised) AS PMM_QtyPromised_OnDate, pc.DatePromised::Date, pc.M_Product_ID, pc.M_AttributesetInstance_ID
		FROM pmm_purchasecandidate pc
		GROUP BY pc.DatePromised::date, pc.M_Product_ID, pc.M_AttributesetInstance_ID 
	) qp ON qp.DatePromised = p.DateGeneral::date AND qp.M_Product_ID = p.M_Product_ID 
		 AND COALESCE(qp.M_AttributesetInstance_ID,-1)=COALESCE(p.M_AttributesetInstance_ID,-1)
	
WHERE true
	AND p.DateGeneral::Date=$1::Date 
	AND p.M_Product_ID=$2
--	AND p.M_Product_ID=2000039 
--	AND p.DateGeneral::Date='2015-03-24'::Date
  GROUP BY 
	prod.AD_Client_ID, prod.AD_Org_ID,
	p.M_Product_ID, ASIKey,	p.DateGeneral::date, 
	GroupNames -- groupnames is just required by postgres, but it doesn't make a difference: same ASIKey allways means same GroupNames
;
$BODY$
LANGUAGE sql STABLE;
COMMENT ON FUNCTION x_mrp_productinfo_detail_v(date, numeric) IS 'Creates tha basic info for the MRP info window, for a given date, M_Product_ID and M_AttributesetInstance_ID.
The returned rows always have IsFallback=''N'' to indicate that these rows are the "real" rows, with actual data.
Note about https://github.com/metasfresh/metasfresh/issues/213 that the function does do not return X_MRP_ProductInfo_Detail_MV_ID and QtyOnHand. The former is set via default (from a sequence) and the latter is set via java-code.
';
