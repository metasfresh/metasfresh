-- Source DDL: backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/functions/Docs_Sales_OrderCheckup_Details.sql
DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Details(IN p_record_id numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_OrderCheckup_Details(IN p_record_id numeric)
RETURNS TABLE
	(
	line numeric,
	attributes text,
	prodValue character varying,
	value character varying,
	name character varying(255),
	ean character varying,
	pricelist numeric,
	capacity numeric,
	priceactual numeric,
	qtyenteredtu numeric,
	qtyentered numeric,
	container character varying(60),
	uomsymbol character varying(10),
	c_order_mfgwarehouse_report_id numeric,
	reportdocumenttype character varying(2),
	C_Order_MFGWarehouse_ReportLine_ID numeric,
	c_order_id numeric,
	c_orderline_id numeric,
	m_warehouse_id numeric,
	pp_plant_id numeric,
	c_bpartner_id numeric,
	datepromised timestamp with time zone,
	barcode character varying(255),
	-- Carries a text line's own free text. Named differently from the sibling sales-order report
	-- function's "description" column: that function already had an order-line description column and
	-- reused it, while this function has none to share, so a distinct name is the honest one.
	textline character varying(2048),
	istextline character(1)
	)
AS
$$

WITH
-- The record itself: the one active C_Order_MFGWarehouse_Report row for $1 (the Bestellkontrolle).
target_report AS (
	SELECT report.C_Order_MFGWarehouse_Report_ID, report.DocumentType, report.M_Warehouse_ID,
	       report.PP_Plant_ID, report.C_Order_ID
	FROM C_Order_MFGWarehouse_Report report
	WHERE report.IsActive = 'Y' AND report.C_Order_MFGWarehouse_Report_ID = $1
),
-- Article lines this record actually prints -- same predicates as the pre-text-line function, now
-- reached through target_report instead of repeating its own IsActive/id filter. This is the single
-- source of truth for "which order lines does this record carry": record_lines below derives its line
-- set from here instead of re-stating the same filter by hand, so the two cannot drift apart again.
article_rows AS (
	SELECT
		ol.line,
		att.Attributes,
		p.value AS prodValue,
		COALESCE(bpp.ProductNo, p.value) AS Value,
		p.Name AS Name,
		COALESCE(bpp.UPC, p.UPC) AS EAN,
		-- Rounding these columns is important to have them in one group
		-- Jasper groups by comparing the BigDecimals. In that logic, 1.00 is not the same as 1
		round(ol.pricelist, 3) AS PriceList,
		round(ip.qty, 3) AS Capacity,
		round(ol.Priceactual, 3) AS PriceActual,
		ol.QtyEnteredTU,
		ol.QtyEntered,
		pm.name as Container,
		uom.UOMSymbol AS UOMSymbol,
		--
		-- Filtering columns
		report.C_Order_MFGWarehouse_Report_ID,
		report.DocumentType as ReportDocumentType,
		reportLine.C_Order_MFGWarehouse_ReportLine_ID,
		o.C_Order_ID,
		ol.C_OrderLine_ID,
		report.M_Warehouse_ID,
		report.PP_Plant_ID,
		o.C_BPartner_ID,
		o.DatePromised,
		reportLine.barcode AS barcode
	FROM
		target_report report
		INNER JOIN C_Order o on (report.C_Order_ID=o.C_Order_ID) AND o.isActive = 'Y'
		INNER JOIN C_Order_MFGWarehouse_ReportLine reportLine on (reportLine.C_Order_MFGWarehouse_Report_ID=report.C_Order_MFGWarehouse_Report_ID)
		INNER JOIN C_OrderLine ol ON (ol.C_OrderLine_ID = reportLine.C_OrderLine_ID) AND ol.isActive = 'Y'
		--
		LEFT OUTER JOIN C_BPartner bp ON ol.C_BPartner_ID =  bp.C_BPartner_ID AND bp.isActive = 'Y'
		LEFT OUTER JOIN M_HU_PI_Item_Product ip ON ol.M_HU_PI_Item_Product_ID = ip.M_HU_PI_Item_Product_ID AND ip.isActive = 'Y'
		LEFT OUTER JOIN M_HU_PI_Item pii ON ip.M_HU_PI_Item_ID = pii.M_HU_PI_Item_ID AND pii.isActive = 'Y'
		LEFT OUTER JOIN M_HU_PI_Item pmi ON pmi.M_HU_PI_Version_ID = pii.M_HU_PI_Version_ID  AND pmi.isActive = 'Y'
			AND pmi.ItemType= 'PM'
		LEFT OUTER JOIN M_HU_PackingMaterial pm ON pmi.M_HU_PackingMaterial_ID = pm.M_HU_PackingMaterial_ID AND pm.isActive = 'Y'
		-- Product and its translation
		LEFT OUTER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'

		LEFT OUTER JOIN C_BPartner_Product bpp ON bp.C_BPartner_ID = bpp.C_BPartner_ID AND bpp.isActive='Y'
			AND p.M_Product_ID = bpp.M_Product_ID
		LEFT OUTER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
		-- Unit of measurement and its translation
		LEFT OUTER JOIN C_UOM uom ON ol.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
		-- ADR Attribute
		LEFT OUTER JOIN	LATERAL(
			SELECT 	String_agg ( ai_value, ', ' ) AS Attributes, M_AttributeSetInstance_ID
			FROM 	Report.fresh_Attributes
			WHERE	at_Value IN ( '1000015', '1000001', '1000002' ) -- Marke (ADR), task 08891: also Herkunft, task 2237: also Label
				AND M_AttributeSetInstance_ID = ol.M_AttributeSetInstance_ID AND  ol.M_AttributeSetInstance_ID != 0
			GROUP BY	M_AttributeSetInstance_ID
		) att ON TRUE
	WHERE
		1=1
		AND reportLine.IsActive='Y'
		AND COALESCE(pc.M_Product_Category_ID, -1) != getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID)
		AND o.IsSOTrx != 'N'
		AND o.DocStatus = 'CO'
),
-- The record's OWN line set -- derived from article_rows above, not re-filtered by hand (two copies of
-- the same predicate drift; deriving cannot). The carry-rule test below must intersect against THIS (the
-- record's own lines), not against the order's full line set.
record_lines AS (
	SELECT line FROM article_rows
),
-- The order's text lines, each paired with the run of article lines it belongs with (carry-rule 1-3):
-- article and text lines live in one physical sequence (Line), a text line's run is the article lines
-- beneath it up to the next text line or end of order, and consecutive text lines (nothing but text
-- lines between them) share the run of the LAST line of their block. The run is a property of the
-- ORDER's own line sequence -- independent of which report record we are filtering for; record_lines
-- above is only consulted afterwards, by the rule-4 test in the final branch.
text_runs AS (
	SELECT
		tl.C_Doc_TextLine_ID,
		tl.Line AS tl_line,
		tl.TextLine,
		tl.TextLineScope,
		block.block_end_line,
		(
			SELECT MIN(t2.Line)
			FROM C_Doc_TextLine t2
			WHERE t2.C_Order_ID = tl.C_Order_ID AND t2.IsActive = 'Y' AND t2.Line > block.block_end_line
		) AS next_block_start_line
	FROM target_report tr
		INNER JOIN C_Doc_TextLine tl ON tl.C_Order_ID = tr.C_Order_ID AND tl.IsActive = 'Y'
		-- The next article line strictly after tl, anywhere in the order (ALL active order lines --
		-- packaging material and any DocStatus included: the run is about the order's own physical
		-- line sequence, not about which lines this particular report happens to carry).
		CROSS JOIN LATERAL (
			SELECT MIN(ol2.Line) AS next_article_line
			FROM C_OrderLine ol2
			WHERE ol2.C_Order_ID = tl.C_Order_ID AND ol2.IsActive = 'Y' AND ol2.Line > tl.Line
		) next_article
		-- Rule 3: the last line of the maximal run of consecutive text lines starting at (or containing)
		-- tl -- i.e. the furthest text line reachable from tl with no article line in between.
		CROSS JOIN LATERAL (
			SELECT MAX(t.Line) AS block_end_line
			FROM C_Doc_TextLine t
			WHERE t.C_Order_ID = tl.C_Order_ID AND t.IsActive = 'Y' AND t.Line >= tl.Line
			  AND (next_article.next_article_line IS NULL OR t.Line < next_article.next_article_line)
		) block
)
--
-- Article lines (unchanged predicates from the pre-text-line function; two NULL columns appended for the
-- new text/istextline output shape).
SELECT
	article_rows.*,
	NULL::character varying(2048) AS textline,
	'N' AS IsTextLine
FROM article_rows

UNION ALL

-- Free-text lines (C_Doc_TextLine), filtered by the carry rule: a text line is carried onto THIS record
-- when at least one order line of its run belongs to this record's own line set (rule 4), unless its own
-- scope overrides that (rule 5: a 'D' -- whole-document -- scoped line is always carried, printing at the
-- head when none of its run is present here). Column shape follows the same document-level vs.
-- article-level split worked out for the sibling sales-order report function (Docs_Sales_Order_Details):
-- report/order/warehouse/plant/partner/date/document-type identity columns are carried through unchanged
-- (true of a text row exactly as of an article row on the same record); every column that describes an
-- ARTICLE (product, attributes, price, capacity, quantities, container, UOM, the order-LINE id, and the
-- report-LINE id + its barcode -- both reached only through C_Order_MFGWarehouse_ReportLine, which a text
-- line has no row in) is NULL.
SELECT
	CASE
		WHEN tr_run.TextLineScope = 'D' AND NOT EXISTS (
			SELECT 1 FROM record_lines rl
			WHERE rl.line >= tr_run.block_end_line
			  AND (tr_run.next_block_start_line IS NULL OR rl.line < tr_run.next_block_start_line)
		)
			-- A whole-document-scoped line whose run is absent from this record still prints, at the
			-- head. The offset (1,000,000) guarantees a negative result: C_Doc_TextLine.Line is
			-- numeric(10,4), so tl_line is always well under 1,000,000, while C_OrderLine.Line is a
			-- positive integer by the system's own 10/20/30 convention (no negative/zero Line is ever
			-- produced) -- so the offset result never collides with a real article Line. Relative order
			-- among several such lines (if that ever happens) still follows their own Line, since the
			-- offset is a constant.
			THEN tr_run.tl_line - 1000000
		ELSE tr_run.tl_line
	END AS line,
	NULL::text AS attributes,
	NULL::character varying AS prodValue,
	NULL::character varying AS value,
	NULL::character varying(255) AS name,
	NULL::character varying AS ean,
	NULL::numeric AS pricelist,
	NULL::numeric AS capacity,
	NULL::numeric AS priceactual,
	NULL::numeric AS qtyenteredtu,
	NULL::numeric AS qtyentered,
	NULL::character varying(60) AS container,
	NULL::character varying(10) AS uomsymbol,
	target_report.C_Order_MFGWarehouse_Report_ID,
	target_report.DocumentType AS ReportDocumentType,
	NULL::numeric AS C_Order_MFGWarehouse_ReportLine_ID,
	target_report.C_Order_ID,
	NULL::numeric AS c_orderline_id,
	target_report.M_Warehouse_ID,
	target_report.PP_Plant_ID,
	o.C_BPartner_ID,
	o.DatePromised,
	NULL::character varying(255) AS barcode,
	tr_run.TextLine AS textline,
	'Y' AS IsTextLine
FROM text_runs tr_run
	CROSS JOIN target_report
	INNER JOIN C_Order o ON o.C_Order_ID = target_report.C_Order_ID AND o.isActive = 'Y'
WHERE
	o.IsSOTrx != 'N'
	AND o.DocStatus = 'CO'
	AND (
		tr_run.TextLineScope = 'D'
		OR EXISTS (
			SELECT 1 FROM record_lines rl
			WHERE rl.line >= tr_run.block_end_line
			  AND (tr_run.next_block_start_line IS NULL OR rl.line < tr_run.next_block_start_line)
		)
	)

-- Postgres UNION ORDER BY only allows result-column names, no expressions -- 'Y' > 'N', so DESC puts a
-- text line ahead of an article line landing on the exact same position (matching the run's own
-- inclusive lower bound above: an article tied with a text line's Line is "beneath" it, per this
-- tiebreak, and is therefore part of that text line's run).
ORDER BY line, istextline DESC

$$
LANGUAGE sql STABLE;
