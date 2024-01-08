drop function if exists pp_product_bom_recursive_report(p_pp_product_bom_id numeric,referencedate timestamp without time zone);
create function pp_product_bom_recursive_report(p_pp_product_bom_id numeric,referencedate timestamp without time zone)
    returns TABLE(line text, productvalue character varying, productname character varying, qtybom numeric, percentage numeric, uomsymbol character varying)
    stable
    language sql
as
$$
select
    t.Line,
    t.ProductValue,
    t.ProductName,
    t.QtyBOM,
    t.Percentage,
    t.UOMSymbol
from PP_Product_BOM_Recursive(PP_Product_BOM_Recursive_Report.p_PP_Product_BOM_ID, null,referencedate) t
order by t.path
$$;

alter function pp_product_bom_recursive_report(numeric,timestamp without time zone) owner to metasfresh;
