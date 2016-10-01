--
-- stuff done in this file might be redone/overridden by 5451209_sys_gh213_MRP_ProductInfo_extend_views_and_functions.sql in an endcustomer project.
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
	SUM(v.QtyOnHand) AS QtyOnHand, -- gh #213
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

DROP FUNCTION IF EXISTS x_mrp_productinfo_attributeval_v(date, numeric);
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
  LANGUAGE sql STABLE;
COMMENT ON FUNCTION x_mrp_productinfo_attributeval_v(date, numeric) IS 'This function is a union of X_MRP_ProductInfo_AttributeVal_V_Raw and the dimension spec''s attribute values that do *not* have matching X_MRP_ProductInfo_Detail_MV records for the given date';


DROP VIEW IF EXISTS X_MRP_ProductInfo_V;
CREATE OR REPLACE VIEW X_MRP_ProductInfo_V AS
SELECT 
	p.ad_client_id, p.ad_org_id, p.m_product_id, p.name AS ProductName, 
	p.value, p.ispurchased, p.issold, p.m_product_category_id, p.isactive,
	v.DateGeneral,
	SUM(v.QtyOnHand) AS QtyOnHand, -- gh #213
	SUM(v.PMM_QtyPromised_OnDate) AS PMM_QtyPromised_OnDate, -- FRESH-86
	SUM(v.qtyreserved_ondate) AS qtyreserved_ondate, 
	SUM(v.qtyordered_ondate) AS qtyordered_ondate, 
	SUM(v.qtymaterialentnahme) AS qtymaterialentnahme, 
	SUM(v.fresh_qtyonhand_ondate) AS fresh_qtyonhand_ondate, 
	SUM(v.fresh_qtypromised) AS fresh_qtypromised, 
	(
		SELECT SUM(Fresh_QtyMRP)
		FROM "de.metas.fresh".X_MRP_ProductInfo_Detail_Poor_Mans_MRP mrp
		WHERE mrp.DateGeneral = v.DateGeneral AND mrp.M_Product_MRP_ID = p.M_Product_ID
		GROUP BY DateGeneral, M_Product_MRP_ID
	) AS Fresh_QtyMRP
FROM
	M_Product p
		JOIN X_MRP_ProductInfo_Detail_MV v ON v.M_Product_ID=p.M_Product_ID
GROUP BY
	p.ad_client_id, p.ad_org_id, p.m_product_id, p.name, 
	p.value, p.ispurchased, p.issold, p.m_product_category_id, p.isactive,
	v.DateGeneral;
COMMENT On VIEW X_MRP_ProductInfo_V IS 
'This is the view the is displayed in the main table of the MRP product info window. It heavily depends on X_MRP_ProductInfo_Detail_MV';


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
	,COALESCE(MAX(p.M_AttributesetInstance_ID),-1) AS M_AttributesetInstance_ID -- gh #213: we need one prototypical M_AttributesetInstance_ID so that later on we can constructs a storage query when computing QtyOnHand in the java code
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


CREATE OR REPLACE FUNCTION "de.metas.fresh".X_MRP_ProductInfo_Detail_MV_Refresh(
	IN DateAt date, 
	IN M_Product_ID numeric, 
	IN M_AttributesetInstance_ID numeric)
  RETURNS void AS
$BODY$

    -- Delete existing records from X_MRP_ProductInfo_Detail_MV. 
    -- They will be recreated if there is anything to be recreated
    -- we don't do any ASI related filtering, because there might be an inout line with an ASI that is totally different from the order line,
    -- but still we need to change both the the X_MRP_ProductInfo_Detail_MV records for the inout and the order line
    DELETE FROM X_MRP_ProductInfo_Detail_MV 
    WHERE DateGeneral::Date = $1 AND M_Product_ID = $2
		AND IsFallBack='N'; -- don''t delete fallback records. They are neutral and don't interfere with non-fallback records. However, if e.g. a date changes, the new non-fallback record will have a different date, so we need a fallback record at the "old" date

	-- Insert new rows.
    INSERT INTO X_MRP_ProductInfo_Detail_MV (
		AD_Client_ID,
		AD_Org_ID,
		Created,
		CreatedBy,
		Updated,
		UpdatedBy,
		IsActive,
    	m_product_id,
		dategeneral,
		asikey,
		M_AttributesetInstance_ID, -- gh #213: we need one prototypical M_AttributesetInstance_ID so that later on we can constructs a storage query when computing QtyOnHand in the java code
		PMM_QtyPromised_OnDate, -- FRESH-86
		qtyreserved_ondate,
		qtyordered_ondate,
		qtyordered_sale_ondate,
		qtymaterialentnahme,
		fresh_qtyonhand_ondate,
		fresh_qtypromised,
		isfallback,
		groupnames)
    SELECT
		AD_Client_ID,
		AD_Org_ID,
		now() AS Created,
		99 AS CreatedBy,
		now() AS Updated,
		99 AS UpdatedBy,
		'Y' AS IsActive,
    	m_product_id,
		dategeneral,
		asikey,
		M_AttributesetInstance_ID,
		PMM_QtyPromised_OnDate, -- FRESH-86: Qty from PMM_PurchaseCandidate
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

COMMENT ON FUNCTION "de.metas.fresh".X_MRP_ProductInfo_Detail_MV_Refresh(date, numeric, numeric) IS 'tasks 08681 and 08682: refreshes the table X_MRP_ProductInfo_Detail_MV.
1. deleting existing records
2. calling X_MRP_ProductInfo_Detail_MV so add new records
';


DROP FUNCTION IF EXISTS X_MRP_ProductInfo_Detail_Fallback_V(date);
CREATE OR REPLACE FUNCTION X_MRP_ProductInfo_Detail_Fallback_V(IN DateFrom date) RETURNS 
TABLE (
  M_Product_ID numeric(10,0),
  DateGeneral date,
  ASIKey character varying(2000),
  M_AttributesetInstance_ID numeric,
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
		,0 AS M_AttributesetInstance_ID
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



DROP FUNCTION IF EXISTS X_MRP_ProductInfo_Detail_Insert_Fallback(date);
CREATE OR REPLACE FUNCTION X_MRP_ProductInfo_Detail_Insert_Fallback(IN DateOn date) 
	RETURNS VOID 
  AS
$BODY$

INSERT INTO X_MRP_ProductInfo_Detail_MV (
	AD_Client_ID
	,AD_Org_ID
	,Created
	,CreatedBy
	,IsActive
	,Updated
	,UpdatedBy
	,M_Product_ID
	,DateGeneral
	,ASIKey
	,M_AttributesetInstance_ID
	,QtyReserved_OnDate
	,QtyOrdered_OnDate
	,QtyOrdered_Sale_OnDate
	,QtyMaterialentnahme 
	,fresh_qtyonhand_ondate
	,fresh_qtypromised
	,QtyOnHand  -- gh #213
	,IsFallback
	,GroupNames
)
SELECT 
	AD_Client_ID
	,AD_Org_ID
	,Created
	,CreatedBy
	,IsActive
	,Updated
	,UpdatedBy
	,M_Product_ID
	,DateGeneral
	,ASIKey
	,M_AttributesetInstance_ID
	,QtyReserved_OnDate
	,QtyOrdered_OnDate
	,QtyOrdered_Sale_OnDate
	,QtyMaterialentnahme 
	,fresh_qtyonhand_ondate
	,fresh_qtypromised
	,QtyOnHand  -- gh #213	
	,IsFallback
	,GroupNames
FROM X_MRP_ProductInfo_Detail_Fallback_V($1);

$BODY$
LANGUAGE sql VOLATILE;
COMMENT ON FUNCTION X_MRP_ProductInfo_Detail_Insert_Fallback(date) 
IS 'Calls X_MRP_ProductInfo_Detail_V(date) and directly inserts the result into X_MRP_ProductInfo_Detail_MV for a given day.';
