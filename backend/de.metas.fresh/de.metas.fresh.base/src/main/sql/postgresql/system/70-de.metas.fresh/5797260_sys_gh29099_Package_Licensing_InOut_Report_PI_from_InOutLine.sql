-- gh#29099: Extract PackagingInstructionFactor from the actual InOut line's M_HU_PI_Item_Product,
-- falling back to the product's default PI if the InOut line has no PI reference.
-- This gives more accurate results because it uses the PI that was actually used for that specific receipt/shipment.

DROP FUNCTION IF EXISTS report.Package_Licensing_InOut_Report(p_DateFrom              timestamp with time zone,
                                                              p_DateTo                timestamp with time zone,
                                                              p_Country_id            numeric)
;
DROP FUNCTION IF EXISTS report.Package_Licensing_InOut_Report(p_DateFrom              timestamp with time zone,
                                                              p_DateTo                timestamp with time zone,
                                                              p_Country_id            numeric,
                                                              p_IsIncludeAllProducts  varchar)
;

CREATE OR REPLACE FUNCTION report.Package_Licensing_InOut_Report(p_DateFrom              timestamp with time zone,
                                                                 p_DateTo                timestamp with time zone,
                                                                 p_Country_id            numeric,
                                                                 p_IsIncludeAllProducts  varchar DEFAULT 'Y')

    RETURNS TABLE
            (
                DocumentNo                 varchar,
                MovementDate               date,
                CountryCode                varchar,
                ProductValue               varchar,
                ProductName                varchar,
                PurchaseQty                numeric,
                ForeignSalesQty            numeric,
                TotalSalesQty              numeric,
                UOMSymbol                  varchar,
                Weight                     numeric,
                ProductGroup               varchar,
                MaterialType               varchar,
                SmallPackagingMaterial     varchar,
                SmallPackagingWeight       numeric,
                OuterPackagingMaterial     varchar,
                OuterPackagingWeight       numeric,
                PackagingInstructionFactor numeric
            )

AS
$$

SELECT io.DocumentNo,
       io.MovementDate,
       c.CountryCode,
       p.value                                                                                 AS ProductValue,
       p.name                                                                                  AS ProductName,
       (CASE WHEN io.IsSoTrx = 'N' THEN iol.MovementQty END)                                   AS PurchaseQty,
       (CASE WHEN io.movementtype = 'C-' AND bc.c_country_id != p_Country_id
             THEN iol.MovementQty END)                                                          AS ForeignSalesQty,
       (CASE WHEN io.movementtype = 'C-' THEN iol.MovementQty END)                              AS TotalSalesQty,
       uom.UOMSymbol,
       p.weight                                                                                AS Weight,
       -- ProductGroup (first matching for country)
       (SELECT pg.value
        FROM M_Product_PackageLicensing_ProductGroup pppg
                 JOIN M_PackageLicensing_ProductGroup pg
                      ON pg.M_PackageLicensing_ProductGroup_ID = pppg.M_PackageLicensing_ProductGroup_ID
                          AND pg.IsActive = 'Y'
                          AND pg.C_Country_ID = p_Country_id
        WHERE pppg.M_Product_ID = p.M_Product_ID
          AND pppg.IsActive = 'Y'
        ORDER BY pg.M_PackageLicensing_ProductGroup_ID
        LIMIT 1)                                                                               AS ProductGroup,
       -- MaterialType (comma-separated when multiple)
       (SELECT STRING_AGG(pg.Name, ', ' ORDER BY pg.Name)
        FROM M_Product_PackageLicensing_ProductGroup pppg
                 JOIN M_PackageLicensing_ProductGroup pg
                      ON pg.M_PackageLicensing_ProductGroup_ID = pppg.M_PackageLicensing_ProductGroup_ID
                          AND pg.IsActive = 'Y'
                          AND (p_Country_id IS NULL OR pg.C_Country_ID = p_Country_id)
        WHERE pppg.M_Product_ID = p.M_Product_ID
          AND pppg.IsActive = 'Y')                                                             AS MaterialType,
       -- SmallPackagingMaterial (resolved by country parameter)
       (SELECT mg.name
        FROM M_Product_SmallPackagingMaterial spm
                 JOIN M_PackageLicensing_MaterialGroup mg
                      ON mg.M_PackageLicensing_MaterialGroup_ID = spm.M_PackageLicensing_MaterialGroup_ID
                          AND mg.IsActive = 'Y'
                          AND mg.C_Country_ID = p_Country_id
        WHERE spm.M_Product_ID = p.M_Product_ID
          AND spm.IsActive = 'Y'
        LIMIT 1)                                                                               AS SmallPackagingMaterial,
       p.SmallPackagingWeight,
       -- OuterPackagingMaterial (resolved by country parameter)
       (SELECT mg.name
        FROM M_Product_OuterPackagingMaterial opm
                 JOIN M_PackageLicensing_MaterialGroup mg
                      ON mg.M_PackageLicensing_MaterialGroup_ID = opm.M_PackageLicensing_MaterialGroup_ID
                          AND mg.IsActive = 'Y'
                          AND mg.C_Country_ID = p_Country_id
        WHERE opm.M_Product_ID = p.M_Product_ID
          AND opm.IsActive = 'Y'
        LIMIT 1)                                                                               AS OuterPackagingMaterial,
       p.OuterPackagingWeight,
       -- Packaging instruction factor (CUs per TU). Fallback chain:
       -- 1) PI from the InOut line (only if Qty > 0 to skip "No Packing Item" placeholder)
       -- 2) Actual CU/TU ratio from the movement (MovementQty / QtyEnteredTU),
       --    but only when QtyEnteredTU > 1 (QtyEnteredTU=1 is often a default, not an actual TU count)
       -- 3) Product's default PI from masterdata
       COALESCE(
           NULLIF(piip_iol.Qty, 0),
           CASE WHEN iol.QtyEnteredTU > 1 AND iol.MovementQty != 0 THEN ABS(iol.MovementQty / iol.QtyEnteredTU) END,
           (SELECT piip.Qty
            FROM M_HU_PI_Item_Product piip
            WHERE piip.M_Product_ID = p.M_Product_ID
              AND piip.IsActive = 'Y'
              AND piip.Qty > 0
            ORDER BY piip.IsDefaultForProduct DESC, piip.Created DESC
            LIMIT 1)
       )                                                                                       AS PackagingInstructionFactor

FROM m_inout io
         INNER JOIN m_inoutline iol ON io.m_inout_id = iol.m_inout_id
         LEFT JOIN M_HU_PI_Item_Product piip_iol ON piip_iol.M_HU_PI_Item_Product_ID = iol.M_HU_PI_Item_Product_ID
         INNER JOIN C_UOM uom ON iol.c_uom_id = uom.c_uom_id
         INNER JOIN m_product p ON p.m_product_id = iol.m_product_id
         INNER JOIN m_warehouse wh ON wh.m_warehouse_id = io.m_warehouse_id
         INNER JOIN c_location l ON l.c_location_id = wh.c_location_id
         INNER JOIN c_country c ON c.c_country_id = l.c_country_id

    -- Shipment destination
         LEFT JOIN c_bpartner_location bpl ON bpl.c_bpartner_location_id = io.c_bpartner_location_id
         LEFT JOIN c_location bl ON bl.c_location_id = bpl.c_location_id
         LEFT JOIN c_country bc ON bc.c_country_id = bl.c_country_id


WHERE io.movementdate BETWEEN p_DateFrom AND p_DateTo
  AND io.DocStatus IN ('CO', 'CL')
  -- When IsIncludeAllProducts='N', only include products with packaging data for the given country
  AND (COALESCE(p_IsIncludeAllProducts, 'Y') = 'Y'
    OR EXISTS (SELECT 1
               FROM M_Product_PackageLicensing_ProductGroup pppg
                        JOIN M_PackageLicensing_ProductGroup pg
                             ON pg.M_PackageLicensing_ProductGroup_ID = pppg.M_PackageLicensing_ProductGroup_ID
                                 AND pg.IsActive = 'Y'
                                 AND pg.C_Country_ID = p_Country_id
               WHERE pppg.M_Product_ID = p.M_Product_ID
                 AND pppg.IsActive = 'Y'))
  AND (COALESCE(p_IsIncludeAllProducts, 'Y') = 'Y'
    OR EXISTS (SELECT 1
               FROM M_Product_SmallPackagingMaterial spm
                        JOIN M_PackageLicensing_MaterialGroup mg
                             ON mg.M_PackageLicensing_MaterialGroup_ID = spm.M_PackageLicensing_MaterialGroup_ID
                                 AND mg.IsActive = 'Y'
                                 AND mg.C_Country_ID = p_Country_id
               WHERE spm.M_Product_ID = p.M_Product_ID
                 AND spm.IsActive = 'Y')
    OR EXISTS (SELECT 1
               FROM M_Product_OuterPackagingMaterial opm
                        JOIN M_PackageLicensing_MaterialGroup mg
                             ON mg.M_PackageLicensing_MaterialGroup_ID = opm.M_PackageLicensing_MaterialGroup_ID
                                 AND mg.IsActive = 'Y'
                                 AND mg.C_Country_ID = p_Country_id
               WHERE opm.M_Product_ID = p.M_Product_ID
                 AND opm.IsActive = 'Y'))
ORDER BY io.movementdate, io.documentno, p.value
    ;

$$
    LANGUAGE sql STABLE
;
