-- Run mode: SWING_CLIENT

DROP FUNCTION IF EXISTS report.Package_Licensing_Product_Report(numeric);

CREATE OR REPLACE FUNCTION report.Package_Licensing_Product_Report(p_C_Country_ID numeric DEFAULT NULL)
    RETURNS TABLE
            (
                ProductNo              varchar,
                ProductName            varchar,
                ProductGroup           varchar,
                SmallPackagingMaterial varchar,
                SmallPackagingWeight   numeric,
                OverpackMaterial       varchar,
                OverpackWeight         numeric
            )
    LANGUAGE sql
    STABLE
AS
$$
SELECT p.Value                AS ProductNo,
       p.Name                 AS ProductName,
       pp.Name                AS ProductGroup,
       mg_small.Name          AS SmallPackagingMaterial,
       p.SmallPackagingWeight AS SmallPackagingWeight,
       mg_outer.Name          AS OverpackMaterial,
       p.OuterPackagingWeight AS OverpackWeight

FROM M_Product p

         -- Product group (country-specific)
         LEFT JOIN M_Product_PackageLicensing_ProductGroup pppg
                   ON pppg.M_Product_ID = p.M_Product_ID
                       AND pppg.IsActive = 'Y'
         LEFT JOIN M_PackageLicensing_ProductGroup pp
                   ON pp.M_PackageLicensing_ProductGroup_ID = pppg.M_PackageLicensing_ProductGroup_ID
                       AND pp.IsActive = 'Y'
                       AND (p_C_Country_ID IS NULL OR pp.C_Country_ID = p_C_Country_ID)

         -- Small packaging material (country-specific)
         LEFT JOIN M_Product_SmallPackagingMaterial pspm
                   ON pspm.M_Product_ID = p.M_Product_ID
                       AND pspm.IsActive = 'Y'
         LEFT JOIN M_PackageLicensing_MaterialGroup mg_small
                   ON mg_small.M_PackageLicensing_MaterialGroup_ID = pspm.M_PackageLicensing_MaterialGroup_ID
                       AND mg_small.IsActive = 'Y'
                       AND (p_C_Country_ID IS NULL OR mg_small.C_Country_ID = p_C_Country_ID)

         -- Outer packaging material (country-specific)
         LEFT JOIN M_Product_OuterPackagingMaterial popm
                   ON popm.M_Product_ID = p.M_Product_ID
                       AND popm.IsActive = 'Y'
         LEFT JOIN M_PackageLicensing_MaterialGroup mg_outer
                   ON mg_outer.M_PackageLicensing_MaterialGroup_ID = popm.M_PackageLicensing_MaterialGroup_ID
                       AND mg_outer.IsActive = 'Y'
                       AND (p_C_Country_ID IS NULL OR mg_outer.C_Country_ID = p_C_Country_ID)

WHERE p.IsActive = 'Y'
  AND (pp.M_PackageLicensing_ProductGroup_ID IS NOT NULL
    OR pspm.M_Product_SmallPackagingMaterial_ID IS NOT NULL
    OR popm.M_Product_OuterPackagingMaterial_ID IS NOT NULL)

ORDER BY p.Value, p.Name;
$$;

COMMENT ON FUNCTION report.Package_Licensing_Product_Report(numeric) IS 'gh#28225: Product master data export for packaging licensing. Lists all active products that have at least one packaging licensing attribute (product group, small packaging material, or outer packaging material). Optional country filter restricts material/product group names to the specified country.';
