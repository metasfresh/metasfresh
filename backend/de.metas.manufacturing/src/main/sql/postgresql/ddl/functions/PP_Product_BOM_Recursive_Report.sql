DROP FUNCTION IF EXISTS PP_Product_BOM_Recursive_Report(numeric)
;

CREATE OR REPLACE FUNCTION PP_Product_BOM_Recursive_Report(p_PP_Product_BOM_ID numeric)
    RETURNS table
            (
                Line         text,
                ProductValue varchar,
                ProductName  varchar,
                QtyBOM       numeric,
                Percentage   numeric,
                UOMSymbol    varchar,
                Cost         numeric
            )
AS
$BODY$
SELECT t.Line,
       t.ProductValue,
       t.ProductName,
       t.QtyBOM,
       t.Percentage,
       t.UOMSymbol,
       t.Cost
FROM PP_Product_BOM_Recursive(PP_Product_BOM_Recursive_Report.p_PP_Product_BOM_ID, NULL) t
ORDER BY t.path
$BODY$
    LANGUAGE sql STABLE
                 COST 100
;
