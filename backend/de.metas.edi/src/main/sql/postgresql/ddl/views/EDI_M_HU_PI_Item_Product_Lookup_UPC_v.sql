-- View: EDI_M_HU_PI_Item_Product_Lookup_UPC_v
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
		piip.GTIN AS UPC /* backwards compatibility*/,
		piip.IsActive
	FROM M_HU_PI_Item_Product piip
	UNION SELECT
		piip.M_HU_PI_Item_Product_ID,
		piip.C_BPartner_ID,
		piip.EAN_TU,
		piip.IsActive
	FROM M_HU_PI_Item_Product piip
	UNION SELECT
		piip.M_HU_PI_Item_Product_ID,
		piip.C_BPartner_ID,
		piip.UPC,
		piip.IsActive
	FROM M_HU_PI_Item_Product piip
	UNION 
	SELECT
		101 AS M_HU_PI_Item_Product_ID, -- fallback to "No Packing Item"
		bpp.C_BPartner_ID,
		bpp.EAN_CU,
		bpp.IsActive
	FROM C_BPartner_Product bpp
	UNION SELECT
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
COMMENT ON VIEW EDI_M_HU_PI_Item_Product_Lookup_UPC_v IS
'Lookup of M_HU_PI_Item_Product_ID via a M_HU_PI_Item_Product''s GTIN, TU-EAN (column EAN_TU), UPC (UPC) or as fallback to M_HU_PI_Item_Product_ID=101 with C_BPartner_Product''s EAN_CU or UPC.';
