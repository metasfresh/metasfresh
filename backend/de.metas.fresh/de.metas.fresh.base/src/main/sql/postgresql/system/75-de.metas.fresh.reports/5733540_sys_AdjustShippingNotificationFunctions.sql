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
       t3.name                                              AS orgName,
       (COALESCE(t3.address || E'\n', '') ||
        COALESCE(t3.postal, '') || ' ' || COALESCE(t3.city, '')) AS orgAddress,
       t3.vataxid                                           AS orgvataxid,
       t3.phone                                             AS orgPhone,
       t3.phone2                                            AS orgPhone2,
       t3.fax                                               AS orgfax,
       t3.email                                             AS orgEmail,
       t3.url                                               AS orgURL,
       sn.bpartneraddress                                   AS customerAddress,
       u.phone                                              AS customerPhone,
       u.fax                                                AS customerFax,
       u.email                                              AS customerEmail,
       sn.shipfromaddress                                   AS DeliveryFrom,
       wu.phone                                             AS deliveryfromPhone,
       wu.fax                                               AS deliveryfromFax,
       wu.email                                             AS deliveryfromEmail,
       COALESCE(l.name, l.value)                            AS locatorName
FROM M_Shipping_Notification sn
         INNER JOIN C_BPartner bp ON sn.C_BPartner_ID = bp.C_BPartner_ID
         LEFT OUTER JOIN AD_user u ON COALESCE(sn.ad_user_id, (SELECT ad_user_id FROM AD_user u1 WHERE u1.c_bpartner_id = sn.C_BPartner_ID ORDER BY isdefaultcontact DESC LIMIT 1)) = u.ad_user_id
         INNER JOIN c_bpartner wbp ON sn.ShipFrom_Partner_ID = wbp.c_bpartner_id
         LEFT OUTER JOIN AD_user wu ON COALESCE(sn.shipfrom_user_id, (SELECT ad_user_id FROM AD_user u2 WHERE u2.c_bpartner_id = sn.ShipFrom_Partner_ID ORDER BY isdefaultcontact DESC LIMIT 1)) = wu.ad_user_id
         INNER JOIN m_locator l ON sn.m_locator_id = l.m_locator_id
         INNER JOIN de_metas_endcustomer_fresh_reports.Docs_Generics_Org_Report(NULL, 'Y', sn.ad_org_ID) AS t3 ON TRUE
WHERE sn.M_Shipping_Notification_ID = p_record_id
  AND sn.isActive = 'Y'
    ;
$$
;


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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Shipping_Notification_Details(p_record_id   numeric,
                                                                                              p_ad_language character varying)
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Shipping_Notification_Details(p_record_id   numeric,
                                                                                      p_ad_language character varying)
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
         LEFT OUTER JOIN C_Auction a ON sn.c_auction_id = a.c_auction_id
         INNER JOIN C_BPartner bp ON sn.C_BPartner_ID = bp.C_BPartner_ID
         INNER JOIN C_OrderLine ol ON snl.c_orderline_id = ol.c_orderline_id
         INNER JOIN C_Order o ON ol.c_order_id = o.c_order_id
         INNER JOIN C_UOM uom ON uom.C_UOM_ID = snl.C_UOM_ID
         LEFT OUTER JOIN C_UOM_Trl uomt
                         ON uomt.C_UOM_ID = uom.C_UOM_ID AND uomt.AD_Language = p_ad_language
    -- Product and its translation
         INNER JOIN M_Product p ON snl.M_Product_ID = p.M_Product_ID
         LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_ad_language

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
                            AND snla.m_shipping_notification_id = p_record_id
                          GROUP BY at.M_AttributeSetInstance_ID) att ON snl.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID

         LEFT OUTER JOIN
     de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(snl.M_Product_ID, bp.C_BPartner_ID,
                                                                      att.M_AttributeSetInstance_ID) AS bpp ON TRUE
WHERE snl.m_shipping_notification_id = p_record_id
ORDER BY line
    ;
$$
;



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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Shipping_Notification_Description(p_record_id   numeric,
                                                                                                  p_ad_language character varying)
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Shipping_Notification_Description(p_record_id   numeric,
                                                                                          p_ad_language character varying)
    RETURNS TABLE
            (
                Document_name         character varying,
                documentno            character varying,
                sales_order           character varying,
                poreference           character varying,
                physicalclearancedate timestamp WITHOUT TIME ZONE,
                Harvesting_year       character varying,
                description           character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT

       dt_trl.name     AS Document_name,
       sn.documentno,
       o.documentno    AS sales_order,
       sn.poreference,
       sn.physicalclearancedate,
       year.fiscalyear AS Harvesting_year,
       sn.description

FROM M_Shipping_Notification sn
         INNER JOIN c_order o ON sn.c_order_id = o.c_order_id
         INNER JOIN c_year year ON o.harvesting_year_id = year.c_year_id
         INNER JOIN C_DocType dt ON sn.c_doctype_id = dt.c_doctype_id
         LEFT OUTER JOIN C_DocType_trl dt_trl ON dt.c_doctype_id = dt_trl.c_doctype_id
    AND dt_trl.ad_language = p_ad_language
WHERE sn.m_shipping_notification_id = p_record_id
$$
;


