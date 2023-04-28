DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Customer_Returns_Details(IN p_record_id   numeric,
                                                                                                     IN p_AD_Language Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Customer_Returns_Details(IN p_record_id   numeric,
                                                                                                        IN p_AD_Language Character Varying(6))
    RETURNS TABLE
            (
                Attributes             text,
                Name                   character varying,
                HUQty                  numeric,
                HUName                 text,
                MovementQty            numeric,
                UOMSymbol              character varying,
                StdPrecision           numeric(10, 0),
                QualityDiscountPercent numeric,
                QualityNote            character varying,
                Description            Character Varying,
                bp_product_no          character varying(30),
                bp_product_name        character varying(100),
                line                   numeric
            )
AS
$$

SELECT Attributes,
       Name, -- product
       SUM(HUQty)       AS HUQty,
       HUName,
       SUM(MovementQty) AS MovementQty,
       UOMSymbol,
       StdPrecision,
       QualityDiscountPercent,
       QualityNote,
       iol.Description,
       bp_product_no,
       bp_product_name,
       MAX(iol.line)    AS line

FROM (
         SELECT a.Attributes,
                COALESCE(pt.Name, p.name)                              AS Name,
                iol.qtyenteredtu                                       AS HUQty,
                hupi.name                                              AS HUName,
                iol.MovementQty                                        AS MovementQty,
                COALESCE(uomt.UOMSymbol, uom.UOMSymbol)                AS UOMSymbol,
                uom.StdPrecision,
                iol.QualityDiscountPercent,
                iol.QualityNote,
                iol.Description,
                -- in case there is no C_BPartner_Product, fallback to the default ones
                COALESCE(NULLIF(bpp.ProductNo, ''), p.value)           AS bp_product_no,
                COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name) AS bp_product_name,

                iol.line                                               AS line


         FROM M_Inout io --customer return

                  INNER JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID

                  INNER JOIN M_Product p ON iol.M_Product_ID = p.M_Product_ID
                  LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_AD_Language AND pt.isActive = 'Y'
                  LEFT JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID
                  LEFT OUTER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID
                  LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID
             AND p.M_Product_ID = bpp.M_Product_ID


             -- Unit of measurement & its translation
                  INNER JOIN C_UOM uom ON iol.C_UOM_ID = uom.C_UOM_ID
                  LEFT OUTER JOIN C_UOM_Trl uomt ON iol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_AD_Language AND uomt.isActive = 'Y'

                  LEFT OUTER JOIN M_HU_PI_Item_Product hupi ON iol.M_HU_PI_Item_Product_ID = hupi.M_HU_PI_Item_Product_ID

             -- Attributes
                  LEFT OUTER JOIN LATERAL
             (
             SELECT /** Jasper Servlet runs under linux, jasper client under windows (mostly). both have different fonts therefore, when
		  * having more than 2 lines, the field is too short to display all lines in the windows font to avoid this I add an extra
		  * line as soon as the attributes string has more than 15 characters (which is still very likely to fit in two lines)
		  */
                 CASE WHEN LENGTH(Attributes) > 15 THEN Attributes || E'\n' ELSE Attributes END AS Attributes,
                 M_AttributeSetInstance_ID,
                 M_InOutLine_ID
             FROM (
                      SELECT STRING_AGG(att.ai_value, ', ' ORDER BY att.M_AttributeSetInstance_ID, LENGTH(att.ai_value), att.ai_value) AS Attributes,
                             att.M_AttributeSetInstance_ID,
                             iol.M_InOutLine_ID
                      FROM Report.fresh_Attributes att
                      WHERE att.IsPrintedInDocument = 'Y'
                        AND iol.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID
                      GROUP BY att.M_AttributeSetInstance_ID, iol.M_InOutLine_ID
                  ) x
             ) a ON TRUE


         WHERE COALESCE(pc.M_Product_Category_ID, -1) != getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol.AD_Client_ID, iol.AD_Org_ID)
           AND io.M_InOut_ID = p_record_id
     ) iol


GROUP BY Attributes,
         Name, -- product
         HUName,
         UOMSymbol,
         StdPrecision,
         QualityDiscountPercent,
         QualityNote,
         iol.Description,
         bp_product_no,
         bp_product_name
$$
    LANGUAGE sql STABLE
;