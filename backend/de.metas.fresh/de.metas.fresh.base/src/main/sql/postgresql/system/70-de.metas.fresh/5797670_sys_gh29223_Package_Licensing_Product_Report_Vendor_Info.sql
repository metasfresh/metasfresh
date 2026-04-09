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
                VendorName                 varchar,
                VendorCountryCode          varchar,
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
                PackagingInstructionFactor  numeric,
                IsVendorPackageLicensingExempt varchar
            )
    LANGUAGE sql
    STABLE
AS
$$
-- Aggregates the InOut Report to product + vendor level.
-- When a product has multiple vendors, each vendor gets its own row with PurchaseQty.
-- ForeignSalesQty and TotalSalesQty are shown only on the first vendor row per product.
WITH detail AS (
    SELECT *
    FROM report.Package_Licensing_InOut_Report(
                 p_DateFrom := p_DateFrom,
                 p_DateTo := p_DateTo,
                 p_Country_id := p_C_Country_ID,
                 p_IsIncludeAllProducts := p_IsIncludeAllProducts
         )
),
-- Purchases aggregated by product + vendor
purchases AS (
    SELECT d.ProductValue,
           d.ProductName,
           d.VendorName,
           d.VendorCountryCode,
           d.IsVendorPackageLicensingExempt,
           SUM(d.PurchaseQty)  AS PurchaseQty,
           d.UOMSymbol,
           d.Weight,
           d.ProductGroup,
           d.MaterialType,
           d.SmallPackagingMaterial,
           d.SmallPackagingWeight,
           d.OuterPackagingMaterial,
           d.OuterPackagingWeight,
           d.PackagingInstructionFactor
    FROM detail d
    WHERE d.PurchaseQty IS NOT NULL
    GROUP BY d.ProductValue, d.ProductName,
             d.VendorName, d.VendorCountryCode,
             d.IsVendorPackageLicensingExempt,
             d.UOMSymbol, d.Weight,
             d.ProductGroup, d.MaterialType,
             d.SmallPackagingMaterial, d.SmallPackagingWeight,
             d.OuterPackagingMaterial, d.OuterPackagingWeight,
             d.PackagingInstructionFactor
),
-- Sales aggregated by product (not vendor-specific)
sales AS (
    SELECT d.ProductValue,
           SUM(d.ForeignSalesQty) AS ForeignSalesQty,
           SUM(d.TotalSalesQty)   AS TotalSalesQty
    FROM detail d
    GROUP BY d.ProductValue
),
-- Products that only have sales (no purchases) — still need to appear
sales_only_products AS (
    SELECT DISTINCT
           d.ProductValue,
           d.ProductName,
           d.UOMSymbol,
           d.Weight,
           d.ProductGroup,
           d.MaterialType,
           d.SmallPackagingMaterial,
           d.SmallPackagingWeight,
           d.OuterPackagingMaterial,
           d.OuterPackagingWeight,
           d.PackagingInstructionFactor
    FROM detail d
    WHERE NOT EXISTS (SELECT 1 FROM purchases pu WHERE pu.ProductValue = d.ProductValue)
),
-- Combine purchase rows + sales-only rows, rank per product
combined AS (
    -- Purchase rows (one per product+vendor)
    SELECT p.ProductValue, p.ProductName,
           p.VendorName, p.VendorCountryCode,
           p.IsVendorPackageLicensingExempt,
           p.PurchaseQty,
           p.UOMSymbol, p.Weight,
           p.ProductGroup, p.MaterialType,
           p.SmallPackagingMaterial, p.SmallPackagingWeight,
           p.OuterPackagingMaterial, p.OuterPackagingWeight,
           p.PackagingInstructionFactor,
           ROW_NUMBER() OVER (PARTITION BY p.ProductValue ORDER BY p.VendorName NULLS LAST) AS rn
    FROM purchases p

    UNION ALL

    -- Sales-only products (no purchases at all)
    SELECT sp.ProductValue, sp.ProductName,
           NULL::varchar, NULL::varchar,
           NULL::varchar,
           NULL::numeric,
           sp.UOMSymbol, sp.Weight,
           sp.ProductGroup, sp.MaterialType,
           sp.SmallPackagingMaterial, sp.SmallPackagingWeight,
           sp.OuterPackagingMaterial, sp.OuterPackagingWeight,
           sp.PackagingInstructionFactor,
           1 AS rn
    FROM sales_only_products sp
)
SELECT c.ProductValue,
       c.ProductName,
       c.VendorName,
       c.VendorCountryCode,
       c.PurchaseQty,
       CASE WHEN c.rn = 1 THEN s.ForeignSalesQty END AS ForeignSalesQty,
       CASE WHEN c.rn = 1 THEN s.TotalSalesQty END   AS TotalSalesQty,
       c.UOMSymbol,
       c.Weight,
       c.ProductGroup,
       c.MaterialType,
       c.SmallPackagingMaterial,
       c.SmallPackagingWeight,
       c.OuterPackagingMaterial,
       c.OuterPackagingWeight,
       c.PackagingInstructionFactor,
       c.IsVendorPackageLicensingExempt
FROM combined c
         LEFT JOIN sales s ON s.ProductValue = c.ProductValue
ORDER BY c.ProductValue, c.VendorName NULLS LAST;
$$;
