/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
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

SELECT db_drop_functions('*.purchase_order_dynamics_json')
;

CREATE FUNCTION purchase_order_dynamics_json(p_order_id text)
    RETURNS TABLE
            (
                "orgCode"           text,
                "orderDocumentNo"   text,
                "orderNumber"       numeric,
                "currencyCode"      text,
                "partnerIdentifier" text,
                "partnerValue"      text,
                "partnerName"       text,
                "dropShip"          jsonb,
                "lines"             jsonb
            )
    STABLE
    LANGUAGE sql
AS
$$
WITH dynamics_system AS (SELECT externalsystem_id
                         FROM externalsystem
                         WHERE value = 'Dynamics365'
                         LIMIT 1)
SELECT org.value,
       porder.poreference,
       porder.c_order_id,
       c.iso_code::text,
       COALESCE(ext_ref.externalreference, partner.c_bpartner_id::text),
       partner.value,
       partner.name,
       dropShipBPartners.json_data::jsonb,
       lines.json_data::jsonb
FROM c_order porder
         INNER JOIN ad_org org ON porder.ad_org_id = org.ad_org_id
         INNER JOIN c_bpartner partner ON porder.c_bpartner_id = partner.c_bpartner_id
         INNER JOIN c_currency c ON c.C_Currency_ID = porder.C_Currency_ID
         LEFT JOIN LATERAL (
    SELECT externalreference
    FROM s_externalreference
    WHERE record_id = porder.c_bpartner_id
      AND type = 'BPartner'
      AND referenced_record_id = porder.c_bpartner_id
      AND externalsystem_id = (SELECT externalsystem_id FROM dynamics_system)
    LIMIT 1
    ) ext_ref ON TRUE
         LEFT JOIN (SELECT ol.c_order_id,
                           JSON_AGG(JSON_BUILD_OBJECT(
                                            'orderLineId', ol.c_orderline_id,
                                            'orderLineNumber', ol.line,
                                            'uom', ouom.x12de355,
                                            'qty', ol.qtyentered,
                                            'price', ol.priceactual,
                                            'discount', ol.discount,
                                            'productName', product.name,
                                            'productDescription', product.description,
                                            'productIdentifier', product.value,
                                            'dateRequested', COALESCE(ol.datepromised, p_order_inner.datepromised)::date
                                    ) ORDER BY ol.line) AS json_data
                    FROM c_orderline ol
                             INNER JOIN m_product product ON product.m_product_id = ol.m_product_id
                             INNER JOIN c_uom ouom ON ouom.c_uom_id = ol.c_uom_id
                             INNER JOIN c_order p_order_inner ON ol.c_order_id = p_order_inner.c_order_id
                    GROUP BY ol.c_order_id) lines ON lines.c_order_id = porder.C_Order_ID
         LEFT JOIN (SELECT dropShipBPartner.c_bpartner_id,
                           dropShipBPartnerLocation.c_bpartner_location_id,
                           JSON_BUILD_OBJECT(
                                   'partnerID', dropShipBPartner.c_bpartner_id,
                                   'partnerValue', dropShipBPartner.value,
                                   'partnerName', dropShipBPartner.name,
                                   'address1', dropShipLocation.address1,
                                   'address2', dropShipLocation.address2,
                                   'address3', dropShipLocation.address3,
                                   'address4', dropShipLocation.address4,
                                   'postal', dropShipLocation.postal,
                                   'city', dropShipLocation.city,
                                   'country', country.countrycode_3digit
                           ) AS json_data
                    FROM c_bpartner dropShipBPartner
                             INNER JOIN c_bpartner_location dropShipBPartnerLocation
                                        ON dropShipBPartner.c_bpartner_id = dropShipBPartnerLocation.c_bpartner_id
                             LEFT JOIN c_location dropShipLocation
                                       ON dropShipBPartnerLocation.c_location_id = dropShipLocation.c_location_id
                             LEFT JOIN c_country country
                                       ON dropShipLocation.c_country_id = country.c_country_id) dropShipBPartners
                   ON dropShipBPartners.c_bpartner_location_id =
                      CASE
                          WHEN porder.IsDropShip = 'Y'
                              THEN COALESCE(porder.dropship_location_id, porder.c_bpartner_location_id)
                              ELSE (SELECT wh.c_bpartner_location_id FROM m_warehouse wh WHERE wh.m_warehouse_id = porder.m_warehouse_id)
                      END
WHERE porder.c_order_id = p_order_id::numeric
  AND porder.issotrx = 'N';
$$
;
