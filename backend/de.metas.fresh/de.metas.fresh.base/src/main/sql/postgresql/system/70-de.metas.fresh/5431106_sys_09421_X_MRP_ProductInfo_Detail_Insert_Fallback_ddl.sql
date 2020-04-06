
DROP FUNCTION IF EXISTS X_MRP_ProductInfo_Detail_Insert_Fallback(date);
CREATE OR REPLACE FUNCTION X_MRP_ProductInfo_Detail_Insert_Fallback(IN DateOn date) 
	RETURNS VOID 
  AS
$BODY$

INSERT INTO X_MRP_ProductInfo_Detail_MV
SELECT * 
FROM X_MRP_ProductInfo_Detail_Fallback_V($1, $1);

$BODY$
LANGUAGE sql STABLE;
COMMENT ON FUNCTION X_MRP_ProductInfo_Detail_Fallback_V(date, date) 
IS 'Calls X_MRP_ProductInfo_Detail_V(date,date) and directly inserts the result into X_MRP_ProductInfo_Detail_MV for a given day.';
