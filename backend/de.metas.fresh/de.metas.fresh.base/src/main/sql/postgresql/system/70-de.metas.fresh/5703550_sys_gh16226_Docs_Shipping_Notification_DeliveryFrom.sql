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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Shipping_Notification_DeliveryFrom(record_id numeric)
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Shipping_Notification_DeliveryFrom(record_id numeric)
    RETURNS TABLE
            (
                DeliveryFrom character varying
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
           ) AS DeliveryFrom

FROM M_Shipping_Notification sn

         INNER JOIN m_warehouse w ON sn.m_warehouse_id = w.m_warehouse_id
         INNER JOIN c_bpartner wbp ON w.c_bpartner_id = wbp.c_bpartner_id
         INNER JOIN c_bpartner_location wbpl ON wbp.c_bpartner_id = wbpl.c_bpartner_id
         INNER JOIN c_location wbploc ON wbploc.c_location_id = wbpl.c_location_id
         INNER JOIN C_Country wbpc ON wbploc.C_Country_ID = wbpc.C_Country_ID

WHERE sn.m_shipping_notification_id = $1
  AND sn.isActive = 'Y'
$$
;
