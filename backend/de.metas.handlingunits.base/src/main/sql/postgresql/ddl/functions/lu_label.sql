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

         JOIN M_HU_QRCode qr ON tu.m_hu_id = qr.m_hu_id
WHERE tu.M_HU_ID = p_M_HU_ID;
$$
;