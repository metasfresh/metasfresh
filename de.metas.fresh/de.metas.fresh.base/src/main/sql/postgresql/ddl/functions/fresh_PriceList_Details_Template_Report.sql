CREATE OR REPLACE FUNCTION report.fresh_pricelist_details_template_report(IN p_c_bpartner_id numeric, IN p_m_pricelist_version_id numeric, IN p_alt_pricelist_version_id numeric, IN p_ad_language character varying)
    RETURNS TABLE(
                     prodvalue text, customerproductnumber text, productcategory text, productname text,
                     attributes text,itemproductname text, qty numeric, uomsymbol text, pricestd numeric) AS
$BODY$
--
SELECT
    plc.value AS prodvalue, plc.customerproductnumber as customerproductnumber,
    plc.productcategory as productcategory, plc.productname as productname, plc.attributes as attributes,
    plc.itemproductname as itemproductname,
    -- if packing instructions exist, we will display the qty one as it refers to one TU (Transportation Unit).
    CASE WHEN plc.itemproductname IS NOT NULL THEN 1 ELSE plc.qtycuspertu END AS qty,
    plc.uomsymbol as uomsymbol, plc.pricestd as pricestd
FROM
    report.fresh_PriceList_Details_Report( $1, $2, $3, $4) plc;
--
$BODY$
    LANGUAGE sql STABLE
                 COST 100
                 ROWS 1000;
