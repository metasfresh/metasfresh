-- View: RV_M_HU_Storage_InvoiceHistory

-- DROP VIEW IF EXISTS RV_M_HU_Storage_InvoiceHistory;

CREATE OR REPLACE VIEW RV_M_HU_Storage_InvoiceHistory AS
SELECT 
	mrpInfo.AD_Client_ID,
	mrpInfo.AD_Org_ID,
	mrpInfo.IsActive,

	mrpInfo.M_Product_ID,
	mrpInfo.M_Locator_ID,
	mrpInfo.HUStorageASIKey,

	SUM(mrpInfo.QtyReserved) AS QtyReserved,
	SUM(mrpInfo.QtyOrdered) AS QtyOrdered,
	SUM(mrpInfo.QtyOnHand) AS QtyOnHand,
	SUM(mrpInfo.QtyMaterialentnahme) AS QtyMaterialentnahme
FROM RV_M_HU_Storage_MRPProductInfo mrpInfo
GROUP BY
	mrpInfo.AD_Client_ID,
	mrpInfo.AD_Org_ID,
	mrpInfo.IsActive,

	mrpInfo.M_Product_ID,
	mrpInfo.M_Locator_ID,
	mrpInfo.HUStorageASIKey

ORDER BY M_Product_ID, M_Locator_ID ASC NULLS FIRST, HUStorageASIKey
;
