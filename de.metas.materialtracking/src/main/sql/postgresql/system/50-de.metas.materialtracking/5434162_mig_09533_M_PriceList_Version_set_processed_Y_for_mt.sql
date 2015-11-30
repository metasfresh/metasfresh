

UPDATE M_PriceList_Version
SET Processed='Y' -- set prexisting PLVs to processed
WHERE M_PriceList_Version_ID IN (
	SELECT --c.NAme, pl.Name, plv.Name, 
		plv.M_PriceList_Version_ID
	FROM C_Flatrate_Conditions c
		JOIN M_PriceList pl ON pl.M_PricingSystem_ID=c.M_PricingSystem_ID
			JOIN M_PriceList_Version plv ON plv.M_PriceList_ID=pl.M_PriceList_ID
	WHERE c.Type_Conditions='QualityBsd'
);

