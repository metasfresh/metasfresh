CREATE OR REPLACE FUNCTION X_MRP_ProductInfo_Detail_MV_Refresh(IN DateFrom date, 
	IN M_Product_ID numeric, 
	IN M_AttributesetInstance_ID numeric)
  RETURNS void AS
$BODY$

    DELETE FROM X_MRP_ProductInfo_Detail_MV 
    WHERE DateGeneral::Date = $1 AND M_Product_ID = $2 AND AsiKey=GenerateHUStorageASIKey($3,'');

	-- do the "actual" refreshing
    INSERT INTO X_MRP_ProductInfo_Detail_MV
    SELECT * FROM X_MRP_ProductInfo_Detail_V($1, $2, $3);

    SELECT "de.metas.fresh".X_MRP_ProductInfo_Detail_Update_Poor_Mans_MRP($1, $2, $3);

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
