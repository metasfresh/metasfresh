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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Shipping_Notification_Description(record_id   numeric,
                                                                                                  ad_language character varying)
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Shipping_Notification_Description(record_id   numeric,
                                                                                          ad_language character varying)
    RETURNS TABLE
            (
                DeliveryFrom          character varying,
                DeliveryTo            character varying,
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
SELECT TRIM(COALESCE(wbp.name || E'\n', '') ||
            COALESCE(wbploc.address1 || E'\n', '') ||
            COALESCE(wbploc.postal || E'\n', '') ||
            COALESCE(wbploc.city || E'\n', '') ||
            COALESCE(wbpc.name, '')
           )           AS DeliveryFrom,
       TRIM(COALESCE(snbp.name || E'\n', '') ||
            COALESCE(snbploc.address1 || E'\n', '') ||
            COALESCE(snbploc.postal || E'\n', '') ||
            COALESCE(snbploc.city || E'\n', '') ||
            COALESCE(snbpc.name, '')
           )           AS DeliveryTo,
       dt_trl.name     AS Document_name,
       sn.documentno,
       o.documentno    AS sales_order,
       sn.poreference,
       sn.physicalclearancedate,
       year.fiscalyear AS Harvesting_year,
       sn.description

FROM M_Shipping_Notification sn
         -- DeliveryFrom (warehouse partner and location)
         INNER JOIN m_warehouse w ON sn.m_warehouse_id = w.m_warehouse_id
         INNER JOIN c_bpartner wbp ON w.c_bpartner_id = wbp.c_bpartner_id
         INNER JOIN c_bpartner_location wbpl ON wbp.c_bpartner_id = wbpl.c_bpartner_id
         INNER JOIN c_location wbploc ON wbploc.c_location_id = wbpl.c_location_id
         INNER JOIN C_Country wbpc ON wbploc.C_Country_ID = wbpc.C_Country_ID

    -- DeliveryTo -> BPartner Address

         INNER JOIN c_bpartner snbp ON sn.c_bpartner_id = snbp.c_bpartner_id
         INNER JOIN c_bpartner_location snbpl ON snbpl.c_bpartner_id = snbp.c_bpartner_id
         INNER JOIN c_location snbploc ON snbploc.c_location_id = snbpl.c_location_id
         INNER JOIN C_Country snbpc ON snbploc.C_Country_ID = snbpc.C_Country_ID

         INNER JOIN c_order o ON sn.c_order_id = o.c_order_id
         INNER JOIN c_year year ON o.harvesting_year_id = year.c_year_id

         INNER JOIN C_DocType dt ON sn.c_doctype_id = dt.c_doctype_id
         LEFT OUTER JOIN C_DocType_trl dt_trl ON dt.c_doctype_id = dt_trl.c_doctype_id
    AND dt_trl.ad_language = $2
WHERE sn.m_shipping_notification_id = $1
  AND sn.isActive = 'Y'
$$
;
