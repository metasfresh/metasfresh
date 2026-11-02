/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

DROP FUNCTION IF EXISTS report.getCustomerDeliveryPriceOverview(
    timestamp with time zone,
    timestamp with time zone,
    numeric
)
;

CREATE OR REPLACE FUNCTION report.getCustomerDeliveryPriceOverview(
    p_DeliveryDateFrom timestamp with time zone,
    p_DeliveryDateTo   timestamp with time zone,
    p_C_BPartner_ID    numeric
)
    RETURNS TABLE
            (
                Param_BPartnerValue     varchar,
                BPvalue                 varchar,
                ProductNo               varchar,
                Product                 varchar,
                Qty                     numeric,
                UOM                     varchar,
                BaseListPrice           numeric,
                BasePricePerUOM         varchar,
                BasePriceListVersion    varchar,
                SpecialPrice            numeric,
                SpecialPricePerUOM      varchar,
                SpecialPriceListVersion varchar
            )
AS
$$
WITH deliveries AS (
    -- One row per (business partner, delivery country, product). The sales price is driven by
    -- the delivery location's COUNTRY (country-specific price lists), so we group by that country
    -- instead of by raw location: locations in the same country collapse to one row (identical
    -- price), while a genuinely different-country price stays on its own row -- no arbitrary pick.
    -- The effective delivery location honors the schedule override (C_BP_Location_Override_ID);
    -- rep_c_bpartner_location_id is any location of that country (all resolve to the same price).
    SELECT sched.c_bpartner_id,
           loc.c_country_id,
           sched.m_product_id,
           SUM(sched.qtydelivered)         AS qty,
           MIN(eff.c_bpartner_location_id) AS rep_c_bpartner_location_id
    FROM m_shipmentschedule sched
             LEFT JOIN c_bpartner_location eff
                       ON eff.c_bpartner_location_id =
                          COALESCE(sched.c_bp_location_override_id, sched.c_bpartner_location_id)
             LEFT JOIN c_location loc ON loc.c_location_id = eff.c_location_id
    WHERE sched.processed = 'Y'
      AND sched.deliverydate >= p_DeliveryDateFrom
      -- DateTo bounds the window inclusively when given; when omitted the window runs until
      -- today (NOW()), preserving the old single-parameter "from date until today" behaviour
      AND sched.deliverydate < COALESCE(p_DeliveryDateTo, NOW()) + INTERVAL '1 day'
      AND (p_C_BPartner_ID IS NULL OR sched.c_bpartner_id = p_C_BPartner_ID)
    GROUP BY sched.c_bpartner_id, loc.c_country_id, sched.m_product_id
)
SELECT (SELECT value FROM C_BPartner WHERE C_BPartner_id = p_C_BPartner_ID) AS Param_BPartnerValue,
       bp.value           AS BPvalue,
       p.value            AS ProductNo,
       p.name             AS Product,
       d.qty              AS Qty,
       uom.name           AS UOM,
       pr.BasePriceStd    AS BaseListPrice,
       base_uom.name      AS BasePricePerUOM,
       pr.BasePLV         AS BasePriceListVersion,
       pr.SpecialPriceStd AS SpecialPrice,
       special_uom.name   AS SpecialPricePerUOM,
       pr.SpecialPLV      AS SpecialPriceListVersion
FROM deliveries d
         JOIN m_product p ON p.m_product_id = d.m_product_id
         JOIN c_uom uom ON uom.c_uom_id = p.c_uom_id
         JOIN c_bpartner bp ON bp.c_bpartner_id = d.c_bpartner_id
         -- anchor prices at the period end: when DateTo is given, the last instant of that day
         -- (matches the window upper bound in the CTE) -> a reproducible price for a closed period;
         -- when DateTo is omitted, NOW() -> "until today" prices, as the old single-date report did
         LEFT JOIN LATERAL report.getSalesPriceSpecialAndBase(
                 COALESCE(p_DeliveryDateTo + INTERVAL '1 day' - INTERVAL '1 microsecond', NOW()),
                 d.rep_c_bpartner_location_id, d.m_product_id) pr ON TRUE
         LEFT JOIN c_uom base_uom ON base_uom.c_uom_id = pr.BaseC_UOM_ID
         LEFT JOIN c_uom special_uom ON special_uom.c_uom_id = pr.SpecialC_UOM_ID
ORDER BY bp.value, p.value
$$
    LANGUAGE sql
    STABLE
;

COMMENT ON FUNCTION report.getCustomerDeliveryPriceOverview(timestamp with time zone, timestamp with time zone, numeric)
    IS 'Returns processed deliveries per (business partner, product) starting at p_DeliveryDateFrom. p_DeliveryDateTo bounds the end inclusively when given (prices anchored at that day''s end -> reproducible); when NULL the range runs until today and prices are taken as of now, matching the former single-parameter behaviour. One row per (partner, product): Qty is the summed delivered quantity, and each row carries both the base/standard list price and the customer-specific special price (empty when none), resolved via report.getSalesPriceSpecialAndBase.'
;
