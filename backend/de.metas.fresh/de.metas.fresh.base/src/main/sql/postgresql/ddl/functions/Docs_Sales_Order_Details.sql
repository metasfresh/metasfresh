DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Details(IN p_record_id   numeric,
                                                                                    IN p_ad_language Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Details(IN p_record_id   numeric,
                                                                                       IN p_ad_language Character Varying(6))

    RETURNS TABLE
            (
                line                         numeric(10, 4), -- scale 4 must match C_Doc_TextLine.Line and DocTextLineRepository.LINE_SCALE
                Name                         character varying,
                Attributes                   text,
                HUQty                        numeric,
                HUName                       character varying,
                QtyEnteredInPriceUOM         numeric,
                PriceEntered                 numeric,
                UOMSymbol                    character varying(10),
                StdPrecision                 numeric(10, 0),
                linenetamt                   numeric,
                discount                     numeric,
                isDiscountPrinted            character(1),
                rate                         character varying,
                isPrintTax                   character(1),
                description                  character varying,
                documentnote                 character varying,
                productdescription           character varying,
                bp_product_no                character varying(30),
                bp_product_name              character varying(100),
                cursymbol                    character varying(10),
                p_value                      character varying(40),
                p_description                character varying(255),
                order_description            character varying(1024),
                c_order_compensationgroup_id numeric,
                isgroupcompensationline      character(1),
                groupname                    character varying(255),
                iso_code                     character(3),
                iscampaignprice              character(1),
                weight                       numeric,
                PricePattern                 text,
                AmountPattern                text,
                QtyPattern                   text,
                iswithoutcharge              character(1),
                reason                       character(1),
                IsTextLine                   character(1),
                IsNetSumShown                character(1),
                IsTaxRateShown               character(1),
                -- Breaks a tie between two rows landing on the same "line" AND the same IsTextLine
                -- (in practice: two C_Doc_TextLine rows at one position -- two article rows can never
                -- tie here, C_OrderLine.Line is distinct per order). Carries each row's own id: the
                -- text branch's C_Doc_TextLine_ID (matching DocTextLineRepository.getByDocument's own
                -- "ORDER BY Line, C_Doc_TextLine_ID", so a tie prints in the same order the WebUI text-
                -- lines modal shows), the article branch's C_OrderLine_ID. The article value is never
                -- actually compared against a text row's: IsTextLine ('N' vs 'Y') already differs for
                -- that pair, so ORDER BY never reaches this third key for an article/text comparison --
                -- a real id is used anyway (never NULL) so the column carries a meaningful value in
                -- both branches rather than relying on that reasoning to keep a NULL harmless.
                LineTieBreakId               numeric

            )
AS
$$
-- Article lines (C_OrderLine). Line is declared numeric(10,4) (widened from numeric(10,0)) to document
-- that a text line's fractional position -- e.g. 10.5, which is how the text branch below interleaves
-- without renumbering an article line -- travels through this column. That declaration is documentation,
-- not a guard: PostgreSQL does not enforce a RETURNS TABLE typmod, so the decimals survive either way
-- (measured).
SELECT ol.line                                                AS line,
       COALESCE(pt.Name, p.name)                              AS Name,
       CASE
           WHEN LENGTH(att.Attributes) > 15
               THEN att.Attributes || E'\n'
               ELSE att.Attributes
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
       CASE
           WHEN report.IsHiddenReportElement(o.C_DocTypeTarget_ID, 'Net_Sum ') = 'N'
               THEN ol.linenetamt
       END                                                    AS linenetamt,
       CASE
           WHEN ROUND(discount, 0) = discount THEN ROUND(discount, 0)
           WHEN ROUND(discount, 1) = discount THEN ROUND(discount, 1)
                                              ELSE ROUND(discount, 2)
       END                                                    AS discount,
       bp.isDiscountPrinted,

       CASE
           WHEN report.IsHiddenReportElement(o.C_DocTypeTarget_ID, 'Tax_Rate') = 'N'
               THEN CASE
                        WHEN ROUND(rate, 0) = rate THEN ROUND(rate, 0)
                        WHEN ROUND(rate, 1) = rate THEN ROUND(rate, 1)
                                                   ELSE ROUND(rate, 2)
                    END::character varying
       END                                                    AS rate,
       isPrintTax,
       ol.description,
       ol.M_Product_DocumentNote                              AS documentnote,
       ol.productdescription,
       -- in case there is no C_BPartner_Product, fallback to the default ones
       COALESCE(NULLIF(bpp.ProductNo, ''), p.value)           AS bp_product_no,
       COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name) AS bp_product_name,
       c.cursymbol,
       p.value                                                AS p_value,
       CASE
           WHEN report.IsHiddenReportElement(o.C_DocTypeTarget_ID, 'p_description') = 'N'
               THEN COALESCE(pt.description, p.description)
       END                                                    AS p_description,
       o.description                                          AS order_description,
       ol.c_order_compensationgroup_id,
       ol.isgroupcompensationline,
       cg.name,
       c.iso_code,
       ol.iscampaignprice,
       p.weight,
       report.getPricePatternForJasper(o.m_pricelist_id)      AS PricePattern,
       report.getAmountPatternForJasper(c.c_currency_id)      AS AmountPattern,
       report.getQtyPattern(uom.StdPrecision)                 AS QtyPattern,
       ol.iswithoutcharge,
       ol.reason,
       'N'                                                    AS IsTextLine,
       CASE
           WHEN report.IsHiddenReportElement(o.C_DocTypeTarget_ID, 'Net_Sum ') = 'N'
               THEN 'Y' ELSE 'N'
       END                                                    AS IsNetSumShown,
       CASE
           WHEN report.IsHiddenReportElement(o.C_DocTypeTarget_ID, 'Tax_Rate') = 'N'
               THEN 'Y' ELSE 'N'
       END                                                    AS IsTaxRateShown,
       ol.C_OrderLine_ID                                       AS LineTieBreakId
FROM C_OrderLine ol
         INNER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID
         INNER JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID
         LEFT OUTER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID
         LEFT OUTER JOIN M_HU_PI_Item_Product ip ON ol.M_HU_PI_Item_Product_ID = ip.M_HU_PI_Item_Product_ID AND ip.isActive = 'Y'
         LEFT OUTER JOIN M_HU_PI_Item piit ON ip.M_HU_PI_Item_ID = piit.M_HU_PI_Item_ID AND piit.isActive = 'Y'
    -- Product and its translation
         LEFT OUTER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID
         LEFT OUTER JOIN M_Product_Trl pt ON ol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_ad_language AND pt.isActive = 'Y'
         LEFT OUTER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID

    -- Unit of measurement and its translation
         LEFT OUTER JOIN C_UOM uom ON ol.Price_UOM_ID = uom.C_UOM_ID
         LEFT OUTER JOIN C_UOM_Trl uomt ON ol.Price_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_ad_language AND uomt.isActive = 'Y' AND uomt.isActive = 'Y'
    -- Tax
         LEFT OUTER JOIN C_Tax t ON ol.C_Tax_ID = t.C_Tax_ID

    -- Get Attributes
         LEFT OUTER JOIN (SELECT STRING_AGG(att.ai_value, ', ' ORDER BY LENGTH(att.ai_value)) AS Attributes, att.M_AttributeSetInstance_ID, ol.C_OrderLine_ID
                          FROM Report.fresh_Attributes att
                                   JOIN C_OrderLine ol ON att.M_AttributeSetInstance_ID = ol.M_AttributeSetInstance_ID
                          WHERE att.IsPrintedInDocument = 'Y'
                            AND ol.C_Order_ID = p_record_id
                          GROUP BY att.M_AttributeSetInstance_ID, ol.C_OrderLine_ID) att ON ol.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID AND ol.C_OrderLine_ID = att.C_OrderLine_ID

         LEFT OUTER JOIN
     de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(p.M_Product_ID, bp.C_BPartner_ID, att.M_AttributeSetInstance_ID) AS bpp ON 1 = 1

         -- compensation group
         LEFT JOIN c_order_compensationgroup cg ON ol.c_order_compensationgroup_id = cg.c_order_compensationgroup_id

         LEFT JOIN C_Currency c ON o.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'

WHERE ol.C_Order_ID = p_record_id
  AND ol.isActive = 'Y'
  AND (COALESCE(pc.M_Product_Category_ID, -1) != getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID))
  AND ol.isHideWhenPrinting != 'Y'

UNION ALL

-- Free-text lines (C_Doc_TextLine), interleaved with the article lines above by their shared Line
-- ordering. Deliberately NOT sharing the article branch's WHERE clauses: packing-material category and
-- IsHideWhenPrinting are properties of an ARTICLE line, so applying them here would filter out every
-- text line.
-- Column shape: document-level values (order description, currency, price/amount patterns, the
-- partner's discount- and tax-printing flags, the doc type's column-visibility flags) are carried
-- through, because they are as true of a text row as of an article row. Everything that describes an
-- article is NULL. TextLine passes through untouched -- an empty text line is legal and prints as a
-- blank line.
SELECT tl.line                                                AS line,
       NULL::character varying                                AS Name,
       NULL::text                                              AS Attributes,
       NULL::numeric                                           AS HUQty,
       NULL::character varying                                 AS HUName,
       NULL::numeric                                           AS QtyEnteredInPriceUOM,
       NULL::numeric                                           AS PriceEntered,
       NULL::character varying(10)                             AS UOMSymbol,
       NULL::numeric(10, 0)                                    AS StdPrecision,
       NULL::numeric                                           AS linenetamt,
       NULL::numeric                                           AS discount,
       bp.isDiscountPrinted,
       NULL::character varying                                 AS rate,
       bpg.isPrintTax,
       tl.TextLine                                             AS description,
       NULL::character varying                                 AS documentnote,
       NULL::character varying                                 AS productdescription,
       NULL::character varying(30)                             AS bp_product_no,
       NULL::character varying(100)                            AS bp_product_name,
       c.cursymbol,
       NULL::character varying(40)                             AS p_value,
       NULL::character varying(255)                            AS p_description,
       o.description                                           AS order_description,
       NULL::numeric                                           AS c_order_compensationgroup_id,
       NULL::character(1)                                      AS isgroupcompensationline,
       NULL::character varying(255)                            AS groupname,
       c.iso_code,
       NULL::character(1)                                      AS iscampaignprice,
       NULL::numeric                                           AS weight,
       report.getPricePatternForJasper(o.m_pricelist_id)      AS PricePattern,
       report.getAmountPatternForJasper(c.c_currency_id)      AS AmountPattern,
       NULL::text                                              AS QtyPattern,
       NULL::character(1)                                      AS iswithoutcharge,
       NULL::character(1)                                      AS reason,
       'Y'                                                     AS IsTextLine,
       CASE
           WHEN report.IsHiddenReportElement(o.C_DocTypeTarget_ID, 'Net_Sum ') = 'N'
               THEN 'Y' ELSE 'N'
       END                                                    AS IsNetSumShown,
       CASE
           WHEN report.IsHiddenReportElement(o.C_DocTypeTarget_ID, 'Tax_Rate') = 'N'
               THEN 'Y' ELSE 'N'
       END                                                    AS IsTaxRateShown,
       tl.C_Doc_TextLine_ID                                    AS LineTieBreakId
FROM C_Doc_TextLine tl
         INNER JOIN C_Order o ON tl.C_Order_ID = o.C_Order_ID
         LEFT JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID
         LEFT OUTER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID
         LEFT JOIN C_Currency c ON o.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'
WHERE tl.C_Order_ID = p_record_id
  AND tl.isActive = 'Y'

-- Postgres UNION ORDER BY only allows result-column names, no expressions -- 'Y' > 'N', so DESC
-- puts a text line ahead of an article line landing on the exact same position. LineTieBreakId is
-- the third key, for the one case the first two leave undecided: two text rows tied at the same
-- line (see the RETURNS TABLE comment on that column for why the article branch's own value never
-- factors into that comparison).
ORDER BY line, IsTextLine DESC, LineTieBreakId

$$
    LANGUAGE sql STABLE
;
