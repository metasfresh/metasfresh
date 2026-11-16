-- Corrective data migration for the marker written by migration 5801580 (C_BPartner_Product -> M_Product_ASI_Data):
-- that script stored a free-text marker '[Migrated from C_BPartner_Product ...]' in
-- M_Product_ASI_Data.ProductDescription, which the EDI INVOIC export then printed as the product description.
-- Replace the marker with the description of the C_BPartner_Product row the M_Product_ASI_Data row was created from
-- (C_BPartner_Product.ProductDescription, else C_BPartner_Product.Description), or NULL if that row has neither.
--
-- Matching mirrors 5801580: same M_Product_ID, C_BPartner_ID, AD_Org_ID, IsActive; the C_BPartner_Product row carries
-- at least one EDI override; the M_Product_ASI_Data row has M_AttributeSetInstance_ID IS NULL.
-- If several C_BPartner_Product rows match one M_Product_ASI_Data row, the most recently updated one wins
-- (tie-break: lowest C_BPartner_Product_ID), so no row is updated twice.
-- Idempotent: only rows still carrying the marker are touched, and none carries it afterwards.

SELECT backup_table('m_product_asi_data', '_gh33256_fix_migration_marker');

UPDATE M_Product_ASI_Data asi
SET ProductDescription = src.NewProductDescription,
    Updated            = TO_TIMESTAMP('2026-10-30 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy          = 99
FROM (
    SELECT asi2.M_Product_ASI_Data_ID,
           cbp.NewProductDescription
    FROM M_Product_ASI_Data asi2
    LEFT JOIN LATERAL (
        SELECT COALESCE(NULLIF(BTRIM(c.ProductDescription), ''), NULLIF(BTRIM(c.Description), '')) AS NewProductDescription
        FROM C_BPartner_Product c
        WHERE c.M_Product_ID  = asi2.M_Product_ID
          AND c.C_BPartner_ID = asi2.C_BPartner_ID
          AND c.AD_Org_ID     = asi2.AD_Org_ID
          AND c.IsActive      = asi2.IsActive
          AND (c.GTIN IS NOT NULL OR c.EAN_CU IS NOT NULL OR c.UPC IS NOT NULL OR c.ProductNo IS NOT NULL OR c.EAN13_ProductCode IS NOT NULL)
        ORDER BY c.Updated DESC, c.C_BPartner_Product_ID
        LIMIT 1
    ) cbp ON TRUE
    WHERE asi2.ProductDescription LIKE '[Migrated from C_BPartner_Product %]'
      AND asi2.M_AttributeSetInstance_ID IS NULL
) src
WHERE asi.M_Product_ASI_Data_ID = src.M_Product_ASI_Data_ID;
