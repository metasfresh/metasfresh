
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
ALTER FUNCTION X_MRP_ProductInfo_Detail_MV_Refresh()
  OWNER TO adempiere;
COMMENT ON FUNCTION X_MRP_ProductInfo_Detail_MV_Refresh(date, numeric, numeric) IS 'tasks 08681 and 08682: refreshes the table X_MRP_ProductInfo_Detail_MV.
1. deleting existing records
2. calling X_MRP_ProductInfo_Detail_MV so add new records
3. updating fresh_qtymrp using the view "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V
';
