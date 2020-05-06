-- View: RV_HU_Quantities_Summary

-- DROP VIEW IF EXISTS RV_HU_Quantities_Summary;

CREATE OR REPLACE VIEW RV_HU_Quantities_Summary AS
SELECT 
	product.M_Product_ID
	, product.Name

	, SUM(COALESCE(ih.QtyReserved, 0)) AS QtyReserved
	, SUM(COALESCE(ih.QtyOrdered, 0)) AS QtyOrdered
	, SUM(COALESCE(ih.QtyOnHand, 0)) AS QtyOnHand
	, SUM(COALESCE(ih.QtyOnHand, 0)) - SUM(COALESCE(ih.QtyReserved, 0)) AS QtyAvailable
	, SUM(COALESCE(ih.QtyMaterialentnahme, 0)) AS QtyMaterialentnahme

	, product.Value
	, product.IsPurchased
	, product.IsSold
	, product.M_Product_Category_ID

	, product.AD_Client_ID
	, product.AD_Org_ID
	, product.IsActive
FROM M_Product product
LEFT OUTER JOIN RV_M_HU_Storage_InvoiceHistory ih ON ih.M_Product_ID = product.M_Product_ID
GROUP BY 
	product.M_Product_ID
	, product.Name

	, product.Value
	, product.IsPurchased
	, product.IsSold
	, product.M_Product_Category_ID

	, product.AD_Client_ID
	, product.AD_Org_ID
	, product.isActive
;

