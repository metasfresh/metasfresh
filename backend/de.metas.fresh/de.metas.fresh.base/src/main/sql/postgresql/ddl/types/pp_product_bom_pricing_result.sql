CREATE TYPE de_metas_endcustomer_fresh_reports.pp_product_bom_pricing_result AS
(
    ProductValue                 varchar,
    ProductName                  varchar,
    UOM                          varchar,
    ValidFrom                    date,
    ProductPriceDifference       varchar,
    ProductsListPriceDifferences text
)
;

COMMENT ON TYPE de_metas_endcustomer_fresh_reports.pp_product_bom_pricing_result IS 'The result of getBOMProductDifferences(), it is used to provide an overview of the differences between the Current List Price vs the average List Price of BOM Component Products.'
;
