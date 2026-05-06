/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.get_Product_Price(numeric,
                                                                             numeric,
                                                                             date)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.get_Product_Price(IN p_productId   numeric,
                                                                                IN p_priceListId numeric,
                                                                                IN p_onDate      date)
    RETURNS table
            (
                priceStd      numeric,
                c_uom_id      numeric,
                c_currency_id numeric
            )
    LANGUAGE plpgsql
AS
$BODY$
DECLARE
    result           record;
    priceList        record;
    priceListVersion record;
BEGIN
    --get m_pricelist
    SELECT * FROM m_pricelist WHERE M_PriceList_ID = p_priceListId INTO priceList;
    --get m_pricelist_version
    SELECT *
    FROM M_PriceList_Version
    WHERE M_PriceList_ID = p_priceListId
      AND TRUNC(ValidFrom, 'DD') <= TRUNC(p_onDate::TIMESTAMP, 'DD')
      AND IsActive = 'Y'
    ORDER BY ValidFrom DESC NULLS FIRST
    INTO priceListVersion;

    SELECT *
    FROM M_ProductPrice
    WHERE IsActive = 'Y'
      AND M_PriceList_Version_ID = priceListVersion.m_pricelist_version_id
      AND M_Product_ID = p_productId
      AND IsInvalidPrice <> 'Y'
      AND IsAttributeDependant = 'N'
      AND M_HU_PI_Item_Product_ID IS NULL
    INTO result;

    IF (result.m_productprice_id IS NOT NULL) AND (pricelist.m_pricelist_id IS NOT NULL) THEN
        RETURN QUERY (SELECT result.priceStd, result.c_uom_id, priceList.c_currency_id);
        -- calculated from current pricelist
    ELSIF pricelist.basepricelist_id IS NOT NULL THEN
        RETURN QUERY SELECT * FROM de_metas_endcustomer_fresh_reports.get_Product_Price(p_productId, pricelist.basepricelist_id, p_onDate);
        -- try to retrieve from basepricelist
    END IF;
END;
$BODY$
;

COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.get_Product_Price(numeric, numeric, date) IS 'Retrieve the current price of the given product in the given pricelist at the given date'
;


DROP FUNCTION IF EXISTS report.getCurrentSalesPrice(
    timestamp with time zone,
    numeric,
    numeric
);

CREATE OR REPLACE FUNCTION report.getCurrentSalesPrice(
    p_Date                    timestamp with time zone,
    p_C_BPartner_Location_ID  numeric,
    p_M_Product_ID            numeric
)
    RETURNS TABLE
            (
                PriceStd       numeric,
                C_UOM_ID       numeric,
                C_Currency_ID  numeric,
                M_PriceList_ID numeric
            )
AS
$$
WITH
    bpl AS (
        SELECT bpl.C_BPartner_ID,
               loc.C_Country_ID
        FROM C_BPartner_Location bpl
                 JOIN C_Location loc ON loc.C_Location_ID = bpl.C_Location_ID
        WHERE bpl.C_BPartner_Location_ID = p_C_BPartner_Location_ID
    ),
    pricing_system AS (
        SELECT COALESCE(bp.M_PricingSystem_ID, bpg.M_PricingSystem_ID) AS M_PricingSystem_ID,
               bpl.C_Country_ID
        FROM bpl
                 JOIN C_BPartner bp  ON bp.C_BPartner_ID = bpl.C_BPartner_ID
                 LEFT JOIN C_BP_Group bpg ON bpg.C_BP_Group_ID = bp.C_BP_Group_ID
    ),
    -- Pick the SO price list matching the pricing system; country-specific wins over generic
    pricelist_root AS (
        SELECT pl.M_PriceList_ID
        FROM M_PriceList pl, pricing_system ps
        WHERE pl.M_PricingSystem_ID = ps.M_PricingSystem_ID
          AND pl.IsSOPriceList = 'Y'
          AND pl.IsActive = 'Y'
          AND (pl.C_Country_ID = ps.C_Country_ID OR pl.C_Country_ID IS NULL)
        ORDER BY (pl.C_Country_ID IS NOT NULL) DESC,
                 pl.M_PriceList_ID
        LIMIT 1
    )
SELECT gpp.priceStd,
       gpp.c_uom_id,
       gpp.c_currency_id,
       r.M_PriceList_ID
FROM pricelist_root r
         CROSS JOIN LATERAL de_metas_endcustomer_fresh_reports.get_Product_Price(
        p_M_Product_ID,
        r.M_PriceList_ID,
        p_Date::date
                            ) gpp
$$
    LANGUAGE sql
    STABLE;


/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2022 metas GmbH
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
DROP FUNCTION IF EXISTS report.getCustomerDeliveryPriceOverview(
    timestamp with time zone,
    numeric
)
;

CREATE OR REPLACE FUNCTION report.getCustomerDeliveryPriceOverview(
    p_DeliveryDateFrom timestamp with time zone,
    p_C_BPartner_ID    numeric
)
    RETURNS TABLE
            (
                Param_BPartnerValue varchar,
                BPvalue             varchar,
                ProductNo           varchar,
                Product             varchar,
                Qty                 numeric,
                UOM                 varchar,
                Price               numeric,
                PricePerUOM         varchar,
                PriceListVersion    varchar,
                IsFromBasePriceList char(1)
            )
AS
$$
SELECT (SELECT value FROM C_BPartner WHERE C_BPartner_id = p_C_BPartner_ID) AS Param_BPartnerValue,
       bp.value                                                             AS BPvalue,
       p.value                                                              AS ProductNo,
       p.name                                                               AS Product,
       SUM(sched.qtydelivered)                                              AS Qty,
       uom.name                                                             AS UOM,
       pp.pricestd                                                          AS Price,
       pp_uom.name                                                          AS PricePerUOM,
       plv.name                                                             AS PriceListVersion,
       pp.isfrombasepricelist                                               AS IsFromBasePriceList
FROM m_shipmentschedule sched
         JOIN m_product p ON p.m_product_id = sched.m_product_id
         JOIN c_uom uom ON uom.c_uom_id = p.c_uom_id
         JOIN c_bpartner bp ON bp.c_bpartner_id = sched.c_bpartner_id
         LEFT JOIN LATERAL report.getCurrentSalesPrice(NOW(), sched.c_bpartner_location_id, sched.m_product_id) pp ON TRUE
         LEFT JOIN c_uom pp_uom ON pp_uom.c_uom_id = pp.c_uom_id
         LEFT JOIN m_pricelist_version plv ON plv.m_pricelist_version_id = pp.m_pricelist_version_id
WHERE sched.processed = 'Y'
  AND sched.deliverydate >= p_DeliveryDateFrom
  AND (p_C_BPartner_ID IS NULL OR bp.C_BPartner_ID = p_C_BPartner_ID)
GROUP BY p.value, p.name, uom.name,
         pp.pricestd, pp.c_currency_id,
         pp.c_uom_id, pp.m_pricelist_id, pp.isfrombasepricelist,
         plv.name, pp_uom.name, bp.value
ORDER BY bp.value, p.value
$$
    LANGUAGE sql
    STABLE
;

COMMENT ON FUNCTION report.getCustomerDeliveryPriceOverview(timestamp with time zone, numeric)
    IS 'Returns processed deliveries per product for a given business partner (or all if NULL) from a given date, enriched with the current sales price resolved via report.getCurrentSalesPrice.'
;