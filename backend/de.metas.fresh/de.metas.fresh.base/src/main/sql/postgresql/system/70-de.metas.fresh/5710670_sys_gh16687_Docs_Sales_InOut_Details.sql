DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details (IN p_Record_ID   numeric,
                                                                                     IN p_AD_Language Character Varying(6))
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details(IN p_Record_ID   numeric,
                                                                            IN p_AD_Language Character Varying(6))
    RETURNS TABLE
            (
                Line              Numeric(10, 0),
                Name              Character Varying,
                Attributes        Text,
                HUQty             Numeric,
                HUName            Text,
                qtyEntered        Numeric,
                PriceEntered      Numeric,
                UOMSymbol         Character Varying(10),
                StdPrecision      Numeric(10, 0),
                QtyPattern        character varying,
                LineNetAmt        Numeric,
                Discount          Numeric,
                IsDiscountPrinted Character(1),
                Description       Character Varying,
                bp_product_no     character varying(30),
                bp_product_name   character varying(100),
                best_before_date  text,
                lotno             character varying,
                p_value           character varying(30),
                p_description     character varying(255),
                inout_description character varying(255),
                iscampaignprice   character(1),
                qtyordered        Numeric,
                orderUOMSymbol    Character Varying(10)
            )
AS
$$

SELECT iol.line,
       COALESCE(pt.Name, p.name)                                                                       AS Name,
       CASE
           WHEN LENGTH(att.Attributes) > 15
               THEN att.Attributes || E'\n'
               ELSE att.Attributes
       END                                                                                             AS Attributes,
       iol.QtyEnteredTU                                                                                AS HUQty,
       pi.name                                                                                         AS HUName,
       (CASE
            WHEN qtydeliveredcatch IS NOT NULL
                THEN qtydeliveredcatch
                ELSE iol.QtyEntered * COALESCE(multiplyrate, 1)
        END)                                                                                           AS QtyEntered,
       COALESCE(ic.PriceEntered_Override, ic.PriceEntered)                                             AS PriceEntered,
       (CASE
            WHEN qtydeliveredcatch IS NOT NULL
                THEN COALESCE(uomct.UOMSymbol, uomc.UOMSymbol)
                ELSE COALESCE(uomt.UOMSymbol, uom.UOMSymbol)
        END)                                                                                           AS UOMSymbol,
       uom.stdPrecision,
       report.getQtyPattern(uom.StdPrecision)                                          AS QtyPattern,
       COALESCE(ic.PriceActual_Override, ic.PriceActual) * iol.MovementQty * COALESCE(multiplyrate, 1) AS linenetamt,
       COALESCE(ic.Discount_Override, ic.Discount)                                                     AS Discount,
       bp.isDiscountPrinted,
       COALESCE(iol.Description, ol.Description),
       -- in case there is no C_BPartner_Product, fallback to the default ones
       COALESCE(NULLIF(bpp.ProductNo, ''), p.value)                                                    AS bp_product_no,
       COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name)                                          AS bp_product_name,
       TO_CHAR(att.best_before_date :: date, 'MM.YYYY')                                                AS best_before_date,
       att.lotno,
       p.value                                                                                         AS p_value,
       p.description                                                                                   AS p_description,
       io.description                                                                                  AS inout_description,
       ol.iscampaignprice,
       ol.qtyordered,
       COALESCE(uomt_ol.UOMSymbol, uom_ol.UOMSymbol)                                                   AS orderUOMSymbol
FROM M_InOutLine iol
         INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID
         LEFT OUTER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID
         LEFT OUTER JOIN (
    SELECT AVG(ic.PriceEntered_Override) AS PriceEntered_Override,
           AVG(ic.PriceEntered)          AS PriceEntered,
           AVG(ic.PriceActual_Override)  AS PriceActual_Override,
           AVG(ic.PriceActual)           AS PriceActual,
           AVG(ic.Discount_Override)     AS Discount_Override,
           AVG(ic.Discount)              AS Discount,
           Price_UOM_ID,
           iciol.M_InOutLine_ID
    FROM C_InvoiceCandidate_InOutLine iciol
             INNER JOIN C_Invoice_Candidate ic
                        ON iciol.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID AND ic.isActive = 'Y'
             INNER JOIN M_InOutLine iol ON iol.M_InOutLine_ID = iciol.M_InOutLine_ID AND iol.isActive = 'Y'
    WHERE iol.M_InOut_ID = p_Record_ID
      AND iciol.isActive = 'Y'
    GROUP BY Price_UOM_ID, iciol.M_InOutLine_ID
) ic ON iol.M_InOutLine_ID = ic.M_InOutLine_ID

    -- get details from order line
         LEFT OUTER JOIN c_orderline ol ON ol.c_orderline_id = iol.c_orderline_id
         LEFT OUTER JOIN C_UOM uom_ol ON uom_ol.C_UOM_ID = ol.C_UOM_ID
         LEFT OUTER JOIN C_UOM_Trl uomt_ol
                         ON uomt_ol.C_UOM_ID = uom_ol.C_UOM_ID AND uomt_ol.AD_Language = p_AD_Language AND uomt_ol.isActive = 'Y'

    -- Get Packing instruction
         LEFT OUTER JOIN
     (
         SELECT STRING_AGG(DISTINCT name, E'\n'
                           ORDER BY name) AS Name,
                M_InOutLine_ID
         FROM (
                  SELECT DISTINCT
                      -- 08604 - in IT1 only one PI was shown though 2 were expected. Only the fallback can do this, so we use it first
                      COALESCE(pifb.name, pi.name) AS name,
                      iol.M_InOutLine_ID
                  FROM M_InOutLine iol
                           -- Get PI directly from InOutLine (1 to 1)
                           LEFT OUTER JOIN M_HU_PI_Item_Product pi
                                           ON iol.M_HU_PI_Item_Product_ID = pi.M_HU_PI_Item_Product_ID AND pi.isActive = 'Y'
                           LEFT OUTER JOIN M_HU_PI_Item piit ON piit.M_HU_PI_Item_ID = pi.M_HU_PI_Item_ID AND piit.isActive = 'Y'
                      -- Get PI from HU assignments (1 to n)
                      -- if the HU was set manually don't check the assignments
                           LEFT OUTER JOIN M_HU_Assignment asgn ON asgn.AD_Table_ID = ((SELECT get_Table_ID('M_InOutLine')))
                      AND asgn.Record_ID = iol.M_InOutLine_ID AND asgn.isActive = 'Y' AND
                                                                   iol.ismanualpackingmaterial = 'N'
                           LEFT OUTER JOIN M_HU tu ON asgn.M_TU_HU_ID = tu.M_HU_ID
                           LEFT OUTER JOIN M_HU_PI_Item_Product pifb
                                           ON tu.M_HU_PI_Item_Product_ID = pifb.M_HU_PI_Item_Product_ID AND pifb.isActive = 'Y'
                           LEFT OUTER JOIN M_HU_PI_Item pit ON pifb.M_HU_PI_Item_ID = pit.M_HU_PI_Item_ID AND pit.isActive = 'Y'
                      --
                           LEFT OUTER JOIN M_HU_PI_Version piv
                                           ON piv.M_HU_PI_Version_ID = COALESCE(pit.M_HU_PI_Version_ID, piit.M_HU_PI_Version_ID) AND piv.isActive = 'Y'
                  WHERE piv.M_HU_PI_Version_ID != 101
                    AND iol.M_InOut_ID = p_Record_ID
                    AND iol.isActive = 'Y'
              ) x
         GROUP BY M_InOutLine_ID
     ) pi ON iol.M_InOutLine_ID = pi.M_InOutLine_ID
         -- Product and its translation
         LEFT OUTER JOIN M_Product p ON iol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
         LEFT OUTER JOIN M_Product_Trl pt ON iol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_AD_Language AND pt.isActive = 'Y'
         LEFT OUTER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'

    -- Unit of measurement and its translation
         LEFT OUTER JOIN C_UOM uom ON ic.Price_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
         LEFT OUTER JOIN C_UOM_Trl uomt ON ic.Price_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_AD_Language AND uomt.isActive = 'Y'
         LEFT OUTER JOIN C_UOM_Conversion conv ON conv.C_UOM_ID = iol.C_UOM_ID
    AND conv.C_UOM_To_ID = ic.Price_UOM_ID
    AND iol.M_Product_ID = conv.M_Product_ID
    AND conv.isActive = 'Y'

    -- Unit of measurement and its translation for catch weight
         LEFT OUTER JOIN C_UOM uomc ON uomc.C_UOM_ID = iol.catch_uom_id
         LEFT OUTER JOIN C_UOM_Trl uomct ON uomct.c_UOM_ID = uom.C_UOM_ID AND uomct.AD_Language = p_AD_Language
    -- Attributes
         LEFT OUTER JOIN (
    SELECT STRING_AGG(at.ai_value, ', '
           ORDER BY LENGTH(at.ai_value), at.ai_value)
           FILTER (WHERE at.at_value NOT IN ('HU_BestBeforeDate', 'Lot-Nummer'))
                                                        AS Attributes,

           at.M_AttributeSetInstance_ID,
           STRING_AGG(REPLACE(at.ai_value, 'MHD: ', ''), ', ')
           FILTER (WHERE at.at_value LIKE 'HU_BestBeforeDate')
                                                        AS best_before_date,
           STRING_AGG(ai_value, ', ')
           FILTER (WHERE at.at_value LIKE 'Lot-Nummer') AS lotno

    FROM Report.fresh_Attributes at
             JOIN M_InOutLine iol
                  ON at.M_AttributeSetInstance_ID = iol.M_AttributeSetInstance_ID AND iol.isActive = 'Y'
    WHERE at.IsPrintedInDocument = 'Y'
      AND iol.M_InOut_ID = p_Record_ID
    GROUP BY at.M_AttributeSetInstance_ID
) att ON iol.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID

         LEFT OUTER JOIN
     de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(p.M_Product_ID, bp.C_BPartner_ID,
                                                                      att.M_AttributeSetInstance_ID) AS bpp ON 1 = 1
WHERE iol.M_InOut_ID = p_Record_ID
  AND iol.isActive = 'Y'
  AND (COALESCE(pc.M_Product_Category_ID, -1) !=
       getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol.AD_Client_ID, iol.AD_Org_ID))
  AND iol.QtyEntered != 0 -- Don't display lines without a Qty. See 08293
ORDER BY line

$$
    LANGUAGE sql
    STABLE
;