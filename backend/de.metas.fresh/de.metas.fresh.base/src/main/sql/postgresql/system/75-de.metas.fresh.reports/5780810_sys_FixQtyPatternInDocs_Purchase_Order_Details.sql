DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Details(IN p_record_id   numeric,
                                                                                       IN p_ad_language Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Order_Details(IN p_record_id   numeric,
                                                                                          IN p_ad_language Character Varying(6))

    RETURNS TABLE
            (
                line                 numeric(10, 0),
                Name                 character varying,
                Attributes           text,
                HUQty                numeric,
                HUName               character varying,
                qtylu                numeric,
                LU_Name              character varying,
                QtyEnteredInPriceUOM numeric,
                PriceEntered         numeric,
                UOMSymbol            character varying(10),
                StdPrecision         numeric(10, 0),
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
                order_description    character varying(1024),
                PricePattern         text,
                AmountPattern        text,
                QtyPattern           text,
                PriceQtyPattern      text,
                week_year            character varying
            )
AS
$$
SELECT ol.line,
       COALESCE(pt.Name, p.name)                                               AS Name,
       CASE
           WHEN LENGTH(Attributes) > 15
               THEN Attributes || E'\n'
               ELSE Attributes
       END                                                                     AS Attributes,
       ol.QtyEnteredTU                                                         AS HUQty,
       CASE
           WHEN piit.M_HU_PI_Version_ID = 101 OR ol.QtyEnteredTU IS NULL
               THEN NULL
               ELSE ip.name
       END                                                                     AS HUName,
       ol.qtylu,
       piv.name                                                                AS LU_Name,
       ol.QtyEnteredInPriceUOM                                                 AS QtyEnteredInPriceUOM,
       ol.PriceEntered                                                         AS PriceEntered,
       COALESCE(puomt.UOMSymbol, puom.UOMSymbol)                               AS UOMSymbol,
       uom.StdPrecision,
       ol.linenetamt                                                           AS linenetamt,
       CASE
           WHEN ROUND(discount, 0) = discount THEN ROUND(discount, 0)
           WHEN ROUND(discount, 1) = discount THEN ROUND(discount, 1)
                                              ELSE ROUND(discount, 2)
       END                                                                     AS discount,
       bp.isDiscountPrinted,
       CASE
           WHEN ROUND(rate, 0) = rate THEN ROUND(rate, 0)
           WHEN ROUND(rate, 1) = rate THEN ROUND(rate, 1)
                                      ELSE ROUND(rate, 2)
       END::character varying                                                  AS rate,
       isPrintTax,
       ol.Description,
       -- in case there is no C_BPartner_Product, fallback to the default ones
       COALESCE(NULLIF(bpp.ProductNo, ''), p.value)                            AS bp_product_no,
       COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name)                  AS bp_product_name,
       c.cursymbol,
       p.value                                                                 AS p_value,
       COALESCE(pt.description, p.description)                                 AS p_description,
       p.documentnote                                                          AS p_documentnote,
       o.description                                                           AS order_description,
       report.getPricePatternForJasper(o.m_pricelist_id)                       AS PricePattern,
       report.getAmountPatternForJasper(c.c_currency_id)                       AS AmountPattern,
       report.getQtyPattern(uom.StdPrecision)                                  AS QtyPattern,
       report.getQtyPattern(puom.StdPrecision)                                 AS PriceQtyPattern,
       TO_CHAR(ol.datepromised, 'WW') || '.' || TO_CHAR(ol.datepromised, 'YY') AS week_year

FROM C_OrderLine ol
         INNER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID AND o.isActive = 'Y'
         LEFT OUTER JOIN C_BPartner bp ON ol.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
         INNER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID AND bpg.isActive = 'Y'
         LEFT OUTER JOIN M_HU_PI_Item_Product ip ON ol.M_HU_PI_Item_Product_ID = ip.M_HU_PI_Item_Product_ID AND ip.isActive = 'Y'
         LEFT OUTER JOIN M_HU_PI_Item piit ON ip.M_HU_PI_Item_ID = piit.M_HU_PI_Item_ID AND piit.isActive = 'Y'

    -- LU details
         LEFT OUTER JOIN M_HU_PI_Version piv ON piv.M_HU_PI_ID = ol.m_lu_hu_pi_id AND piv.hu_unittype IN ('LU', 'V') AND piv.iscurrent = 'Y'

    -- Product and its translation
         LEFT OUTER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
         LEFT OUTER JOIN M_Product_Trl pt ON ol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_ad_language AND pt.isActive = 'Y'
         LEFT OUTER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'

         LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID
    AND p.M_Product_ID = bpp.M_Product_ID AND bpp.isActive = 'Y' AND bpp.UsedForVendor = 'Y'
    -- Price Unit of measurement and its translation
         LEFT OUTER JOIN C_UOM puom ON ol.Price_UOM_ID = puom.C_UOM_ID
         LEFT OUTER JOIN C_UOM_Trl puomt ON ol.Price_UOM_ID = puomt.C_UOM_ID AND puomt.AD_Language = p_ad_language

    -- Unit of measurement and its translation
         LEFT OUTER JOIN C_UOM uom ON ol.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
         LEFT OUTER JOIN C_UOM_Trl uomt ON ol.Price_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_ad_language AND uomt.isActive = 'Y'

    --
    -- Tax
         LEFT OUTER JOIN C_Tax t ON ol.C_Tax_ID = t.C_Tax_ID AND t.isActive = 'Y'
    -- Get Attributes
         LEFT OUTER JOIN (SELECT STRING_AGG(att.ai_value, ', ' ORDER BY LENGTH(att.ai_value)) AS Attributes, att.M_AttributeSetInstance_ID, ol.C_OrderLine_ID
                          FROM Report.fresh_Attributes att
                                   JOIN C_OrderLine ol ON att.M_AttributeSetInstance_ID = ol.M_AttributeSetInstance_ID
                          WHERE att.IsPrintedInDocument = 'Y'
                            AND ol.C_Order_ID = p_record_id

                          GROUP BY att.M_AttributeSetInstance_ID, ol.C_OrderLine_ID) att ON ol.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID AND ol.C_OrderLine_ID = att.C_OrderLine_ID

         LEFT JOIN C_Currency c ON o.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'

         INNER JOIN M_PriceList pl ON pl.m_pricelist_id = o.m_pricelist_id

WHERE ol.C_Order_ID = p_record_id
  AND ol.isActive = 'Y'
  AND COALESCE(pc.M_Product_Category_ID, -1) != getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID)
ORDER BY ol.line

$$
    LANGUAGE sql STABLE
;



DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details (IN p_Record_ID   numeric,
                                                                                     IN p_AD_Language Character Varying(6))
;


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details(IN p_Record_ID   numeric,
                                                                            IN p_AD_Language Character Varying(6))
    RETURNS TABLE
            (
                Line                   Numeric(10, 0),
                Name                   Character Varying,
                Attributes             Text,
                HUQty                  Numeric,
                HUName                 Text,
                qtyEntered             Numeric,
                PriceEntered           Numeric,
                UOMSymbol              Character Varying(10),
                StdPrecision           Numeric(10, 0),
                LineNetAmt             Numeric,
                Discount               Numeric,
                IsDiscountPrinted      Character(1),
                IsShipmentPricePrinted Character(1),
                Description            Character Varying,
                bp_product_no          character varying(30),
                bp_product_name        character varying(100),
                best_before_date       text,
                lotno                  character varying,
                p_value                character varying(30),
                p_description          character varying(255),
                inout_description      character varying(255),
                iscampaignprice        character(1),
                qtyordered             Numeric,
                orderUOMSymbol         Character Varying(10),
                catchweight            Numeric,
                weight_uom             Character Varying,
                docstatus              char(2),
                QtyPattern             text
            )
AS
$$
SELECT iol.line,
       COALESCE(pt.Name, p.name)                                                                       AS NAME,
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
       COALESCE(ic.PriceActual_Override, ic.PriceActual) * iol.MovementQty * COALESCE(multiplyrate, 1) AS linenetamt,
       COALESCE(ic.Discount_Override, ic.Discount)                                                     AS Discount,
       bp.isDiscountPrinted,
       bp.IsShipmentPricePrinted,
       CASE
           WHEN report.IsHiddenReportElement(io.C_DocType_ID, 'description') = 'N' THEN
               COALESCE(iol.Description, ol.Description)
       END                                                                                             AS description,
       -- in case there is no C_BPartner_Product, fallback to the default ones
       COALESCE(NULLIF(bpp.ProductNo, ''), p.value)                                                    AS bp_product_no,
       COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name)                                          AS bp_product_name,
       TO_CHAR(att.best_before_date :: DATE, 'dd.MM.YYYY')                                             AS best_before_date,
       att.lotno,
       p.value                                                                                         AS p_value,
       CASE
           WHEN report.IsHiddenReportElement(io.C_DocType_ID, 'p_description') = 'N' THEN
               p.description
       END                                                                                             AS p_description,
       io.description                                                                                  AS inout_description,
       ol.iscampaignprice,
       ol.qtyordered,
       COALESCE(uomt_ol.UOMSymbol, uom_ol.UOMSymbol)                                                   AS orderUOMSymbol,
       w.catchweight                                                                                   AS catchweight,
       w.weight_uom                                                                                    AS weight_uom,
       io.docstatus,
       report.getQtyPattern(uom.StdPrecision)                                                          AS QtyPattern
FROM M_InOutLine iol
         INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID
         LEFT OUTER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID
         LEFT OUTER JOIN (SELECT AVG(ic.PriceEntered_Override) AS PriceEntered_Override,
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
                          GROUP BY Price_UOM_ID, iciol.M_InOutLine_ID) ic ON iol.M_InOutLine_ID = ic.M_InOutLine_ID

    -- get details from order line
         LEFT OUTER JOIN c_orderline ol ON ol.c_orderline_id = iol.c_orderline_id
         LEFT OUTER JOIN C_UOM uom_ol ON uom_ol.C_UOM_ID = ol.C_UOM_ID
         LEFT OUTER JOIN C_UOM_Trl uomt_ol
                         ON uomt_ol.C_UOM_ID = uom_ol.C_UOM_ID AND uomt_ol.AD_Language = p_AD_Language AND uomt_ol.isActive = 'Y'

    -- Get Packing instruction
         LEFT OUTER JOIN
     (SELECT STRING_AGG(DISTINCT NAME, E'\n'
                        ORDER BY NAME) AS NAME,
             M_InOutLine_ID
      FROM (SELECT DISTINCT
                -- 08604 - in IT1 only one PI was shown though 2 were expected. Only the fallback can do this, so we use it first
                COALESCE(pifb.name, pi.name) AS NAME,
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
              AND iol.isActive = 'Y') x
      GROUP BY M_InOutLine_ID) pi ON iol.M_InOutLine_ID = pi.M_InOutLine_ID
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
         LEFT OUTER JOIN LATERAL (SELECT STRING_AGG(AT.ai_value, ', '
                                         ORDER BY LENGTH(AT.ai_value), AT.ai_value)
                                         FILTER (WHERE AT.at_value NOT IN ('HU_BestBeforeDate', 'Lot-Nummer'))
                                                                                      AS Attributes,

                                         AT.M_AttributeSetInstance_ID,
                                         STRING_AGG(REPLACE(AT.ai_value, 'MHD: ', ''), ', ')
                                         FILTER (WHERE AT.at_value LIKE 'HU_BestBeforeDate')
                                                                                      AS best_before_date,
                                         STRING_AGG(ai_value, ', ')
                                         FILTER (WHERE AT.at_value LIKE 'Lot-Nummer') AS lotno

                                  FROM Report.fresh_Attributes(iol.M_AttributeSetInstance_ID) AT
                                  WHERE AT.IsPrintedInDocument = 'Y'
                                  GROUP BY AT.M_AttributeSetInstance_ID) att ON TRUE

         LEFT OUTER JOIN
     de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(p.M_Product_ID, bp.C_BPartner_ID,
                                                                      att.M_AttributeSetInstance_ID) AS bpp ON TRUE
         LEFT OUTER JOIN
     de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Sum_Weight(p_Record_ID, p_AD_Language) AS w ON TRUE
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



DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Details (IN p_C_Invoice_ID numeric,
                                                                                       IN p_AD_Language  Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Details(IN p_C_Invoice_ID numeric,
                                                                                         IN p_AD_Language  Character Varying(6))
    RETURNS TABLE
            (
                InOuts                     text,
                docType                    character varying,
                reference                  character varying(40),
                shipLocation               character varying(60),
                Tour                       text,
                week_year                  character varying,
                InOuts_DateFrom            text,
                InOuts_DateTo              text,
                InOuts_IsSameDate          boolean,
                InOuts_IsDataComplete      boolean,
                IsHU                       boolean,
                line                       numeric(10, 0),
                Name                       character varying,
                Attributes                 text,
                HUQty                      numeric,
                HUName                     text,
                QtyInvoicedInPriceUOM      numeric,
                shipped                    numeric,
                retour                     numeric,
                PriceActual                numeric,
                PriceEntered               numeric,
                Discount                   numeric,
                UOM                        character varying(10),
                PriceUOM                   character varying(10),
                StdPrecision               numeric(10, 1),
                linenetamt                 numeric,
                rate                       numeric,
                isdiscountprinted          character,
                isprinttax                 character,
                description                character varying,
                productdescription         character varying,
                bp_product_no              character varying,
                bp_product_name            character varying,
                p_value                    character varying,
                p_description              character varying,
                invoice_description        character varying,
                cursymbol                  character varying,
                iscampaignprice            character,
                isprintwhenpackingmaterial character,
                PricePattern               text,
                AmountPattern              text,
                QtyPattern                 text,
                PriceQtyPattern            text,
                catchweight                numeric,
                weight_uom                 character varying(10),
                customs_number             text
            )
AS
$$
SELECT io.DocType || ': ' || io.DocNo                         AS InOuts,
       io.docType,
       io.reference,
       io.shipLocation,
       io.Tour,
       io.week_year,
       TO_CHAR(io.DateFrom, 'DD.MM.YYYY')                     AS InOuts_DateFrom,
       TO_CHAR(io.DateTo, 'DD.MM.YYYY')                       AS InOuts_DateTo,
       DateFrom :: date = DateTo :: Date                      AS InOuts_IsSameDate,
       DocNo IS NOT NULL                                      AS InOuts_IsDataComplete,
       COALESCE(pc.IsHU, FALSE)                               AS IsHU,
       il.line,
       COALESCE(pt.name, p.name)                              AS Name,
       COALESCE(
               CASE
                   WHEN LENGTH(att.Attributes) > 15
                       THEN att.Attributes || E'\n'
                       ELSE att.Attributes
               END,
               ''
       )                                                      AS Attributes,
       il.QtyenteredTU                                        AS HUQty,
       piip.name                                              AS HUName,

       CASE
           WHEN report.IsHiddenReportElement(i.C_DocType_ID, 'QtyInvoicedInPriceUOM') = 'N' THEN
               il.QtyInvoicedInPriceUOM
       END                                                    AS QtyInvoicedInPriceUOM,
       CASE
           WHEN il.QtyEntered > 0
               THEN il.QtyEntered
               ELSE 0
       END                                                    AS shipped,
       CASE
           WHEN il.QtyEntered < 0
               THEN il.QtyEntered * -1
               ELSE 0
       END                                                    AS retour,
       il.PriceActual,
       il.PriceEntered,
       il.Discount,
       COALESCE(uomt.UOMSymbol, uom.UOMSymbol)                AS UOM,
       CASE
           WHEN report.IsHiddenReportElement(i.C_DocType_ID, 'UOMSymbol') = 'N' THEN
               COALESCE(puomt.UOMSymbol, puom.UOMSymbol)
       END                                                    AS PriceUOM,

       puom.StdPrecision,
       il.linenetamt,
       t.rate,
       i.isDiscountPrinted,
       bpg.IsPrintTax,
       il.Description,
       il.ProductDescription,
       -- in case there is no C_BPartner_Product, fallback to the default ones
       COALESCE(NULLIF(bpp.ProductNo, ''), p.value)           AS bp_product_no,
       COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name) AS bp_product_name,
       p.value                                                AS p_value,
       p.description                                          AS p_description,
       i.description                                          AS invoice_description,
       c.cursymbol,
       ol.iscampaignprice,
       p.IsPrintWhenPackingMaterial,
       report.getPricePatternForJasper(i.m_pricelist_id)      AS PricePattern,
       report.getAmountPatternForJasper(c.c_currency_id)      AS AmountPattern,
       report.getQtyPattern(uom.StdPrecision)                 AS QtyPattern,
       report.getQtyPattern(puom.StdPrecision)                AS PriceQtyPattern,
       w.catchweight,
       w.weight_uom,
       pcus.value || ' ' || COALESCE(pcus.name, '')           AS customs_number
FROM C_InvoiceLine il
         INNER JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID
         INNER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID
         LEFT OUTER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID

    -- get promotional price details from order line
         LEFT OUTER JOIN c_orderline ol ON ol.c_orderline_id = il.c_orderline_id

    -- Get Product and its translation
         LEFT OUTER JOIN M_Product p ON il.M_Product_ID = p.M_Product_ID
         LEFT OUTER JOIN M_Product_Trl pt ON il.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_AD_Language

    -- Get customs number
         LEFT OUTER JOIN m_customstariff pcus ON p.M_CustomsTariff_ID = pcus.M_CustomsTariff_ID

         LEFT OUTER JOIN LATERAL
    (
    SELECT M_Product_Category_ID =
           getSysConfigAsNumeric('PackingMaterialProductCategoryID', il.AD_Client_ID, il.AD_Org_ID) AS isHU,
           M_Product_Category_ID
    FROM M_Product_Category
    ) pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID

    -- Get Unit of measurement and its translation
         LEFT OUTER JOIN C_UOM uom ON il.C_UOM_ID = uom.C_UOM_ID
         LEFT OUTER JOIN C_UOM_Trl uomt ON il.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_AD_Language
         LEFT OUTER JOIN C_UOM puom ON il.Price_UOM_ID = puom.C_UOM_ID
         LEFT OUTER JOIN C_UOM_Trl puomt
                         ON il.Price_UOM_ID = puomt.C_UOM_ID AND puomt.AD_Language = p_AD_Language
    -- Tax rate
         LEFT OUTER JOIN C_Tax t ON il.C_Tax_ID = t.C_Tax_ID

         LEFT OUTER JOIN C_Currency c ON i.C_Currency_ID = c.C_Currency_ID

    -- Get shipment details
         LEFT OUTER JOIN (SELECT DISTINCT ON (x.C_InvoiceLine_ID) x.C_InvoiceLine_ID,
                                                                  First_Agg(x.DocType)             AS DocType,
                                                                  STRING_AGG(x.DocNo, ', '
                                                                             ORDER BY x.DocNo)     AS DocNo,
                                                                  STRING_AGG(x.week_year, ', '
                                                                             ORDER BY x.week_year) AS week_year,
                                                                  MIN(x.DateFrom)                  AS DateFrom,
                                                                  MAX(x.DateTo)                    AS DateTo,
                                                                  STRING_AGG(x.reference, ', '
                                                                             ORDER BY x.DocNo)     AS reference,
                                                                  x.shipLocation,
                                                                  x.tour,
                                                                  x.M_InOut_ID
                          FROM (SELECT DISTINCT ON (iliol.C_InvoiceLine_ID) iliol.C_InvoiceLine_ID,
                                                                            First_Agg(COALESCE(dtt.Printname, dt.Printname)
                                                                                      ORDER BY io.DocumentNo)                                       AS DocType,
                                                                            STRING_AGG(io.DocumentNo, ', '
                                                                                       ORDER BY io.DocumentNo)                                      AS DocNo,
                                                                            TO_CHAR(io.MovementDate, 'WW') || '.' || TO_CHAR(io.MovementDate, 'YY') AS week_year,
                                                                            MIN(io.MovementDate)                                                    AS DateFrom,
                                                                            MAX(io.MovementDate)                                                    AS DateTo,
                                                                            io.poreference                                                          AS reference,
                                                                            bpl.name                                                                AS shipLocation,
                                                                            t.name                                                                  AS tour,
                                                                            io.M_InOut_ID
                                FROM (SELECT DISTINCT ON (C_InvoiceLine_ID) M_InOut_ID,
                                                                            C_InvoiceLine_ID,
                                                                            M_InOutLine_ID
                                      FROM Report.fresh_IL_to_IOL_V
                                      WHERE C_Invoice_ID = p_C_Invoice_ID) iliol
                                         LEFT OUTER JOIN M_InOut io ON iliol.M_InOut_ID = io.M_InOut_ID
                                    AND io.DocStatus IN ('CO', 'CL')
                                    /* task 09290 */
                                         INNER JOIN C_BPartner_Location bpl
                                                    ON io.C_BPartner_Location_ID = bpl.C_BPartner_Location_ID
                                         LEFT OUTER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID
                                         LEFT OUTER JOIN C_DocType_Trl dtt
                                                         ON io.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_AD_Language
                                         LEFT OUTER JOIN M_tour t ON io.m_tour_id = t.m_tour_id

                                GROUP BY C_InvoiceLine_ID, io.poreference, bpl.C_BPartner_Location_ID, t.name, io.M_InOut_ID) x

                          GROUP BY x.C_InvoiceLine_ID, x.shipLocation, x.tour, x.M_InOut_ID) io ON il.C_InvoiceLine_ID = io.C_InvoiceLine_ID
         LEFT OUTER JOIN
     de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Sum_Weight(io.m_inout_id, p_AD_Language) AS w ON TRUE

         -- Get Packing instruction
         LEFT OUTER JOIN (SELECT STRING_AGG(Name, E'\n'
                                            ORDER BY Name) AS Name,
                                 C_InvoiceLine_ID
                          FROM (SELECT DISTINCT COALESCE(pifb.name, pi.name) AS name,
                                                C_InvoiceLine_ID
                                FROM Report.fresh_IL_TO_IOL_V iliol
                                         INNER JOIN M_InOutLine iol
                                                    ON iliol.M_InOutLine_ID = iol.M_InOutLine_ID
                                         LEFT OUTER JOIN M_HU_PI_Item_Product pi
                                                         ON iol.M_HU_PI_Item_Product_ID = pi.M_HU_PI_Item_Product_ID
                                         LEFT OUTER JOIN M_HU_PI_Item piit
                                                         ON piit.M_HU_PI_Item_ID = pi.M_HU_PI_Item_ID

                                         LEFT OUTER JOIN M_HU_Assignment asgn
                                                         ON asgn.AD_Table_ID = ((SELECT get_Table_ID('M_InOutLine')))
                                                             AND asgn.Record_ID = iol.M_InOutLine_ID
                                         LEFT OUTER JOIN M_HU tu ON asgn.M_TU_HU_ID = tu.M_HU_ID
                                         LEFT OUTER JOIN M_HU_PI_Item_Product pifb
                                                         ON tu.M_HU_PI_Item_Product_ID = pifb.M_HU_PI_Item_Product_ID
                                         LEFT OUTER JOIN M_HU_PI_Item pit
                                                         ON pifb.M_HU_PI_Item_ID = pit.M_HU_PI_Item_ID
                                    --
                                         LEFT OUTER JOIN M_HU_PI_Version piv
                                                         ON piv.M_HU_PI_Version_ID = COALESCE(pit.M_HU_PI_Version_ID, piit.M_HU_PI_Version_ID)
                                WHERE piv.M_HU_PI_Version_ID != 101
                                  AND iliol.C_Invoice_ID = p_C_Invoice_ID) pi
                          GROUP BY C_InvoiceLine_ID) piip ON il.C_InvoiceLine_ID = piip.C_InvoiceLine_ID

    -- Get Attributes
         LEFT OUTER JOIN
     (SELECT STRING_AGG(ai_value, ', '
                        ORDER BY LENGTH(ai_value)) AS Attributes,
             att.M_AttributeSetInstance_ID,
             il.C_InvoiceLine_ID
      FROM report.fresh_Attributes att
               JOIN C_InvoiceLine il ON il.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID
      WHERE att.IsPrintedInDocument = 'Y'
        AND il.C_Invoice_ID = p_C_Invoice_ID
      GROUP BY att.M_AttributeSetInstance_ID, il.C_InvoiceLine_ID) att ON il.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID AND il.C_InvoiceLine_ID = att.C_InvoiceLine_ID

         LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID
    AND p.M_Product_ID = bpp.M_Product_ID

    -- get inoutline - to order by it. The main error i think is that the lines in invoice are not ordered anymore as they used to
         LEFT OUTER JOIN M_InOutLine miol ON il.M_InOutLine_ID = miol.M_InOutLine_ID
    --ordering gebinde if config exists
         LEFT OUTER JOIN M_InOut mio ON mio.M_Inout_ID = miol.M_Inout_ID
         LEFT OUTER JOIN C_DocType mdt ON mio.C_DocType_ID = mdt.C_DocType_ID
         LEFT OUTER JOIN C_DocLine_Sort dls ON mdt.DocBaseType = dls.DocBaseType
    AND EXISTS(SELECT 0
               FROM C_BP_DocLine_Sort bpdls
               WHERE bpdls.C_DocLine_Sort_ID = dls.C_DocLine_Sort_ID
                 AND bpdls.C_BPartner_ID = mio.C_BPartner_ID)
         LEFT OUTER JOIN C_DocLine_Sort_Item dlsi
                         ON dls.C_DocLine_Sort_ID = dlsi.C_DocLine_Sort_ID AND dlsi.M_Product_ID = il.M_Product_ID
WHERE il.C_Invoice_ID = p_C_Invoice_ID
ORDER BY io.DateFrom,
         io.DocNo,
         COALESCE(pc.IsHU, FALSE),
         CASE
             WHEN COALESCE(pc.IsHU, FALSE) = 't' AND dlsi.SeqNo IS NOT NULL
                 THEN dlsi.SeqNo
         END,
         CASE
             WHEN COALESCE(pc.IsHU, FALSE) = 't' AND dlsi.SeqNo IS NULL
                 THEN p.name
         END,
         miol.line,
         line

$$
    LANGUAGE sql
    STABLE
;



DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Details (IN p_Record_ID   numeric,
                                                                                        IN p_AD_Language Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_InOut_Details(IN p_Record_ID   numeric,
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
                isInDispute            character(1),
                Description            Character Varying,
                bp_product_no          character varying(30),
                bp_product_name        character varying(100),
                line                   numeric,
                QtyPattern             text
            )
AS
$$
SELECT *
FROM (SELECT Attributes,
             Name, -- product
             SUM(HUQty)                                                         AS HUQty,
             HUName,
             SUM(MovementQty)                                                   AS MovementQty,
             UOMSymbol,
             StdPrecision,
             QualityDiscountPercent,
             QualityNote,
             isInDispute,
             iol.Description,
             bp_product_no,
             bp_product_name,
             CAST((ROW_NUMBER() OVER (ORDER BY MAX(iol.line))) * 10 AS numeric) AS line,
             QtyPattern

      FROM
          -- Sub select to get all in out lines we need. They are in a subselect so we can neatly group by the attributes
          -- (Otherwise we'd have to copy the attributes-sub-select in the group by clause. Hint: That would suck)
          (SELECT iol.M_InOutLine_ID,
                  Attributes,
                  COALESCE(pt.Name, p.name)                                                                          AS Name,
                  iol.QtyEnteredTU                                                                                   AS HUQty,
                  CASE WHEN iol.QtyEnteredTU IS NULL THEN NULL ELSE pi.name END                                      AS HUName,
                  iol.MovementQty                                                                                    AS MovementQty,
                  COALESCE(uomt.UOMSymbol, uom.UOMSymbol)                                                            AS UOMSymbol,
                  uom.StdPrecision,

                  CASE
                      WHEN iol.isInDispute = 'N' THEN NULL
                                                 ELSE
                                                     COALESCE(ic.QualityDiscountPercent_Override, ic.QualityDiscountPercent)
                  END                                                                                                AS QualityDiscountPercent,
                  CASE
                      WHEN iol.isInDispute = 'N' THEN NULL
                                                 ELSE
                                                     (SELECT rs.QualityNote
                                                      FROM M_ReceiptSchedule rs
                                                      WHERE rs.isActive = 'Y'
                                                        AND EXISTS(SELECT 0
                                                                   FROM M_ReceiptSchedule_Alloc rsa
                                                                   WHERE rsa.m_receiptschedule_id = rs.m_receiptschedule_id
                                                                     AND rs.C_OrderLine_ID = ic.C_OrderLine_ID
                                                                     AND rsa.isActive = 'Y'))
                  END                                                                                                AS QualityNote,
                  iol.isInDispute,
                  CASE WHEN iol.Description IS NOT NULL AND iol.Description != '' THEN iol.Description ELSE NULL END AS Description,
                  -- in case there is no C_BPartner_Product, fallback to the default ones
                  COALESCE(NULLIF(bpp.ProductNo, ''), p.value)                                                       AS bp_product_no,
                  COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name)                                             AS bp_product_name,
                  iol.line,
                  report.getQtyPattern(uom.StdPrecision)                                                             AS QtyPattern
           FROM
               -- All In Outs linked to the order
               (SELECT DISTINCT io.*
                FROM
                    -- All In Out Lines directly linked to the order
                    M_InOutLine iol
                        INNER JOIN C_OrderLine ol ON iol.C_OrderLine_ID = ol.C_OrderLine_ID AND ol.isActive = 'Y'
                        INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'
                WHERE ol.C_Order_ID = p_Record_ID
                  AND io.DocStatus IN ('CO', 'CL')
                  AND iol.isActive = 'Y') io
                   --
                   /*
                    * Now, join all in out lines of those in outs. Might be more than the in out lines selected in the previous
                    * sub select because not all in out lines are linked to the order (e.g Packing material). NOTE: Due to the
                    * process we assume, that all lines of one inout belong to only one order
                    */
                   INNER JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID AND iol.isActive = 'Y'
                   LEFT OUTER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID
                   -- Product and its translation
                   INNER JOIN M_Product p ON iol.M_Product_ID = p.M_Product_ID
                   LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_AD_Language AND pt.isActive = 'Y'
                   LEFT JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID
                   LEFT OUTER JOIN C_BPartner_Product bpp
                                   ON bp.C_BPartner_ID = bpp.C_BPartner_ID
                                       AND p.M_Product_ID = bpp.M_Product_ID
                   -- Unit of measurement & its translation
                   INNER JOIN C_UOM uom ON iol.C_UOM_ID = uom.C_UOM_ID
                   LEFT OUTER JOIN C_UOM_Trl uomt ON iol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_AD_Language AND uomt.isActive = 'Y'
                   -- Packing Instruction
                   LEFT OUTER JOIN
               (SELECT STRING_AGG(DISTINCT pifb.name, E'\n' ORDER BY pifb.name) AS NAME,
                       iol.M_InOutLine_ID
                FROM M_InOutLine iol
                         INNER JOIN M_HU_Assignment asgn ON asgn.AD_Table_ID = ((SELECT get_Table_ID('M_InOutLine'))) AND asgn.isActive = 'Y'
                    AND asgn.Record_ID = iol.M_InOutLine_ID
                         INNER JOIN M_HU tu ON asgn.M_TU_HU_ID = tu.M_HU_ID --
                         INNER JOIN M_HU_PI_Item_Product pifb ON tu.M_HU_PI_Item_Product_ID = pifb.M_HU_PI_Item_Product_ID AND pifb.isActive = 'Y'
                         INNER JOIN M_HU_PI_Item piit ON pifb.M_HU_PI_Item_ID = piit.M_HU_PI_Item_ID AND piit.isActive = 'Y'
                WHERE piit.M_HU_PI_Version_ID != 101
                  AND iol.isActive = 'Y'
                  AND EXISTS(SELECT 1
                             FROM M_InOutLine iol2
                                      INNER JOIN C_OrderLine ol ON iol2.C_OrderLine_ID = ol.C_OrderLine_ID AND ol.isActive = 'Y'
                             WHERE ol.C_Order_ID = p_Record_ID
                               AND iol.M_InOut_ID = iol2.M_InOut_ID
                               AND iol2.isActive = 'Y')
                GROUP BY M_InOutLine_ID) pi ON iol.M_InOutLine_ID = pi.M_InOutLine_ID
                   -- Attributes
                   LEFT OUTER JOIN
               (SELECT /** Jasper Servlet runs under linux, jasper client under windows (mostly). both have different fonts therefore, when
				  * having more than 2 lines, the field is too short to display all lines in the windows font to avoid this I add an extra
				  * line as soon as the attributes string has more than 15 characters (which is still very likely to fit in two lines)
				  */
                    CASE WHEN LENGTH(Attributes) > 15 THEN Attributes || E'\n' ELSE Attributes END AS Attributes,
                    M_AttributeSetInstance_ID,
                    M_InOutLine_ID
                FROM (SELECT STRING_AGG(att.ai_value, ', ' ORDER BY att.M_AttributeSetInstance_ID, LENGTH(att.ai_value), att.ai_value) AS Attributes,
                             att.M_AttributeSetInstance_ID,
                             iol.M_InOutLine_ID
                      FROM Report.fresh_Attributes att
                               INNER JOIN M_InOutLine iol ON att.M_AttributeSetInstance_ID = iol.M_AttributeSetInstance_ID AND iol.isActive = 'Y'
                               INNER JOIN C_OrderLine ol ON iol.C_OrderLine_ID = ol.C_OrderLine_ID AND ol.isActive = 'Y'
                      WHERE att.IsPrintedInDocument = 'Y'
                        AND ol.C_Order_ID = p_Record_ID
                      GROUP BY att.M_AttributeSetInstance_ID, iol.M_InOutLine_ID) x) a ON iol.M_AttributeSetInstance_ID = a.M_AttributeSetInstance_ID AND iol.M_InOutLine_ID = a.M_InOutLine_ID

                   -- Quality (is taken from Invoice candidates because there the quality is already aggregated)
                   LEFT OUTER JOIN C_InvoiceCandidate_InOutLine iciol ON iol.M_InOutLine_ID = iciol.M_InOutLine_ID AND iciol.isActive = 'Y'
                   LEFT OUTER JOIN C_Invoice_Candidate ic ON ic.C_Invoice_Candidate_ID = iciol.C_Invoice_Candidate_ID AND ic.isActive = 'Y'
           WHERE COALESCE(pc.M_Product_Category_ID
                     , -1) != getSysConfigAsNumeric('PackingMaterialProductCategoryID'
                     , iol.AD_Client_ID
                     , iol.AD_Org_ID)) iol
      GROUP BY Attributes,
               Name, -- product
               HUName,
               UOMSymbol,
               QualityDiscountPercent,
               QualityNote,
               isInDispute,
               StdPrecision,
               Description,
               bp_product_no,
               bp_product_name,
               QtyPattern
      ORDER BY Name, MIN(M_InOutLine_ID), line) AS result
ORDER BY line

$$
    LANGUAGE sql STABLE
;


DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Invoice_Details (IN p_record_id numeric,
                                                                                          IN p_language  Character Varying(6))
;

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Invoice_Details(IN p_record_id numeric,
                                                                                 IN p_language  Character Varying(6))
    RETURNS TABLE
            (
                inouts                text,
                doctype               character varying,
                inouts_datefrom       text,
                inouts_isdatacomplete boolean,
                ishu                  boolean,
                line                  numeric,
                description           character varying,
                productdescription    character varying,
                name                  character varying,
                attributes            text,
                huqty                 numeric,
                huname                text,
                qtyinvoicedinpriceuom numeric,
                shipped               numeric,
                retour                numeric,
                priceactual           numeric,
                priceentered          numeric,
                uom                   character varying,
                priceuom              character varying,
                stdprecision          numeric,
                linenetamt            numeric,
                rate                  numeric,
                isprinttax            character,
                bp_product_no         character varying,
                bp_product_name       character varying,
                p_value               character varying,
                p_description         character varying,
                invoice_description   character varying,
                cursymbol             character varying,
                PricePattern          text,
                AmountPattern         text,
                QtyPattern            text,
                PriceQtyPattern       text
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT *
FROM (SELECT COALESCE(io1.DocType, io2.DocType) || ': ' || COALESCE(io1.DocNo, io2.DocNo) AS InOuts,
             COALESCE(io1.DocType, io2.DocType)                                           AS DocType,
             TO_CHAR(COALESCE(io1.DateFrom, io2.DateFrom), 'DD.MM.YYYY')                  AS InOuts_DateFrom,
             COALESCE(io1.DocNo, io2.DocNo) IS NOT NULL                                   AS InOuts_IsDataComplete,
             COALESCE(pc.IsHU, FALSE)                                                     AS IsHU,
             MAX(il.line)                                                                 AS line,
             -- ts: QnD: appending the invoice line description to the product name.
             -- TODO: create a dedicated field for it etc
             il.Description,
             il.ProductDescription,
             COALESCE(pt.name, p.name)                                                    AS Name,
             COALESCE(
                     CASE
                         WHEN LENGTH(att.Attributes) > 15
                             THEN att.Attributes || E'\n'
                             ELSE att.Attributes
                     END,
                     ''
             )                                                                            AS Attributes,
             SUM(il.QtyenteredTU)                                                         AS HUQty,
             piip.name                                                                    AS HUName,
             SUM(il.QtyInvoicedInPriceUOM)                                                AS qtyinvoicedinpriceuom,
             SUM(CASE
                     WHEN il.QtyEntered > 0 THEN il.QtyEntered
                                            ELSE 0
                 END)                                                                     AS shipped,
             SUM(CASE
                     WHEN il.QtyEntered < 0 THEN il.QtyEntered * -1
                                            ELSE 0
                 END)                                                                     AS retour,
             il.PriceActual,
             il.PriceEntered,
             COALESCE(uomt.UOMSymbol, uom.UOMSymbol)                                      AS UOM,
             COALESCE(puomt.UOMSymbol, puom.UOMSymbol)                                    AS PriceUOM,
             puom.StdPrecision,
             SUM(il.linenetamt)                                                           AS linenetamt,
             t.rate,
             bpg.IsPrintTax,
             -- in case there is no C_BPartner_Product, fallback to the default ones
             COALESCE(NULLIF(bpp.ProductNo, ''), p.value)                                 AS bp_product_no,
             COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name)                       AS bp_product_name,
             p.value                                                                      AS p_value,
             p.description                                                                AS p_description,
             i.description                                                                AS invoice_description,
             c.cursymbol,
             report.getPricePatternForJasper(i.m_pricelist_id)                            AS PricePattern,
             report.getAmountPatternForJasper(c.c_currency_id)                            AS AmountPattern,
             report.getQtyPattern(uom.StdPrecision)                                       AS QtyPattern,
             report.getQtyPattern(puom.StdPrecision)                                      AS PriceQtyPattern
      FROM C_InvoiceLine il
               INNER JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID
               INNER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID
               LEFT OUTER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID

          -- Get Product and its translation
               LEFT OUTER JOIN M_Product p ON il.M_Product_ID = p.M_Product_ID
               LEFT OUTER JOIN M_Product_Trl pt ON il.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_language
               LEFT OUTER JOIN LATERAL
          (
          SELECT M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', il.AD_Client_ID, il.AD_Org_ID) AS isHU,
                 M_Product_Category_ID
          FROM M_Product_Category
          ) pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID

               LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID
          AND p.M_Product_ID = bpp.M_Product_ID

          -- Get Unit of measurement and its translation
               LEFT OUTER JOIN C_UOM uom ON il.C_UOM_ID = uom.C_UOM_ID
               LEFT OUTER JOIN C_UOM_Trl uomt ON il.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_language
               LEFT OUTER JOIN C_UOM puom ON il.Price_UOM_ID = puom.C_UOM_ID
               LEFT OUTER JOIN C_UOM_Trl puomt ON il.Price_UOM_ID = puomt.C_UOM_ID AND puomt.AD_Language = p_language

          -- Tax rate
               LEFT OUTER JOIN C_Tax t ON il.C_Tax_ID = t.C_Tax_ID

               LEFT OUTER JOIN C_Currency c ON i.C_Currency_ID = c.C_Currency_ID

          -- Get shipment grouping header
               LEFT OUTER JOIN (SELECT o.C_InvoiceLine_ID,
                                       First_Agg(COALESCE(dtt.Printname, dt.Printname) ORDER BY io.DocumentNo) AS DocType,
                                       First_agg(DISTINCT io.DocumentNo ORDER BY io.DocumentNo) ||
                                       CASE WHEN COUNT(DISTINCT io.documentNo) > 1 THEN ' ff.' ELSE '' END     AS DocNo,
                                       MIN(io.MovementDate)                                                    AS DateFrom
                                FROM (SELECT DISTINCT COALESCE(dl_ol.C_Order_ID, sl_ol.C_Order_ID) AS C_Order_ID,
                                                      ila.C_InvoiceLine_ID
                                      FROM C_InvoiceLine il
                                               INNER JOIN C_Invoice_Line_Alloc ila ON il.C_InvoiceLine_ID = ila.C_InvoiceLine_ID
                                               INNER JOIN C_Invoice_Candidate ic ON ila.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID
                                          -- Direct Link from IC to OL. Applies for non HU lines
                                               LEFT OUTER JOIN C_Orderline dl_ol ON ic.AD_Table_ID = (SELECT Get_Table_ID('C_OrderLine')) AND ic.Record_ID = dl_ol.C_OrderLine_ID
                                          -- Stow away link. For ICs that are created as a result of shipped HUs that weren't in the original order
                                               LEFT OUTER JOIN M_InOutLine siol ON ic.AD_Table_ID = (SELECT Get_Table_ID('M_InOutLine')) AND ic.Record_ID = siol.M_InOutLine_ID
                                               LEFT OUTER JOIN M_InOutLine sio_all ON siol.M_InOut_ID = sio_all.M_InOut_ID
                                               LEFT OUTER JOIN M_ReceiptSchedule_Alloc rsa ON sio_all.M_InOutLine_ID = rsa.M_InOutLine_ID
                                               LEFT OUTER JOIN M_ReceiptSchedule rs ON rsa.M_ReceiptSchedule_ID = rs.M_ReceiptSchedule_ID
                                               LEFT OUTER JOIN C_Orderline sl_ol ON rs.AD_Table_ID = (SELECT Get_Table_ID('C_OrderLine')) AND rs.C_OrderLine_ID = sl_ol.C_OrderLine_ID
                                      WHERE COALESCE(dl_ol.C_Order_ID, sl_ol.C_Order_ID) IS NOT NULL
                                        AND il.C_Invoice_ID = p_record_id) o
                                         INNER JOIN C_OrderLine ol ON o.C_Order_ID = ol.C_Order_ID
                                         INNER JOIN M_ReceiptSchedule rs ON rs.AD_Table_ID = (SELECT Get_Table_ID('C_OrderLine')) AND rs.C_OrderLine_ID = ol.C_OrderLine_ID
                                         INNER JOIN M_ReceiptSchedule_Alloc rsa ON rs.M_ReceiptSchedule_ID = rsa.M_ReceiptSchedule_ID
                                         INNER JOIN M_InOutLine iol ON rsa.M_InOutLine_ID = iol.M_InOutLine_ID
                                         INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID
                                    AND io.DocStatus IN ('CO', 'CL') /* task 09290 */
                                         INNER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID
                                         LEFT OUTER JOIN C_DocType_Trl dtt ON io.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_language
                                GROUP BY C_InvoiceLine_ID) io1 ON il.C_InvoiceLine_ID = io1.C_InvoiceLine_ID
          -- Get Alternate shipment grouping header
               LEFT OUTER JOIN (SELECT DISTINCT iliol.C_InvoiceLine_ID,
                                                First_Agg(COALESCE(dtt.Printname, dt.Printname)) OVER (PARTITION BY iliol.C_Invoice_ID ORDER BY io.DocumentNo) AS DocType,
                                                First_Agg(io.DocumentNo) OVER (PARTITION BY iliol.C_Invoice_ID ORDER BY io.DocumentNo) ||
                                                CASE WHEN (COUNT(io.documentNo) OVER (PARTITION BY iliol.C_Invoice_ID)) > 1 THEN ' ff.' ELSE '' END            AS DocNo,
                                                MIN(io.MovementDate) OVER (PARTITION BY iliol.C_Invoice_ID)                                                    AS DateFrom
                                FROM (SELECT DISTINCT M_InOut_ID, C_InvoiceLine_ID, C_Invoice_ID
                                      FROM Report.fresh_IL_to_IOL_V
                                      WHERE C_Invoice_ID = p_record_id) iliol
                                         LEFT OUTER JOIN M_InOut io ON iliol.M_InOut_ID = io.M_InOut_ID
                                    AND io.DocStatus IN ('CO', 'CL') /* task 09290 */
                                         LEFT OUTER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID
                                         LEFT OUTER JOIN C_DocType_Trl dtt ON io.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = p_language) io2 ON il.C_InvoiceLine_ID = io2.C_InvoiceLine_ID
          -- Get Packing instruction
               LEFT OUTER JOIN (SELECT STRING_AGG(Name, E'\n' ORDER BY Name) AS Name, C_InvoiceLine_ID
                                FROM (SELECT DISTINCT COALESCE(pifb.name, pi.name) AS name,
                                                      C_InvoiceLine_ID
                                      FROM Report.fresh_IL_TO_IOL_V iliol
                                               INNER JOIN M_InOutLine iol ON iliol.M_InOutLine_ID = iol.M_InOutLine_ID
                                               LEFT OUTER JOIN M_HU_PI_Item_Product pi ON iol.M_HU_PI_Item_Product_ID = pi.M_HU_PI_Item_Product_ID
                                               LEFT OUTER JOIN M_HU_PI_Item piit ON piit.M_HU_PI_Item_ID = pi.M_HU_PI_Item_ID
                                               LEFT OUTER JOIN M_HU_Assignment asgn ON asgn.AD_Table_ID = ((SELECT get_Table_ID('M_InOutLine')))
                                          AND asgn.Record_ID = iol.M_InOutLine_ID
                                               LEFT OUTER JOIN M_HU tu ON asgn.M_TU_HU_ID = tu.M_HU_ID
                                               LEFT OUTER JOIN M_HU_PI_Item_Product pifb ON tu.M_HU_PI_Item_Product_ID = pifb.M_HU_PI_Item_Product_ID
                                               LEFT OUTER JOIN M_HU_PI_Item pit ON pifb.M_HU_PI_Item_ID = pit.M_HU_PI_Item_ID
                                               LEFT OUTER JOIN M_HU_PI_Version piv ON piv.M_HU_PI_Version_ID = COALESCE(pit.M_HU_PI_Version_ID, piit.M_HU_PI_Version_ID)
                                      WHERE piv.M_HU_PI_Version_ID != 101
                                        AND iliol.C_Invoice_ID = p_record_id) pi
                                GROUP BY C_InvoiceLine_ID) piip ON il.C_InvoiceLine_ID = piip.C_InvoiceLine_ID

          -- Get Attributes
          -- we join the first M_MatchInv record to get it's M_InOutLine's ASI
          -- if there are many inoutLines with different ASIs, then we can assume that all of them have that same instance values for those attributes that are flagged with M_Attribute.IsAttrDocumentRelevant='Y'
          -- and these are also the only M_Attributes's instance values that we show

               LEFT OUTER JOIN
           (SELECT DISTINCT ON (C_InvoiceLine_ID) STRING_AGG(att.ai_value, ', ' ORDER BY LENGTH(att.ai_value),att.ai_value) AS Attributes, att.M_AttributeSetInstance_ID, il.c_invoiceline_id
            FROM c_invoiceline il
                     JOIN m_matchinv mi ON mi.c_invoiceline_id = il.c_invoiceline_id
                     JOIN Report.fresh_Attributes att ON mi.m_attributesetinstance_id = att.m_attributesetinstance_id
            WHERE att.IsPrintedInDocument = 'Y'
              AND il.c_invoice_id = p_record_id
            GROUP BY att.M_AttributeSetInstance_ID, il.c_invoiceline_id) att ON att.C_InvoiceLine_ID = il.C_InvoiceLine_ID

      WHERE il.C_Invoice_ID = p_record_id

      GROUP BY InOuts,
               COALESCE(io1.DocType, io2.DocType),
               TO_CHAR(COALESCE(io1.DateFrom, io2.DateFrom), 'DD.MM.YYYY'),
               COALESCE(io1.DocNo, io2.DocNo) IS NOT NULL,
               COALESCE(pc.IsHU, FALSE),
               -- ts: QnD: appending the invoice line description to the product name.
               -- TODO: create a dedicated field for it etc
               il.Description,
               il.ProductDescription,
               COALESCE(pt.name, p.name),
               COALESCE(
                       CASE
                           WHEN LENGTH(att.Attributes) > 15
                               THEN att.Attributes || E'\n'
                               ELSE att.Attributes
                       END,
                       ''
               ),
               piip.name,
               il.PriceActual,
               il.PriceEntered,
               COALESCE(uomt.UOMSymbol, uom.UOMSymbol),
               COALESCE(puomt.UOMSymbol, puom.UOMSymbol),
               puom.StdPrecision,
               uom.StdPrecision,
               t.rate,
               bpg.IsPrintTax,
               COALESCE(io1.DateFrom, io2.DateFrom),
               COALESCE(io1.DocNo, io2.DocNo),
               COALESCE(NULLIF(bpp.ProductNo, ''), p.value),
               COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name),
               p.value,
               p.description,
               i.description,
               c.cursymbol,
               i.m_pricelist_id,
               c.c_currency_id

      ORDER BY COALESCE(io1.DateFrom, io2.DateFrom),
               COALESCE(io1.DocNo, io2.DocNo),
               COALESCE(pc.IsHU, FALSE),
               Name,
               MAX(line)) result
ORDER BY line
$$
;

