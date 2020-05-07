update PMM_Product t set
	ProductName=coalesce(
		(select bpp.ProductName from C_BPartner_Product bpp where bpp.M_Product_ID=t.M_Product_ID and bpp.C_BPartner_ID=t.C_BPartner_ID)
		, (select p.Name from M_Product p where p.M_Product_ID=t.M_Product_ID)
	)
	, PackDescription=(select piip.Description from M_HU_PI_Item_Product piip where piip.M_HU_PI_Item_Product_ID=t.M_HU_PI_Item_Product_ID)
;
