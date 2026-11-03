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