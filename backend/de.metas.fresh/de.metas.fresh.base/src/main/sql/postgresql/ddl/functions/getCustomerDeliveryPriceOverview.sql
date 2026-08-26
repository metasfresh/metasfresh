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
    -- One row per (business partner, product): the delivered quantity summed over the whole
    -- window, plus a representative location for pricing. Collapsing to (bp, product) here is
    -- what makes each article appear ONCE per partner -- a partner whose ship-to locations
    -- resolve to different (country-specific) price lists no longer yields one row per location.
    -- The representative is the lowest-id delivery location; a partner's locations resolve to
    -- different prices only when country-specific price lists apply.
    SELECT sched.c_bpartner_id,
           sched.m_product_id,
           SUM(sched.qtydelivered)           AS qty,
           MIN(sched.c_bpartner_location_id) AS rep_c_bpartner_location_id
    FROM m_shipmentschedule sched
    WHERE sched.processed = 'Y'
      AND sched.deliverydate >= p_DeliveryDateFrom
      AND sched.deliverydate < COALESCE(p_DeliveryDateTo, p_DeliveryDateFrom) + INTERVAL '1 day'
      AND (p_C_BPartner_ID IS NULL OR sched.c_bpartner_id = p_C_BPartner_ID)
    GROUP BY sched.c_bpartner_id, sched.m_product_id
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
         -- anchor prices at the last instant of the delivery period (matches the window upper bound
         -- in the CTE) so DateTo scopes the prices too: one reproducible price per article for a
         -- closed period, instead of the wall-clock NOW() that ignored DateTo
         LEFT JOIN LATERAL report.getSalesPriceSpecialAndBase(
                 COALESCE(p_DeliveryDateTo, p_DeliveryDateFrom) + INTERVAL '1 day' - INTERVAL '1 microsecond',
                 d.rep_c_bpartner_location_id, d.m_product_id) pr ON TRUE
         LEFT JOIN c_uom base_uom ON base_uom.c_uom_id = pr.BaseC_UOM_ID
         LEFT JOIN c_uom special_uom ON special_uom.c_uom_id = pr.SpecialC_UOM_ID
ORDER BY bp.value, p.value
$$
    LANGUAGE sql
    STABLE
;

COMMENT ON FUNCTION report.getCustomerDeliveryPriceOverview(timestamp with time zone, timestamp with time zone, numeric)
    IS 'Returns processed deliveries per product for a given business partner (or all if NULL) within the delivery-date range [p_DeliveryDateFrom, p_DeliveryDateTo] inclusive (p_DeliveryDateTo NULL falls back to p_DeliveryDateFrom, single day). Each row carries both the base/standard list price and the customer-specific special price (empty when none), resolved via report.getSalesPriceSpecialAndBase.'
;
