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
