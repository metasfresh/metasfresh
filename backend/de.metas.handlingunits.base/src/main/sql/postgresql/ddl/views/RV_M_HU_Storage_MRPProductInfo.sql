-- View: RV_M_HU_Storage_MRPProductInfo

-- DROP VIEW IF EXISTS RV_M_HU_Storage_MRPProductInfo;

/**
 * Story: Aggregate the Qty of the HUs which are ACTIVE and use it as QtyOnHand
 */
CREATE OR REPLACE VIEW RV_M_HU_Storage_MRPProductInfo AS
--
-- Order Lines: for QtyReserved, QtyOrdered
SELECT
 -- Mandatory ADempiere data
 ol_ext.AD_Client_ID,
 ol_ext.AD_Org_ID,
 ol_ext.IsActive,
 --
 ol_ext.M_Product_ID,
 NULL::NUMERIC AS M_Locator_ID,
 ol_ext.HUStorageASIKey AS HUStorageASIKey,
 --
 hupiip.Name AS M_HU_PI_Item_Product,
 --
 SUM(CASE WHEN o.IsSOTrx='Y' THEN ol_ext.QtyReserved ELSE 0 END) AS QtyReserved,
 SUM(CASE WHEN o.IsSOTrx='Y' THEN 0 ELSE ol_ext.QtyReserved END) AS QtyOrdered,
 0 AS QtyOnHand, -- For orders, we don't have QtyOnHand
 0 AS QtyReserved_MRP, -- fresh_08273
 0 AS QtyMaterialentnahme
FROM C_Order o
INNER JOIN (
 -- Extend OrderLine with the ASIKey
 SELECT
  GenerateHUStorageASIKey(ol.M_AttributeSetInstance_ID) AS HUStorageASIKey,

  ol.AD_Client_ID,
  ol.AD_Org_ID,
  ol.IsActive,

  ol.M_Product_ID,
  ol.QtyReserved,

  ol.C_Order_ID,
  ol.M_HU_PI_Item_Product_ID,
  ol.IsHUStorageDisabled
 FROM C_OrderLine ol
) ol_ext ON ol_ext.C_Order_ID=o.C_Order_ID
LEFT JOIN M_HU_PI_Item_Product hupiip ON hupiip.M_HU_PI_Item_Product_ID = ol_ext.M_HU_PI_Item_Product_ID
WHERE o.DocStatus IN ('CO', -- Complete
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
 ol_ext.HUStorageASIKey,
 --
 hupiip.Name
--
--
-- MRP: QtyReserved_MRP ...from completed MOs (fresh_08273)
UNION ALL
SELECT
 mrp.AD_Client_ID,
 mrp.AD_Org_ID,
 mrp.IsActive,
 --
 mrp.M_Product_ID,
 NULL::NUMERIC AS M_Locator_ID,
 GenerateHUStorageASIKey(mrp.M_AttributeSetInstance_ID) AS HUStorageASIKey,
 --
 NULL::character varying(80) AS M_HU_PI_Item_Product,
 --
 0 AS QtyReserved,
 0 AS QtyOrdered,
 0 AS QtyOnHand, -- For MRPs, we don't have QtyOnHand
 COALESCE(SUM(mrp.Qty),0) AS QtyReserved_MRP, -- fresh_08273
 0 AS QtyMaterialentnahme
FROM PP_MRP mrp
WHERE mrp.IsActive='Y' 
		AND mrp.TypeMRP='D' -- Demands
		AND mrp.OrderType='MOP' -- Manufacturing Order
		AND mrp.DocStatus='CO' -- Completed Manufacturing orders
GROUP BY
 mrp.AD_Client_ID
 , mrp.AD_Org_ID
 , mrp.IsActive
 --
 , mrp.M_Product_ID
 , HUStorageASIKey
 --
 -- hupiip.Name
--
--
-- HU Storage: QtyOnHand
UNION ALL
(SELECT 
 hu_storage_info.AD_Client_ID,
 hu_storage_info.AD_Org_ID,
 hu_storage_info.IsActive,

 hu_storage_info.M_Product_ID,
 hu_storage_info.M_Locator_ID,
 hu_storage_info.HUStorageHUKey,

 hu_storage_info.M_HU_PI_Item_Product,

 0 AS QtyReserved,
 0 AS QtyOrdered,
 SUM(CASE
 	WHEN hu_storage_info.HUStatus='A' THEN COALESCE(hu_storage_info.HUStorageQty, 0)
  	ELSE -1 * COALESCE(hu_storage_info.HUStorageQty, 0)
 	END) AS QtyOnHand, -- For storages, we don't have QtyReserved or QtyOrdered
 0 AS QtyReserved_MRP, -- fresh_08273
 COALESCE(SUM(hu_storage_info.QtyMaterialentnahme),0) AS QtyMaterialentnahme
FROM X_M_HU_Storage_HUInfo_MV hu_storage_info -- use materialized view

GROUP BY
 hu_storage_info.AD_Client_ID,
 hu_storage_info.AD_Org_ID,
 hu_storage_info.IsActive,

 hu_storage_info.M_Product_ID,
 hu_storage_info.M_Locator_ID,
 hu_storage_info.HUStorageHUKey,

 hu_storage_info.M_HU_PI_Item_Product
)
ORDER BY M_Product_ID, M_Locator_ID ASC NULLS FIRST, HUStorageASIKey
;
