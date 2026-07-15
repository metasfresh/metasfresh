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

SELECT db_drop_functions('*.sales_order_dynamics_json')
;

CREATE OR REPLACE FUNCTION sales_order_dynamics_json(p_order_id text)
    RETURNS TABLE
            (
                "orgCode"           text,
                "orderNumber"       numeric,
                "dateOrdered"       date,

                "datePromised"      date,
                "partnerIdentifier" text,
                "partnerValue"      text,
                "partnerName"       text,
                "currency"          text,
                "lines"             jsonb,
                "charges"           jsonb
            )
    LANGUAGE sql
    STABLE
AS
$$
WITH dynamics_system AS (SELECT externalsystem_id
                         FROM externalsystem
                         WHERE value = 'Dynamics365'
                         LIMIT 1),
     order_lines AS (SELECT ol.c_order_id,
                            COALESCE(
                                    JSONB_AGG(
                                            JSONB_BUILD_OBJECT(
                                                    'orderLineId', ol.c_orderline_id,
                                                    'orderLineNumber', ol.line,
                                                    'uom', ouom.x12de355,
                                                    'qty', ol.qtyentered,
                                                    'price', ol.priceactual,
                                                    'discount', ol.discount,
                                                    'productName', product.name,
                                                    'productDescription', product.description,
                                                    'productIdentifier', product.value
                                            ) ORDER BY ol.line
                                    ),
                                    '[]'::jsonb
                            ) AS json_data
                     FROM c_orderline ol
                              INNER JOIN m_product product ON product.m_product_id = ol.m_product_id
                              INNER JOIN c_uom ouom ON ouom.c_uom_id = ol.c_uom_id
                     WHERE product.producttype = 'I'
                       AND ol.c_order_id = p_order_id::numeric
                     GROUP BY ol.c_order_id),
     order_charges AS (SELECT ol.c_order_id,
                              COALESCE(
                                      JSONB_AGG(
                                              JSONB_BUILD_OBJECT(
                                                      'chargeIdentifier', ol.c_orderline_id,
                                                      'price', ol.priceactual
                                              ) ORDER BY ol.line
                                      ),
                                      '[]'::jsonb
                              ) AS json_data
                       FROM c_orderline ol
                                INNER JOIN m_product product ON product.m_product_id = ol.m_product_id
                       WHERE product.producttype != 'I'
                         AND ol.c_order_id = p_order_id::numeric
                       GROUP BY ol.c_order_id)
SELECT org.value,
       sorder.c_order_id,
       sorder.dateordered::date,
       sorder.datepromised::date,
       COALESCE(ext_ref.externalreference, partner.c_bpartner_id::text),
       partner.value,
       partner.name,
       c.iso_code::text,
       COALESCE(lines.json_data, '[]'::jsonb),
       COALESCE(charges.json_data, '[]'::jsonb)
FROM c_order sorder
         INNER JOIN ad_org org ON sorder.ad_org_id = org.ad_org_id
         INNER JOIN c_bpartner partner ON sorder.c_bpartner_id = partner.c_bpartner_id
         INNER JOIN c_currency c ON c.c_currency_id = sorder.c_currency_id
         LEFT JOIN LATERAL (
    SELECT externalreference
    FROM s_externalreference
    WHERE record_id = partner.c_bpartner_id
      AND type = 'BPartner'
      AND referenced_record_id = partner.c_bpartner_id
      AND externalsystem_id = (SELECT externalsystem_id FROM dynamics_system)
    LIMIT 1
    ) ext_ref ON TRUE
         LEFT JOIN order_lines lines ON lines.c_order_id = sorder.c_order_id
         LEFT JOIN order_charges charges ON charges.c_order_id = sorder.c_order_id
WHERE sorder.c_order_id = p_order_id::numeric
  AND sorder.issotrx = 'Y';
$$
;
