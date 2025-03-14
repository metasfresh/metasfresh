/*
 * #%L
 * de.metas.handlingunits.base
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

DROP FUNCTION IF EXISTS "de.metas.handlingunits".hu_label(
    p_M_HU_ID numeric
)
;

DROP FUNCTION IF EXISTS "de.metas.handlingunits".hu_label(
    p_M_HU_ID           numeric,
    p_StringAttributeKey1 character varying,
    p_DateAttributeKey1   character varying
)
;


CREATE OR REPLACE FUNCTION "de.metas.handlingunits".hu_label(
    p_M_HU_ID           numeric,
    p_StringAttributeKey1 character varying = NULL,
    p_DateAttributeKey1   character varying = NULL
)
    RETURNS TABLE
            (
                prod_name            character varying,
                prod_value           character varying,
                prod_desc            text,
                displayableqrcode    text,
                renderedqrcode       text,
                vendorName           text,
                PurchaseOrderNo      text,
                receiptdate          timestamp WITHOUT TIME ZONE,
                proddate             date,
                StringAttributeValue text,
                DateAttributeValue   timestamp WITHOUT TIME ZONE
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT DISTINCT p.name,
                p.value            AS prod_value,
                p.description      AS prod_desc,
                qr.displayableqrcode,
                qr.renderedqrcode,
                ri.vendorName,
                ri.PurchaseOrderNo,
                ri.receiptdate,
                (SELECT hua.valuedate::date
                 FROM M_HU_Attribute hua
                 WHERE hua.M_HU_ID = tu.M_HU_ID
                   AND hua.M_Attribute_ID = (SELECT M_Attribute_ID FROM M_Attribute WHERE value = 'ProductionDate'))    AS proddate,
                (CASE
                     WHEN p_StringAttributeKey1 IS NOT NULL
                         THEN (SELECT hua.value
                               FROM M_HU_Attribute hua
                               WHERE hua.M_HU_ID = tu.M_HU_ID
                                 AND hua.M_Attribute_ID = (SELECT M_Attribute_ID FROM M_Attribute WHERE value = p_StringAttributeKey1))
                 END)              AS StringAttributeValue1,
                (CASE
                     WHEN p_DateAttributeKey1 IS NOT NULL
                         THEN (SELECT hua.ValueDate
                               FROM M_HU_Attribute hua
                               WHERE hua.M_HU_ID = tu.M_HU_ID
                                 AND hua.M_Attribute_ID = (SELECT M_Attribute_ID FROM M_Attribute WHERE value = p_DateAttributeKey1))
                 END)              AS DateAttributeValue1
FROM M_HU tu

         /** Get Product */
         INNER JOIN M_HU_Storage tus ON tu.M_HU_ID = tus.M_HU_ID
         INNER JOIN M_Product p ON tus.M_Product_ID = p.M_Product_ID
    -- QR Code
         INNER JOIN M_HU_QRCODE_ASSIGNMENT qr_assign ON tu.m_hu_id = qr_assign.m_hu_id
         INNER JOIN M_HU_QRCode qr ON qr_assign.m_hu_qrcode_id = qr.m_hu_qrcode_id

    /** receipt infos */
         LEFT OUTER JOIN (SELECT DISTINCT ON (tu.M_HU_ID) tu.M_HU_ID,
                                                          bp.name         AS vendorName,
                                                          o.DocumentNo    AS PurchaseOrderNo,
                                                          io.movementdate AS receiptdate
                          FROM M_HU tu
                                   LEFT OUTER JOIN M_HU_Item lui ON lui.M_HU_Item_ID = tu.M_HU_Item_Parent_ID AND lui.isActive = 'Y'
                                   LEFT OUTER JOIN M_HU lu ON lu.M_HU_ID = lui.M_HU_ID
                                   LEFT OUTER JOIN M_HU_PI_Version piv
                                                   ON piv.M_HU_PI_Version_ID = lu.M_HU_PI_Version_ID
                                   LEFT OUTER JOIN M_HU thu ON thu.M_HU_ID = COALESCE(lu.M_HU_ID, tu.M_HU_ID)
                                   LEFT OUTER JOIN M_HU_Assignment hf_a ON thu.M_HU_ID = hf_a.M_HU_ID
                                   LEFT JOIN M_ReceiptSchedule rs ON hf_a.Record_ID = rs.M_ReceiptSchedule_ID
                              AND hf_a.ad_table_id = get_table_id('M_ReceiptSchedule') AND rs.isActive = 'Y'
                                   LEFT JOIN m_inoutLine iol ON hf_a.Record_ID = iol.m_inoutLine_ID
                              AND hf_a.ad_table_id = get_table_id('m_inoutLine') AND iol.isActive = 'Y'
                                   LEFT JOIN m_inout io ON io.m_inout_id = iol.m_inout_id
                                   LEFT JOIN C_BPartner bp ON COALESCE(rs.C_BPartner_ID, io.c_bpartner_id) = bp.C_BPartner_ID
                                   LEFT JOIN C_Order o ON COALESCE(rs.C_Order_ID, io.c_order_id) = o.C_Order_ID
                          ORDER BY tu.M_HU_ID, bp.name NULLS LAST, o.DocumentNo NULLS LAST) ri ON ri.M_HU_ID = tu.M_HU_ID

WHERE tu.M_HU_ID = p_M_HU_ID;
$$
;