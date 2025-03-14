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

DROP FUNCTION IF EXISTS "de.metas.handlingunits".lu_label(
    p_M_HU_ID     numeric
)
;


CREATE OR REPLACE FUNCTION "de.metas.handlingunits".lu_label(
    p_M_HU_ID     numeric
)
    RETURNS TABLE
            (
                lotno             character varying,
                MHD               timestamp WITHOUT TIME ZONE,
                weight            CHARACTER VARYING,
                name              character varying,
                prod_value        character varying,
                prod_desc         text,
                displayableqrcode text,
                renderedqrcode    text,
                biolabel_image_id numeric,
                M_product_ID      numeric
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT DISTINCT tua_lotno.value   AS lotno,
                tua_MHD.valuedate AS MHD,
                tua_w.value       AS weight,
                p.name,
                p.value           AS prod_value,
                p.description     AS prod_desc,
                qr.displayableqrcode,
                qr.renderedqrcode,
                lv.ad_image_id    AS biolabel_image_id,
                p.M_product_ID
FROM M_HU tu

         /** Get Product */
         INNER JOIN M_HU_Storage tus ON tu.M_HU_ID = tus.M_HU_ID
         INNER JOIN M_Product p ON tus.M_Product_ID = p.M_Product_ID

    /** Get Lot no */
         LEFT OUTER JOIN M_HU_Attribute tua_lotno ON tu.M_HU_ID = tua_lotno.M_HU_ID
    AND tua_lotno.M_Attribute_ID = ((SELECT M_Attribute_ID
                                     FROM M_Attribute
                                     WHERE value = 'Lot-Nummer'
    ))
    /** Get MHD */
         LEFT OUTER JOIN M_HU_Attribute tua_MHD ON tu.M_HU_ID = tua_MHD.M_HU_ID
    AND tua_MHD.M_Attribute_ID = ((SELECT M_Attribute_ID
                                   FROM M_Attribute
                                   WHERE value = 'HU_BestBeforeDate'
    ))

    /** Get label attribute */
		LEFT OUTER JOIN M_HU_Attribute tua_label ON tu.M_HU_ID = tua_label.M_HU_ID
    AND tua_label.M_Attribute_ID = ((SELECT M_Attribute_ID
                                     FROM M_Attribute
                                     WHERE value = '1000002'))
        LEFT JOIN m_attributevalue lv ON lv.M_Attribute_ID = tua_label.M_Attribute_ID AND lv.isactive = 'Y' and lv.value=tua_label.value

    /** Get WeightNet attribute */
         LEFT OUTER JOIN M_HU_Attribute tua_w ON tu.M_HU_ID = tua_w.M_HU_ID
    AND tua_w.M_Attribute_ID = ((SELECT M_Attribute_ID
                                 FROM M_Attribute
                                 WHERE value = 'WeightNet'))

         INNER JOIN M_HU_QRCODE_ASSIGNMENT qr_assign ON tu.m_hu_id = qr_assign.m_hu_id
         INNER JOIN M_HU_QRCode qr ON qr_assign.m_hu_qrcode_id = qr.m_hu_qrcode_id
WHERE tu.M_HU_ID = p_M_HU_ID;
$$
;