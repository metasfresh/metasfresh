
DROP VIEW IF EXISTS "de.metas.fresh".M_Product_ID_M_AttributeSetInstance_ID_V;
CREATE OR REPLACE VIEW "de.metas.fresh".M_Product_ID_M_AttributeSetInstance_ID_V AS
SELECT M_Product_ID, M_AttributeSetInstance_ID, DateGeneral
FROM (
	SELECT t.M_Product_ID, t.M_AttributeSetInstance_ID, t.MovementDate::date as DateGeneral, 'M_Transaction' as source
	FROM M_Transaction t
	UNION
	SELECT ol.M_Product_ID, ol.M_AttributeSetInstance_ID, 
		COALESCE(
			sched.PreparationDate_Override, sched.PreparationDate, -- task 08931: the user works with PreparationDate instead of DatePromised 
			o.PreparationDate, -- fallback in case the schedule does not exist yet
			o.DatePromised -- fallback in case of orders created by the system (e.g. EDI) and missing proper tour planning master data 
		)::Date AS DateGeneral,
		'C_OrderLine' 
	FROM C_Order o
		JOIN C_OrderLine ol ON ol.C_Order_ID=o.C_Order_ID
		LEFT JOIN M_ShipmentSchedule sched ON sched.C_OrderLine_ID=ol.C_OrderLine_ID AND sched.IsActive='Y'
	WHERE o.DocStatus IN ('CO', 'CL')
	UNION
	SELECT qohl.M_Product_ID, qohl.M_AttributeSetInstance_ID, qoh.DateDoc::date, 'Fresh_QtyOnHand_Line'
	FROM Fresh_QtyOnHand qoh
		JOIN Fresh_QtyOnHand_Line qohl ON qoh.fresh_qtyonhand_id = qohl.Fresh_qtyonhand_id
	WHERE qoh.Processed='Y' AND qoh.IsActive='Y' AND qohl.IsActive='Y'
	UNION -- FRESH-86
	SELECT pc.M_Product_ID, pc.M_AttributeSetInstance_ID, pc.DatePromised::Date
	FROM PMM_PurchaseCandidate pc
	WHERE pc.IsActive='Y'
) data
WHERE true
	AND M_Product_ID IS NOT NULL
--	AND M_Product_ID=(select M_Product_ID from M_Product where Value='P000037')
--	AND DateGeneral::date='2015-04-09'::date
GROUP BY M_Product_ID, M_AttributeSetInstance_ID, DateGeneral;

COMMENT ON VIEW "de.metas.fresh".M_Product_ID_M_AttributeSetInstance_ID_V IS 'Used in X_MRP_ProductInfo_Detail_V to enumerate all the products and ASIs for which we need numbers.
Note: i tried changing this view into an SQL function with dateFrom and dateTo as where-clause paramters, but it didn''t bring any gain in X_MRP_ProductInfo_Detail_V.'
;