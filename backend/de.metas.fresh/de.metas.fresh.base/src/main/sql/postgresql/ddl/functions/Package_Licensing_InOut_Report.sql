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
       pp.value                                                                                AS ProductGroup,
       -- NEW: MaterialType (replaces ProductGroup; comma-separated when multiple)
       (SELECT STRING_AGG(pp.Name, ', ' ORDER BY pp.Name)
        FROM M_Product_PackageLicensing_ProductGroup pppg
                 JOIN M_PackageLicensing_ProductGroup pp
                      ON pp.M_PackageLicensing_ProductGroup_ID = pppg.M_PackageLicensing_ProductGroup_ID
                          AND pp.IsActive = 'Y'
                          AND (p_Country_id IS NULL OR pp.C_Country_ID = p_Country_id)
        WHERE pppg.M_Product_ID = p.M_Product_ID
          AND pppg.IsActive = 'Y')                                                             AS MaterialType,
       plmp1.name                                                                              AS SmallPackagingMaterial,
       p.SmallPackagingWeight,
       plmp2.name                                                                              AS OuterPackagingMaterial,
       p.OuterPackagingWeight,
       -- NEW: Packaging instruction factor (default PI preferred)
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

    -- Product group and packaging
         LEFT JOIN m_product_packagelicensing_productgroup ppp ON ppp.m_product_id = p.m_product_id
         LEFT JOIN M_PackageLicensing_ProductGroup pp ON pp.M_PackageLicensing_ProductGroup_id = ppp.M_PackageLicensing_ProductGroup_ID AND pp.c_country_id = p_Country_id
         LEFT JOIN M_Product_SmallPackagingMaterial pspm ON pspm.m_product_id = p.m_product_id
         LEFT JOIN M_PackageLicensing_MaterialGroup plmp1 ON pspm.M_PackageLicensing_MaterialGroup_id = plmp1.m_packagelicensing_materialgroup_id AND plmp1.c_country_id = p_Country_id
         LEFT JOIN M_Product_OuterPackagingMaterial popm ON popm.m_product_id = p.m_product_id
         LEFT JOIN M_PackageLicensing_MaterialGroup plmp2 ON popm.M_PackageLicensing_MaterialGroup_id = plmp2.m_packagelicensing_materialgroup_id AND plmp2.c_country_id = p_Country_id

    -- Shipment destination
         LEFT JOIN c_bpartner_location bpl ON bpl.c_bpartner_location_id = io.c_bpartner_location_id
         LEFT JOIN c_location bl ON bl.c_location_id = bpl.c_location_id
         LEFT JOIN c_country bc ON bc.c_country_id = bl.c_country_id


WHERE io.movementdate BETWEEN p_DateFrom AND p_DateTo
  AND io.DocStatus IN ('CO', 'CL')
  AND (plmp1.name IS NOT NULL OR plmp2.name IS NOT NULL)
  AND pp.value IS NOT NULL
ORDER BY io.movementdate, io.documentno, p.value
    ;

$$
    LANGUAGE sql STABLE
;
