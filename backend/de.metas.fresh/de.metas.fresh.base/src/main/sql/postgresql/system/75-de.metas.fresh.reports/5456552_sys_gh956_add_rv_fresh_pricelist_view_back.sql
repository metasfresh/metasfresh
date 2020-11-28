DROP VIEW IF EXISTS RV_fresh_PriceList;

CREATE OR REPLACE VIEW RV_fresh_PriceList AS
SELECT
	-- Mandatory Columns
	plc.AD_Org_ID, plc.AD_Client_ID,
	plc.Created, plc.CreatedBy,
	plc.Updated, plc.UpdatedBy,
	plc.IsActive,

	-- Displayed pricelist data
	plc.ProductCategory,
	plc.ProductCategoryValue,
	plc.M_Product_ID,
	plc.Value,
	plc.ProductName,
	plc.IsSeasonFixedPrice,
	plc.ItemProductName,
	plc.PackingMaterialName,
	plc.PriceStd,
	plc.AltPriceStd,
	plc.HasAltPrice,
	plc.UOMSymbol,
	plc.Attributes,

	-- Filter Columns
	plc.C_BPartner_ID,
	plc.M_Pricelist_Version_ID,
	plc.M_ProductPrice_ID
FROM
	RV_fresh_PriceList_Comparison plc 
WHERE
	plc.M_Pricelist_Version_ID = plc.Alt_PriceList_Version_ID
;

COMMENT ON VIEW RV_fresh_PriceList IS '05956 Printformat for Pricelist (109054740508) 
Refactored in Task 07833 and 07915
A view for a report that displays all product prices of a BPartner, including the attribute prices and CU:TU relation';
