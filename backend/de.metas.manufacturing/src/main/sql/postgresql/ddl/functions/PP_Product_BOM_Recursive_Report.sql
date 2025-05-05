DROP FUNCTION IF EXISTS pp_product_bom_recursive_report(p_pp_product_bom_id numeric,
                                                        referencedate       timestamp WITHOUT TIME ZONE)
;

CREATE FUNCTION pp_product_bom_recursive_report(p_pp_product_bom_id numeric,
                                                referencedate       timestamp WITHOUT TIME ZONE)
    RETURNS TABLE
            (
                line         text,
                productvalue character varying,
                productname  character varying,
                qtybom       numeric,
                percentage   numeric,
                uomsymbol    character varying,
                supplier     text
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT t.Line,
       t.ProductValue,
       t.ProductName,
       t.QtyBOM,
       t.Percentage,
       t.UOMSymbol,
       t.Supplier
FROM PP_Product_BOM_Recursive(PP_Product_BOM_Recursive_Report.p_PP_Product_BOM_ID, NULL, referencedate) t
ORDER BY t.path
$$
;
