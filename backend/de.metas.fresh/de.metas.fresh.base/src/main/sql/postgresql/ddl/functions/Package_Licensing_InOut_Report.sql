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
                DocumentNo             varchar,
                MovementDate           date,
                CountryCode            varchar,
                Product                varchar,
                MovementQty            numeric,
                UOMSymbol              varchar,
                Weight                 numeric,
                ProductGroup           varchar,
                SmallPackagingMaterial varchar,
                SmallPackagingWeight   numeric,
                OuterPackagingMaterial varchar,
                OuterPackagingWeight   numeric
            )

AS
$$

SELECT io.DocumentNo,
       io.MovementDate,
       c.CountryCode,
       p.value || ' ' || p.name                                                                AS Product,
       (CASE WHEN io.movementtype = 'C-' THEN iol.MovementQty * (-1) ELSE iol.MovementQty END) AS MovementQty,
       uom.UOMSymbol,
       p.weight                                                                                AS Weight,
       pp.value                                                                                AS ProductGroup,
       plmp1.name                                                                              AS SmallPackagingMaterial,
       p.SmallPackagingWeight,
       plmp2.name                                                                              AS OuterPackagingMaterial,
       p.OuterPackagingWeight

FROM m_inout io
         INNER JOIN m_inoutline iol ON io.m_inout_id = iol.m_inout_id
         INNER JOIN C_UOM uom ON iol.c_uom_id = uom.c_uom_id
         INNER JOIN m_product p ON p.m_product_id = iol.m_product_id
         INNER JOIN m_warehouse wh ON wh.m_warehouse_id = io.m_warehouse_id
         INNER JOIN c_location l ON l.c_location_id = wh.c_location_id
         INNER JOIN c_country c ON c.c_country_id = l.c_country_id

    -- Product group and packaging
         LEFT JOIN m_product_packagelicensing_productgroup ppp ON ppp.m_product_id = p.m_product_id
         LEFT JOIN M_PackageLicensing_ProductGroup pp ON pp.M_PackageLicensing_ProductGroup_id = ppp.M_PackageLicensing_ProductGroup_ID AND pp.c_country_id = l.c_country_id
         LEFT JOIN M_Product_SmallPackagingMaterial pspm ON pspm.m_product_id = p.m_product_id
         LEFT JOIN M_PackageLicensing_MaterialGroup plmp1 ON pspm.M_PackageLicensing_MaterialGroup_id = plmp1.m_packagelicensing_materialgroup_id AND plmp1.c_country_id = l.c_country_id
         LEFT JOIN M_Product_OuterPackagingMaterial popm ON popm.m_product_id = p.m_product_id
         LEFT JOIN M_PackageLicensing_MaterialGroup plmp2 ON popm.M_PackageLicensing_MaterialGroup_id = plmp2.m_packagelicensing_materialgroup_id AND plmp2.c_country_id = l.c_country_id

    -- Shipment destination
         LEFT JOIN c_bpartner_location bpl ON bpl.c_bpartner_location_id = io.c_bpartner_location_id
         LEFT JOIN c_location bl ON bl.c_location_id = bpl.c_location_id
         LEFT JOIN c_country bc ON bc.c_country_id = bl.c_country_id


WHERE (
    -- Case 1: Receipts into local warehouses
    (io.movementtype = 'V+' AND l.c_country_id = p_Country_id)
        OR

        -- Case 2: Shipments into foreign countries
    (io.movementtype = 'C-' AND bc.c_country_id != p_Country_id)
    )

  AND io.movementdate BETWEEN p_DateFrom AND p_DateTo
  AND (plmp1.name IS NOT NULL OR plmp2.name IS NOT NULL)
  AND pp.value IS NOT NULL
ORDER BY io.movementdate, io.documentno, p.value
    ;

$$
    LANGUAGE sql STABLE
;
