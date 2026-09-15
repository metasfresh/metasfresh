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


DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_TransportOrder_Package_Details (IN p_M_ShipperTransportation_ID numeric)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_TransportOrder_Package_Details(IN p_M_ShipperTransportation_ID numeric)
    RETURNS TABLE
            (
                BPName           varchar,
                BPAddress        varchar,
                ipa_sscc18       text,
                cu_per_tu        numeric,
                QtyTU            numeric,
                QtyLU            numeric,
                packageweight    numeric,
                packagenetweight numeric,
                paletno          varchar,
                order_docno      varchar,
                note             text
            )
AS
$$


SELECT bp.name                                                                                                                      AS BPName,
       bpl.address                                                                                                                  AS BPAddress,
       package.ipa_sscc18                                                                                                           AS sscc,
       piip.qty                                                                                                                     AS cu_per_tu,
       (CASE WHEN TU IS NOT NULL AND TU > 0 THEN TU ELSE shippingPackage.QtyTU END)                                                 AS QtyTU,
       (CASE WHEN LU IS NOT NULL AND LU > 0 THEN LU ELSE shippingPackage.QtyLU END)                                                 AS QtyLU,
       shippingPackage.packageweight,
       COALESCE(piip.qty, 0) * COALESCE(p.weight, 0) * (CASE WHEN TU IS NOT NULL AND TU > 0 THEN TU ELSE shippingPackage.QtyTU END) AS packagenetweight,
       sscc18_extract_serialnumber(package.ipa_sscc18::TEXT)                                                                        AS paletno,
       o.documentno                                                                                                                 AS order_docno,
       shippingPackage.note

FROM M_ShippingPackage shippingPackage
         INNER JOIN C_BPartner bp ON shippingPackage.C_BPartner_ID = bp.C_BPartner_ID
         INNER JOIN C_BPartner_Location bpl ON shippingPackage.C_BPartner_Location_ID = bpl.C_BPartner_Location_ID
         LEFT JOIN C_OrderLine ol ON ol.c_orderline_id = shippingPackage.c_orderline_id
         LEFT JOIN C_Order o ON o.C_order_id = ol.C_order_id
         JOIN M_Package package ON shippingPackage.m_package_id = package.m_package_id
         LEFT JOIN m_product p ON p.m_product_id = ol.m_product_id
         LEFT JOIN M_HU_PI_Item_Product piip ON ol.m_product_id = piip.m_product_id AND ol.m_hu_pi_item_product_id = piip.m_hu_pi_item_product_id
         LEFT OUTER JOIN M_Package_HU phu ON package.M_Package_ID = phu.M_Package_ID
         LEFT OUTER JOIN M_HU hu ON phu.M_HU_ID = hu.M_HU_ID
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

WHERE m_shippertransportation_id = p_M_ShipperTransportation_ID
ORDER BY shippingPackage.M_ShippingPackage_ID
$$
    LANGUAGE sql
    STABLE
;
