DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Manufacturing_Order_Description(IN NUMERIC,
                                                                                                IN NUMERIC,
                                                                                                IN CHARACTER VARYING(6))
;

CREATE
    OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Manufacturing_Order_Description(IN record_id        NUMERIC,
                                                                                                IN p_m_attribute_id NUMERIC,
                                                                                                IN p_ad_language    CHARACTER VARYING(6))

    RETURNS TABLE
            (
                documentno    CHARACTER VARYING(30),
                dateordered   TIMESTAMP WITH TIME ZONE,
                datepromised  TIMESTAMP WITH TIME ZONE,
                VALUE         CHARACTER VARYING(60),
                NAME          CHARACTER VARYING(255),
                PrintName     CHARACTER VARYING(60),
                attributes    CHARACTER VARYING,
                co_documentno CHARACTER VARYING,
                qty           NUMERIC,
                bpName        CHARACTER VARYING(255)
            )
AS
$$

SELECT pp.DocumentNo,
       pp.DateOrdered,
       pp.datepromised,
       pbom.value,
       COALESCE(pt.name, pbom.name)          AS name,
       COALESCE(dtt.PrintName, dt.PrintName) AS PrintName,
       Attributes.attributes_value,
       o.documentno                          AS co_documentno,
       pp.qtyentered,
       bp.name                               AS bpName
FROM PP_Order pp
         JOIN PP_Product_BOM bom ON pp.PP_Product_BOM_ID = bom.PP_Product_BOM_ID
         LEFT JOIN c_bpartner bp ON pp.c_bpartner_id = bp.c_bpartner_id
         LEFT JOIN c_orderline ol ON pp.c_orderline_id = ol.c_orderline_id
         LEFT JOIN c_order o ON ol.c_order_id = o.c_order_id
         LEFT JOIN de_metas_endcustomer_fresh_reports.get_hu_attribute_value_for_pp_order_and_pp_order_bomline(p_m_attribute_id, pp.pp_order_id, NULL) AS Attributes ON 1 = 1

    -- Product and its translation
         JOIN M_product pbom ON bom.m_product_id = pbom.m_product_id
         LEFT JOIN M_Product_Trl pt ON bom.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_ad_language

         LEFT JOIN C_DocType dt ON pp.C_DocTypeTarget_ID = dt.C_DocType_ID
         LEFT JOIN C_DocType_Trl dtt
                   ON pp.C_DocTypeTarget_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_ad_language
WHERE pp.PP_Order_ID = record_id
$$
    LANGUAGE SQL
    STABLE
;