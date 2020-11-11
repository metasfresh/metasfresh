-- DROP VIEW IF EXISTS EDI_M_HU_PI_Item_Product_Lookup_UPC_v;
CREATE OR REPLACE VIEW EDI_M_HU_PI_Item_Product_Lookup_UPC_v AS
SELECT DISTINCT ON (lookup.UPC, bpl.GLN)
	lookup.*,
	TRIM(BOTH ' ' FROM bpl.GLN) AS GLN
FROM
(
	SELECT
		piip.M_HU_PI_Item_Product_ID,
		piip.C_BPartner_ID,
		piip.UPC,
		piip.IsActive
	FROM M_HU_PI_Item_Product piip
	UNION 
	SELECT
		101 AS M_HU_PI_Item_Product_ID, -- fallback to "No Packing Item"
		bpp.C_BPartner_ID,
		bpp.UPC,
		bpp.IsActive
	FROM C_BPartner_Product bpp
) lookup
LEFT JOIN C_BPartner_Location bpl ON bpl.C_BPartner_ID=lookup.C_BPartner_ID AND bpl.GLN IS NOT NULL AND TRIM(BOTH ' ' FROM bpl.GLN)!=''
WHERE lookup.UPC IS NOT NULL AND trim(both ' ' from lookup.UPC)!=''
ORDER BY lookup.UPC, bpl.GLN, lookup.M_HU_PI_Item_Product_ID DESC
;


