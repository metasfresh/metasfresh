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

-- Task 3 (partial): add p_DeliveryDateTo (nullable; NULL falls back to p_DeliveryDateFrom -> single day)
-- and bound the delivery window. Price columns unchanged for now (still via report.getCurrentSalesPrice).

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
SELECT (SELECT value FROM C_BPartner WHERE C_BPartner_id = p_C_BPartner_ID) AS Param_BPartnerValue,
       bp.value                                                             AS BPvalue,
       p.value                                                              AS ProductNo,
       p.name                                                               AS Product,
       SUM(sched.qtydelivered)                                              AS Qty,
       uom.name                                                             AS UOM,
       pr.BasePriceStd                                                      AS BaseListPrice,
       base_uom.name                                                        AS BasePricePerUOM,
       pr.BasePLV                                                           AS BasePriceListVersion,
       pr.SpecialPriceStd                                                   AS SpecialPrice,
       special_uom.name                                                     AS SpecialPricePerUOM,
       pr.SpecialPLV                                                        AS SpecialPriceListVersion
FROM m_shipmentschedule sched
         JOIN m_product p ON p.m_product_id = sched.m_product_id
         JOIN c_uom uom ON uom.c_uom_id = p.c_uom_id
         JOIN c_bpartner bp ON bp.c_bpartner_id = sched.c_bpartner_id
         LEFT JOIN LATERAL report.getSalesPriceSpecialAndBase(NOW(), sched.c_bpartner_location_id, sched.m_product_id) pr ON TRUE
         LEFT JOIN c_uom base_uom ON base_uom.c_uom_id = pr.BaseC_UOM_ID
         LEFT JOIN c_uom special_uom ON special_uom.c_uom_id = pr.SpecialC_UOM_ID
WHERE sched.processed = 'Y'
  AND sched.deliverydate >= p_DeliveryDateFrom
  AND sched.deliverydate < COALESCE(p_DeliveryDateTo, p_DeliveryDateFrom) + INTERVAL '1 day'
  AND (p_C_BPartner_ID IS NULL OR bp.C_BPartner_ID = p_C_BPartner_ID)
GROUP BY p.value, p.name, uom.name,
         pr.BasePriceStd, base_uom.name, pr.BasePLV,
         pr.SpecialPriceStd, special_uom.name, pr.SpecialPLV,
         bp.value
ORDER BY bp.value, p.value
$$
    LANGUAGE sql
    STABLE
;

COMMENT ON FUNCTION report.getCustomerDeliveryPriceOverview(timestamp with time zone, timestamp with time zone, numeric)
    IS 'Returns processed deliveries per product for a given business partner (or all if NULL) within the delivery-date range [p_DeliveryDateFrom, p_DeliveryDateTo] inclusive (p_DeliveryDateTo NULL falls back to p_DeliveryDateFrom, single day). Each row carries both the base/standard list price and the customer-specific special price (empty when none), resolved via report.getSalesPriceSpecialAndBase.'
;
