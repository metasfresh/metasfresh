DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Details_HU(IN p_record_id numeric, IN p_language Character Varying (6));

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Details_HU(IN p_record_id numeric, IN p_language Character Varying (6))
    RETURNS TABLE
            (
                qtyentered                 numeric,
                name                       character varying,
                price                      numeric,
                linenetamt                 numeric,
                uomsymbol                  character varying,
                description                character varying,
                isprintwhenpackingmaterial character,
                PricePattern               text
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT ol.QtyEntered,
       COALESCE(pt.Name, p.name)                         AS Name,
       ol.PriceEntered                                   AS Price,
       ol.linenetamt,
       COALESCE(uom.UOMSymbol, uomt.UOMSymbol)           AS UOMSymbol,
       ol.Description,
       p.IsPrintWhenPackingMaterial,
       report.getPricePatternForJasper(o.m_pricelist_id) AS PricePattern
FROM c_orderline ol
         INNER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
    -- Product and its translation
         LEFT OUTER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID
         LEFT OUTER JOIN M_Product_Trl pt ON ol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_language
         LEFT OUTER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID
    -- Unit of measurement and its translation
         LEFT OUTER JOIN C_UOM uom ON ol.C_UOM_ID = uom.C_UOM_ID
         LEFT OUTER JOIN C_UOM_Trl uomt ON ol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_language
WHERE ol.C_Order_ID = p_record_id
  AND pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID)

$$
;

