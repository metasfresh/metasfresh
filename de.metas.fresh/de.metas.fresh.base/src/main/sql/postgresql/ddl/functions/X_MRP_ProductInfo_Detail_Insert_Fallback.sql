
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
