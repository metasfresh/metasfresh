DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.get_BOM_Product_Differences(numeric,
                                                                                       numeric,
                                                                                       date)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.get_BOM_Product_Differences(p_pp_product_bom_id numeric,
                                                                                          p_priceListId       numeric,
                                                                                          p_onDate            date)
    RETURNS setof de_metas_endcustomer_fresh_reports.pp_product_bom_pricing_result
    LANGUAGE plpgsql
AS

$BODY$
DECLARE
    filterCriteria               varchar;
    bom                          record;
    bomLine                      record;
    bomLineProduct               varchar;
    bomProductPrice              numeric;
    bomProductCost               numeric;
    subBomID                     numeric;
    subBomPrice                  numeric;
    subBomCost                   numeric;
    bomProductPriceDifferencePct numeric;
    result                       de_metas_endcustomer_fresh_reports.pp_product_bom_pricing_result;
    DIGITS_TO_ROUND              integer := 2;
BEGIN
    IF p_pp_product_bom_id IS NULL THEN
        filterCriteria := '1=1';
    ELSE
        filterCriteria := 'pp_product_bom_id = ' || p_pp_product_bom_id;
    END IF;
    FOR bom IN EXECUTE FORMAT('SELECT * FROM PP_PRODUCT_BOM WHERE IsActive=''Y'' AND docstatus = ''CO'' AND  %s ORDER BY name', filterCriteria)
        LOOP
            SELECT value, name FROM m_product WHERE m_product_id = bom.m_product_id INTO result.ProductValue, result.ProductName;
            SELECT name FROM c_uom WHERE c_uom_id = bom.c_uom_id INTO result.UOM;
            result.ValidFrom := bom.validFrom;
            bomProductPrice := de_metas_endcustomer_fresh_reports.get_BOM_Product_Price(bom.pp_product_bom_id, p_priceListId, p_onDate);
            bomProductCost := de_metas_endcustomer_fresh_reports.get_BOM_Product_Cost(bom.pp_product_bom_id, p_priceListId, p_onDate);
            IF bomProductPrice > 0 THEN
                result.ProductPriceDifference := ROUND((bomProductPrice - bomProductCost) * 100 / bomProductCost, DIGITS_TO_ROUND) || '%';
            ELSE
                result.ProductPriceDifference := '0%';
            END IF;
            result.ProductsListPriceDifferences := '';
            FOR bomLine IN SELECT * FROM PP_PRODUCT_BOMLINE WHERE pp_product_bom_id = bom.pp_product_bom_id AND isactive = 'Y' AND componenttype = 'CO' ORDER BY pp_product_bomline_id
                LOOP
                    bomLineProduct := (SELECT name FROM m_product WHERE m_product_id = bomLine.m_product_id);
                    subBomID := (SELECT pp_product_bom_id FROM pp_product_bom WHERE m_product_id = bomLine.m_product_id AND isactive = 'Y' AND docstatus = 'CO' ORDER BY validFrom DESC LIMIT 1);
                    IF subBomID IS NOT NULL THEN
                        subBomPrice := de_metas_endcustomer_fresh_reports.get_BOM_Product_Price(subBomID, p_priceListId, p_onDate);
                        subBomCost := de_metas_endcustomer_fresh_reports.get_BOM_Product_Cost(subBomID, p_priceListId, p_onDate);
                        IF bomProductPrice > 0 THEN
                            bomProductPriceDifferencePct := (subBomPrice - subBomCost) * 100 / subBomCost;
                        ELSE
                            bomProductPriceDifferencePct := 0;
                        END IF;
                    ELSE
                        bomProductPriceDifferencePct := de_metas_endcustomer_fresh_reports.get_Product_Price_Difference_Pct(bomLine.m_product_id, p_priceListId, p_onDate);
                    END IF;
                    result.ProductsListPriceDifferences := result.ProductsListPriceDifferences || bomLineProduct || ': ' || ROUND(bomProductPriceDifferencePct, DIGITS_TO_ROUND) || '%' || e'\n';
                END LOOP;
            IF (LENGTH(result.ProductsListPriceDifferences) > 0) THEN
                result.ProductsListPriceDifferences := SUBSTRING(result.ProductsListPriceDifferences, 1, LENGTH(result.ProductsListPriceDifferences) - 1);
            END IF;
            RETURN NEXT result;
        END LOOP;
    RETURN;
END
$BODY$
;

COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.get_BOM_Product_Differences(numeric, numeric, date) IS 'Returns the difference between the cost of purchasing at the current prices all the components of a BOM product and the cost of purchasing at the average purchase prices of the components. The difference is expressed as a percentage from the average purchase price.'
;
