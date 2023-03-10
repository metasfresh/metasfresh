CREATE OR REPLACE FUNCTION pp_product_bom_recursive_report(p_pp_product_bom_id numeric)
    RETURNS TABLE(line text, productvalue character varying, productname character varying, qtybom numeric, percentage numeric, uomsymbol character varying, supplier text)
    STABLE
    LANGUAGE sql
AS
$$
select
    t.Line,
    t.ProductValue,
    t.ProductName,
    t.QtyBOM,
    t.Percentage,
    t.UOMSymbol,
    t.Supplier
from PP_Product_BOM_Recursive(PP_Product_BOM_Recursive_Report.p_PP_Product_BOM_ID, null) t
order by t.path
$$
;

ALTER FUNCTION pp_product_bom_recursive_report(numeric) OWNER TO metasfresh
;