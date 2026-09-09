-- Expose C_OrderLine.DescriptionAboveLine (the free text printed above an order line) as
-- "descriptionaboveline" to every core sales line-detail report source that already
-- reaches C_OrderLine, and add the join for the two sources that did not yet reach it
-- (Docs_Sales_InOut_Details_HU in section 4, and the PickingList view in section 8).
--
-- Source DDL:
--   backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/functions/Docs_Sales_Order_Details.sql
--   backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/functions/Docs_Sales_Order_Details_HU.sql
--   backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/functions/Docs_Sales_InOut_Details.sql
--   backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/functions/Docs_Sales_InOut_Details_HU.sql
--   backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/functions/Docs_Sales_Invoice_Details.sql
--   backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/functions/Docs_Sales_Picking_Details.sql
--   backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/functions/Docs_Sales_Picking_Details_HU.sql
--   backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/views/PickingList.sql
--
-- Docs_Sales_Invoice_Details_HU is NOT part of this script -- it gets the same column in its
-- own sibling migration (5823580), because it does have a live consumer (a customer overriding
-- template reads it), contrary to an earlier reading of core alone.

-- ============================================================================
-- 1) Docs_Sales_Order_Details -- FROM C_OrderLine directly, column appended
-- ============================================================================
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Details(IN p_record_id   numeric,
                                                                                    IN p_ad_language Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Details(IN p_record_id   numeric,
                                                                                       IN p_ad_language Character Varying(6))

    RETURNS TABLE
            (
                line                         numeric(10, 0),
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
                descriptionaboveline         character varying

            )
AS
$$
SELECT ol.line,
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
       ol.descriptionaboveline
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
ORDER BY ol.line

$$
    LANGUAGE sql STABLE
;

-- ============================================================================
-- 2) Docs_Sales_Order_Details_HU -- FROM c_orderline directly, column appended
-- ============================================================================
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Details_HU(IN p_C_Order_ID numeric, IN p_ad_language Character Varying (6));

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Order_Details_HU(IN p_C_Order_ID numeric, IN p_ad_language Character Varying (6))

RETURNS TABLE
(
	QtyEntered numeric,
	Name character varying,
	Price numeric,
	LineNetAmt numeric,
	UOMSymbol character varying(10),
	Description character varying,
	IsPrintWhenPackingMaterial char(1),
	DescriptionAboveLine character varying
)
AS
$$
SELECT
	ol.QtyEntered,
	COALESCE(pt.Name, p.name)		AS Name,
	ol.PriceEntered			AS Price,
	ol.linenetamt,
	COALESCE(uom.UOMSymbol, uomt.UOMSymbol)	AS UOMSymbol,
	ol.Description,
	p.IsPrintWhenPackingMaterial,
	ol.DescriptionAboveLine
FROM
	c_orderline ol
	-- Product and its translation
	LEFT OUTER JOIN M_Product p 			ON ol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Trl pt 		ON ol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_ad_language AND pt.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
	-- Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom			ON ol.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Trl uomt			ON ol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_ad_language AND uomt.isActive = 'Y'
WHERE
	ol.C_Order_ID = p_C_Order_ID AND ol.isActive = 'Y'
	AND pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID)

$$
LANGUAGE sql STABLE
;

-- ============================================================================
-- 3) Docs_Sales_InOut_Details -- already joins c_orderline, column appended with
--    the same per-doctype IsHiddenReportElement suppression idiom used for 'description'
-- ============================================================================
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
                QtyPattern             text,
                descriptionaboveline   character varying
            )
AS
$$ SELECT iol.line,
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
          CASE
              WHEN report.IsHiddenReportElement(io.C_DocType_ID, 'descriptionaboveline') = 'N' THEN
                  ol.DescriptionAboveLine
          END                                                                                             AS descriptionaboveline
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

-- ============================================================================
-- 4) Docs_Sales_InOut_Details_HU -- did NOT reach C_OrderLine; new join added
--    via m_inoutline.c_orderline_id, column appended (pseudo-table pattern)
-- ============================================================================
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU ( IN p_M_InOut_ID numeric, IN p_AD_Language Character Varying (6) );
DROP TABLE IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU;

CREATE TABLE de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU
(
	MovementQty numeric,
	Name Character Varying,
	UOMSymbol Character Varying (10),
	Description Character Varying,
	IsPrintWhenPackingMaterial char(1),
	DescriptionAboveLine Character Varying
);


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU ( IN p_M_InOut_ID numeric, IN p_AD_Language Character Varying (6) )
RETURNS SETOF de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Details_HU AS
$$
SELECT
	SUM(iol.QtyEntered)			AS MovementQty,
	COALESCE(pt.Name, p.name)		AS Name,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol)	AS UOMSymbol,
	iol.Description,
	p.IsPrintWhenPackingMaterial,
	ol.DescriptionAboveLine
FROM
	M_InOut io
	INNER JOIN M_InOutLine iol 			ON io.M_InOut_ID = iol.M_InOut_ID AND iol.isActive = 'Y'
	-- get order line for the free-text-above-order-line column
	LEFT OUTER JOIN C_OrderLine ol			ON ol.C_OrderLine_ID = iol.C_OrderLine_ID AND ol.isActive = 'Y'
	-- Product and its translation
	LEFT OUTER JOIN M_Product p 			ON iol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Trl pt 		ON iol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_AD_Language AND pt.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
	-- Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom			ON iol.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Trl uomt			ON iol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_AD_Language AND uomt.isActive = 'Y'
	--ordering gebinde if config exists
	LEFT OUTER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT OUTER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID and dt.isActive = 'Y'
	LEFT OUTER JOIN C_DocLine_Sort dls ON dt.DocBaseType = dls.DocBaseType AND dls.isActive = 'Y'
		AND EXISTS (
			SELECT 0 FROM C_BP_DocLine_Sort bpdls
			WHERE bpdls.C_DocLine_Sort_ID = dls.C_DocLine_Sort_ID AND bpdls.C_BPartner_ID = bp.C_BPartner_ID AND bpdls.isActive = 'Y'
		)
	LEFT OUTER JOIN C_DocLine_Sort_Item dlsi ON dls.C_DocLine_Sort_ID = dlsi.C_DocLine_Sort_ID AND dlsi.M_Product_ID = iol.M_Product_ID AND dlsi.isActive = 'Y'

WHERE
	io.M_InOut_ID = p_M_InOut_ID AND io.isActive = 'Y'
	AND pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', iol.AD_Client_ID, iol.AD_Org_ID)
	AND iol.QtyEntered != 0 -- Don't display lines without a Qty. See 08293
-- DescriptionAboveLine is one of the grouping keys, so packing-material rows that agree on
-- product, UOM and packing-material flag but carry different free texts are now reported as
-- separate rows instead of being summed into one. That is intended: the free text belongs to
-- its own order line and a summed row could not carry two different texts.
GROUP BY
	 COALESCE(pt.Name, p.name), COALESCE(uomt.UOMSymbol, uom.UOMSymbol), dlsi.SeqNo, iol.description, p.IsPrintWhenPackingMaterial, ol.DescriptionAboveLine
ORDER BY
	dlsi.SeqNo NULLS LAST

$$
LANGUAGE sql STABLE
;

-- ============================================================================
-- 5) Docs_Sales_Invoice_Details -- already joins c_orderline, column appended
-- ============================================================================
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
                customs_number             text,
                iswithoutcharge            character(1),
                reason                     character varying(4000),
                Is_TotalAmount_Hidden      char,
                Is_Weight_Hidden           char,
                descriptionaboveline       character varying
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
       pcus.value || ' ' || COALESCE(pcus.name, '')           AS customs_number,
       il.iswithoutcharge,
       il.reason,
       report.IsHiddenReportElement(i.C_DocType_ID, 'TotalAmount') AS Is_TotalAmount_Hidden,
       report.IsHiddenReportElement(i.C_DocType_ID, 'Weight') AS Is_Weight_Hidden,
       ol.DescriptionAboveLine                                AS descriptionaboveline
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

-- ============================================================================
-- 6) Docs_Sales_Picking_Details -- FROM C_OrderLine directly, column appended
-- ============================================================================
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Picking_Details ( IN C_Order_ID numeric, IN AD_Language Character Varying (6) );


CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Picking_Details ( IN C_Order_ID numeric, IN AD_Language Character Varying (6) )
RETURNS TABLE
(
	Line Numeric (10,0),
	Name Character Varying,
	Attributes Text,
	HUQty Numeric,
	HUName Text,
	qtyEntered Numeric,
	qtyDelivered Numeric,
	qtyToDeliver Numeric,
	UOMSymbol Character Varying (10),
	StdPrecision Numeric (10,0),
	QtyPattern text,
	Description Character Varying,
	bp_product_no character varying(30),
	bp_product_name character varying(100),
	upc character varying(30),
	descriptionaboveline character varying
)
AS
$$

SELECT
	ol.line,
	COALESCE(pt.Name, p.name) AS Name,
	CASE WHEN Length( att.Attributes ) > 15
		THEN att.Attributes || E'\n'
		ELSE att.Attributes
	END AS Attributes,
	ol.QtyEnteredTU AS HUQty,
	pi.name AS HUName,
	QtyEntered * COALESCE (multiplyrate, 1) AS QtyEntered,
	ss.QtyDelivered,
	ss.QtyToDeliver,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol) AS UOMSymbol,
	uom.stdPrecision,
	CASE WHEN StdPrecision = 0 THEN '#,##0' ELSE Substring( '#,##0.000' FROM 0 FOR 7+StdPrecision::integer) END AS QtyPattern,
	ol.Description,
	-- in case there is no C_BPartner_Product, fallback to the default ones
	COALESCE(NULLIF(bpp.ProductNo, ''), p.value) as bp_product_no,
	COALESCE(NULLIF(bpp.ProductName, ''), pt.Name, p.name) as bp_product_name,
	p.upc,
	ol.DescriptionAboveLine
FROM
	C_OrderLine ol
	INNER JOIN C_Order o 			ON ol.C_Order_ID = o.C_Order_ID AND o.isActive = 'Y'
	LEFT OUTER JOIN C_BPartner bp			ON o.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT OUTER JOIN (
		SELECT ss.C_OrderLine_ID, ss.QtyDelivered, ss.QtyToDeliver
		FROM 	M_ShipmentSchedule ss
			INNER JOIN C_OrderLine ol ON ol.C_OrderLine_ID = ss.C_OrderLine_ID AND ol.isActive = 'Y'
		WHERE ol.C_Order_ID = $1 AND ss.isActive = 'Y'
	) ss ON ol.C_OrderLine_ID = ss.C_OrderLine_ID
	-- Get Packing instruction
	LEFT OUTER JOIN
	(
		SELECT String_Agg( DISTINCT name, E'\n' ORDER BY name ) AS Name, C_OrderLine_ID
		FROM
			(
				SELECT DISTINCT
					-- 08604 - in IT1 only one PI was shown though 2 were expected. Only the fallback can do this, so we use it first
					COALESCE ( pifb.name, pi.name ) AS name,
					ol.C_OrderLine_ID
				FROM
					C_OrderLine ol
					-- Get PI directly from OrderLine (1 to 1)
					LEFT OUTER JOIN M_HU_PI_Item_Product pi ON ol.M_HU_PI_Item_Product_ID = pi.M_HU_PI_Item_Product_ID AND pi.isActive = 'Y'
					LEFT OUTER JOIN M_HU_PI_Item piit ON piit.M_HU_PI_Item_ID = pi.M_HU_PI_Item_ID AND piit.isActive = 'Y'
					-- Get PI from HU assignments (1 to n)
					LEFT OUTER JOIN M_HU_Assignment asgn ON asgn.AD_Table_ID = ((SELECT get_Table_ID( 'C_OrderLine' ) ))
						AND asgn.Record_ID = ol.C_OrderLine_ID AND asgn.isActive = 'Y'
					LEFT OUTER JOIN M_HU tu ON asgn.M_TU_HU_ID = tu.M_HU_ID
					LEFT OUTER JOIN M_HU_PI_Item_Product pifb ON tu.M_HU_PI_Item_Product_ID = pifb.M_HU_PI_Item_Product_ID AND pifb.isActive = 'Y'
					LEFT OUTER JOIN M_HU_PI_Item pit ON pifb.M_HU_PI_Item_ID = pit.M_HU_PI_Item_ID AND pit.isActive = 'Y'
					--
					LEFT OUTER JOIN M_HU_PI_Version piv ON piv.M_HU_PI_Version_ID = COALESCE(pit.M_HU_PI_Version_ID, piit.M_HU_PI_Version_ID) AND piv.isActive = 'Y'
				WHERE
					piv.M_HU_PI_Version_ID != 101
					AND ol.C_Order_ID = $1 AND ol.isActive = 'Y'
			) x
		GROUP BY C_OrderLine_ID
	) pi ON ol.C_OrderLine_ID = pi.C_OrderLine_ID
	-- Product and its translation
	LEFT OUTER JOIN M_Product p 			ON ol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Trl pt 		ON ol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'

	LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID
		AND p.M_Product_ID = bpp.M_Product_ID AND bpp.isActive = 'Y'
	-- Unit of measurement and its translation
	LEFT OUTER JOIN C_UOM uom			ON ol.Price_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Trl uomt			ON ol.Price_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2 AND uomt.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Conversion conv		ON conv.C_UOM_ID = ol.C_UOM_ID
		AND conv.C_UOM_To_ID = ol.Price_UOM_ID
		AND ol.M_Product_ID = conv.M_Product_ID
		AND conv.isActive = 'Y'
	-- Attributes
	LEFT OUTER JOIN	(
		SELECT 	String_agg ( at.ai_value, ', ' ORDER BY Length(at.ai_value), at.ai_value ) AS Attributes, at.M_AttributeSetInstance_ID FROM Report.fresh_Attributes at
		JOIN C_OrderLine ol ON at.M_AttributeSetInstance_ID = ol.M_AttributeSetInstance_ID AND ol.isActive = 'Y'
		WHERE	at.at_value IN ('1000002', '1000001', '1000030', '1000015') -- Label, Herkunft, Aktionen, Marke (ADR)
			AND ol.C_Order_ID = $1
		GROUP BY	at.M_AttributeSetInstance_ID
	) att ON ol.M_AttributeSetInstance_ID = att.M_AttributeSetInstance_ID
WHERE
	ol.C_Order_ID = $1 AND ol.isActive = 'Y'
	AND COALESCE(pc.M_Product_Category_ID, -1) != getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID)
	AND QtyEntered != 0 -- Don't display lines without a Qty. See 08293
ORDER BY
	line

$$
LANGUAGE sql STABLE
;

-- ============================================================================
-- 7) Docs_Sales_Picking_Details_HU -- FROM C_Order/C_OrderLine directly, column appended
-- ============================================================================
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Picking_Details_HU ( IN C_Order_ID numeric, IN AD_Language Character Varying (6) );

CREATE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Picking_Details_HU ( IN C_Order_ID numeric, IN AD_Language Character Varying (6) )
RETURNS TABLE
(
	MovementQty numeric,
	Name Character Varying,
	UOMSymbol Character Varying (10),
	DescriptionAboveLine Character Varying
)
AS
$$
SELECT
	SUM(ol.QtyEntered)			AS MovementQty,
	COALESCE(pt.Name, p.name)		AS Name,
	COALESCE(uomt.UOMSymbol, uom.UOMSymbol)	AS UOMSymbol,
	ol.DescriptionAboveLine
FROM
	C_Order o
	INNER JOIN C_OrderLine ol 			ON o.C_Order_ID = ol.C_Order_ID AND ol.isActive = 'Y'
	-- Product and its translato.
	LEFT OUTER JOIN M_Product p 			ON ol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Trl pt 		ON ol.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $2 AND pt.isActive = 'Y'
	LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
	-- Unit of measurement and its translato.
	LEFT OUTER JOIN C_UOM uom			ON ol.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
	LEFT OUTER JOIN C_UOM_Trl uomt			ON ol.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = $2 AND uomt.isActive = 'Y'
	--ordering gebinde if config exists
	LEFT OUTER JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	LEFT OUTER JOIN C_DocType dt ON o.C_DocType_ID = dt.C_DocType_ID and dt.isActive = 'Y'
	LEFT OUTER JOIN C_DocLine_Sort dls ON dt.DocBaseType = dls.DocBaseType AND dls.isActive = 'Y'
		AND EXISTS (
			SELECT 0 FROM C_BP_DocLine_Sort bpdls
			WHERE bpdls.C_DocLine_Sort_ID = dls.C_DocLine_Sort_ID AND bpdls.C_BPartner_ID = bp.C_BPartner_ID AND bpdls.isActive = 'Y'
		)
	LEFT OUTER JOIN C_DocLine_Sort_Item dlsi ON dls.C_DocLine_Sort_ID = dlsi.C_DocLine_Sort_ID AND dlsi.M_Product_ID = ol.M_Product_ID AND dlsi.isActive = 'Y'

WHERE
	o.C_Order_ID = $1 AND o.isActive = 'Y'
	AND pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID)
	AND QtyEntered != 0 -- Don't display lines without a Qty. See 08293
-- DescriptionAboveLine is one of the grouping keys, so packing-material rows that agree on
-- product, UOM and description but carry different free texts are now reported as separate
-- rows instead of being summed into one. That is intended: the free text belongs to its own
-- order line and a summed row could not carry two different texts.
GROUP BY
	 COALESCE(pt.Name, p.name), COALESCE(uomt.UOMSymbol, uom.UOMSymbol), dlsi.SeqNo, ol.description, ol.DescriptionAboveLine
ORDER BY
	dlsi.SeqNo NULLS LAST

$$
LANGUAGE sql STABLE
;

-- ============================================================================
-- 8) PickingList (view) -- did NOT reach C_OrderLine; new join added via
--    m_packageable_v.c_orderlineso_id, column appended -- via db_alter_view()
-- ============================================================================
DROP VIEW IF EXISTS de_metas_endcustomer_fresh_reports.PickingList$new;

CREATE OR REPLACE VIEW de_metas_endcustomer_fresh_reports.PickingList$new AS
SELECT

	v.bpartnervalue,
	v.bpartnername,
	v.bpartneraddress_override,
	v.orderdocumentno,
	v.warehousename,
	v.preparationdate,
	p.value as productvalue,
	v.productname,
	v.qtyordered,
	pc.qtyPicked,
	v.c_uom_id,
	v.ad_org_id,
	v.c_orderso_id,
	u.uomsymbol,
	l.value as locator,
	l.x,
	l.y,
	l.z,
	l.x1,
	ol.descriptionaboveline
FROM m_picking_candidate pc
	JOIN m_packageable_v v  on pc.m_shipmentschedule_id = v.m_shipmentschedule_id
    JOIN m_product p on p.m_product_id = v.m_product_id
	JOIN C_Uom u on u.C_Uom_id = v.C_Uom_id
	LEFT JOIN M_HU hu on hu.M_hu_id = pc.pickfrom_hu_id
	LEFT JOIN m_locator l on l.m_locator_id = hu.m_locator_id
	LEFT JOIN C_OrderLine ol on ol.C_OrderLine_ID = v.c_orderlineso_id AND ol.isActive = 'Y'
ORDER BY l.value, l.x, l.y, l.z, l.x1;

SELECT public.db_alter_view(
    'de_metas_endcustomer_fresh_reports.PickingList',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(table_schema) = lower('de_metas_endcustomer_fresh_reports')
       AND lower(table_name) = lower('PickingList$new'))
);

DROP VIEW IF EXISTS de_metas_endcustomer_fresh_reports.PickingList$new;
