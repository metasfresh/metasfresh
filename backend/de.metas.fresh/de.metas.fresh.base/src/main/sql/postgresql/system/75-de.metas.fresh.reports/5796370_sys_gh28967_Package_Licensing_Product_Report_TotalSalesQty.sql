-- Replace MovementQty with TotalSalesQty in Package_Licensing_Product_Report.
-- Aligns hotfix with prod.

DROP FUNCTION IF EXISTS report.Package_Licensing_Product_Report(numeric);
DROP FUNCTION IF EXISTS report.Package_Licensing_Product_Report(numeric, varchar);
DROP FUNCTION IF EXISTS report.Package_Licensing_Product_Report(timestamp with time zone, timestamp with time zone, numeric, varchar);

CREATE OR REPLACE FUNCTION report.Package_Licensing_Product_Report(
    p_DateFrom             timestamp with time zone,
    p_DateTo               timestamp with time zone,
    p_C_Country_ID         numeric,
    p_IsIncludeAllProducts varchar DEFAULT 'Y'
)
    RETURNS TABLE
            (
                ProductValue               varchar,
                ProductName                varchar,
                PurchaseQty                numeric,
                ForeignSalesQty            numeric,
                TotalSalesQty              numeric,
                UOMSymbol                  varchar,
                Weight                     numeric,
                ProductGroup               varchar,
                MaterialType               varchar,
                SmallPackagingMaterial      varchar,
                SmallPackagingWeight        numeric,
                OuterPackagingMaterial      varchar,
                OuterPackagingWeight        numeric,
                PackagingInstructionFactor  numeric
            )
    LANGUAGE sql
    STABLE
AS
$$
-- Aggregates the InOut Report to product level.
-- Same columns as the detail report, minus per-InOut fields (DocumentNo, MovementDate, CountryCode).
SELECT r.ProductValue,
       r.ProductName,
       SUM(r.PurchaseQty)       AS PurchaseQty,
       SUM(r.ForeignSalesQty)   AS ForeignSalesQty,
       SUM(r.TotalSalesQty)     AS TotalSalesQty,
       r.UOMSymbol,
       r.Weight,
       r.ProductGroup,
       r.MaterialType,
       r.SmallPackagingMaterial,
       r.SmallPackagingWeight,
       r.OuterPackagingMaterial,
       r.OuterPackagingWeight,
       r.PackagingInstructionFactor
FROM report.Package_Licensing_InOut_Report(
             p_DateFrom := p_DateFrom,
             p_DateTo := p_DateTo,
             p_Country_id := p_C_Country_ID,
             p_IsIncludeAllProducts := p_IsIncludeAllProducts
     ) r
GROUP BY r.ProductValue,
         r.ProductName,
         r.UOMSymbol,
         r.Weight,
         r.ProductGroup,
         r.MaterialType,
         r.SmallPackagingMaterial,
         r.SmallPackagingWeight,
         r.OuterPackagingMaterial,
         r.OuterPackagingWeight,
         r.PackagingInstructionFactor
ORDER BY r.ProductValue, r.ProductName;
$$;
