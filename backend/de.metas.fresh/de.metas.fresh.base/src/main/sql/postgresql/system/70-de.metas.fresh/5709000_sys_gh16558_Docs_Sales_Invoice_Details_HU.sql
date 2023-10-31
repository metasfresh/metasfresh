DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Details_HU ( IN p_record_id numeric, IN p_language Character Varying (6) );
CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Details_HU( IN p_record_id numeric, IN p_language Character Varying (6) )
    RETURNS TABLE
            (
                shipped                    numeric,
                retour                     numeric,
                name                       character varying,
                price                      numeric,
                linenetamt                 numeric,
                uomsymbol                  character varying,
                rate                       numeric,
                isprinttax                 character,
                qtyentered                 numeric,
                description                character varying,
                isprintwhenpackingmaterial character,
                PricePattern               text
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT SUM(CASE
               WHEN il.QtyEntered > 0 THEN il.QtyEntered
                                      ELSE 0
           END)                                          AS shipped,
       SUM(CASE
               WHEN il.QtyEntered < 0 THEN il.QtyEntered * -1
                                      ELSE 0
           END)                                          AS retour,
       COALESCE(pt.Name, p.Name)                         AS Name,
       il.PriceEntered                                   AS Price,
       SUM(il.LineNetAmt)                                AS LineNetAmt,
       COALESCE(uom.UOMSymbol, uomt.UOMSymbol)           AS UOMSymbol,
       t.rate,
       bpg.IsPrintTax,
       SUM(il.QtyEntered)                                AS QtyEntered,
       il.Description,
       p.IsPrintWhenPackingMaterial,
       report.getPricePatternForJasper(i.m_pricelist_id) AS PricePattern
FROM C_InvoiceLine il
         INNER JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID
         INNER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID
         LEFT OUTER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID
    -- Product and its translation
         LEFT OUTER JOIN M_Product p ON il.M_Product_ID = p.M_Product_ID
         LEFT OUTER JOIN M_Product_Trl pt ON il.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_language
         LEFT OUTER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID
    -- Unit of measurement and its translation
         LEFT OUTER JOIN C_UOM uom ON il.C_UOM_ID = uom.C_UOM_ID
         LEFT OUTER JOIN C_UOM_Trl uomt ON il.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_language
    -- Tax rate
         LEFT OUTER JOIN C_Tax t ON il.C_Tax_ID = t.C_Tax_ID
WHERE il.C_Invoice_ID = p_record_id
  AND pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', il.AD_Client_ID, il.AD_Org_ID)
GROUP BY COALESCE(pt.Name, p.Name),
         COALESCE(uom.UOMSymbol, uomt.UOMSymbol),
         il.PriceEntered,
         t.rate,
         bpg.IsPrintTax,
         il.Description,
         p.IsPrintWhenPackingMaterial,
         i.m_pricelist_id
$$
;

