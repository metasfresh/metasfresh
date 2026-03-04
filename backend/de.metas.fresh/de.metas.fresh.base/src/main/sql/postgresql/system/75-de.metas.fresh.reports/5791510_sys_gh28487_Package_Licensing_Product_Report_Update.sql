-- Run mode: SWING_CLIENT

DROP FUNCTION IF EXISTS report.Package_Licensing_Product_Report(numeric);

CREATE OR REPLACE FUNCTION report.Package_Licensing_Product_Report(p_C_Country_ID numeric DEFAULT NULL)
    RETURNS TABLE
            (
                ProductNo                  varchar,
                ProductName                varchar,
                ProductCategory            varchar,
                MaterialType               varchar,
                SmallPackagingMaterial      varchar,
                SmallPackagingWeight        numeric,
                OverpackMaterial            varchar,
                OverpackWeight              numeric,
                PackagingInstructionFactor  numeric,
                DeliveredQtyLast12Months    numeric
            )
    LANGUAGE sql
    STABLE
AS
$$
SELECT p.Value                AS ProductNo,
       p.Name                 AS ProductName,

       -- NEW: Product category
       pc.Name                AS ProductCategory,

       -- NEW: MaterialType (replaces ProductGroup; comma-separated when multiple)
       (SELECT string_agg(pp.Name, ', ' ORDER BY pp.Name)
        FROM M_Product_PackageLicensing_ProductGroup pppg
                 JOIN M_PackageLicensing_ProductGroup pp
                      ON pp.M_PackageLicensing_ProductGroup_ID = pppg.M_PackageLicensing_ProductGroup_ID
                          AND pp.IsActive = 'Y'
                          AND (p_C_Country_ID IS NULL OR pp.C_Country_ID = p_C_Country_ID)
        WHERE pppg.M_Product_ID = p.M_Product_ID
          AND pppg.IsActive = 'Y'
       )                      AS MaterialType,

       -- Small packaging material (refactored to subquery)
       (SELECT mg.Name
        FROM M_Product_SmallPackagingMaterial pspm
                 JOIN M_PackageLicensing_MaterialGroup mg
                      ON mg.M_PackageLicensing_MaterialGroup_ID = pspm.M_PackageLicensing_MaterialGroup_ID
                          AND mg.IsActive = 'Y'
                          AND (p_C_Country_ID IS NULL OR mg.C_Country_ID = p_C_Country_ID)
        WHERE pspm.M_Product_ID = p.M_Product_ID
          AND pspm.IsActive = 'Y'
        LIMIT 1
       )                      AS SmallPackagingMaterial,

       p.SmallPackagingWeight AS SmallPackagingWeight,

       -- Outer packaging material (refactored to subquery)
       (SELECT mg.Name
        FROM M_Product_OuterPackagingMaterial popm
                 JOIN M_PackageLicensing_MaterialGroup mg
                      ON mg.M_PackageLicensing_MaterialGroup_ID = popm.M_PackageLicensing_MaterialGroup_ID
                          AND mg.IsActive = 'Y'
                          AND (p_C_Country_ID IS NULL OR mg.C_Country_ID = p_C_Country_ID)
        WHERE popm.M_Product_ID = p.M_Product_ID
          AND popm.IsActive = 'Y'
        LIMIT 1
       )                      AS OverpackMaterial,

       p.OuterPackagingWeight AS OverpackWeight,

       -- NEW: Packaging instruction factor (default PI preferred)
       (SELECT piip.Qty
        FROM M_HU_PI_Item_Product piip
        WHERE piip.M_Product_ID = p.M_Product_ID
          AND piip.IsActive = 'Y'
        ORDER BY piip.IsDefaultForProduct DESC, piip.Created DESC
        LIMIT 1
       )                      AS PackagingInstructionFactor,

       -- NEW: Delivered quantity in last 12 months (completed customer shipments)
       (SELECT COALESCE(SUM(iol.MovementQty), 0)
        FROM M_InOutLine iol
                 JOIN M_InOut io ON io.M_InOut_ID = iol.M_InOut_ID
        WHERE iol.M_Product_ID = p.M_Product_ID
          AND io.IsActive = 'Y'
          AND io.DocStatus IN ('CO', 'CL')
          AND io.MovementType = 'C-'
          AND io.MovementDate >= (CURRENT_DATE - INTERVAL '12 months')
       )                      AS DeliveredQtyLast12Months

FROM M_Product p

         -- Product category
         LEFT JOIN M_Product_Category pc ON pc.M_Product_Category_ID = p.M_Product_Category_ID

WHERE p.IsActive = 'Y'
  AND (EXISTS (SELECT 1
               FROM M_Product_PackageLicensing_ProductGroup pppg
               WHERE pppg.M_Product_ID = p.M_Product_ID AND pppg.IsActive = 'Y')
    OR EXISTS (SELECT 1
               FROM M_Product_SmallPackagingMaterial pspm
               WHERE pspm.M_Product_ID = p.M_Product_ID AND pspm.IsActive = 'Y')
    OR EXISTS (SELECT 1
               FROM M_Product_OuterPackagingMaterial popm
               WHERE popm.M_Product_ID = p.M_Product_ID AND popm.IsActive = 'Y'))

ORDER BY p.Value, p.Name;
$$;

COMMENT ON FUNCTION report.Package_Licensing_Product_Report(numeric) IS 'gh#28487: Updated product master data export for packaging licensing. Added ProductCategory, MaterialType (replaces ProductGroup), PackagingInstructionFactor, and DeliveredQtyLast12Months. Refactored to subqueries to avoid row multiplication.';
