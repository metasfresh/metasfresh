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

COMMENT ON TYPE de_metas_endcustomer_fresh_reports.pp_product_bom_pricing_result IS 'The result of getBOMProductDifferences()'
;

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.get_Product_Price(numeric,
                                                                             numeric,
                                                                             date)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.get_Product_Price(IN p_productId   numeric,
                                                                                IN p_priceListId numeric,
                                                                                IN p_onDate      date)
    RETURNS table
            (
                priceStd      numeric,
                c_uom_id      numeric,
                c_currency_id numeric
            )
    LANGUAGE plpgsql
AS
$BODY$
DECLARE
    result           record;
    priceList        record;
    priceListVersion record;
BEGIN
    --get m_pricelist
    SELECT * FROM m_pricelist INTO priceList;
    --get m_pricelist_version
    SELECT *
    FROM M_PriceList_Version
    WHERE M_PriceList_ID = p_priceListId
      AND TRUNC(ValidFrom, 'DD') <= TRUNC(p_onDate::TIMESTAMP, 'DD')
      AND IsActive = 'Y'
    ORDER BY ValidFrom DESC NULLS FIRST
    INTO priceListVersion;

    SELECT *
    FROM M_ProductPrice
    WHERE IsActive = 'Y'
      AND M_PriceList_Version_ID = priceListVersion.m_pricelist_version_id
      AND M_Product_ID = p_productId
      AND IsInvalidPrice <> 'Y'
      AND IsAttributeDependant = 'N'
      AND M_HU_PI_Item_Product_ID IS NULL
    INTO result;

    IF (result.m_productprice_id IS NOT NULL) AND (pricelist.m_pricelist_id IS NOT NULL) THEN
        RETURN QUERY (SELECT result.priceStd, result.c_uom_id, priceList.c_currency_id);
        -- calculated from current pricelist
    ELSIF pricelist.basepricelist_id IS NOT NULL THEN
        RETURN QUERY SELECT * FROM de_metas_endcustomer_fresh_reports.get_Product_Price(p_productId, pricelist.basepricelist_id, p_onDate);
        -- try to retrieve from basepricelist
    END IF;
END;
$BODY$
;

COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.get_Product_Price(numeric, numeric, date) IS 'Retrieve the current price of the given product in the given pricelist at the given date'
;

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.get_Product_Price_Difference_Pct(numeric,
                                                                                            numeric,
                                                                                            date)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.get_Product_Price_Difference_Pct(p_productId   numeric,
                                                                                               p_priceListId numeric,
                                                                                               p_onDate      date)
    RETURNS numeric
    LANGUAGE plpgsql
AS
$BODY$
DECLARE
    priceStd        numeric;
    currentCost     numeric;
    priceUOMId      numeric;
    costUOMId       numeric;
    priceCurrencyID numeric;
    costCurrencyID  numeric;
BEGIN
    SELECT * FROM de_metas_endcustomer_fresh_reports.get_Product_Price(p_productId, p_priceListId, p_onDate) INTO priceStd, priceUOMId, priceCurrencyID;
    SELECT currentcostprice, c_uom_id, c_currency_id
    FROM m_cost
    WHERE m_product_id = p_productId
      AND m_costelement_id = 1000002 --Bestellpreis Durchschnitt
    INTO currentCost, costUOMId, costCurrencyID;

    IF (priceUOMId IS NULL) OR (costUOMId IS NULL) THEN
        RETURN 0;
        -- is there a better way to signal that a price could not be found?
    END IF;
    IF priceUOMId != costUOMId THEN
        currentCost = priceuomconvert(p_productId, currentCost, costUOMId, priceUOMId, 8);
    END IF;
    IF (priceCurrencyID IS NULL) OR (costCurrencyID IS NULL) THEN
        RETURN 0;
    END IF;
    IF priceCurrencyID != costCurrencyID THEN
        currentCost := currencyconvert(currentCost, costCurrencyID, priceCurrencyID, p_onDate);
    END IF;
    IF (currentCost IS NULL) OR (currentcost = 0) THEN RETURN 0; END IF;
    RETURN (priceStd - currentCost) * 100 / currentCost;
END;
$BODY$
;

COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.get_Product_Price_Difference_Pct(numeric, numeric, date) IS 'Returns the difference between the current purchase price of a product and the average purchase price as a percentage of the current purchase price'
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
    linePrice      numeric;
    lineUOMId      numeric;
    lineCurrencyId numeric;
    qtyBom         numeric;
BEGIN
    FOR bomLine IN SELECT * FROM pp_product_bomline WHERE pp_product_bom_id = p_pp_product_bom_id AND isactive = 'Y' AND componenttype = 'CO'
        LOOP
            -- is this product a BOM?
            subBomUOMId := (SELECT c_uom_id FROM pp_product_bom WHERE pp_product_bom.m_product_id = bomLine.m_product_id AND pp_product_bom.isactive = 'Y' AND pp_product_bom.validfrom <= p_onDate AND (pp_product_bom.validto >= p_onDate OR pp_product_bom.validTo IS NULL));
            IF subBomUOMId IS NOT NULL THEN
                subBomPrice := de_metas_endcustomer_fresh_reports.get_BOM_Product_Cost(bomLine.m_product_id, p_priceListId, p_onDate) * bomLine.qtybom;
                subBomPrice := priceuomconvert(bomLine.m_product_id, subBomPrice, subBomUOMId, bomLine.c_uom_id, 8);

                IF (bomline.isqtypercentage = 'Y') THEN
                    qtyBom := bomline.qtybom;
                ELSE
                    qtyBom := bomline.qtybom * bomline.qtybatch;
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
            IF bomLine.isqtypercentage THEN
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
            RAISE NOTICE '% .... %', lineprice,qtyBom;
            bomPrice := bomPrice + (linePrice * qtyBom);
            RAISE NOTICE '%', bomPrice;
        END LOOP;
    RETURN bomPrice;
END;
$BODY$
;

COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.get_BOM_Product_Price(numeric, numeric, date) IS 'For a given PP_Product_BOM_ID returns the sum of all purchase prices of the products and quantities used in the BOM. How much would it cost to purchase now all the components to make one BOM product.'
;


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
    subBomUOMId    numeric;
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
            subBomUOMId := (SELECT c_uom_id FROM pp_product_bom WHERE pp_product_bom.m_product_id = bomLine.m_product_id AND pp_product_bom.isactive = 'Y' AND pp_product_bom.validfrom <= p_onDate AND (pp_product_bom.validto >= p_onDate OR pp_product_bom.validTo IS NULL));
            IF subBomUOMId IS NOT NULL THEN
                subBomCost := de_metas_endcustomer_fresh_reports.get_BOM_Product_Cost(bomLine.m_product_id, p_priceListId, p_onDate) * bomLine.qtybom;
                subBomCost := priceuomconvert(bomLine.m_product_id, subBomCost, subBomUOMId, bomLine.c_uom_id, 8);
                IF (bomline.isqtypercentage = 'Y') THEN
                    qtyBom := bomline.qtybom;
                ELSE
                    qtyBom := bomline.qtybom * bomline.qtybatch;
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
            IF bomLine.isqtypercentage THEN
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
    bomProductPriceDifferencePct numeric;
    result                       de_metas_endcustomer_fresh_reports.pp_product_bom_pricing_result;
    DIGITS_TO_ROUND              integer := 2;
BEGIN
    IF p_pp_product_bom_id IS NULL THEN
        filterCriteria := '1=1';
    ELSE
        filterCriteria := 'pp_product_bom_id = ' || p_pp_product_bom_id;
    END IF;
    FOR bom IN EXECUTE FORMAT('SELECT * FROM PP_PRODUCT_BOM WHERE IsActive=''Y'' AND %s', filterCriteria)
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
            FOR bomLine IN SELECT * FROM PP_PRODUCT_BOMLINE WHERE pp_product_bom_id = bom.pp_product_bom_id AND isactive = 'Y' AND componenttype = 'CO'
                LOOP
                    bomLineProduct := (SELECT name FROM m_product WHERE m_product_id = bomLine.m_product_id);
                    bomProductPriceDifferencePct := de_metas_endcustomer_fresh_reports.get_Product_Price_Difference_Pct(bomLine.m_product_id, p_priceListId, p_onDate);
                    result.ProductsListPriceDifferences := result.ProductsListPriceDifferences || bomLineProduct || ': ' || ROUND(bomProductPriceDifferencePct, DIGITS_TO_ROUND) || '%' || e'\n';
                END LOOP;
            IF (LENGTH(result.ProductsListPriceDifferences) > 0) THEN
                result.ProductsListPriceDifferences := SUBSTRING(result.ProductsListPriceDifferences, 1, LENGTH(result.ProductsListPriceDifferences) - 1);
            END IF;
            RETURN NEXT result;
        END LOOP;
    RETURN;
END;
$BODY$
;

COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.get_BOM_Product_Differences(numeric, numeric, date) IS 'Returns the difference between the cost of purchasing at the current prices all the components of a BOM product and the cost of purchasing at the average purchase prices of the components. The difference is expressed as a percentage from the average purchase price.'
;
