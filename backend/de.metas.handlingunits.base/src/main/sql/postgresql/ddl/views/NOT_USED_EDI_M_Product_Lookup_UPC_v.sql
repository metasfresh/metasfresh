-- newer version in de.metas.edi

-- View: EDI_M_Product_Lookup_UPC_v

-- DROP VIEW IF EXISTS EDI_M_Product_Lookup_UPC_v;

CREATE OR REPLACE VIEW EDI_M_Product_Lookup_UPC_v AS
SELECT DISTINCT ON (lookup.UPC, bpl.GLN)
	lookup.*,
	TRIM(BOTH ' ' FROM bpl.GLN) AS GLN
FROM
(
	SELECT
		piip.M_Product_ID,
		piip.C_BPartner_ID,
		piip.UPC,
		piip.IsActive
	FROM M_HU_PI_Item_Product piip
	UNION SELECT
		bpp.M_Product_ID,
		bpp.C_BPartner_ID,
		bpp.UPC,
		bpp.IsActive
	FROM C_BPartner_Product bpp
	WHERE NOT EXISTS (SELECT 1 FROM M_HU_PI_Item_Product piip WHERE piip.UPC=bpp.UPC)
) lookup
LEFT JOIN C_BPartner_Location bpl ON bpl.C_BPartner_ID=lookup.C_BPartner_ID AND bpl.GLN IS NOT NULL AND TRIM(BOTH ' ' FROM bpl.GLN)!=''
WHERE lookup.UPC IS NOT NULL AND TRIM(BOTH ' ' FROM lookup.UPC)!=''
;


