--DROP FUNCTION m_storage_recalculate();

CREATE FUNCTION  m_storage_recalculate() RETURNS VOID
AS $$

DELETE FROM t_m_storage_candidate;

INSERT INTO t_m_storage_candidate
(
	AD_Client_ID, AD_Org_ID, M_Locator_ID, M_Product_ID, M_AttributeSetInstance_ID
	, QtyOnHand, QtyOrdered, QtyReserved
	, Created, CreatedBy, Updated, UpdatedBy, IsActive
)
SELECT t.AD_Client_ID, wh.AD_Org_ID, loc.M_Locator_ID, t.M_Product_ID, t.M_AttributeSetInstance_ID
, SUM(t.MovementQty) as QtyOnHand
, 0 as QtyOrdered
, 0 as QtyReserved
, min(t.Created) as Created, 0 as CreatedBy, max(t.Updated) as Updated, 0 as UpdatedBy, 'Y' as IsActive
FROM M_Transaction t
INNER JOIN M_Locator loc ON (loc.M_Locator_ID=t.M_Locator_ID)
INNER JOIN M_Warehouse wh ON (wh.M_Warehouse_ID=loc.M_Warehouse_ID)
GROUP BY t.AD_Client_ID, wh.AD_Org_ID, loc.M_Locator_ID, t.M_Product_ID, t.M_AttributeSetInstance_ID
;


INSERT INTO t_m_storage_candidate
(
	AD_Client_ID, AD_Org_ID, M_Locator_ID, M_Product_ID, M_AttributeSetInstance_ID
	, QtyOnHand, QtyOrdered, QtyReserved
	, Created, CreatedBy, Updated, UpdatedBy, IsActive
)
SELECT ol.AD_Client_ID, wh.AD_Org_ID
	, (SELECT loc.M_Locator_ID FROM M_Locator loc WHERE loc.M_Warehouse_ID=wh.M_Warehouse_ID ORDER BY loc.IsDefault DESC, loc.M_Locator_ID ASC LIMIT 1) AS M_Locator_ID
	, ol.M_Product_ID
	, 0 AS M_AttributeSetInstance_ID
	, 0 AS QtyOnHand
	, SUM(CASE WHEN o.IsSOTrx='Y' THEN ol.QtyOrdered - ol.QtyDelivered ELSE 0 END) as QtyOrdered
	, SUM(CASE WHEN o.IsSOTrx='N' THEN ol.QtyOrdered - ol.QtyDelivered ELSE 0 END) as QtyReserved
	, min(o.Created) as Created, 0 as CreatedBy, max(o.Updated) as Updated, 0 as UpdatedBy, 'Y' as IsActive
FROM C_OrderLine ol
INNER JOIN C_Order o ON (o.C_Order_ID=ol.C_Order_ID)
INNER JOIN M_Warehouse wh ON (wh.M_Warehouse_ID=COALESCE(ol.M_Warehouse_ID, o.M_Warehouse_ID))
WHERE ol.M_Product_ID IS NOT NULL
	AND (ol.QtyOrdered - ol.QtyDelivered) <> 0
GROUP BY ol.AD_Client_ID, wh.AD_Org_ID, wh.M_Warehouse_ID, ol.M_Product_ID
;

DELETE FROM M_Storage;

INSERT INTO M_Storage (
	AD_Client_ID, AD_Org_ID, M_Locator_ID, M_Product_ID, M_AttributeSetInstance_ID
	, QtyOnHand, QtyOrdered, QtyReserved
	, Created, CreatedBy, Updated, UpdatedBy, IsActive
)
SELECT sc.AD_Client_ID, sc.AD_Org_ID, sc.M_Locator_ID, sc.M_Product_ID, sc.M_AttributeSetInstance_ID
	, SUM(sc.QtyOnHand) as QtyOnHand
	, SUM(sc.QtyOrdered) as QtyOrdered
	, SUM(sc.QtyReserved) as QtyReserved
	, MIN(sc.Created) as Created
	, 0 as CreatedBy
	, MIN(sc.Updated) as Updated
	, 0 as UpdatedBy
	, 'Y' as IsActive
FROM t_m_storage_candidate sc
GROUP BY sc.AD_Client_ID, sc.AD_Org_ID, sc.M_Locator_ID, sc.M_Product_ID, sc.M_AttributeSetInstance_ID
HAVING (SUM(sc.QtyOnHand) <> 0 or SUM(sc.QtyOrdered) <> 0 or SUM(sc.QtyReserved) <> 0)
;
$$ LANGUAGE SQL;