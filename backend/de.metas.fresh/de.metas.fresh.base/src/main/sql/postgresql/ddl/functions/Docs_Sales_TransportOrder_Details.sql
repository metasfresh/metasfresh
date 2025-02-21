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


DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_TransportOrder_Details (IN p_M_ShipperTransportation_ID numeric)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_TransportOrder_Details(IN p_M_ShipperTransportation_ID numeric)
    RETURNS TABLE
            (
                name                text,
                address             text,
                DeliveryDateTimeMax Timestamp WITH TIME ZONE,
                isPallet            char(1),
                QtyTU               numeric,
                QtyLU               numeric,
                notes               text
            )
AS
$$
    SELECT name,
       address,
       DeliveryDateTimeMax,
       isPallet,
       QtyTU,
       QtyLU,
       STRING_AGG(note, E'\n') AS notes
FROM (SELECT name,
             address,
             DeliveryDateTimeMax,
             isPallet,
             QtyTU,
             QtyLU,
             note
      FROM (SELECT bp.name,
                   bpl.address,
                   CASE WHEN hupm.name ILIKE '%pal%' THEN 'Y' ELSE 'N' END     AS isPallet,
                   hu.M_HU_ID,
                   dd.DeliveryDateTimeMax,
                   (CASE WHEN TU IS NULL AND TU > 0 THEN TU ELSE sp.QtyTU END) AS QtyTU,
                   (CASE WHEN LU IS NULL AND LU > 0 THEN LU ELSE sp.QtyLU END) AS QtyLU,
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
                     LEFT OUTER JOIN (SELECT M_HU_ID,
                                             MAX(CASE WHEN type = 'LU' THEN cnt ELSE 0 END) AS LU,
                                             MAX(CASE WHEN type = 'TU' THEN cnt ELSE 0 END) AS TU

                                      FROM (SELECT M_HU_ID,
                                                   TYPE,
                                                   COUNT(TYPE) AS cnt
                                            FROM (SELECT p_hu.M_HU_ID,
                                                         (CASE
                                                              WHEN p_hu.M_HU_Item_Parent_ID IS NULL AND p_hu_i.itemtype = 'HA' THEN 'TU'
                                                              WHEN p_hu_v.hu_unittype IS NULL                                  THEN 'LU'
                                                                                                                               ELSE p_hu_v.hu_unittype
                                                          END) AS TYPE
                                                  FROM M_HU p_hu
                                                           LEFT OUTER JOIN M_HU_Item p_hu_i ON p_hu_i.M_HU_ID = p_hu.M_HU_ID
                                                           LEFT OUTER JOIN M_HU_PI_Version p_hu_v ON p_hu.m_hu_id = p_hu_v.m_hu_pi_id)
                                                     AS d
                                            GROUP BY M_HU_ID, TYPE) AS fd
                                      GROUP BY m_hu_ID) AS hu_qty
                                     ON hu.m_hu_id = hu_qty.m_hu_id
            WHERE sp.M_ShipperTransportation_ID = p_M_ShipperTransportation_ID) foo) bar
GROUP BY name, address, DeliveryDateTimeMax, isPallet, QtyTU, QtyLU

$$
    LANGUAGE sql
    STABLE
;
