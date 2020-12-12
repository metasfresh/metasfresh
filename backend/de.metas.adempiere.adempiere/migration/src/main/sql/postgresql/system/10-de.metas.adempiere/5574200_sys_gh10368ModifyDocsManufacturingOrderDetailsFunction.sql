DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Manufacturing_Order_Details(IN numeric, IN numeric, IN Character Varying)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Manufacturing_Order_Details(IN record_id        numeric,
                                                                                               IN p_m_attribute_id numeric,
                                                                                               IN p_ad_language    character Varying)

    RETURNS TABLE
            (
                line            numeric,
                qtyrequiered    numeric,
                uomsymbol       character varying,
                value           character varying,
                vendorProductNo character varying,
                description     character varying,
                productName     character varying,
                attributes      character varying
            )
AS
$$

SELECT line,
       qtyrequiered,
       COALESCE(uom.UOMSymbol, uomt.UOMSymbol) AS UOMSymbol,
       p.value,
       coalesce(bpp.productno, p.value)        AS vendorProductNo,
       coalesce(pt.description, p.description) AS description,
       coalesce(pt.Name, p.Name)               AS productName,
       Attributes.attributes_value
FROM PP_Order_BOMLine bomLine

         -- Product and its translation
         JOIN M_Product p ON bomLine.m_product_id = p.m_product_id
         LEFT OUTER JOIN M_Product_Trl pt ON bomLine.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_ad_language AND pt.isActive = 'Y'
    -- Unit of measurement and its translation
         JOIN c_uom uom ON bomLine.c_uom_id = uom.c_uom_id
         LEFT OUTER JOIN C_UOM_Trl uomt ON bomLine.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_ad_language
         LEFT JOIN getc_bpartner_product_vendor(p.m_product_id) bpp ON 1 = 1
         LEFT JOIN de_metas_endcustomer_fresh_reports.get_hu_attribute_value_for_pp_order_and_pp_order_bomline(p_m_attribute_id, bomLine.pp_order_id, bomLine.pp_order_bomline_id) AS Attributes ON 1 = 1
WHERE bomLine.PP_Order_ID = record_id
  AND bomLine.isActive = 'Y'
  AND bomLine.componenttype != 'BY'
ORDER BY line
$$
    LANGUAGE sql
    STABLE
;
