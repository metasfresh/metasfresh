DROP VIEW IF EXISTS "de.metas.fresh".RV_C_OrderLine_QtyOrderedReservedPromised_OnDate_V;
CREATE OR REPLACE VIEW "de.metas.fresh".RV_C_OrderLine_QtyOrderedReservedPromised_OnDate_V AS
SELECT 
	M_Product_ID, 
	DateGeneral::Date, 
	M_AttributeSetInstance_ID,
	SUM(QtyReserved_Sale) AS QtyReserved_Sale,
	SUM(QtyReserved_Purchase) AS QtyReserved_Purchase,
	SUM(QtyReserved_Purchase) - SUM(QtyReserved_Sale) as QtyPromised,
	SUM(QtyOrdered_Sale) AS QtyOrdered_Sale
FROM
(
SELECT
	COALESCE(
		sched.PreparationDate_Override, sched.PreparationDate, -- task 08931: the user needs to plan with PreparationDate instead of DatePromised 
		o.PreparationDate, -- fallback in case the schedule does not exist yet
		o.DatePromised -- fallback in case of orders created by the system (e.g. EDI) and missing proper tour planning master data 
	) AS DateGeneral,
	ol.M_Product_ID, 
	ol.M_AttributeSetInstance_ID,
	CASE WHEN o.IsSOTrx='Y' THEN COALESCE(ol.QtyReserved,0) ELSE 0 END AS QtyReserved_Sale,
	CASE WHEN o.IsSOTrx='N' THEN COALESCE(ol.QtyReserved,0) ELSE 0 END AS QtyReserved_Purchase,
	CASE WHEN o.IsSOTrx='Y' THEN COALESCE(ol.QtyOrdered,0) ELSE 0 END AS QtyOrdered_Sale
FROM C_Order o
	JOIN C_OrderLine ol ON ol.C_Order_ID=o.C_Order_ID AND ol.IsActive='Y' AND ol.IsHUStorageDisabled='N'
	LEFT JOIN M_ShipmentSchedule sched ON sched.C_OrderLine_ID=ol.C_OrderLine_ID AND sched.IsActive='Y'
WHERE true
	AND o.IsActive='Y'
	AND o.DocStatus IN ('CO', 'CL')
) data
GROUP BY 
	DateGeneral,
	M_Product_ID, 
	M_AttributeSetInstance_ID
;