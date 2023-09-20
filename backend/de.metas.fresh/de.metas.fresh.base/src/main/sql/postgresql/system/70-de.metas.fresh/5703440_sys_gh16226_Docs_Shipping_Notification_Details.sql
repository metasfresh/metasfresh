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
                line        numeric,
                p_value     character varying,
                Name        character varying,
                Attributes  character varying,
                MovementQty numeric,
                UOMSymbol   character varying,
                QtyPattern  character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT snl.line,
       COALESCE(NULLIF(bpp.ProductNo, ''), p.value)           AS p_value,
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
       END                                                    AS QtyPattern

FROM m_shipping_notificationline snl
         INNER JOIN M_Shipping_Notification sn ON snl.m_shipping_notification_id = sn.m_shipping_notification_id
         INNER JOIN C_BPartner bp ON sn.C_BPartner_ID = bp.C_BPartner_ID
         INNER JOIN C_UOM uom ON uom.C_UOM_ID = snl.C_UOM_ID
         LEFT OUTER JOIN C_UOM_Trl uomt
                         ON uomt.C_UOM_ID = uom.C_UOM_ID AND uomt.AD_Language = $2
    -- Product and its translation
         LEFT OUTER JOIN M_Product p ON snl.M_Product_ID = p.M_Product_ID
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
