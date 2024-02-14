DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.get_BOM_Product_Cost(numeric,
                                                                                numeric,
                                                                                date)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.get_BOM_Product_Cost(p_pp_product_bom_id numeric,
                                                                                   p_priceListId       numeric,
                                                                                   p_onDate            date)
    RETURNS NUMERIC
    LANGUAGE plpgsql
AS

$BODY$
DECLARE
    bomLine        record;
    bomCost        numeric := 0;
    subBomCost     numeric;
    subBomUomId    numeric;
    subBomId       numeric;
    currentCost    numeric;
    costUOMId      numeric;
    costCurrencyId numeric;
    qtyBom         numeric;
    lineUOMId      numeric;
    lineCurrencyId numeric;
BEGIN
    FOR bomLine IN (SELECT * FROM pp_product_bomline WHERE pp_product_bom_id = p_pp_product_bom_id AND isActive = 'Y' AND componenttype = 'CO')
        LOOP
            -- is this product a BOM?
            SELECT c_uom_id, pp_product_bom_id FROM pp_product_bom WHERE pp_product_bom.m_product_id = bomLine.m_product_id AND pp_product_bom.isactive = 'Y' AND pp_product_bom.validfrom <= p_onDate AND (pp_product_bom.validto >= p_onDate OR pp_product_bom.validTo IS NULL) INTO subBomUomId, subBomId;
            IF subBomId IS NOT NULL THEN
                subBomCost := de_metas_endcustomer_fresh_reports.get_BOM_Product_Cost(subBomId, p_priceListId, p_onDate);
                subBomCost := priceuomconvert(bomLine.m_product_id, subBomCost, bomLine.c_uom_id, subBomUOMId, 8);
                IF (bomline.isqtypercentage = 'Y') THEN
                    qtyBom := bomLine.qtybatch;
                ELSE
                    qtyBom := bomLine.qtybom;
                END IF;
                IF (subBomCost IS NULL) OR (qtyBom IS NULL) THEN
                    CONTINUE;
                END IF;
                bomCost := bomCost + subBomCost * qtyBom;
                CONTINUE;
            END IF;

            --don't include the cost if the product isn't sold on this pricelist
            SELECT * FROM de_metas_endcustomer_fresh_reports.get_Product_Price(bomLine.m_product_id, p_priceListId, p_onDate) INTO lineUOMId, lineCurrencyId;
            IF (lineUOMId IS NULL) OR (lineCurrencyId IS NULL) THEN
                CONTINUE;
            END IF;

            SELECT currentcostprice, c_uom_id, c_currency_id
            FROM m_cost
            WHERE m_product_id = bomLine.m_product_id
              AND m_costelement_id = 1000002 --Bestellpreis Durchschnitt
            INTO currentCost, costUOMId, costCurrencyId;

            IF costUOMId IS NULL OR costCurrencyId IS NULL OR currentCost = 0 THEN
                CONTINUE ;
            END IF;
            IF bomLine.isqtypercentage = 'Y' THEN
                qtyBom := bomLine.qtybatch;
            ELSE
                qtyBom := bomLine.qtybom;
            END IF;
            IF costUOMId != bomLine.c_uom_id THEN
                currentCost := priceuomconvert(bomLine.m_product_id, currentCost, bomLine.c_uom_id, costUOMId, 8);
            END IF;
            IF bomLine.scrap IS NOT NULL AND bomLine.scrap > 0 THEN
                qtyBom := qtyBom * (100 + bomLine.scrap) / 100;
            END IF;
            IF (currentCost IS NULL) OR (qtyBom IS NULL) THEN
                CONTINUE;
            END IF;
            bomCost := bomCost + (currentCost * qtyBom);
        END LOOP;
    RETURN bomCost;
END;
$BODY$
;

COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.get_BOM_Product_Cost(numeric, numeric, date) IS 'for a given PP_Product_BOM_ID returns the sum of all average purchase prices of the products and quantities used in the BOM. What''s the average cost to purchase all the components to make one BOM product.'
;
