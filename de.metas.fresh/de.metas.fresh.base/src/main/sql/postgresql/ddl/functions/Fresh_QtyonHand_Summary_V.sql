
DROP FUNCTION IF EXISTS "de.metas.fresh".fresh_QtyOnHand_Summary_V(numeric);
CREATE OR REPLACE FUNCTION "de.metas.fresh".fresh_QtyOnHand_Summary_V(PP_Plant_ID numeric) RETURNS TABLE(
	QtyCountSum numeric, 
	DateDoc date,
	M_Product_ID numeric,
	M_AttributesetInstance_ID numeric)
AS
$BODY$
SELECT SUM(qohl.QtyCount) AS QtyCountSum, qoh.datedoc::date, qohl.M_Product_ID, qohl.M_AttributesetInstance_ID
FROM fresh_qtyonhand qoh
	LEFT JOIN fresh_qtyonhand_line qohl ON qoh.fresh_qtyonhand_id = qohl.fresh_qtyonhand_id
WHERE true
	AND qoh.Processed='Y'
	AND qohl.PP_Plant_ID=COALESCE($1, qohl.PP_Plant_ID)
GROUP BY qoh.datedoc::date, qohl.M_Product_ID, qohl.M_AttributesetInstance_ID
;
$BODY$
LANGUAGE sql STABLE;
COMMENT ON FUNCTION "de.metas.fresh".fresh_QtyOnHand_Summary_V(numeric) IS 'Returns the fresh_QtyOnHand_Line.QtyCount sums, grouped by M_Product_ID, M_AttributesetInstance_ID and DateDoc, optionally filtered by PP_Plant_ID.
USE WITH CAUTION: I left this function here because it might have its uses, but note that it actually turned out to be too unperformant to be left-joined into X_MRP_ProductInfo_Detail_V.'
;