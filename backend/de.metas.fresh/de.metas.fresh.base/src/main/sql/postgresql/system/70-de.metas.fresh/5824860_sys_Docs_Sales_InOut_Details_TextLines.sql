-- Source DDL: backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/functions/Docs_Sales_InOut_Details.sql
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details (IN p_Record_ID   numeric,
                                                                                     IN p_AD_Language Character Varying(6))
;


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details(IN p_Record_ID   numeric,
                                                                            IN p_AD_Language Character Varying(6))
    RETURNS TABLE
            (
                -- scale 4 must match C_Doc_TextLine.Line and DocTextLineRepository.LINE_SCALE: the text
                -- branch below emits that column verbatim, so this is the real domain of the merged
                -- column even though the article branch only ever fills it with whole numbers.
                -- DOCUMENTATION, not a rounding guard -- measured: PostgreSQL does not enforce a
                -- RETURNS TABLE typmod for a LANGUAGE sql function, so a fractional text position
                -- (19.9999) comes through unrounded under either declaration. Stated so nobody later
                -- "simplifies" this back to (10,0) believing the engine would still protect the scale,
                -- and so nobody reads the declaration as the thing that makes interleaving work.
                Line                   Numeric(10, 4),
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
                QtyPattern             text,
                IsTextLine             Character(1),
                -- Breaks a tie between two rows landing on the same "line" AND the same IsTextLine
                -- (in practice: two C_Doc_TextLine rows at one position -- two article rows can never
                -- tie here, M_InOutLine.Line is distinct per shipment). Carries each row's own id: the
                -- text branch's C_Doc_TextLine_ID (matching DocTextLineRepository.getByDocument's own
                -- "ORDER BY Line, C_Doc_TextLine_ID"), the article branch's M_InOutLine_ID. The article
                -- value is never actually compared against a text row's: IsTextLine ('N' vs 'Y')
                -- already differs for that pair, so ORDER BY never reaches this third key for an
                -- article/text comparison -- a real id is used anyway (never NULL) so the column
                -- carries a meaningful value in both branches.
                LineTieBreakId         Numeric
            )
AS
$$
-- Article lines (M_InOutLine). Their Line is a whole number; the decimal positions that let a text
-- line sit between two of them (TextLineShipmentCopier's anchor ties and its 0.0001 block-member
-- offsets) come from the text branch below and are never rounded -- see the RETURNS TABLE comment
-- on the Line column for what that declaration does and does not guarantee.
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
          TO_CHAR(att.best_before_date :: DATE, 'dd.MM.YYYY')                                                AS best_before_date,
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
          report.getQtyPattern(uom.StdPrecision)                                  AS QtyPattern,
          'N'                                                                                             AS IsTextLine,
          iol.M_InOutLine_ID                                                                              AS LineTieBreakId
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

UNION ALL

-- Free-text lines interleaved with the shipment's article lines (C_Doc_TextLine). The rows selected
-- here are the shipment's OWN copies, written by TextLineShipmentCopier when the shipment was
-- generated -- the carry rule ("does this text line's run reach this shipment?") was already applied
-- there, so this branch only reads what that step decided; it must not re-apply any of it.
-- This branch does NOT reuse the article branch's WHERE clauses above: the packing-material exclusion
-- and "QtyEntered != 0" are both conditions about an ARTICLE line (a packing product, a delivered
-- quantity) that a text line has no equivalent of -- copying them would silently filter every text
-- line out.
-- Column shape: document-level descriptors are carried through, not NULLed. inout_description and
-- docstatus come from the shipment itself; isDiscountPrinted and IsShipmentPricePrinted both come
-- from the shipment's business partner; catchweight and weight_uom are the DOCUMENT's summed weight
-- (Docs_Sales_InOut_Sum_Weight takes p_Record_ID, not a line) and are read by the templates' summary
-- bands off whichever record happens to be last -- so a text row must report them exactly as an
-- article row on the same document does, or a trailing text line would erase the document's weight
-- total. Only genuinely ARTICLE-level columns (product, HU, quantity, price, discount, attributes,
-- best-before/lot, the ordered qty and its UOM, and QtyPattern -- derived from a line's UOM
-- precision) are NULL. TextLine is nullable and an empty text line is legal (prints as a blank line)
-- -- it is passed straight through with no NULLIF/COALESCE that could coerce an empty value away.
SELECT tl.line,
       NULL::character varying     AS Name,
       NULL::text                  AS Attributes,
       NULL::numeric               AS HUQty,
       NULL::text                  AS HUName,
       NULL::numeric               AS QtyEntered,
       NULL::numeric               AS PriceEntered,
       NULL::character varying(10) AS UOMSymbol,
       NULL::numeric(10, 0)        AS StdPrecision,
       NULL::numeric               AS LineNetAmt,
       NULL::numeric               AS Discount,
       bp.isDiscountPrinted,
       bp.IsShipmentPricePrinted,
       tl.TextLine                 AS description,
       NULL::character varying(30) AS bp_product_no,
       NULL::character varying(100) AS bp_product_name,
       NULL::text                  AS best_before_date,
       NULL::character varying     AS lotno,
       NULL::character varying(30) AS p_value,
       NULL::character varying(255) AS p_description,
       io.description              AS inout_description,
       NULL::character(1)          AS iscampaignprice,
       NULL::numeric               AS qtyordered,
       NULL::character varying(10) AS orderUOMSymbol,
       w.catchweight               AS catchweight,
       w.weight_uom                AS weight_uom,
       io.docstatus,
       NULL::text                  AS QtyPattern,
       'Y'                         AS IsTextLine,
       tl.C_Doc_TextLine_ID        AS LineTieBreakId
FROM C_Doc_TextLine tl
         INNER JOIN M_InOut io ON tl.M_InOut_ID = io.M_InOut_ID
         LEFT OUTER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID
         LEFT OUTER JOIN
    de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Sum_Weight(p_Record_ID, p_AD_Language) AS w ON TRUE
WHERE tl.M_InOut_ID = p_Record_ID
  AND tl.isActive = 'Y'

-- Postgres UNION ORDER BY only allows result-column names, no expressions -- 'Y' > 'N', so DESC
-- puts a text line ahead of an article line landing on the exact same position. That tie is the
-- normal case here, not an edge case: TextLineShipmentCopier stores a carried text line AT its
-- anchor shipment line's own Line precisely so this rule renders it immediately before that line.
-- LineTieBreakId is the third key, for the one case the first two leave undecided: two text rows
-- tied at the same line.
ORDER BY line, IsTextLine DESC, LineTieBreakId

$$
    LANGUAGE sql
    STABLE
;