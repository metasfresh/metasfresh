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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Shipping_Notification_Root(record_id   numeric,
                                                                                           ad_language character varying)
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Shipping_Notification_Root(record_id   numeric,
                                                                                   ad_language character varying)
    RETURNS TABLE
            (
                ad_user_id                           numeric(10),
                ad_org_id                            numeric(10),
                m_shipping_notification_id           numeric(10),
                docstatus                            char(2),
                c_bpartner_id                        numeric(10),
                c_bpartner_location_id               numeric(10),
                printname                            varchar(60),
                ad_language                          text,
                ishidepackingmaterialinshipmentprint char,
                issourcesupplycert                   char
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT sn.ad_user_id,
       sn.ad_org_id,
       sn.m_shipping_notification_id,
       sn.docstatus,
       sn.c_bpartner_id,
       sn.c_bpartner_location_id,
       CASE
           WHEN sn.DocStatus = 'DR'
               THEN dt.printname
               ELSE COALESCE(dtt.printname, dt.printname)
       END                                                             AS printname,
       CASE WHEN sn.DocStatus IN ('DR', 'IP') THEN 'de_CH' ELSE $2 END AS AD_Language,
       bp.isHidePackingMaterialInShipmentPrint,
       bp.issourcesupplycert
FROM M_Shipping_Notification sn
         JOIN C_BPartner bp ON sn.C_BPartner_ID = bp.C_BPartner_ID
         INNER JOIN C_DocType dt ON sn.C_DocType_ID = dt.C_DocType_ID
         LEFT OUTER JOIN C_DocType_Trl dtt ON dt.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'

WHERE sn.M_Shipping_Notification_ID = $1
  AND sn.isActive = 'Y'
    ;
$$
;
