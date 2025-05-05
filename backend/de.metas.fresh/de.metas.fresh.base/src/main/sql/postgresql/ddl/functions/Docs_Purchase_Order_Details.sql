DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Details(IN p_record_id   numeric,
                                                                                       IN p_ad_language Character Varying(6))
;


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Details(IN p_record_id   numeric,
                                                                               IN p_ad_language Character Varying(6))
    RETURNS TABLE
            (
                line                 numeric(10, 0),
                Name                 character varying,
                Attributes           text,
                HUQty                numeric,
                HUName               character varying,
                QtyEnteredInPriceUOM numeric,
                PriceEntered         numeric,
                UOMSymbol            character varying(10),
                StdPrecision         numeric(10, 0),
                QtyPattern           character varying,
                linenetamt           numeric,
                discount             numeric,
                isDiscountPrinted    character(1),
                rate                 character varying,
                isPrintTax           character(1),
                Description          Character Varying,
                bp_product_no        character varying(30),
                bp_product_name      character varying(100),
                cursymbol            character varying(10),
                p_value              character varying(40),
                p_description        character varying(255),
                p_documentnote       character varying,
                order_description    character varying,
                PricePattern         text
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT ol.line,
       COALESCE(pt.Name, p.name)                              AS Name,
       CASE
           WHEN LENGTH(Attributes) > 15
               THEN Attributes || E'\n'
               ELSE Attributes
       END                                                    AS Attributes,
       ol.QtyEnteredTU                                        AS HUQty,
       CASE
           WHEN piit.M_HU_PI_Version_ID = 101 OR ol.QtyEnteredTU IS NULL
               THEN NULL
               ELSE ip.name
       END                                                    AS HUName,
       ol.QtyEnteredInPriceUOM                                AS QtyEnteredInPriceUOM,
       ol.PriceEntered                                        AS PriceEntered,
       COALESCE(uomt.UOMSymbol, uom.UOMSymbol)                AS UOMSymbol,
       uom.StdPrecision,
       report.getQtyPattern(uom.StdPrecision)AS QtyPattern,
       ol.linenetamt                                          AS linenetamt,
       CASE
           WHEN ROUND(discount, 0) = discount THEN ROUND(discount, 0)
           WHEN ROUND(discount, 1) = discount THEN ROUND(discount, 1)
                                              ELSE ROUND(discount, 2)
       END                                                    AS discount,
       bp.isDiscountPrinted,
       CASE
           WHEN ROUND(rate, 0) = rate THEN ROUND(rate, 0)
           WHEN ROUND(rate, 1) = rate THEN ROUND(rate, 1)
                                      ELSE ROUND(rate, 2)
       END::character varying                                 AS rate,
       isPrintTax,
       ol.Description,
       -- in case there is no C_BPartner_Product, fallback to the default ones
       COALESCE(NULLIF(bpp.ProductNo, ''), p.value)           AS bp_product_no,
       COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name) AS bp_product_name,
       c.cursymbol,
       p.value                                                AS p_value,
       COALESCE(pt.description, p.description)                AS p_description,
       COALESCE(pt.documentnote, p.documentnote)              AS p_documentnote,
       o.description                                          AS order_description,
       report.getPricePatternForJasper(pl.m_pricelist_id)     AS PricePattern

FROM C_OrderLine ol
         INNER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
         LEFT OUTER JOIN C_BPartner bp ON ol.C_BPartner_ID = bp.C_BPartner_ID
         INNER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID
         LEFT OUTER JOIN M_HU_PI_Item_Product ip ON ol.M_HU_PI_Item_Product_ID = ip.M_HU_PI_Item_Product_ID
         LEFT OUTER JOIN M_HU_PI_Item piit ON ip.M_HU_PI_Item_ID = piit.M_HU_PI_Item_ID
    -- Product and its translation
         LEFT OUTER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID
         LEFT OUTER JOIN M_Product_Trl pt ON ol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_ad_language
         LEFT OUTER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID

         LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID
    AND p.M_Product_ID = bpp.M_Product_ID AND bpp.UsedForVendor = 'Y'
    -- Unit of measurement and its translation
         LEFT OUTER JOIN C_UOM uom ON ol.Price_UOM_ID = uom.C_UOM_ID
         LEFT OUTER JOIN C_UOM_Trl uomt ON ol.Price_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_ad_language
    -- Tax
         LEFT OUTER JOIN C_Tax t ON ol.C_Tax_ID = t.C_Tax_ID
    -- Get Attributes
         LEFT OUTER JOIN (SELECT STRING_AGG(att.ai_value, ', ' ORDER BY LENGTH(att.ai_value)) AS Attributes, att.M_AttributeSetInstance_ID, ol.C_OrderLine_ID
                          FROM Report.fresh_Attributes att
                                   JOIN C_OrderLine ol ON att.M_AttributeSetInstance_ID = ol.M_AttributeSetInstance_ID
                          WHERE att.at_Value IN ('1000002', '1000001', '1000030', '1000015', 'M_Material_Tracking_ID')
                            AND ol.C_Order_ID = p_record_id
                          -- att.at_IsAttrDocumentRelevant = 'Y' currently those flags are set to be correct for purchase invoices. we need something more flexible for all kinds of documents
                          GROUP BY att.M_AttributeSetInstance_ID, ol.C_OrderLine_ID) att ON ol.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID AND ol.C_OrderLine_ID = att.C_OrderLine_ID

         LEFT JOIN C_Currency c ON o.C_Currency_ID = c.C_Currency_ID

         INNER JOIN M_PriceList pl ON pl.m_pricelist_id = o.m_pricelist_id

WHERE ol.C_Order_ID = p_record_id
  AND COALESCE(pc.M_Product_Category_ID, -1) != getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID)
ORDER BY ol.line

$$
;

