
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
