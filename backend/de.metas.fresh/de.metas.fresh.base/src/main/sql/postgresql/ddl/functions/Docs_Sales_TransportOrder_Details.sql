/*
 * #%L
 * de.metas.fresh.base
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

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_TransportOrder_Details(IN p_ShipperTransportation_ID numeric)
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_TransportOrder_Details(IN p_ShipperTransportation_ID numeric)
    RETURNS TABLE
            (
                name                varchar(100),
                address             varchar(360),
                deliverydatetimemax timestamp with time zone,
                isPallet            varchar(60),
                Qty                 bigint,
                notes               varchar(255)
            )
AS
$$
SELECT name,
       address,
       DeliveryDateTimeMax,
       isPallet,
       SUM(Qty)                AS Qty,
       STRING_AGG(note, E'\n') AS notes
FROM (SELECT name,
             address,
             DeliveryDateTimeMax,
             isPallet,
             CAST(COUNT(M_HU_ID) AS bigint) AS Qty,
             note
      FROM (SELECT bp.name,
                   bpl.address,
                   CASE WHEN hupm.name ILIKE '%pal%' THEN 'Y' ELSE 'N' END AS isPallet,
                   hu.M_HU_ID,
                   dd.DeliveryDateTimeMax,
                   note
            FROM M_ShippingPackage sp
                     INNER JOIN C_BPartner bp ON sp.C_BPartner_ID = bp.C_BPartner_ID
                     INNER JOIN C_BPartner_Location bpl ON sp.C_BPartner_Location_ID = bpl.C_BPartner_Location_ID
                     INNER JOIN M_Package pk ON pk.M_Package_ID = sp.M_Package_ID
                     LEFT OUTER JOIN M_Package_HU phu ON pk.M_Package_ID = phu.M_Package_ID
                     LEFT OUTER JOIN M_HU hu ON phu.M_HU_ID = hu.M_HU_ID
                     LEFT OUTER JOIN M_HU_PI_Item hupii ON hu.M_HU_PI_Version_ID = hupii.M_HU_PI_Version_ID AND itemtype = 'PM'
                     LEFT OUTER JOIN M_HU_PackingMaterial hupm ON hupii.M_HU_PackingMaterial_ID = hupm.M_HU_PackingMaterial_ID
                     LEFT OUTER JOIN M_Tour_Instance ti ON sp.M_ShipperTransportation_ID = ti.M_ShipperTransportation_ID
                     LEFT OUTER JOIN M_DeliveryDay dd ON ti.M_Tour_Instance_ID = dd.M_Tour_Instance_ID
                AND sp.C_BPartner_ID = dd.C_BPartner_ID AND sp.C_BPartner_Location_ID = dd.C_BPartner_Location_ID
            WHERE sp.M_ShipperTransportation_ID = p_ShipperTransportation_ID) foo
      GROUP BY name, address, DeliveryDateTimeMax, isPallet, note) bar
GROUP BY name, address, DeliveryDateTimeMax, isPallet
    ;
$$
    LANGUAGE sql STABLE
;

