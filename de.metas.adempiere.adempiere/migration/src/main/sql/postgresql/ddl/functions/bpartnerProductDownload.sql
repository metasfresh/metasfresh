DROP FUNCTION IF EXISTS bpartnerProductDownload(numeric, numeric);

CREATE OR REPLACE FUNCTION bpartnerProductDownload(
    IN p_M_Product_ID numeric,
	IN p_C_BPartner_ID numeric)

  RETURNS TABLE 
  (
	ProductValue character varying,
	ProductName character varying,
	BPartnerValue character varying,
	BPartnerName character varying,
	seqNo numeric,
	UsedForCustomer character,
	Name character varying,
	ProductNo character varying,
	
	M_AttributeSetInstance_ID character varying,
	EAN_CU character varying,
	UPC character varying,
	ProductCategory character varying,
	
	UsedForVendor character,
	VendorCategory character varying,
	VendorProductNo character varying,
	VendorName character varying,
	
	IsActive character,
	IsDropShip character,
	IsCurrentVendor character,
	Manufacturer character varying,
	QualityRating numeric,
	Description character varying,
	ProductDescription character varying,
	ShelfLifeMinDays numeric,
	ShelfLifeMinPct numeric,
	IsExcludedFromSale character,
		
	ExclusionFromSaleReason character varying,
	CustomerLabelName character varying,
	Ingredients character varying,
				
	DeliveryTime_Promised numeric,
	Order_Min numeric,
	Order_Pack numeric
  )
   AS


$BODY$
  
  SELECT 
	p.value as ProductValue,
	p.name as ProductName,
	bp.value as BPartnerValue,
	bp.name BPartnerName,
	bpp.SeqNo,

	
	bpp.UsedForCustomer,
	bpp.ProductName,
	bpp.ProductNo,
	
	asi.Description,
	bpp.EAN_CU,
	bpp.UPC,
	bpp.ProductCategory,
	
	bpp.UsedForVendor,
	bpp.VendorCategory,
	bpp.VendorProductNo,
	vendor.Name,
	
	bpp.IsActive,
	bpp.IsDropShip,
	bpp.IsCurrentVendor,
	bpp.Manufacturer,
	bpp.QualityRating,
	bpp.Description,
	bpp.ProductDescription,
	bpp.ShelfLifeMinDays,
	bpp.ShelfLifeMinPct,
	bpp.IsExcludedFromSale,
		
	bpp.ExclusionFromSaleReason,
	bpp.CustomerLabelName,
	bpp.Ingredients,
				
	bpp.DeliveryTime_Promised,
	bpp.Order_Min,
	bpp.Order_Pack


	
FROM
	M_Product p
	JOIN C_BPartner_Product bpp ON p.M_Product_ID = bpp.M_Product_ID
	JOIN C_BPartner bp ON bpp.C_BPartner_ID = bp.C_BPartner_ID
	LEFT JOIN M_AttributeSetInstance asi on bpp.M_AttributeSetInstance_ID = asi.M_AttributeSetInstance_ID
	LEFT JOIN C_BPartner vendor ON bpp.C_BPartner_Vendor_ID = vendor.C_BPartner_ID
	
	WHERE CASE WHEN p_M_Product_ID > 0 THEN p.M_PRoduct_ID = p_M_Product_ID ELSE 1=1 END
	AND CASE WHEN p_C_BPartner_ID > 0 THEN bp.C_BPartner_ID = p_C_BPartner_ID ELSE 1=1 END

$BODY$
  LANGUAGE sql STABLE
  COST 100
  ROWS 1000;