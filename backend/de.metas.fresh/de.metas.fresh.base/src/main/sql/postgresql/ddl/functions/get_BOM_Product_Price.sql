DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.get_BOM_Product_Price(numeric,
                                                                                 numeric,
                                                                                 date)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.get_BOM_Product_Price(p_pp_product_bom_id numeric,
                                                                                    p_priceListId       numeric,
                                                                                    p_onDate            date)
    RETURNS NUMERIC
    LANGUAGE plpgsql
AS

$BODY$
DECLARE
    bomLine        record;
    bomPrice       numeric := 0;
    subBomPrice    numeric;
    subBomUomId    numeric;
    subBomId       numeric;
    linePrice      numeric;
    lineUOMId      numeric;
    lineCurrencyId numeric;
    qtyBom         numeric;
BEGIN
    FOR bomLine IN SELECT * FROM pp_product_bomline WHERE pp_product_bom_id = p_pp_product_bom_id AND isactive = 'Y' AND componenttype = 'CO'
        LOOP
            -- is this product a BOM?
            SELECT c_uom_id, pp_product_bom_id FROM pp_product_bom WHERE pp_product_bom.m_product_id = bomLine.m_product_id AND pp_product_bom.isactive = 'Y' AND docstatus = 'CO' AND pp_product_bom.validfrom <= p_onDate AND (pp_product_bom.validto >= p_onDate OR pp_product_bom.validTo IS NULL) INTO subBomUomId, subBomId;
            IF subBomId IS NOT NULL THEN
                subBomPrice := de_metas_endcustomer_fresh_reports.get_BOM_Product_Price(subBomId, p_priceListId, p_onDate);
                subBomPrice := priceuomconvert(bomLine.m_product_id, subBomPrice, bomLine.c_uom_id, subBomUOMId, 8);
                IF bomline.isqtypercentage = 'Y' THEN
                    qtyBom := bomline.qtybatch;
                ELSE
                    qtyBom := bomline.qtybom;
                END IF;
                IF (subBomPrice IS NULL) OR (qtyBom IS NULL) THEN
                    CONTINUE;
                END IF;
                bomPrice := bomPrice + subBomPrice * qtyBom;
                CONTINUE;
            END IF;
            SELECT * FROM de_metas_endcustomer_fresh_reports.get_Product_Price(bomLine.m_product_id, p_priceListId, p_onDate) INTO linePrice, lineUOMId, lineCurrencyId;

            IF lineUOMId IS NULL OR lineCurrencyId IS NULL THEN
                CONTINUE ;
            END IF;
            IF bomLine.isqtypercentage = 'Y' THEN
                qtyBom := bomLine.qtybatch;
            ELSE
                qtyBom := bomLine.qtybom;
            END IF;
            IF lineUOMId != bomLine.c_uom_id THEN
                linePrice := priceuomconvert(bomLine.m_product_id, linePrice, bomLine.c_uom_id, lineUOMId, 8);
            END IF;
            IF bomLine.scrap IS NOT NULL AND bomLine.scrap > 0 THEN
                qtyBom := qtyBom * (100 + bomLine.scrap) / 100;
            END IF;
            bomPrice := bomPrice + (linePrice * qtyBom);
        END LOOP;
    RETURN bomPrice;
END;
$BODY$
;

COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.get_BOM_Product_Price(numeric, numeric, date) IS 'For a given PP_Product_BOM_ID returns the sum of all purchase prices of the products and quantities used in the BOM. How much would it cost to purchase now all the components to make one BOM product.'
;
