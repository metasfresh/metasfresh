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


UPDATE AD_Process
SET SQLStatement = 'SELECT ProductValue as "Suchschlüssel",
           ProductName as "Produktname",
           UOM as "Maßeinheit",
           ValidFrom as "Gültig ab",
           ProductPriceDifference as "Produkt Preisdifferenz",
           ProductsListPriceDifferences as "Komponente Preisdifferenzen"
    FROM de_metas_endcustomer_fresh_reports.get_BOM_Product_Differences(@PP_Product_BOM_ID/NULL@, @M_PriceList_ID@, ''@Date@''::date )',
    updated      = TO_TIMESTAMP('2022-03-15 20:40:20', 'YYYY-MM-DD HH24:MI:SS'), updatedby = 100
WHERE ad_process_id = 584994
;