CREATE OR REPLACE VIEW EDI_M_HU_PI_Item_Product_Lookup_UPC_v AS
SELECT DISTINCT ON (lookup.UPC, bpLookup.GLN, bpLookup.storegln)
    lookup.M_HU_PI_Item_Product_ID,
    --    lookup.M_Product_ID, not outputting M_Product_ID because we didn't do it in the past; just added M_Product_ID brelow so we can exclude inactive and discontinued products
    lookup.C_BPartner_ID,
    lookup.UPC,
    lookup.IsActive,
    bpLookup.GLN AS GLN,
    bpLookup.storegln AS StoreGLN
FROM
    (
        SELECT
            piip.M_HU_PI_Item_Product_ID,
            piip.M_Product_ID,
            piip.C_BPartner_ID,
            piip.GTIN AS UPC /* backwards compatibility*/,
            piip.IsActive
        FROM M_HU_PI_Item_Product piip
        WHERE piip.IsActive='Y'
        UNION SELECT
                  piip.M_HU_PI_Item_Product_ID,
                  piip.M_Product_ID,
                  piip.C_BPartner_ID,
                  piip.EAN_TU,
                  piip.IsActive
        FROM M_HU_PI_Item_Product piip
        WHERE piip.IsActive='Y'
        UNION SELECT
                  piip.M_HU_PI_Item_Product_ID,
                  piip.M_Product_ID,
                  piip.C_BPartner_ID,
                  piip.UPC,
                  piip.IsActive
        FROM M_HU_PI_Item_Product piip
        WHERE piip.IsActive='Y'
        UNION
        SELECT
            101 AS M_HU_PI_Item_Product_ID, -- fallback to "No Packing Item"
            bpp.M_Product_ID,
            bpp.C_BPartner_ID,
            bpp.EAN_CU,
            bpp.IsActive
        FROM C_BPartner_Product bpp
        WHERE bpp.IsActive='Y'
        UNION SELECT
                  101 AS M_HU_PI_Item_Product_ID, -- fallback to "No Packing Item"
                  bpp.M_Product_ID,
                  bpp.C_BPartner_ID,
                  bpp.UPC,
                  bpp.IsActive
        FROM C_BPartner_Product bpp
        WHERE bpp.IsActive='Y'
    ) lookup
        JOIN m_product p ON p.m_product_id = lookup.m_product_id
        LEFT JOIN EDI_C_BPartner_Lookup_BPL_GLN_v bpLookup on bpLookup.c_bpartner_id = lookup.c_bpartner_id
WHERE lookup.UPC IS NOT NULL AND trim(both ' ' from lookup.UPC)!=''
  AND p.isactive='Y' AND p.discontinued='N'
ORDER BY lookup.UPC, bpLookup.GLN, bpLookup.storegln, lookup.M_HU_PI_Item_Product_ID DESC
;
COMMENT ON VIEW EDI_M_HU_PI_Item_Product_Lookup_UPC_v IS
    'Lookup of M_HU_PI_Item_Product_ID via a M_HU_PI_Item_Product''s GTIN, TU-EAN (column EAN_TU), UPC (UPC) or as fallback to M_HU_PI_Item_Product_ID=101 with C_BPartner_Product''s EAN_CU or UPC. Ignores inactive or discontinued products as well as inactive packing-instructions (TU-EANs/TU-UPCs/GTINs) and inactive BPartner-Product-Records (CU-EAN/CU-UPC).';