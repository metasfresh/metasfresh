create unique index PMM_PurchaseCandidate_UQ on PMM_PurchaseCandidate (
	-- Segment
	C_BPartner_ID
	, M_Product_ID
	, coalesce(M_AttributeSetInstance_ID, 0)
	, coalesce(M_HU_PI_Item_Product_ID, 0)
	, coalesce(C_Flatrate_DataEntry_ID, 0)
	-- Date
	, DatePromised
);

