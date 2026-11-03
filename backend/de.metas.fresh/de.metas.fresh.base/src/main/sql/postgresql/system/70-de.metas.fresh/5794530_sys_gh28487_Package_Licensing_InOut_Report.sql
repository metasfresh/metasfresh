/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

DROP FUNCTION IF EXISTS report.Package_Licensing_InOut_Report(p_DateFrom   timestamp with time zone,
                                                              p_DateTo     timestamp with time zone,
                                                              p_Country_id numeric)
;

CREATE OR REPLACE FUNCTION report.Package_Licensing_InOut_Report(p_DateFrom   timestamp with time zone,
                                                                 p_DateTo     timestamp with time zone,
                                                                 p_Country_id numeric)

    RETURNS TABLE
            (
                DocumentNo                 varchar,
                MovementDate               date,
                CountryCode                varchar,
                ProductValue               varchar,
                ProductName                varchar,
                MovementQty                numeric,
                PurchaseQty                numeric,
                ForeignSalesQty            numeric,
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
       (CASE WHEN io.movementtype = 'C-' THEN iol.MovementQty * (-1) ELSE iol.MovementQty END) AS MovementQty,
       (CASE WHEN io.IsSoTrx = 'N' THEN iol.MovementQty END)                                   AS PurchaseQty,
       (CASE WHEN io.movementtype = 'C-' AND bc.c_country_id != p_Country_id
             THEN iol.MovementQty END)                                                          AS ForeignSalesQty,
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
       -- Packaging instruction factor (default PI preferred)
       (SELECT piip.Qty
        FROM M_HU_PI_Item_Product piip
        WHERE piip.M_Product_ID = p.M_Product_ID
          AND piip.IsActive = 'Y'
        ORDER BY piip.IsDefaultForProduct DESC, piip.Created DESC
        LIMIT 1)                                                                               AS PackagingInstructionFactor

FROM m_inout io
         INNER JOIN m_inoutline iol ON io.m_inout_id = iol.m_inout_id
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
  -- Must have packaging data for the given country
  AND EXISTS (SELECT 1
              FROM M_Product_PackageLicensing_ProductGroup pppg
                       JOIN M_PackageLicensing_ProductGroup pg
                            ON pg.M_PackageLicensing_ProductGroup_ID = pppg.M_PackageLicensing_ProductGroup_ID
                                AND pg.IsActive = 'Y'
                                AND pg.C_Country_ID = p_Country_id
              WHERE pppg.M_Product_ID = p.M_Product_ID
                AND pppg.IsActive = 'Y')
  AND (EXISTS (SELECT 1
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
