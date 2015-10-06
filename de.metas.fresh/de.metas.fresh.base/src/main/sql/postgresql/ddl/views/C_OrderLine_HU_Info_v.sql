DROP VIEW IF EXISTS C_OrderLine_HU_Info_v;
CREATE OR REPLACE VIEW C_OrderLine_HU_Info_v AS
--
-- Order Lines: for QtyReserved, QtyOrdered
SELECT
 -- Mandatory ADempiere data
 ol_ext.AD_Client_ID,
 ol_ext.AD_Org_ID,
 ol_ext.IsActive,
 --
 COALESCE (
 	ol_ext.sched_PreparationDate, -- task 08931: the user works with PreparationDate instead of DatePromised
 	o.PreparationDate, -- fallback in case the schedule does not exist yet 
 	o.DatePromised -- fallback in case of orders created by the system (e.g. EDI) and missing proper tour planning master data 
 ) AS DateGeneral,
 ol_ext.M_Product_ID,
 ol_ext.ASIKeyName,
 --
 hupiip.Name AS M_HU_PI_Item_Product,
 --
 SUM(CASE WHEN o.IsSOTrx='Y' THEN ol_ext.QtyReserved ELSE 0 END) AS QtyReserved,
 SUM(CASE WHEN o.IsSOTrx='Y' THEN 0 ELSE ol_ext.QtyReserved END) AS QtyOrdered

FROM C_Order o
INNER JOIN (
	 -- Extend OrderLine with the ASIKey
	 SELECT
	  GenerateHUStorageASIKey(ol.M_AttributeSetInstance_ID) AS ASIKeyName,
	
	  ol.AD_Client_ID,
	  ol.AD_Org_ID,
	  ol.IsActive,
	
	  ol.M_Product_ID,
	  ol.QtyReserved,
	
	  ol.C_Order_ID,
	  ol.M_HU_PI_Item_Product_ID,
	  ol.IsHUStorageDisabled,
	  COALESCE(sched.PreparationDate_Override, sched.PreparationDate) AS sched_PreparationDate
	 FROM C_OrderLine ol
		LEFT JOIN M_ShipmentSchedule sched ON sched.C_OrderLine_ID=ol.C_OrderLine_ID AND sched.IsActive='Y'
) ol_ext ON ol_ext.C_Order_ID=o.C_Order_ID
LEFT JOIN M_HU_PI_Item_Product hupiip ON hupiip.M_HU_PI_Item_Product_ID = ol_ext.M_HU_PI_Item_Product_ID
WHERE o.DocStatus IN (
		'CO', -- Complete
		 -- 'CL', -- Closed
		'IP') -- Prepare
	AND o.IsActive='Y' AND ol_ext.IsActive='Y'
	AND ol_ext.QtyReserved <> 0
	AND ol_ext.IsHUStorageDisabled='N' /* task 08242: hide pre-go-live records */
GROUP BY
	 ol_ext.AD_Client_ID,
	 ol_ext.AD_Org_ID,
	 ol_ext.IsActive,
	 --
	 ol_ext.M_Product_ID,
	 COALESCE (ol_ext.sched_PreparationDate, o.PreparationDate, o.DatePromised),
	 ol_ext.ASIKeyName,
	 --
	 hupiip.Name
--LIMIT 100
 ;