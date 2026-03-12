/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Shipping_Notification_Details(record_id   numeric,
                                                                                              ad_language character varying)
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Shipping_Notification_Details(record_id   numeric,
                                                                                      ad_language character varying)
    RETURNS TABLE
            (
                OrderNo       character varying,
                auction       character varying,
                line          numeric,
                p_value       character varying,
                product_value character varying,
                Name          character varying,
                Attributes    character varying,
                MovementQty   numeric,
                UOMSymbol     character varying,
                QtyPattern    character varying,
                priceactual   numeric
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT o.documentno                                           AS OrderNo,
       a.name                                                 AS auction,
       snl.line,
       COALESCE(NULLIF(bpp.ProductNo, ''), p.value)           AS p_value,
       p.value                                                AS product_value,
       COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name) AS Name,
       CASE
           WHEN LENGTH(att.Attributes) > 15
               THEN att.Attributes || E'\n'
               ELSE att.Attributes
       END                                                    AS Attributes,
       snl.MovementQty,
       COALESCE(uomt.UOMSymbol, uom.UOMSymbol)                AS UOMSymbol,
       CASE
           WHEN uom.StdPrecision = 0
               THEN '#,##0'
               ELSE SUBSTRING('#,##0.000' FROM 0 FOR 7 + uom.StdPrecision :: integer)
       END                                                    AS QtyPattern,
       ol.priceactual

FROM m_shipping_notificationline snl
         INNER JOIN M_Shipping_Notification sn ON snl.m_shipping_notification_id = sn.m_shipping_notification_id
         INNER JOIN C_Auction a ON sn.c_auction_id = a.c_auction_id
         INNER JOIN C_BPartner bp ON sn.C_BPartner_ID = bp.C_BPartner_ID
         INNER JOIN C_OrderLine ol ON snl.c_orderline_id = ol.c_orderline_id
         INNER JOIN C_Order o ON ol.c_order_id = o.c_order_id
         INNER JOIN C_UOM uom ON uom.C_UOM_ID = snl.C_UOM_ID
         LEFT OUTER JOIN C_UOM_Trl uomt
                         ON uomt.C_UOM_ID = uom.C_UOM_ID AND uomt.AD_Language = $2
    -- Product and its translation
         INNER JOIN M_Product p ON snl.M_Product_ID = p.M_Product_ID
         LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2

    -- Attributes
         LEFT OUTER JOIN (SELECT STRING_AGG(at.ai_value, ', '
                                 ORDER BY LENGTH(at.ai_value), at.ai_value)
                                 FILTER (WHERE at.at_value NOT IN ('HU_BestBeforeDate', 'Lot-Nummer'))
                                                                              AS Attributes,

                                 at.M_AttributeSetInstance_ID,
                                 STRING_AGG(REPLACE(at.ai_value, 'MHD: ', ''), ', ')
                                 FILTER (WHERE at.at_value LIKE 'HU_BestBeforeDate')
                                                                              AS best_before_date,
                                 STRING_AGG(ai_value, ', ')
                                 FILTER (WHERE at.at_value LIKE 'Lot-Nummer') AS lotno

                          FROM Report.fresh_Attributes at
                                   JOIN m_shipping_notificationline snla
                                        ON at.M_AttributeSetInstance_ID = snla.M_AttributeSetInstance_ID
                          WHERE at.IsPrintedInDocument = 'Y'
                            AND snla.m_shipping_notification_id = $1
                          GROUP BY at.M_AttributeSetInstance_ID) att ON snl.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID

         LEFT OUTER JOIN
     de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(snl.M_Product_ID, bp.C_BPartner_ID,
                                                                      att.M_AttributeSetInstance_ID) AS bpp ON TRUE
WHERE snl.m_shipping_notification_id = $1
  AND snl.isactive = 'Y'
ORDER BY line
    ;
$$
;


DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Shipping_Notification_Clearence_Notice(p_record_id numeric)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Shipping_Notification_Clearence_Notice(p_record_id numeric)
    RETURNS TABLE
            (
                ad_org_id                  numeric,
                ad_user_id                 numeric,
                m_shipping_notification_id numeric,
                c_bpartner_id              numeric,
                c_bpartner_location_id     numeric,
                ShipFrom_Partner_ID        numeric,
                documentno                 varchar,
                poreference                varchar,
                orgName                    varchar,
                orgAddress                 varchar,
                varcharorgvataxid          varchar,
                orgPhone                   varchar,
                orgPhone2                  varchar,
                orgfax                     varchar,
                orgEmail                   varchar,
                orgURL                     varchar,
                customerAddress            varchar,
                customerPhone              varchar,
                customerFax                varchar,
                customerEmail              varchar,
                DeliveryFrom               varchar,
                deliveryfromPhone          varchar,
                deliveryfromFax            varchar,
                deliveryfromEmail          varchar,
                locatorName                varchar
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT sn.ad_org_id,
       sn.ad_user_id,
       sn.m_shipping_notification_id,
       sn.c_bpartner_id,
       sn.c_bpartner_location_id,
       sn.ShipFrom_Partner_ID,
       sn.documentno,
       sn.poreference,
       t3.name            AS orgName,
       t3.address         AS orgAddress,
       t3.vataxid         AS orgvataxid,
       t3.phone           AS orgPhone,
       t3.phone2          AS orgPhone2,
       t3.fax             AS orgfax,
       t3.email           AS orgEmail,
       t3.url             AS orgURL,
       sn.bpartneraddress AS customerAddress,
       u.phone            AS customerPhone,
       u.fax              AS customerFax,
       u.email            AS customerEmail,
       sn.shipfromaddress AS DeliveryFrom,
       wu.phone           AS deliveryfromPhone,
       wu.fax             AS deliveryfromFax,
       wu.email           AS deliveryfromEmail,
       l.name             AS locatorName
FROM M_Shipping_Notification sn
         INNER JOIN C_BPartner bp ON sn.C_BPartner_ID = bp.C_BPartner_ID
         LEFT OUTER JOIN AD_user u ON sn.ad_user_id = u.ad_user_id
         INNER JOIN c_bpartner wbp ON sn.ShipFrom_Partner_ID = wbp.c_bpartner_id
         LEFT OUTER JOIN AD_user wu ON sn.shipfrom_user_id = wu.ad_user_id
         INNER JOIN m_locator l ON sn.m_locator_id = l.m_locator_id
         INNER JOIN de_metas_endcustomer_fresh_reports.Docs_Generics_Org_Report(NULL, 'Y', sn.ad_org_ID) AS t3 ON TRUE
WHERE sn.M_Shipping_Notification_ID = p_record_id
  AND sn.isActive = 'Y'
    ;
$$
;
