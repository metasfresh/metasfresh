-- Expose C_OrderLine.DescriptionAboveLine (the free text printed above an order line) as
-- "descriptionaboveline" on the HU invoice line-detail report source. The function did not
-- reach C_OrderLine at all, so the column comes with a new LEFT OUTER JOIN via
-- c_invoiceline.c_orderline_id -- LEFT so invoice lines without an order line (manually added
-- lines) are kept.
--
-- Source DDL:
--   backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/functions/Docs_Sales_Invoice_Details_HU.sql
--
-- The RETURNS TABLE output signature changes, so the function is dropped before it is created
-- (a bare CREATE OR REPLACE cannot change the output signature).

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Details_HU ( IN p_C_Invoice_ID numeric, IN p_AD_Language Character Varying (6) );
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Details_HU ( IN p_C_Invoice_ID numeric, IN p_AD_Language Character Varying (6) )
    RETURNS TABLE
            (
                shipped numeric,
                retour numeric,
                Name character varying,
                Price numeric,
                LineNetAmt numeric,
                UOMSymbol character varying(10),
                rate numeric,
                IsPrintTax character(1),
                QtyEntered numeric,
                description  character varying,
                IsPrintWhenPackingMaterial char(1),
                DescriptionAboveLine character varying
            )
AS
$$
SELECT
    SUM ( CASE
              WHEN il.QtyEntered > 0 THEN il.QtyEntered
                                     ELSE 0
          END )				AS shipped,
    SUM ( CASE
              WHEN il.QtyEntered < 0 THEN il.QtyEntered*-1
                                     ELSE 0
          END )				AS retour,
    COALESCE(pt.Name, p.Name)		AS Name,
    il.PriceEntered			AS Price,
    SUM(il.LineNetAmt)			AS LineNetAmt,
    COALESCE(uom.UOMSymbol, uomt.UOMSymbol)	AS UOMSymbol,
    t.rate,
    bpg.IsPrintTax,
    SUM(il.QtyEntered) as QtyEntered,
    il.Description,
    p.IsPrintWhenPackingMaterial,
    ol.DescriptionAboveLine
FROM
    C_InvoiceLine il
        INNER JOIN C_Invoice i ON il.C_Invoice_ID = i.C_Invoice_ID AND i.isActive = 'Y'
        INNER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
        LEFT OUTER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID AND bpg.isActive = 'Y'
        -- get order line for the free-text-above-order-line column
        LEFT OUTER JOIN C_OrderLine ol ON ol.C_OrderLine_ID = il.C_OrderLine_ID
        -- Product and its translation
        LEFT OUTER JOIN M_Product p 			ON il.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
        LEFT OUTER JOIN M_Product_Trl pt 		ON il.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_AD_Language AND pt.isActive = 'Y'
        LEFT OUTER JOIN M_Product_Category pc 		ON p.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.isActive = 'Y'
        -- Unit of measurement and its translation
        LEFT OUTER JOIN C_UOM uom			ON il.C_UOM_ID = uom.C_UOM_ID AND uom.isActive = 'Y'
        LEFT OUTER JOIN C_UOM_Trl uomt			ON il.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_AD_Language AND uomt.isActive = 'Y'
        -- Tax rate
        LEFT OUTER JOIN C_Tax t			ON il.C_Tax_ID = t.C_Tax_ID AND t.isActive = 'Y'
WHERE
        il.C_Invoice_ID = p_C_Invoice_ID AND il.isActive = 'Y'
  AND pc.M_Product_Category_ID = getSysConfigAsNumeric('PackingMaterialProductCategoryID', il.AD_Client_ID, il.AD_Org_ID)
GROUP BY
    COALESCE(pt.Name, p.Name), COALESCE(uom.UOMSymbol, uomt.UOMSymbol), il.PriceEntered,
    t.rate,
    bpg.IsPrintTax, il.Description,   p.IsPrintWhenPackingMaterial, ol.DescriptionAboveLine
$$
    LANGUAGE sql STABLE
;
